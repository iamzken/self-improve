package com.gupaoedu.michael;

import com.gupaoedu.michael.tp.Card;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service
public class CreditCard extends Card{


    @Override
    protected void execTransaction(int cents) {
        System.out.println("当前支付方式是：credit， 金额:["+cents+"]");

    }

    @Override
    protected String getType() {
        return Constants.CREDI_CARD;
    }
}
