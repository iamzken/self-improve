package com.gupao.course.demo;

import java.util.concurrent.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CallableDemo implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("enter in");
        return "success";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service= Executors.newFixedThreadPool(2);
        CallableDemo callableDemo=new CallableDemo();
        Future future=service.submit(callableDemo);
        /***
         * 1
         * 2
         * 3
         */
        System.out.println(future.get());
    }
}
