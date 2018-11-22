package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 社区话题
 *
 */
@Entity
@Table(name="t_bbs_topic")
public class BBSTopic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2058291505347392981L;
	
	
	private Long id;
	private String title;//标题
	private String content;//内容
	private String contentImgs;//内容中的图片（JSON数组，内容格式参考ImageItem）
	private Long createTime;//创建日期
	private Long updateTime;//话题更新时间
	private Long updateUid;//更新话题的用户Id
	private Long creatorId;//话题创建人id
	private Long readPersonCount;//阅读人数
	private Long lastReplyUid;//最后回复人的id
	private String lastReplyName;//最后回复人名称
	private String lastReplyContent;//最后回复内容
	private String lastReplyContentImgs;//最后回复内容中包含的图片
	private Long lastReplyTime;//最后回复时间
	private Integer top;//置顶标识 （0：否；1：是） （插入时默认为0）
	private Integer choice;//精华贴（0：否；1：是） （插入时默认为0）
	private Integer prohibit;// 审核（ 0：未审核；1：已审核；2：已删除；3：已锁定 ） （插入时默认为0）
	private Integer stick;// 话题结贴标识 （ 0：正常；1：结贴 ） （插入时默认为0）
	private String cityPath;//城市path
	private Long residentialId;//社区id
	private String forumPath;//板块的path
	private Float lat;//纬度
	private Float lon;//经度
	private Long forumId;//板块id
	private Long replyCount;//回复数量
	private String praiseUser;//点赞用户
	private Integer praiseCount;//点赞数量
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getResidentialId() {
		return residentialId;
	}
	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentImgs() {
		return contentImgs;
	}
	public void setContentImgs(String contentImgs) {
		this.contentImgs = contentImgs;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long getReadPersonCount() {
		return readPersonCount;
	}
	public void setReadPersonCount(Long readPersonCount) {
		this.readPersonCount = readPersonCount;
	}
	public Long getLastReplyUid() {
		return lastReplyUid;
	}
	public void setLastReplyUid(Long lastReplyUid) {
		this.lastReplyUid = lastReplyUid;
	}
	public String getLastReplyName() {
		return lastReplyName;
	}
	public void setLastReplyName(String lastReplyName) {
		this.lastReplyName = lastReplyName;
	}
	public String getLastReplyContent() {
		return lastReplyContent;
	}
	public void setLastReplyContent(String lastReplyContent) {
		this.lastReplyContent = lastReplyContent;
	}
	public String getLastReplyContentImgs() {
		return lastReplyContentImgs;
	}
	public void setLastReplyContentImgs(String lastReplyContentImgs) {
		this.lastReplyContentImgs = lastReplyContentImgs;
	}
	public Long getLastReplyTime() {
		return lastReplyTime;
	}
	public void setLastReplyTime(Long lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getChoice() {
		return choice;
	}
	public void setChoice(Integer choice) {
		this.choice = choice;
	}
	public Integer getProhibit() {
		return prohibit;
	}
	public void setProhibit(Integer prohibit) {
		this.prohibit = prohibit;
	}
	public Integer getStick() {
		return stick;
	}
	public void setStick(Integer stick) {
		this.stick = stick;
	}
	public Long getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(Long updateUid) {
		this.updateUid = updateUid;
	}
	public String getForumPath() {
		return forumPath;
	}
	public void setForumPath(String forumPath) {
		this.forumPath = forumPath;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	public Long getForumId() {
		return forumId;
	}
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
	public Long getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Long replyCount) {
		this.replyCount = replyCount;
	}
	public String getPraiseUser() {
		return praiseUser;
	}
	public void setPraiseUser(String praiseUser) {
		this.praiseUser = praiseUser;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	
}
