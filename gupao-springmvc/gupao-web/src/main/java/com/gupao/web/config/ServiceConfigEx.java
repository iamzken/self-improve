package com.gupao.web.config;

import com.gupao.web.redis.ClusterConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.NamedNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by James
 * on 16/8/16.
 */
@EnableWebMvc
@ComponentScan(basePackages = "com.gupao.web.redis")
public class ServiceConfigEx {

    @Autowired
    private ClusterConfigurationProperties clusterProperties;

    @Bean
    public JedisPoolConfig poolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        return jedisPoolConfig;
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPoolConfig(poolConfig());
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

//    @Bean
//    public RedisConnectionFactory connectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration() .master("mymaster")
//                .sentinel("127.0.0.1", 26379).sentinel("127.0.0.1", 36379);
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig);
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//    }

    @Bean
    public RedisTemplate getRedisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory());
        return redisTemplate;
    }
}
