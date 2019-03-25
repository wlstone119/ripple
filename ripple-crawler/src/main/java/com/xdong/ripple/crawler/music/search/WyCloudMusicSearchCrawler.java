package com.xdong.ripple.crawler.music.search;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xdong.ripple.crawler.common.CrawlerUtil;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.music.vo.MusicVo;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyInterface;

/**
 * 类WyCloudMusicSearchCrawler.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei Mar 24, 2019 9:26:10 PM
 */
public class WyCloudMusicSearchCrawler implements CrawlerStrategyInterface {

    private static Logger logger = Logger.getLogger(WyCloudMusicSearchCrawler.class);

    @Override
    public List<MusicVo> execute(ParamVo paramVo) {
        String searchKey = paramVo.getSearchKey();
        if (StringUtils.isBlank(searchKey)) {
            return null;
        }
        // String bufferUrl = Constant.CRAWLER_PREFIXURL_WANGYI + "/search/#/?s=" + searchKey + "&type=1";
        String bufferUrl = "http://music.163.com/search/#/?s=xuezhiqian&type=1";

        try {
            Document dom = CrawlerUtil.connectUrl(bufferUrl);

            Elements songSheets = dom.getElementsByClass("srchsongst");
            if (songSheets != null) {
                Element element = songSheets.get(0);
                for (Element ele : element.children()) {
                    System.out.println(ele.text());
                }

            }

        } catch (IOException e) {
            logger.error("io exception:", e);
        } catch (Exception e) {
            logger.error(String.format("获取网易音乐数据时出现异常，url %s", bufferUrl), e);
        }
        return null;
    }

    public static void main(String[] args) {
        ParamVo paramVo = new ParamVo();
        paramVo.setSearchKey("薛之谦");
        new WyCloudMusicSearchCrawler().execute(paramVo);
    }
}
