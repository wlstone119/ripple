package com.xdong.ripple.service.system.impl;

import com.ripple.mplus.support.service.impl.MPServiceImpl;
import com.xdong.ripple.dal.entity.system.RpSysLogDo;
import com.xdong.ripple.dal.mapper.system.RpSysLogDoMapper;
import com.xdong.ripple.spi.system.IRpSysLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-23
 */
@Service
public class RpSysLogServiceImpl extends MPServiceImpl<RpSysLogDoMapper, RpSysLogDo> implements IRpSysLogService {

}
