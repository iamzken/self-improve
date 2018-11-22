package com.gupaoedu.serial;

import com.alibaba.fastjson.JSON;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class FastjsonSerializer implements ISerializer{

    @Override
    public <T> byte[] serializer(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deSerializer(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data),clazz);
    }
}
