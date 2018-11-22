package com.gupao.web.tomcat;

public class Handler {

    public void handle(Client client) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.callback("from server >> " + Thread.currentThread().getName() + " -" + Thread.currentThread().getId() + " -" +
                client.getClientId());
    }
}