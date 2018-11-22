package com.d3sq.center.service;

import javax.core.common.ResultMsg;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.model.entity.Address;
import com.d3sq.model.entity.Member;

public interface ICenterService {
	
	/**
	 * 检查手机号是否被绑定
	 * @param local
	 * @param phone
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> chkPhoneForBind(String local, String loginName,String phone,String enc);
	
	
	/**
	 * 解绑手机号
	 * @param local
	 * @param loginName
	 * @param smsCode
	 * @param token
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> unbindPhone(String local,String token,String enc);
	
	
	/**
	 * 绑定手机号
	 * @param local
	 * @param loginName
	 * @param longPass
	 * @param confirmPass
	 * @param token
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> bindPhone(String local,String loginName,String phone,String smsCode,String token,String enc);
	
	
	/**
	 * 
	 * @param local
	 * @param member
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> modifyMemberInfo(String local,Member member,String enc);
	
	
	/**
	 * 修改密码
	 * @param local
	 * @param loginName
	 * @param loginPass
	 * @param newLoginPass
	 * @param enc
	 * @return
	 */
	ResultMsg<?> modifyPass(String local, String loginName, String oldPass,String newPass,String confirmPass, String enc);

	/**
	 * 获取常用收获地址
	 * @param local
	 * @param userId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getAddress(String local, Long userId, String enc);

	
	/**
	 * 获取订单列表
	 * @param local
	 * @param userId
	 * @param process
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getOrders(String local, Long userId,Integer process, String enc);

	/**
	 * 获取积分列表
	 * @param local
	 * @param userId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getStores(String local, Long userId, String enc);

	/**
	 * 获取优惠卷
	 * @param local
	 * @param userId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getCoupons(String local, Long userId, String enc);

	
	/**
	 * 获取个人收藏列表
	 * @param local
	 * @param userId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getFavorites(String local, Long userId, String enc);

	/**
	 * 获取个人设置
	 * @param local
	 * @param userId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getSettings(String local, Long userId, String enc);

	/**
	 * 获取登录信息
	 * @param local
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getInfo(String local, JSONObject userinfo,String enc);

	/**
	 * 添加收货地址
	 * @param local
	 * @param address
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> addAress(String local, Address address, String enc);


	/**
	 * 获取订单详情
	 * @param local
	 * @param domain
	 * @param orderId
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> getOrderDetail(String local, String domain,
			String orderId, String enc);

}
