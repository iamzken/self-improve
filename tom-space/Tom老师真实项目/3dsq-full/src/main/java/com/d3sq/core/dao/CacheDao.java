package com.d3sq.core.dao;

import javax.core.common.encrypt.MD5;
import javax.core.common.redis.JsonCacheClient;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.SystemConstant;


/**
 * 操作缓存
 */
@Repository
public class CacheDao{
	
	private JsonCacheClient client = new JsonCacheClient(SystemConstant.REDIS_REF_HOSTS);
	
	/**
	 * 获取原始客户端
	 * @return
	 */
	public JsonCacheClient getClient(){
		return client;
	}
	
	/**
	 * 生成缓存加密key
	 * @param key key前缀
	 * @param id 对象ID
	 * @return
	 */
	public String genMD5Key(String key,String id){
		return MD5.calcMD5(key + "_" + id);
	}
	
	/**
	 * 从缓存中获取一条数据
	 * @param key 存入缓存时使用的key值
	 * @return
	 */
	public Object get(String key){
		return client.get(key);
	}
	
	/**
	 * 判断一个对象是否存在
	 * @param key 存入缓存时使用的key值
	 * @return
	 */
	public boolean exists(String key){
		return client.exists(key);
	}
	
	/**
	 * 往缓存中插入一条数据,如果key值已存在，则覆盖原来的值
	 * @param key 存入缓存的key值
	 * @param value 存入的内容
	 * @param expire 过期时间(秒)
	 */
	public boolean insert(String key,Object value,int expire){
		return client.set(key, value, expire);
	}
	
	/**
	 * 删除缓存中的一条数据
	 * @param key 存入缓存时的key值
	 */
	public void delete(String key){
		client.del(key);
	}
}
