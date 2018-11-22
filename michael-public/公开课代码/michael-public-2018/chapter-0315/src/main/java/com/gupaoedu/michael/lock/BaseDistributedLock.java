package com.gupaoedu.michael.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class BaseDistributedLock {

    private  CuratorFramework curatorFramework;
    private  String path;
    private  String basePath;
    private  String lockName;

    public BaseDistributedLock(CuratorFramework curatorFramework, String path, String lockName) {
        this.curatorFramework = curatorFramework;
        this.basePath = path;
        this.path = path.concat("/").concat(lockName);
        this.lockName = lockName;
    }
    private void deleteOurPath(String ourPath) throws Exception {
        curatorFramework.delete().forPath(ourPath);
    }

    private String createLockNode(String path) throws Exception {
        return curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path,"0".getBytes());
    }

    private List<String> getShortedChildren() throws Exception {
        try {
            List<String> childrens = curatorFramework.getChildren().forPath(basePath);
            Collections.sort(childrens, String::compareTo);
            return childrens;
        }catch (KeeperException.NoNodeException e ){
            curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(basePath,"0".getBytes());
            return getShortedChildren();
        }catch (Exception e){
            throw e;
        }
    }

    private boolean waitToLock(long startMillis,Long millisToWait,String ourPath) throws Exception {
        boolean holdLock=false;
        boolean isDelete=false;
        try {
            while (!holdLock) {
                List<String> children = getShortedChildren(); //获取从小大到排序的顺序节点
                String sequenceNodeName = ourPath.substring(basePath.length() + 1);
                int ourIndex = children.indexOf(sequenceNodeName);
                boolean isHoldLock = (ourIndex == 0);
                String pathToWatch = isHoldLock ? null : children.get(ourIndex - 1);
                if (isHoldLock) {
                    holdLock = true;
                    System.out.println(Thread.currentThread().getName()+":获得锁成功");
                } else {
                    System.out.println(Thread.currentThread().getName()+":获得锁失败");
                    String previousSequencePath = basePath.concat("/").concat(pathToWatch);
                    final CountDownLatch latch = new CountDownLatch(1);
                    NodeCache nodeCache = new NodeCache(curatorFramework, previousSequencePath, false);
                    NodeCacheListener nodeCacheListener = () -> {

                        System.out.println("触发监听事件:" + nodeCache.getCurrentData().getPath());
                        latch.countDown();
                    };
                    nodeCache.getListenable().addListener(nodeCacheListener);
                    try {
                        nodeCache.start();
                        //判断等待超时事件
                        if (millisToWait != null) {
                            millisToWait = System.currentTimeMillis() - startMillis;
                            startMillis = System.currentTimeMillis();
                            if (millisToWait <= 0) {
                                isDelete = true; //超时
                                break;
                            }
                            latch.await(millisToWait, TimeUnit.DAYS.MILLISECONDS);
                        }
                        Thread.sleep(5000);
                    } catch (KeeperException.NoNodeException e) { //节点不存在出现异常

                    } finally {
                        nodeCache.getListenable().removeListener(nodeCacheListener);
                    }
                }
            }
        }catch (Exception e){
            isDelete=true;
            throw e;
        }finally {
            if(isDelete){
                deleteOurPath(ourPath);
            }
        }
        return holdLock;
    }
    protected String attemptLock(long time, TimeUnit unit) throws Exception {
        final long startMillis = System.currentTimeMillis();
        final Long millisToWait = (unit != null) ? unit.toMillis(time) : null;

        String ourPath = null;
        boolean hasTheLock = false;
        boolean isDone = false;
        int retryCount = 0;

        //网络闪断需要重试一试
        while (!isDone) {
            isDone = true;

            try {
                //createLockNode用于在locker（basePath持久节点）下创建客户端要获取锁的[临时]顺序节点
                ourPath = createLockNode(path);
                /**
                 * 该方法用于判断自己是否获取到了锁，即自己创建的顺序节点在locker的所有子节点中是否最小
                 * 如果没有获取到锁，则等待其它客户端锁的释放，并且稍后重试直到获取到锁或者超时
                 */
                hasTheLock = waitToLock(startMillis, millisToWait, ourPath);

            } catch (KeeperException.NoNodeException e) {
                if (retryCount++ < 10) {
                    isDone = false;
                } else {
                    throw e;
                }
            }
        }

        if (hasTheLock) {
            return ourPath;
        }
        return null;
    }
    protected void releaseLock(String lockPath) throws Exception{
        deleteOurPath(lockPath);
    }
}
