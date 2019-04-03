package com.xdong.ripple.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.ripple.commonservice.annotation.Log;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyClient;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;

@Controller
@RequestMapping("/crawler")
public class CrawlerController extends BaseController {

    private static final Logger    logger = LogManager.getLogger(CrawlerController.class);

    private static final String    prefix = "crawler";

    @Autowired
    private IRpCrawlerSongsService rpCrawlerSongsServiceImpl;

    @Autowired
    private CrawlerStrategyClient  crawlerStrategyClient;

    @RequestMapping("/list")
    @ResponseBody
    public RpCrawlerSongsDo getSongsList(Long id) {
        return rpCrawlerSongsServiceImpl.selectById(id);
    }

    @Log("音乐首页")
    @RequestMapping(value = "/home")
    @ResponseBody
    public ModelAndView pageInit(int pageNo, int pageSize, String name, String flag) {

        Page<RpCrawlerSongsDo> page = new Page<RpCrawlerSongsDo>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);

        Wrapper<RpCrawlerSongsDo> wrapper = new EntityWrapper<RpCrawlerSongsDo>();
        wrapper.eq("name", name).orderBy("name", true);

        Page<RpCrawlerSongsDo> result = rpCrawlerSongsServiceImpl.selectPage(page, wrapper);

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

    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView execute() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(getUrl(prefix, "crawler"));
        return mav;
    }

    @RequestMapping(value = "/crawl", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object crawl(ParamVo paramVo) {
        try {
            return crawlerStrategyClient.execute(paramVo);
        } catch (Exception e) {
            logger.error("爬虫任务调度时出现异常", e);
            return false;
        }
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public boolean index() {
        try {
            // indexer.index(true);
            return true;
        } catch (Exception e) {
            logger.error("新闻索引异常", e);
            return false;
        }
    }
}
