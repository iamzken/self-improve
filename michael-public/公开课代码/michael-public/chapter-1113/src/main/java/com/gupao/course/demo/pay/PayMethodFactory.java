package com.gupao.course.demo.pay;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PayMethodFactory {

    public static PayMethod getPayMethod(int type) throws Exception {
        switch (type){
            case Contant.JIEJIKA:
                return new JJKCard("mic","1111","01/02");
            case Contant.XINGYONGKA:
                return new XykCard("james","2222","10/02");
            default:
                throw new Exception("can't find paymethod");
        }
    }
}
