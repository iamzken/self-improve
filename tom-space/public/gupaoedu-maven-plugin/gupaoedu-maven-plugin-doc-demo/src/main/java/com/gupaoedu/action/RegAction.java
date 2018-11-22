package com.gupaoedu.action;

import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Domain;
import javax.core.common.doc.annotation.Rule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reg")
@Domain(desc="用户管理")
public class RegAction {
	
	@Api(author="Tom",
		createtime="2016-12-04",
		name="注册接口",
		desc="用户可以通过填写用户名和密码在系统中注册一个新账号",
		params={
				@Rule(name="username",desc="用户名"),
				@Rule(name="password",desc="密码"),
				@Rule(name="address",desc="住址")
		})
	@RequestMapping(value="/regist.json",method=RequestMethod.POST)
	public ModelAndView regist(@RequestParam(value="username") String userName,
								@RequestParam(value="password") String password,
								@RequestParam(value="address",required=false,defaultValue="不详") String address){
		return null;
	}
	
	
	@Api(author="Tom老师",
			createtime="2016-12-04",
			name="注销",
			desc="通过调用此接口，可以从系统中清空用户的登录信息，然后，注册返回至登录页")
		@RequestMapping("/logout.json")
		public ModelAndView logout(){
			return null;
		}
	
	@Api(author="Tom老师",
			createtime="2016-12-04",
			name="登录")
		@RequestMapping("/login.json")
		public ModelAndView login(){
			return null;
		}
	
}
