package com.gupao.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Created by Jack
 * Create in 21:19 2018/9/1
 * Description:
 */
public class ZkServiceRegistryCenterImpl implements IServiceRegistryCenter {

    //zk写入节点了  zkClient  Curator实现对zk的一个操作
    private CuratorFramework curatorFramework;
    public ZkServiceRegistryCenterImpl(){
        curatorFramework= CuratorFrameworkFactory.builder().connectString(ZkConfig.zkAddress).
                sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000,10)).
                build();
        //客户端已经连接上zk的服务端了
        curatorFramework.start();
    }
    //服务注册
    @Override
    public void register(String serviceName, String serviceAddress) {
        //构建  /registrys/com.gupao.GPServiceName     持久化的节点
        String servicePath=ZkConfig.zkRegistryPath+"/"+serviceName;
        try {
            if(curatorFramework.checkExists().forPath(servicePath)==null){
                curatorFramework.create().creatingParentsIfNeeded().
                        withMode(CreateMode.PERSISTENT).forPath(servicePath,"0".getBytes());
            }
            System.out.println("serviceName创建成功:"+servicePath);
//            /registrys/com.gupao.GPServiceName/127.0.0.1:8080    临时节点
            String addressPath=servicePath+"/"+serviceAddress;
            String addNode=curatorFramework.create().withMode(CreateMode.EPHEMERAL).
                    forPath(addressPath,"0".getBytes());
            System.out.println("serviceAddress创建成功:"+addNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
