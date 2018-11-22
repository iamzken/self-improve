# Spring Boot 整合RabbitMQ

## 环境版本
  Spring Boot 2.0.6.RELEASE
  JDK 1.8

## 工程结构
消息消费者 springboot-rabbit-consumer
消息生产者 springboot-rabbit-producer

## 开发步骤
  生产者：  
1.  创建生产者，注入gupaoTemplate发送消息。
     在任何需要发送MQ消息的地方注入gupaoTemplate，或者在单元测试类中注入生产者，调用send()方法。
  消费者： 
1.  创建配置类，定义队列、交换机、绑定
2.  创建消费者，监听队列

## 界面测试地址
http://127.0.0.1:9071/merchantList  ，新增、删除、修改信息、修改状态会发送MQ消息到VIP_TOPIC_EXCHANGE，
路由到VIP_SECOND_QUEUE。

## 测试方法
1.  先启动消费者springboot-rabbit-consumer
2.  在界面上修改商户信息，或者调用生产者的单元测试类AppTests发送消息。

## 其他说明
  Spring AMQP默认使用的消息转换器是SimpleMessageConverter。  
  Jackson2JsonMessageConverter 用于将消息转换为JSON后发送。
  FourthConsumer里面实现了Java对象与JSON报文的转换。