package com.gupaoedu.rpc.registry;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.gupaoedu.rpc.core.msg.InvokerMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RegistryHandler  extends ChannelInboundHandlerAdapter {
	
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<String,Object>();
    
    private List<String> classCache = new ArrayList<String>();
    
    public RegistryHandler(){
    	scannerClass("com.gupaoedu.rpc.provider");
    	doRegister();
    }
    
    
    @Override    
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	Object result = new Object();
        InvokerMsg request = (InvokerMsg)msg;  
        
        if(registryMap.containsKey(request.getClassName())){ 
        	Object clazz = registryMap.get(request.getClassName());
        	Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParames());    
        	result = method.invoke(clazz, request.getValues());   
        }
        ctx.write(result);  
        ctx.flush();    
        ctx.close();  
    }
    
    @Override    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {    
         cause.printStackTrace();    
         ctx.close();    
    }
    

	private void scannerClass(String packageName){
		URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
		File dir = new File(url.getFile());
		for (File file : dir.listFiles()) {
			//如果是一个文件夹，继续递归
			if(file.isDirectory()){
				scannerClass(packageName + "." + file.getName());
			}else{
				classCache.add(packageName + "." + file.getName().replace(".class", "").trim());
			}
		}
	}

	
	private void doRegister(){
		if(classCache.size() == 0){ return; }
		
		for (String className : classCache) {
			try {
				Class<?> clazz = Class.forName(className);
				
				Class<?> interfaces = clazz.getInterfaces()[0];
				
				registryMap.put(interfaces.getName(), clazz.newInstance()); 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
  
}
