package com.xdong.ripple.service.idol.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeDo;
import com.xdong.ripple.dal.mapper.idol.XdIdolHomeDoMapper;
import com.xdong.ripple.spi.idol.IXdIdolHomeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * idol首页试图展示表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
@Service
public class XdIdolHomeServiceImpl extends ServiceImpl<XdIdolHomeDoMapper, XdIdolHomeDo> implements IXdIdolHomeService {

	@Override
	public XdIdolHomeDo getIdolHome(Long idolId) {
		QueryWrapper<XdIdolHomeDo> entityWrapper = new QueryWrapper<XdIdolHomeDo>();
		entityWrapper.eq("idol_id", idolId);

		return getOne(entityWrapper);
	}

}
