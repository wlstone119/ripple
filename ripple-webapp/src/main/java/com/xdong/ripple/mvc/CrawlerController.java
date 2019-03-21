package com.xdong.ripple.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;

@Controller
@RequestMapping("/crawler")
public class CrawlerController {

    @Autowired
    private IRpCrawlerSongsService rpCrawlerSongsServiceImpl;

    @RequestMapping("/list")
    @ResponseBody
    public RpCrawlerSongsDo getSongsList(Long id) {
        return rpCrawlerSongsServiceImpl.selectById(id);
    }
}
