$(document).ready(function () {
	var url =window.location.search;
	if(url.indexOf("?")!=-1){
		var str=url.substr(1);
		var urlArray=str.split("&");
		for(var i=0;i<urlArray.length;i++){
			var index=urlArray[i].indexOf("=");
			var lastUrl=urlArray[i].substring(index+1,urlArray[i].length);
			var firtUrl=urlArray[i].substring(0,index);
            $("#MappingTable").append("<tr><td>"+firtUrl+"</td><td>"+lastUrl+"</td></tr>");
		}
	}

})
var url=window.location.search;
$("#primaryChange").click(function(){
	var binSummary = new Array();
			var binSet = new Set();
	$.ajax({
		type:"get",
		url:"/DataParseSystem/GetWaferMap/Mapping"+url,
		async:false,
		success:function(data){
			$.each(data, function(i,item) {
				if(item.z==="Stdf"){
					 var dieInforObj={
                            x:item.x,
                            y:item.y,
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
				}
				
			});
		}
	});
				var chart = Highcharts.chart('container', {
				chart: {
					renderTo: 'container',
					type: 'scatter',
					plotBorderWidth: 1,
					zoomType: 'xy'
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
					text: 'Rawdata生成Mapping图'
				},
				subtitle: {
					text: '数据来源:/192.168.10.212/RawData'
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
})
$("#retestChange").click(function(){
	var binSummary = new Array();
	var binSet = new Set();
	$.ajax({
		type:"get",
		url:"./js/data3.json",
		async:false,
		success:function(data){
			$.each(data, function(i,item) {
				if(item.z==="Stdf"){
					 var dieInforObj={
                            x:item.x,
                            y:item.y,
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
				}
				
			});
		}
	});
				var chart = Highcharts.chart('container', {
				chart: {
					renderTo: 'container',
					type: 'scatter',
					plotBorderWidth: 1,
					zoomType: 'xy'
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
					text: 'Rawdata生成Mapping图'
				},
				subtitle: {
					text: '数据来源:/192.168.10.212/RawData'
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
})
$("#finalChange").click(function(){
	var binSummary = new Array();
			var binSet = new Set();
			$.ajax({
				url: './js/data2.json',
				async: false,
				type: 'GET',
				dataType: 'json',
	                success: function(data) {
                    $.each(data, function(i, item) {
                        var dieInforObj={
                            x:item.x,
                            y:item.y,
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
					text: 'Rawdata生成Mapping图'
				},
				subtitle: {
					text: '数据来源:/192.168.10.212/RawData'
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
	
	
})
