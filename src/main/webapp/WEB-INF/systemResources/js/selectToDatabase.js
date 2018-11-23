var customerToDevice = [];
var lotToCP = [];
var remainingData=[];
$(document).ready(function() {
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
    var $selWN = $('#wafer_no');
    function appendOptTo($o, k, v) {
        var $opt = $("<option>").text(k).val(v);
        $opt.appendTo($o);
    };
    $("#refresh").click(function() {
        $selCC.html("");
        $selDe.html("");
        $selLI.html("");
        $selCS.html("");
        $selWN.html("");
        appendOptTo($selCC, "*", "0");
        appendOptTo($selDe, "*", "0");
        appendOptTo($selLI, "*", "0");
        appendOptTo($selCS, "*", "0");
        appendOptTo($selWN, "*", "0");
        $.ajax({
            type: "get",
            url:"/DataParseSystem/getWaferInfor/getCustomerAndDevices",
            async: false,
            dataType: 'json',
            success: function (data) {
                customerToDevice = data;
            }
        });
        $.each(customerToDevice, function (i, item) {
            appendOptTo($selCC, item.customer, i + 1);
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
                $.each(item.device, function (x, task) {
                    appendOptTo($selDe, task, i + 1)
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
        $.each(lotToCP, function (i, item) {
            appendOptTo($selLI, item.lot, i + 1)
        });
        $selLI.change();
    }).change();
    $selLI.change(function () {
        $selCS.html("");
        $selWN.html("");
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
        $selCS.change();
    }).change();
    $selCS.change(function () {
        $selWN.html("");
        appendOptTo($selWN, "*", "0")
        if (this.selectedIndex == -1) return;
        var customers=document.getElementById("customer_code");
        var customerTxt=customers.options[customers.selectedIndex].text;
        var devices=document.getElementById("device");
        var deviceTxt=devices.options[devices.selectedIndex].text;
        var lots=document.getElementById("lot_id");
        var lotTxt=lots.options[lots.selectedIndex].text;
        var cp = this.options[this.selectedIndex].text;
        $.ajax({
            type: "get",
            url: "/DataParseSystem/getWaferInfor/getWaferIds?customer="+customerTxt+"&device="+deviceTxt+"&lot="+lotTxt+"&cp="+cp,
            async: false,
            dataType: 'json',
            success: function (data) {
                remainingData = data;
            }
        });
        $.each(remainingData, function (i, item) {
            appendOptTo($selWN,item, i + 1)

        });
    }).change();
})
    var table = $('#example').DataTable({
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        "columns": [{
            "className": 'details-control',
            "orderable": false,
            "data": null,
            "defaultContent": '<i id="details-button" class="fa fa-chevron-circle-down fa-2x center-block" style="margin-left:5px"></i>'
        }, {
            "data": "customCode"
        },
            {
                "data": "device"
            },
            {
                "data": "lotId"
            },
            {
                "data": "cpStep"
            },
            {
                "data": "waferNo"
            },
            {
                "data": "testerId"
            },
            {
                "data": "proberId"
            },
            {
                "data": "proberCardId"
            },
            {
                "data": "startTime"
            },
            {
                "data": "endTime"
            }
        ]
    });
function getUrl(customer,device,lot,wafer,cp){
    var url="?customer";
    if(customer=="*")
    {
        return "*";
    }else
    {
        url=url+"="+customer;
    }
    if (device=="*")
    {
            return url;
    }else
    {
        url=url+"&device="+device;
    }
    if (lot=="*")
    {
        return url;
    }else
    {
        url=url+"&lot="+lot;
    }
    if (cp=="*")
    {
        return url;
    }else
    {
        url=url+"&cp="+cp;
    }
    if (wafer=="*")
    {
        return url;
    }else
    {
        url=url+"&wafer="+wafer;
    }
    return url;

}
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
$("#findData").click(function() {
	/*table.fnDeleteRow();*/
	table.clear().draw();
    var totalData=[];
    var customer = document.getElementById("customer_code");
    var device = document.getElementById("device");
    var lot = document.getElementById("lot_id");
    var wafer = document.getElementById("wafer_no");
    var cp = document.getElementById("cp_step");
    var customerTxt=customer.options[customer.selectedIndex].text;
    var deviceTxt=device.options[device.selectedIndex].text;
    var lotTxt=lot.options[lot.selectedIndex].text;
    var waferTxt=wafer.options[wafer.selectedIndex].text;
    var cpTxt=cp.options[cp.selectedIndex].text;
    var url=getUrl(customerTxt,deviceTxt,lotTxt,waferTxt,cpTxt);
    if(url=="*"){
        alert("please choose one customer!")
    }
    else {
        $.ajax({
            type: "get",
            url: "/DataParseSystem/getWaferInfor/getQureyInfors"+url,
            async: false,
            dataType: 'json',
            success: function (data) {
                totalData = data;
            }
        });
        $.each(totalData, function(i, item) {
            item.startTime=new Date(item.startTime).Format("yyyy-MM-dd hh:mm:ss");
            item.endTime=new Date(item.endTime).Format("yyyy-MM-dd hh:mm:ss");
            table.row.add(item).draw();
        });
    }
});
function format(d) {
    return '<table border="5" style="background-color: #5cb85c width: 100%;border:solid gainsboro; border-width:1px 0px 0px 1px;text-align: center;" id="detailsTable">' +
        '<tr>' +
        '<th>Operator:</th>' +
        '<th>Program:</th>' +
        '<th>GrossDie:</th>' +
        '<th>PassDie:</th>' +
        '<th>FailDie:</th>' +
        '<th>Yield:</th>' +
        '<th>MapCols:</th>' +
        '<th>MapRows:</th>' +
        '<th>MinX:</th>' +
        '<th>MinY:</th>' +
        '<th>PidName:</th>' +
        '<th>PidVersion:</th>' +
        '<th>Map图</th>'+
        '</tr>'+
        '<tr>'+
        '<th>' + d.oprator + '</th>'+
        '<th>' + d.testerProgram + '</th>' +
        '<th>' + d.grossDie + '</th>' +
        '<th>' + d.passDie + '</th>' +
        '<th>' + d.failDie + '</th>' +
        '<th>' + d.yield + '</th>' +
        '<th>' + d.mapCols + '</th>' +
        '<th>' + d.mapRows + '</th>' +
        '<th>' + d.minX + '</th>' +
        '<th>' + d.minY + '</th>' +
        '<th>' + d.pidName + '</th>' +
        '<th>' + d.pidVersion + '</th>' +
        '<th>' +
        '<a target="_blank" href="Mapping.html?customer='+d.customCode+'&device='+d.device+'&lot='+d.lotId+'&cp='+d.cpStep+'&wafer='+d.waferNo+'"><i class="fa fa-hand-o-right"></i>查看</a>'+ '</th>' +'</tr>'+
        "</table>";
}
$('#example tbody').on('click', 'td.details-control', function() {
	var trs = $(this).closest('tr');
	var rows = table.row(table.row(trs).index());
	if(rows.child.isShown()) {
		$(this).children().removeClass('fa-chevron-circle-up');
		$(this).children().addClass('fa-chevron-circle-down');
		rows.child.hide();
		trs.removeClass('shown');
	} else {
		$(this).children().removeClass('fa-chevron-circle-down');
		$(this).children().addClass('fa-chevron-circle-up');
		rows.child(format(rows.data())).show();
		trs.addClass('shown');
	}
});