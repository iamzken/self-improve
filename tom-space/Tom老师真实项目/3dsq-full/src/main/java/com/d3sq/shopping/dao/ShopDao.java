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
import com.d3sq.model.entity.Shop;

@Repository
public class ShopDao extends BaseDaoSupport<Shop, Long> {
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

	public Long insert(Shop shop) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.insertAndReturnId(shop);
	}

	public List<Shop> selectAll() {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.getAll();
	}

	public int saveAll(List<Shop> list) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.saveAll(list);
	}

	public Shop selectById(Long id) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.get(id);
	}

	public List<Shop> selectByIds(List<Long> shopIds) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andIn("id", shopIds);
		return super.find(queryRule);
	}

	
	
}
