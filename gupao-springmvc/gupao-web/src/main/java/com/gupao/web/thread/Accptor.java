package com.gupao.web.thread;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by
 * on 17/3/10.
 * Description:
 */
public class Accptor {
    private AtomicLong count = new AtomicLong(0);
    private volatile boolean block = false;
    private final int REFUSE_LIMIT = 12;

    public void accept(Client client) {
        new Thread(new Worker(client, new TomcatServer())).start();
    }

    class Worker implements Runnable {
        Client client;
        TomcatServer server;

        public Worker(Client client, TomcatServer server) {
            this.client = client;
            this.server = server;
        }

        public void run() {
            if (count.incrementAndGet() > REFUSE_LIMIT) {
                block = true;
            }
            if (block) {
                throw new RuntimeException("拒绝服务 > " + client.getClientId());
            }
            try {
                server.handle(client);
            } finally {
                count.decrementAndGet();
            }
        }
    }
}
