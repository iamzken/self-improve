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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @Author: qingshan
 * @Date: 2018/11/2 17:32
 * @Description: 咕泡学院，只为更好的你
 * 布隆过滤器并发测试，在redis和数据库之间
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class BloomTestsConcurrency {
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    private static final int THREAD_NUM = 1000; // 并发线程数量，Windows机器不要设置过大

    static BloomFilter<String> bf;

    static List<User> allUsers;

    @PostConstruct
    public void init() {
        // 从数据库获取数据，加载到布隆过滤器
        long start = System.currentTimeMillis();
        allUsers = userService.getAllUser();
        if (allUsers == null || allUsers.size() == 0) {
            return;
        }
        // 创建布隆过滤器，默认误判率0.03，即3%
        bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), allUsers.size());
        // 误判率越低，数组长度越长，需要的哈希函数越多
        // bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), allUsers.size(), 0.0001);
        // 将数据存入布隆过滤器
        for (User user : allUsers) {
            bf.put(user.getAccount());
        }
        long end = System.currentTimeMillis();
        System.out.println("加载"+allUsers.size()+"条数据到布隆过滤器完毕，总耗时："+(end -start ) +"毫秒");
    }

    @Test
    public void cacheBreakDownTest() {
        long start = System.currentTimeMillis();
        allUsers = userService.getAllUser();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUM);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++){
            executorService.execute(new BloomTestsConcurrency().new MyThread(cyclicBarrier, redisTemplate, userService));
        }

        executorService.shutdown();
        //判断是否所有的线程已经运行完
        while (!executorService.isTerminated()) {

        }

        long end = System.currentTimeMillis();
        System.out.println("并发数："+THREAD_NUM + "，新建线程以及过滤总耗时："+(end -start ) +"毫秒");
    }

    public class MyThread implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private RedisTemplate redisTemplate;
        private UserService userService;

        public MyThread(CyclicBarrier cyclicBarrier, RedisTemplate redisTemplate, UserService userService) {
            this.cyclicBarrier = cyclicBarrier;
            this.redisTemplate = redisTemplate;
            this.userService = userService;
        }

        @Override
        public void run() {
            //所有子线程等待，当子线程全部创建完成再一起并发执行后面的代码
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            // 随机产生一个字符串，在布隆过滤器中不存在
            String randomUser = UUID.randomUUID().toString();
            // 从List中获取一个存在的用户
            //String randomUser = allUsers.get(new Random().nextInt(allUsers.size())).getAccount();
            String key = "Key:" + randomUser;

            Date date1 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 如果布隆过滤器中不存在这个用户直接返回，将流量挡掉
            if (!bf.mightContain(randomUser)) {
                System.out.println(sdf.format(date1)+" 布隆过滤器中不存在，非法请求");
                return;
            }
            // 查询缓存，如果缓存中存在直接返回缓存数据
            ValueOperations<String, String> operation = (ValueOperations<String, String>) redisTemplate.opsForValue();
            Object cacheUser = operation.get(key);
            if (cacheUser != null) {
                Date date2 = new Date();
                System.out.println(sdf.format(date2)+" 命中redis缓存");
                return;
            }
            // TODO 防止并发重复写缓存，加锁
            synchronized (randomUser) {
                // 如果缓存不存在查询数据库
                List<User> user = userService.getUserByAccount(randomUser);
                if (user == null || user.size() == 0) {
                    return;
                }
                // 将mysql数据库查询到的数据写入到redis中
                Date date3 = new Date();
                System.out.println(sdf.format(date3)+" 缓存不存在，从数据库查询并写入Reids");
                operation.set("Key:" + user.get(0).getAccount(), user.get(0).getAccount());
            }
        }

    }


}
