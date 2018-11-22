package com.gupaoedu.mybatis.demo.jdbc;

import com.gupaoedu.mybatis.beans.Test;

import java.sql.*;

import static java.sql.Types.INTEGER;

/**
 * Created by James on 2017/3/26.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class JDBCDemo {
    public static void main(String[] args) throws SQLException {
//        System.out.println(get(1));
        System.out.println(insert(new Test(null, 66, "jdbc insert")));
    }

    public static int insert(Test test) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123456");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO test VALUES (?,?,?)");
            if (null != test.getId()) {
                preparedStatement.setInt(1, test.getId());
            } else {
                preparedStatement.setNull(1, INTEGER);
            }
            preparedStatement.setInt(2, test.getNums());
            preparedStatement.setString(3, test.getName());
            connection.commit();
            return preparedStatement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static Test get(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Test test = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123456");
            preparedStatement = connection.prepareStatement("SELECT * FROM test WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                test = new Test();
                test.setId(rs.getInt(1));
                test.setNums(rs.getInt(2));
                test.setName(rs.getString(3));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return test;
    }
}
