package com.gupao.standalone.tomcat.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 2017-05-27.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class ServletConfigMapping {
    private static List<GpServletConfig> configs  = new ArrayList<GpServletConfig>();

    static {
        configs.add(new GpServletConfig("GetServlet","/gp/get","com.gupao.standalone.tomcat.servlet.GetServlet"));
    }

    public static List<GpServletConfig> getConfigs(){
        return configs;
    }
}
