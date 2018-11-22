package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class VisibilityDemo extends Thread{
    private volatile boolean stop;

    @Override
    public void run() {
        int i=0;
        System.out.println("执行循环");
        while (!isStop()){
            i++;
        }
        System.out.println("执行完成，i:"+i);
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public static void main(String[] args) throws InterruptedException {
        //Alt+Insert
        VisibilityDemo visibilityDemo=new VisibilityDemo();
        visibilityDemo.start();
        Thread.sleep(1000);
        visibilityDemo.setStop(true);
        Thread.sleep(1000);
        System.out.println("程序执行完毕："+visibilityDemo.isStop());
    }
}
