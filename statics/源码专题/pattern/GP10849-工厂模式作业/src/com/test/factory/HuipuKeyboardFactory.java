package com.test.factory;

import com.test.IKeyboard;
import com.test.huipu.HuipuKeyboard;

public class HuipuKeyboardFactory implements IKeyboardFactory{
    @Override
    public IKeyboard createKeyboard() {
        return new HuipuKeyboard();
    }
}
