package com.gupao.impl;

import com.gupao.RpcService;
import com.gupao.api.IGpService;

/**
 * Created by Jack
 * Create in 21:02 2018/9/1
 * Description:
 */
@RpcService(IGpService.class)
public class GpServiceImpl2 implements IGpService {
    @Override
    public String hello(String name) {
        return "Hello2:"+name;
    }
}
