package com.gupaoedu.michael.demo2;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class XmlSerializer implements ISerializer{

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        XMLEncoder xe=new XMLEncoder(out,"utf-8",true,0);
        xe.writeObject(obj);
        xe.close();
        return out.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        XMLDecoder xd=new XMLDecoder(new ByteArrayInputStream(data));
        Object obj=xd.readObject();
        xd.close();
        return (T)obj;
    }

}
