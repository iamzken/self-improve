package com.gupaoedu.mybatis.gp.config.mappers;

import com.gupaoedu.mybatis.beans.Test;

public interface TestMapper { //com.gupaoedu.mybatis.gp.config.mappers.TestMapper
    Test selectByPrimaryKey(Integer userId);
}