package com.gupaoedu.mapper;

import com.gupaoedu.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {

    User getUserById(Integer id);

    public List<User> getUserList(String name, int page, int limit);

    public List<User> getAllUser();

    public int add(User user);

    public int update(User user);

    public int delete(Integer id);

    int getUserCount();

    List<User> getUserByAccount(String account);
}