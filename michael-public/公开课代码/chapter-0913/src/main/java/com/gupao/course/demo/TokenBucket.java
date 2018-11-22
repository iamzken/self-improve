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

    private static final ConcurrentMap<String,RateLimiter> LIMITER_CONCURRENT_MAP=new ConcurrentHashMap<>();

    /**
     * 初始化方法
     * @param resource
     * @param qps
     */
    public static void createFlowLimitMap(String resource,double qps){
        RateLimiter limiter=LIMITER_CONCURRENT_MAP.get(resource);
        if(limiter==null){
            limiter=RateLimiter.create(qps);
            LIMITER_CONCURRENT_MAP.putIfAbsent(resource,limiter);
        }
        limiter.setRate(qps);
    }

    public static boolean enter(String resource){
        RateLimiter limiter=LIMITER_CONCURRENT_MAP.get(resource);
        return limiter.tryAcquire();
    }

    public static void main(String[] args) {
        createFlowLimitMap("/getUser",10);

        ExecutorService service= Executors.newFixedThreadPool(200);

        Work work=new Work("/getUser");
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
        private String resource;
        public Work(String resource){
            this.resource=resource;
        }
        @Override
        public void run() {
            if(enter(resource)){
                System.out.println("允许通过");
            }else{
                System.out.println("你被限制住了");
            }
        }
    }
}
