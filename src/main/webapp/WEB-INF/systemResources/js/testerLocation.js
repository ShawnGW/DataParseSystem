Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
function getSmpFormatDate(date, isFull) {
    var pattern = "";
    if (isFull == true || isFull == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    } else {
        pattern = "yyyy-MM-dd";
    }
    return getFormatDate(date, pattern);
}
function getSmpFormatNowDate(isFull) {
    return getSmpFormatDate(new Date(), isFull);
}
function getSmpFormatDateByLong(l, isFull) {
    return getSmpFormatDate(new Date(l), isFull);
}
function getFormatDateByLong(l, pattern) {
    return getFormatDate(new Date(l), pattern);
}
function getFormatDate(date, pattern) {
    if (date == undefined) {
        date = new Date();
    }
    if (pattern == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    }
    return date.format(pattern);
}
function resizeEle(ele, isResetH, IsResetW) {
    var windowWidth =$(window).width();
    var windowHeight = $(window).height();
    if (isResetH !== null)
        $(ele).css("height", (windowHeight - isResetH) + "px").addClass('overflowY');
    if (IsResetW !== null)
        $(ele).css("width", (windowWidth - IsResetW) + "px");
}
function getData(){
    var seriesData=[];
    $.ajax({
        type: "get",
        url: "/DataParseSystem/getTesterLocation/location",
        async: false,
        dataType: 'json',
        success:function (data) {
            $.each(data,function (i,item) {
                    seriesData=data;
            })
        }
    })
    $.ajax({
        type: "get",
        url: "/DataParseSystem/GetTesterStatus/status",
        async: false,
        dataType: 'json',
        success:function (data) {
            $.each(data,function (i,item) {
                $.each(seriesData,function (k,issue) {
                        if(item.testerId==issue.name) {
                            issue.checkResult = item.checkResult;
                            issue.endTime = item.endTime;
                        }
                })
            })
        }
    })
    return seriesData;
}
var chart=null;
$(document).ready(function () {
    resizeEle($("#containerA2"),80,null);
    resizeEle($("#containerD1"),80,null);
    $(window).resize(function () {
        resizeEle($("#containerA2"),80,null);
        resizeEle($("#containerD1"),80,null);
    })
})
function setTesterData(value) {
    if(value)
    {
        var testerId=value.split(";")[0];
        var waferNo=value.split(";")[1];
        var pointIndex=value.split(";")[2];
        var seriesIndex=value.split(";")[3];
        var seriesName=value.split(";")[4];
        chart=$('#'+seriesName).highcharts();
        chart.series[seriesIndex].data[pointIndex].update({
            color:"rgba(0,128,0, .5)",
            checkResult:"normal"
        })
        $.ajax({
            type:'post',
            url:'/DataParseSystem/getWaferInfor/updateTesterCheckResult?tester='+testerId+"&waferId="+waferNo,
            success:function () {
                alert("Release True")
            }
        })
    }

}
function getCharts(name,seriesData) {
    var series=[];
    var  passData=[];
    var failData=[];
    var idleData=[];
    var seriesObjectP=new Object();
    var seriesObjectF=new Object();
    var seriesObjectI=new Object();
    seriesObjectP.name="Pass";
    seriesObjectP.color="rgba(0,128,0, .5)";
    seriesObjectF.name="Fail";
    seriesObjectF.color="rgba(255,0,0, .5)";
    seriesObjectI.name="Idle"
    seriesObjectI.color="rgba(128,128,128, .5)";
    var marker=new Object();
    marker.symbol="square";
    seriesObjectP.marker=marker;
    seriesObjectF.marker=marker;
    seriesObjectI.marker=marker;
    $.each(seriesData,function (i,item) {
        if(item.hasOwnProperty("endTime")&&item.hasOwnProperty("checkResult")){
            var endTime=getFormatDateByLong(item.endTime,"yyyyMMddhhmmss");
            var nowTime = new Date().format("yyyyMMddhhmmss");
            var oneDay= 1000000;
            item.z=100;
            if (parseInt(endTime) <parseInt(nowTime) - oneDay) {
                item.checkResult="normal";
                idleData.push(item);
                seriesObjectI.data=idleData;
            }else{
                if(item.checkResult!="normal"&&item.releaseFlag!=true){
                    failData.push(item);
                    seriesObjectF.data=failData;
                }else if(item.checkResult!="normal"&&item.releaseFlag==true){
                    item.checkResult="normal";
                    passData.push(item);
                    seriesObjectP.data=passData;
                }else if(item.checkResult=="normal"){
                    passData.push(item);
                    seriesObjectP.data=passData;
                }
            }
        }else{
            item.z=100;
            idleData.push(item);
            seriesObjectI.data=idleData;
        }

    })
    series.push(seriesObjectI);
    series.push(seriesObjectF);
    series.push(seriesObjectP);
    chart = Highcharts.chart(name, {
        chart: {
            type: 'bubble',
        },
        navigation:{
            buttonOptions:{
                verticalAlign: "bottom",
                y:-20
            }
        },
        title: {
            text: ''
        },
        subtitle: {
        },
        xAxis: {
            visible:false,
            opposite: true,
            title: {
                text:'X'
            },
            startOnTick:false,
            endOnTick: false,
            showLastLabel: true
        },
        yAxis: {
            visible:false,
            title: {
                text:'Y'
            },
            reversed:true
        },
        tooltip:{
            pointFormat:'X:{point.x},Y:{point.y}'
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            verticalAlign: 'top',
            x: 100,
            y: 0,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
            borderWidth: 1
        },
        plotOptions: {
            bubble: {
                mixSize:20,
                maxSize: 60,
                pointPadding:0.5
            },
            series: {
                dataLabels: {
                    enabled: true,
                    formatter:function () {
                        if(this.point.hasOwnProperty("checkResult")){
                            if(this.point.checkResult!="normal"){
                                var checkResults=this.point.checkResult;
                                var checkResultSummary=checkResults.split(";");
                                var checkResult=this.point.name;
                                for(var i=0;i<checkResultSummary.length;i++){
                                    checkResult=checkResult+"<br>"+checkResultSummary[i].replace("Fail","").trim()
                                }
                                return checkResult;
                            }
                        }
                        return this.point.name;
                    }
                },
                point:{
                    events:{
                        click:function () {
                           var  pointIndex=this.index;
                           var seriesIndex=this.series.index;
                           var seriesName=this.series.name;
                            $('#detailsTable').html("");
                            $('#historyTable').bootstrapTable('removeAll');
                            $.ajax({
                                type: "get",
                                url: "/DataParseSystem/GetTesterStatus/getTesterStatusSingle?tester="+this.name,
                                async: false,
                                dataType: 'json',
                                success: function (data) {
                                    if(data!=null){
                                        var newData=[];
                                        newData.push(data)
                                        $.each(newData, function (i, item) {
                                            var endTime=getSmpFormatDateByLong(item.endTime,true);
                                            var startTime=getSmpFormatDateByLong(item.startTime,true);
                                            var processYield;
                                            var othersParams=item.othersParams.replace("{","").replace("}","").trim();
                                            var others=othersParams.split('",');
                                            var properties=[];
                                            var values=[];
                                            for(var k=0;k<others.length;k++){
                                                var index=others[k].indexOf(":");
                                                var property=others[k].substring(1,index-1);
                                                var value=others[k].substring(index+2,others[k].length);
                                                if(k==others.length-1){
                                                    value=others[k].substring(index+2,others[k].length-1);
                                                }
                                                if(property!="ALL CP Programs"&&property!="Process Yield"){
                                                    properties.push(property);
                                                    values.push(value);
                                                }
                                                if(property=="Process Yield"){
                                                    processYield=value;
                                                }
                                            }
                                            values.push(item.testerProgram);
                                            values.push(item.pidName);
                                            values.push(item.pidName);
                                            values.push(item.pidVersion);
                                            values.push(item.passDie);
                                            values.push(item.failDie);
                                            values.push(item.mapCols);
                                            values.push(item.mapRows);
                                            values.push(item.minX);
                                            values.push(item.minY);
                                            values.push(item.checkStatus);
                                            properties.push("TesterProgram");
                                            properties.push("PidName");
                                            properties.push("PidVersion");
                                            properties.push("GoodDieCount");
                                            properties.push("BadDieCount");
                                            properties.push("MapCols");
                                            properties.push("MapRows");
                                            properties.push("MinX");
                                            properties.push("MinY");
                                            properties.push("CheckStatus");
                                            var propertyRow="<tr>";
                                            var propertyTable;
                                            for(var k=0;k<properties.length;k++)
                                            {
                                                if((parseInt(k)+1)%2==0)
                                                {
                                                    propertyRow+='<th style="background-color: #4fa2c4">'+properties[k]+'</th>'+'<td >'+values[k]+'</td>'+'</tr>';
                                                    propertyTable+=propertyRow;
                                                    propertyRow="";
                                                }else {
                                                    propertyRow+='<th style="background-color: #4fa2c4">'+properties[k]+'</th>'+'<td >'+values[k]+'</td>';
                                                }
                                                if(k==properties.length-1){
                                                    propertyTable+=propertyRow+'</tr>';
                                                }
                                            }
                                            $("#remainingTable").html(propertyTable);
                                            $("#detailsTable").append("<tr><th><label>CustomerCode:</label><td>" + item.customerCode + "</td></td></tr>"+
                                                "<tr><th><label style='background-color: #0e84f8'>Device:</label><td>" + item.device + "</td></td></tr>"+
                                                "<tr><th><label style='background-color: #0e84f8'>LotID:</label><td>" + item.lotId + "</td></td></tr>"+
                                                "<tr><th><label style='background-color: #0e84f8'>WaferNO:</label><td>" + item.waferNo + "</td></td></tr>"+
                                                "<tr><th><label>CpStep:</label><td>" + item.cpStep + "</td></td></tr>"+
                                                "<tr><th><label>EquipID:</label><td>" + item.testerId + "</td></td></tr>"+
                                                "<tr><th><label>ProdID:</label><td >" + item.proberId + "</td></td></tr>"+
                                                "<tr><th><label>ProberCardId:</label><td >" + item.proberCardId + "</td></td></tr>"+
                                                "<tr><th><label>Operator:</label><td>" + item.oprator + "</td></td></tr>"+
                                                "<tr><th><label style='background-color: #0e84f8'>CPYield:</label><td>" + item.yield + "</td></td></tr>"+
                                                "<tr><th><label>Process Yield:</label><td >" + processYield+ "</td></td></tr>"+
                                                "<tr><th><label>GrossDie:</label><td>" + item.grossDie + "</td></td></tr>"+
                                                "<tr><th><label>StartTestTime:</label><td>" + startTime + "</td></td></tr>"+
                                                "<tr><th><label>EndTestTime:</label><td>" + endTime+ "</td></td></tr>"+
                                                '<tr><td><p class="div-float"><button class="button button-primary button-tiny button-3d" data-toggle="modal" data-target="#history">历史记录</button></p></td></tr>'+
                                                '<tr><td><p class="div-float"><a target="_blank" href="/DataParseSystem/Navigation/waferMap?customer='+item.customerCode+'&device='+item.device+'&lot='+item.lotId+'&cp='+item.cpStep+'&wafer='+item.waferNo+'">Map图</a>'+ '</p></td></tr>')
                                            if(item.checkResult!="normal"&&seriesName=="Fail"){
                                                $("#detailsTable").append('<tr><td><button class="button button-primary button-tiny button-3d" value='+item.testerId+";"+item.waferNo+";"+pointIndex+";"+seriesIndex+";"+name+' onclick="setTesterData(this.value)">Release</button></td></tr>');
                                            }
                                        })
                                        $('#details').modal('show');
                                    }
                                    else {
                                        alert("Tester Unused!")
                                    }
                                }
                            })
                            var historyData=[];
                            var columns=[
                                {title:'CCode',field:'customerCode'},
                                {title:'Device',field:'device'},
                                {title:'LotID',field:'lotId'},
                                {title:'WaferNo',field:'waferNo'},
                                {title:'CPStep',field:'cpStep'},
                                {title:'TesterID',field:'testerId'},
                                {title:'ProberCardID',field:'proberCardId'},
                                {title:'StartTime',field:'startTime'},
                                {title:'EndTime',field:'endTime'},
                                {title:'CheckResult',field:'checkResult'}
                            ];
                            $.ajax({
                                type: "get",
                                url: "/DataParseSystem/GetTesterStatus/getTesterStatusList?tester="+this.name,
                                async: false,
                                dataType: 'json',
                                success: function(data) {
                                    $.each(data,function (i,item) {
                                        item.startTime=getSmpFormatDateByLong(item.startTime,true);
                                        item.endTime=getSmpFormatDateByLong(item.endTime,true);
                                        historyData.push(item);
                                    })
                                }
                            });
                            $('#historyTable').bootstrapTable('append', historyData);
                            $('#historyTable').bootstrapTable({
                                data: historyData,
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
                                columns: columns,
                                showExport: true,
                                buttonsAlign: "right",
                                exportDataType: "all",
                                exportTypes: ['excel', 'xlsx'],
                                Icons: 'glyphicon-export',
                                exportOptions: {
                                    fileName: 'TesterStatusHistory',
                                    worksheetName: 'Sheet1',
                                    tableName: 'data',
                                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                                }
                            })
                        }
                    }
                }

            }
        },
        series:series
    });
}
function showTester() {
    var AData=[];
    var DData=[];
    $.each(getData(),function (i,item) {
        if(item.location=="SHA2"){
            AData.push(item);
        }
        if(item.location=="SHD1"){
            DData.push(item);
        }
    })
    getCharts("containerD1",DData);
    getCharts("containerA2",AData);
}
showTester()
setInterval(showTester,30*1000);
$("#A2").click(function () {
    $("#containerA2").show();
    $("#containerD1").hide();
})
$("#D1").click(function () {
    $("#containerA2").hide();
    $("#containerD1").show();
})