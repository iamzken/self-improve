package com.gupao.web.filter;

import com.gupao.web.controllers.MonitorHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by James on
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class MonitorFilter implements Filter{

    private ServletContext sc;

    public void init(FilterConfig filterConfig) throws ServletException {
        sc = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        MonitorHelper monitorHelper =  wac.getBean(MonitorHelper.class);
        monitorHelper.count(((HttpServletRequest)request).getRequestURI());
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
