package javax.core.common.mongo;

import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import javax.core.common.utils.GenericsUtils;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Tom on 2018/9/5.
 */
public abstract class BaseDaoSupport<T extends Serializable,PK extends Serializable> {

//    Logger logger = Lo

    private MongoTemplate mongoTemplate;
    private EntiyOperation<T> op;

    public BaseDaoSupport(){
        Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass(),0);
        op = new EntiyOperation<T>(entityClass);
    }

    protected void setTempate(MongoTemplate tempate){
        this.mongoTemplate = tempate;
    }

    protected abstract String getPKColumn();


    //可控
    protected List<T> find(QueryRule queryRule){
       // "starttime " + "" + System.currentTimeMillis();
        QueryRuleBulider bulider = new QueryRuleBulider(queryRule);
        Query query = bulider.getQuery();

        return mongoTemplate.find(query,op.entiyClass);
    }


    protected int saveAll(List<T> list){
        mongoTemplate.insertAll(list);
        return list.size();
    }


    protected  T get(PK id){
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual(this.getPKColumn(),id);
        QueryRuleBulider bulider = new QueryRuleBulider(queryRule);
        Query query = bulider.getQuery();

        return mongoTemplate.findOne(query,op.entiyClass);
    }

    protected int delete(T entity){
        return mongoTemplate.remove(entity).getN();
    }

}
