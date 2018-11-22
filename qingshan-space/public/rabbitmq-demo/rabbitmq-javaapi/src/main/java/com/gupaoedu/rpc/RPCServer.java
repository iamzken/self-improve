package com.gupaoedu.rpc;

import com.gupaoedu.util.ResourceUtil;
import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;

/**
 * @Author: qingshan
 * @Date: 2018/9/21 10:52
 * @Description: 咕泡学院，只为更好的你
 * RPC服务端，先启动
 */
public class RPCServer {
    private final static String REQUEST_QUEUE_NAME="RPC_REQUEST";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        //创建一个新的连接 即TCP连接
        Connection connection = factory.newConnection();
        //创建一个通道
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
        //设置prefetch值 一次处理1条数据
        channel.basicQos(1);

        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                BasicProperties replyProperties = new BasicProperties.Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();

                //获取客户端指定的回调队列名
                String replyQueue = properties.getReplyTo();
                //返回获取消息的平方
                String message = new String(body,"UTF-8");
                // 计算平方
                Double mSquare =  Math.pow(Integer.parseInt(message),2);
                String repMsg = String.valueOf(mSquare);

                // 把结果发送到回复队列
                channel.basicPublish("",replyQueue,replyProperties,repMsg.getBytes());
                //手动回应消息应答
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(REQUEST_QUEUE_NAME, true, consumer);

    }
}
