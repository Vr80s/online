<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	

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
		  当前位置：组卷管理 <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> ${param.courseName} </span>
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> 编辑试卷 </span>
		</div>
		<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
			<button class="btn btn-sm btn-success" id="returnButton">
				返回上一页
			</button>
		</div>
</div>

<!-- 增加form -->
<form id="updateExamPaper-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
	<input type="hidden" name="courseId" id="courseId" value="${param.courseId}">
	<input type="hidden" name="courseName" id="courseName" value="${param.courseName}">
	<input type="hidden" name="id" id="id" value="${param.id}">
	<input type="hidden" name="difficulty" id="difficulty" value="${param.difficulty}">
	<div style="width:75%" id="mainDiv">
		   <div class="form-group">
	         <label class="col-sm-2 control-label" for="name"><font color="red">*</font>试卷名称:</label>
	         <div class="col-sm-4">
	            <input class="form-control {required:true,maxlength:60}" name="paperName" id="paperName" type="text" placeholder="请输入试卷名称" value="${param.paperName}"/>
	         </div>
	         <div class="col-sm-6" style="text-align: right;">
	            <button class="btn btn-sm btn-success save_bx2" id="cancelbt" type="button">
					保存试卷
				</button>
	         </div>
	      </div>
	      <div class="form-group" id="bulidExam" style="margin-top: 30px;text-align: center;width: 100%;">
				<div class="form-group" style="border-bottom: 1px solid black;display: none;" id="bulidExamTop">
					<!-- 试卷预览部分 -->
					<label class="col-sm-2 control-label" style="font-weight: bold;font-size:20px">试卷预览</label>
					<div class="col-sm-4" style="padding-left: 0px;margin-left: -81px;padding-top: 14px;font-size: 15;">
						（试卷难度为：<span id="difficultyInfo" style="font-size: 15;">无</span>）
					</div>
				</div>
		  </div>
	 </div>
</form>

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
			<ul id="selectTree" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
		</div>
		<div class="col-xs-12" >
			<h3>预览</h3>
		</div>
		<div class="col-xs-12" id="showQuestion" style="margin-left: -190px;">
			<!-- 预览试题部分 -->
		</div>
	</div>
</div>
<script type="text/javascript" src="${base}/js/exam/examPaperUpdate.js?v=1.7"></script>