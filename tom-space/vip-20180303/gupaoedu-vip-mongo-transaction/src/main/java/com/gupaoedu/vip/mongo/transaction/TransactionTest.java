package com.gupaoedu.vip.mongo.transaction;

import com.gupaoedu.vip.mongo.transaction.entity.Member;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

/**
 * Created by Tom on 2018/9/2.
 */
public class TransactionTest {
    public static void main(String[] args) {

       com.mongodb.client.MongoClient mongoClient = MongoClients.create("mongodb://192.168.102.129:30000");

        MongoDatabase db = mongoClient.getDatabase("gupaoedu-tx");

        ClientSession session = mongoClient.startSession();
        try {
            session.startTransaction();

            MongoCollection member = db.getCollection("t_member");


            Document memberdoc = new Document("name", "TomTest")
                    .append("type", "database")
                    .append("count", 1)
                    .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                    .append("info", new Document("x", 203).append("y", 102));

            member.insertOne(memberdoc);


            MongoCollection user = db.getCollection("t_user");
            Document userdoc = new Document("name", "MongoDB")
                    .append("type", "database")
                    .append("count", 1/0)
                    .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                    .append("info", new Document("x", 203).append("y", 102));

            member.insertOne(userdoc);

            session.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            session.abortTransaction();
//            session.
        }finally {
            session.close();
        }

    }
}
