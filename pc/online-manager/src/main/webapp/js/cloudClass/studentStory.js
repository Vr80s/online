var searchCase;
var studentStoryForm;
var studentStoryTable;
var editStudentStoryform;
$(function(){
	
	document.onkeydown=function(event){
	}
	
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"60px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
				        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
				    }},
	               { "title": "序号", "class": "center","width":"80px","sortable": false,"data":"id" },
	               { "title": "学员姓名", "class":"center","sortable":false,"data": 'name' },
	               { "title": "学员化名", "class":"center","sortable":false,"data": 'otherName' },
	               { "title": "所学课程", "class":"center","sortable":false,"data": 'course' },
				   { "title": "入职公司", "class":"center","sortable":false,"data": 'company' },
				   { "title": "薪资", "class":"center","sortable":false,"data": 'salary' },
	               { "title": "故事简介", "class":"center","sortable":false,width:"220px","data": 'introduce' ,"render": function(data, type, row) {
	               	
	               	   var MaxLength = 14;//设置一行显示的长度
	                   if (data.length > MaxLength) {
	                	   
	                       return "<div title='"+data+"'>"+data.substr(0, MaxLength) + '...'+"</div>";
	                   }else{
	                   	
	                   	return data;
	                   }
	               }},
					{ "title": "创建日期", "class":"center","sortable":false,"data": 'createTime',"mRender":function (data, display, row){
			        	var d = new Date(data);
			        	var createDate = FormatDate(d);
			        	
			        	return createDate != "" ? createDate: "";
			        }  },
	               { "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
	            	   return '<div class="hidden-sm hidden-xs action-buttons">'+
	       		    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
	       			'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'
				   		}
		      		}];
	studentStoryTable = initTables("studentStoryTable",basePath+"/cloudclass/studentStory/list",objData,true,true,2,null,searchCase,function(data){
		//tableFont60();
	});
	studentStoryForm = $("#addStudentStory-form").validate({
		messages:{
			name:{
				required:"请输入学员姓名",
				minlength:"学员姓名过短，应大于2个汉字！",
	
			},
			headImg:{
				required:"请添加头像图片！"
			},
			menuId:{
				required:"请选择所学学科"
			},
			courseTypeId:{
				required:"请选择所学课程"
			},
			company:{
				required:"请输入公司名称"
			},
			salary:{
				required:"请输入薪资数目"
			},
			introduce:{
				required:"请输入故事简介"
			},
			title:{
				required:"请输入故事标题"
			},
			text:{
				required:"请输入故事正文"
			},
		}
	});

	editStudentStoryform = $("#editStudentStory-form").validate({
		messages:{
			name:{
				required:"请输入学员姓名",
				minlength:"学员姓名过短，应大于2个字符！",
	
			},
			headImg:{
				required:"图片不能为空"
			},
			menuId:{
				required:"请选择所学学科"
			},
			courseTypeId:{
				required:"请选择所学课程"
			},
			company:{
				required:"请输入公司名称"
			},
			salary:{
				required:"请输入薪资数目"
			},
			introduce:{
				required:"请输入故事简介"
			},
			title:{
				required:"请输入故事标题"
			},
			text:{
				required:"请输入故事正文"
			},
		}
	});

	
	//下线时间 时间控件
	createDatetimePicker3($(".datetime-picker"),"yy-mm-dd");
	
	$(".wysiwyg-editor").ace_wysiwyg({
		toolbar:
				[
					'font',
					null,
					'fontSize',
					null,
					{name:'bold', className:'btn-info'},
					{name:'italic', className:'btn-info'},
					{name:'strikethrough', className:'btn-info'},
					{name:'underline', className:'btn-info'},
					null,
					{name:'insertunorderedlist', className:'btn-success'},
					{name:'insertorderedlist', className:'btn-success'},
					{name:'outdent', className:'btn-purple'},
					{name:'indent', className:'btn-purple'},
					null,
					{name:'justifyleft', className:'btn-primary'},
					{name:'justifycenter', className:'btn-primary'},
					{name:'justifyright', className:'btn-primary'},
					{name:'justifyfull', className:'btn-inverse'},
					null,
					{name:'createLink', className:'btn-pink'},
					{name:'unlink', className:'btn-pink'},
					null,
					{name:'insertImage', className:'btn-success'},
					null,
					'foreColor',
					null,
					{name:'undo', className:'btn-grey'},
					{name:'redo', className:'btn-grey'}
				],
		'wysiwyg': {
			fileUploadError: showErrorAlert
		}
	}).prev().addClass('wysiwyg-style2');
	
	
	$( "#courseTypeId" ).change(function() {
		$("#addcourse").val($('#courseTypeId option:selected').text());
		});
	
	$( "#editscoreType" ).change(function() {
		$("#editcourse").val($('#editscoreType option:selected').text());
		});
	
	
});

function search(){
	searchButton(studentStoryTable);
};


//新增框
$(".add_bx").click(function(){
	studentStoryForm.resetForm();
	imgSenBut();
	createImageUpload($('.uploadImg'));//生成图片编辑器
	$(".ace-file-container").css({"width": "97px", "height": "97px"});
	$(".ace-file-input").css({"width": "97px", "height": "97px"});
	$("#message_content").empty();
	
	var dialog = openDialog("addStudentStoryDialog","dialogAddStudentStoryDiv","新增学员故事",1045,650,true,"确定",function(){
		 
		var content=$("#message_content").html();
		$("#addtext").val(content);
		
		if($("#addStudentStory-form").valid()){
			mask();
			
			 $("#addStudentStory-form").attr("action", basePath+"/cloudclass/studentStory/addStudentStory");
	            $("#addStudentStory-form").ajaxSubmit(function(data){
	            	data = getJsonData(data);
	                unmask();
	                if(data.success){
	                    $("#addStudentStoryDialog").dialog("close");
	                    layer.msg(data.errorMessage);
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
	deleteAll(basePath+"/cloudclass/studentStory/deletes",studentStoryTable);
});

/**
 * 查看
 * 
 */
function previewDialog(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = studentStoryTable.fnGetData(oo); // get datarow
	
	
    $.get(basePath+"/cloudclass/studentStory/findStudentStoryById",{id:row.id}, function(result){
    	
    	$("#showname").text(result.name);
    	$("#showotherName").text(result.otherName);
    	$("#showcourse").text(result.course);
    	$("#showcompany").text(result.company);
    	$("#showsalary").text(result.salary+"K");
    	$("#showintroduce").text(result.introduce);
    	$("#showtitle").text(result.title);
    	$("#show_headImgPathFile").attr("src",result.headImg);
    	$("#message_content_preview").html(row.text);
    	$("#message_content_preview").attr("contenteditable","false");
    	for(i=0;i<$("#showmenuIdSelect option").length;i++){
    		if($("#showmenuIdSelect option").eq(i).val()==result.menuId){
         			$("#showmenuIdSelect option").eq(i).attr("select","selected"); 
         			$("#showmenuIdSelect").val(result.menuId);
         		}
    	}
    	
    	$("#showmenuId").text($("#showmenuIdSelect").find("option:selected").text());
    	for(i=0;i<$("#showscoreTypeSelect option").length;i++){
    		if($("#showscoreTypeSelect option").eq(i).val()==result.courseTypeId){
         			$("#showscoreTypeSelect option").eq(i).attr("select","selected"); 
         			$("#showscoreTypeSelect").val(result.courseTypeId);
         		}
    	}
    	$("#showscoreType").text($("#showscoreTypeSelect").find("option:selected").text());
    	
    	if(result.useOtherName==true){
    		$("#showuseOtherName").prop("checked", true); 
    	}
    	var dialog = openDialogNoBtnName("showStudentStoryDialog","dialogShowStudentStoryDiv","查看学员故事",845,500,false,"确定",null);
    });
	
}
/**
 * 修改
 * 
 */
function toEdit(obj){
	editStudentStoryform.resetForm();
	edit_imgSenBut();
	createImageUpload($('.uploadImg'));//生成图片编辑器
	$(".ace-file-container").css({"width": "97px", "height": "97px"});
	$(".ace-file-input").css({"width": "97px", "height": "97px"});
	
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
	    	            	data = getJsonData(data);
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
$("#editStudentStory-form").on("change","#edit_headImgPathFile",function(){
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

function tableFont60(){
	var MaxLength = 60;//设置一行显示的长度
    var txt = new Array();
    var texts = $("table .center");
    for (var i = 0; i < texts.length; i++) {
        if (texts.eq(i).text().length > MaxLength) {
            texts.eq(i).parent().index(i);
            txt[i] = texts.eq(i).text();
            var st = texts.eq(i).text().substr(0, MaxLength) + '...';
            //texts.eq(i).attr("title",txt[i]);
            texts.eq(i).text(st);
        }
    }
}