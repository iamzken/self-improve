package com.gupaoedu.vip.pattern.prototype.simple;

import java.util.ArrayList;

/**
 * Created by Tom on 2018/3/7.
 */
public class CloneTest {

    public static void main(String[] args) {

        CloneTarget p = new CloneTarget();
        p.name = "Tom";
//        p.list = new ArrayList<CloneTarget>();
//        p.list.add(new CloneTarget());
        p.target = new CloneTarget();
        System.out.println(p.target);

        try {
            CloneTarget obj =  (CloneTarget) p.clone();
            System.out.println(obj.target);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
