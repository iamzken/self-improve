package com.d3sq.common.interceptors;


import java.util.HashSet;
import java.util.Set;

import javax.core.common.ResultMsg;
import javax.core.common.utils.CookiesUtil;
import javax.core.common.utils.ToolKit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.config.CustomConfig;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.WebUtil;
//import com.d3sq.passport.service.IAuthService;
import com.d3sq.core.dao.CacheDao;

/**
 * 登录拦截
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	Logger LOG = Logger.getLogger(this.getClass());
	
	@Autowired CacheDao cacheDao;
	
	private String ALLOW_MODES = ".*";	//保存允许访问的模块
	private String ALLOW_URLS = ".*";	//保存允许访问的url
	private String RULES_FILTER_REGX = ".*";	//保存权限过滤的正则表达式
	
	
	public LoginInterceptor(){
		//保存从配置文件中读取的所有的允许访问的方法
		Set<String> allMethods = new HashSet<String>();
		//保存从配置文件中读取的所有的允许访问的模块
		StringBuffer modes = new StringBuffer();
		//保存从配置文件中读取的所有的允许访问的url
		StringBuffer urls = new StringBuffer();
		for(String key : CustomConfig.getKeys()){
			//忽略非权限相关的配置信息
			if(!key.startsWith("sso.rule")){ continue; }
			
			String value = CustomConfig.getString(key);
			
			if(key.startsWith("sso.rule.allow.modes")){
				modes.append(value.replaceAll(",", "|"));
			}
			if(key.startsWith("sso.rule.allow.urls")){
				urls.append(value.replaceAll(",", "|"));
			}
		}
		String ms = allMethods.toString().replaceAll(",", "|").replaceAll(" ", "").replaceAll("^\\[|\\]$", "");
		RULES_FILTER_REGX = "^(" + ms + ")" + RULES_FILTER_REGX + "$";
		ALLOW_MODES = modes.toString();
		ALLOW_URLS = urls.toString();
	}
	

	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		 String local = ToolKit.getString(request, "local",null);
		 if(null != local){
			 request.getSession().setAttribute(SystemConstant.LNG_LOCAL,local);
		 }
		return checkLogin(request,response,handler);
	}
	
	
	protected boolean validateUrlFile(String uri) {
		
		String [] urls = uri.replaceFirst("/", "").split("/");
		if(urls[0].equals("system")){ return true; } //system开头的直接通过
		if(urls[0].equals("open")){ return true;} //open开头的url需要验证授权密钥
		
		//过滤掉公共模块的权限限制
		if(urls[1].matches(ALLOW_MODES)){
			return true;
		}
		
		//允许访问的URL过滤
		if(uri.matches(".*(" + ALLOW_URLS + ")$")){
			return true;
		}
		
		return false;
	}

	
	/**
	 * 检查登录状态
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	private boolean checkLogin(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		
		LOG.info("tag : filter all request uri");
		String currentUri = request.getRequestURI();
		
		String userkey = CookiesUtil.loadCookie(SystemConstant.SYSTEM_LOGIN_COOKIE, request);
		if(null != userkey){
			if(cacheDao.exists(userkey)){
				Object userinfo = cacheDao.get(userkey);
				request.getSession().setAttribute(SystemConstant.SYSTEM_LOGIN_FLAG,userinfo);
			}
		}
		
		try{
			LOG.info("current request uri at checkLogin:" + currentUri);
			//如果是登录链接，则不拦截
			if(validateUrlFile(currentUri)){
				return true;
			}
			
			//先判断session是否有值，如果有值则认为是已经登录，往下执行
			//如果session中没有值，则从统一认证服务取值
			
			if(userkey == null){
				LOG.warn("user not login,must forward to passport");
				forwardLogin(currentUri,request, response);
				return false;
			}
			
			
//			if(null != request.getSession().getAttribute(SystemConstant.SYSTEM_LOGIN_FLAG)){
//				return true;
//			}else 
			
			if(null != userkey){
				if(!cacheDao.exists(userkey)){
					LOG.info("缓存和session中取不到用户信息，强制退出。");
					forwardLogin(currentUri,request, response); 
					return false;
				}
			}
			
			LOG.info("user alredy login");
			
		}catch(Exception e){
			e.printStackTrace();
			LOG.info(e.getMessage());
			e.printStackTrace();
			String jsonStr = JSONObject.toJSONString(new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "服务器内部异常,请稍后再试",null));
			WebUtil.outJsonpToView(request, response,jsonStr);
			return false;
		}
		return true;
	}
	
	private void forwardLogin(String requestUri,HttpServletRequest request,HttpServletResponse response){
		try {
			String ext = requestUri.substring(requestUri.lastIndexOf("."));
			if(ext.equals(".html")){
				 response.sendRedirect("/login.html");
				 return;
			}
			
			JSONObject data = new JSONObject();
			String refer = request.getParameter("service");
			if(StringUtils.isEmpty(refer)){
				refer = request.getHeader("Referer");
			}
			if(StringUtils.isEmpty(refer)){
				refer = CustomConfig.getValue("system.myHost");
			}
			data.put("refer", refer);
			
			//动态获取域名信息
			String loginHost = CustomConfig.getValue("sso.loginHost");
			String myHost = CustomConfig.getValue("system.myHost");
			data.put("loginHost", loginHost + "?service=" + myHost);
			
			String jsonStr = JSONObject.toJSONString(new ResultMsg<Object>(SystemConstant.RESULT_STATUS_UNLOGIN,"您尚未登录",data));
			WebUtil.outJsonpToView(request, response,jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
