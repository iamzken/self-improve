package com.d3sq.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.core.common.utils.ListUtils;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Member;
import com.d3sq.model.entity.Shop;

/**
 * 跟全系统相关的数据持久操作
 *
 */
@Repository
public class RequiredDao {

	Logger log = Logger.getLogger(this.getClass());
	
	private JdbcTemplate jdbc;
	private DynamicDataSourceEntry dynamicDataSourceEntry;
	
	/**
	 * 设定数据源
	 * @param dataSource
	 */
	@Resource(name="dynamicDataSource")
	public void setDataSource(DataSource dataSource){
		dynamicDataSourceEntry = ((DynamicDataSource) dataSource).getDataSourceEntry();
		jdbc = new JdbcTemplate(dataSource);
	}
	
//	/**
//	 * 获取登录用户的权限
//	 * @param id
//	 * @return
//	 */
//	public Map<String,Object> selectAuthByMemberId(Long id){
//		return null;
//	}
//	
//	/**
//	 * 根据用户名获取权限
//	 * @param loginName
//	 * @return
//	 */
//	public Map<String,Object> selectAuthByUserId(Long id){
//		
//		Map<String,Object> result = new HashMap<String,Object>();
//		
//		/** 获取角色列表   */
//		StringBuffer roleSql = new StringBuffer();
//		roleSql.append("select r.id as id ");				//角色ID
//		roleSql.append(" ,r.name as roleName");			//角色名称
//		roleSql.append(" ,r.discription as discription");	//角色描述
//		roleSql.append(" ,r.root as is_root");				//是否超级管理员
//		roleSql.append(" ,r.options as options");		//角色权限
//		roleSql.append(" from t_role r left join t_user_role ur on r.id = ur.roleId where ur.userId = ?");
//		
//		StringBuffer companySql = new StringBuffer();
//		companySql.append("select c.*");				//角色ID
//		companySql.append(" from t_company c left join t_user u on u.companyId = c.id where u.id = ?");
//		
//		dynamicDataSourceEntry.set(DataSourceConstant.DB_ADMIN);
//		List<Map<String,Object>> roles = jdbc.queryForList(roleSql.toString(),id);
//		
//		try{
//			Map<String,Object> company = jdbc.queryForMap(companySql.toString(),id);
//			result.put("company", company);//机构信息
//		}catch(Exception e){
//			
//		}
//		result.put("roles", roles); //角色列表
//		
//		result.put("sites", new Object());//站点
//		
//		result.put("options", new Object());//权限
//		
//		return result;
//	}
	
	/**
	 * 获取我的店铺
	 * @param memberId
	 * @return
	 */
	public Shop selectMyShop(Long memberId){
		String sql = "select * from t_shop where creatorId = ?";
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		List<Map<String,Object>> r = jdbc.queryForList(sql,memberId);
		if(null == r || r.size() == 0){
			return null;
		}
		Shop shop = JSON.parseObject(JSON.toJSONString(r.get(0)), Shop.class);
		return shop;
	}
	
	/**
	 * 删除我的店铺
	 * @param memberId
	 * @return
	 */
	public boolean deleteMyShop(Long memberId){
		String sql = "delete from t_shop where creatorId = ?";
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return jdbc.update(sql,memberId) > 0;
	}
	
	
	/**
	 * 根据用户ID列表，获取用户信息
	 * @param ids
	 * @return
	 */
	public List<Member> selectMemberByIds(List<Long> ids){
		if(null == ids || ids.size() == 0){
			return new ArrayList<Member>();
		}
		
		String sql = "select id,mtype,loginName,nickName,photo,sex,tel,store,level,state,certFlag,auditFlag from t_member ";
		sql += " where id in (" + ListUtils.join(ids, ",") + ")";
		
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		List<Map<String,Object>> r = jdbc.queryForList(sql);
		List<Member> result = JSONArray.parseArray(JSON.toJSONString(r), Member.class);
		return result;
	}
	
	/**
	 * 获取单个用户信息
	 * @param id
	 * @return
	 */
	public Member selectMemberById(Long id){
		String sql = "select id,mtype,loginName,nickName,photo,sex,tel,store,level,state,certFlag,auditFlag from t_member where id = ?";
		
		dynamicDataSourceEntry.set(DataSourceConstant.DB_PASSPORT);
		Map<String,Object> result = jdbc.queryForMap(sql,id);
		Member member = JSON.parseObject(JSON.toJSONString(result), Member.class);
		return member;
	}
	
}
