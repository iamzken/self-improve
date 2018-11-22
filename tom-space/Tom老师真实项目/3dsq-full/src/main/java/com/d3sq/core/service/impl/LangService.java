package com.d3sq.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.core.common.ResultMsg;
import javax.core.common.encrypt.MD5;
import javax.core.common.utils.StringUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.config.CustomConfig;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.dao.CacheDao;
import com.d3sq.core.dao.RequiredDao;
import com.d3sq.core.service.ILangService;
import com.d3sq.core.service.ISequenceService;
import com.d3sq.model.entity.Member;
import com.d3sq.model.entity.Shop;



@Service
public class LangService implements ILangService{
	
	
	private Logger log = Logger.getLogger(LangService.class);
	@Autowired RequiredDao requiredDao;
	@Autowired CacheDao cacheDao;
	@Autowired ISequenceService sequenceService;
	private SimpleDateFormat ORDER_PATTERN = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public String genUserkey(JSONObject json) {
		return MD5.calcMD5(CustomConfig.getString("sso.cookie.domain") + "_" + json.getJSONObject("user").getLong("id"));
	}
	
	/**
	 * 获取用户权限信息
	 */
	public ResultMsg<JSONObject> getAuthByMember(Long memberId){
		
		Member member = requiredDao.selectMemberById(memberId);
		
		JSONObject result =  new JSONObject();
		result.put("user",JSON.toJSON(member));//用户信息
		
		try{
			String third = result.getJSONObject("user").getString("thirdInfo");
			result.getJSONObject("user").put("thirdInfo", JSON.parseObject(third));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Shop shop = requiredDao.selectMyShop(member.getId());
		result.put("shop", shop);
		
		result.put("relation",new JSONObject());//关联账号
		
		JSONObject site = new JSONObject();	//站点信息
		
		site.put("config", new JSONObject());
		result.put("site", site);
		
		result.put("roles", new JSONArray());//角色信息
		
		result.put("options", new JSONObject());//权限信息
		
		return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "", result);
	}
	
	
	/**
	 * 获取用户个性化配置
	 * @param local
	 * @param mode
	 * @param uid
	 * @return
	 */
	public ResultMsg<String> getConfig(String local,String mode,JSONObject userinfo){
		StringBuffer result = new StringBuffer();
		
		//加载本地语言环境
			result.append("LNG=").append("{};");
		Integer is_root = 0;
		//获取用户权限
		try{
			if(userinfo == null){ throw new Exception(); }
			
			JSONObject settings = new JSONObject();
			
			userinfo.put("settings", settings);
			is_root = isRoot(userinfo.getJSONArray("roles"));
			//拼接输出格式
			result.append("AUTH=").append(userinfo.toJSONString()).append(";");
		}catch(Exception e){
			log.debug(e.getStackTrace());
			result.append("AUTH={};");
		}
		
		//获取全局配置
		try{
			JSONObject g = new JSONObject();
			g.put("is_root", is_root);
			g.put("systime", System.currentTimeMillis());
			result.append("G=" + g.toJSONString());
		}catch(Exception e){
			log.debug(e.getStackTrace());
			result.append("G={};");
		}
		
		return new ResultMsg<String>(SystemConstant.RESULT_STATUS_SUCCESS,"",result.toString());
	}
	
	/**
	 * 判断是否是超级管理员
	 * @param roles
	 * @return
	 */
	public int isRoot(JSONArray roles){
		for(int i = 0; i < roles.size(); i ++){
			Object is_root = roles.getJSONObject(i).get("is_root");
			if(null != is_root && is_root.toString().equals("" + SystemConstant.IS_ROOT)){
				return SystemConstant.IS_ROOT;
			}
		}
		return SystemConstant.IS_NORMAL;
	}
	
	/**
	 * 获取一个订单号
	 * @param isParent
	 * @param otype
	 * @return
	 */
	public String getOrderNum(boolean isParent,int otype){
		StringBuffer orderNum = new StringBuffer();
		orderNum.append(isParent ? FieldConstant.PAY_NUM_PREFIX_P : FieldConstant.PAY_NUM_PREFIX_C);
		orderNum.append(otype);
		orderNum.append(ORDER_PATTERN.format(new Date()));
		String seq = sequenceService.nextVal("order_number", true);
		orderNum.append(seq);
		return orderNum.toString();
	}
	
	/**
	 * 
	 */
	public boolean isParentOrderNum(String orderNum){
		if(StringUtils.isEmpty(orderNum)){
			return false;
		}
		return orderNum.startsWith(FieldConstant.PAY_NUM_PREFIX_P);
	}
	
}
