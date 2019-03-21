package com.xdong.ripple.service.crawler.impl;

import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsSheetDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerSongsSheetDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsSheetService;
import com.weidai.mp.support.service.impl.MPServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌单表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@Service
public class RpCrawlerSongsSheetServiceImpl extends MPServiceImpl<RpCrawlerSongsSheetDoMapper, RpCrawlerSongsSheetDo> implements IRpCrawlerSongsSheetService {

}
