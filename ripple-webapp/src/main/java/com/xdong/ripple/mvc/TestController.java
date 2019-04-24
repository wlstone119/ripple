package com.xdong.ripple.mvc;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeViewDetailDo;
import com.xdong.ripple.spi.idol.IXdIdolHomeViewDetailService;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    private final Logger                 logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IXdIdolHomeViewDetailService xdIdolHomeViewDetailServiceImpl;

    @Value("${server.port}")
    private static String                port;

    @RequestMapping(value = "/value", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object getResult() {
        return port;
    }

    @RequestMapping(value = "/testWrapper", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object testWrapper() {

        Wrapper<XdIdolHomeViewDetailDo> wrapper = new EntityWrapper<XdIdolHomeViewDetailDo>();
        wrapper.and("site_type >= {0} or content<= {1}", "siteType", "content");
        List<XdIdolHomeViewDetailDo> list = xdIdolHomeViewDetailServiceImpl.selectList(wrapper);
        return port;
    }

    @RequestMapping(value = "/testRollback", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object testRollback(Integer isRuntime) throws IOException {

        XdIdolHomeViewDetailDo viewDo1 = xdIdolHomeViewDetailServiceImpl.selectById(1);
        XdIdolHomeViewDetailDo viewDo2 = xdIdolHomeViewDetailServiceImpl.selectById(2);
        viewDo1.setContent("wanglei");
        viewDo2.setContent("wanglei");
        logger.error("rollbackOnly:{}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());

        try {
            xdIdolHomeViewDetailServiceImpl.saveData(viewDo1, viewDo2, isRuntime);
        } catch (Exception e) {
            logger.error("rollbackOnly:{}", TransactionSynchronizationManager.isCurrentTransactionReadOnly(), e);
        }

        return null;
    }
}
