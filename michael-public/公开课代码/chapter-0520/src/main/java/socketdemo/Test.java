package socketdemo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        SocketClient.send("我是发送信息");
        /*new Thread(()->{
           while(true){
               SocketClient.send("我是发送信息");
               try {
                   Thread.currentThread().sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }).start();*/
    }
}
