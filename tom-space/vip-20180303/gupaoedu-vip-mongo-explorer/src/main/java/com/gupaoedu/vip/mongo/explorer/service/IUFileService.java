package com.gupaoedu.vip.mongo.explorer.service;

import javax.core.common.ResultMsg;
import java.io.File;
import java.io.OutputStream;
import java.util.Map;

/**
 * 文件操作接口
 * @author Tanyongde
 *
 */
public interface IUFileService {

	/**
	 * 获取临时存储目录
	 * @return
	 */
	ResultMsg<?> getTempPath();

	
	/**
	 * 获取文件列表
	 * @param uname
	 * @param path
	 * @return
	 */
	ResultMsg<?> list(String group, String uname, String path);

	public ResultMsg<?> createFolder(String group, String uname,String path);

	/**
	 * 下载
	 * @return
	 */
	ResultMsg<?> download(String uname,String id);
	

	/**
	 * 上传文件
	 * @param uname
	 * @param path
	 * @return
	 */
	ResultMsg<?> upload(String group, String uname, String path, Map<String, File> files);
}
