package com.d3sq.sns.vo;

import java.util.Date;

import javax.core.common.utils.DateUtils;
import javax.core.common.utils.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.model.entity.BBSTopic;
import com.d3sq.model.entity.Member;

/**
 * 话题vo
 *
 */
public class BBSTopicVo {
	
	private Long id;
	private String title;//标题
	private String content;//内容
	private JSONObject contentImgs;//内容中的图片
	private Long createTime;//创建日期
	private Long updateTime;//话题更新时间
	private String updateText;//更新提示
	private Long creatorId;//话题创建人id
	private Long readPersonCount;//阅读人数
	private Long lastReplyUid;//最后回复人的id
	private String lastReplyName;//最后回复人名称
	private String lastReplyContent;//最后回复内容
	private JSONObject lastReplyContentImgs;//最后回复内容中包含的图片
	private Long lastReplyTime;//最后回复时间
	private Integer top;//置顶标识 （0：否；1：是） （插入时默认为0）
	private Integer choice;//精华贴（0：否；1：是） （插入时默认为0）
	private Integer prohibit;// 审核（ 0：未审核；1：已审核；2：已删除；3：已锁定 ） （插入时默认为0）
	private Integer stick;// 话题结贴标识 （ 0：正常；1：结贴 ） （插入时默认为0）
	private Long residentialId;//社区id
	private String cityPath;//城市path
	private String forumPath;//板块的path
	private Float lat;//纬度
	private Float lon;//经度
	private Long forumId;//板块id
	private Member member;
	private Long replyCount;
	private Integer praiseCount;//点赞数量
	private Integer praise = 0;//是否点赞标识,默认为未点赞
	private String ftime;//格式话后的发布时间
	
	
	
	public static BBSTopicVo getInstance(BBSTopic topic,Member member){
		
		if(null == topic) return null;
		
		BBSTopicVo vo = new BBSTopicVo();
		
		vo.setId(topic.getId());
		vo.setTitle(topic.getTitle());
		vo.setContent(topic.getContent());
		String contentImgs = topic.getContentImgs();
		if(!StringUtils.isEmpty(contentImgs)){
			vo.setContentImgs(JSONObject.parseObject(contentImgs));
		}
		vo.setReplyCount(topic.getReplyCount());
		vo.setForumPath(topic.getForumPath());
		vo.setCreatorId(topic.getCreatorId());
		vo.setCreateTime(topic.getCreateTime());
		vo.setUpdateTime(topic.getUpdateTime());
		vo.setPraiseCount(topic.getPraiseCount());
		vo.setLastReplyUid(topic.getLastReplyUid());
		vo.setLastReplyContent(topic.getLastReplyContent());
		String lastReplyContentImgs = topic.getLastReplyContentImgs();
		if(!StringUtils.isEmpty(lastReplyContentImgs)){
			vo.setLastReplyContentImgs(JSONObject.parseObject(lastReplyContentImgs));
		}
		vo.setLastReplyTime(topic.getLastReplyTime());
		vo.setLastReplyName(topic.getLastReplyName());
		
		vo.setTop(topic.getTop());
		vo.setChoice(topic.getChoice());
		vo.setProhibit(topic.getProhibit());
		vo.setStick(topic.getStick());
		vo.setResidentialId(topic.getResidentialId());
		vo.setReadPersonCount(topic.getReadPersonCount());
		vo.setCityPath(topic.getCityPath());
		vo.setLat(topic.getLat());
		vo.setLon(topic.getLon());
		vo.setForumId(topic.getForumId());
		vo.setForumPath(topic.getForumPath());
		vo.setFtime(DateUtils.distance(new Date(topic.getCreateTime()), "yyyy-MM-dd HH:mm"));
		vo.setMember(member);
		return vo;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public JSONObject getContentImgs() {
		return contentImgs;
	}

	public void setContentImgs(JSONObject contentImgs) {
		this.contentImgs = contentImgs;
	}

	public void setLastReplyContentImgs(JSONObject lastReplyContentImgs) {
		this.lastReplyContentImgs = lastReplyContentImgs;
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
	public JSONObject getLastReplyContentImgs() {
		return lastReplyContentImgs;
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
	public Long getResidentialId() {
		return residentialId;
	}
	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getUpdateText() {
		return updateText;
	}

	public void setUpdateText(String updateText) {
		this.updateText = updateText;
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

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	
}
