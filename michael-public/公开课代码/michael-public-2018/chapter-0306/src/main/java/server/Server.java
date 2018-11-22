package server;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Server {

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        IOperation iOperation=new OperationImpl();//实例化远程对象并创建代理类
        LocateRegistry.createRegistry(1099);
        Naming.rebind("rmi://127.0.0.1/Operation",iOperation);
        System.out.println("Server startup");
    }
}
