package lession2;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Consumer {

    List<String> serverList=new ArrayList<>();
    private String serviceName="ShopService";

    private void init(){
        ZkClient zkClient=new ZkClient("192.168.11.147:2181");
        String SERVER_PATH="/configcenter/"+serviceName;
        if(zkClient.exists(SERVER_PATH)){
            serverList=zkClient.getChildren(SERVER_PATH);
        }else{
            throw  new RuntimeException("server not found");
        }
        //注册监听事件
        zkClient.subscribeChildChanges(SERVER_PATH, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("服务端节点发生变化："+s);
                serverList=list;
            }
        });
    }

    public void consumer(){
        Random random=new Random();
        System.out.println("调用:"+serverList.get(random.nextInt(serverList.size()))+"提供的服务");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Consumer consumer=new Consumer();
        consumer.init();
        for(int i=0;i<20;i++){
            consumer.consumer();
            TimeUnit.SECONDS.sleep(4);
        }

        System.in.read();
    }
}
