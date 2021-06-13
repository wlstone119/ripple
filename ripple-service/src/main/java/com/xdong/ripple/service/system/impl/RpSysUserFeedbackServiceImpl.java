package com.xdong.ripple.service.system.impl;

import com.ripple.mplus.support.service.impl.MPServiceImpl;
import com.xdong.ripple.dal.entity.system.RpSysUserFeedbackDo;
import com.xdong.ripple.dal.mapper.system.RpSysUserFeedbackDoMapper;
import com.xdong.ripple.spi.system.IRpSysUserFeedbackService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户反馈信息表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-05-02
 */
@Service
public class RpSysUserFeedbackServiceImpl extends MPServiceImpl<RpSysUserFeedbackDoMapper, RpSysUserFeedbackDo>
		implements IRpSysUserFeedbackService {

}
