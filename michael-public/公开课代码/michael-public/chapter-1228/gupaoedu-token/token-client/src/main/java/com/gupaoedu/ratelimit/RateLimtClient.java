package com.gupaoedu.ratelimit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service
public class RateLimtClient {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisScript<Long> rateLimiterClientLua;

    /*
     * 获得token的操作
     */
    public Token accquireToken(String key){
        return accquireToken(key,1);
    }

    public Token accquireToken(String key,Integer permits){
        Token token;
        Long currMillSecond=stringRedisTemplate.execute(
                (RedisCallback<Long>) redisConnection -> redisConnection.time()
        );

       stringRedisTemplate.execute(rateLimiterClientLua,
                Collections.singletonList(getKey(key)),permits.toString(),currMillSecond.toString());
       /* if(accquire==1){
            token=Token.SUCCESS;
        }else{
            token=Token.FAILED;
        }*/
        return Token.SUCCESS;
    }

    private String getKey(String key){
        return Constants.RATE_LIMIT_KEY+key;
    }
}
