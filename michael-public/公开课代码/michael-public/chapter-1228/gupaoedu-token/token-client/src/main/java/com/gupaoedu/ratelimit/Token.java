package com.gupaoedu.ratelimit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public enum  Token {
    SUCCESS,
    FAILED;
    public boolean isSuccess(){return this.equals(SUCCESS);}
    public boolean isFailed(){return this.equals(FAILED);}
}
