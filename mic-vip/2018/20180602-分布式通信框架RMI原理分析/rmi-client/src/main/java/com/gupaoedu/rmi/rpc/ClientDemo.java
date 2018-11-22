package com.gupaoedu.rmi.rpc;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ClientDemo {

    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy=new RpcClientProxy();

        IGpHello hello=rpcClientProxy.clientProxy
                (IGpHello.class,"localhost",8888);
        System.out.println(hello.sayHello("mic"));
    }
}
