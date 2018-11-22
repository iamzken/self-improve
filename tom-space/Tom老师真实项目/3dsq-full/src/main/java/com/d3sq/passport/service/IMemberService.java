package com.d3sq.passport.service;

import javax.core.common.ResultMsg;

import com.alibaba.fastjson.JSONObject;

public interface IMemberService {
	
	/**
	 * 根据登录名获取信息
	 * @param local
	 * @param loginName
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getByLoginName(String local, String loginName, String enc);
	
	/**
	 * 校验短信验证码
	 * @param local
	 * @param ver
	 * @param loginName
	 * @param zone
	 * @param smsCode
	 * @param enc
	 * @return
	 */
	ResultMsg<?> chkSmsCode(String local,String ver,String loginName, String zone, String smsCode, String enc);
	
	/**
	 * 用户注册后自动登录
	 * @param local
	 * @param domain
	 * @param loginName
	 * @param ip
	 * @return
	 */
	public ResultMsg<JSONObject> loginForRegist(String local,String domain, String loginName,String location,String ip);
	

	/**
	 * 注册
	 * @param local
	 * @param mtype
	 * @param loginName
	 * @param loginPass
	 * @param email
	 * @param enc
	 * @return
	 */
	ResultMsg<?> addForRegist(String local,String ver, Integer mtype,String loginName, String loginPass,String email,String smsCode, String enc);

	
	/**
	 * 使用令牌自动登录
	 * @param local
	 * @param domain
	 * @param token
	 * @param ip
	 * @param enc
	 * @return
	 */
	public ResultMsg<JSONObject> loginForToken(String local,String domain, String token,String location,String ip,String enc);
	
	/**
	 * 第三方登录
	 * @param local
	 * @param domain
	 * @param account
	 * @param loginName
	 * @param info
	 * @param ip
	 * @param enc
	 * @return
	 */
	public ResultMsg<JSONObject> loginForThird(String local,String domain,Integer account, String loginName,String info,Integer remeber,String location,String ip,String enc);
	
	/**
	 * 用户登录
	 * @param local
	 * @param account
	 * @param loginName
	 * @param loginPass
	 * @param enc
	 * @return
	 */
	ResultMsg<JSONObject> login(String local, String domain, String loginName,String loginPass, String authCode,String sessionCode,Integer remeber,String location,String ip,String enc);

	
	/**
	 * 退出登录
	 * @param userkey
	 * @return
	 */
	ResultMsg<?> logout(String userkey);
	
	/**
	 * 忘记密码
	 * @param local
	 * @param loginName
	 * @param smsCode
	 * @param enc
	 * @return
	 */
	ResultMsg<?> forgetPass(String local,String loginName,String newPass,String confirmPass,String smsCode, String token,String enc);
	
	
	/**
	 * 提交工商执照
	 * @param local
	 * @param licenseNum
	 * @param licenseImgs
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> addLicense(String local,Long memberId, String licenseNum,String licenseImgs,String enc);
	
	/**
	 * 提交个人身份证
	 * @param local
	 * @param idCardNum
	 * @param idCardImgs
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> addIdCard(String local,Long memberId, String idCardNum,String idCardImgs,String enc);
	
	/**
	 * 提交审核
	 * @param local
	 * @param memberId
	 * @param certFlag
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> addAudit(String local,Long memberId, Integer certFlag,String enc);
	
	
	/**
	 * 取消审核
	 * @param local
	 * @param memberId
	 * @param certFlag
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> cancelAudit(String local,Long memberId,String enc);

}
