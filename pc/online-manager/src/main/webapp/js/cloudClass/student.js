var searchCase= new Array();
var searchRecordCase= new Array();
var studentStoryForm;
var studentStoryTable;
var editStudentStoryform;
var studentRecordTable;
var showStudentIsPaymentForm;
$(function(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"60px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
				        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
				    }},
	                { "title": "学号", "class": "center","width":"80px","sortable": false,"data":"studentNumber" },
	                { "title": "帐号", "class":"center","sortable":false,"data": 'loginName' },
	                { "title": "学员姓名", "class":"center","sortable":false,"data": 'realName' },
					{ "title": "手机号", "class":"center","sortable":false,"data": 'mobile' },
					{ "title": "邮箱", "class":"center","sortable":false,"data": 'email' },
					{ "title": "QQ号", "class":"center","sortable":false,"data": 'qq' },
					{ "title": "毕业院校", "class":"center","sortable":false,"data": 'schoolName' },
					{ "title": "学历", "class":"center","sortable":false,"data": 'educationName' },
					{ "title": "专业", "class":"center","sortable":false,"data": 'majorName' },
					{ "title": "缴费状态", "class":"center","sortable":false,"data": 'isPayment',"visible":false ,"mRender":function (data, display, row) {
							if(data=="1")
							{
								return "未缴费";
							}
							if(data=="0")
							{
								return "免费";
							}
							return "已缴费";
						}
					},
	               { "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
	            	   /* var paymentButton;
					   if(row.isPayment=="0")
					   {
						   paymentButton='<a class="gray" href="javascript:void(-1);" title="标记缴费状态"><i class="ace-icon glyphicon glyphicon-usd bigger-130"></i></a>';
					   }else{
						   paymentButton='<a class="blue" href="javascript:void(-1);" title="标记缴费状态" onclick="openPaymentDialog(this)"><i class="ace-icon glyphicon glyphicon-usd bigger-130"></i></a>';
					   }*/
	            	   return '<div class="hidden-sm hidden-xs action-buttons">'+
	       		    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
					/*paymentButton+*/
	       			'<a class="blue" href="javascript:void(-1);" title="跟踪记录" onclick="openRecordDialog(this)"><i class="ace-icon glyphicon glyphicon-dashboard bigger-130"></i></a>'
				   		}
		      		}];

	searchCase.push('{"tempMatchType":"7","propertyName":"gradeId","propertyValue1":"' + $("#gradeId").val() + '","tempType":"String"}');
	studentStoryTable = initTables("studentStoryTable",basePath+"/cloudClass/studentGrade/findStudentList",objData,true,true,null,null,searchCase,function(data){
		var iDisplayStart = data._iDisplayStart;
		var countNum = data._iRecordsTotal;//总条数
		pageSize = data._iDisplayLength;//每页显示条数
		currentPage = iDisplayStart / pageSize +1;//页码
	});
	searchCase.pop();


	var objData2 = [
		{ "title": "ID", "class": "center","width":"80px","sortable": false,"data":"studentNumber", "visible":false },
		{ "title": "记录时间", "class": "center","width":"80px","sortable": false,"data":"recordTime" },
		{ "title": "记录内容", "class":"center","width":"80px","sortable":false,"data": 'recordContent' },
		{ "title": "记录人", "class":"center","width":"80px","sortable":false,"data": 'lecturerName' },
		{ "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
			return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="updateRecordRow(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="删除" onclick="deleteRecordRow(this)"><i class="ace-icon glyphicon fa fa-trash-o bigger-130"></i></a>'
		}
		}];
	searchRecordCase.push('{"tempMatchType":"7","propertyName":"applyId","propertyValue1":"-1","tempType":"String"}');

	studentRecordTable = initTables("studentRecordTable",basePath+"/cloudClass/studentGrade/findTrackRecordList",objData2,true,true,null,null,searchRecordCase,function(data){
	});
	searchRecordCase.pop();

	//下线时间 时间控件
	//createDatetimePicker2($(".datetime-picker"),"yy.mm.dd","HH:mm");
	$('#cancelbt,#returnbutton').on('click',function(){
		window.location.href='/home#cloudClass/grade/index?page='+$('#page').val();
	});
	
	$("#addStudentRecord-form").validate({
        messages: {
        	recordTime: {
				required:"请选择跟踪时间！"
            },
            recordContent: {
				required:"请输入记录内容！"
			},
			lecturerId: {
				required:"请选择记录人！"
			}
        }
    });
	$("#updateStudentRecord-form").validate({
		messages: {
			recordTime: {
				required:"请选择跟踪时间！"
			},
			recordContent: {
				required:"请输入记录内容！"
			},
			lecturerId: {
				required:"请选择记录人！"
			}
		}
	});
	
});
$(".datetime-picker").focus(function () {
	createDatetimePicker2($(".datetime-picker"),"yy.mm.dd","HH:mm");
});
/*$(".datetime-picker").blur(function () {
	$(".datetime-picker").datetimepicker("hide");
})*/
function search(){
	searchCase.push('{"tempMatchType":"7","propertyName":"gradeId","propertyValue1":"' + $("#gradeId").val() + '","tempType":"String"}');
	var isPayment=$("#isPayment").val();
	if(isPayment!=null)
	{
		searchCase.push('{"tempMatchType":"8","propertyName":"isPayment","propertyValue1":"' + isPayment + '","tempType":"String"}');
	}
	searchButton(studentStoryTable,searchCase);
	searchCase.pop();
	searchCase.pop();
};


//新增框
$(".add_bx").click(function(){
	studentStoryForm.resetForm();
	imgSenBut();
	createImageUpload($('.uploadImg'));//生成图片编辑器
	$("#message_content").empty();
	
	var dialog = openDialog("addStudentStoryDialog","dialogAddStudentStoryDiv","新增学员故事",1045,650,true,"确定",function(){
		var content=$("#message_content").html();
		$("#addtext").val(content);
		
		if($("#addStudentStory-form").valid()){
			mask();
			
			 $("#addStudentStory-form").attr("action", basePath+"/cloudclass/studentStory/addStudentStory");
	            $("#addStudentStory-form").ajaxSubmit(function(data){
	            	try{
                		data = jQuery.parseJSON(jQuery(data).text());
                	}catch(e) {
                		data = data;
                	  }
	                unmask();
	                if(data.success){
	                    $("#addStudentStoryDialog").dialog("close");
	                    freshTable(studentStoryTable);
	                }else{
	                	layer.msg(data.errorMessage);
	                }
	            });
			
		}
	});
});

/**
 * 批量逻辑删除
 * 
 */

$(".dele_bx").click(function(){
	deleteAll(basePath+"/cloudClass/studentGrade/student/deletes",studentStoryTable);
});

//单条删除
function deleteRecordRow(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = studentRecordTable.fnGetData(oo);
	showDelDialog(function(){
		ajaxRequest(basePath+"/cloudClass/studentGrade/student/record/delete/"+aData.id,null,function(res){
			if(res.success){
				freshTable(studentRecordTable);
			}
			//layerAlterInfo(res.errorMessage);
		});
	});
}



$(".record_add_bx").click(function(){
	$("#recordTime").val("");
	$("#recordContent").val("");
	var dialog = openDialog("addStudentRecordDialog", "dialogAddStudentRecordDiv","新增跟踪记录",600,500,true,"确定",function(){

		if($("#addStudentRecord-form").valid()){
			mask();

			$("#addStudentRecord-form").attr("action", basePath+"/cloudClass/studentGrade/student/record/save");
			$("#addStudentRecord-form").ajaxSubmit(function(data){
				try{
					data = jQuery.parseJSON(jQuery(data).text());
				}catch(e) {
					data = data;
				}
				unmask();
				if(data.success){
					$("#addStudentRecordDialog").dialog("close");
					freshTable(studentRecordTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});

		}
	});
	$("#recordContent").focus();
	$(".datetime-picker").datetimepicker("hide");
});



function updateRecordRow(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = studentRecordTable.fnGetData(oo);
	$("#updateRecordTime").val(aData.recordTime);
	$("#updateRecordContent").val(aData.recordContent);
	$("#updateLecturerId").val(aData.lecturerId);
	$("#recordId").val(aData.id);
	var dialog = openDialog("updateStudentRecordDialog", "dialogUpdateStudentRecordDiv"," 修改跟踪记录",600,500,true,"确定",function(){

		if($("#updateStudentRecord-form").valid()){
			mask();

			$("#updateStudentRecord-form").attr("action", basePath+"/cloudClass/studentGrade/student/record/update");
			$("#updateStudentRecord-form").ajaxSubmit(function(data){
				try{
					data = jQuery.parseJSON(jQuery(data).text());
				}catch(e) {
					data = data;
				}
				unmask();
				if(data.success){
					$("#updateStudentRecordDialog").dialog("close");
					freshTable(studentRecordTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});

		}
	});
	$("#updateRecordContent").focus();
	$(".datetime-picker").datetimepicker("hide");
}

/**
 * 查看
 * 
 */
function previewDialog(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = studentStoryTable.fnGetData(oo); // get datarow

	$("#showName").text(row.realName);
	$("#showStudentNumber").text(row.studentNumber);
	$("#showPhone").text(row.mobile);
	$("#educationName").text(row.educationName);
	$("#majorName").text(row.majorName);
	$("#showIsPayment").text(row.isPayment=="1"?"未缴费":"已缴费"+row.cost+"元");

	var dialog = openDialogNoBtnName("showStudentStoryDialog","dialogShowStudentStoryDiv","查看学员",430,500,false,"确定",null);
	//
}


function openRecordDialog(obj){

	var oo = $(obj).parent().parent().parent();
	var row = studentStoryTable.fnGetData(oo); // get datarow
	debugger
	$("#currentStudent").text("当前学员:"+(row.realName==null?row.loginName:row.realName));
	$("#addApplyId").val(row.applyId);
	$("#addGradeId").val($("#gradeId").val());

	searchRecordCase.push('{"tempMatchType":"7","propertyName":"applyId","propertyValue1":"'+row.applyId+'","tempType":"String"}');
	searchRecordCase.push('{"tempMatchType":"7","propertyName":"gradeId","propertyValue1":"'+$("#gradeId").val()+'","tempType":"String"}');
	searchButton(studentRecordTable,searchRecordCase);
	searchRecordCase.pop();
	searchRecordCase.pop();
	var dialog = openDialogNoBtnName("showStudentIsRecordDialog","dialogShowStudentRecordDiv","跟踪记录",980,580,true,"确定");

	//
}

function openPaymentDialog(obj){
	$("#showStudentIsPayment-form").resetForm();
	var oo = $(obj).parent().parent().parent();
	var row = studentStoryTable.fnGetData(oo); // get datarow

	if(row.isPayment=="1")
	{
		$("input[name='isPayment'][value=1]").attr("checked",true);//未缴费
		$("input[name='isPayment'][value=2]").attr("checked",false);//已缴费
		$("#cost").parent().parent().hide();
	}else{
		$("input[name='isPayment'][value=2]").attr("checked",true);//已缴费
		$("input[name='isPayment'][value=1]").attr("checked",false);//未缴费
		$("#cost").parent().parent().show();
	}
	$("#applyId").val(row.id);
	
	$("input[name='isPayment']").on('click',function(){
		
		if($("input[name='isPayment']:checked").val() == 1){//未缴费
			$("#cost").parent().parent().hide();
		}else{
			$("#cost").parent().parent().show();
		}
	});
	$("#cost").val(row.cost);
	
	//$("#showName").text(row.realName);
	//$("#showStudentNumber").text(row.studentNumber);
	//$("#showPhone").text(row.mobile);
	//$("#educationName").text(row.educationName);
	//$("#majorName").text(row.majorName);
	//$("#showIsPayment").text(row.isPayment=="1"?"未缴费"+row.cost+"元":"已缴费"+row.cost+"元");

	var dialog = openDialog("showStudentIsPaymentDialog","dialogShowStudentIsPaymentDiv","修改缴费状态",430,230,true,"确定",function(){
		
		showStudentIsPaymentForm = $("#showStudentIsPayment-form").validate({
	      	  messages: {
	      		isPayment: {
	                    required: "请选择是否缴费"
	                },
	                cost: {
	                    required: "请输入金额",
	                    digits: "金额非法"/*,
	                    min: jQuery.validator.format("金额最小为{0}")*/
	                }
	            }
	      });
		var selectRadio=$("input[name='isPayment']:checked").val();
		if(selectRadio==1){
			$("#cost").attr("disabled","disabled");
		}
		if($("#showStudentIsPayment-form").valid()){
			mask();

			$("#showStudentIsPayment-form").attr("action", basePath+"/cloudClass/studentGrade/student/changePayment");
			$("#showStudentIsPayment-form").ajaxSubmit(function(data){
				try{
					data = jQuery.parseJSON(jQuery(data).text());
				}catch(e) {
					data = data;
				}
				unmask();
				if(data.success){
					$("#cost").removeAttr("disabled");
					$("#showStudentIsPaymentDialog").dialog("close");
					freshTable(studentStoryTable);
				}else{
					$("#cost").removeAttr("disabled");
					layer.msg(data.errorMessage);
				}
			});

		}
		
	});
}

/**
 * 修改
 * 
 */
function toEdit(obj){
	edit_imgSenBut();
	createImageUpload($('.uploadImg'));//生成图片编辑器
	
	var oo = $(obj).parent().parent().parent();
	var row = studentStoryTable.fnGetData(oo); // get datarow
	 $.get(basePath+"/cloudclass/studentStory/findStudentStoryById",{id:row.id}, function(result){
		 	$("#edit_id").val(row.id);
	    	$("#editname").val(result.name);
	    	$("#editotherName").val(result.otherName);
	    	$("#editcourse").val(result.course);
	    	$("#editcompany").val(result.company);
	    	$("#editsalary").val(result.salary);
	    	$("#editintroduce").val(result.introduce);
	    	$("#edittitle").val(result.title);
	    	$("#edit_headImgPathFile").attr("src",result.headImg);
	    	$("#edit_headImgPath").val(result.headImg);
	    	if(result.headImg!=null&&result.headImg!=""){
	    		reviewImage("edit_headImgPath", result.headImg);
	    	}
	    	$("#message_content_edit").html(row.text);
	    	$("#message_content").attr("contenteditable","true");
	    	
	    	for(i=0;i<$("#editmenuId option").length;i++){
	    		if($("#editmenuId option").eq(i).val()==result.menuId){
	         			$("#editmenuId option").eq(i).attr("select","selected"); 
	         			$("#editmenuId").val(result.menuId);
	         		}
	    	}
	    	
	    	for(i=0;i<$("#editscoreType option").length;i++){
	    		if($("#editscoreType option").eq(i).val()==result.courseTypeId){
	         			$("#editscoreType option").eq(i).attr("select","selected"); 
	         			$("#editscoreType").val(result.courseTypeId);
	         		}
	    	}
	    	if(result.useOtherName==true){
	    		$("#edituseOtherName").prop("checked", true); 
	    	}
	    	
	    	//$("#message_content_preview").attr("contenteditable","false");
	    	var dialog = openDialog("editStudentStoryDialog","dialogEditStudentStoryDiv","修改学员故事",1045,650,true,"确定",function(){
	    		var content=$("#message_content_edit").html();
	    		$("#edittext").val(content);
	    		
	    		if($("#editStudentStory-form").valid()){
	    			mask();
	    			
	    			 $("#editStudentStory-form").attr("action", basePath+"/cloudclass/studentStory/updateStudentStoryById");
	    	            $("#editStudentStory-form").ajaxSubmit(function(data){
	    	            	try{
	                    		data = jQuery.parseJSON(jQuery(data).text());
	                    	}catch(e) {
	                    		data = data;
	                    	  }
	    	                unmask();
	    	                if(data.success){
	    	                    $("#editStudentStoryDialog").dialog("close");
	    	                    freshTable(studentStoryTable);
	    	                }else{
	    	                	layer.msg(data.errorMessage);
	    	                }
	    	            });
	    			
	    		}
	    		
	    	});
	    	
	 });
	
}

function imgSenBut(){
	$("#imgAdd").html('<input type="file" name="img" id="headImgPathFile" class="uploadImg"/>');
};

function edit_imgSenBut(){
	$("#edit_imgAdd").html('<input type="file" name="img" id="edit_headImgPathFile" class="uploadImg"/>');
};

var _this;
//图片上传统一上传到附件中心
$("#addStudentStory-form").on("change","#headImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			
			$("#headImgPath").val(data.url);
			document.getElementById("imgAdd").focus();
			document.getElementById("imgAdd").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}	
	})
});

//图片上传统一上传到附件中心---- 修改
$("#editStudentStory-form").on("change","#edit_headImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
	
		if (data.error == 0) {
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#edit_headImgPath").val(data.url);
		  	document.getElementById("edit_imgAdd").focus();
		  	document.getElementById("edit_imgAdd").blur();
		  	$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

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
    return year+"-"+month+"-"+day ; 
}
function getFormat(time){
	/*if(time >= 1 && time < 9){
		time = "0"+time;
	}*/
	return time;
}
function getFormatHMS(time){
	if(time >= 0 && time < 9){
		time = "0"+time;
	}
	return time;
}