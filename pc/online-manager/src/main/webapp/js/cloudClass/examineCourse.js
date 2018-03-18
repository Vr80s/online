var _courseTable;
var searchCase;
var courseForm;
var teacherArray=new Array();
var _courseRecTable;
var searchJson = new Array();
$(function(){
	
    //下线时间 时间控件
    createDatePicker($("#s_startTime"));
    createDatePicker($("#s_stopTime"));
	
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    }},
    {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
/*    { "title": "公开课名称", "class":"center","width":"15%","sortable":false,"data": 'title' ,"mRender":function (data, display, row) {
    		return "<span name='courseNameList'>"+data+"</span>";
    } },*/
    { "title": "审核状态", "class":"center","width":"8%","sortable":false,"data": 'examineStatus',"mRender":function (data, display, row) {
    	if(data==0){  //审核状态 0未审核 1 审核通过 2 审核未通过
    		return data="<span name='zt'>未审核 </span>";
    	}else if(data == 1){
    		return data="<span name='zt'> 审核通过 </span>";
    	}else if(data == 2){
    		return data="<span name='zt'>审核未通过</span>";
    	}else if(data == 3){
    		return data="<span name='zt'> 申诉中 </span>";
    	}else if(data == 4){
    		return data="<span name='zt'>申诉失败</span>";
    	}
    }},
    { "title": "主播", "class":"center","width":"10%","sortable":false,"data": 'lecturerName',"mRender":function (data, display, row) {
		return "<span name='lecturerNameList'>"+data+"</span>";
    } },
    
    { "title": "课程名", "class":"center","width":"10%","sortable":false,"data": 'title',"mRender":function (data, display, row) {
    	return "<span name='lecturerNameList'>"+data+"</span>";
    } },
    { "title": "观看方式", "class":"center","width":"6%","sortable":false,"data": 'seeMode',"mRender":function (data, display, row) {
    	if(data==0){  //审核状态 0未审核 1 审核通过 2 审核未通过
    		return data="<span name='zt'>免费</span>";
    	}else if(data == 1){
    		return data="<span name='zt'> 付费 </span>";
    	}else if(data == 2){
    		return data="<span name='zt'>密码</span>";
    	}
    	
    } },
 
    { "sortable": false,"class": "center","width":"6%","title":"申请详情","mRender":function (data, display, row) {
    	var str =  '<div class="hidden-sm hidden-xs action-buttons">';
    	str +='<a class="blue" href="javascript:void(-1);" title="查看" onclick="toEdit(this)"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
    	str+="</div>";
    	return str;
      } 
	},
    
    { "sortable": false,"class": "center","width":"8%","title":"状态","mRender":function (data, display, row) {
    	
    	var str =  '<div class="hidden-sm hidden-xs action-buttons">';
    	if(row.examineStatus == "0"){
    		str +="<span>未审核</span>";
		}else if(row.examineStatus == "1"){
			str +="<span>已通过</span>";
		}else if(row.examineStatus == "2"){
			str +="<span>已驳回</span>";
		}else if(row.examineStatus == "3"){
			str +="<span>申诉中</span>";
		}else if(row.examineStatus == "4"){
			str +="<span>申诉失败</span>";
		}
    	return str;
      } 
	},
    
	{ "sortable": false,"class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {
	    	var str =  '<div class="hidden-sm hidden-xs action-buttons">';
	    	//str+='<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
	    	if(row.examineStatus == "0" || row.examineStatus == "3"){
	    		str +='<a class="blue" href="javascript:void(-1);" title="通过" onclick="toPass(this);">通过</a>';
	    		str +='<a class="blue" href="javascript:void(-1);" title="驳回" onclick="toBoHui(this);">驳回</a>'; 
			}else if(row.examineStatus == "1"){
				if(row.isDelete){
					str +='<a class="blue" href="javascript:void(-1);" title="恢复此审核过的直播,此直播变为有效状态,用户端也将看到此直播" onclick="huifuZhiBo(this);">恢复直播</a>';
				}else{
					str +='<a class="blue" href="javascript:void(-1);" title="删除直播,用户端将看不到此直播" onclick="delZhiBo(this);">删除直播</a>';
				}
			}else if(row.examineStatus == "2" || row.examineStatus == "4"){
				//撤销驳回   --》 重新审核
				str +='<a class="blue" href="javascript:void(-1);" title="撤销驳回,删除驳回理由,可重新审核" onclick="cxBoHui(this);">撤销驳回</a>';
			}
	    	return str;
	      } 
		},
    
    { "title": "审核人", "class":"center","width":"8%","sortable":false,"data": 'auditPersonStr',"mRender":function (data, display, row) {
    	if(data==null){
            return "<span name='auditPersonStr'>无</span>";
		}
		return "<span name='auditPersonStr'>"+data+"</span>";
    } },
    
    { "title": "审核时间", "class":"center","width":"10%", "sortable":false,"data": 'auditTime',"mRender":function (data, display, row) {
    	return data;
    } },
    { "sortable": false,"class": "center","width":"8%","title":"是否有效","mRender":function (data, display, row) {
    	var str =  '<div class="hidden-sm hidden-xs action-buttons">';
    	if(row.isDelete){
    		str +='<span>无效</span>';
		}else{
			str +="<span>有效</span>";
		}
        return str;
      } 
	},
    
    ];
	
	_courseTable = initTables("courseTable",basePath+"/cloudClass/examine/list",objData,true,true,2,null,searchCase,function(data){
		var texts = $("[name='courseNameList']");
	    for (var i = 0; i < texts.length; i++) {
	            texts.eq(i).parent().attr("title",texts.eq(i).text());
	    }
	    var texts1 = $("[name='lecturerNameList']");
	    for (var i = 0; i < texts.length; i++) {
	    	texts1.eq(i).parent().attr("title",texts1.eq(i).text());
	    }
	     var iDisplayStart = data._iDisplayStart;
	        var countNum = data._iRecordsTotal;//总条数
	        pageSize = data._iDisplayLength;//每页显示条数
	        currentPage = iDisplayStart / pageSize +1;//页码

			var countPage;
			
			if(countNum%pageSize == 0){
				countPage = parseInt(countNum/pageSize);
			}else{
				countPage = parseInt(countNum/pageSize) + 1;
			}
			
			$("[name='upa']").each(function(index){
				if(index == 0){
					$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
				}
			});
			$("[name='downa']").each(function(index){
				if(index == $("[name='downa']").size()-1){
					$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
				}
			});
	
		
		
		buihuiForm = $("#bohui-form").validate({
			messages: {
				againstReason:{
					required:"驳回原因不能为空！",
	            }
			}	
		});
		
	});

	createImageUpload($('.uploadImg'));//生成图片编辑器
	    
	createDatePicker($("#search_startTime"));
	createDatePicker($("#search_endTime"));
});


$('#startTime,#courseLength').change(function(){
	
     var startTime = $('#startTime').val();
     var hours= $('#courseLength').val()*60*60*1000;
     if(startTime!=""&&!isNaN(hours)&&hours!=""){
    	 startTime = startTime.replace(new RegExp("-","gm"),"/");
         startTime = (new Date(startTime)).getTime(); //得到毫秒数
         $('#endTime').val(new Date(startTime+hours).format("yyyy-MM-dd hh:mm:ss"));
     }
	});

Date.prototype.Format = function(fmt) { //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 



$('#startTime_edit,#courseLength_edit').change(function(){
	
    var startTime = $('#startTime_edit').val();
    var hours= $('#courseLength_edit').val()*60*60*1000;
    if(startTime!=""&&!isNaN(hours)&&hours!=""){
   	 startTime = startTime.replace(new RegExp("-","gm"),"/");
        startTime = (new Date(startTime)).getTime(); //得到毫秒数
        $('#endTime_edit').val(new Date(startTime+hours).format("yyyy-MM-dd hh:mm:ss"));
    }
	});

Date.prototype.Format = function(fmt) { //author: meizz   
 var o = {   
   "M+" : this.getMonth()+1,                 //月份   
   "d+" : this.getDate(),                    //日   
   "h+" : this.getHours(),                   //小时   
   "m+" : this.getMinutes(),                 //分   
   "s+" : this.getSeconds(),                 //秒   
   "q+" : Math.floor((this.getMonth()+3)/3), //季度   
   "S"  : this.getMilliseconds()             //毫秒   
 };   
 if(/(y+)/.test(fmt))   
   fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
 for(var k in o)   
   if(new RegExp("("+ k +")").test(fmt))   
 fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
 return fmt;   
} 
debugger;

/**
 * 批量逻辑删除
 */
$(".dele_bx").click(function(){
	deleteAll(basePath+"/cloudClass/examine/deletes",_courseTable);
});
/**
 * 批量回复操作 
 */
$(".recovery_bx").click(function(){
	deleteAll(basePath+"/cloudClass/examine/recoverys",_courseTable);
});

//点击通过
function toPass(obj){
	var oo = $(obj).parent().parent().parent();
	var aData =  _courseTable.fnGetData(oo);
	showDelDialog(function(){
		mask();
		var url = "cloudClass/examine/passApply";
		ajaxRequest(url,{'id':aData.id},function(data){
			unmask();
			//if(data.success){
				alertInfo(data.errorMessage);
				freshTable(_courseTable);
			//}else{
			//	alertInfo(data.errorMessage);
				//freshTable(_courseTable);
			//}
		});
	},null,"确认此课程审核通过?");
}



/*str +='<a class="blue" href="javascript:void(-1);" title="删除直播,用户端将看不到此直播" onclick="delZhiBo(this);">删除直播</a>';
str +="<span>已通过</span>";
//return '已通过';
}else if(row.examineStatus == "2"){

//撤销驳回   --》 重新审核
str +='<a class="blue" href="javascript:void(-1);" title="撤销驳回,删除驳回理由,可重新审核" onclick="cxBoHui(this);">撤销驳回</a>';
str +="<span>已驳回</span>";
//return '已驳回';
*/

/**
 *  恢复此直播
 */
function huifuZhiBo(obj){
	debugger;
	var oo = $(obj).parent().parent().parent();
	var aData =  _courseTable.fnGetData(oo);
	var ids =[];
	ids.push(aData.id)
	showDelDialog(function(){
		mask();
		var url = "/cloudClass/examine/recoverys";
		ajaxRequest(url,{'ids':ids.join(",")},function(data){
			
			unmask();
			//if(data.success){
				alertInfo(data.errorMessage);
				freshTable(_courseTable);
			//}else{
			//	alertInfo(data.errorMessage);
				//freshTable(_courseTable);
			//}
		});
	},null,"恢复此审核过的直播,此直播变为有效状态,用户端也将看到此直播?");
}

/**
 *   删除此直播
 */
function delZhiBo(obj){
	var oo = $(obj).parent().parent().parent();
	var aData =  _courseTable.fnGetData(oo);
	var ids =[];
	ids.push(aData.id)
	showDelDialog(function(){
		mask();
		var url = "/cloudClass/examine/deletes";
		ajaxRequest(url,{'ids':ids.join(",")},function(data){
			
			unmask();
			//if(data.success){
				alertInfo(data.errorMessage);
				freshTable(_courseTable);
			//}else{
			//	alertInfo(data.errorMessage);
				//freshTable(_courseTable);
			//}
		});
	},null,"删除此审核过的直播,此直播变为无效状态,用户端也将看不到此直播?");
}

/**
 * 撤销此驳回
 * @param obj
 */
function cxBoHui(obj){
	debugger;
	
	var oo = $(obj).parent().parent().parent();
	var aData =  _courseTable.fnGetData(oo);
	var ids =[];
	ids.push(aData.id);
	showDelDialog(function(){
		mask();
		var url = "cloudClass/examine/cxBoHui";
		ajaxRequest(url,{'id':aData.id},function(data){
			unmask();
			if(data.success){
				alertInfo(data.errorMessage);
				freshTable(_courseTable);
			}else{
				alertInfo(data.errorMessage);
				freshTable(_courseTable);
			}
		});
	},null,"撤销此驳回,将删除原有驳回理由,且可重新审核?");
}

/**
 * 点击驳回  显示一个弹框，然后输入内容
 */
function toBoHui(obj){
	debugger;
	var oo = $(obj).parent().parent().parent();
	var aData =  _courseTable.fnGetData(oo);
	$("#bohuiExamineId").val(aData.id);
	/*
	 * 显示这个弹框，
	 */
	var dialog = openDialog("bohuiDialog","bohuiDiv","修改课程",580,580,true,"确定",function(){
	if($("#bohui-form").valid()){
			mask();
            $("#bohui-form").attr("action", basePath+"/cloudClass/examine/bohuiApply"); //请求的方法
            $("#bohui-form").ajaxSubmit(function(data){
            	try{
            		data = jQuery.parseJSON(jQuery(data).text());
            	}catch(e) {
            		data = data;
            	  }
                unmask();
                if(data.success){
                    $("#bohuiDialog").dialog("close");
                    layer.msg(data.errorMessage);
                     freshTable(_courseTable);
                }else{
                	 layer.msg(data.errorMessage);
                	 $("#bohuiDialog").dialog("close");
                }
            });
		}
	});
	
}

//修改
function toEdit(obj){
	
	debugger;
	$('#startTime_edit').datepicker( "option" , {
		 minDate: null,
		 maxDate: null} );
//	$('#endTime_edit').datepicker( "option" , {
//		 minDate: null,
//		 maxDate: null} );
	$('#startTime_edit').datetimepicker({
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm:ss',
    	onSelect: function( startDate ) {
    		var $startDate = $( "#startTime_edit" );
    		var $endDate = $('#endTime_edit');
    		var endDate = $endDate.datepicker( 'getDate' );
    		if(endDate < startDate){
    			$endDate.datetimepicker('setDate', startDate - 3600*1*24*60*60*60);
    		}
    		$endDate.datetimepicker( "option", "minDate", startDate );
    	}
    });
//    $('#endTime_edit').datetimepicker({
//    	showSecond: true,
//		changeMonth: true,
//		changeYear: true,
//		dateFormat:'yy-mm-dd',
//		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
//		timeFormat: 'HH:mm:ss',
//    	onSelect: function( endDate ) {
//    		var $startDate = $( "#startTime_edit" );
//    		var $endDate = $('#endTime_edit');
//    		var startDate = $startDate.datepicker( "getDate" );
//    		if(endDate < startDate){
//    			$startDate.datetimepicker('setDate', startDate + 3600*1*24*60*60*60);
//    		}
//    		$startDate.datetimepicker( "option", "maxDate", endDate );
//    	}
//    });
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	
	debugger;
	reviewImageRecImg("smallImgPath_file_edit",row.logo);//预览

	$("#updateCourse-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
	
	$("#editCourse_id").val(row.id); //ID
	
	debugger;
	//所属学科
	for(i=0;i<$("#menuId_edit option").length;i++){
		if($("#menuId_edit option").eq(i).text()==row.menuName){
			$("#menuId_edit option").eq(i).attr("select","selected"); 
			$("#menuId_edit").val($("#menuId_edit option").eq(i).val());
		}
	}
	changeMenu_edit();
	
	$("#examineStatus").val(row.examineStatus);
	$("#courseName_edit").val(row.title); //课程名称
	
	debugger;
	var whenLong = row.whenLong / (1000 * 60 * 60);
	
	$("#courseLength_edit").val(whenLong.toFixed(2)); //课程时常
	$("#startTime_edit").val(row.startTime); //开播时间
	//$("#endTime_edit").val(row.endTime); //结束时间
	//直播方式
	if(row.directSeeding == 1){ //本站
		$("#this_web_edit1").attr('checked',true);
		$("#this_web_edit2").removeAttr('checked');
		$("#this_web_edit3").removeAttr('checked');
		
	}else if(row.directSeeding == 2){//外站
		$("#this_web_edit2").attr('checked',true);
		$("#this_web_edit1").removeAttr('checked');
		$("#this_web_edit3").removeAttr('checked');
	}else{
		$("#this_web_edit3").attr('checked',true);
		$("#this_web_edit1").removeAttr('checked');
		$("#this_web_edit2").removeAttr('checked');
	}
	$("#directId_edit").val(row.directId);//直播间ID
//	$("#externalLinks_edit").val(row.externalLinks);//外部链接
	$("#smallImgPath_edit").val(row.logo); //图片字段赋值
	$("#teacherImgPath_edit").val(row.teacherImgPath); //图片字段赋值
	
	$("#coursePwd_edid").val(row.password); //密码
	
	//$("#edid_originalCost").val(row.originalCost); //原价
	
	$("#edid_currentPrice").val(row.price); //现价
	
	$("#edid_courseDescribe1").val(row.content); //简介
	
	console.log($("#userId option").eq(i).val()+"==="+row.userId+"==="+$("#userId option").eq(i).text());
	//杨宣修改--增加模糊查询讲师功能
	for(i=0;i<$("#userId option").length;i++){
	    console.log($("#combobox1 option").eq(i).val()+"==="+row.userId+"==="+$("#userId option").eq(i).text());
		if($("#userId option").eq(i).val()==row.userId){
			$("#userId option").eq(i).attr("select","selected"); 
			$("#userId").val($("#userId option").eq(i).val());
			//$("#updateCourse-form #userId").val($("#userId option").eq(i).text());
		}
    }
	/**
	 * 得到申诉信息
	 */
	$.ajax({
		url:basePath+"/cloudClass/examine/getApplysByExamId",
		data:{'id':row.id},
		type:'POST',
		async: false,
		success:function(data){
			if(!data.success){
				alertInfo(data.errorMessage);
			}else{
				var obj = data.resultObject;
				var str ="";
				for (var int = 0; int < obj.length; int++) {
					var reviewerTime  = obj[int].reviewerTime.replace(" ",",");
					str += "<div class='form-group' id='edit-originalCost' style='margin-top: 15px;'>"
					  +"<label class='col-sm-2 control-label' for='coursePwd'>审核人: </label>"
					  +"<div class='col-sm-3'>"
					  +"<input type='text' name='auditPersonStr' disabled='disabled' value="+obj[int].name+" readonly='readonly'  id='auditPersonStr'  class='col-xs-10 col-sm-12'>"
					  +"</div>"
		             
					  +"<label class='col-sm-3 control-label' for='courseName'>审核时间: </label>"
					  +"<div class='col-sm-3'>"
					  +"<input type='text' name='reviewerTime' disabled='disabled' value="+reviewerTime+"  readonly='readonly'   id='reviewerTime'  class='col-xs-10 col-sm-12'>"
					  +"</div>"
					  +"</div>"
		      
					  +"<div class='form-group' style='margin-top: 15px;'>"
					  +"<label class='col-sm-2 control-label no-padding-right' for='courseDescribe'>驳回原因: </label>"
					  +" <div class='col-sm-6'>"
					  +"<textarea class='form-control' name='againstReason' id='againstReason'  disabled='disabled'  readonly='readonly'  rows='3'>"+obj[int].againstReason+"</textarea>"
					  +"</div>"
					  +"</div>"
					  
					  if(int == 0){
						 $("#appealTime").val(obj[int].appealTime); //申诉时间
						 $("#appealReason").val(obj[int].appealReason); //申诉理由
					  }
				}
				
				if(str!=""){
					$("#shxx").html(str);
					$(".ssxx").show();
					$("#shxx").show();
				}else{
					$("#shxx").hide();
					$(".ssxx").hide();
				}
			}
		}
	});
	var dialog = openDialog("EditCourseDialog","dialogEditCourseDiv","课程详情",800,600,true,"确定",function(){
		 $("#EditCourseDialog").dialog("close");
	});
}

//图片上传统一上传到附件中心--------修改用
$("#updateCourse-form").on("change","#smallImgPath_file_edit",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			
			$("#smallImgPath_edit").val(data.url);
			
			document.getElementById("smallImgPath_file_edit").focus();
			document.getElementById("smallImgPath_file_edit").blur();
		}else {
			alert(data.message);
		}
		$(".remove").hide();
	})
});

//图片上传统一上传到附件中心--------修改用
$("#updateCourse-form").on("change","#teacherImgPath_file_edit",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 89px; height: 89px;");
			
			
			$("#teacherImgPath_edit").val(data.url);
			
			document.getElementById("teacherImgPath_file_edit").focus();
			document.getElementById("teacherImgPath_file_edit").blur();
		}else {
			alert(data.message);
		}
		$(".remove").hide();
	})
});

/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImageRecImg(inputId,imgSrc){
	if(imgSrc == null){
		return ;
	}
	$(".remove").hide();
	var fileName = imgSrc;
	if(imgSrc.indexOf("/")>-1){
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
	}
	$("#"+inputId).parent().find('.ace-file-name').remove();
	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
			 +('<img class="middle" style="width: 252px; height: 97px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
					 +'</span>');
}
/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImageRecImg2(inputId,imgSrc){
	if(imgSrc == null){
		$("#teacherImgEdit").html("<input type=\"file\" name=\"teacherImgPath_file\" id=\"teacherImgPath_file_edit\" class=\"uploadImg\"/>");
		$("#teacherImgPath_edit").val("");
		createImageUpload($("#teacherImgPath_file_edit"));
		
		//图片上传统一上传到附件中心--------修改用
		$("#updateCourse-form").on("change","#teacherImgPath_file_edit",function(){
			var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
			if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
				layer.alert("图片格式错误,请重新选择.");
				this.value="";
				return;
			}
			var id = $(this).attr("id");
			ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
				if (data.error == 0) {
					$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
					$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
					$("#"+id).parent().find(".ace-file-name img").attr("style","width: 89px; height: 89px;");
					
					
					$("#teacherImgPath_edit").val(data.url);
					
					document.getElementById("teacherImgPath_file_edit").focus();
					document.getElementById("teacherImgPath_file_edit").blur();
				}else {
					alert(data.message);
				}
				$(".remove").hide();
			})
		});		
		return ;
	}
	$(".remove").hide();
	var fileName = imgSrc;
	if(imgSrc.indexOf("/")>-1){
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
	}
	$("#"+inputId).parent().find('.ace-file-name').remove();
	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
			+('<img class="middle" style="width: 89px; height: 89px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
			+'</span>');
}

function changeMenu(){
	teacherArray=[];
	$("#lecturer").html("");
	ajaxRequest(basePath+'/publiccloudclass/course/getTeacher',{"menuId":$("#menuId").val()},function(res) {
		$('#lecturer').empty();
		 
	       if(res.length>0)
	        {
	            for(var i=0;i<res.length;i++)
	            {
	            	teacherArray.push(res[i]);
	                $('#lecturer').append("<option value='"+res[i].id+"'>"+res[i].name+"</option>");
	               
	            }
	           
	        }else{
	        	$('#lecturer').append("<option value=''>无</option>");
	        }
	       
		
	});
}

function changeMenu_edit(){
	teacherArray=[];
	$("#lecturer_edit").html("");
	ajaxRequest(basePath+'/publiccloudclass/course/getTeacher',{"menuId":$("#menuId_edit").val()},function(res) {
		$('#lecturer_edit').empty();
		 
	       if(res.length>0)
	        {
	            for(var i=0;i<res.length;i++)
	            {
	            	teacherArray.push(res[i]);
	                $('#lecturer_edit').append("<option value='"+res[i].id+"'>"+res[i].name+"</option>");
	               
	            }
	           
	        }else{
	        	$('#lecturer_edit').append("<option value=''>无</option>");
	        }
	       
		
	});
}

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/cloudclass/course/updateStatus",{"id":row.id},function(data){
		console.log(data);
		if(data.success==false){
			layer.msg(data.errorMessage);
		}
		freshTable(_courseTable);
	});
};

function search(){

	//searchButton(_courseTable);
	
	 var startTime=$("#s_startTime").val(); //开始时间
     var stopTime=$("#s_stopTime").val(); //结束时间
     var status=$("#search_status").val();
     var type=$("#search_type").val();
     var name=$("#search_courseName").val();

     var isDelete=$("#search_isdelete").val();
     searchJson = new Array();
     if(startTime != "" || stopTime != "") {
    	 if (startTime != "" && stopTime != "" && startTime > stopTime) {
             alertInfo("开始日期不能大于结束日期");
             return;
         }
         searchJson.push('{"tempMatchType":"7","propertyName":"startTime","propertyValue1":"' + startTime + '","tempType":"String"}');
         searchJson.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + stopTime + '","tempType":"String"}');
     }
     
     if(status != ""){
    	 searchJson.push('{"tempMatchType":"8","propertyName":"status","propertyValue1":"' + status + '","tempType":"String"}');
     }
     
     if(type!=null&&type!=""){
         searchJson.push('{"tempMatchType":"9","propertyName":"type","propertyValue1":"' + type + '","tempType":"String"}');
     }
     
     if(name!=null&&name!=""){
         searchJson.push('{"tempMatchType":"10","propertyName":"name","propertyValue1":"' + name + '","tempType":"String"}');
     }
     
     if(isDelete!=null&&isDelete!=""){
         searchJson.push('{"tempMatchType":"10","propertyName":"isDelete","propertyValue1":"' + isDelete + '","tempType":"Integer"}');
     }
     
     searchButton(_courseTable,searchJson);
     searchJson.pop();
};
/**
 * 公开课管理显示
 * 
 */
$(".kcgl_bx").click(function(){
	$("#courseDiv").show();
	$("#courseRecDiv").hide();
	freshTable(_courseTable);
});

/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/publiccloudclass/course/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};
/**
 * 下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/publiccloudclass/course/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};



//$("#this_web").click(function(){
//	$("#directId").val("");
//	$("#externalLinks").val("");
//	
//	$("#add-directIdDiv").show();
//	$("#add-externalLinksDiv").hide();
//	
//	$('#externalLinks').attr("disabled",true);
//	$("#directId").attr("disabled",false);
//
//});

//$("#other_web").click(function(){
//	$("#directId").val("");
//	$("#externalLinks").val("");
//	
//	$("#add-externalLinksDiv").show();
//	$("#add-directIdDiv").hide();
//	
//	$('#externalLinks').attr("disabled",false);
//	$("#directId").attr("disabled",true);
//	
//});

//$("#this_web_edit").click(function(){
///*	$("#directId_edit").val("");
//	$("#externalLinks_edit").val("");*/
//	
//	$("#directIdDiv_edit").show();
//	$("#externalLinksDiv_edit").hide();
//	
//	$('#externalLinks_edit').attr("disabled",true);
//	$("#directId_edit").attr("disabled",false);
//
//});

//$("#other_web_edit").click(function(){
///*	$("#directId_edit").val("");
//	$("#externalLinks_edit").val("");*/
//	
//	$("#externalLinksDiv_edit").show();
//	$("#directIdDiv_edit").hide();
//	
//	$('#externalLinks_edit').attr("disabled",false);
//	$("#directId_edit").attr("disabled",true);
//	
//});
//-------------------------------------------------------------------公开课统计-----------------------------------------------------------------------
/**
 * 公开课统计显示
 * 
 */
$(".kctj_bx").click(function(){
	$("#courseDiv").hide();
	$("#courseRecDiv").show();
	freshTable(_courseRecTable);
});


function searchCourseRecTable(){
	searchButton(_courseRecTable);
}

(function( $ ) {
  $.widget( "custom.combobox", {
    _create: function() {
      this.wrapper = $( "<span>" )
        .addClass( "custom-combobox" )
        .insertAfter( this.element );

      this.element.hide();
      this._createAutocomplete();
      this._createShowAllButton();
    },

    _createAutocomplete: function() {
      var selected = this.element.children( ":selected" ),
        value = selected.val() ? selected.text() : "";

      this.input = $( "<input>" )
        .appendTo( this.wrapper )
        .val( value )
        .attr( "title", "" )
        .attr( "id", "nihao" )
        .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
        .autocomplete({
          delay: 0,
          minLength: 0,
          source: $.proxy( this, "_source" )
        })
        .tooltip({
          tooltipClass: "ui-state-highlight"
        });

      this._on( this.input, {
        autocompleteselect: function( event, ui ) {
          ui.item.option.selected = true;
          this._trigger( "select", event, {
            item: ui.item.option
          });
        },

        autocompletechange: "_removeIfInvalid"
      });
    },

    _createShowAllButton: function() {
      var input = this.input,
        wasOpen = false;

      $( "<a>" )
        .attr( "tabIndex", -1 )
        .attr( "title", "Show All Items" )
        .tooltip()
        .appendTo( this.wrapper )
        .button({
          icons: {
            primary: "ui-icon-triangle-1-s"
          },
          text: false
        })
        .removeClass( "ui-corner-all" )
        .addClass( "custom-combobox-toggle ui-corner-right" )
        .mousedown(function() {
          wasOpen = input.autocomplete( "widget" ).is( ":visible" );
        })
        .click(function() {
          input.focus();

          // 如果已经可见则关闭
          if ( wasOpen ) {
            return;
          }

          // 传递空字符串作为搜索的值，显示所有的结果
          input.autocomplete( "search", "" );
        });
    },

    _source: function( request, response ) {
      var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
      response( this.element.children( "option" ).map(function() {
        var text = $( this ).text();
        if ( this.value && ( !request.term || matcher.test(text) ) )
          return {
            label: text,
            value: text,
            option: this
          };
      }) );
    },

    _removeIfInvalid: function( event, ui ) {

      // 选择一项，不执行其他动作
      if ( ui.item ) {
        return;
      }

      // 搜索一个匹配（不区分大小写）
      var value = this.input.val(),
        valueLowerCase = value.toLowerCase(),
        valid = false;
      this.element.children( "option" ).each(function() {
        if ( $( this ).text().toLowerCase() === valueLowerCase ) {
          this.selected = valid = true;
          return false;
        }
      });

      // 找到一个匹配，不执行其他动作
      if ( valid ) {
        return;
      }

      // 移除无效的值
      this.input
        .val( "" )
        .attr( "title", value + " didn't match any item" )
        .tooltip( "open" );
      this.element.val( "" );
      this._delay(function() {
        this.input.tooltip( "close" ).attr( "title", "" );
      }, 2500 );
      this.input.data( "ui-autocomplete" ).term = "";
    },

    _destroy: function() {
      this.wrapper.remove();
      this.element.show();
    }
  });
})( jQuery );


/**
 * 获取微吼主播url
 * @param obj
 */
function getWebinarUrl(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	

	ajaxRequest(basePath+"/publiccloudclass/course/getWebinarUrl",{"webinarId":row.directId},function(data){
		console.log(data);
		$("#zburl").html(data.resultObject);
		var dialog = openDialog("ShowUrlDialog","dialogShowUrlDiv","直播地址",580,350,false,"确定",function(){
			
		});
	});
};

/**
 * 创建直播间（适用于平台直播间已创建，微吼直播间id创建失败或未插入活动id）
 * @param obj
 */
function createWebinar(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	
	ajaxRequest(basePath+"/publiccloudclass/course/reCreateWebinar",{"courseId":row.id},function(data){
		if(data.success){
			freshTable(_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};