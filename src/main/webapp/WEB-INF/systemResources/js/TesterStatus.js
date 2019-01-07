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
function popover() {
    $("[data-toggle='popover']").popover();
}
setInterval(popover,100);
var index = 0;
var testerData = [];
    function test() {
        $("#goodTester").html("");
        $("#badTester").html("");
        $("#idleTester").html("");
        $("#unusedTester").empty();
        $.ajax({
            type: "get",
            url: "/DataParseSystem/GetTesterStatus/status",
            async: false,
            dataType: 'json',
            success: function (data) {
                testerData = data;
            }
        })
        return testerData;
    }
function getTable(obj) {
    $("#example3").html("");
    $.each(testerData, function (i, item) {
        var endTime=getSmpFormatDateByLong(item.endTime,true);
        var startTime=getSmpFormatDateByLong(item.startTime,true);
        if (obj.id == item.testerId) {
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
                if (property!="ALL CP Programs") {
                    properties.push(property);
                    values.push(value);
                }
            }
            var propertyRow="<tr>";
            var valueRow="<tr>";
            var propertyTable;
            for(var k=0;k<properties.length;k++)
            {
                if((parseInt(k)+1)%7==0)
                {
                    propertyRow+='<th style="background-color: #4fa2c4">'+properties[k]+'</th>'+'</tr>';
                    valueRow+='<td >'+values[k]+'</td>'+"</tr>";
                    propertyTable+=propertyRow+valueRow;
                    propertyRow="";
                    valueRow="";
                }else {
                    propertyRow+='<th style="background-color: #4fa2c4">'+properties[k]+'</th>';
                    valueRow+='<td >'+values[k]+'</td>';
                }
                if(k==properties.length-1){
                    propertyTable+=propertyRow+'</tr>'+valueRow;
                }
            }
            $("#remainingTable").html(propertyTable);
            $("#example3").append("<tr><td>EquipID</td><td>" + item.testerId + "</td></tr>+" +
                "<tr><td>TesterProgram</td><td>" + item.testerProgram + "</td></tr>"+
                "<tr><td>PidName</td><td>" + item.pidName + "</td></tr>"+
                "<tr><td>PidVersion</td><td>" + item.pidVersion + "</td></tr>"+
                "<tr><td>ProberCardId</td><td>" + item.proberCardId + "</td></tr>"+
                "<tr><td>ProdID</td><td>" + item.proberId + "</td></tr>"+
                "<tr><td>CustomerCode</td><td>" + item.customerCode + "</td></tr>"+
                "<tr><td>Device</td><td>" + item.device + "</td></tr>"+
                "<tr><td>LotID</td><td>" + item.lotId + "</td></tr>"+
                "<tr><td>CpStep</td><td>" + item.cpStep + "</td></tr>"+
                "<tr><td>WaferNO</td><td>" + item.waferNo + "</td></tr>"+
                "<tr><td>Operator</td><td>" + item.oprator + "</td></tr>"+
                "<tr><td>CPYield</td><td>" + item.yield + "%</td></tr>"+
                "<tr><td>GrossDie</td><td>" + item.grossDie + "</td></tr>"+
                "<tr><td>GoodDieCount</td><td>" + item.passDie + "</td></tr>"+
                "<tr><td>BadDieCount</td><td>" + item.failDie + "</td></tr>"+
                "<tr><td>MapCols</td><td>" + item.mapCols + "</td></tr>"+
                "<tr><td>MapRows</td><td>" + item.mapRows + "</td></tr>"+
                "<tr><td>MinX</td><td>" + item.minX + "</td></tr>"+
                "<tr><td>MinY</td><td>" + item.minY + "</td></tr>"+
                "<tr><td>StartTestTime</td><td>" + startTime + "</td></tr>"+
                "<tr><td>EndTestTime</td><td>" + endTime + "</td></tr>"+
                "<tr><td>CheckStatus</td><td>" + item.checkStatus + "</td></tr>"+
                '<tr><td>详情</td><td><button class="button button-primary button-tiny" data-toggle="modal" data-target="#myModal">请点击</button></td></tr>'+
                '<tr><td>Map图</td><td><a target="_blank" href="/DataParseSystem/Navigation/waferMap?customer='+item.customerCode+'&device='+item.device+'&lot='+item.lotId+'&cp='+item.cpStep+'&wafer='+item.waferNo+'"><i class="fa fa-hand-o-right"></i>查看</a>'+ '</td></tr>')
        }
    })
}
    function statusRefresh() {
        testerData=test();
        var unusedData = [];
        var idleData=[];
        var passData=[];
        var failData=[];
        $.each(testerData, function (i, item) {
            var endTime=new Date(getSmpFormatDateByLong(item.endTime,true));
            var time=endTime.format("yyyyMMddhhmmss");
            var nowTime = new Date().format("yyyyMMddhhmmss");
            var halfDay = 60000;
            var twoDay = 2000000;
            var title="Fail";
            var buttonValue = item.testerId.replace("-", " ");
            if (parseInt(time) > parseInt(nowTime) - twoDay && parseInt(time) < parseInt(nowTime) - halfDay) {
                if (item.checkResult == "normal") {
                    title="Good";
                }
                var button = '<button id="' + item.testerId + '" class="button button-caution button-rounded" data-toggle="popover"' +
                    'title="'+title+'" data-content="' + item.checkResult + '" data-trigger="hover" style="margin: 5px;width: 100px;height: 100px;background-color: gray;border-color: gray" onclick="getTable(this)">' + buttonValue + '</button>';
                    idleData.push(button);

            }
            else if (parseInt(time) > parseInt(nowTime) - halfDay) {
                if (item.checkResult != "normal") {
                    var button = '<button id="' + item.testerId + '" class="button button-caution button-primary button-rounded" data-toggle="popover"' +
                        'title="Fail" data-content="' + item.checkResult + '" data-trigger="hover" style="margin: 5px;width: 100px;height: 100px;" onclick="getTable(this)">' + buttonValue + '</button>';
                        failData.push(button);

                } else {
                    var button = '<button id="' + item.testerId + '" class="button  button-primary button-rounded" data-toggle="popover"' +
                        'title="Good" data-content="' + item.checkResult + '" data-trigger="hover" style="margin: 5px;background-color: green;border-color: green;width: 100px;height: 100px;"onclick="getTable(this)">' + buttonValue + '</button>';
                    passData.push(button);
                }
            }
            else {
                if (item.checkResult == "normal") {
                    title="Good";
                }
                var button = '<button id="' + item.testerId + '" class="button button-caution button-rounded" data-toggle="popover"' +
                    'title="'+title+'" data-content="' + item.checkResult + '" data-trigger="hover" style="margin: 5px;width: 100px;height: 100px;background-color:#8B4513;border-color: #8B4513;" onclick="getTable(this)">' + buttonValue + '</button>';
                    unusedData.push(button);
            }
        });

        function getPaging(data,elem,id){
            $('#'+id).parent().show();
            $('#'+id).parent().siblings().hide();
            $(".button-group").parent().show();
            layui.use(['laypage', 'layer'], function () {
                var laypage = layui.laypage
                    , layer = layui.layer;
                laypage.render({
                    elem: elem
                    , count: data.length
                    , layout: ['limit', 'prev', 'page', 'next']
                    ,limit: 50
                    ,limits: [50, 100, 150]
                    ,curr: location.hash.replace('#!Page=', '')
                    ,hash: 'Page'
                    , jump: function (obj) {
                        document.getElementById(id).innerHTML = function () {
                            var arr = []
                                , thisData = data.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                            layui.each(thisData, function (index, item) {
                                arr.push('<li style="float: left">' + item + '</li>');
                            });
                            return arr.join('');
                        }();
                    }
                });
            });
        };
        getPaging(passData,"goodPaging","goodTester");
        if(index==1){
            getPaging(failData,"badPaging","badTester");
        }
        if(index==2){
            getPaging(passData,"goodPaging","goodTester");
        }
        if(index==3){
            getPaging(idleData,"idlePaging","idleTester");
        }
        if(index==4){
            getPaging(unusedData,"unusedPaging","unusedTester");
        }
        $("#failButton").click(function () {
            getPaging(failData,"badPaging","badTester");
            index=1;
        })
        $("#passButton").click(function () {
            getPaging(passData,"goodPaging","goodTester");
            index=2;
        })
        $("#idleButton").click(function () {
            getPaging(idleData,"idlePaging","idleTester");
            index=3;
        })
        $("#unusedButton").click(function () {
            getPaging(unusedData,"unusedPaging","unusedTester");
            index=4;
        })
    }
    statusRefresh();
    setInterval(statusRefresh,30*1000);

