package com.xdong.ripple.service.crawler.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerUrlDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 爬虫url列表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@Service
public class RpCrawlerUrlServiceImpl extends ServiceImpl<RpCrawlerUrlDoMapper, RpCrawlerUrlDo>
		implements IRpCrawlerUrlService {

	@Override
	public RpCrawlerUrlDo getCrawlerUrlRecord(String type, String crawlerClss) {

		QueryWrapper<RpCrawlerUrlDo> wrapper = new QueryWrapper<RpCrawlerUrlDo>();
		RpCrawlerUrlDo entity = new RpCrawlerUrlDo();
		entity.setType(type);
		entity.setCrawlerClass(crawlerClss);
		wrapper.setEntity(entity);

		return getOne(wrapper);
	}

}
