package com.d3sq.test.search.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.core.common.encrypt.MD5;

/*功能:		duanxin.cm JAVA HTTP接口 发送短信
*修改日期:	2014-03-19
*说明:	http://api.duanxin.cm/?ac=send&username=账号&password=MD5位32密码&phone=号码&content=内容
	*状态:
		*	100 发送成功
		*	101 验证失败
		*	102 短信不足
		*	103 操作失败
		*	104 非法字符
		*	105 内容过多
		*	106 号码过多
		*	107 频率过快
		*	108 号码内容空
		*	109 账号冻结
		*	110 禁止频繁单条发送
		*	111 系统暂定发送
		*	112 号码不正确
		*	120 系统升级
		*/
public class Msg {



		/**
		 * @param args
		 * @throws IOException
		 */
		public static void main(String[] args) throws IOException {
			//发送内容
			String content = "duanxin.cm JAVA示例测试"; 
			
			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer("http://api.duanxin.cm/?");

			// 向StringBuffer追加用户名
			sb.append("action=send&username=tom");
			
			String pass = MD5.calcMD5("123456654321");

			// 向StringBuffer追加密码（密码采用MD5 32位 小写）
			sb.append("&password=" + pass);

			// 向StringBuffer追加手机号码
			sb.append("&phone=15911125523");

			// 向StringBuffer追加消息内容转URL标准码
			sb.append("&content="+URLEncoder.encode(content));

			String u = "http://180.97.163.89:8012/OpenPlatform/OpenApi?action=sendOnce&ac=1001@800000810001&authkey=87B01469201D6557C2685137EB3B5DEC&cgid=80&c=%E6%B5%8B%E8%AF%95&m=15911125523";
			
			// 创建url对象
			URL url = new URL(u);

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 设置url请求方式 'get' 或者 'post'
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// 返回发送结果
			String inputline = in.readLine();

			// 返回结果为'100' 发送成功
			System.out.println(inputline);

		}

}
