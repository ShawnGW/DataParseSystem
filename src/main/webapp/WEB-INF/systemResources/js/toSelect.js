var customerToDevice = [];
var remainingData = [];
var totalData=[];
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
    $.ajax({
        type: "get",
        url: "./js/chart3.json",
        async: false,
        dataType: 'json',
        success: function (data) {
            remainingData = data;
        }
    });
    $.ajax({
        type: "get",
        url: "./js/chart4.json",
        async: false,
        dataType: 'json',
        success: function (data) {
            totalData = data;
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
    appendOptTo($selCC, "*", "0")
    $.each(customerToDevice, function (i, item) {
        appendOptTo($selCC, item.customer, item.id, i + 1)

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
        var device = this.options[this.selectedIndex].text;
        $.each(remainingData, function (i, item) {
            if (device === item.device) {
                $.each(item.lot, function (x, task) {
                    appendOptTo($selLI, task, i + 1)
                })
            }
        });
        $selLI.change();
    }).change();
    $selLI.change(function () {
        $selCS.html("");
        $selWN.html("");
        appendOptTo($selCS, "*", "0")
        if (this.selectedIndex == -1) return;
        var lot = this.options[this.selectedIndex].text;
        $.each(remainingData, function (i, item) {
            $.each(item.lot, function (x, task) {
                if (lot === task) {
                    $.each(item.cp, function (m, issue) {
                        appendOptTo($selCS, issue, i + 1)
                    })
                }
            })
        });
        $selCS.change();
    }).change();
    $selCS.change(function () {
        $selWN.html("");
        appendOptTo($selWN, "*", "0")
        if (this.selectedIndex == -1) return;
        var lot=document.getElementById("lot_id");
        var lotTxt=lot.options[lot.selectedIndex].text;
        var cp = this.options[this.selectedIndex].text;
        $.each(remainingData, function (i, item) {
        	$.each(item.lot,function (x,task) {
                if(lotTxt==task){
                    $.each(item.cp, function (m, issue) {
                    	if(cp==issue){
                            $.each(item.wafer,function (t,subject) {
                                appendOptTo($selWN, subject, i + 1)
                            })
                        }
					})

                }
            })

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
        "data": "customer_code"
    },
        {
            "data": "device"
        },
        {
            "data": "lot_id"
        },
        {
            "data": "cp_step"
        },
        {
            "data": "wafer_no"
        },
        {
            "data": "tester_id"
        },
        {
            "data": "prober_id"
        },
        {
            "data": null
        },
        {
            "data": "start_time"
        },
        {
            "data": "end_time"
        }
    ]
});
$("#btn2").click(function() {
	/*table.fnDeleteRow();*/
	table.clear().draw();
	$.each(totalData, function(i, item) {
		var customer = document.getElementById("customer_code");
		var device = document.getElementById("device");
		var lot = document.getElementById("lot_id");
		var wafer = document.getElementById("wafer_no");
		var cp = document.getElementById("cp_step");
		if(customer.options[customer.selectedIndex].text === item.customer_code) {
			if(device.options[device.selectedIndex].text === "请选择") {
				table.row.add(item).draw();
			}
			if(device.options[device.selectedIndex].text === item.device) {
				if(lot.options[lot.selectedIndex].text === "请选择") {
					/*table.fnAddData(item);*/
					table.row.add(item).draw();
				}
				if(lot.options[lot.selectedIndex].text === item.lot_id) {
					if(cp.options[cp.selectedIndex].text === "请选择") {
						/*table.fnAddData(item);*/
						table.row.add(item).draw();
					}
					if(cp.options[cp.selectedIndex].text === item.cp_step) {
						if(wafer.options[wafer.selectedIndex].text === "请选择") {
							/*table.fnAddData(item);*/
							table.row.add(item).draw();
						}
						if(wafer.options[wafer.selectedIndex].text === item.wafer_no) {
							/*table.fnAddData(item);*/
							table.row.add(item).draw();
						}
					}
				}

			}

		}
	})
});


function formatData(d) {
	return '<form class="form-horizontal">' +
		'<div class="form-group">' +
		'<label class="col-sm-1">Operator:</label>' + '<div class="col-sm-2">' + d.operator + '</div>' +
		'<label class="col-sm-1">Program:</label>' + '<div class="col-sm-2">' + d.tester_program + '</div>' +
		'<label class="col-sm-1">GrossDie:</label>' + '<div class="col-sm-2">' + d.gross_die + '</div>' +
		'<label class="col-sm-1">PassDie:</label>' + '<div class="col-sm-2">' + d.pass_die + '</div>' + '</div>' +
		'<div class="form-group">' +
		'<label class="col-sm-1">FailDie:</label>' + '<div class="col-sm-2">' + d.fail_die + '</div>' +
		'<label class="col-sm-1">Yield:</label>' + '<div class="col-sm-2">' + d.yield + '</div>' +
		'<label class="col-sm-1">MapCols:</label>' + '<div class="col-sm-2">' + d.map_cols + '</div>' +
		'<label class="col-sm-1">MapRows:</label>' + '<div class="col-sm-2">' + d.map_rows + '</div>' + '</div>' +
		'<div class="form-group">' +
		'<label class="col-sm-1">MinX:</label>' + '<div class="col-sm-2">' + d.minx + '</div>' +
		'<label class="col-sm-1">MinY:</label>' + '<div class="col-sm-2">' + d.miny + '</div>' +
		'<label class="col-sm-1">CheckStatus:</label>' + '<div class="col-sm-2">' + d.check_status + '</div>' +
		'<label class="col-sm-1">LoadTime:</label>' + '<div class="col-sm-2">' + d.load_time + '</div>' + '</div>' +
		'<div class="form-group">' +
		'<label class="col-sm-1">CheckResult:</label>' + '<div class="col-sm-2">' + d.check_result + '</div>' +
		'<label class="col-sm-1">KeyPara:</label>' + '<div class="col-sm-2">' + d.key_para + '</div>'+
        '<a href="Mapping.html?customer='+d.customer_code+'&device='+d.device+'&lot='+d.lot_id+'&cp='+d.cp_step+'&wafer='+d.wafer_no+'">查看Mapping图</a>'+ '</div>' +
		"</form>";
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
		rows.child(formatData(rows.data())).show();
		trs.addClass('shown');
	}
});