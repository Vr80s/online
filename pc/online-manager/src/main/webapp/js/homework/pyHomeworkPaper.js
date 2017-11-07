var edit;
var commentKindeditor;
$(function () {
	$(document).bind("click",function(e){
		var target = $(e.target);
		if(target.closest("img").length == 0){
			$("body",document).find(".imgpreshow").remove();
		}
	});
	setNextOneButtonHide();
    tabChange($(".ultabs"));
	npyaccordionChange($(".ulaccrodion"));
	$(".ulaccrodion li:eq(0)").find(".divaccordion").css("display","block");
	$(".ulaccrodion li:eq(0) .sptitle i").removeClass('fa-chevron-down').addClass('fa-chevron-up')

	commentKindeditor = createKindEdit('textarea[class="kindeditor"]',null,function(){
		if ("pre" == $("#py_type").val()) {//查看答卷
			commentKindeditor.readonly(true);
		}
	});
	/*$(".divtk .pque img,.divtk .ptrueans img,.divchose img").on("click",function () {
		$(".imgpreshow").remove();
		var cimg = $("<img class='imgpreshow' src='"+$(this).attr("src")+"' />")
			.on('click', function () {
				$(this).fadeOut();
			});
		$("body").append(cimg);
		cimg.fadeIn();
	});*/
	//图片滚动鼠标滑轮放大缩小
	$(".divtk .pque img,.divtk .ptrueans img,.divchose img").initImageViewer();
	$(".divaccordion").on("click", function () {
		$(".divchosescore").remove();
	});
	
	/*初始点击显示大图【学员答案】*/
	//picturePreview('.sac-link',"npapy");
	//previewHide('#mbi-outer');
	/*试卷评价下拉,同一页面可多处使用 start*/
	$('.evaluate-link').mouseover(function(){
		var _thisover=$(this);
		_thisover.find('.evaluate-list').show();
		_thisover.find('.evaluate-line').addClass('opcity-0').removeClass('opcity-1');
	});
	$('.evaluate-link').mouseout(function(){
		var _thisout=$(this);
		_thisout.find('.evaluate-list').hide();
		_thisout.find('.evaluate-line').addClass('opcity-1').removeClass('opcity-0');
	});
  $('.js-evaluate-li').click(function(){
	  var _this=$(this),
		  backnode=_this.parent().parent();
	  //backnode.parent().next('.js-py-textarea').val(_this.text());
	  commentKindeditor.html(_this.text());
	  backnode.find('.evaluate-list').hide();
	  backnode.find('.evaluate-line').addClass('opcity-1').removeClass('opcity-0');
  });
	/*试卷评价下拉,同一页面可多处使用 end*/
	/*初始化星星评分 start*/
	//填空题
  	var tk_size = $("#tk_questionCount").text();
	for(var i=1;i<=tk_size;i++){
		var star="tk_star"+ i,
		    result="tk_targetInput"+i,
			errorstr="tk_targeterror"+ i,
		    tk_eachscore="tk_spscore"+i;
		var initStar;
		var targetV=$("#" + result).val();
		if(targetV!=''&&targetV!=null){
			initStar= targetV.indexOf('.')==-1?parseInt(targetV):parseFloat(targetV).toFixed(1);
		}else{
			initStar = '';
		}
		setinitstar(star,result,tk_eachscore,errorstr,initStar);
	}
	//简答题
	var jd_size = $("#jd_questionCount").text();
	for(var i=1;i<=jd_size;i++){
		var star="jd_star"+ i,
			result="jd_targetInput"+i,
			errorstr="jd_targeterror"+ i,
			tk_eachscore="jd_spscore"+i;
		var initStar;
		var targetV=$("#" + result).val();
		if(targetV!=''&&targetV!=null){
			initStar= targetV.indexOf('.')==-1?parseInt(targetV):parseFloat(targetV).toFixed(1);
		}else{
			initStar = '';
		}
		setinitstar(star,result,tk_eachscore,errorstr,initStar);
	}
	//代码题
	var dm_size = $("#dm_questionCount").text();
	for(var i=1;i<=dm_size;i++){
		var star="dm_star"+ i,
			result="dm_targetInput"+i,
			errorstr="dm_targeterror"+ i,
			tk_eachscore="dm_spscore"+i;
		var initStar;
		var targetV=$("#" + result).val();
		if(targetV!=''&&targetV!=null){
			initStar= targetV.indexOf('.')==-1?parseInt(targetV):parseFloat(targetV).toFixed(1);
		}else{
			initStar = '';
		}
		setinitstar(star,result,tk_eachscore,errorstr,initStar);
	}
	//实操题
	var sc_size = $("#sc_questionCount").text();
	for(var i=1;i<=sc_size;i++){
		var star="sc_star"+ i,
			result="sc_targetInput"+i,
			errorstr="sc_targeterror"+ i,
			tk_eachscore="sc_spscore"+i;
		var initStar;
		var targetV=$("#" + result).val();
		if(targetV!=''&&targetV!=null){
			initStar= targetV.indexOf('.')==-1?parseInt(targetV):parseFloat(targetV).toFixed(1);
		}else{
			initStar = '';
		}
		setinitstar(star,result,tk_eachscore,errorstr,initStar);
	}
	/*初始化星星评分 end*/
	
	setPyType();
});
//手风琴效果
function npyaccordionChange(obj){
	$(obj).on("click","li .ptitle", function () {
          var objthis=$(this),
			  liobj=objthis.closest("li"),
			  faobj=objthis.find('.fa');
		if(liobj.find(".divaccordion").css("display")=="none"){
			$(obj).find("li .divaccordion").slideUp();
			liobj.find(".divaccordion").slideDown();
			faobj.addClass('fa-chevron-up').removeClass('fa-chevron-down');
		}else{
			liobj.find(".divaccordion").slideUp();
			faobj.removeClass('fa-chevron-up').addClass('fa-chevron-down');
		}
	});
}
//组合星星
function setinitstar(star,result,tk_eachscore,errorstr,initStar){
	star= '#' + star;
	result= '#' + result;
	tk_eachscore= '#' + tk_eachscore;
	errorstr = '#' + errorstr;
	/*每颗星的值*/
	var eachstar=parseDivisionError(parseFloat($(tk_eachscore).text()),5);
	//初始化构成星星
	createStar(star,tk_eachscore,eachstar,initStar,result,errorstr);
	$(result).keyup(function(){
		var _this=$(this);
		createStar(star,tk_eachscore,eachstar,_this.val(),result,errorstr);
		var objthisva=parseSubtraError(_this.val(),_this.next('.fs_hiddenInput').val());
		var fidobj=_this.parent().parent().parent().parent().parent().find(".priceNum");//题型总分对象
		fidobj.text(parseAddError(fidobj.text(),objthisva));
		_this.next('.fs_hiddenInput').val(_this.val());
	});
}
/*构成星星评分*/
function createStar(star,tk_eachscore,eachstar,inputscore,result,errorstr){
	if(parseFloat(inputscore)<0)return;
	var str='',strsrc='',starcout=5;
	if(parseFloat(inputscore)==0){
		for(var e=1;e<=starcout;e++){
			str+='<img title="'+parseMultiplError(e,eachstar)+'" src="'+basePath+'/images/star-off-big.png" alt="'+e+'">';
		}
		strsrc='cancel-on-big.png';
	}else{
		var flsc=parseFloat(inputscore),
			eachStV=parseDivisionError(flsc,eachstar);
		if(flsc<=parseFloat($(tk_eachscore).text())){
			$(errorstr).html('&nbsp;');
			if((eachStV+'').indexOf('.')==-1){//整数
				for(var j=1;j<=eachStV;j++){
					str+='<img title="'+parseMultiplError(j,eachstar)+'" src="'+basePath+'/images/star-on-big.png" alt="'+j+'">';
				}
				for(var a=eachStV+1;a<=starcout;a++){
					str+='<img title="'+parseMultiplError(a,eachstar)+'" src="'+basePath+'/images/star-off-big.png" alt="'+a+'">';
				}
			}else{
				var yushust=parseInt(((eachStV)+'').split('.')[0]);
				if(yushust==0){//获得的值为0.1到0.9
					str+='<img title="'+eachstar+'" src="'+basePath+'/images/star-half-big.png" alt="'+1+'">';
					for(var k=2;k<=starcout;k++){
						str+='<img title="'+parseMultiplError(k,eachstar)+'" src="'+basePath+'/images/star-off-big.png" alt="'+k+'">';
					}
				}else{
					for(var c=1;c<=yushust;c++){
						str+='<img title="'+parseMultiplError(c,eachstar)+'" src="'+basePath+'/images/star-on-big.png" alt="'+c+'">';
					}
					var numhalf=yushust+1;
					str+='<img title="'+parseMultiplError(numhalf,eachstar)+'" src="'+basePath+'/images/star-half-big.png" alt="'+numhalf+'">';
					for(var d=numhalf+1;d<=starcout;d++){
						str+='<img title="'+parseMultiplError(d,eachstar)+'" src="'+basePath+'/images/star-off-big.png" alt="'+d+'">';
					}
				}
			}
		}else{
			if(inputscore!=''){
				$(errorstr).html('&nbsp;输入的分数不能大于题目的总分或只能输入数字');
			}
			for(var g=1;g<=starcout;g++){
				str+='<img title="'+parseMultiplError(g,eachstar)+'" src="'+basePath+'/images/star-off-big.png" alt="'+g+'">';
			}
		}
		if($(star).hasClass("notAnswer")){
			strsrc='cancel-on-big.png';
			$(star).parent().find(".getscore").val(0);
		}else{
			strsrc='cancel-off-big.png';
		}
	}
	$(star).html(str+'<span class="or-style">or</span><img alt="x" title="0" src="'+basePath+'/images/'+strsrc+'" class="raty-cancel">');
	$(star+" img").click(function(){
		var imgThis=$(this);
		var pyType = $("#py_type").val();
		if(pyType=="pre" || imgThis.parent().hasClass("notAnswer")){//是查看或者是未答，则不允许设置评分
			return;
		}
		$(result).val(imgThis.attr('title'));
		$(result).trigger('keyup');//触发得分输入框的keyup事件的执行
		$(result).closest(".spt-line").find(".getscore").focus();
	});
}

/**
 * 返回学员列表
 */
function retStu(){
    var comment = commentKindeditor.html();
    if(isnull(comment)){
        alertInfo("试卷评语不能为空！");
        return;
    }
	//判断当前试卷是不是所有习题都已批阅  未批阅完弹出提示
	var userPaperId = $("#input_userPaperId").val();
	var hasReadOverAllQuestion = true;
	syncRequest(basePath+"/homework/judgeReadOverAllQuestions",
			{"userPaperId":userPaperId}, function(res){
		if(res.success && res.resultObject==false){
			alertInfo("还有试题未批阅");
			hasReadOverAllQuestion = false;
		}else if(!res.success){
			alertInfo(res.errorMessage);
			hasReadOverAllQuestion = false;
		}
	});
	if(!hasReadOverAllQuestion)return; 
	
	ajaxRequestPyHomework();
	var classPaperId = $("#input_classPaperId").val();
	var className = $("#input_className").val();
	var classId = $("#input_classId").val();
	var paperName = $("#input_paperName").val();
	var courseId = $("#input_courseId").val();
	window.location.href=basePath+'/home#homework/studentHomework?classPaperId='+classPaperId+'&className='+className+'&classId='+classId+'&paperName='+paperName+'&courseId='+courseId;
	window.location.reload();//返回列表加载不出来，暂时解决办法
}

/**
 * 批阅下一学员
 */
function nextStu(){
	//判断当前试卷是不是所有习题都已批阅   未批阅完弹出提示
	var userPaperId = $("#input_userPaperId").val();
	if(isnull(userPaperId)){
		alertInfo("试卷Id不能为空");
		return;
	}
    var comment = commentKindeditor.html();
    if(isnull(comment)){
        alertInfo("试卷评语不能为空！");
        return;
    }
	var hasReadOverAllQuestion = true;
	syncRequest(basePath+"/homework/judgeReadOverAllQuestions",
			{"userPaperId":userPaperId}, function(res){
		if(res.success && res.resultObject==false){
			alertInfo("还有试题未批阅");
			hasReadOverAllQuestion = false;
		}else if(!res.success){
			alertInfo(res.errorMessage);
			hasReadOverAllQuestion = false;
		}
	});
	if(!hasReadOverAllQuestion)return; 
	
	ajaxRequestPyHomework();
	doPyNextStudentPaper();
}

/**
 * 异步调用接口去更新试题的批阅信息
 * @author yuanziyang
 * @date 2016年6月3日 下午3:42:03
 * @param question
 */
function ajaxRequestPy(inputThis,questionId,totleScore){
	var pyType = $("#py_type").val();
	if("pre"==pyType)
		return;
	var notAnswerSpan=$(inputThis).closest(".spt-line").find(".notAnswer");
	if(notAnswerSpan!=null && notAnswerSpan.length>0)
		return;
	if(inputThis.value==null || inputThis.value=='')
		inputThis.value = 0;
	var score = parseFloat(inputThis.value);
	if(score >= 0){
	}else{
		alertInfo("请输入正确的分数");
		return;
	}
	totleScore = parseFloat(totleScore);
	if($(inputThis).prev().hasClass("dmOrScNotAnswer")){//判断是否是代码或实操的未答题，如果是弹出提示，确定后提交到后端
		confirmInfo('此题学员未上传答案，确定要给分吗？', function(){//点击确定按钮
			notAnswerSpan=$(inputThis).closest(".spt-line").find(".dmOrScNotAnswer");
			var type = notAnswerSpan.data('question_type');
			var userPaperId = $("#input_userPaperId").val();
			ajaxRequest(basePath+"/homework/readOverQuestion",
					{"userPaperId":userPaperId, "questionId":questionId,
				"type":type, "totleScore":totleScore, "score":score}, function(res){
					if(!res.success){
						alertInfo(res.errorMessage);
					}
			});
		}, function(){//点击取消或关闭按钮
			inputThis.value = '';
			$(inputThis).trigger('keyup');
		});
		return;
	}
	
	ajaxRequest(basePath+"/homework/readOverQuestion",
			{"questionId":questionId, "totleScore":totleScore,"score":score}, function(res){
			if(!res.success){
				alertInfo(res.errorMessage);
			}
	});
}

/**
 * 同步调用接口去更新试卷的批阅信息
 * @author yuanziyang
 * @date 2016年6月3日 下午3:42:03
 * @param question
 */
function ajaxRequestPyHomework(){
	var classPaperId = $("#input_classPaperId").val();
	var userPaperId = $("#input_userPaperId").val();
	var comment = commentKindeditor.html();//$("#textarea_comment").html();
	syncRequest(basePath+"/homework/readOverStudentPaper",
			{"userPaperId":userPaperId, "comment":comment,"classPaperId":classPaperId}, function(res){
			if(!res.success){
				alertInfo(res.errorMessage);
			}
	});
}

/**
 * 执行批阅下一个学生答卷
 * 
 * @author yuanziyang
 * @date 2016年6月1日 下午8:35:58
 * @param obj
 */
function doPyNextStudentPaper(){
	var classPaperId = $("#input_classPaperId").val();
	var className = $("#input_className").val();
	var classId = $("#input_classId").val();
	var paperName = $("#input_paperName").val();
	var courseId = $("#input_courseId").val();
	var userPaperId = $("#input_userPaperId").val();
	window.location.href=basePath+'/home#homework/toPyNextStudentPage?classPaperId='+classPaperId+'&className='+className+'&classId='+classId
		+'&paperName='+paperName+'&courseId='+courseId+'&userPaperId='+userPaperId;
}


/**
 * 设置下一位学员按钮是否影藏
 * @author yuanziyang
 * @date 2016年6月7日 下午4:04:44
 */
function setNextOneButtonHide(){
	var pyType = $("#py_type").val();
	if("pre"==pyType)
		return;
	var classPaperId = $("#input_classPaperId").val();
	var userPaperId = $("#input_userPaperId").val();
	ajaxRequest(basePath+"/homework/judgeHasNextUnReadOverPaper",
			{"classPaperId":classPaperId,"userPaperId":userPaperId}, function(res){
			if(!res.success || res.resultObject==false){
				$(".nextone").addClass("hide");
			}else{
				$(".nextone").removeClass("hide");
			}
	});
}



function setPyType(){
	$(".notAnswer").closest(".spt-line").find(".getscore").each(function(){
		$(this).attr("readonly",true);
	});
	var pyType = $("#py_type").val();
	$(".textarea-outer").addClass('m_t13');
	if("pre"==pyType){//查看答卷
		$(".getscore").each(function(){
			$(this).attr("readonly",true);
		});
		$(".js-py-textarea").each(function(){
			$(this).attr("readonly",true);
		});
		$(".pybtn_hide").addClass("hide");
		$(".evaluate-link,.js-py-textarea").addClass("cursorDefault");
		$(".evaluate-list,.evaluate-line").addClass("hide");
		$(".textarea-outer").removeClass('m_t13');
		$(".writeCommentBtn").each(function(){
        	$(this).addClass("hidden");
        });
        $(".questionComment").each(function(){
        	if(!isnull($(this).html())){
        		$(this).removeClass("hidden");
        	}
        	 $(this).attr("readonly", true);
        });
	}else{
		$(".question_comment_div").each(function(){
			if(!isnull($(this).find(".valueBack").val())){
				$(this).find(".questionComment").removeClass("hidden");
				$(this).find(".writeCommentBtn").text('取消评语');
			}
		});
	}
}

/**
 * 控制输入文本值，只能输入大于0的数字，不能超过最大值
 * @author yuanziyang
 * @date 2016年6月8日 上午11:04:45
 * @param value
 * @param maxScore
 * @returns
 */
function controllerValue(input,maxValue){
	var score = parseFloat(input.value);
	if(score > maxValue){
		input.value = maxValue;
		return ;
	}
	if(input.value==input.value2)
		return ;
	if(input.value.search(/^\d*(?:\d\.\d{0,1})?$/)==-1)
		input.value = (input.value2)?input.value2:'';
	else input.value2=input.value;
}

function returnStudentPaperList(){
	var classPaperId = $("#input_classPaperId").val();
	var className = $("#input_className").val();
	var classId = $("#input_classId").val();
	var paperName = $("#input_paperName").val();
	var courseId = $("#input_courseId").val();
	window.location.href=basePath+'/home#homework/studentHomework?classPaperId='+classPaperId+'&className='+className+'&classId='+classId+'&paperName='+paperName+'&courseId='+courseId;
	window.location.reload();//返回列表加载不出来，暂时解决办法
}
