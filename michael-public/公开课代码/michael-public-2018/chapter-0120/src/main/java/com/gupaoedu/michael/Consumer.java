package com.gupaoedu.michael;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Consumer {

    private String serviceName="order-service";

    private List<String> serviceList=new ArrayList<>();

    public void init(){
        String ROOT="/configcenter";

        ZkClient zkClient=new ZkClient("192.168.11.147:2181");

        String SERVICE_PATH=ROOT+"/"+serviceName;  //订单服务的路径

        if(zkClient.exists(SERVICE_PATH)){
            serviceList=zkClient.getChildren(SERVICE_PATH); //拿到/configcenter/order-service/下所有子节点
        }else{
            throw new RuntimeException("service not exists");
        }

        //如果/configcenter/order-service节点下的数据有变化的话，要同步给我吧！
        zkClient.subscribeChildChanges(SERVICE_PATH, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("感知到节点发生变化，重新保存到内存");
                serviceList=list;
            }
        });
    }

    public void consume(){
        Random rdm=new Random();
        if(!serviceList.isEmpty()){
            int rd=rdm.nextInt(serviceList.size());
            System.out.println("调用服务："+serviceList.get(rd)+"");
        }else{
            System.out.println("当前没有可用的服务");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Consumer consumer=new Consumer();
        consumer.init();
        for(int i=0;i<100;i++) {
            consumer.consume();
            TimeUnit.SECONDS.sleep(2);
        }
        System.in.read();
    }

}
