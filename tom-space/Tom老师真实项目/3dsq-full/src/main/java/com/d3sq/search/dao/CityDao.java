package com.d3sq.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.utils.ListUtils;
import javax.core.common.utils.StringUtils;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.City;

@Repository("cCityDao")
public class CityDao extends BaseDaoSupport<City, Long> {

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
	 * 
	 * @param id
	 * @return
	 */
	public List<City> selectByPath(String xpath){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		String [] paths = xpath.split("/");
		//List<String> sqls = new ArrayList<>();
		String sql = "";
		List<String> params = new ArrayList<String>();
		for (int i = 0; i < paths.length; i ++) {
			if(StringUtils.isEmpty(paths[i])){ continue; }
			sql += "select * from " + super.getTableName() + " where id = ? ";
			if(i < paths.length-1){
				sql += " union ";
			}
			params.add(paths[i]);
		}
		
		List<Map<String, Object>> r = super.findBySql(sql, params.toArray());
		
		return JSONArray.parseArray(JSON.toJSONString(r), City.class);
	}
	
}
