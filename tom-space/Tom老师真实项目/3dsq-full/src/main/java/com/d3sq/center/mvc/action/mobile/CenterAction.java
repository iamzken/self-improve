package com.d3sq.center.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Auth;
import javax.core.common.doc.annotation.Demo;
import javax.core.common.doc.annotation.Domain;
import javax.core.common.doc.annotation.Rule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.center.service.ICenterService;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.core.service.ILangService;
import com.d3sq.model.entity.Address;
import com.d3sq.model.entity.Member;

/**
 * 个人中心(用户版)Controller
 *
 */
@Controller
@RequestMapping("/mobile/my")
@Domain(value="i",desc="个人中心")
public class CenterAction extends BaseAction{
	
	@Autowired private ICenterService centerService;
	@Autowired private ILangService langService;
	
	
	@Api(name="检查手机号是否被绑定",
	 desc="检查手机号是否已被绑定，用户于注册和解绑时调用",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="phone",desc="手机号码"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + phone")
	 })
	@RequestMapping(value="/chkPhoneForBind.json")
	public ModelAndView chkPhoneForBind(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="phone") String phone,
			@RequestParam(value="enc") String enc){
		String local = super.getLocal(request);
		String loginName = super.getUserInfo(request).getJSONObject("user").getString("loginName");
		ResultMsg<?> result = centerService.chkPhoneForBind(local,loginName,phone, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	@Api(name="解绑手机号",
	 desc="此接口用于解除已经绑定的手机号，需要登录后才能操作",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码，加密规则：私钥 + 已登录用户的手机号")
	 })
	@RequestMapping(value="/unbindPhone.json",method=RequestMethod.POST)
	public ModelAndView unbindPhone(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="enc") String enc){
		String local = super.getLocal(request);
		String loginName = super.getUserInfo(request).getJSONObject("user").getString("loginName");
		ResultMsg<?> result = centerService.unbindPhone(local,loginName,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	@Api(name="绑定手机号",
	 desc="绑定未绑定过的手机号，需要成功接收到短信验证码方可操作",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="phone",desc="手机号"),
		@Rule(name="smsCode",desc="短信验证码"),
		@Rule(name="token",desc="短信验证成功后生成token"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + phone + token")
	 })
	@RequestMapping(value="/bindPhone.json",method=RequestMethod.POST)
	public ModelAndView bindPhone(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="phone") String phone,
			@RequestParam(value="smsCode") String smsCode,
//			@RequestParam(value="loginPass") String loginPass,
//			@RequestParam(value="confirmPass") String confirmPass,
			@RequestParam(value="token") String token,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		String loginName = super.getUserInfo(request).getJSONObject("user").getString("loginName");
		ResultMsg<?> result = centerService.bindPhone(local,loginName,phone,smsCode,token,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	@Api(name="修改个人资料",
	 desc="用于修改用户头像、昵称等信息",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码，加密规则：私钥 + 已登录用户名")
	 })
	@RequestMapping(value="/modifyInfo.json",method=RequestMethod.POST)
	public ModelAndView modifyInfo(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Member member,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		String loginName = super.getUserInfo(request).getJSONObject("user").getString("loginName");
		member.setLoginName(loginName);
		ResultMsg<?> result = centerService.modifyMemberInfo(local,member,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	@Api(name="修改密码",
	 desc="用于修改用户的登录密码，修改密码前需要验证旧密码是否正确",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="oldPass",desc="旧密码"),
		@Rule(name="newPass",desc="新密码"),
		@Rule(name="confirmPass",desc="确认新密码"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + oldPass + newPass")
	 })
	@RequestMapping(value="/modifyPass.json",method=RequestMethod.POST)
	public ModelAndView modifyPass(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="oldPass") String oldPass,
			@RequestParam(value="newPass") String newPass,
			@RequestParam(value="confirmPass") String confirmPass,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		String loginName = super.getUserInfo(request).getJSONObject("user").getString("loginName");
		ResultMsg<?> result = centerService.modifyPass(local,loginName,oldPass,newPass,confirmPass,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="添加常用收货地址",
	 desc="添加地址到常用收货地址列表",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码，加密规则：私钥 + 当前登录用户的id")
	 })
	@RequestMapping(value="/addAddress.json")
	public ModelAndView addAddress(@ModelAttribute Address address,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		address.setMemberId(userId);
		ResultMsg<?> result = centerService.addAress(local,address,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	@Api(name="获取常用收货地址列表",
	 desc="获取用户自己添加的收货列表",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码，加密规则：私钥 + 当前登录用户的id")
	 })
	@RequestMapping(value="/getAddress.json")
	public ModelAndView getAddress(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = centerService.getAddress(local,userId,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	@Api(name="获取订单列表",
	 desc="获取用户订单列表，包括购买商品订单、预约服务订单、物业缴费订单、快递订单、话费充值等订单都可以调用此接口",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码，加密规则：私钥 + 当前登录用户的id")
	 },
	 demo=@Demo(
		param="enc=ce6078738a0f4c0874879dec38e2aa2b",
		success="{\"data\":[{\"process\":1,\"payAmount\":12,\"createTime\":1471601129690,\"products\":[{\"buyCount\":7,\"coverImg\":\"http://resource.3dsq.me/uploads/mall/18674818061/_1471600193181.png\",\"price\":12,\"productName\":\"牙刷\",\"productId\":812}],\"orderId\":\"P22016081918052900000074\"},{\"process\":1,\"payAmount\":3.5,\"createTime\":1471599818010,\"products\":[{\"buyCount\":1,\"price\":3,\"productName\":\"可口可乐\",\"productId\":810}],\"orderId\":\"P22016081917433800000072\"}],\"msg\":\"获取成功\",\"status\":1}",
		error="{\"msg\":\"授权码错误\",\"status\":9}"
	))
	@RequestMapping(value="/getOrders.json")
	public ModelAndView getOrders(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="process",required=false) Integer process,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = centerService.getOrders(local,userId,process,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="获取订单详情",
	 desc="获取订单详情，需要填写提供订单ID、父订单ID和子订单ID均可",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="orderId",desc="订单ID"),
		@Rule(name="enc",desc="授权码，加密规则：私钥 + orderId")
	 })
	@RequestMapping(value="/getOrderDetail.json")
	public ModelAndView getProductDetail(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="orderId") String orderId,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		ResultMsg<?> result = centerService.getOrderDetail(local,domain,orderId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	@Api(name="获取积分列表",
	 desc="获取当前登录用户的积分列表",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码：加密规则：私钥 + 当前登录用户id")
	 })
	@RequestMapping(value="/getStores.json")
	public ModelAndView getStores(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = centerService.getStores(local,userId,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	@Api(name="获取优惠券列表",
	 desc="获取当前登录用户的优惠券列表",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码：加密规则：私钥 + 当前登录用户id")
	 })
	@RequestMapping(value="/getCoupons.json")
	public ModelAndView getCoupons(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = centerService.getCoupons(local,userId,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	@Api(name="获取个人收藏列表",
	 desc="获取当前登录用户的收藏列表",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码：加密规则：私钥 + 当前登录用户id")
	 })
	@RequestMapping(value="/getFavorites.json")
	public ModelAndView getFavorites(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = centerService.getFavorites(local,userId,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	@Api(name="获取个人设置",
	 desc="获取当前登录用户的个性设置列表",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码：加密规则：私钥 + 当前登录用户id")
	 })
	@RequestMapping(value="/getSettings.json")
	public ModelAndView getSettings(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = centerService.getSettings(local,userId,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	
	
	@Api(name="获取登录信息",
	 desc="获取当前登录用户的个人信息，包括关联账号信息、站点信息、权限信息，商铺信息等",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="enc",desc="授权码：加密规则：私钥 + 当前登录用户id")
	 })
	@RequestMapping(value="/getInfo.json")
	public ModelAndView getInfo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject userinfo = super.getUserInfo(request);
		ResultMsg<?> result = centerService.getInfo(local, userinfo, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
}
