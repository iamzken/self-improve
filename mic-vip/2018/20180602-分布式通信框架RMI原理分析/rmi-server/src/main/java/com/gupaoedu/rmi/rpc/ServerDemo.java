package com.gupaoedu.rmi.rpc;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ServerDemo {
    public static void main(String[] args) {
        IGpHello iGpHello=new GpHelloImpl();
        RpcServer rpcServer=new RpcServer();
        rpcServer.publisher(iGpHello,8888);
    }
}
