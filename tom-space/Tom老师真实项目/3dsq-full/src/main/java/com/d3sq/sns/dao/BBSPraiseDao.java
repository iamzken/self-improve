package com.d3sq.sns.dao;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.BBSPraise;

@Repository
public class BBSPraiseDao extends BaseDaoSupport<BBSPraise, Long> {
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

	public Long insert(BBSPraise reply){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.insertAndReturnId(reply);
	}
	
	public BBSPraise selectReplyById(Long praiseId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.get(praiseId);
	}
	
	@Override
	public int update(BBSPraise entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.update(entity);
	}

	@Override
	public int delete(Object entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.delete(entity);
	}

	public BBSPraise selectOnePraise(Integer type,Long uid,Long topicId,Long replyId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		if(null == type || null == uid || (null == topicId && null == replyId)) return null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("type", type);
		queryRule.andEqual("uid", uid);
		if(null != topicId){
			queryRule.andEqual("topicId", topicId);
		}
		if(null != replyId){
			queryRule.andEqual("replyId", replyId);
		}
		return super.findUnique(queryRule);
	}
}
