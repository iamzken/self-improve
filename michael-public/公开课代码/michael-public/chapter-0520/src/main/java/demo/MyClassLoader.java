package demo;

import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName=name.substring(name.lastIndexOf(".")+1)+".class";
        InputStream is=this.getClass().getResourceAsStream(fileName);
        try {
            byte[] b=new byte[is.available()];
            is.read(b);
            return defineClass(name,b,0,b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
