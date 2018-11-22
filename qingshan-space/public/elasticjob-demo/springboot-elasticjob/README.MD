# Spring Boot Elastic-Job 示例
Spring Boot Elastic-Job 
 
## 环境说明
需要在本机安装mysql、zookeeper、IDEA需要安装lombok插件。

## 依赖版本
elastic-job 2.1.5
lombok 1.16.18
druid 1.1.10
mysql

## 开发步骤
1. pom文件引入依赖
2. 任务编写 MySimpleJob.java
3. Spring配置文件spring-elasticjob.xml：注册中心（Zookeeper）、数据库连接、作业
4. 配置类ElasticJobConfig.java 扫描xml和.properties文件

## 运行说明
运行App启动类后，数据库会创建两张表 job_execution_log、 job_status_trace_log。
Zookeeper上会创建相应的节点。

## 注解方式配置
Spring Boot中暂时没有提供stater使用，可以参考[yinjihuan](https://www.jianshu.com/p/dd3c5b7d7694) 的方案