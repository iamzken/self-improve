package com.d3sq.sns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.BBSForum;

@Repository
public class BBSForumDao extends BaseDaoSupport<BBSForum, Long> {
	
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
	
	public Long insert(BBSForum forum){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.insertAndReturnId(forum);
	}
	
	public BBSForum selectForumById(Long forumId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.get(forumId);
	}
	
	@Override
	public int update(BBSForum entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.update(entity);
	}

	@Override
	public int delete(Object entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.delete(entity);
	}
	

	@Override
	public int deleteAll(List<BBSForum> list) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.deleteAll(list);
	}
	public boolean delete(Long forumId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.delete(forumId);
	}
	
	public List<Map<String, Object>> selectFornumList(Long residentialId,Integer typeFlag,Integer orderFlag,Long lastId,Integer pageSize){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(super.getTableName());
		sql.append(" where 1=1 ");
		if(null != residentialId && residentialId != 0){
			sql.append(" and residentialId = ").append(residentialId);
		}
		if(null != typeFlag){
			if(typeFlag == 1){
				sql.append(" and alias = ").append(FieldConstant.BBSFORUM_ALIAS_RESIDENTIAL);
			}else if(typeFlag == 2){
				sql.append(" and alias = ").append(FieldConstant.BBSFORUM_ALIAS_PROPERTY);
			}
		}
		String orderBy = " createTime asc ";
		String greaterThanOrLessThanFlag = " > ";
		if(null != orderFlag && orderFlag != 0){
			if(orderFlag == 1) {
				orderBy = " createTime desc ";
				greaterThanOrLessThanFlag = " < ";
			}
		}
		if(null != lastId && lastId != 0){
			sql.append(" and id ").append(greaterThanOrLessThanFlag).append(lastId);
		}
		sql.append(" order by ").append(orderBy);
		
		if(null != pageSize) {
			sql.append(" limit 0,").append(pageSize);
		}
		return super.findBySql(sql.toString(), new HashMap<String, Object>());
	}

}
