//var questionTable;

var objData; 
var questionForm;
var editFormEl = "#question-edit-form";
var editValidateForm;
var OptionIndex = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'];

$(function(){
	questionForm = $("#question-edit-form").validate({
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
			answer:{
		        required:"正确答案不能为空"
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
		alertMsg(res.errorMessage);
		location.reload();
	}else{
		alertMsg(res.errorMessage);
	}
}
var wysiwygEditorNoFilling;//题干(不是填空题)
var wysiwygEditorSolution;//答案说明
var wysiwygEditorFilling;//题干(填空题)
var wysiwygEditorAnswer;//正确答案
$(function(){
	
	var optionsHtml='';
	var id = $("#questionId").val();
	var ksystemId    = $("#ksystemId").val();
	var questionType = $("#questionType").val();
	var questionNo   = $("#questionNo").val();
	var createTime   = $("#createTime").val();
	var questionOptions      = eval($("#questionOptions").val());
	var questionSolution     = $("#questionSolution").html();
	var head                 = $("#head").html();
	if (questionType == 0) {//单选
		var questionAnswer       = eval($("#questionAnswer").html());
		$("#multiple_one").attr("checked", true);
		$("#questionOptionValueEdit").empty();
		$("#questionContentValueEdit").empty();
		$("#questionAnswerValueEdit").empty();
		$("#questionOptionValueEdit").html(multipleOne);
		$("#questionContentValueEdit").html(contentTypeNoFilling);
		$("#questionSolutionValueEdit").html(solution);
		
		wysiwygEditorNoFilling = createKindEdit('#question-edit-form #wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#question-edit-form #wysiwygEditorSolution textarea[class="kindeditor"]');
		wysiwygEditorNoFilling.html(head);
		wysiwygEditorSolution.html(questionSolution);
		var data = {};
		
		if (questionOptions != null) {
			ajaxRequest("question/getQopList",{"questionId":id,"questionType":null,"optionName":null},function(res){
				if (res != null) {
					var map=new Map();
					for (var k=0; k < res.length; k++) {
						map.put(res[k].optionName,res[k].attachmentId);
					}
					
					for (var j = 0; j<questionOptions.length; j++ ) {
										
						if (questionAnswer == j) {
							if (j > 1) {
								if (map.get(OptionIndex[j]) != null) {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="radio" checked class="question_options_radio" value="'+OptionIndex[j]+'" name="radioOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="radioOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-1">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'" value="'+map.get(OptionIndex[j])+'" >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
													<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
												</div>\
											</div>\
						                </li>';
								} else {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="radio" checked class="question_options_radio" value="'+OptionIndex[j]+'" name="radioOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="radioOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-1">\
					            					<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'"  >\
					            					<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
					            				</div>\
						            			<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
						            				<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
						            			</div>\
					            			</div>\
					            		</li>';
								}
							} else {
								if (map.get(OptionIndex[j]) != null) {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="radio" checked class="question_options_radio" value="'+OptionIndex[j]+'" name="radioOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="radioOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-1">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'" value="'+map.get(OptionIndex[j])+'" >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
												</div>\
											</div>\
										</li>';
								} else {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="radio" checked class="question_options_radio" value="'+OptionIndex[j]+'" name="radioOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="radioOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-1">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'"  >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
												</div>\
											</div>\
										</li>';
								}
							}
						} else {
							if (j > 1) {
								if (map.get(OptionIndex[j]) != null) {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="radio" class="question_options_radio" value="'+OptionIndex[j]+'" name="radioOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="radioOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-1">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'" value="'+map.get(OptionIndex[j])+'" >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
													<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
												</div>\
											</div>\
						                </li>';
								} else {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="radio" class="question_options_radio" value="'+OptionIndex[j]+'" name="radioOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="radioOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-1">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'"  >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
													<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
												</div>\
											</div>\
										</li>';
								}
							} else {
								if (map.get(OptionIndex[j]) != null) {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="radio" class="question_options_radio" value="'+OptionIndex[j]+'" name="radioOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="radioOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-1">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'" value="'+map.get(OptionIndex[j])+'" >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right ">\
												</div>\
											</div>\
										</li>';
								} else {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="radio" class="question_options_radio" value="'+OptionIndex[j]+'" name="radioOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="radioOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-1">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'"  >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right ">\
												</div>\
											</div>\
										</li>';
								}
							}
							
						}
					}
				}
				$("#question-edit-form #chooseOption1_ul").html(optionsHtml);
				createImageUpload($('.uploadImg'));//生成图片编辑器,在多选框后面
				for (var j = 0; j<questionOptions.length; j++ ){
					$("input[questionOptions="+j+"]").val(questionOptions[j]);
					if (map.get(OptionIndex[j]) != null) {
						reviewImage2("imgFile"+(j+1)+"", map.get(OptionIndex[j]));
					}
				}
				console.info($(".remove"));
				$(".remove").click(function(){
					$(this).closest("div").find("input:hidden").val("");
					validateOption();
				});
				
			});
			
		}
	} else if (questionType == 1) {//多选
		var questionAnswer       = eval($("#questionAnswer").html());
		$("#multiple_many").attr("checked", true);
		$("#questionOptionValueEdit").empty();
		$("#questionContentValueEdit").empty();
		$("#questionAnswerValueEdit").empty();
        $("#questionOptionValueEdit").html(multipleMany);
        createImageUpload($('.uploadImg'));//生成图片编辑器,在多选框后面
        $("#questionContentValueEdit").html(contentTypeNoFilling);
        $("#questionSolutionValueEdit").html(solution);
        
        wysiwygEditorNoFilling = createKindEdit('#question-edit-form #wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#question-edit-form #wysiwygEditorSolution textarea[class="kindeditor"]');
		wysiwygEditorNoFilling.html(head);
		wysiwygEditorSolution.html(questionSolution);
        
        if (questionOptions != null) {
			
        	ajaxRequest("question/getQopList",{"questionId":id,"questionType":null,"optionName":null},function(res){
				if (res != null) {
					var map=new Map();
					for (var k=0; k < res.length; k++) {
						map.put(res[k].optionName,res[k].attachmentId);
					}
					for (var j = 0; j<questionOptions.length; j++ ) {
						if ( j > 1 ) {//选项大于B
							if (isContained(j,questionAnswer)) {
								if (map.get(OptionIndex[j]) != null) {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="checkbox" checked class="question_options_check" value="'+OptionIndex[j]+'" name="multipleOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="multipleOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
													<div class="col-sm-2">\
														<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'" value="'+map.get(OptionIndex[j])+'" >\
														<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
													</div>\
													<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
														<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)">\
													</div>\
											</div>\
										</li>';
								} else {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="checkbox" checked class="question_options_check" value="'+OptionIndex[j]+'" name="multipleOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="multipleOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
													<div class="col-sm-2">\
														<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'"  >\
														<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
													</div>\
													<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
														<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)">\
													</div>\
											</div>\
										</li>';
								}
									
							} else {
								if (map.get(OptionIndex[j]) != null) {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="checkbox" class="question_options_check" value="'+OptionIndex[j]+'" name="multipleOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="multipleOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
													<div class="col-sm-2">\
														<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'" value="'+map.get(OptionIndex[j])+'" >\
														<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
													</div>\
													<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
														<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)">\
													</div>\
											</div>\
										</li>';
								} else {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="checkbox" class="question_options_check" value="'+OptionIndex[j]+'" name="multipleOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="multipleOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
													<div class="col-sm-2">\
														<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'"  >\
														<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
													</div>\
													<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
														<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)"\
													</div>\
											</div>\
										</li>';
								}
							}
						} else {
							if (isContained(j,questionAnswer)) {
								if (map.get(OptionIndex[j]) != null) {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="checkbox" checked class="question_options_check" value="'+OptionIndex[j]+'" name="multipleOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="multipleOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-2">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'" value="'+map.get(OptionIndex[j])+'" >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
												</div>\
											</div>\
										</li>';
								} else {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="checkbox" checked class="question_options_check" value="'+OptionIndex[j]+'" name="multipleOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="multipleOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-2">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'"  >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
												</div>\
											</div>\
										</li>';
								}
									
							} else {
								if (map.get(OptionIndex[j]) != null) {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="checkbox" class="question_options_check" value="'+OptionIndex[j]+'" name="multipleOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="multipleOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
											<div class="form-group">\
												<div class="col-sm-2">\
													<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'" value="'+map.get(OptionIndex[j])+'" >\
													<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
												</div>\
												<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
												</div>\
											</div>\
										</li>';
								} else {
									optionsHtml += 
										'<li>\
											<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
												<input type="checkbox" class="question_options_check" value="'+OptionIndex[j]+'" name="multipleOption">\
												<span>'+OptionIndex[j]+'：</span>\
												<input name="multipleOptionValue'+(j+1)+'" type="text" style="width:81%" questionOptions='+j+' value="">\
											</div>\
												<div class="form-group">\
													<div class="col-sm-2">\
														<input type="hidden" name="uploadify'+(j+1)+'" id="uploadify'+(j+1)+'"  >\
														<input type="file"   name="attachment" id="imgFile'+(j+1)+'" class="uploadImg" value="" />\
													</div>\
													<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
													</div>\
											</div>\
										</li>';
								}
							}
						}
						
					}
				}
				$("#question-edit-form #multipleOption1_ul").html(optionsHtml);
				createImageUpload($('.uploadImg'));//生成图片编辑器,在多选框后面
				for (var j = 0; j<questionOptions.length; j++ ){
					$("input[questionOptions="+j+"]").val(questionOptions[j]);
					if (map.get(OptionIndex[j]) != null) {
						reviewImage2("imgFile"+(j+1)+"", map.get(OptionIndex[j]));
					}
				}
				console.info($(".remove"));
				$(".remove").click(function(){
					$(this).closest("div").find("input:hidden").val("");
					validateOption();
				});
			});
		}
	} else if (questionType == 2) {//判断
		$("#true_false").attr("checked", true);
		$("#questionOptionValueEdit").empty();
		$("#questionContentValueEdit").empty();
		$("#questionAnswerValueEdit").empty();
		$("#questionContentValueEdit").html(contentTypeNoFilling);
		$("#questionAnswerValueEdit").html(answerJudge);
		$("#questionSolutionValueEdit").html(solution);
		
		wysiwygEditorNoFilling = createKindEdit('#question-edit-form #wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#question-edit-form #wysiwygEditorSolution textarea[class="kindeditor"]');
		wysiwygEditorNoFilling.html(head);
		wysiwygEditorSolution.html(questionSolution);
		
		var questionAnswer       = $("#questionAnswer").html();
		if (questionAnswer == '错') {
			$('#question-edit-form input[name=judge]').eq(1).attr("checked","checked");
		} else {
			$('#question-edit-form input[name=judge]').eq(0).attr("checked","checked");
		}
 	} else if (questionType == 3) {//填空
		$("#empty_spaces").attr("checked", true);
		$("#questionOptionValueEdit").empty();
		$("#questionContentValueEdit").empty();
		$("#questionAnswerValueEdit").empty();
		$("#questionContentValueEdit").html(contentTypeFilling);
		$("#questionSolutionValueEdit").html(solution);
	
		wysiwygEditorSolution = createKindEdit('#question-edit-form #wysiwygEditorSolution textarea[class="kindeditor"]');
		wysiwygEditorSolution.html(questionSolution);
		var questionAnswer  = $("#questionAnswer").html().replace(/\^/g,',');
		//console.info(questionAnswer);
		var ans = eval(questionAnswer);
//		console.info("ans=========="+ans);
		var contentAndAnswer='';
		var str= new Array();  
		//html中,【】文本框限制最大输入长度的属性是【】。
		str = head.split('【】');
//		console.log("head========"+str);
		for (var i=0;i<=str.length-1 ;i++) {
//			if(str[i]){
				contentAndAnswer += str[i];
				if(questionAnswer.length-1>=i && ans[i]){
					//console.info(ans[i]);
					contentAndAnswer += "【";
					contentAndAnswer += ans[i];
					contentAndAnswer += "】";
				}
//			}
		}
		//console.info(contentAndAnswer);
		wysiwygEditorFilling = createKindEdit('#question-edit-form #wysiwygEditorFilling textarea[class="kindeditor"]',editAfterBulrBlank);
		wysiwygEditorFilling.html(contentAndAnswer);
	} else if (questionType == 4) {//简答
		$("#raw_text").attr("checked", true);
		$("#questionOptionValueEdit").empty();
		$("#questionContentValueEdit").empty();
		$("#questionAnswerValueEdit").empty();
		$("#questionContentValueEdit").html(contentTypeNoFilling);
		$("#questionAnswerValueEdit").html(answerNomal);
		$("#questionSolutionValueEdit").html(solution);
		
		wysiwygEditorNoFilling = createKindEdit('#question-edit-form #wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#question-edit-form #wysiwygEditorSolution textarea[class="kindeditor"]');
		wysiwygEditorAnswer = createKindEdit('#question-edit-form #wysiwygEditorAnswer textarea[class="kindeditor"]',editAfterBulrAnswer);
		wysiwygEditorNoFilling.html(head);
		wysiwygEditorAnswer.html($("#questionAnswer").html());
		wysiwygEditorSolution.html(questionSolution);
		
	} else if (questionType == 5) {//代码
		$("#code").attr("checked", true);
		$("#questionOptionValueEdit").empty();
		$("#questionContentValueEdit").empty();
		$("#questionAnswerValueEdit").empty();
		$("#questionContentValueEdit").html(contentTypeNoFilling);
		$("#questionSolutionValueEdit").html(solution);
		$("#questionAnswerValueEdit").html(answerAttachment);
    	$("#wysiwygEditorAnswer").append('<span class="show" onclick="selectFile1(this)">'+$("#orgFileName").val()+'</span>');
    	if (!isnull($("#attachmentId").val())) {
			$("#answerNomalAttachment").hide();
		}
    	$("#questionAnswerValueEdit #answerAttachment").val($("#attachmentId").val());
    	
    	
    	wysiwygEditorNoFilling = createKindEdit('#question-edit-form #wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#question-edit-form #wysiwygEditorSolution textarea[class="kindeditor"]');
		wysiwygEditorNoFilling.html(head);
		wysiwygEditorSolution.html(questionSolution);
    	
    	
	} else if (questionType == 6) {//素材
		$("#practical").attr("checked", true);
		$("#questionOptionValueEdit").empty();
		$("#questionContentValueEdit").empty();
		$("#questionAnswerValueEdit").empty();
		$("#questionContentValueEdit").html(contentTypeNoFilling);
		$("#questionSolutionValueEdit").html(solution);
		$("#questionAnswerValueEdit").html(practical);
    	$("#wysiwygEditorAnswer").append('<span class="show" onclick="selectFile2(this)">'+$("#orgFileName").val()+'</span>');
    	if (!isnull($("#attachmentId").val())) {
    		$("#practicalNomalAttachment").hide();
		}
    	$("#questionAnswerValueEdit #practicalAttachment").val($("#attachmentId").val());
    	
    	
    	wysiwygEditorNoFilling = createKindEdit('#question-edit-form #wysiwygEditorNoFilling textarea[class="kindeditor"]',editAfterBulr);
		wysiwygEditorSolution = createKindEdit('#question-edit-form #wysiwygEditorSolution textarea[class="kindeditor"]');
		wysiwygEditorNoFilling.html(head);
		wysiwygEditorSolution.html(questionSolution);
    	
	} 
	$('#question-edit-form input[name=questionTypeRadio]').attr("disabled","disabled");//题型不可选
	
	/**
	 * 上传图片 start
	 */
	var _this;
	$("#question-edit-form").on("change",".uploadImg",function(){
		_this = this;
		var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
		if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
			alertMsg("图片格式错误,请重新选择.");
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
//        	$("#"+id).attr("value",data.fileName);
//        	$("#"+id).parent().parent("div").find("input:hidden").val(data.id);
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
	$("#question-edit-form").on("change","#answerNomalAttachment",function(){
		_this = this;
		var a = this.value.split(".");
		var v = a[a.length-1].toUpperCase();
		if(v!='TXT' && v!='DOC' && v!='DOCX' && v!='XLS' && v!= 'XLSX' && v!='ZIP' && v!='RAR'){
			alertMsg("请上传txt,word,excel,zip,rar的附件格式.");
			this.value="";
			return;
		}
		var id = $(this).attr("id");
		if(!validateImgSize(id,100)){
			alertMsg("附件大于100M,请重新选择.");
			this.value="";
			return false;
		}
		mask("附件正在上传,请等待");
		ajaxFileUpload(this.id, basePath+"/attachmentCenter/upload?projectName=kcenter&fileType=2", function(data){
			unmask();
			$("#answerNomalAttachment").hide();
        	$("#wysiwygEditorAnswer").find('.show').remove();
        	$("#wysiwygEditorAnswer").append('<span class="show" onclick="selectFile1(this)">'+data.orgFileName+'</span>');
        	$("#"+id).attr("value",data.fileName);
        	$("#"+id).parent().parent("div").find("#answerAttachment").val(data.id);
        	$("#"+id).parent().parent("div").find("#attachmentUrl").val(data.url);
		});
	});
	/**
	 * 上传附件 end
	 */
	/**
	 * 上传素材 start
	 */
	var _this;
	$("#question-edit-form").on("change","#practicalNomalAttachment",function(){
		_this = this;
		var a = this.value.split(".");
		var v = a[a.length-1].toUpperCase();
		if(v!='TXT' && v!='DOC' && v!='DOCX' && v!='XLS' && v!= 'XLSX' && v!='ZIP' && v!='RAR'
			&& v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
			alertMsg("请上传txt,word,excel,zip,rar或者图片的附件格式.");
			this.value="";
			return;
		}
		var id = $(this).attr("id");
		if(!validateImgSize(id,500)){
			alertMsg("素材大于500M,请重新选择.");
			this.value="";
			return false; 
		}
		mask("素材正在上传,请等待");
		ajaxFileUpload(this.id, basePath+"/attachmentCenter/upload?projectName=kcenter&fileType=2", function(data){
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
});

function selectFile1(t) {
	var i = $(t);
	i.parent("#wysiwygEditorAnswer").find("#answerNomalAttachment").click();
}
function selectFile2(t) {
	var i = $(t);
	i.parent("#wysiwygEditorAnswer").find("#practicalNomalAttachment").click();
}
/*function returnToList() {
	var ksystemId = $("#question-edit-form #ksystemId").val();
	turnPage(basePath+'/home#questionsub/index?ksystemId='+ksystemId);
}*/

function submitToEdit() {
	$("#editButton").attr("disabled", true);

	var ksys = $("#ksystemId").val();
	if(!ksys){
		$("#editButton").attr("disabled", false);
		alertMsg("请选择知识体系");
		return;
	}
	
	var knowledgeNames = $("#knowledgeNames").val();
	if(!knowledgeNames){
		$("#editButton").attr("disabled", false);
		alertMsg("请选择知识点");
		return;
	}
	/*
	 * 提交表单的时候编辑器里面的值动态赋值给hidden隐藏域作表单验证,因为编辑器无法作值验证 begin
	 */
	var radioValue = $("input[type='radio']:checked").val();
	if (isnull(wysiwygEditorSolution.text())) {
		$('#question-edit-form #solution').val("");
	} else {
		$("#question-edit-form #solution").val(wysiwygEditorSolution.html());
	}
	/**
	 * 存文本数据 start
	 */
	$("#divText").html(wysiwygEditorSolution.html());
	$("#question-edit-form #solutionText").val($("#divText").text());
	/**
	 * 存文本数据 end
	 */
	if (radioValue == 0 || radioValue == 1 || radioValue == 2) {//单选
		if (isnull(wysiwygEditorNoFilling.text())) {
			$('#question-edit-form #questionHeadNoFilling').val("");
			alertMsg("题干为空");
			$("#editButton").attr("disabled", false);
		} else {
			$('#question-edit-form #questionHeadNoFilling').val(wysiwygEditorNoFilling.html());
		}
		
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorNoFilling.html());
		$("#question-edit-form #questionHeadNoFillingText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
	} else if (radioValue == 3) {//填空
		if (isnull(wysiwygEditorFilling.text())) {
			$('#question-edit-form #questionHeadFilling').val("");
			alertMsg("题干为空");
		} else {
			$('#question-edit-form #questionHeadFilling').val(wysiwygEditorFilling.html());
		}
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorFilling.html());
		$("#question-edit-form #questionHeadFillingText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
	} else if (radioValue == 4) {//解答
		if (isnull(wysiwygEditorNoFilling.text())) {
			$('#question-edit-form #questionHeadNoFilling').val("");
			alertMsg("题干为空");
		} else {
			$('#question-edit-form #questionHeadNoFilling').val(wysiwygEditorNoFilling.html());
		}
		if (isnull(wysiwygEditorAnswer.text())) {
			$('#question-edit-form #answer').val("");
			alertMsg("正确答案为空");
		} else {
			$('#question-edit-form #answer').val(wysiwygEditorAnswer.html());
		}
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorNoFilling.html());
		$("#question-edit-form #questionHeadNoFillingText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorAnswer.html());
		$("#question-edit-form #answerText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
	} else if (radioValue == 5 || radioValue == 6) {//代码
		if (isnull(wysiwygEditorNoFilling.text())) {
			$('#question-edit-form #questionHeadNoFilling').val("");
			alertMsg("题干为空");
		} else {
			$('#question-edit-form #questionHeadNoFilling').val(wysiwygEditorNoFilling.html());
		}
		/**
		 * 存文本数据 start
		 */
		$("#divText").html(wysiwygEditorNoFilling.html());
		$("#question-edit-form #questionHeadNoFillingText").val($("#divText").text());
		/**
		 * 存文本数据 end
		 */
	}
	
	/*
	 * 提交表单的时候编辑器里面的值动态赋值给hidden隐藏域作表单验证,因为编辑器无法作值验证 end
	 */
	
	if (radioValue == 0) {
		var radioOption = $('input:radio[name="radioOption"]:checked').val();
		if (radioOption === undefined || radioOption.length < 1) {
			$("#editButton").attr("disabled", false);
			$("#singleHintDiv").empty();
			$("#singleHintDiv").append(singleError);
			return false;
		}
	} else if (radioValue == 1) {
		var radioOption =[]; 
		$('input[name="multipleOption"]:checked').each(function(){ 
			radioOption.push($(this).val()); 
		}); 
			
		if(radioOption === undefined || radioOption.length < 2){
			$("#editButton").attr("disabled", false);
			$("#multipleHintDiv").empty();
			$("#multipleHintDiv").append(multiplError);
			return false;
		}
	}
	if (!$("#question-edit-form").valid()) {
		$("#editButton").attr("disabled", false);
	}
	if($("#question-edit-form").valid()){
		var radioOptionValue_ = [];
		if (radioValue == 0) {
			/**
			 * 选项图片和选项内容不能同时为空 begin
			 */
	/*		var radioLength = $("#chooseOption1_ul").children("li").length;
			for(var i=0;i<radioLength;i++){
				if(isnull($("#chooseOption1_ul").children("li").eq(i).find("input[name='radioOptionValue"+(i+1)+"']").val()) && isnull($("#uploadify"+(i+1)).val())) {
					$("#editButton").attr("disabled", false);
					$("#choose_answer_option").append(optionBlankError);
					return false;
				}
			}*/
			if(!validateOption()){
				$("#editButton").attr("disabled", false);
				return false;
			}
			/**
			 * 选项图片和选项内容不能同时为空 end
			 */
			$("#chooseOption1_ul").find("[name^='radioOptionValue']").each(function(){
				if(-1 < $.inArray(this.value,radioOptionValue_)){
					$("#editButton").attr("disabled", false);
					$("#choose_answer_option").append(OptionRepeatError);
					radioOptionValue_=false;
					return false;
				}
				if (!isnull(this.value)) {
					radioOptionValue_.push(this.value);
				}
			});
		}
		if(!radioOptionValue_){
			$("#editButton").attr("disabled", false);
			return;
		}
		//多选判断选项内容是否重复
		var multipleOptionValue_ = [];
		if (radioValue == 1) {
			/**
			 * 选项图片和选项内容不能同时为空 begin
			 */
		/*	var radioLength = $("#multipleOption1_ul").children("li").length;
			for(var i=0;i<radioLength;i++){
				if(isnull($("#multipleOption1_ul").children("li").eq(i).find("input[name='multipleOptionValue"+(i+1)+"']").val()) && isnull($("#uploadify"+(i+1)).val())) {
					$("#editButton").attr("disabled", false);
					$("#multipleOption1_ul").append(optionBlankError);
					return false;
				}
			}*/
			if(!validateOption()){
				$("#editButton").attr("disabled", false);
				return false;
			}
			/**
			 * 选项图片和选项内容不能同时为空 end
			 */
			$("#multipleOption1_ul").find("[name^='multipleOption']").each(function(){
				if(-1 < $.inArray(this.value,multipleOptionValue_)){
					$("#editButton").attr("disabled", false);
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
			$("#editButton").attr("disabled", false);
			return;
		}
		
		if (radioValue == 3) {
			var questionHeadFilling = $("#question-edit-form #questionHeadFilling").val();
			var questionMatchs = questionHeadFilling.match(/【([^【^】]+)】/g);
	        var matchs = questionHeadFilling.match(/【】/g);
	        if ((questionMatchs != null) && (questionMatchs.length > 0) && 
	        		!(matchs != null && matchs.length != 0)) {
	        	for (var i=0; i < questionMatchs.length; i++) {
	        		var a = questionMatchs[i].substring(1,questionMatchs[i].length-1);
	        		if (isnull($.trim(a.replace(/&nbsp;/g,"")))) {
	        			$("#editButton").attr("disabled", false);
	        			$("#fillingError").append(fillingError);
	        			return false;
	        		}
	        	}
	        } else {
	        	$("#editButton").attr("disabled", false);
	        	$("#fillingError").append(fillingError);
				return false;
	        }
		}
		var param = $("#question-edit-form").serialize();
		ajaxRequest(basePath+"/question/update",param,function(res){
			if(res.success){
				turnPage(basePath+'/home#cloudclass/course/videoRes?page=3&courseId='+$("#ksystemId").val()+'&courseName='+$("#courseName").val()+'&activeQues=ques');
			}
		});
		
	}
}

function returnToList(){
	turnPage(basePath+'/home#cloudclass/course/videoRes?page=3&courseId='+$("#ksystemId").val()+'&courseName='+$("#courseName").val()+'&activeQues=ques');
	$(".questionli a").click();
}
function manageDialogEdit(obj){
	var ksystemId = $("#question-edit-form #ksystemId").val();
	var knowledgeIds = $("#question-edit-form #knowledgeIds").val();
	dialog = openDialog("knowledgeDialog","knowledgeDialogDiv","修改知识体系",550,550,true,"保存",saveKnowledgeEdit);
	
	clearTree();
	
	initCommonZtrees("leftResource","rightResource","question/findDataForTreeRight",{"currentNodeId":knowledgeIds},"right",function(){//左右两边都加载
		initCommonZtrees("leftResource","rightResource","question/findDataForTreeLeft",{"courseId":ksystemId},"left");
	});
}
/**
 * 保存知识点
 */
function saveKnowledgeEdit(){
	viewRightTree(function(arr){
		var kpo = new Array();
		var kpname = new Array();
		$(arr).each(function(i,o){
				kpo.push(o.id);
				kpname.push(o.name);
		});
		$("#question-edit-form #knowledgeNames").val(kpname);
		$("#question-edit-form #knowledgeIds").val(kpo);
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
//点击增加选项函数 单选
function addRadio() {
  //循环数组，获取当前下标
  //获取目前列表最后一个下标
  var contentHtml = "";
  /* index = $("input[name='radioOption']").last().attr('value');*/
  var index = $("#chooseOption1_ul").children("li").last().find("input[name='radioOption']").attr('value');
  if (index == 'K') {
	  $("#singleHintDiv").empty();
	  $("#singleHintDiv").append(OptionMaxError);
  }
  for (var i = 0; i < OptionIndex.length; i++) {
      //查找下标
      if (i >= OptionIndex.length - 1) {
      	/**/
          $(".mengban").show();
          $(".prompt").show();
      } else if (index == OptionIndex[i]) {
          //将dom元素添加到页面var
    	  contentHtml += 
    		  '<li>\
    		  		<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
	    	      		<input type="radio" value="'+ OptionIndex[i + 1] +'" name="radioOption">\
	    	      		<span>'+ OptionIndex[i + 1] +'：</span>\
	    	      		<input name="radioOptionValue'+(i+2)+'" type="text" style="width:81%" value="" maxlength="30">\
	    	      	</div>\
	    	      	<div class="form-group">\
	    	      		<div class="col-sm-1">\
	    	      			<input type="hidden" name="uploadify'+(i+2)+'" id="uploadify'+(i+2)+'"  >\
	    	      			<input type="file"   name="attachment" id="imgFile'+(i+2)+'" class="uploadImg" value="" />\
	    	      		</div>\
		    	      	<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
		    	      		<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="singleDelete(this)"\
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

//点击增加选项函数 多选
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
        	contentHtml += 
        		'<li>\
        			<div class="dw"><div class="col-xs-3 paddingleft0px paddingright0px">\
		        	    <input type="checkbox" class="question_options_check" value="'+ OptionIndex[i + 1] +'" name="multipleOption">\
		        	    <span>'+ OptionIndex[i + 1] +'：</span>\
		                <input name="multipleOptionValue'+(i+2)+'" type="text" style="width:81%" value="" maxlength="30">\
		            </div>\
		            <div class="form-group">\
		                <div class="col-sm-2">\
		                	<input type="hidden" name="uploadify'+(i+2)+'" id="uploadify'+(i+2)+'"  >\
							<input type="file"   name="attachment" id="imgFile'+(i+2)+'" class="uploadImg" value="" />\
			        	</div>\
						<div class="col-xs-1 paddingleft0px paddingright0px control-label no-padding-right">\
							<input type="button" class="answer_delete_btn chooseOption1_delete_btn" value="删除" onclick="multiDelete(this)"\
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
 * 验证文件的大小
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


/**
 * 处理输入文本后，不能为空的红色提示消失
 */
function editAfterBulr(){
	if (isnull(wysiwygEditorNoFilling.text())) {
		$('#question-edit-form #questionHeadNoFilling').val("");
	} else {
		$('#question-edit-form #questionHeadNoFilling').val(wysiwygEditorNoFilling.html
		());
	}
	$("#question-edit-form").validate().element($("#questionHeadNoFilling"));
}
//处理输入文本后，不能为空的红色提示消失（填空题题干）
function editAfterBulrBlank(){
	if (isnull(wysiwygEditorFilling.text())) {
		$('#question-edit-form #questionHeadFilling').val("");
	} else {
		$('#question-edit-form #questionHeadFilling').val(wysiwygEditorFilling.html
		());
	}
	$("#question-edit-form").validate().element($("#questionHeadFilling"));
}
//处理输入文本后，不能为空的红色提示消失（简答题答案）
function editAfterBulrAnswer(){
	if (isnull(wysiwygEditorAnswer.text())) {
		$('#question-edit-form #answer').val("");
	} else {
		$('#question-edit-form #answer').val(wysiwygEditorAnswer.html
		());
	}
	$("#question-edit-form").validate().element($("#answer"));
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
	var questionType = $('input[name="questionTypeRadio"]').filter(':checked').val();
	if(questionType=="0"){
		return validateChooseOption();
	}else if(questionType=="1"){
		return validateMultipleOption();
	}
}

/**
 * 单选选项修改
 */
$("#question-edit-form").on("change","#questionOptionValueEdit input[name^='radioOptionValue']",function(){
	validateOption();
});
/**
 * 多选选项修改
 */
$("#question-edit-form").on("change","#questionOptionValueEdit input[name^='multipleOptionValue']",function(){
	validateOption();
});
