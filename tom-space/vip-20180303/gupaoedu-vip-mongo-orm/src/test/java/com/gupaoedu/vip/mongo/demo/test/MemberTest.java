package com.gupaoedu.vip.mongo.demo.test;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.vip.mongo.demo.dao.MemberDao;
import com.gupaoedu.vip.mongo.demo.entity.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.core.common.mongo.QueryRule;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 2018/9/5.
 */
@ContextConfiguration(locations={"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberTest {


    @Autowired
    private  MemberDao memberDao;

    @Test
    public void testInsertAll(){

        List<Member> data = new ArrayList<Member>();
        data.add(new Member("tom","123456",1,18));
        memberDao.insertAll(data);

    }


    @Test
    public void testSelect(){
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("nickname","tom");
        List<Member> memberList = memberDao.select(queryRule);

        System.out.println(JSON.toJSONString(memberList,true));

    }

}
