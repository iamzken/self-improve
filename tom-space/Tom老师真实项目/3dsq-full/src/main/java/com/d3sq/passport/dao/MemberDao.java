package com.d3sq.passport.dao;

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
@Repository
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
	
	
	/**
	 * 根据身份证号查询
	 * @param loginName
	 * @return
	 */
	public Member selectByIdCard(String idCardNum) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("idCardNum", idCardNum);
		
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		return super.findUnique(queryRule);
	}
	
	
	/**
	 * 根据营业执照查询
	 * @param loginName
	 * @return
	 */
	public Member selectByLicense(String licenseNum) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("licenseNum", licenseNum);
		
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		return super.findUnique(queryRule);
	}
	
	
	/**
	 * 根据用户ID查询用户信息
	 * @param loginName
	 * @return
	 */
	public Member selectById(Long id) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("id", id);
		
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		return super.findUnique(queryRule);
	}
	
	@Override
	public int update(Member entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		return super.update(entity);
	}
	
	@Override
	public Long insertAndReturnId(Member entity) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		return super.insertAndReturnId(entity);
	}
	
}
