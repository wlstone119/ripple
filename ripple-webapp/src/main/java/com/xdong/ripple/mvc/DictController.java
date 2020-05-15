package com.xdong.ripple.mvc;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

	private static final Logger logger = LogManager.getLogger(DictController.class);

	private static final String prefix = "dict";

	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView getListByType(HttpServletRequest request, String queryKey, String type, Integer pageNo,
			Integer pageSize) {

		if (pageNo == null)
			pageNo = 1;

		if (pageSize == null)
			pageSize = 20;

		ModelAndView mav = new ModelAndView();

		mav.setViewName(getUrl(prefix, "search"));

		return mav;
	}
}
