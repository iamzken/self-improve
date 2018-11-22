package com.gupaoedu.michael.lession1;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ValatileDemo {

    private volatile static ValatileDemo instance;

    private ValatileDemo() {

    }
    // 不完整实例
    public static ValatileDemo getInstance(){
        if(instance==null){
            synchronized (ValatileDemo.class) {
                if(instance==null) {
                    instance = new ValatileDemo();
                }
            }
        }
        return instance;
    }
}
