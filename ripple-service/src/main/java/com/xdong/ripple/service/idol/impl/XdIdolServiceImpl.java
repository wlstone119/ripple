package com.xdong.ripple.service.idol.impl;

import com.xdong.ripple.dal.entity.idol.XdIdolDo;
import com.xdong.ripple.dal.mapper.idol.XdIdolDoMapper;
import com.xdong.ripple.spi.idol.IXdIdolService;
import com.weidai.mp.support.service.impl.MPServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * idol表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@Service
public class XdIdolServiceImpl extends MPServiceImpl<XdIdolDoMapper, XdIdolDo> implements IXdIdolService {

}
