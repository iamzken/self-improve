package com.gupaoedu.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver {
    public static void main(String[] args) throws JMSException {
        // 连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin",
                "admin", "tcp://localhost:61616");
        // 连接
        Connection connection = connectionFactory.createConnection();

        connection.start();

        // 会话
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 消息的目的地
        Destination destination = session.createQueue("my-queue");
        // 消费者
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    TextMessage message = (TextMessage) msg;
                    System.out.println("queue consumer received msg ：" + message.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
