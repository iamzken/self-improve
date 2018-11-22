package com.test.simple;

import com.test.IMouse;
import com.test.huashuo.HuashuoMouse;
import com.test.huipu.HuipuMouse;

public class SimpleMouseFactory {

    public IMouse createMouse(String brand){
        if("huipu".equals(brand)){
            return new HuipuMouse();
        }else if("huashuo".equals(brand)){
            return new HuashuoMouse();
        }else {
            return null;
        }
    }
}
