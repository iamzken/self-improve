package com.gupaoedu.michael;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ZookeeperDemo implements Watcher{

    private static ZooKeeper zookeeper;
    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException, NoSuchAlgorithmException {
        zookeeper=new ZooKeeper("192.168.11.147:2181", 5000, new ZookeeperDemo());
        countDownLatch.await();
        System.out.println(zookeeper.getState());

        //通过设置权限以后，如果需要访问该节点，必须要先授权，通过zookeeper.addAuthInfo来授权，授权以后也仍然只有读的权限
        zookeeper.addAuthInfo("digest","super:man".getBytes());
        zookeeper.setData("/zk-digest","123".getBytes(),-1);
        //修改节点的值
        /*Thread.sleep(1000);
        zookeeper.setData("/zk-persistence2","313".getBytes(),-1);
        //删除节点
        Thread.sleep(1000);
        zookeeper.delete("/zk-persistence2",-1);*/
        //查询节点
      /*  Thread.sleep(1000);
        zookeeper.getData("/zk-persistence3",new ZookeeperDemo(),new Stat());*/
        System.in.read();
    }
    @Override
    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected==event.getState()){
            countDownLatch.countDown();
        }
        System.out.println(event.getType());
        if(Event.EventType.NodeDataChanged==event.getType()){
            try {
                zookeeper.getData(event.getPath(),true,null);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
