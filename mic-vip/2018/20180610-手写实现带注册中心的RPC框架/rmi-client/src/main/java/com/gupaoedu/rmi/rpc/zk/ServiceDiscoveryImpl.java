package com.gupaoedu.rmi.rpc.zk;

import com.gupaoedu.rmi.rpc.zk.loadbalance.LoadBanalce;
import com.gupaoedu.rmi.rpc.zk.loadbalance.RandomLoadBanalce;
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
public class ServiceDiscoveryImpl implements IServiceDiscovery{

    List<String> repos=new ArrayList<>();

    private String address;

    private CuratorFramework curatorFramework;

    public ServiceDiscoveryImpl(String address) {
        this.address = address;

        curatorFramework=CuratorFrameworkFactory.builder().
                connectString(address).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000,
                        10)).build();
        curatorFramework.start();
    }

    @Override
    public String discover(String serviceName) {
        String path=ZkConfig.ZK_REGISTER_PATH+"/"+serviceName;
        try {
            repos=curatorFramework.getChildren().forPath(path);

        } catch (Exception e) {
            throw new RuntimeException("获取子节点异常："+e);
        }
        //动态发现服务节点的变化
        registerWatcher(path);

        //负载均衡机制
        LoadBanalce loadBanalce=new RandomLoadBanalce();

        return loadBanalce.selectHost(repos); //返回调用的服务地址
    }

    private void registerWatcher(final String path){
        PathChildrenCache childrenCache=new PathChildrenCache
                (curatorFramework,path,true);

        PathChildrenCacheListener pathChildrenCacheListener=new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos=curatorFramework.getChildren().forPath(path);
            }
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
           throw new RuntimeException("注册PatchChild Watcher 异常"+e);
        }


    }
}
