package com.d3sq.shopping.dao;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.KindProduct;

@Repository
public class KindProductDao extends BaseDaoSupport<KindProduct, Long> {
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
	
	public Long insertAndReturnId(KindProduct kindProduct){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.insertAndReturnId(kindProduct);
	}
}
