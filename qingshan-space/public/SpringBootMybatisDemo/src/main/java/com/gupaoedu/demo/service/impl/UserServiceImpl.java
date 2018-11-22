package com.gupaoedu.demo.service.impl;

import com.gupaoedu.demo.entity.User;
import com.gupaoedu.demo.mapper.UserMapper;
import com.gupaoedu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> getUserList(String name , int page, int limit) {

        return userMapper.getUserList(name,page,limit);
    }


    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int delete(Integer id) {
        return userMapper.delete(id);
    }

    @Override
    public int getUserCount() {
        return userMapper.getUserCount();

    }
}