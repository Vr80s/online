var distributionForm;
var tempDefultSetting;//用于切换主观题设置方式临时保存的地方
var tempDetailSetting;
var tempSubForm;//提交表单的时候 需要处理一些数据   先用该值把处理前的表单缓存一下 之后再换回来
var questionNum = 0;//用来动态生成题目时候的计数器 控件名称不唯一
var searchJson = new Array();
var questionTable = null;
var	optionLetter = ["A","B","C","D","E","F","G","H","I","J","K"];//用来遍历选项
var k = 0;//用来计算是第几道题 生成的时候 使用
var allQuestionIds;
var thisQuestionType;
var thisQuestionScore;
$(function() {
	$("#addexamPaper-form").validate({
        messages: {
        	paperName: {
				required:"请输入试卷名称",
				maxlength:"试卷名称过长，应小于50个字符"
            },
            score: {
				required:"请输入分数",
				//range:"请输入1-500整数！",
				digits:"请输入整数"
			},
			duration: {
				required:"请输入时长",
				//range:"请输入1-500整数！",
				digits:"请输入整数"
			},
			difficultyAllocation:{
				required:"难度分配不能为空",
			}
        }
     });
	
	distributionForm = $("#distribution-form").validate({
		messages: {
			practicalQuestionCntD: {
				range:"分配题数最小为0最大为1000"
			},
			practicalQuestionCntC: {
				range:"分配题数最小为0最大为1000"
			},
			practicalQuestionCntB: {
				range:"分配题数最小为0最大为1000"
			},
			practicalQuestionCntA: {
				range:"分配题数最小为0最大为1000"
			}
		}
	});

	$.validator.addMethod(
			"lessThanTotalQuestion", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number(value) > Number($(element).parent().parent().find("span").eq(0).text())){
					return false;
				}
				return true;
			}, 
			'可用题不足！'//验证提示信息
	);

	$.validator.addMethod(
			"equal0", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number($("#totalScoreSpan").text())==0)
				{
					return true;
				}
				if(Number(value) != 0 && Number(value) > 0){
					return false;
				}
				
				return true;
			}, 
			'还有分数未分配！'//验证提示信息
	);
	
	$.validator.addMethod(
			"equalTotalScore", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number(value) < 0){
					return false;
				}
				
				return true;
			}, 
			'已超过总分数！'//验证提示信息
	);
	
	$.validator.addMethod(
			"syncRequired", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number(value) == 0 || value == ""){//如果本题未分配  
					//要求总分也未分配
					if(Number($(element).parent().parent().find("[name='strategyTotalScore']").eq(0).val()) == 0){//如果伟
						return true;
					}
					return false;
				}else{
					if(Number($(element).parent().parent().find("[name='strategyTotalScore']").eq(0).val()) == 0){//如果伟
						return false;
					}
					return true;
				}
			}, 
			'题数和分数必须同时分配'//验证提示信息
	);
	
	$.validator.addMethod(
			"syncRequired2", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number($(element).parent().parent().find("[name='strategyTotalScore']").eq(0).val()) > 0){
					if(Number(value) == 0 || value == "0,0,0,0"){
						return false;
					}
				}
				return true;
			}, 
			'请进行难度分配'//验证提示信息
	);
	
	//难度分配验证
	$.validator.addMethod(
			"lessThanTotalQuestion2", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number(value) == 0){
					return true;//如果为0就不验证
				}
				var practicalQuestionCnt = 0;
				$(".practicalQuestionCnt").each(function(){
					var tempCnt = Number(this.value);
					if(isNaN(tempCnt)){
						tempCnt = 0;
					}
					practicalQuestionCnt += tempCnt;
				})
				var totalQuestionHid = Number($("#totalQuestionHid").val());
				//判断所有分配的题的数量加起来 是否超过可以分配的数量
				if(Number(practicalQuestionCnt) == totalQuestionHid){
					return true;
				}
				return false;
			},
			'分配的题目数量必须等于总题数'//验证提示信息
	);

	//难度分配验证
	$.validator.addMethod(
			"lessThanOwn", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number(value) == 0){
					return true;//如果为0就不验证
				}

				var tempCnt = Number(value);
				if(isNaN(tempCnt)){
					tempCnt = 0;
				}
				
				var totalQuestion = Number($(element).parent().parent().find(".totalQuestion").eq(0).text());

				if(tempCnt > totalQuestion){
					return false;
				}
				return true;
			}, 
			'题目数量不足'//验证提示信息
	);

	//细分设置验证
	$.validator.addMethod(
			"totalScoreEqualStrategyTotalScore", 
			function(value, element, param) {//验证规则  strategyCnt
				var strategyTotalScore = Number($(element).parent().parent().parent().parent().find("[name='strategyTotalScore']").eq("0").val());//先取出总分  strategyTotalScore
				if(isNaN(strategyTotalScore)){
					strategyTotalScore = 0;
				}
				
				var idStr = $(element).parent().parent().parent().parent().find("[name='strategyCnt']").eq("0").attr("id");//取出获取每道题分值的ID值  用来用类获取所有控件 计算总和 是否等于 strategyTotalScore
				var totalScore = 0;
				$("." + idStr + "_question").each(function(){//计算值
					var tempScore = Number(this.value);
					if(isNaN(tempScore)){
						tempScore = 0;
					}
					totalScore += tempScore;
				});
				
				if(totalScore == strategyTotalScore){//只有分值相等才算通过验证
					return true;
				}
				
				return false;
			}, 
			'分值加起来必须等于该类题总分'//验证提示信息
	);

	$.validator.addMethod(
			"checkQuestionCnt", //验证方法名称
			function(value, element, param) {//验证规则
				//这个验证有两个验证   第一种  本身该选项只有0题  第二种 本次分配的该种题型已经超过总共的题量
				if($(element).find("option:selected").text().indexOf("共0题") != -1){//不为1 说明 不是0题
					return false;
				}
				
				//如果不为0 那么取出隐藏域中的ID值 
				var difficultyCnt = $(element).parent().parent().parent().parent().find("[name='difficultyCnts']").eq("0").val().split(",");
				var tempDifficultyCnt = 0;//该难度题的总共数量
				if(value=="D"){
					tempDifficultyCnt = Number(difficultyCnt[0]);
				}else if(value == "C"){
					tempDifficultyCnt = Number(difficultyCnt[1]);
				}else if(value == "B"){
					tempDifficultyCnt = Number(difficultyCnt[2]);
				}else if(value == "A"){
					tempDifficultyCnt = Number(difficultyCnt[3]);
				}
				var tempAlreadyDifficultyCnt = 0;//当前已经分配该题型计数器
				//获取到所有的难度设置值
				$(element).parent().parent().parent().find("select").each(function(){
					if($(this).val() == value){//如果值相等就给计数器 +1
						tempAlreadyDifficultyCnt ++; 
					}
				});
				
				if(tempAlreadyDifficultyCnt > tempDifficultyCnt){//已经分配的数量大于数据库中共有的数据量 返回false
					return false;
				}

				return true;
			}, 
			'题目数量不足'//验证提示信息
	);

	$("#getTree").append('<ul id="treeDemo_'+$("#courseId").val()+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
	Tree2 = loadTree2("treeDemo_"+$("#courseId").val(),null);
	
	//加载选择资源的树节点
	loadTreeAll("knowledge",basePath+"/cloudclass/videores/showTreeExamPaperAdd",{"courseId":$("#courseId").val(),"courseName":$("#courseName").val()});

	//先把默认div中的内容做个快照
	tempDefultSetting = $("#defultSetting").html();
	//再把tempDetailSetting  缓存内容赋值给 detailSetting DIV
	tempDetailSetting = $("#detailSetting").html();
	$("#detailSetting").empty();
});

$('#returnButton').on('click',function(){
	window.location.href=basePath+"/home#/exam/examPaper/examPaper?courseId="+$("#courseId").val()+'&courseName='+$("#courseName").val();
})

$("#paperName").on("keyup change",function(){
	var arr = $(this).val().split("");
	$("#wordCnt").text(arr.length);
})

$("#score").on("keyup change",function(){
	if(isNaN($(this).val())){
		$(this).val(0);
	}
	// && $(this).val()<=500
	if($(this).val()>0){//在合法范围内生效
		$("#totalScoreSpan").text($(this).val());
		changeInfo($("#radioQuestionCnt"));//$("#radioQuestionCnt") 随便选了下面分配题的一个文本框 因为这个方法传哪个文本框都一样
	}
})

function changeInfo(obj){
	//处理完后 获取7组总分的得分 计算已经分配的分数
	var totalScore1 = Number($("#mainDiv").find("[name='strategyTotalScore']").eq(0).val());
	var totalScore4 = Number($("#mainDiv").find("[name='strategyTotalScore']").eq(1).val());
	var totalScore7 = Number($("#mainDiv").find("[name='strategyTotalScore']").eq(2).val());
	var totalScore10 = Number($("#mainDiv").find("[name='strategyTotalScore']").eq(3).val());
	var totalScore13 = Number($("#mainDiv").find("[name='strategyTotalScore']").eq(4).val());
	var totalScore16 = Number($("#mainDiv").find("[name='strategyTotalScore']").eq(5).val());
	var totalScore19 = Number($("#mainDiv").find("[name='strategyTotalScore']").eq(6).val());

	var totalScore = Number($("#score").val());
	if(isNaN(totalScore1)){
		totalScore1 = 0;
	}
	
	if(isNaN(totalScore4)){
		totalScore4 = 0;
	}
	
	if(isNaN(totalScore7)){
		totalScore7 = 0;
	}
	
	if(isNaN(totalScore10)){
		totalScore10 = 0;
	}
	
	if(isNaN(totalScore13)){
		totalScore13 = 0;
	}
	
	if(isNaN(totalScore16)){
		totalScore16 = 0;
	}
	
	if(isNaN(totalScore19)){
		totalScore19 = 0;
	}
	
	if(isNaN(totalScore)){
		totalScore = 0;
	}
	
	//得分
	$("#distributionScore").text(totalScore1 + totalScore4 + totalScore7 + totalScore10 + totalScore13 + totalScore16 + totalScore19);
	$("#unDistributionScore").text(totalScore - (totalScore1 + totalScore4 + totalScore7 + totalScore10 + totalScore13 + totalScore16 + totalScore19));
	$("#unDistributionScore_hid").val(totalScore - (totalScore1 + totalScore4 + totalScore7 + totalScore10 + totalScore13 + totalScore16 + totalScore19));
}

$("#radioQuestionCnt,#radioQuestionCnt_totalScore,#multiselectQuestionCnt,#multiselectQuestionCnt_totalScore,#nonQuestionCnt,#nonQuestionCnt_totalScore").on("keyup change",function(event){
	if(isNaN($(this).val())){
		$(this).val(0);
	}

	var totalQuestion = Number($(this).parent().parent().find("[type='text']").eq(0).val());
	var totalScore = Number($(this).parent().parent().find("[type='text']").eq(1).val());
	
	//本级联动
	if(totalQuestion !=0 && totalScore != 0){
		$(this).parent().parent().find("[type='text']").eq(2).val(totalScore/totalQuestion);			
	}else if(totalQuestion==0 || totalScore==0){
		$(this).parent().parent().find("[type='text']").eq(2).val(0);
	}

	//负数时的处理
	if(totalQuestion < 0){
		$(this).parent().parent().find("[type='text']").eq(0).val(0);
		$(this).parent().parent().find("[type='text']").eq(2).val(0);
	}
	if(totalScore < 0){
		$(this).parent().parent().find("[type='text']").eq(1).val(0);
		$(this).parent().parent().find("[type='text']").eq(2).val(0);
	}
	
	changeInfo(this);
	$("[name='strategyCnt']").each(function(){
		$(this).valid()
	});
	$("#unDistributionScore_hid").valid();
});

//值改变的时候 把同行的难度分配清空 要求其重新分配
$("input[name='strategyCnt']").on("change",function(){
	$(this).parent().parent().find(".difficultyAllocation").eq("0").val("0,0,0,0");
	$(this).parent().parent().find(".difficultyAllocation_span").eq("0").text("");
});

$(".save_bx2").on("click",function(){
//	$("#addexamPaper-form").valid();
//	return ;
	if($("#addexamPaper-form").valid()){//如果验证通过就把数据 返回到后台
		//验证通过以后
		mask();
		backupAndRestore(0);
		$("#addexamPaper-form").attr("action", basePath+"/exam/examPaper/saveExamPaper");
        $("#addexamPaper-form").ajaxSubmit(function(data){
        	backupAndRestore(1);
        	try{
        		data = jQuery.parseJSON(jQuery(data).text());
        	}catch(e) {
        		data = data;
        	}
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

$(".save_bx").on("click",function(){
//	$("#addexamPaper-form").valid();
//	return ;
	var valid = 0;
	$("[name='strategyScore']").each(function(){
		if(!$(this).valid()){
			valid ++;
		}
	});
	
	$("[name='strategyCnt']").each(function(){
		if(!$(this).valid()){
			valid ++;
		}
	});
	
	$("[name='strategyTotalScore']").each(function(){
		if(!$(this).valid()){
			valid ++;
		}
	});

	if(valid > 0){
		return false;
	}
	
	if($("#addexamPaper-form").valid() && $("input[name='strategyScore']").valid()){//如果验证通过就把数据 返回到后台
		//验证通过以后
		mask();
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

		$("#addexamPaper-form").attr("action", basePath+"/exam/examPaper/bulidExamPaper");
        $("#addexamPaper-form").ajaxSubmit(function(data){
        	
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
        	
        	try{//暂时不需要转换 先注释掉
        		data = jQuery.parseJSON(jQuery(data).text());
        	}catch(e) {
        		data = data;
        	}
        	unmask();
            if(data.success){
                layer.msg("生成成功！");
                //先保存 bulidExamTop
                var bulidExamTop = $("#bulidExamTop");
                //再先清空bulidExam
                $("#bulidExam").empty();
                //最后再生成 bulidExam
                $("#bulidExam").append(bulidExamTop);
                
                var examPaper = data.resultObject.examPaper;//获取难度信息
                var difficultyInfo = "";
                if(examPaper.difficulty == "D"){
                	difficultyInfo = "非常困难";
                }else if(examPaper.difficulty == "C"){
                	difficultyInfo = "困难";
                }else if(examPaper.difficulty == "B"){
                	difficultyInfo = "一般";
                }else if(examPaper.difficulty == "A"){
                	difficultyInfo = "简单";
                }
                $("#difficulty").val(examPaper.difficulty);//保存了从后台传过来的试卷难度值
                $("#difficultyInfo").text(difficultyInfo);
                var qustionList = data.resultObject.qustionList;//获取试题信息遍历
                k = 0;//用来计算第几道题的
                var htmlStr = "";
                for(var i = 0; i < qustionList.length;i++ ){
                	var tempQuestionMap = qustionList[i];
                	//然后生成头部
                	var questionType = getQuestionType(tempQuestionMap.questionType);
                	htmlStr = " <div class=\"form-group\"> " +
			                  "	<label class=\"col-sm-3 control-label\" style=\"font-size: 18px;\">"+questionType+"("+tempQuestionMap.totalScore+"分)"+"</label> " +
			                  "</div> ";
                	//遍历生成每一道题
                	htmlStr += bulidQuestionHmtl(tempQuestionMap.questionList,tempQuestionMap.questionType);
                	//把内容追加进去
                    $("#bulidExam").append(htmlStr);
                }
                $("#bulidExam").show();
                $("#bulidButton").show();
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

//判断传过来的题型 是哪一种
function getQuestionType(questionType){
	//题型，0单选、1多选、2判断、3填空、4简答、5代码、6应用
	var questionTypes= ["单选题","多选题","判断题","填空题","简答题","代码题","实操题"];
	return questionTypes[questionType];
}
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
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
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
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
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
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
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
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
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
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
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
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
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
						"	<input type='hidden' name='lastQuestionId' id='last_question_id_"+k+"' value='"+question.id+"'> " +
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
	$("#search_kpointNames").val("");
	
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
		
		initSelectTree(zNodes);//初始化选择知识点
	})
	var dialog = openDialog("changeQuestionDialog","dialogChangeQuestionDiv","更换试题",1000,700,true,"确定",function(){
		if($("input[name='questionRadio']:checked").length==0){//如果等于
			alertInfo("请选择一道题进行替换！");
			return false;
		}
		//把那一行的内容替换掉
		$(obj).parent().parent().children().eq(0).replaceWith($("#showQuestion").children().eq("0").html());//替换掉内容 完成提
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
			}else{
				alertInfo(data.errorMessage);
			}
		})

		dialog.dialog( "close" );
	 });
}
//初始化下拉选择的树
function initSelectTree(zNodes){
//	var setting = {
//			check: {
//				enable: true,
//				chkboxType: {"Y":"", "N":""}
//			},
//			view: {
//				dblClickExpand: false
//			},
//			data: {
//				simpleData: {
//					enable: true
//				}
//			},
//			callback: {
//				beforeClick: beforeClick,
//				onCheck: onCheck
//			}
//		};
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
	$("#search_kpointNames").val(v);
	$("#search_kpointIds").val(id);
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
	 searchJson.push('{"tempMatchType":"11","propertyName":"search_courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
	 questionTable = initTables("questionTable", basePath + "/exam/examPaper/findQuestionList", dataFields, true, true, 0,null,searchJson,function(data){
		 $("#questionTable").next(".row").css("z-index","0");
		 $("#questionTable").next(".row").css("position","relative");
	 });
}

function search(){
	searchJson = [];//清空数组
	searchJson.push('{"tempMatchType":"9","propertyName":"search_allQuestionIds","propertyValue1":"' + allQuestionIds + '","tempType":"String"}');
	searchJson.push('{"tempMatchType":"10","propertyName":"search_questionType","propertyValue1":"' + thisQuestionType + '","tempType":"String"}');
	searchJson.push('{"tempMatchType":"11","propertyName":"search_courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
	searchButton(questionTable,searchJson);
}

//预览试题
function showQuestion(obj){
	var oo = $(obj).parent().parent();
	var row = questionTable.fnGetData(oo); // get datarow
	var htmlStr = "";//预览试题
	var k = $("#search_kNum").val();
	
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
						" " + k +"、"+ question.questionHead + " <br/> ";
			if(question.answer != null && question.answer != ""){
				htmlStr += " 	 答案：" + question.fileName + '<a href="question/questionCodeAttachmentDownload?id='+question.answer+'&fileName='+question.fileName+'">下载</a>' +" <br/> ";//这个地方答案先省略
			}else{
				htmlStr += " 	 答案：略 <br/>";//这个地方答案先省略
			}
			htmlStr +=  " 答案解析：" + question.solution + " <br/> " +
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
						" " + k +"、"+ question.questionHead + " <br/> ";
			if(question.answer != null && question.answer != ""){
				htmlStr += " 	 答案：" + question.fileName + '<a href="question/questionCodeAttachmentDownload?id='+question.answer+'&fileName='+question.fileName+'">下载</a>' +" <br/> ";//这个地方答案先省略
			}else{
				htmlStr += " 	 答案：略 <br/>";//这个地方答案先省略
			}
			htmlStr +=  " 答案解析：" + question.solution + " <br/> " +
						"	</div> " +
						" </div> ";
	}

	$("#showQuestion").html(htmlStr);//放入预览区域
}

$("#selKpoint,#selKpoint1").on("click",function(){
	  //重新根据已经选择的构建树
	  var arrNodes = $("#kpointIds").val().split(",");
	  for(var i = 0;i<arrNodes.length;i++){
		  var node1 = Tree1.getNodesByParam("id",arrNodes[i],null);
		  
		  if(node1.length > 0){
			  node1[0].checked = true;
			  Tree1.updateNode(node1[0]);
			  node1[0].getParentNode().checked = true;
			  Tree1.updateNode(node1[0].getParentNode());
			  node1[0].getParentNode().getParentNode().checked = true;
			  Tree1.updateNode(node1[0].getParentNode().getParentNode());
			  node1[0].getParentNode().getParentNode().getParentNode().checked = true;
			  Tree1.updateNode(node1[0].getParentNode().getParentNode().getParentNode());
		  }
	  }

	  if(Tree2 == null){
		  $("#getTree").append('<ul id="treeDemo_'+$("#course_id").val()+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
		  Tree2 = loadTree2("treeDemo_"+$("#course_id").val(),null);
	  }

	  moveTreeNode(Tree1, Tree2);
	  
	  var dialog = openDialog("kpointDialog","dialogKpointDiv","选择知识点",600,550,true,"确定",function(){
			Tree3 = $.fn.zTree.getZTreeObj("treeDemo_"+$("#courseId").val());
			var arrNames = new Array();
			var arrIds = new Array();
			if(!isnull(Tree3)){
				//获取到  getTree 所有 知识点层级的节点  把name显示到 selectionKpoints中
				//把ID 存放在  kpointIds中
				var nodes = Tree3.transformToArray(Tree3.getNodes());
				for(var i=0;i<nodes.length;i++){
					if(nodes[i].level==3){//如果是知识点层就保存
						arrNames.push(nodes[i].name);
						arrIds.push(nodes[i].id);
					}
				}
			}
			$("#selectionKpoints").html(arrNames.join(","));
			$("#selectionKpoints").attr("title",arrNames.join(","));
			$("#kpointIds").val(arrIds.join(","));//更新了知识点范围以后 就把分配题的地方还原
			//先还原客户观题部分
			//单选
			$("#radioQuestionCnt").val(0);
			$("#radioQuestionCnt_totalScore").val(0);
			$("#radioQuestionCnt_score").val(0);
			$("#radioQuestionCnt_difficultyAllocation").val("");
			//多选
			$("#multiselectQuestionCnt").val(0);
			$("#multiselectQuestionCnt_totalScore").val(0);
			$("#multiselectQuestionCnt_score").val(0);
			$("#multiselectQuestionCnt_difficultyAllocation").val("");
			//判断
			$("#nonQuestionCnt").val(0);
			$("#nonQuestionCnt_totalScore").val(0);
			$("#nonQuestionCnt_score").val(0);
			$("#nonQuestionCnt_difficultyAllocation").val("");
			
			$(".difficultyAllocation_span").each(function(){
				$(this).text("");
			});
			
			//再还原主观题 被激活的部分
			selSetMethodInit($("#settingMethod").val());
			
			//然后根据已经选择的知识点 从后台查询出对应的题型及数量 
			ajaxRequest(basePath + "/exam/examPaper/getQuestions",{"kpointIds":$("#kpointIds").val()},function(data){
				//造假数据
				$("#radioQuestionCnt_span").text(0);
				$("#multiselectQuestionCnt_span").text(0);
				$("#nonQuestionCnt_span").text(0);
				if(data.success){
					if(data.resultObject != null){
						var results = data.resultObject;
						for(var i=0;i<results.length;i++){//0单选、1多选、2判断、3填空、4简答、5代码、6应用
							//0单选、1多选、2判断、
							if(results[i].questionType == 0){
								$("#radioQuestionCnt_span").text(results[i].cnt);
								$("#radioQuestionCnt").val(0);
							}else if(results[i].questionType == 1){
								$("#multiselectQuestionCnt_span").text(results[i].cnt);
								$("#multiselectQuestionCnt").val(0);
							}else if(results[i].questionType == 2){
								$("#nonQuestionCnt_span").text(results[i].cnt);
								$("#nonQuestionCnt").val(0);
							}else if(results[i].questionType == 3){
								$("#completionQuestionCnt_span").text(results[i].cnt);
								$("#completionQuestionCnt").val(0);
							}else if(results[i].questionType == 4){
								$("#shortAnswerQuestionCnt_span").text(results[i].cnt);
								$("#shortAnswerQuestionCnt").val(0);
							}else if(results[i].questionType == 5){
								$("#codeQuestionCnt_span").text(results[i].cnt);
								$("#codeQuestionCnt").val(0);
							}else if(results[i].questionType == 6){
								$("#practicalQuestionCnt_span").text(results[i].cnt);
								$("#practicalQuestionCnt").val(0);
							}
						}
					}
				}else{
					alert("加载可用题数量失败，请重新加载！");
				}

			});
			
			dialog.dialog( "close" );
			
			//如果已经选择了 就把错误提示隐藏掉
			if($("#kpointIds").val() != null && $("#kpointIds").val() != ''){
				$("#kpointIds").parent().find("label").hide();
			}
			showSelKpointsBtn();
		});
})

var dealDifficulty = function(){
	if(!$(this).parent().parent().find("[name='strategyCnt']").eq("0").valid()){//如果题数不合法 让其合法后再填写
		return;
	}
	distributionForm.resetForm();
	//难度分配下的span重置
	$("#difficultyDialog .proportion").each(function(){$(this).text(0)});
	$("#difficultyDialog .totalQuestion").each(function(){$(this).text(0)});
	$("#difficultyDialog .alreadyAssigned").each(function(){$(this).text(0)});
	//初始化可分配题数  从当前分配的题中找出可分配题  如果为0 那么给出提示
	var strategyTotalScore = Number($(this).parent().parent().find("[name='strategyCnt']").eq(0).val());

	if(isNaN(strategyTotalScore) || strategyTotalScore <= 0 ){
		layer.msg("请分配题数后再进行难度分配！");
		$(this).parent().parent().find("[name='strategyCnt']").eq(0).focus();
		return;
	}
	
	$("#totalQuestionHid").val(strategyTotalScore);

	var questionType = $(this).parent().parent().find("[name='questionType']").eq(0).val();
	var difficultyCnt;
	syncRequest(basePath + "/exam/examPaper/getQuestionsCnt",{"kpointIds":$("#kpointIds").val(),"questionType":questionType},function(data){
		//数据
		if(data.success){
			difficultyCnt = data.resultObject;
		}else{
			alert("加载难度可用题数量失败，请重新加载！");
		}
	})

	$(".totalQuestion").each(function(){
		$(this).text(0);
	});

	//初始化总题数 要从数据库中返回 该类题型 该课程 四个难度分别的数量
	for(var i = 0;difficultyCnt != null && i < difficultyCnt.length;i++){
		if(difficultyCnt[i].difficulty == "A"){//A简单，B一般，C困难、D非常困难
			$(".totalQuestion").eq("3").text(difficultyCnt[i].cnt);
		}else if(difficultyCnt[i].difficulty == "B"){
			$(".totalQuestion").eq("2").text(difficultyCnt[i].cnt);
		}else if(difficultyCnt[i].difficulty == "C"){
			$(".totalQuestion").eq("1").text(difficultyCnt[i].cnt);
		}else if(difficultyCnt[i].difficulty == "D"){
			$(".totalQuestion").eq("0").text(difficultyCnt[i].cnt);
		}
	}
	
	//初始化总题数
	$(".alreadyAssigned").each(function(){
		$(this).text(0);
	});
	
	var arrTemp = $(this).parent().parent().find(".difficultyAllocation").eq("0").val().split(",");
	for(var i = 0;i<arrTemp.length;i++){
		if(i == 0){
			$("#practicalQuestionCntD").val(arrTemp[i]);
			$("#practicalQuestionCntD").parent().parent().find(".alreadyAssigned").eq("0").text(arrTemp[i]);
		}else if(i == 1){
			$("#practicalQuestionCntC").val(arrTemp[i]);
			$("#practicalQuestionCntC").parent().parent().find(".alreadyAssigned").eq("0").text(arrTemp[i]);
		}else if(i == 2){
			$("#practicalQuestionCntB").val(arrTemp[i]);
			$("#practicalQuestionCntB").parent().parent().find(".alreadyAssigned").eq("0").text(arrTemp[i]);
		}else if(i == 3){
			$("#practicalQuestionCntA").val(arrTemp[i]);
			$("#practicalQuestionCntA").parent().parent().find(".alreadyAssigned").eq("0").text(arrTemp[i]);
		}
	}

	$("#practicalQuestionCntD").trigger("change");
	$("#practicalQuestionCntC").trigger("change");
	$("#practicalQuestionCntB").trigger("change");
	$("#practicalQuestionCntA").trigger("change");
	
	var difficultyAllocation = $(this).parent().parent().find(".difficultyAllocation_span").eq(0);
	var difficultyAllocationHid = $(this).parent().parent().find(".difficultyAllocation").eq(0);
	$("#totalQuestionHid").val(strategyTotalScore);
	var dialog = openDialog("difficultyDialog","dialogDifficultyDiv","难度分配",800,350,true,"确定",function(){
//		$("#distribution-form").valid()
		if ($("#distribution-form").valid()){//如果验证通过 在那一行添加提示 并且赋值给 那一行的 difficultyAllocation 字段
			var practicalQuestionCntD = $("#practicalQuestionCntD").val() == ""?0:Number($("#practicalQuestionCntD").val());
			var practicalQuestionCntC = $("#practicalQuestionCntC").val() == ""?0:Number($("#practicalQuestionCntC").val());
			var practicalQuestionCntB = $("#practicalQuestionCntB").val() == ""?0:Number($("#practicalQuestionCntB").val());
			var practicalQuestionCntA = $("#practicalQuestionCntA").val() == ""?0:Number($("#practicalQuestionCntA").val());
			if(isNaN(practicalQuestionCntD)){
				practicalQuestionCntD = 0;
			}
			
			if(isNaN(practicalQuestionCntC)){
				practicalQuestionCntC = 0;
			}
			
			if(isNaN(practicalQuestionCntB)){
				practicalQuestionCntB = 0;
			}
			
			if(isNaN(practicalQuestionCntA)){
				practicalQuestionCntA = 0;
			}
			
			var str = "非常困难：" +practicalQuestionCntD+"题   困难："+practicalQuestionCntC+"题  一般："+practicalQuestionCntB+"题  简单："+practicalQuestionCntA+"题";
			difficultyAllocation.text(str);//保存难度的顺序是 D C B A
			difficultyAllocationHid.val(practicalQuestionCntD+","+practicalQuestionCntC+","+practicalQuestionCntB+","+practicalQuestionCntA);
			difficultyAllocationHid.valid();//主动验证一次
			
			dialog.dialog("close");
		}
	});
}

$(".distribution").on("click",dealDifficulty);

//难度分触发方法
$(".practicalQuestionCnt").on("keyup change",function(event){
	var alreadyAssigned = Number(this.value);
	var totalQuestion = Number($("#totalQuestionHid").val());
	if(isNaN(alreadyAssigned)){
		alreadyAssigned = 0;
	}
	//把当前方框的值 存入已分配
	$(this).parent().parent().find(".alreadyAssigned").eq("0").text(alreadyAssigned);
	var proportion = alreadyAssigned/totalQuestion;
	//设置已分配所占百分比
	$(this).parent().parent().find(".proportion").eq("0").text(toDecimal2(proportion * 100));
})

//保留两位小数  
//功能：将浮点数四舍五入，取小数点后2位 
function toDecimal(x) { 
  var f = parseFloat(x); 
  if (isNaN(f)) { 
    return; 
  } 
  f = Math.round(x*100)/100; 
  return f; 
} 

//制保留2位小数，如：2，会在2后面补上00.即2.00 
function toDecimal2(x) { 
  var f = parseFloat(x); 
  if (isNaN(f)) { 
    return false; 
  } 
  var f = Math.round(x*100)/100; 
  var s = f.toString(); 
  var rs = s.indexOf('.'); 
  if (rs < 0) { 
    rs = s.length; 
    s += '.'; 
  } 
  while (s.length <= rs + 2) { 
    s += '0'; 
  } 
  return s; 
} 

function loadTreeAll(objId,url,data){
		ajaxRequest(url,data,function(zNodes){
			Tree1 = loadTree(objId,zNodes);//初始化选择知识点
			var arrKpoint = $("#kpointIds").val().split(",");
			
			for(var i=0;i<arrKpoint.length;i++){
				var oNode = Tree1.getNodeByParam("id", arrKpoint[i], null);
				if(oNode!= null && !oNode.isParent){
//					Tree1.setChkDisabled(oNode, false);
					if(oNode.chkDisabled){//如果该节点已经被选  那么就清空在可选知识点范围
						 $("#kpointIds").val("");
						 $("#selectionKpoints").text("");
					}

					Tree1.checkNode(oNode, true, true);
					zTreeOnCheck(null,objId,oNode);

				}
			}

			//遍历所有的0、1、2 级节点 如果不可以用 全部禁用
			var nodesAll = Tree1.transformToArray(Tree1.getNodes());

			for(var i=0;i<nodesAll.length;i++){
				var tempNode = nodesAll[i];
				if(tempNode.level == 2){
					//查询出该父节点下是否有 不为禁用的节点 如果有 就可选 否则就不可选
					var tempNodes = Tree1.getNodesByParam("chkDisabled",false,tempNode);
					if(tempNodes.length == 0){
						Tree1.setChkDisabled(nodesAll[i],true);
					}
				}
			}
			
			nodesAll = Tree1.transformToArray(Tree1.getNodes());
			for(var i=0;i<nodesAll.length;i++){
				var tempNode = nodesAll[i];
				if(tempNode.level == 1){
					//查询出该父节点下是否有 不为禁用的节点 如果有 就可选 否则就不可选
					var tempNodes = Tree1.getNodesByParam("chkDisabled",false,tempNode);
					if(tempNodes.length == 0){
						Tree1.setChkDisabled(nodesAll[i],true);
					}
				}
			}
			
			nodesAll = Tree1.transformToArray(Tree1.getNodes());
			for(var i=0;i<nodesAll.length;i++){
				var tempNode = nodesAll[i];
				if(tempNode.level == 0){
					//查询出该父节点下是否有 不为禁用的节点 如果有 就可选 否则就不可选
					var tempNodes = Tree1.getNodesByParam("chkDisabled",false,tempNode);
					if(tempNodes.length == 0){
						Tree1.setChkDisabled(nodesAll[i],true);
					}
				}
			}

			//然后根据已经选择的知识点 从后台查询出对应的题型及数量  
			ajaxRequest(basePath + "/exam/examPaper/getQuestions",{"kpointIds":$("#kpointIds").val()},function(data){
				//造假数据
				$("#radioQuestionCnt_span").text(0);
				$("#multiselectQuestionCnt_span").text(0);
				$("#nonQuestionCnt_span").text(0);
				if(data.success){
					if(data.resultObject != null){
						var results = data.resultObject;
						for(var i=0;i<results.length;i++){
							//0单选、1多选、2判断、
							if(results[i].questionType == 0){
								$("#radioQuestionCnt_span").text(results[i].cnt);
								$("#radioQuestionCnt").val(0);
							}else if(results[i].questionType == 1){
								$("#multiselectQuestionCnt_span").text(results[i].cnt);
								$("#multiselectQuestionCnt").val(0);
							}else if(results[i].questionType == 2){
								$("#nonQuestionCnt_span").text(results[i].cnt);
								$("#nonQuestionCnt").val(0);
							}else if(results[i].questionType == 3){
								$("#completionQuestionCnt_span").text(results[i].cnt);
								$("#completionQuestionCnt").val(0);
							}else if(results[i].questionType == 4){
								$("#shortAnswerQuestionCnt_span").text(results[i].cnt);
								$("#shortAnswerQuestionCnt").val(0);
							}else if(results[i].questionType == 5){
								$("#codeQuestionCnt_span").text(results[i].cnt);
								$("#codeQuestionCnt").val(0);
							}else if(results[i].questionType == 6){
								$("#practicalQuestionCnt_span").text(results[i].cnt);
								$("#practicalQuestionCnt").val(0);
							}
						}
					}
				}else{
					alert("加载可用题数量失败，请重新加载！");
				}
			});
			showSelKpointsBtn();
		});
	}

	function loadTree(objId,zNodes){
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
					onCheck: zTreeOnCheck
				}
			};
		return $.fn.zTree.init($("#"+objId), setting, zNodes);
	}

	function loadTree2(objId,zNodes){
		var setting = {
				view: {
					selectedMulti: false,
					expandSpeed: "normal"
				},
				check: {
					enable: false,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onCheck: zTreeOnCheck
				}
		};
		return $.fn.zTree.init($("#"+objId), setting, zNodes);
	}
	
	/**
	 * 捕获左右两边树的check事件
	 */
	function zTreeOnCheck(event, treeId, treeNode) {
		var objId = "treeDemo_"+$("#courseId").val();
		var treeUrl = $("#getTree").html();
		isUpdateClickForCourseSystem = true;
	    if(treeNode.checked){//选中
	    	if(treeUrl.indexOf(objId)==-1){
	    		$("#getTree").append('<ul id="'+objId+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
	    		Tree2 = loadTree2(objId,null);
	    	}else{
	    		Tree2 = $.fn.zTree.getZTreeObj(objId);
	    	}
	    	if(treeNode.isParent){
	    		mask();
	    		setTimeout(function(){
	    			moveTreeNode(Tree1, Tree2);
	        		unmask();
	    		},50);
	    	}else if(treeNode.type=="4"){
	    		moveTreeNode(Tree1, Tree2);
	    	}
	    }else{//取消选中
    		var pNode = Tree1.getNodeByParam("id", treeNode.pId, null);//获取当前节点的父节点
        	if(!isnull(pNode)){
        		if(!pNode.checked){//判断父节点是否取消
        			var selects = Tree1.getCheckedNodes(true);
        		    if(selects.length==0){
        		    	deleteParentNode(Tree1,pNode);//删除父节点
        		    }else{
        		    	if(pNode.type=="3"){
        		    		var parentNode = Tree1.getNodeByParam("id", pNode.pId, null);//获取父节点的父节点
        		    		if(!isnull(parentNode) && !parentNode.checked){//父父节点取消
        		    			deleteTreeNode(Tree2,parentNode);//删除父父节点
        		    		}
        		    	}
        		    	deleteTreeNode(Tree2,pNode);//删除当前节点的父节点
        		    }
            	}
        	}
	    	deleteTreeNode(Tree2,treeNode);//删除当前节点
	    }
	}

	function showSelKpointsBtn(){
		if($("#selectionKpoints").text() == "" || $("#selectionKpoints").text() == null){
			$("#selKpoint1").show();
			$("#selKpoint").parent().hide();
		}else{
			$("#selKpoint1").hide();
			$("#selKpoint").parent().show();
		}
		$("#selectionKpoints").show();
	}
	//把这个处理主观联动的方法 新建成对象  方便在切换默认设置和基本设置的时候 再次绑定
	var dealSubjectiveQuestion = function(){
				if(isNaN($(this).val())){
					$(this).val(0);
				}
				var totalQuestion = Number($(this).parent().parent().find("[type='text']").eq(0).val());
				var totalScore = Number($(this).parent().parent().find("[type='text']").eq(1).val());
				//此处添加判断  激活的主观题设置 是默认设置 还是细分设置
//				var tempId = $(".active").eq("1").attr("id");
				var tempId = $("#settingMethod").val()==1?"defultSetting":"detailSetting";
				
				if(tempId == "defultSetting"){//如果是默认设置
					//本级联动
					if(totalQuestion !=0 && totalScore != 0){
						$(this).parent().parent().find("[type='text']").eq(2).val(totalScore/totalQuestion);			
					}else if(totalQuestion==0 || totalScore==0){
						$(this).parent().parent().find("[type='text']").eq(2).val(0);
					}

					//负数时的处理
					if(totalQuestion < 0){
						$(this).parent().parent().find("[type='text']").eq(0).val(0);
						$(this).parent().parent().find("[type='text']").eq(2).val(0);
					}
					if(totalScore < 0){
						$(this).parent().parent().find("[type='text']").eq(1).val(0);
						$(this).parent().parent().find("[type='text']").eq(2).val(0);
					}
				}else if(tempId == "detailSetting" && totalQuestion >= 0 ){
					
					//这里要动态生成试题
					//先把现在有的试题数量取出来和现在的分配题的值比对
					var detailCnt = $(this).parent().parent().find("select").length;
					var result = totalQuestion - detailCnt;
					
					var selectStrD = " <option value=\"D\">非常困难(共0题)</option> ";
					var selectStrC = " <option value=\"C\">困难(共0题)</option> ";
					var selectStrB = " <option value=\"B\">一般(共0题)</option> ";
					var selectStrA = " <option value=\"A\">简单(共0题)</option> ";
					
					//如果大于   追加
					if(result > 0 ){
						var detailQuestionDiv = $(this).parent().parent().find(".detailQuestion").eq(0);
//						if(detailCnt > 0){//如果现在已经有了分配的题 那么从现在有的下拉框中查找一个下拉框的参数 存放到变量中 在下面的添加中拼接
//							//把查询出来的每个难度的题目的数量保存到隐藏域中 用来验证
//							var difficultyCnts = $(this).parent().parent().find("[name='difficultyCnts']").eq(0).val().split(",");
//			
//							for(var i = 0;i<difficultyCnts.length;i++){
//								if(i == 3){//A简单，B一般，C困难、D非常困难
//									selectStrA.replace("(共0题)","(共"+difficultyCnts[i].cnt+"题)");
//								}else if(i == 2){
//									selectStrB.replace("(共0题)","(共"+difficultyCnts[i].cnt+"题)");
//								}else if(i == 1){
//									selectStrC.replace("(共0题)","(共"+difficultyCnts[i].cnt+"题)");
//								}else if(i == 0){
//									selectStrD.replace("(共0题)","(共"+difficultyCnts[i].cnt+"题)");
//								}
//							}
//			
//						}else{//如果目前还没有分配题 那么需要从后台获取

							var questionType = $(this).parent().parent().find("[name='questionType']").eq(0).val();
							var difficultyCnt = [];
			
							syncRequest(basePath + "/exam/examPaper/getQuestionsCnt",{"kpointIds":$("#kpointIds").val(),"questionType":questionType},function(data){
								//数据
								if(data.success){
									difficultyCnt = data.resultObject;
								}else{
									alert("加载难度可用题数量失败，请重新加载！");
								}
							})

							var difficultyCnts = $(this).parent().parent().find("[name='difficultyCnts']").eq(0).val().split(",");
			
							for(var i = 0;i<difficultyCnt.length;i++){
								if(difficultyCnt[i].difficulty == "A"){//A简单，B一般，C困难、D非常困难
									selectStrA = selectStrA.replace("(共0题)","(共"+difficultyCnt[i].cnt+"题)");
									difficultyCnts[3] = difficultyCnt[i].cnt;
								}else if(difficultyCnt[i].difficulty == "B"){
									selectStrB = selectStrB.replace("(共0题)","(共"+difficultyCnt[i].cnt+"题)");
									difficultyCnts[2] = difficultyCnt[i].cnt;
								}else if(difficultyCnt[i].difficulty == "C"){
									selectStrC = selectStrC.replace("(共0题)","(共"+difficultyCnt[i].cnt+"题)");
									difficultyCnts[1] = difficultyCnt[i].cnt;
								}else if(difficultyCnt[i].difficulty == "D"){
									selectStrD = selectStrD.replace("(共0题)","(共"+difficultyCnt[i].cnt+"题)");
									difficultyCnts[0] = difficultyCnt[i].cnt;
								}
							}
			
							$(this).parent().find("[name='difficultyCnts']").eq(0).val(difficultyCnts.join(","));//把新查询出来的值设置的隐藏域中
//						}
			
						var htmlStr = "";
						for(var i = detailCnt + 1;i<=(result + detailCnt);i++){//循环添加
							questionNum ++;//进入以后先把计数器+1
							if(i == 1){//第一行不需要TOP10属性
								htmlStr +=  " <div class=\"form-group\" > " +
											" <span  class=\"col-sm-2\" style=\"padding-top: 7px;width:70px\">第"+i+"题</span> " +
											" <div class=\"col-sm-2\" style=\"padding: 0px;\"><input type=\"text\" name=\""+$(this).attr("id")+"_question_"+questionNum+"\" value=\"0\" maxlength=\"4\" class=\"col-sm-12 {digits:true,range:[1,500],totalScoreEqualStrategyTotalScore:true} "+$(this).attr("id")+"_question\"></div>" +
											" <span  class=\"col-sm-1\" style=\"padding-top: 7px\">分</span> " +
											" <div class=\"col-sm-3\"><select class=\"col-sm-12 {required:true,checkQuestionCnt:true} "+$(this).attr("id")+"_difficulty\" name=\""+$(this).attr("id")+"_difficulty_"+questionNum+"\"> " +
											"	<option value=\"\">请选择困难度</option> " +
												selectStrD +
												selectStrC +
												selectStrB +
												selectStrA +
											" </select> </div>" +
											" </div>";
							}else{
								htmlStr +=  " <div class=\"form-group\" style=\"margin-top: 10px;\"> " +
											" <span  class=\"col-sm-2\" style=\"padding-top: 7px;width:70px\">第"+i+"题</span> " +
											" <div class=\"col-sm-2\" style=\"padding: 0px;\"><input type=\"text\" name=\""+$(this).attr("id")+"_question_"+questionNum+"\" value=\"0\" maxlength=\"4\" class=\"col-sm-12 {digits:true,range:[1,500],totalScoreEqualStrategyTotalScore:true} " + $(this).attr("id") + "_question\"></div>" +
											" <span  class=\"col-sm-1\" style=\"padding-top: 7px\">分</span> " +
											" <div class=\"col-sm-3\"><select  class=\"col-sm-12 {required:true,checkQuestionCnt:true} "+$(this).attr("id")+"_difficulty\" name=\""+$(this).attr("id")+"_difficulty_"+questionNum+"\"> " +
											"	<option value=\"\">请选择困难度</option> " +
												selectStrD +
												selectStrC +
												selectStrB +
												selectStrA +
											" </select> </div>" +
											" </div> ";
							}
						}

						//在当前题型设置中插入DIV
						detailQuestionDiv.append(htmlStr);
					}else if(result == 0 ){//如果等于  不变
						
					}else if(result < 0 ){//如果小于  去掉多余的部分
						result = result * -1;//先把差值边为正数 然后循环剔除
						var tempLenght = $(this).parent().parent().find("select").length;
						for(var i = 1; i <= result ; i++){
							$(this).parent().parent().find("select").eq((tempLenght - i)).parent().parent().remove();//移除掉  直到相等
						}
					}
				}
				changeInfo(this);
				$("[name='strategyCnt']").each(function(){
					$(this).valid()
				});
				$("#unDistributionScore_hid").valid();
	} 

	//主观题的触发事件
	$("#completionQuestionCnt,#completionQuestionCnt_totalScore,#shortAnswerQuestionCnt,#shortAnswerQuestionCnt_totalScore,#codeQuestionCnt,#codeQuestionCnt_totalScore," +
			  "#practicalQuestionCnt,#practicalQuestionCnt_totalScore").on("keyup change",dealSubjectiveQuestion);//keyup 把这个事件先去掉吧 影响编辑的时候体验 如果仅仅想从10 变成 15 那么要先删除0 这时候 会把之前已经编辑好的题删除到只剩下 1道 然后再追加14道题


	//选择主观题默认设置方式
	function selSetMethod(menthodType){
		
		$("#settingMethod").val(menthodType);
		if(menthodType == 2){
//			//先把默认div中的内容做个快照
//			tempDefultSetting = $("#defultSetting").html();

			//然后取出所有的值赋给你值细分设置
			var completionQuestionCnt_span = $("#completionQuestionCnt_span").text();//填空题可用题数
			var shortAnswerQuestionCnt_span = $("#shortAnswerQuestionCnt_span").text();//简单题可用题数
			var codeQuestionCnt_span = $("#codeQuestionCnt_span").text();//代码题可用题数
			var practicalQuestionCnt_span = $("#practicalQuestionCnt_span").text();//实操题可用题数
			
			var completionQuestionCnt = $("#completionQuestionCnt").val();//填空题共分配题数
			var shortAnswerQuestionCnt = $("#shortAnswerQuestionCnt").val();//填空题共分配题数
			var codeQuestionCnt = $("#codeQuestionCnt").val();//填空题共分配题数
			var practicalQuestionCnt = $("#practicalQuestionCnt").val();//填空题共分配题数
			
			var completionQuestionCnt_totalScore = $("#completionQuestionCnt_totalScore").val();//填空题共分配分数
			var shortAnswerQuestionCnt_totalScore = $("#shortAnswerQuestionCnt_totalScore").val();//填空题共分配分数
			var codeQuestionCnt_totalScore = $("#codeQuestionCnt_totalScore").val();//填空题共分配分数
			var practicalQuestionCnt_totalScore = $("#practicalQuestionCnt_totalScore").val();//填空题共分配分数
			
			//再把tempDetailSetting  缓存内容赋值给 detailSetting DIV
			$("#detailSetting").html(tempDetailSetting);
			//最后把本div下的内容移除
			$("#defultSetting").empty();
			//把取出的值赋给激活的DIV
			$("#completionQuestionCnt_span").text(completionQuestionCnt_span);
			$("#shortAnswerQuestionCnt_span").text(shortAnswerQuestionCnt_span);
			$("#codeQuestionCnt_span").text(codeQuestionCnt_span);
			$("#practicalQuestionCnt_span").text(practicalQuestionCnt_span);
			
			$("#completionQuestionCnt").val(completionQuestionCnt);
			$("#shortAnswerQuestionCnt").val(shortAnswerQuestionCnt);
			$("#codeQuestionCnt").val(codeQuestionCnt);
			$("#practicalQuestionCnt").val(practicalQuestionCnt);
			
			$("#completionQuestionCnt_totalScore").val(completionQuestionCnt_totalScore);
			$("#shortAnswerQuestionCnt_totalScore").val(shortAnswerQuestionCnt_totalScore);
			$("#codeQuestionCnt_totalScore").val(codeQuestionCnt_totalScore);
			$("#practicalQuestionCnt_totalScore").val(practicalQuestionCnt_totalScore);
			//根据设置好的值动态生成有多少题
			$(".detailQuestion").each(function(){
				var strategyCnt = $(this).parent().find("[name='strategyCnt']").eq(0);//需要生成的题的数量
				var htmlStr = "";
				
				var questionType = $(this).parent().find("[name='questionType']").eq(0).val();
				var difficultyCnt;

				syncRequest(basePath + "/exam/examPaper/getQuestionsCnt",{"kpointIds":$("#kpointIds").val(),"questionType":questionType},function(data){
					//数据
					if(data.success){
						difficultyCnt = data.resultObject;
					}else{
						alert("加载难度可用题数量失败，请重新加载！");
					}
				})

				var selectStrD = " <option value=\"D\">非常困难(共0题)</option> ";
				var selectStrC = " <option value=\"C\">困难(共0题)</option> ";
				var selectStrB = " <option value=\"B\">一般(共0题)</option> ";
				var selectStrA = " <option value=\"A\">简单(共0题)</option> ";

				//把查询出来的每个难度的题目的数量保存到隐藏域中 用来验证
				var difficultyCnts = $(this).parent().find("[name='difficultyCnts']").eq(0).val().split(",");

				for(var i = 0;i<difficultyCnt.length;i++){
					if(difficultyCnt[i].difficulty == "A"){//A简单，B一般，C困难、D非常困难
						selectStrA = selectStrA.replace("(共0题)","(共"+difficultyCnt[i].cnt+"题)");
						difficultyCnts[3] = difficultyCnt[i].cnt;
					}else if(difficultyCnt[i].difficulty == "B"){
						selectStrB = selectStrB.replace("(共0题)","(共"+difficultyCnt[i].cnt+"题)");
						difficultyCnts[2] = difficultyCnt[i].cnt;
					}else if(difficultyCnt[i].difficulty == "C"){
						selectStrC = selectStrC.replace("(共0题)","(共"+difficultyCnt[i].cnt+"题)");
						difficultyCnts[1] = difficultyCnt[i].cnt;
					}else if(difficultyCnt[i].difficulty == "D"){
						selectStrD = selectStrD.replace("(共0题)","(共"+difficultyCnt[i].cnt+"题)");
						difficultyCnts[0] = difficultyCnt[i].cnt;
					}
				}

				$(this).parent().find("[name='difficultyCnts']").eq(0).val(difficultyCnts.join(","));//把新查询出来的值设置的隐藏域中
				for(var i=1;i<=strategyCnt.val();i++){//这里的下拉框不能加title 属性 不然的话验证提示语就是title中的内容  不知道为什么 可能是验证框架的BUG
					questionNum ++;//进入以后先把计数器+1
					if(i == 1){//第一行不需要TOP10属性
						htmlStr +=  " <div class=\"form-group\" > " +
									" <span  class=\"col-sm-2\" style=\"padding-top: 7px;width:70px\">第"+i+"题</span> " +
									" <div class=\"col-sm-2\" style=\"padding: 0px;\"><input type=\"text\" name=\""+strategyCnt.attr("id")+"_question_"+questionNum+"\" id=\""+strategyCnt.attr("id")+"_question_"+questionNum+"\" value=\"0\" maxlength=\"4\" class=\"col-sm-12 {digits:true,range:[1,500],totalScoreEqualStrategyTotalScore:true} "+strategyCnt.attr("id")+"_question\"></div>" +
									" <span  class=\"col-sm-1\" style=\"padding-top: 7px\">分</span> " +
									" <div class=\"col-sm-3\"><select  class=\"col-sm-12 {required:true,checkQuestionCnt:true} "+strategyCnt.attr("id")+"_difficulty\" name=\""+strategyCnt.attr("id")+"_difficulty_"+questionNum+"\" id=\""+strategyCnt.attr("id")+"_difficulty_"+questionNum+"\"> " +
									"	<option value=\"\">请选择困难度</option> " +
										selectStrD +
										selectStrC +
										selectStrB +
										selectStrA +
									" </select> </div>" +
									" </div>";
					}else{
						htmlStr +=  " <div class=\"form-group\" style=\"margin-top: 10px;\"> " +
									" <span  class=\"col-sm-2\" style=\"padding-top: 7px;width:70px\">第"+i+"题</span> " +
									" <div class=\"col-sm-2\" style=\"padding: 0px;\"><input type=\"text\" name=\""+strategyCnt.attr("id")+"_question_"+questionNum+"\" id=\""+strategyCnt.attr("id")+"_question_"+questionNum+"\" value=\"0\" maxlength=\"4\" class=\"col-sm-12 {digits:true,range:[1,500],totalScoreEqualStrategyTotalScore:true} "+strategyCnt.attr("id")+"_question\"></div>" +
									" <span  class=\"col-sm-1\" style=\"padding-top: 7px\">分</span> " +
									" <div class=\"col-sm-3\"><select  class=\"col-sm-12 {required:true,checkQuestionCnt:true} "+strategyCnt.attr("id")+"_difficulty\" name=\""+strategyCnt.attr("id")+"_difficulty_"+questionNum+"\" id=\""+strategyCnt.attr("id")+"_difficulty_"+questionNum+"\"> " +
									"	<option value=\"\">请选择困难度</option> " +
										selectStrD +
										selectStrC +
										selectStrB +
										selectStrA +
									" </select> </div>" +
									" </div>";
					}
				}

				$(this).html(htmlStr);
				//再次绑定主观题的触发事件
				$("#completionQuestionCnt,#completionQuestionCnt_totalScore,#shortAnswerQuestionCnt,#shortAnswerQuestionCnt_totalScore,#codeQuestionCnt,#codeQuestionCnt_totalScore," +
				  "#practicalQuestionCnt,#practicalQuestionCnt_totalScore").on("change",dealSubjectiveQuestion);//keyup 把这个事件先去掉吧 影响编辑的时候体验 如果仅仅想从10 变成 15 那么要先删除0 这时候 会把之前已经编辑好的题删除到只剩下 1道 然后再追加14道题
			})
		}else if(menthodType == 1){
			//然后取出所有的值赋给你值细分设置
			var completionQuestionCnt_span = $("#completionQuestionCnt_span").text();//填空题可用题数
			var shortAnswerQuestionCnt_span = $("#shortAnswerQuestionCnt_span").text();//简单题可用题数
			var codeQuestionCnt_span = $("#codeQuestionCnt_span").text();//代码题可用题数
			var practicalQuestionCnt_span = $("#practicalQuestionCnt_span").text();//实操题可用题数
			
			var completionQuestionCnt = $("#completionQuestionCnt").val();//填空题共分配题数
			var shortAnswerQuestionCnt = $("#shortAnswerQuestionCnt").val();//填空题共分配题数
			var codeQuestionCnt = $("#codeQuestionCnt").val();//填空题共分配题数
			var practicalQuestionCnt = $("#practicalQuestionCnt").val();//填空题共分配题数
			
			var completionQuestionCnt_totalScore = $("#completionQuestionCnt_totalScore").val();//填空题共分配分数
			var shortAnswerQuestionCnt_totalScore = $("#shortAnswerQuestionCnt_totalScore").val();//填空题共分配分数
			var codeQuestionCnt_totalScore = $("#codeQuestionCnt_totalScore").val();//填空题共分配分数
			var practicalQuestionCnt_totalScore = $("#practicalQuestionCnt_totalScore").val();//填空题共分配分数
			
			//再把tempDefultSetting  缓存内容赋值给 defultSetting DIV
			$("#defultSetting").html(tempDefultSetting);//首先重置这个tab页
			//最后把细分设置div下的内容移除
			$("#detailSetting").empty();
			
			//把取出的值赋给激活的DIV
			$("#completionQuestionCnt_span").text(completionQuestionCnt_span);
			$("#shortAnswerQuestionCnt_span").text(shortAnswerQuestionCnt_span);
			$("#codeQuestionCnt_span").text(codeQuestionCnt_span);
			$("#practicalQuestionCnt_span").text(practicalQuestionCnt_span);
			
			$("#completionQuestionCnt").val(completionQuestionCnt);
			$("#shortAnswerQuestionCnt").val(shortAnswerQuestionCnt);
			$("#codeQuestionCnt").val(codeQuestionCnt);
			$("#practicalQuestionCnt").val(practicalQuestionCnt);
			
			$("#completionQuestionCnt_totalScore").val(completionQuestionCnt_totalScore);
			$("#shortAnswerQuestionCnt_totalScore").val(shortAnswerQuestionCnt_totalScore);
			$("#codeQuestionCnt_totalScore").val(codeQuestionCnt_totalScore);
			$("#practicalQuestionCnt_totalScore").val(practicalQuestionCnt_totalScore);
			
			//再把这几个控件触发一次值改变事件 用于计算每个题的平均得分
			$("#completionQuestionCnt_span").trigger("change");
			$("#shortAnswerQuestionCnt_span").trigger("change");
			$("#codeQuestionCnt_span").trigger("change");
			$("#practicalQuestionCnt_span").trigger("change");

			$("#completionQuestionCnt,#completionQuestionCnt_totalScore,#shortAnswerQuestionCnt,#shortAnswerQuestionCnt_totalScore,#codeQuestionCnt,#codeQuestionCnt_totalScore," +
			  "#practicalQuestionCnt,#practicalQuestionCnt_totalScore").on("keyup change",dealSubjectiveQuestion);
			$(".distribution").on("click",dealDifficulty);
			//值改变的时候 把同行的难度分配清空 要求其重新分配
			$("input[name='strategyCnt']").on("change",function(){
				$(this).parent().parent().find(".difficultyAllocation").eq("0").val("0,0,0,0");
				$(this).parent().parent().find(".difficultyAllocation_span").eq("0").text("");
			});
		}

	}
	//选择主观题默认设置方式
	function selSetMethodInit(menthodType){
		if(menthodType == 2){
			//再把tempDetailSetting  缓存内容赋值给 detailSetting DIV
			$("#detailSetting").html(tempDetailSetting);
			//最后把本div下的内容移除
			$("#defultSetting").empty();
			//再次绑定主观题的触发事件
			$("#completionQuestionCnt,#completionQuestionCnt_totalScore,#shortAnswerQuestionCnt,#shortAnswerQuestionCnt_totalScore,#codeQuestionCnt,#codeQuestionCnt_totalScore," +
			"#practicalQuestionCnt,#practicalQuestionCnt_totalScore").on("change",dealSubjectiveQuestion);//keyup 把这个事件先去掉吧 影响编辑的时候体验 如果仅仅想从10 变成 15 那么要先删除0 这时候 会把之前已经编辑好的题删除到只剩下 1道 然后再追加14道题
		}else if(menthodType == 1){
			
			//再把tempDefultSetting  缓存内容赋值给 defultSetting DIV
			$("#defultSetting").html(tempDefultSetting);//首先重置这个tab页
			//最后把细分设置div下的内容移除
			$("#detailSetting").empty();
			
			$("#completionQuestionCnt,#completionQuestionCnt_totalScore,#shortAnswerQuestionCnt,#shortAnswerQuestionCnt_totalScore,#codeQuestionCnt,#codeQuestionCnt_totalScore," +
			"#practicalQuestionCnt,#practicalQuestionCnt_totalScore").on("keyup change",dealSubjectiveQuestion);
			$(".distribution").on("click",dealDifficulty);
			//值改变的时候 把同行的难度分配清空 要求其重新分配
			$("input[name='strategyCnt']").on("change",function(){
				$(this).parent().parent().find(".difficultyAllocation").eq("0").val("0,0,0,0");
				$(this).parent().parent().find(".difficultyAllocation_span").eq("0").text("");
			});
		}
	}

	$("#cancelbt").on('click',function(){
		$("#totalScoreSpan").text("0");
		$("#distributionScore").text("0");
		$("#unDistributionScore").text("0");
	});