package com.gupaoedu.protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class UdpServerDemo {
    public static void main(String[] args) throws IOException {
        //创建服务, 并且接收一个数据包
        DatagramSocket datagramSocket=new DatagramSocket(8080);
        byte[] receiveData=new byte[1024];
        DatagramPacket receivePacket=
                new DatagramPacket(receiveData,receiveData.length);
        datagramSocket.receive(receivePacket);;

        System.out.println(new String
                (receiveData,0,receivePacket.getLength()));


    }
}
