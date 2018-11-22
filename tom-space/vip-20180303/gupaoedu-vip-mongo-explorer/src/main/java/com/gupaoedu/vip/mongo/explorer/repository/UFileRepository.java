package com.gupaoedu.vip.mongo.explorer.repository;

import com.gupaoedu.vip.mongo.explorer.entity.UFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Tom on 2018/8/23.
 */
public interface UFileRepository extends MongoRepository<UFile,String> {

//    boolean exists(@Param("group")String group,@Param("owner") String uname,@Param("xpath") String xpath,@Param("fname") String fname);


    //用SpringBoot的好处就是解放双手，你完全不用写实现类
    //约定优于配置
    //编码规范   find开头就是 查   By条件  And 或者 Or ，For返回值
    List<UFile> findByXpath(String xpath);

}
