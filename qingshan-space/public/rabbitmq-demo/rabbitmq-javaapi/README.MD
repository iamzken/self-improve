# RabbitMQ Java API编程
需要在本机安装Erlang、RabbitMQ并运行。<br/>

RabbitMQ默认端口：5672。

# 目录说明
  ack  消息手工确认、不确认、拒绝和重回队列的测试
  confirm 生产者发送确认的测试
  dlx 死信队列的测试
  limit 消费者限流的测试
  returnlistener 当消息无法匹配到队列时，会发回给生产者
  rpc RPC测试
  simple 简单收发消息测试
  transaction 生产者发送事务的测试
  ttl 消息存活时间测试