package com.d3sq.search.service;

import javax.core.common.ResultMsg;

public interface ICreateIndexService {
	
	/**
	 * 创建首页索引(包括商品、服务、店铺)
	 * @return
	 */
	public ResultMsg<Object> createHomeIndex();
	
	
	/**
	 * 创建商品索引
	 * @return
	 */
	public ResultMsg<Object> createCommodityIndex();
	
	
	/**
	 * 创建服务索引
	 * @return
	 */
	public ResultMsg<Object> createServiceIndex();
	
	
	/**
	 * 创建店铺索引
	 * @return
	 */
	public ResultMsg<Object> createShopIndex();
	
	
	/**
	 * 创建小区索引
	 * @return
	 */
	public ResultMsg<Object> createResidentialIndex();
	
	/**
	 * 创建城市索引
	 * @return
	 */
	public ResultMsg<Object> createCityIndex();
}
