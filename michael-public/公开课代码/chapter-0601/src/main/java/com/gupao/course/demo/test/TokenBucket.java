package com.gupao.course.demo.test;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TokenBucket {

    private static final ConcurrentMap<String,RateLimiter> resourceRateLimiterMap=new ConcurrentHashMap<>();

    public static void createFlowLimitMap(String resource,double qps){
        RateLimiter limiter=resourceRateLimiterMap.get(resource);
        if(limiter==null){
            limiter=RateLimiter.create(qps);
            resourceRateLimiterMap.putIfAbsent(resource,limiter);
        }
        limiter.setRate(qps);
    }

    public static boolean enter(String resource) throws Exception{
        RateLimiter limiter=resourceRateLimiterMap.get(resource);
        if(limiter==null){
            throw new Exception(resource);
        }
        return limiter.tryAcquire();
    }

    static class Work implements Runnable{
        String source;
        public Work(String source){
            this.source=source;
        }
        public void run(){
            try {
                if(enter(source)){
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
        createFlowLimitMap("/getName",10);
        ExecutorService service= Executors.newFixedThreadPool(200);
        Work work=new Work("/getName");
        for(int i=0;i<200;i++){
            Random random=new Random();
            int time=random.nextInt(100);
            Thread.sleep(time);
            service.submit(work);
        }
    }
}
