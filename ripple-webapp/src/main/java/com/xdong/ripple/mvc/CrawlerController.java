package com.xdong.ripple.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.ripple.common.crawler.CrawlerSearchSongVo;
import com.xdong.ripple.commonservice.annotation.Log;
import com.xdong.ripple.crawler.common.CrawlerResultVo;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyClient;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerTaskDo;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;
import com.xdong.ripple.spi.crawler.IRpCrawlerTaskService;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;

@Controller
@RequestMapping("/crawler")
public class CrawlerController extends BaseController {

    private static final Logger    logger = LogManager.getLogger(CrawlerController.class);

    private static final String    prefix = "crawler";

    @Autowired
    private IRpCrawlerSongsService rpCrawlerSongsServiceImpl;

    @Autowired
    private IRpCrawlerUrlService   rpCrawlerUrlServiceImpl;

    @Autowired
    private IRpCrawlerTaskService  rpCrawlerTaskServiceImpl;

    @Autowired
    private CrawlerStrategyClient  crawlerStrategyClient;

    @Log("音乐搜索")
    @RequestMapping("/index")
    @ResponseBody
    public ModelAndView index(HttpServletRequest request, String queryKey, String type, Integer pageNo,
                              Integer pageSize) {
        if (pageNo == null) pageNo = 1;
        if (pageSize == null) pageSize = 20;

        ModelAndView mav = new ModelAndView();
        Page<CrawlerSearchSongVo> crawlerList = rpCrawlerSongsServiceImpl.searchBykey(queryKey, type, pageNo, pageSize);
        if (StringUtils.isNotBlank(queryKey)) {
            mav.addObject("pageResult", crawlerList);
        }
        mav.addObject("queryKey", queryKey);
        mav.addObject("type", type);

        mav.setViewName(getUrl(prefix, "search"));
        return mav;
    }

    @RequestMapping("/crawler")
    @ResponseBody
    public ModelAndView crawler() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(getUrl(prefix, "crawler"));
        return mav;
    }

    @RequestMapping("/search")
    @ResponseBody
    public Object search(HttpServletRequest request, String queryKey, String type, Integer pageNo, Integer pageSize) {

        if (pageNo == null) pageNo = 1;
        if (pageSize == null) pageSize = 20;

        Page<CrawlerSearchSongVo> pageResult = rpCrawlerSongsServiceImpl.searchBykey(queryKey, type, pageNo, pageSize);

        return getResult(pageResult);
    }

    @RequestMapping("/tableList")
    @ResponseBody
    public List<RpCrawlerUrlDo> getTableList(HttpServletRequest request) {
        List<RpCrawlerUrlDo> urlList = new ArrayList<RpCrawlerUrlDo>();
        Cookie[] cookies = request.getCookies();
        String password = request.getParameter("password");
        for (Cookie var : cookies) {
            if (("temp_h".equals(var.getName()) && "iamaadmin".equals(var.getValue()))
                || "passwordiamaadmin".equals(password)) {
                urlList = rpCrawlerUrlServiceImpl.selectList(new EntityWrapper<RpCrawlerUrlDo>());
            }
        }
        return urlList;
    }

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
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("name", name).orderBy("name", true);
        }

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

    @RequestMapping(value = "/indexold", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView execute() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(getUrl(prefix, "crawler"));
        return mav;
    }

    @RequestMapping(value = "/crawl", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object crawl(ParamVo paramVo) {
        try {
            CrawlerResultVo resultVo = crawlerStrategyClient.execute(paramVo);
            return resultVo.getResultList();
        } catch (Exception e) {
            logger.error("爬虫任务调度时出现异常", e);
            return false;
        }
    }

    @RequestMapping(value = "/indexer", method = RequestMethod.POST)
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

    @RequestMapping(value = "/execute", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object execute(HttpServletRequest request, ParamVo paramVo) {
        Cookie[] cookies = request.getCookies();
        String password = request.getParameter("password");
        for (Cookie var : cookies) {
            if (("temp_h".equals(var.getName()) && "iamaadmin".equals(var.getValue()))
                || "passwordiamaadmin".equals(password)) {
                CrawlerResultVo resultVo = crawlerStrategyClient.execute(paramVo);
                return resultVo.getResultList();
            }
        }

        return "success";
    }
}
