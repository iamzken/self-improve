package com.d3sq.test.passport.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.core.common.ResultMsg;
import javax.core.common.encrypt.MD5;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.passport.service.impl.MemberService;


@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberServiceTest {

	
	@Autowired MemberService memberService;
	
	@Test
	@Ignore
	public void chkSms(){
		String local = "zh_CN";
		String ver = MobileConstant.VER_SHOP;
		String loginName = "13800138000";
		String zone = "86";
		String smsCode = "7237";
		String enc = MobileConstant.genEnc(loginName+smsCode);
		ResultMsg<?> r = memberService.chkSmsCode(local, ver, loginName, zone, smsCode, enc);
		System.out.println(JSON.toJSONString(r));
	}
	
	
	
	
	
	@Test
	@Ignore
	public  void processQQ(){
		File f = new File("/WORKSPACES/3DSQ_WORKSPACE/3dsq-full/src/test/java/com/d3sq/test/passport/service/test.txt");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			StringBuffer info = new StringBuffer();
			String s = null;
			while((s = br.readLine()) != null){
				info.append(s);
			}
			System.out.println(info.toString().replaceAll(" ", "").replaceAll("\\\\/", "/"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(null != br){
					br.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	@Ignore
	public void testGenPass(){
		String loginName = "13800138000";
		String loginPass = "123456";
		
		System.out.println(MD5.calcMD5(loginName + loginPass));
	}
	
	
	@Test
	@Ignore
	public void testEnc(){
		String loginName = "13800138000";
		String smsCode = "8901";
		String enc = MobileConstant.genEnc(loginName+smsCode);
		System.out.println(enc);
	}
	
	
	@Test
	@Ignore
	public  void  testLogin(){
		
		String local = "zh_CN";
		String domain = "3dsq.me";
		
		Integer account = FieldConstant.MEMBER_MTYPE_TEL;
		String loginName = "13800138000";
		String loginPass = "888888";
		String enc = MobileConstant.genEnc(account+loginName+loginPass);
		
		ResultMsg<?> result = memberService.login(local, domain, loginName, loginPass, "", null, 1,null,"127.0.0.1", enc);
		
		System.out.println(JSON.toJSONString(result));
	}
	
	
	@Test
	@Ignore
	public void testRegist(){
		
		String local = "zh_CN";
		Integer mtype = FieldConstant.MEMBER_MTYPE_TEL;
		String loginName = "13800138000";
		String loginPass = "123456";
		String email = "tyd_java@163.com";
		String smsCode = "5609";
		String enc = MobileConstant.genEnc(loginName+smsCode);
		
		ResultMsg<?> result = memberService.addForRegist(local,null, mtype,loginName, loginPass, email, smsCode, enc);
		
		System.out.println(JSON.toJSONString(result));
		
	}
	
	
	/**
	 * 批量注册
	 */
	@Test
	@Ignore
	public void testRegistForBatch(){
		try{
			
			Long tel = 18800000000L;
			for(int i = 0; i < 1000; i ++){
				String local = "zh_CN";
				Integer mtype = FieldConstant.MEMBER_MTYPE_TEL;
				String loginName = "" + (tel + i);
				String loginPass = "123456";
				String email = "tom@163.com";
				String smsCode = "5609";
				String enc = MobileConstant.genEnc(loginName+smsCode);
				
				ResultMsg<?> result = memberService.addForRegist(local,null, mtype,loginName, loginPass, email, smsCode, enc);
				
				System.out.println(JSON.toJSONString(result));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
