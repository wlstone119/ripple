package com.xdong.ripple.service.idol.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ripple.mplus.support.service.impl.MPServiceImpl;
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
public class XdIdolHomeServiceImpl extends MPServiceImpl<XdIdolHomeDoMapper, XdIdolHomeDo> implements IXdIdolHomeService {

	@Override
	public XdIdolHomeDo getIdolHome(Long idolId) {
		Wrapper<XdIdolHomeDo> entityWrapper = new EntityWrapper<XdIdolHomeDo>();
		entityWrapper.eq("idol_id", idolId);

		return selectOne(entityWrapper);
	}

}
