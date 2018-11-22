package com.d3sq.core.mvc.action;

import javax.activation.MimetypesFileTypeMap;
import javax.core.common.utils.CookiesUtil;
import javax.core.common.utils.StringUtils;
import javax.core.common.utils.ToolKit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.config.CustomConfig;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.WebUtil;

/**
 * 基础Action类，原则上所有Action都继承此类
 *
 */
public class BaseAction {
	
	/**
	 * 公共跳转方法
	 * 
	 * @param viewName 视图名
	 * @param modelMap 参数列表
	 * @param request 
	 * @return
	 */
	protected ModelAndView jumpToView(String viewName, ModelMap modelMap,HttpServletRequest request) {
		return jumpToView(viewName,modelMap);
	}
	
	/**
	 * 公共跳转方法
	 * @param viewName
	 * @param modelMap
	 * @return
	 */
	protected ModelAndView jumpToView(String viewName, ModelMap modelMap) {
		if(modelMap == null) {
			return new ModelAndView(viewName);
		}
		return new ModelAndView(viewName, modelMap);
	}
	
	/**
	 * 公共跳转方法
	 * @param viewName
	 * @param modelMap
	 * @return
	 */
	protected ModelAndView jumpToView(String viewName) {
		return jumpToView(viewName,null);
	}
	
	
	/**
	 * 设置导出文件的头信息
	 * @param res
	 * @param filename
	 */
	protected void setExportHeader(HttpServletResponse res, String filename){
		try{
			MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
			res.setHeader("Content-Disposition", "attachment;filename=\"" + new String(filename.getBytes("gb2312"), "ISO8859-1") + "\"");
			res.setHeader("Content-Type", mimeTypesMap.getContentType(filename));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 返回 jsonp 处理
	 * @param request
	 * @param jsonStr
	 * @return
	 */
	protected ModelAndView callBackForJsonp(HttpServletRequest request,HttpServletResponse response,String jsonStr){
		WebUtil.outJsonpToView(request, response,jsonStr);
		return null;
	}
	
	
	
	/**
	 * 输出信息（用于解决输出JSON中文为乱码的问题）
	 * 
	 * @param msg（输出对象）
	 * @return
	 */
	protected ModelAndView outToView(HttpServletRequest request,HttpServletResponse response,String msg) {
		WebUtil.outHtmlToView(request, response,msg);
		return null;
	}
	
	
	
	/**
     * 获得客户端真实ip
     */
	protected  String getIpAddr(HttpServletRequest request) {    
       return WebUtil.getIpAddr(request);
	}
	
	/**
	 * 获取Cookie内容
	 * @param request
	 * @param name
	 * @return
	 */
	protected String getCookieValue(HttpServletRequest request, String name) {
		return CookiesUtil.loadCookie(name, request);
	}
	
	
	/**
	 * 获取本地语言环境，优先从session中获取，其次是cookie，然后再从request取
	 * @param request
	 * @return
	 */
	protected String getLocal(HttpServletRequest request){
		
		Object localObj = request.getSession().getAttribute(SystemConstant.LNG_LOCAL);
		if(null != localObj){
			return localObj.toString();
		}
		
		String cookie = CookiesUtil.loadCookie(SystemConstant.LNG_LOCAL, request);
		if(!StringUtils.isEmpty(cookie)){
			return cookie;
		}
		
		String local = CustomConfig.getValue("system.local");
		local = ToolKit.getString(request, "local",local);
		return local;
	}
	
	
	/**
	 * 获取当前登录的用户信息
	 * @param request
	 * @return
	 */
	protected JSONObject getUserInfo(HttpServletRequest request){
		JSONObject result = new JSONObject();
		//正常登录
		Object userinfo = request.getSession().getAttribute(SystemConstant.SYSTEM_LOGIN_FLAG);
		
		if(null == userinfo){ return null;}
		
		result = JSONObject.parseObject(userinfo.toString());
		return result;
	}
	/**
	 * 获取domain
	 * @param request
	 * @return
	 */
	protected String getDomain(HttpServletRequest request){
		return WebUtil.getDomain(request);
	}
	
	/**
	 * 获取登录用户的企业编号
	 * @param request
	 * @return
	 */
	protected long getCompanyId(HttpServletRequest request){
		String cid = request.getParameter("cid");
		if(!(StringUtils.isEmpty(cid)||"null".equals(cid))) {
			return Long.valueOf(cid);
		} else {
			JSONObject userInfo = getUserInfo(request);
			return userInfo.getJSONObject("company").getInteger("id");
		}
	}
}
