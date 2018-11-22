package com.gupaoedu.vip.mongo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.gupaoedu.vip.mongo.entity.Hotel;
import com.mongodb.MongoClient;

public class MorphiaTest {

	
	public static void main(String[] args) {
		
		Morphia morphia = new Morphia();
		
		Datastore ds = morphia.createDatastore(new MongoClient("127.0.0.1",27017), "gupaoedu-demo");
		
//		Hotel hotel = new Hotel();
//		hotel.setHotelName("麓谷酒店");
//		hotel.setHotelAddress("湖南长沙高新区麓谷企业广场");
		
//		Key<Hotel> key = ds.save(hotel);
		
//		System.out.println(key.toString());
		
		Query<Hotel> query = ds.createQuery(Hotel.class);
		
		System.out.println(query.get());
		
	}
	
}
