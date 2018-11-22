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
import com.d3sq.model.entity.MallCart;

@Repository
public class CartDao extends BaseDaoSupport<MallCart,Long>{
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

	public MallCart selectByMemberId(Long memberId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		/*Map<String, Object> pamam = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  tmc.id,tmc.productId,tmc.shopId,tmc.unitPrice,tmc.buyCount,tmc.totalAmount,ts.name as shopName,tp.name,tp.coverImg from t_mall_cart tmc,t_shop ts,t_product tp WHERE tmc.shopId=ts.id AND tmc.productId=tp.id");
		sql.append(" AND tmc.memberId=:memberId");
		System.out.println(sql.toString());
		pamam.put("memberId", userId);*/
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("memberId", memberId);
		return super.findUnique(queryRule);
	}
	
	public Long insertAndReturnId(MallCart cart){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.insertAndReturnId(cart);
	}
	
	public boolean delete(Long id){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.delete(id);
	}

	public MallCart selectById(Long id) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.get(id);
	}
	
	public int update(MallCart cart){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.update(cart);
	}

	public List<MallCart> selectByMemberIdAndProduct(Long memberId, Long productId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("memberId", memberId);
		queryRule.andEqual("productId", productId);
		return super.find(queryRule);
	}

	public boolean deleteByMemberIdAndProductId(Long memberId, Long prodcutId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		String sql = "delete from t_mall_cart where memberId=:memberId and productId=:prodcutId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		paramMap.put("prodcutId", prodcutId);
		return super.update(sql, paramMap) > 0?true : false;
	}
}
