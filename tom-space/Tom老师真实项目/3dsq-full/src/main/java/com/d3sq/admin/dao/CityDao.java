package com.d3sq.admin.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.City;

@Repository("aCityDao")
public class CityDao extends BaseDaoSupport<City, Long> {

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
	
	public City selectOne(String name){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("name", name);
		List<City> result = super.find(queryRule);
		if(null == result || 0 == result.size()){
			return null;
		}
		return result.get(0);
	}
	
	
}
