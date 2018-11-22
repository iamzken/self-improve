package com.gupao.standalone.tomcat;

import com.gupao.standalone.tomcat.config.GpServletConfig;
import com.gupao.standalone.tomcat.config.ServletConfigMapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 2017-05-27.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class GPTomcat {
    private int port = 8082;

    private Map<String, Class<GPServlet>> servletMap = new HashMap<String, Class<GPServlet>>();

    public GPTomcat() {
    }

    public GPTomcat(int port) {
        this.port = port;
    }

    //main entry 程序入口
    public static void main(String[] args) throws Exception {
        new GPTomcat().start();
    }

    //container start
    public void start() throws Exception {
        //init
        initServlets();
        //BIO
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Tomcat started on " + this.port);
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                // in  out
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                //分发处理
                dispatch(in, out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != socket) {
                    socket.close();
                }
            }
        }
    }

    private void initServlets() throws ClassNotFoundException {
        for(GpServletConfig servletConfig : ServletConfigMapping.getConfigs()){
            servletMap.put(servletConfig.getUrlMapping(), (Class<GPServlet>) Class.forName(servletConfig.getClazz()));
        }
    }

    private void dispatch(InputStream in, OutputStream out){
        GPRequest request = new GPRequest(in);
        GPResponse response = new GPResponse(out);
        //分发
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
}
