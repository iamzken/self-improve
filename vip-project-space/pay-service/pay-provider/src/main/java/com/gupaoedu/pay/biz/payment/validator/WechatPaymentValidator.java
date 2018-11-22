package com.gupaoedu.pay.biz.payment.validator;

import com.gupaoedu.pay.biz.abs.BaseValidator;
import com.gupaoedu.pay.commons.AbstractRequest;
import com.gupaoedu.pay.dto.PaymentRequest;
import com.gupaoedu.pay.exception.ValidateException;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("wechatPaymentValidator")
public class WechatPaymentValidator extends BaseValidator {

    @Override
    public void validate(AbstractRequest request) {
        if(request==null){
            throw new ValidateException("请求参数为空");
        }
        PaymentRequest paymentRequest=(PaymentRequest)request;

    }
}
