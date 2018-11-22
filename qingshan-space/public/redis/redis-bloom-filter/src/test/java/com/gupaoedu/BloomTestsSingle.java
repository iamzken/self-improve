package com.gupaoedu;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;


/**
 * @Author: qingshan
 * @Date: 2018/11/2 17:32
 * @Description: 咕泡学院，只为更好的你
 * 布隆过滤器单条测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class BloomTestsSingle {

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    int count = 0;

    BloomFilter<String> bf;

    List<User> allUsers;

    @PostConstruct
    public void init() {
        // 将数据从数据库加载到布隆过滤器
        long start = System.currentTimeMillis();

        allUsers = userService.getAllUser();
        if (allUsers == null || allUsers.size() == 0) {
            return;
        }
        // 创建布隆过滤器(默认3%误差)
        bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), allUsers.size());
        // 将数据存入布隆过滤器
        for (User user : allUsers) {
            bf.put(user.getAccount());
        }
        long end = System.currentTimeMillis();
        System.out.println("加载" + allUsers.size() + "条数据到布隆过滤器完毕，总耗时：" + (end - start) + " 毫秒");
    }

    @Test
    public void cacheBreakDownTest() {
        // 不存在的用户
        //String randomUser = UUID.randomUUID().toString();
        // 获取一个在数据库，但是不在redis的用户
        //  String randomUser = allUsers.get(new Random().nextInt(allUsers.size())).getAccount();
        // 在Redis缓存的用户
        String randomUser = "684a172e-616d-46d6-9ff9-934b2688da4b";
        String key = "Key:" + randomUser;
        System.out.println(key);

        //如果布隆过滤器中不存在这个用户直接返回，将流量挡掉
        if (!bf.mightContain(randomUser)) {
            System.out.println("布隆过滤器中不存在，非法请求");
            return;
        }
        //查询缓存，如果缓存中存在直接返回缓存数据
        ValueOperations<String, String> operation = (ValueOperations<String, String>) redisTemplate.opsForValue();

        Object cacheUser = operation.get(key);
        if (cacheUser != null) {
            System.out.println("命中redis缓存");
            return;
        }
        synchronized (this) {
            // 再次检测redis是否存在
            if(null == operation.get(key)){
                // 如果缓存不存在查询数据库
                List<User> user = userService.getUserByAccount(randomUser);
                if (user == null || user.size() == 0) {
                    return;
                }
                //将mysql数据库查询到的数据写入到redis中
                System.out.println("缓存不存在，从数据库查询并写入Reids");
                operation.set("Key:" + user.get(0).getAccount(), user.get(0).getAccount());
            }
        }
    }
}
