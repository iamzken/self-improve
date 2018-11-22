package com.d3sq.shopping.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.Kind;

@Repository
public class KindDao extends BaseDaoSupport<Kind, Long> {
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

	public List<Map<String, Object>> selectByAlias(String alias) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		Map<String, Object> pamam = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.id,t.name,t.xpath,t.parentId FROM t_kind t WHERE 1=1");
		/*if(null != parentId){
			sql.append(" AND t.parentId=:parentId");
			pamam.put("parentId", parentId);
		}*/
		sql.append(" AND t.alias=:alias");
		pamam.put("alias", alias);
		sql.append(" AND t.state=").append(SystemConstant.ENABLE);
		sql.append(" ORDER BY t.orderNum");
		return super.findBySql(sql.toString(), pamam);
	}
	
	
	public List<Map<String, Object>> selectByParentId(Long parentId) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		Map<String, Object> pamam = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.xpath as id,t.name FROM t_kind t WHERE 1=1");
		sql.append(" AND t.parentId=:parentId AND t.id <> t.parentId");
		pamam.put("parentId", parentId);
		sql.append(" AND t.state=").append(SystemConstant.ENABLE);
		sql.append(" ORDER BY t.orderNum");
		return super.findBySql(sql.toString(), pamam);
	}
}
