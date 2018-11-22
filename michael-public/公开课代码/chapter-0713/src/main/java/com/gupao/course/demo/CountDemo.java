package com.gupao.course.demo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CountDemo {

    /**
     * 实现计数器
     */

    private static long timestamp=System.currentTimeMillis();

    private static int reqCount=0; //请求次数

    private static final int limit=100;  //限制次数

    private static final long interval=1000; //间隔时间

    //获取授权
    private static boolean start(){
        long now=System.currentTimeMillis();
        if(now<timestamp+interval){ //表示在当前一秒钟之内
            reqCount++;
            return reqCount<=limit;
        }
        timestamp=now;
        reqCount=1;
        return true;
    }

    static class Worker implements Runnable{
        @Override
        public void run() {
            if(start()){  //当前线程有没有权限访问这个接口
                System.out.println("你很幸运,可以正常访问");
            }else{
                System.out.println("你被限流啦。。");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service= Executors.newFixedThreadPool(200);

        Worker worker=new Worker();

        for(int i=0;i<20000;i++){
            Random random=new Random();
            int time=random.nextInt(10);

            Thread.sleep(time);

            service.submit(worker);
        }
    }
}
