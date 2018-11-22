package com.gupaoedu.vip.mongo.explorer.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by Tom on 2018/8/25.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.explorer")
public class ExplorerMongoConfig extends AbstractMongoConfig {

    public static final String MONGO_TEMPLATE = "explorerMongoTemplate";

    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate getExplorerMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

    @Primary
    @Bean(name = "mongoTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return getExplorerMongoTemplate();
    }


}
