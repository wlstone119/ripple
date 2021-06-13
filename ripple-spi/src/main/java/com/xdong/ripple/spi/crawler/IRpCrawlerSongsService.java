package com.xdong.ripple.spi.crawler;

import com.baomidou.mybatisplus.plugins.Page;
import com.ripple.mplus.support.service.IMPService;
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
public interface IRpCrawlerSongsService extends IMPService<RpCrawlerSongsDo> {

	public Page<CrawlerSearchSongVo> searchBykey(String queryKey, String type, int pageNo, int pageSize);

	public boolean checkSongIdExists(Long songId, String resource);

}
