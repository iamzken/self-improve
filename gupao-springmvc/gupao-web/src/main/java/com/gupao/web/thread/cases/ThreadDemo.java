package com.gupao.web.thread.cases;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class ThreadDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService es = null;
        try {
            es = Executors.newFixedThreadPool(10);
            final Constants constants = new Constants();
            es.submit(new Worker(constants));
            Thread.sleep(100);
            es.submit(new WriteWorker(constants));
        } finally {
            es.shutdown();
        }
    }
}
