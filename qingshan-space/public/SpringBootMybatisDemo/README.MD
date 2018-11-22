# SpringBootMybatisDemo
SpringBoot + Mybatis + LayUI <br/>
一个简单的提供增删改查页面的demo，主要是用来演示Mycat分库分表的，当然也可以作为一个SpringBoot的脚手架使用。

## mycat 演示说明
1. 首先要创建三个虚拟机，三台虚拟机都安装mysql数据库，运行3306端口；
2. mycat的服务可以运行在windows上，也可以运行在linux上（推荐CentOS 7），必须修改的配置：server.xml、schema.xml、rule.xml以及具体分片策略的txt配置；
3. mycat配置文件在resource\mycat目录下，除了里面的5个文件其他都没改。需要把schema.xml里面的三个虚拟机IP改成自己的虚拟机IP；
4. 在老师上课演示的案例中，mycat的服务是安装在第一个虚拟机上的，配置完成后在bin目录用./mycat start命令，运行8066端口。

## SQL脚本
在resources\db目录下。建表语句（数据库和表）要分别在三个虚拟机的主机中创建。<br/>
mycat服务启动后，可以执行 resource/db 里面的SQL脚本来测试不同的分片策略。

## 界面增删改查数据测试地址
访问：
http://127.0.0.1:9091/stu/studentList