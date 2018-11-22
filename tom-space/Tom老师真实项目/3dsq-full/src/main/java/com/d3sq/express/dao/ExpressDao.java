package com.d3sq.express.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Express;

@Repository
public class ExpressDao extends BaseDaoSupport<Express,Long>{

	private DynamicDataSourceEntry dynamicDataSourceEntry;

	@Override
	protected String getPKColumn() { return "id"; }

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

	@Override
	public int saveAll(List<Express> list) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_EXPRESS);
		return super.saveAll(list);
	}

	@Override
	public Long insertAndReturnId(Express entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_EXPRESS);
		return super.insertAndReturnId(entity);
	}

	@Override
	public int update(Express entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_EXPRESS);
		return super.update(entity);
	}
	
	
	
	
}
