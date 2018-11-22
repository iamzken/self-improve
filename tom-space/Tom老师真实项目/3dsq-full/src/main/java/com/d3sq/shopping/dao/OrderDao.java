package com.d3sq.shopping.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Order;


@Repository
public class OrderDao extends BaseDaoSupport<Order, Long>{
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

	public List<Order> selectByUserIdAndProcess(Long userId, Integer process) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("fromMemberId", userId);
		if(null != process && process.intValue() > 0){
			queryRule.andEqual("process", process);
		}
		return super.find(queryRule);
	}
	
	
	public List<Order> selectByUserIdAndProcessAndOtype(Long userId, Integer process,Integer otype) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("fromMemberId", userId);
		queryRule.andEqual("otype", otype);
		if(null != process && process.intValue() > 0){
			queryRule.andEqual("process", process);
		}
		return super.find(queryRule);
	}

	public Long insertAndReturnId(Order payOrder) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.insertAndReturnId(payOrder);
	}
	
	public int update(Order payOrder) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.update(payOrder);
	}
	
	public boolean delete(Long id) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.delete(id);
	}

	public List<Order> selectByParentNum(String parentNum) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("parentNum", parentNum);
		return super.find(queryRule);
	}

	public List<Order> selectByOrderNum(String orderId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("num", orderId);
		return super.find(queryRule);
	}
}
