package com.gupaoedu.mybatis.my;

import com.gupaoedu.mybatis.beans.Test;

public interface TestMapper {
    Test selectByPrimaryKey(Integer userId);
}