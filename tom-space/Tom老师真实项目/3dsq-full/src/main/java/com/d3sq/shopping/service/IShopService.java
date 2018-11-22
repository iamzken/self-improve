package com.d3sq.shopping.service;

import javax.core.common.ResultMsg;

import com.d3sq.shopping.vo.AddShopVo;


public interface IShopService {
	/**
	 * 添加店铺
	 * @param local
	 * @param domain
	 * @param shopVo
	 * @param enc
	 * @return
	 */
	ResultMsg<?> addShop(String local,String domain, AddShopVo shopVo,String enc);

}
