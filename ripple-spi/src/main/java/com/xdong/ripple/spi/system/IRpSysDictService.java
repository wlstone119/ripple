package com.xdong.ripple.spi.system;

import com.ripple.mplus.support.service.IMPService;
import com.xdong.ripple.dal.entity.system.RpSysDictDo;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2019-03-21
 */
public interface IRpSysDictService extends IMPService<RpSysDictDo> {

	/**
	 * 根据type获取数据
	 * 
	 * @param map
	 * @return
	 */
	List<RpSysDictDo> listByType(String type);

}
