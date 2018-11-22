package com.d3sq.core.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Residential;

/**
 * 居民小区操作
 *
 */
@Repository
public class ResidentialDao  extends BaseDaoSupport<Residential, Long> {
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
	 * 保存小区信息
	 */
	@Override
	public int saveAll(List<Residential> list) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		return super.saveAll(list);
	}
}
