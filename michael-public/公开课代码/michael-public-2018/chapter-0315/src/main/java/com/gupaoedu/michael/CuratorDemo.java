package com.gupaoedu.michael;

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
        CuratorFramework curatorFramework=CuratorFrameworkFactory.builder().
                connectString("192.168.11.147:2181").sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();
        curatorFramework.start();
        //创建节点， 如果父节点不存在并且需要的话，会自己创建，并且节点为持久化节点
        curatorFramework.create().creatingParentsIfNeeded().
                withMode(CreateMode.PERSISTENT).forPath("/first","data".getBytes());
        //查询节点信息
        Stat stat=new Stat();//用于存储节点的详细信息
        String val=new String(curatorFramework.getData().storingStatIn(stat).forPath("/first"));
        System.out.println("/first节点的值为："+val);//输出节点信息
        //更新节点信息，并且输出更新节点后的状态
        stat=curatorFramework.setData().withVersion(stat.getVersion()).forPath("/first","change".getBytes());
        //删除节点,设置节点的版本号。如果有子节点，进行递归删除
        curatorFramework.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath("/first");
        curatorFramework.close();
    }
}
