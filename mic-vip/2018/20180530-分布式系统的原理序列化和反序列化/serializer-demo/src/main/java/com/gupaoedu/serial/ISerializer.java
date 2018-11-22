package com.gupaoedu.serial;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface ISerializer {

    <T> byte[] serializer(T obj);

    <T> T deSerializer(byte[] data,Class<T> clazz);
}
