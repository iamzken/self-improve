package com.gupao.course.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.UUID;
import java.util.concurrent.locks.Lock;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 *
 *
 */
public class SimpleLock {

    Jedis conn=new Jedis("192.168.11.140",6379);
    private final String LOCK_NAME="lock";

    //获得锁 重入锁和非重入锁
    public String accquireLock(int timeout){
        String uuid= UUID.randomUUID().toString();
        long end=System.currentTimeMillis()+timeout;
        while (System.currentTimeMillis()<end) {
            if (conn.setnx(LOCK_NAME, uuid).intValue() == 1) {
                conn.expire(LOCK_NAME,timeout);
                return uuid;
            }
            if(conn.ttl(LOCK_NAME)==-1){
                conn.expire(LOCK_NAME,timeout);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //释放锁
    public boolean releaseLock(String uuid){
        while(true) {
            conn.watch(LOCK_NAME);
            if (uuid.equals(conn.get(LOCK_NAME))) {
                Transaction transaction = conn.multi();
                transaction.del(LOCK_NAME);
                if (transaction.exec() == null) {
                    continue;
                }
                return true;
            }
            conn.unwatch();
            break;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleLock simpleLock=new SimpleLock();
        String uuid = simpleLock.accquireLock(1000000);
        if (uuid != null) {
            System.out.println("获得锁成功");
        }else{
            System.out.println("获得锁失败");
        }

    }

}
