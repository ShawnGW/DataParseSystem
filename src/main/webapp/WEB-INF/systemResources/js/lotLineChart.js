var customerToDevice = [];
var lotToCP = [];
$(document).ready(function () {
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getWaferInfor/getCustomerAndDevices",
        async: false,
        dataType: 'json',
        success: function (data) {
            customerToDevice = data;
        }
    });
    var $selCC = $('#customer_code');
    var $selDe = $('#device');
    var $selLI = $('#lot_id');
    var $selCS = $('#cp_step');

    function appendOptTo($o, k, v) {
        var $opt = $("<option>").text(k).val(v);
        $opt.appendTo($o);
    };
    $("#refresh").click(function () {
        $selCC.html("");
        $selDe.html("");
        $selLI.html("");
        $selCS.html("");
        appendOptTo($selCC, "*", "0");
        appendOptTo($selDe, "*", "0");
        appendOptTo($selLI, "*", "0");
        appendOptTo($selCS, "*", "0");
        $.ajax({
            type: "get",
            url: "/DataParseSystem/getWaferInfor/getCustomerAndDevices",
            async: false,
            dataType: 'json',
            success: function (data) {
                customerToDevice = data;
            }
        });
        $.each(customerToDevice, function (i, item) {
            appendOptTo($selCC, item.customer, i + 1)

        });
    });
    appendOptTo($selCC, "*", "0")
    $.each(customerToDevice, function (i, item) {
        appendOptTo($selCC, item.customer, i + 1)

    });

    $selCC.change(function () {
        $selDe.html("");
        $selLI.html("");
        $selCS.html("");
        appendOptTo($selDe, "*", "0")
        if (this.selectedIndex == -1) return;
        var customer = this.options[this.selectedIndex].text;
        $.each(customerToDevice, function (i, item) {
            if (customer === item.customer) {
                $.each(item.device, function (x, issue) {
                    appendOptTo($selDe, issue, i + 1)
                })

            }
        });
        $selDe.change();
    }).change();
    $selDe.change(function () {
        $selLI.html("");
        $selCS.html("");
        appendOptTo($selLI, "*", "0");
        if (this.selectedIndex == -1) return;
        var customers = document.getElementById("customer_code");
        var customerTxt = customers.options[customers.selectedIndex].text;
        var device = this.options[this.selectedIndex].text;
        $.ajax({
            type: "get",
            url: "/DataParseSystem/getWaferInfor/getLotAndCps?customer=" + customerTxt + "&device=" + device,
            async: false,
            dataType: 'json',
            success: function (data) {
                lotToCP = data;
            }
        });
        $.each(lotToCP, function (i, item) {
            appendOptTo($selLI, item.lot, i + 1)
        });
        $selLI.change();
    }).change();
    $selLI.change(function () {
        $selCS.html("");
        appendOptTo($selCS, "*", "0");
        if (this.selectedIndex == -1) return;
        var lot = this.options[this.selectedIndex].text;
        $.each(lotToCP, function (i, item) {
            if (lot == item.lot) {
                $.each(item.cp, function (t, issue) {
                    appendOptTo($selCS, issue, i + 1)
                })
            }
        });
    }).change();
    $("#drawMap").click(function () {
        $("#wTable").html("");
        var waferTable = document.getElementById("wTable");
        var tableCode = '<table  id="waferTable"></table>';
        waferTable.innerHTML = tableCode;
        var customers = document.getElementById("customer_code");
        var devices = document.getElementById("device");
        var lots = document.getElementById("lot_id");
        var cps = document.getElementById("cp_step");
        var customer = customers.options[customers.selectedIndex].text;
        var device = devices.options[devices.selectedIndex].text;
        var lot = lots.options[lots.selectedIndex].text;
        var cp = cps.options[cps.selectedIndex].text;
        var series = [];
        var categories = [];
        var waferData = [];
        var waferIdNumbers = 0;
        var tableColumns = [{
            field: 'waferNo',
            title: 'Wafer'
        }, {
            field: 'grossDie',
            title: 'Total'
        }, {
            field: 'passDie',
            title: 'Pass'
        }, {
            field: 'failDie',
            title: 'Fail'
        }, {
            field: 'yield',
            title: 'Yield'
        }];
        $.ajax({
            type: "get",
            url: "/DataParseSystem/getWaferInfor/getBinSummary?customer=" + customer + "&device=" + device + "&lot=" + lot + "&cp=" + cp,
            async: false,
            dataType: 'json',
            success: function (data) {
                $.each(data, function (i, item) {
                    waferIdNumbers++;
                    var column = new Object();
                    if (item.waferNo == "bin_define") {
                        for (var k = 0; k < item.binSummary.length; k++) {
                            column.field = "" + k + "";
                            column.title = "Bin" + item.binSummary[k];
                            tableColumns.push(column);
                            column = new Object();
                        }
                    } else {
                        waferData.push(item);
                        for (var k = 0; k < item.binSummary.length; k++) {
                            item[k] = item.binSummary[k];
                        }
                        item.yield = item.yield + "%";
                    }

                })
            }
        });
        $("#wTable").css("height", waferIdNumbers * 38 + "px");
        $('#waferTable').bootstrapTable({
            data: waferData,
            toolbar: '.scroll',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 25,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            // fixedColumns: true,
            // fixedNumber:1,
            columns: tableColumns,
            rowStyle: function (row, index) {
                var style = "";
                if (index == 0 || index % 2 == 0) {
                    style = "info";
                }
                if (index == 1 || index % 2 != 0) {
                    style = "success";
                }
                return {classes: style}
            }
        });
        $("#waferTable").css("font-size", "1px");
        $.ajax({
            type: "get",
            url: "/DataParseSystem/getWaferInfor/getYields?customer=" + customer + "&device=" + device + "&lot=" + lot + "&cp=" + cp,
            async: false,
            dataType: 'json',
            success: function (data) {
                var waferLimitValues = [];
                var lotLimmitValus = [];
                $.each(data, function (i, item) {
                    if (item.name == "wafer yield limit") {
                        $.each(item.values, function (index, value) {
                            waferLimitValues.push(parseFloat(value));
                        });
                    }
                    if (item.name == "lot yield limit") {
                        $.each(item.values, function (index, value) {
                            lotLimmitValus.push(parseFloat(value));
                        });
                    }
                })
                $.each(data, function (i, item) {
                    if (item.name == "yield") {
                        var percentage = new Object();
                        percentage.name = 'PassBinPercent';
                        percentage.type = 'column';
                        percentage.yAxis = 0;
                        percentage.data = new Array();
                        $.each(item.values, function (index, value) {
                            var values = new Object();
                            values.y = parseFloat(value);
                            if (values.y < waferLimitValues[index]) {
                                values.color = '#EEAD0E';
                            }
                            if (values.y < lotLimmitValus[index]) {
                                values.color = '#CD4F39';
                            }
                            if (values.y > lotLimmitValus[index] && values.y > waferLimitValues[index]) {
                                values.color = '#A2CD5A';
                            }
                            percentage.data.push(values);
                        });
                        var tooltip = new Object();
                        tooltip.valueSuffix = ' %';
                        percentage.tooltip = tooltip;
                        series.push(percentage);
                    }
                    else {
                        if (item.name != "wafer") {
                            var percentage = new Object();
                            percentage.name = item.name;
                            percentage.type = 'spline';
                            percentage.yAxis = 1;
                            percentage.data = new Array();
                            var tooltip = new Object();
                            // tooltip.valueSuffix = ' %';
                            percentage.tooltip = tooltip;
                            var values = [];
                            $.each(item.values, function (index, value) {
                                values.push(parseFloat(value));
                            });
                            percentage.data = values;
                            series.push(percentage);
                        }
                    }
                    if (item.name == "wafer") {
                        categories = item.values;
                    }
                });
            }
        });
        var chart = new Highcharts.chart('container', {
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: 'PassBinPercentAndFailBinPercent'
            },
            subtitle: {
                text: 'v-test lot line'
            },
            xAxis: [{
                categories: categories,
                crosshair: true
            }],
            yAxis: [{
                tickAmount: 8,
                labels: {
                    format: '{value}%',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'PassBinPercent',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }, {
                tickAmount: 8,
                title: {
                    text: 'FailBinPercent',
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                labels: {
                    format: '{value}%',
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                opposite: true
            }],
            tooltip: {
                formatter: function () {
                    var binStr = this.x;
                    $.each(this.points, function (i, item) {
                        if (this.y > 0) {
                            binStr += '</br><span style="color:' + item.series.color + '">' + '<span class="fa fa-circle"></span></span>' + this.series.name + ':<span style="font-weight: bold">' + this.y + "%</span>";
                        }
                    });
                    return binStr;
                },
                shared: true,
                useHTML: true
            },
            series: series
        });
    });
})

