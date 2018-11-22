package com.gupao.course.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TransactionService {

    //
    //对交易记录进行筛选
    //1. 按照价格筛选 ，大于1000的
    //2. 按照币种进行分类
   /* private static Map<String,List<Transaction>> demo(List<Transaction> transactions){
        Map<String,List<Transaction>> mapByCurrency =new HashMap<>();
        for (Transaction transaction:transactions){
            if(transaction.getPrice()>1000){
                String currency=transaction.getCurrency();
                List<Transaction> transactionsByCurrcy=mapByCurrency.get(currency);
                if(transactionsByCurrcy==null){
                    transactionsByCurrcy=new ArrayList<>();
                    mapByCurrency.put(currency,transactionsByCurrcy);
                }
                transactionsByCurrcy.add(transaction);
            }
        }
        return mapByCurrency;
    }*/

    public static void main(String[] args) {
        List<Transaction> transactions=new ArrayList<>();
        transactions.add(new Transaction(800,"CNY"));
        transactions.add(new Transaction(1100,"USD"));
        transactions.add(new Transaction(1500,"USD"));
        transactions.add(new Transaction(500,"CNY"));
        transactions.add(new Transaction(2100,"CNY"));
        transactions.add(new Transaction(1000,"CNY"));
        transactions.add(new Transaction(600,"USD"));
        transactions.add(new Transaction(100,"CNY"));
        Map<String,List<Transaction>> map=TransactionService.demo2(transactions);
        System.out.println(map);
    }

    private static Map<String,List<Transaction>> demo2(List<Transaction> transactions){
        //形式参数，多个参数以逗号分割  -> 代码块
        //(Transaction transaction)->{transaction.getPrice()>1000;};

        //方法引用 :lambda表达式的简化调用
        //1. 静态方法引用
        //2. 引用对象实例方法
        //3. 引用某个对象的任意对象的实例方法
        //4. 引用类的构造函数

        return transactions.stream().filter((Transaction transaction)->transaction.getPrice()>1000).
                collect(Collectors.groupingBy(Transaction::getCurrency)); //JAVA8的语法
    }

}
