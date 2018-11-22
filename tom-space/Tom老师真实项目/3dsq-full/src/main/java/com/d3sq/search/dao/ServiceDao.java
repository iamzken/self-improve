package com.d3sq.search.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Service;

@Repository("sServiceDao")
public class ServiceDao extends BaseDaoSupport<Service, Long> {
	
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

	public List<Service> selectAll() {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.getAll();
	}

	public Service selectById(Long id) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.get(id);
	}

	public List<com.d3sq.model.entity.Service> selectByIds(List<Long> servieIds) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andIn("id", servieIds);
		return super.find(queryRule);
	}
	
}
