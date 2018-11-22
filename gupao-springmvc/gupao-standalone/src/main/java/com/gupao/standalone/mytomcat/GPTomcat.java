package com.gupao.standalone.mytomcat;

import com.gupao.standalone.mytomcat.config.GpServletConfig;
import com.gupao.standalone.mytomcat.config.ServletConfigMapping;
import com.gupao.standalone.mytomcat.servlet.JamesServlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 2017-05-27.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 * <p>
 * tomcat窗口启动类
 */
public class GPTomcat {
    private int port = 8080;

    public GPTomcat() {
    }

    public GPTomcat(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        //init
        initServlets();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Tomcat started on " + this.port);
            while (true) {
                Socket socket = serverSocket.accept();
                GPRequest request = new GPRequest(socket.getInputStream());
                GPResponse response = new GPResponse(socket.getOutputStream());
                dispatch(request, response);
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private Map<String, Class<GPServlet>> servletMap = new HashMap<String, Class<GPServlet>>();

    private void initServlets() throws ClassNotFoundException {
        for(GpServletConfig servletConfig : ServletConfigMapping.getConfigs()){
            servletMap.put(servletConfig.getUrlMapping(), (Class<GPServlet>) Class.forName(servletConfig.getClazz()));
        }
    }

    private void dispatch(GPRequest request, GPResponse response) {
        Class<?> clazz = servletMap.get(request.getUrl());
        try {
            if(null != clazz){
                GPServlet servlet = (GPServlet)clazz.newInstance();
                servlet.service(request,response);
            }else {
                //404
                response.write("404 Not Found");
            }
        } catch (Exception e) {
            response.write("500 internal error \r\n "+ Arrays.toString(e.getStackTrace()));
        }
    }

    public static void main(String[] args) throws Exception {
        new GPTomcat().start();
    }
}
