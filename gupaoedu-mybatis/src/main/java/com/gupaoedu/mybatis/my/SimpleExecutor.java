package com.gupaoedu.mybatis.my;

import com.gupaoedu.mybatis.beans.Test;

import java.sql.*;

public class SimpleExecutor implements Executor {

    public <E> E query(String sql, Object parameter) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(String.format(sql, Integer.parseInt(String.valueOf(parameter))));
            ResultSet rs = pstmt.executeQuery();
            Test test = new Test();
            while (rs.next()) {
                test.setId(rs.getInt(1));
                test.setName(rs.getString(2));
            }
            return (E) test;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Connection getConnection() throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/gp?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "123456";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}