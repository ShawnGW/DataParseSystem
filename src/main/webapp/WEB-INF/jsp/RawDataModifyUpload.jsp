<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/8/29
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<meta charset="UTF-8">
<head>
    <title>RawdataModifyUpload</title>
</head>
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
<style type="text/css">
    body {
        margin: 10px 20px;
        /*text-align: center;*/
        font-family: Arial, sans-serif;
        min-width: 1000px;
        /*font-size: medium;*/
    }
    form{
        background-color: yellowgreen;
    }
    .error{
        color: red;
    }
    .prevLabel
    {
        margin-right: 10px;
        padding-left: 500px;
        width: 100px;
    }
    i{
        padding-left: 5px;
    }
</style>
<script type="text/javascript">
    $(function () {
        $("#sbt").click(
            function () {
                $("#FormFieldSet").mask("正在处理中...");
                $("#target").submit();
            }
        );
        setInterval( function () {
            $("#time").html(new Date())
        },1000);
        jQuery.validator.addMethod("CheckFile", function(value, element) {
            var filepath=$("#modifyFile").val();
            var fileArr=filepath.split("\\");
            var fileTArr=fileArr[fileArr.length-1].toLowerCase().split(".");
            var filetype=fileTArr[fileTArr.length-1];
            if(filetype == "csv"||filetype == "zip"){
                return true;
            }else{
                return false;
            }
        }, "上传文件格式错误");
        $("#target").validate({
            rules:{
                type:{
                    required:true,
                },
                modifyFile:{
                    CheckFile:true
                }
            },
            messages:{
                type:{
                    required:"类型必选"
                },
                modifyFile:{
                    checkPic:"接受jpg或者png等图片"
                }
            }
        })
    })
</script>
<body>
    <div>
        <p><i class="fa fa-clock-o" ></i><label id="time" style="margin-left: 5px"></label></p>
        <fieldset id="FormFieldSet" class="ui-state-default ui-corner-all">
            <legend>RawData Modify Upload<label id="tip" style="color: red">(目前仅接受csv格式和zip压缩包格式)</label></legend>
                <form  class="ui-corner-all" id="target" method="post" enctype="multipart/form-data" action="<c:url value="/RawdataModify/modify"/>">
                    <p><label class="prevLabel" for="type">修改类型<i class="fa fa-list-ul"></i></label> <select id="type" name="type" class="ui-corner-all">
                        <option value="Modify" selected>仅修改测试点</option>
                        <option value="deapModify">深度修改(增加测试点)</option>
                    </select></p>
                    <p><label class="prevLabel" for="modifyFile">上传文件<i class="fa fa-folder"></i></label><input type="file" id="modifyFile" name="modifyFile" class="ui-corner-all"></p>
                    <p><label class="prevLabel" for="sbt">点击提交<i class="fa fa-arrow-circle-o-up"></i></label><input type="button" id="sbt" value="提交" class="ui-corner-all ui-button-icon" /></p>
                </form>
        </fieldset>
    </div>
</body>
</html>
