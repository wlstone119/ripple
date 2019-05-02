package com.xdong.ripple.mvc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xdong.ripple.common.RpcResult;
import com.xdong.ripple.common.crawler.CrawlerSearchSongVo;
import com.xdong.ripple.common.enums.IErrorEnum;

@Controller
public class BaseController {

    protected static String SUCCESS  = "SUCCESS";
    protected static String ERROR    = "ERROR";

    protected static String REDIRECT = "redirect:";
    protected static String FORWARD  = "forward:";

    public Map<String, Object> convertConditionParam(Map<String, Object> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : paramMap.keySet()) {
            if (!"offset".equals(key) && !"limit".equals(key) && !"page".equals(key) && !"sort".equals(key)
                && !"order".equals(key)) {
                result.put(StringUtils.camelToUnderline(key), paramMap.get(key));
            }
        }
        return result;
    }

    protected String getUrl(String prefix, String url) {
        return prefix + "/" + url;
    }

    public String getResult(Object obj) {
        RpcResult<Object> result = new RpcResult<Object>();
        result.setObj(obj);
        return JSON.toJSONString(obj);
    }

    public String getErrResult(IErrorEnum errorEnum) {
        RpcResult<Object> result = new RpcResult<Object>();
        result.setErroMsg(errorEnum.getMsg());
        result.setCode(errorEnum.getErrorCode());
        return JSON.toJSONString(result);
    }
}
