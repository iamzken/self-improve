package com.gupaoedu.rmi.rpc;

import com.gupaoedu.rmi.rpc.zk.IRegisterCenter;
import com.gupaoedu.rmi.rpc.zk.RegisterCenterImpl;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class LBServerDemo {
    public static void main(String[] args) throws IOException {
        IGpHello iGpHello=new GpHelloImpl();
        IRegisterCenter registerCenter=new RegisterCenterImpl();
        RpcServer rpcServer=new RpcServer(registerCenter,"127.0.0.1:8080");
        rpcServer.bind(iGpHello);
        rpcServer.publisher();
        System.in.read();
    }
}
