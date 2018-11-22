package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 交易订单表
 *
 */
@Entity
@Table(name="t_order")
public class Order implements Serializable {
	
	private Long id;			//自增ID
	private String num;			//订单号共18位，生成规则前缀加数字，1位:前缀C，2位交易类型，3-10位年月日,11-18位，交易序列号，例如C12015051800000009
	private String parentNum;   //主订单号
	private String payNum;		//支付服务商生成的订单号
	private String oname;		//订单名称
	private Long shopId;		//店铺ID
	private String productId;	//商品ID
	private	Integer otype;		//交易类型1：未知分类，2：购买商品支付，3:会员充值，4：物业费，5：快递邮费，6：充话费,7:预约服务支付
	private Integer payType;	//支付类型1：支付宝，2：微信，3:银行卡，4：现金支付
	private Float orderAmount;	//订单应该支付金额
	private Float payAmount;	//实际支付金额
	private Integer productCount;//包含商品数
	private Long fromMemberId;	//付款人
	private Long toMemberId;	//收款人
	private Integer process;	//订单状态1：已下单未付款，2：已付款待确认，3：已确认待送货，4：正在送货待收货，5：已收货，完成交易
	private Long addrId;		//收货地址
	private String cityPath;	//城市路径
	private String addrDetail;	//收货地址详细说明
	private Long estimatTime;	//预计送达时间
	private Long finishTime;	//实际完成时间
	private String remark;		//备注信息
	private String invoiceNum;	//发票号码
	private	String	sellerTel;	//商家电话
	private String sellerName;	//商家姓名
	private String customTel;	//客户电话
	private String customName;	//客户姓名
	private Long createTime;	//交易时间
	private Integer state;		//订单状态1：可用，0：禁用
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getParentNum() {
		return parentNum;
	}
	public void setParentNum(String parentNum) {
		this.parentNum = parentNum;
	}
	public String getPayNum() {
		return payNum;
	}
	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getOtype() {
		return otype;
	}
	public void setOtype(Integer otype) {
		this.otype = otype;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Float getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Float orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Float getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getFromMemberId() {
		return fromMemberId;
	}
	public void setFromMemberId(Long fromMemberId) {
		this.fromMemberId = fromMemberId;
	}
	public Long getToMemberId() {
		return toMemberId;
	}
	public void setToMemberId(Long toMemberId) {
		this.toMemberId = toMemberId;
	}
	public Integer getProcess() {
		return process;
	}
	public void setProcess(Integer process) {
		this.process = process;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getAddrId() {
		return addrId;
	}
	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}
	public String getAddrDetail() {
		return addrDetail;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public Long getEstimatTime() {
		return estimatTime;
	}
	public void setEstimatTime(Long estimatTime) {
		this.estimatTime = estimatTime;
	}
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public String getSellerTel() {
		return sellerTel;
	}
	public void setSellerTel(String sellerTel) {
		this.sellerTel = sellerTel;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getCustomTel() {
		return customTel;
	}
	public void setCustomTel(String customTel) {
		this.customTel = customTel;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}
	
}
