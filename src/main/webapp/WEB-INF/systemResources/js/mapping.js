$(document).ready(function () {
    var compare = function (prop) {
        return function (obj1, obj2) {
            var val1 = obj1[prop];
            var val2 = obj2[prop];
            var newVal1 = parseInt(val1.replace("Bin", ""));
            var newVal2 = parseInt(val2.replace("Bin", ""));
            if (newVal1 < newVal2) {
                return -1;
            } else if (newVal1 > newVal2) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    var color=["rgb(255,182,193)","rgb(255,192,203)","rgb(220,20,60)","rgb(255,240,245)","rgb(219,112,147)","rgb(255,105,180)","rgb(255,20,147)","rgb(199,21,133)","rgb(218,112,214)","rgb(216,191,216)","rgb(221,160,221)","rgb(238,130,238)","rgb(255,0,255)","rgb(255,0,255)","rgb(139,0,139)","rgb(128,0,128)","rgb(186,85,211)","rgb(148,0,211)","rgb(153,50,204)","rgb(75,0,130)","rgb(138,43,226)","rgb(147,112,219)","rgb(123,104,238)","rgb(106,90,205)","rgb(72,61,139)","rgb(230,230,250)","rgb(248,248,255)","rgb(0,0,255)","rgb(0,0,205)","rgb(25,25,112)","rgb(0,0,139)","rgb(0,0,128)","rgb(65,105,225)","rgb(100,149,237)","rgb(176,196,222)","rgb(119,136,153)","rgb(112,128,144)","rgb(30,144,255)","rgb(240,248,255)","rgb(70,130,180)","rgb(135,206,250)","rgb(135,206,235)","rgb(0,191,255)","rgb(173,216,230)","rgb(176,224,230)","rgb(95,158,160)","rgb(240,255,255)","rgb(225,255,255)","rgb(175,238,238)","rgb(0,255,255)","rgb(0,255,255)","rgb(0,206,209)","rgb(47,79,79)","rgb(0,139,139)","rgb(0,128,128)","rgb(72,209,204)","rgb(32,178,170)","rgb(64,224,208)","rgb(127,255,170)","rgb(0,250,154)","rgb(107,142,35)","rgb(250,250,210)","rgb(255,255,240)","rgb(255,255,224)","rgb(255,255,0)","rgb(128,128,0)","rgb(189,183,107)","rgb(255,250,205)","rgb(238,232,170)","rgb(240,230,140)","rgb(255,215,0)","rgb(255,248,220)","rgb(218,165,32)","rgb(255,250,240)","rgb(253,245,230)","rgb(245,222,179)","rgb(255,228,181)","rgb(255,165,0)","rgb(255,239,213)","rgb(255,235,205)","rgb(255,222,173)","rgb(250,235,215)","rgb(210,180,140)","rgb(222,184,135)","rgb(255,228,196)","rgb(255,140,0)","rgb(250,240,230)","rgb(205,133,63)","rgb(255,218,185)","rgb(244,164,96)","rgb(210,105,30)","rgb(139,69,19)","rgb(255,245,238)","rgb(160,82,45)","rgb(255,160,122)","rgb(255,127,80)","rgb(255,69,0)","rgb(233,150,122)","rgb(255,99,71)","rgb(255,228,225)"];
	var url =window.location.search;
    var primaryBinSummary= new Array();
    var retestBinSummary= new Array();
    var finalBinSummary= new Array();
    var columnChart;
    var title="";
    var waferId="";
    var cp="";
    var passBin="";
    if(url.indexOf("?")!=-1){
        var str=url.substr(1);
        var urlArray=str.split("&");
        for(var i=0;i<urlArray.length;i++){
            var index=urlArray[i].indexOf("=");
            var lastUrl=urlArray[i].substring(index+1,urlArray[i].length);
            var firstUrl=urlArray[i].substring(0,index);
            if(firstUrl=="wafer"){
                waferId=lastUrl;
            }
            if(firstUrl=="cp"){
                cp=lastUrl;
            }
            title=title+firstUrl+":"+lastUrl+",";
        }
    }
    $.ajax({
        type:"get",
        url:"/DataParseSystem/getPassBin/passBin?waferId="+waferId,
        async:false,
        success:function(data){
            passBin=data;
        }
    })
    var table='<table id="siteTable" class="table" style="width: 100%"></table>';
    $("#siteTableContainer").append(table);
    $("#export").click(function(){
        $("#siteTable").tableExport({
            type:"xlsx",
            escape:"false"});
    });

    function getDrilldownChart(siteSeries,binSeries){
        var drilldownChart=new Highcharts.Chart({
            chart: {
                zoomType: 'xy',
                type:'column',
                renderTo: 'binContainer',
                /* options3d: {
                     enabled: true,
                     beta: 15,
                     depth: 50,
                     viewDistance: 25
                 }*/
            },
            xAxis: {
                type: 'category'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y}'
                    }
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
            },
            series: siteSeries,
            drilldown:{
                series:binSeries
            }
        });

    }
    function getBasicColumnChart(columnCategories,columnSeries){
        var BasicColumnChart = Highcharts.chart('basicContainer',{
            chart: {
                zoomType: 'xy',
                type: 'column',
                options3d: {
                    enabled: true,
                    alpha: 15,
                    depth: 50,
                    viewDistance: 25
                }
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: columnCategories,
                crosshair: true
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y}</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    borderWidth: 0
                }
            },
            series: columnSeries
        });

    }


    function getMap(summary){
         var waferMapChart=new Highcharts.chart('container', {
            chart: {
                type: 'scatter',
                plotBorderWidth: 1,
                zoomType: 'xy'
            },
            // legend: {
            //     layout: 'vertical',
            //     align: 'left',
            //     verticalAlign: 'top',
            //     x: 70,
            //     y: 70,
            //     floating: true,
            //     backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
            //     borderWidth: 1
            // },
            title: {
                text: 'Wafer Map'
            },
            subtitle: {
                text: title.substring(0,title.length-1)
            },
            xAxis: {
                visible:false,
                title: {
                    enabled: true,
                    text: 'x',
                },
                startOnTick: true,
                endOnTick: true,
                showLastLabel: true
            },
            yAxis: {
                visible:false,
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
             plotOptions:{
                 scatter:{
                     marker:{
                         radius:2
                     }
                 }
             },
            series: summary
        });
    }
    function getPieChart(pieSeries){
       var pieChart=new Highcharts.chart('totalContainer', {
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            },
            title: {
                text: ''
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 40,
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: pieSeries
        });

    }
    function getColumnChart(columnSeries,columnCategories){
        columnChart=new Highcharts.Chart('lineContainer',{
            chart: {
                zoomType: 'xy',
                options3d: {
                    enabled: true,
                    beta: 15,
                    depth: 50,
                    viewDistance: 25
                }
            },
            xAxis: [{
                categories: columnCategories
            }],
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            plotOptions: {
                column: {
                    depth: 25
                },
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: columnSeries
        })
        var alphaValue = document.getElementById('alpha-value'),
            betaValue = document.getElementById('beta-value'),
            depthValue = document.getElementById('depth-value');
        function showValues() {
            alphaValue.innerHTML = columnChart.options.chart.options3d.alpha;
            betaValue.innerHTML = columnChart.options.chart.options3d.beta;
            depthValue.innerHTML = columnChart.options.chart.options3d.depth;
        }
        $('#sliders input').on('input change', function () {
            columnChart.options.chart.options3d[this.id] = this.value;
            showValues();
            columnChart.redraw(false);
        });
        showValues();
    }

    function getDrilldownColumnChart(siteSeries,binSeries){
      var drilldownColumn=new Highcharts.Chart({
            chart: {
                zoomType: 'xy',
                type:'column',
                renderTo: 'siteContainer',
                 options3d: {
                     enabled: true,
                     beta: -1,
                     depth: 50,
                     viewDistance: 25
                 }
            },
            xAxis: {
                type: 'category'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y}'
                    }
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
            },
            series: siteSeries,
            drilldown:{
                series:binSeries
            }
        });

    }
    //Final
    $.ajax({
        type:"get",
        url:"/DataParseSystem/GetWaferMap/Mapping"+url,
        async:false,
        dataType: 'json',
        success:function(data){
            var transformData = data.sort(compare("n"));
            var binSet = new Set();
            var j=0;
            $.each(transformData, function (i, item) {
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
                        var passBins=passBin.split(";");
                        for(var k=0;k<passBins.length;k++){
                            var index=passBins[k].indexOf(":");
                            if(passBins[k].substring(0,index)==cp){
                                var pass=(passBins[k].substring(index+1,passBins[k].length)).split(",");
                                for(var m=0;m<pass.length;m++){
                                    if("Bin"+pass[m]==item.n){
                                        binInfo.color="rgb(0,255,0)";
                                        break;
                                    }else {
                                        if(j>color.length-1){
                                            j=0;
                                        }
                                        binInfo.color=color[j];
                                        j++;

                                    }
                                }

                            }
                        }
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
	var finalPieSeries=[];
	var finalColumnSeries=[];
	var finalColumnCategories=[];
    $.ajax({
        type:"get",
        url:"/DataParseSystem/getWaferInfor/getWaferIdSummary"+url+"&dataType=total&type=F",
        async:false,
        dataType: 'json',
        success:function(data){
            var totalDie=0;
            var pieData=[];
            var columnData=[];
            var lineData=[];
            var dataNew=[];
            dataNew.push(data);
            $.each(dataNew,function (k,issue) {
                var bins=JSON.stringify(issue).split(",");
                for(var i=0;i<bins.length;i++){
                    var m=bins[i].indexOf(":");
                    totalDie=totalDie+parseInt(bins[i].substring(m+1,bins[i].length));
                }
            })
          $.each(dataNew,function (i,item) {
              var bins=JSON.stringify(item).split(",");
              for(var m=0;m<bins.length;m++){
                  var serie=new Object();
                  var columnSerie=new Object();
                  var k=bins[m].replace("{","").replace("}","").indexOf(":");
                  var bin=bins[m].replace("{","").replace("}","");
                  serie.name="Bin"+bin.substring(1,k-1);
                  finalColumnCategories.push("Bin"+bin.substring(1,k-1));
                  columnSerie.y=parseInt(bin.substring(k+1,bin.length));
                  lineData.push(parseInt(bin.substring(k+1,bin.length)));
                  var percentage=parseInt(bin.substring(k+1,bin.length))/totalDie;
                  serie.y=percentage*100;
                  pieData.push(serie);
                  columnData.push(columnSerie);
              }
          })
            var lineSummary=new Object();
            lineSummary.name="Line Chart";
            lineSummary.type="spline";
            lineSummary.data=lineData;
            lineSummary.color="#98FB98";
            var pieSummary=new Object();
            pieSummary.data=pieData;
            pieSummary.name="Yield";
            finalPieSeries.push(pieSummary)
            var columnSummary=new Object();
            columnSummary.data=columnData;
            columnSummary.type="column";
            columnSummary.colorByPoint=true;
            columnSummary.name="Bin Line Chart";
            finalColumnSeries.push(columnSummary);
            finalColumnSeries.push(lineSummary);

        }
    });
    var finalSiteSeries=[];
    var finalBinSeries=[];
    var finalBasicColumnSeries=[];
    var finalBasicColumnCategories=[];
    var finalBinColumnSeries=[];
    var finalSiteColumnSeries=[];
    var finalRow="";
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getWaferInfor/getWaferIdSummary"+url+"&dataType=site&type=F",
        async: false,
        dataType: 'json',
        success: function (data) {
            var tableRow="<tr><td>Site</td><td>TotalDie</td><td>PassDie</td><td>FailDie</td><td>TotalYield</td><td>Yield</td>";
            var tableColumns="<tr>";
            var siteSummary = new Object();
            var siteData=[];
            var number=0;
            $.each(data,function (i,item) {
                number=i;
            })
            $.each(data,function (i,item) {
                if(item.name=="total"){
                    tableColumns=tableColumns+"<td>total</td><td>"+item.total+"</td>"+"<td>"+item.pass+"</td>"+
                        "<td>"+item.fail+"</td>"+"<td>"+(parseFloat(item.totalYield)*100).toFixed(2)+"%</td>"+"<td>"+(parseFloat(item.yield)*100).toFixed(2)+"%</td>";
                    var totalMap=JSON.stringify(item.binMap).replace("{","").replace("}","");
                    var totalMaps=totalMap.split(",");
                    var binColumnSummary=new Object();
                    binColumnSummary.colorByPoint=true;
                    binColumnSummary.name="Bin";
                    binColumnSummary.data=[];
                    var binColumnData=binColumnSummary.data;
                    for(var t=0;t<totalMaps.length;t++){
                        var index=totalMaps[t].indexOf(":");
                        var binColumnSerie=new Object();
                        var siteColumnSummary=new Object();
                        siteColumnSummary.id="Bin"+totalMaps[t].substring(1,index-1);
                        siteColumnSummary.name="Bin"+totalMaps[t].substring(1,index-1);
                        siteColumnSummary.data=[];
                        binColumnSerie.name="Bin"+totalMaps[t].substring(1,index-1);
                        binColumnSerie.y=parseInt(totalMaps[t].substring(index+1,totalMaps[t].length));
                        binColumnSerie.drilldown="Bin"+totalMaps[t].substring(1,index-1);
                        tableRow=tableRow+"<td>Bin"+totalMaps[t].substring(1,index-1)+"</td>";
                        finalBasicColumnCategories.push("Bin"+totalMaps[t].substring(1,index-1))
                        tableColumns=tableColumns+"<td>"+totalMaps[t].substring(index+1,totalMaps[t].length)+"</td>";
                        binColumnData.push(binColumnSerie);
                        finalSiteColumnSeries.push(siteColumnSummary);

                    }
                    finalBinColumnSeries.push(binColumnSummary)
                    tableRow=tableRow+"</tr>";
                    tableColumns=tableColumns+"</tr>";
                    finalRow=tableRow+tableColumns;

                }else {
                    var basicBinSummary=new Object();
                    basicBinSummary.name=item.name;
                    basicBinSummary.data=[];
                    var basicBinData=basicBinSummary.data;
                    var binSummary=new Object();
                    binSummary.data=[];
                    var binData=binSummary.data;
                    var serie=new Object();
                    serie.name=item.name;
                    serie.y=item.total;
                    serie.drilldown=item.name;
                    binSummary.id=item.name;
                    binSummary.name=item.name;
                    binSummary.colorByPoint=true;
                    var secondRow="<tr><td>"+item.name+"</td>"+"<td>"+item.total+"</td>"+"<td>"+item.pass+"</td>"+
                        "<td>"+item.fail+"</td>"+"<td>"+(parseFloat(item.totalYield)*100).toFixed(2)+"%</td>"+"<td>"+(parseFloat(item.yield)*100).toFixed(2)+"%</td>";
                    siteData.push(serie);
                    var binMap=JSON.stringify(item.binMap).replace("{","").replace("}","");
                    var binMaps=binMap.split(",");
                    for(var k=0;k<binMaps.length;k++){
                        var m=binMaps[k].indexOf(":");
                        var siteColumnData=[];
                        siteColumnData.push(item.name);
                        siteColumnData.push(parseInt(binMaps[k].substring(m+1,binMaps[k].length)));
                        finalSiteColumnSeries[k].data.push(siteColumnData);
                        var drilldownSerie=new Object();
                        if(parseInt(binMaps[k].substring(m+1,binMaps[k].length))>0){
                            drilldownSerie.name="Bin"+binMaps[k].substring(1,m-1);
                            drilldownSerie.y=parseInt(binMaps[k].substring(m+1,binMaps[k].length));
                            binData.push(drilldownSerie);
                        }
                        basicBinData.push(parseInt(binMaps[k].substring(m+1,binMaps[k].length)))
                        secondRow=secondRow+"<td>"+binMaps[k].substring(m+1,binMaps[k].length)+"</td>";
                    }
                    secondRow=secondRow+"</tr>";
                    finalRow=finalRow+secondRow;
                    if(number<9){
                        finalBasicColumnSeries.push(basicBinSummary)
                    }else {
                        $("#basicColumn").css("display","none");
                    }
                    finalBinSeries.push(binSummary);
                }

            })
            $("#siteTable").append(finalRow);
            siteSummary.name="site";
            siteSummary.colorByPoint=true;
            siteSummary.data=siteData;
            finalSiteSeries.push(siteSummary);
        }
    });
    //Primary
    $.ajax({
        type:"get",
        url:"/DataParseSystem/GetWaferMap/PrimaryOrRetestMap"+url+"&type=P",
        async:false,
        dataType: 'json',
        success:function(data){
            var binSet = new Set();
            var transformData = data.sort(compare("n"));
            var j=0;
            $.each(transformData, function (i, item) {
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
                    var passBins=passBin.split(";");
                    for(var k=0;k<passBins.length;k++){
                        var index=passBins[k].indexOf(":");
                        if(passBins[k].substring(0,index)==cp){
                            var pass=(passBins[k].substring(index+1,passBins[k].length)).split(",");
                            for(var m=0;m<pass.length;m++){
                                if("Bin"+pass[m]==item.n){
                                    binInfo.color="rgb(0,255,0)";
                                    break;
                                }else {
                                    if(j>color.length-1){
                                        j=0;
                                    }
                                    binInfo.color=color[j];
                                    j++;

                                }
                            }

                        }
                    }
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
    var primaryPieSeries=[];
    var primaryColumnSeries=[];
    var primaryColumnCategories=[];
    $.ajax({
        type:"get",
        url:"/DataParseSystem/getWaferInfor/getWaferIdSummary"+url+"&dataType=total&type=P",
        async:false,
        dataType: 'json',
        success:function(data){
            var totalDie=0;
            var pieData=[];
            var columnData=[];
            var lineData=[];
            var dataNew=[];
            dataNew.push(data);
            $.each(dataNew,function (k,issue) {
                var bins=JSON.stringify(issue).split(",");
                for(var i=0;i<bins.length;i++){
                    var m=bins[i].indexOf(":");
                    totalDie=totalDie+parseInt(bins[i].substring(m+1,bins[i].length));
                }
            })
            $.each(dataNew,function (i,item) {
                var bins=JSON.stringify(item).split(",");
                for(var m=0;m<bins.length;m++){
                    var serie=new Object();
                    var columnSerie=new Object();
                    var k=bins[m].replace("{","").replace("}","").indexOf(":");
                    var bin=bins[m].replace("{","").replace("}","");
                    serie.name="Bin"+bin.substring(1,k-1);
                    primaryColumnCategories.push("Bin"+bin.substring(1,k-1));
                    columnSerie.y=parseInt(bin.substring(k+1,bin.length));
                    lineData.push(parseInt(bin.substring(k+1,bin.length)));
                    var percentage=parseInt(bin.substring(k+1,bin.length))/totalDie;
                    serie.y=percentage*100;
                    pieData.push(serie);
                    columnData.push(columnSerie);
                }
            })
            var lineSummary=new Object();
            lineSummary.name="Line Chart";
            lineSummary.type="spline";
            lineSummary.data=lineData;
            lineSummary.color="#98FB98";
            var pieSummary=new Object();
            pieSummary.data=pieData;
            pieSummary.name="Yield";
            primaryPieSeries.push(pieSummary)
            var columnSummary=new Object();
            columnSummary.data=columnData;
            columnSummary.type="column";
            columnSummary.colorByPoint=true;
            columnSummary.name="Bin Line Chart";
            primaryColumnSeries.push(columnSummary);
            primaryColumnSeries.push(lineSummary);

        }
    });
    var primarySiteSeries=[];
    var primaryBinSeries=[];
    var primaryBasicColumnSeries=[];
    var primaryBasicColumnCategories=[];
    var primaryBinColumnSeries=[];
    var primarySiteColumnSeries=[];
    var primaryRow="";
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getWaferInfor/getWaferIdSummary"+url+"&dataType=site&type=P",
        async: false,
        dataType: 'json',
        success: function (data) {
            var tableRow="<tr><td>Site</td><td>TotalDie</td><td>PassDie</td><td>FailDie</td><td>TotalYield</td><td>Yield</td>";
            var tableColumns="<tr>";
            var siteSummary = new Object();
            var siteData=[];
            var number=0;
            $.each(data,function (i,item) {
                number=i;
            })
            $.each(data,function (i,item) {
                if(item.name=="total"){
                    tableColumns=tableColumns+"<td>total</td><td>"+item.total+"</td>"+"<td>"+item.pass+"</td>"+
                        "<td>"+item.fail+"</td>"+"<td>"+(parseFloat(item.totalYield)*100).toFixed(2)+"%</td>"+"<td>"+(parseFloat(item.yield)*100).toFixed(2)+"%</td>";
                    var totalMap=JSON.stringify(item.binMap).replace("{","").replace("}","");
                    var totalMaps=totalMap.split(",");
                    var binColumnSummary=new Object();
                    binColumnSummary.colorByPoint=true;
                    binColumnSummary.name="Bin";
                    binColumnSummary.data=[];
                    var binColumnData=binColumnSummary.data;
                    for(var t=0;t<totalMaps.length;t++){
                        var index=totalMaps[t].indexOf(":");
                        var binColumnSerie=new Object();
                        var siteColumnSummary=new Object();
                        siteColumnSummary.id="Bin"+totalMaps[t].substring(1,index-1);
                        siteColumnSummary.name="Bin"+totalMaps[t].substring(1,index-1);
                        siteColumnSummary.data=[];
                        binColumnSerie.name="Bin"+totalMaps[t].substring(1,index-1);
                        binColumnSerie.y=parseInt(totalMaps[t].substring(index+1,totalMaps[t].length));
                        binColumnSerie.drilldown="Bin"+totalMaps[t].substring(1,index-1);
                        tableRow=tableRow+"<td>Bin"+totalMaps[t].substring(1,index-1)+"</td>";
                        primaryBasicColumnCategories.push("Bin"+totalMaps[t].substring(1,index-1))
                        tableColumns=tableColumns+"<td>"+totalMaps[t].substring(index+1,totalMaps[t].length)+"</td>";
                        binColumnData.push(binColumnSerie);
                        primarySiteColumnSeries.push(siteColumnSummary);

                    }
                    primaryBinColumnSeries.push(binColumnSummary)
                    tableRow=tableRow+"</tr>";
                    tableColumns=tableColumns+"</tr>";
                    primaryRow=tableRow+tableColumns;

                }else {
                    var basicBinSummary=new Object();
                    basicBinSummary.name=item.name;
                    basicBinSummary.data=[];
                    var basicBinData=basicBinSummary.data;
                    var binSummary=new Object();
                    binSummary.data=[];
                    var binData=binSummary.data;
                    var serie=new Object();
                    serie.name=item.name;
                    serie.y=item.total;
                    serie.drilldown=item.name;
                    binSummary.id=item.name;
                    binSummary.name=item.name;
                    binSummary.colorByPoint=true;
                    var secondRow="<tr><td>"+item.name+"</td>"+"<td>"+item.total+"</td>"+"<td>"+item.pass+"</td>"+
                        "<td>"+item.fail+"</td>"+"<td>"+(parseFloat(item.totalYield)*100).toFixed(2)+"%</td>"+"<td>"+(parseFloat(item.yield)*100).toFixed(2)+"%</td>";
                    siteData.push(serie);
                    var binMap=JSON.stringify(item.binMap).replace("{","").replace("}","");
                    var binMaps=binMap.split(",");
                    for(var k=0;k<binMaps.length;k++){
                        var m=binMaps[k].indexOf(":");
                        var siteColumnData=[];
                        siteColumnData.push(item.name);
                        siteColumnData.push(parseInt(binMaps[k].substring(m+1,binMaps[k].length)));
                        primarySiteColumnSeries[k].data.push(siteColumnData);
                        var drilldownSerie=new Object();
                        if(parseInt(binMaps[k].substring(m+1,binMaps[k].length))>0){
                            drilldownSerie.name="Bin"+binMaps[k].substring(1,m-1);
                            drilldownSerie.y=parseInt(binMaps[k].substring(m+1,binMaps[k].length));
                            binData.push(drilldownSerie);
                        }
                        basicBinData.push(parseInt(binMaps[k].substring(m+1,binMaps[k].length)))
                        secondRow=secondRow+"<td>"+binMaps[k].substring(m+1,binMaps[k].length)+"</td>";
                    }
                    secondRow=secondRow+"</tr>";
                    primaryRow=primaryRow+secondRow;
                    if(number<9){
                        primaryBasicColumnSeries.push(basicBinSummary)
                    }else {
                        $("#basicColumn").css("display","none");
                    }
                    primaryBinSeries.push(binSummary);
                }

            })

            siteSummary.name="site";
            siteSummary.colorByPoint=true;
            siteSummary.data=siteData;
            primarySiteSeries.push(siteSummary);
        }
    });
    //retest
    $.ajax({
        type:"get",
        url:"/DataParseSystem/GetWaferMap/PrimaryOrRetestMap"+url+"&type=R",
        async:false,
        dataType: 'json',
        success:function(data){
            var binSet = new Set();
            var transformData = data.sort(compare("n"));
            var j=0;
            $.each(transformData, function (i, item) {
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
                    var passBins=passBin.split(";");
                    for(var k=0;k<passBins.length;k++){
                        var index=passBins[k].indexOf(":");
                        if(passBins[k].substring(0,index)==cp){
                            var pass=(passBins[k].substring(index+1,passBins[k].length)).split(",");
                            for(var m=0;m<pass.length;m++){
                                if("Bin"+pass[m]==item.n){
                                    binInfo.color="rgb(0,255,0)";
                                    break;
                                }else {
                                    if(j>color.length-1){
                                        j=0;
                                    }
                                    binInfo.color=color[j];
                                    j++;

                                }
                            }

                        }
                    }
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
    var retestPieSeries=[];
    var retestColumnSeries=[];
    var retestColumnCategories=[];
    $.ajax({
        type:"get",
        url:"/DataParseSystem/getWaferInfor/getWaferIdSummary"+url+"&dataType=total&type=R",
        async:false,
        dataType: 'json',
        success:function(data){
            var totalDie=0;
            var pieData=[];
            var columnData=[];
            var lineData=[];
            var dataNew=[];
            dataNew.push(data);
            $.each(dataNew,function (k,issue) {
                var bins=JSON.stringify(issue).split(",");
                for(var i=0;i<bins.length;i++){
                    var m=bins[i].indexOf(":");
                    totalDie=totalDie+parseInt(bins[i].substring(m+1,bins[i].length));
                }
            })
            $.each(dataNew,function (i,item) {
                var bins=JSON.stringify(item).split(",");
                for(var m=0;m<bins.length;m++){
                    var serie=new Object();
                    var columnSerie=new Object();
                    var k=bins[m].replace("{","").replace("}","").indexOf(":");
                    var bin=bins[m].replace("{","").replace("}","");
                    serie.name="Bin"+bin.substring(1,k-1);
                    retestColumnCategories.push("Bin"+bin.substring(1,k-1));
                    columnSerie.y=parseInt(bin.substring(k+1,bin.length));
                    lineData.push(parseInt(bin.substring(k+1,bin.length)));
                    var percentage=parseInt(bin.substring(k+1,bin.length))/totalDie;
                    serie.y=percentage*100;
                    pieData.push(serie);
                    columnData.push(columnSerie);
                }
            })
            var lineSummary=new Object();
            lineSummary.name="Line Chart";
            lineSummary.type="spline";
            lineSummary.data=lineData;
            lineSummary.color="#98FB98";
            var pieSummary=new Object();
            pieSummary.data=pieData;
            pieSummary.name="Yield";
            retestPieSeries.push(pieSummary)
            var columnSummary=new Object();
            columnSummary.data=columnData;
            columnSummary.type="column";
            columnSummary.colorByPoint=true;
            columnSummary.name="Bin Line Chart";
            retestColumnSeries.push(columnSummary);
            retestColumnSeries.push(lineSummary);

        }
    });
    var retestSiteSeries=[];
    var retestBinSeries=[];
    var retestBasicColumnSeries=[];
    var retestBasicColumnCategories=[];
    var retestBinColumnSeries=[];
    var retestSiteColumnSeries=[];
    var retestRow="";
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getWaferInfor/getWaferIdSummary"+url+"&dataType=site&type=R",
        async: false,
        dataType: 'json',
        success: function (data) {
            var tableRow="<tr><td>Site</td><td>TotalDie</td><td>PassDie</td><td>FailDie</td><td>TotalYield</td><td>Yield</td>";
            var tableColumns="<tr>";
            var siteSummary = new Object();
            var siteData=[];
            var number=0;
            $.each(data,function (i,item) {
                number=i;
            })
            $.each(data,function (i,item) {
                if(item.name=="total"){
                    tableColumns=tableColumns+"<td>total</td><td>"+item.total+"</td>"+"<td>"+item.pass+"</td>"+
                        "<td>"+item.fail+"</td>"+"<td>"+(parseFloat(item.totalYield)*100).toFixed(2)+"%</td>"+"<td>"+(parseFloat(item.yield)*100).toFixed(2)+"%</td>";
                    var totalMap=JSON.stringify(item.binMap).replace("{","").replace("}","");
                    var totalMaps=totalMap.split(",");
                    var binColumnSummary=new Object();
                    binColumnSummary.colorByPoint=true;
                    binColumnSummary.name="Bin";
                    binColumnSummary.data=[];
                    var binColumnData=binColumnSummary.data;
                    for(var t=0;t<totalMaps.length;t++){
                        var index=totalMaps[t].indexOf(":");
                        var binColumnSerie=new Object();
                        var siteColumnSummary=new Object();
                        siteColumnSummary.id="Bin"+totalMaps[t].substring(1,index-1);
                        siteColumnSummary.name="Bin"+totalMaps[t].substring(1,index-1);
                        siteColumnSummary.data=[];
                        binColumnSerie.name="Bin"+totalMaps[t].substring(1,index-1);
                        binColumnSerie.y=parseInt(totalMaps[t].substring(index+1,totalMaps[t].length));
                        binColumnSerie.drilldown="Bin"+totalMaps[t].substring(1,index-1);
                        tableRow=tableRow+"<td>Bin"+totalMaps[t].substring(1,index-1)+"</td>";
                        retestBasicColumnCategories.push("Bin"+totalMaps[t].substring(1,index-1))
                        tableColumns=tableColumns+"<td>"+totalMaps[t].substring(index+1,totalMaps[t].length)+"</td>";
                        binColumnData.push(binColumnSerie);
                        retestSiteColumnSeries.push(siteColumnSummary);

                    }
                    retestBinColumnSeries.push(binColumnSummary)
                    tableRow=tableRow+"</tr>";
                    tableColumns=tableColumns+"</tr>";
                    retestRow=tableRow+tableColumns;

                }else {
                    var basicBinSummary=new Object();
                    basicBinSummary.name=item.name;
                    basicBinSummary.data=[];
                    var basicBinData=basicBinSummary.data;
                    var binSummary=new Object();
                    binSummary.data=[];
                    var binData=binSummary.data;
                    var serie=new Object();
                    serie.name=item.name;
                    serie.y=item.total;
                    serie.drilldown=item.name;
                    binSummary.id=item.name;
                    binSummary.name=item.name;
                    binSummary.colorByPoint=true;
                    var secondRow="<tr><td>"+item.name+"</td>"+"<td>"+item.total+"</td>"+"<td>"+item.pass+"</td>"+
                        "<td>"+item.fail+"</td>"+"<td>"+(parseFloat(item.totalYield)*100).toFixed(2)+"%</td>"+"<td>"+(parseFloat(item.yield)*100).toFixed(2)+"%</td>";
                    siteData.push(serie);
                    var binMap=JSON.stringify(item.binMap).replace("{","").replace("}","");
                    var binMaps=binMap.split(",");
                    for(var k=0;k<binMaps.length;k++){
                        var m=binMaps[k].indexOf(":");
                        var siteColumnData=[];
                        siteColumnData.push(item.name);
                        siteColumnData.push(parseInt(binMaps[k].substring(m+1,binMaps[k].length)));
                        retestSiteColumnSeries[k].data.push(siteColumnData);
                        var drilldownSerie=new Object();
                        if(parseInt(binMaps[k].substring(m+1,binMaps[k].length))>0){
                            drilldownSerie.name="Bin"+binMaps[k].substring(1,m-1);
                            drilldownSerie.y=parseInt(binMaps[k].substring(m+1,binMaps[k].length));
                            binData.push(drilldownSerie);
                        }
                        basicBinData.push(parseInt(binMaps[k].substring(m+1,binMaps[k].length)))
                        secondRow=secondRow+"<td>"+binMaps[k].substring(m+1,binMaps[k].length)+"</td>";
                    }
                    secondRow=secondRow+"</tr>";
                    retestRow=retestRow+secondRow;
                    if(number<9){
                        retestBasicColumnSeries.push(basicBinSummary)
                    }else {
                        $("#basicColumn").css("display","none");
                    }
                    retestBinSeries.push(binSummary);
                }

            })
            siteSummary.name="site";
            siteSummary.colorByPoint=true;
            siteSummary.data=siteData;
            retestSiteSeries.push(siteSummary);
        }
    });
    getMap(finalBinSummary);
    getPieChart(finalPieSeries);
    getColumnChart(finalColumnSeries,finalColumnCategories);
    getBasicColumnChart(finalBasicColumnCategories,finalBasicColumnSeries);
    getDrilldownChart(finalBinColumnSeries,finalSiteColumnSeries);
    getDrilldownColumnChart(finalSiteSeries,finalBinSeries);
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
        var primaryButton=document.getElementById("primaryChange");
        var retestButton=document.getElementById("retestChange");
        var finalButton=document.getElementById("finalChange");
        primaryButton.style.display="none";
        retestButton.style.display="none";
        finalButton.style.display="none";
    }
    else {
        $("#primaryChange").click(function(){
            $("#siteTableContainer").html("");
            $("#siteTableContainer").append(table);
            $("#siteTable").append(primaryRow);
            getMap(primaryBinSummary);
            getPieChart(primaryPieSeries);
            getColumnChart(primaryColumnSeries,primaryColumnCategories);
            getBasicColumnChart(primaryBasicColumnCategories,primaryBasicColumnSeries);
            getDrilldownChart(primaryBinColumnSeries,primarySiteColumnSeries);
            getDrilldownColumnChart(primarySiteSeries,primaryBinSeries);
        })
        $("#retestChange").click(function(){
            $("#siteTableContainer").html("");
            $("#siteTableContainer").append(table);
            $("#siteTable").append(retestRow);
            getMap(retestBinSummary);
            getPieChart(retestPieSeries);
            getColumnChart(retestColumnSeries,retestColumnCategories);
            getBasicColumnChart(retestBasicColumnCategories,retestBasicColumnSeries);
            getDrilldownChart(retestBinColumnSeries,retestSiteColumnSeries);
            getDrilldownColumnChart(retestSiteSeries,retestBinSeries);
        })
        $("#finalChange").click(function(){
            $("#siteTableContainer").html("");
            $("#siteTableContainer").append(table);
            $("#siteTable").append(finalRow);
            getMap(finalBinSummary);
            getPieChart(finalPieSeries);
            getColumnChart(finalColumnSeries,finalColumnCategories);
            getBasicColumnChart(finalBasicColumnCategories,finalBasicColumnSeries);
            getDrilldownChart(finalBinColumnSeries,finalSiteColumnSeries);
            getDrilldownColumnChart(finalSiteSeries,finalBinSeries);
        })
    }
})

