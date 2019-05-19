package com.xdong.ripple.crawler.strategy;

import com.xdong.ripple.crawler.common.CrawlerResultVo;
import com.xdong.ripple.crawler.common.ParamVo;

/**
 * 类CrawlerStrategyInterface.java的实现描述：爬虫抽象策略接口，通过url确定用何种策略算法执行爬虫
 * 
 * @author wanglei Mar 24, 2019 9:19:29 PM
 */
public interface CrawlerStrategyInterface {

    // 爬虫的执行方法体
    public CrawlerResultVo execute(ParamVo paramVo);

}
