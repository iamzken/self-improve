package com.test.huipu;

import com.test.IMouse;

public class HuipuMouse implements IMouse{
    @Override
    public String getBrand() {
        return "惠普鼠标";
    }

    @Override
    public String getSize() {
        return "中";
    }
}
