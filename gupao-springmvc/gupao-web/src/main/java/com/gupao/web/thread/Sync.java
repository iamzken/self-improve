package com.gupao.web.thread;

public class Sync {

    public static void main(String[] args) {
        final Sync run = new Sync();
        System.out.print("S");
        new Thread() {
            public void run() {
                run.y();
            }
        }.start();
        new Thread() {
            public void run() {
                run.z();
            }
        }.start();
        new Thread() {
            public void run() {
                run.x();
            }
        }.start();

    }

    public synchronized void x() {
        System.out.print("x");
        delay();
    }

    public synchronized void y() {
        System.out.print("y");
        delay();
    }

    public synchronized void z() {
        System.out.print("z");
        delay();
    }

    void delay() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }
}
