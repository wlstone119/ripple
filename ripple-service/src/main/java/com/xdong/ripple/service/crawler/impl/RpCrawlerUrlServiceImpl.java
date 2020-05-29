package com.xdong.ripple.service.crawler.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerUrlDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
	public RpCrawlerUrlDo getCrawlerUrlRecord(String name, String moduleName) {

		QueryWrapper<RpCrawlerUrlDo> wrapper = new QueryWrapper<RpCrawlerUrlDo>();

		RpCrawlerUrlDo entity = new RpCrawlerUrlDo();
		entity.setModuleName(moduleName);
		entity.setName(name);
		wrapper.setEntity(entity);

		return getOne(wrapper);
	}

	@Override
	public List<RpCrawlerUrlDo> getCrawlerUrlList(List<String> typeList) {

		QueryWrapper<RpCrawlerUrlDo> wrapper = new QueryWrapper<RpCrawlerUrlDo>();
		wrapper.in(CollectionUtils.isNotEmpty(typeList), "type", typeList);

		return baseMapper.selectList(wrapper);
	}

	@Override
	public List<RpCrawlerUrlDo> getCrawlerUrlList(String type, String name) {

		QueryWrapper<RpCrawlerUrlDo> wrapper = new QueryWrapper<RpCrawlerUrlDo>();
		wrapper.eq(StringUtils.isNotBlank(type), "type", type);
		wrapper.eq(StringUtils.isNotBlank(name), "name", name);

		return baseMapper.selectList(wrapper);
	}

}
