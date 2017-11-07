var lecturerTable;
var searchCase;
var lecturerForm;
var editLecturerform;
$(function(){
	
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	
	var checkbox = '<input type="checkbox" class="ace" style="width:5%;" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    }},
    { "title": "序号", "class": "center","width":"5%","sortable": false,"data":"id" },
    { "title": "老师姓名", "class":"center","width":"8%","sortable":false,"data": 'name' },
    { "title": "角色", "class":"center","width":"6%","sortable":false,"data": 'roleTypeName' },
    { "title": "所属学科", "class":"center","width":"10%","sortable":false,"data": 'menuName' },
    { "title": "简介", "class":"center","sortable":false,"data": 'description',"render": function(data, type, row) {
    	
    	var MaxLength = 45;//设置一行显示的长度
        if (data.length > MaxLength) {
        	
            return "<div title='"+data+"'>"+data.substr(0, MaxLength) + '...'+"</div>";
        }else{
        	
        	return data;
        }
    }},
    { "title": "创建时间", "class":"center","width":"10%","sortable":false,"data": 'createTime'},
    
    { "sortable": false,"class": "center","title":"操作","width":"10%","mRender":function (data, display, row) {//gradeCount
    	/*if(row.gradeCount==0){*/
    		return '<div class="hidden-sm hidden-xs action-buttons">'+
		    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
			'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
			'<a class="blue" href="javascript:void(-1);" title="授课记录" onclick="viewTeachRecords(this)"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
    	/*}else{
    		return '<div class="hidden-sm hidden-xs action-buttons">'+
		    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
			'<a class="gray" href="javascript:void(-1);" title="修改"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
			'<a class="blue" href="javascript:void(-1);" title="授课记录" onclick="viewTeachRecords(this)"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
    	}*/
	    		
	    } ,},
    { "title": "头像", "class": "center","width":"80px","sortable": false,"data":"headImg","visible":false },
	{ "title": "讲师介绍信息", "class": "center","width":"80px","sortable": false,"data":"description","visible":false },
	{ "title": "角色", "class": "center","width":"80px","sortable": false,"data":"roleType","visible":false },
	{ "title": "所属学科", "class": "center","width":"80px","sortable": false,"data":"menuId","visible":false },
    ];
	lecturerTable = initTables("lecturerTable",basePath+"/cloudClass/lecturer/list",objData,true,true,2,null,searchCase,function(data){
		//tableFont60();
	    });
	lecturerForm = $("#addLecturer-form").validate({
		messages:{
			headImg:{
				required:"请添加头像图片"
			},
			menuId:{
				required:"请选择学科"
			},
			name:{
				required:"请输入教师姓名",
				minlength:"教师姓名过短，应大于2个字符"
			},
			roleType:{
				required:"请选择角色",
			},
			nickname:{
				required:"请输入教师昵称",
				minlength:"教师昵称过短，应大于2个字符"
			},
			description:{
				required:"请输入老师简介"
			},
		}
    });
	
	editLecturerform = $("#editLecturer-form").validate({
		messages:{
			headImg:{
				required:"请添加头像图片"
			},
			menuId:{
				required:"请选择学科"
			},
			name:{
				required:"请输入教师姓名",
				minlength:"教师姓名过短，应大于2个字符"
			},
			nickname:{
				required:"请输入教师昵称",
				minlength:"教师昵称过短，应大于2个字符"
			},
			roleType:{
				required:"请选择角色",
			},
			description:{
				required:"请输入老师简介"
			},
		}
    });
	
});



//新增框
$(".add_bx").click(function(){
	lecturerForm.resetForm();
	imgSenBut();
	createImageUpload($('.uploadImg'));//生成图片编辑器
	$(".ace-file-container").css({"width": "97px", "height": "97px"});
	$(".ace-file-input").css({"width": "97px", "height": "97px"});
	
	$("#addLecturer-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
	var dialog = openDialog("addLecturerDialog","dialogAddLecturerDiv","新增老师",480,600,true,"确定",function(){
		if($("#addLecturer-form").valid()){
			mask();
			$("#addLecturer-form").attr("action", basePath+"/cloudClass/lecturer/addLecturer");
			$("#addLecturer-form").ajaxSubmit(function(data){
				try{
            		data = jQuery.parseJSON(jQuery(data).text());
            	}catch(e) {
            		data = data;
            	  }
                unmask();
                if(data.success){
                    $("#addLecturerDialog").dialog("close");
                    freshTable(lecturerTable);
                    layer.msg(data.errorMessage);
                }else{
                	layer.msg(data.errorMessage);
                }
			});
		}
		
	})
});

/**
 * 修改
 * 
 */
function toEdit(obj){
	editLecturerform.resetForm();
	edit_imgSenBut();
	createImageUpload($('.uploadImg'));//生成图片编辑器
	$(".ace-file-input").css({"width": "97px", "height": "97px"});
	
	$(".ace-file-container").css({"width": "97px", "height": "97px"});
	var oo = $(obj).parent().parent().parent();
	var row = lecturerTable.fnGetData(oo); // get datarow
	$("#edit_id").val(row.id);
	$("#edit_name").val(row.name);
	$("#edit_nickname").val(row.nickname);
	$("#edit_description").val(row.description);
	$("#edit_headImgPathFile").attr("src",row.headImg);
	$("#edit_headImgPath").val(row.headImg);
	if(row.headImg!=null&&row.headImg!=""){
//		reviewImage("edit_headImgPath", row.headImg);
		var inputId = "edit_headImgPath";
		var imgSrc = row.headImg;

		var fileName = imgSrc;
		if(imgSrc.indexOf("/")>-1){
			fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
		}
		$("#"+inputId).parent().find('.ace-file-name').remove();
		$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
		.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
				 +('<img class="middle" style="width: 85px; height: 85px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
						 +'</span>');
		
	}
	for(i=0;i<$("#edit_menuId option").length;i++){
		if($("#edit_menuId option").eq(i).val()==row.menuId){
     			$("#edit_menuId option").eq(i).attr("select","selected"); 
     			$("#edit_menuId").val(row.menuId);
     		}
	}
	for(i=0;i<$("#edit_roleType option").length;i++){
		if($("#edit_roleType option").eq(i).val()==row.roleType){
     			$("#edit_roleType option").eq(i).attr("select","selected"); 
     			$("#edit_roleType").val(row.roleType);
     		}
	}
	openDialog("editLecturerDialog","dialogEditLecturerDiv","修改老师",680,550,true,"提交",function(){
		if($("#editLecturer-form").valid()){
			mask();
			$("#editLecturer-form").attr("action", basePath+"/cloudClass/lecturer/update");
			$("#editLecturer-form").ajaxSubmit(function(data){
				unmask();
				try{
            		data = jQuery.parseJSON(jQuery(data).text());
            	}catch(e) {
            		data = data;
            	  }
				if(data.success){
					$("#editLecturerDialog").dialog("close");
					layer.msg(data.errorMessage);
					freshTable(lecturerTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});
		}
	});
	
	
}

//查看
function previewDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var row = lecturerTable.fnGetData(oo); // get datarow
	
	$("#show_name").text(row.name);
	$("#show_menu").text(row.menuName);
	$("#show_roleType").text(row.roleTypeName);
	$("#show_nickname").text(row.nickname);
	$("#show_description").text(row.description);
	$("#show_headImgPathFile").attr("src",row.headImg);
	var dialog = openDialogNoBtnName("ViewLecturerDialog","dialogViewLecturerDiv","查看老师",680,450,false,"确定",null);
}
/*  删除*/
$(".dele_bx").click(function(){
	deleteAll(basePath+'/cloudClass/lecturer/deletes',lecturerTable);
});
//课程记录
function viewTeachRecords(obj){
	var oo = $(obj).parent().parent().parent();
	var row = lecturerTable.fnGetData(oo); // get datarow
	
	$("#TeachReconds_name").text(row.name);
	 $.get(basePath+"/cloudClass/lecturer/findTeachRecordsByLecturerId",{id:row.id}, function(result){
		 
		 var str=[];
		 for(var i=0;i<result.length;i++){
			 str.push(result[i].teachRecords+"</br>")
		 }
		 
		 $("#TeachReconds_description").html(str);
	 });
	 var dialog = openDialogNoBtnName("ViewTeachRecondsDialog","dialogViewTeachRecondsDiv","授课记录",580,550,false,"确定",null);
}
function search(){
	searchButton(lecturerTable);
};
function createImageUpload(obj){
	obj.ace_file_input({
		style:'well',
		btn_choose:'点击选择图片',
		btn_change:null,
		no_icon:'ace-icon fa fa-cloud-upload',
		droppable:true,
		thumbnail:'small',//large | fit	
		preview_error : function(filename, error_code) {
		}
	}).on('change', function(){
	});
	obj.ace_file_input('update_settings',
			{
		'allowExt': ["jpeg", "jpg", "png", "gif" , "bmp"],
		'allowMime': ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"]
			});
	
	$(".remove").hide();
	
}

//新增头像
function imgSenBut(){
	$("#imgAdd").html('<input type="file" name="img" id="headImgPathFile" class="uploadImg"/>');
};
//修改头像
function edit_imgSenBut(){
	$("#edit_imgAdd").html('<input type="file" name="img" id="edit_headImgPathFile" class="uploadImg"/>');
};

var _this;
//图片上传统一上传到附件中心
$("#addLecturer-form").on("change","#headImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/cloudclass/studentStory/uploadHeadImg", function(data){
		
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
$("#editLecturer-form").on("change","#edit_headImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/cloudclass/studentStory/uploadHeadImg", function(data){
	
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
//修改文本显示带...
function tableFont60(){
	var MaxLength = 60;//设置一行显示的长度
    var txt = new Array();
    var texts = $("table .center");
    for (var i = 0; i < texts.length; i++) {
        if (texts.eq(i).text().length > MaxLength) {
            texts.eq(i).parent().index(i);
            txt[i] = texts.eq(i).text();
            var st = texts.eq(i).text().substr(0, MaxLength) + '...';
            texts.eq(i).attr("title",txt[i]);
            texts.eq(i).text(st);
        }
    }
}