<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/9/3
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Uplaod</title>
    <script type="text/javascript" src="<c:url value="/resources/jqurey/jquery-1.11.1.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/jqurey/validate-1.14.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/jqurey/jquery.ui.core.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/jqurey/jquery.ui.datepicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/jqurey/jquery-ui-1.9.2.custom.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/jqurey/jquery.loadmask.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/jqurey/demos.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/jqurey/jquery.loadmask.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/jqurey/jquery-ui-1.9.2.custom.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/jqurey/jquery.ui.all.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/Font/css/font-awesome.css"/>">
<script type="text/javascript">
    $(function () {
        $.ajax({
            url:"",
            date:new FormData(${"#bumpMapping"}),
            success: function (date) {

            }
        })
    })
</script>
    <style type="text/css">
        body {
            margin: 10px 20px;
            font-family: Arial, sans-serif;
            min-width: 1000px;
        }
    </style>
</head>
<body>
   <div>
       <form method="post" action="" enctype="multipart/form-data">
           <select name="customerCode"></select>
           <input id="bumpMapping" type="file" name="bumpMapping" size="20px">
       </form>
   </div>
<div id="mappingInfors"></div>
</body>
</html>
