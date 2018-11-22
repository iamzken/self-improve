package com.gupaoedu.rmi.rpc;

import java.lang.reflect.Proxy;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RpcClientProxy {


    /**
     * 创建客户端的远程代理。通过远程代理进行访问
     * @param interfaceCls
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    public <T> T clientProxy(final Class<T>
                                     interfaceCls,
                             final String host,final int port){
        //使用到了动态代理。
        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class[]{interfaceCls},new RemoteInvocationHandler(host,port));
    }




}
