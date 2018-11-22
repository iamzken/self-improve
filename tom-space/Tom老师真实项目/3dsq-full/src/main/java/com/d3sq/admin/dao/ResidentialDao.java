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
import com.d3sq.model.entity.Residential;

@Repository("aResidentialDao")
public class ResidentialDao extends BaseDaoSupport<Residential, Long>{
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
	
	public Long insertAndReturnId(Residential entity){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		return super.insertAndReturnId(entity);
	}
	
	public Residential selectByNameForCity(String cityPath,String name){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andLike("cityPath", "%" + cityPath + "%");
		queryRule.andEqual("shortName", name);
		List<Residential> r = super.find(queryRule);
		if(null == r || 0 == r.size()){
			return null;
		}
		return r.get(0);
	}
	
}
