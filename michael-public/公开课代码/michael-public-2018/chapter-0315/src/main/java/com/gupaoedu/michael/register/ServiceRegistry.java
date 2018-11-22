package com.gupaoedu.michael.register;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 * 服务注册
 */
public interface ServiceRegistry {

    /**
     * 注册服务
     * @param serviceName  服务名称
     * @param serviceAddress  服务地址
     */
    void register(String serviceName,String serviceAddress);
}
