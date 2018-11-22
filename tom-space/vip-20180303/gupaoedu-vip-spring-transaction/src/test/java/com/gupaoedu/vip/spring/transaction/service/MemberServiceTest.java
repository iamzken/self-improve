package com.gupaoedu.vip.spring.transaction.service;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.vip.spring.transaction.entity.Member;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/5/2.
 */
@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    @Ignore
    public void queryAll(){
        try{
            List<Member> list = memberService.queryAll();
            System.out.println(JSON.toJSONString(list,true));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void testRemove(){
        try{
            boolean r = memberService.remove(1L);
            System.out.println(r);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void testLogin(){
        try{
            memberService.login(15L, "tom");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAdd(){
        try{
            Member member = new Member("Tom","Hunan Changsha",18);
            memberService.add(member);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
