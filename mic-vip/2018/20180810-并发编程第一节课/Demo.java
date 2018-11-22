package com.gupaoedu.pub2018.chapter4;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {
    PrintProcessor printProcessor;

    public Demo() {
        SaveProcessor saveProcessor=new SaveProcessor();
        saveProcessor.start();
        printProcessor=new PrintProcessor(saveProcessor);
        printProcessor.start();
    }

    public static void main(String[] args) {
        Request request=new Request();
        request.setName("mic");
        new Demo().doTest(request);
    }

    public void doTest(Request request){

        printProcessor.processorRequest(request);

    }

}
