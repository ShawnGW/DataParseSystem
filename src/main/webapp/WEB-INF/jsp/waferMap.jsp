<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>V-Test Map</title>
		<link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">
		<link rel="stylesheet" href="<c:url value="/resources/system/css/button.css"/>" />
		<script src="<c:url value="/resources/system/js/jquery.min.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/highcharts.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/exporting.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/highcharts-zh_CN.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/highcharts-more.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/boost.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/boost-canvas.js"/>" type="text/javascript" charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/bootstrap.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/resources/system/js/drilldown.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/resources/system/js/highcharts-3d.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/resources/system/js/xlsx.core.min.js"/>" type="text/javascript"
				charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/xlsx.full.min.js"/>" type="text/javascript"
				charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/FileSaver.min.js"/>" type="text/javascript"
				charset="utf-8"></script>
		<script src="<c:url value="/resources/system/js/tableExport.js"/>" type="text/javascript"
				charset="utf-8"></script>
	</head>
	<body>
	<div id="ChartsPage">
		<div class="panel panel-default" style="width: 100%;height: 100%">
			<div class="panel-heading">
				Mapping Chart
				<div style="float: right">
					<button class="button button-action button-primary button-pill button-tiny" id="primaryChange">初测</button>
					<button class="button button-action button-primary button-pill button-tiny" id="retestChange">复测</button>
					<button class="button button-action button-primary button-pill button-tiny" id="finalChange">终测</button>
					<button class="button button-action button-primary button-pill button-tiny" id="export">导出</button>
				</div>
			</div>
			<div class="panel-body">
				<div class="col-lg-7">
					<div id="primaryTest">
						<div id="container" style="width:100%;height:600px"></div>
					</div>
				</div>
				<div class="col-lg-5">
					<div id="lineContainer"></div>
					<div id="sliders">
						<table>
							<tr>
								<td>α 角（内旋转角）</td>
								<td><input id="alpha" type="range" min="0" max="45" value="15"/> <span id="alpha-value" class="value"></span></td>
							</tr>
							<tr>
								<td>β 角（外旋转角）</td>
								<td><input id="beta" type="range" min="-45" max="45" value="15"/> <span id="beta-value" class="value"></span></td>
							</tr>
							<tr>
								<td>深度</td>
								<td><input id="depth" type="range" min="20" max="100" value="50"/> <span id="depth-value" class="value"></span></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<div id="totalContainer"></div>
			</div>
			<div class="panel-body" style="width: 100%">
				<div id="siteContainer"></div>
			</div>
			<div class="panel-body" style="width: 100%">
				<div id="binContainer"></div>
			</div>
			<div class="panel-body" id="basicColumn" style="width: 100%">
				<div id="basicContainer"></div>
			</div>
			<div class="panel-body" id="siteTableContainer" style="width: 100%">
			</div>
		</div>
	</div>
	<%--<script src="<c:url value="/resources/system/js/waferMap.js"/>" type="text/javascript" charset="utf-8"></script>--%>
	<script src="<c:url value="/resources/system/js/mapping.js"/>" type="text/javascript" charset="utf-8"></script>
	</body>
</html>