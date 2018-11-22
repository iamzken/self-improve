package com.test.simple;

import com.test.IKeyboard;
import com.test.huashuo.HuashuoKeyboard;
import com.test.huipu.HuipuKeyboard;

public class SimpleKeyboardFactory {
    public IKeyboard createKeyboard(String brand){
        if("huipu".equals(brand)){
            return new HuipuKeyboard();
        }else if("huashuo".equals(brand)){
            return new HuashuoKeyboard();
        }else {
            return null;
        }
    }
}
