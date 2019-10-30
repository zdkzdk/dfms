<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
</head>

<body>
<script type="text/javascript">
    function tiao() {
        console.info(${sessionScope.userType})

        <c:if test="${sessionScope.userType==0}">
        window.location.href = "<%=path %>/admin/index.jsp";
        </c:if>
        <c:if test="${sessionScope.userType==1}">
        window.location.href = "<%=path %>/admin/index.jsp";
        </c:if>
        window.location.href = "<%=path %>/admin/index.jsp";
    }

    setTimeout(tiao,1000)
</script>
<br> <br> <br> <br> <br> <br> <br> <br> <br>
<%--<shiro:principal property="userType"/>--%>

<center><img src="<%=path %>/img/loading32.gif">页面跳转中</center>
</body>
</html>
