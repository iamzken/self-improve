package com.gupao.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by James on 2018-01-03.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/jvm-cpu")
    public String cpu() {
        while (true) {

        }
    }

//    @GetMapping("/jvm-mem")
//    public String mem() {
//
//    }

    @GetMapping("/jvm-io")
    public String io() throws InterruptedException, IOException {
        while (true) {
            File file = new File("/tmp/iotest/" + System.currentTimeMillis() + ".txt");
            try (FileOutputStream outputStream = new FileOutputStream(file);) {
                for (int i = 51; i < 10000; i++) {
                    outputStream.write(new byte[256]);
                }
                outputStream.flush();
            }
        }
    }

    private static List<int[]> bigObj = new ArrayList();
    private static List<char[]> bigCharObj = new ArrayList();

    public static int[] generate1M() {
        return new int[524288];
    }

    public static char[] generate1MChar() {
        return new char[1048576];
    }

    @GetMapping("/jvm-error")
    public void error() throws InterruptedException {
        for (int i = 0; i < 1000; ++i) {
            if (i == 0) {
                Thread.sleep(500L);
                System.out.println("start = [" + new Date() + "]");
            } else {
                Thread.sleep(4000L);
            }
            bigObj.add(generate1M());
        }

    }

    @GetMapping("/jvm-info")
    public String info() {
        List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
        StringBuffer sb = new StringBuffer();
        for (GarbageCollectorMXBean b : l) {
            sb.append(b.getName() + "\n");
        }
        return sb.toString();
    }

    @GetMapping("/jvm-lock")
    public void deadLock() {
        DeadLock dl = new DeadLock();
        Thread0 t0 = new Thread0(dl);
        Thread1 t1 = new Thread1(dl);
        t0.start();
        t1.start();
    }

    public class Thread1 extends Thread {
        private DeadLock dl;

        public Thread1(DeadLock dl) {
            this.dl = dl;
        }

        public void run() {
            try {
                dl.rightLeft();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class Thread0 extends Thread {
        private DeadLock dl;

        public Thread0(DeadLock dl) {
            this.dl = dl;
        }

        public void run() {
            try {
                dl.leftRight();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class DeadLock {
        private final Object left = new Object();
        private final Object right = new Object();

        public void leftRight() throws Exception {
            synchronized (left) {
                Thread.sleep(2000);
                System.out.println("leftRight start!");
                synchronized (right) {
                    System.out.println("leftRight end!");
                }
            }
        }

        public void rightLeft() throws Exception {
            synchronized (right) {
                Thread.sleep(2000);
                System.out.println("rightLeft start!");
                synchronized (left) {
                    System.out.println("rightLeft end!");
                }
            }
        }
    }

}
