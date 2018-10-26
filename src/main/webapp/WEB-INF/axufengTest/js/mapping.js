			var data1 = [];
			var strSet = new Set();
			var binInfo=new Object();
			binInfo.animation=false;
			binInfo.turboThreshold=0;
			binInfo.data=[];
			binInfo.name='Bin1';
			binInfo.marker.symbol='square';
			$.ajax({
				url: './js/data.json',
				async: false,
				type: 'GET',
				dataType: 'json',
				success: function(data) {
					$.each(data, function(i, item) {
						var str = "{x:" + item.x + ",y:" + item.y + ",z:" + item.z + ",name:" + "'" + item.name + "'" + ",hardbin:" + item.hardbin + ",softbin:" + item.softbin + ",site:" + item.site + "}";
						var obj = eval('(' + str + ')');
						binInfo.data.push(obj);
					})
				}
			})
			alert(binInfo.data);
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
				plotOptions: {
					series: {
						dataLabels: {
							enabled: true,
							format: ''
						}
					}
				},
				series: []
			});
			chart.addSeries(binInfo);