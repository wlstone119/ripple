package com.xdong.ripple.service.crawler.impl;

import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerUrlDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.weidai.mp.support.service.impl.MPServiceImpl;
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
public class RpCrawlerUrlServiceImpl extends MPServiceImpl<RpCrawlerUrlDoMapper, RpCrawlerUrlDo> implements IRpCrawlerUrlService {

    @Override
    public RpCrawlerUrlDo getCrawlerUrlRecord(String type, String crawlerClss) {

        EntityWrapper<RpCrawlerUrlDo> wrapper = new EntityWrapper<RpCrawlerUrlDo>();
        RpCrawlerUrlDo entity = new RpCrawlerUrlDo();
        entity.setType(type);
        entity.setCrawlerClass(crawlerClss);
        wrapper.setEntity(entity);

        return this.selectOne(wrapper);
    }

}
