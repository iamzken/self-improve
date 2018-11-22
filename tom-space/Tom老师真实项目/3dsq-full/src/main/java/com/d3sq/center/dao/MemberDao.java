package com.d3sq.center.dao;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Member;


/**
 * 登录用户操作
 *
 */
@Repository("cmemberDao")
public class MemberDao extends BaseDaoSupport<Member, Long> {
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

	
	
//	public Long insertAndReturnId(Member entity) {
//		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
//		return super.insertAndReturnId(entity);
//	}

	
	/**
	 * 根据账号查询
	 * @param loginName
	 * @return
	 */
	public Member selectByLoginName(String loginName) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("loginName", loginName);
		
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		return super.findUnique(queryRule);
	}
	
	
	
	@Override
	public int update(Member entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		return super.update(entity);
	}
	
	/**
	 * 更新为解绑状态
	 * @param id
	 * @return
	 */
	public int updateForUnbind(Long id,Long bindId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		String sql = "update " + super.getTableName() + " set bindId = null,tel = null where id = ? or id = ?";
		java.util.List<Object> params = new ArrayList<Object>();
		params.add(id);
		params.add(bindId);
		return super.update(sql, params.toArray());
	}
	
	/**
	 * 插入并返回ID
	 */
	public Long insertAndReturnId(Member entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		return super.insertAndReturnId(entity);
	}
	
}
