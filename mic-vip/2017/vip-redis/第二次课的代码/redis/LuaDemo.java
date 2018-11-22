package com.gupao.vip.mic.dubbo.redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class LuaDemo {

    public static void main(String[] args) throws Exception {

        Jedis jedis=RedisManager.getJedis();

        String lua="local num=redis.call('incr',KEYS[1])\n" +
                "if tonumber(num)==1 then\n" +
                "   redis.call('expire',KEYS[1],ARGV[1])\n" +
                "   return 1\n" +
                "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
                "   return 0\n" +
                "else\n" +
                "   return 1\n" +
                "end";
        List<String> keys=new ArrayList<>();
        keys.add("phone:limit:177,,,");
      //  String sha=jedis.scriptLoad(lua);
     //   System.out.println(sha);
        List<String> arggs=new ArrayList<>();
        arggs.add("6000");
        arggs.add("10");
        Object obj=jedis.evalsha("8a8ee74e246c39d3ac49ddfc938fa2942c56e087",keys,arggs);
        System.out.println(obj);
    }
}
