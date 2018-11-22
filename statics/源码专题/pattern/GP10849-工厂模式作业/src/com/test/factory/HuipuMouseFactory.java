package com.test.factory;

import com.test.IMouse;
import com.test.huashuo.HuashuoMouse;
import com.test.huipu.HuipuMouse;

public class HuipuMouseFactory implements IMouseFatory{
    @Override
    public IMouse createMouse() {
        return new HuipuMouse();
    }
}
