    var table = $('#example1').DataTable( {
        "oLanguage": {
							"sLengthMenu": "每页显示 _MENU_ 条记录",
							"sZeroRecords": "对不起，查询不到任何相关数据",
							"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
							"sInfoEmtpy": "找不到相关数据",
							"sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
							"sProcessing": "正在加载中...",
							"sSearch": "搜索",
							"sUrl": "",
							"oPaginate": {
								"sFirst": "第一页",
								"sPrevious": " 上一页 ",
								"sNext": " 下一页 ",
								"sLast": " 最后一页 "
							}
						},
        "columns": [
           
            { "data":"filename"},
            { "data": "fileurl" },
            {
                "className":      'details-control',
                "orderable":      false,
                "data":           null,
                "defaultContent": '<button id="details-button" class="fa fa-download fa-2x center-block button-3d button-primary" style="margin-left:5px;border-style:none"></button>'
            }
        ]
    } );
var data1=[];
    $.ajax({
		type: "get",
		url: "download.json",
		async: false,
		dataType: 'json',
		success: function(data) {
			$.each(data, function(i, item) {
			    data1=data;
				table.row.add(item).draw();
			});
		}
	});

	 $('#details-button').click(function () {
	     alert("123")
          window.open("")
     })