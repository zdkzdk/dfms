<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height: 100%">
<head>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>

    <title>analyzedCharts</title>
    <script type="text/javascript" src="<%=path %>/js/echarts.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/jquery.js"></script>

</head>
<body style="height: 100%; margin: 0">
<form action="/fenxi/analyzedFeiYongCharts" method="get" id="feiYongForm">
    <select id="yearSelect" name="year" style="margin-left: 10%;">
        <c:forEach var="year" items="${years}">
            <option value="${year}" <c:if test="${year == currentYear}"> selected="selected" </c:if> >${year}</option>
        </c:forEach>
    </select>
</form>
<div id="container" style="height: 100%"></div>

<script type="text/javascript">
    $("#yearSelect").change(function () {
        $("#feiYongForm").submit();
    })
//================================
    eval('var map =${map}');

    var legendName = [];
    var seriesDatas = [];

    for (var key in map) {
        legendName.push(key);
        var seriesData = {
            value: '',
            name: ''
        };
        seriesData.name = key;
        seriesData.value = map[key];
        seriesDatas.push(seriesData);
    }

    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        title : {
            text: '年度费用分析饼图',
            subtext: 'dc corporation',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: legendName
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:seriesDatas,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
</body>
</html>
