package com.gupao.registry;

/**
 * Created by Jack
 * Create in 11:38 2018/8/31
 * Description:
 */
public class ZkConfig {
    //zk集群的地址,客户端连接的地址
    public final static String zkAddress="127.0.0.1:2181";
    //public final static String zkAddress="10.211.55.19:2181,10.211.55.21:2181,10.211.55.20:2181";
    //所有服务发布在哪个节点下面
    public final static String zkRegistryPath="/registrys";
}
