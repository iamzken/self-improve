package com.gupaoedu.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 先启动订阅者
 */
public class Subscriber {

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
        Destination destination = session.createTopic("api-topic");

        // 消费者1
        MessageConsumer consumer1 = session.createConsumer(destination);

        consumer1.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    TextMessage message = (TextMessage) msg;
                    System.out.println("topic consumer1 received msg : " + message.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 消费者2
        MessageConsumer consumer2 = session.createConsumer(destination);
        consumer2.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    TextMessage message = (TextMessage) msg;
                    System.out.println("topic consumer2 received msg : " + message.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
