package com.d3sq.sns.vo;

import java.util.Date;
import java.util.List;

import javax.core.common.utils.DateUtils;
import javax.core.common.utils.StringUtils;

import com.d3sq.common.constants.FieldConstant;
import com.d3sq.model.entity.BBSForum;

/**
 * 板块vo
 *
 */
public class BBSForumVo {
	
	private Long id;			//自增ID
	private Long parentId;		//父级版块
	private Long residentialId;	//小区ID
	private Integer alias;		//版块别名(见FieldConstant中定义)
	private String xpath;		//xpath;
	private String cityPath;	//所在城市
	private Integer level;		//层级
	private String title;		//版块名称
	private Long creatorId;		//版主
	private Long createTime;	//创建时间
	private Integer state;		//状态码
	private String photo;//板块创建者头像
	private String creatorName;//板块创建者名称
	private String ftime;//格式化后的时间
	
	private List<BBSTopicVo> topic;//板块话题
	
	public static BBSForumVo getInstance(BBSForum forum){
		if(null == forum) return null;
		BBSForumVo vo = new BBSForumVo();
		vo.setId(forum.getId());
		String alias = forum.getAlias();
		if(!StringUtils.isEmpty(alias)){
			if(FieldConstant.BBSFORUM_ALIAS_RESIDENTIAL.equals(alias)){
				vo.setAlias(1);
			}
			if(FieldConstant.BBSFORUM_ALIAS_PROPERTY.equals(alias)){
				vo.setAlias(2);
			}
		}
		vo.setFtime(DateUtils.distance(new Date(forum.getCreateTime()), "yyyy-MM-dd HH:mm"));
		vo.setTitle(forum.getTitle());
		vo.setCreatorId(forum.getCreatorId());
		vo.setCityPath(forum.getCityPath());
		vo.setCreateTime(forum.getCreateTime());
		vo.setCreatorId(forum.getCreatorId());
		vo.setLevel(forum.getLevel());
		vo.setParentId(forum.getParentId());
		vo.setResidentialId(forum.getResidentialId());
		vo.setState(forum.getState());
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

	public Long getResidentialId() {
		return residentialId;
	}

	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}

	public Integer getAlias() {
		return alias;
	}

	public void setAlias(Integer alias) {
		this.alias = alias;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getCityPath() {
		return cityPath;
	}

	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public List<BBSTopicVo> getTopic() {
		return topic;
	}

	public void setTopic(List<BBSTopicVo> topic) {
		this.topic = topic;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

}
