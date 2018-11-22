package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SingleDemo {

    private volatile static SingleDemo instance;

    private SingleDemo(){}

    /**
     * 1 memory=allocate  分配对象的内存空间
     * 2 actorInstance(memory) 初始化对象
     * 3 instance=memory  设置instance指向刚刚被分配的内存地址
     *  1 2 3
     *  1 3 2
     *
     * @return
     */

    public static SingleDemo getInstance(){ //性能不高 ；双重检查 //不完整实例
        if(instance==null){
            synchronized (SingleDemo.class) {
                if(instance==null) {
                    instance = new SingleDemo();
                }
            }
        }
        return instance;
    }
}
