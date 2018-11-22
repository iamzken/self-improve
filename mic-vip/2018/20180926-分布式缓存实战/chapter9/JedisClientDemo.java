package com.gupaoedu.pub2018.chapter9;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JedisClientDemo {

    public static void main(String[] args) {
        //sentinel
//        HostAndPort hostAndPort=new HostAndPort();
        //哨兵集群的地址
//        JedisSentinelPool jedisSentinelPool=new JedisSentinelPool();
        JedisPool jedisPool=null;
//        jedisPool.getResource().set
        Set<HostAndPort> hostAndPortSet=new HashSet<>();
        hostAndPortSet.add(new HostAndPort("192.168.11.153",7000));
        hostAndPortSet.add(new HostAndPort("192.168.11.153",7001));

        JedisCluster jedisCluster=new JedisCluster(hostAndPortSet);
        jedisCluster.set("","");

    }
}
