package com.gupaoedu.michael;

import java.util.List;
import java.util.Random;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RandomLoadBalance extends AbstraceLoadBanance{

    @Override
    protected String doSelect(List<String> repos) {
        int length=repos.size();
        Random random=new Random();
        return repos.get(random.nextInt(length));
    }
}
