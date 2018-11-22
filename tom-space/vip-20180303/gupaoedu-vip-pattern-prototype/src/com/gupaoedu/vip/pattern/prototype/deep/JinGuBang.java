package com.gupaoedu.vip.pattern.prototype.deep;

import java.io.Serializable;

/**
 * Created by Tom on 2018/3/7.
 */
public class JinGuBang implements Serializable {
    public float h = 100;
    public float d = 10;

    public void big(){
        this.d *= 2;
        this.h *= 2;
    }

    public void small(){
        this.d /= 2;
        this.h /= 2;
    }


}
