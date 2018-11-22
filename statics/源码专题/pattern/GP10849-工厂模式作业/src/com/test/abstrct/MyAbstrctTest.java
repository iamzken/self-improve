package com.test.abstrct;

import com.test.abstrct.AbstrctPcFactory;
import com.test.abstrct.HuashuoPcFactory;

public class MyAbstrctTest {
    public static void main(String[] args) {
        AbstrctPcFactory huashuoPcFactory=new HuashuoPcFactory();
        System.out.println(huashuoPcFactory.createKeyboard().getBrand());
        AbstrctPcFactory huipuPcFactory=new HuipuPcFactory();
        System.out.println(huipuPcFactory.createKeyboard().getBrand());
    }
}
