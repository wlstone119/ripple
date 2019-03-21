package com.xdong.ripple.spi.idol;

import com.xdong.ripple.dal.entity.idol.XdIdolHomeDo;
import com.weidai.mp.support.service.IMPService;

/**
 * <p>
 * idol首页试图展示表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
public interface IXdIdolHomeService extends IMPService<XdIdolHomeDo> {

    public XdIdolHomeDo getIdolHome(Long idolId);
    
}
