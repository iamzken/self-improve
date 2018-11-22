package com.gupao.course.demo;

import java.util.concurrent.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CallabledDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service= Executors.newFixedThreadPool(2);
        MyCallabledDemo mc1=new MyCallabledDemo("Thread A");
        MyCallabledDemo mc2=new MyCallabledDemo("Thread B");
        Future future1=service.submit(mc1);
        Future future2=service.submit(mc2);
        System.out.println(future1.get().toString());
        System.out.println(future2.get().toString());
        service.shutdown();
    }
}

class MyCallabledDemo implements Callable{

    private String name;

    public MyCallabledDemo(String name){
        this.name=name;
    }

    public Object call() throws Exception {
        return name+"：返回的内容";
    }
}
