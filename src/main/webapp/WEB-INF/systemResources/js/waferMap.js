$(document).ready(function () {
    var compare = function (prop) {
        return function (obj1, obj2) {
            var val1 = obj1[prop];
            var val2 = obj2[prop];if (val1 < val2) {
                return -1;
            } else if (val1 > val2) {
                return 1;
            } else {
                return 0;
            }
        }
    };
	var url =window.location.search;
    var primaryBinSummary = new Array();
    var retestBinSummary= new Array();
    var finalBinSummary= new Array();
    var binSet = new Set();
    var title="";
    $.ajax({
        type:"get",
        url:"/DataParseSystem/GetWaferMap/Mapping"+url,
        async:false,
        dataType: 'json',
        success:function(data){
            var  transformData=data.sort(compare("s"));
            $.each(transformData, function(i,item) {
                    var dieInforObj={
                        x:item.x,
                        y:item.y,
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
                        finalBinSummary.push(binInfo);
                    } else {
                        for(var i = 0; i < finalBinSummary.length; i++) {
                            if(finalBinSummary[i].name === item.n) {
                                finalBinSummary[i].data.push(dieInforObj);
                            }
                        }
                    }

            });
        }
    });
	if(url.indexOf("?")!=-1){
		var str=url.substr(1);
		var urlArray=str.split("&");
		for(var i=0;i<urlArray.length;i++){
			var index=urlArray[i].indexOf("=");
			var lastUrl=urlArray[i].substring(index+1,urlArray[i].length);
			var firstUrl=urlArray[i].substring(0,index);
            title=title+firstUrl+":"+lastUrl+",";
		}
	}
   var flag=false;
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getWaferInfor/checkDataSourceType"+url,
        async: false,
        dataType: 'json',
        success: function (data) {
           if(JSON.stringify(data)=="true"){
               flag=true;
           }
        }
    })
    if(!flag){
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
                text: title.substring(0,title.length-1)
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
                pointFormat:'<tr><th colspan="2"><h3>{point.name}</h3></th></tr>' +
                '<tr><th>X:</th><td>{point.x}</td></tr>' +
                '<tr><th>Y:</th><td>{point.y}</td></tr>' +
                '<tr><th>HardBin:</th><td>{point.hardbin}</td></tr>' +
                '<tr><th>SoftBin:</th><td>{point.softbin}</td></tr>' +
                '<tr><th>Site:</th><td>{point.site}</td></tr>',
                footerFormat: '</table>',
                followPointer: true
            },
            series: finalBinSummary
        });


    }
    else {
        $("#prfButtons").css("display","block");
        $.ajax({
            type:"get",
            url:"/DataParseSystem/GetWaferMap/Mapping"+url,
            async:false,
            dataType: 'json',
            success:function(data){
                var transformData=data.sort(compare("s"));
                $.each(transformData, function(i,item) {
                    var dieInforObj={
                        x:item.x,
                        y:item.y,
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
                        primaryBinSummary.push(binInfo);
                    } else {
                        for(var i = 0; i < primaryBinSummary.length; i++) {
                            if(primaryBinSummary[i].name === item.n) {
                                primaryBinSummary[i].data.push(dieInforObj);
                            }
                        }
                    }

                });
            }
        });
        $.ajax({
            type:"get",
            url:"/DataParseSystem/GetWaferMap/Mapping"+url,
            async:false,
            dataType: 'json',
            success:function(data){
                var  transformData=data.sort(compare("s"));
                $.each(transformData, function(i,item) {
                    var dieInforObj={
                        x:item.x,
                        y:item.y,
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
                        retestBinSummary.push(binInfo);
                    } else {
                        for(var i = 0; i < retestBinSummary.length; i++) {
                            if(retestBinSummary[i].name === item.n) {
                                retestBinSummary[i].data.push(dieInforObj);
                            }
                        }
                    }

                });
            }
        });
        $("#primaryChange").click(function(){
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
                    text: title.substring(0,title.length-1)
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
                series: primaryBinSummary
            });
        })
        $("#retestChange").click(function(){
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
                    text: title.substring(0,title.length-1)
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
                series: retestBinSummary
            });
        })
        $("#finalChange").click(function(){
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
                    text: title.substring(0,title.length-1)
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
                series: finalBinSummary
            });


        })
    }
})

