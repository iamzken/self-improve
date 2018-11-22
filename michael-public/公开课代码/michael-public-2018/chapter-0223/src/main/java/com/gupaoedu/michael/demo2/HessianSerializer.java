package com.gupaoedu.michael.demo2;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class HessianSerializer implements ISerializer{
    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        HessianOutput ho=new HessianOutput(os);
        try {
            ho.writeObject(obj);
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream is=new ByteArrayInputStream(data);
        HessianInput hi=new HessianInput(is);
        try {
            return (T)hi.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
