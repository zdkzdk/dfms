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
<form action="/fenxi/analyzedJingYingCharts" method="get" id="jingyingForm">
    <select id="yearSelect" name="year" style="margin-left: 10%;">
        <c:forEach var="year" items="${years}">
            <option value="${year}" <c:if test="${year == currentYear}"> selected="selected" </c:if> >${year}</option>
        </c:forEach>
    </select>
</form>
<div id="container" style="height: 100%"></div>

<script type="text/javascript">


    $("#yearSelect").change(function () {
        $("#jingyingForm").submit();
    })


    //=========================================
    var touru = "${touru}";
    var shouyi = "${shouyi}";
    var lirun = "${lirun}";

    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        title: {
            text: '当前年度经营状况雷达图'
        },
        tooltip: {},
        legend: {
            data: ['预算分配（Allocated Budget）', '实际开销（Actual Spending）']
        },
        radar: {
            // shape: 'circle',
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5]
                }
            },
            indicator: [
                {name: '利润', max: 250},
                {name: '投入', max: 380},
                {name: '收益', max: 520}

            ]
        },
        series: [{
            name: '预算 vs 开销（Budget vs spending）',
            type: 'radar',
            // areaStyle: {normal: {}},
            data: [
                {
                    value: [200, 300, 500],
                    name: '预算分配（Allocated Budget）'
                },
                {
                    value: [touru, shouyi, lirun],
                    name: '实际开销（Actual Spending）'
                }
            ]
        }],
        graphic: [
            {
                type: 'text',
                z: 100,
                left: 'left',
                top: 'middle',
                style: {
                    fill: '#333',
                    text: [
                        '本年度预期投入300(万元)',
                        '本年度预期预期收益500(万元)',
                        '本年度预期预期利润200(万元'
                    ].join('\n'),
                    font: '14px Microsoft YaHei'
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
