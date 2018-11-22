package com.gupaoedu.service;


import com.gupaoedu.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getUserList(String name, int page, int limit);

    public List<User> getAllUser();

    User getUserById(Integer id);

    public List<User> getUserByAccount(String account);

    public int add(User user);

    public int update(User user);

    public int delete(Integer id);

    int getUserCount();
}
