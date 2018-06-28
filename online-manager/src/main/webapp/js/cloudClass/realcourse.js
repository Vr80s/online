var P_courseTable;//线下课列表
var M_courseTable;//微课列表
var courseForm;//添加课程表单
var updateCourseForm;//修改课程表单
var studyDayForm;//设置学习计划模板表单

var _courseRecTable;//课程推荐列表
var _cityTable;

$(function(){
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	//;
	/** 线下课列表begin */
    var searchCase_P = new Array();
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    { "title": "课程ID", "class": "center","width":"5%","sortable": false,"data":"id" },
	{"title": "封面图", "class": "center", "width": "12%", "sortable": false, "data": 'smallingPath',"mRender":function(data){
			return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;'/>";
		}},
    { "title": "课程名称", "class":"center","width":"9%","sortable":false,"data": 'courseName' },
    { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'xMenuName' },
	{ "title": "主讲人", "class":"center","width":"8%","sortable":false,"data": 'lecturer'},
	{ "title": "主播用户", "class":"center","width":"8%","sortable":false,"data": 'lecturerName'},
    { "title": "所在城市", "class":"center","width":"6%", "sortable":false,"data": 'realCitys'},
    { "title": "实际学习人数", "class":"center","width":"6%", "sortable":false,"data": 'actCount',"visible":true},
    { "title": "课程时长", "class":"center","width":"8%", "sortable":false,"data": 'courseLength',"visible":false,"mRender":function (data, display, row) {
        return data+"'";
    }},
    { "title": "价格", "class":"center","width":"6%","sortable":false,"mRender":function(data,display,row){
    	data = parseInt(row.currentPrice);
    	return "<span name='coursePrice'>"+data+"</span>"
    }},
    { "title": "开课时间", "class":"center","width":"10%", "sortable":false,"data": 'startTime'},
    { "title": "结束时间", "class":"center","width":"10%", "sortable":false,"data": 'endTime'},
    { "title": "发布时间", "class":"center","width":"10%", "sortable":false,"data": 'releaseTime' },
    { "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return data="<span name='zt'>已启用</span>";
    	}else{
    		return data="<span name='zt'>已禁用</span>";
    	}
    } },
	{ "title": "推荐时效", "class":"center","width":"10%","sortable":false,"data": 'sortUpdateTime',"mRender":function (data, display, row) {
		if(row.recommendSort == null||row.recommendSort == 0)return null;
		return data;
	}},
	{ "title": "推荐值", "class":"center","width":"5%", "sortable":false,"data": 'recommendSort',"mRender":function (data, display, row) {
		if(data == null)return 0;
		return data;
	}},
    { "sortable": false,"class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
	    	if(row.status=="1"){
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
                '<a class="blue" href="javascript:void(-1);" title="设置推荐值" onclick="updateRecommendSort(this,1)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '

	    	}else{
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
                    '<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this,1)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '
	    	}
	    } 
	}];
;
	P_courseTable = initTables("courseTable",basePath+"/realClass/course/list",objData,true,true,0,null,searchCase_P,function(data){
		;
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
	});
	/** 线下课列表end */

	/** 城市管理开始 */
	var objCityData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    { "title": "城市管理ID", "class": "center","width":"5%","sortable": false,"data":"id" },
    { "title": "城市名称", "class":"center","width":"9%","sortable":false,"data": 'cityName' },
    { "title": "城市展示图", "class":"center","width":"13%","sortable":false,"data": 'icon' ,"mRender":function (data, display, row) {
    	if(data != "" && data != null){
    		return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;'/>";
    	}else{
    		return "暂无图片";    	
    	}
	}},
    {title: '排序', "class": "center", "width": "8%","height":"34px","data": 'sort', "sortable": false,"mRender":function(data, display, row){
    	var str;
    	if(row.status ==1 || row.isRecommend == 0){//如果是禁用
    		str='<a class="blue" name="cityUpa" href="javascript:void(-1);" title="上移" onclick="cityUpMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
        	'<a class="blue" href="javascript:void(-1);" name="cityDowna" title="下移" onclick="cityDownMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
    	}else{//如果是不禁用
    		str='<a class="gray" href="javascript:void(-1);" title="上移"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
        	'<a class="gray" href="javascript:void(-1);" title="下移" ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
    	}
    	return '<div class="hidden-sm hidden-xs action-buttons">'+str;
    }},
	{ "title": "是否推荐", "class":"center","width":"8%","sortable":false,"data": 'isRecommend',"mRender":function (data, display, row) {
			if(data==1){
				return "<span name='sftj'>已推荐</span>";
			}else{
				return "<span name='sftj'>未推荐</span>";
			}
		} },
	{ "sortable": false,"class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {
		return '<div class="hidden-sm hidden-xs action-buttons">'+
		'<a class="blue" href="javascript:void(-1);" title="设置图片" onclick="updateRecImg(this);">设置图片</a> </div>';
		}
	}];
     ;
	_cityTable = initTables("courseCityTable",basePath+"/realClass/course/courseCityList",objCityData,true,true,0,null,searchCase_P,function(data){
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
        $("[name='cityUpa']").each(function(index){
            if(index == 0){
                $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
            }
        });
        $("[name='cityDowna']").each(function(index){
            if(index == $("[name='cityDowna']").size()-1){
                $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
            }
        });
		
	});
	/** 城市管理结束 */

	$("#updateRecImg-form").validate({
		messages: {
			icon: {
				required: "请选择图片！"
			}
		}
	});

    $('#recommendTime').datetimepicker({
        showSecond: true,
        changeMonth: true,
        changeYear: true,
        language: 'zh-CN',
        dateFormat:'yy-mm-dd',
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        timeFormat: 'HH:mm:ss',
        minDate:new Date(),
        onSelect: function( startDate ) {
        }
    });
});

$(".refresh-data").click(function(){
    ajaxRequest(basePath+"/cloudclass/course/initCoursesSolrData",null,function(data){
        if(data.success){//如果失败
            layer.msg(data.resultObject);
        }else{
            layer.msg(data.errorMessage);
        }
    });
});
/**
 * 城市列表上移
 * @param obj
 */
function cityUpMove(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = _cityTable.fnGetData(oo);
    ajaxRequest(basePath+'/cloudclass/course/cityUpMove',{"id":aData.id},function(res){
        if(res.success){
            freshTable(_cityTable);
        }else{
            layer.msg(res.errorMessage);
        }
    });
};

function showDetailDialog(obj,status){
	var oo = $(obj).parent().parent().parent();
	var aData,page;
		aData = P_courseTable.fnGetData(oo); // get datarow
        page = getCurrentPageNo(P_courseTable);
	window.location.href=basePath+'/home#cloudclass/course/courseDetail?courseId='+aData.id;
}

/**
 * 城市列表下移
 * @param obj
 */
function cityDownMove(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = _cityTable.fnGetData(oo);
    ajaxRequest(basePath+'/cloudclass/course/cityDownMove',{"id":aData.id},function(res){
        if(res.success){
            freshTable(_cityTable);
        }else{
            layer.msg(res.errorMessage);
        }
    });
};

function imgSenBut(){
	$("#imgAdd").html('<input type="file" name="img" id="smallingPathFile" class="uploadImg"/>');
};
function imgSenBut1(){
	$("#imgAdd1").html('<input type="file" name="img" id="detailImgPathFile" class="uploadImg"/>');
};
function imgSenBut2(){
	$("#imgAdd2").html('<input type="file" name="img" id="bigImgPathFile" class="uploadImg"/>');
};

function edit_imgSenBut(){
	$("#edit_imgAdd").html('<input type="file" name="img" id="edit_smallingPathFile" class="uploadImg"/>');
};
function edit_imgSenBut1(){
	$("#edit_imgAdd1").html('<input type="file" name="img" id="edit_detailImgPathFile" class="uploadImg"/>');
};
function edit_imgSenBut2(){
	$("#edit_imgAdd2").html('<input type="file" name="img" id="edit_bigImgPathFile" class="uploadImg"/>');
};


/**
 * 图片上传
 */
var _this;
//图片上传统一上传到附件中心
$("#addCourse-form").on("change","#smallingPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#smallingPath").val(data.url);
		  	document.getElementById("imgAdd").focus();
		  	document.getElementById("imgAdd").blur();
		  	$(".remove").hide();
		} else {
			layer.msg(data.message);
		}
	})
});
//图片上传统一上传到附件中心
$("#addCourse-form").on("change","#detailImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			
			$("#detailImgPath").val(data.url);
			document.getElementById("imgAdd1").focus();
			document.getElementById("imgAdd1").blur();
			$(".remove").hide();
		}else {
			layer.msg(data.message);
		}
	})
});
//图片上传统一上传到附件中心
$("#addCourse-form").on("change","#bigImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
	
		if (data.error == 0) {
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#bigImgPath").val(data.url);
		  	document.getElementById("imgAdd2").focus();
		  	document.getElementById("imgAdd2").blur();
		  	$(".remove").hide();
		}else {
			layer.msg(data.message);
		}
	})
});


//图片上传统一上传到附件中心---- 修改
$("#updateCourse-form").on("change","#edit_smallingPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
	
		if (data.error == 0) {
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#edit_smallingPath").val(data.url);
		  	document.getElementById("edit_imgAdd").focus();
		  	document.getElementById("edit_imgAdd").blur();
		  	$(".remove").hide();
		}else {
			layer.msg(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改
$("#updateCourse-form").on("change","#edit_detailImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
	
		if (data.error == 0) {
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#edit_detailImgPath").val(data.url);
		  	document.getElementById("edit_imgAdd1").focus();
		  	document.getElementById("edit_imgAdd1").blur();
		  	$(".remove").hide();
		}else {
			layer.msg(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改
$("#updateCourse-form").on("change","#edit_bigImgPathFile",function(){
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
		  	
		  	$("#edit_bigImgPath").val(data.url);
		  	document.getElementById("edit_imgAdd2").focus();
		  	document.getElementById("edit_imgAdd2").blur();
		  	$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
// zhuwenbao-2018-0109 图片上传统一上传到附件中心-新增（线下课）
$("#addCourse-form").on("change","#smallImgPath_file",function(){
    _this = this;

    // 添加唯一class用来区分用户点击的是确定按钮还是取消按钮
    $('.ui-dialog-buttonset .ui-button-text').eq(1).addClass('add_P_cancel');

    var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
    if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
        layer.msg("图片格式错误,请重新选择.");
        this.value="";
        return;
    }
    var id = $(this).attr("id");
    ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
        if (data.error == 0) {
        	console.log('上传成功');
            $("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
            $("#"+id).parent().find(".ace-file-name img").attr("src",data.url);

            $("#smallImgPath").val(data.url);
            document.getElementById("imgAdd").focus();
            document.getElementById("imgAdd").blur();
            $(".remove").hide();
        } else {
            layer.msg(data.message);
        }
    })
});
// zhuwenbao-2018-0109 图片上传统一上传到附件中心-修改（线下课）
$("#updateCourse-form").on("change","#smallImgPathFileEdit",function(){
    _this = this;

    // 添加唯一class用来区分用户点击的是确定按钮还是取消按钮
    $('.ui-dialog-buttonset .ui-button-text').eq(1).addClass('edit_P_cancel');

    var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
    if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
        layer.msg("图片格式错误,请重新选择.");
        this.value="";
        return;
    }
    var id = $(this).attr("id");
    ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
        if (data.error == 0) {
            console.log('上传成功');
            $("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
            $("#"+id).parent().find(".ace-file-name img").attr("src",data.url);

            $("#edid_smallImgPath").val(data.url);

            document.getElementById("imgAdd").focus();
            document.getElementById("imgAdd").blur();
            $(".remove").hide();
        } else {
            layer.msg(data.message);
        }
    })
});

// 防止 新增／修改职业课 上传好图片后 直接点取消或者右上角的x后 下次再新增职业课时出现上次上传好的图
$('#dialogAddCourseDiv').on("click",".ui-dialog-titlebar-close", function () {
    $(".ace-file-container").remove();
});

$('#dialogAddCourseDiv').on("click",".add_P_cancel", function () {
    $(".ace-file-container").remove();
});

$('#dialogEditCourseDiv').on("click",".ui-dialog-titlebar-close", function () {
    $(".ace-file-container").remove();
});

$('#dialogEditCourseDiv').on("click",".edit_P_cancel", function () {
    $(".ace-file-container").remove();
});

/**
 * 线下课列表搜索
 */
function search_P(){
    var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    json.push('{"tempMatchType":"9","propertyName":"search_isRecommend","propertyValue1":"2","tempType":"Integer"}');
    
	searchButton(P_courseTable,json);
};

/**
 * 城市推荐管理
 */
function search_City(){
	var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_city","propertyValue1":"'+$("#search_city").val()+'","tempType":"String"}');
	searchButton(_cityTable,json);
};


/**
 * 查看课程信息
 * @param obj
 * @param status（1，线下课，2：微课）
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
    $.get(basePath+"/cloudclass/course/findCourseById",{id:row.id}, function(result){
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
    	$("#show_courseLength").text(result[0].courseLength+"分钟"); //课程时长
    	$("#show_coursePwd").text(result[0].coursePwd); //课程时长
		$("#show_gradeQQ").text(result[0].gradeQQ); //班级QQ群
    	$("#show_qqno").text(result[0].qqno); //咨询QQ
    	$("#show_learndCount").text(result[0].defaultStudentCount); //默认报名人数
    	$("#show_actCount").text(row.actCount==null?0:row.actCount);//实际报名人数
    	$("#show_sum").text(result[0].defaultStudentCount*1+row.actCount*1);//累计报名人数
    	
    	$("#role_type1_show").html(row.lecturerName);//讲师
//    	$("#role_type1_show").html(row.role_type1);//讲师
//    	$("#role_type2_show").html(row.role_type2);//班主任
//    	$("#role_type3_show").html(row.role_type3);//助教
//    	
//    	$("#show_description").text(result[0].description); //包含班级
    	$("#show_createTime").text(result[0].createTime); //创建时间
        $("#show_startTime").text(result[0].startTime); //创建时间
        $("#show_endTime").text(result[0].endTime); //创建时间
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
    	
    	$("#show_address").text(result[0].address); //现价格
    	$("#show_courseDescribe").text(result[0].description); //课程简介
    	$("#show_cloudClassroom").text(result[0].cloudClassroom); //云课堂连接
    	
    	for(i=0;i<$("#view_mapList option").length;i++){
    		if($("#view_mapList option").eq(i).val()==result[0].userLecturerId){
    			
    			$("#show_userLecturerId").text($("#view_mapList option").eq(i).text());
    		}
         }
    });
	var prev_title="查看课程";
	if(status ==1){
		prev_title="查看线下课";
	}else{
		prev_title="查看微课程";
	}
	var dialog = openDialogNoBtnName("showCourseDialog","showCourseDiv",prev_title,530,600,false,"确定",null);
};

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj,status){
	var oo = $(obj).parent().parent().parent();
	var row;
	if(status==1) {
		row = P_courseTable.fnGetData(oo); // get datarow
	}else{
		row = M_courseTable.fnGetData(oo); // get datarow
	}
	ajaxRequest(basePath+"/realClass/course/updateStatus",{"id":row.id},function(){
		if(status==1) {
			freshTable(P_courseTable);
		}else{
			freshTable(M_courseTable);
		}
	});
};



/**
 * Description：设置推荐值
 * @Date: 2018/3/9 14:11
 **/
function updateRecommendSort(obj,key){
    var row="";
    var oo = $(obj).parent().parent().parent();
    if(key==1){
        row = P_courseTable.fnGetData(oo);
	}else{
        row = _courseRecTable.fnGetData(oo); // get datarow
	}
    $("#UpdateRecommendSort_id").val(row.id);
    var dialog = openDialog("UpdateRecommendSortDialog","dialogUpdateRecommendSortDiv","修改推荐值",350,300,true,"确定",function(){
        if($("#UpdateRecommendSortFrom").valid()){
            mask();
            $("#UpdateRecommendSortFrom").attr("action", basePath+"/cloudclass/course/updateRecommendSort");
            $("#UpdateRecommendSortFrom").ajaxSubmit(function(data){
                data = getJsonData(data);
                unmask();
                if(data.success){
                	$("#recommendSort").val("");
                    $("#recommendTime").val("");
                    $("#UpdateRecommendSortDialog").dialog("close");
                    layer.msg(data.resultObject);
                    if(key==1){
                        freshTable(P_courseTable);
                    }else{
                        freshTable(_courseRecTable);
                    }
                }else{
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
};

/**
 * 设置图片
 * @param obj
 */
function updateRecImg(obj){
	; //TODO
	var oo = $(obj).parent().parent().parent();
	var row = _cityTable.fnGetData(oo); // get datarow
	
	$(".clearfixUpdate").remove();
 	$("#updateRecImg").prepend("<div class=\"clearfixUpdate\">\n" +
						  "	<input type=\"file\" name=\"update_recImgPath_file\" id=\"update_recImgPath_file\" class=\"uploadImg\"/>\n" +
						  "</div>");
 	
 	createImageUpload($('.uploadImg'));//生成图片编辑器

	$("#update_id").val(row.id);
	$("#update_recImgPath").val(row.recImgPath);
	
	if(row.recImgPath != null && row.recImgPath != ""){
		reviewImageRecImg("update_recImgPath_file",row.recImgPath);
		$(".remove").hide();
	}
	var dialog = openDialog("updateRecImgDialog","dialogUpdateRecImgDiv","设置课程展示图",580,330,true,"确定",function(){
 		if($("#updateRecImg-form").valid()){
 			 mask();
 			 $("#updateRecImg-form").attr("action", basePath+"/realClass/course/courseCityUpdate");
 	            $("#updateRecImg-form").ajaxSubmit(function(data){
 	            	data = getJsonData(data);
 	                unmask();
 	                ;
 	                if(data.success){
 	                    $("#updateRecImgDialog").dialog("close");
 	                    layer.msg("修改成功");
 	                    freshTable(_cityTable);
 	                }else{
 	                	layer.msg(data.errorMessage);
 	               }
 	         });
 		}
 	});
};

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

/**
 * 逻辑删除
 * @param obj
 */
function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var row = P_courseTable.fnGetData(oo); // get datarow
	showDelDialog(function(){
		mask();
		ajaxRequest(basePath+"/cloudclass/course/deleteCourseById",{"id":row.id},function(data){
			if(!data.success){
				//alertInfo(data.errorMessage);
				layer.msg(data.errorMessage);
			}else{
					layer.msg(data.resultObject);
					freshDelTable(P_courseTable);
			}
			unmask();
			
		});
	});
	
}
/**
 * 线下课批量逻辑删除
 * 
 */

$(".dele_P").click(function(){
	deleteAll(basePath+"/cloudclass/course/deletes",P_courseTable,null,"删除操作不可逆，对于已报名用户不会产生影响，是否确认删除该课程？");
});
/**
 * 微课批量逻辑删除
 *
 */

$(".dele_M").click(function(){
    deleteAll(basePath+"/cloudclass/course/deletes",M_courseTable);
});


/**
 * 城市批量推荐
 *
 */
$(".city_rec").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){

        if($(trs[i]).parent().parent().find("[name='sftj']").eq("0").text() == "已推荐")
        {
            showDelDialog("","","无法推荐已推荐城市！","");
            return false;
        }
        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/cloudclass/course/updateCityRec",{'ids':ids.join(","),"isRec":1},function(data){
            if(!data.success){//如果失败
                //alertInfo(data.errorMessage);
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(_cityTable)){
                    layer.msg("推荐成功！");
                    search_City();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择推荐城市！","");
    }
});
/**
 * 城市取消推荐
 *
 */
$(".city_qx_rec").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){

        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/cloudclass/course/updateCityRec",{'ids':ids.join(","),"isRec":0},function(data){
            if(!data.success){//如果失败
                //alertInfo(data.errorMessage);
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(_cityTable)){
                    layer.msg("取消成功！");
                    search_City();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择未推荐城市！","");
    }
});


/**
 * 展示线下课管理
 * 
 */
$(".xxpxb_bx").click(function(){
	$("#courseDiv").show();
	$("#courseRecDiv").hide();
	$("#courseCityDiv").hide();
	freshTable(P_courseTable);
});

/**
 * 城市推荐管理
 */
$(".city_bx").click(function(){
	$("#courseDiv").hide();
	$("#courseRecDiv").hide();
	$("#courseCityDiv").show();
	
	/*
	 * 点击这个地方时，就要把对应的城市刷新下
	 */

	freshTable(_cityTable);
});


function test(){
	$(".libox").css("display","block");
}



function showCourseInfoDetail(obj, status) {

    var oo = $(obj).parent().parent().parent();
    var aData;
    if (status == 1) {
        aData = P_courseTable.fnGetData(oo); // get datarow
    }
    window.location.href = basePath + '/home#cloudclass/course/courseInfoDetail?id=' + aData.id;
}



