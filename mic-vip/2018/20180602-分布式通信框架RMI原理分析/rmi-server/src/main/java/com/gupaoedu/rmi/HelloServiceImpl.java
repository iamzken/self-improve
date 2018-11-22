package com.gupaoedu.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class HelloServiceImpl extends UnicastRemoteObject implements IHelloService{

    protected HelloServiceImpl() throws RemoteException {
       // super();
    }

    @Override
    public String sayHello(String msg) throws RemoteException{
        return "Hello,"+msg;
    }
}
