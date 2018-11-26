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
		<div class="panel panel-default" style="width: 100%;height: 100%">
			<div class="panel-heading">
				Mapping Chart
				<div style="float: right; display: none" id="prfButtons">
					<button class="button button-action button-primary button-pill button-tiny" id="primaryChange">初测</button>
					<button class="button button-action button-primary button-pill button-tiny" id="retestChange">复测</button>
					<button class="button button-action button-primary button-pill button-tiny" id="finalChange">终测</button>
				</div>
			</div>
			<div class="panel-body">
				<div id="primaryTest">
					<div id="container" style="width:100%;height:750px"></div>
				</div>

			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/system/js/waferMap.js"/>" type="text/javascript" charset="utf-8"></script>
	</body>
</html>