package com.gupaoedu.mybatis.my;

import com.gupaoedu.mybatis.beans.Test;

import java.lang.reflect.Proxy;

public class BootStrap {
    public static void start(){
        MySqlSession sqlSession = new MySqlSession();
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        Test test = testMapper.selectByPrimaryKey(1);
        System.out.println(test);
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        start();
    }
}