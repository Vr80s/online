var k = 0;//用来计算是第几道题
var questionNum = 0;//用来给控件起名字
var	optionLetter = ["A","B","C","D","E","F","G","H","I","J","K"];//用来遍历选项
var allQuestionIds;
var thisQuestionType;
var thisQuestionScore;
var searchJson = new Array();
var questionTable = null;

$(function() {
	//初始化试卷
	ajaxRequest(basePath+"/exam/examPaper/getExamPaper",{'id':$("#id").val()},function(data){
        if(data.success){
            var difficulty = $("#difficulty").val();//获取难度信息
            var difficultyInfo = "";
            if(difficulty == "D"){
            	difficultyInfo = "非常困难";
            }else if(difficulty == "C"){
            	difficultyInfo = "困难";
            }else if(difficulty == "B"){
            	difficultyInfo = "一般";
            }else if(difficulty == "A"){
            	difficultyInfo = "简单";
            }
            $("#difficultyInfo").text(difficultyInfo);
            var qustionList = data.resultObject;//获取试题信息遍历

            k = 0;//用来计算第几道题的
            var htmlStr = "";
            for(var i = 0; i < qustionList.length;i++ ){
            	var tempQuestionMap = qustionList[i];
            	//然后生成头部
            	var questionType = getQuestionType(tempQuestionMap.questionType);
            	htmlStr = " <div class=\"form-group\"> " +
		                  "	<label class=\"col-sm-3 control-label\" style=\"font-size: 18px;margin-left: -60px;\">"+questionType+"("+tempQuestionMap.questionScore+"分)"+"</label> " +
		                  "</div> ";
            	//遍历生成每一道题
            	htmlStr += bulidQuestionHmtl(tempQuestionMap.questionList,tempQuestionMap.questionType);
            	//把内容追加进去
                $("#bulidExam").append(htmlStr);
                //把所有的更换试题隐藏掉
            }
            $(".control-label").each(function(){
            	if($(this).text() == "更换试题"){
            		$(this).hide();
            	}
            });
        }else{
        	alertInfo(data.errorMessage);
        }
    });
	
});

//判断传过来的题型 是哪一种
function getQuestionType(questionType){
	//题型，0单选、1多选、2判断、3填空、4简答、5代码、6应用
	var questionTypes= ["单选题","多选题","判断题","填空题","简答题","代码题","实操题"];
	return questionTypes[questionType];
}

$('#returnButton').on('click',function(){
	window.location.href=basePath+"/home#/exam/examPaper/examPaper?courseId="+$("#courseId").val()+'&courseName='+$("#courseName").val();
})

//根据传过来的参数返回html字符串
function bulidQuestionHmtl(questionList,questionType){
	var htmlStr = "";
	if(questionType == 0){//单选题
		for(var i = 0;i < questionList.length; i ++){//遍历问题列表
			questionNum ++;//为了给答案控件取名字
			var question = questionList[i];
			k ++;
			htmlStr += " <div class=\"form-group\" style=\"border-bottom: 1px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.questionId+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+question.questionScore+"'> " + 
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k + "、" + question.questionHead + " <br/> ";
						
						try{
							question.options = jQuery.parseJSON(question.options);
			        	}catch(e) {
			        		question.options = null;
			        	}
			        	try{
			        		question.optionsPicture = jQuery.parseJSON(question.optionsPicture);
			        	}catch(e) {
			        		question.optionsPicture = null;
			        	}
						//遍历选项
						for(var j = 0 ;question.options != null && j<question.options.length;j++){
							htmlStr += optionLetter[j];
							
							if(j == Number(question.answer)){
								htmlStr += "<input type=\"radio\" disabled name=\"radio"+questionNum+"\" checked = 'true'> " +
										   " <span style='color:blue'> " + question.options[j] + " </span> ";
							}else{
								htmlStr += "<input type=\"radio\" disabled name=\"radio"+questionNum+"\">" +
								   		   " <span> " + question.options[j] + " </span> ";
							}
							
							if(question.optionsPicture != null && question.optionsPicture[j] != null && question.optionsPicture[j] != ""){
								htmlStr += " <img src='" + question.optionsPicture[j] + "'> ";
							}
							htmlStr += "<br/>";
						}
			htmlStr +=  " 答案说明：" + question.solution +
						"	</div> " +
						"	<label class=\"col-sm-2 control-label\" style=\"margin-top: -8px;\"><a href='javascript:void(0);' onclick='changeQuestion(this)'>更换试题</a></label> " +
						" </div> ";

		}
	}else if(questionType == 1){//多选题
		for(var i = 0;i < questionList.length; i ++){//遍历问题列表
			questionNum ++;//为了给答案控件取名字
			var question = questionList[i];
			k ++;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 1px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.questionId+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+question.questionScore+"'> " + 
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k + "、" + question.questionHead + " <br/> ";
						try{
							question.options = jQuery.parseJSON(question.options);
			        	}catch(e) {
			        		question.options = null;
			        	}
			        	try{
			        		question.optionsPicture = jQuery.parseJSON(question.optionsPicture);
			        	}catch(e) {
			        		question.optionsPicture = null;
			        	}
						//遍历选项
						for(var j = 0 ;question.options !=null && j<question.options.length;j++){
							htmlStr += optionLetter[j];
							var cnt = 0;//计数器 用来计算本次答案 是否有匹配  0 没匹配  1 与匹配
							for(var l = 0;l<question.answer.length;l ++){
								if(j == question.answer[l]){
									cnt ++;
									break;
								}
							}

							if(cnt > 0){
								htmlStr += "<input type=\"checkbox\" disabled name=\"checkbox"+questionNum+"\" checked = 'true'>" +
								   		   " <span style='color:blue'> " + question.options[j] + " </span> ";
							}else{
								htmlStr += "<input type=\"checkbox\" disabled name=\"checkbox"+questionNum+"\">" +
						   		   		   " <span> " + question.options[j] + " </span> ";
							}

							//添加单选按钮 判断 如果等于答案 就设为已选中
							if(question.optionsPicture != null && question.optionsPicture[j] != null && question.optionsPicture[j] != ""){
								htmlStr += " <img src='" + question.optionsPicture[j] + "'> ";
							}
							htmlStr += "<br/>";
						}
			htmlStr +=  " 答案解析：" + question.solution + 
						"	</div> " +
						"	<label class=\"col-sm-2 control-label\" style=\"margin-top: -8px;\"><a href='javascript:void(0);' onclick='changeQuestion(this)'>更换试题</a></label> " +
						" </div> ";
			
		}
	}else if(questionType == 2){//判断题
		for(var i = 0;i < questionList.length; i ++){//遍历问题列表
			var question = questionList[i];
			k ++;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 1px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.questionId+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+question.questionScore+"'> " + 
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k + "、" + question.questionHead + " <br/> ";

						if(question.answer == "对"){
							htmlStr += "<span style='color:blue'>对</span> <br/>" +
									   "<span >错</span> <br/>";
						}else{
							htmlStr += "<span >对</span> <br/>" +
							   		   "<span style='color:blue'>错</span> <br/>";
						}

			htmlStr +=  " 答案解析：" + question.solution + " <br/> " +
						"	</div> " +
						"	<label class=\"col-sm-2 control-label\" style=\"margin-top: -8px;\"><a href='javascript:void(0);' onclick='changeQuestion(this)'>更换试题</a></label> " +
						" </div> ";
		}
	}else if(questionType == 3){//填空题
		for(var i = 0;i < questionList.length; i ++){//遍历问题列表
			questionNum ++;//为了给答案控件取名字
			var question = questionList[i];
			k ++;
			
			try{
				question.answer = jQuery.parseJSON(question.answer);
        	}catch(e) {
        	}
			
			//暂定这样
			for(var j = 0; j < question.answer.length; j ++){
				question.questionHead = question.questionHead.replace("【】","【<span style='color:blue'>"+question.answer[j]+"</span>】");
			}
			
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 1px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.questionId+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+question.questionScore+"'> " + 
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k + "、" + question.questionHead + " <br/> ";

			htmlStr +=  " 答案解析：" + question.solution +
						"	</div> " +
						"	<label class=\"col-sm-2 control-label\" style=\"margin-top: -8px;\"><a href='javascript:void(0);' onclick='changeQuestion(this)'>更换试题</a></label> " +
						" </div> ";
		}
	}else if(questionType == 4){//简答题
		for(var i = 0;i < questionList.length; i ++){//遍历问题列表
			var question = questionList[i];
			k ++;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 1px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.questionId+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+question.questionScore+"'> " + 
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k + "、" + question.questionHead + " <br/> " +
						" 	 答案：" + question.answer + " <br/> " +
						" 答案解析：" + question.solution + 
						"	</div> " +
						"	<label class=\"col-sm-2 control-label\" style=\"margin-top: -8px;\"><a href='javascript:void(0);' onclick='changeQuestion(this)'>更换试题</a></label> " +
						" </div> ";
		}
	}else if(questionType == 5){//代码题
		for(var i = 0;i < questionList.length; i ++){//遍历问题列表
			var question = questionList[i];
			k ++;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 1px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.questionId+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+question.questionScore+"'> " + 
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> ";
			if(question.answer != null && question.answer != ""){
				htmlStr += " 	 答案：" + question.fileName + '<a href="question/questionCodeAttachmentDownload?id='+question.answer+'&fileName='+question.fileName+'">下载</a>' +" <br/> ";//这个地方答案先省略
			}else{
				htmlStr += " 	 答案：略 <br/>";//这个地方答案先省略
			}
			htmlStr +=  " 答案解析：" + question.solution + " <br/> " +
						"	</div> " +
						"	<label class=\"col-sm-2 control-label\" style=\"margin-top: -8px;\"><a href='javascript:void(0);' onclick='changeQuestion(this)'>更换试题</a></label> " +
						" </div> ";
		}
	}else if(questionType == 6){//代码题
		for(var i = 0;i < questionList.length; i ++){//遍历问题列表
			var question = questionList[i];
			k ++;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 1px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.questionId+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+question.questionScore+"'> " + 
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> ";
			if(question.answer != null && question.answer != ""){
				htmlStr += " 	 答案：" + question.fileName + '<a href="question/questionCodeAttachmentDownload?id='+question.answer+'&fileName='+question.fileName+'">下载</a>' +" <br/> ";//这个地方答案先省略
			}else{
				htmlStr += " 	 答案：略 <br/>";//这个地方答案先省略
			}
			htmlStr +=  " 答案解析：" + question.solution + " <br/> " +
						"	</div> " +
						"	<label class=\"col-sm-2 control-label\" style=\"margin-top: -8px;\"><a href='javascript:void(0);' onclick='changeQuestion(this)'>更换试题</a></label> " +
						" </div> ";
		}
	}
	
	return htmlStr;
}

function changeQuestion(obj){
	//获取此次更换的题的ID 、题型ID、所有的题的ID
	var thisQuestion=$(obj).parent().parent().find("[name='lastQuestionId']").eq("0");//当前行的题
	thisQuestionType = $(obj).parent().parent().find("[name='lastQquestionType']").eq("0").val();//当前行的题型
	lastQquestionScore = $(obj).parent().parent().find("[name='lastQquestionScore']").eq("0").val();//当前行的分数
	$("#search_kNum").val($(obj).parent().parent().find("[name='kNum']").eq("0").val());//序号值 k
	$("#showQuestion").empty();
	//把已经有的查询条件清空
	$("#search_difficulty").val("");
	$("#search_questionHead").val("");
	$("#search_kpointIds").val("");
	
	allQuestionIds = new Array();;//目前页面内所有的试题ID
	$("[name='lastQuestionId']").each(function(index){
		allQuestionIds.push("'"+$(this).val()+"'");
	});
	if(questionTable == null){//如果为空的时候 就渲染
		loadQuestionTable();
	}else{//否则就根据新的查询条件构建
		search();
	}

	ajaxRequest(basePath+"/cloudclass/videores/showTreeExamPaperAdd",{"courseId":$("#courseId").val(),"courseName":$("#courseName").val()},function(zNodes){
		debugger
		initSelectTree(zNodes);//初始化选择知识点
	})
	var dialog = openDialog("changeQuestionDialog","dialogChangeQuestionDiv","更换试题",1000,700,true,"确定",function(){
		if($("input[name='questionRadio']:checked").length==0){//如果等于
			alertInfo("请选择一道题进行替换！");
			return false;
		}
		//把那一行的内容替换掉
		$(obj).parent().parent().children().eq(0).replaceWith($("#showQuestion").children().eq(0).html());//替换掉内容 完成提
		var lastQuestionIds = new Array();//把目前所有的题的ID拼接起来传到后台
		$("[name='lastQuestionId']").each(function(){
			lastQuestionIds.push("'"+this.value+"'");
		});
		//替换以后 要刷新难度
		ajaxRequest(basePath+"/exam/examPaper/refreshDifficulty",{"id":lastQuestionIds.join(",")},function(data){
			if(data.success){
				var difficultyInfo = "";
                if(data.resultObject == "D"){
                	difficultyInfo = "非常困难";
                }else if(data.resultObject == "C"){
                	difficultyInfo = "困难";
                }else if(data.resultObject == "B"){
                	difficultyInfo = "一般";
                }else if(data.resultObject == "A"){
                	difficultyInfo = "简单";
                }
				$("#difficultyInfo").text(difficultyInfo);//替换难度
				$("#difficulty").val(data.resultObject);//更新难度值
			}else{
				alertInfo(data.errorMessage);
			}
		})

		dialog.dialog( "close" );
	 });
}

//初始化下拉选择的树
function initSelectTree(zNodes){
	var setting = {
				view: {
					selectedMulti: true,
					expandSpeed: "normal"
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
	$.fn.zTree.init($("#selectTree"), setting, zNodes);
}

function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectTree"),
	nodes = zTree.getCheckedNodes(true),
	v = "";
	id = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		id += "'"+nodes[i].id + "',";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (id.length > 0 ) id = id.substring(0, id.length-1);
	var kpointObj = $("#search_kpointNames");
	var kpointIdsObj = $("#search_kpointIds");
	kpointObj.attr("value", v);
	kpointIdsObj.attr("value", id);
}
function showMenu() {
	var kpointObj = $("#search_kpointNames");
	var cityOffset = $("#search_kpointNames").offset();
//	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + kpointObj.outerHeight() + "px"}).slideDown("fast");
	$("#menuContent").css({left:"439px",top:"35px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "search_kpointNames" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

function loadQuestionTable(){
	//生成Table
	var dataFields = [
	      {title: '序号', "class": "center", "width": "5%","data": 'id', "sortable": false,"mRender":function (data, display, row) {
		          return "<input type='radio' name='questionRadio' value='"+data+"' onclick='showQuestion(this)'>";
		  }},
	      {title: '题干', "class": "center","width": "15%","data": 'paperName', "sortable": false,"mRender":function (data, display, row) {
		    	  var questionHead = "";
		    	  if(row.questionHeadText != null){
		    		  questionHead = row.questionHeadText;
		    	  }else{
		    		  questionHead = row.questionHead;
		    	  }
		          return questionHead;
		  }},
	      {title: '所在知识点', "class": "center","width": "15%","data": 'chapterName', "sortable": false},
	      {title: '难度', "class": "center", "width": "10%","data": 'difficulty', "sortable": false,"mRender":function (data, display, row) {
		      	var difficulty;
		      	switch (data)
		      	{
		        	case "A":
		        		difficulty = "简单";
		        	  break;
		        	case "B":
		        		difficulty = "一般";
		        	  break;
		        	case "C":
		        		difficulty = "难";
		        	  break;
		        	case "D":
		        		difficulty = "困难";
		        	  break;
		      	}
	          return difficulty;
	      }}
	  ];
		searchJson = [];//清空数组
		searchJson.push('{"tempMatchType":"9","propertyName":"search_allQuestionIds","propertyValue1":"' + allQuestionIds + '","tempType":"String"}');
		searchJson.push('{"tempMatchType":"10","propertyName":"search_questionType","propertyValue1":"' + thisQuestionType + '","tempType":"String"}');
	 questionTable = initTables("questionTable", basePath + "/exam/examPaper/findQuestionList", dataFields, true, true, 0,null,searchJson,function(data){
		 $("#questionTable").next(".row").css("z-index","0");
		 $("#questionTable").next(".row").css("position","relative");
	 });
}

function search(){
	searchJson = [];//清空数组
	searchJson.push('{"tempMatchType":"9","propertyName":"search_allQuestionIds","propertyValue1":"' + allQuestionIds + '","tempType":"String"}');
	searchJson.push('{"tempMatchType":"10","propertyName":"search_questionType","propertyValue1":"' + thisQuestionType + '","tempType":"String"}');
	searchButton(questionTable,searchJson);
}

//预览试题
function showQuestion(obj){
	var oo = $(obj).parent().parent();
	var row = questionTable.fnGetData(oo); // get datarow
	var htmlStr = "";//预览试题
	var k = $("#search_kNum").val();
	debugger
	if(row.questionType == 0){//单选题
			questionNum ++;
			var question = row;
			htmlStr += " <div class=\"form-group\" style=\"border-bottom: 0px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+lastQquestionScore+"'> " +
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> ";
						
						try{
							question.options = jQuery.parseJSON(question.options);
			        	}catch(e) {
//			        		question.options = null;
			        	}
			        	try{
			        		question.optionsPicture = jQuery.parseJSON(question.optionsPicture);
			        	}catch(e) {
//			        		question.optionsPicture = null;
			        	}
						//遍历选项
						for(var j = 0 ;question.options != null && j<question.options.length;j++){
							htmlStr += optionLetter[j];
							
							if(j == Number(question.answer)){
								htmlStr += "<input type=\"radio\" name=\"radio"+questionNum+"\" checked = 'true'> " +
										   " <span style='color:blue'> " + question.options[j] + " </span> ";
							}else{
								htmlStr += "<input type=\"radio\" name=\"radio"+questionNum+"\">" +
								   		   " <span> " + question.options[j] + " </span> ";
							}
							
							if(question.optionsPicture != null && question.optionsPicture[j] != null && question.optionsPicture[j] != ""){
								htmlStr += " <img src='" + question.optionsPicture[j] + "'> ";
							}
							htmlStr += "<br/>";
						}
			htmlStr +=  " 答案说明：" + question.solution +
						"	</div> " +
						" </div> ";

	}else if(row.questionType == 1){//多选题
			questionNum ++;//为了给答案控件取名字
			var question = row;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 0px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+lastQquestionScore+"'> " +
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> ";
						try{
							question.options = jQuery.parseJSON(question.options);
			        	}catch(e) {
			        		question.options = null;
			        	}
			        	try{
			        		question.optionsPicture = jQuery.parseJSON(question.optionsPicture);
			        	}catch(e) {
			        		question.optionsPicture = null;
			        	}
						//遍历选项
						for(var j = 0 ;question.options !=null && j<question.options.length;j++){
							htmlStr += optionLetter[j];
							var cnt = 0;//计数器 用来计算本次答案 是否有匹配  0 没匹配  1 与匹配
							for(var l = 0;l<question.answer.length;l ++){
								if(j == question.answer[l]){
									cnt ++;
									break;
								}
							}

							if(cnt > 0){
								htmlStr += "<input type=\"checkbox\" name=\"checkbox"+questionNum+"\" checked = 'true'>" +
								   		   " <span style='color:blue'> " + question.options[j] + " </span> ";
							}else{
								htmlStr += "<input type=\"checkbox\" name=\"checkbox"+questionNum+"\">" +
						   		   		   " <span> " + question.options[j] + " </span> ";
							}

							//添加单选按钮 判断 如果等于答案 就设为已选中
							if(question.optionsPicture != null && question.optionsPicture[j] != null && question.optionsPicture[j] != ""){
								htmlStr += " <img src='" + question.optionsPicture[j] + "'> ";
							}
							htmlStr += "<br/>";
						}
			htmlStr +=  " 答案解析：" + question.solution + 
						"	</div> " +
						" </div> ";
			
	}else if(row.questionType == 2){//判断题
			var question = row;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 0px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+lastQquestionScore+"'> " +
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> ";

						if(question.answer == "对"){
							htmlStr += "<span style='color:blue'>对</span> <br/>" +
									   "<span >错</span> <br/>";
						}else{
							htmlStr += "<span >对</span> <br/>" +
							   		   "<span style='color:blue'>错</span> <br/>";
						}

			htmlStr +=  " 答案解析：" + question.solution + " <br/> " +
						"	</div> " +
						" </div> ";
	}else if(row.questionType == 3){//填空题
			var question = row;
			//暂定这样
			for(var j = 0; j > question.answer.lenght; j ++){
				question.questionHead.replace("【】","<span style='color:blue'>"+question.answer[j]+"</span>");
			}
			
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 0px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+lastQquestionScore+"'> " +
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> ";

			htmlStr +=  " 答案解析：" + question.solution +
						"	</div> " +
						" </div> ";
	}else if(row.questionType == 4){//简答题
		for(var i = 0;i < questionList.length; i ++){//遍历问题列表
			var question = row;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 0px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+lastQquestionScore+"'> " +
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> " +
						" 	 答案：" + question.answer + " <br/> " +
						" 答案解析：" + question.solution + 
						"	</div> " +
						" </div> ";
		}
	}else if(row.questionType == 5){//代码题
			var question = row
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 0px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+lastQquestionScore+"'> " +
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> " +
						" 	 答案：" + "下载" + " <br/> " +//这个地方答案先省略
						" 答案解析：" + question.solution + " <br/> " +
						"	</div> " +
						" </div> ";
	}else if(row.questionType == 6){//代码题
			var question = row;
			htmlStr +=  " <div class=\"form-group\" style=\"border-bottom: 0px dotted black;\"> " +
						"	<div class=\"col-sm-10\" style=\"text-align: left;padding-left: 182px;\"> " +
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
						"	<input type='hidden' name='lastQquestionType' id='last_question_id_"+k+"' value='"+question.questionType+"'> " +
						"	<input type='hidden' name='lastQquestionScore' id='last_question_score_"+k+"' value='"+lastQquestionScore+"'> " +
						"	<input type='hidden' name='kNum' value='"+k+"'> " +
						" " + k +"、"+ question.questionHead + " <br/> " +
						" 	 答案：" + "下载" + " <br/> " +//这个地方答案先省略
						" 答案解析：" + question.solution + 
						"	</div> " +
						" </div> ";
	}

	$("#showQuestion").html(htmlStr);//放入预览区域
}

$(".save_bx2").on("click",function(){
	if($("#updateExamPaper-form").valid()){//如果验证通过就把数据 返回到后台
		//验证通过以后
		mask();
		backupAndRestore(0);
		$("#updateExamPaper-form").attr("action", basePath+"/exam/examPaper/updateExamPaperById");
        $("#updateExamPaper-form").ajaxSubmit(function(data){
        	backupAndRestore(1);
        	data = getJsonData(data);
        	unmask();
            if(data.success){
                layer.msg(data.resultObject);
                //跳转回上一页
                window.location.href=basePath+"/home#/exam/examPaper/examPaper?courseId="+$("#courseId").val()+'&courseName='+$("#courseName").val();
            }else{
            	alertInfo(data.errorMessage);
            }
        });
	}
})

/**
 * 用来处理提交前后的控件名字
 */
function backupAndRestore(type){
	if(type == 0){
		$(".difficultyAllocation").each(function(){//把这个提交的名称先设置
			$(this).attr("name","difficultyAllocation");
		});
		//提交的时候试题名称
		$(".completionQuestionCnt_question").each(function(){
			$(this).attr("name","completionQuestionCntQuestion");
		});
		$(".completionQuestionCnt_difficulty").each(function(){
			$(this).attr("name","completionQuestionCntDifficulty");
		});

		$(".shortAnswerQuestionCnt_question").each(function(){
			$(this).attr("name","shortAnswerQuestionCntQuestion");
		});
		
		$(".shortAnswerQuestionCnt_difficulty").each(function(){
			$(this).attr("name","shortAnswerQuestionCntDifficulty");
		});

		$(".codeQuestionCnt_question").each(function(){
			$(this).attr("name","codeQuestionCntQuestion");
		});
		$(".codeQuestionCnt_difficulty").each(function(){
			$(this).attr("name","codeQuestionCntDifficulty");
		});
		
		$(".practicalQuestionCnt_question").each(function(){
			$(this).attr("name","practicalQuestionCntQuestion");
		});
		$(".practicalQuestionCnt_difficulty").each(function(){
			$(this).attr("name","practicalQuestionCntDifficulty");
		});
		
	}else if(type == 1){
		$(".difficultyAllocation").each(function(){//把这个提交的名称先设置
			$(this).attr("name",$(this).attr("id"));
		});
		//提交的时候试题名称
		$(".completionQuestionCnt_question").each(function(){
			$(this).attr("name",$(this).attr("id"));
		});
		
		$(".completionQuestionCnt_difficulty").each(function(){
			$(this).attr("name",$(this).attr("id"));
		});

		$(".shortAnswerQuestionCnt_question").each(function(){
			$(this).attr("name",$(this).attr("id"));
		});
		
		$(".shortAnswerQuestionCnt_difficulty").each(function(){
			$(this).attr("name",$(this).attr("id"));
		});

		$(".codeQuestionCnt_question").each(function(){
			$(this).attr("name",$(this).attr("id"));
		});
		$(".codeQuestionCnt_difficulty").each(function(){
			$(this).attr("name",$(this).attr("id"));
		});
		
		$(".practicalQuestionCnt_question").each(function(){
			$(this).attr("name",$(this).attr("id"));
		});
		$(".practicalQuestionCnt_difficulty").each(function(){
			$(this).attr("name",$(this).attr("id"));
		});
	}
}
