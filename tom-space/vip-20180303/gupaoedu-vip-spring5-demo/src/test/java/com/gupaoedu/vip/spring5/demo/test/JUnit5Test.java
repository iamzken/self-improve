package com.gupaoedu.vip.spring5.demo.test;


import com.gupaoedu.vip.spring5.demo.model.Member;
import com.gupaoedu.vip.spring5.demo.service.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;


//@ContextConfiguration(locations = {"classpath:application-context.xml"})
//@ExtendWith(SpringExtension.class)
public class JUnit5Test {

//    @Autowired MemberService memberService;

    /**
     * @BeforeEach 相当于 JUnit 4 @Before
     * @BeforeAll 相当于 JUnit 4 @BeforeClass
     * @AfterEach 相当于 JUnit 4 @After
     * @AfterAll 相当于 JUnit 4  @AfterClass
     */
//    @BeforeEach
    public void before(){
        System.out.println("准备好数据");
    }

    @Test
    @Disabled
    public void test(){
        System.out.println("执行业务逻辑");
    }

//    @AfterEach
    public void after(){
        System.out.println("后置处理");
    }



    //支持参数注入
    @RepeatedTest(value = 100)
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    public void foreach(int i){
        System.out.println("===========" + i);
    }


}
