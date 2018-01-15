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
			当前位置：云课堂管理 
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> 笔记管理 </span> 
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small> 
			<span> ${param.courseName} </span>
		</div>
		<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
			<button class="btn btn-sm btn-success" id="returnbutton">
				<i class="glyphicon glyphicon-arrow-left"></i>
				返回上级
			</button>
		</div>
</div>

<div class="row" style="margin-left: 0;margin-right: 0;">
	<div class="col-md-3" style="padding-left: 0px;">
		<div style='font-size: 20px;margin-bottom: 10px;'>当前课程:${param.courseName}</div>
		<div class="contrightbox" 
			style="overflow-y: hidden; overflow-x: hidden;height: 800px;">
			<div class="zTreeDemoBackground"
				style="overflow-y: auto; overflow-x: auto;height: 800px;">
				<ul id="ztree" class="ztree"
					style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
			</div>
		</div>
	</div>
	<div class="col-md-9" style="padding: 0;">
		<div class="mainrighttab tabresourse bordernone" style="float: right;">
			<p class="col-xs-3" style="padding: 0;">
				<button class="btn btn-sm btn-success" title="点赞数从高到低排列" onclick="search(1)">
					点赞数↓
				</button>
			</p>
			<div class="searchDivClass" id="searchDiv">
		        <div class="profile-info-row" >
		            <table frame=void >
		                <tr>
		                	<td style="display: none">
		                        <div class="profile-info-value searchTr">
		                            <input type="hidden" placeholder = "ID" class="propertyValue1" id="search_courseId" value="${param.courseId}">
		                            <input type="hidden" value="search_courseId" class="propertyName"/>
		                        </div>
		                        <input type="hidden" name="courseId" id="courseId" value="${param.courseId}">
								<input type="hidden" name="courseName" id="courseName" value="${param.courseName}">
		                    </td>
		                	<td style="display: none">
		                        <div class="profile-info-value searchTr">
		                            <input type="hidden" placeholder = "ID" class="propertyValue1" id="search_chapterId">
		                            <input type="hidden" value="search_chapterId" class="propertyName"/>
		                        </div>
		                    </td>
		                	<td style="display: none">
		                        <div class="profile-info-value searchTr">
		                            <input type="hidden" placeholder = "ID" class="propertyValue1" id="search_chapterLevel">
		                            <input type="hidden" value="search_chapterLevel" class="propertyName"/>
		                        </div>
		                    </td>
		                	<td>
		                        <div class="profile-info-value searchTr">
		                            <input type="text" placeholder = "笔记关键词" class="propertyValue1" id="search_content" maxlength="20" style="width:90px">
		                            <input type="hidden" value="search_content" class="propertyName"/>
		                        </div>
		                    </td>
		                	<td>
		                        <div class="profile-info-value searchTr">
		                            <input type="text" placeholder = "作者/用户昵称" class="propertyValue1" id="search_createPersonName" maxlength="10" style="width:90px">
		                            <input type="hidden" value="search_createPersonName" class="propertyName"/>
		                        </div>
		                    </td>
		                    <td>
		                       <div class="profile-info-value searchTr">
		                            <select name="search_gradeId" id="search_gradeId" class="propertyValue1"  style="width:140px">
					               		<option value="">班级</option>
					               		<c:forEach var="grade" items="${gradeList}">
					                        <option value="${grade.id}">${grade.name}</option>
					                    </c:forEach>
					                </select>
		                            <input type="hidden" value="search_gradeId" class="propertyName"/>
		                        </div>
		                    </td>
		                    <td>
		                        <div class="profile-info-value searchTr">
		                            <input type="text" class="datetime-picker propertyValue1"  id="startTime" name="startTime" placeholder = "开始日期" style="width:85px"/>
		                            <input type="hidden" value="startTime" class="propertyName"/>
		                        </div>
		                    </td>
		                    <td>
		                        <div class="profile-info-value">
		                            至 
		                        </div>
		                    </td>
		                    <td>
		                        <div class="profile-info-value searchTr">
		                            <input type="text" class="datetime-picker propertyValue1"  id="stopTime" name="stopTime" placeholder = "结束日期" style="width:85px"/>
		                            <input type="hidden" value="stopTime" class="propertyName"/>
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
					<table id="notesTable"
						class="table table-striped table-bordered table-hover">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 修改form -->
<div id="dialogShowNotesDiv"></div>
<div id="showNotesDialog" class="hide">
	<form id="updateNotes-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="show_id" id="show_id">
		<p style="margin-bottom: 15px;font-size: 15;">笔记内容:</p>
		<div id="showDiv" style="font-size: 15;word-break: break-all;word-wrap: break-word;"></div>
		<hr/>
		<p style="margin-bottom: 15px;font-size: 15;">评论:</p>
		<div class="col-xs-12" style="padding: 0;">
			<table id="commentTable"
				class="table" style="border:none">
			</table>
		</div>
	</form>
</div>

<script type="text/javascript" src="${base}/js/cloudClass/notesDetail.js?v=ipandatcm_1.3"></script>
