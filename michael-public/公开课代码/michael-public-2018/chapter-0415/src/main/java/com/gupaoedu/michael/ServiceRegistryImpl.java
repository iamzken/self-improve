package com.gupaoedu.michael;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ServiceRegistryImpl implements ServiceRegistry{

    private CuratorFramework curatorFramework=null;

    private static final String REGISTER_ROOT="/registry";
    {
        curatorFramework= CuratorFrameworkFactory.builder().
                connectString("192.168.11.152:2181").sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(2000,10)).
                build();
        curatorFramework.start();
        System.out.println("连接创建成功");
    }

    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        String servicePath=REGISTER_ROOT+"/"+serviceName;
        if(curatorFramework.checkExists().forPath(servicePath)==null){
            curatorFramework.create().creatingParentsIfNeeded().
                    withMode(CreateMode.PERSISTENT).forPath(servicePath,"0".getBytes());
        }
        String addressPath=servicePath+"/"+serviceAddress;
        String node=curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(addressPath,"0".getBytes());
        System.out.println("节点注册成功:"+node);
    }

    public static void main(String[] args) throws Exception {
        ServiceRegistry serviceRegistry=new ServiceRegistryImpl();
//        serviceRegistry.register("order-service","192.168.11.111:8080");

        serviceRegistry.register("order-service","192.168.11.112:8080");
        System.in.read();
    }
}
