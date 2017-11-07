var planTable;
var planForm;
var nowTime;
var searchJson = new Array();
var courseArray=new Array();
var planDateMax;
var planDateMin;
$(function() {

	//createDatetimePicker($("#chuanjiangStartTime"));

	
    loadPlanList();
    
    //动态添加下拉框
	ajaxRequest(basePath+'/cloudClass/plan/getLecturers',{"courseId":$("#courseId").val()},function(res) {
        if(res.success) {
        	var results = res.resultObject;
        	for(var i = 0;i <results.length;i++){
        		$("#chuanjiangLecturerId").append("<option value='"+results[i].id+"'>"+results[i].name+"</option>");;
        	}
        }else{
        	//true说明可以新建
        	alert("获取教师失败，请重试！");
        }
    });
	//$('#microCourseIds').selectpicker('deselectAll');
	$('#microCourseIds').selectpicker({
		  size: 10,
		  width:290,
		  noneSelectedText: '无',
		  actionsBox:true,
		  //actionsBox:true, //在下拉选项添加选中所有和取消选中的按钮
	});
});

//列表展示
function loadPlanList(){
	//第一次进入到该页面  首先按照规则生成计划
	ajaxRequest(basePath+'/cloudClass/plan/addPlan',{"courseId":$("#courseId").val(),"gradeId":$("#gradeId").val()},function(res) {
        if(res.success) {
        	var results = res.resultObject;
        	bulidTable(results);
        }else{
        	//true说明可以新建
        	alert("生成计划失败，请重试！");
        }
    });

    planFormEdit = $("#update-form").validate({
        messages: {
        	chuanjiangName: {
				required:"请输入串讲课名称！",
            },
            chuanjiangDuration: {
				required:"请输入直播时长！"
			},
			chuanjiangLecturerId: {
				required:"串讲老师不能为空！"
			},
			chuanjiangStartTime: {
				required:"请选择直播开始时间！",
			},
			chuanjiangEndTime: {
				required:"请选择直播结束时间！",
			},
			chuanjiangRoomId: {
				required:"请输入直播间ID！",
			},
			chuanjiangRoomLink: {
				required:"请输入外部链接！",
			}
        }
    });
}

$('#returnButton').on('click',function(){
	window.location.href=basePath+'/home#cloudClass/grade/index';
});

function bulidTable(results){
	$("#planTable tbody tr").remove();
	//false说明不可以新建
	for(var i = 0;i<results.length;i++){
		var str = "<tr onclick='selChecked(this)' style='cursor:pointer'><td><input type='checkbox' style='width: 18px;height: 18px;cursor: pointer;' name='checkPlan' value='"+results[i].id+"'></td>" +
				  "    <td>"+(i+1)+"" +
				  "		<input type='hidden' name='id' value='"+results[i].id+"'>" +
				  "		<input type='hidden' name='chuanjiangHas' value='"+results[i].chuanjiangHas+"'>" +
				  "		<input type='hidden' name='chuanjiangName' value='"+results[i].chuanjiangName+"'>" +
				  "		<input type='hidden' name='chuanjiangDuration' value='"+results[i].chuanjiangDuration+"'>" +
				  "		<input type='hidden' name='chuanjiangStartTime' value='"+results[i].chuanjiangStartTime+"'>" +
				  "		<input type='hidden' name='chuanjiangEndTime' value='"+results[i].chuanjiangEndTime+"'>" +
				  "		<input type='hidden' name='chuanjiangRoomId' value='"+results[i].chuanjiangRoomId+"'>" +
				  "		<input type='hidden' name='chuanjiangRoomLink' value='"+results[i].chuanjiangRoomLink+"'>" +
				  "		<input type='hidden' name='chuanjiangMode' value='"+results[i].chuanjiangMode+"'>" +
				  "		<input type='hidden' name='planDate' value='"+results[i].planDate+"'>" +
				  "		<input type='hidden' name='templateId' value='"+results[i].templateId+"'>" +
				  "		<input type='hidden' name='chuanjiangLecturerId' value='"+results[i].chuanjiangLecturerId+"'>" +
				  "		<input type='hidden' name='microCourseIds' value='"+results[i].microCourseIds+"'> " +
				  "		<input type='hidden' name='chuanjiangisSend' value='"+results[i].chuanjiangisSend+"'> " +
				  "		</td> " +
				  "    <td>"+results[i].planDate+"</td> " +
				  "    <td>"+results[i].week+"</td> ";

		if(results[i].day != null){
			str += "    <td>第"+results[i].day+"天</td> ";
		}else{
			str += "    <td>休息</td> ";
		}		  
		
		if(results[i].day != null){
			str +="    <td>"+(results[i].chuanjiangStartTime==null?'--':(new Date(results[i].chuanjiangStartTime).Format("hh:mm")))+"</td> " +
		  		  "    <td><div class=\"hidden-sm hidden-xs action-buttons\">" +
		  		  "	   <a class=\"blue\" href=\"javascript:void(-1);\" title=\"查看学习计划\" onclick=\"showGradePlanDialog('"+results[i].templateId+"');\">查看学习计划</a> ";
			if(results[i].chuanjiangHas == 0){
				str += "	   <a class=\"blue\" href=\"javascript:void(-1);\" title=\"添加串讲\" onclick=\"updateChuanjiang(this);\">添加串讲</a> ";
			}else{
				str += "	   <a class=\"blue\" href=\"javascript:void(-1);\" title=\"修改串讲\" onclick=\"updateChuanjiang(this);\">修改串讲</a> " +
					   "	   <a class=\"blue\" href=\"javascript:void(-1);\" title=\"删除串讲\" onclick=\"deleteChuanjiang(this);\">删除串讲</a> ";
			}
		  		  str +="</div></td> </tr>";
		}else{
			str +="    <td>"+(results[i].chuanjiangStartTime==null?'--':(new Date(results[i].chuanjiangStartTime).Format("hh:mm")))+"</td> " +  
	  		  	  "    <td><div class=\"hidden-sm hidden-xs action-buttons\">" +
	  		  	  "	     <a class=\"red\" href=\"javascript:void(-1);\" title=\"删除休息日\" onclick=\"delGradePlan('"+results[i].id+"','"+results[i].gradeId+"');\">删除休息日</a>";
			if(results[i].chuanjiangHas == 0){
				str += "	   <a class=\"blue\" href=\"javascript:void(-1);\" title=\"添加串讲\" onclick=\"updateChuanjiang(this);\">添加串讲</a> ";
			}else{
				str += "	   <a class=\"blue\" href=\"javascript:void(-1);\" title=\"修改串讲\" onclick=\"updateChuanjiang(this);\">修改串讲</a> " +
					   "	   <a class=\"blue\" href=\"javascript:void(-1);\" title=\"删除串讲\" onclick=\"deleteChuanjiang(this);\">删除串讲</a> ";
			}
			
			str +="</div></td> </tr>";
		}

        $("#planTable tbody").append(str);
	}
}

//查看相关知识点
function showGradePlanDialog(templateId){
	 $("#gradePlanTable tbody tr").remove();
	//$("#gradePlanTable tr").remove();//去掉所有的TD
	syncRequest(basePath+'/cloudClass/plan/getGradePlanChapter',{"templateId":templateId},function(res) {
        if(res.success) {
        	var results = res.resultObject;
        	if(results.length > 0){
        		//false说明不可以新建
            	for(var i = 0;i<results.length;i++){
    				var str = "<tr> " +
    						  "    <td>"+results[i].name+"</td> " +
    						  "    <td>"+results[i].videoTime+"</td></tr>";
    				 $("#gradePlanTable tbody").append(str);
        		}
        	}else{
        		var str = "<tr><td colspan='2'>暂无知识点 </td></tr>";
        		 $("#gradePlanTable tbody").append(str);
        	}
        }else{
        	//true说明可以新建
        	alertInfo(data.errorMessage);
        }
    });
    openDialog("showGradePlanDialog","dialogGradePlanDiv","查看详情 ",720,500,false,null,null);
}

//修改串讲信息
function updateChuanjiang(obj){
	planFormEdit.resetForm();
	$("#microCourseIds").selectpicker("refresh");
	var chuanjiangHas = $(obj).parent().parent().parent().find("[name='chuanjiangHas']").eq(0).val();
	var title="添加串讲";
	var errorInfo = "添加成功！";
	
	//当前选择的时间限制
	planDateMax = $(obj).parent().parent().parent().find("[name='planDate']").eq(0).val()+" 00:00:00";
	planDateMin = $(obj).parent().parent().parent().find("[name='planDate']").eq(0).val()+" 23:59:59";

	if(chuanjiangHas  == 1){// 是否有串讲，1有，0无
		title = "修改串讲";
		errorInfo = "修改成功！";
		$("#chuanjiangHas").val(chuanjiangHas);
		$("#id").val($(obj).parent().parent().parent().find("[name='id']").eq(0).val());
		$("#chuanjiangName").val($(obj).parent().parent().parent().find("[name='chuanjiangName']").eq(0).val());
		$("#chuanjiangDuration").val($(obj).parent().parent().parent().find("[name='chuanjiangDuration']").eq(0).val());
		$("#chuanjiangStartTime").val($(obj).parent().parent().parent().find("[name='chuanjiangStartTime']").eq(0).val());
		$("#chuanjiangEndTime").val($(obj).parent().parent().parent().find("[name='chuanjiangEndTime']").eq(0).val());
		$("#chuanjiangRoomId").val($(obj).parent().parent().parent().find("[name='chuanjiangRoomId']").eq(0).val());
		$("#chuanjiangRoomLink").val($(obj).parent().parent().parent().find("[name='chuanjiangRoomLink']").eq(0).val());
		$("#chuanjiangLecturerId option").prop("selected", false);
		$("#chuanjiangLecturerId option[value='"+$(obj).parent().parent().parent().find("[name='chuanjiangLecturerId']").eq(0).val()+"']").prop("selected", true);

		if($(obj).parent().parent().parent().find("[name='microCourseIds']").eq(0).val() != null && $(obj).parent().parent().parent().find("[name='microCourseIds']").eq(0).val() != ""){
			$("#microCourseIds").selectpicker('val',$(obj).parent().parent().parent().find("[name='microCourseIds']").eq(0).val().split(","));
		}

		if($(obj).parent().parent().parent().find("[name='chuanjiangMode']").eq(0).val()==0){//本站
			$("#thisWeb").prop('checked',true);
			$("#otherWeb").prop('checked',false);
		}else{//外站
			$("#thisWeb").prop('checked',false);
			$("#otherWeb").prop('checked',true);
		}
	}else{
		$("#chuanjiangName").val("");
		$("#id").val($(obj).parent().parent().parent().find("[name='id']").eq(0).val());
		$("#chuanjiangDuration").val("");
		$("#chuanjiangStartTime").val("");
		$("#chuanjiangEndTime").val("");
		$("#chuanjiangRoomId").val("");
		$("#chuanjiangRoomLink").val("");
		$("#chuanjiangLecturerId option").prop("selected", false);
		$("#chuanjiangLecturerId option[value='']").prop("selected", true);
		$("#microCourseIds").selectpicker('val',"");
		
		$("#thisWeb").prop('checked',true);
		$("#otherWeb").prop('checked',false);
	}
	
	changeMode();
	
	openDialog("updateChuanJiangDialog","dialogChuanJiangDiv",title,620,470,true,"确定",function(){
		if($("#update-form").valid()){
            mask();
            $("#chuanjiangHas").val(1);
            $("#update-form").attr("action", basePath+"/cloudClass/plan/updatePlanById");
            $("#update-form").ajaxSubmit(function(data){
                unmask();
                if(data.success){
                    $("#updateChuanJiangDialog").dialog("close");
                    layer.msg(errorInfo);

                    $(obj).parent().parent().parent().find("[name='chuanjiangName']").eq(0).val($("#chuanjiangName").val());
                    $(obj).parent().parent().parent().find("[name='chuanjiangDuration']").eq(0).val($("#chuanjiangDuration").val());
                    $(obj).parent().parent().parent().find("[name='chuanjiangStartTime']").eq(0).val($("#chuanjiangStartTime").val());
                    $(obj).parent().parent().parent().find("[name='chuanjiangEndTime']").eq(0).val($("#chuanjiangEndTime").val());
                    $(obj).parent().parent().parent().find("[name='chuanjiangRoomId']").eq(0).val($("#chuanjiangRoomId").val());
                    $(obj).parent().parent().parent().find("[name='chuanjiangRoomLink']").eq(0).val($("#chuanjiangRoomLink").val());
                    $(obj).parent().parent().parent().find("[name='chuanjiangLecturerId']").eq(0).val($("#chuanjiangLecturerId").val());
                    $(obj).parent().parent().parent().find("[name='chuanjiangMode']").eq(0).val($("[name='chuanjiangMode']:checked").eq(0).val());
                    $(obj).parent().parent().parent().find("[name='chuanjiangHas']").eq(0).val(1);
                    $(obj).parent().parent().parent().find("[name='microCourseIds']").eq(0).val($("#microCourseIds").eq(0).val());
                    $(obj).parent().parent().parent().find("a").eq(1).text("修改串讲").attr("title","修改串讲");
                    if(errorInfo == "添加成功！"){
                    	$(obj).parent().parent().parent().find("a").eq(1).after("<a class=\"blue\" href=\"javascript:void(-1);\" title=\"删除串讲\" onclick=\"deleteChuanjiang(this);\">删除串讲</a> ");
                    }
                    $(obj).parent().parent().parent().find("td").eq(5).text(new Date($("#chuanjiangStartTime").val()).Format("hh:mm"));
                }else{
               	 	alertInfo(data.errorMessage);
                }
            });
        }
	});
}
//删除串讲信息
function deleteChuanjiang(obj){
	//计划ID
	var planId = $(obj).parent().parent().parent().find("[name='id']").eq(0).val();
	showDelDialog(function(){
		ajaxRequest("/cloudClass/plan/deletePlanChuanJiangById",{'id':planId,'gradeId':$("#gradeId").val()},function(data){
			if(data.success){
				layer.msg("删除成功！");
				$(obj).remove();
				bulidTable(data.resultObject);
			}else{
				alertInfo(data.errorMessage);
			}
		});
	},null,"确认要删除该串讲?","确认");
}

function changeMode(){
	if($("#thisWeb").prop('checked')){//本站被选
		$("#chuanjiangRoomId").removeAttr("disabled","");
		$("#chuanjiangRoomId").parent().parent().show();
		$("#chuanjiangRoomLink").attr("disabled","disabled");
		$("#chuanjiangRoomLink").parent().parent().hide();
	}else{//外站被选
		$("#chuanjiangRoomId").attr("disabled","disabled");
		$("#chuanjiangRoomId").parent().parent().hide();
		$("#chuanjiangRoomLink").removeAttr("disabled");
		$("#chuanjiangRoomLink").parent().parent().show();
	}
}

$('#chuanjiangStartTime,#chuanjiangDuration').on('change',function(){
    var chuanjiangStartTime = $('#chuanjiangStartTime').val();
    var chuanjiangDuration = $('#chuanjiangDuration').val()*60*60*1000;
    if(chuanjiangStartTime!=""&&!isNaN(chuanjiangDuration)&&chuanjiangDuration!=""){
    	chuanjiangStartTime = chuanjiangStartTime.replace(new RegExp("-","gm"),"/");
    	chuanjiangStartTime = (new Date(chuanjiangStartTime)).getTime(); //得到毫秒数
        $('#chuanjiangEndTime').val(new Date(chuanjiangStartTime+chuanjiangDuration).format("yyyy-MM-dd hh:mm:ss"));
    }
});

/**
 * 删除
 */
function delGradePlan(id,gradeId){
	showDelDialog(function(){
		ajaxRequest("/cloudClass/plan/deletes",{'ids':id,'gradeId':gradeId},function(data){
			if(data.success){
				layer.msg(data.errorMessage);
				bulidTable(data.resultObject);
			}else{
				alertInfo(data.errorMessage);
			}
		});
	},null,"确认要删除该休息日?","确认");
}

$(".insert_bx").on('click',function(){
	if($("[name='checkPlan']:checked").size() == 0 ){
		alertInfo("请选择要插入休息日的记录");
		return false;
	}
	
	if($("[name='checkPlan']:checked").size() > 1 ){//如果选中多个 提示请单选
		alertInfo("只能在一条记录前插入休息日");
		return false;
	}
	
	if($("[name='checkPlan']:checked").eq($("[name='checkPlan']:checked").length-1).val()==$("[name='checkPlan']").eq($("[name='checkPlan']").length-1).val()){
		alertInfo("不能在最后一天后插入休息日");
		return false;
	}

	var id = $("[name='checkPlan']:checked").parent().parent().find("[name='id']").eq(0).val();
	var planDate = $("[name='checkPlan']:checked").parent().parent().find("[name='planDate']").eq(0).val();
	var templateId = $("[name='checkPlan']:checked").parent().parent().find("[name='templateId']").eq(0).val();
	
	if($("[name='checkPlan']:checked").size() == 1 ){//如果选中多个 提示请单选
		showDelDialog(function(){
			ajaxRequest("/cloudClass/plan/addOneRestPlan",{'id':id,'gradeId':$("#gradeId").val(),'planDate':planDate,'templateId':templateId},function(data){
				if(data.success){
					layer.msg(data.errorMessage);
					bulidTable(data.resultObject);
				}else{
					alertInfo(data.errorMessage);
				}
			});
		},null,"确定要在"+$("[name='checkPlan']:checked").parent().parent().find("td").eq(2).text()+"日之后加入休息日吗?","确认");
	}
	
});

$(".download_bx").on('click',function(){
	window.location.href='/cloudClass/plan/exportExcelPlan?gradeName='+$("#gradeName").val()+'&gradeId='+$("#gradeId").val();
});

function selChecked(obj){
	if(event.target=="javascript:void(-1);"||event.target.name == "checkPlan"){
		return false;
	}
	if($(obj).find("input[type='checkbox']").eq(0).prop("checked")){
		$(obj).find("input[type='checkbox']").eq(0).prop("checked",false);
	}else{
		$(obj).find("input[type='checkbox']").eq(0).prop("checked",true);
	}
}

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
