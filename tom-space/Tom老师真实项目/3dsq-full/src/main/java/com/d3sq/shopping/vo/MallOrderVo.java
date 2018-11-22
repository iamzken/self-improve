package com.d3sq.shopping.vo;

import java.util.List;

import com.d3sq.center.vo.PayOrderDetailVo;

/**
 * 订单vo 
 *
 */
public class MallOrderVo {
	private String customName;    //收货人
	private String customTel;     //联系电话
	private String cityPath;      //城市path
	private Integer otype;        //商品类型
	private String addrDetail;    //收货地址
	private String remark;        //备注
	private String coupons;       //优惠券信息
	private String integral;      //积分信息
	
	
	private Long serviceId;       //服务id
	private int feeStandIndex;    //服务价格规则下标
	private int timesDualIndex;   //服务服务时段下标
	
	
	
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getCustomTel() {
		return customTel;
	}
	public void setCustomTel(String customTel) {
		this.customTel = customTel;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}
	public Integer getOtype() {
		return otype;
	}
	public void setOtype(Integer otype) {
		this.otype = otype;
	}
	public String getAddrDetail() {
		return addrDetail;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCoupons() {
		return coupons;
	}
	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public int getFeeStandIndex() {
		return feeStandIndex;
	}
	public void setFeeStandIndex(int feeStandIndex) {
		this.feeStandIndex = feeStandIndex;
	}
	public int getTimesDualIndex() {
		return timesDualIndex;
	}
	public void setTimesDualIndex(int timesDualIndex) {
		this.timesDualIndex = timesDualIndex;
	}
	
	
	
}
