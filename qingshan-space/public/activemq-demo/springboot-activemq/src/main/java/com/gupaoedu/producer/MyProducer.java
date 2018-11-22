package com.gupaoedu.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;

@Component
public class MyProducer {
    // JmsMessagingTemplate 对 JmsTemplate 进行了封装
    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Resource(name="myQueue")
    Destination queue;

    @Resource(name="myTopic")
    Destination topic;

    public void send(){
        // 发往queue
        jmsMessagingTemplate.convertAndSend(queue,"a queue msg1 ");
        jmsMessagingTemplate.convertAndSend(queue,"a queue msg2 ");

        // 发往topic
        jmsMessagingTemplate.convertAndSend(topic,"a topic msg ");
    }
}
