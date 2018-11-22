package com.gupao.loadbalance;

import java.util.List;

public interface LoadBalance {
    //返回list集合中的任意一个元素  url
    String select(List<String> lists);
}
