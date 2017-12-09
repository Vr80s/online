var P_courseTable;//医馆列表
var M_courseTable;//微课列表
var PX_courseTable;//课程排序列表
var courseForm;//添加课程表单
var updateCourseForm;//修改课程表单
var studyDayForm;//设置学习计划模板表单

var _courseRecTable;//课程推荐列表

$(function(){
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	//debugger;
	/** 医馆列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    searchCase_P.push('{"tempMatchType":undefined,"propertyName":"type","propertyValue1":"'+$("#type").val()+'","tempType":undefined}');
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    // { "title": "医馆ID", "class": "center","width":"5%","sortable": false,"data":"id" },
    { "title": "医馆名", "class":"center","width":"9%","sortable":false,"data": 'name' },
    { "title": "经纬度", "class":"center","width":"8%","sortable":false,"data": 'lal'},
    { "title": "联系电话", "class":"center","width":"6%", "sortable":false,"data": 'tel',"visible":true},
    { "title": "email", "class":"center","width":"6%", "sortable":false,"data": 'email',"visible":true},
    { "title": "地址", "class":"center","width":"8%", "sortable":false,"data": 'detailedAddress',"visible":true,"mRender":function (data, display, row) {
        debugger
    	return row.province+"-"+row.city+"-"+row.detailedAddress;
    }}, { "title": "创建日期", "class":"center","width":"8%","sortable":false,"data": 'createTime','mRender':function(data, display, row){
                return getLocalTime(data);
            }
            },
    { "title": "医馆照片", "class":"center","width":"6%","sortable":false,"data": 'hasPicture',"mRender":function (data, display, row) {
    	if(data){
    		return data="<span name='zt'>已上传</span>";
    	}else{
    		return data="<span name='zt'>待补充</span>";
    	}
    } },
    { "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return data="<span name='zt'>已启用</span>";
    	}else{
    		return data="<span name='zt'>已禁用</span>";
    	}
    } },
    /*{"sortable": false,"class": "center","width":"5%","title":"排序","mRender":function (data, display, row) {
//    	debugger;
//    	if(row.status=="1"){
    		return '<div class="hidden-sm hidden-xs action-buttons">'+
    		'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)" name="up_PX"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
    		'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)" name="down_PX"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
//    	}else{
//    		return '<div class="hidden-sm hidden-xs action-buttons">'+
//    		'<a class="gray" href="javascript:void(-1);" title="上移" name="up_PX"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
//    		'<a class="gray" href="javascript:void(-1);" title="下移" name="down_PX"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
//    	}
	}},*/
    { "sortable": false,"class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
	    	if(row.status){
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
			    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this,1)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '+
				'<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'
	    	}else{
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
			    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this,1)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '+
				'<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'
	    	}
	    }
	}];
	
	P_courseTable = initTables("courseTable",basePath+"/medical/hospital/list",objData,true,true,0,null,searchCase_P,function(data){
		// debugger;
		// var iDisplayStart = data._iDisplayStart;
		// var countNum = data._iRecordsTotal;//总条数
		// pageSize = data._iDisplayLength;//每页显示条数
		// currentPage = iDisplayStart / pageSize +1;//页码
		// if(currentPage == 1){//第一页的第一行隐藏向上箭头
		// 	$("#courseTable tbody tr:first td").eq(7).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
		// }
		// if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
		// 	$("#courseTable tbody tr:last td").eq(7).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		// }
		// var countPage;
		// if(countNum%pageSize == 0){
		// 	countPage = parseInt(countNum/pageSize);
		// }else{
		// 	countPage = parseInt(countNum/pageSize) + 1;
		// }
		// if(countPage == currentPage){//隐藏最后一条数据下移
		// 	$("#courseTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		// }
	});
	/** 医馆列表end */

	/** 表单验证START */
	studyDayForm = $("#studyDay-form").validate({
		messages: {
			totalDay : {
				required : "请填写学习计划模板天数"
			}
		}
	});
	courseForm = $("#addCourse-form").validate({
        rules: {
            email: {
                required: false,
                email: true
            },
            tel: {
                required: true,
                // tel: true
            }
        },
        messages: {
            name: {
                required:"请输入医馆名称！",
                minlength:"医馆名称过短，应大于2个字符！",
                maxlength:"医馆名称过长，应小于20个字符！"
            },
            tel: {
                required:"请输入联系方式！",
                tel:"请输入正确的联系方式！"
            },
            lal: {
                // required:"请输入联系方式！",
                tel:"请输入正确的经纬度！"
            },
            email: {
                email: "请输入正确的邮箱！"
            },
            province: {
                required:"请选择省市！"
            },
            detailedAddress: {
                required:"请输入详细地址！"
            },
            courseLength: {
                required:"请输入课程时长！",
                digits: "课程时长必须为整数"
            },
            description: {
                required:"医馆简介不能为空！"
            }
        }
	});
	updateCourseForm = $("#updateCourse-form").validate({
        rules: {
            email: {
                required: false,
                email: true
            },
			tel: {
                required: true,
                // tel: true
            }
        },
		messages: {
            name: {
				required:"请输入医馆名称！",
				minlength:"医馆名称过短，应大于2个字符！",
				maxlength:"医馆名称过长，应小于20个字符！"
			},
            tel: {
				required:"请输入联系方式！",
				tel:"请输入正确的联系方式！"
			},
            lal: {
				// required:"请输入联系方式！",
				tel:"请输入正确的经纬度！"
			},
            email: {
                email: "请输入正确的邮箱！"
			},
			province: {
				required:"请选择省市！"
			},
			detailedAddress: {
				required:"请输入详细地址！"
			},
			courseLength: {
				required:"请输入课程时长！",
				digits: "课程时长必须为整数"
			},
			description: {
				required:"医馆简介不能为空！"
			}
		}
	});
    // 手机号码验证
    jQuery.validator.addMethod("tel", function(value, element) {
        var length = value.length;
        var mobile = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,8})?$)|(^0?1[35]\d{9}$)/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");
    // 经纬度验证
    // jQuery.validator.addMethod("lal", function(value, element) {
    //     var length = value.length;
    //     var mobile = /[^\d{1,}\.\d{1,}|\d{1,}]/g;
    //     return this.optional(element) || (length == 11 && mobile.test(value));
    // }, "请输入正确的经纬度");
	/** 表单验证END */

	//TODO
	
	//新增根据一级菜单获取相应的二级菜单 暂时用不上，现在没有级联菜单
	$('#menuName').change(function(){
		var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
		$.ajax({
			type:'post',
			url:basePath+'/cloudclass/course/getSecoundMenu',
			data:{firstMenuNumber:firstMenuNumber},
			dataType:'json',
			success:function(data){
				var optionstring = "";
				for (var j = 0; j < data.length; j++) {
					optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";
				}
				$("#menuNameSecond").html("<option value=''>请选择</option> "+optionstring);
			}
		}) ;
	}) ;

	//编辑根据一级菜单获取相应的二级菜单
	$('#edid_menuName').change(function () {
		var firstMenuNumber = $(this).children('option:selected').val();//这就是selected的值
		$.ajax({
			type: 'post',
			url: basePath + '/cloudclass/course/getSecoundMenu',
			data: {firstMenuNumber: firstMenuNumber},
			dataType: 'json',
			success: function (data) {
				var optionstring = "";
				for (var j = 0; j < data.length; j++) {
					optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";
				}
				$("#edid_menuNameSecond").html("<option value=''>请选择</option> " + optionstring);
			}
		});
	});

	$('#search_menu').change(function () {
		var firstMenuNumber = $(this).children('option:selected').val();//这就是selected的值
		$.ajax({
			type: 'post',
			url: basePath + '/cloudclass/course/getSecoundMenu',
			data: {firstMenuNumber: firstMenuNumber},
			dataType: 'json',
			success: function (data) {
				var optionstring = "";
				for (var j = 0; j < data.length; j++) {
					optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";
				}
				$("#search_scoreType").html("<option value=''>请选择</option> " + optionstring);
			}
		});
	});

	$("#updateRecImg-form").validate({
		messages: {
			recImgPath: {
				required: "请选择图片！"
			}
		}
	});
	
	/*$("#add-originalCost").hide();
	$("#add-currentPrice").hide();*/
	createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
});



/**
 * 课程排序列表上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = P_courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/medical/hospital/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(P_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 课程推荐列表上移
 * @param obj
 */
function upMoveRec(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseRecTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudclass/course/upMoveRec',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseRecTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

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
	window.location.href=basePath+'/home#medical/hospital/hospitalDetail?page='+page+'&courseId='+aData.id;
}



/**
 * 课程排序列表下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = P_courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/medical/hospital/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(P_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 课程推荐列表下移
 * @param obj
 */
function downMoveRec(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseRecTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudclass/course/downMoveRec',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseRecTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

$(".add_P").click(function(){
    /**
     * 添加医馆
     */
// $("input[name='isFree']").eq(1).attr("checked","checked");
    courseForm.resetForm();
    debugger

	$("#province option:first").prop("selected", 'selected');
	var dialog = openDialog("addCourseDialog","dialogAddCourseDiv","新增医馆",580,600,true,"确定",function(){
		// $("#descriptionHid").val($("#courseDescribe").val());
        $("#realProvince").val($("#province").find("option:selected").text());
        $("#realCitys").val($("#citys").find("option:selected").text());
		if($("#addCourse-form").valid()){
			mask();
			 $("#addCourse-form").attr("action", basePath+"/medical/hospital/add");
	            $("#addCourse-form").ajaxSubmit(function(data){
	            	try{
                		data = jQuery.parseJSON(jQuery(data).text());
                	}catch(e) {
                		data = data;
                	  }
                	unmask();
	                if(data.success){
	                    $("#addCourseDialog").dialog("close");
	                    layer.msg(data.errorMessage);
	                    freshTable(P_courseTable);
	                    $("html").css("overflow","auto");
	                }else{
	                	layer.msg(data.errorMessage);
	                }
	            });
		}
	});
});

//新增 -- 当选择付费时，显示原价格和现价格
$("#is_free").click(function(){
	$("#add-originalCost").show();
	$("#add-currentPrice").show();
	
	$('#originalCost').attr("disabled",false);
	$("#currentPrice").attr("disabled",false);
	$("#originalCost").val("");
	$("#currentPrice").val("");
	
//	$("#originalCost").addClass("col-xs-10 col-sm-12 {required:true,number:true,range:[0,999999.99]}");
//	$("#currentPrice").addClass("col-xs-10 col-sm-12 {required:true,number:true,range:[0,999999.99]}");
});
//新增 -- 当选择免费时，隐藏原价格和现价格
$("#no_free").click(function(){
	$("#add-originalCost").hide();
	$("#add-currentPrice").hide();
	
	$("#originalCost").val(1);
	$("#currentPrice").val(1);
	$('#originalCost').attr("disabled",true);
	$("#currentPrice").attr("disabled",true);
//	$("#originalCost").removeClass("col-xs-10 col-sm-12 {required:true,number:true,range:[0,999999.99]}");
//	$("#currentPrice").removeClass("col-xs-10 col-sm-12 {required:true,number:true,range:[0,999999.99]}");
//	$("#originalCost").addClass("col-xs-10 col-sm-12");
//	$("#currentPrice").addClass("col-xs-10 col-sm-12");
});

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

/**
 * 医馆列表搜索
 */
function search_P(){
    var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
	searchButton(P_courseTable,json);
};
// /**
//  * 微课列表搜索
//  */
// function search_M(){
// 	var json = new Array();
//     json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"1","tempType":"String"}');
// 	$("#searchDiv_M .searchTr").each(function() {
// 		if (!isnull($(this).find('.propertyValue1').val())) {
// 			var propertyValue2 = $(this).find('.propertyValue2').val();
// 			if(!isnull(propertyValue2)){
// 				json.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
// 					+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
// 					+',"propertyValue2":"'+propertyValue2+'"}');
// 			}else{
// 				json.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
// 					+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
// 			}
// 		}
// 	});
// 	var str = "[" + json.join(",") + "]";
// 	M_courseTable.fnFilter(str);
// };
/**
 * 课程排序列表搜索
 */
function search_PX(){
	var json = new Array();
	$("#searchDiv_PX .searchTr").each(function() {
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
	PX_courseTable.fnFilter(str);
};


/**
 * 查看课程信息
 * @param obj
 * @param status（1，医馆，2：微课）
 */
function previewDialog(obj,status){
	var oo = $(obj).parent().parent().parent();
	var row;
	if(status==1) {
		row = P_courseTable.fnGetData(oo); // get datarow
	}else{
		row = M_courseTable.fnGetData(oo); // get datarow
	}
	
	debugger
	//根据当前id查找对应的课程信息
    $.get(basePath+"/medical/hospital/findMedicalHospitalById",{id:row.id}, function(result){

    	$("#show_name").text(result.name);
    	$("#show_lal").text(result.lal);
    	$("#show_tel").text(result.tel);
    	$("#show_postCode").text(result.postCode);
    	$("#show_email").text(result.email);
    	$("#show_province").text(result.province);
    	$("#show_city").text(result.city);
    	$("#show_detailedAddress").text(result.detailedAddress);
    	$("#show_description").text(result.description); //课程简介

    });
	var prev_title="查看课程";
	if(status ==1){
		prev_title="查看医馆";
	}else{
		prev_title="查看微课程";
	}
	var dialog = openDialogNoBtnName("showCourseDialog","showCourseDiv",prev_title,530,600,false,"确定",null);
};


/**
 * 修改表单
 * @param obj
 * @param status（1：医馆，2：微课）
 */
function toEdit(obj,status){
    debugger
	updateCourseForm.resetForm();
	
	var oo = $(obj).parent().parent().parent();
	var row;
    row = P_courseTable.fnGetData(oo); // get datarow
	$("#updateCourse-form").resetForm();
	// $("#updateCourse-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
	//根据当前id查找对应的课程信息
    $.get(basePath+"/medical/hospital/findMedicalHospitalById",{id:row.id}, function(result){
    	$("#editHospital_id").val(row.id);
    	debugger;
    		//省
    		$("#edit_citys").empty();
    		
    		$('#edit_province option:contains(' + result.province + ')').each(function(){
    		  if ($(this).text() == result.province) {
    		     $(this).prop("selected", 'selected');
    		  }
    		});
    		//市
        	doProvAndCityRelationEdit();
        $('#edit_citys option:contains(' + result.city + ')').each(function(){
            if ($(this).text() == result.city) {
                $(this).prop("selected", 'selected');
            }
        });
    		//地点
    		$("#edit_address").val(result.detailedAddress);
    		$("#edit_realProvince").val(result.province);

			$("#edit_name").val(result.name);
			$("#edit_lal").val(result.lal);
			$("#edit_tel").val(result.tel);
			$("#edit_postCode").val(result.postCode);
			$("#edit_email").val(result.email);
			$("#edit_city").val(result.city);
			$("#edit_detailedAddress").val(result.detailedAddress);
			$("#edit_description").html(result.description); //课程简介


    	var edit_title="修改医馆";
    	var dialog = openDialog("EditCourseDialog","dialogEditCourseDiv",edit_title,580,650,true,"确定",function(){
    		debugger
            $("#edit_realProvince").val($("#edit_province").find("option:selected").text());
            $("#edit_realCitys").val($("#edit_citys").find("option:selected").text());

            $("#edid_descriptionHid").val($("#edid_courseDescribe").val());
    		if($("#updateCourse-form").valid()){
                mask();
                $("#updateCourse-form").attr("action", basePath+"/medical/hospital/updateMedicalHospitalById");
                $("#updateCourse-form").ajaxSubmit(function(data){
                	try{
                		data = jQuery.parseJSON(jQuery(data).text());
                	}catch(e) {
                		data = data;
                	  }
                
                    unmask();
                    if(data.success){
                    	
                        $("#EditCourseDialog").dialog("close");
                        layer.msg(data.errorMessage);
                        if(edit_title=='修改医馆'){
                        	freshTable(P_courseTable);	
                        }else{
                        	freshTable(M_courseTable);
                        }
                         
                    }else{
                    	 layer.msg(data.errorMessage);
                    }
                });
            }
	    });
    });
    
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
	ajaxRequest(basePath+"/medical/hospital/updateStatus",{"id":row.id},function(){
		if(status==1) {
			freshTable(P_courseTable);
		}else{
			freshTable(M_courseTable);
		}
	});
};

/**
 * 设置图片
 * @param obj
 */
function updateRecImg(obj){
	var oo = $(obj).parent().parent().parent();
	var row = _courseRecTable.fnGetData(oo); // get datarow
	
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
 			 $("#updateRecImg-form").attr("action", basePath+"/cloudclass/course/updateRecImgPath");
 	            $("#updateRecImg-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                if(data.success){
 	                    $("#updateRecImgDialog").dialog("close");
 	                    layer.msg(data.errorMessage);
 	                    freshTable(_courseRecTable);
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
 * 医馆批量逻辑删除
 * 
 */

$(".dele_P").click(function(){
	deleteAll(basePath+"/medical/hospital/deletes",P_courseTable,null,"删除操作不可逆，是否确认删除该医馆？");
});


function test(){
	$(".libox").css("display","block");
}

$(".list-items1").on("click",function(event){
  event.stopPropagation();
  var target2=event.target;
  if($(target2).is("span")){
      $(target2).parent().find(".list-items2").toggle();
      sanjiaoChangeStatus(target2);
  }
})



(function() {
	initTeacher();
})();
function initTeacher(){
	$=jQuery;
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

}

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}

