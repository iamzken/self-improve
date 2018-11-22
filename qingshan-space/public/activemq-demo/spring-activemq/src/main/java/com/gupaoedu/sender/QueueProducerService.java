package com.gupaoedu.sender;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class QueueProducerService {
    @Resource(name = "jmsQueueTemplate")
    private JmsTemplate jmsQueueTemplate;

    public void sendMessage(Destination destination, final String message){
        jmsQueueTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
