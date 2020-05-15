package com.xdong.ripple.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdong.ripple.dal.entity.system.RpSysDictDo;
import com.xdong.ripple.dal.mapper.system.RpSysDictDoMapper;
import com.xdong.ripple.spi.system.IRpSysDictService;

import java.util.List;

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
public class RpSysDictServiceImpl extends ServiceImpl<RpSysDictDoMapper, RpSysDictDo> implements IRpSysDictService {

	@Override
	public List<RpSysDictDo> listByType(String type) {
		QueryWrapper<RpSysDictDo> wrapper = new QueryWrapper<RpSysDictDo>();
		wrapper.eq("type", type);
		return list(wrapper);
	}

}
