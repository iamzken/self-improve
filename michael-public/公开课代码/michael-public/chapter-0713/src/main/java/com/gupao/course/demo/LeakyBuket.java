package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class LeakyBuket {

    private static long timestamp=System.currentTimeMillis();

    private static int capactiy=10 ; //桶的容量

    private static int rate=3 ; // 出水速度

    private static int water=0;  //当前的水量

    private static boolean enter(){
        long now=System.currentTimeMillis();
        int out=(int)(now-timestamp)*rate; //计算剩余的水量

        water=Math.max(0,water-out);

        timestamp=now;

        if((water+1)<capactiy) {  //水还没满
            water++; //可以进水
            return true;
        }
        return false;
    }



}
