package com.gupaoedu.michael.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class LambdaDemo {

    private static String readFile(IBufferedReaderInterface p) throws IOException {
        try(BufferedReader br=new BufferedReader(new FileReader("data.txt"))){
            return p.process(br);
        }
    }

    //思考： java.util.function

    public static void main(String[] args) throws IOException {
        System.out.println(LambdaDemo.readFile(br->br.readLine()));
    }
}
