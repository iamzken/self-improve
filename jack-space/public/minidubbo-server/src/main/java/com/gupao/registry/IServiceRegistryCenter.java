package com.gupao.registry;

/**
 * Created by Jack
 * Create in 21:18 2018/9/1
 * Description:
 */
public interface IServiceRegistryCenter {
    //就是要把serviceName(服务名称):com.gupao.api.IGpService
    // serviceAddresss(服务地址):127.0.0.1:8080进行一个绑定
    void register(String serviceName,String serviceAddress);
}
