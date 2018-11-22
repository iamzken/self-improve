package com.test.huipu;

import com.test.IKeyboard;

public class HuipuKeyboard implements IKeyboard{
    @Override
    public String getBrand() {
        return "惠普键盘";
    }

    @Override
    public String getPort() {
        return "2";
    }
}
