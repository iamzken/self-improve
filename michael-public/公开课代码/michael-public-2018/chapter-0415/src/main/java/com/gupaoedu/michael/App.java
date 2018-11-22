package com.gupaoedu.michael;

/**
 * Hello world!
 */
public class App {
    private static volatile App instance = null;

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public static void main(String[] args) {
        App.getInstance();
    }
}
