package com.gupaoedu.vip.pattern.singleton.test;

import com.gupaoedu.vip.pattern.singleton.lazy.LazyOne;
import com.gupaoedu.vip.pattern.singleton.lazy.LazyTwo;

/**
 * Created by Tom on 2018/3/8.
 */
public class LazyTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 200000000;i ++) {
            Object obj = LazyTwo.getInstance();
        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start));
    }

}
