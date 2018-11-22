package com.d3sq.shopping.service;

import javax.core.common.ResultMsg;

import com.d3sq.model.entity.MallCart;

public interface ICartService {
	/**
	 * 获取购物车中的商品列表
	 * @param local
	 * @param userId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getList(String local, Long userId, String enc);

	/**
	 * 添加购物车
	 * @param local
	 * @param cart
	 * @param enc
	 * @return
	 */
	ResultMsg<?> addCart(String local, Long productId,Integer addCount,Float addPrice,Long userId, String enc);


	/**
	 * 编辑购物车商品是否选中状态
	 * @param local
	 * @param id
	 * @param type
	 * @param check
	 * @param userId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> modifyCheck(String local, Long id, Integer type,
			Integer check, Long userId, String enc);

	/**
	 * 同步购物车
	 * @param local
	 * @param userId
	 * @param shops
	 * @param enc
	 * @return
	 */
	ResultMsg<?> mergeCart(String local, Long userId, String shops, String enc);
	
	
	/**
	 * 删除购物车
	 * @param local
	 * @param userId
	 * @param productId
	 * @param enc
	 * @return
	 */
	//ResultMsg<?> removeCart(String local,Long userId, Long productId, String enc);

}
