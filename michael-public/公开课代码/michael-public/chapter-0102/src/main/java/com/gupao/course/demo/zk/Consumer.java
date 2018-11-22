package com.gupao.course.demo.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Consumer {

    private List<String> serverList=new ArrayList<>(); //保存服务提供方的地址信息

    private String serviceName="serviceA";

    public void init() throws Exception {
        //产生连接并且判断根结是否存在，不存在则创建
        String zkServer="192.168.11.142:2181";
        ZkClient zkClient=new ZkClient(zkServer);
        String servicePath="/configcenter/"+serviceName;
        boolean isExist=zkClient.exists(servicePath);
        if(isExist){
            serverList=zkClient.getChildren(servicePath);
        }else{
            throw new Exception("error");
        }
        zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("服务节点发生变化，节点的信息："+list);
                serverList=list;
            }
        });
    }

    public void consume(){
        Random rdm=new Random();
        int i=rdm.nextInt(serverList.size()); //随机负载
        System.out.println(i);
        System.out.println("调用："+serverList.get(i)+"提供的服务");
    }


    public static void main(String[] args) throws Exception {
        Consumer consumer=new Consumer();
        consumer.init();
        for(int i=0;i<10;i++) {
            consumer.consume();
            Thread.sleep(5000);
        }
        System.in.read();
    }

}
