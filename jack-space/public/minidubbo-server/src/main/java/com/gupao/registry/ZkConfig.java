package com.gupao.registry;

/**
 * Created by Jack
 * Create in 11:38 2018/8/31
 * Description:
 */
public class ZkConfig {
    //zk集群的地址,客户端连接的地址
    public final static String zkAddress="192.168.137.111:2181,192.168.137.112:2181,192.168.137.113:2181";
    //public final static String zkAddress="10.211.55.19:2181,10.211.55.21:2181,10.211.55.20:2181";
    //所有服务发布在哪个节点下面
    public final static String zkRegistryPath="/registrys";
}
