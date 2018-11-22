package com.test.factory;

public class MyFactoryTest {
    public static void main(String[] args) {
        IKeyboardFactory huipuKeyboardFactory=new HuipuKeyboardFactory();
        System.out.println(huipuKeyboardFactory.createKeyboard().getBrand());
    }
}
