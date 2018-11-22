package com.gupaoedu.mybatis.gp.config;

import lombok.Data;

import java.io.File;
import java.io.IOException;

/**
 * Created by James on 2017-07-01.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
@Data
public class GpConfiguration {

    private String scanPath;

    private MapperRegistory mapperRegistory = new MapperRegistory();

    public GpConfiguration scanPath(String scanPath) {
        this.scanPath = scanPath;
        return this;
    }

    public void build() throws IOException {
        if (null == scanPath || scanPath.length() < 1) {
            throw new RuntimeException("scan path is required .");
        }
    }

    public static void main(String[] args) throws IOException {
        new GpConfiguration().scanPath("com/gupaoedu/mybatis/gp/config/mappers").build();
    }
}
