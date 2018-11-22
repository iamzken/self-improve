package com.gupaoedu.michael;

import java.util.concurrent.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo3 implements Callable<String>{

    @Override
    public String call() throws Exception {
        return "Hello Mic";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool= Executors.newFixedThreadPool(1);
        Future<String> future=pool.submit(new Demo3());

        System.out.println(future.get());
    }
}
