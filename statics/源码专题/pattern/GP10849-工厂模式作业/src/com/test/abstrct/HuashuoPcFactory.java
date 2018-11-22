package com.test.abstrct;

import com.test.IKeyboard;
import com.test.IMouse;
import com.test.factory.HuashuoKeyboardFactory;
import com.test.factory.HuashuoMouseFactory;

public class HuashuoPcFactory extends AbstrctPcFactory{
    @Override
    IMouse createMouse() {
        return new HuashuoMouseFactory().createMouse();
    }

    @Override
    IKeyboard createKeyboard() {
         return new HuashuoKeyboardFactory().createKeyboard();
    }
}
