package com.gupaoedu.dubbo.core.msg;

import java.io.Serializable;

public class InvokerMsg implements Serializable {
	  
    private static final long serialVersionUID = -8970942815543515064L;

    
    private String className;//类名
    private String methodName;//函数名称 
    private Class<?>[] parames;//参数类型
    private Object[] values;//参数列表
    
    public String getClassName() {
        return className;  
    }  
    public void setClassName(String className) {
        this.className = className;
    }  
    public String getMethodName() {
        return methodName;
    }  
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
	public Class<?>[] getParames() {
		return parames;
	}
	public void setParames(Class<?>[] parames) {
		this.parames = parames;
	}
	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}

}
