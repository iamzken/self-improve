package com.gupaoedu.pub2018.chapter4;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MyExecutors extends ThreadPoolExecutor {
    //beforeExecutor、afterExecutor、shutdown

    private ConcurrentMap<String,Date> startTime;


    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.startTime=new ConcurrentHashMap<>();
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void shutdown() {
        System.out.print("已经执行的任务数量："+this.getCompletedTaskCount()+"\n");
        System.out.print("当前的活动线程数："+this.getActiveCount()+"\n");
        System.out.print("当前排队的线程数："+this.getQueue().size()+"\n");
        super.shutdown();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTime.put(String.valueOf(r.hashCode()),new Date());
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Date statDate=startTime.remove(String.valueOf(r.hashCode()));
        Date finishDate=new Date();
        long dif=finishDate.getTime()-statDate.getTime(); //执行间隔时间
        System.out.println("任务耗时："+dif);
        System.out.println("最大允许的线程数:"+this.getMaximumPoolSize());
        System.out.println("线程的空闲时间"+this.getKeepAliveTime(TimeUnit.MILLISECONDS));
        System.out.println("任务总数:"+this.getTaskCount());
        super.afterExecute(r, t);
    }

    public static ExecutorService newMyExecutors(){
        return new MyExecutors(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }
}
