package com.bio.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerHandler implements  Runnable {

    private Socket socket;

    public ServerHandler(Socket socket){
        this.socket=socket;
    }

    public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	BufferedReader reader=null;
    public void run() {
    	 BufferedReader in = null;  
         try {  
			        	 
			        	        
        	                       in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
			                       while(true){
			                      	 String msg = in.readLine(); 
			                      	 for(Socket socket:Server.list){
			                      		 if(this.socket.equals(socket)){
			                      			 continue;
			                      		 }
			                      		PrintWriter out = new PrintWriter(socket.getOutputStream());  
			                      		System.out.println("服务器收到信息"+msg);
			                      		out.println( msg+"\n");  
			                      		out.flush();  
			                      	 }
			                       } 
			        		 
			        	  
//                 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
//                out = new PrintWriter(socket.getOutputStream());  
//                 while(true){
//                	 String msg = in.readLine();  
//                	 System.out.println("服务器收到信息"+msg);
//                	 out.println("Server received " + msg+"\n");  
//                	 out.flush();  
//                 }
         } catch(IOException ex) {  
             ex.printStackTrace();  
         } finally {  
             try {  
                 in.close();  
             } catch (Exception e) {}  
             try {  
            	 socket.close();  
             } catch (Exception e) {}  
         }  
     }  
}
