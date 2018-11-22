package com.gupao.course.demo.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class LeakyBucket {

    private static long timeStamp=System.currentTimeMillis();

    private static int capacity=10; //桶的容量

    private static int rate=3; //出水的速度

    private static int water=0;  //当前的水量

    public static boolean grant(){
        long now=System.currentTimeMillis();
        //先执行漏水，计算剩余水量
        int out=(int)(now-timeStamp)*rate;

        water=Math.max(0,water-out);

        timeStamp=now;

        if((water+1)<capacity){// 尝试加水,并且水还未满
            water++;
            return true;
        }else{// 水满，拒绝加水
            return false;
        }
    }

    static class Work implements Runnable{
        public void run(){
            if(grant()){
                System.out.println("允许执行业务逻辑");
            }else{
                System.out.println("限流中，请稍后");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service= Executors.newFixedThreadPool(200);
        Work work=new Work();
        for(int i=0;i<200;i++){
            service.submit(work);
        }
    }

}
