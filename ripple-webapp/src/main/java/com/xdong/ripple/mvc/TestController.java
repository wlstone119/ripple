package com.xdong.ripple.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    private final Logger  logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.port}")
    private static String port;

    @RequestMapping(value = "/value", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object getResult() {
        return port;
    }

}
