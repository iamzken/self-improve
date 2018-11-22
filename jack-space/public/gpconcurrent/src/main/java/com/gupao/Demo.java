package com.gupao;

public class Demo {

    //用鲜花迎接下面的这段代码
    public synchronized void run1(){
        System.out.println("run1执行好了，发出通知");
        this.notifyAll();   //通知其他线程一个线程  wait池中的线程   run2  run3   但是不释放锁 ，要等当前线程中的代码执行完了，自己才会释放锁  if 已经释放锁了
        //System.out.println("hahhahah");
    }


    public synchronized void run2(){
        try {
            System.out.println("刚刚进入run2方法");
            this.wait();   //当前线程要进入到等待状态了，同时要释放锁   wait和notify一定要结合synchronized
            System.out.println("run2执行好了，发出通知");
//            this.notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void run3(){

        try {
            System.out.println("刚刚进入run3方法");
            this.wait();
            System.out.println("run3执行好了，发出通知");
//            this.notify();   //notify关键字只会通知一个线程执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Demo demo=new Demo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.run2();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.run3();
            }
        }).start();




        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.run1();
            }
        }).start();
    }
}
