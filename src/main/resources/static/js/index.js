var dataSet = [
		[ "Tiger Nixon", "System Architect", "Edinburgh", "5421", "2011/04/25",
				"$320,800" ],
		[ "Garrett Winters", "Accountant", "Tokyo", "8422", "2011/07/25",
				"$170,750" ],
		[ "Ashton Cox", "Junior Technical Author", "San Francisco", "1562",
				"2009/01/12", "$86,000" ],
		[ "Cedric Kelly", "Senior Javascript Developer", "Edinburgh", "6224",
				"2012/03/29", "$433,060" ],
		[ "Airi Satou", "Accountant", "Tokyo", "5407", "2008/11/28", "$162,700" ],
		[ "Brielle Williamson", "Integration Specialist", "New York", "4804",
				"2012/12/02", "$372,000" ],
		[ "Herrod Chandler", "Sales Assistant", "San Francisco", "9608",
				"2012/08/06", "$137,500" ],
		[ "Rhona Davidson", "Integration Specialist", "Tokyo", "6200",
				"2010/10/14", "$327,900" ],
		[ "Colleen Hurst", "Javascript Developer", "San Francisco", "2360",
				"2009/09/15", "$205,500" ],
		[ "Sonya Frost", "Software Engineer", "Edinburgh", "1667",
				"2008/12/13", "$103,600" ],
		[ "Jena Gaines", "Office Manager", "London", "3814", "2008/12/19",
				"$90,560" ],
		[ "Quinn Flynn", "Support Lead", "Edinburgh", "9497", "2013/03/03",
				"$342,000" ],
		[ "Charde Marshall", "Regional Director", "San Francisco", "6741",
				"2008/10/16", "$470,600" ],
		[ "Haley Kennedy", "Senior Marketing Designer", "London", "3597",
				"2012/12/18", "$313,500" ],
		[ "Tatyana Fitzpatrick", "Regional Director", "London", "1965",
				"2010/03/17", "$385,750" ],
		[ "Michael Silva", "Marketing Designer", "London", "1581",
				"2012/11/27", "$198,500" ],
		[ "Paul Byrd", "Chief Financial Officer (CFO)", "New York", "3059",
				"2010/06/09", "$725,000" ],
		[ "Gloria Little", "Systems Administrator", "New York", "1721",
				"2009/04/10", "$237,500" ],
		[ "Bradley Greer", "Software Engineer", "London", "2558", "2012/10/13",
				"$132,000" ],
		[ "Dai Rios", "Personnel Lead", "Edinburgh", "2290", "2012/09/26",
				"$217,500" ],
		[ "Jenette Caldwell", "Development Lead", "New York", "1937",
				"2011/09/03", "$345,000" ],
		[ "Yuri Berry", "Chief Marketing Officer (CMO)", "New York", "6154",
				"2009/06/25", "$675,000" ],
		[ "Caesar Vance", "Pre-Sales Support", "New York", "8330",
				"2011/12/12", "$106,450" ],
		[ "Doris Wilder", "Sales Assistant", "Sidney", "3023", "2010/09/20",
				"$85,600" ],
		[ "Angelica Ramos", "Chief Executive Officer (CEO)", "London", "5797",
				"2009/10/09", "$1,200,000" ],
		[ "Gavin Joyce", "Developer", "Edinburgh", "8822", "2010/12/22",
				"$92,575" ],
		[ "Jennifer Chang", "Regional Director", "Singapore", "9239",
				"2010/11/14", "$357,650" ],
		[ "Brenden Wagner", "Software Engineer", "San Francisco", "1314",
				"2011/06/07", "$206,850" ],
		[ "Fiona Green", "Chief Operating Officer (COO)", "San Francisco",
				"2947", "2010/03/11", "$850,000" ],
		[ "Shou Itou", "Regional Marketing", "Tokyo", "8899", "2011/08/14",
				"$163,000" ],
		[ "Michelle House", "Integration Specialist", "Sidney", "2769",
				"2011/06/02", "$95,400" ],
		[ "Suki Burks", "Developer", "London", "6832", "2009/10/22", "$114,500" ],
		[ "Prescott Bartlett", "Technical Author", "London", "3606",
				"2011/05/07", "$145,000" ],
		[ "Gavin Cortez", "Team Leader", "San Francisco", "2860", "2008/10/26",
				"$235,500" ],
		[ "Martena Mccray", "Post-Sales support", "Edinburgh", "8240",
				"2011/03/09", "$324,050" ],
		[ "Unity Butler", "Marketing Designer", "San Francisco", "5384", "2009/12/09", "$85,675" ]
];
 


function initnewAttributeTable() {
var table = $('#newAttributeTable').dataTable({
						"paging" : false, // 翻页
						// "bPaginate" : true, //显示分页器
						// "iDisplayLength":28,
						"ordering" : false,
						"searching" : false, // 搜索框
						// "bLengthChange":true,//改变每行显示数据数量
						// "aLengthMenu": [[10, 30, 50, -1], [10, 30, 50,
						// "All"]],
						"bInfo" : false, // 页脚
						'bStateSave' : true,
						"bAutoWidth" : false,
						"bSort" : true,
						"processing" : false,
						"serverSide" : false,
						"sServerMethod" : "get", // post
						"bDestroy" : true,
						"ajax" : {
							"url" : encodeURI("${pageContext.request.contextPath}/ajax/certificate/getCertificateTypeList"),
							// "dataSrc": "data",//默认为data
							"type" : "get",
							"error" : function() {
							}
    },
"columns": [{
									"data" : null,
									"title" : "序号",
									"sWidth" : "30px",
									"sClass" : "text-tables-center",
									"createdCell" : function(nTd, sData, oData,
											iRow, iCol) {
										var startnum = this.api().page()
												* (this.api().page.info().length);
										$(nTd).html(iRow + 1 + startnum); // 分页行号累加：$(nTd).html(iRow+1);
									}
								}, {
									"data" : "typeName",
									"title" : "证照属性设置"
								}, {
									"data" : null,
									"title" : "操作"
								} ],
						"columnDefs": [
      {
									"targets" : [ 1 ],
									"data" : "typeName",
									"render" : function(data, type, row) {
										// isLockStatus:true：显示有锁 false:显示没有锁
										if (row.isLockStatus == true) {
											return ""
													+ row.typeName
													+ "&nbsp;&nbsp;<i class='fa fa-lock l-fa' style='cursor: pointer'></i>";
										} else {
											return row.typeName;
}
        }
      },
{
									"targets" : [ 2 ],
									"data" : "id",
									"sWidth" : "60px",
									"sClass" : "text-tables-center",
									"render" : function(data, type, row) {
										var id = row.id == undefined ? -1
												: row.id;// -1说明是新增，其余表示为编辑
										if (row.isLockStatus == true) {
											return "-";
										} else {
											return "<i class='fa fa-pencil l-fa edit-btn' style='cursor: pointer' id=\'"
													+ id
													+ "\'></i>&nbsp;&nbsp;<i class='fa fa-trash-o m-fa del-btn' style='cursor: pointer' id=\'"
													+ id + "\'></i>";
										}
									}
								}, {
									sDefaultContent : '',
									aTargets : [ '_all' ]
								} ]
					});

	// 新增一行表格
	$("#newAttribute").unbind('click').click(function() {
		if ($("#newAttributeTable tbody tr:last td i").eq(0).attr('id') == -1) {
			return;
		}
		$('#newAttributeTable').dataTable().fnAddData([]);
		var nTrsLength = $("#newAttributeTable tbody tr").length;
		var nTrs = $("#newAttributeTable tbody tr").eq(nTrsLength - 1);
		var nIs = nTrs.find("td").eq(2).find("i").eq(0)
		nIs.click();
	});
	// 点击编辑图标出现编辑框
	$("#newAttributeTable tbody")
			.unbind('click')
			.on(
					"click",
					".edit-btn",
					function() {
						var tds = $(this).parents("tr").children();
						var i;
						$
								.each(
										tds,
										function(i, val) {
											var jqob = $(val);
											var txt = jqob.text();
											if (i == 1) {
												var put = $("<input type='text' class='form-control' style='width:100%'>");
												put.val(txt);
												jqob.html(put);
											}
										});
						$(this).toggleClass("edit-btn fa-pencil");
						$(this).toggleClass("save-btn fa-save");
					});
	// 保存按钮
	$("#newAttributeTable tbody").on("click", ".save-btn", function() {
		var id = $(this).attr("id");
		var tds = $(this).parents("tr").children();
		var sTds = tds.length;
		var lisval = [];
		for (var i = 0; i < sTds; i++) {
			if (i < sTds - 1)
				lisval.push(tds.eq(i).children("input").val());
			else
				lisval.push(tds.eq(i).children("input").val())
		}
		if (i == sTds - 1)
			lisval.push();
		if (lisval[1] == "") {
			toNotification("警告！", "证照属性设置不能为空！", "warning");
			return false;
		} else if (lisval[1].length > 10) {
			toNotification("警告！", "证照属性设置不能超过十个字符！", "warning");
			return false;
		} else {
			$.each(tds, function(i, val) {
				var jqob = $(val);
				// 把input变为字符串
				if (!jqob.has('i').length) {
					var txt = jqob.children("input").val();
					jqob.html(txt);
				}
			});
			$(this).toggleClass("edit-btn fa-pencil");
			$(this).toggleClass("save-btn fa-save");
			// 保存数据
			SaveData(lisval[1], id);
		}
	});

	// 证照属性设置保存信息
	function SaveData(msg, id) {
		var data = {
			id : id, // -1表示是新增
			typeName : msg
		}
		var url = encodeURI("${pageContext.request.contextPath}/ajax/certificate/saveCertificateType");
		actionRequest(data, url, function(data) {
			if (data.successed) {
				toNotification("提示！", "操作成功！", "success");
				//刷新表格
				initnewAttributeTable();
			} else {
				toNotification("操作失败！", data.message, "error");
				//刷新表格
				initnewAttributeTable();
			}
		});
	}

	//证照属性设置删除
	$("#newAttributeTable tbody").on("click", ".del-btn", function() {
		var id = $(this).attr("id");
		if (id == -1) {
			var trs = $(this).parents("tr");
			initnewAttributeTable();
			return false;
		} else {
			var tds = $(this).parents("tr").children();
			var data = {
				id : id
			};
			var url = "/ajax/certificate/deleteCertificateType";
			//执行删除
			toConfirm(data, url, function(data) {
				if (data.successed) {
					swal({
						title : "删除成功",
						text : "<small>1秒后自动关闭<small>",
						type : "success",
						html : true,
						timer : 1000
					});
					initnewAttributeTable();
				} else {
					swal("删除失败!", data.message, "error");
				}
			});
		}
	});
}
//获得标识
function getActionId() {
	return new Date().getTime();
}

$(document).ready(function() {
	initnewAttributeTable();
});
