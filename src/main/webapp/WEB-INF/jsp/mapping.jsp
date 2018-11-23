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
		<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
		<link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">
		<link rel="stylesheet" href="<c:url value="/resources/system/css/button.css"/>" />
	</head>
	<body>
	<div id="ChartsPage">
		<div class="panel panel-default" style="width: 70%; float: left;">
			<div class="panel-heading">
				Mapping Chart
			</div>
			<div class="panel-body">
				<div id="primaryTest">
					<div id="container" style="width:100%;height:100%"></div>
				</div>

			</div>
		</div>
		<div class="panel panel-default" style="width: 30%;float: right;">
			<div class="panel-heading">
				<button class="button button-action button-primary button-pill button-tiny" id="primaryChange">初测</button>
				<button class="button button-action button-primary button-pill button-tiny" id="retestChange">复测</button>
				<button class="button button-action button-primary button-pill button-tiny" id="finalChange">终测</button>
			</div>
			<div class="panel-body">
				<table id="MappingTable" class="table table-striped table-bordered table-hover display" >
				</table>
			</div>
		</div>
	</div>
		<%--<div id="container" style="width:1200px;height:1200px"></div>--%>
		<%--<script type="text/javascript">--%>
            <%--var url=window.location.search;--%>
            <%--var binSummary = [];--%>
            <%--var binSet = new Set();--%>
            <%--$.ajax({--%>
                <%--url: '/DataParseSystem/GetWaferMap/Mapping'+url,--%>
                <%--async: false,--%>
                <%--type: 'GET',--%>
                <%--dataType: 'json',--%>
                <%--success: function(data) {--%>
                    <%--$.each(data, function(i, item) {--%>
                        <%--var dieInforObj={--%>
                            <%--x:item.x,--%>
                            <%--y:item.y,--%>
                            <%--z:item.z,--%>
                            <%--name:item.n.toString(),--%>
                            <%--hardbin:item.h,--%>
                            <%--softbin:item.s,--%>
                            <%--site:item.t,--%>
                        <%--};--%>
                        <%--if(!binSet.has(item.n)) {--%>
                            <%--binSet.add(item.n);--%>
                            <%--var binInfo = new Object();--%>
                            <%--binInfo.animation = false;--%>
                            <%--binInfo.turboThreshold =0;--%>
                            <%--binInfo.name = item.n;--%>
                            <%--var marker = new Object();--%>
                            <%--marker.symbol = 'square';--%>
                            <%--binInfo.marker = marker;--%>
                            <%--binInfo.data = new Array();--%>
                            <%--binInfo.data.push(dieInforObj);--%>
                            <%--binSummary.push(binInfo);--%>
                        <%--} else {--%>
                            <%--for(var i = 0; i < binSummary.length; i++) {--%>
                                <%--if(binSummary[i].name === item.n) {--%>
                                    <%--binSummary[i].data.push(dieInforObj);--%>
                                <%--}--%>
                            <%--}--%>
                        <%--}--%>
                    <%--})--%>
                <%--}--%>
            <%--})--%>
            <%--var chart = Highcharts.chart('container', {--%>
                <%--chart: {--%>
                    <%--renderTo: 'container',--%>
                    <%--type: 'scatter',--%>
                    <%--plotBorderWidth: 1,--%>
                    <%--zoomType: 'xy'--%>
                <%--},--%>
                <%--scrollbar: {--%>
                    <%--enabled: true--%>
                <%--},--%>
                <%--boost: {--%>
                    <%--useGPUTranslations: true,--%>
                    <%--usePreAllocated: true--%>
                <%--},--%>
                <%--legend: {--%>
                    <%--layout: 'vertical',--%>
                    <%--align: 'left',--%>
                    <%--verticalAlign: 'top',--%>
                    <%--x: 70,--%>
                    <%--y: 70,--%>
                    <%--floating: true,--%>
                    <%--backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',--%>
                    <%--borderWidth: 1--%>
                <%--},--%>
                <%--title: {--%>
                    <%--text: 'V-Test Mapping'--%>
                <%--},--%>
                <%--subtitle: {--%>
                    <%--text: 'Map'--%>
                <%--},--%>
                <%--xAxis: {--%>
                    <%--title: {--%>
                        <%--enabled: true,--%>
                        <%--text: 'x',--%>
                    <%--},--%>
                    <%--startOnTick: true,--%>
                    <%--endOnTick: true,--%>
                    <%--showLastLabel: true--%>
                <%--},--%>
                <%--yAxis: {--%>
                    <%--title: {--%>
                        <%--text: 'y'--%>
                    <%--}--%>
                <%--},--%>
                <%--tooltip: {--%>
                    <%--useHTML: true,--%>
                    <%--headerFormat: '<table>',--%>
                    <%--pointFormat: '<tr><th colspan="2"><h3>{point.name}</h3></th></tr>' +--%>
                    <%--'<tr><th>X:</th><td>{point.x}</td></tr>' +--%>
                    <%--'<tr><th>Y:</th><td>{point.y}</td></tr>' +--%>
                    <%--'<tr><th>HardBin:</th><td>{point.hardbin}</td></tr>' +--%>
                    <%--'<tr><th>SoftBin:</th><td>{point.softbin}</td></tr>' +--%>
                    <%--'<tr><th>Site:</th><td>{point.site}</td></tr>',--%>
                    <%--footerFormat: '</table>',--%>
                    <%--followPointer: true--%>
                <%--},--%>
                <%--series: binSummary--%>
            <%--});--%>
		<%--</script>--%>
	<script src="<c:url value="/resources/system/js/mapping.js"/>" type="text/javascript" charset="utf-8"></script>
	</body>
</html>