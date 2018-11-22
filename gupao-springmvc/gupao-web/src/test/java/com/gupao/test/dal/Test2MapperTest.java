package com.gupao.test.dal;

import com.gupao.dal.dao.Test2;
import com.gupao.dal.dao.Test2Mapper;
import com.gupao.dal.enums.TestEnum;
import com.gupao.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * Created by James
 * on 16/8/16.
 * Description:
 */
public class Test2MapperTest extends BaseTest {
    private final static Logger log = LoggerFactory.getLogger(Test2MapperTest.class);
    @Autowired
    private Test2Mapper mapper;

    @Test
    @Rollback(false)
    public void insert() {
        Test2 test = new Test2();
        test.setTestName("name");
        test.setTestVr("vr ---");
        log.info("{}", mapper.insert(test));
    }

}
