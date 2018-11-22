package com.gupaoedu.tcc;

import com.gupaoedu.tcc.dto.Order;
import org.mengyun.tcctransaction.api.Compensable;

import java.math.BigDecimal;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface PaymentService {

    @Compensable
    void makePayment(Order orderDto, BigDecimal capitalPayAmount);
}
