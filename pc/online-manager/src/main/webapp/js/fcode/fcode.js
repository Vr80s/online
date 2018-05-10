var fcodeTable;
var searchJson = new Array();
var validateForm;
$(function() {
	    $('#startTime').datetimepicker({
	    	
	    	showSecond: true,
			changeMonth: true,
			changeYear: true,
			dateFormat:'yy-mm-dd',
			monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
			timeFormat: 'HH:mm',
			minDate:new Date(),
	    	onSelect: function( startDate ) {
	    		var $startDate = $( "#startTime" );
	    		var $endDate = $('#endTime');
	    		$startDate.datetimepicker( "option", "minDate", new Date());
	    		$endDate.datetimepicker( "option", "minDate", startDate );
	    	}
	    });
	
	    $('#endTime').datetimepicker({
	    	showSecond: true,
			changeMonth: true,
			changeYear: true,
			dateFormat:'yy-mm-dd',
			monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
			timeFormat: 'HH:mm',
	    	onSelect: function( endDate ) {
	    		var $startDate = $( "#startTime" );
	    		var $endDate = $('#endTime');
	    		$startDate.datetimepicker( "option", "maxDate", endDate);
	    	}
	    });
	    
	 $('#edit_startTime').datetimepicker({
	    	
	    	showSecond: true,
			changeMonth: true,
			changeYear: true,
			dateFormat:'yy-mm-dd',
			monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
			timeFormat: 'HH:mm',
			minDate:new Date(),
	    	onSelect: function( startDate ) {
	    		var $startDate = $( "#edit_startTime" );
	    		var $endDate = $('#edit_endTime');
	    		$endDate.datetimepicker( "option", "minDate", startDate );
	    	}
	    });

    $('#edit_endTime').datetimepicker({
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm',
    	onSelect: function( endDate ) {
    		var $startDate = $( "#edit_startTime" );
    		var $endDate = $('#edit_endTime');
    		$startDate.datetimepicker( "option", "maxDate", endDate);
    	}
    });
    
	   loadFcodeList();
	   
	   $('#add_subjectIds').selectpicker({
			  size: 10,
			  width:185,
			  noneSelectedText: '选择学科',
			  actionsBox:true,//在下拉选项添加选中所有和取消选中的按钮
		});

	    $('#add_microCourseIds').selectpicker({
	    	size: 10,
	    	width:185,
	    	noneSelectedText: '选择课程',
	    	actionsBox:true,//在下拉选项添加选中所有和取消选中的按钮
	    });
	    
	    $('#edit_subjectIds').selectpicker({
			  size: 10,
			  width:185,
			  noneSelectedText: '选择学科',
			  actionsBox:true,//在下拉选项添加选中所有和取消选中的按钮
		});

	    $('#edit_microCourseIds').selectpicker({
	    	size: 10,
	    	width:185,
	    	noneSelectedText: '选择课程',
	    	actionsBox:true,//在下拉选项添加选中所有和取消选中的按钮
	    });
});
//列表展示
function loadFcodeList(){
	var dataFields = [
	                {title: 'ID', "class": "center", "width": "5%","height":"68px","data": 'lotNo', "visible": false},
	          		{title: '批次号', "class": "center", "width": "5%","height":"68px","data": 'lotNo', "sortable": false},
	          		{title: '兑换活动名称', "class": "center", "width": "5%","height":"68px","data": 'name', "sortable": false},
	          		{title: '兑换码数量', "class": "center", "width": "5%","height":"68px","data": 'fcodeSum', "sortable": false},
	          		{title: '已兑换', "class": "center", "width": "5%","height":"68px","data": 'clockFcodeSum', "sortable": false},
	          		{title: '已使用', "class": "center", "width": "5%","height":"68px","data": 'usedFcodeSum', "sortable": false},
	          		{title: '课程范围', "class": "center", "width": "5%","height":"68px","data": 'includeCourses', "sortable": false},
	          		{title: '开始时间', "class": "center", "width": "5%","height":"68px","data": 'startTime', "sortable": false},
	          		{title: '结束时间', "class": "center", "width": "5%","height":"68px","data": 'endTime', "sortable": false},
	          		{title: '创建人', "class": "center", "width": "5%","height":"68px","data": 'createPerson', "sortable": false},
	          		{title: '创建时间', "class": "center", "width": "5%","height":"68px","data": 'createTime', "sortable": false},
	          		{title: "自动生成", "class":"center","width":"8%","sortable":false,"data": 'auto', "visible": false},
	          		{title: "学科范围", "class":"center","width":"8%","sortable":false,"data": 'subjectNames', "visible": false},
	          		{title: "学科范围ID", "class":"center","width":"8%","sortable":false,"data": 'subjectIds', "visible": false},
	          		{title: '课程范围ID', "class": "center", "width": "5%","height":"68px","data": 'includeCourseIds', "visible": false},
	          		{title: '开始时间判断是否可修改标志', "class": "center", "width": "5%","height":"68px","data": 'startFlag', "visible": false},
	          		{title: '结束时间判断是否可修改标志', "class": "center", "width": "5%","height":"68px","data": 'endFlag', "visible": false},
	          		 {
	                    "sortable": false,"data":"id","class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
	                        var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
	                                     '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showFcodeDialog(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>' +
	                                     '<a class="blue" href="javascript:void(-1);" title="修改" onclick="editFcodeDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
	                        			 '<a class="blue" href="javascript:void(-1);" title="兑换码使用详情" onclick="toFcodeDetail(this)"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130" aria-hidden="true"></i></a></div>';
	                        return buttons;

	                    }
	                }];
	fcodeTable = initTables("fcodeTable", basePath + "/factivity/fcode/findFcodeList", dataFields, true, true, 0,null,searchJson,function(data){
    });
	
	validateForm = $("#fcode-form").validate({
		messages:{
			
			name:{
                required:"兑换活动名称不能为空"
            },
            lotNo:{
                required:"兑换活动批次不能为空"
            },
            startTime:{
                required:"开始时间不能为空"
            },
            endTime:{
                required:"结束时间不能为空"
            },
            subjectIds:{
                required:"学科范围不能为空"
            },
            includeCourseIds:{
                required:"课程范围不能为空"
            }
			
		}
	});
	
	editValidateForm = $("#editfcode-form").validate({
		messages:{
			
			name:{
                required:"兑换活动名称不能为空"
            },
            lotNo:{
                required:"兑换活动批次不能为空"
            },
            startTime:{
                required:"开始时间不能为空"
            },
            endTime:{
                required:"结束时间不能为空"
            },
            subjectIds:{
                required:"学科范围不能为空"
            },
            includeCourseIds:{
                required:"课程范围不能为空"
            }
			
		}
	});
}

$('#add_subjectIds').on('change',function(){
	var subjectIds = $(this).val();
	var strSub = "";
	if(subjectIds != null && subjectIds.length > 0){
		strSub = subjectIds.join(",");
		$('#spanCount1').text("已选择"+subjectIds.length+"个学科");
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

$('#add_microCourseIds').on('change',function(){
	var courseIds = $(this).val();
	if(courseIds!= null){
		$('#spanCount2').text("已选择"+courseIds.length+"门课程");
	}
	
});

$('#edit_subjectIds').on('change',function(){
	var subjectIds = $(this).val();
	var strSub = "";
	if(subjectIds != null && subjectIds.length > 0){
		strSub = subjectIds.join(",");
		$('#edit_spanCount1').text("已选择"+subjectIds.length+"个学科");
	}

	ajaxRequest(basePath+"/activity/activityRule/getMicroCourseList",{"subjectIds":strSub},function(data){
		//layer.msg(data.resultObject);
		if(data.success){
			var microCourseIdsTemp = $('#edit_microCourseIds').val();//临时保存微课ID
			
			//动态把获取到的数据添加到课程下拉框中
			$("#edit_microCourseIds").empty();//移除掉所有选项
            var itemsMicroCourse = data.resultObject;

            for(var i = 0;i<itemsMicroCourse.length;i++){
            	$("#edit_microCourseIds").append("<option value=\""+itemsMicroCourse[i].id+"\">"+itemsMicroCourse[i].courseName+"</option>");
            }

            $('#edit_microCourseIds').selectpicker('render');
            $('#edit_microCourseIds').selectpicker('refresh');
            $('#edit_microCourseIds').selectpicker('val',microCourseIdsTemp!=null?microCourseIdsTemp:"");
		}else{
			alertInfo(data.errorMessage);
		}
	});
});

$('#edit_microCourseIds').on('change',function(){
	var courseIds = $(this).val();
	if(courseIds!= null){
		$('#edit_spanCount2').text("已选择"+courseIds.length+"门课程");
	}
	
});

$(".add_bx").click(function(){
	validateForm.resetForm();
	$('#add_subjectIds').selectpicker('deselectAll');
	$('#add_microCourseIds').selectpicker('deselectAll');
	$("#fcode-form input").val("");
	$("#fcode-form input,select").attr("disabled",false); 
	$('#spanCount1').text("");
	$('#spanCount2').text("");
	var dialog = openDialog("addFcodeDialog","dialogaddFcodeDiv","新增兑换活动",680,600,true,"确定",function(){
		if($("#fcode-form").valid()){
			 mask();
 			 $("#fcode-form").attr("action", basePath+"/factivity/fcode/addFcodeRule");
	 		 $("#fcode-form").ajaxSubmit(function(data){
                 data = getJsonData(data);
		                unmask();
		                if(data.success){
		                    $("#addFcodeDialog").dialog("close");
		                    layer.msg(data.resultObject);
		                    freshTable(fcodeTable);
		                }else{
		                	alertInfo(data.errorMessage);
		               }
		         });
			}
	});
});

$("#auto").on('change',function(){
	$("#fcodeSum").val("");
});

$("#fcodeSum").on('change',function(){
	$("#auto").attr("checked", false);
});

function getFCodeEndTime(){
	if($("#startTime").val()!=null&&$("#endTime").val()!=null&&$("#startTime").val()!=""&&$("#endTime").val()!=""){
		$("#fCodeEndTime").val($("#startTime").val()+"至"+AddDays($("#endTime").val(),10));
	}
}
function edit_getFCodeEndTime(){
	if($("#edit_startTime").val()!=null&&$("#edit_endTime").val()!=null&&$("#edit_startTime").val()!=""&&$("#edit_endTime").val()!=""){
		$("#edit_fCodeEndTime").val($("#edit_startTime").val()+"至"+AddDays($("#edit_endTime").val(),10));
	}
}


function showFcodeDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var row = fcodeTable.fnGetData(oo); // get datarow

	$("#view_lotNo").val(row.lotNo);
	$("#view_name").val(row.name);
	$("#view_fcodeSum").val(row.fcodeSum);
	if(row.auto==1){
		$("#view_auto").attr("checked","checked");
	}
	$("#view_subjectIds").val(row.subjectNames);
	$("#view_microCourseIds").val(row.includeCourses);
	$("#view_startTime").val(row.startTime);
	$("#view_endTime").val(row.endTime);
	$("#view_fCodeEndTime").val(row.startTime+"至"+AddDays(row.endTime,10)); 

	var dialog = openDialog("viewFcodeDialog","dialogViewFcodeDiv","查看兑换活动",680,600,false,"",null);
}

function editFcodeDialog (obj){
	var oo = $(obj).parent().parent().parent();
	var row = fcodeTable.fnGetData(oo); // get datarow
	editValidateForm.resetForm();
	
	$('#edit_spanCount1').text("已选择"+row.subjectIds.split(",").length+"个学科");
	$('#edit_spanCount2').text("已选择"+row.includeCourseIds.split(",").length+"门课程");
	
	$("#editfcode-form input,select").attr("disabled",false); 
	$("#edit_fcodeId").val(row.id);
	$("#edit_lotNo").val(row.lotNo);
	$("#edit_name").val(row.name);
	$("#edit_fcodeSum").val(row.fcodeSum);
	if(row.auto==1){
		$("#edit_auto").attr("checked","checked");
	}
	$("#edit_subjectIds").selectpicker('val',row.subjectIds.split(","));
	
	
	
	syncRequest(basePath+"/activity/activityRule/getMicroCourseList",{"subjectIds":row.subjectIds},function(data){
		if(data.success){
			var microCourseIdsTemp = $('#edit_microCourseIds').val();//临时保存微课ID
			//动态把获取到的数据添加到课程下拉框中
			$("#edit_microCourseIds").empty();//移除掉所有选项
			var itemsMicroCourse = data.resultObject;
			
			for(var i = 0;i<itemsMicroCourse.length;i++){
				$("#edit_microCourseIds").append("<option value=\""+itemsMicroCourse[i].id+"\">"+itemsMicroCourse[i].courseName+"</option>");
			}
			
			$('#edit_microCourseIds').selectpicker('render');
			$('#edit_microCourseIds').selectpicker('refresh');
			/*if(!row.isEdit){//如果不可以修改  row.isEdit = true 是可以修改 
				$("#edit_microCourseIds option").attr("disabled","disabled");
			}*/
			$('#edit_microCourseIds').selectpicker('val',microCourseIdsTemp!=null?microCourseIdsTemp:"");
		}else{
			alertInfo(data.errorMessage);
		}
	});
	
	//再设置课程ID
	$("#edit_microCourseIds").selectpicker('val',row.includeCourseIds.split(","))
	$("#edit_startTime").val(row.startTime);
	$("#edit_endTime").val(row.endTime);
	$("#edit_fCodeEndTime").val(row.startTime+"至"+AddDays(row.endTime,10));
	if(row.startFlag&&!row.endFlag){
		$("#editfcode-form input,select").not("#edit_endTime,#edit_fCodeEndTime").attr("disabled","disabled");
	}
	if(row.endFlag){
		$("#editfcode-form input,select").attr("disabled","disabled");
	}
	var dialog = openDialog("editFcodeDialog","dialogEditFcodeDiv","修改兑换活动",680,600,true,"确定",function(){
		$("#editfcode-form input,select").attr("disabled",false); 
		if($("#editfcode-form").valid()){
			 mask();
 			 $("#editfcode-form").attr("action", basePath+"/factivity/fcode/updateFcodeRule");
	 		 $("#editfcode-form").ajaxSubmit(function(data){
		            	data = getJsonData(data);
		                unmask();
		                if(data .success){
		                    $("#editFcodeDialog").dialog("close");
		                    layer.msg(data.resultObject);
		                    freshTable(fcodeTable);
		                }else{
		                	alertInfo(data.errorMessage);
		               }
		         });
			}
	});
}

//日期加上天数后的新日期.
function AddDays(date,days){
var nd = new Date(date);
   nd = nd.valueOf();
   nd = nd + days * 24 * 60 * 60 * 1000;
   nd = new Date(nd);
   //alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" + nd.getDate() + "日");
var y = nd.getFullYear();
var m = nd.getMonth()+1;
var d = nd.getDate();
var H = nd.getHours();
var M = nd.getMinutes();
if(m <= 9) m = "0"+m;
if(d <= 9) d = "0"+d; 
if(H <= 9) H = "0"+H;
if(M <= 9) M = "0"+M; 
var cdate = y+"-"+m+"-"+d+" "+H+":"+M;
return cdate;
}

/**
* 日期格式化yyyy-MM-dd HH:mm:ss
*/
function FormatDate (strTime) {
	var year = strTime.getFullYear();
	var month = getFormat(strTime.getMonth()+1);
	var day = getFormat(strTime.getDate());
	var hours = getFormatHMS(strTime.getHours());
	var minutes = getFormatHMS(strTime.getMinutes());
	var seconds = getFormatHMS(strTime.getSeconds());
    return year+"-"+month+"-"+day+" "+hours+":"+minutes ; 
}
/**
 * 搜索
 */
function search(){
	searchButton(fcodeTable);
};
function toFcodeDetail(obj){
	var oo = $(obj).parent().parent().parent();
	var row = fcodeTable.fnGetData(oo); // get datarow
	//window.location.href=basePath+"/home#/barrier/barrier/barrierDetail?courseId="+courseId+"&courseName="+courseName;
	window.location.href=basePath+"/home#/factivity/fcode/toFcodeIndex?activityName="+row.name+"&lotNo="+row.lotNo;
}