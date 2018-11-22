package com.d3sq.passport.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Auth;
import javax.core.common.doc.annotation.Domain;
import javax.core.common.doc.annotation.Demo;
import javax.core.common.doc.annotation.Rule;
import javax.core.common.utils.CookiesUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.core.service.ILangService;
import com.d3sq.passport.service.IMemberService;

/**
 * 用户信息表Controller
 *
 */
@Controller
@RequestMapping("/mobile/system")
@Domain(value="passport",desc="用户认证")
public class SystemAction extends BaseAction{
	static Logger logger = Logger.getLogger(SystemAction.class);

	@Autowired private IMemberService memberService;
	@Autowired private ILangService langService;

	
	@Api(name="检查手机号是否已注册",
	 desc="用户注册前调用此接口，检查用户是否已注册",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="loginName",desc="登录账号（仅手机号码）"),
		@Rule(name="enc",desc="授权码,加密规则：私钥 + loginName")
	 },
	 returns={
		@Rule(name="成功",type="JSON",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/loginNameExists.json")
	public ModelAndView loginNameExists(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="loginName") String loginName,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		ResultMsg<?> result = memberService.getByLoginName(local, loginName, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	@Api(name="校验短信验证码",
	 desc="检查短信验证码是否正确",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="loginName",desc="不带区号的电话号码"),
		@Rule(name="zone",desc="纯数字区号(默认：86)"),
		@Rule(name="smsCode",desc="短信验证码"),
		@Rule(name="ver",desc="APP版本"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + loginName + smsCode")
	 },
	 returns={
		@Rule(name="成功",type="JSON",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/chkSmsCode.json",method=RequestMethod.POST)
	public ModelAndView chkSmsCode(
			@RequestParam(value="loginName") String loginName,
			@RequestParam(value="zone",defaultValue="86",required=false) String zone,
			@RequestParam(value="smsCode") String smsCode,
			@RequestParam(value="ver",required=false) String ver,
			@RequestParam(value="enc") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		ResultMsg<?> result = memberService.chkSmsCode(local,ver,loginName,zone,smsCode,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	

	@Api(name="用户注册",
	 desc="此接口仅支持手机号注册",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="loginName",desc="登录账号（仅手机号码）"),
		@Rule(name="loginPass",desc="登录密码"),
		@Rule(name="email",desc="电子邮箱"),
		@Rule(name="smsCode",desc="短信验证码"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + loginName + smsCode")
	 },
	 returns={
		@Rule(name="成功",type="JSON",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/regist.json",method=RequestMethod.POST)
	public ModelAndView regist(
			@RequestParam(value="loginName") String loginName,
			@RequestParam(value="loginPass") String loginPass,
			@RequestParam(value="mtype",defaultValue="1") Integer mtype,
			@RequestParam(value="email",required=false) String email,
			@RequestParam(value="smsCode") String smsCode,
			@RequestParam(value="ver",required=false) String ver,
			@RequestParam(value="location",required=false) String location,
			@RequestParam(value="enc") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		//mtype  默认设置为手机号注册
//		Integer mtype = ToolKit.getInt(request, "mtype",FieldConstant.MEMBER_MTYPE_TEL);
		String local = super.getLocal(request);
		ResultMsg<?> result = memberService.addForRegist(local,ver,mtype,loginName,loginPass,email,smsCode,enc);
		
		//注册成功，自动登录
		if(result.getStatus() == SystemConstant.RESULT_STATUS_SUCCESS){
			String domain = getDomain(request);
			String ip = getIpAddr(request);
			ResultMsg<JSONObject> r = memberService.loginForRegist(local,domain,loginName,location,ip);
			return processLogin(request, response, r);
		}else{
			return super.callBackForJsonp(request, response,JSON.toJSONString(result));
		}
	}
	
	
	
	@Api(name="注销登录",
	 desc="在用户登录成功后才能调用此接口。如果未登录的情况下调用会提示您尚未登录",
	 auth={ @Auth(checkEnc=false) },
	 returns={
		@Rule(name="成功",type="JSON",desc="返回登录URL")
	},
	demo=@Demo(success="{\"data\":{\"loginHost\":\"http://passport.3dsq.me\"},\"msg\":\"退出成功\",\"status\":1}"))
	@RequestMapping(value="/logout.json")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) {
		
		request.getSession().removeAttribute(SystemConstant.SYSTEM_LOGIN_AUTHCODE);//清除验证码
		request.getSession().removeAttribute(SystemConstant.SYSTEM_LOGIN_FLAG);//清除登录信息
		
		String userkey =  CookiesUtil.loadCookie(SystemConstant.SYSTEM_LOGIN_COOKIE, request);
		removeCookie(request,response);//删除cookie
		ResultMsg<?> result = memberService.logout(userkey); // 清除缓存
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	@Api(name="自动登录",
	desc="如果用户在登录时，选择了记住登录状态（自动登录），"
		+ "则在登录结果中会生成一个token，将该token保存在客户端，"
		+ "自动登录时传回给服务器校验。tokne在30天内有效，"
		+ "每次重新登录后会自动延期30天。",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="token",desc="登录令牌"),
		@Rule(name="appKey",desc="APP客户端版本标识（商家版:IOS_SHOP,用户版：IOS_CUSTOM）"),
		@Rule(name="appVer",desc="客户端安装的版本号"),
		@Rule(name="imei",desc="客户端使用的手机串号"),
		@Rule(name="location",desc="登录坐标，纬度在前，经度在后，用逗号隔开"),
		@Rule(name="enc",type="JSON",desc="授权码,加密规则：私钥 + (自动登录标识,为常量值5) + token")
	 },
	 returns={
		@Rule(name="site",type="JSON",desc="用户所在的站点信息"),
		@Rule(name="shop",type="JSON",desc="用户已注册的商铺信息"),
		@Rule(name="roles",type="JSONArray",desc="该用户拥有的角色列表"),
		@Rule(name="relation",type="JSON",desc="该用户的关联账号"),
		@Rule(name="user",type="JSON",desc="用户基本信息"),
		@Rule(name="token",type="字符串",desc="登录成功，自动延长有效期以后的令牌")
	 },
	 demo=@Demo(
		success="{\"data\":{\"site\":{\"domain\":\"3dsq.me\",\"config\":{}},\"shop\":{\"address\":\"湖南省长沙市岳麓区\",\"certFlag\":0,\"createTime\":1471677363046,\"creatorId\":2,\"id\":590,\"lat\":28.3706,\"lon\":113.23,\"name\":\"步步高超市\",\"pinyin\":\"bubugaochaoshi\",\"state\":1},\"token\":\"4714b88aba831191bc1de60c85f296fa9c403dd34c3b12b2e944ff9dd96f8f1ed7d201edcfd95500548e33e859bb0edfa070878f0c677e4464e792b8d2a05911\",\"roles\":[],\"relation\":{},\"user\":{\"sex\":1,\"certFlag\":2,\"tel\":\"13800138000\",\"state\":1,\"id\":2,\"nickName\":\"Tom\",\"loginName\":\"13800138000\",\"mtype\":1,\"photo\":\"http://resource.3dsq.me/uploads/anonymous/13800138000/_1471851035273.png\",\"currLoginTime\":1472466221579,\"auditFlag\":1,\"currLoginIp\":\"220.168.106.214\"},\"options\":{}},\"msg\":\"登录成功\",\"status\":1}",
		error="{\"status\":0,\"msg\":\"您的密码已被修改，需要重新登录\"}"
	))
	@RequestMapping(value="/loginForToken.json",method=RequestMethod.POST)
	public ModelAndView loginForToken(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="token") String token,
			@RequestParam(value="appKey",required=false) String appKey,
			@RequestParam(value="appVer",required=false) String appVer,
			@RequestParam(value="imei",required=false) String imei,
			@RequestParam(value="location",required=false) String location,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		String ip = getIpAddr(request);
		ResultMsg<JSONObject> result = memberService.loginForToken(local,domain,token,location,ip,enc);
		return processLogin(request, response, result);
	}
	
	
	
	
	@Api(name="第三方账号登录",
	 desc="目前支持QQ登录，手机号登录请调用普通登录接口",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="loginName",desc="登录账号，可以是QQ、微信号、新浪微博"),
		@Rule(name="account",desc="登录方式。2：QQ号登录，3:微信号登录，4：新浪微博登录"),
		@Rule(name="info",desc="第三方登录成功后返回的josn数据"),
		@Rule(name="appKey",desc="APP客户端版本标识（商家版:IOS_SHOP,用户版：IOS_CUSTOM）"),
		@Rule(name="appVer",desc="客户端安装的版本号"),
		@Rule(name="imei",desc="客户端使用的手机串号"),
		@Rule(name="remember",desc="是否记住登录状态，下次自动登录(1:是，0：否)，默认1"),
		@Rule(name="location",desc="登录坐标，纬度在前，经度在后，用逗号隔开"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + 登录标识(QQ登录：3，微信登录：2，新浪微博登录：4) + loginName")
	 },
	 returns={
		@Rule(name="site",type="JSON",desc="用户所在的站点信息"),
		@Rule(name="shop",type="JSON",desc="用户已注册的商铺信息"),
		@Rule(name="roles",type="JSONArray",desc="该用户拥有的角色列表"),
		@Rule(name="relation",type="JSON",desc="该用户的关联账号"),
		@Rule(name="user",type="JSON",desc="用户基本信息"),
		@Rule(name="token",type="字符串",desc="登录成功以后的令牌，使用该令牌可以实现自动登录")
	},
	demo=@Demo(
		success="{\"data\":{\"site\":{\"domain\":\"3dsq.me\",\"config\":{}},\"token\":\"c9b4dd5bd91e1362201b21538945ba371fe32934bf42d59fbfdf3ed0bf3656d42bcba4655f4c4c0eef42669149c78d717d009cfddd8a66ec\",\"roles\":[],\"relation\":{},\"user\":{\"sex\":1,\"certFlag\":0,\"state\":1,\"id\":1,\"nickName\":\"Tom\",\"loginName\":\"0F4A06ED89620D434F5DD652AFAB6E1C\",\"mtype\":3,\"photo\":\"http://q.qlogo.cn/qqapp/1105405813/0F4A06ED89620D434F5DD652AFAB6E1C/100\",\"currLoginTime\":1472628633812,\"auditFlag\":0,\"currLoginIp\":\"220.168.106.132\"},\"options\":{}},\"msg\":\"登录成功\",\"status\":1}",
		error="{\"status\":0,\"msg\":\"登录失败，请重试\"}"
	))
	@RequestMapping(value="/loginForThird.json",method=RequestMethod.POST)
	public ModelAndView loginForThird(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="loginName") String loginName,
			@RequestParam(value="account") Integer account,
			@RequestParam(value="info") String info,
			@RequestParam(value="appKey",required=false) String appKey,
			@RequestParam(value="appVer",required=false) String appVer,
			@RequestParam(value="imei",required=false) String imei,
			@RequestParam(value="remember",required=false,defaultValue="1") Integer remeber,
			@RequestParam(value="location",required=false) String location,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		String ip = getIpAddr(request);
		ResultMsg<JSONObject> result = memberService.loginForThird(local,domain,account,loginName,info,remeber,location,ip,enc);
		return processLogin(request, response, result);
	}
	
	
	
	
	@Api(name="普通用户登录",
	desc="使用手机号登录可调用此接口",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="loginName",desc="登录账号（仅手机号）"),
		@Rule(name="loginPass",desc="登录密码"),
		@Rule(name="appKey",desc="APP客户端版本标识（商家版:IOS_SHOP,用户版：IOS_CUSTOM）"),
		@Rule(name="appVer",desc="客户端安装的版本号"),
		@Rule(name="imei",desc="客户端使用的手机串号"),
		@Rule(name="authCode",desc="如果有验证码，则必填。如果出现密码错误等安全性相关的操作，需要输入验证码。"),
		@Rule(name="remember",desc="是否记住登录状态，下次自动登录(1:是，0：否)，默认1"),
		@Rule(name="location",desc="登录坐标，纬度在前，经度在后，用逗号隔开"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + (手机登录标识,为常量值1) + loginName + loginPass")
	 },
	 returns={
		@Rule(name="site",type="JSON",desc="用户所在的站点信息"),
		@Rule(name="shop",type="JSON",desc="用户已注册的商铺信息"),
		@Rule(name="roles",type="JSONArray",desc="该用户拥有的角色列表"),
		@Rule(name="relation",type="JSON",desc="该用户的关联账号"),
		@Rule(name="user",type="JSON",desc="用户基本信息"),
		@Rule(name="token",type="字符串",desc="登录成功以后的令牌，使用该令牌可以实现自动登录")
	},
	demo=@Demo(
		param="loginName=13800138000&authCode=2323&loginPass=123456&enc=40756d8b0ab1f48d6d6328c95e729924",
		success="{\"data\":{\"site\":{\"domain\":\"3dsq.me\",\"config\":{}},\"shop\":{\"address\":\"湖南省长沙市岳麓区\",\"certFlag\":0,\"createTime\":1471677363046,\"creatorId\":2,\"id\":590,\"lat\":28.3706,\"lon\":113.23,\"name\":\"步步高超市\",\"pinyin\":\"bubugaochaoshi\",\"state\":1},\"token\":\"4714b88aba831191bc1de60c85f296fa9c403dd34c3b12b2e944ff9dd96f8f1ed7d201edcfd95500548e33e859bb0edfa070878f0c677e4464e792b8d2a05911\",\"roles\":[],\"relation\":{},\"user\":{\"sex\":1,\"certFlag\":2,\"tel\":\"13800138000\",\"state\":1,\"id\":2,\"nickName\":\"Tom\",\"loginName\":\"13800138000\",\"mtype\":1,\"photo\":\"http://resource.3dsq.me/uploads/anonymous/13800138000/_1471851035273.png\",\"currLoginTime\":1472466221579,\"auditFlag\":1,\"currLoginIp\":\"220.168.106.214\"},\"options\":{}},\"msg\":\"登录成功\",\"status\":1}",
		error="{\"data\":{\"pwd\":\"密码错误\"},\"status\":0,\"msg\":\"密码错误\"}"
	))
	@RequestMapping(value="/login.json",method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="loginName") String loginName,
			@RequestParam(value="loginPass") String loginPass,
			@RequestParam(value="appKey",required=false) String appKey,
			@RequestParam(value="appVer",required=false) String appVer,
			@RequestParam(value="imei",required=false) String imei,
			@RequestParam(value="authCode",required=false) String authCode,
			@RequestParam(value="remeber",required=false,defaultValue="1") Integer remeber,
			@RequestParam(value="location",required=false) String location,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		String ip = getIpAddr(request);
		Object sessionCode = request.getSession().getAttribute(SystemConstant.SYSTEM_LOGIN_AUTHCODE);
		String session_code = null;
		if(null != sessionCode){
			session_code = sessionCode.toString();
		}
		ResultMsg<JSONObject> result = memberService.login(local,domain,loginName, loginPass,authCode,session_code,remeber,location,ip,enc);
		return processLogin(request, response, result);
	}
	
	
	
	
	@Api(name="重置（找回）密码",
	 desc="用户忘记密码的情况下，可调用此接口找回密码（仅支持手机号注册的用户）。找回密码需要短信验证码",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="loginName",desc="登录账号（仅手机号）"),
		@Rule(name="newPass",desc="设置新密码"),
		@Rule(name="confirmPass",desc="确认新密码"),
		@Rule(name="smsCode",desc="短信验证码"),
		@Rule(name="token",desc="短信校验成功后获取的token"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + loginName + token")
	 })
	@RequestMapping(value="/forgetPass.json",method=RequestMethod.POST)
	public ModelAndView forgetPass(
			@RequestParam(value="loginName") String loginName,
			@RequestParam(value="newPass") String newPass,
			@RequestParam(value="confirmPass") String confirmPass,
			@RequestParam(value="smsCode") String smsCode,
			@RequestParam(value="token") String token,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		ResultMsg<?> result = memberService.forgetPass(local,loginName,newPass,confirmPass,smsCode,token,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	@Api(name="提交个人认证资料",
     desc="申请个人认证的用户，调用此接口上传身份证信息",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="idCardNum",desc="身份证号码"),
		@Rule(name="idCardImgs",desc="身份证电子版"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + memberId + idCardNum")
	 })
	@RequestMapping(value="/addIdCard.json",method=RequestMethod.POST)
	public ModelAndView addIdCard(
			@RequestParam(value="idCardNum") String idCardNum,
			@RequestParam(value="idCardImgs") String idCardImgs,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = memberService.addIdCard(local, userId, idCardNum, idCardImgs, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="提交企业认证资料",
	 desc="申请企业认证的用户，调用此接口上传工商执照信息",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="licenseNum",desc="工商执照编号编号"),
		@Rule(name="licenseImgs",desc="工商执照电子版"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + memberId + licenseNum")
	 })
	@RequestMapping(value="/addLicense.json",method=RequestMethod.POST)
	public ModelAndView addLicense(
			@RequestParam(value="licenseNum") String licenseNum,
			@RequestParam(value="licenseImgs") String licenseImgs,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = memberService.addLicense(local, userId, licenseNum, licenseImgs, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	@Api(name="提交审核",
	 desc="未注册商铺的用户，可调用此接口提交审核资料申请注册",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="certFlag",desc="认证类型（1：个人认证，2：企业认证）"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + memberId + certFlag")
	 })
	@RequestMapping(value="/addAudit.json",method=RequestMethod.POST)
	public ModelAndView addAudit(
			@RequestParam(value="certFlag") Integer certFlag,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = memberService.addAudit(local, userId, certFlag, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	@Api(name="取消审核",
	 desc="如果用户已提交审核资料，并处于待审核状态可取消审核，取消审核后，所有资料需要重新提交。如果已经审核通过，不能再取消审核",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码，加密规则：私钥 + memberId")
	 })
	@RequestMapping(value="/cancelAudit.json",method=RequestMethod.POST)
	public ModelAndView cancelAudit(
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = memberService.cancelAudit(local, userId, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	
	/**
	 * 处理登录结果
	 * @param request
	 * @param response
	 * @param result
	 * @return
	 */
	private ModelAndView processLogin(HttpServletRequest request,HttpServletResponse response,ResultMsg<JSONObject> result){
		if(SystemConstant.RESULT_STATUS_SUCCESS == result.getStatus()){
			request.getSession().removeAttribute(SystemConstant.SYSTEM_LOGIN_AUTHCODE);//清除验证码
			
			//重新设置seesion信息
			request.getSession().setAttribute(SystemConstant.SYSTEM_LOGIN_FLAG,result.getData());
			//重新设置cookie信息
			writeCookie(request,response,result.getData());
		}
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	/**
	 * 清除cookie信息
	 * @param request
	 * @param response
	 */
	private void removeCookie(HttpServletRequest request,HttpServletResponse response){
		String domain = getDomain(request);
		CookiesUtil.removeAll(domain, response, request);
	}
	
	/**
	 * 往cookie中写值
	 * @param request
	 * @param response
	 * @param json
	 */
	private void writeCookie(HttpServletRequest request,HttpServletResponse response,JSONObject json){
		String domain = getDomain(request);
		CookiesUtil.write(domain,SystemConstant.SYSTEM_LOGIN_COOKIE, langService.genUserkey(json), response, request);
	}
	
}
