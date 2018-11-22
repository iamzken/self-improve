package com.d3sq.common.interceptors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.core.common.ResultMsg;
import javax.core.common.utils.CookiesUtil;
import javax.core.common.utils.ToolKit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.d3sq.common.config.CustomConfig;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.WebUtil;

/**
 * 检查权限拦截
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private Logger LOG = Logger.getLogger(this.getClass());
//	@Autowired ILangService langService;
	
	private Map<String,String> METHOD_RULES = new HashMap<String,String>();
	private String ALLOW_MODES = ".*";	//保存允许访问的模块
	private String ALLOW_URLS = ".*";	//保存允许访问的url
	private String RULES_FILTER_REGX = ".*";	//保存权限过滤的正则表达式
	private String METHOD_NAMED_REGX = "([A-Za-z0-9]{1}.*)?";	//方法命名后缀的正则
	
	public AuthInterceptor(){
		//保存从配置文件中读取的所有的允许访问的方法
		Set<String> allMethods = new HashSet<String>();
		//保存从配置文件中读取的所有的允许访问的模块
		StringBuffer modes = new StringBuffer();
		//保存从配置文件中读取的所有的允许访问的url
		StringBuffer urls = new StringBuffer();
		for(String key : CustomConfig.getKeys()){
			//忽略非权限相关的配置信息
			if(!key.startsWith("auth.rule")){ continue; }
			
			String value = CustomConfig.getString(key);
			if(key.startsWith("auth.rule.method")){
				String method = key.replace("auth.rule.method.", "");
				String m = value.replaceAll(",", "|");
				allMethods.add(m);
				String regx = "^(" + m + ")" + METHOD_NAMED_REGX + "$";
				METHOD_RULES.put(regx, method);
			}
			if(key.startsWith("auth.rule.allow.modes")){
				modes.append(value.replaceAll(",", "|"));
			}
			if(key.startsWith("auth.rule.allow.urls")){
				urls.append(value.replaceAll(",", "|"));
			}
		}
		String ms = allMethods.toString().replaceAll(",", "|").replaceAll(" ", "").replaceAll("^\\[|\\]$", "");
		RULES_FILTER_REGX = "^(" + ms + ")" + RULES_FILTER_REGX + "$";
		ALLOW_MODES = modes.toString();
		ALLOW_URLS = urls.toString();
	}

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 String local = getLocal(request);
		 return checkAuth(request,response,local);
	}
	
	
	

	/**
	 * 检查权限
	 * @param request
	 * @param response
	 * @param local
	 * @param userinfo
	 * @return
	 */
	private boolean checkAuth(HttpServletRequest request,HttpServletResponse response,String local){
		String uri = request.getServletPath().replaceFirst("/", "");
		LOG.info("current access uri at checkAuth:" + uri);
		int end = uri.lastIndexOf("."); end = (end == -1 ? uri.length() : end);
		uri = uri.substring(0, end);//将请求的后缀去掉
		
		String [] urls = uri.split("/");
		if(urls[0].equals("system")){ return true; } //system开头的直接通过
		if(urls[0].equals("open")){ return true;} //open开头的url需要验证授权密钥
		if(urls[0].equals("admin")){return true;} //admin开头的需要验证IP地址
		
		//目前只拦截web开头的链接
		if(!urls[0].equals("web")){
//			LOG.error("非法地址：url应该以system、open、addmin或者web开头。");
//			String json = JSON.toJSONString(new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "非法地址",null));
//			WebUtil.outJsonpToView(request, response, json);
//			return false;
			return true;
		}

		//如果是超级管理员，不做权限限制
//		int is_root = langService.isRoot(userinfo.getJSONArray("roles"));
//		if(is_root == SystemConstant.IS_ROOT){
//			log.info("超级管理员，不受权限限制。");
//			return true;
//		}
		
		//允许访问的URL过滤
		if(uri.matches("^(" + ALLOW_URLS + ")")){
			LOG.info("不受权限限制的特定URL，直接通过。");
			return true;
		}
		
		//检测方法命名是否合法
		boolean isPerm = false;
		String currMethod = "";
		for (int i = 1; i < urls.length; i ++) {
			if(urls[i].matches(RULES_FILTER_REGX)){ 
				currMethod = urls[i];
				isPerm = true;
				break; 
			}
		}
		String json = JSON.toJSONString(new ResultMsg<Object>(SystemConstant.RESULT_STATUS_NO_PERMI, "您没有权限",null));
		if(!isPerm){
			LOG.error("非法访问：不允许的url语法规则。");
			WebUtil.outJsonpToView(request, response, json);
			return false;
		}
		//过滤掉公共模块的权限限制
		if(urls[1].matches(ALLOW_MODES)){
			LOG.info("公共模块直接通过");
			return true;
		}
		
		
		
		/*
		String userstr = request.getSession().getAttribute(SystemConstant.SYSTEM_LOGIN_FLAG).toString();
		JSONObject userinfo = JSONObject.parseObject(userstr);
		//检查参数
		String aid = request.getParameter("auth_aid"); //获取应用ID
		String mid = request.getParameter("auth_mid"); //获取菜单ID
		if(StringUtils.isEmpty(aid) || StringUtils.isEmpty(mid)){
			log.error("非法访问：没有获取到应用ID和菜单ID");
			WebUtil.outJsonpToView(request, response, json);
			return false;
		}
		
		//开始检查用户操作权限
		JSONObject options = userinfo.getJSONObject("options");
		if(!options.containsKey(aid)){ //是否有该应用的操作权限
			log.error("没有该应用的操作权限，应用ID： " + aid);
			WebUtil.outJsonpToView(request, response, json);
			return false;
		}
		if(!options.getJSONObject(aid).containsKey(mid)){//是否有该菜单的操作权限
			log.error("没有该菜单的操作权限，菜单ID： " + mid);
			WebUtil.outJsonpToView(request, response, json);
			return false;
		}
		
		//检查是否有方法的操作权限
		boolean allow = false;
		JSONObject method = options.getJSONObject(aid).getJSONObject(mid);
		for (String key : METHOD_RULES.keySet()) {
			String m = METHOD_RULES.get(key);
			Integer flag = method.getInteger(m);
			if(flag == null){ flag = 0; }
			if(currMethod.matches(key) &&  flag == 1){
				log.info("方法名:" + currMethod + ",权限:" + m);
				allow = true; break;
			}
		}
		
		//校验方法验证结果
		if(!allow){
			log.error("没有该方法的的操作权限，方法名： " + currMethod);
			WebUtil.outJsonpToView(request, response, json);
			return false;
		}
		*/
		return true;
	}
	
	/**
	 * 获取国际化语言环境
	 * @param request
	 * @return
	 */
	private String getLocal(HttpServletRequest request){
		Object localObj = request.getSession().getAttribute(SystemConstant.LNG_LOCAL);
		if(null != localObj){ return localObj.toString(); }
		
		String cookie = CookiesUtil.loadCookie(SystemConstant.LNG_LOCAL, request);
		if(!StringUtils.isEmpty(cookie)){ return cookie; }
		
		String local = CustomConfig.getString("system.local");
		local = ToolKit.getString(request, "local",local);
		return local;
	}
}
