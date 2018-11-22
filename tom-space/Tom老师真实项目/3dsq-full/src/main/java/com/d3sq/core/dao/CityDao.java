package com.d3sq.core.dao;

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

/**
 * 全国行政区划
 * @author tanyongde
 *
 */
@Repository
public class CityDao  extends BaseDaoSupport<City, Long> {
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

	public List<City> selectAll() {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		QueryRule queryRule = QueryRule.getInstance();
		return super.find(queryRule);
	}
	
	public List<City> selectByLevelType(int levelType) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("levelType", levelType);
		return super.find(queryRule);
	}

	@Override
	public int update(City entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		return super.update(entity);
	}
	
	
	public List<City> getByParentId(Long parentId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		QueryRule queryRule = QueryRule.getInstance();
		if(null == parentId){
			queryRule.andIsNull("parentId");
		}else{
			queryRule.andEqual("parentId", parentId);
		}
		return super.find(queryRule);
	}
	

}
