package com.gupaoedu.michael;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CuratorEventDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework=CuratorFrameworkFactory.builder().
                connectString("192.168.11.150:2181").sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();
        curatorFramework.start();
        Thread.sleep(100);
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/first","sc1".getBytes());//添加子节点

        addListenerWithPathChildrenCache(curatorFramework); //添加当前节点事件监听
       Thread.sleep(100);
        curatorFramework.setData().forPath("/first","Mic".getBytes());//修改节点事件
      /*  Thread.sleep(100);
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/second/sc1","sc1".getBytes());//添加子节点
        Thread.sleep(100);
        curatorFramework.setData().forPath("/second/sc1","Mic".getBytes());//修改子节点事件
        Thread.sleep(100);
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/second/sc1/sc1-1","sc1-1".getBytes()); //添加孙子节点
        Thread.sleep(100);
        curatorFramework.setData().forPath("/second/sc1/sc1-1","Mic".getBytes());//修改孙子节点事件*/
        Thread.sleep(100);
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/first");        //删除节点事件
        curatorFramework.close();
        System.in.read();
    }
    public static void addListenerWithTreeCache(CuratorFramework curatorFramework) throws Exception {
        TreeCache treeCache=new TreeCache(curatorFramework,"/second");
        TreeCacheListener treeCacheListener=new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("进入TreeCache事件回调");
                ChildData childData=treeCacheEvent.getData(); //获取子节点的数据
                System.out.println(treeCacheEvent.getType()+":"+
                        childData.getPath()+"->"+new String(childData.getData()));
            }
        };
        treeCache.getListenable().addListener(treeCacheListener);
        treeCache.start();
    }


    public static void addListenerWithNodeCache(CuratorFramework curatorFramework) throws Exception {
        NodeCache nodeCache=new NodeCache(curatorFramework,"/first",false);
        NodeCacheListener nodeCacheListener=new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Receive Node Changed");
                System.out.println("path"+nodeCache.getCurrentData().getPath()+"->data:"+new String(nodeCache.getCurrentData().getData()));
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    public static void addListenerWithPathChildrenCache(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache childrenCache=new PathChildrenCache(curatorFramework,"/first",true);
        PathChildrenCacheListener childrenCacheListener=new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("进入pathchildren事件回调");
                ChildData childData=pathChildrenCacheEvent.getData(); //获取子节点的数据
                System.out.println(pathChildrenCacheEvent.getType()+":"+
                        childData.getPath()+"->"+new String(childData.getData()));
            }
        };
        childrenCache.getListenable().addListener(childrenCacheListener);//添加监听
        childrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }
}
