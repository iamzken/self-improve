package com.gupaoedu.michael.register;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ServiceRegistryImpl implements ServiceRegistry{

    private CuratorFramework curatorFramework=null;
    private static final String REGISTRY_ROOT="/registry";
    {
        curatorFramework= CuratorFrameworkFactory.builder().
                connectString("192.168.11.150:2181").sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,10)).build();
        curatorFramework.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        try {
            String servicePath=REGISTRY_ROOT+"/"+serviceName;
            if(curatorFramework.checkExists().forPath(servicePath)==null){
                curatorFramework.create().creatingParentsIfNeeded().
                        withMode(CreateMode.PERSISTENT).forPath(servicePath,"0".getBytes());
            }
            String addressPath=servicePath+"/"+serviceAddress;
            String addressNode=curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath,"0".getBytes());
            System.out.println("节点注册成功:"+addressNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServiceRegistry serviceRegistry=new ServiceRegistryImpl();
        serviceRegistry.register("order-service","192.168.11.111:8080");
        serviceRegistry.register("order-service","192.168.11.112:8080");
        System.in.read();
    }
}
