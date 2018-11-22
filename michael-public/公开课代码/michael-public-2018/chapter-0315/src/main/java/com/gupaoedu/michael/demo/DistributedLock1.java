package com.gupaoedu.michael.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class DistributedLock1 implements Lock,Watcher{
    private ZooKeeper zk=null;
    private String ROOT_LOCK="/locks";
    private String WAIT_LOCK; //等待前一个锁
    private String CURRENT_LOCK; //当前锁
    private int sessionTimeOut=30000;
    private CountDownLatch countDownLatch;
    public DistributedLock1() {
        try {
            zk=new ZooKeeper("192.168.11.150:2181",sessionTimeOut,this);
            Stat stat=zk.exists(ROOT_LOCK,false); //判断根节点是否存在，不存在则创建
            if(stat==null){
                zk.create(ROOT_LOCK,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean tryLock() {
        try {
            //创建临时有序节点
            CURRENT_LOCK= zk.create(ROOT_LOCK+"/",new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+":节点:"+CURRENT_LOCK+"创建成功,尝试去竞争锁");
            List<String> childrens=zk.getChildren(ROOT_LOCK,false); //获取根节点下的所有子节点
            SortedSet<String> sortedSet=new TreeSet<>(); //通过集合进行排序
            for(String children:childrens){
                sortedSet.add(ROOT_LOCK+"/"+children);
            }
            String firstNode=sortedSet.first(); //获取子节点里面最小的节点
            //获取比当前创建成功的节点小的节点集合,比如A,B,C  ,headeSet('c'),则返回A,B
            SortedSet<String> lessThanMe=sortedSet.headSet(CURRENT_LOCK);
            if(CURRENT_LOCK.equals(firstNode)){
                return true;
            }
            if(!lessThanMe.isEmpty()){ //如果有比自己更小的节点
                WAIT_LOCK=lessThanMe.last(); //获得比当前节点更小的最后一个节点，设置给WAIT_LOCK
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void lock() {//拿不到lock就不罢休，不然线程就一直block
        if(this.tryLock()){
            System.out.println(Thread.currentThread().getName()+":"+CURRENT_LOCK+"成功获得锁");
            return;
        }
        try {
            waitForLock(WAIT_LOCK,sessionTimeOut); //没有获得锁，继续等待获得锁
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean waitForLock(String prev,long waitTime) throws KeeperException, InterruptedException {
        //等待当前节点的前一个节点
        Stat stat=zk.exists(prev,true);
        if(stat!=null){
            System.out.println(Thread.currentThread().getName()+":等待锁"+ROOT_LOCK+"/"+prev);
            countDownLatch=new CountDownLatch(1);
            // 计数等待，若等到前一个节点消失，则precess中进行countDown，停止等待，获取锁
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"获得锁成功");
        }
        return true;
    }
    @Override//马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            if (this.tryLock()) {
                return true;
            }
            return waitForLock(WAIT_LOCK, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void unlock() {
        try {
            System.out.println(Thread.currentThread().getName()+":释放锁 " + CURRENT_LOCK);
            zk.delete(CURRENT_LOCK, -1);
            CURRENT_LOCK = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Condition newCondition() {
        return null;
    }
    @Override
    public void process(WatchedEvent event) {
        //处理监听事件，当节点删除则触发事件
        if (this.countDownLatch != null) {
            this.countDownLatch.countDown();
        }
    }
    @Override//允许中断，通过Thread.interrupt来中断线程
    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }
}
