package com.gupao.course.demo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Mic 老师
 *
 * 通过计数器的方式来做限流操作(1分钟之内接口的请求不能超过100次)
 */
public class CountDemo {

    private static int REQCOUNT=0;

    private static final int LIMIT=100;

    private static final long INTERVAL=1000; //间隔时间

    private static long TIMESTAMP=System.currentTimeMillis();

    private static boolean canAccess(){
        long now=System.currentTimeMillis();
        if(now<TIMESTAMP+INTERVAL){
            REQCOUNT++;
            return REQCOUNT<=LIMIT;
        }
        TIMESTAMP=now;
        REQCOUNT=1;
        return true;
    }


    public static void main(String[] args) {
        ExecutorService service= Executors.newFixedThreadPool(200);

        Work work=new Work();
        for(int i=0;i<20000;i++){
            Random random=new Random();
            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            service.submit(work);

        }
    }

    static class Work implements Runnable{
        @Override
        public void run() {
            if(canAccess()){
                System.out.println("允许通过");
            }else{
                System.out.println("你被限制住了");
            }
        }
    }
}
