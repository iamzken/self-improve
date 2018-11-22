package com.d3sq.core.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.utils.DataUtils;
import javax.core.common.utils.StringUtils;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
import com.d3sq.model.entity.SysSequence;


/**
 * 生成业务代码
 *
 */
@Repository
public class SequenceDao extends BaseDaoSupport<SysSequence, String>{

	private Logger log = Logger.getLogger(SequenceDao.class);
	private final String PK_COLUMN = "businessCode";
	
	private DynamicDataSourceEntry dynamicDataSourceEntry;
	
	@Resource(name="dynamicDataSource")
	public void setDataSource(DataSource dataSource) {
		dynamicDataSourceEntry = ((DynamicDataSource) dataSource).getDataSourceEntry();
		super.setDataSourceReadOnly(dataSource);
		super.setDataSourceWrite(dataSource);
	}

	protected String getPKColumn() {
		return PK_COLUMN;
	}

	private final String prefix = "";//前缀
	private final String splitFlag = "";//分隔符
	private final Integer numLen = 8;//长度
	

	/**
	 * 
	 * @param businessCode
	 * @param prefixValue
	 * @param splitFlagValue
	 * @param numLength
	 * @param autoLength
	 * @param init	是否初始化
	 * @return
	 */
	public String selectNextVal(String businessCode,String prefixValue,String splitFlagValue,Integer numLength,boolean autoLength,boolean init){
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SYSBASE);
		
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" and businessCode = ?");	paramList.add(businessCode);
		if(!StringUtils.isEmpty(prefixValue)){whereSql.append(" and prefixValue = ?");paramList.add(prefixValue);}
		if(!StringUtils.isEmpty(splitFlagValue)){whereSql.append(" and splitFlagValue = ?");paramList.add(splitFlagValue);}
		Object [] params = new Object[paramList.size()];
		for (int i = 0 ; i < paramList.size() ; i ++) {params[i] = paramList.get(i);}
		List<Map<String,Object>> list = super.findBySql("select max(currentNum) max ,min(currentNum) min ,count(currentNum) currentNum from " + super.getTableName() + " where 1 = 1 " + whereSql,params);
		
		Map<String,Object> mapArr = (Map<String,Object>) list.get(0);
		int count = Integer.parseInt(String.valueOf(mapArr.get("currentNum")));
		if (count == 0) {
			if(!init) {
				try {
					throw new Exception(String.format("没有业务代码[%s]提供初始化！", businessCode));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				log.info(String.format("业务代码[%s]没有初始化，正在初始化！", businessCode));
				Map<String,Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("businessCode", businessCode);
				paramsMap.put("hasPrefix", StringUtils.isEmpty(prefixValue));
				paramsMap.put("prefixValue", prefixValue);
				paramsMap.put("hasSplitFlag", StringUtils.isEmpty(splitFlagValue));
				paramsMap.put("splitFlagValue", splitFlagValue);
				paramsMap.put("numLength", numLength);
				paramsMap.put("currentNum", 1);
				
				try {
					boolean result = super.doInsert(paramsMap);
					if(!result) {
						throw new Exception(String.format("初始化业务代码[%s]失败！", businessCode));
					} else  {
						log.info(String.format("业务代码[%s]初始化成功！", businessCode));
						return new StringBuffer().append(prefixValue).append(splitFlagValue).append(pull(1, numLength)).toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		String maxNo = DataUtils.nullToZero(mapArr.get("max").toString());
		String minNo = DataUtils.nullToZero(mapArr.get("min").toString());
		if (maxNo.equals(minNo)) {
			int max = Integer.parseInt(minNo, 10) + 1;
			maxNo = pull(max, numLength);
			super.update("UPDATE " + super.getTableName() + " SET currentNum = ? WHERE businessCode = ? ", max, businessCode);
		}
		return new StringBuffer().append(prefixValue).append(splitFlagValue).append(maxNo).toString();
	}
	
	
	public String selectNextVal(String businessCode,String prefixValue,Integer numLength,boolean init){
		return selectNextVal(businessCode, prefixValue, splitFlag, numLength,false,init);
	}
	
	
	public String selectNextVal(String businessCode,Integer numLength,boolean init){
		return selectNextVal(businessCode, prefix, splitFlag, numLength,false,init);
	}
	
	public String selectNextVal(String businessCode,boolean init){
		return selectNextVal(businessCode, prefix, splitFlag, numLen,false,init);
	}
	
	public String selectNextVal(String businessCode,boolean autoLength,boolean init){
		return selectNextVal(businessCode, prefix, splitFlag, numLen ,autoLength,init);
	}
	
	public String selectNextVal(String businessCode){
		return selectNextVal(businessCode, prefix, splitFlag, numLen,false,false);
	}
	
	/**
	 * 拉长顺序号
	 * 
	 * @param value 顺序号
	 * @param len  拉长到的位数
	 * @return 拉长的顺序号字符串
	 */
	public String pull(int value, int len) {
		String serialText = "00000000000000000000";
		String s = Integer.toString(value);
		serialText = serialText.substring(0, len - s.length());
		return (serialText + s);
	}
}
