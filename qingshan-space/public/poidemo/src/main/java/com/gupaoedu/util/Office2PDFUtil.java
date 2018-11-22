package com.gupaoedu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 咕泡学院，只为更好的你
 */
public class Office2PDFUtil {

	// 获取openOffice的安装路径
	private static final String OPENOFFICE_PATH = "C:/Program Files (x86)/OpenOffice 4/program/soffice.exe";
	// 获取openOffice服务的地址
	private static final String OPENOFFICE_SERVER_HOST = "127.0.0.1";
	// 获取openOffice服务的端口
	private static final String OPENOFFICE_SERVER_PORT = "8100";

	/**
	 * 将office文档转换为PDF文档
	 *
	 * @param sourceFile
	 *            源文件路径，绝对路径
	 * @param destFile
	 *            输出文件路径，绝对路径
	 * @return 操作成功与否的提示信息<br>
	 *         如果返回-2表示连接openOffice服务失败；返回-1表示找不到源文件；返回0表示转换失败；返回1表示转换成功
	 * @throws Exception
	 */
	public static void transfrom(String sourceFile, String destFile) throws Exception {
		Process pro = null;
		OpenOfficeConnection conn = null;
		try {
			File inputFile = new File(sourceFile);
			// 如果目标路径不存在，则创建该路径
			File outputFile = new File(destFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}
			// 启动openOffice服务的命令
			String command = OPENOFFICE_PATH + " -headless " + "-accept=\"socket,host=" + OPENOFFICE_SERVER_HOST
					+ ",port=" + OPENOFFICE_SERVER_PORT + ";urp;\"";
			pro = Runtime.getRuntime().exec(command);
			// 连接openOffice服务
			conn = new SocketOpenOfficeConnection(OPENOFFICE_SERVER_HOST, Integer.parseInt(OPENOFFICE_SERVER_PORT));
			conn.connect();
			// 转换文档
			DocumentConverter converter = new OpenOfficeDocumentConverter(conn);
			converter.convert(inputFile, outputFile);
		} catch (FileNotFoundException e) {
			throw new Exception("将office文档转换为PDF文档中，找不到源文件");
		} catch (ConnectException e) {
			throw new Exception("将office文档转换为PDF文档中，连接openOffice服务失败");
		} catch (IOException e) {
			throw new Exception("将office文档转换为PDF文档失败，IO异常，openoffice服务未启动");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("将office文档转换为PDF文档失败");
		} finally {
			if (conn != null) {
				// 关闭连接
				conn.disconnect();
			}
			if (pro != null) {
				// 关闭openOffice服务的进程
				pro.destroy();
			}
		}
	}
}
