package com.gupaoedu.vip.orm.demo.dao;

import com.gupaoedu.vip.orm.demo.model.User;
import com.gupaoedu.vip.orm.framework.BaseDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by Tom on 2018/5/12.
 */
@Repository
public class UserDao extends BaseDaoSupport<User,Integer> {

    @Override
    protected String getPKColumn() {return "id";}

    @Resource(name="dynamicDataSource")
    protected void setDataSource(DataSource dataSource) {

    }
}
