package com.gupao.standalone.mytomcat.servlet;

import com.gupao.standalone.mytomcat.GPRequest;
import com.gupao.standalone.mytomcat.GPResponse;
import com.gupao.standalone.mytomcat.GPServlet;

/**
 * Created by James on 2017-05-27.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class JamesServlet extends GPServlet {
    public void doGet(GPRequest request, GPResponse response) {
        response.write("James Servlet GET response");
    }

    public void doPost(GPRequest request, GPResponse response) {
        response.write("James Servlet POST response");
    }
}
