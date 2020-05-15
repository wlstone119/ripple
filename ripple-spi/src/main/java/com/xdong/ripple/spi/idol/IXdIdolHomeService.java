package com.xdong.ripple.spi.idol;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeDo;

/**
 * <p>
 * idol首页试图展示表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
public interface IXdIdolHomeService extends IService<XdIdolHomeDo> {

	public XdIdolHomeDo getIdolHome(Long idolId);

}
