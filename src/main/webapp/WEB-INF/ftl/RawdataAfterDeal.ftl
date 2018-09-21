<#import "spring.ftl" as spring/>
<html>
<head>
    <title>deal success</title>
    <script type="text/javascript" src="<@spring.url '/resources/jqurey/jquery-1.11.1.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/resources/jqurey/demos.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/resources/jqurey/jquery.loadmask.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/resources/jqurey/jquery-ui-1.9.2.custom.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/resources/jqurey/jquery.ui.all.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/resources/Font/css/font-awesome.css'/>">
    <style type="text/css">
        body {
            margin: 10px 20px;
            text-align: left;
            font-family: Arial, sans-serif;
        }
        table {
            border-collapse:collapse;
            border:1px solid black;
        }
        td,th{
            border:1px solid black;
        }
        .failedClass{
            background-color: red;
            color: white;
        }
    </style>
    <script type="text/javascript">
        $(function () {

        })
    </script>
</head>
<body>
   <#-- <#function  getStatus listName>
        <#return listName?split('&')[1]?lower_case>
    </#function>
    <#function  getFileName listName>
        <#return listName?split('&')[0]>
    </#function>-->
    <#assign order=1>
    <div id="tabDiv">
        <table id="list" class="ui-state-highlight ui-corner-all">
            <tr><th>order</th><th>FileName</th><th>Status</th><th>Details</th></tr>
        <#list dealList as dealfile>
            <#if (dealfile.status?lower_case='failed')>
                <tr class="failedClass"><td>${order}</td><td>${dealfile.fileName}</td><td>${dealfile.status?lower_case}</td><td>${dealfile.reason}</td></tr>
            <#else >
                <tr><td>${order}</td><td>${dealfile.fileName}</td><td>${dealfile.status?lower_case}</td><td>${dealfile.reason}</td></tr>
            </#if>
        <#assign order=order+1 >
        </#list>
        </table>
        <p></p>
        <a href="<@spring.url '/RawdataModify/upload'/>">重新上传</a> <p/>
    </div>
</body>
</html>