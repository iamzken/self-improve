package com.d3sq.shopping.vo;

public class ServiceVo {
	private Long id;
	private Long shopId;	//店铺ID
	private String name;	//服务名称
	private String pinyin;	//拼音
	private String price;   //价格区间
	private String coverImg;//封面图片
	private Integer stock;  //库存
	private String photos;	//图片列表(JSON数组，参考ImageItem)
	private String intro;	//详情介绍(JSON数组，参考ImageItem)
	private String content;	//服务内容
	private String feeStand;//收费标准(JSON数组，参考RuleItem)
	private String timesDual;//服务时段(JSON数组,参考TimeDualItem)
	private String program; //服务流程(JSON数组，参考ListItem)
	private String promise;	//商家承诺(JSON数组，参考ListItem)
	private String notice;	//服务须知(JSON数组，参考ListItem)
	private Long creatorId;	//创建人
	private Long createTime;//创建时间
	private Integer state;	//状态码
	private String shopName;
	private Float lon;
	private Float lat;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFeeStand() {
		return feeStand;
	}
	public void setFeeStand(String feeStand) {
		this.feeStand = feeStand;
	}
	public String getTimesDual() {
		return timesDual;
	}
	public void setTimesDual(String timesDual) {
		this.timesDual = timesDual;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getPromise() {
		return promise;
	}
	public void setPromise(String promise) {
		this.promise = promise;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
