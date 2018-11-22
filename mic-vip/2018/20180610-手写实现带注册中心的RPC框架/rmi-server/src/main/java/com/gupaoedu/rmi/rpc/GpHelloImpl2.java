package com.gupaoedu.rmi.rpc;

import com.gupaoedu.rmi.rpc.anno.RpcAnnotation;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 *
 */
@RpcAnnotation(value = IGpHello.class)
public class GpHelloImpl2 implements IGpHello{
    @Override
    public String sayHello(String msg) {
        return "I'm 8081 node ："+msg;
    }
}
