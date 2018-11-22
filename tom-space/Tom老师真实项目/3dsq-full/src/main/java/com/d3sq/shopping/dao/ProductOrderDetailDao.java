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
import com.d3sq.model.entity.ProductOrderDetail;


@Repository
public class ProductOrderDetailDao extends BaseDaoSupport<ProductOrderDetail, Long>{
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

	public int saveAll(List<ProductOrderDetail> payOrderDetails) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.saveAll(payOrderDetails);
	}

	public List<ProductOrderDetail> selectByOrderId(Long orderId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("orderId", orderId);
		return super.find(queryRule);
	}

	public List<ProductOrderDetail> selectByOrderIds(List<Long> orderIds) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andIn("orderId", orderIds);
		return super.find(queryRule);
	}
}
