package com.gupaoedu.michael.demo;

import java.util.function.Supplier;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PersonFactory {

    private Supplier<Person> supplier;

    public PersonFactory(Supplier<Person> supplier) {
        this.supplier = supplier;
    }
    public Person getPerson(){
        return this.supplier.get();
    }
}
