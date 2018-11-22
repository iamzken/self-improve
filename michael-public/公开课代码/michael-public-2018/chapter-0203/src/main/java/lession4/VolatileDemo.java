package lession4;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class VolatileDemo {
    private volatile static boolean stop=false; //flag : ACC_VOLATILE
    //C volatile  ;屏蔽了优化的过程 ； 内存屏障

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            int i=0;
            while(!stop){
                i++;
            }
        }).start();
        Thread.sleep(1000);
        System.out.println("执行线程");
        stop=true;
    }
}
