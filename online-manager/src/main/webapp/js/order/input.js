var orderinputTable;
var orderinputForm;
$(function() {

	createDatetimePicker($('#create_time_start'));
	createDatetimePicker($('#create_time_end'));
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [
		{ "title": checkbox, "class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    }},{
		"title" : "课程名称",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'course_name'
	},{
		"title" : "购买者(登录帐号)",
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
        },{
            "title" : "有效期",
            "width" : "12%",
            "class" : "center",
            "sortable" : false,
            "data" : 'validity',
            "mRender":function(data,display,row){
                if(data == '9999-01-01 00:00:00.0')return "永久";
                return data == null ? "永久":data.substring(0,19);
            }
        }
        ,{
            "title" : "类型",
            "width" : "12%",
            "class" : "center",
            "sortable" : false,
            "data" : 'order_from',
            "mRender":function(data){
                if(data == 5)return "线下订单";
                if(data == 6)return "工作人员";
                if(data == 0)return "赠送";
                return null;
            }
        }

        ,{
		"title" : "创建时间",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'create_time',
		"mRender":function(data){
			return data.substring(0,19);
		}
	},{
		"title" : "创建人",
		"width" : "12%",
		"class" : "center",
		"sortable" : false,
		"data" : 'create_person'
	} ];
	orderinputTable = initTables("orderinputTable", basePath + "/order/input/find", objData, true, true, 0);
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
$(".validity").click(function () {
	debugger
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");

    for(var i = 0;i<trs.size();i++){
        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
    	$("#ids").val(ids.join(","));
        var dialog = openDialog("UpdateValidityDialog","dialogUpdateValidityDiv","修改推荐值",350,200,true,"确定",function(){
            if($("#UpdateValidityFrom").valid()){
                mask();
                $("#UpdateValidityFrom").attr("action", basePath+"/order/input/setValidity");
                $("#UpdateValidityFrom").ajaxSubmit(function(data){
                    data = getJsonData(data);
                    unmask();
                    if(data.success){
                        $("#UpdateValidityDialog").dialog("close");
                        layer.msg(data.resultObject);
                        $("#days").val("");
                        freshTable(orderinputTable);
                    }else{
                        alertInfo(data.errorMessage);
                    }
                });
            }
        });
    }else{
        showDelDialog("","","请选择订单！","");
    }
});
function importOrders (obj){
    ;
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
				  ;
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