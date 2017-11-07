var M_courseTable;
var searchCase_M = new Array();

$(function(){
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	var searchCase_M = new Array();
	searchCase_M.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"1","tempType":"String"}');
	var m_checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var m_objData = [{ "title": m_checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
		return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
	}},
	{ "title": "课程ID", "class": "center","width":"5%","sortable": false,"data":"id" },
	{ "title": "课程名称", "class":"center","sortable":false,"data": 'courseName' },
	{ "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'xMenuName' },
	{ "title": "课程类别", "class":"center","width":"9%","sortable":false,"data": 'scoreTypeName' },
	{ "title": "授课老师", "class":"center","width":"8%","sortable":false,"data": 'lecturerName',"mRender":function (data, display, row) {
		return "<span name='skfs'>"+data+"</span>";
	} },
	{ "title": "实际学习人数", "class":"center","width":"10%", "sortable":false,"data": 'actCount',"visible":true},
    { "title": "课程时长", "class":"center","width":"8%", "sortable":false,"data": 'courseLength',"visible":false,"mRender":function (data, display, row) {
        return data+"h";
    }},
    { "title": "咨询QQ", "class":"center","sortable":false,"data": 'qqno',"visible":false},
	{ "title": "现价格", "class":"center","sortable":false,"data": 'originalCost',"visible":false},
	{ "title": "原价格/现价格", "class":"center","width":"9%","sortable":false,"mRender":function(data,display,row){
		data = row.originalCost+"/"+row.currentPrice;
		return "<span name='coursePrice'>"+data+"</span>"
	}},
	{ "title": "现价格", "class":"center","sortable":false,"data": 'currentPrice',"visible":false},
	{ "title": "班级数", "class":"center","sortable":false,"data": 'countGradeNum',"visible":false},
	{ "title": "默认报名人数", "class":"center","sortable":false,"data": 'learndCount',"visible":false},
	{ "title": "实际报名人数", "class":"center","sortable":false,"data": 'actCount',"visible":false},
	{ "title": "课程详情", "class":"center","width":"8%","sortable":false,"data": 'isCourseDetails',"mRender":function(data, display, row){
		console.log(data);
		console.log(row);
		if(data==1){
			return "<span name='kcxq'>展示</span>";
		}else{
			return "<span name='kcxq'>未展示</span>";
		}
	}},
	{ "title": "是否推荐", "class":"center","width":"8%","sortable":false,"data": 'isRecommend',"mRender":function (data, display, row) {
		if(data==1){
			return "<span name='sftj'>已推荐</span>";
		}else{
			return "<span name='sftj'>未推荐</span>";
		}
	} },
	{ "title": "是否配置", "class":"center","width":"8%","sortable":false,"data": 'isCourseDetails',"mRender":function (data, display, row) {
		if(data==1){
			return "<span name='sftj'>已配置</span>";
		}else{
			return "<span name='sftj'>未配置</span>";
		}
	} },
	{ "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
		if(data==1){
			return data="<span name='zt'>已启用</span>";
		}else{
			return data="<span name='zt'>已禁用</span>";
		}
	} },
	{ "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
		if(row.status=="1"){
			return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this,2)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,2);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
				
		}else{
			return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this,2)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,2);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
		}
	}
	},
	{ "title": "学科id", "class": "center","sortable": false,"data":"menuId","visible":false },
	{ "title": "课程类别id", "class": "center","sortable": false,"data":"courseTypeId","visible":false },
	{title: '讲师', "class": "center", "width": "7%","data": 'role_type1', "visible": false},
    {title: '班主任', "class": "center", "width": "7%","data": 'role_type2', "visible": false},
    {title: '助教', "class": "center", "width": "7%","data": 'role_type3', "visible": false},
	{ "title": "授课方式id", "class": "center","sortable": false,"data":"courseType","visible":false }];

	M_courseTable = initTables("courseTable_M",basePath+"/mobile/course/list",m_objData,true,true,0,null,searchCase_M,function(data){
	});
});

//搜索微课
function search_M(){
	var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"1","tempType":"String"}');
	$("#searchDiv_M .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				json.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
					+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
					+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				json.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
					+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});
	var str = "[" + json.join(",") + "]";
	M_courseTable.fnFilter(str);
};
/**
 * 查看课程信息
 * @param obj
 * @param status（1，职业课，2：微课）
 */
function previewDialog(obj,status){
	var oo = $(obj).parent().parent().parent();
	var row;
	if(status==1) {
		row = P_courseTable.fnGetData(oo); // get datarow
	}else{
		row = M_courseTable.fnGetData(oo); // get datarow
	}
	
	
	//根据当前id查找对应的课程信息
    $.get(basePath+"/mobile/course/findCourseById",{id:row.id}, function(result){
        $("#show_gradeStudentSum").text(result[0].classRatedNum); //班级额定人数
        if(status==1) {
            $("#show_classRatedNum").hide();
			$("#classGradeQQ").hide();
        }else{
            $("#show_classRatedNum").show();
        }
    	$("#show_courseName").text(result[0].courseName); //课程名称
    	$("#show_menuName").text(result[0].xMenuName); //所属学科
    	$("#show_menuNameSecond").text(result[0].scoreTypeName); //课程类别
    	$("#show_courseType").text(result[0].teachMethodName); //授课方式
    	$("#show_courseLength").text(result[0].courseLength+"小时"); //课程时长
		$("#show_gradeQQ").text(result[0].gradeQQ); //班级QQ群
    	$("#show_qqno").text(result[0].qqno); //咨询QQ
    	$("#show_learndCount").text(result[0].defaultStudentCount); //默认报名人数
    	$("#show_actCount").text(row.actCount==null?0:row.actCount);//实际报名人数
    	$("#show_sum").text(result[0].defaultStudentCount*1+row.actCount*1);//累计报名人数
    	
    	$("#role_type1_show").html(row.role_type1);//讲师
    	$("#role_type2_show").html(row.role_type2);//班主任
    	$("#role_type3_show").html(row.role_type3);//助教
    	
//    	$("#show_description").text(result[0].description); //包含班级
    	$("#show_createTime").text(result[0].createTime); //创建时间
    	if(result[0].status == 1){
    		$("#show_status").text("启用"); //状态
    	}else{
    		$("#show_status").text("禁用"); //状态
    	}
    	if(result[0].isFree == true){ //免费 true
    		$("#show_no_free").attr('checked',true);
    		$("#show-originalCost").hide();
    		$("#show-currentPrice").hide();
    	}else if(result[0].isFree == false){
    		$("#show_is_free").attr('checked',true);
    		$("#show-originalCost").show();
    		$("#show-currentPrice").show();
    	}
    	
    	$("#show_originalCost").text(result[0].originalCost); //原价格
    	$("#show_currentPrice").text(result[0].currentPrice); //现价格
    	$("#show_courseDescribe").text(result[0].description); //课程简介
    	$("#show_cloudClassroom").text(result[0].cloudClassroom); //云课堂连接
    });
	var prev_title="查看课程";
	if(status ==1){
		prev_title="查看职业课程";
	}else{
		prev_title="查看微课程";
	}
	var dialog = openDialogNoBtnName("showCourseDialog","showCourseDiv",prev_title,530,600,false,"确定",null);
};
//编辑详情
function showDetailDialog(obj,status){
	var oo = $(obj).parent().parent().parent();
	var aData,page;
	if(status==1) {
		aData = P_courseTable.fnGetData(oo); // get datarow
        page = getCurrentPageNo(P_courseTable);
	}else{
		aData = M_courseTable.fnGetData(oo); // get datarow
        page = getCurrentPageNo(M_courseTable);
	}
	window.location.href=basePath+'/home#mobile/course/courseDetail?page='+page+'&courseId='+aData.id;
}
/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImageRecImg(inputId,imgSrc){
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

//展示图片大图
function showImg(rowId,rowDescription,rowImgPath){
	 var a=new Image()
	 a.src = rowImgPath;
	 
	 layer.open({
		  title:false,
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框 
		  area: [(a.widht+20)+"px",(a.height+12)+"px"], //宽高 926 386
		  content: '<img src="'+rowImgPath+'" onclick="layer.closeAll()"/>',
		  shadeClose:true
		});
	 
}

//图片上传统一上传到附件中心---- 修改  列表页
$("#updateRecImg-form").on("change","#update_recImgPath_file",function(){
 	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
 	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
 		layer.alert("图片格式错误,请重新选择.");
 		this.value="";
 		return;
 	}
 	var id = $(this).attr("id");
 	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
 		if (data.error == 0) {
 			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
 			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 252px; height: 97px;");
 			
 			$("#update_recImgPath").val(data.url);
 			document.getElementById("update_recImgPath_file").focus();
 			document.getElementById("update_recImgPath_file").blur();
 			$(".remove").hide();
 		}else {
 			alert(data.message);
 		}
 	})
 });

	