package com.gupaoedu.pub2018.chapter4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadPoolDemo implements Runnable{

    static ExecutorService executorService=MyExecutors.newMyExecutors();

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ThreadPoolExecutor tpe=(ThreadPoolExecutor)executorService;
        for(int i=0;i<100;i++){
            executorService.execute(new ThreadPoolDemo());
        }
        executorService.shutdown();
    }
}
