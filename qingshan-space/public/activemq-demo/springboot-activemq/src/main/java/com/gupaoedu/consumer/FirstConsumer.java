package com.gupaoedu.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class FirstConsumer {
    @JmsListener(destination = "test.queue")
    public void process(String msg){
        System.out.println("---------------------first consumer received msg : "+msg);
    }
}
