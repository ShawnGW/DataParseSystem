<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/11/19
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Degree Transform</title>
    <link rel="stylesheet" href="<c:url value="/resources/system/select/bootstrap-select.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/Font/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resources/system/js/jquery.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/system/js/bootstrap-filestyle.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>" type="text/javascript" ></script>
    <script src="<c:url value="/resources/system/select/bootstrap-select.min.js"/>" type="text/javascript" ></script>
    <script src="<c:url value="/resources/system/select/defaults-zh_CN.min.js"/>" type="text/javascript" ></script>
</head>
<body>
<div class="col-md-4"></div>
<div class="col-md-4">
    <form class="form-horizontal" role="form" method="post" action="" enctype="multipart/form-data">
        <div class="form-group">
            <label for="coordinateX" class="col-sm-2 control-label"><i class="fa fa-arrow-circle-o-right"></i>CoorX</label>
            <div class="col-sm-10">
                <input name="coordinateX" type="text" class="form-control" id="coordinateX"
                       placeholder="please input modify value of coordinate x">
            </div>
        </div>
        <div class="form-group">
            <label for="coordinateY" class="col-sm-2 control-label"><i class="fa fa-arrow-circle-o-down"></i>CoorY</label>
            <div class="col-sm-10">
                <input type="text" name="coordinateY" class="form-control" id="coordinateY"
                       placeholder="please input modify value of coordinate y">
            </div>
        </div>
        <div class="form-group">
            <label for="MapChoose" class="col-sm-2 control-label" ><i class="fa fa fa-list-ul"></i>Type</label>
            <div class="col-sm-10">
                <select id="MapChoose" name="MapChoose" class="btn-info" style=" padding-left: 10px;display: block ; width: 220px ;height: 35px;border-radius: 3px;">
                    <option value="TestDie" selected>Only TestDie</option>
                    <option value="MarkDie">With Mark Die</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="jobData" class="col-sm-2 control-label"><i class="fa fa-file-o"></i>Data</label>
            <div class="file-container col-sm-10" style="display:inline-block;position:relative;overflow: hidden;vertical-align:middle">
                <button class="btn btn-success fileinput-button" type="button">上传</button>
                <input type="file" name="rawdata" id="jobData" onchange="loadFile(this.files[0])" style="position:absolute;top:0;left:0;font-size:34px; opacity:0">
            </div>
            <span id="filename" style="vertical-align: middle;size: 180px;margin-left: 100px">未上传文件</span>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">提交</button>
            </div>
        </div>
    </form>
</div>
<div class="col-md-4"></div>
<script type="application/javascript">
    function loadFile(file)
    {
        $("#filename").html(file.name);
    }
</script>
</body>
</html>
