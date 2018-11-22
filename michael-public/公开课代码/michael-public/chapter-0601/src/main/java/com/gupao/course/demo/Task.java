package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Task {

    private static boolean stop=false;

    public static void main(String[] args) throws InterruptedException {
        Thread  t=new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(!stop){
                    i++;
                }
            }
        });
        t.start();
        Thread.sleep(1000);
        System.out.println("stop Thread");
        stop=true;
    }
}
