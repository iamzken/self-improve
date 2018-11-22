# 服务提供者
是一个注册中心的客户端，也是一个配置中心的客户端。
注意git仓库里面的文件名称，一定要和客户端的服务名称一致，否则获取不到。

## 从配置中心服务端获取配置内容
http://localhost:8922/getConfig

## 测试刷新配置
    1.  Git提交修改——本例使用了咕泡的git公共仓库，如果要修改，必须修改配置中心服务端为自己的git仓库
    2.  Postman 访问 http://localhost:8922/refresh （只支持post方法，不能用浏览器刷新）
    3.  再次获取配置 http://localhost:8922/getConfig

## 消息总线方式刷新
如果有多个实例，只需要访问其中任意一个：
Postman 访问 http://localhost:8922/bus/refresh
【a】只针对某个实例更新：/bus/refresh?destination=service-gupao:8922，只更新端口为8922的service-gupao服务实例
【b】某个服务的全部实例更新: /bus/refresh?destination=service-gupao:**,这样会更新service-gupao服务的所有实例


