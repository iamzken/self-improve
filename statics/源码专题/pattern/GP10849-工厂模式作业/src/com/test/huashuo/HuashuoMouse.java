package com.test.huashuo;

import com.test.IMouse;

public class HuashuoMouse implements IMouse{
    @Override
    public String getBrand() {
        return "华硕鼠标";
    }

    @Override
    public String getSize() {
        return "大";
    }
}
