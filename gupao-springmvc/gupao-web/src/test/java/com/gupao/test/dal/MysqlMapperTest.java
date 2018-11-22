package com.gupao.test.dal;

import com.gupao.dal.dao.TcUser;
import com.gupao.dal.dao.TcUserMapper;
import com.gupao.dal.dao.UserGroup;
import com.gupao.dal.dao.UserGroupMapper;
import com.gupao.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * Created by James
 * on 16/8/16.
 * Description:
 */
public class MysqlMapperTest extends BaseTest {
    @Autowired
    private TcUserMapper mapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Test
    public void insert() {
        for (int i = 200003; i < 300000; i++) {
            TcUser user = new TcUser();
            user.setNickName("test" + i);
            user.setCreatedTime(new Date());
            user.setBirthday(null);
            user.setUserSign(UUID.randomUUID().toString());
            mapper.insert(user);
        }
        System.out.println("done");
    }

    @Test
    public void insertUserGroup() {
        for (int i = 100000; i < 200000; i++) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUserId(i);
            if (i % 2 == 0) {
                userGroup.setGroupId(2);
                userGroup.setGroupName("group" + 2);
            } else if (i % 3 == 0) {
                userGroup.setGroupId(3);
                userGroup.setGroupName("group" + 3);
            } else if (i % 5 == 0) {
                userGroup.setGroupId(5);
                userGroup.setGroupName("group" + 5);
            } else {
                userGroup.setGroupId(8);
                userGroup.setGroupName("group" + 8);
            }
            userGroup.setCreatedTime(new Date());
            userGroupMapper.insert(userGroup);
        }
        System.out.println("done");
    }

    @Test
    public void insertUserGroupUser() {
        for (int i = 3; i < 100; i++) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUserId(2);
            userGroup.setCreatedTime(new Date());
            userGroup.setGroupName("group" + i);
            userGroupMapper.insert(userGroup);
        }
        System.out.println("done");
    }
}
