package com.gupaoedu.vip.spring5.demo.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Tom on 2018/5/13.
 */
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");


        PrintWriter out = resp.getWriter();

        out.println(Thread.currentThread().getName() + "接到请求");
        AsyncContext asyncContext = req.startAsync();

        asyncContext.addListener(new AsyncListener() {

            //完成时执行
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                out.println(Thread.currentThread().getName() + "请求完成!");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                out.println(Thread.currentThread().getName() + "请求超时!");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                out.println(Thread.currentThread().getName() + "请求错误!");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                out.println(Thread.currentThread().getName() + "请求开始!");
            }
        });


        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                out.println(Thread.currentThread().getName() + "真正的业务逻辑，正在执行");
                asyncContext.complete();
            }
        });
    }
}
