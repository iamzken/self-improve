package com.gupao.registry;

import com.gupao.loadbalance.LoadBalance;
import com.gupao.loadbalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack
 * Create in 21:31 2018/9/1
 * Description:
 */
public class ServiceDiscoveryImpl implements IServiceDiscovery {

    List<String> lists=new ArrayList<String>();
    private CuratorFramework curatorFramework;
    public ServiceDiscoveryImpl(){
            curatorFramework= CuratorFrameworkFactory.builder().connectString(ZkConfig.zkAddress).
                    sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000,10)).
                    build();
            curatorFramework.start();
    }
    @Override
    public String discover(String serviceName) {
        //根据serviceName进行拼接   /registrys/com.gupao.test
        String path=ZkConfig.zkRegistryPath+"/"+serviceName;

        try {
            lists=curatorFramework.getChildren().forPath(path);    //  url   String
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if n 个  取舍   要哪一个    选择   负载均衡   ----->要哪一个url

        //动态感知服务节点的变化
        registerWatch(path);




        //lists有值之后，进行负载均衡选择   n urls   url
        LoadBalance loadBalance=new RandomLoadBalance();
        return loadBalance.select(lists);
    }

    private void registerWatch(final String path) {
        PathChildrenCache childrenCache=new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener pathChildrenCacheListener=new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                lists=curatorFramework.getChildren().forPath(path);
            }
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            throw new RuntimeException("注册PathChild Watcher 异常"+e);
        }
    }
}
