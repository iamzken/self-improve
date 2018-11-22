package com.gupaoedu.pub2018.chapter9;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JedisConnectionUtils {
    private static JedisPool pool=null;
    static {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        pool=new JedisPool(jedisPoolConfig,"192.168.11.153",6379);
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }
}
