package com.gupao.web.thread;

/**
 * Created by
 * on 17/3/10.
 * Description:
 */
public class TomcatServer {

    public void handle(Client client) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.callback("from server >> " + Thread.currentThread().getName() + " -" + Thread.currentThread().getId());
    }
}
