package com.gupao.standalone.tomcat.servlet;

import com.gupao.standalone.tomcat.GPRequest;
import com.gupao.standalone.tomcat.GPResponse;
import com.gupao.standalone.tomcat.GPServlet;

/**
 * Created by James on 2017-05-27.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class GetServlet extends GPServlet{
    public void doGet(GPRequest request, GPResponse response) {
        String resp = "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html\n" +
                "\r\n" +
                "<html>" +
                "  <body>Hello World</body>" +
                "</html>";
        response.write(resp);
//        response.write("GetServlet method[GET] working");
    }

    public void doPost(GPRequest request, GPResponse response) {
        response.write("GetServlet method[POST] working");
    }
}
