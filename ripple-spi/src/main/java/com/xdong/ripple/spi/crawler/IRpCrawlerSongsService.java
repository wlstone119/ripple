package com.xdong.ripple.spi.crawler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdong.ripple.common.crawler.CrawlerSearchSongVo;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;

/**
 * <p>
 * 音乐表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
public interface IRpCrawlerSongsService extends IService<RpCrawlerSongsDo> {

	public Page<CrawlerSearchSongVo> searchBykey(String queryKey, String type, int pageNo, int pageSize);

	public boolean checkSongIdExists(Long songId, String resource);

}
