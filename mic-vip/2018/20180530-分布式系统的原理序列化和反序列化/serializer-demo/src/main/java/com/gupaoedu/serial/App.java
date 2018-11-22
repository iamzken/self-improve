package com.gupaoedu.serial;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        ISerializer iSerializer=new FastjsonSerializer();
        User user=new User();
        user.setAge(18);
        user.setName("Mic");
        user.setHobby("菲菲");
        user.setSex("男");

        byte[] rs=iSerializer.serializer(user);

        System.out.println(new String(rs));


        User user1=iSerializer.deSerializer(rs,User.class);
        System.out.println(user1+"->"+user1.getSex());





    }
}
