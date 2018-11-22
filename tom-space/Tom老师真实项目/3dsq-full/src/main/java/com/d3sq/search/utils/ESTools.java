package com.d3sq.search.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.d3sq.common.config.CustomConfig;

public class ESTools {
	
	 private static Settings settings = Settings  
	            .settingsBuilder()  
	            .put("cluster.name",CustomConfig.getValue("es.cluster.name"))  
				.put("client.transport.sniff",CustomConfig.getBoolean("es.client.transport.sniff"))  
				.build(); 
	 
	//创建私有对象  
	 private static TransportClient client;
  

	static {  
	    try {  
	        client = TransportClient.builder().settings(settings).build()  
	                .addTransportAddress(new InetSocketTransportAddress(
	                		InetAddress.getByName(CustomConfig.getString("es.host.ip")), 
	                		CustomConfig.getInt("es.host.port")));  
	    } catch (UnknownHostException e) {  
	        e.printStackTrace();  
	    }  
	} 
		
    // 取得实例
    public static synchronized TransportClient getClient() {
        return client;
    }
    

    public static  boolean isExistsType(String indexName){
		Client client = ESTools.getClient();
		ClusterStateResponse response = client .admin().cluster()    
				.prepareState()    
				.execute().actionGet();  
		//获取所有索引  
		String[] indexs=response.getState().getMetaData().getConcreteAllIndices();  
		for (String index : indexs) {  
			if(!index.equals(indexName)){
				continue;
			}
			return true;
		}  
		return false;
	}
}
