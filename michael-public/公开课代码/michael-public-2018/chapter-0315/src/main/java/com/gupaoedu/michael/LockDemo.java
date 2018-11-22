package com.gupaoedu.michael;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class LockDemo {

    private final String ROOT="/Locks";
    private final String LOCK="/lock";

    public boolean tryLock(String threadName){
        System.out.println(threadName+"尝试获取锁");
        CuratorFramework curatorFramework=CuratorUtils.getCurator(); //每个客户端获取不同的连接
        try {
            curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(LOCK,"data".getBytes());
            System.out.println(threadName+":获取锁成功");
            Thread.sleep(4000);
            curatorFramework.delete().forPath(ROOT+LOCK);
        }catch (KeeperException.NodeExistsException e){
            System.out.println(threadName+":获取锁失败,注册监听事件");
            registerEvent(curatorFramework);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void registerEvent(CuratorFramework curatorFramework){
        PathChildrenCache childrenCache=new PathChildrenCache(curatorFramework,ROOT,true);
        PathChildrenCacheListener childrenCacheListener=new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println(Thread.currentThread().getName()+"->监听子节点事件");
                if(pathChildrenCacheEvent.getType()== PathChildrenCacheEvent.Type.CHILD_REMOVED){
                    tryLock(Thread.currentThread().getName());
                }
            }
        };
        childrenCache.getListenable().addListener(childrenCacheListener);
    }

    public static void main(String[] args) throws IOException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        LockDemo lockDemo=new LockDemo();
        for(int i=0;i<100;i++){
            Thread thread=new Thread(()->{
                try {
                    countDownLatch.await();
                    lockDemo.tryLock(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"Thread-"+i);
            thread.start();
            countDownLatch.countDown();
        }
        System.in.read();
    }
}
