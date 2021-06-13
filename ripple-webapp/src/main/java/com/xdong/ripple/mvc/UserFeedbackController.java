package com.xdong.ripple.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdong.ripple.common.enums.IErrorEnum;
import com.xdong.ripple.dal.entity.system.RpSysUserFeedbackDo;
import com.xdong.ripple.spi.system.IRpSysUserFeedbackService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户反馈信息表 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2019-05-02
 */
@Controller
@RequestMapping("/userBack")
public class UserFeedbackController extends BaseController {

	private static final Logger logger = LogManager.getLogger(UserFeedbackController.class);

	@Autowired
	private IRpSysUserFeedbackService rpSysUserFeedbackServiceImpl;

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Object saveUserFeed(RpSysUserFeedbackDo feeback) {

		if (StringUtils.isBlank(feeback.getMobile()) && StringUtils.isBlank(feeback.getEmailAddress())) {
			logger.warn("用户反馈信息时参数错误:" + JSON.toJSONString(feeback));
			return getErrResult(IErrorEnum.USER_FEED_FORMAT_ERROR);
		}

		rpSysUserFeedbackServiceImpl.insert(feeback);

		return getResult(true);
	}

}
