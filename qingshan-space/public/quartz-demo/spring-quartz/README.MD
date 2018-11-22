# Quartz Demo
  Spring 整合 Quartz，有两种存储方式，一种是RAM存储，一种是JDBC存储。<br/>

## 代码说明
  Spring整合Quartz进行配置 步骤如下：<br/>
    1：定义工作任务的Job；<br/>
    2：定义触发器Trigger，并将触发器与工作任务绑定；<br/>
    3：定义调度器Scheduler，并将Trigger注册到Scheduler。<br/>
    4：配置还有点问题，持久化到数据库时无法注入applicationContext。

## SQL脚本
  持久化到数据库（JDBC存储）的方式需要修改数据库连接，并创建quartz的11张表。建表脚本在位置 resource/db。<br/>
  持久化到数据库以后，如果修改了任务名称或者类路径，需要清空所有11张表，重新运行，否则会报错。<br/>
  重复运行可能会报错，key my_group_1.my_job_1在数据库中已存在，忽略即可。<br/>
  
## 测试方法
  运行单元测试类QuartzTest的main()方法。需要测试不同的存储方式，切换spring_quartz里面两个scheduler的注释即可。<br/>