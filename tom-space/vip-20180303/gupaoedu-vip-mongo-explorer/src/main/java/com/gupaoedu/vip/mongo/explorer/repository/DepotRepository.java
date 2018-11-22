package com.gupaoedu.vip.mongo.explorer.repository;

import com.gupaoedu.vip.mongo.explorer.common.config.DepotMongoConfig;
import com.gupaoedu.vip.mongo.explorer.entity.OFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

/**
 * Created by Tom on 2018/8/25.
 */
@Repository
public class DepotRepository {

    @Autowired
    @Qualifier(DepotMongoConfig.MONGO_TEMPLATE)
    private MongoTemplate mongoTemplate;

    public String putFile(OFile file) {
        return putFile(file,null);
    }

    public String putFile(OFile file,String alias) {
        try {

            //根据hash值计算MD5的值，作为文件的唯一ID
            //CRC32是根据文件流，是一种可逆算法
            String id = file.getId();
            if(exists(id)){return  id;}

            String fileName = file.getName();
            GridFSInputFile gfs = getGridFS().createFile(file);
            //分片的预案，设置到数据中
            gfs.put("aliases", alias);
            gfs.put("filename", fileName);
            gfs.put("contentType", fileName.substring(fileName.lastIndexOf(".")));
            gfs.save();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("存储文件时发生错误！！！");
        }
        return null;
    }

    public boolean exists(String id){
        DBObject query = new BasicDBObject("md5",id);
        GridFSDBFile dbfile = getGridFS().findOne(query);
        return dbfile != null;
    }


    private GridFS getGridFS(){
        DB db = mongoTemplate.getMongoDbFactory().getLegacyDb();
        // 获取fs的根节点
       return new GridFS(db, getCollectionName());
    }

    private String getCollectionName(){
        return OFile.class.getAnnotation(Document.class).collection();
    }

}
