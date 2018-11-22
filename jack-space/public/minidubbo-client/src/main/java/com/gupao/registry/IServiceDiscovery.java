package com.gupao.registry;

/**
 * Created by Jack
 * Create in 21:30 2018/9/1
 * Description:
 */
public interface IServiceDiscovery {
    //com.gupao.api.IGpService  ---->127.0.0.1:8080  url
    String discover(String serviceName);
}
