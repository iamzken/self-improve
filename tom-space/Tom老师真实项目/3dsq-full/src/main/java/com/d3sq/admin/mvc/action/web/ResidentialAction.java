package com.d3sq.admin.mvc.action.web;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Domain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.admin.service.IResidentialService;
import com.d3sq.admin.vo.ResidentialVo;
import com.d3sq.core.mvc.action.BaseAction;

@Controller
@RequestMapping("/mobile/resiential")
@Domain(value="admin",desc="后台管理接口")
public class ResidentialAction extends BaseAction {

	@Autowired IResidentialService residentialService;
	
	
	@RequestMapping(value="/add.json")
	public ModelAndView add(
			@ModelAttribute ResidentialVo residential,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = residentialService.add(local,userId,residential,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
}
