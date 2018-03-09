<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<script src="/js/layer/layer.js"></script>
<link href="/js/layer/skin/layer.css" type="text/css" />
<script src="/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header">
	当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i></small> 
	<span> 班级管理 </span>
	<small> <i class="ace-icon fa fa-angle-double-right"></i></small> 
	<span> ${gradeName}</span>
	<small> <i class="ace-icon fa fa-angle-double-right"></i></small> 
	<span>学员列表</span>
</div>
<!-- <div class="mainrighttab tabresourse bordernone"
	style="text-align: right; width: 100%;">
	<button class="btn btn-sm btn-success add_bx" id="returnbutton">
		<i class="glyphicon glyphicon-arrow-left"></i> 返回上级
	</button>
</div> -->
<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<!-- <button class="btn btn-sm btn-success add_bx" id="returnbutton">
			<i class="glyphicon glyphicon-arrow-left"></i> 返回上级
		</button> -->
		<button class="btn btn-sm btn-success dele_bx">
			<i class="glyphicon glyphicon-trash"></i> 批量移除
		</button>
	</p>

	<div class="searchDivClass" id="searchDiv">
		<div class="profile-info-row">

			<table frame=void >
				<input type="hidden" id="gradeId" value="${gradeId}" />
				<input type="hidden" id="gradeName" value="${gradeName}" />
				<input type="hidden" id="page" value="${page}" />
				<tr>
					<td>
						<div class="profile-info-value searchTr">
							<input type="text" placeholder="学号/用户名/学员姓名"
								class="propertyValue1" id="NameSearch" style="width: 150px;">
							<input type="hidden" value="NameSearch" class="propertyName" />
						</div>
					</td>
					<td>
						<button id="searchBtn" type="button"
							class="btn btn-sm  btn-primary " onclick="search();">
							<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
						</button>
					</td>
				</tr>
			</table>
		</div>
	</div>


	<div class="row">
		<div class="col-xs-12">
			<table id="studentStoryTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 查看form -->
<div id="dialogShowStudentStoryDiv"></div>
<div id="showStudentStoryDialog" class="hide">
	<form class="form-horizontal" id="showStudentStory-form" method="post" action="">
		<div class="form-group" style="margin-top: 15px;">
			<label class="col-sm-5 control-label no-padding-right" for="showName"><font
				color="red">*</font><b>学员姓名: </b></label>
			<div class="col-sm-4">
				<p id="showName" class="paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label no-padding-right" for="showName"><font
				color="red">*</font><b>所在班级: </b></label>
			<div class="col-sm-4">
				<p id="showGradeName" class="paddingtop7px padding7">${gradeName}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label no-padding-right" for="showName"><font
				color="red">*</font><b>学号: </b></label>
			<div class="col-sm-4">
				<p id="showStudentNumber" class="paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label no-padding-right" for="showName"><font
				color="red">*</font><b>手机号: </b></label>
			<div class="col-sm-4">
				<p id="showPhone" class="paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label no-padding-right" for="showName"><font
				color="red">*</font><b>学历: </b></label>
			<div class="col-sm-4">
				<p id="educationName" class="paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label no-padding-right" for="showName"><font
				color="red">*</font><b>专业: </b></label>
			<div class="col-sm-4">
				<p id="majorName" class="paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label no-padding-right" for="showName"><font
				color="red">*</font><b>缴费详情: </b></label>
			<div class="col-sm-4">
				<p id="showIsPayment" class="paddingtop7px padding7"></p>
			</div>
		</div>
	</form>
</div>




<!-- 修改缴费状态 -->
<div id="dialogShowStudentIsPaymentDiv"></div>
<div id="showStudentIsPaymentDialog" class="hide">
	<form class="form-horizontal" id="showStudentIsPayment-form"
		method="post" action="">
		<input type="hidden" name="gradeId" value="${gradeId}" /> <input
			type="hidden" id="applyId" name="id" />
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label  class="control-label col-xs-12 col-sm-3 no-padding-right" for="showName"><font
				color="red">*</font>缴费状态: </label>
			<div class="col-xs-12 col-sm-9">
				<input type="hidden" name="nouse" id="nouse" value="nouse" autofocus="autofocus">
				<label  class="control-label" style="cursor: pointer;"><input type="radio" name="isPayment"  value="1" style="cursor: pointer;" class="control-label {required:true}" />未缴费</label>&nbsp;&nbsp; 
				<label  class="control-label" style="cursor: pointer;"><input type="radio" name="isPayment" value="2" style="cursor: pointer;" class="control-label {required:true}"/>已缴费</label>
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="showName"><font
				color="red">*</font>缴费数目: </label>
			<div class="col-xs-12 col-sm-9">
				<input type="text" name="cost" id="cost" maxlength=5 class="{required:true,number:true}">
				<label class="control-label" >元 </label>
			</div>
		</div>
	</form>
</div>



<!-- 跟踪记录 -->
<div id="dialogShowStudentRecordDiv"></div>
<div id="showStudentIsRecordDialog" class="hide">
	<div class="mainrighttab tabresourse bordernone">
		<p class="col-xs-4" style="padding: 0;height: 34px;">
			<button class="btn btn-sm btn-success record_add_bx">
				<i class="glyphicon glyphicon-plus"></i> 新增
			</button>
		</p>
		<p style="text-align: right;height: 34px;line-height: 44px;" id="currentStudent" >当前学员:</p>
		<div class="row">
			<div class="col-xs-12">
				<table id="studentRecordTable"
					class="table table-striped table-bordered table-hover">
				</table>
			</div>
		</div>
	</div>

</div>




<!-- 新增跟踪记录 -->
<div id="dialogAddStudentRecordDiv"></div>
<div id="addStudentRecordDialog" class="hide">
	<form class="form-horizontal" id="addStudentRecord-form" method="post"
		action="">
		<input type="hidden" id="addApplyId" name="applyId" /> <input
			type="hidden" id="addGradeId" name="gradeId" />
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-4 control-label no-padding-right" for="showName"><font
				color="red">*</font>记录时间: </label>
			<div class="col-sm-4">
				<input type="text" class="datetime-picker {required:true}" id="recordTime"
					name="recordTime" placeholder="请选择时间" /> <input type="hidden"
					value="recordTime" class="propertyValue1" />
			</div>
		</div>
		<div class="form-group" style="margin-top: 25px;">
			<label class="col-sm-4 control-label no-padding-right" for="showName"><font
				color="red">*</font>记录内容: </label>
			<div class="col-sm-6">
				<textarea class="form-control {required:true}" name="recordContent"
					id="recordContent" rows="3" maxlength="240"></textarea>
			</div>
		</div>
		<%-- <div class="form-group" style="margin-top: 25px;">
			<label class="col-sm-4 control-label no-padding-right" for="showName"><font
				color="red">*</font>记录人: </label>
			<div class="col-sm-4">
				<select id="lecturerId" name="lecturerId"  class="{required:true}">
					<c:forEach var="l" items="${lecturer}">
						<option value="${l.id}">${l.name}</option>
					</c:forEach>
				</select>
			</div>
		</div> --%>
	</form>
</div>


<!-- 新增跟踪记录 -->
<div id="dialogUpdateStudentRecordDiv"></div>
<div id="updateStudentRecordDialog" class="hide">
	<form class="form-horizontal" id="updateStudentRecord-form"
		method="post" action="">
		<input type="hidden" id="recordId" name="id" />
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-4 control-label no-padding-right" for="showName"><font
				color="red">*</font>记录时间: </label>
			<div class="col-sm-4">
				<input type="text" class="datetime-picker {required:true}" id="updateRecordTime"
					name="recordTime" placeholder="请选择时间" />
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-4 control-label no-padding-right" for="showName"><font
				color="red">*</font>记录内容: </label>
			<div class="col-sm-6">
				<textarea class="form-control {required:true}" name="recordContent"
					id="updateRecordContent" rows="3" maxlength="240"></textarea>
			</div>
		</div>
		<%-- <div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-4 control-label no-padding-right" for="showName"><font
				color="red">*</font>记录人: </label>
			<div class="col-sm-4">
				<select id="updateLecturerId" name="lecturerId" class="{required:true}">
					<c:forEach var="l" items="${lecturer}">
						<option value="${l.id}">${l.name}</option>
					</c:forEach>
				</select>
			</div>
		</div> --%>
	</form>
</div>

<script type="text/javascript"
	src="/js/cloudClass/student.js?v=ipandatcm_1.3"></script>