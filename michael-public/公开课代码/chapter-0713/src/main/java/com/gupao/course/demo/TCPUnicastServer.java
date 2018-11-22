package com.gupao.course.demo;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TCPUnicastServer {

    public static void main(String[] args) {
        try {
            //指定组播要发送的组[	组播地址段: 224.0.0.0 - 239.255.255.255]
            InetAddress group=InetAddress.getByName("224.5.6.7");

            MulticastSocket socket=new MulticastSocket();
            for(int i=0;i<10;i++){
                String data="hello mic";
                byte[] bytes=data.getBytes();
                //将数据发送到对应组的对应端口
                socket.send(new DatagramPacket(bytes,bytes.length,group,8888));
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
