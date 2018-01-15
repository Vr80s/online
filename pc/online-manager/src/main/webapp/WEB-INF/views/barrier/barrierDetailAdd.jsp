<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<style>
.text-overflow {
	display: block; /*内联对象需加 */
	width: 31em; /* 何问起 hovertree.com */
	word-break: keep-all; /* 不换行 */
	white-space: nowrap; /* 不换行 */
	overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */
	text-overflow: ellipsis;
	/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
}
</style>
<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header row">
		<div class="col-xs-8" style="padding-left: 0px;padding-right: 0px;">
		  当前位置：学习闯关管理 <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
		    <span> 关卡管理 </span>
		    <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> ${param.courseName} </span>
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> 关卡列表 </span>
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> 设置闯关试卷 </span>
		</div>
		<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
			<button class="btn btn-sm btn-success" id="returnButton">
				返回上一页
			</button>
		</div>
</div>

<form id="addBarrierDetail-form" class="form-horizontal"  method="post" action="">
<input type="hidden" name="courseId" id="courseId" value="${param.courseId}">
<input type="hidden" name="courseName" id="courseName" value="${param.courseName}">
<input type="hidden" name="kpointId" id="kpointId" value="${param.kpointId}">
<div style="width:75%">
		 <fieldset>
	       <legend style="border-bottom: 0px solid blue;width: 170px;">关卡基本信息设置</legend>
	      <div class="form-group">
	         <label class="col-sm-2 control-label" for="name"><font color="red">*</font>关卡名称:</label>
	         <div class="col-sm-4">
	            <input class="form-control {required:true,maxlength:60}" name="name" id="name" type="text" placeholder="请输入60字符以内关卡名称"/>
	         </div>
	         <div class="col-sm-2" style="text-align: left;padding-top: 7px;color: gray;">
	            <span id="wordCnt">0</span>/60
	         </div>
	      </div>
	      <div class="form-group"  style="margin-top: 10px;">
	         <label class="col-sm-2 control-label" for="parentId"><font color="red">*</font>选择上一阶段关卡:</label>
	         <div class="col-sm-4">
	            <select id="parentId" name="parentId" class="form-control">
	                <option value="">无</option>
	             </select>
	         </div>
	      </div>
	      <div class="form-group"  style="margin-top: 10px;">
	         <label class="col-sm-2 control-label" for="limitTime"><font color="red">*</font>时长:</label>
	         <div class="col-sm-3">
	            	<input class="form-control {required:true,range:[1,500],digits:true}" name="limitTime" id="limitTime" type="text"  placeholder="请输入时长"/>
	         </div>
	         <div class="col-sm-3" style="padding-top: 7px;">
	            	min
	         </div>
	      </div>
	      <div class="form-group"  style="margin-top: 10px;">
	         <label class="col-sm-2 control-label" for="totalScore"><font color="red">*</font>总分数:</label>
	         <div class="col-sm-3">
	            <input class="form-control {required:true,range:[1,500],digits:true}" maxlength="3" name="totalScore" id="totalScore" type="text" placeholder="请输入分数"/>
	         </div>
	         <div class="col-sm-3" style="padding-top: 7px;">
	          	 分
	         </div>
	      </div>
	      <div class="form-group"  style="margin-top: 10px;">
	         <label class="col-sm-2 control-label" for="passScorePercent"><font color="red">*</font>设置通关标准:</label>
	         <div class="col-sm-3">
	            <input class="form-control {required:true,range:[1,500],digits:true,lessThanTotal:true}" name="passScorePercent" id="passScorePercent" type="text" placeholder="请输入分数"/>
	         </div>
	         <div class="col-sm-3" style="padding-top: 7px;">
	            	分
	         </div>
	      </div>
	      <div class="form-group"  style="margin-top: 10px;">
	         <label class="col-xs-2 control-label" for="kpointIds"><font color="red">*</font>知识点范围:</label>
	         <div class="col-xs-3" style="padding-top: 7px;">
	         	<span id="selectionKpoints" style="display:none;" class="text-overflow" title="${param.kpointName}">${param.kpointName}</span>
	            <input class="form-control {required:true}" name="kpointIds" id="kpointIds" type="hidden" value="${param.kpointId}"/>
	            <button id="selKpoint1" type="button">请选择知识点范围</button>
	         </div>
	         <div class="col-xs-3 {required:true}" style="padding-top: 4px;">
	            <button id="selKpoint"  style="color: blue;border: 0;background-color: transparent;" type="button">更改</button>
	         </div>
	      </div>
	   </fieldset>
	   <br/>
	   <fieldset>
	       <legend style="border-bottom: 0px solid blue;width: 170px;">关卡试题信息配置</legend>
	       <div class="form-group">
	          <label for="disabledSelect"  class="col-sm-2 control-label" style="font-weight: bold;font-size: 18px">题目信息配置:</label>
	          <div class="col-sm-10" style="padding-top: 7px;font-size: 18px">
					共<span style="color: blue;font-size: 18px" id="totalScoreSpan">0</span>分，
					已分配<span style="color: blue;font-size: 18px" id="distributionScore">0</span>分，
					还有<span style="color: blue;font-size: 18px"  id="unDistributionScore">0</span>分没有分配
					<input class="form-control  {equal0:true,equalTotalScore:true}" type="text" style="display:none" name="unDistributionScore_hid" id="unDistributionScore_hid" value="0">
	          </div>
	       </div>
	       <div class="form-group" style="margin-top: 10px;">
	          <label  class="col-sm-2 control-label">可用<span id="radioQuestionCnt_span">0</span>道题</label>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	单选题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control  {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="radioQuestionCnt" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
	          		题&nbsp;共
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control  {digits:true}" name="strategyTotalScore" id="radioQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	分&nbsp;每题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control  {digits:true}" name="strategyScore" id="radioQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;">
	             	分
	          </div>
	       </div>
	       <div class="form-group" style="margin-top: 10px;">
	          <label class="col-sm-2 control-label">可用<span id="multiselectQuestionCnt_span">0</span>道题</label>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	多选题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="multiselectQuestionCnt" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
	          		题&nbsp;共
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control  {digits:true}" name="strategyTotalScore" id="multiselectQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	分&nbsp;每题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control {digits:true}" name="strategyScore" id="multiselectQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;">
	             	分
	          </div>
	       </div>
	       <div class="form-group" style="margin-top: 10px;">
	          <label class="col-sm-2 control-label">可用<span id="nonQuestionCnt_span" >0</span>道题</label>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	判断题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control  {digits:true,lessThanTotalQuestion:true,syncRequired:true}" name="strategyCnt" id="nonQuestionCnt" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 40px;">
	          		题&nbsp;共
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control  {digits:true}" name="strategyTotalScore" id="nonQuestionCnt_totalScore" type="text" value="0" placeholder="0"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;padding-right: 0px;width: 50px;">
	             	分&nbsp;每题
	          </div>
	          <div class="col-sm-1" style="padding-left: 0px;">
	             	<input class="form-control  {digits:true}" name="strategyScore" id="nonQuestionCnt_score" type="text" value="0" placeholder="0" readonly="readonly"/>
	          </div>
	          <div class="col-sm-1" style="padding-top: 7px;padding-left: 0px;">
	             	分
	          </div>
	       </div>
	       <div class="form-group"  style="margin-top: 30px;text-align: center;width: 60%;">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-sm btn-success save_bx" type="button">
					<i class="glyphicon glyphicon-ok"></i> 保存
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-sm btn-success" id="cancelbt" type="reset">
					<i class="glyphicon glyphicon-remove"></i> 重置
				</button>
		   </div>
	   </fieldset>
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

<script type="text/javascript" src="${base}/js/barrier/barrierDetailAdd.js?v=ipandatcm_1.3"></script>
