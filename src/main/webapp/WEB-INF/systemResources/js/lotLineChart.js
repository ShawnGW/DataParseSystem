var waferLimitValues=[];
var lotLimmitValus=[];
var customerToDevice = [];
var lotToCP = [];
$(document).ready(function() {
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getWaferInfor/getCustomerAndDevices",
        async: false,
        dataType: 'json',
        success: function(data) {
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
    appendOptTo($selCC, "*", "0")
    $.each(customerToDevice, function(i, item) {
        appendOptTo($selCC,item.customer,i+1)

    });

    $selCC.change(function() {
        $selDe.html("");
        $selLI.html("");
        $selCS.html("");
        appendOptTo($selDe, "*", "0")
        if(this.selectedIndex == -1) return;
        var customer = this.options[this.selectedIndex].text;
        $.each(customerToDevice, function(i, item) {
            if(customer === item.customer) {
                $.each(item.device,function (x,issue) {
                    appendOptTo($selDe, issue,i+1)
                })

            }
        });
        $selDe.change();
    }).change();
    $selDe.change(function() {
        $selLI.html("");
        $selCS.html("");
        appendOptTo($selLI, "*", "0");
        if(this.selectedIndex == -1) return;
        var customers=document.getElementById("customer_code");
        var customerTxt=customers.options[customers.selectedIndex].text;
        var device = this.options[this.selectedIndex].text;
        $.ajax({
            type: "get",
            url: "/DataParseSystem/getWaferInfor/getLotAndCps?customer="+customerTxt+"&device="+device,
            async: false,
            dataType: 'json',
            success: function (data) {
                lotToCP = data;
            }
        });
        $.each(lotToCP, function(i, item) {
                   appendOptTo($selLI, item.lot,i+1)
        });
        $selLI.change();
    }).change();
    $selLI.change(function() {
        $selCS.html("");
        appendOptTo($selCS, "*", "0");
        if (this.selectedIndex == -1) return;
        var lot = this.options[this.selectedIndex].text;
        $.each(lotToCP, function (i, item) {
            if(lot==item.lot){
                $.each(item.cp,function (t,issue) {
                    appendOptTo($selCS, issue, i + 1)
                })
            }
        });
    }).change();
})
$("#drawMap").click(function() {
    var customers=document.getElementById("customer_code");
    var devices=document.getElementById("device");
    var lots=document.getElementById("lot_id");
    var cps=document.getElementById("cp_step");
    var customer=customers.options[customers.selectedIndex].text;
    var device=devices.options[devices.selectedIndex].text;
    var lot=lots.options[lots.selectedIndex].text;
    var cp=cps.options[cps.selectedIndex].text;
    var series = [];
    var categories=[];
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getWaferInfor/getYields?customer="+customer+"&device="+device+"&lot="+lot+"&cp="+cp,
        async: false,
        dataType: 'json',
        success: function(data) {
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
                    var values = new Object();

                    $.each(item.values, function (index, value) {
                        values.y = parseFloat(value);
                        values.color = '#A2CD5A';
                        if (waferLimitValues[index]>0&&values.y < waferLimitValues[index]) {
                            values.color = '#CD4F39';
                        }
                        else if (values.y < lotLimmitValus[index]) {
                            values.color = '#EEAD0E';
                        }
                        percentage.data.push(values);
                        values = new Object();
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
                        tooltip.valueSuffix = ' %';
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
    var chart = Highcharts.chart('container', {
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: 'PassBinPercentAndFailBinPercent'
        },
        subtitle: {
            text: '数据来源: 192.168.10.212/RawData'
        },
        xAxis: [{
            categories: categories,
            crosshair: true
        }],
        yAxis: [{
            tickAmount:8,
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
        },{
            tickAmount:8,
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
            shared: true
        },
        series:series
    });
})
