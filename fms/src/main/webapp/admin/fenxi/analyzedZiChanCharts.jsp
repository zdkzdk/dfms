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

    <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
    <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
    <script type='text/javascript' src='<%=path %>/dwr/interface/FenXiController.js'></script>
</head>
<body style="height: 100%; margin: 0">
<select id="yearSelect" name="year" style="margin-left: 10%;"></select>
<div id="container" style="height: 100%"></div>

<script type="text/javascript">
    $(function () {
        $('#yearSelect').change(function () {
            window.year = $('#yearSelect').val();
            FenXiController.echarts(year,callback);

        })


        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        option = {
            xAxis: {
                type: 'category',
                data: ''
            },
            yAxis: {
                type: 'value'
            },
            legend: {
                data:['最高气温','最低气温']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series: [{
                data: '',
                type: 'line'
            }]
        };
        year = 2019;
        FenXiController.echarts(year,callback);
        function callback(data) {
            eval(" window.map = " + data);
            option.xAxis.data = map.xAxisData;
            option.series[0].data = map.yAxisData;
            /*
            给年份下拉框渲染数据
             */
            var str = '';
            var set = map.yearSet;
            for (var i in set) {
                str += '<option value = ' + set[i] + '>' + set[i] + '</option>';
            }
            $('#yearSelect').html(str);

            if (option && typeof option === "object") {
                myChart.setOption(option, true);
            }
            $('#yearSelect').val(year);
        }
    })

</script>

</body>
</html>
