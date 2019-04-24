package com.xdong.ripple.spi.crawler;

import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.weidai.mp.support.service.IMPService;

/**
 * <p>
 * 爬虫url列表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
public interface IRpCrawlerUrlService extends IMPService<RpCrawlerUrlDo> {

    RpCrawlerUrlDo getCrawlerUrlRecord(String type, String crawlerClss);

}
