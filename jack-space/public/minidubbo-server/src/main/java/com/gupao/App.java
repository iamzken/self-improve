package com.gupao;

import com.gupao.api.IGpService;
import com.gupao.impl.GpServiceImpl;
import com.gupao.registry.IServiceRegistryCenter;
import com.gupao.registry.ZkServiceRegistryCenterImpl;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
//        System.out.println( "Hello World!" );
//        IGpService iGpService=new GpServiceImpl();
//        iGpService.hello("Jack");
        IServiceRegistryCenter iServiceRegistryCenter=new ZkServiceRegistryCenterImpl();
        iServiceRegistryCenter.register("com.gupao.test","127.0.0.1:8080");
        System.in.read();
    }
}
