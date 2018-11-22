package com.gupaoedu.pub2018.chapter9;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class BloomFilterDemo {

    public static void main(String[] args) {
        BloomFilter bloomFilter=BloomFilter.create
                (Funnels.stringFunnel(Charset.defaultCharset()),
                        1000000,0.001); //1%
        bloomFilter.put("mic");
        System.out.println(bloomFilter.mightContain("mic"));
    }
}
