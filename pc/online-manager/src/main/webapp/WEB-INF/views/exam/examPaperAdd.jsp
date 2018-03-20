<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	

<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header row">
		<div class="col-xs-8" style="padding-left: 0px;padding-right: 0px;">
		  当前位置：组卷管理 <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> ${param.courseName} </span>
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> 新建试卷 </span>
		</div>
		<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
			<button class="btn btn-sm btn-success" id="returnButton">
				返回上一页
			</button>
		</div>
</div>

<!-- 增加form -->
<form id="addexamPaper-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
	<input type="hidden" name="courseId" id="courseId" value="${param.courseId}">
	<input type="hidden" name="courseName" id="courseName" value="${param.courseName}">
	<input type="hidden" name="settingMethod" id="settingMethod" value="1">
	<div style="width:100%" id="mainDiv">
	      <div class="form-group">
	         <label class="col-sm-2 control-label" for="name"><font color="red">*</font>试卷名称:</label>
	         <div class="col-sm-4">
	            <input class="form-control {required:true,maxlength:60}" name="paperName" id="paperName" type="text" placeholder="请输入试卷名称"/>
	         </div>
	         <div class="col-sm-2" style="text-align: left;padding-top: 7px;color: gray;">
	            <span id="wordCnt">0</span>/50
	         </div>
	      </div>
	      <div class="form-group"  style="margin-top: 10px;">
	         <label class="col-sm-2 control-label" for="score"><font color="red">*</font>试卷分数:</label>
	         <div class="col-sm-3">
	            <input class="form-control {required:true,digits:true}" maxlength="4" name="score" id="score" type="text" placeholder="请输入分数"/>
	         </div>
	         <div class="col-sm-3" style="padding-top: 7px;">
	          	 分
	         </div>
	      </div>
	      <div class="form-group"  style="margin-top: 10px;">
	         <label class="col-sm-2 control-label" for="limitTime"><font color="red">*</font>时长:</label>
	         <div class="col-sm-3">
	            	<input class="form-control {required:true,digits:true}"  maxlength="6" name="duration" id="duration" type="text"  placeholder="请输入时长"/>
	         </div>
	         <div class="col-sm-3" style="padding-top: 7px;">
	            	min
	         </div>
	      </div>
	      <div class="form-group"  style="margin-top: 10px;">
	         <label class="col-xs-2 control-label" for="kpointIds"><font color="red">*</font>知识点范围:</label>
	         <div class="col-xs-3" style="padding-top: 7px;">
	         	<div id="selectionKpoints" style="display:none;white-space:nowrap;overflow:hidden;width:23em;text-overflow:ellipsis;" title="${param.kpointName}">${param.kpointName}</div>
	            <button id="selKpoint1" type="button">请选择知识点范围</button>
	            <input class="form-control {required:true}" name="kpointIds" id="kpointIds" type="hidden" value="${param.kpointId}"/>
	         </div>
	         <div class="col-xs-3 {required:true}" style="padding-top: 4px;">
	            <button id="selKpoint"  style="color: blue;border: 0;background-color: transparent;" type="button">更改</button>
	         </div>
	      </div>
	      <div class="form-group">
	          <label for="disabledSelect"  class="col-sm-2 control-label" style="font-weight: bold;">题目信息配置:</label>
	          <div class="col-sm-10">
					应分配分数：<span style="color: blue;font-size: 18px" id="totalScoreSpan">0</span>分
					已分配分数：<span style="color: blue;font-size: 18px" id="distributionScore">0</span>分
					剩余未分配分数：<span style="color: blue;font-size: 18px"  id="unDistributionScore">0</span>分
					<input class="form-control {equal0:true,equalTotalScore:true}" type="text" style="display:none" name="unDistributionScore_hid" id="unDistributionScore_hid" value="0">
	          </div>
	      </div>
	      <div class="form-group">
	          <label class="col-sm-2 control-label" style="font-weight: bold;font-size:18px">客观题</label>
	      </div>
	      <div class="form-group" style="margin-top: 10px;">
	          <label  class="col-sm-2 control-label">可用<span id="radioQuestionCnt_span">0</span>道题</label>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	单选题
	             	<input type="hidden" name="questionType" value="0">
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="radioQuestionCnt" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
	          		题&nbsp;共
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true}" name="strategyTotalScore" id="radioQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	分&nbsp;每题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true}" name="strategyScore" id="radioQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;width: 100px;">
	             	分
	             	<button class="btn btn-xs btn-primary distribution" type="button" style="margin-top: -4px;">
						难度分配
					</button>
	          </div>
	          <div class="col-sm-3" style="padding-top: 7px;padding-left: 0px;">
	          		<input type="hidden" name="radioQuestionCnt_difficultyAllocation" id="radioQuestionCnt_difficultyAllocation" class="form-control {syncRequired2:true} difficultyAllocation">
	             	<span class="difficultyAllocation_span"></span>
	          </div>
	       </div>
	       <div class="form-group" style="margin-top: 10px;">
	          <label class="col-sm-2 control-label">可用<span id="multiselectQuestionCnt_span">0</span>道题</label>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	多选题
	             	<input type="hidden" name="questionType" value="1">
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="multiselectQuestionCnt" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
	          		题&nbsp;共
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true}" name="strategyTotalScore" id="multiselectQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	分&nbsp;每题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true}" name="strategyScore" id="multiselectQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;width: 100px;">
	             	分
	             	<button class="btn btn-xs btn-primary distribution" type="button" style="margin-top: -4px;">
						难度分配
					</button>
	          </div>
	          <div class="col-sm-3" style="padding-top: 7px;padding-left: 0px;">
	          		<input type="hidden" name="multiselectQuestionCnt_difficultyAllocation" id="multiselectQuestionCnt_difficultyAllocation" class="form-control {syncRequired2:true} difficultyAllocation">
	             	<span class="difficultyAllocation_span"></span>
	          </div>
	       </div>
	       <div class="form-group" style="margin-top: 10px;">
	          <label class="col-sm-2 control-label">可用<span id="nonQuestionCnt_span" >0</span>道题</label>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	判断题
	             	<input type="hidden" name="questionType" value="2">
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="nonQuestionCnt" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
	          		题&nbsp;共
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true}" name="strategyTotalScore" id="nonQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	分&nbsp;每题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true}" name="strategyScore" id="nonQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;width: 100px;">
	             	分
	             	<button class="btn btn-xs btn-primary distribution" type="button" style="margin-top: -4px;">
						难度分配
					</button>
	          </div>
	          <div class="col-sm-3" style="padding-top: 7px;padding-left: 0px;">
	          		<input type="hidden" name="nonQuestionCnt_difficultyAllocation" id="nonQuestionCnt_difficultyAllocation" class="form-control {syncRequired2:true} difficultyAllocation">
			        <span class="difficultyAllocation_span"></span>
	          </div>
	       </div>
	       <div class="form-group" style="margin-top: 10px;">
	          <label class="col-sm-2 control-label" style="font-weight: bold;font-size:18px">主观题</label>
	          <div class="col-sm-10" style="padding-left: 0px;">
	             	<ul class="nav nav-tabs" role="tablist">
					  <li role="presentation" class="active"><a href="#defultSetting" role="tab" data-toggle="tab" onclick="selSetMethod(1)">默认设置</a></li>
					  <li role="presentation"><a href="#detailSetting" role="tab" data-toggle="tab" onclick="selSetMethod(2)">细分设置</a></li>
				   </ul>
	          </div>
	       </div>
		   <!-- Tab panes -->
		   <div class="tab-content" style="padding-left: 0;">
			  <div role="tabpanel" class="tab-pane active" id="defultSetting">
			  	   <div class="form-group" style="margin-top: 10px;">
			          <label  class="col-sm-2 control-label">可用<span id="completionQuestionCnt_span">0</span>道题</label>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	填空题
			             	<input type="hidden" name="questionType" value="3">
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="completionQuestionCnt" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
			          		题&nbsp;共
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyTotalScore" id="completionQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	分&nbsp;每题
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyScore" id="completionQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;width: 100px;">
			             	分
			             	<button class="btn btn-xs btn-primary distribution" type="button" style="margin-top: -4px;">
								难度分配
							</button>
			          </div>
			          <div class="col-sm-3" style="padding-top: 7px;padding-left: 0px;">
			             	<input type="hidden" name="completionQuestionCnt_difficultyAllocation" id="completionQuestionCnt_difficultyAllocation" class="form-control {syncRequired2:true} difficultyAllocation">
			             	<span class="difficultyAllocation_span"></span>
			          </div>
			       </div>
			       <div class="form-group" style="margin-top: 10px;">
			          <label class="col-sm-2 control-label">可用<span id="shortAnswerQuestionCnt_span">0</span>道题</label>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	简答题
			             	<input type="hidden" name="questionType" value="4">
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="shortAnswerQuestionCnt" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
			          		题&nbsp;共
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyTotalScore" id="shortAnswerQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	分&nbsp;每题
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyScore" id="shortAnswerQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;width: 100px;">
			             	分
			             	<button class="btn btn-xs btn-primary distribution" type="button" style="margin-top: -4px;">
								难度分配
							</button>
			          </div>
			          <div class="col-sm-3" style="padding-top: 7px;padding-left: 0px;">
			             	<input type="hidden" name="shortAnswerQuestionCnt_difficultyAllocation" id="shortAnswerQuestionCnt_difficultyAllocation" class="form-control {syncRequired2:true} difficultyAllocation">
			             	<span class="difficultyAllocation_span"></span>
			          </div>
			       </div>
			       <div class="form-group" style="margin-top: 10px;">
			          <label class="col-sm-2 control-label">可用<span id="codeQuestionCnt_span" >0</span>道题</label>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	代码题
			             	<input type="hidden" name="questionType" value="5">
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="codeQuestionCnt" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
			          		题&nbsp;共
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyTotalScore" id="codeQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	分&nbsp;每题
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyScore" id="codeQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;width: 100px;">
			             	分
			             	<button class="btn btn-xs btn-primary distribution" type="button" style="margin-top: -4px;">
								难度分配
							</button>
			          </div>
			          <div class="col-sm-3" style="padding-top: 7px;padding-left: 0px;">
			             	<input type="hidden" name="codeQuestionCnt_difficultyAllocation" id="codeQuestionCnt_difficultyAllocation" class="form-control {syncRequired2:true} difficultyAllocation">
			             	<span class="difficultyAllocation_span"></span>
			          </div>
			       </div>
			       <div class="form-group" style="margin-top: 10px;">
			          <label class="col-sm-2 control-label">可用<span id="practicalQuestionCnt_span" >0</span>道题</label>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	实操题
			             	<input type="hidden" name="questionType" value="6">
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="practicalQuestionCnt" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
			          		题&nbsp;共
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyTotalScore" id="practicalQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	分&nbsp;每题
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyScore" id="practicalQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;width: 100px;">
			             	分
			             	<button class="btn btn-xs btn-primary distribution" type="button" style="margin-top: -4px;">
								难度分配
							</button>
			          </div>
			          <div class="col-sm-3" style="padding-top: 7px;padding-left: 0px;">
			             	<input type="hidden" name="practicalQuestionCnt_difficultyAllocation" id="practicalQuestionCnt_difficultyAllocation" class="form-control {syncRequired2:true} difficultyAllocation">
			             	<span class="difficultyAllocation_span"></span>
			          </div>
			       </div>
	       	  </div>
			  <div role="tabpanel" class="tab-pane" id="detailSetting">
			  		<div class="form-group" style="margin-top: 10px;">
			          <label class="col-sm-2 control-label">可用<span id="completionQuestionCnt_span">0</span>道题</label>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	填空题
			             	<input type="hidden" name="questionType" value="3">
			             	<input type="hidden" name="difficultyCnts" value="0,0,0,0"><!-- D、C、B、A -->
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="completionQuestionCnt" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
			          		题&nbsp;共
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyTotalScore" id="completionQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	分
			          </div>
			          <div class="col-sm-5 detailQuestion" style="padding-left: 0px;" >
							
			          </div>
			       </div>
			       <div class="form-group" style="margin-top: 10px;">
			          <label class="col-sm-2 control-label">可用<span id="shortAnswerQuestionCnt_span">0</span>道题</label>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	简答题
			             	<input type="hidden" name="questionType" value="4">
			             	<input type="hidden" name="difficultyCnts" value="0,0,0,0"><!-- D、C、B、A -->
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="shortAnswerQuestionCnt" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
			          		题&nbsp;共
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyTotalScore" id="shortAnswerQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	分
			          </div>
			          <div class="col-sm-5 detailQuestion" style="padding-left: 0px;" >
			          </div>
			       </div>
			       <div class="form-group" style="margin-top: 10px;">
			          <label class="col-sm-2 control-label">可用<span id="codeQuestionCnt_span" >0</span>道题</label>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	代码题
			             	<input type="hidden" name="questionType" value="5">
			             	<input type="hidden" name="difficultyCnts" value="0,0,0,0"><!-- D、C、B、A -->
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="codeQuestionCnt" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
			          		题&nbsp;共
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyTotalScore" id="codeQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	分
			          </div>
			          <div class="col-sm-5 detailQuestion" style="padding-left: 0px;" >
			          </div>
			       </div>
			       <div class="form-group" style="margin-top: 10px;">
			          <label class="col-sm-2 control-label">可用<span id="practicalQuestionCnt_span" >0</span>道题</label>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	实操题
			             	<input type="hidden" name="questionType" value="6">
			             	<input type="hidden" name="difficultyCnts" value="0,0,0,0"><!-- D、C、B、A -->
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="practicalQuestionCnt" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
			          		题&nbsp;共
			          </div>
			          <div class="col-sm-1" style="padding-left: 0px;">
			             	<input class="form-control {digits:true}" name="strategyTotalScore" id="practicalQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
			          </div>
			          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
			             	分
			          </div>
			          <div class="col-sm-5 detailQuestion" style="padding-left: 0px;" >
					  </div>
			       </div>
	       	  </div>
	       </div>
	       <div class="form-group"  style="margin-top: 30px;text-align: center;width: 60%;">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-sm btn-success save_bx" type="button">
					<i class="glyphicon glyphicon-ok"></i> 生成试卷
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-sm btn-success" id="cancelbt" type="reset">
					<i class="glyphicon glyphicon-remove"></i> 重置
				</button>
		   </div>
	       <div class="form-group" id="bulidExam" style="margin-top: 30px;text-align: center;width: 60%;display:none">
				<div class="form-group" style="border-bottom: 1px solid black" id="bulidExamTop">
					<!-- 试卷预览部分 -->
					<label class="col-sm-2 control-label" style="font-weight: bold;font-size:20px">试卷预览</label>
					<div class="col-sm-4" style="padding-left: 0px;padding-top: 14px;font-size: 15;text-align: left">
						（试卷难度为：<span id="difficultyInfo" style="font-size: 15;">无</span>）
						<input type="hidden" name="difficulty" id="difficulty">
					</div>
				</div>
		   </div>
		   <div class="form-group" id="bulidButton" style="margin-top: 30px;text-align: center;width: 60%;display:none">
		   		&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-sm btn-success save_bx2" id="cancelbt" type="button">
					 <i class="glyphicon glyphicon-ok"></i> 保存试卷
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-sm btn-success save_bx" type="button">
					<i class="glyphicon glyphicon-remove"></i>重新生成
				</button>
		   </div>
	 </div>
</form>
<div id="dialogKpointDiv"></div>
<div id="kpointDialog" class="hide">
	<div class="row">
		<div class="col-xs-12">
			<input type="hidden" id="level" value="">
			<input type="hidden" id="treeId" value="">
			<table style="width:100%">
				<tr>
					<td style="vertical-align: top;width:50%">
						<div style="text-align: center">
							<label for="form-field-select-2">选择资源</label>
						</div>
						<div class="contrightbox" style="overflow-y: auto; overflow-x: auto; height: 420px; min-width: 240px;">
							<div class="zTreeDemoBackground" style="min-height: 400px;">
								<ul id="knowledge" class="ztree" style="font-size: 13px; font-weight: bold;"></ul>
							</div>
						</div>
					</td>
					<td style="vertical-align: top;width:50%">
						<div>
							<div style="text-align: center">
								<label for="form-field-select-2">已选择资源</label>
							</div>
							<div>
								<div class="contrightbox" style="overflow-y: auto; overflow-x: auto; height: 420px; min-width: 240px;">
									<div class="zTreeDemoBackground" id="getTree" style="min-height: 400px;"></div>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div id="dialogDifficultyDiv"></div>
<div id="difficultyDialog" class="hide">
	<form id="distribution-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="totalQuestionHid" id="totalQuestionHid">
		<div class="form-group" style="margin-top: 10px;">
	          <div class="col-xs-2" style="padding-top: 7px;text-align: right;">
	             	非常困难：
	          </div>
	          <div class="col-xs-3" style="padding-left: 0px;">
	             	<input class="form-control practicalQuestionCnt {digits:true,range:[0,1000],lessThanOwn:true,lessThanTotalQuestion2:true}" maxlength="5" name="practicalQuestionCntD" id="practicalQuestionCntD" type="number" value="0" placeholder="0"/>
	          </div>
	          <div class="col-xs-6" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;">
					比例： <span class="proportion">0</span>% 题库共 <span class="totalQuestion">0</span>题 已分配：<span class="alreadyAssigned">0</span>道题
	          </div>
	    </div>
		<div class="form-group" style="margin-top: 20px;">
	          <div class="col-xs-2" style="padding-top: 7px;text-align: right;">
	             	困难：
	          </div>
	          <div class="col-xs-3" style="padding-left: 0px;">
	             	<input class="form-control practicalQuestionCnt {digits:true,range:[0,1000],lessThanOwn:true,lessThanTotalQuestion2:true}" maxlength="5" name="practicalQuestionCntC" id="practicalQuestionCntC" type="number" value="0" placeholder="0"/>
	          </div>
	          <div class="col-xs-6" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;">
					比例： <span class="proportion">0</span>% 题库共 <span class="totalQuestion">0</span>题 已分配：<span class="alreadyAssigned">0</span>道题
	          </div>
	    </div>
		<div class="form-group" style="margin-top: 20px;">
	          <div class="col-xs-2" style="padding-top: 7px;text-align: right;">
	             	一般：
	          </div>
	          <div class="col-xs-3" style="padding-left: 0px;">
	             	<input class="form-control practicalQuestionCnt {digits:true,range:[0,1000],lessThanOwn:true,lessThanTotalQuestion2:true}" maxlength="5" name="practicalQuestionCntB" id="practicalQuestionCntB" type="number" value="0" placeholder="0"/>
	          </div>
	          <div class="col-xs-6" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;">
					比例： <span class="proportion">0</span>% 题库共 <span class="totalQuestion">0</span>题 已分配：<span class="alreadyAssigned">0</span>道题
	          </div>
	    </div>
		<div class="form-group" style="margin-top: 20px;">
	          <div class="col-xs-2" style="padding-top: 7px;text-align: right;">
	             	简单：
	          </div>
	          <div class="col-xs-3" style="padding-left: 0px;">
	             	<input class="form-control practicalQuestionCnt {digits:true,range:[0,1000],lessThanOwn:true,lessThanTotalQuestion2:true}" maxlength="5" name="practicalQuestionCntA" id="practicalQuestionCntA" type="number" value="0" placeholder="0"/>
	          </div>
	          <div class="col-xs-6" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;">
					比例： <span class="proportion">0</span>% 题库共 <span class="totalQuestion">0</span>题 已分配：<span class="alreadyAssigned">0</span>道题
	          </div>
	    </div>
	</form>
</div>
<div id="dialogChangeQuestionDiv"></div>
<div id="changeQuestionDialog" class="hide">
	<div class="col-xs-11" style="margin-left: 38px;width: 900px;">
		<div class="searchDivClass" id="searchDiv">
	        <div class="profile-info-row" >
	            <table frame=void >
	                <tr>
	                	<td>
	                        <div class="profile-info-value searchTr">
	                            <input type="text"   id="search_kpointNames" onclick="showMenu();" placeholder = "请选择课程体系" readonly="readonly"/>
	                            <input type="hidden"   id="search_kpointIds" class="propertyValue1"/>
	                            <input type="hidden" value="search_kpointIds" class="propertyName"/>
	                        </div>
	                        <input type="hidden" id="search_kNum"/>
	                    </td>
	                    <td>
	                        <div class="profile-info-value searchTr">
	                            <select id="search_difficulty" class="propertyValue1">
	                                <option value="" >请选择难易度</option>
	                                <option value="A" >简单</option>
	                                <option value="B" >一般</option>
	                                <option value="C" >难</option>
	                                <option value="D" >困难</option>
	                            </select>
	                        	<input type="hidden" value="search_difficulty" class="propertyName" />
	                        </div>
	                    </td>
	                    <td>
	                        <div class="profile-info-value searchTr">
	                            <input type="text" id="search_questionHead" class="propertyValue1"  placeholder = "请输入题干" maxlength="30"/>
	                            <input type="hidden" value="search_questionHead" class="propertyName"/>
	                        </div>
	                    </td>
	                    <td>
	                        <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
	                                onclick="search();">
	                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
	                        </button>
	                    </td>
	                </tr>
	            </table>
	        </div>
	    </div>
	
		<div class="row">
			<div class="col-xs-12">
				<table id="questionTable"
					class="table table-striped table-bordered table-hover">
				</table>
			</div>
		</div>
		<div id="menuContent" class="menuContent"  style="display:none; position: absolute;border: 1px solid #99bbe7;background-color: white;">
			<ul id="selectTree" class="ztree" style="margin-top:0; min-width:180px; min-height: 300px;"></ul>
		</div>
		<div class="col-xs-12" >
			<h3>预览</h3>
		</div>
		<div class="col-xs-12" id="showQuestion" style="margin-left: -190px;">
			<!-- 预览试题部分 -->
		</div>
	</div>
</div>
<script type="text/javascript" src="/js/exam/examPaperAdd.js?v=ipandatcm_1.3"></script>