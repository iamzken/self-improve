package com.gupaoedu.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 先启动订阅者
 */
public class Publisher {
    public static void main(String[] args) throws JMSException, InterruptedException {
        // 连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");

        // 连接
        Connection connection =  connectionFactory.createConnection();

        connection.start();
        // 会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 消息的目的地
        Destination destination =  session.createTopic("api-topic");

        // 消息发送者
        MessageProducer producer =  session.createProducer(destination);

        TextMessage message = session.createTextMessage("this is a topic msg " );

        //发送消息
        producer.send(message);

        // 关闭连接
        session.close();
        connection.close();
    }
}
