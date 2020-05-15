package com.xdong.ripple.service.idol.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeDo;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeViewDetailDo;
import com.xdong.ripple.dal.mapper.idol.XdIdolHomeViewDetailDoMapper;
import com.xdong.ripple.spi.idol.IXdIdolHomeService;
import com.xdong.ripple.spi.idol.IXdIdolHomeViewDetailService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * <p>
 * idol首页试图详情h维护表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-09-24
 */
@Service
public class XdIdolHomeViewDetailServiceImpl extends ServiceImpl<XdIdolHomeViewDetailDoMapper, XdIdolHomeViewDetailDo>
		implements IXdIdolHomeViewDetailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IXdIdolHomeService xdIdolHomeServiceImpl;

	@Override
	public List<XdIdolHomeViewDetailDo> getHomeViewList(Long homeId) {
		QueryWrapper<XdIdolHomeViewDetailDo> entityWrapper = new QueryWrapper<XdIdolHomeViewDetailDo>();
		entityWrapper.eq("pre_view_id", homeId);

		return list(entityWrapper);
	}

	@Override
	public List<XdIdolHomeViewDetailDo> getHomeViewDetailByIdolId(Long idolId) {
		List<XdIdolHomeViewDetailDo> homeViewList = new ArrayList<XdIdolHomeViewDetailDo>();
		XdIdolHomeDo idoHome = xdIdolHomeServiceImpl.getIdolHome(idolId);
		if (idoHome != null) {
			homeViewList = getHomeViewList(idoHome.getHomeId());
		}
		return homeViewList;
	}

	@Transactional
	@Override
	public void saveData(XdIdolHomeViewDetailDo viewDo1, XdIdolHomeViewDetailDo viewDo2, Integer isRuntime)
			throws IOException {

		try {
			updateById(viewDo1);
			updateById(viewDo2);
			if (isRuntime == 1) {
				throw new RuntimeException("异常");
			} else if (isRuntime == 2) {
				throw new IOException("123");
			}
		} catch (Exception e) {
			logger.error("rollbackOnly:{}", TransactionSynchronizationManager.isCurrentTransactionReadOnly(), e);
		}
	}
}
