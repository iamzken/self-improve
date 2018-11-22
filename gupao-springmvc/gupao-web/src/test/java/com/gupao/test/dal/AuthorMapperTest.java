package com.gupao.test.dal;

import com.gupao.dal.dao.AuthorMapper;
import com.gupao.test.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by James
 * on 16/8/16.
 * Description:
 */
public class AuthorMapperTest extends BaseTest {
    private final static Logger log = LoggerFactory.getLogger(AuthorMapperTest.class);
    @Autowired
    private AuthorMapper mapper;

//    @Test
//    public void select() {//嵌套结果
//        AuthorResultMap resultMap = mapper.selectAuthorResultMap(1);
//        System.out.println(resultMap);
//    }

//    @Test
//    public void 嵌套查询() {//嵌套查询
//        AuthorResultMap resultMap = mapper.selectAuthorBlogs(1);
//        System.out.println(resultMap);
//    }

}
