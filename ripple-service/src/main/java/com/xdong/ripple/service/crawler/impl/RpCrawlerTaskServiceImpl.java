package com.xdong.ripple.service.crawler.impl;

import com.xdong.ripple.dal.entity.crawler.RpCrawlerTaskDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerTaskDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerTaskService;
import com.weidai.mp.support.service.impl.MPServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 爬虫任务表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-05-19
 */
@Service
public class RpCrawlerTaskServiceImpl extends MPServiceImpl<RpCrawlerTaskDoMapper, RpCrawlerTaskDo> implements IRpCrawlerTaskService {

}
