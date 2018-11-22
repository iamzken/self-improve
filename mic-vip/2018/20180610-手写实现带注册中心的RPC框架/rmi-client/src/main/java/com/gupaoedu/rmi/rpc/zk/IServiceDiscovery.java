package com.gupaoedu.rmi.rpc.zk;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface IServiceDiscovery {

    /**
     * 根据请求的服务地址，获得对应的调用地址
     * @param serviceName
     * @return
     */
    String discover(String serviceName);
}
