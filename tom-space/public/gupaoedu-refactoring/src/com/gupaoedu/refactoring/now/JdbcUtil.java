package com.gupaoedu.refactoring.now;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//工具类
public class JdbcUtil {
	
	private JdbcUtil() { }

	private static Properties p;

//	static {
//	1. 加载注册驱动
//		try {			
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//public static Connection getConnection() {
//	try {
//		// 2. 获取数据库连接
//		return DriverManager.getConnection("jdbc:mysql:///jdbcdemo", "root", "root");
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return null;
//}
	
	static {
		// 1. 加载注册驱动
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = loader.getResourceAsStream("db.properties");
			p = new Properties();
			p.load(inputStream);
			Class.forName(p.getProperty("driverClassName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			// 2. 获取数据库连接
			return DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
					p.getProperty("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//释放资源
	public static void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
