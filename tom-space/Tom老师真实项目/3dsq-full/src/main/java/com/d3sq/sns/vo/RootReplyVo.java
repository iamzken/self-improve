package com.d3sq.sns.vo;

import java.util.Date;
import java.util.List;

import javax.core.common.utils.DateUtils;
import javax.core.common.utils.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.model.entity.BBSReply;

public class RootReplyVo {
	
	private Long id;//回复主键标识
	private Long parentId;//父级回复id
	private Integer level;//回复的楼层
	private Long topicId;//话题id
	private String updateText;
	private String content;//回复内容
	private JSONObject contentImgs;//回复图片
	private Long createTime;//回复的创建时间
	private String ftime;//格式化后的日期
	private Long updateTime;//回复的更新时间
	private Long creatorId;//回复创人id
	private String creatorName;//回复创建人名称
	private String photo;//用户头像
	private Long replyToUid;//被回复人的id
	private ReplyTo replyTo;//回复的人
	private Integer rootReplyCount;//针对一级回复的数量
	private Integer residualReplyCount;//剩余的回复数
	private Integer praiseCount;//点赞数量
	private Integer praise = 0;//是否点赞标识,默认为未点赞
	private List<RootReplyVo> secondReply;//子集回复

	
	
	
	public static RootReplyVo getInstance(BBSReply reply){
		if(null == reply) return null;
		RootReplyVo vo = new RootReplyVo();
		vo.setId(reply.getId());
		vo.setParentId(reply.getParentId());
		vo.setLevel(reply.getLevel());
		vo.setTopicId(reply.getTopicId());
		vo.setContent(reply.getContent());
		vo.setPraiseCount(reply.getPraiseCount());
		String contentImgs = reply.getContentImgs();
		if(!StringUtils.isEmpty(contentImgs)){
			vo.setContentImgs(JSONObject.parseObject(contentImgs));
		}
		vo.setCreatorId(reply.getCreatorId());
		vo.setCreateTime(reply.getCreateTime());
		vo.setFtime(DateUtils.distance(new Date(reply.getCreateTime()), "yyyy-MM-dd HH:mm"));
		vo.setUpdateTime(reply.getUpdateTime());
		vo.setRootReplyCount(reply.getRootReplyCount());
		vo.setReplyToUid(reply.getReplyToUid());
		return vo;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public String getUpdateText() {
		return updateText;
	}
	public void setUpdateText(String updateText) {
		this.updateText = updateText;
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
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<RootReplyVo> getSecondReply() {
		return secondReply;
	}

	public void setSecondReply(List<RootReplyVo> secondReply) {
		this.secondReply = secondReply;
	}

	public Integer getRootReplyCount() {
		return rootReplyCount;
	}

	public void setRootReplyCount(Integer rootReplyCount) {
		this.rootReplyCount = rootReplyCount;
	}

	public Integer getResidualReplyCount() {
		return residualReplyCount;
	}

	public void setResidualReplyCount(Integer residualReplyCount) {
		this.residualReplyCount = residualReplyCount;
	}

	public Long getReplyToUid() {
		return replyToUid;
	}

	public void setReplyToUid(Long replyToUid) {
		this.replyToUid = replyToUid;
	}

	public ReplyTo getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(ReplyTo replyTo) {
		this.replyTo = replyTo;
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

	
	
}
