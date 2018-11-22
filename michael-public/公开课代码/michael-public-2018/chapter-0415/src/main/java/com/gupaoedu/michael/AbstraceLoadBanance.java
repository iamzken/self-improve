package com.gupaoedu.michael;

import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public abstract class AbstraceLoadBanance implements LoadBanalceStrategy{

    @Override
    public String selectHost(List<String> serviceRepos) {
        if(serviceRepos==null||serviceRepos.size()==0){
            return null;
        }
        if(serviceRepos.size()==1){
            return serviceRepos.get(0);
        }
        return doSelect( serviceRepos);
    }

    protected abstract String doSelect(List<String> repos);
}
