<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>V-Test Map</title>
		<script src="<c:url value="/resources/xufeng/js/highcharts.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/xufeng/js/exporting.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/xufeng/js/highcharts-zh_CN.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/xufeng/js/highcharts-more.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/xufeng/js/boost.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/xufeng/js/boost-canvas.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/xufeng/js/jquery.min.js"/>" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<div id="container" style="width:1200px;height:1200px"></div>
		<script type="text/javascript">
            var binSummary = [];
            var binSet = new Set();
            $.ajax({
                url: '/vtest/DAO/test?x=240&y=230',
                async: false,
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    $.each(data, function(i, item) {
                        var dieInforObj={
                            x:item.x,
                            y:item.y,
                            z:item.z,
                            name:item.name.toString(),
                            hardbin:item.hardbin,
                            softbin:item.softbin,
                            site:item.site,
                        };
                        if(!binSet.has(item.name)) {
                            binSet.add(item.name);
                            var binInfo = new Object();
                            binInfo.animation = false;
                            binInfo.turboThreshold =0;
                            binInfo.name = item.name;
                            var marker = new Object();
                            marker.symbol = 'square';
                            binInfo.marker = marker;
                            binInfo.data = new Array();
                            binInfo.data.push(dieInforObj);
                            binSummary.push(binInfo);
                        } else {
                            for(var i = 0; i < binSummary.length; i++) {
                                if(binSummary[i].name === item.name) {
                                    binSummary[i].data.push(dieInforObj);
                                }
                            }
                        }
                    })
                }
            })
            var chart = Highcharts.chart('container', {
                chart: {
                    renderTo: 'container',
                    type: 'scatter',
                    plotBorderWidth: 1,
                    zoomType: 'xy'
                },
                scrollbar: {
                    enabled: true
                },
                boost: {
                    useGPUTranslations: true,
                    usePreAllocated: true
                },
                legend: {
                    layout: 'vertical',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 70,
                    y: 70,
                    floating: true,
                    backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
                    borderWidth: 1
                },
                title: {
                    text: 'V-Test Mapping'
                },
                subtitle: {
                    text: 'Map'
                },
                xAxis: {
                    title: {
                        enabled: true,
                        text: 'x',
                    },
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true
                },
                yAxis: {
                    title: {
                        text: 'y'
                    }
                },
                tooltip: {
                    useHTML: true,
                    headerFormat: '<table>',
                    pointFormat: '<tr><th colspan="2"><h3>{point.name}</h3></th></tr>' +
                    '<tr><th>X:</th><td>{point.x}</td></tr>' +
                    '<tr><th>Y:</th><td>{point.y}</td></tr>' +
                    '<tr><th>HardBin:</th><td>{point.hardbin}</td></tr>' +
                    '<tr><th>SoftBin:</th><td>{point.softbin}</td></tr>' +
                    '<tr><th>Site:</th><td>{point.site}</td></tr>',
                    footerFormat: '</table>',
                    followPointer: true
                },
                series: binSummary
            });
		</script>
	</body>

</html>