<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/bootstrap/assets/css/bootstrap-select.css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />
<style type="text/css">
.bs-select-all.btn, .bs-deselect-all.btn, .dropdown-toggle.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 12px  !important;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent !important;
    border-radius: 4px;
    text-shadow: 0 0px 0 rgba(0, 0, 0, 0.25) !important;
}

.bs-select-all.btn-default, .bs-deselect-all.btn-default, .dropdown-toggle.btn-default{
    color: #333 !important;
    background-color: #fff !important;
    border-color: #ccc !important;
}

.bs-actionsbox .btn-group button {
    width: 49.6% !important;
}

</style>
<script type="text/javascript" src="${base}/bootstrap/assets/js/bootstrap-select.js"></script>
<script type="text/javascript" src="${base}/bootstrap/assets/js/defaults-zh_CN.js"></script>
<script type="text/javascript" src="${base}/js/My97DatePicker/WdatePicker.js"></script>

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
	  当前位置：班级管理 <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
	    <span> 班级学习计划 </span>
	</div>
	<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
		<button class="btn btn-sm btn-sm" id="returnButton">
			返回班级列表
		</button>
	</div>
</div>
	<input type="hidden" name="courseId" id="courseId" value="${param.courseId}">
	<input type="hidden" name="gradeId" id="gradeId" value="${param.gradeId}">
	<input type="hidden" name="gradeName" id="gradeName" value="${param.gradeName}">

	<table class="table table-bordered" id="planTable" style="font-size: 12px;width:75%">
	  <caption>
  		  <button class="btn btn-sm btn-success insert_bx" title="插入休息日">
				<i class="glyphicon glyphicon-plus"></i> 插入休息日
		  </button>
  		  <button class="btn btn-sm btn-success download_bx" title="下载学习计划">
				<i class="glyphicon glyphicon-download-alt"></i> 下载学习计划
		  </button>
	  </caption>
	  <thead style="color: black;">
	    <tr>
	      <th style="width:5%">选择</th>
	      <th style="width:5%">序号</th>
	      <th style="width:15%">日期</th>
	      <th style="width:10%">星期</th>
	      <th style="width:10%">天数</th>
	      <th style="width:15%">串讲开始时间</th>
	      <th style="width:20%">操作</th>
	    </tr>
	  </thead>
	  <tbody>
	  </tbody>
	</table>
<!-- 查看学习计划-->
<div id="dialogGradePlanDiv"></div>
<div id="showGradePlanDialog" class="hide" >
	<table class="table table-bordered" id="gradePlanTable" style="font-size: 12px;">
	  <thead>
	    <tr>
	      <th colspan="2" style="background-color: white;border-bottom: 1px solid #dddddd;color: black;">学习计划详情</th>
	    </tr>
	    <tr>
	      <th style="width:50%;background-color: white;border-bottom: 1px solid #dddddd;color: black;">知识点</th>
	      <th style="width:50%;background-color: white;border-bottom: 1px solid #dddddd;color: black;">知识点时长</th>
	    </tr>
	  </thead>
	  <tbody>
	  </tbody>
	</table>
</div>

<!-- 添加串讲 -->
<div id="dialogChuanJiangDiv"></div>
<div id="updateChuanJiangDialog" class="hide" >
	<form action="" method="post" class="form-horizontal" role="form" id="update-form">
		<input type="hidden" name="id" id="id">
		<input type="hidden" name="chuanjiangHas" id="chuanjiangHas">
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 串讲课名称: </label>
            <div class="col-sm-9">
                <input type="text" name="chuanjiangName" id="chuanjiangName"  class="col-sm-8 {required:true}">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 授课老师: </label>
            <div class="col-sm-9">
                <select name="chuanjiangLecturerId" id="chuanjiangLecturerId"  class="col-sm-8 {required:true}">
                	<option value="">授课老师</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 直播时长: </label>
            <div class="col-sm-8">
                <input type="text" name="chuanjiangDuration" id="chuanjiangDuration"  class="col-sm-9 {required:true,number:true,rangelength:[1,200]}">
            </div>
            <div class="col-sm-1" style="margin-top: 7px;margin-left: -110px;">
                		小时
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 直播时间: </label>
            <div class="col-sm-3">
                <input type="text" name="chuanjiangStartTime" id="chuanjiangStartTime" readonly="readonly"  
	                onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:planDateMax,maxDate:planDateMin,startDate:planDateMax,onpicked:function(dp){$('#chuanjiangStartTime').trigger('change') }})"
	                class="Wdate {required:true}"/>
            </div>
            <div class="col-sm-3" style="margin-left: -2px;">
                <input type="text" name="chuanjiangEndTime" id="chuanjiangEndTime" readonly="readonly" class="{required:true}">
            </div>
        </div>
        <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" ><font color="red">*</font>直播方式: </label>
			 <div class="col-sm-3">
			 	<p class="paddingtop7px padding7" style="line-height:22px">
			 		<label style="cursor: pointer;"><input type="radio" class="{required:true}" style="cursor: pointer;vertical-align:text-top;margin-top:2px;margin-left:2px;margin-right:5px" onclick="changeMode()" name="chuanjiangMode"  id="thisWeb" value="0" checked>本站 </label>
			 		<label style="cursor: pointer;"><input type="radio" class="{required:true}" style="vertical-align:text-top;margin-top:2px;margin-right:5px"  onclick="changeMode()" name="chuanjiangMode"  id="otherWeb" value="1">外部</label>
			 	</p>
             </div>
             <div class="col-sm-3">
             </div>
		</div>
		<div class="form-group">
			 <label class="col-sm-3 control-label no-padding-right" for="chuanjiangRoomId"><font color="red">*</font>直播间ID: </label>
			 <div class="col-sm-6" >
			 	<input type="text" name="chuanjiangRoomId"  id="chuanjiangRoomId" maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
		<div class="form-group">
			 <label class="col-sm-3 control-label no-padding-right" for="chuanjiangRoomLink"><font color="red">*</font>外部链接: </label>
			 <div class="col-sm-6" >
			 	<input type="text" name="chuanjiangRoomLink" id="chuanjiangRoomLink" maxlength="255" class="col-xs-10 col-sm-12 {required:true}">
             </div>
		</div>
		<div class="form-group"  style="margin-top: 18px;">
			 <label class="col-sm-3 control-label no-padding-right" for="chuanjiangRoomLink">新增微课权限: </label>
			 <div class="col-sm-6" >
			 	<select name="microCourseIds" id="microCourseIds" class="selectpicker" multiple data-live-search="true" style="padding: 0px;">
						<!-- <option value="-1">全部班级</option> -->
					<c:forEach var="course" items="${coursesMicro}">
                        <option value="${course.id}">${course.courseName}</option>
                    </c:forEach>
				</select>
             </div>
		</div>
     </form>
</div>
<script type="text/javascript" src="${base}/js/cloudClass/plan.js?v=ipandatcm_1.3"></script>