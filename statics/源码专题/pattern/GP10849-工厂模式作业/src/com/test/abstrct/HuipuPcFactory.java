package com.test.abstrct;

import com.test.IKeyboard;
import com.test.IMouse;
import com.test.factory.HuipuKeyboardFactory;
import com.test.factory.HuipuMouseFactory;

public class HuipuPcFactory extends AbstrctPcFactory{
    @Override
    IMouse createMouse() {
        return new HuipuMouseFactory().createMouse();
    }

    @Override
    IKeyboard createKeyboard() {
        return new HuipuKeyboardFactory().createKeyboard();
    }
}
