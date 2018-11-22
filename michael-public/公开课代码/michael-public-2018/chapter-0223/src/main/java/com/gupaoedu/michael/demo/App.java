package com.gupaoedu.michael.demo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{

    /**
     *  静态方法引用
     *  引用对象的实例方法
     *  引用某个对象的任意对象的实例方法
     *  引用构造函数
     *
     *
     * @param transactions
     * @return
     */

    //按照交易金额进行筛选大于1000， 然后按照币种分类
    private Map<String,List<Transaction>> filter(List<Transaction> transactions){
        /*Map<String,List<Transaction>> transByCurrencies=new HashMap<>();
        for(Transaction transaction:transactions){
            if(transaction.getPrice()>1000){
                String currency=transaction.getCurrency();
                List<Transaction> transactionList=transByCurrencies.get(currency);
                if(transactionList==null){
                    transactionList=new ArrayList<>();
                    transByCurrencies.put(currency,transactionList);
                }
                transactionList.add(transaction);
            }
        }
        return transByCurrencies;*/

        return transactions.stream().filter(transaction->transaction.getPrice()>1000)
                .collect(Collectors.groupingBy(Transaction::getCurrency));
    }

    public List<Integer> stream(List<Transaction> transactions){
        return transactions.stream().filter(transaction->transaction.getPrice()>500).
                sorted(Comparator.comparing(Transaction::getPrice)).limit(2).
                map(Transaction::getPrice).collect(Collectors.toList());
    }


    public static void main( String[] args ){
        List<Transaction> transactions=new ArrayList<>();
        transactions.add(new Transaction(1100,"CNY"));
        transactions.add(new Transaction(500,"USD"));
        transactions.add(new Transaction(800,"CNY"));
        transactions.add(new Transaction(2000,"USD"));
        transactions.add(new Transaction(3000,"CNY"));
        transactions.add(new Transaction(1200,"USD"));
        transactions.add(new Transaction(900,"CNY"));
        App app=new App();
//        Map<String,List<Transaction>> map=app.filter(transactions);
        List<Integer> lists=app.stream(transactions);
        System.out.println(lists);
    }
}
