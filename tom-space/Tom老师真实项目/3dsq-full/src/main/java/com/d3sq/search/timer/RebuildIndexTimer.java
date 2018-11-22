package com.d3sq.search.timer;

import javax.core.common.ResultMsg;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.d3sq.common.constants.SystemConstant;
import com.d3sq.search.service.ICreateIndexService;

/**
 * 定时刷新索引
 *
 */
public class RebuildIndexTimer {
	
	private Logger LOG = Logger.getLogger(this.getClass());
	
	
	@Autowired ICreateIndexService createIndexService;
	
	//执行任务
	public synchronized void execute(){
		createHomeIndex();
		createCommodityIndex();
		createServiceIndex();
		createShopIndex();
	}
	
	
	private void createHomeIndex(){
		LOG.info("开始重建首页索引 ...");
		long start = System.currentTimeMillis();
		try{
			//首页索引
			ResultMsg<?> result = createIndexService.createHomeIndex();
			if(result.getStatus() == SystemConstant.RESULT_STATUS_SUCCESS){
				LOG.info("重建首页索引成功!");
			}else{
				LOG.info("重建索引失败，下次重试！");
			}
		}catch(Exception e){
			LOG.info("重建首页索引时出现异常" + e.getMessage());
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		LOG.info("重建首页索引结束，耗时" + (end - start) + "ms.");
	}
	
	private void createCommodityIndex(){
		LOG.info("开始重建商品索引 ...");
		long start = System.currentTimeMillis();
		try{
			//首页索引
			ResultMsg<?> result = createIndexService.createCommodityIndex();
			if(result.getStatus() == SystemConstant.RESULT_STATUS_SUCCESS){
				LOG.info("重建商品索引成功!");
			}else{
				LOG.info("重建商品索引失败，下次重试！");
			}
		}catch(Exception e){
			LOG.info("重建商品索引时出现异常" + e.getMessage());
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		LOG.info("重建商品索引结束，耗时" + (end - start) + "ms.");
	}
	
	private void createServiceIndex(){
		LOG.info("开始重建服务索引 ...");
		long start = System.currentTimeMillis();
		try{
			//首页索引
			ResultMsg<?> result = createIndexService.createServiceIndex();
			if(result.getStatus() == SystemConstant.RESULT_STATUS_SUCCESS){
				LOG.info("重建服务索引成功!");
			}else{
				LOG.info("重建服务索引失败，下次重试！");
			}
		}catch(Exception e){
			LOG.info("重建服务索引时出现异常" + e.getMessage());
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		LOG.info("重建服务索引结束，耗时" + (end - start) + "ms.");
	}
	
	
	private void createShopIndex(){
		LOG.info("开始重建店铺索引 ...");
		long start = System.currentTimeMillis();
		try{
			//首页索引
			ResultMsg<?> result = createIndexService.createShopIndex();
			if(result.getStatus() == SystemConstant.RESULT_STATUS_SUCCESS){
				LOG.info("重建店铺索引成功!");
			}else{
				LOG.info("重建店铺索引失败，下次重试！");
			}
		}catch(Exception e){
			LOG.info("重建店铺索引时出现异常" + e.getMessage());
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		LOG.info("重建店铺索引结束，耗时" + (end - start) + "ms.");
	}
}
