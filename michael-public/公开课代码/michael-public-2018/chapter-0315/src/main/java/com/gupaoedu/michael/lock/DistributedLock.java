package com.gupaoedu.michael.lock;

import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface  DistributedLock {


    /**获取锁，如果没有得到就等待*/

    void acquire()  throws Exception;

    /**
     * 获取锁，直到超时
     * @param time 超时时间
     * @param unit time参数的单位
     * @return是否获取到锁
     * @throws Exception
     */
      boolean acquire (long time, TimeUnit unit)  throws Exception;

    /**
     * 释放锁
     * @throws Exception
     */
      void release()  throws Exception;
}
