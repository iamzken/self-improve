## mybatis generator 使用手册 ##

1. 修改 src/main/resources/mybatis/generatorConfi.xml
    classPathEntry 数据库驱动jar的位置
    jdbcConnection 连接信息、用户名、密码
    targetPackage  输出路径改成自己的
    table          定义各个表的信息，一个表一个table

2. 运行mvn mybatis-generator:generate