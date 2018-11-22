package com.gupaoedu.michael;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ZookeeperDemoWithAsync{


    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch=new CountDownLatch(2);//等待异步回调执行完再结束main进程
        ExecutorService executorService= Executors.newFixedThreadPool(2);//创建一个线程池
        CuratorFramework curatorFramework= CuratorFrameworkFactory.builder().
                connectString("192.168.11.147:2181").sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();
        curatorFramework.start();
        //创建持久化节点，基于异步回调方式
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground(
                (curatorFramework1, curatorEvent) ->{
                   System.out.println(Thread.currentThread().getName()+"->"+curatorEvent.getResultCode());
                   countDownLatch.countDown();
        },executorService).forPath("/first","data".getBytes());
        //创建持久化节点，基于异步回调方式
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(
                (curatorFramework12, curatorEvent) -> {
                    System.out.println(Thread.currentThread().getName()+"->"+curatorEvent.getResultCode());
                    countDownLatch.countDown();
        },executorService).forPath("/second","second".getBytes());

        countDownLatch.await();
        executorService.shutdown(); //释放资源
        curatorFramework.close();
    }

}
