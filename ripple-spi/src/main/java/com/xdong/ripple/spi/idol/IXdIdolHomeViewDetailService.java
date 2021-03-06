package com.xdong.ripple.spi.idol;

import com.ripple.mplus.support.service.IMPService;
import com.xdong.ripple.dal.entity.idol.XdIdolHomeViewDetailDo;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * idol首页试图详情h维护表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-20
 */
public interface IXdIdolHomeViewDetailService extends IMPService<XdIdolHomeViewDetailDo> {

	public List<XdIdolHomeViewDetailDo> getHomeViewList(Long homeId);

	public List<XdIdolHomeViewDetailDo> getHomeViewDetailByIdolId(Long idolId);

	public void saveData(XdIdolHomeViewDetailDo viewDo1, XdIdolHomeViewDetailDo viewDo2, Integer isRuntime)
			throws IOException;

}
