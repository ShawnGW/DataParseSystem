<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/8/24
  Time: 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Upload</title>
</head>
<meta charset="UTF-8">
<script type="text/javascript" src="<c:url value="/resources/jqurey/jquery-1.11.1.js"/>"></script>
<script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
<script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
<script src="https://img.hcharts.cn/highcharts/modules/heatmap.js"></script>
<script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
<style type="text/css">
    body {
        margin: 10px 20px;
        text-align: center;
        font-family: Arial, sans-serif;
    }
    form {
        background-color: yellowgreen;
        border: 1px solid yellowgreen ;
    }
</style>
<script type="text/javascript">
    $(function () {
        $("#sbt").click(
            function () {
                $("#sbt").attr("disabled", true);
                $("#target").submit();
            }
        )
    })
</script>
<body>
 <div id="content">
     <fieldset>
         <legend>Bumpping mapping</legend>
         <form id="target" method="post" action="" enctype="multipart/form-data">
             <input name="file" type="file" size="20px"><p/>
             <input type="button" id="sbt" value="提交">
         </form>
     </fieldset>
     <div id="container" style="height: 400px; min-width: 310px; max-width: 600px; margin: 0 auto"></div>
     <script>
         Highcharts.chart('container', {
             chart: {
                 type: 'heatmap',
                 marginTop: 40,
                 marginBottom: 80,
                 plotBorderWidth: 1
             },
             title: {
                 text: 'Sales per employee per weekday'
             },
             xAxis: {
                 categories: ['Alexander', 'Marie', 'Maximilian', 'Sophia', 'Lukas', 'Maria', 'Leon', 'Anna', 'Tim', 'Laura']
             },
             yAxis: {
                 categories: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
                 title: null
             },
             colorAxis: {
                 min: 0,
                 minColor: '#FFFFFF',
                 maxColor: Highcharts.getOptions().colors[0]
             },
             legend: {
                 align: 'right',
                 layout: 'vertical',
                 margin: 0,
                 verticalAlign: 'top',
                 y: 25,
                 symbolHeight: 280
             },
             tooltip: {
                 formatter: function () {
                     return '<b>' + this.series.xAxis.categories[this.point.x] + '</b> sold <br><b>' +
                         this.point.value + '</b> items on <br><b>' + this.series.yAxis.categories[this.point.y] + '</b>';
                 }
             },
             series: [{
                 name: 'Sales per employee',
                 borderWidth: 1,
                 data: [[0, 0, 10], [0, 1, 19], [0, 2, 8], [0, 3, 24], [0, 4, 67], [1, 0, 92], [1, 1, 58], [1, 2, 78], [1, 3, 117], [1, 4, 48], [2, 0, 35], [2, 1, 15], [2, 2, 123], [2, 3, 64], [2, 4, 52], [3, 0, 72], [3, 1, 132], [3, 2, 114], [3, 3, 19], [3, 4, 16], [4, 0, 38], [4, 1, 5], [4, 2, 8], [4, 3, 117], [4, 4, 115], [5, 0, 88], [5, 1, 32], [5, 2, 12], [5, 3, 6], [5, 4, 120], [6, 0, 13], [6, 1, 44], [6, 2, 88], [6, 3, 98], [6, 4, 96], [7, 0, 31], [7, 1, 1], [7, 2, 82], [7, 3, 32], [7, 4, 30], [8, 0, 85], [8, 1, 97], [8, 2, 123], [8, 3, 64], [8, 4, 84], [9, 0, 47], [9, 1, 114], [9, 2, 31], [9, 3, 48], [9, 4, 91]],
                 dataLabels: {
                     enabled: true,
                     color: '#000000'
                 }
             }]
         });
     </script>

 </div>
</body>
</html>
