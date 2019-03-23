package com.xdong.ripple.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.ripple.annotation.Log;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;

@Controller
@RequestMapping("/crawler")
public class CrawlerController extends BaseController {

    private static final Logger    logger = LogManager.getLogger(CrawlerController.class);

    private static final String    prefix = "crawler";

    @Autowired
    private IRpCrawlerSongsService rpCrawlerSongsServiceImpl;

    @RequestMapping("/list")
    @ResponseBody
    public RpCrawlerSongsDo getSongsList(Long id) {
        return rpCrawlerSongsServiceImpl.selectById(id);
    }

    @Log("音乐首页")
    @RequestMapping(value = "/home")
    @ResponseBody
    public ModelAndView pageInit(int pageNo, int pageSize) {

        Page<RpCrawlerSongsDo> page = new Page<RpCrawlerSongsDo>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<RpCrawlerSongsDo> result = rpCrawlerSongsServiceImpl.selectPage(page);

        Map<String, List<RpCrawlerSongsDo>> modelMap = new HashMap<String, List<RpCrawlerSongsDo>>();
        modelMap.put("songs", result.getRecords());

        ModelAndView mav = new ModelAndView();
        mav.setViewName(getUrl(prefix, "crawler_music"));
        mav.addAllObjects(modelMap);
        return mav;
    }

    @Log("音乐单曲")
    @RequestMapping(value = "/songData")
    @ResponseBody
    public Object songData(@RequestParam("songId") String songId) {
        return rpCrawlerSongsServiceImpl.selectById(Long.parseLong(songId));
    }
}
