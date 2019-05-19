package com.xdong.ripple.crawler.music;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.xdong.ripple.common.crawler.SupplementErrorTask;
import com.xdong.ripple.common.enums.CrawlerMusicTaskTypeEnum;
import com.xdong.ripple.crawler.common.Constant;
import com.xdong.ripple.crawler.common.CrawlerResultDto;
import com.xdong.ripple.crawler.common.CrawlerUtil;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;

/**
 * 类BaiduMusicCrawler.java的实现描述：TODO 类实现描述
 * @author wanglei Mar 24, 2019 9:18:55 PM
 */
@Service
public class BaiduMusicCrawler extends AbstractMusicCrawler {

    private static Logger logger = Logger.getLogger(BaiduMusicCrawler.class);

    @Override
    protected String getCrawlerUrlByDataUrl(String url, int page) {
        String offset = String.valueOf(page * 20);
        return (url.contains("offset")) ? url.replaceAll("offset", offset) : url + "&offset=" + offset;
    }

    @Override
    protected CrawlerResultDto getResultByCrawlerUrl(String url) {
        CrawlerResultDto resultDto = new CrawlerResultDto();
        getBaiduHotTopMusic(url, resultDto);
        return resultDto;
    }

    @Override
    public boolean songSheetUrlCompensate(SupplementErrorTask task) {
        getBaiduHotTopMusic(task.getTaskUrl(), null);
        return true;
    }

    @Override
    public boolean songUrlCompensate(SupplementErrorTask task) {
        Document songs = CrawlerUtil.connectUrl(task.getTaskUrl());
        if (songs == null) {
            logger.error("线程：【" + Thread.currentThread().getName() + "】-> 补单未获取到网页信息，终止爬虫，url:" + task.getTaskUrl());

            // 放入补单队列
            SupplementErrorTask errorTask = new SupplementErrorTask(this.getClass().getName(),
                                                                    CrawlerMusicTaskTypeEnum.SONGS, task.getTaskUrl());
            errorTask.setRetryCount(task.getRetryCount() + 1);
            taskExecutor.errorUrlQueue.add(errorTask);

            return false;
        }

        Elements mainBody = songs.getElementsByClass("main-body");
        if (mainBody.isEmpty()) {
            logger.error("线程：【" + Thread.currentThread().getName() + "】-> 补单未获取到百度歌曲信息，url:" + task.getTaskUrl());
            return false;
        }

        // 收录歌曲信息
        executeSongUrl(songs, null);

        return true;
    }

    /**
     * 爬取百度音乐盒歌曲
     * 
     * @param url
     */
    public void getBaiduHotTopMusic(String url, CrawlerResultDto resultDto) {
        logger.info("线程：【" + Thread.currentThread().getName() + "】-> 开始爬取，url:" + url);

        Document dom = CrawlerUtil.connectUrl(url);
        if (dom == null) {
            logger.error("线程：【" + Thread.currentThread().getName() + "】-> 未获取到网页信息，终止爬虫，url:" + url);
            taskExecutor.errorUrlQueue.add(new SupplementErrorTask(this.getClass().getName(),
                                                                   CrawlerMusicTaskTypeEnum.SONGSHEET, url));
            return;
        }

        Elements songDivs = dom.getElementsByClass("songlist-list");
        Element songDiv = songDivs.get(0);
        Elements uls = songDiv.getElementsByTag("ul");
        if (uls != null) {
            Element songSheets = uls.get(0);
            for (Element songSheet : songSheets.children()) {
                try {
                    loopSongSheet(songSheet, resultDto);
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
    private void loopSongSheet(Element root, CrawlerResultDto resultDto) throws IOException {
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
                                logger.error("线程：【" + Thread.currentThread().getName()
                                             + "】-> 请检查获取的songUrl是否准确？songUrl:" + songUrl);
                                break;
                            }

                            String executeUrl = String.format(pageUrl, offset);
                            Document songs = CrawlerUtil.connectUrl(executeUrl);
                            if (songs == null) {
                                logger.error("线程：【" + Thread.currentThread().getName() + "】-> 未获取到网页信息，终止爬虫，url:"
                                             + executeUrl);

                                // 放入补单队列
                                taskExecutor.errorUrlQueue.add(new SupplementErrorTask(this.getClass().getName(),
                                                                                       CrawlerMusicTaskTypeEnum.SONGS,
                                                                                       executeUrl));

                                break;
                            }

                            Elements mainBody = songs.getElementsByClass("main-body");
                            if (mainBody.isEmpty()) {
                                logger.error("线程：【" + Thread.currentThread().getName()
                                             + "】-> 未获取到百度歌曲信息，退出当前歌单循环，最后一页url:" + executeUrl);
                                break;
                            }

                            // 收录歌曲信息
                            executeSongUrl(songs, resultDto);

                            offset += 20;
                        }
                    }
                }
            }
        }
    }

    private void executeSongUrl(Document songs, CrawlerResultDto resultDto) {

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

                // 所有歌曲遍历保存
                for (Element musicLi : ul.children()) {

                    // 填充歌曲信息
                    for (Element var : musicLi.children()) {
                        loopSong(var, songDo);
                    }

                    if (rpSongsServiceImpl.checkSongIdExists(songDo.getSongId(), Constant.CRAWLER_RESOURCE_BAIDU)) {
                        // logger.info("撞库成功，已存在：" + Constant.CRAWLER_RESOURCE_BAIDU + "-" + songDo.getSongId());
                        if (resultDto != null) {
                            resultDto.setRepatCount(resultDto.getRepatCount() + 1);
                        }
                        continue;
                    }

                    rpSongsServiceImpl.insert(songDo);

                    logger.info("已收录歌曲：" + songDo.getName() + " 歌曲来源及主键：" + Constant.CRAWLER_RESOURCE_BAIDU + "-"
                                + songDo.getSongId());
                    if (resultDto != null) {
                        resultDto.setInsertCount(resultDto.getInsertCount() + 1);
                    }
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

    private String appendUrl(String url) {
        return domainUrl + url;
    }
}
