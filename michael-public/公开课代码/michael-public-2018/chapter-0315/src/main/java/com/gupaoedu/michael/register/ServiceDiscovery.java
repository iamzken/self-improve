package com.gupaoedu.michael.register;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ServiceDiscovery {

    List<String> serviceRepos=new ArrayList<>(); //服务缓存
    private static final String REGISTRY_ROOT="/registry";

    private CuratorFramework curatorFramework=null;
    {
        curatorFramework= CuratorFrameworkFactory.builder().
                connectString("192.168.11.150:2181").sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,10)).build();
        curatorFramework.start();
    }
    public void init(String serviceName) throws Exception {
        String path=REGISTRY_ROOT+"/" + serviceName;
        try {
            serviceRepos = curatorFramework.getChildren().forPath(path);
        }catch (KeeperException.NoNodeException e){
            System.out.println("节点不存在");
        }catch (Exception e){
            throw e;
        }
        registerWatcher(path);
    }

    public String getServiceEndPoint(){
        return new RandomStrategy().doSelect(serviceRepos);
    }


    private void registerWatcher(String path) throws Exception {
        PathChildrenCache pathChildrenCache=new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener pathChildrenCacheListener=new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                serviceRepos=client.getChildren().forPath(path);
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start();
    }

    public static void main(String[] args) throws Exception {
        ServiceDiscovery serviceDiscovery=new ServiceDiscovery();
        serviceDiscovery.init("order-service");
        for(int i=0;i<10;i++){
            System.out.println(serviceDiscovery.getServiceEndPoint());
            Thread.sleep(4000);
        }
    }

}
