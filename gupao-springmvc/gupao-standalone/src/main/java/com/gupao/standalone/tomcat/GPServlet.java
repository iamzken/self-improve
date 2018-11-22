package com.gupao.standalone.tomcat;

/**
 * Created by James on 2017-05-27.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public abstract class GPServlet {
    public void service(GPRequest request,GPResponse response){
        if("GET".equals(request.getMethod())){
            doGet(request, response);
        }else{
            doPost(request, response);
        }
    }

    public abstract void doGet(GPRequest request,GPResponse response);

    public abstract void doPost(GPRequest request,GPResponse response);
}
