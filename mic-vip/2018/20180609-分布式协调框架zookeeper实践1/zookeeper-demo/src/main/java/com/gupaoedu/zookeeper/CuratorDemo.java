package com.gupaoedu.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework=CuratorFrameworkFactory.
                builder().connectString("192.168.11.153:2181," +
                "192.168.11.154:2181,192.168.11.155:2181").
                sessionTimeoutMs(4000).retryPolicy(new
                ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();

        curatorFramework.start();

        //结果: /curator/mic/node1
        //原生api中，必须是逐层创建，也就是父节点必须存在，子节点才能创建
        /*curatorFramework.create().creatingParentsIfNeeded().
                withMode(CreateMode.PERSISTENT).
                forPath("/mic/node1","1".getBytes());*/
        //删除
//        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/mic/node1");

        Stat stat=new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/mic/node1");

        curatorFramework.setData().
                withVersion(stat.getVersion()).forPath("/mic/node1","xx".getBytes());

        curatorFramework.close();


    }
}
