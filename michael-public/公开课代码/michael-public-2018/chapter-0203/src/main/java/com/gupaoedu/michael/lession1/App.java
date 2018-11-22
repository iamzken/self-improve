package com.gupaoedu.michael.lession1;

/**
 * Hello world!
 *
 */
public class App 
{
    private volatile int i=0;

    public void add(){
        i++;
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
