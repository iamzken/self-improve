package com.test.factory;

import com.test.IKeyboard;
import com.test.huashuo.HuashuoKeyboard;
import com.test.huipu.HuipuKeyboard;

public class HuashuoKeyboardFactory implements IKeyboardFactory{
    @Override
    public IKeyboard createKeyboard() {
        return new HuashuoKeyboard();
    }
}
