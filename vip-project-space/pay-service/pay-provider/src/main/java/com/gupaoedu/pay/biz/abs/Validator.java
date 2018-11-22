package com.gupaoedu.pay.biz.abs;


import com.gupaoedu.pay.commons.AbstractRequest;

/**
 * 数据验证接口类
 */
public interface Validator {

    void validate(AbstractRequest request);
}
