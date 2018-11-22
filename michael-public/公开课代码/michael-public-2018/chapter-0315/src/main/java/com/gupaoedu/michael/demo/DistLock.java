package com.gupaoedu.michael.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class DistLock {
    private static final String rootPath = "distLock";

    private static final int DEFAULT_LOCK_TIMEOUT = 120;

    private CuratorFramework client;

    private Map<String, InterProcessMutex> locks;
    public DistLock() {
        this.client =
                CuratorFrameworkFactory.builder().connectString("192.168.11.150:2181")
                        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                        .connectionTimeoutMs(5000)
                        .sessionTimeoutMs(5000).namespace(rootPath).build();
        locks = new HashMap<>(32);
        this.client.start();
    }

    /**
     *
     * <p>Descrption: 获取zk客户端</p>
     * @Author J
     * @return CuratorFramework
     * @return
     */
    public CuratorFramework getClient() {
        return client;
    }

    /**
     *
     * <p>Descrption: 获取分布式锁</p>
     * @Author J
     * @return boolean
     * @param action
     * @param lockId
     * @param time
     * @return
     * @throws Exception
     */
    public boolean lock(String action, String lockId, int time) throws Exception {
        String uniqueLockId = action+"_"+lockId;

        InterProcessMutex lock = new InterProcessMutex(this.client, "/"+uniqueLockId);
        boolean isLocked = lock.acquire(time, TimeUnit.SECONDS);
        return isLocked;
    }

    /**
     *
     * <p>Descrption: 获取分布式锁</p>
     * @Author J
     * @return boolean
     * @param action
     * @param lockId
     * @return
     * @throws Exception
     */
    public boolean lock(String action, String lockId) throws Exception {
        return lock(action, lockId, DEFAULT_LOCK_TIMEOUT);
    }
    /**
     *
     * <p>Descrption: 释放锁</p>
     * @Author J
     * @return void
     * @param action
     * @param lockId
     * @throws Exception
     */
    public void unlock(String action, String lockId) throws Exception {
        String uniqueLockId = action+"_"+lockId;
        InterProcessMutex lock = null;
        if ((lock = this.locks.get(uniqueLockId)) != null) {
            this.locks.remove(uniqueLockId);
            lock.release();
        }
    }
    /**
     *
     * <p>Descrption: 关闭客户端</p>
     * @Author J
     * @return void
     */
    public void close() {
        if (this.client != null) {
            CloseableUtils.closeQuietly(this.client);
        }
    }
}
