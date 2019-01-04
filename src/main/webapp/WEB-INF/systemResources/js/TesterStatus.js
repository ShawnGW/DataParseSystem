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
    function test() {
        var testerData = [];
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
    var unusedData = [];
    var idleData=[];
    var passData=[];
    var failData=[];
    $("#goodTester").html("");
    $("#badTester").html("");
    $("#badDemo").html("");
    $("#goodDemo").html("");
    $("#idleTester").html("");
    $("#idleDemo").html("");
    $("#unusedTester").html("");
    $("#unusedDemo").html("");
    var badIndex = 0;
    var goodIndex = 0;
    var idleIndex = 0;
    var unusedIndex = 0;
    $.each(test(), function (i, item) {
        var endTime=new Date(getSmpFormatDateByLong(item.endTime,true));
        var time=endTime.format("yyyyMMddhhmmss");
        var nowTime = new Date().format("yyyyMMddhhmmss");
        var halfDay = 60000;
        var twoDay = 2000000;
        if (parseInt(time) > parseInt(nowTime) - twoDay && parseInt(time) < parseInt(nowTime) - halfDay) {
            idleIndex++;
            var buttonValue = item.testerId.replace("-", " ")
            var button = '<button id="' + item.testerId + '" class="button button-caution button-rounded" data-toggle="popover"' +
                'title="Fail" data-content="' + item.checkResult + '" data-trigger="hover" style="margin: 5px;width: 100px;height: 100px;background-color: gray;border-color: gray">' + buttonValue + '</button>';
            if (idleIndex <= 7) {
                $("#idleTester").append(button);
            }
            else {
                idleData.push(button);
            }

        }
        else if (parseInt(time) > parseInt(nowTime) - halfDay) {
            if (item.testerId != "normal") {
                badIndex++;
                var buttonValue = item.testerId.replace("-", " ")
                var button = '<button id="' + item.testerId + '" class="button button-caution button-primary button-rounded" data-toggle="popover"' +
                    'title="Fail" data-content="' + item.checkResult + '" data-trigger="hover" style="margin: 5px;width: 100px;height: 100px;">' + buttonValue + '</button>';
                if (badIndex <= 7) {
                    $("#badTester").append(button);
                }
                else {
                    failData.push(button);
                }

            } else {
                goodIndex++;
                var buttonValue = item.testerId.replace("-", " ")
                var button = '<button id="' + item.testerId + '" class="button  button-primary button-rounded" data-toggle="popover"' +
                    'title="Good" data-content="' + item.checkResult + '" data-trigger="hover" style="margin: 5px;background-color: green;border-color: green;width: 100px;height: 100px;">' + buttonValue + '</button>';
                if (goodIndex > 7) {
                    passData.push(button);
                } else {
                    $("#goodTester").append(button);
                }
            }
        }
        else {
            unusedIndex++;
            var buttonValue = item.testerId.replace("-", " ")
            var button = '<button id="' + item.testerId + '" class="button button-caution button-rounded" data-toggle="popover"' +
                'title="Fail" data-content="' + item.checkResult + '" data-trigger="hover" style="margin: 5px;width: 100px;height: 100px;background-color:#8B4513;border-color: #8B4513;" onclick="getTable(this)">' + buttonValue + '</button>';
            if (unusedIndex <= 7) {
                $("#unusedTester").append(button);
            }
            else {
                unusedData.push(button);

            }
        }
    });

    function getPaging(data,elem,id){
        layui.use(['laypage', 'layer'], function () {
            var laypage = layui.laypage
                , layer = layui.layer;
            laypage.render({
                elem: elem
                , count: data.length
                , layout: ['limit', 'prev', 'page', 'next']
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
    }
    $("#goodTester").hide();
    $("#goodDemo").hide();
    $("#idleTester").hide();
    $("#idleDemo").hide();
    $("#unusedTester").hide();
    $("#unusedDemo").hide();
    $("#failButton").click(function () {
        $("#badTester").show();
        $("#badDemo").show();
        $("#goodTester").hide();
        $("#goodDemo").hide();
        $("#idleTester").hide();
        $("#idleDemo").hide();
        $("#unusedTester").hide();
        $("#unusedDemo").hide();
        $("#badDemo").addClass("collapse in");
        $("#unusedButton").next().attr("id", "badButton").attr("data-target", "#badDemo").removeClass("collapsed").attr("aria-expanded", "true");
        $("#unusedButton").next().children("i").attr("class", "fa fa-minus-circle");
        $("#goodDemo").removeClass("collapse");
        $("#idleDemo").removeClass("collapse");
        $("#unusedPaging").hide();
        $("#idlePaging").hide();
        $("#badPaging").show();
        $("#goodPaging").hide();
        getPaging(failData,"badPaging","badDemo");
    })
    $("#passButton").click(function () {
        $("#badTester").hide();
        $("#badDemo").hide();
        $("#goodTester").show();
        $("#goodDemo").show();
        $("#idleTester").hide();
        $("#idleDemo").hide();
        $("#unusedTester").hide();
        $("#unusedDemo").hide();
        $("#goodDemo").addClass("collapse in");
        $("#unusedButton").next().attr("id", "goodButton").attr("data-target", "#goodDemo").removeClass("collapsed").attr("aria-expanded", "true");
        $("#unusedButton").next().children("i").attr("class", "fa fa-minus-circle");
        $("#badDemo").removeClass("collapse");
        $("#idleDemo").removeClass("collapse");
        $("#unusedPaging").hide();
        $("#idlePaging").hide();
        $("#badPaging").hide();
        $("#goodPaging").show();
        getPaging(passData,"goodPaging","goodDemo");
    })
    $("#idleButton").click(function () {
        $("#badTester").hide();
        $("#badDemo").hide();
        $("#goodTester").hide();
        $("#goodDemo").hide();
        $("#idleTester").show();
        $("#idleDemo").show();
        $("#unusedTester").hide();
        $("#unusedDemo").hide();
        $("#idleDemo").addClass("collapse in");
        $("#unusedButton").next().attr("id", "idleCollapse").attr("data-target", "#idleDemo").removeClass("collapsed").attr("aria-expanded", "true");
        $("#unusedButton").next().children("i").attr("class", "fa fa-minus-circle");
        $("#badDemo").removeClass("collapse");
        $("#goodDemo").removeClass("collapse");
        $("#unusedDemo").removeClass("collapse");
        $("#unusedPaging").hide();
        $("#idlePaging").show();
        $("#badPaging").hide();
        $("#goodPaging").hide();
        getPaging(idleData,"idlePaging","idleDemo");
    })
    $("#unusedButton").click(function () {
        $("#badTester").hide();
        $("#badDemo").hide();
        $("#goodTester").hide();
        $("#goodDemo").hide();
        $("#idleTester").hide();
        $("#idleDemo").hide();
        $("#unusedTester").show();
        $("#unusedDemo").show();
        $("#unusedDemo").addClass("collapse in");
        $("#unusedButton").next().attr("id", "unusedCollapse").attr("data-target", "#unusedDemo").removeClass("collapsed").attr("aria-expanded", "true");
        $("#unusedButton").next().children("i").attr("class", "fa fa-minus-circle");
        $("#badDemo").removeClass("collapse");
        $("#goodDemo").removeClass("collapse");
        $("#idleDemo").removeClass("collapse");
        $("#unusedPaging").show();
        $("#idlePaging").hide();
        $("#badPaging").hide();
        $("#goodPaging").hide();
        getPaging(unusedData,"unusedPaging","unusedDemo");
    })
    $("#badDemo").on("show.bs.collapse", function () {
        $("#badButton i").attr("class", "fa fa-minus-circle")
    });
    $("#badDemo").on("hide.bs.collapse", function () {

        $("#badButton i").attr("class", "fa fa-plus-circle")
    })
    $("#goodDemo").on("show.bs.collapse", function () {
        $("#goodButton i").attr("class", "fa fa-minus-circle")
    });
    $("#goodDemo").on("hide.bs.collapse", function () {

        $("#goodButton i").attr("class", "fa fa-plus-circle")
    })
    $("#idleDemo").on("show.bs.collapse", function () {
        $("#idleCollapse i").attr("class", "fa fa-minus-circle")
    });
    $("#idleDemo").on("hide.bs.collapse", function () {

        $("#idleCollapse i").attr("class", "fa fa-plus-circle")
    })
    $("#unusedDemo").on("show.bs.collapse", function () {
        $("#unusedCollapse i").attr("class", "fa fa-minus-circle")
    });
    $("#unusedDemo").on("hide.bs.collapse", function () {

        $("#unusedCollapse i").attr("class", "fa fa-plus-circle")
    });

    function getTable(obj) {
        $("#example3").html("");
        $.each(test(), function (i, item) {
            var endTime=getSmpFormatDateByLong(item.endTime,true);
            var startTime=getSmpFormatDateByLong(item.startTime,true);
            var othersParams=item.othersParams.replace("{","").replace("}","").trim();
            var others=othersParams.split(",");
            var table="<table>";
            for(var k=0;k<others.length;k++){
                var index=others[k].indexOf(":");
                /*alert(others[k].substring(1,index-1))*/
                /*alert(others[k].substring(index+2,others[k].length-1))*/
                table=table+"<tr><td>"+others[k].substring(1,index-1)+"</td><td>"+others[k].substring(index+2,others[k].length-1)+"</td></tr>";

            }
            table=table+"</table>";
            if (obj.id == item.testerId) {
                $("#example3").append("<tr><td>EquipID</td><td>" + item.testerId + "</td></tr>+" +
                    "<tr><td>TesterProgram</td><td>" + item.testerProgram + "</td></tr>"+
                    "<tr><td>PidName</td><td>" + item.pidName + "</td></tr>"+
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
                    "<tr><td>StartTestTime</td><td>" + startTime + "</td></tr>"+
                    "<tr><td>EndTestTime</td><td>" + endTime + "</td></tr>"+
                    '<tr><td>其余详情</td><td><a href="#" data-toggle="popover" title="'+table+'" data-trigger="hover" data-html="true">请悬停在我的上面</a></td></tr>'+
                    '<tr><td>Map图</td><td><a target="_blank" href="/DataParseSystem/Navigation/waferMap?customer='+item.customerCode+'&device='+item.device+'&lot='+item.lotId+'&cp='+item.cpStep+'&wafer='+item.waferNo+'"><i class="fa fa-hand-o-right"></i>查看</a>'+ '</td></tr>')
            }
        })
    }
    setInterval(test, 30 * 1000);

