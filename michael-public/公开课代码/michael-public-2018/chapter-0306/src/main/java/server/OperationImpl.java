package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class OperationImpl extends UnicastRemoteObject implements IOperation{

    protected OperationImpl() throws RemoteException {
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
