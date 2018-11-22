package lession4;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class DoubleCheckDemo {

    private volatile static DoubleCheckDemo instance;

    private DoubleCheckDemo(){}
    //性能问题
    //写一个单例模式
    //不完整实例

    /**
     *
     * memory=allocate(); //分配内存空间
     * actorInstance(memory) ; 初始化对象
     * instance=memory ; //instance指向内存地址
     *
     * memory=allocate(); //分配内存空间
     * instance=memory ; //instance指向内存地址
     * actorInstance(memory) ; 初始化对象
     * @return
     */
    public static DoubleCheckDemo getInstance(){
        if(instance==null){
            synchronized (DoubleCheckDemo.class) {
                if(instance==null) {
                    instance = new DoubleCheckDemo();
                }
            }
        }
        return instance;
    }
}
