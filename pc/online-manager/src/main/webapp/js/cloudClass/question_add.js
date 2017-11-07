//var questionTable;
var objData; 
var questionForm;
var editFormEl = "#question-edit-form";
var editValidateForm;
var OptionIndex = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'];

$(function(){
	
	objData = [
	            { "title": createAllCheckBox(),"class":"center","data":"id","sortable":false,"width":"5%","mRender":function(data,display,row){
                    return createCheckBox(data);
                }},
                { "title": "序号", "class":"center","sortable":false,"data": 'id',"width":"90px"  },
                { "title": "题目编号", "class":"center","width":"90px","sortable":false,"data": 'questionNo',"visible":false },
	            { "title": "类型", "class":"center","sortable":false,"data": 'questionType', "mRender":function (data, display, row) {
	            	if("0" == data){return "单选题";}
	            	if("1" == data){return "多选题";}
	            	if("2" == data){return "判断题";}
	            	if("3" == data){return "填空题";}
	            	if("4" == data){return "简答题";}
	            	if("5" == data){return "代码题";}
	            	if("6" == data){return "实操题";}
	            }},
		        { "title": "状态", "class":"center","sortable":false,"data": 'status', "mRender":function (data, diaplay, row) {
		        	if("0" == data){return "禁用";}
	            	if("1" == data){return "启用";}
		        }},
		        { "title": "难度", "class":"center","sortable":false,"data": 'difficulty' },
		        { "title": "录入人", "class":"center","sortable":false,"data": 'inputPerson' },
		        { "title": "知识点数", "class":"center","sortable":false,"data": 'relatedKponitNumber' },
		        { "title": "知识点", "class":"center","sortable":false,"data": 'kpointNames' },
		        { "title": "创建时间", "class":"center","sortable":false,"data": 'createTimeStr'},
		        { "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
				    return '<div class="hidden-sm hidden-xs action-buttons">'+
					'<a class="blue" href="javascript:void(-1);" title="修改" onclick="editDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
					'<a class="blue" href="javascript:void(-1);" title="预览" onclick="previewDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
					'<a class="red" href="javascript:void(-1);" title="删除" onclick="delDialog(this)"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
				}}
		    ];

	questionTable = initTables("questionTable","question/load/question",objData,true,true,2,null,null);//第2列作为序号列
	
	questionForm = $("#question-form").validate({
		messages:{
			questionHead:{
                required:"题干不能为空"
            },
            inputPerson:{
		        required:"录入人不能为空"
		    },
		    ksystemId:{
		        required:"知识体系不能为空"
		    },
		    knowledgeNames:{
		        required:"知识点不能为空"
		    },
		    answerAttachment:{
		    	required:"附件不能为空"
		    }
		},
		errorPlacement:function(error, element){
			error.appendTo(element.parent());
		}
	});
	
	editValidateForm = $(editFormEl).validate({
		messages:{
		}
	});
	
});
//单条删除
function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = questionTable.fnGetData(oo);
	showDelDialog(function(){
		ajaxRequest("question/delete",{"id":aData.id},function(res){
			layer.msg(res.errorMessage);
			freshTable(questionTable);
		});
	});
}

//批量删除
function deleteBatch(){
	deleteAll("question/deleteBatch",questionTable);
}
//条件查询
function search(){
	searchButton(questionTable,searchCase);
}
function isContained(a, b){
    if(!(b instanceof Array)) return false;
    for(var i = 0, len = b.length; i < len; i++){
    	if (a == b[i]) { 
    		return true; 
    		break;
    	}
    }
    return false;
}

function ajaxCallback(res){
	if(res.success){
		layer.msg(res.errorMessage);
		location.reload();
	}else{
		layer.msg(res.errorMessage);
	}
}

var wysiwygEditorNoFilling;//题干(不是填空题)
var wysiwygEditorSolution;//答案说明
var wysiwygEditorFilling;//题干(填空题)
var wysiwygEditorAnswer;//正确答案
$(function(){
	
	$("#questionOptionValue").html(multipleOne);
	createImageUpload($('.uploadImg'));//生成图片编辑器
	$("#questionContentValue").html(contentTypeNoFilling);
	$("#questionSolutionValue").html(solution);
	wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
	wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
	
	/**
	 * 题目类型切换动态地修改该类型需要的题干，答案 begin
	 */
	$("#qustion_type :radio").click(function(){
		$("#questionSolutionValue").empty();
		$("#questionSolutionValue").html(solution);
		var radioValue = $("input[type='radio']:checked").val();
		if (radioValue == 0) {//单选
			/**
			 * 切换题型所有输入值还原 begin
			 */
			$(':input', '#subject-form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
		/*	$("#knowledgeNames").val("");*/
			$("#knowledgeIds").val("");
			/**
			 * 切换题型所有输入值还原 end
			 */
			$("input[type='radio']").eq(0).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionOptionValue").html(multipleOne);
			createImageUpload($('.uploadImg'));//生成图片编辑器
			$("#questionContentValue").html(contentTypeNoFilling);
			wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
			wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
			console.info($(".remove"));
			$(".remove").click(function(){
				console.info($(this).closest("div"));
				console.info($(this).closest("div").find("input:hidden"));
				$(this).closest("div").find("input:hidden").val("");
				validateOption();
			});
		} else if (radioValue == 1) {//多选
			/**
			 * 切换题型所有输入值还原 begin
			 */
			$(':input', '#subject-form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
			/*$("#knowledgeNames").val("");*/
			$("#knowledgeIds").val("");
			/**
			 * 切换题型所有输入值还原 end
			 */
			$("input[type='radio']").eq(1).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
	        $("#questionOptionValue").html(multipleMany);
	        createImageUpload($('.uploadImg'));//生成图片编辑器,在多选框后面
	        $("#questionContentValue").html(contentTypeNoFilling);
	        wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
			wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
			console.info($(".remove"));
			$(".remove").click(function(){
				console.info($(this).closest("div"));
				console.info($(this).closest("div").find("input:hidden"));
				$(this).closest("div").find("input:hidden").val("");
				validateOption();
			});
		} else if (radioValue == 2) {//判断
			/**
			 * 切换题型所有输入值还原 begin
			 */
			$(':input', '#subject-form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
			/*$("#knowledgeNames").val("");*/
			$("#knowledgeIds").val("");
			/**
			 * 切换题型所有输入值还原 end
			 */
			$("input[type='radio']").eq(2).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeNoFilling);
			$("#questionAnswerValue").html(answerJudge);
			wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
			wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
		} else if (radioValue == 3) {//填空
			/**
			 * 切换题型所有输入值还原 begin
			 */
			$(':input', '#subject-form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
			/*$("#knowledgeNames").val("");*/
			$("#knowledgeIds").val("");
			$("input[name='status']").eq(0).attr("checked",true);
			$("input[name='difficulty']").eq(0).attr("checked",true);
			/**
			 * 切换题型所有输入值还原 end
			 */
			$("input[type='radio']").eq(3).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeFilling);
			wysiwygEditorFilling = createKindEdit('#wysiwygEditorFilling textarea[class="kindeditor"]',editAfterBulrBlank);
			wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
		} else if (radioValue == 4) {//解答
			/**
			 * 切换题型所有输入值还原 begin
			 */
			$(':input', '#subject-form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
			/*$("#knowledgeNames").val("");*/
			$("#knowledgeIds").val("");
			/**
			 * 切换题型所有输入值还原 end
			 */
			$("input[type='radio']").eq(4).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeNoFilling);
			$("#questionAnswerValue").html(answerNomal);
			wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
			wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
			wysiwygEditorAnswer = createKindEdit('#wysiwygEditorAnswer textarea[class="kindeditor"]',editAfterBulrAnswer);
		} else if (radioValue == 5) {//代码
			/**
			 * 切换题型所有输入值还原 begin
			 */
			$(':input', '#subject-form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
			/*$("#knowledgeNames").val("");*/
			$("#knowledgeIds").val("");
			/**
			 * 切换题型所有输入值还原 end
			 */
			$("input[type='radio']").eq(5).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeNoFilling);
			$("#questionAnswerValue").html(answerAttachment);
			wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
			wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
		} else if (radioValue == 6) {//素材
			/**
			 * 切换题型所有输入值还原 begin
			 */
			$(':input', '#subject-form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
			/*$("#knowledgeNames").val("");*/
			$("#knowledgeIds").val("");
			/**
			 * 切换题型所有输入值还原 end
			 */
			$("input[type='radio']").eq(6).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeNoFilling);
			$("#questionAnswerValue").html(practical);
			wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
			wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
		} 
		/**
		 * 题目类型切换动态地修改该类型需要的题干，答案 end
		 */
	});
	
	/**
	 * 上传图片 start
	 */
	var _this;
	//图片上传统一上传到附件中心
	$("#question-form").on("change",".uploadImg",function(){
		_this = this;
		var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
		if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
			layer.msg("图片格式错误,请重新选择.");
			this.value="";
			return;
		}
		var id = $(this).attr("id");
		ajaxFileUpload(this.id, basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
        	if ($("#"+id).parent().find(".ace-file-name img").length == 0) {
        		$("#"+id).parent().find(".ace-file-name").append('<img class="middle"><i class=" ace-icon fa fa-picture-o file-image"></i>');
        		$("#"+id).parent().find(".ace-file-container").addClass("selected");
        		$("#"+id).parent().find(".ace-file-container").addClass("hide-placeholder");
        		$("#"+id).parent().find(".ace-file-container").removeAttr("data-title");
        		var fileName = _this.value.substring(_this.value.lastIndexOf("\\")+1);
        		$("#"+id).parent().find(".ace-file-name").attr("data-title",fileName);
        	}
        	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 50px; height: 50px;");
        	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
        	$("#"+id).parent().parent("div").find("input:hidden").val(data.url);
	       	var uploadify = id.replace("imgFile","uploadify");
	     	$("#"+uploadify).val(data.url);
    	 	validateOption();
		})
	});
	
	/**
	 * 上传图片 end
	 */
	/**
	 * 上传附件 start
	 */
	var _this;
	$("#question-form").on("change","#answerNomalAttachment",function(){
		_this = this;
		var a = this.value.split(".");
		var v = a[a.length-1].toUpperCase();
		if(v!='TXT' && v!='DOC' && v!='DOCX' && v!='XLS' && v!= 'XLSX' && v!='ZIP' && v!='RAR'){
			alertInfo("请上传txt,word,excel,zip,rar的附件格式.");
			this.value="";
			return;
		}
		var id = $(this).attr("id");
		if(!validateImgSize(id,100)){
			alertInfo("附件大于100M,请重新选择.");
			this.value="";
			return false;
		}
		mask("附件正在上传,请等待");
		
		ajaxFileUpload(this.id, basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
			console.log(data);
			unmask();
			$("#answerNomalAttachment").hide();
        	$("#wysiwygEditorAnswer").find('.show').remove();
        	$("#wysiwygEditorAnswer").append('<span class="show" onclick="selectFile1(this)">'+data.orgFileName+'</span>');
        	$("#"+id).attr("value",data.fileName);
        	$("#"+id).parent().parent("div").find("#answerAttachment").val(data.id);
        	$("#"+id).parent().parent("div").find("#attachmentUrl").val(data.url);
		})
		
	});
	/**
	 * 上传附件 end
	 */
	/**
	 * 上传素材 start
	 */
	var _this;
	$("#question-form").on("change","#practicalNomalAttachment",function(){
		_this = this;
		var a = this.value.split(".");
		var v = a[a.length-1].toUpperCase();
		if(v!='TXT' && v!='DOC' && v!='DOCX' && v!='XLS' && v!= 'XLSX' && v!='ZIP' && v!='RAR'
			&& v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
			alertInfo("请上传txt,word,excel,zip,rar或者图片的附件格式.");
			this.value="";
			return;
		}
		var id = $(this).attr("id");
		if(!validateImgSize(id,500)){
			alertInfo("素材大于500M,请重新选择.");
			this.value="";
			return false;
		}
		mask("素材正在上传,请等待");
		ajaxFileUpload(this.id, basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
			unmask();
			$("#practicalNomalAttachment").hide();
        	$("#wysiwygEditorAnswer").find('.show').remove();
        	$("#wysiwygEditorAnswer").append('<span class="show" onclick="selectFile2(this)">'+data.orgFileName+'</span>');
        	$("#"+id).attr("value",data.fileName);
        	$("#"+id).parent().parent("div").find("#practicalAttachment").val(data.id);
        	$("#"+id).parent().parent("div").find("#practicalAttachmentUrl").val(data.url);
			
		});
	});
	/**
	 * 上传素材 end
	 */
	console.info($(".remove"));
	$(".remove").click(function(){
		console.info($(this).closest("div"));
		console.info($(this).closest("div").find("input:hidden"));
		$(this).closest("div").find("input:hidden").val("");
		validateOption();
	});
});
function selectFile1(t) {
	var i = $(t);
	i.parent("#wysiwygEditorAnswer").find("#answerNomalAttachment").click();
}
function selectFile2(t) {
	var i = $(t);
	i.parent("#wysiwygEditorAnswer").find("#practicalNomalAttachment").click();
}
/**
 * 重置按钮
 */
function toReset() {
	//公共组件还原
	/** 切换题型所有输入值还原 begin*/
	$("input[name='labletype']").each(function(){
		//console.log($(this).attr("id"));
		$(this).attr("checked",false);
	});
	$(':input', '#subject-form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
	$("#question-form #ksystemId").val("").trigger("chosen:updated");
	$("#ksystemId").val(""); 
	$("#knowledgeNames").val("");
	$("#qKeyword").val("");
	$("#knowledgeIds").val("");
	/**切换题型所有输入值还原 end*/
	
	$("#questionSolutionValue").empty();
	$("#questionSolutionValue").html(solution);
	var radioValue = $("input[type='radio']:checked").val();
	
	if (radioValue == 0) {//单选
		
		$("input[type='radio']").eq(0).attr("checked", true);
		$("#questionOptionValue").empty();
		$("#questionContentValue").empty();
		$("#questionAnswerValue").empty();
		$("#questionOptionValue").html(multipleOne);
		createImageUpload($('.uploadImg'));//生成图片编辑器
		$("#questionContentValue").html(contentTypeNoFilling);
//		createEdit($('.wysiwyg-editor'));
		wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution  = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
	} else if (radioValue == 1) {//多选

		$("input[type='radio']").eq(1).attr("checked", true);
		$("#questionOptionValue").empty();
		$("#questionContentValue").empty();
		$("#questionAnswerValue").empty();
        $("#questionOptionValue").html(multipleMany);
        createImageUpload($('.uploadImg'));//生成图片编辑器,在多选框后面
        $("#questionContentValue").html(contentTypeNoFilling);
//		createEdit($('.wysiwyg-editor'));
		wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution  = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
	} else if (radioValue == 2) {//判断
		
		$("input[type='radio']").eq(2).attr("checked", true);
		$("#questionOptionValue").empty();
		$("#questionContentValue").empty();
		$("#questionAnswerValue").empty();
		$("#questionContentValue").html(contentTypeNoFilling);
		$("#questionAnswerValue").html(answerJudge);
//		createEdit($('.wysiwyg-editor'));
		wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution  = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
	} else if (radioValue == 3) {//填空
		
		
		$("input[type='radio']").eq(3).attr("checked", true);
		$("#questionOptionValue").empty();
		$("#questionContentValue").empty();
		$("#questionAnswerValue").empty();
		$("#questionContentValue").html(contentTypeFilling);
		//createEdit($('.wysiwyg-editor'));
		wysiwygEditorFilling = createKindEdit('#wysiwygEditorFilling textarea[class="kindeditor"]',editAfterBulrBlank);
		wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
	} else if (radioValue == 4) {//解答
		
		$("input[type='radio']").eq(4).attr("checked", true);
		$("#questionOptionValue").empty();
		$("#questionContentValue").empty();
		$("#questionAnswerValue").empty();
		$("#questionContentValue").html(contentTypeNoFilling);
		$("#questionAnswerValue").html(answerNomal);
		//createEdit($('.wysiwyg-editor'));
		wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
		wysiwygEditorAnswer = createKindEdit('#wysiwygEditorAnswer textarea[class="kindeditor"]',editAfterBulrAnswer);
	} else if (radioValue == 5) {//代码
		
		$("input[type='radio']").eq(5).attr("checked", true);
		$("#questionOptionValue").empty();
		$("#questionContentValue").empty();
		$("#questionAnswerValue").empty();
		$("#questionContentValue").html(contentTypeNoFilling);
		$("#questionAnswerValue").html(answerAttachment);
		//createEdit($('.wysiwyg-editor'));
		wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
	} else if (radioValue == 6) {//代码
		
		$("input[type='radio']").eq(6).attr("checked", true);
		$("#questionOptionValue").empty();
		$("#questionContentValue").empty();
		$("#questionAnswerValue").empty();
		$("#questionContentValue").html(contentTypeNoFilling);
		$("#questionAnswerValue").html(practical);
		//createEdit($('.wysiwyg-editor'));
		wysiwygEditorNoFilling = createKindEdit('#wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#wysiwygEditorSolution textarea[class="kindeditor"]');
	} 
	
	$("input[name='status']").eq(0).attr("checked",true);
	$("input[name='difficulty']").eq(0).attr("checked",true);
	
}
/**
 * 新增提交
 */
function submitToAdd(mark) {

	$("#addButton1").attr("disabled", true);
	$("#addButton2").attr("disabled", true);
	$("#addButton3").attr("disabled", true);
	
	/*
	 * 提交表单的时候编辑器里面的值动态赋值给hidden隐藏域作表单验证,因为编辑器无法作值验证 begin
	 */
	var radioValue = $("input[type='radio']:checked").val();
	if (isnull(wysiwygEditorSolution.text())) {
		$('#question-form #solution').val("");
	} else {
		$("#question-form #solution").val(wysiwygEditorSolution.html());
	}
	
	var ksys = $("#courseId").val();
	if(!ksys){
		$("#addButton1").attr("disabled", false);
		$("#addButton2").attr("disabled", false);
		$("#addButton3").attr("disabled", false);
		layer.msg("请选择知识体系");
		return;
	}
	
	var knowledgeNames = $("#knowledgeNames").val();
	if(!knowledgeNames){
		$("#addButton1").attr("disabled", false);
		$("#addButton2").attr("disabled", false);
		$("#addButton3").attr("disabled", false);
		layer.msg("请选择知识点");
		return;
	}
	/**
	 * 存文本数据 start
	 */
	//debugger;
	$("#divText").html(wysiwygEditorSolution.html());
	$("#question-form #solutionText").val($("#divText").text());
	
//	var ssss = $("#questionHeadNoFilling").val();
	/**
	 * 存文本数据 end
	 */
	if (radioValue == 0 || radioValue == 1 || radioValue == 2) {//单选
		if (isnull(wysiwygEditorNoFilling.text())) {
			$('#question-form #questionHeadNoFilling').val("");
			layer.msg("题干为空");
		} else {
			$('#question-form #questionHeadNoFilling').val(wysiwygEditorNoFilling.html());
		}
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorNoFilling.html());
		$("#question-form #questionHeadNoFillingText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
	} else if (radioValue == 3) {//填空
		if (isnull(wysiwygEditorFilling.text())) {
			$('#question-form #questionHeadFilling').val("");
			layer.msg("题干为空");
		} else {
			$('#question-form #questionHeadFilling').val(wysiwygEditorFilling.html());
		}
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorFilling.html());
		$("#question-form #questionHeadFillingText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
	} else if (radioValue == 4) {//解答
		if (isnull(wysiwygEditorNoFilling.text())) {
			$('#question-form #questionHeadNoFilling').val("");
			layer.msg("题干为空");
			$("#addButton1").attr("disabled", false);
			$("#addButton2").attr("disabled", false);
			$("#addButton3").attr("disabled", false);
		} else {
			$('#question-form #questionHeadNoFilling').val(wysiwygEditorNoFilling.html());
		}
		
		if (isnull(wysiwygEditorAnswer.text())) {
			$('#question-form #answer').val("");
		} else {
			$('#question-form #answer').val(wysiwygEditorAnswer.html());
		}
		
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorNoFilling.html());
		$("#question-form #questionHeadNoFillingText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorAnswer.html());
		$("#question-form #answerText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
	} else if (radioValue == 5 || radioValue == 6) {//代码和实操
		if (isnull(wysiwygEditorNoFilling.text())) {
			$('#question-form #questionHeadNoFilling').val("");
			layer.msg("题干为空");
			$("#addButton1").attr("disabled", false);
			$("#addButton2").attr("disabled", false);
			$("#addButton3").attr("disabled", false);
		} else {
			$('#question-form #questionHeadNoFilling').val(wysiwygEditorNoFilling.html());
		}
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorNoFilling.html());
		$("#question-form #questionHeadNoFillingText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
	}   

	/*
	 * 提交表单的时候编辑器里面的值动态赋值给hidden隐藏域作表单验证,因为编辑器无法作值验证 end
	 */
	if (!$("#question-form").valid()) {
		$("#addButton1").attr("disabled", false);
		$("#addButton2").attr("disabled", false);
		$("#addButton3").attr("disabled", false);
	}
	if($("#question-form").valid()){
		var radioOptionValue_ = [];
		if (radioValue == 0) {
			$("#chooseOption1_ul").find("[name^='radioOptionValue']").each(function(){
				if(-1 < $.inArray(this.value,radioOptionValue_)){
					$("#choose_answer_option").append(OptionRepeatError);
					$("#addButton1").attr("disabled", false);
					$("#addButton2").attr("disabled", false);
					$("#addButton3").attr("disabled", false);
					radioOptionValue_=false;
					return false;
				}
				if (!isnull(this.value)) {
					radioOptionValue_.push(this.value);
				}
			});
		}
		if(!radioOptionValue_){
			$("#addButton1").attr("disabled", false);
			$("#addButton2").attr("disabled", false);
			$("#addButton3").attr("disabled", false);
			return;
		}
		//多选判断选项内容是否重复
		var multipleOptionValue_ = [];
		if (radioValue == 1) {
			$("#multipleOption1_ul").find("[name^='multipleOption']").each(function(){
				if(-1 < $.inArray(this.value,multipleOptionValue_)){
					$("#addButton1").attr("disabled", false);
					$("#addButton2").attr("disabled", false);
					$("#addButton3").attr("disabled", false);
					$("#multipleOption1_ul").append(OptionRepeatError);
					multipleOptionValue_=false;
					return false;
				}
				if (!isnull(this.value)) {
					multipleOptionValue_.push(this.value);
				}
			});
		}
		if(!multipleOptionValue_){
			$("#addButton1").attr("disabled", false);
			$("#addButton2").attr("disabled", false);
			$("#addButton3").attr("disabled", false);
			return;
		}
		
		if (radioValue == 0) {
			/**
			 * 选项图片和选项内容不能同时为空 begin
			 */
			/*var radioLength = $("#chooseOption1_ul").children("li").length;
			for(var i=0;i<radioLength;i++){
				if(isnull($("#chooseOption1_ul").children("li").eq(i).find("input[name='radioOptionValue"+(i+1)+"']").val()) && isnull($("#uploadify"+(i+1)).val())) {
					$("#addButton1").attr("disabled", false);
					$("#addButton2").attr("disabled", false);
					$("#addButton3").attr("disabled", false);
					$("#choose_answer_option").append(optionBlankError);
					return false;
				}
			}*/
			if(!validateOption()){
				$("#addButton1").attr("disabled", false);
				$("#addButton2").attr("disabled", false);
				$("#addButton3").attr("disabled", false);
				return false;
			}
			/**
			 * 选项图片和选项内容不能同时为空 end
			 */
			debugger
			var radioOption = $('input:radio[name="radioOption"]:checked').val();
			if (radioOption === undefined || radioOption.length < 1) {
				$("#addButton1").attr("disabled", false);
				$("#addButton2").attr("disabled", false);
				$("#addButton3").attr("disabled", false);
				$("#choose_answer_option").append(singleError);
				return false;
			}
			
			
		} else if (radioValue == 1) {
			/**
			 * 选项图片和选项内容不能同时为空 begin
			 */
		/*	var radioLength = $("#multipleOption1_ul").children("li").length;
			for(var i=0;i<radioLength;i++){
				if(isnull($("#multipleOption1_ul").children("li").eq(i).find("input[name='multipleOptionValue"+(i+1)+"']").val()) && isnull($("#uploadify"+(i+1)).val())) {
					$("#addButton1").attr("disabled", false);
					$("#addButton2").attr("disabled", false);
					$("#addButton3").attr("disabled", false);
					$("#multipleOption1_ul").append(optionBlankError);
					return false;
				}
			}*/
			if(!validateOption()){
				$("#addButton1").attr("disabled", false);
				$("#addButton2").attr("disabled", false);
				$("#addButton3").attr("disabled", false);
				return false;
			}
			/**
			 * 选项图片和选项内容不能同时为空 end
			 */
			var radioOption =[]; 
			$('input[name="multipleOption"]:checked').each(function(){ 
				radioOption.push($(this).val()); 
			}); 
				
			if(radioOption === undefined || radioOption.length < 2){
				$("#addButton1").attr("disabled", false);
				$("#addButton2").attr("disabled", false);
				$("#addButton3").attr("disabled", false);
				$("#multipleOption1_ul").append(multiplError);
				return false;
			}
		}
		
		if (radioValue == 3) {
			var questionHeadFilling = $("#question-form #questionHeadFilling").val();
			//console.log(questionHeadFilling);
			var questionMatchs = questionHeadFilling.match(/【([^【^】]+)】/g);
	        var matchs = questionHeadFilling.match(/【】/g);
	        if ((questionMatchs != null) && (questionMatchs.length > 0) &&
	        		!(matchs != null && matchs.length != 0)) {
	        	for (var i=0; i < questionMatchs.length; i++) {
	        		var a = questionMatchs[i].substring(1,questionMatchs[i].length-1);
	        		if (isnull($.trim(a.replace(/&nbsp;/g,"")))) {
	        			$("#addButton1").attr("disabled", false);
						$("#addButton2").attr("disabled", false);
						$("#addButton3").attr("disabled", false);
	        			$("#fillingError").append(fillingError);
	        			return false;
	        		}
	        	}
	        } else {
	        	$("#addButton1").attr("disabled", false);
				$("#addButton2").attr("disabled", false);
				$("#addButton3").attr("disabled", false);
	        	$("#fillingError").append(fillingError);
				return false;
	        }
		}
		var param = $("#question-form").serialize();
		ajaxRequest(basePath+"/question/add",param,function(res){
			if(res.success){
				if (mark == 1) turnPage(basePath+'/home#cloudclass/course/videoRes?page=3&courseId='+$("#courseId").val()+'&courseName='+$("#courseName").val()+'&activeQues=ques');
				if (mark == 2) turnPage(basePath+'/home#question/toAdd?courseId='+$("#courseId").val()+"&courseName="+$("#courseName").val()+"&pId="+$("#pId").val()+"&currentNodeId="+$("#currentNodeId").val()+"&currentNodeName="+$("#knowledgeNames").val()+'&time=' + new Date().getTime());
			}
		});
	}
}
function returnToList(){
	location.href = basePath+'/home#cloudclass/course/videoRes?page=3&courseId='+$("#courseId").val()+'&courseName='+$("#courseName").val()+'&activeQues=ques';
}
function manageDialogAdd(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = questionTable.fnGetData(oo); 
	var courseId = $("#courseId").val();
	var pId = $("#pId").val();
	var currentNodeId = $("#currentNodeId").val();
	
	dialog = openDialog("knowledgeDialog","knowledgeDialogDiv","知识体系设置",550,550,true,"保存",saveKnowledge);
	
	clearTree();
	
	initCommonZtrees("leftResource","rightResource","question/findDataForTreeRight",{"currentNodeId":currentNodeId},"right",function(){//左右两边都加载
		initCommonZtrees("leftResource","rightResource","question/findDataForTreeLeft",{"courseId":courseId},"left");
	});
}
/**
 * 保存知识点
 */
function saveKnowledge(){
	viewRightTree(function(arr){
		var kpo = new Array();
		var kpname = new Array();
		$(arr).each(function(i,o){
				kpo.push(o.id);
				kpname.push(o.name);
		});
		$("#question-form #knowledgeNames").val(kpname);
		$("#question-form #currentNodeId").val(kpo);
		dialog.dialog("close");
	});
}

/**
 * 多选删除
 * @param obj 当前选中的删除按钮对象
 */
function multiDelete(obj){
//    $("#multipleOption1_ul").children("li").last().remove();
    
	var nextAll = $(obj).closest("li").nextAll();//获取删除当前节点的剩下节点
	$(obj).closest("li").remove();
	if(nextAll.length > 0){
		for(var i=0; i<nextAll.length; i++){
			var option=OptionIndex[$(nextAll[i]).index()]
			$(nextAll[i]).find('span').first().html( option+ '：');
			$(nextAll[i]).find("input[name='multipleOption']").val(option);
			$(nextAll[i]).find("input[id^='imgFile']").attr("id","imgFile"+($(nextAll[i]).index()+1));
			$(nextAll[i]).find("input[id^='uploadify']").attr("id","uploadify"+($(nextAll[i]).index()+1));
			$(nextAll[i]).find("input[name^='uploadify']").attr("name","uploadify"+($(nextAll[i]).index()+1));
			$(nextAll[i]).find("input[name^='multipleOptionValue']").attr("name","multipleOptionValue"+($(nextAll[i]).index()+1));
			
		}
	}
	validateOption();
};
/**
 * 单选删除
 * @param obj 当前选中的删除按钮对象
 */
function singleDelete(obj){
	var nextAll = $(obj).closest("li").nextAll();//获取删除当前节点的剩下节点
	$(obj).closest("li").remove();
	if(nextAll.length > 0){
		for(var i=0; i<nextAll.length; i++){
			var option=OptionIndex[$(nextAll[i]).index()]
			$(nextAll[i]).find('span').first().html( option+ '：');
			$(nextAll[i]).find("input[name='radioOption']").val(option);
			$(nextAll[i]).find("input[id^='imgFile']").attr("id","imgFile"+($(nextAll[i]).index()+1));
			$(nextAll[i]).find("input[id^='uploadify']").attr("id","uploadify"+($(nextAll[i]).index()+1));
			$(nextAll[i]).find("input[name^='uploadify']").attr("name","uploadify"+($(nextAll[i]).index()+1));
			$(nextAll[i]).find("input[name^='radioOptionValue']").attr("name","radioOptionValue"+($(nextAll[i]).index()+1));
		
		}
	}
	validateOption();
};

var OptionIndex = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'];
//点击增加选项函数
function addRadio() {
  //循环数组，获取当前下标
  //获取目前列表最后一个下标
  var contentHtml = "";
  /* index = $("input[name='radioOption']").last().attr('value');*/
  var index = $("#chooseOption1_ul").children("li").last().find("input[name='radioOption']").attr('value');
  if (index == 'K') {
	  $("#singleHintDiv").empty();
	  $("#singleHintDiv").append(OptionMaxError);
  }else {
	  //$("#singleHintDiv").remove(OptionMaxError);
  }

  for (var i = 0; i < OptionIndex.length; i++) {
      //查找下标
      if (i >= OptionIndex.length - 1) {
      	/**/
          $(".mengban").show();
          $(".prompt").show();
      } else if (index == OptionIndex[i]) {
          //将dom元素添加到页面var
    	  contentHtml += '<li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
    	                    <input type="radio" value="'+ OptionIndex[i + 1] +'"\
                           name="radioOption"><span style="width:20px;display:inline-block">'+ OptionIndex[i + 1] +'：</span>\
                          <input name="radioOptionValue'+(i+2)+'" type="text" style="width:80%" value="" maxlength="3000">\
                         </div><div class="form-group">\
			          	<div class="col-xs-1 ">\
			          	<input type="hidden" name="uploadify'+(i+2)+'" id="uploadify'+(i+2)+'"  >\
						<input type="file"   name="attachment" id="imgFile'+(i+2)+'" class="uploadImg" value="" />\
			          	</div>\
			          	<div  class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"><input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
			          	</div>\
			          	</div>\
              </li>';
          break;
      }
  }
  $("#chooseOption1_ul").append(contentHtml);
  createImageUpload($('#imgFile'+(i+2)));//生成图片编辑器,在多选框后面
  $(".remove").click(function(){
	  console.info($(this).closest("div"));
	  console.info($(this).closest("div").find("input:hidden"));
	  $(this).closest("div").find("input:hidden").val("");
	  validateOption();
  });
  validateOption();
}

//点击增加选项函数
function addCheckbox() {
    //循环数组，获取当前下标
    //获取目前列表最后一个下标
    var contentHtml = "";
    var index = $("input[name='multipleOption']").last().attr('value');
    if (index == 'K') {
  	  $("#multipleHintDiv").empty();
  	  $("#multipleHintDiv").append(OptionMaxError);
    }
    for (var i = 0; i < OptionIndex.length; i++) {
        //查找下标
        if (i >= OptionIndex.length - 1) {
        	/**/
            $(".mengban").show();
            $(".prompt").show();
        } else if (index == OptionIndex[i]) {
            //将dom元素添加到页面var
        	contentHtml += '<li><div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
			        	          <input type="checkbox" class="question_options_check" value="'+ OptionIndex[i + 1] +'"\
			                       name="multipleOption"><span style="width:20px;display:inline-block">'+ OptionIndex[i + 1] +'：</span>\
			                       <input name="multipleOptionValue'+(i+2)+'" type="text" style="width:80%;" value="" maxlength="3000">\
			        		      </div> <div class="form-group">\
			        		   	  <div class="col-sm-1 ">\
			        		   	  <input type="hidden" name="uploadify'+(i+2)+'" id="uploadify'+(i+2)+'"  >\
								  <input type="file"   name="attachment" id="imgFile'+(i+2)+'" class="uploadImg" value="" />\
			        		   	   </div>\
			        		   	   <div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right"><input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)"\
			        		   	</div>\
			        		   	</div>\
                       </li>';
        	
            break;
        }
    }
    $("#multipleOption1_ul").append(contentHtml);
    createImageUpload($('#imgFile'+(i+2)));//生成图片编辑器,在多选框后面
    $(".remove").click(function(){
    	console.info($(this).closest("div"));
    	console.info($(this).closest("div").find("input:hidden"));
    	$(this).closest("div").find("input:hidden").val("");
    	validateOption();
    });
 validateOption();
}

/**
 * 验证图片的大小
 */
function validateImgSize(imgId,fSize){
	var imgFile = $("#"+imgId).val();
	if(!isnull(imgFile)){
		var fileSize=fileChangeGetSize(document.getElementById(imgId))
		if(fileSize>fSize){
			return false;
		}
	}
	return true;
}
//js判断文件大小 兼容所有浏览器  传入的对象必须使用 document.getElementById("zx");的方式
function fileChangeGetSize(target) {  
  var isIE = /msie/i.test(navigator.userAgent) && !window.opera;       
  var fileSize = 0;        
  if (isIE && !target.files) {    
    var filePath = target.value;    
    var fileSystem = new ActiveXObject("Scripting.FileSystemObject");  //获取上传文件的对象     
    var file = fileobject.GetFile(target.value);    //获取上传的文件  
    var filesize = file.Size;//文件大小
    fileSize = file.Size;  
  } else {  
   fileSize = target.files[0].size;    
  }  
  var size = fileSize / (1024*1024);  
  return size;       
}

$("#ksystemId").bind('change',function(){
	$("#question-form").validate().element($("#ksystemId"));
});

//处理输入文本后，不能为空的红色提示消失
function editAfterBulr(){
	if (isnull(wysiwygEditorNoFilling.text())) {
		$('#question-form #questionHeadNoFilling').val("");
	} else {
		$('#question-form #questionHeadNoFilling').val(wysiwygEditorNoFilling.html
		());
	}
	$("#question-form").validate().element($("#questionHeadNoFilling"));
}

//处理输入文本后，不能为空的红色提示消失（填空题题干）
function editAfterBulrBlank(){
	if (isnull(wysiwygEditorFilling.text())) {
		$('#question-form #questionHeadFilling').val("");
	} else {
		$('#question-form #questionHeadFilling').val(wysiwygEditorFilling.html
		());
	}
	$("#question-form").validate().element($("#questionHeadFilling"));
}
//处理输入文本后，不能为空的红色提示消失（简答题答案）
function editAfterBulrAnswer(){
	if (isnull(wysiwygEditorAnswer.text())) {
		$('#question-form #answer').val("");
	} else {
		$('#question-form #answer').val(wysiwygEditorAnswer.html
		());
	}
	$("#question-form").validate().element($("#answer"));
}
/**
 * 验证单选选项
 */
function validateChooseOption(){
   	$("#choose_answer_option").find(".error").remove();
	var boo=true;
	/**
	 * 选项图片和选项内容不能同时为空 begin
	 */
	var radio = $("#chooseOption1_ul").children("li");
	$(radio).each(function(){
		if(isnull($(this).find("input[name^='radioOptionValue']").val()) && 
				isnull($(this).find("input[id^='uploadify']").val())) {
			boo=false;
			return;
		}
	});
	if(!boo){
		$("#editButton").attr("disabled", false);
		$("#choose_answer_option").append(optionBlankError);
		return false;
	}else{
		return true;	
	}
	/**
	 * 选项图片和选项内容不能同时为空 end
	 */
}

/**
 * 验证多选选项
 */
function validateMultipleOption(){
	
   	$("#multiple_answer_option").find(".error").remove();
	var boo=true;
	/**
	 * 选项图片和选项内容不能同时为空 begin
	 */
	var radio = $("#multipleOption1_ul").children("li");
	$(radio).each(function(){
		if(isnull($(this).find("input[name^='multipleOptionValue']").val()) && 
				isnull($(this).find("input[id^='uploadify']").val())) {
			boo=false;
			return;
		}
	});
	if(!boo){
		$("#editButton").attr("disabled", false);
		$("#multiple_answer_option").append(optionBlankError);
		return false;
	}else{
		return true;	
	}
	/**
	 * 选项图片和选项内容不能同时为空 end
	 */
}
/**
 * 验证选项
 */
function validateOption(){
	var questionType = $('input[name="questionType"]').filter(':checked').val();
	if(questionType=="0"){
		return validateChooseOption();
	}else if(questionType=="1"){
		return validateMultipleOption();
	}
}

/**
 * 单选选项修改
 */
$("#question-form").on("change","#questionOptionValue input[name^='radioOptionValue']",function(){
	validateOption();
});
/**
 * 多选选项修改
 */
$("#question-form").on("change","#questionOptionValue input[name^='multipleOptionValue']",function(){
	validateOption();
});


