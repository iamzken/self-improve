package com.gupaoedu.spi;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class OracleDriver implements DataBaseDriver{

    @Override
    public String connect(String s) {
        return "Build connection With Oracle:"+s;
    }
}
