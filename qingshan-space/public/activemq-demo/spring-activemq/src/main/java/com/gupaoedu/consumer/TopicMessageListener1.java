package com.gupaoedu.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TopicMessageListener1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("TopicMessageListener1 received msg : " + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}