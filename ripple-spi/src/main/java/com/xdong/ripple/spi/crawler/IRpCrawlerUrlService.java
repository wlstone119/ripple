package com.xdong.ripple.spi.crawler;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;

/**
 * <p>
 * 爬虫url列表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
public interface IRpCrawlerUrlService extends IService<RpCrawlerUrlDo> {

	RpCrawlerUrlDo getCrawlerUrlRecord(String type, String crawlerClss);

}
