package com.gupaoedu.serial;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ProtobufDemo {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        //fluent
        UserProto.User user=
                UserProto.User.newBuilder().
                        setName("Mic").setAge(18).build();

        ByteString bytes=user.toByteString();

        System.out.println();

        UserProto.User nUser=UserProto.User.parseFrom(bytes);
        System.out.println(nUser);
        //BitMap/BitSet
    }
}
