package com.gupao.course.demo.rmi;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class HelloClient {

    public static void main(String[] args) {
        try {
            IHello hello=(IHello) Naming.lookup("rmi://localhost:1099/hello");
            System.out.println(hello + "--------------");



            for(String str: Naming.list("rmi://localhost:1099/hello")){
                System.out.println(str);
            }
            System.out.println(hello.sayHello("test"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
