var courseDetailForm;
var _courseRecTable;
var _courseTable;
var tempDesId = "";//用于判断切换保存的变量
var ueEditccpe_lecturer =  UE.getEditor('lecturerDescription_content',{
	toolbars:[['source', //源代码
	           'undo', //撤销
	           'redo', //重做
	           'bold', //加粗
	           'forecolor', //字体颜色
	           'backcolor', //背景色
	           'indent', //首行缩进
	           'removeformat',//清除格式
	           'formatmatch', //格式刷
	           'blockquote', //引用
	           'fontfamily', //字体
	           'fontsize', //字号
	           'paragraph', //段落格式
	           'italic', //斜体
	           'underline', //下划线
	           'strikethrough', //删除线
	           'superscript', //上标
	           'subscript', //下标
	           'touppercase', //字母大写
	           'tolowercase', //字母小写
	           'justifyleft', //居左对齐
	           'justifyright', //居右对齐
	           'justifycenter', //居中对齐
	           'justifyjustify',//两端对齐
	           'link', //超链接
	           'unlink', //取消链接
	           'simpleupload', //单图上传
	           'insertimage', //多图上传
	           'emotion', //表情
	           ] ],
	           autoHeightEnabled: false,
	           autoFloatEnabled: true,
	           enableAutoSave:false,
	           imagePopup:false
});
var ueEditccpe_content =  UE.getEditor('courseDetail_content',{
	toolbars:[['source', //源代码
	           'undo', //撤销
	           'redo', //重做
	           'bold', //加粗
	           'forecolor', //字体颜色
	           'backcolor', //背景色
	           'indent', //首行缩进
	           'removeformat',//清除格式
	           'formatmatch', //格式刷
	           'blockquote', //引用
	           'fontfamily', //字体
	           'fontsize', //字号
	           'paragraph', //段落格式
	           'italic', //斜体
	           'underline', //下划线
	           'strikethrough', //删除线
	           'superscript', //上标
	           'subscript', //下标
	           'touppercase', //字母大写
	           'tolowercase', //字母小写
	           'justifyleft', //居左对齐
	           'justifyright', //居右对齐
	           'justifycenter', //居中对齐
	           'justifyjustify',//两端对齐
	           'link', //超链接
	           'unlink', //取消链接
	           'simpleupload', //单图上传
	           'insertimage', //多图上传
	           'emotion', //表情
	           ] ],
	           autoHeightEnabled: false,
	           autoFloatEnabled: true,
	           enableAutoSave:false,
	           imagePopup:false
});
var ueEditccpe_commonProblem_content =  UE.getEditor('commonProblem_content',{
	toolbars:[['source', //源代码
	           'undo', //撤销
	           'redo', //重做
	           'bold', //加粗
	           'forecolor', //字体颜色
	           'backcolor', //背景色
	           'indent', //首行缩进
	           'removeformat',//清除格式
	           'formatmatch', //格式刷
	           'blockquote', //引用
	           'fontfamily', //字体
	           'fontsize', //字号
	           'paragraph', //段落格式
	           'italic', //斜体
	           'underline', //下划线
	           'strikethrough', //删除线
	           'superscript', //上标
	           'subscript', //下标
	           'touppercase', //字母大写
	           'tolowercase', //字母小写
	           'justifyleft', //居左对齐
	           'justifyright', //居右对齐
	           'justifycenter', //居中对齐
	           'justifyjustify',//两端对齐
	           'link', //超链接
	           'unlink', //取消链接
	           'simpleupload', //单图上传
	           'insertimage', //多图上传
	           'emotion', //表情
	           ] ],
	           autoHeightEnabled: false,
	           autoFloatEnabled: true,
	           enableAutoSave:false,
	           imagePopup:false
});

$(function() {
	debugger
	$('#myTab a').click(function (e) {
	  e.preventDefault();
	  $(this).tab('show');
	  $("html").eq(0).css("overflow","scroll");
	})

	$('#myTab a[href="#profile"]').tab('show') // Select tab by name
	$('#myTab a:first').tab('show') // Select first tab
	$('#myTab a:last').tab('show') // Select last tab
	$('#myTab li:eq(2) a').tab('show') // Select third tab (0-indexed)

	document.onkeydown=function(event){

	}


	courseDetailForm = $("#courseDetailForm").validate({
		messages : {
			smallImgPath:{
				required:"列表页图不能为空！"
			},detailImgPath:{
				required:"详情页图不能为空！"
			},courseDetail:{
				required:"课程详情内容不能为空！",
				minlength:"课程详情内容不能为空！",
			},courseOutline:{
				required:"课程大纲内容不能为空！",
				minlength:"课程大纲内容不能为空！"
			},commonProblem:{
				required:"常见问题不能为空！",
				minlength:"常见问题不能为空！"
			}
		}
	});

	$("#addDes-form").validate({
		messages : {
			courseTitle:{
				required:"故事标题不能为空！"
			}
		}
	});
	$("#updateDes-form").validate({
		messages : {
			courseTitle:{
				required:"故事标题不能为空！"
			}
		}
	});

	$.ajax({
		url:basePath+'/cloudclass/course/getCourseDetail',
		dataType:'json',
		type:'post',
		data:{courseId:$('#courseId').val()},
		success:function(data){
			if (data.success) {
				debugger;
				if(data.resultObject.smallImgPath && data.resultObject.smallImgPath != ''){
					$('#edit_smallImgPath').val(data.resultObject.smallImgPath);
					reviewImage("edit_smallImgPath", data.resultObject.smallImgPath);
				}
				/*2017-08-14---yuruixin*/
				if(data.resultObject.smallImgPath1 && data.resultObject.smallImgPath1 != ''){
					$('#edit_smallImgPath1').val(data.resultObject.smallImgPath1);
					reviewImage("edit_smallImgPath1", data.resultObject.smallImgPath1);
				}
				if(data.resultObject.smallImgPath2 && data.resultObject.smallImgPath2 != ''){
					$('#edit_smallImgPath2').val(data.resultObject.smallImgPath2);
					reviewImage("edit_smallImgPath2", data.resultObject.smallImgPath2);
				}
				/*2017-08-14---yuruixin*/
				if(data.resultObject.detailImgPath && data.resultObject.detailImgPath != ''){
					$('#edit_detailImgPath').val(data.resultObject.detailImgPath);
					reviewImage("edit_detailImgPath", data.resultObject.detailImgPath);
				}
				debugger
				if(data.resultObject.lecturerDescription && data.resultObject.lecturerDescription != ''){
//					$('#courseDetail_content').html(data.resultObject.courseDetail);
                    ueEditccpe_lecturer.ready(function(){
                        ueEditccpe_lecturer.setContent(data.resultObject.lecturerDescription);
					});
					$('#courseDetail').html(data.resultObject.courseDetail);
				}
                if(data.resultObject.courseDetail && data.resultObject.courseDetail != ''){
//					$('#courseDetail_content').html(data.resultObject.courseDetail);
                    ueEditccpe_content.ready(function(){
                        ueEditccpe_content.setContent(data.resultObject.courseDetail);
                    });
                    $('#courseDetail').html(data.resultObject.courseDetail);
                }
				if(data.resultObject.commonProblem && data.resultObject.commonProblem != ''){
					ueEditccpe_commonProblem_content.ready(function(){
						ueEditccpe_commonProblem_content.setContent(data.resultObject.commonProblem);
					});
//					$('#commonProblem_content').html(data.resultObject.commonProblem);
					$('#commonProblem').html(data.resultObject.commonProblem);
				} else {
					initTemplate('problem','commonProblem_content','commonProblem');
				}
				if(data.resultObject.descriptionShow==1){//1是展示
					$("#descriptionShow").prop("checked",true);
				}else{
					$("#descriptionShow").prop("checked",false);
				}
				$("#titleXQ").text(data.resultObject.gradeName);
				$(".remove").hide();
			}
		}
	});

	function initTemplate(type,contentid,id){
		$.ajax({
			url:basePath+'/cloudclass/course/getTemplate',
			dataType:'json',
			type:'get',
			data:{type:type},
			success:function(data){
				if(data.success){
					switch (type) {
					case "outline":
						ueEditccpe_courseOutline_content.ready(function(){
							ueEditccpe_courseOutline_content.setContent(data.resultObject);
						});
						break;
					case "problem":
						ueEditccpe_commonProblem_content.ready(function(){
							ueEditccpe_commonProblem_content.setContent(data.resultObject);
						});
						break;

					default:
						break;
					}
//					$('#'+contentid).html(data.resultObject);
					$('#'+id).html(data.resultObject);
				}
			}
		});
	}

	createImageUpload($('.uploadImg'));//生成图片编辑器

	//这里开始加载第三个Tab页的东西
	var objRecData = [
      { "title": "序号", "class": "center","width":"5%","sortable": false,"data":"id" },
      { "title": "课程名称", "class":"center","width":"33%","sortable":false,"data": 'courseName' },
      { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'xMenuName' },
      { "title": "所属课程类别", "class":"center","width":"9%","sortable":false,"data": 'scoreTypeName' },
//      { "title": "授课方式", "class":"center","width":"10%","sortable":false,"data": 'teachMethodName' },
      { "title": "课程时长", "class":"center","width":"8%", "sortable":false,"data": 'courseLength'},
      { "title": "原价格/现价格", "class":"center","width":"9%","sortable":false,"mRender":function(data,display,row){
      	return data = row.originalCost+"/"+row.currentPrice;
      }},
      {"sortable": false,"class": "center","width":"10%","title":"排序","mRender":function (data, display, row) {
      	return '<div class="hidden-sm hidden-xs action-buttons">'+
  		'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMoveRec(this)" name="upa"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
      	'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMoveRec(this)" name="downa"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
  	}},
      { "sortable": false,"class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {
      		return '<div class="hidden-sm hidden-xs action-buttons"><input type="hidden" name="realRecCourseId" value="'+row.showCourseId+'">'+
  			'<a class="blue" href="javascript:void(-1);" title="取消推荐" onclick="deletes(\''+row.id+'\');">取消推荐</a> </div>';
      }
  	}];

	var searchCaseRecTable = new Array();
	$("#searchDivTj .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				searchCaseRecTable.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
						+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				searchCaseRecTable.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});

	_courseRecTable = initTables("courseRecTable",basePath+"/cloudClass/courseRecommend/recList",objRecData,true,false,1,null,searchCaseRecTable,function(data){
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
		$("#courseRecTable_info").hide();
	});

//	 //重载方法，实现图片等附件传到我们的附件中心

    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
    	var url = '/ueditor/upload'
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return url;
        } else if (action == 'uploadvideo') {//视频上传：
            return url;
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }

});

$('#okbt,#previewbt').on('click',function(e){
	var id = $(e.currentTarget).attr('id');
	var methodName = 'updateCourseDetail';
	// var methodName = id=='previewbt' ? 'addPreview' : 'updateCourseDetail';
	$('#courseDetail').val(ueEditccpe_content.getContent());
	$('#lecturerDescription').val(ueEditccpe_lecturer.getContent());
	$('#commonProblem').val(ueEditccpe_commonProblem_content.getContent());
	debugger
	var validate = id=='previewbt' ? true : $("#courseDetailForm").valid();
	if(validate){
		mask();
		$("#courseDetailForm").attr("action", basePath+"/cloudclass/course/"+methodName);
		$("#courseDetailForm").ajaxSubmit(function(data){
			unmask();
            data = getJsonData(data);
			if(data.success){
				alertInfo("保存成功！");
				$("html").eq(0).css("overflow","scroll");
			}else{
				layer.msg(data.errorMessage);
				$("html").eq(0).css("overflow","scroll");
			}
		});
	}

});

$('#cancelbt,#returnbutton,#returnbutton2').on('click',function(){
	window.location.href=basePath+'/home#cloudclass/course/index?page='+$('#page').val();
});

//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file",function(){
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

			$("#edit_smallImgPath").val(data.url);
			document.getElementById("smallImgPath_file").focus();
			document.getElementById("smallImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改 详情页
$("#courseDetailForm").on("change","#detailImgPath_file",function(){
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
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");

		  	$("#edit_detailImgPath").val(data.url);
		  	document.getElementById("detailImgPath_file").focus();
		  	document.getElementById("detailImgPath_file").blur();
		  	$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file",function(){
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
			
			$("#edit_smallImgPath").val(data.url);
			document.getElementById("smallImgPath_file").focus();
			document.getElementById("smallImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file1",function(){
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
			
			$("#edit_smallImgPath1").val(data.url);
			document.getElementById("smallImgPath_file1").focus();
			document.getElementById("smallImgPath_file1").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file2",function(){
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
			
			$("#edit_smallImgPath2").val(data.url);
			document.getElementById("smallImgPath_file2").focus();
			document.getElementById("smallImgPath_file2").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改 详情页
$("#courseDetailForm").on("change","#detailImgPath_file",function(){
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
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_detailImgPath").val(data.url);
			document.getElementById("detailImgPath_file").focus();
			document.getElementById("detailImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});


// $("#tjgsbt").on("click",function(){//点击添加UL
//
// 	$("#courseTitle").val("");
// 	//先从后台插入一条数据
// 	//然后重新查询出所有的数据进行遍历
// 	var dialog = openDialog("desDialog","dialogDesDiv","添加故事标题",580,200,true,"确定",function(){
//
// 		if($("#titleUl input[name='desId']").length>=9){
// 			alert("最多只能添加九个故事标题！");
// 			return false;
// 		}
//
// 		if($("#addDes-form").valid()){
//
// 			//把上个保存了
// 			if(tempDesId != "" && tempDesId != null&& $("#desConHid"+tempDesId).val() != ueEditccpe.getContent()){//必须编辑过才保存
// 				$("#update_desId").val(tempDesId);
// 				$("#update_courseTitle").val($("#desTitleHid"+tempDesId).val());
// 				$("#update_courseContent").val(ueEditccpe.getContent());
//
// 				if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
// 					$("#update_status").val(1);
// 				}else{
// 					$("#update_status").val(0);
// 				}
// 				$("#updateDes-form").attr("action", basePath+"/cloudClass/courseDescription/updateCourseDescriptionById");
// 		        $("#updateDes-form").ajaxSubmit(function(data){
// 		        	unmask();
// 		        	if(data.success){
// 		        		$("#desConHid"+tempDesId).val($("#update_courseContent").val());
// 		        		//layer.msg(data.errorMessage);
// 		        		if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
// 		        			$("#desStatusHid"+tempDesId).val(1);
// 		        		}else{
// 		        			$("#desStatusHid"+tempDesId).val(0);
// 		        		}
// 		        	}else{
// 		        		layer.msg(data.errorMessage);
// 		        	}
// 		        });
// 			}else{
// 			}
//
// 	        mask();
// 	        $("#addDes-form").attr("action", basePath+"/cloudClass/courseDescription/addCourseDescription");
// 	        $("#addDes-form").ajaxSubmit(function(data){
// 	        	tempDesId = "";
// 	            unmask();
// 	            if(data.success){
// 	                $("#desDialog").dialog("close");
// 	                layer.msg(data.errorMessage);
// 	                initGsjd();
// 	                selTA($("input[name='desId']").eq($("input[name='desId']").length-1).val());
// 	            }else{
// 	            	layer.msg(data.errorMessage);
// 	            }
// 	        });
// 	    }
// 	});
// });

/**
 * 上移
 * @param obj
 */
function upMoveRec(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseRecTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudClass/courseRecommend/upMoveRec',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseRecTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 下移
 * @param obj
 */
function downMoveRec(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseRecTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudClass/courseRecommend/downMoveRec',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseRecTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

//批量删除
function deletes(id){
	ajaxRequest(basePath+"/cloudClass/courseRecommend/deletes",{'ids':id},function(data){
		if(!data.success){//如果失败
			//alertInfo(data.errorMessage);
			layer.msg(data.errorMessage);
		}else{
			if(!isnull(_courseRecTable)){
				freshDelTable(_courseRecTable);
			}
			layer.msg(data.errorMessage);
		}
	});
}


function addRecCourse(){//修改
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"10%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    }},
    { "title": "学科名称", "class":"center","sortable":false,"data": 'xMenuName' },
    { "title": "课程类别", "class":"center","sortable":false,"data": 'scoreTypeName' },
    { "title": "授课方式", "class":"center","sortable":false,"data": 'teachMethodName' },
    { "title": "课程名称", "class":"center","sortable":false,"data": 'courseName' },
    { "title": "现价格", "class":"center","width":"10%","sortable":false,"data": 'currentPrice'}];

	var searchCaseCourseTable = new Array();
	$("#searchDivKclb .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				searchCaseCourseTable.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
						+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				searchCaseCourseTable.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});

	_courseTable = initTables("courseTable",basePath+"/cloudclass/course/list",objData,true,true,0,null,searchCaseCourseTable,function(data){

			$("#courseDiv .dataTables_length").hide();

	});

	$("#yxzkcRec").replaceWith('<ul class="list-group" id="yxzkcRec" style="margin: 0px;"></ul>');
	if($("#courseRecTable .dataTables_empty").length==0){

		//初始化已选择的值
		var ids = new Array();
		var names = new Array();
		$("#courseRecTable tbody tr").each(function(){
			ids.push($(this).find("input[name='realRecCourseId']").val());
			names.push($(this).find("td").eq(1).text());
		})
		for(var i=0 ;i<ids.length;i++){
			$("#yxzkcRec").append('<a href="javascript:void(0);" onclick="javascript:void(0);return false;" id="recCourse'+ids[i]+'" style="height:40px;line-height:40px;border:0px;" class="list-group-item">'
							 + "<span id=\"recCourseS"+ids[i]+"\" style=\"margin-top: -10px;width:170px;float:left;word-break:keep-all;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;\" title=\""+names[i]+"\">"+names[i] +
					'</span><span class="badge badge-info" id="recCourseST'+ids[i]+'"><i class="glyphicon glyphicon-trash" onclick="delRecCourse(\''+ids[i]+'\')"></i></span></a>'+
					'<input type="hidden" id="recCourseHid'+ids[i]+'" name="recCourseHid" value="'+ids[i]+'">');
		}
	}
	
     $("#search_menu").val("");
  	 $("#search_scoreType").val("");
  	 $("#search_courseType").val("");
  	search();
	var dialog = openDialog("courseDialog","dialogCourseDiv","新增推荐课程",1100,600,true,"确定",function(){
		if($("#yxzkcRec input[name='recCourseHid']").length > 0){
	        mask();
	        $("#addRecCourse-form").attr("action", basePath+"/cloudClass/courseRecommend/addCourseRecommend");
	        $("#addRecCourse-form").ajaxSubmit(function(data){
	            unmask();
	            if(data.success){
	                $("#courseDialog").dialog("close");
	                layer.msg(data.errorMessage);
	                freshTable(_courseRecTable);
	            }else{
	            	layer.msg(data.errorMessage);
	            }
	        });
		}else{
			$("#courseDialog").dialog("close");
			return ;
		}
	});

}

function search(){
//	searchButton(_courseTable);
	var searchs = new Array();
	$("#searchDivKclb .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				searchs.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
						+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				searchs.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});

//	searchs = encodeURI(searchs);
	var str = "[" + searchs.join(",") + "]";

	_courseTable.fnFilter(str);

};

$("#ydRecCourse").on("click",function(){
	if($("#yxzkcRec input[name='recCourseHid']").length>5){
		alert("最多只能推荐5门课程!");
		return false;
	}
	var ids = new Array();
	var names = new Array();
	var testLength = new Array();
	//获取到所有选中的节点
	$("#courseDiv .dataTable tbody input[type='checkbox']:checked").each(function(){
		ids.push($(this).val());
		names.push($(this).parent().parent().find("td").eq(4).text());
	});

	$("#yxzkcRec input[name='recCourseHid']").each(function(){
		for(var i = 0;i<ids.length;i++){
			if(ids[i]==$(this).val()){
				delete ids[i];
				delete names[i];
			}
		}
	});

	if($("#yxzkcRec input[name='recCourseHid']").length == 0)
	{
		if(ids.length>5){
			alert("最多只能推荐5门课程!");
			return false;
		}
	}else{
		for(var i=0;i<ids.length;i++){
			if(ids[i]!=undefined){
				testLength.push(ids[i]);
			}
		}

		if(($("#yxzkcRec input[name='recCourseHid']").length+testLength.length)>5){
			alert("最多只能推荐5门课程!");
			return false;
		}
	}

	for(var i=0 ;i<ids.length;i++){
		if(ids[i]==undefined){
			continue;
		}
		$("#yxzkcRec").append('<a href="javascript:void(0);" onclick="javascript:void(0);return false;" id="recCourse'+ids[i]+'" style="height:40px;line-height:40px;border:0px;" class="list-group-item">'
						 + "<span id=\"recCourseS"+ids[i]+"\" style=\"margin-top: -10px;width:170px;display: block;float:left;word-break:keep-all;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;\" title=\""+names[i]+"\">"+names[i] +
				'</span><span class="badge badge-info" id="recCourseST'+ids[i]+'"><i class="glyphicon glyphicon-trash" onclick="delRecCourse(\''+ids[i]+'\')"></i></span></a>'+
				'<input type="hidden" id="recCourseHid'+ids[i]+'" name="recCourseHid" value="'+ids[i]+'">');
	}

});

//删除一个推荐课程
function delRecCourse(courseId){
	$("#recCourseHid"+courseId).remove();
	$("#recCourse"+courseId).remove();
}

$("#descriptionShow").on("click",function(){//点击添加UL
	var  descriptionShow = 0;
	if($("#descriptionShow").is(':checked')){//如果选中赋值1 否则0
		descriptionShow = 1;
	}else{
		descriptionShow = 0;
	}
	$.ajax({
         type:'post',
         url:basePath+'/cloudclass/course/descriptionShow',
         data:{descriptionShow:descriptionShow,id:$("#courseId").val()},
         dataType:'json',
         success:function(data){
        	 if(data.success){//如果成功了无所谓

        	 }else{//失败了就还原
        		 if($("#descriptionShow").is(':checked')){//如果选中赋值1 否则0
        			 $("#descriptionShow").prop("checked",false);
    			 }else{
    				 $("#descriptionShow").prop("checked",true);
    			 }
        	 }
         }
     }) ;
});
