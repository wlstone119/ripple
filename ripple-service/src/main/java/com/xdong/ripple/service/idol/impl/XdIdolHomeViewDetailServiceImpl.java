package com.xdong.ripple.service.idol.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeDo;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeViewDetailDo;
import com.xdong.ripple.dal.mapper.idol.XdIdolHomeViewDetailDoMapper;
import com.xdong.ripple.spi.idol.IXdIdolHomeService;
import com.xdong.ripple.spi.idol.IXdIdolHomeViewDetailService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * idol首页试图详情h维护表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-09-24
 */
@Service
public class XdIdolHomeViewDetailServiceImpl extends ServiceImpl<XdIdolHomeViewDetailDoMapper, XdIdolHomeViewDetailDo> implements IXdIdolHomeViewDetailService {

    @Autowired
    private IXdIdolHomeService xdIdolHomeServiceImpl;

    @Override
    public List<XdIdolHomeViewDetailDo> getHomeViewList(Long homeId) {
        EntityWrapper<XdIdolHomeViewDetailDo> entityWrapper = new EntityWrapper<XdIdolHomeViewDetailDo>();
        entityWrapper.eq("pre_view_id", homeId);

        return selectList(entityWrapper);
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
}
