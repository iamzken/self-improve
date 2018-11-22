package com.gupaoedu;

import com.gupaoedu.entity.User;
import com.gupaoedu.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @Author: qingshan
 * @Date: 2018/11/3 20:39
 * @Description: 咕泡学院，只为更好的你
 * 把数据库的数据全部缓存到redis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RedisTest {
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void loadDataToRedis() {
        ValueOperations<String, String> operation = (ValueOperations<String, String>) redisTemplate.opsForValue();

        List<User> allUsers = userService.getAllUser();
        if (allUsers == null || allUsers.size() == 0) {
            return;
        }

        for (User user : allUsers) {
            Object cacheUser = operation.get("Key:" + user.getAccount());
            if (cacheUser == null) {
                Random random = new Random();
                operation.set("Key:" + user.getAccount(), user.getAccount(),1000*60*24 + random.nextInt(60000));
            }
        }
    }
}
