package com.gupao.course.demo.rmi;

import java.rmi.Remote;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface IHello extends Remote{

    String sayHello(String name) throws java.rmi.RemoteException;
}
