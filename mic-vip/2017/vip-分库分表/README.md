# 数据库分库分表

> 在互联网行业中，最大的特点是并发量高、数据量大；为了应对这两个特点，后端的技术架构需要使用各种技术来支撑；而这个专题所讲的是关于数据库层面的优化
> 当数据库的访问量到达一定的瓶颈的时候，当数据库单表数据比较大严重影响ddl性能的时候。我们应该根据什么思路去做优化和调整

## 课程大纲
- **持久化存储在大型分布式架构下部分需要应对的问题**
- **如何去做分库分表**
- **拆分策略**
- **分库分表带来的问题及解决方案**
- **如何知道当前的系统需要做分库分表**
- **Mysql的读写分离实战**
- **Mysql主主复制以及基于keepalived实现双主高可用**
- **基于HAProxy实现**
- **主从同步延迟问题及解决方案**
- **认识Mycat及Mycat安装**
- **Mycat数据切分实战**
- **Mycat读写分离实战**
- **Mycat分片策略**
- **Mycat全局表配置**

## centos7安装mysql5.7操作步骤
1. 下载mysql的repo源
> wget http://repo.mysql.com/mysql57-community-release-el7-8.noarch.rpm
2. 安装源
> rpm -ivh mysql57-community-release-el7-8.noarch.rpm
3. 安装数据库
> yum install mysql-server
4. 启动数据库
> systemctl start mysqld

## 登录到mysql
1. 5.7版本默认对于root帐号有一个随机密码，可以通过 grep "password" /var/log/mysqld.log获得，root@localhost: 此处为随机密码

2. 运行mysql -uroot -p 回车

3. 粘贴随机密码

## 操作
1. 默认的随机密码是没办法直接对数据库做操作的，需要修改密码，然后，5.7版本用了validate_password密码加强插件，因此在修改密码的时候绝对不是 123456 能糊弄过去的。需要严格按照规范去设置密码

2. 但是，如果想让密码简单点也可以，降低安全策略， 登录到mysql客户端执行如下两条命令

set global validate_password_length=1;

set global validate_password_policy=0; 

3. 这样就能设置简单的密码了，但是密码长度必须是大于等于4位

## 赋权操作
> 默认情况下其他服务器的客户端不能直接访问mysql服务端，需要对ip授权

> GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;







