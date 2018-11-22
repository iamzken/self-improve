# 配置中心服务端
可以将文件存放在本地供客户端读取，也可以放在Git仓库。本例配置文件均放在Git仓库。
如果要测试客户端刷新，需要修改为自己的git公开地址，并且配置用户名和密码。

## 测试地址
访问以下地址会读取配置文件信息：
http://localhost:9010/service-gupao/dev
这个路径表示查询git仓库中是否存在service-gupao-dev.properties这个配置文件

访问以下地址会读取配置文件内容（如果浏览器看到这里面的中文乱码，可以忽略）：
http://localhost:9010/service-gupao-dev.properties
http://localhost:9010/master/service-gupao-dev.properties 

## 路径格式
/{application}/{profile}[/{label}] 
/{application}-{profile}.yml 
/{label}/{application}-{profile}.yml 
/{application}-{profile}.properties 
/{label}/{application}-{profile}.properties

label:表示git仓库的分支
application：表示具体服务单元的名称，即后面service-gupao的application-name
profile: 指定环境类型
  dev-开发环境
  prod-生产环境
  test-测试环境

## 解决中文乱码
https://blog.csdn.net/sinat_38843093/article/details/79960777

## 参考文章
https://blog.csdn.net/Weixiaohuai/article/details/82759084
