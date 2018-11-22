package com.gupao.course.demo.json;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class BaseResponse<T> {

    private String code;

    private String msg;

    private T  data;

    public BaseResponse(){}

    public BaseResponse(String code,String msg,T t){
        this.code=code;
        this.msg=msg;
        this.data=t;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
