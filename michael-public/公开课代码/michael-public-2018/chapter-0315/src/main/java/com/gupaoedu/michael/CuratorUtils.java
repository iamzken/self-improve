package com.gupaoedu.michael;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
         * 风骚的Michael 老师
         */
public class CuratorUtils {


    public static CuratorFramework getCurator(){
        CuratorFramework curatorFramework= CuratorFrameworkFactory.builder().
                connectString("192.168.11.150:2181").sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,10)).build();
        curatorFramework.start();
        return curatorFramework;
    }
}
