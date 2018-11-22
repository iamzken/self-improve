package com.gupao;

public class GpThread {
    public static void main(String[] args) throws InterruptedException {
        final Person person=new Person("Jack",18);

      /*  person.change("Rose",19);

        person.change("Lily",20);*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                person.change("Rose",19);
            }
        }).start();
        Thread.sleep(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                person.change("Lily",20);
            }
        }).start();
    }
}
