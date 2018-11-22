package com.gupao.course.demo.zk;

import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ProviderB {

    private String serviceName="serviceA";

    private final String ROOT="/configcenter"; //根节点
    public void init(){
        //产生连接并且判断根结是否存在，不存在则创建
        String zkServer="192.168.11.142:2181";
        ZkClient zkClient=new ZkClient(zkServer);
        if(!zkClient.exists(ROOT)){
            zkClient.createPersistent(ROOT);
        }

        //启动服务判断当前服务节点是否注册过
        if(!zkClient.exists(ROOT+"/"+serviceName)){
            zkClient.createPersistent(ROOT+"/"+serviceName);
        }

        String ip="192.168.11.131:8080";
        zkClient.createEphemeral(ROOT+"/"+serviceName+"/"+ip);
        System.out.println("providerB服务启动成功");
    }

    public static void main(String[] args) throws IOException {
        ProviderB providerA=new ProviderB();
        providerA.init();
        System.in.read();
    }

}
