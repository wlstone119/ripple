package com.xdong.ripple.spi.crawler;

import java.util.List;

import com.ripple.mplus.support.service.IMPService;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;

/**
 * <p>
 * 爬虫url列表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
public interface IRpCrawlerUrlService extends IMPService<RpCrawlerUrlDo> {

	List<RpCrawlerUrlDo> getCrawlerUrlList(String type, String name);

	List<RpCrawlerUrlDo> getCrawlerUrlList(List<String> typeList);

	RpCrawlerUrlDo getCrawlerUrlRecord(String name, String moduleName);

}
