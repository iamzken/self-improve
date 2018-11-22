package com.gupaoedu.michael;

/**
 * Hello world!
 *
 */
public class App extends Thread{

    @Override
    public void run() {

        System.out.println("run");
    }

    public static void main(String[] args) {
        new App().interrupt();  //优雅关闭
    }
}
