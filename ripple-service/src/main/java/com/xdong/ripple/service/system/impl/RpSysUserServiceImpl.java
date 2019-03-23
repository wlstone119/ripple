package com.xdong.ripple.service.system.impl;

import com.xdong.ripple.dal.entity.system.RpSysUserDo;
import com.xdong.ripple.dal.mapper.system.RpSysUserDoMapper;
import com.xdong.ripple.spi.system.IRpSysUserService;
import com.weidai.mp.support.service.impl.MPServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-23
 */
@Service
public class RpSysUserServiceImpl extends MPServiceImpl<RpSysUserDoMapper, RpSysUserDo> implements IRpSysUserService {

}
