package com.gupao.web.thread;

/**
 * Created by
 * on 17/3/10.
 * Description:
 */
public class Client {
    private String clientId;

    public Client(String clientId) {
        this.clientId = clientId;
    }

    public void callback(String resp) {
        System.out.println("Client消息返回 [" + resp + "]");
    }

    public void send(Accptor accptor) {
        System.out.println("消息发送 > " + Thread.currentThread().getName() + " -" + Thread.currentThread().getId());
        accptor.accept(this);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
