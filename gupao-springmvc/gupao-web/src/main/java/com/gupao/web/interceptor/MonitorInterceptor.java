package com.gupao.web.interceptor;

import com.gupao.web.controllers.MonitorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class MonitorInterceptor implements HandlerInterceptor, Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private MonitorHelper monitorHelper;

    public int getOrder() {
        return 0;
    }

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        monitorHelper.count(httpServletRequest.getRequestURI());
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public MonitorHelper getMonitorHelper() {
        return monitorHelper;
    }

    public void setMonitorHelper(MonitorHelper monitorHelper) {
        this.monitorHelper = monitorHelper;
    }
}