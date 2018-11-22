package com.gupaoedu;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.*;

/**
 * @Author: qingshan
 * @Date: 2018/11/2 17:32
 * @Description: 咕泡学院，只为更好的你
 *
 * 测试布隆过滤器的正确判断和误判
 *
 * 往布隆过滤器里面存放100万个元素
 * 测试100个存在的元素和9900个不存在的元素
 *
 */
public class BloomFilterDemo {
    private static final int insertions = 1000000;

    public static void main(String[] args) {

        // 初始化一个存储string数据的布隆过滤器，初始化大小为100W
        // 默认误判率是0.03
        BloomFilter<String> bf = BloomFilter.create(
                Funnels.stringFunnel(Charsets.UTF_8), insertions);

        // 用于存放所有实际存在的key，判断key是否存在
        Set<String> sets = new HashSet<String>(insertions);

        // 用于存放所有实际存在的key，可以取出使用
        List<String> lists = new ArrayList<String>(insertions);

        // 向三个容器初始化100W个随机并且唯一的字符串
        for (int i = 0; i < insertions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            sets.add(uuid);
            lists.add(uuid);
        }

        int right = 0; // 正确判断的次数
        int wrong = 0; // 错误判断的次数

        for (int i = 0; i < 10000; i++) {
            // 可以被100整除的时候，取一个存在的数。否则随机生成一个UUID
            // 0-10000之间，可以被100整除的数有100个（100的倍数）
            String data = i % 100 == 0 ? lists.get(i / 100) : UUID.randomUUID().toString();

            if (bf.mightContain(data)) {
                if (sets.contains(data)) {
                    // 判断存在实际存在的时候，命中
                    right++;
                    continue;
                }
                // 判断存在却不存在的时候，错误
                wrong++;
            }
        }

        float percent = (float) wrong / 9900;
        System.out.println("100个实际存在的元素，判断存在的："+right);
        System.out.println("9900个实际不存在的元素，误认为存在的："+wrong+"，误判率："+percent);
    }
}