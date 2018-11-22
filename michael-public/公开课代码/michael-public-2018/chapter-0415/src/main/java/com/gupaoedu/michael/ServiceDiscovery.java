package com.gupaoedu.michael;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ServiceDiscovery {

    List<String> serviceRepos=new ArrayList<>();

    private CuratorFramework curatorFramework=null;

    private LoadBanalceStrategy loadBanalceStrategy=new RandomLoadBalance();

    private static final String REGISTER_ROOT="/registry";
    {
        curatorFramework= CuratorFrameworkFactory.builder().
                connectString("192.168.11.152:2181").sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(2000,10)).
                build();
        curatorFramework.start();
    }

    public void init(String serviceName) throws Exception {
        String servicePath=REGISTER_ROOT+"/"+serviceName;
        try {
            serviceRepos=curatorFramework.getChildren().forPath(servicePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        registerWatcher(servicePath);
    }

    //注册监听
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

    public String getServiceEndpoint(){
        return loadBanalceStrategy.selectHost(serviceRepos);
    }

    public static void main(String[] args) throws Exception {
        ServiceDiscovery serviceDiscovery=new ServiceDiscovery();
        serviceDiscovery.init("order-service");

        for(int i=0;i<10;i++){
            System.out.println(serviceDiscovery.getServiceEndpoint());
            Thread.sleep(4000);
        }
    }
}
