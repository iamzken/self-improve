package lession2;

import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 *
 *
 * 服务端   商品模块
 */
public class ShopService {

    private String serviceName="ShopService";
    private String PATH="/configcenter";

    public void init(){
        ZkClient zkClient=new ZkClient("192.168.11.147:2181");

        if(!zkClient.exists(PATH)){
            zkClient.createPersistent(PATH);
        }

        if(!zkClient.exists(PATH+"/"+serviceName)){
            zkClient.createPersistent(PATH+"/"+serviceName);
        }

        zkClient.createEphemeral(PATH+"/"+serviceName+"/192.168.1.11:8080");
        System.out.println("提供服务节点ShopService:"+PATH+"/"+serviceName+"/192.168.1.11:8080");
    }

    public static void main(String[] args) throws IOException {
        ShopService shopService=new ShopService();
        shopService.init();

        System.in.read();
    }
}
