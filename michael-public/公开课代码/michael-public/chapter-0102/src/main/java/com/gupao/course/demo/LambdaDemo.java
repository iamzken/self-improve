package com.gupao.course.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class LambdaDemo {

   /* private static String proccessFile(IBufferedReaderProcessor processor) throws IOException{
        try(BufferedReader br=new BufferedReader(new FileReader("mic.txt"))){
            return processor.process(br);
        }
    }*/

   private static String predicateDemo(Predicate<String> predicate) {
       if(predicate.test("red")){
           return "true";
       }
       return "false";
   }

    public static void main(String[] args) throws IOException {
        System.out.println(LambdaDemo.predicateDemo(str->"red".equals(str)));
    }

}
