package com.xdong.ripple.service.crawler.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ripple.mplus.support.service.impl.MPServiceImpl;
import com.xdong.ripple.common.crawler.CrawlerSearchSongVo;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerSongsDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 音乐表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@Service
public class RpCrawlerSongsServiceImpl extends MPServiceImpl<RpCrawlerSongsDoMapper, RpCrawlerSongsDo>
		implements IRpCrawlerSongsService {

	// 一行展示4个
	private static final int oneLength = 4;

	// css可设置长度
	private static final int cssLength = 12;

	@Override
	public Page<CrawlerSearchSongVo> searchBykey(String queryKey, String type, int pageNo, int pageSize) {

		Page<CrawlerSearchSongVo> result = new Page<CrawlerSearchSongVo>();

		Page<RpCrawlerSongsDo> page = new Page<RpCrawlerSongsDo>();
		page.setCurrent(pageNo);
		page.setSize(pageSize);

		Wrapper<RpCrawlerSongsDo> wrapper = new EntityWrapper<RpCrawlerSongsDo>();
		wrapper.eq("resource", type);
		wrapper.addFilter(" MATCH(name,song_author,song_album) AGAINST({0})", queryKey);

		Page<RpCrawlerSongsDo> resultPage = selectPage(page, wrapper);

		if (resultPage != null && CollectionUtils.isNotEmpty(resultPage.getRecords())) {
			result.setCurrent(resultPage.getCurrent());
			result.setSize(resultPage.getSize());
			result.setTotal(resultPage.getTotal());
			result.setRecords(modifyToVo(resultPage.getRecords()));
		}

		return result;
	}

	private List<CrawlerSearchSongVo> modifyToVo(List<RpCrawlerSongsDo> records) {

		List<CrawlerSearchSongVo> voList = new ArrayList<CrawlerSearchSongVo>();

		if (CollectionUtils.isNotEmpty(records)) {
			List<RpCrawlerSongsDo> doList = new ArrayList<RpCrawlerSongsDo>();
			for (int i = 0; i < records.size(); i++) {
				doList.add(records.get(i));

				if ((i + 1) % oneLength == 0) {
					CrawlerSearchSongVo songVo = new CrawlerSearchSongVo();
					songVo.setCssLength(cssLength / oneLength);
					songVo.setViewList(copyList(doList));
					voList.add(songVo);

					doList.clear();
				} else if ((i + 1) == records.size()) {
					CrawlerSearchSongVo songVo = new CrawlerSearchSongVo();
					songVo.setCssLength(cssLength / oneLength);
					songVo.setViewList(copyList(doList));
					voList.add(songVo);
				}
			}
		}

		return voList;
	}

	public List<RpCrawlerSongsDo> copyList(List<RpCrawlerSongsDo> doList) {
		List<RpCrawlerSongsDo> copyResult = new ArrayList<RpCrawlerSongsDo>();
		for (RpCrawlerSongsDo song : doList) {
			RpCrawlerSongsDo target = new RpCrawlerSongsDo();
			BeanUtils.copyProperties(song, target);
			copyResult.add(target);
		}
		return copyResult;
	}

	@Override
	public boolean checkSongIdExists(Long songId, String resource) {

		EntityWrapper<RpCrawlerSongsDo> wrapper = new EntityWrapper<RpCrawlerSongsDo>();

		RpCrawlerSongsDo entity = new RpCrawlerSongsDo();

		entity.setSongId(songId);
		entity.setResource(resource);

		wrapper.setEntity(entity);

		return selectList(wrapper).size() > 0;
	}

}
