package com.xdong.ripple.service.crawler.impl;

import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerSongsDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;
import com.weidai.mp.support.service.impl.MPServiceImpl;
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
public class RpCrawlerSongsServiceImpl extends MPServiceImpl<RpCrawlerSongsDoMapper, RpCrawlerSongsDo> implements IRpCrawlerSongsService {

}
