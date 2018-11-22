package com.gupaoedu.rmi.rpc.zk.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RandomLoadBanalce extends AbstractLoadBanance{

    @Override
    protected String doSelect(List<String> repos) {
        int len=repos.size();
        Random random=new Random();
        return repos.get(random.nextInt(len));
    }
}
