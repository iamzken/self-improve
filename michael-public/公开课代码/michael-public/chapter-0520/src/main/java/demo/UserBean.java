package demo;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class UserBean implements Serializable{


    private static final long serialVersionUID = 1826015764654102866L;
    private String name;

    private String sex;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
