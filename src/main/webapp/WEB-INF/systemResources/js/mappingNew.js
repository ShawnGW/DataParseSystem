$(document).ready(function () {
    var url = window.location.search;
    var primaryBinSummary = new Array();
    var retestBinSummary = new Array();
    var finalBinSummary = new Array();
    var binSet = new Set();
    var title = "";
    $.ajax({
        type: "get",
        url: "/DataParseSystem/GetWaferMap/Mapping" + url,
        async: false,
        dataType: 'json',
        success: function (data) {
            $.each(data, function (i, item) {
                var dieInforObj = {
                    x: item.x,
                    y: item.y,
                    name: item.name.toString(),
                    hardbin: item.hardbin,
                    softbin: item.softbin,
                    site: item.site,
                };
                if (!binSet.has(item.name)) {
                    binSet.add(item.name);
                    var binInfo = new Object();
                    binInfo.animation = false;
                    binInfo.turboThreshold = 0;
                    binInfo.name = item.name;
                    var marker = new Object();
                    marker.symbol = 'square';
                    binInfo.marker = marker;
                    binInfo.data = new Array();
                    binInfo.data.push(dieInforObj);
                    finalBinSummary.push(binInfo);
                } else {
                    for (var i = 0; i < finalBinSummary.length; i++) {
                        if (finalBinSummary[i].name === item.name) {
                            finalBinSummary[i].data.push(dieInforObj);
                        }
                    }
                }

            });
        }
    });
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var urlArray = str.split("&");
        for (var i = 0; i < urlArray.length; i++) {
            var index = urlArray[i].indexOf("=");
            var lastUrl = urlArray[i].substring(index + 1, urlArray[i].length);
            var firstUrl = urlArray[i].substring(0, index);
            title = title + firstUrl + ":" + lastUrl + ",";
        }
    }
    var flag = false;
    var pieSeries = [];
    var columnSeries = [];
    var columnCategories = [];
//yield
    $.ajax({
        type: "get",
        url: "./js/Mapping2.json",
        async: false,
        dataType: 'json',
        success: function (data) {
            var totalDie = 0;
            var pieData = [];
            var columnData = [];
            var lineData = [];
            $.each(data, function (k, issue) {
                var bins = JSON.stringify(issue).split(",");
                for (var i = 0; i < bins.length; i++) {
                    var m = bins[i].indexOf(":");
                    totalDie = totalDie + parseInt(bins[i].substring(m + 1, bins[i].length));
                }
            })
            $.each(data, function (i, item) {
                var bins = JSON.stringify(item).split(",");
                for (var m = 0; m < bins.length; m++) {
                    var serie = new Object();
                    var columnSerie = new Object();
                    var k = bins[m].replace("{", "").replace("}", "").indexOf(":");
                    var bin = bins[m].replace("{", "").replace("}", "");
                    serie.name = "Bin" + bin.substring(1, k - 1);
                    columnCategories.push("Bin" + bin.substring(1, k - 1));
                    columnSerie.y = parseInt(bin.substring(k + 1, bin.length));
                    lineData.push(parseInt(bin.substring(k + 1, bin.length)));
                    var percentage = parseInt(bin.substring(k + 1, bin.length)) / totalDie;
                    serie.y = percentage * 100;
                    pieData.push(serie);
                    columnData.push(columnSerie);
                }
            })
            var lineSummary = new Object();
            lineSummary.name = "Line Chart";
            lineSummary.type = "spline";
            lineSummary.data = lineData;
            lineSummary.color = "#98FB98";
            var pieSummary = new Object();
            pieSummary.data = pieData;
            pieSummary.name = "Yield";
            pieSeries.push(pieSummary)
            var columnSummary = new Object();
            columnSummary.data = columnData;
            columnSummary.type = "column";
            columnSummary.colorByPoint = true;
            columnSummary.name = "Bin Line Chart";
            columnSeries.push(columnSummary);
            columnSeries.push(lineSummary);

        }
    });

    var siteSeries = [];
    var binSeries = [];
    //site
    $.ajax({
        type: "get",
        url: "./js/Mapping3.json",
        async: false,
        dataType: 'json',
        success: function (data) {
            var tableRow = "<tr><td>Site</td><td>TotalDie</td><td>PassDie</td><td>FailDie</td><td>TotalYield</td><td>Yield</td>";
            var tableColumns = "<tr>";
            var siteSummary = new Object();
            var siteData = [];
            var finalRow = "";
            $.each(data, function (i, item) {
                if (item.name == "total") {
                    tableColumns = tableColumns + "<td>total</td><td>" + item.total + "</td>" + "<td>" + item.pass + "</td>" +
                        "<td>" + item.fail + "</td>" + "<td>" + (parseFloat(item.totalYield) * 100).toFixed(2) + "%</td>" + "<td>" + (parseFloat(item.yield) * 100).toFixed(2) + "%</td>";
                    var totalMap = JSON.stringify(item.binMap).replace("{", "").replace("}", "");
                    var totalMaps = totalMap.split(",");
                    for (var t = 0; t < totalMaps.length; t++) {
                        var index = totalMaps[t].indexOf(":");
                        tableRow = tableRow + "<td>Bin" + totalMaps[t].substring(1, index - 1) + "</td>";
                        tableColumns = tableColumns + "<td>" + totalMaps[t].substring(index + 1, totalMaps[t].length) + "</td>";
                    }
                    tableRow = tableRow + "</tr>";
                    tableColumns = tableColumns + "</tr>";
                    finalRow = tableRow + tableColumns;
                } else {
                    var binSummary = new Object();
                    binSummary.data = [];
                    var binData = binSummary.data;
                    var serie = new Object();
                    serie.name = item.name;
                    serie.y = item.total;
                    serie.drilldown = item.name;
                    binSummary.id = item.name;
                    binSummary.name = item.name;
                    binSummary.colorByPoint = true;
                    var secondRow = "<tr><td>" + item.name + "</td>" + "<td>" + item.total + "</td>" + "<td>" + item.pass + "</td>" +
                        "<td>" + item.fail + "</td>" + "<td>" + (parseFloat(item.totalYield) * 100).toFixed(2) + "%</td>" + "<td>" + (parseFloat(item.yield) * 100).toFixed(2) + "%</td>";
                    siteData.push(serie);
                    var binMap = JSON.stringify(item.binMap).replace("{", "").replace("}", "");
                    var binMaps = binMap.split(",");
                    for (var k = 0; k < binMaps.length; k++) {
                        var m = binMaps[k].indexOf(":");
                        if (parseInt(binMaps[k].substring(m + 1, binMaps[k].length)) > 0) {
                            var drilldownSerie = new Object();
                            drilldownSerie.name = "Bin" + binMaps[k].substring(1, m - 1);
                            drilldownSerie.y = parseInt(binMaps[k].substring(m + 1, binMaps[k].length));
                        }
                        binData.push(drilldownSerie);
                        secondRow = secondRow + "<td>" + binMaps[k].substring(m + 1, binMaps[k].length) + "</td>";
                    }
                    secondRow = secondRow + "<tr>";
                    finalRow = finalRow + secondRow;
                    binSeries.push(binSummary);
                }

            })
            $("#siteTable").append(finalRow);
            siteSummary.name = "site";
            siteSummary.colorByPoint = true;
            siteSummary.data = siteData;
            siteSeries.push(siteSummary);
        }
    })
//judge
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getWaferInfor/checkDataSourceType" + url,
        async: false,
        dataType: 'json',
        success: function (data) {
            if (JSON.stringify(data) == "true") {
                flag = true;
            }
        }
    })
    if (!flag) {
        var primaryButton = document.getElementById("primaryChange");
        var retestButton = document.getElementById("retestChange");
        var finalButton = document.getElementById("finalChange");
        primaryButton.style.display = "none";
        retestButton.style.display = "none";
        finalButton.style.display = "none";
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
                text: title.substring(0, title.length - 1)
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
        var chart2 = Highcharts.chart('totalContainer', {
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
        var chart3 = new Highcharts.Chart({
            chart: {
                zoomType: 'xy',
                renderTo: 'lineContainer',
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
        });
        var chart4 = new Highcharts.Chart({
            chart: {
                zoomType: 'xy',
                type: 'column',
                renderTo: 'siteContainer',
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
            drilldown: {
                series: binSeries
            }
        });
        var alphaValue = document.getElementById('alpha-value'),
            betaValue = document.getElementById('beta-value'),
            depthValue = document.getElementById('depth-value');

        function showValues() {
            alphaValue.innerHTML = chart.options.chart.options3d.alpha;
            betaValue.innerHTML = chart.options.chart.options3d.beta;
            depthValue.innerHTML = chart.options.chart.options3d.depth;
        }

        $('#sliders input').on('input change', function () {
            chart3.options.chart.options3d[this.id] = this.value;
            showValues();
            chart3.redraw(false);
        });
        showValues();
    }
    else {
        $.ajax({
            type: "get",
            url: "/DataParseSystem/GetWaferMap/Mapping" + url,
            async: false,
            dataType: 'json',
            success: function (data) {
                $.each(data, function (i, item) {
                    var dieInforObj = {
                        x: item.x,
                        y: item.y,
                        name: item.name.toString(),
                        hardbin: item.hardbin,
                        softbin: item.softbin,
                        site: item.site,
                    };
                    if (!binSet.has(item.name)) {
                        binSet.add(item.name);
                        var binInfo = new Object();
                        binInfo.animation = false;
                        binInfo.turboThreshold = 0;
                        binInfo.name = item.name;
                        var marker = new Object();
                        marker.symbol = 'square';
                        binInfo.marker = marker;
                        binInfo.data = new Array();
                        binInfo.data.push(dieInforObj);
                        primaryBinSummary.push(binInfo);
                    } else {
                        for (var i = 0; i < primaryBinSummary.length; i++) {
                            if (primaryBinSummary[i].name === item.name) {
                                primaryBinSummary[i].data.push(dieInforObj);
                            }
                        }
                    }
                });
            }
        });
        $.ajax({
            type: "get",
            url: "/DataParseSystem/GetWaferMap/Mapping" + url,
            async: false,
            dataType: 'json',
            success: function (data) {
                $.each(data, function (i, item) {
                    var dieInforObj = {
                        x: item.x,
                        y: item.y,
                        name: item.name.toString(),
                        hardbin: item.hardbin,
                        softbin: item.softbin,
                        site: item.site,
                    };
                    if (!binSet.has(item.name)) {
                        binSet.add(item.name);
                        var binInfo = new Object();
                        binInfo.animation = false;
                        binInfo.turboThreshold = 0;
                        binInfo.name = item.name;
                        var marker = new Object();
                        marker.symbol = 'square';
                        binInfo.marker = marker;
                        binInfo.data = new Array();
                        binInfo.data.push(dieInforObj);
                        retestBinSummary.push(binInfo);
                    } else {
                        for (var i = 0; i < retestBinSummary.length; i++) {
                            if (retestBinSummary[i].name === item.name) {
                                retestBinSummary[i].data.push(dieInforObj);
                            }
                        }
                    }
                });
            }
        });
        $("#primaryChange").click(function () {
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
                    text: title.substring(0, title.length - 1)
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
        $("#retestChange").click(function () {
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
                    text: title.substring(0, title.length - 1)
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
        $("#finalChange").click(function () {
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
                    text: title.substring(0, title.length - 1)
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

