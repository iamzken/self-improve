package com.gupaoedu.vip.mongo;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class NativeTest {

	
	public static void main(String[] args) {
		
		//JDBC  Connection
		
		//只是一个配置
		Mongo mongo = new Mongo("127.0.0.1", 27017);

		
		//建立连接
		DB db = new DB(mongo, "gupaoedu-demo");
		
//		System.out.println(db.getCollectionNames());
		
		//ResultSet 
		// hasNext();     rs.next();
		//游标
		DBCursor cursor = db.getCollection("Hotel").find();
		
		for (DBObject dbObject : cursor) {
			System.out.println(dbObject);
		}
		
	}
	
}
