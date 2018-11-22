package com.d3sq.sns.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Auth;
import javax.core.common.doc.annotation.Domain;
import javax.core.common.doc.annotation.Rule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.sns.service.impl.BBSPraiseSevice;

@Controller
@RequestMapping("/mobile/bbs/praise")
@Domain(value="sns",desc="话题点赞")
public class BBSPraiseAction extends BaseAction {
	
	@Autowired
	private BBSPraiseSevice bbsPraiseSevice;
	
	@Api(name="点赞",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="type",desc="点赞类型."),
			 @Rule(name="objId",desc="点赞对象id."),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/add.json")
	public ModelAndView addPraise(@RequestParam(value = "enc") String enc,
			@RequestParam(value = "type",required = false) Integer type,
			@RequestParam(value = "objId",required = false) Long objId,
			HttpServletRequest request,HttpServletResponse response){
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsPraiseSevice.addPraise(local, userId, enc, type, objId);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="取消点赞",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="type",desc="点赞类型."),
			 @Rule(name="objId",desc="点赞对象id."),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/remove.json")
	public ModelAndView removePraise(@RequestParam(value = "enc") String enc,
			@RequestParam(value = "type",required = false) Integer type,
			@RequestParam(value = "objId",required = false) Long objId,
			HttpServletRequest request,HttpServletResponse response){
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsPraiseSevice.removePraise(local, userId, enc, type, objId);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}

}
