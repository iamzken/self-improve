package com.d3sq.shopping.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
//import com.d3sq.core.plugin.queue.annotation.QueueTarget;
//import com.d3sq.core.plugin.queue.annotation.QueueTask;
//import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.model.entity.Product;

@Repository
public class ProductDao extends BaseDaoSupport<Product, Long> {
	
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

	public Long insert(Product prodcut) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.insertAndReturnId(prodcut);
	}

	public List<Product> getByIds(List<Long> productIds) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andIn("id", productIds);
		return super.find(queryRule);
	}

	public Product getById(Long productId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.get(productId);
	}

	public List<Product> selectAll() {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.getAll();
	}

	public List<Map<String, Object>> selectByCatalogueIdForPage(
			String catalogueId, Long lastProductId, Integer pageSize) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select tp.*,ts.title,ts.longitude,ts.latitude from t_product tp,t_kind_product tkp,t_shop ts where tp.id=tkp.productId and tp.shopId=ts.id");
		if(null != lastProductId && lastProductId > 0){
			sql.append(" and tp.id > ").append(lastProductId);
		}
		
		sql.append(" and  kindPath like '").append(catalogueId).append("%'");
		sql.append(" limit 0,").append(pageSize);
//		System.out.println(sql.toString());
		return super.findBySql(sql.toString(), new HashMap<String, Object>());
	}

	@Override
	public int update(Product entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.update(entity);
	}
	
	public List<Product> selectByPtype(Integer ptype) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("ptype", ptype);
		return super.find(queryRule);
	}

	public List<Product> selectByIds(List<Long> productIds) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andIn("id", productIds);
		return super.find(queryRule);
	}
	
	
//	public List<Product> selectByPtype(Integer ptype,
//			@QueueTarget(idField="id",alias="public",opt=QueueTarget.OPT_ADD) Object o) {
//		return null;
//	}
}
