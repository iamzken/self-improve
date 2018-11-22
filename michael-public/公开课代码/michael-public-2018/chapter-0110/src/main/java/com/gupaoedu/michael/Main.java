package com.gupaoedu.michael;

import com.gupaoedu.michael.tp.Card;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext
                        ("applicationContext.xml");
        context.start();

        Card.cardMap.get(Constants.DEBIT_CARD).pay(10000);

    }
}
