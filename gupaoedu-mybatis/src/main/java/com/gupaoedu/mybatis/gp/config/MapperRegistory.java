package com.gupaoedu.mybatis.gp.config;

import com.gupaoedu.mybatis.beans.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class MapperRegistory {
    public static final Map<String, MapperData> methodSqlMapping = new HashMap<>();

    //使用 1. 在这里配置
    //2. Java Bean的属性名字要和数据库表的名字一致
    public MapperRegistory() {
        methodSqlMapping.put("com.gupaoedu.mybatis.my.TestMapper.selectByPrimaryKey",
                new MapperData("select * from test where id = %d",Test.class));
    }

    public class MapperData<T>{
        private String sql;
        private Class<T> type;

        public MapperData(String sql, Class<T> type) {
            this.sql = sql;
            this.type = type;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Class<T> getType() {
            return type;
        }

        public void setType(Class<T> type) {
            this.type = type;
        }
    }

    public MapperData get(String nameSpace) {
        return methodSqlMapping.get(nameSpace);
    }
}
