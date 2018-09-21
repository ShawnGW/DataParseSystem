<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/8/29
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>ModifySucess</title>
    <style type="text/css">
        body {
            margin: 10px 20px;
            text-align: center;
            font-family: Arial, sans-serif;
        }
    </style>
</head>
<body>
    Congratulations, Modify Success!!!&nbsp&nbsp
    <a href="<c:url value="/RawdataModify/upload"/>">重新上传</a> <p/>
    <c:forEach items="${dealList}" var="dealfile">
        ${dealfile}<br>
    </c:forEach>
</body>
</html>
