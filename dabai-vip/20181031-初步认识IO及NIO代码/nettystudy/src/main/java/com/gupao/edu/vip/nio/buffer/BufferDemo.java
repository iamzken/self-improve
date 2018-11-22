package com.gupao.edu.vip.nio.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class BufferDemo {

    public static void decode(String str) throws UnsupportedEncodingException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        byteBuffer.put(str.getBytes("UTF-8"));
        byteBuffer.flip();

        /*对获取utf8的编解码器*/
        Charset utf8 = Charset.forName("UTF-8");
        CharBuffer charBuffer = utf8.decode(byteBuffer);/*对bytebuffer中的内容解码*/

        /*array()返回的就是内部的数组引用，编码以后的有效长度是0~limit*/
        char[] charArr = Arrays.copyOf(charBuffer.array(), charBuffer.limit());
        System.out.println(charArr);
    }

    public static void encode(String str){
        CharBuffer charBuffer = CharBuffer.allocate(128);
        charBuffer.append(str);
        charBuffer.flip();

        /*对获取utf8的编解码器*/
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuffer byteBuffer = utf8.encode(charBuffer); /*对charbuffer中的内容解码*/

        /*array()返回的就是内部的数组引用，编码以后的有效长度是0~limit*/
        byte[] bytes = Arrays.copyOf(byteBuffer.array(), byteBuffer.limit());
        System.out.println(Arrays.toString(bytes));
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        BufferDemo.decode("咕泡学院");
        BufferDemo.encode("咕泡学院");
    }
}
