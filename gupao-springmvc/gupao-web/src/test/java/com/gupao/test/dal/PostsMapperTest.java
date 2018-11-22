package com.gupao.test.dal;

import com.gupao.dal.dao.PostsMapper;
import com.gupao.test.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by James
 * on 16/8/16.
 * Description:
 */
public class PostsMapperTest extends BaseTest {
    private final static Logger log = LoggerFactory.getLogger(PostsMapperTest.class);
    @Autowired
    private PostsMapper mapper;

//    @Test
//    public void select() {//嵌套结果
//        List<Posts> resultMap = mapper.selectByBlogId(1);
//        System.out.println(resultMap);
//    }

}
