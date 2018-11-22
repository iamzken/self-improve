package com.gupaoedu.vip.pattern.singleton.test;

import com.gupaoedu.vip.pattern.singleton.lazy.LazyOne;
import com.gupaoedu.vip.pattern.singleton.lazy.LazyTwo;
import com.gupaoedu.vip.pattern.singleton.register.Color;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tom on 2018/3/7.
 */
public class ColorTest {
    public static void main(String[] args) {
        System.out.println(Color.RED);
    }
}