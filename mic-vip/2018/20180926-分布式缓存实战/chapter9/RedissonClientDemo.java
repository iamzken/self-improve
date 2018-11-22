package com.gupaoedu.pub2018.chapter9;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RedissonClientDemo {
    public static void main(String[] args) {
        Config config=new Config();
        config.useClusterServers().
                addNodeAddress("redis://192.168.11.153:7000",
                        "redis://192.168.11.153:7001");
        RedissonClient redissonClient=Redisson.create(config);
        /*redissonClient.getLock();//分布式锁
        redissonClient.getBucket("mic").set("value");*/

    }
}
