package com.xdong.ripple.service.crawler.impl;

import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.dal.mapper.crawler.RpCrawlerSongsDoMapper;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;
import com.weidai.mp.support.service.impl.MPServiceImpl;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<RpCrawlerSongsDo> searchBykey(String queryKey) {
        List<RpCrawlerSongsDo> crawlerList = new ArrayList<RpCrawlerSongsDo>();

        RpCrawlerSongsDo musicDto1 = this.selectById(29054L);
        RpCrawlerSongsDo musicDto2 = this.selectById(213483L);
        RpCrawlerSongsDo musicDto3 = this.selectById(91269L);

        crawlerList.add(musicDto1);
        crawlerList.add(musicDto2);
        crawlerList.add(musicDto3);

        return crawlerList;
    }

}
