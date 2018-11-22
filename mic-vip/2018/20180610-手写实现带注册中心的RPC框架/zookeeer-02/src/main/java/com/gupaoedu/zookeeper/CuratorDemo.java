package com.gupaoedu.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CuratorDemo {
    public static void main(String[] args) {
        CuratorFramework curatorFramework=CuratorFrameworkFactory.builder().build();
        InterProcessMutex interProcessMutex=new InterProcessMutex(curatorFramework,"/locks");
        try {
            interProcessMutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
