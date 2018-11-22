package com.gupaoedu.refactoring.now;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcTemplate {
	
	private JdbcTemplate(){};
	
	//修改数据通用模板
	public static void update(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			// 设置值
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(null, ps, conn);
		}
	}
	
	/*
	//查询统一模板
	public static List<Student> query(String sql,Object...params){
		List<Student> list=new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
			conn=JDBCUtil.getConnection();
			ps=conn.prepareStatement(sql);
			//设置值
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				Student stu = new Student(id, name, age);
				list.add(stu);
			}
			// 5. 释放资源
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return list;
	}
	*/
	
	/*
	public static List<Student> query(String sql,IRowMapper rsh, Object...params){
		List<Student> list=new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
			conn=JDBCUtil.getConnection();
			ps=conn.prepareStatement(sql);
			//设置值
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			rs = ps.executeQuery();
			return rsh.rowMapper(rs);
			// 5. 释放资源
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return list;
	}
	*/
	
	public static <T> T query(String sql,IRowMapper<T> rsh, Object...params){
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			//设置值
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			rs = ps.executeQuery();
			return rsh.mapping(rs);
			// 5. 释放资源
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, ps, conn);
		}
		return null;
	}
}
