package com.xdong.ripple.mvc;

import java.util.ArrayList;
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
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.crawler.strategy.CrawlerStrategyClient;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerSongsDo;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerSongsService;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;
import com.xdong.ripple.spi.system.IRpSysDictService;

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private static final Logger logger = LogManager.getLogger(DictController.class);

    private static final String prefix = "dict";

    @Autowired
    private IRpSysDictService   rpSysDictServiceImpl;

    @RequestMapping("/list")
    @ResponseBody
    public ModelAndView getListByType(HttpServletRequest request, String queryKey, String type, Integer pageNo,
                              Integer pageSize) {
        if (pageNo == null) pageNo = 1;
        if (pageSize == null) pageSize = 20;

        ModelAndView mav = new ModelAndView();
        mav.setViewName(getUrl(prefix, "search"));
        return mav;
    }
}
