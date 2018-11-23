var RawData=[];
$.ajax({
	type: "get",
	url: "./js/status.json",
	async: false,
	dataType: 'json',
	success: function(data) {
		$.each(data, function(i, item) {
			if(item.key_para != "null") {
				var obj = document.getElementById("badTester");
				var button1 = document.createElement("input");
				button1.setAttribute("type", "button");
				button1.setAttribute("id", item.tester_id);
				button1.setAttribute("value", item.tester_id);
				button1.setAttribute("class", "button button-caution button-primary button-pill button-small button-3d");
				button1.setAttribute("onclick", "function(this.id)");
				obj.appendChild(button1);
			} else {
				var obj = document.getElementById("demo2");
				var button1 = document.createElement("input");
				button1.setAttribute("type", "button");
				button1.setAttribute("id", item.tester_id);
				button1.setAttribute("value", item.tester_id);
				button1.setAttribute("class", "button button-action button-primary button-pill button-3d");
				button1.setAttribute("onclick", "function(this.id)");
				obj.appendChild(button1);
			}
			$('#' + item.tester_id).click(function() {
                $("#example3 tr").html("");
                $("#example3").append("<tr><td>EquipID</td><td>"+item.tester_id+"</td></tr>");
                $("#example3").append("<tr><td>ProdID</td><td>"+item.customer_code+"</td></tr>");
                $("#example3").append("<tr><td>LotID</td><td>"+item.lot_id+"</td></tr>");
                $("#example3").append("<tr><td>WaferNO</td><td>"+item.wafer_no+"</td></tr>");
                $("#example3").append("<tr><td>CPYield</td><td>"+item.yield+"</td></tr>");
                $("#example3").append("<tr><td>GoodDieCount</td><td>"+item.pass_die+"</td></tr>");
                $("#example3").append("<tr><td>BadDieCount</td><td>"+item.fail_die+"</td></tr>");
                $("#example3").append("<tr><td>StartTestTime</td><td>"+item.start_time+"</td></tr>");
                $("#example3").append("<tr><td>EndTestTime</td><td>"+item.end_time+"</td></tr>");

			})

		});
	}
});

