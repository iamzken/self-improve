package com.d3sq.model.helper;

/**
 * 图片文件格式
 *
 */
public class ImageItem {
	
	private String filePath;	//图片URL
	private Long size;			//文件大小
	private Long progressId;	//上传进度ID
	private String fileName;	//文件名称
	private Long createTime;	//创建时间
	private Long creator;		//上传人
	private Integer audit;		//是否审核通过
	private Integer orderNum;	//排序
	private Integer coverFlag;	//是否为封面
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Long getProgressId() {
		return progressId;
	}
	public void setProgressId(Long progressId) {
		this.progressId = progressId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Integer getAudit() {
		return audit;
	}
	public void setAudit(Integer audit) {
		this.audit = audit;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getCoverFlag() {
		return coverFlag;
	}
	public void setCoverFlag(Integer coverFlag) {
		this.coverFlag = coverFlag;
	}
	
}
