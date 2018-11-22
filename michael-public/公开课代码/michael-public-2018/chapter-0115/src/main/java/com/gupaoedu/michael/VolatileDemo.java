package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class VolatileDemo {

    private volatile static boolean stop=false;

    public static void main(String[] args) throws InterruptedException {
        int a=1;
        int b=2;

        Thread t=new Thread(()->{
            int i=0;
            while(!stop){
                i++;
            }
        });
        t.start();
        Thread.sleep(1000);
        System.out.println("Execute Thread");
        stop=true;
    }
}
