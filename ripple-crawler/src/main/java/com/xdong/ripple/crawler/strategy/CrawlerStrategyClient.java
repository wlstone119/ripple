package com.xdong.ripple.crawler.strategy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.common.BizException;
import com.xdong.ripple.common.utils.SpringUtil;
import com.xdong.ripple.crawler.common.ParamVo;
import com.xdong.ripple.dal.entity.crawler.RpCrawlerUrlDo;
import com.xdong.ripple.spi.crawler.IRpCrawlerUrlService;

/**
 * 类CrawlerStrategyClient.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei Mar 24, 2019 9:09:31 PM
 */
@Service
public class CrawlerStrategyClient {

    @Autowired
    private IRpCrawlerUrlService rpCrawlerUrlServiceImpl;

    /**
     * 策略模式执行者帮你自动选择策略进行执行
     * 
     * @param paramVo
     * @throws BizException
     */
    public Object execute(ParamVo paramVo) throws BizException {
        Long urlKey = paramVo.getUrlKey();
        if (urlKey == null || urlKey <= 0) {
            throw BizException.create(String.format("param error: %s", JSON.toJSONString(paramVo)));
        }

        RpCrawlerUrlDo urlDo = rpCrawlerUrlServiceImpl.selectById(urlKey);
        paramVo.setUrl(urlDo.getCrawlerUrl());
        paramVo.setDomainUrl(urlDo.getDomainName());
        CrawlerStrategyInterface strategy = (CrawlerStrategyInterface) SpringUtil.getBeansByName(urlDo.getCrawlerClass());

        return strategy.execute(paramVo);
    }

    /**
     * 策略模式执行者帮你自动选择策略进行执行
     * 
     * @param paramVo
     * @throws BizException
     */
    public Object search(ParamVo paramVo) throws BizException {

        if (StringUtils.isBlank(paramVo.getSearchKey())) {
            throw BizException.create(String.format("param error: %s", JSON.toJSONString(paramVo)));
        }

        RpCrawlerUrlDo urlDo = rpCrawlerUrlServiceImpl.getCrawlerUrlRecord("search", paramVo.getModelName());
        paramVo.setUrl(String.format(urlDo.getCrawlerUrl(), paramVo.getSearchKey()));
        paramVo.setDomainUrl(urlDo.getDomainName());
        CrawlerSearchInterface strategy = (CrawlerSearchInterface) SpringUtil.getBeansByName(urlDo.getCrawlerClass());

        return strategy.search(paramVo);
    }
}
