package lession2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class FutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        Callable<String> openIdea=new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("打开开发工具Intellij IDEA");
                Thread.sleep(5000); //模拟打开的时间
                System.out.println("打开成功");
                return "打开Idea成功";
            }
        };

        FutureTask<String> task=new FutureTask<String>(openIdea);
        new Thread(task).start();

        //去github找源码下载地址
        Thread.sleep(2000); //模拟打开github获得url的时间
        System.out.println("github源码地址获得成功");
        if(!task.isDone()){
            System.out.println("idea开发工具还在加载中");
        }
        String result=task.get();
        System.out.println("开发工具打开好了，开始通过VCS来clone源码");
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }
}
