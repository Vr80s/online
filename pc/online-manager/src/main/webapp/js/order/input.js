var orderinputTable;
var orderinputForm;
$(function() {

	createDatetimePicker($('#create_time_start'));
	createDatetimePicker($('#create_time_end'));

	var objData = [{
		"title" : "序号",
		"width" : "6%",
		"class" : "center",
		"sortable" : false,
		"data" : "id"
	},{
		"title" : "课程名称",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'course_name'
	},{
		"title" : "购买者(登录账号)",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'login_name'
	},{
		"title" : "实际支付（元）",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'actual_pay'
	}
	
	,{
		"title" : "创建时间",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'create_time'
	},{
		"title" : "创建人",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'create_person'
	} ];
	orderinputTable = initTables("orderinputTable", basePath + "/order/input/find", objData, true, true, 1);
	orderinputForm = $("#orderinputForm").validate({});
});

function search() {
	searchButton(orderinputTable);
}

function add(obj){
	$("#orderinputForm").resetForm();
	openDialog("orderInputDialog","dialogOrderInputDiv","线下订单录入",350,350,true,"提交",function(){
		if($("#orderinputForm").valid()){
			mask();
			$("#orderinputForm").attr("action",basePath+"/order/input/add");
			$("#orderinputForm").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#orderInputDialog").dialog("close");
					layer.msg('操作成功！');
					freshTable(orderinputTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});
		}
	});
}

function importOrders (obj){
    debugger;
	  $("#orderImportForm").resetForm();
	  openDialog("orderImportDialog","dialogOrderImportDiv","线下订单导入",350,200,true,"提交",function(){
		  var file = $('#excelFile').val();
		  if(file.substring(file.lastIndexOf('.') + 1) != 'xlsx'){
			  alert('请上传excel文件');
			  return;
		  }
		  if($("#orderImportForm").valid()){
			  mask();
			  $("#orderImportForm").attr("action",basePath+"/order/input/importOrder");
			  $("#orderImportForm").ajaxSubmit(function(data){
			  	console.info(data);
				  data = $.parseJSON(data);
				  unmask();
				  debugger;
				  if(data.success){
					  $("#orderImportDialog").dialog("close");
					  layer.msg('操作成功！');
					  freshTable(orderinputTable);
				  }else{
					  layer.msg(data.errorMessage);
				  }
			  });
		  }
	  });
}