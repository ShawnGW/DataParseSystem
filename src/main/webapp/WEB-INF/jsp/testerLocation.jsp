<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2019/5/27
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html lang="zh">
<head>
    <title>Tester</title>
    <link rel="stylesheet" href="<c:url value="/resources/system/css/button.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/Font/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">


    <script src="<c:url value="/resources/system/js/jquery-1.11.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/system/js/bootstrap-table.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/xlsx.core.min.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/xlsx.full.min.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/FileSaver.min.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/tableExport.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/bootstrap-table-export.min.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/highcharts.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/exporting.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/highcharts-zh_CN.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/highcharts-more.js"/>" type="text/javascript"
            charset="utf-8"></script>
</head>
<body>
<div class="modal fade" id="details" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 100%;">
    <div class="modal-dialog" style="width: 1500px" id="detailModal">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    详细情况
                </h4>
            </div>
            <div class="modal-body">
                <table id="remainingTable" border="2" class="div-float"></table>
                <table id="detailsTable" class="div-float" style="margin: auto;position: absolute;left:70%;bottom: 20%"></table>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="history" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 100%;">
    <div class="modal-dialog" style="width: 1500px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    历史
                </h4>
            </div>
            <div class="modal-body">
                <table id="historyTable" border="2"></table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
<P class="button-group">
    <button type="button" class="button button-primary button-3d button-pill" id="A2">A2</button>
    <button type="button" class="button button-action button-3d button-pill" id="D1">D1</button>
</P>
<div id="containerA2" style="width:100%;display: none" ></div>
<div id="containerD1" style="width:100%"></div>
<script src="<c:url value="/resources/system/js/testerLocation.js"/>" type="text/javascript" charset="utf-8"></script>
</body>
</html>
