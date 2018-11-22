package com.d3sq.sns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.utils.StringUtils;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.BBSTopic;

@Repository
public class BBSTopicDao extends BaseDaoSupport<BBSTopic, Long> {
	
	private DynamicDataSourceEntry dynamicDataSourceEntry;

	@Override
	protected String getPKColumn() {
		return "id";
	}
	
	/**
	 * 配置动态数据源
	 * 
	 * @param dataSource
	 */
	@Resource(name = "dynamicDataSource")
	public void setDataSource(DataSource dataSource) {
		dynamicDataSourceEntry = ((DynamicDataSource) dataSource).getDataSourceEntry();
		super.setDataSourceReadOnly(dataSource);
		super.setDataSourceWrite(dataSource);
	}
	
	public Long insert(BBSTopic bbsTopic){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.insertAndReturnId(bbsTopic);
	}
	
	public BBSTopic selectTopicById(Long topicId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.get(topicId);
	}
	
	@Override
	public int update(BBSTopic entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.update(entity);
	}

	@Override
	public int delete(Object entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.delete(entity);
	}

	public boolean delete(Long bbsTopicId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.delete(bbsTopicId);
	}
	/**
	 * 查询话题相关信息
	 * @param creatorId 话题创建人id
	 * @param residentialId 社区id
	 * @param top 置顶标识
	 * @param lastBBSTopicId 最新话题id，用于分页.
	 * @param pageSize
	 * @param orderby 排序字段,默认根据最新回复时间排序
	 * @param sw 搜索字段，搜索范围(话题标题、内容、创建人)
	 * @return
	 */
	public List<Map<String, Object>> selectTopicListForPage(Long creatorId,Long residentialId,
			Long forumId,Integer top,String sw,Long lastTopicId,Integer orderFlag, Integer pageSize){
		
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(super.getTableName());
		sql.append(" where 1=1 ");
		if(null != creatorId && creatorId != 0){
			sql.append(" and creatorId = ").append(creatorId);
		}
		if(null != residentialId && residentialId != 0) {
			sql.append(" and residentialId = ").append(residentialId);
		}
		if(null != top){
			sql.append(" and top = ").append(top);
		}
		if(null != forumId){
			sql.append(" and forumId = ").append(forumId);
		}
		if(!StringUtils.isEmpty(sw)) {
			sql.append(" and (title like %").append(sw).append("%");
			sql.append(" or ");
			sql.append(" content like %").append(sw).append("%");
		}
		
		String orderBy = " createTime asc ";
		String greaterThanOrLessThanFlag = " > ";
		if(null != orderFlag && orderFlag != 0) {
			if(orderFlag == 1){
				orderBy = " createTime desc ";
				greaterThanOrLessThanFlag = " < ";
			}
			if(orderFlag == 2){
				orderBy = " lastReplyTime asc ";
				greaterThanOrLessThanFlag = " > ";
			}
			if(orderFlag == 3){
				orderBy = " lastReplyTime desc ";
				greaterThanOrLessThanFlag = " < ";
			}
		}
		if(null != lastTopicId && lastTopicId != 0){
			sql.append(" and id ").append(greaterThanOrLessThanFlag).append(lastTopicId);
		}
		sql.append(" order by ").append(orderBy);
		if(null != pageSize) {
			sql.append(" limit 0,").append(pageSize);
		}
		return super.findBySql(sql.toString(), new HashMap<String, Object>());
		
	}

	public List<Map<String, Object>> selectToipicByForumIds(List<Long> forumIds,Integer pageSize){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		if(null == forumIds || forumIds.size() == 0) return null;
		StringBuffer sql = new StringBuffer();
		for (int i = 0; i < forumIds.size(); i++) {
			long forumId = forumIds.get(i);
			sql.append("(select * from ").append(super.getTableName());
			sql.append("  where forumId = ").append(forumId);
			sql.append(" limit 0,").append(pageSize).append(") ");
			if(i < (forumIds.size() - 1)){
				sql.append(" union ");
			}
		}
		return super.findBySql(sql.toString(), new HashMap<String, Object>());
	}
}
