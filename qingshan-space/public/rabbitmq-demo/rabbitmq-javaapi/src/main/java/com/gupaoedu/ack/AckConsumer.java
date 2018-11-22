package com.gupaoedu.ack;

import com.gupaoedu.util.ResourceUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author: qingshan
 * @Date: 2018/9/21 10:53
 * @Description: 咕泡学院，只为更好的你
 * 消息消费者，用于测试消费者手工应答和重回队列
 */
public class AckConsumer {
    private final static String QUEUE_NAME = "TEST_ACK_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        final Channel channel = conn.createChannel();

        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" Waiting for message....");

        // 创建消费者，并接收消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message : '" + msg + "'");

                if (msg.contains("拒收")){
                    // 拒绝消息
                    // requeue：是否重新入队列，true：是；false：直接丢弃，相当于告诉队列可以直接删除掉
                    // TODO 如果只有这一个消费者，requeue 为true 的时候会造成消息重复消费
                    channel.basicReject(envelope.getDeliveryTag(), false);
                } else if (msg.contains("异常")){
                    // 批量拒绝
                    // requeue：是否重新入队列
                    // TODO 如果只有这一个消费者，requeue 为true 的时候会造成消息重复消费
                    channel.basicNack(envelope.getDeliveryTag(), true, false);
                } else {
                    // 手工应答
                    // 如果不应答，队列中的消息会一直存在，重新连接的时候会重复消费
                    channel.basicAck(envelope.getDeliveryTag(), true);
                }
            }
        };

        // 开始获取消息，注意这里开启了手工应答
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

}

