package com.gupaoedu.pub2018.chapter4;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class AtomicDemo {
    private static int count=0;

    public static void inc(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<1000;i++){
            new Thread(AtomicDemo::inc).start();
        }
        Thread.sleep(4000);
        System.out.println("y运行结果："+count);
    }
}
