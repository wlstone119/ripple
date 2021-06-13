package com.xdong.ripple.service.crawler.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ripple.mplus.support.service.impl.MPServiceImpl;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerUrlDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
public class RpCrawlerUrlServiceImpl extends MPServiceImpl<RpCrawlerUrlDoMapper, RpCrawlerUrlDo>
		implements IRpCrawlerUrlService {

	@Override
	public RpCrawlerUrlDo getCrawlerUrlRecord(String name, String moduleName) {

		EntityWrapper<RpCrawlerUrlDo> wrapper = new EntityWrapper<RpCrawlerUrlDo>();

		RpCrawlerUrlDo entity = new RpCrawlerUrlDo();
		entity.setModuleName(moduleName);
		entity.setName(name);
		wrapper.setEntity(entity);

		return selectOne(wrapper);
	}

	@Override
	public List<RpCrawlerUrlDo> getCrawlerUrlList(List<String> typeList) {

		EntityWrapper<RpCrawlerUrlDo> wrapper = new EntityWrapper<RpCrawlerUrlDo>();
		wrapper.in(CollectionUtils.isNotEmpty(typeList), "type", typeList);

		return selectList(wrapper);
	}

	@Override
	public List<RpCrawlerUrlDo> getCrawlerUrlList(String type, String name) {

		EntityWrapper<RpCrawlerUrlDo> wrapper = new EntityWrapper<RpCrawlerUrlDo>();
		wrapper.eq(StringUtils.isNotBlank(type), "type", type);
		wrapper.eq(StringUtils.isNotBlank(name), "name", name);

		return selectList(wrapper);
	}

}
