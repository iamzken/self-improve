package com.d3sq.center.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.core.common.ResultMsg;
import javax.core.common.encrypt.MD5;
import javax.core.common.utils.RegexUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.center.dao.AddressDao;
import com.d3sq.center.dao.MemberDao;
import com.d3sq.center.dao.UserSettingsDao;
import com.d3sq.center.service.ICenterService;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.service.ILangService;
import com.d3sq.model.entity.Address;
import com.d3sq.model.entity.ProductOrderDetail;
import com.d3sq.model.entity.Member;
import com.d3sq.model.entity.Product;
import com.d3sq.model.entity.Order;
import com.d3sq.model.entity.ServiceOrderDetail;
import com.d3sq.model.entity.UserSettings;
import com.d3sq.search.dao.ServiceDao;
import com.d3sq.shopping.dao.OrderDao;
import com.d3sq.shopping.dao.ProductOrderDetailDao;
import com.d3sq.shopping.dao.ProductDao;
import com.d3sq.shopping.dao.ServiceOrderDetailDao;

/**
 * 个人中心业务逻辑处理
 *
 */
@Service("centerService")
public class CenterService implements ICenterService {
	@Autowired private MemberDao cmemberDao;
	@Autowired private AddressDao addressDao;
	@Autowired private UserSettingsDao userSettingsDao;
	@Autowired private OrderDao mallOrderDao;
	@Autowired private ProductOrderDetailDao productOrderDetailDao;
	@Autowired private ServiceOrderDetailDao serviceOrderDetailDao;
	@Autowired private ProductDao productDao;
	@Autowired private ServiceDao serviceDao;


	@Autowired private ILangService langService;


	/**
	 * 检查手机号是否被绑定
	 */
	@Override
	public ResultMsg<?> chkPhoneForBind(String local,String loginName,String phone,String enc) {
		//验证参数
		if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(phone)){
				error.put("loginName", "手机号未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		if(!RegexUtils.test(phone, RegexUtils.mobile)){
			JSONObject error = new JSONObject();
			error.put("loginName", "手机号码格式错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(phone).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		Member third = cmemberDao.selectByLoginName(loginName);
		if(third.getBindId() != null){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "您已经绑定了一个手机号");
		}

		if(FieldConstant.MEMBER_MTYPE_TEL.equals(third.getMtype())){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "您是用手机号登录，不能再绑定别的手机");
		}

		Member tel = cmemberDao.selectByLoginName(phone);

		//判断手机号是否已经被绑定
		if(tel == null || tel.getBindId() == null){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "可以绑定的手机号");
		}

		if(tel.getId().equals(third.getBindId())){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "您已经绑定了该手机号");
		}else{
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "该手机已被其他账号绑定");
		}
	}



	/**
	 * 解绑手机号
	 */
	public ResultMsg<?> unbindPhone(String local,String loginName,String enc){
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(loginName)){
				error.put("phone", "账号未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		Member member = cmemberDao.selectByLoginName(loginName);

		//检查授权码是否正确
		if(!MobileConstant.genEnc(member.getTel()).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		int count = cmemberDao.updateForUnbind(member.getId(),member.getBindId());
		if(count > 1){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "解绑成功");
		}else{
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "解绑失败");
		}
	}


	/**
	 * 绑定手机号
	 * @param local
	 * @param loginName
	 * @param oldPass
	 * @param longPass
	 * @param confirmPass
	 * @param token
	 * @param enc
	 * @return
	 */
	public ResultMsg<?> bindPhone(String local,String loginName,String phone,String smsCode,String token,String enc){
		//验证参数
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsCode) || 
				StringUtils.isEmpty(token)  || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(phone)){
				error.put("phone", "手机号未填写");
			}
			if(StringUtils.isEmpty(smsCode)){
				error.put("smsCode", "验证码未填写");
			}
			if(StringUtils.isEmpty(token)){
				error.put("token", "令牌未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//记得设置有效期，10分钟以内有效
		if(!MD5.calcMD5(FieldConstant.MEMBER_MTYPE_TEL+phone+smsCode).equals(token)){
			JSONObject error = new JSONObject();
			error.put("token", "令牌不正确");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(phone+token).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}
		Member third = cmemberDao.selectByLoginName(loginName);
		if(third.getBindId() != null){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "您已经绑定了一个手机号");
		}

		if(FieldConstant.MEMBER_MTYPE_TEL.equals(third.getMtype())){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "您是用手机号登录，不能再绑定别的手机");
		}

		Member tel = cmemberDao.selectByLoginName(phone);

		if(null == tel){
			tel = new Member();
			tel.setLoginName(phone);
			tel.setLoginPass(FieldConstant.MEMBER_LOGINPASS_EMPTY);
			tel.setSex(third.getSex());
			tel.setPhoto(third.getPhoto());
			tel.setBindId(third.getId());
			tel.setMtype(FieldConstant.MEMBER_MTYPE_TEL);
			tel.setNickName(third.getNickName());
			tel.setCreateTime(System.currentTimeMillis());
			tel.setState(SystemConstant.ENABLE);
			tel.setTel(phone);
			Long id = cmemberDao.insertAndReturnId(tel);
			tel.setId(id);
		}else if(tel.getBindId() == null){
			tel.setBindId(third.getId());
			cmemberDao.update(tel);
		}else if(tel.getId().equals(third.getBindId())){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "您已经绑定了该手机号");
		}else if(third.getBindId() != null && !tel.getBindId().equals(third.getBindId())){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "该手机已被其他账号绑定");
		}

		third.setTel(phone);
		third.setBindId(tel.getId());

		int count = cmemberDao.update(third);
		if(count > 0){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "绑定成功",tel);
		}else{
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "绑定失败,请重试",tel);
		}

	}


	/**
	 * 修改用户信息
	 */
	public ResultMsg<?> modifyMemberInfo(String local,Member member,String enc) {

		if(!MobileConstant.genEnc(member.getLoginName()).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		Member old = cmemberDao.selectByLoginName(member.getLoginName());

		JSONObject modified = new JSONObject();
		if(!StringUtils.isEmpty(old.getModified())){
			modified = JSON.parseObject(old.getModified());
		}

		int field = 0;
		//设置性别
		if(null != member.getSex()){
			if(FieldConstant.SEX_WOMEN <= member.getSex() && FieldConstant.SEX_HIDE >= member.getSex() &&
					!old.getSex().equals(member.getSex())){
				old.setSex(member.getSex());
				modified.put("sex", SystemConstant.ENABLE);
				field ++;
			}
		}
		//设置头像
		if(!StringUtils.isEmpty(member.getPhoto()) && !member.getPhoto().equals(old.getPhoto())){
			old.setPhoto(member.getPhoto());
			modified.put("photo", SystemConstant.ENABLE);
			field ++;
		}
		//设置昵称
		if(!StringUtils.isEmpty(member.getNickName()) && !member.getNickName().trim().equals(old.getNickName())){
			old.setNickName(member.getNickName().trim());
			modified.put("nickName", SystemConstant.ENABLE);
			field ++;
		}

		//修改登录坐标
		if(!StringUtils.isEmpty(member.getLastLoginLocation())){
			old.setLastLoginLocation(member.getLastLoginLocation().trim());
			field ++;
		}

		if(field == 0){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "您没有做任何改动，无须修改");
		}

		old.setModified(modified.toJSONString());
		int count = cmemberDao.update(old);
		if(count > 0){
			member.setId(old.getId());
			member.setId(old.getBindId());
			JSONObject r =  JSON.parseObject(JSON.toJSONString(member));
			r.remove("loginPass");
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "修改成功",r);
		}else{
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "修改失败");
		}
	}


	/**
	 * 修改密码
	 */
	@Override
	public ResultMsg<?> modifyPass(String local, String loginName,String oldPass, String newPass,String confirmPass, String enc) {
		//验证参数
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(oldPass) || StringUtils.isEmpty(newPass) || StringUtils.isEmpty(confirmPass)  || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(loginName)){
				error.put("loginName", "账号未填写");
			}
			if(StringUtils.isEmpty(oldPass)){
				error.put("oldPass", "密码未填写");
			}
			if(StringUtils.isEmpty(newPass)){
				error.put("newPass", "新密码未填写");
			}
			if(StringUtils.isEmpty(confirmPass)){
				error.put("confirmPass", "确认密码未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		if(newPass.equals(oldPass)){
			JSONObject error = new JSONObject();
			error.put("newPass", "新密码与旧密码不能一样");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		if(newPass.length() < 6 || newPass.length() > 24){
			JSONObject error = new JSONObject();
			error.put("loginPass", "密码必须为6-24位字符");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		if(!newPass.equals(confirmPass)){
			JSONObject error = new JSONObject();
			error.put("confirmPass", "确认密码与新密码不一致");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(oldPass+newPass).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		Member member = cmemberDao.selectByLoginName(loginName);
		if(null == member){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "未找到用户信息");
		}

		if(!MD5.calcMD5(loginName+oldPass).equals(member.getLoginPass())){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "旧密码错误");
		}
		member.setLoginPass(MD5.calcMD5(loginName+newPass));
		int result = 0;
		member.setState(FieldConstant.MEMBER_STATE_NOMAL);
		result = cmemberDao.update(member);

		if(result > 0){

			JSONObject r =  JSON.parseObject(JSON.toJSONString(member));
			r.remove("loginPass");

			Long loginTime = System.currentTimeMillis();
			String token = MobileConstant.genToken(loginTime + "," + member.getLoginName() + "," + member.getLoginPass());
			r.put("token", token);

			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "修改成功",r);
		}else{
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "修改失败");
		}
	}

	@Override
	public ResultMsg<?> getAddress(String local, Long userId, String enc) {
		//验证参数
		if(null == userId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("loginName", "未登录");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		//查询当前用户下的收获地址集合
		List<Address> list = addressDao.selectByUserId(userId);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",list);
	}

	@Override
	public ResultMsg<?> getOrders(String local, Long userId,Integer process, String enc) {
		//验证参数
		if(null == userId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("loginName", "未登录");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(userId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		//组装订单信息
		Map<String,JSONObject> data = new HashMap<String, JSONObject>();

		//获取商品订单
		this.getOrdersForProduct(data,userId,process);
		//获取服务订单
		this.getOrdersForServie(data,userId,process);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",data.values());
	}

	/**
	 * 获取商品订单
	 * @param data
	 * @param userId
	 * @param process
	 */
	private void getOrdersForProduct(Map<String,JSONObject> data,Long userId,Integer process){
		List<Order> orders = mallOrderDao.selectByUserIdAndProcessAndOtype(userId, process,FieldConstant.PAY_OTYPE_SHOPPING);
		if(orders.isEmpty()){
			return;
		}
		//获取订单商品列表
		List<Long> orderIds = new ArrayList<Long>();
		for(Order order:orders){
			orderIds.add(order.getId());
		}
		List<ProductOrderDetail> orderDetails = productOrderDetailDao.selectByOrderIds(orderIds);
		//查找对应商品信息
		List<Long> productIds = new ArrayList<Long>();
		for(ProductOrderDetail orderDetal:orderDetails){
			productIds.add(orderDetal.getProductId());
		}
		List<Product> products = productDao.selectByIds(productIds);

		Map<Long,Product> productMap = new HashMap<Long,Product>();
		for(Product p:products){
			productMap.put(p.getId(), p);
		}

		for(Order order:orders){
			if(!data.containsKey(order.getParentNum())){
				JSONObject obj = new JSONObject();
				//主订单id
				obj.put("orderId", order.getParentNum());
				//下单时间
				obj.put("createTime", order.getCreateTime());
				//实际金额
				obj.put("payAmount", order.getOrderAmount());
				//订单进度
				obj.put("process", order.getProcess());
				//商品列表
				JSONArray arr = new JSONArray();
				for(ProductOrderDetail orderDetail : orderDetails){
					if(!order.getId().equals(orderDetail.getOrderId())){
						continue;
					}
					JSONObject product = new JSONObject();
					product.put("productId", orderDetail.getProductId());
					product.put("productName", orderDetail.getProductName());
					product.put("price", orderDetail.getPrice());
					product.put("buyCount", orderDetail.getBuyCount());
					product.put("payMent", orderDetail.getPayMent());
					//设置封面图片
					if(null != productMap.get(orderDetail.getProductId())){
						product.put("coverImg", productMap.get(orderDetail.getProductId()).getCoverImg());
					}
					arr.add(product);
				}
				obj.put("products", arr);
				data.put(order.getParentNum(), obj);
			}else{
				//累加订单金额
				JSONObject obj = data.get(order.getParentNum());
				float orderAmount = obj.getFloatValue("payAmount");
				orderAmount += order.getOrderAmount();
				obj.put("payAmount", orderAmount);
				//累加商品列表
				JSONArray arr = obj.getJSONArray("products");
				for(ProductOrderDetail orderDetail : orderDetails){
					if(!order.getId().equals(orderDetail.getOrderId())){
						continue;
					}
					JSONObject product = new JSONObject();
					product.put("productId", orderDetail.getProductId());
					product.put("productName", orderDetail.getProductName());
					product.put("price", orderDetail.getPrice());
					product.put("buyCount", orderDetail.getBuyCount());
					product.put("payMent", orderDetail.getPayMent());
					//设置封面图片
					if(null != productMap.get(orderDetail.getProductId())){
						product.put("coverImg", productMap.get(orderDetail.getProductId()).getCoverImg());
					}
					arr.add(product);
				}
				obj.put("products", arr);
				data.put(order.getParentNum(), obj);
			}
		}
	}




	/**
	 * 获取服务订单
	 * @param data
	 * @param userId
	 * @param process
	 */
	private void getOrdersForServie(Map<String,JSONObject> data,Long userId,Integer process){
		List<Order> orders = mallOrderDao.selectByUserIdAndProcessAndOtype(userId, process,FieldConstant.PAY_OTYPE_SERVICE);
		if(orders.isEmpty()){
			return;
		}
		//获取订单商品列表
		List<Long> orderIds = new ArrayList<Long>();
		for(Order order:orders){
			orderIds.add(order.getId());
		}
		List<ServiceOrderDetail> orderDetails = serviceOrderDetailDao.selectByOrderIds(orderIds);
		//查找对应商品信息
		List<Long> servieIds = new ArrayList<Long>();
		for(ServiceOrderDetail orderDetal:orderDetails){
			servieIds.add(orderDetal.getServiceId());
		}
		List<com.d3sq.model.entity.Service> services = serviceDao.selectByIds(servieIds);

		Map<Long,com.d3sq.model.entity.Service> productMap = new HashMap<Long,com.d3sq.model.entity.Service>();
		for(com.d3sq.model.entity.Service s:services){
			productMap.put(s.getId(), s);
		}

		for(Order order:orders){
			if(!data.containsKey(order.getParentNum())){
				JSONObject obj = new JSONObject();
				//主订单id
				obj.put("orderId", order.getParentNum());
				//下单时间
				obj.put("createTime", order.getCreateTime());
				//实际金额
				obj.put("payAmount", order.getOrderAmount());
				//订单进度
				obj.put("process", order.getProcess());
				//商品列表
				JSONArray arr = new JSONArray();
				for(ServiceOrderDetail orderDetail : orderDetails){
					if(!order.getId().equals(orderDetail.getOrderId())){
						continue;
					}
					JSONObject product = new JSONObject();
					product.put("productId", orderDetail.getServiceId());
					product.put("productName", orderDetail.getServiceName());
					product.put("price", orderDetail.getPrice());
					//product.put("buyCount", orderDetail.getBuyCount());
					product.put("payMent", orderDetail.getPayMent());
					//设置封面图片
					if(null != productMap.get(orderDetail.getServiceId())){
						product.put("coverImg", productMap.get(orderDetail.getServiceId()).getCoverImg());
					}
					arr.add(product);
				}
				obj.put("products", arr);
				data.put(order.getParentNum(), obj);
			}else{
				//累加订单金额
				JSONObject obj = data.get(order.getParentNum());
				float orderAmount = obj.getFloatValue("payAmount");
				orderAmount += order.getOrderAmount();
				obj.put("payAmount", orderAmount);
				//累加商品列表
				JSONArray arr = obj.getJSONArray("products");
				for(ServiceOrderDetail orderDetail : orderDetails){
					if(!order.getId().equals(orderDetail.getOrderId())){
						continue;
					}
					JSONObject product = new JSONObject();
					product.put("productId", orderDetail.getServiceId());
					product.put("productName", orderDetail.getServiceName());
					product.put("price", orderDetail.getPrice());
					//product.put("buyCount", orderDetail.getBuyCount());
					product.put("payMent", orderDetail.getPayMent());
					//设置封面图片
					if(null != productMap.get(orderDetail.getServiceId())){
						product.put("coverImg", productMap.get(orderDetail.getServiceId()).getCoverImg());
					}
					arr.add(product);
				}
				obj.put("products", arr);
				data.put(order.getParentNum(), obj);
			}
		}
	}



	@Override
	public ResultMsg<?> getOrderDetail(String local, String domain,
			String orderId, String enc) {
		//验证参数
		if(null == orderId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == orderId){
				error.put("orderId", "订单号不能为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(orderId).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		List<Order> orders = mallOrderDao.selectByParentNum(orderId);
		if(orders.isEmpty()){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "未找到订单信息");
		}

		Map<String,JSONObject> data = new HashMap<String, JSONObject>();

		int otype = orders.get(0).getOtype();
		//商品订单信息
		if(FieldConstant.PAY_OTYPE_SHOPPING == otype){
			this.getOrderDetailForProduct(data,orders);
		}else if(FieldConstant.PAY_OTYPE_SERVICE == otype){
			this.getOrderDetailForService(data,orders);
		}

		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",new ArrayList<Object>(data.values()).get(0));
	}

	/**
	 * 获取商品订单详情
	 * @param data
	 * @param orders
	 */
	private void getOrderDetailForProduct(Map<String,JSONObject> data,List<Order> orders){
		//获取订单商品列表
		List<Long> orderIds = new ArrayList<Long>();
		for(Order order:orders){
			orderIds.add(order.getId());
		}
		List<ProductOrderDetail> orderDetails = productOrderDetailDao.selectByOrderIds(orderIds);
		//查找对应商品信息
		List<Long> productIds = new ArrayList<Long>();
		for(ProductOrderDetail orderDetal:orderDetails){
			productIds.add(orderDetal.getProductId());
		}
		List<Product> products = productDao.selectByIds(productIds);

		Map<Long,Product> productMap = new HashMap<Long,Product>();
		for(Product p:products){
			productMap.put(p.getId(), p);
		}
		//组装订单信息

		for(Order order:orders){
			if(!data.containsKey(order.getParentNum())){
				JSONObject obj = new JSONObject();
				//主订单id
				obj.put("orderId", order.getParentNum());
				//下单时间
				obj.put("createTime", order.getCreateTime());
				//实际金额
				obj.put("payAmount", order.getOrderAmount());
				//金额
				obj.put("totalAmount", order.getOrderAmount());
				//订单进度
				obj.put("process", order.getProcess());
				//收货人
				obj.put("customName", order.getCustomName());
				//收货人联系方式
				obj.put("customTel", order.getCustomTel());
				//收货人地址
				obj.put("addrDetail", order.getAddrDetail());
				//备注
				obj.put("remark", order.getRemark());
				//商品列表
				JSONArray arr = new JSONArray();
				for(ProductOrderDetail orderDetail : orderDetails){
					if(!order.getId().equals(orderDetail.getOrderId())){
						continue;
					}
					JSONObject product = new JSONObject();
					product.put("productId", orderDetail.getProductId());
					product.put("productName", orderDetail.getProductName());
					product.put("price", orderDetail.getPrice());
					product.put("buyCount", orderDetail.getBuyCount());
					product.put("payMent", orderDetail.getPayMent());
					//设置封面图片
					if(null != productMap.get(orderDetail.getProductId())){
						product.put("coverImg", productMap.get(orderDetail.getProductId()).getCoverImg());
					}
					arr.add(product);
				}
				obj.put("products", arr);

				//优惠券可抵金额
				obj.put("couponAmount", 0);
				//积分可抵金额
				obj.put("integralAmount", 0);
				//配送金额
				obj.put("distriAmount", 0);
				data.put(order.getParentNum(), obj);
			}else{
				//累加订单金额
				JSONObject obj = data.get(order.getParentNum());
				float orderAmount = obj.getFloatValue("totalAmount");
				orderAmount += order.getOrderAmount();
				obj.put("totalAmount", orderAmount);
				obj.put("payAmount", orderAmount);
				//累加商品列表
				JSONArray arr = obj.getJSONArray("products");
				for(ProductOrderDetail orderDetail : orderDetails){
					if(!order.getId().equals(orderDetail.getOrderId())){
						continue;
					}
					JSONObject product = new JSONObject();
					product.put("productId", orderDetail.getProductId());
					product.put("productName", orderDetail.getProductName());
					product.put("price", orderDetail.getPrice());
					product.put("buyCount", orderDetail.getBuyCount());
					product.put("payMent", orderDetail.getPayMent());
					//设置封面图片
					//设置封面图片
					if(null != productMap.get(orderDetail.getProductId())){
						product.put("coverImg", productMap.get(orderDetail.getProductId()).getCoverImg());
					}
					arr.add(product);
				}
				obj.put("products", arr);
				data.put(order.getParentNum(), obj);
			}
		}
	}



	/**
	 * 获取服务订单详情
	 * @param data
	 * @param orders
	 */
	private void getOrderDetailForService(Map<String,JSONObject> data,List<Order> orders){
		//获取订单商品列表
		List<Long> orderIds = new ArrayList<Long>();
		for(Order order:orders){
			orderIds.add(order.getId());
		}
		List<ServiceOrderDetail> orderDetails = serviceOrderDetailDao.selectByOrderIds(orderIds);
		//查找对应商品信息
		List<Long> serviceIds = new ArrayList<Long>();
		for(ServiceOrderDetail orderDetal:orderDetails){
			serviceIds.add(orderDetal.getServiceId());
		}
		List<com.d3sq.model.entity.Service> services = serviceDao.selectByIds(serviceIds);

		Map<Long,com.d3sq.model.entity.Service> productMap = new HashMap<Long,com.d3sq.model.entity.Service>();
		for(com.d3sq.model.entity.Service s:services){
			productMap.put(s.getId(), s);
		}
		//组装订单信息

		for(Order order:orders){
			if(!data.containsKey(order.getParentNum())){
				JSONObject obj = new JSONObject();
				//主订单id
				obj.put("orderId", order.getParentNum());
				//下单时间
				obj.put("createTime", order.getCreateTime());
				//实际金额
				obj.put("payAmount", order.getOrderAmount());
				//金额
				obj.put("totalAmount", order.getOrderAmount());
				//订单进度
				obj.put("process", order.getProcess());
				//收货人
				obj.put("customName", order.getCustomName());
				//收货人联系方式
				obj.put("customTel", order.getCustomTel());
				//收货人地址
				obj.put("addrDetail", order.getAddrDetail());
				//备注
				obj.put("remark", order.getRemark());
				//商品列表
				JSONArray arr = new JSONArray();
				for(ServiceOrderDetail orderDetail : orderDetails){
					if(!order.getId().equals(orderDetail.getOrderId())){
						continue;
					}
					JSONObject product = new JSONObject();
					product.put("productId", orderDetail.getServiceId());
					product.put("productName", orderDetail.getServiceName());
					product.put("price", orderDetail.getPrice());
					//product.put("buyCount", orderDetail.getBuyCount());
					product.put("payMent", orderDetail.getPayMent());
					//设置封面图片
					if(null != productMap.get(orderDetail.getServiceId())){
						product.put("coverImg", productMap.get(orderDetail.getServiceId()).getCoverImg());
					}
					arr.add(product);
				}
				obj.put("products", arr);

				//优惠券可抵金额
				obj.put("couponAmount", 0);
				//积分可抵金额
				obj.put("integralAmount", 0);
				//配送金额
				obj.put("distriAmount", 0);
				data.put(order.getParentNum(), obj);
			}else{
				//累加订单金额
				JSONObject obj = data.get(order.getParentNum());
				float orderAmount = obj.getFloatValue("totalAmount");
				orderAmount += order.getOrderAmount();
				obj.put("totalAmount", orderAmount);
				obj.put("payAmount", orderAmount);
				//累加商品列表
				JSONArray arr = obj.getJSONArray("products");
				for(ServiceOrderDetail orderDetail : orderDetails){
					if(!order.getId().equals(orderDetail.getOrderId())){
						continue;
					}
					JSONObject product = new JSONObject();
					product.put("productId", orderDetail.getServiceId());
					product.put("productName", orderDetail.getServiceName());
					product.put("price", orderDetail.getPrice());
					//product.put("buyCount", orderDetail.getBuyCount());
					product.put("payMent", orderDetail.getPayMent());
					//设置封面图片
					if(null != productMap.get(orderDetail.getServiceId())){
						product.put("coverImg", productMap.get(orderDetail.getServiceId()).getCoverImg());
					}
					arr.add(product);
				}
				obj.put("products", arr);
				data.put(order.getParentNum(), obj);
			}
		}
	}

	@Override
	public ResultMsg<?> getStores(String local, Long userId, String enc) {
		//验证参数
		if(null == userId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("loginName", "未登录");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功");
	}

	@Override
	public ResultMsg<?> getCoupons(String local, Long userId, String enc) {
		//验证参数
		if(null == userId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("loginName", "未登录");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功");
	}

	@Override
	public ResultMsg<?> getFavorites(String local, Long userId, String enc) {
		//验证参数
		if(null == userId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("loginName", "未登录");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功");
	}

	@Override
	public ResultMsg<?> getSettings(String local, Long userId, String enc) {
		//验证参数
		if(null == userId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("loginName", "未登录");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		List<UserSettings> list = userSettingsDao.selectByUserId(userId);
		String settings = null;
		if(!list.isEmpty()){
			settings = list.get(0).getSettings();
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",settings);
	}

	@Override
	public ResultMsg<?> getInfo(String local, JSONObject userinfo, String enc) {
		//验证参数
		if(StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}


		Long memberId = userinfo.getJSONObject("user").getLong("id");

		if(!MobileConstant.genEnc("" + memberId).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<JSONObject>(SystemConstant.RESULT_PARAM_ERROR, null,error);
		}

		ResultMsg<JSONObject> r = langService.getAuthByMember(memberId);

		//认证信息
		userinfo.getJSONObject("user").put("certFlag",r.getData().getJSONObject("user").get("certFlag"));
		userinfo.getJSONObject("user").put("auditFlag",r.getData().getJSONObject("user").get("auditFlag"));

		//只更新店铺信息和权限信息
		if(!userinfo.containsKey("shop")){
			userinfo.put("shop", r.getData().get("shop"));
		}
		userinfo.put("options", r.getData().get("options"));

		//这里应该还要同步session状态，暂时不影响业务


		return new ResultMsg<JSONObject>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功", userinfo);

	}



	@Override
	public ResultMsg<?> addAress(String local, Address address, String enc) {
		//验证参数
		if(null == address.getMemberId() || StringUtils.isEmpty(address.getFullAddr()) || StringUtils.isEmpty(address.getDetail()) || StringUtils.isEmpty(address.getContacts()) || StringUtils.isEmpty(address.getTelPhone()) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == address.getMemberId()){
				error.put("loginName", "未登录");
			}
			if(StringUtils.isEmpty(address.getFullAddr())){
				error.put("fullAddr", "全地址不能为空");
			}
			if(StringUtils.isEmpty(address.getDetail())){
				error.put("detail", "详细地址不能为空");
			}
			if(StringUtils.isEmpty(address.getContacts())){
				error.put("contacts", "联系人不能为空");
			}
			if(StringUtils.isEmpty(address.getTelPhone())){
				error.put("telPhone", "联系电话不能为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(address.getMemberId()+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		address.setCreateTime(System.currentTimeMillis());
		address.setState(SystemConstant.ENABLE);
		Long id = addressDao.insertAndReturnId(address);
		if(id > 0){
			address.setId(id);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "添加成功",address);
		}else{
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "添加失败",address);
		}
	}

}
