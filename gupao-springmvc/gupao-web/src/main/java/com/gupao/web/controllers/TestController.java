package com.gupao.web.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gupao.dal.dao.TestMapper;
import com.gupao.web.redis.RedisVo;
import com.gupao.web.service.TestService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by
 * on 16/8/16.
 * Description:
 */
@Controller
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TestService testService;

    @RequestMapping("/test/db")
    public void test(String key) {
        testService.test();
    }

    @RequestMapping("/zrange")
    @ResponseBody
    public String zrange(String key, Integer start, Integer end) {
        return JSON.toJSONString(redisTemplate.opsForZSet().range(key, start, end));
    }

    @RequestMapping(name = "/show")
    @ResponseBody
    public String show(String uri, Integer period) {
        String hash = String.format("%s:%s", period, uri);
        JSONObject result = new JSONObject();
        Map<Object, Object> dataMap = redisTemplate.opsForHash().entries("count:" + hash);
        for (Map.Entry entry : dataMap.entrySet()) {
            logger.info("key={},count={}", DateFormatUtils.format(new Date(Long.parseLong((String) entry.getKey()) * 1000), "yyyy-MM-dd'T'HH:mm:ss"), entry.getValue());
            result.put(DateFormatUtils.format(new Date(Long.parseLong((String) entry.getKey()) * 1000), "yyyy-MM-dd HH:mm:ss"), entry.getValue());
        }
        return result.toJSONString();
    }

    @RequestMapping("/get")
    @ResponseBody
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @RequestMapping("/hgetall")
    @ResponseBody
    public String hgetAll(String key) {
        return JSON.toJSONString(redisTemplate.opsForHash().entries(key));
    }

    @RequestMapping("/set")
    @ResponseBody
    public String set(String key, String val) {
        redisTemplate.opsForValue().set(key, val);
        return redisTemplate.opsForValue().get("test");
    }

    @RequestMapping("/test")
    public String hello() {
        System.out.println(testService.toString());
        System.out.println(context.getBean(TestService.class));
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        testService.test();
        return "test";
    }

    @RequestMapping("/r/statics/b")
    public String staticsB(String uri, Integer period, Model model) {
        String hash = String.format("%s:%s", period, uri);
        Map<Object, Object> dataMap = redisTemplate.opsForHash().entries("count:" + hash);
        List<RedisVo> result = new ArrayList<RedisVo>();
        for (Map.Entry entry : dataMap.entrySet()) {
            RedisVo redisVo = new RedisVo();
            redisVo.setDate(new Date(Long.parseLong((String) entry.getKey()) * 1000));
            redisVo.setCount(Integer.parseInt((String) entry.getValue()));
            result.add(redisVo);
        }
        Collections.sort(result, new Comparator<RedisVo>() {
            public int compare(RedisVo o1, RedisVo o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        model.addAttribute("uri", uri);
        model.addAttribute("dataMap", result);
        return "statics";
    }

    @RequestMapping("/r/statics")
    public String statics(String uri, Integer period, Model model) {
        String hash = String.format("%s:%s", period, uri);
        Map<Object, Object> dataMap = redisTemplate.opsForHash().entries("count:" + hash);
        List<String> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        List<Object> keys = dataMap.keySet().stream().sorted(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                Date d1 = new Date(Long.parseLong((String) o1) * 1000);
                Date d2 = new Date(Long.parseLong((String) o2) * 1000);
                return d1.compareTo(d2);
            }
        }).collect(Collectors.toList());
        for (Object entry : keys) {
            x.add(DateFormatUtils.format(new Date(Long.parseLong((String) entry) * 1000),
                    "HH:mm:ss"));
            y.add(Integer.parseInt((String) dataMap.get((String) entry)));
        }
        model.addAttribute("uri", uri);
        model.addAttribute("xaxix", x);
        model.addAttribute("yaxix", y);
        return "statics";
    }

    @RequestMapping(value = "/test/form")
    @ResponseBody
    public String hello(String myname, HttpServletResponse response) {
        System.out.println(myname);
        System.out.println(response.getCharacterEncoding());
        return "hello 中 !" + myname;
    }

}
