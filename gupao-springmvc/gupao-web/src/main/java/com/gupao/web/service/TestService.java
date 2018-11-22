package com.gupao.web.service;

import com.gupao.dal.dao.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by
 * on 17/3/6.
 * Description:
 */
@Service
@Scope("prototype")
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public void test(){
        System.out.println(testMapper.selectByPrimaryKey(1));
    }
}
