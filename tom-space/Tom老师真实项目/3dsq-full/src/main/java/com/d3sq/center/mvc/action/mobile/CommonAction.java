package com.d3sq.center.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Auth;
import javax.core.common.doc.annotation.Rule;
import javax.core.common.doc.annotation.Demo;
import javax.core.common.doc.annotation.Domain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.d3sq.center.service.ICommonService;
import com.d3sq.core.mvc.action.BaseAction;


/**
 * 
 */
@Controller
@RequestMapping("/mobile/common")
@Domain(value="www",desc="公共接口")
public class CommonAction extends BaseAction{
	@Autowired private ICommonService commonService;
	
	
	@RequestMapping(value="/getCity.json")
	public ModelAndView getCity(@RequestParam(value="parentId",defaultValue="") Long parentId,@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response){
		String local = super.getLocal(request);
		ResultMsg<?> result = commonService.getCity(local,parentId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	@Api(name="获取系统版本",
	 desc="通过调用此接口获得APP升级信息",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="appKey",desc="客户端版本标识(商家版：IOS_SHOP，客户版：IOS_CUSTOM"),
		@Rule(name="imei",desc="客户端使用的手机串号"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + appKey")
	 },
	 returns={
		@Rule(name="成功",type="JSON",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/getVersion.json")
	public ModelAndView getVersion(
			@RequestParam(value="type") String appKey,
			@RequestParam(value="imei",required=false) String imei,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		ResultMsg<?> result = new ResultMsg<Object>();//sysSettingService.getVersion(type,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
}
