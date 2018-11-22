package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Service {

    private boolean isContinueRun=true;
    public void runMethod() throws InterruptedException {
        String ay=new String ();
        while(isContinueRun){
            /*synchronized (ay){

            }*/
            Thread.sleep(1);
        }
        System.out.println("stoped");
    }
    public void stopMethod(){
        isContinueRun=false;
    }
}
