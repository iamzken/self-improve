package com.d3sq.shopping.service;

import java.util.List;

import javax.core.common.ResultMsg;

import com.d3sq.model.entity.Shop;
import com.d3sq.shopping.vo.MallOrderVo;

public interface IShoppingService {
	/**
	 * 确认下单
	 * @param local
	 * @param domain
	 * @param userId
	 * @param mallOrderVo
	 * @param enc
	 * @return
	 */
	ResultMsg<?> addOrder(String local, String domain,Long userId, MallOrderVo mallOrderVo,
			String enc);

	/**
	 * 查找所有店铺(内部调用)
	 * @return
	 */
	List<Shop> getShopList();

	/**
	 * 去下单
	 * @param local
	 * @param userId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> toAddOrder(String local, Long userId, String enc);

	/**
	 * 取消订单
	 * @param local
	 * @param userId
	 * @param orderId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> cancelOrder(String local, Long userId, String orderId, String enc);

}
