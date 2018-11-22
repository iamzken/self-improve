package com.gupao.course.demo;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TCPUnicastClient {

    public static void main(String[] args) {
        try {
            MulticastSocket clientSocket=new MulticastSocket(8888); //多播客户端监听相应的端口
            //客户端将自己加入到指定的多播组中，这样就能收到来自这个组的消息
            InetAddress group=InetAddress.getByName("224.5.6.7");
            clientSocket.joinGroup(group);

            byte[] buf=new byte[256];
            while(true){
                DatagramPacket msgPacket=new DatagramPacket(buf,buf.length);
                clientSocket.receive(msgPacket);

                String msg=new String(msgPacket.getData());
                System.out.println("received msg:"+msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
