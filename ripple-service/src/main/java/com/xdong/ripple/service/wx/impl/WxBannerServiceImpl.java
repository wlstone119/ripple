package com.xdong.ripple.service.wx.impl;

import com.xdong.ripple.dal.entity.wx.WxBannerDo;
import com.xdong.ripple.dal.mapper.wx.WxBannerDoMapper;
import com.xdong.ripple.spi.wx.IWxBannerService;
import com.ripple.mplus.support.service.impl.MPServiceImpl;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信小程序banner表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2021-06-14
 */
@Service
public class WxBannerServiceImpl extends MPServiceImpl<WxBannerDoMapper, WxBannerDo> implements IWxBannerService {

}
