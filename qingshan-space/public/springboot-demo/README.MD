# 0. 启动顺序
    首先启动注册中心服务端 Eureka Server
    再启动配置中心服务端 Config Server
    其他服务启动顺序没有要求

# 1. 各工程端口
	EurekaServer 注册中心服务端 8900<br/>
	EurekaClient 服务提供者 8922，同时也是配置中心客户端<br/>
	EurekaClientCopy 服务提供者 8923<br/>
	Ribbon 服务消费者，负载均衡 8940<br/>
	Feign 服务消费者，声明式服务调用 8950<br/>
	Zuul 网关，路由  8960<br/>
	Config Server 配置中心服务端 9010（配置中心服务端和客户端的说明见子工程的README文档）<br/>

# 2. 注册中心
	a.注册中心服务端管理界面，启动EurekaServer，访问： http://127.0.0.1:8900<br/>
	b. 演示注册中心客户端：<br/>
	  启动 EurekaClient 和 EurekaClientCopy<br/>
	  http://localhost:8922/hello?name=菲菲<br/>
	  http://localhost:8923/hello?name=菲菲<br/>

# 3. Ribbon
	a. 演示Ribbon负载均衡<br/>
	启动两个服务提供者（8922和8923），多次访问：http://127.0.0.1:8940/hello?name=菲菲<br/>

	b. 演示断路器：<br/>
	EurekaClient必须先于Ribbon工程启动<br/>
	停止EurekaClient工程和EurekaClientCopy，访问 http://127.0.0.1:8940/hello?name=菲菲<br/>
  
# 4. Feign
	启动EurekaServer工程<br/>
	启动EurekaClient工程<br/>
	启动Feign工程，访问 http://127.0.0.1:8950/hello?name=菲菲<br/>
	因为Feign集成了Ribbon，所以可以实现负载均衡<br/>
 
	演示Feign中的断路器：<br/>
	停止EurekaClient工程，访问 http://127.0.0.1:8950/hello?name=菲菲<br/>
 
	Hystrix仪表盘<br/>
		访问：http://localhost:8940/hystrix/

# 5. Zuul
	演示zuul根据path路由：<br/>
	访问：http://localhost:8960/api-ribbon/hello?name=菲菲<br/>
	访问：http://localhost:8960/api-feign/hello?name=菲菲<br/>
	访问：http://localhost:8960/api-gupao/hello?name=菲菲<br/>
	
# 6. Config
    [服务端](http://git.gupaoedu.com/vip/qingshan-space/blob/master/public/springboot-demo/config-server/README.MD)
    客户端也就是Eureka Client