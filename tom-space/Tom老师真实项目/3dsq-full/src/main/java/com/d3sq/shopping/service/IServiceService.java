package com.d3sq.shopping.service;

import javax.core.common.ResultMsg;

import com.d3sq.shopping.vo.AddServiceVo;

public interface IServiceService {
	/**
	 * 创建服务
	 * @param local
	 * @param userId
	 * @param serviceVo
	 * @param enc
	 * @return
	 */
	ResultMsg<?> addService(String local, Long userId, AddServiceVo serviceVo, String enc);

	/**
	 * 获取服务详情
	 * @param local
	 * @param serviceId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getService(String local, Long serviceId, String enc);

}
