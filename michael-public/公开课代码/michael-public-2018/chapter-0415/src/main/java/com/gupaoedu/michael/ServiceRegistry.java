package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface ServiceRegistry {


    /**
     * 注册服务
     * @param serviceName
     * @param serviceAddress
     */
    void register(String serviceName,String serviceAddress) throws Exception;

}
