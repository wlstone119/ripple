package com.xdong.ripple.crawler.music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdong.ripple.crawler.common.Constant;
import com.xdong.ripple.crawler.common.CrawlerUtil;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.strategy.CrawlerSearchInterface;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyInterface;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;

/**
 * 类WyCloudMusicCrawler.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei Mar 24, 2019 9:21:38 PM
 */
@Service
public class WyCloudMusicCrawler implements CrawlerStrategyInterface, CrawlerSearchInterface {

    private static Logger          logger    = Logger.getLogger(WyCloudMusicCrawler.class);

    private static String          domainUrl = "";

    @Autowired
    private IRpCrawlerSongsService rpSongsServiceImpl;

    @Override
    public Object execute(ParamVo paramVo) {
        List<String> resultList = new ArrayList<String>();

        String url = paramVo.getUrl();
        domainUrl = paramVo.getDomainUrl();

        int begin = paramVo.getBegin() <= 0 ? 0 : paramVo.getBegin();
        int end = paramVo.getEnd() <= 0 ? 1 : paramVo.getEnd();
        ExecutorService service = Executors.newCachedThreadPool();

        ArrayList<FutureTask<String>> list = new ArrayList<FutureTask<String>>();
        for (int i = begin; i < end; i++) {
            FutureTask<String> task = (FutureTask<String>) service.submit(new ExecuteTaskCallable(url, (i * 35) + ""));
            list.add(task);
        }

        for (FutureTask<String> task : list) {
            try {
                resultList.add(task.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("线程任务执行异常", e);
            }
        }

        return resultList;
    }

    private class ExecuteTaskCallable implements Callable<String> {

        private String url;

        public ExecuteTaskCallable(String url, String offset){
            this.url = modifyWangyiUrl(url, offset);
        }

        private String modifyWangyiUrl(String url, String offset) {
            return (url.contains("offset")) ? url.replaceAll("offset", offset) : url + "&offset=" + offset;
        }

        @Override
        public String call() {
            return getWyMusicByCat(url);
        }

    }

    /**
     * 爬取网易云音乐歌曲：类目 url：http://music.163.com/discover/playlist/?cat=民谣&order=hot&limit=35
     * 
     * @param url
     */
    private String getWyMusicByCat(String url) {
        try {
            Document dom = CrawlerUtil.connectUrl(url);
            Element songSheets = dom.getElementById("m-pl-container");
            if (songSheets != null) {
                for (Element songSheet : songSheets.children()) {
                    loopSongSheet(songSheet);
                }
            }
        } catch (IOException e) {
            logger.error("io exception:", e);
        } catch (Exception e) {
            logger.error(String.format("爬取音乐榜单时出现异常：url %s", url), e);
        }

        return url;
    }

    /**
     * 一个歌单元素循环，获取歌单中所有歌曲写入数据库
     * 
     * @param root
     * @throws IOException
     */
    private void loopSongSheet(Element songSheet) throws IOException {
        if (songSheet.hasAttr("href") && songSheet.hasAttr("title")
            && ("tit f-thide s-fc0".equals(songSheet.attr("class")))) {// <a>标签
            String songUrl = songSheet.attr("href").trim();// 歌单url

            // 歌单链接
            String songSheetUrl = appendUrl(songUrl);

            // 获取歌单下的所有歌曲
            Document songs = CrawlerUtil.connectUrl(songSheetUrl);
            Element songsDiv = songs.getElementById("song-list-pre-cache");
            Elements songSheetDiv = songs.getElementsByClass("cntc");
            Elements songsList = songsDiv.getElementsByTag("ul");
            if (songsList != null) {
                writeToDB(songsList, songSheetDiv.get(0));
            }
        } else {
            if (!songSheet.children().isEmpty()) {
                for (Element element : songSheet.children()) {
                    loopSongSheet(element);
                }
            }
        }
    }

    /**
     * 将得到的歌集下的音乐保存至数据库
     * 
     * @param musicTr
     * @throws IOException
     */
    private void writeToDB(Elements songsList, Element songSheetDiv) throws IOException {

        RpCrawlerSongsDo songDo = new RpCrawlerSongsDo();
        songDo.setcTime(new Date());
        songDo.setmTime(new Date());
        songDo.setmUser("system");
        songDo.setcUser("system");
        songDo.setStatus("valid");
        songDo.setResource(Constant.CRAWLER_RESOURCE_WANGYI);

        // 歌单名称
        Elements songSheetH2 = songSheetDiv.getElementsByTag("h2");
        if (!songSheetH2.isEmpty()) {
            songDo.setSongSheet(songSheetH2.html().replaceAll("&nbsp;", " "));
        }

        // 歌曲类型
        StringBuffer sb = new StringBuffer();
        Elements songType = songSheetDiv.getElementsByClass("u-tag");
        for (Element song : songType) {
            sb.append(" " + song.html());
        }
        songDo.setType(sb.toString());

        String songUrl = "";
        String href = null;
        Elements songs = songsList.get(0).getElementsByTag("li");
        for (Element song : songs) {
            href = song.getElementsByTag("a").attr("href");
            songUrl = getSongUrlById(href);
            Long songId = Long.parseLong(href.substring(href.indexOf("=") + 1, href.length()));
            
            if (rpSongsServiceImpl.checkSongIdExists(songId, Constant.CRAWLER_RESOURCE_WANGYI)) {
                continue;
            }

            // 播放器
            songDo.setResourcepath(appendIframe(songUrl));

            // 歌名
            songDo.setName(song.getElementsByTag("a").html());
            // 歌曲url
            songDo.setSongUrl(songUrl);

            songDo.setSongId(songId);

            // 歌曲url获取信息
            Document songDetail = CrawlerUtil.connectUrl(songDo.getSongUrl());
            Elements songDetailDiv = songDetail.getElementsByClass("cnt");
            Elements songDetailArr = songDetailDiv.get(0).getElementsByTag("p");
            for (int i = 0; i < songDetailArr.size(); i++) {
                Element ele = songDetailArr.get(i);

                if (containSpan(ele)) {
                    songDo.setSongAuthor(ele.getElementsByTag("span").attr("title"));
                } else {
                    songDo.setSongAlbum(ele.getElementsByTag("a").html());
                }
            }

            // 专辑图片
            Elements eles = songDetail.getElementsByClass("f-cb");
            Element albumPic = eles.get(0);
            String albumPicUrl = albumPic.getElementsByTag("img").attr("src");
            songDo.setSongAlbumPic(albumPicUrl.indexOf("?") > 0 ? albumPicUrl.substring(0,
                                                                                        albumPicUrl.indexOf("?")) : albumPicUrl);

            rpSongsServiceImpl.insert(songDo);
        }

    }

    private boolean containSpan(Element ele) {
        for (Element unit : ele.children()) {
            if (!unit.getElementsByTag("span").isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private String appendUrl(String url) {
        return domainUrl + url;
    }

    private String getSongUrlById(String songIdStr) {
        return domainUrl + songIdStr;
    }

    private String appendIframe(String id) {
        return "<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" "
               + "marginheight=\"0\" width=330 height=86 src=\"//music.163.com/outchain/player?type=2&id=" + id
               + "&auto=1&height=66\"></iframe>";
    }

    @Override
    public Object search(ParamVo paramVo) {
        try {
            Document dom = CrawlerUtil.connectUrl(paramVo.getUrl());
            Element songSheets = dom.getElementById("m-search");
            if (songSheets != null) {
                for (Element songSheet : songSheets.children()) {
                    loopSearchList(songSheet);
                }
            }
        } catch (IOException e) {
            logger.error("io exception:", e);
        } catch (Exception e) {

        }
        return null;
    }

    private void loopSearchList(Element songSheet) {
        if ("srchsongst".equals(songSheet.attr("class"))) {
            Elements elements = songSheet.children();
            for (Element ele : elements) {
                loopTable(ele);
            }
        } else {
            if (!songSheet.children().isEmpty()) {
                loopSearchList(songSheet);
            }
        }
    }

    private void loopTable(Element ele) {
        Elements elements = ele.children();
        for (Element ele2 : elements) {

        }

    }
}
