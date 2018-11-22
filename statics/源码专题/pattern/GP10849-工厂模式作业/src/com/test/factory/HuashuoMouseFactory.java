package com.test.factory;

import com.test.IMouse;
import com.test.huashuo.HuashuoMouse;

public class HuashuoMouseFactory implements IMouseFatory{
    @Override
    public IMouse createMouse() {
        return new HuashuoMouse();
    }
}
