package com.gupaoedu.refactoring.now;

import java.sql.ResultSet;

public interface IRowMapper<T> {
	//处理结果集
	T mapping(ResultSet rs) throws Exception;
}
