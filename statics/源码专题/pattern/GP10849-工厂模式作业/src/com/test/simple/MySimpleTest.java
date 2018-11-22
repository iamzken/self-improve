package com.test.simple;

import java.util.Optional;

public class MySimpleTest {
    public static void main(String[] args) {
        SimpleKeyboardFactory simpleKeyboardFactory=new SimpleKeyboardFactory();
        Optional.ofNullable(simpleKeyboardFactory.createKeyboard("hui1pu")).ifPresent(value-> System.out.println(value.getBrand()));
    }
}
