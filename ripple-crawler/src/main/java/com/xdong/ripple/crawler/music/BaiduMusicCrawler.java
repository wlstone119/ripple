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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdong.ripple.crawler.common.Constant;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyInterface;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;

/**
 * 类BaiduMusicCrawler.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei Mar 24, 2019 9:18:55 PM
 */
@Service
public class BaiduMusicCrawler implements CrawlerStrategyInterface {

    private static Logger          logger    = Logger.getLogger(BaiduMusicCrawler.class);

    private static String          domainUrl = "";

    @Autowired
    private IRpCrawlerSongsService rpSongsServiceImpl;

    @Override
    public Object execute(ParamVo paramVo) {
        final String url = paramVo.getUrl();

        domainUrl = paramVo.getDomainUrl();

        int begin = paramVo.getBegin() <= 0 ? 0 : paramVo.getBegin();
        int end = paramVo.getLimitPage() <= 0 ? 1 : paramVo.getLimitPage();

        ExecutorService service = null;
        List<String> resultList = new ArrayList<String>();

        try {
            ArrayList<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();
            service = Executors.newCachedThreadPool();

            for (int i = begin; i < end; i++) {
                FutureTask<String> task = (FutureTask<String>) service.submit(new ExecuteTaskRunnable(url,
                                                                                                      (i * 20) + ""));
                taskList.add(task);
            }

            for (FutureTask<String> task : taskList) {
                try {
                    resultList.add(task.get());
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("线程任务执行异常", e);
                }
            }
        } finally {
            if (service != null) {
                service.shutdown();
                logger.info("爬虫任务完成，关闭线程池！");
            }
        }

        return resultList;
    }

    private class ExecuteTaskRunnable implements Callable<String> {

        private String url;

        public ExecuteTaskRunnable(String url, String offset){
            this.url = modifyBaiduUrl(url, offset);
        }

        @Override
        public String call() {
            getBaiduHotTopMusic(url);
            return url;
        }

    }

    private String modifyBaiduUrl(String url, String offSet) {
        return url.replace("$offset", offSet);
    }

    /**
     * 爬取百度音乐盒歌曲
     * 
     * @param url
     */
    public void getBaiduHotTopMusic(String url) {
        logger.info(String.format("线程：【" + Thread.currentThread().getName() + "】-> 开始爬取： %s", "href:" + url));

        Document dom = null;
        try {
            dom = connectUrl(url);
        } catch (IOException e) {
            logger.error("io exception:", e);
        }

        Elements songDivs = dom.getElementsByClass("songlist-list");
        Element songDiv = songDivs.get(0);
        Elements uls = songDiv.getElementsByTag("ul");
        if (uls != null) {
            Element songSheets = uls.get(0);
            for (Element songSheet : songSheets.children()) {
                try {
                    loopSongSheet(songSheet);
                } catch (Exception e) {
                    logger.warn(String.format("线程：【" + Thread.currentThread().getName() + "】爬取歌单时出现异常: %s",
                                              appendUrl(songSheet.getElementsByTag("a").attr("href"))),
                                e);
                }
            }
        }
    }

    /**
     * 一个歌单元素循环，获取歌单中所有歌曲写入数据库
     * 
     * @param root
     * @throws IOException
     */
    private void loopSongSheet(Element root) throws IOException {
        Elements elements = root.children();
        for (Element ele : elements) {
            if (ele.hasClass("text-title")) {
                Elements childs = ele.children();
                for (Element aSheet : childs) {
                    if (aSheet.hasAttr("href") && aSheet.hasAttr("title")) {
                        String songUrl = aSheet.attr("href").trim();// 歌单url

                        if (!songUrl.startsWith("http") && !songUrl.startsWith("https")) {
                            songUrl = appendUrl(songUrl);
                        }
                        String pageUrl = modifySongUrl(songUrl);

                        // 获取歌单下的所有歌曲
                        int offset = 0;
                        while (true) {
                            if (StringUtils.isBlank(pageUrl)) {
                                break;
                            }

                            String executeUrl = String.format(pageUrl, offset);

                            Document songs = connectUrl(executeUrl);
                            Elements mainBody = songs.getElementsByClass("main-body");

                            if (mainBody.isEmpty()) {
                                break;
                            } else {
                                Elements songSheetEle = songs.getElementsByClass("songlist-info-inside");
                                Elements musicTbody = songs.getElementsByClass("song-list-wrap");
                                if (!musicTbody.isEmpty()) {
                                    Element ul = musicTbody.get(0).getElementsByTag("ul").get(0);
                                    if (!ul.children().isEmpty()) {

                                        RpCrawlerSongsDo songDo = new RpCrawlerSongsDo();
                                        songDo.setcTime(new Date());
                                        songDo.setmTime(new Date());
                                        songDo.setmUser("system");
                                        songDo.setcUser("system");
                                        songDo.setStatus("valid");
                                        songDo.setResource(Constant.CRAWLER_RESOURCE_BAIDU);

                                        // 填充歌单信息
                                        fillSongSheet(songSheetEle, songDo);

                                        for (Element musicLi : ul.children()) {

                                            // 填充歌曲信息
                                            for (Element var : musicLi.children()) {
                                                loopSong(var, songDo);
                                            }

                                            if (rpSongsServiceImpl.checkSongIdExists(songDo.getSongId(),
                                                                                     Constant.CRAWLER_RESOURCE_BAIDU)) {
                                                logger.info("撞库成功，已存在：" + Constant.CRAWLER_RESOURCE_BAIDU + "-"
                                                            + songDo.getSongId());
                                                continue;
                                            }

                                            rpSongsServiceImpl.insert(songDo);

                                            logger.info("已收录歌曲：" + songDo.getName() + " 歌曲来源及主键："
                                                        + Constant.CRAWLER_RESOURCE_BAIDU + "-" + songDo.getSongId());
                                        }
                                    }
                                }
                            }

                            offset += 20;
                        }
                    }

                    break;
                }
            }
        }
    }

    private String modifySongUrl(String songUrl) {
        if (StringUtils.isNotBlank(songUrl)) {
            return songUrl + "/offset/%s?third_type=";
        }
        return null;
    }

    private void fillSongSheet(Elements songSheetEle, RpCrawlerSongsDo songDo) {
        Elements elems = songSheetEle.get(0).children();
        for (Element var : elems) {
            if (var.hasClass("songlist-info-pic")) {
                String songPicUrl = var.getElementsByTag("img").attr("src");
                songDo.setSongAlbumPic(songPicUrl);
            } else if (var.hasClass("songlist-info-msg")) {
                String songSheet = var.getElementsByTag("h1").html().trim();
                Element songSheetDiv = var.getElementsByClass("songlist-info-tag").get(0);
                String type = "";
                for (Element typeVar : songSheetDiv.children()) {
                    if (typeVar.html().contains("标签")) {
                        type += typeVar.html();
                    } else {
                        type += typeVar.html() + ",";
                    }
                }
                songDo.setType(type.substring(0, type.length() - 1));
                songDo.setSongSheet(songSheet);
            }
        }

    }

    private void loopSong(Element var, RpCrawlerSongsDo songDo) {
        if (var.hasClass("songlist-title")) {
            Element aHref = var.child(0);
            String songName = aHref.getElementsByTag("a").attr("title").trim();
            String songUrl = aHref.getElementsByTag("a").attr("href").trim();
            songDo.setName(songName);
            if (!songUrl.startsWith("http") && !songUrl.startsWith("https")) {
                songUrl = appendUrl(songUrl);
            }
            songDo.setSongUrl(songUrl);

            Long songId = getSongIdByUrl(songUrl);
            songDo.setSongId(songId);
        } else if (var.hasClass("songlist-album")) {
            if (!var.children().isEmpty()) {
                Element singerHref = var.child(0);
                if (singerHref != null) {
                    String singer = singerHref.getElementsByTag("a").html().trim();
                    songDo.setSongAuthor(singer);
                }
            }
        } else if (var.hasClass("album-name")) {
            if (!var.children().isEmpty()) {
                Element aHref = var.child(0);
                if (aHref != null) {
                    String albumName = aHref.html();
                    songDo.setSongAlbum(albumName);
                }
            }
        } else {
            if (!var.children().isEmpty()) {
                for (Element element : var.children()) {
                    loopSong(element, songDo);
                }
            }
        }
    }

    private Long getSongIdByUrl(String songUrl) {
        return Long.parseLong(songUrl.substring(songUrl.lastIndexOf("/") + 1, songUrl.length()));
    }

    private Document connectUrl(String url) throws IOException {
        return Jsoup.connect(url).userAgent("Mozilla").get();
    }

    private String appendUrl(String url) {
        return domainUrl + url;
    }

}
