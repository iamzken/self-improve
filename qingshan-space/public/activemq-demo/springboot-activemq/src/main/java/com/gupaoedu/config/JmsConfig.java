package com.gupaoedu.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@Configuration
public class JmsConfig {

    @Bean("jmsTopicListenerContainerFactory")
    public JmsListenerContainerFactory getFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean("myQueue")
    public Destination getQueue(){
        return  new ActiveMQQueue("test.queue");
    }

    @Bean("myTopic")
    public  Destination getTopic(){
        return new ActiveMQTopic("test.topic");
    }
}
