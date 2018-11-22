package com.gupaoedu.michael.demo2;

import java.io.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JavaSerializer implements ISerializer{
    

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(data);
        try{
            ObjectInput objectInput=new ObjectInputStream(byteArrayInputStream);
            return (T)objectInput.readObject();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
