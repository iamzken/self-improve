package com.d3sq.sns.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.core.common.utils.StringUtils;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.BBSReply;

@Repository
public class BBSReplyDao extends BaseDaoSupport<BBSReply, Long> {

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

	public Long insert(BBSReply reply){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.insertAndReturnId(reply);
	}
	
	public BBSReply selectReplyById(Long replyId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.get(replyId);
	}
	
	@Override
	public int update(BBSReply entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.update(entity);
	}

	@Override
	public int delete(Object entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.delete(entity);
	}
	

	@Override
	public int deleteAll(List<BBSReply> list) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.deleteAll(list);
	}
	public boolean delete(Long replyId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		return super.delete(replyId);
	}
	/**
	 * 通过xpath获取子回复
	 * @param xpath
	 * @return
	 */
	public List<BBSReply> selectReplyByXpath(String xpath){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		if(StringUtils.isEmpty(xpath)) return null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andLike("xpath", xpath+"%");
		return super.find(queryRule);
	}
	
	public BBSReply selectEqXpath(String xpath){
		if(StringUtils.isEmpty(xpath)) return null;
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("xpath", xpath);
		return super.findUnique(queryRule);
	}
	
	/**
	 * 查询回复
	 * @param topicId
	 * @param xpath
	 * @param parentId
	 * @param level
	 * @param orderFlag
	 * @param lastId
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> selectReplyListForPage(Long topicId,String xpath,Long parentId,Integer level,
			Integer orderFlag,Long lastId,Integer pageSize){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(super.getTableName());
		sql.append(" where 1=1 ");
		if(null != topicId) {
			sql.append(" and topicId = ").append(topicId);
		}
		if(!StringUtils.isEmpty(xpath)){
			sql.append(" and xpath like '").append(xpath+"%'");
			sql.append(" and xpath != '").append(xpath+"'");
		}
		if(null != parentId){
			sql.append(" and parentId = ").append(parentId);
		}
		if(null != level){
			sql.append(" and level = ").append(level);
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
	
	public List<Map<String, Object>> selectSecondReplyList(List<String> xpathList,Integer replySize){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		if(null == xpathList || xpathList.size() == 0) return null;
		StringBuffer sql = new StringBuffer();
		for (int i = 0; i < xpathList.size(); i++) {
			String xpath = xpathList.get(i);
			sql.append("(select * from ").append(super.getTableName());
			sql.append(" where xpath like '").append(xpath+"%'");
			sql.append(" and xpath != '").append(xpath+"'");
			sql.append(" limit 0,").append(replySize).append(") ");
			if(i < (xpathList.size() - 1)){
				sql.append(" union ");
			}
		}
		return super.findBySql(sql.toString(), new HashMap<String, Object>());
	}
	
	public boolean deleteByTopicId(Long topicId){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SNS);
		String sql = "delete from "+super.getTableName()+" where topicId=:topicId ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("topicId", topicId);
		return super.update(sql, paramMap) > 0?true : false;
	}
}
