package com.d3sq.shopping.service;

import javax.core.common.ResultMsg;

public interface IKindService {
	/**
	 * 获取商品分类信息
	 * @param local
	 * @param domain
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getForProduct(String local, String domain, String enc);

	/**
	 * 根据父级分类获取子分类
	 * @param local
	 * @param domain
	 * @param parentId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getByParentId(String local, String domain, Long parentId,
			String enc);

	/**
	 * 获取店铺分类信息
	 * @param local
	 * @param domain
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getForShop(String local, String domain, String enc);

	/**
	 * 获取服务分类信息
	 * @param local
	 * @param domain
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getForService(String local, String domain, String enc);
	
}
