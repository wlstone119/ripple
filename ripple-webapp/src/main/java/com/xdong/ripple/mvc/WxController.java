package com.xdong.ripple.mvc;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xdong.ripple.common.RpcResult;
import com.xdong.ripple.dal.entity.wx.WxBannerDo;
import com.xdong.ripple.spi.wx.IWxBannerService;

@Controller
@RequestMapping("/wx")
public class WxController extends BaseController {

    @Autowired
	private IWxBannerService wxBannerServiceImpl;

    @RequestMapping(value = "/home/multidata", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public RpcResult<List<WxBannerDo>> multidata(Integer bannerType) throws IOException {

    	RpcResult<List<WxBannerDo>>  result = new RpcResult<List<WxBannerDo>>();
    	
    	EntityWrapper<WxBannerDo> wrapper = new EntityWrapper<WxBannerDo>();
    	WxBannerDo bannerDo = new WxBannerDo();
    	bannerDo.setBannerType(bannerType);
    	wrapper.setEntity(bannerDo);
    	wrapper.orderBy("sort", true);
    	
    	List<WxBannerDo> bannerList = wxBannerServiceImpl.selectList(wrapper);
    	result.setObj(bannerList);
    	
		return result;
	}
    
}
