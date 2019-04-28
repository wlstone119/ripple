package com.xdong.ripple.spi.crawler;

import com.xdong.ripple.common.crawler.CrawlerSearchSongVo;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.baomidou.mybatisplus.plugins.Page;
import com.weidai.mp.support.service.IMPService;

/**
 * <p>
 * 音乐表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
public interface IRpCrawlerSongsService extends IMPService<RpCrawlerSongsDo> {

	Page<CrawlerSearchSongVo> searchBykey(String queryKey, String type, int pageNo, int pageSize);

}
