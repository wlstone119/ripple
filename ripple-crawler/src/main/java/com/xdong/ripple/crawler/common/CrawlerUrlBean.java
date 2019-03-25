package com.xdong.ripple.crawler.common;

import java.util.HashMap;
import java.util.Map;

import com.xdong.ripple.common.utils.SpringUtil;
import com.xdong.ripple.crawler.music.BaiduMusicCrawler;
import com.xdong.ripple.crawler.news.SinaNewsCrawler;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyInterface;

/**
 * @description 爬虫url列表
 * @author stone
 * @date 2017年4月11日
 */
public class CrawlerUrlBean {

    public static Map<String, CrawlerStrategyInterface> map = new HashMap<String, CrawlerStrategyInterface>();

    public void initUrlStrategy() {
        map.put(Constant.CRAWLER_RESOURCE_BAIDU, SpringUtil.getBeansByType(BaiduMusicCrawler.class));
        map.put(Constant.CRAWLER_RESOURCE_SINA, SpringUtil.getBeansByType(SinaNewsCrawler.class));
    }

}
