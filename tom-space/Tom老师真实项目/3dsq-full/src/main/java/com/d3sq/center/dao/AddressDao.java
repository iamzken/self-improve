package com.d3sq.center.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Address;

@Repository
public class AddressDao extends BaseDaoSupport<Address, Long>{
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

	public List<Address> selectByUserId(Long userId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("memberId", userId);
		return super.find(queryRule);
	}
	
	public Long insertAndReturnId(Address address){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.insertAndReturnId(address);
	}
}
