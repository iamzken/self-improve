package com.gupaoedu.dubbo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        ClassPathXmlApplicationContext context=new
                ClassPathXmlApplicationContext
                ("dubbo-client.xml");

      /*  Protocol protocol=ExtensionLoader.getExtensionLoader(Protocol.class).
                getExtension("defineProtocol");
        System.out.println(protocol.getDefaultPort());*/

       /*     //得到IGpHello的远程代理对象
            IGpHello iGpHello = (IGpHello) context.getBean("gpHelloService");

            System.out.println(iGpHello.sayHello("Mic"));
            Thread.sleep(4000);*/

        System.in.read();
    }
}
