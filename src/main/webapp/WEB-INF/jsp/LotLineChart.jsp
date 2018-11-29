<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/11/23
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Lot Line Chart</title>
    <link rel="stylesheet" href="<c:url value="/resources/system/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/system/css/button.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/status-select.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/bootstrap-table.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/bootstrap-table-fixed-columns.css"/>" />
    <script src="<c:url value="/resources/system/js/jquery.min.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/highcharts.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/exporting.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/highcharts-zh_CN.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/highcharts-more.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/bootstrap.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/system/js/bootstrap-table.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/bootstrap-table-fixed-columns.js"/>" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="panel panel-default">

    <div class="panel-body">
        <div  id="SelectMapping" class="col-lg-9">
            <select class="customer_code button-3d"id="customer_code">
            </select>
            <select class="device button-3d" id="device">
            </select>
            <select class="lot_id button-3d" id="lot_id">
            </select>
            <select class="cp_step button-3d" id="cp_step">
            </select>
        </div>
        <div class="col-lg-3">
            <button class="button  button-primary button-pill  button-3d" id="drawMap" type="button">画图</button>
            <button class="button  button-primary button-pill  button-3d" id="refresh" type="button">刷新</button>
        </div>
    </div>
    <div class="panel-body" id="lotLineChart">
        <div id="container" style="min-width:400px;height:600px"></div>
    </div>
    <div id="wTable">
    </div>
</div>
<script src="<c:url value="/resources/system/js/lotLineChart.js"/>" type="text/javascript"></script>
</body>
</html>
