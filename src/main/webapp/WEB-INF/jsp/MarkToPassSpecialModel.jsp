<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/12/25
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Upload</title>
    <link rel="stylesheet" href="<c:url value="/resources/system/css/button.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/Font/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resources/system/js/jquery-1.11.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/system/js/jquery.validate.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/system/js/messages_zh.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
</head>
<body>
<div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h1 style="text-align: center">MarkDie TO PassDie Model Upload</h1>
        </div>
        <div class="panel-body col-md-offset-3 ">
            <form id="rawForm" method="post" action="/DataParseSystem/DealMarkToTestDie/GenerateModel" enctype="multipart/form-data">
                <div class="form-group col-md-offset-3 ">
                    <input type="file" name="uploadFile" id="uploadFile" multiple class="file-loading" />
                </div>
                <div class="form-group">
                    <label class="col-lg-1">描述</label>
                    <textarea class="form-control" name="description" rows="3" style="width: 50%"></textarea>
                </div>
                <input class="button  button-primary button-pill  button-3d pull-right"  type="submit" value="Submit">
            </form>
        </div>
    </div>
</div>
</body>
<script>
    $(document).ready(function () {
        var warning='<i class="fa fa-exclamation-triangle" style="color: red"></i>';
        jQuery.validator.addMethod("isRaw",function (value,element) {
            var raw=/^.*\.raw$/;
            return this.optional(element)||(raw.test(value));
        },warning+"文件扩展名必须为raw");
        $("#rawForm").validate({
            rules:{
                uploadFile:{
                    required:true,
                    isRaw:true
                },
                description:"required"
            }
        })
    })
</script>
</html>
