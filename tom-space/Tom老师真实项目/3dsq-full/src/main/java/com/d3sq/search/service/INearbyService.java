package com.d3sq.search.service;

import javax.core.common.ResultMsg;

public interface INearbyService {
	
	/**
	 * 获取附近的商家
	 * @param local
	 * @param location
	 * @param wd
	 * @param enc
	 * @return
	 */
	//ResultMsg<?> getShop(String local,Double lon,Double lat,String name,Long lastCreateTime,int pageSize,String enc);

	
	
	/**
	 * 获取附近的商品
	 * @param local
	 * @param domain
	 * @param lon
	 * @param lat
	 * @param name
	 * @param shopId
	 * @param lastCreateTime
	 * @param pageSize
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getProduct(String local, String domain, String lon,
			String lat,Integer type,String name,Long shopId,Long lastCreateTime,Integer pageSize, String enc);
	
	/**
	 * 获取附近的小区
	 * @param local
	 * @param location
	 * @param wd
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getResidential(String local,String lon,String lat,Long lastCreateTime,String wd,Integer pageSize,String enc);
	
	/**
	 * 获取附近的人
	 * @param local
	 * @param location
	 * @param wd
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getMember(String local,String location,String lastId,String wd,String enc);
	
	/**
	 * 获取周边城市
	 * @param local
	 * @param location
	 * @param wd
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getCity(String local,String lonstr,String latstr,String enc);
	
}
