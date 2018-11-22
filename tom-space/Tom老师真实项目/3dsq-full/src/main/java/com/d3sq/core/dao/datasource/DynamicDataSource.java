package com.d3sq.core.dao.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/** 
 * 动态数据源 
 *  
 */  
public class DynamicDataSource extends AbstractRoutingDataSource {  
  
    private DynamicDataSourceEntry dataSourceEntry;  
    
    @Override  
    protected Object determineCurrentLookupKey() {
        return this.dataSourceEntry.get();  
    }  
  
    public void setDataSourceEntry(DynamicDataSourceEntry dataSourceEntry) {  
        this.dataSourceEntry = dataSourceEntry;
    }
    
    public DynamicDataSourceEntry getDataSourceEntry(){
    	return this.dataSourceEntry;
    }

}
