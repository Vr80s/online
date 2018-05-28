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
		<span> 试卷列表 </span>
	</div>
	<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
		<button class="btn btn-sm btn-success" id="returnButton">
			返回上一页
		</button>
	</div>
</div>

<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" title="新增试卷">
			<i class="glyphicon glyphicon-plus"></i> 新建试卷
		</button>
	</p>
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void >
                <tr>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_paperName" class="propertyValue1"  placeholder = "请输入试卷名称" maxlength="30"/>
                            <input type="hidden" value="search_paperName" class="propertyName"/>
                        </div>
                        <input type="hidden" name="courseId" id="courseId" value="${param.courseId}">
						<input type="hidden" name="courseName" id="courseName" value="${param.courseName}">
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
			<table id="examPaperTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 增加form -->
<div id="dialogAddExamPaperDiv"></div>
<div id="addExamPaperDialog" class="hide">
	<form id="addexamPaper-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="description"><font color="red">*</font>banner名称: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="description"  id="add_description" maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgPath"><font color="red">*</font>banner图片: </label>
			 <div class="col-sm-6" id="addDiv">
			 		<div class="clearfixAdd">
						<input type="file" name="imgPath_file" id="imgPath_file" class="uploadImg"/>
					</div>
					（图片尺寸上传限制：1200*386）
					<input name="imgPath" id="add_imgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgHref"><font color="red">*</font>链接地址: </label>
			 <div class="col-sm-6">
               		<input type="text" name="imgHref"  id="add_imgHref" class="col-xs-10 col-sm-12 {required:true,maxlength:120}">
             </div>
		</div>
	</form>
</div>

<!-- 修改form -->
<div id="dialogUpdateExamPaperDiv"></div>
<div id="updateExamPaperDialog" class="hide">
	<form id="updateExamPaper-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="Id" id="update_id">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="description"><font color="red">*</font>banner名称: </label>
			 <div class="col-sm-6">
			 		<input type="text" name="description"  id="update_description" maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgPath"><font color="red">*</font>banner图片: </label>
			 <div class="col-sm-6">
			 		<div class="clearfixUpdate">
						<input type="file" name="update_imgPath_file" id="update_imgPath_file" class="uploadImg"/>
					</div>
					（图片尺寸上传限制：1200*386）
					<input name="imgPath" id="update_imgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgHref"><font color="red">*</font>链接地址: </label>
			 <div class="col-sm-6">
               		<input type="text" name="imgHref"  id="update_imgHref" class="col-xs-10 col-sm-12 {required:true,maxlength:120}">
             </div>
		</div>
	</form>
</div>
<script type="text/javascript" src="/js/exam/examPaper.js?v=ipandatcm_1.3"></script>
