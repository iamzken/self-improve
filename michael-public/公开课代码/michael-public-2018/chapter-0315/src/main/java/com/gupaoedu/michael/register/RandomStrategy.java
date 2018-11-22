package com.gupaoedu.michael.register;

import java.util.List;
import java.util.Random;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RandomStrategy extends AbstraceLoadBanance{

    @Override
    public String doSelect(List<String> serviceRepos) {
        int length = serviceRepos.size(); // 总个数
        Random random = new Random();
        return serviceRepos.get(random.nextInt(length));
    }
}
