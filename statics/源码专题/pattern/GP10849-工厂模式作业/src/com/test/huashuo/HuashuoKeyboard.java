package com.test.huashuo;

import com.test.IKeyboard;

public class HuashuoKeyboard implements IKeyboard{
    @Override
    public String getBrand() {
        return "华硕键盘";
    }

    @Override
    public String getPort() {
        return "3";
    }
}
