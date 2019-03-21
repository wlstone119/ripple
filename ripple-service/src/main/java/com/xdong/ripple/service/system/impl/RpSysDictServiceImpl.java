package com.xdong.ripple.service.system.impl;

import com.xdong.ripple.dal.entity.system.RpSysDictDo;
import com.xdong.ripple.dal.mapper.system.RpSysDictDoMapper;
import com.xdong.ripple.spi.system.IRpSysDictService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.weidai.mp.support.service.impl.MPServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-21
 */
@Service
public class RpSysDictServiceImpl extends MPServiceImpl<RpSysDictDoMapper, RpSysDictDo> implements IRpSysDictService {

    @Override
    public List<RpSysDictDo> listByType(String type) {
        EntityWrapper<RpSysDictDo> wrapper = new EntityWrapper<RpSysDictDo>();
        wrapper.eq("type", type);
        return selectList(wrapper);
    }

}
