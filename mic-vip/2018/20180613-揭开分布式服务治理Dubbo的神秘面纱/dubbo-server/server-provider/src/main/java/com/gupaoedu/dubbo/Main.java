package com.gupaoedu.dubbo;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Main {
    public static void main(String[] args) throws IOException {

        //默认情况下会使用spring容器来启动服务
        com.alibaba.dubbo.container.Main.main(
                new String[]{"spring","log4j"});



    }
}
