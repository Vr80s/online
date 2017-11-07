var activityRuleTable;
var activityRuleForm;
var activityRuleFormEdit;
var searchJson = new Array();
$(function() {
    createDatePicker($(".profile-info-row .datetime-picker"));

    $('#add_startTime').datetimepicker({
    	
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm',
    	onSelect: function( startDate ) {
    		var $startDate = $( "#add_startTime" );
    		var $endDate = $('#add_endTime');
    		$endDate.datetimepicker( "option", "minDate", startDate );
    	}
    });

    $('#add_endTime').datetimepicker({
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm',
    	onSelect: function( endDate ) {
    		var $startDate = $( "#add_startTime" );
    		var $endDate = $('#add_endTime');
    	}
    });

    $('#add_subjectIds').selectpicker({
		  size: 10,
		  width:155,
		  noneSelectedText: '无',
		  actionsBox:true,//在下拉选项添加选中所有和取消选中的按钮
	});

    $('#add_microCourseIds').selectpicker({
    	size: 10,
    	width:155,
    	noneSelectedText: '无',
    	actionsBox:true,//在下拉选项添加选中所有和取消选中的按钮
    });

    $('#add_subjectIds').on('change',function(){
    	var subjectIds = $(this).val();
    	var strSub = "";
    	if(subjectIds != null && subjectIds.length > 0){
    		strSub = subjectIds.join(",");
    	}

    	ajaxRequest(basePath+"/activity/activityRule/getMicroCourseList",{"subjectIds":strSub},function(data){
    		//layer.msg(data.resultObject);
    		if(data.success){
    			var microCourseIdsTemp = $('#add_microCourseIds').val();//临时保存微课ID
    			
    			//动态把获取到的数据添加到课程下拉框中
    			$("#add_microCourseIds").empty();//移除掉所有选项
                var itemsMicroCourse = data.resultObject;

                for(var i = 0;i<itemsMicroCourse.length;i++){
                	$("#add_microCourseIds").append("<option value=\""+itemsMicroCourse[i].id+"\">"+itemsMicroCourse[i].courseName+"</option>");
                }

                $('#add_microCourseIds').selectpicker('render');
                $('#add_microCourseIds').selectpicker('refresh');
                $('#add_microCourseIds').selectpicker('val',microCourseIdsTemp!=null?microCourseIdsTemp:"");
    		}else{
    			alertInfo(data.errorMessage);
    		}
    	});
    });
    
    $('#update_startTime').datetimepicker({
    	
    	showSecond: true,
    	changeMonth: true,
    	changeYear: true,
    	dateFormat:'yy-mm-dd',
    	monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
    	timeFormat: 'HH:mm',
    	onSelect: function( startDate ) {
    		var $startDate = $( "#update_startTime" );
    		var $endDate = $('#update_endTime');
    		$endDate.datetimepicker( "option", "minDate", startDate );
    	}
    });
    
    $('#update_endTime').datetimepicker({
    	showSecond: true,
    	changeMonth: true,
    	changeYear: true,
    	dateFormat:'yy-mm-dd',
    	monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
    	timeFormat: 'HH:mm',
    	onSelect: function( endDate ) {
    	}
    });
    
    $('#update_subjectIds').selectpicker({
    	size: 10,
    	width:155,
    	noneSelectedText: '无',
    	actionsBox:true,//在下拉选项添加选中所有和取消选中的按钮
    });
    
    $('#update_microCourseIds').selectpicker({
    	size: 10,
    	width:155,
    	noneSelectedText: '无',
    	actionsBox:true,//在下拉选项添加选中所有和取消选中的按钮
    });
    
    $('#update_subjectIds').on('change',function(){
    	var subjectIds = $(this).val();
    	var strSub = "";
    	if(subjectIds != null && subjectIds.length > 0){
    		strSub = subjectIds.join(",");
    	}
    	
    	ajaxRequest(basePath+"/activity/activityRule/getMicroCourseList",{"subjectIds":strSub},function(data){
    		//layer.msg(data.resultObject);
    		if(data.success){
    			var microCourseIdsTemp = $('#update_microCourseIds').val();//临时保存微课ID
    			//动态把获取到的数据添加到课程下拉框中
    			$("#update_microCourseIds").empty();//移除掉所有选项
    			var itemsMicroCourse = data.resultObject;
    			
    			for(var i = 0;i<itemsMicroCourse.length;i++){
    				$("#update_microCourseIds").append("<option value=\""+itemsMicroCourse[i].id+"\">"+itemsMicroCourse[i].courseName+"</option>");
    			}
    			
    			$('#update_microCourseIds').selectpicker('render');
    			$('#update_microCourseIds').selectpicker('refresh');
    			$('#update_microCourseIds').selectpicker('val',microCourseIdsTemp!=null?microCourseIdsTemp:"");
    		}else{
    			alertInfo(data.errorMessage);
    		}
    	});
    });
    loadActivityRuleList();

});

//列表展示
function loadActivityRuleList(){
    var dataFields = [
        {title: '序号', "class": "center", "width": "5%","data": 'id', "sortable": false},
        {title: '活动名称', "class": "center","width": "10%","data": 'name', "sortable": false},
        {title: '规则内容', "class": "center", "width": "15%","data": 'ruleContent', "sortable": false,"mRender":function(data,display,row){
        	return data;
        }},
        {title: '活动落地页', "class": "center","width": "15%","data": 'url', "sortable": false,"mRender":function(data,display,row){
        	return "<div style='white-space:normal;'><a href='"+data+"' target='blank'>"+data+"</a></div>";
        }},
        {title: '课程范围', "class": "center", "width": "10%","data": 'courseNames', "sortable": false},
        {title: '创建人', "class": "center", "width": "8%","data": 'createPersonName', "sortable": false},
        {title: '创建时间', "class": "center", "width": "8%","data": 'createTime', "sortable": false},
        {title: '规则生效起止时间', "class": "center", "width": "14%","data": 'ruleStartTime', "sortable": false,"mRender":function(data,display,row){
        		return row.startTime + " 至 " + row.endTime;
        }},
        {title:"操作","class": "center","width":"8%","data":"id","sortable": false,"mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                			 '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>';
                			 if(!row.isEnd){//如果已经结束就不能修改
                				 buttons += '<a class="blue" href="javascript:void(-1);" title="修改" onclick="updateActivityRule(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
                			 }else{
                				 buttons += '<a class="gray" href="javascript:void(-1);" title="修改" style="cursor:not-allowed;"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
                			 }
				buttons += '</div>';
                return buttons;
        }}
    ];

    activityRuleTable = initTables("activityRuleTable", basePath + "/activity/activityRule/findActivityRuleList", dataFields, true, true, 1,null,searchJson,function(data){
    });
    
    jQuery.validator.addMethod("greaterThanFour", function(value, element) {
    	var courseIds = $(element).val();
    	
    	if(courseIds.length >= 4){
    		return true;
    	}
    	
    	return false;
    }, "课程范围必须大于4个！");

    jQuery.validator.addMethod("greaterThanReach", function(value, element) {
    	var reachMoney = Number($(element).parent().parent().find(".reachMoney").eq(0).val());
    	var minusMoney = Number($(element).parent().parent().find(".minusMoney").eq(0).val());
    	if(reachMoney > minusMoney){
    		return true;
    	}
    	return false;
    }, "必须小于满减基数！");
    
    jQuery.validator.addMethod("notRepeat", function(value, element) {

    	var reachMoneys = $(element).parent().parent().parent().parent().find(".reachMoney");
    	var cnt = 0;//计算相等次数 如果大于1就返回false
    	for(var i = 0;i<reachMoneys.length;i++){
    		if(Number(value) == Number(reachMoneys.eq(i).val())){
    			cnt ++;
    		}
    	}

    	if(cnt > 1){
    		return false;
    	}

    	return true;
    }, "满减价格不能重复！");
    
    jQuery.validator.addMethod("greaterThanStrat", function(value, element) {
    	
    	var startTime = $(element).parent().parent().find("[name='startTime']").eq(0).val();
    	var endTime = $(element).parent().parent().find("[name='endTime']").eq(0).val();
    	
    	if(dateDiff(startTime,endTime)<=0){
    		return false;
    	}
    	
    	return true;
    }, "结束时间必须大于开始时间！");
    

    activityRuleForm = $("#addActivityRule-form").validate({
        messages: {
        	name: {
				required:"请输入活动名称！",
            },
            url: {
				required:"请输入活动落地页地址！"
			},
			subjectIds: {
				required:"请选择学科！",
	        },
	        microCourseIds: {
	        	required:"请选择课程！",
	        },
	        startTime: {
	        	required:"请选择规则生效时间！",
	        },
	        endTime: {
	        	required:"请选择规则结束时间！",
	        }
        }
    });

    activityRuleFormEdit = $("#updateActivityRule-form").validate({
    	messages: {
        	name: {
				required:"请输入活动名称！",
            },
            url: {
				required:"请输入活动落地页地址！"
			},
			subjectIds: {
				required:"请选择学科！",
	        },
	        microCourseIds: {
	        	required:"请选择课程！",
	        },
	        startTime: {
	        	required:"请选择规则生效时间！",
	        },
	        endTime: {
	        	required:"请选择规则结束时间！",
	        }
        }
    });

}

 //条件搜索
 function search(){
	 
     searchButton(activityRuleTable,searchJson);
}

//新增框
 $(".add_bx").click(function(){
 	activityRuleForm.resetForm();
 	//清除table
	$("#ruleTable tr:gt(0)").remove();//把所有非第一个行的规则删除
	$('#add_subjectIds').selectpicker('refresh');//把学科下拉框清除 
	$("#add_microCourseIds").empty();
	$('#add_microCourseIds').selectpicker('render');
    $('#add_microCourseIds').selectpicker('refresh');

 	var dialog = openDialog("addActivityRuleDialog","dialogAddActivityRuleDiv","新增满减优惠规则",700,500,true,"确定",function(){
 		if($("#addActivityRule-form").valid()){
 			//把所有新增的规则都改名字
 			$("#ruleTable .reachMoney").each(function(){
 				$(this).attr("name","reachMoney");
 			});
 			$("#ruleTable .minusMoney").each(function(){
 				$(this).attr("name","minusMoney");
 			});
 			 mask();
 			 $("#addActivityRule-form").attr("action", basePath+"/activity/activityRule/addActivityRule");
 	            $("#addActivityRule-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                if(data.success){
 	                    $("#addActivityRuleDialog").dialog("close");
 	                    layer.msg(data.resultObject);
 	                    freshTable(activityRuleTable);
 	                }else{
 	                	alertInfo(data.errorMessage);
 	               }
 	         });
 		}
 	});
 });

function showDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var row = activityRuleTable.fnGetData(oo); // get datarow

	$("#show_name").html(row.name);
	$("#show_url").html("<a href='"+row.url+"' target='blank'>"+row.url+"</a>");
	$("#show_courseNames").html(row.courseNames.replace(new RegExp(/\,/g),"<br/>"));//所有课程
	$("#show_time").html(row.startTime+" 至 "+row.endTime);
	$("#show_ruleContent").html(row.ruleContent !=null ?row.ruleContent.replace(new RegExp(/\;/g),"<br/>"):'');
	$("#show_createPersonName").html(row.createPersonName);
	$("#show_createTime").html(row.createTime); 

	var dialog = openDialog("showActivityRuleDialog","dialogShowActivityRuleDiv","查看满减优惠规则",580,500,false,"",null);
}

function updateActivityRule(obj){
	activityRuleFormEdit.resetForm();
	$("#updateActivityRule-form input").removeAttr("disabled"); 
	$("#updateActivityRule-form input").val("");
	$("#update_subjectIds option").removeAttr("disabled","disabled");
	$("#update_microCourseIds option").removeAttr("disabled","disabled");
	$("#update_rule").show();
	
	var oo = $(obj).parent().parent().parent();
	var row = activityRuleTable.fnGetData(oo); // get datarow
	
	$("#update_id").val(row.id);
	$("#update_name").val(row.name);
	$("#update_url").val(row.url);
	$("#isEdit").val(row.isEdit);//如果不能修改 那么只能修改结束时间  用于后台判断是否更新其他值
	
	if(!row.isEdit){//如果不可以修改  row.isEdit = true 是可以修改 
		$("#update_subjectIds option").attr("disabled","disabled");
	}
	
	//设置学科ID 然后 刷新控件
	$("#update_subjectIds").selectpicker('val',row.subjectIds.split(","));
	//$("#update_subjectIds").trigger('change');

	syncRequest(basePath+"/activity/activityRule/getMicroCourseList",{"subjectIds":row.subjectIds},function(data){
		if(data.success){
			var microCourseIdsTemp = $('#update_microCourseIds').val();//临时保存微课ID
			//动态把获取到的数据添加到课程下拉框中
			$("#update_microCourseIds").empty();//移除掉所有选项
			var itemsMicroCourse = data.resultObject;
			
			for(var i = 0;i<itemsMicroCourse.length;i++){
				$("#update_microCourseIds").append("<option value=\""+itemsMicroCourse[i].id+"\">"+itemsMicroCourse[i].courseName+"</option>");
			}
			
			$('#update_microCourseIds').selectpicker('render');
			$('#update_microCourseIds').selectpicker('refresh');
			if(!row.isEdit){//如果不可以修改  row.isEdit = true 是可以修改 
				$("#update_microCourseIds option").attr("disabled","disabled");
			}
			$('#update_microCourseIds').selectpicker('val',microCourseIdsTemp!=null?microCourseIdsTemp:"");
		}else{
			alertInfo(data.errorMessage);
		}
	});

	//再设置课程ID
	$("#update_microCourseIds").selectpicker('val',row.courseIds.split(","))
	
	$("#update_startTime").val(row.startTime);
	
	
	$('#update_endTime').datetimepicker( "option", "minDate", $("#update_startTime").val());
	$("#update_endTime").val(row.endTime);
	
	//清除table
	$("#update_ruleTable tr:gt(0)").remove();//把所有非第一个行的规则删除

	var ruleMoneys = row.ruleMoneys.split(",");
	
	for(var i = 0;i<ruleMoneys.length;i++){//设置规则
		if(i > 0 ){
			$("#update_rule").trigger('click');
		}

		var reachMoneys = ruleMoneys[i].split("|")[0];
		var minusMoneys = ruleMoneys[i].split("|")[1];

		$("#update_ruleTable .reachMoney:last").val(reachMoneys);
		$("#update_ruleTable .minusMoney:last").val(minusMoneys);
	}
	
	if(!row.isEdit){//如果不可以修改  row.isEdit = true 是可以修改 
		$("#updateActivityRule-form input").not("#update_endTime,#update_id,#isEdit").attr("disabled","disabled");
		$('.update_rule_a').removeAttr('onclick');//去掉a标签中的onclick事件 blue update_rule_a
		$("#update_rule").hide();		
		$("#update_endTime").val(row.endTime);
		//$('#update_endTime').removeAttr('disabled');
	}

 	var dialog = openDialog("updateActivityRuleDialog","dialogUpdateActivityRuleDiv","修改满减优惠规则",700,500,true,"确定",function(){
 		$("#update_subjectIds option").removeAttr("disabled");
		$("#update_microCourseIds option").removeAttr("disabled");
 		if($("#updateActivityRule-form").valid()){
 			$("#updateActivityRule-form input").removeAttr("disabled");
 			//把所有新增的规则都改名字
 			$("#update_ruleTable .reachMoney").each(function(){
 				$(this).attr("name","reachMoney");
 			});
 			$("#update_ruleTable .minusMoney").each(function(){
 				$(this).attr("name","minusMoney");
 			});
 			 mask();
 			 $("#updateActivityRule-form").attr("action", basePath+"/activity/activityRule/updateActivityRuleById");
 	            $("#updateActivityRule-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                if(data.success){
 	                    $("#updateActivityRuleDialog").dialog("close");
 	                    layer.msg(data.resultObject);
 	                    freshTable(activityRuleTable);
 	                }else{
 	                	alertInfo(data.errorMessage);
 	               }
 	         });
 		}else{
 			$("#update_subjectIds option").attr("disabled","disabled");
 			$("#update_microCourseIds option").attr("disabled","disabled");
 		}
 	});
 	
}

var cntId = 0;//用于处理提示位置总在第一个
$("#add_rule").on('click',function(){
	cntId ++;
	if($("#ruleTable tr").length >= 5){
		layer.msg("最多只能新增5条规则！");
		return false;
	}
	var temp =  "<tr style=\"height: 62px;\">\n" +
				"<td>\n" +
				"			<div class=\"col-sm-1\" style=\"margin-top: 7px;\">\n" +
				"					满\n" +
				"		  </div>\n" +
				"			<div class=\"col-sm-3\">\n" +
				"							<input type=\"text\" name=\"reachMoney"+cntId+"\" id=\"reachMoney"+cntId+"\"  class=\"reachMoney col-sm-12 {required:true,number:true,range:[0.00,999999.99],greaterThanReach:false,notRepeat:true}\">\n" +
				"				 </div>\n" +
				"			<div class=\"col-sm-1\" style=\"width: 65px;margin-left: -11px;margin-top: 7px;margin-right: -14px;\">\n" +
				"			元\n" +
				"			&nbsp;\n" +
				"			减\n" +
				"				 </div>\n" +
				"			<div class=\"col-sm-3\">\n" +
				"							<input type=\"text\" name=\"minusMoney"+cntId+"\" id=\"minusMoney"+cntId+"\"  class=\"minusMoney col-sm-12 {required:true,number:true,range:[0.00,999999.99],greaterThanReach:true}\">\n" +
				"				 </div>\n" +
				"			<div class=\"col-sm-2\" style=\"margin-top: 7px;margin-left: -12px;\">\n" +
				"					元\n" +
				"					<a class=\"blue\" href=\"javascript:void(-1);\" title=\"删除\" onclick=\"delTr(this)\"><i class=\"glyphicon glyphicon-trash\"></i></a>\n" +
				"		 </div>\n" +
				"</td>" +
				"</tr>";

	$("#ruleTable").append(temp);
});

$("#update_rule").on('click',function(){
	cntId ++;
	if($("#update_ruleTable tr").length >= 5){
		layer.msg("最多只能新增5条规则！");
		return false;
	}
	var temp =  "<tr style=\"height: 62px;\">\n" +
				"<td>\n" +
				"			<div class=\"col-sm-1\" style=\"margin-top: 7px;\">\n" +
				"					满\n" +
				"		  </div>\n" +
				"			<div class=\"col-sm-3\">\n" +
				"							<input type=\"text\" name=\"reachMoney"+cntId+"\" id=\"reachMoney"+cntId+"\"  class=\"reachMoney col-sm-12 {required:true,number:true,range:[0.00,999999.99],greaterThanReach:false,notRepeat:true}\">\n" +
				"				 </div>\n" +
				"			<div class=\"col-sm-1\" style=\"width: 65px;margin-left: -11px;margin-top: 7px;margin-right: -14px;\">\n" +
				"			元\n" +
				"			&nbsp;\n" +
				"			减\n" +
				"				 </div>\n" +
				"			<div class=\"col-sm-3\">\n" +
				"							<input type=\"text\" name=\"minusMoney"+cntId+"\" id=\"minusMoney"+cntId+"\"  class=\"minusMoney col-sm-12 {required:true,number:true,range:[0.00,999999.99],greaterThanReach:true}\">\n" +
				"				 </div>\n" +
				"			<div class=\"col-sm-2\" style=\"margin-top: 7px;margin-left: -12px;\">\n" +
				"					元\n" +
				"					<a class=\"blue update_rule_a\" href=\"javascript:void(-1);\" title=\"删除\" onclick=\"delTr(this)\"><i class=\"glyphicon glyphicon-trash\"></i></a>\n" +
				"		 </div>\n" +
				"</td>" +
				"</tr>";
	
	$("#update_ruleTable").append(temp);
});

//移除当前行
function delTr(obj){
	$(obj).parent().parent().parent().remove();
}

$("[name='subjectIds'").on('change',function(){
	$(this).valid();
});

$("[name='microCourseIds'").on('change',function(){
	$(this).valid();
});

//获取当前时间
function show(){
	   var mydate = new Date();
	   var str = "" + mydate.getFullYear() + "-";
	   str += (mydate.getMonth()+1) + "-";
	   str += mydate.getDate() ;
	   return str;
	  }

//字符串转成Time(dateDiff)所需方法
function stringToTime(string) {
   var f = string.split(' ', 2);
   var d = (f[0] ? f[0] : '').split('-', 3);
   var t = (f[1] ? f[1] : '').split(':', 3);
   return (new Date(
  parseInt(d[0], 10) || null,
  (parseInt(d[1], 10) || 1) - 1,
parseInt(d[2], 10) || null,
parseInt(t[0], 10) || null,
parseInt(t[1], 10) || null,
parseInt(t[2], 10) || null
)).getTime();
}

function dateDiff(date1, date2) {
    var type1 = typeof date1, type2 = typeof date2;
    if (type1 == 'string')
        date1 = stringToTime(date1);
    else if (date1.getTime)
        date1 = date1.getTime();
    if (type2 == 'string')
        date2 = stringToTime(date2);
    else if (date2.getTime)
        date2 = date2.getTime();

    return (date2 - date1) / (1000 * 60 * 60 * 24); //结果是秒
}
