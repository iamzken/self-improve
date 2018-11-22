package com.d3sq.passport.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.core.common.ResultMsg;
import javax.core.common.encrypt.MD5;
import javax.core.common.utils.RegexUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.config.CustomConfig;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.SystemUtil;
import com.d3sq.core.dao.CacheDao;
import com.d3sq.core.dao.RequiredDao;
import com.d3sq.core.service.ILangService;
import com.d3sq.model.entity.Member;
import com.d3sq.passport.dao.MemberDao;
import com.d3sq.passport.service.IMemberService;

@Service("memberService")
public class MemberService implements IMemberService {
	
	@Autowired private ILangService langService;
	
	@Autowired private MemberDao memberDao;
	@Autowired private RequiredDao requiredDao;
	@Autowired private CacheDao cacheDao;

	@Override
	public ResultMsg<?> getByLoginName(String local, String loginName,String enc) {
		//验证参数
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(loginName)){
				error.put("loginName", "登录账号未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		if(!RegexUtils.test(loginName, RegexUtils.mobile)){
			JSONObject error = new JSONObject();
			error.put("loginName", "手机号码格式错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc(loginName).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		//2.判断手机号是否已经注册
		Member member = memberDao.selectByLoginName(loginName);
		if(null == member){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "可以注册的账号");
		}
		
		if(!FieldConstant.MEMBER_MTYPE_TEL.equals(member.getMtype())){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "此账户不是一个手机号码");
		}
		
		if(FieldConstant.MEMBER_LOGINPASS_EMPTY.equals(member.getLoginPass())){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "可以注册的账号");
		}
		
		
		JSONObject result = new JSONObject();
		result.put("loginName", member.getLoginName());
		result.put("mtype",member.getMtype());
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "账号已被注册",result);
		
	}

	@Override
	public ResultMsg<?> addForRegist(String local,String ver,Integer mtype, String loginName,String loginPass, String email,String smsCode, String enc) {
		//验证参数
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPass) || StringUtils.isEmpty(enc) || StringUtils.isEmpty("smsCode")){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(loginName)){
				error.put("loginName", "登录账号未填写");
			}
			if(StringUtils.isEmpty(loginPass)){
				error.put("loginPass", "密码未填写");
			}
			//如果是手机号码注册，则要检查是否填写短信验证码
			if(mtype == 1 && StringUtils.isEmpty(smsCode)){
				error.put("smsCode", "验证码未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}

			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		if(!RegexUtils.test(loginName, RegexUtils.mobile)){
			JSONObject error = new JSONObject();
			error.put("loginName", "手机号码格式错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		if(loginPass.length() < 6 || loginPass.length() > 24){
			JSONObject error = new JSONObject();
			error.put("loginPass", "密码必须为6-24位字符");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc(loginName+smsCode).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		
		//如果是用手机号注册，则要检查短信验证码是否正确
		if(mtype == FieldConstant.MEMBER_MTYPE_TEL){
			try{
				JSONObject smsRst = chkSMS(ver,"86",loginName,smsCode);
				if(smsRst.getInteger("status") != 200){
					JSONObject error = new JSONObject();
					error.put("smsCode", "短信验证码校验失败");
					return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, smsRst.getString("msg"),error);
				}
			}catch(Exception e){
				JSONObject error = new JSONObject();
				error.put("smsCode", "服务器内部异常，请重试");
				return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "校验短信验证码出现异常",error);
			}
		}
		
		
		Member member = memberDao.selectByLoginName(loginName);
		if(member != null){
			if(FieldConstant.MEMBER_LOGINPASS_EMPTY.equals(member.getLoginPass()) && 
			   FieldConstant.MEMBER_MTYPE_TEL.equals(member.getMtype())){
				
				member.setMtype(mtype);
				member.setLoginName(loginName);
				member.setTel(loginName);
				member.setLoginPass(MD5.calcMD5(loginName + loginPass));
				member.setCreateTime(System.currentTimeMillis());
				member.setState(SystemConstant.ENABLE);
				
				int count = memberDao.update(member);
				if(count > 0){
					member.setLoginPass(null);
					return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "注册成功",member);
				}else{
					return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "服务器异常,请重试");
				}
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "该用户已被注册，请不要重复注册");
		}
		
		member = new Member();
		member.setMtype(mtype);
		member.setLoginName(loginName);
		member.setTel(loginName);
		member.setLoginPass(MD5.calcMD5(loginName + loginPass));
		member.setCreateTime(System.currentTimeMillis());
		member.setState(SystemConstant.ENABLE);
		Long id = memberDao.insertAndReturnId(member);
		if(id >=0){
			member.setId(id);
			member.setLoginPass(null);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "注册成功",member);
		}else{
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "注册失败");
		}
		
	}
	
	
	@Override
	public ResultMsg<?> chkSmsCode(String local,String ver,String loginName, String zone, String smsCode, String enc) {
		//验证参数
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(smsCode) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(loginName)){
				error.put("loginName", "登录账号未填写");
			}
			if(StringUtils.isEmpty(smsCode)){
				error.put("smsCode", "验证码未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(loginName+smsCode).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
//		Member member = memberDao.selectByLoginName(loginName);
//		if(null == member){
//			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "此账户不存在");
//		}
//		if(!FieldConstant.MEMBER_MTYPE_TEL.equals(member.getMtype())){
//			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "此账户不存在");
//		}
		
		if(StringUtils.isEmpty(zone)){
			zone = "86";
		}
		try{
			JSONObject smsRst = chkSMS(ver,zone,loginName,smsCode);
			if(smsRst.getInteger("status") != 200){
				JSONObject error = new JSONObject();
				error.put("smsCode", "短信验证码校验失败");
				return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, smsRst.getString("msg"),error);
			}
		}catch(Exception e){
			JSONObject error = new JSONObject();
			error.put("smsCode", "服务器内部异常，请重试");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "服务器内部异常，请重试",error);
		}

		JSONObject token = new JSONObject();
		token.put("token", MD5.calcMD5(FieldConstant.MEMBER_MTYPE_TEL + loginName + smsCode));
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "校验成功",token);
	}
	
	
	
	
	/**
     * 发起https 请求
     * @param address
     * @param m
     * @return
     */
    private JSONObject chkSMS(String ver,String zone,String phone,String code){
    		String appkey = CustomConfig.getString("system.mobile.sms.appkey");
    		if(MobileConstant.VER_SHOP.equals(ver)){
    			appkey = CustomConfig.getString("system.mobile.sms.shop.appkey");
    		}
    		
        	String address = "https://webapi.sms.mob.com/sms/verify";
        	String params = "appkey=" + appkey +"&amp;phone=" + phone + "&amp;zone=" + zone + "&amp;code=" + code;
        	
        	JSONObject msg = new JSONObject();
        	
            HttpURLConnection conn = null;
            try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() { return null;}
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			}};
 
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
 
            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                 public boolean verify(String urlHostName, SSLSession session) {
                 return urlHostName.equals(session.getPeerHost());
                 }
            };
 
            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
 
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 
            URL url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// POST
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            if (params!=null) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes(Charset.forName("UTF-8")));
                out.flush();
                out.close();
            }
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            	StringBuffer result = new StringBuffer();
            	try{
            		String s;
            		while((s = br.readLine()) != null){
	            		result.append(s);
	            	}
        		}catch(Exception io){
        			io.printStackTrace();
        		}finally{
        			br.close();
        		}
                return JSONObject.parseObject(result.toString());
            } else {
            	msg.put("status", conn.getResponseCode());
            	msg.put("msg", conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return msg;
    }
    
    
    /**
     * 处理登录结果
     * @param member
     * @param remeber
     * @param domain
     * @param ip
     * @return
     */
    private ResultMsg<JSONObject> processLogin(Member member,String location,String domain,String ip){
    	Integer state = member.getState();
		if(FieldConstant.MEMBER_STATE_RESET.equals(state)){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_RESET, "您需要重新修改密码后才能正常操作");
		}
		Long loginTime = System.currentTimeMillis();
		
		JSONObject result =  langService.getAuthByMember(member.getId()).getData();
		
		result.getJSONObject("user").put("currLoginIp", ip);
		result.getJSONObject("user").put("currLoginTime",System.currentTimeMillis());
		result.getJSONObject("user").put("currLoginLocation",location);
		result.getJSONObject("user").remove("loginPass");
		
		result.getJSONObject("site").put("domain", domain);
		
		String token = MobileConstant.genToken(loginTime + "," + member.getLoginName() + "," + member.getLoginPass());
		result.put("token", token);

		String userkey = langService.genUserkey(result);
		if(cacheDao.exists(userkey)){
			cacheDao.delete(userkey);
		}
		cacheDao.insert(langService.genUserkey(result), result, 3600 * 24 * 7);
		
		try{
			member.setLastLoginIp(ip);
			member.setLastLoginTime(loginTime);
			member.setLoginCount(member.getLoginCount() + 1);
			member.setLastLoginLocation(location);
			memberDao.update(member);
		}catch(Exception e){
			
		}
		return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "登录成功",result);
		
    }
	
    
    
    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
  * @throws ParseException
     */ 
    private int daysBetween(Date smdate,Date bdate) throws ParseException{ 
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	smdate=sdf.parse(sdf.format(smdate));
    	bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(smdate); 
        long time1 = cal.getTimeInMillis();              
        cal.setTime(bdate); 
        long time2 = cal.getTimeInMillis();      
        long between_days=(time2-time1)/(1000*3600*24);
         
       return Integer.parseInt(String.valueOf(between_days));        
    }  
    
    /**
     * 注册后自动登录
     */
    public ResultMsg<JSONObject> loginForRegist(String local,String domain, String loginName,String location,String ip){
		Member member = memberDao.selectByLoginName(loginName);
		return processLogin(member,location,domain,ip);
    }
    
    
    
    /**
     * 使用令牌自动登录
     */
    public ResultMsg<JSONObject> loginForToken(String local,String domain, String token,String location,String ip,String enc){
    	if(StringUtils.isEmpty(token) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(token)){
				error.put("token", "登录令牌未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "加密串未填写");
			}
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
    	
    	if(!StringUtils.isEmpty(location)){
    		if(location.indexOf(",") == -1){
    			JSONObject error = new JSONObject();
    			error.put("location", "坐标格式不对");
    			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR,error);
    		}
    	}
    	
    	//检查授权码是否正确
		if(!MobileConstant.genEnc(SystemConstant.SYSTEM_LOGIN_AUTO + token).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
    	
    	String[] tokens = MobileConstant.decToken(token).split(",");
    	Long lastLoginTime = Long.valueOf(tokens[0]);
    	String loginName = tokens[1];
    	String loginPass = tokens[2];
    	
    	Calendar login = Calendar.getInstance();
    	login.setTimeInMillis(lastLoginTime);
    	
    	Calendar now = Calendar.getInstance();
    	
    	try{
    		int between = daysBetween(now.getTime(),login.getTime());
    		if(between > 30){
    			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "您已经太久没登录过了，需要重新登录");
    		}
    	}catch(Exception e){
    		
    	}
    	
    	Member member = memberDao.selectByLoginName(loginName);
    	
    	if(null == member){
    		return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "用户已不存在不能自动登录");
    	}
    	if(!member.getLoginPass().equals(loginPass)){
    		return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "您的密码已被修改，需要重新登录");
    	}
    	
    	return processLogin(member,location, domain, ip);
    }
    
    
    
    public ResultMsg<JSONObject> loginForThird(String local,String domain,Integer account, String loginName,String info,Integer remeber,String location,String ip,String enc){
    	
    	if(!StringUtils.isEmpty(location)){
    		if(location.indexOf(",") == -1){
    			JSONObject error = new JSONObject();
    			error.put("location", "坐标格式不对");
    			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR,error);
    		}
    	}
    	
    	if(FieldConstant.MEMBER_MTYPE_QQ.equals(account)){
    		return loginForQQ(local, domain, loginName, info,remeber,location, ip, enc);
    	}else if(FieldConstant.MEMBER_MTYPE_WECHAT.equals(account)){
    		return loginForWechat(local, domain, loginName, info,remeber,location, ip, enc);
    	}else if(FieldConstant.MEMBER_MTYPE_WEBLOG.equals(account)){
    		return loginForSina(local, domain, loginName, info, remeber,location,ip, enc);
    	}else{
    		return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "非法登录");
    	}
    	
    }
    
    
    /**
     * QQ号登录
     * @return
     */
    private ResultMsg<JSONObject> loginForQQ(String local,String domain, String loginName,String info,Integer remeber,String location,String ip,String enc){
    	//验证参数
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(info)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(loginName)){
				error.put("loginName", "登录账号未填写");
			}
			if(StringUtils.isEmpty(info)){
				error.put("newPass", "账户信息未设置");
			}
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc(FieldConstant.MEMBER_MTYPE_QQ+loginName).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		Member member = memberDao.selectByLoginName(loginName);
		
		info = info.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\\\\/", "/");
		JSONObject json = JSONObject.parseObject(info);
		
		if(null == member){
			member = new Member();
			member.setMtype(FieldConstant.MEMBER_MTYPE_QQ);
			member.setLoginName(loginName);
			member.setLoginPass(FieldConstant.MEMBER_LOGINPASS_EMPTY);
			member.setCreateTime(System.currentTimeMillis());
			member.setThirdInfo(info);
			member.setState(SystemConstant.ENABLE);
			member.setSex(json.getString("gender").equals("男") ? FieldConstant.SEX_MEN : FieldConstant.SEX_WOMEN);
			member.setNickName(json.getString("nickname"));
			member.setPhoto(json.getString("figureurl_qq_2"));
			Long id = memberDao.insertAndReturnId(member);
			member.setId(id);
		}else{
			member.setThirdInfo(info);
		}
		
		if(StringUtils.isEmpty(member.getModified())){
			member.setSex(json.getString("gender").equals("男") ? FieldConstant.SEX_MEN : FieldConstant.SEX_WOMEN);
			member.setNickName(json.getString("nickname"));
			member.setPhoto(json.getString("figureurl_qq_2"));
		}else{
			JSONObject modified = JSON.parseObject(member.getModified());
			//修改过的字段才更新
			if(null == modified.getInteger("sex") || modified.getInteger("sex") == SystemConstant.DISABLE){
				member.setSex(json.getString("gender").equals("男") ? FieldConstant.SEX_MEN : FieldConstant.SEX_WOMEN);
			}
			if(null == modified.getInteger("nickName") || modified.getInteger("nickName") == SystemConstant.DISABLE){
				member.setNickName(json.getString("nickname"));
			}
			if(null == modified.getInteger("photo") || modified.getInteger("photo") == SystemConstant.DISABLE){
				member.setPhoto(json.getString("figureurl_qq_2"));
			}
			
		}
		return processLogin(member,location,domain,ip);
    }
    
    
    /**
     * 微信号登录
     * @return
     */
    private ResultMsg<JSONObject> loginForWechat(String local,String domain, String loginName,String info,Integer remeber,String location,String ip,String enc){
    	return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "登录成功");
    }
    
    /**
     * 新浪微博账号登录
     * @return
     */
    private ResultMsg<JSONObject> loginForSina(String local,String domain, String loginName,String info,Integer remeber,String location,String ip,String enc){
    	return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "登录成功");
    }
    
    /**
     * 手机号登录
     */
	@Override
	public ResultMsg<JSONObject> login(String local,String domain, String loginName,String loginPass,String authCode,String sessionCode,Integer remeber,String location,String ip,String enc) {
		//验证参数
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPass)  || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(loginName)){
				error.put("loginName", "登录账号未填写");
			}
			if(StringUtils.isEmpty(loginPass)){
				error.put("loginPass", "登录密码未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		
		//判断验证码是否输入并且正确
		if(sessionCode != null && !sessionCode.equals(authCode)){
			JSONObject error = new JSONObject();
			error.put("authCode", "验证码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(FieldConstant.MEMBER_MTYPE_TEL+loginName+loginPass).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		Member member = memberDao.selectByLoginName(loginName);
		
		if(null == member){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "该用户还没有注册");
		}
		
		if(FieldConstant.MEMBER_LOGINPASS_EMPTY.equals(member.getLoginPass())){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "该用户还没有注册");
		}
		
		if(FieldConstant.MEMBER_STATE_FORBIDDEN.equals(member.getState())){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_UNALLOW, "该用户已被禁止登录");
		}else if(FieldConstant.MEMBER_STATE_DELETE.equals(member.getState())){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_UNALLOW, "该用户已被删除");
		}
		
		if(!MD5.calcMD5(loginName + loginPass).equals(member.getLoginPass())){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "密码错误");
		}
		
		return processLogin(member,location,domain,ip);
	}


	/**
	 * 退出登录
	 */
	@Override
	public ResultMsg<?> logout(String userkey) {
		cacheDao.delete(userkey);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"退出成功");
	}


	/**
	 * 忘记密码
	 */
	@Override
	public ResultMsg<?> forgetPass(String local, String loginName,String newPass,String confirmPass,String smsCode, String token,String enc) {
		//验证参数
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(newPass) || StringUtils.isEmpty(confirmPass) || StringUtils.isEmpty(smsCode)  || StringUtils.isEmpty(enc) || StringUtils.isEmpty(token)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(loginName)){
				error.put("loginName", "登录账号未填写");
			}
			if(StringUtils.isEmpty(newPass)){
				error.put("newPass", "新密码未填写");
			}
			if(StringUtils.isEmpty(confirmPass)){
				error.put("confirmPass", "确认密码未填写");
			}
			if(StringUtils.isEmpty(smsCode)){
				error.put("smsCode", "短信验证码未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			if(StringUtils.isEmpty(token)){
				error.put("token", "令牌未填写");
			}
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		
		if(!newPass.equals(confirmPass)){
			JSONObject error = new JSONObject();
			error.put("confirmPass", "确认密码与新密码不一致");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		//记得设置有效期，10分钟以内有效
		if(!MD5.calcMD5(FieldConstant.MEMBER_MTYPE_TEL+loginName+smsCode).equals(token)){
			JSONObject error = new JSONObject();
			error.put("token", "令牌不正确");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc(loginName+token).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		Member member = memberDao.selectByLoginName(loginName);
		if(null == member){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "该用户还没有注册过");
		}
		if(!FieldConstant.MEMBER_MTYPE_TEL.equals(member.getMtype())){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "不能重置密码，此账户不是一个手机号码");
		}
		
//		RandomGifNumUtils rdnu = RandomGifNumUtils.instance(6);
//		String newPass = rdnu.getString();//CustomConfig.getString("sso.resetPass");
		member.setLoginPass(MD5.calcMD5(loginName + newPass));
		int count = memberDao.update(member);
		if(count > 0){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "密码已经重置");
		}else{
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "密码重置失败，请重试！");
		}
	}
	
	/**
	 * 提交工商执照
	 * @param local
	 * @param licenseNum
	 * @param licenseImgs
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> addLicense(String local,Long memberId,String licenseNum,String licenseImgs,String enc){
		
		//验证参数
		if(StringUtils.isEmpty(licenseNum) || StringUtils.isEmpty(licenseImgs) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(licenseNum)){
				error.put("licenseNum", "工商执照编号未填写");
			}
			if(StringUtils.isEmpty(licenseImgs)){
				error.put("licenseImgs", "工商执照电子版未上传");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		
		boolean[] r = SystemUtil.validImgArrByJson(licenseImgs);
		JSONObject license = new JSONObject();
		if(r == null){
			license.put("licenseImgs", "工商执照电子版内容格式错误");
		}else{
			if(!r[0]){
				license.put("licenseImgs", "工商执照电子版格式错误");
			}
		}
		
		if(!license.isEmpty()){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "工商执照内容格式错误",license);
		}
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc(memberId+licenseNum).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		//一个工商执照只能注册一个商铺
		if(null != memberDao.selectByLicense(licenseNum)){
			JSONObject error = new JSONObject();
			error.put("licenseNum", "该工商执照已被其他账号使用");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		
		Member m = memberDao.selectById(memberId);
//		m.setCertFlag(FieldConstant.MEMBER_CERT_COMPANY);
		licenseImgs = licenseImgs.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\\\\/", "/");
		m.setLicenseNum(licenseNum);
		m.setLicenseImgs(licenseImgs);
		
		//暂时默认为审核通过
//		m.setAuditFlag(FieldConstant.MEMBER_AUDIT_SUCCESS);
		int count = memberDao.update(m);
		if(count > 0){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "资料提交成功");
		}else{
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "提交失败，请重试！");
		}
	}
	
	/**
	 * 提交个人身份证
	 * @param local
	 * @param idCardNum
	 * @param idCardImgs
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> addIdCard(String local,Long memberId,String idCardNum,String idCardImgs,String enc){
		//验证参数
		if(StringUtils.isEmpty(idCardNum) || StringUtils.isEmpty(idCardImgs) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(idCardNum)){
				error.put("idCardNum", "身份证号未填写");
			}
			if(StringUtils.isEmpty(idCardImgs)){
				error.put("idCardImgs", "身份证电子版未上传");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		
		boolean[] r = SystemUtil.validImgArrByJson(idCardImgs);
		JSONObject idcard = new JSONObject();
		if(r == null){
			idcard.put("idCardImgs", "身份证电子版内容格式错误");
		}else{
			if(!r[0]){
				idcard.put("idCardImgs", "身份证正面电子版格式错误");
			}
			if(!r[1]){
				idcard.put("idCardImgs", "身份证背面电子版格式错误");
			}
		}
		
		if(!idcard.isEmpty()){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "身份证电子版内容格式错误",idcard);
		}
		
		//一个身份证只能注册一个商铺
		if(null != memberDao.selectByIdCard(idCardNum)){
			JSONObject error = new JSONObject();
			error.put("idCardNum", "该身份证已被其他账号使用");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc(memberId+idCardNum).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		Member m = memberDao.selectById(memberId);
//		m.setCertFlag(FieldConstant.MEMBER_CERT_PERSON);
		idCardImgs = idCardImgs.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\\\\/", "/");
		m.setIdCardImgs(idCardImgs);
		m.setIdCardNum(idCardNum);
		
		//暂时默认为审核通过
//		m.setAuditFlag(FieldConstant.MEMBER_AUDIT_SUCCESS);
		int count = memberDao.update(m);
		if(count > 0){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "资料提交成功");
		}else{
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "提交失败，请重试！");
		}
	}
	
	
	/**
	 * 提交审核
	 * @param local
	 * @param memberId
	 * @param certFlag
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> addAudit(String local,Long memberId, Integer certFlag,String enc){
		
		Member m = memberDao.selectById(memberId);
		
		if(StringUtils.isEmpty(m.getIdCardNum()) || StringUtils.isEmpty(m.getIdCardImgs())){
			JSONObject error = new JSONObject();
			error.put("idCard", "您还未提交身份证信息");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		
		//只有企业认证需要提交工商执照
		if(FieldConstant.MEMBER_CERT_COMPANY.equals(certFlag)){
			if(StringUtils.isEmpty(m.getLicenseNum()) || StringUtils.isEmpty(m.getLicenseImgs())){
				JSONObject error = new JSONObject();
				error.put("idCard", "您还未提交工商执照");
				return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
			}
		}
		
		if(!FieldConstant.MEMBER_CERT_COMPANY.equals(certFlag) && !FieldConstant.MEMBER_CERT_PERSON.equals(certFlag)){
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, "非法的认证类型");
		}
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc("" + memberId + certFlag).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		m.setCertFlag(certFlag);
		//暂时默认为审核通过
		m.setAuditFlag(FieldConstant.MEMBER_AUDIT_SUCCESS);
		m.setShopCount(m.getShopCount() + 1);
		int count = memberDao.update(m);
		if(count > 0){
			
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "提交审核成功");
		}else{
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "提交失败，请重试！");
		}
	}
	
	
	/**
	 * 取消审核
	 * @param local
	 * @param memberId
	 * @param certFlag
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> cancelAudit(String local,Long memberId,String enc){
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc("" + memberId).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		
		Member m = memberDao.selectById(memberId);
		
		//设置为取消状态
		m.setCertFlag(FieldConstant.MEMBER_CERT_EMPTY);
		m.setAuditFlag(FieldConstant.MEMBER_AUDIT_EMPTY);
		m.setShopCount(m.getShopCount() - 1);
		int count = memberDao.update(m);
		if(count > 0){
			requiredDao.deleteMyShop(m.getId());
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "取消审核成功");
		}else{
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_ERROR, "取消失败，请重试！");
		}
	}

}
