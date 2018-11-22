package com.test.abstrct;

import com.test.IKeyboard;
import com.test.IMouse;

public abstract class AbstrctPcFactory {

    abstract IMouse createMouse();
    abstract IKeyboard createKeyboard();
}
