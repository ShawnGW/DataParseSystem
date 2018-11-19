<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>V-Test Map</title>
		<script src="<c:url value="/resources/system/js/jquery.min.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/highcharts.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/exporting.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/highcharts-zh_CN.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/highcharts-more.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/boost.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/boost-canvas.js"/>" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<div id="container" style="width:1200px;height:1200px"></div>
		<script type="text/javascript">
            var binSummary = [];
            var binSet = new Set();
            $.ajax({
                url: '/DataParseSystem/GetWaferMap/Mapping?code=SNU&device=A383&lot=TCUT360100&cp=CP1&waferId=TCUT360101',
                async: false,
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    $.each(data, function(i, item) {
                        var dieInforObj={
                            x:item.x,
                            y:item.y,
                            z:item.z,
                            name:item.n.toString(),
                            hardbin:item.h,
                            softbin:item.s,
                            site:item.t,
                        };
                        if(!binSet.has(item.n)) {
                            binSet.add(item.n);
                            var binInfo = new Object();
                            binInfo.animation = false;
                            binInfo.turboThreshold =0;
                            binInfo.name = item.n;
                            var marker = new Object();
                            marker.symbol = 'square';
                            binInfo.marker = marker;
                            binInfo.data = new Array();
                            binInfo.data.push(dieInforObj);
                            binSummary.push(binInfo);
                        } else {
                            for(var i = 0; i < binSummary.length; i++) {
                                if(binSummary[i].name === item.n) {
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