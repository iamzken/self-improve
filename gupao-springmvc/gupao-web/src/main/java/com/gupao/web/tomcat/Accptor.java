package com.gupao.web.tomcat;

import java.util.concurrent.atomic.AtomicLong;

public class Accptor {
    private AtomicLong count = new AtomicLong(0);
    private volatile boolean block = false;
    private final int REFUSE_LIMIT = 15;

    public void accept(Client client) {
        new Thread(new Worker(client, new Handler())).start();
    }

    class Worker implements Runnable {
        Client client;
        Handler handler;

        public Worker(Client client, Handler handler) {
            this.client = client;
            this.handler = handler;
        }

        public void run() {
            if (count.incrementAndGet() > REFUSE_LIMIT) {
                block = true;
            }
            if (block) {
                throw new RuntimeException("拒绝服务 > " + client.getClientId());
            }
            try {
                handler.handle(client);
            } finally {
                count.decrementAndGet();
            }
        }
    }
}