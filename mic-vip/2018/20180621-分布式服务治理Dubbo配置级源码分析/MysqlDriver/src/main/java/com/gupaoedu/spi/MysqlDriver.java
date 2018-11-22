package com.gupaoedu.spi;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MysqlDriver implements DataBaseDriver{

    @Override
    public String connect(String s) {
        return "begin build Mysql connection";
    }
}
