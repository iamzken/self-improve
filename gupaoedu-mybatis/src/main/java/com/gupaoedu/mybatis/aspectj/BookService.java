package com.gupaoedu.mybatis.aspectj;

public class BookService {
    public int addBook(String name, int price) {
        System.out.println("正在添加图书，书名是：" + name + ",价格是：" + price);
        return 100;
    }

    public static void main(String[] args) {
        BookService bookService = new BookService();
        bookService.addBook("james",1);
    }
}