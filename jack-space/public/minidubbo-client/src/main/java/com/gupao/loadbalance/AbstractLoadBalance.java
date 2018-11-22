package com.gupao.loadbalance;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance{
    @Override
    public String select(List<String> lists) {
        if(lists==null||lists.size()==0){
            return null;
        }
        if(lists.size()==1){
            return lists.get(0);
        }
        return doSelect(lists);
    }

    protected abstract String doSelect(List<String> lists);

}
