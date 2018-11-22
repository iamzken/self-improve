package com.gupao.course.demo;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TokenBucket {


    private static final ConcurrentMap<String,RateLimiter> resourcesMap=new ConcurrentHashMap<>();

    /**
     * 初始化资源限流策略
     */
    private static void createFlowLimiter(String resource,double qps){
        RateLimiter limiter=resourcesMap.get(resource);

        if(limiter==null){
            limiter=RateLimiter.create(qps);
            resourcesMap.putIfAbsent(resource,limiter);
        }
        limiter.setRate(qps);
    }

    private static boolean enter(String resource){
        RateLimiter limiter=resourcesMap.get(resource);
        if(limiter==null){
            return false;//或者抛出异常
        }
        return limiter.tryAcquire(); //尝试去获取令牌
    }


    static class Worker implements Runnable{
        String resource;

        public Worker(String resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            if(enter(resource)){  //当前线程有没有权限访问这个接口
                System.out.println("你很幸运,可以正常访问");
            }else{
                System.out.println("你被限流啦。。");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 初始化资源对应的请求qps
         */
        String resource="/getNameById";
        createFlowLimiter(resource,10);  //rate=10    10/s  100ms生成一个令牌

        ExecutorService service= Executors.newFixedThreadPool(200);
        Worker worker=new Worker(resource);

        for(int i=0;i<200;i++){
            Random random=new Random();
            int time=random.nextInt(100);
            Thread.sleep(time);
            service.submit(worker);
        }
    }

}
