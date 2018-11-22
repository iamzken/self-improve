package com.gupaoedu.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class FourthConsumer {
    @JmsListener(destination = "test.topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void process(String msg){
        System.out.println("--------------------- fourth consumer received msg : " + msg);
    }
}
