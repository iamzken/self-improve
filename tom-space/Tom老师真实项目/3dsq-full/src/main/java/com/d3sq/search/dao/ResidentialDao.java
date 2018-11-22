package com.d3sq.search.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Residential;

@Repository("sResidentialDao")
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
	
	/**
	 * 查询全部小区
	 * @return
	 */
	public List<Residential> selectAll(){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		return super.getAll();
	}
	
	
	/**
	 * 根据小区ID，获取小区信息
	 * @param id
	 * @return
	 */
	public Residential selectById(Long id){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		return super.get(id);
	}
}
