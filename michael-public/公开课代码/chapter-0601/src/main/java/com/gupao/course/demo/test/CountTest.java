package com.gupao.course.demo.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CountTest {

    public static long timeStamp=System.currentTimeMillis();

    public static int reqCount=0;

    public static final int limit =100;

    public static final long interval=1000;

    public static boolean grant(){
        long now=System.currentTimeMillis();
        if(now<timeStamp+interval){
            reqCount++;
            return reqCount<=limit;
        }else{
            timeStamp=now;
            reqCount=1;
            return true;
        }
    }

    static class Work implements Runnable{
        public void run(){
            try {
                if(grant()){
                    System.out.println("允许执行业务逻辑");
                }else{
                    System.out.println("限流中，请稍后");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service= Executors.newFixedThreadPool(200);
        Work work=new Work();
        for(int i=0;i<20000;i++){
            Random random=new Random();
            int time=random.nextInt(10);
            Thread.sleep(time);
            service.submit(work);
        }
    }
}
