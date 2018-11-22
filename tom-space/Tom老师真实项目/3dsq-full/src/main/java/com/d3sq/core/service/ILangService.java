package com.d3sq.core.service;

import javax.core.common.ResultMsg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.model.entity.Member;

public interface ILangService {
	
	/**
	 * 生成userkey
	 * @param json
	 * @return
	 */
	public String genUserkey(JSONObject json);
	
	/**
	 * 获取用户权限信息
	 * @param member
	 * @param location
	 * @param domain
	 * @param ip
	 * @return
	 */
	public ResultMsg<JSONObject> getAuthByMember(Long memberId);

	/**
	 * 获取用户个性化配置
	 * @param local
	 * @param mode
	 * @param uid
	 * @return
	 */
	public ResultMsg<String> getConfig(String local,String mode,JSONObject userinfo);
	
	/**
	 * 判断是否是超级管理员
	 * @param roles
	 * @return
	 */
	public int isRoot(JSONArray roles);
	
	/**
	 * 获取一个订单号
	 * @param isParent 是否为父级订单
	 * @param otype 交易类型1：未知分类，2：购买商品支付，3:会员充值，4：物业费，5：快递邮费，6：充话费,7:预约服务支付
	 * @return
	 */
	public String getOrderNum(boolean isParent,int otype);
	
	/**
	 * 判断订单号是否为父级订单
	 * @param orderNum
	 * @return
	 */
	public boolean isParentOrderNum(String orderNum);
	
}
