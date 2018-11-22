package com.gupaoedu.michael;

import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ProviderB {

    private String serviceName="order-service";

    public void init(){
        ZkClient zkClient=new ZkClient("192.168.11.147:2181");

        String ROOT="/configcenter";

        if(!zkClient.exists(ROOT)){//判断configcenter节点是否存在,如果不存在则创建节点
            zkClient.createPersistent(ROOT);
        }

        if(!zkClient.exists(ROOT+"/"+serviceName)){
            zkClient.createPersistent(ROOT+"/"+serviceName);
        }

        String address="192.168.11.112:8080";
        zkClient.createEphemeral(ROOT+"/"+serviceName+"/"+address);

        System.out.println("提供服务order-service："+address);
    }

    public static void main(String[] args) throws IOException {
        ProviderB providerA=new ProviderB();
        providerA.init();
        System.in.read();
    }
}
