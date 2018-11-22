package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo1 {

    int a=0;
    boolean flag=false;
    public void writer(){
        a=1;
        flag=true;
    }
    public void reader(){
        if(flag){
            int i=a*a;
        }
    }

    public static void main(String[] args) {
        Demo1 demo1=new Demo1();
        new Thread(()->{demo1.writer();}).start();
        new Thread(()->{demo1.reader();}).start();
    }
}
