package com.gupao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jack
 * Create in 16:09 2018/9/5
 * Description:
 */
public class BlockingQueue {
    //队列的实现   存储的容器

    private List<String> list = new ArrayList<String>();
    private int maxSize;   //队列的最大值
    private Object lock = new Object();   //自定义的锁

    public BlockingQueue(int maxSize) {
        this.maxSize = maxSize;
        System.out.println("线程" + Thread.currentThread().getName() + "已经初始化完成...大小是:"+this.maxSize);
    }

    //必然要有一个方法，put，get，线程安全
    public void put(String element) {
        //一定当前线程进入的   lock       同一把锁
        synchronized (lock) {
            try {
                //带阻塞：如果队列满了，就入到等待状态
                if (this.list.size() == this.maxSize) {
                    System.out.println("线程" + Thread.currentThread().getName() + "已经满了,put方法等待中...");
                    lock.wait();   //释放锁，同时当前线程进入到等待状态
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //没有满的话，就可以存元素put
            this.list.add(element);
            System.out.println("线程:" + Thread.currentThread().getName() + "向队列中添加了元素:" + element);
            //唤醒：通知其他线程可以取数据了
            lock.notifyAll();
        }
    }

    //取元素
    public void get() {
        synchronized (lock) {
            //带阻塞：如果对列空了，就要进入到等待状态
            if (this.list.size() == 0) {
                try {
                    System.out.println("线程" + Thread.currentThread().getName() + "当前队列空了，get方法等待中...");
                    lock.wait();   //释放锁，同时当前线程进入到等待状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //取元素
            String result = list.get(0);
            list.remove(0);
            System.out.println("线程:" + Thread.currentThread().getName() + "取到了元素:" + result);
            //通知其他线程可以加入元素了
            lock.notifyAll();
        }
    }

    //测试
    public static void main(String[] args) {
        final BlockingQueue blockingQueue=new BlockingQueue(5);
        new Thread(new Runnable() {
            @Override
            public void run() {
                blockingQueue.put("1");
                blockingQueue.put("2");
                blockingQueue.put("3");
                blockingQueue.put("4");
                blockingQueue.put("5");
                blockingQueue.put("6");
            }
        },"t1-put").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                blockingQueue.get();
                blockingQueue.get();
                blockingQueue.get();
                blockingQueue.get();
                blockingQueue.get();
                blockingQueue.get();
                blockingQueue.get();
            }
        },"t2-get").start();
    }
}
