package com.xdong.ripple.crawler.strategy;

import com.xdong.ripple.common.crawler.SupplementErrorTask;

/**
 * 类CrawlerCompensateInterface.java的实现描述：爬虫抽象策略接口，通过url确定用何种策略算法执行爬虫
 * 
 * @author wanglei Mar 24, 2019 9:19:29 PM
 */
public interface CrawlerCompensateInterface {

    // 爬虫的执行方法体
    public boolean songSheetUrlCompensate(SupplementErrorTask task);

    public boolean songUrlCompensate(SupplementErrorTask task);

}
