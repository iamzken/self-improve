
#  POI工具类

1.  主要功能：替换word文档中的变量（包括段落和表格里面的），并将word文档转换成pdf。模板word文档 —— 填充变量后的word文档 —— pdf。

2. 适用场合：批量生成合同文档的场景。从数据库取值直接填充到文档中以后，减少了需要手写的内容，大大地提升了合同签署、审核的效率，合作方只要手写签名日期等等少量字段就可以了。
3.  这段代码是从原生产项目中剥离出来的，总计生成过上百万个PDF合同（20万+商户 * 五式），理论上值得信赖的，但在各自环境中使用还是要多测试。

## 免责声明
1. **代码仅供参考，用于生产环境请自行充分测试，老师不对代码的使用负任何责任**。
2. 由于老师平时很忙，没有时间解答各种细节问题，请自行解决。后续代码也不会再继续修改优化。
2. 如有疑问可以先在issue中提问：
http://git.gupaoedu.com/vip/qingshan-space/issues

## 附件
均放在 src\main\resources\document 目录下
* word模板
* jar包
* [OpenOffice Windows版](http://down.tech.sina.com.cn/page/8375.html) ， 请下载4.0.2或者4.1.3版本


## 涉及技术
1. Apache POI —— 用于读取和写入word文档
2. OpenOffice + JodConverter —— 用于将word转换为PDF

## 代码说明
需要修改的地方：
1. 文件的存储位置。包括临时生成的word文档和转换后的pdf文档。
2. 所有异常的位置，需要自行添加日志输出。
3. 替换的字段名称，需要自己定义bean。段落内容和表格内容是不同的bean。可以定义多个。
4. 表格是根据关键字定位的，需要修改模板以及replaceStoreTable()里面的关键字。
5. OpenOffice的安装位置，需要放到配置文件中，开发和测试环境不同。

## 开发环境调试说明
1. 转换pdf需要安装OpenOffice4，并启动8100端口。命令如下：

``` shell
cd C:\Program Files (x86)\OpenOffice 4\program 
```

``` shell
soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;"  -nofirststartwizard  
```

2. 转换docx模板需要jodconverter.jar 2.2.2版本。
maven中央仓库没有2.2.2，需要手动下载jar包，放到本地仓库或私服中。

比如maven仓库在D:\repository，则放到如下目录：
D:\repository\com\artofsolving\jodconverter\2.2.2

3. 把template_blank.docx放到D:\template，运行PoiTest即可

## 生产环境部署说明
1. 需要安装``OpenOffice 4``并启动8100端口服务。
2. 注意：Windows环境的OpenOffice和Linux环境的OpenOffice存在一定差异，上线前务必在Linux环境中调试。


## 模板说明
请务必注意：
1. 提供的测试模板，是当时经过大量修改测试的，仅删除了正文内容。在调试模板的过程中可能会出现很多问题，比如变量的字体也会影响内容替换，建议基于提供的模板增加内容，新建word文档可能要花很多时间调试。
2. 变量的字体、格式等等不要轻易修改！
3. 模板注意备份。




