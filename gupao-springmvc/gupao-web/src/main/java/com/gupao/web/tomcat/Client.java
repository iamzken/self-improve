package com.gupao.web.tomcat;

/**
 * Created by James on 2017/4/28.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
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
