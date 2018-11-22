package com.gupao.web;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by James on 2017/4/28.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class ThreadDemo {
    static class Constants {
        private volatile int i = 0;

        public int getI() {
            return i;
        }

        public synchronized void setI() {
            i++;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService es = null;
        try {
             es = Executors.newFixedThreadPool(5);
            final Constants constants = new Constants();
            for (int i = 0; i < 5; i++) {
                es.submit(new Runnable() {
                    public void run() {
                        constants.setI();
                        System.out.println(constants.getI());
                    }
                });
            }
            Thread.sleep(1000);
//            System.out.println(constants.getI());
        } finally {
            es.shutdown();
        }
    }
}
