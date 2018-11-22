package com.bio.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

	private Socket socket;
	
	public ClientHandler(Socket socket){
		this.socket=socket;
	}
	
	private  static  BufferedReader in=null;
	private static PrintWriter out =null;
	@Override
	public void run() {

	        try {
	        	
	                      in=new BufferedReader( new InputStreamReader(socket.getInputStream()))  ;
	                	  String  retMes=in.readLine();
	                	  System.out.println(retMes+"\n");
	           }catch(Exception e){
	             e.printStackTrace();
	           }finally{
	        	   try {
					in.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	   
	           }
	           }

}
