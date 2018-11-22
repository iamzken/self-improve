package com.d3sq.search.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Domain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.search.service.IResidentialService;

@Controller("sResidentialAction")
@RequestMapping("/mobile/common/residential")
@Domain(value="s",desc="小区搜索")
public class ResidentialAction extends BaseAction {
	
	@Autowired private IResidentialService sResidentialService;
	
	@RequestMapping(value="/get.json")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(value="id") Long id
			,@RequestParam(value="enc",defaultValue="") String enc){
		ResultMsg<?> result = sResidentialService.getById(id);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
}
