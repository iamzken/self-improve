<%--
  Created by IntelliJ IDEA.
  User: wac
  Date: 16/8/16
  Time: 上午11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>URL=${uri}统计情况</title>
    <link rel="icon" href="http://gupaoedu.com/template/week_jiaohu/images/week//portal.ico" type="image/x-icon" />
    <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
</head>
<body>
<div id="container" style="min-width:400px;height:400px"></div>

<script>
    /**
     * Highcharts 在 4.2.0 开始已经不依赖 jQuery 了，直接用其构造函数既可创建图表
     **/
    var chart = new Highcharts.Chart('container', {
        title: {
            text: '咕泡学院 ${uri} 监控系统Demo ',
            x: -20
        },
        subtitle: {
            text: '数据来源:咕泡学院 www.gupaoedu.com',
            x: -20
        },
        xAxis: {
            categories: [
                <c:forEach items="${xaxix}" var="x">
                '${x}',
                </c:forEach>
            ]
        },
        yAxis: {
            title: {
                text: '调用次数'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '次'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '调用次数',
            data: ${yaxix}
        }]
    });
</script>
<%--URL=${uri} 统计情况--%>
<%--<table border="1" style="font-family: 'DejaVu Sans Mono', monospace">--%>
        <%--<tr>--%>
            <%--<th> 时间 </th>--%>
            <%--<th>调用次数</th>--%>
        <%--</tr>--%>
        <%--<c:forEach items="${dataMap}" var="data">--%>
            <%--<tr>--%>
                <%--<th> <fmt:formatDate value="${data.date}" pattern="yyyy-MM-dd HH:mm:ss" /></th>--%>
                <%--<th>${data.count}</th>--%>
            <%--</tr>--%>
        <%--</c:forEach>--%>

<%--</table>--%>

</body>
</html>
