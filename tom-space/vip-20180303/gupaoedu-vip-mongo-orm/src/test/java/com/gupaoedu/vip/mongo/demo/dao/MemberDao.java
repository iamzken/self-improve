package com.gupaoedu.vip.mongo.demo.dao;

import com.gupaoedu.vip.mongo.demo.entity.Member;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.core.common.mongo.BaseDaoSupport;
import javax.core.common.mongo.QueryRule;
import java.util.List;

/**
 * Created by Tom on 2018/9/5.
 */
@Repository
public class MemberDao extends BaseDaoSupport<Member,ObjectId> {

    //深切体会到  约定优于配置

    public List<Member> select(QueryRule queryRule){
        return super.find(queryRule);
    }

    public int insertAll(List<Member> data){
        return super.saveAll(data);
    }



    @Resource(name="mongoTemplate")
    @Override
    protected void setTempate(MongoTemplate tempate) {
        super.setTempate(tempate);
    }

    @Override
    protected String getPKColumn() {
        return "_id";
    }
}
