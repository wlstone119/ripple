package com.xdong.ripple.crawler.music;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
        int end = paramVo.getEnd() <= 0 ? 1 : paramVo.getEnd();
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = begin; i < end; i++) {
            service.execute(new executeTaskRunnable(url, (i * 20) + ""));
        }
        return null;
    }

    private class executeTaskRunnable implements Runnable {

        private String url;

        public executeTaskRunnable(String url, String offset){
            this.url = modifyBaiduUrl(url, offset);
        }

        @Override
        public void run() {
            getBaiduHotTopMusic(url);
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
        if (root.hasAttr("href") && root.hasAttr("title") && !root.hasAttr("target")) {// <a>标签
            String songUrl = root.attr("href").trim();// 歌单url

            if (!songUrl.startsWith("http") && !songUrl.startsWith("https")) {
                songUrl = appendUrl(songUrl);
            }
            // 获取歌单下的所有歌曲
            Document songs = connectUrl(songUrl);
            Elements musicTbody = songs.getElementsByClass("song-list-btnBottom");
            if (musicTbody.isEmpty()) {
                musicTbody = songs.getElementsByClass("song-list-btnBoth");
            }
            if (!musicTbody.isEmpty()) {
                Element ul = musicTbody.get(0).getElementsByTag("ul").get(0);
                if (!ul.children().isEmpty()) {
                    for (Element musicLi : ul.children()) {
                        writeToDB(musicLi);
                    }
                }
            }
        } else {
            if (!root.children().isEmpty()) {
                for (Element element : root.children()) {
                    loopSongSheet(element);
                }
            }
        }
    }

    /**
     * 将得到的歌集下的音乐保存至数据库
     * 
     * @param musicTr
     */
    private void writeToDB(Element musicTr) {
        if (musicTr.children().isEmpty()) {
            return;
        }

        RpCrawlerSongsDo songDo = new RpCrawlerSongsDo();
        songDo.setcTime(new Date());
        songDo.setmTime(new Date());
        songDo.setmUser("system");
        songDo.setcUser("system");
        songDo.setStatus("valid");
        songDo.setResource(Constant.CRAWLER_RESOURCE_BAIDU);

        for (Element var : musicTr.children()) {
            loopSong(var, songDo);
        }

        rpSongsServiceImpl.insert(songDo);
        // logger.info(String.format("成功保存歌曲：歌曲名【%s】-- 歌手【%s】",
        // songDo.getName(), songDo.getSongAuthor()));
    }

    private void loopSong(Element var, RpCrawlerSongsDo songDo) {
        if (var.hasClass("song-title")) {
            Element aHref = var.child(0);
            String songName = aHref.html();
            String songUrl = aHref.attr("href").trim();
            songDo.setName(songName);
            if (!songUrl.startsWith("http") && !songUrl.startsWith("https")) {
                songUrl = appendUrl(songUrl);
            }
            songDo.setSongUrl(songUrl);
        } else if (var.hasClass("singer")) {
            if (!var.children().isEmpty()) {
                Node singerHref = var.child(0);
                if (singerHref != null) {
                    String singer = singerHref.attr("title").trim();
                    songDo.setSongAuthor(singer);
                }
            }
        } else if (var.hasClass("album-title")) {
            if (!var.childNodes().isEmpty()) {
                Node aHref = var.childNode(0);
                if (aHref != null) {
                    String albumName = aHref.attr("title").trim();
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

    private Document connectUrl(String url) throws IOException {
        return Jsoup.connect(url).userAgent("Mozilla").get();
    }

    private String appendUrl(String url) {
        return domainUrl + url;
    }

}
