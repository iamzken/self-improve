package com.creditease.cf.bpm.service;

import java.util.Scanner;

/**
 * @Auther: 337685789@qq.com
 * @Date: 2018/11/6 09:55
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws Exception {
        //运行服务器
        ServerDemo.start();
        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);
        //运行客户端
        ClientDemo.start();
        while(ClientDemo.sendMsg(new Scanner(System.in).nextLine()));

    }

}
