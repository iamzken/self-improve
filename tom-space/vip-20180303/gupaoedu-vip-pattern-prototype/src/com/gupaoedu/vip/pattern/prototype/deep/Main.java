package com.gupaoedu.vip.pattern.prototype.deep;

import com.gupaoedu.vip.pattern.prototype.deep.QiTianDaSheng;

public class Main {

    public static void main(String[] args) {

//        QiTianDaSheng qiTianDaSheng = new QiTianDaSheng();
//
//        try {
//            QiTianDaSheng clone = (QiTianDaSheng)qiTianDaSheng.clone();
//
//            System.out.println( qiTianDaSheng.jinGuBang == clone.jinGuBang);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



        QiTianDaSheng q = new QiTianDaSheng();
        QiTianDaSheng n = q.copy(q);
        System.out.println(q.jinGuBang == n.jinGuBang);



    }
}
