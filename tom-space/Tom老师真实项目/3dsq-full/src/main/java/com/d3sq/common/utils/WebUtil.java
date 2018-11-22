package com.d3sq.common.utils;


import java.io.PrintWriter;

import javax.core.common.utils.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.config.CustomConfig;

/**
 * Web页面相关的操作
 */
public class WebUtil {
	/**
	 * Android 手机
	 */
	public static final String OSTYPE_ANDROID="Android";
	/**
	 * IPhone 手机
	 */
	public static final String OSTYPE_IOS="IOS";
	
	/**
	 * Windows 手机
	 */
	public static final String OSTYPE_WP="WINDOWS PHONE";
	/**
	 * 黑莓手机
	 */
	public static final String OSTYPE_BLACKBERRY="BLACKBERRY";
	/***
	 * pad
	 */
	public static final String DEVICE_TYPE_PAD="Pad";
	/***
	 * 手机
	 */
	public static final String DEVICE_TYPE_PHONE="Phone";
	
	/**
	 * 输出字符到页面
	 * @param response
	 * @param contentType
	 * @param content
	 */
	private static void out(HttpServletResponse response,String contentType,String content){
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType + "; charset=UTF-8");
		try{
			PrintWriter writer = response.getWriter();
			writer = response.getWriter();
			writer.print(content);
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出json到页面
	 * @param request
	 * @param response
	 * @param json
	 */
	public static void outJsonpToView(HttpServletRequest request,HttpServletResponse response,String json){
			if(null == json){ json = "{\"data\":{}}";}
			String callbackName = request.getParameter("callback");
			String iframe = request.getParameter("iframe");
			String script = request.getParameter("script");
			
			StringBuffer retrunStr = new StringBuffer();
			
			if("1".equals(iframe) || "1".equals(script)){
				
				String domain = request.getParameter("domain");
				if(!(null == domain || "".equals(domain))){
					retrunStr.append("window.document.domain=\"" + domain + "\";");
				}
				
				if("1".equals(iframe)){
					retrunStr.append("window.parent." + ((callbackName == null) ? "callback" : callbackName) + "(" + json + ");");
					retrunStr.insert(0, "<script type=\"text/javascript\">").append("</script>");
					out(response,"text/html",retrunStr.toString());
				}else if("1".equals(script)){
					JSONObject obj = null;
					try{
						obj = JSONObject.parseObject(json);
					}catch(Exception e){
						out(response,"text/javascript",json);
						return;
					}
					JSONObject data = obj.getJSONObject("data");
					String loginHost = (data == null ? "" : data.getString("loginHost"));
					if(StringUtils.isEmpty(loginHost)){
						loginHost += "?service=" + CustomConfig.getValue("sso.service");
						out(response,"text/javascript",json);
					}else{
						retrunStr.append("var callback = " + json + ";");
						retrunStr.append("window.parent.location = callback.data.loginHost;");
						out(response,"text/javascript",retrunStr.toString());
					}
				}
			}else{
				out(response,"application/json",((callbackName == null) ? json : (callbackName + "(" + json + ")")));
			}
	}
	
	
	/**
	 * 输出字符串到页面
	 * @param request
	 * @param response
	 * @param str
	 */
	public static void outStringToView(HttpServletRequest request,HttpServletResponse response,String str){
		out(response,"text/text",str);
	}
	
	/**
	 * 输出html到页面
	 * @param request
	 * @param response
	 * @param html
	 */
	public static void outHtmlToView(HttpServletRequest request,HttpServletResponse response,String html){
		out(response,"text/html",html);
	}
	
	/**
	 * 获取domain
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getDomain(HttpServletRequest request){
		String host = request.getServerName().toLowerCase();
		int start = host.indexOf(".");
		if(start != -1){
			host = host.substring(start + 1);
			return host;
		}else{
			return host;
		}
		/*
		String host = request.getServerName().toLowerCase();
		Pattern pattern = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)$");
		Matcher matcher = pattern.matcher(host);
		while (matcher.find()) {
			return matcher.group();
		}
		return "";
		*/
	}
	
	/**
	 * 获取真实的IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");    
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("Proxy-Client-IP");    
        }    
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("WL-Proxy-Client-IP");    
        }    
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getRemoteAddr();
        }
        return ip;
	}
	
	
	
}
