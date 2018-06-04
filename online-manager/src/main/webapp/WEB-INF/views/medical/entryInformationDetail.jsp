<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />

<script type="text/javascript">

    try {
        var scripts = [ null, null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        });
    } catch (e) {
    }

</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/cloudClass/provinces.js?v=ipandatcm_1.3"></script>
<script type="text/javascript" src="js/medical/entryInformationDetail.js"></script>
<div class="page-header">
	当前位置：师承管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small> <span> 报名详情</span>
</div>

<!-- 查看 form -->
<div id="dialogDiv"></div>
<div id="editDialog" >
	<form id="update-form" class="form-horizontal"  method="post" action="" >
		<input type="hidden" id="update_id"  name="id" value="${MedicalEntryInformation.id}" >
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right" for="name"><font color="red">*</font>姓名: </label>
			<div class="col-sm-3">
				<input type="text" name="name"  id="name" disabled="disabled"  value="${MedicalEntryInformation.name}" >
			</div>
		</div>
		<div class="form-group"  style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right">年龄: </label>
			<div class="col-sm-5" >
				<input type="text" name="age"  id="age" disabled="disabled" maxlength="20" value="${MedicalEntryInformation.age}" >
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right">性别: </label>
			<div class="col-sm-2" >
				<input type="hidden"   id="oldSex"   value="${MedicalEntryInformation.sex}" >
				<select   name="sex"   id="sex"  class="col-xs-12 col-sm-9 " disabled="disabled">
					<option  value="1" >男</option>
					<option  value="0" >女</option>
					<option  value="2" >未知</option>
				</select>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" ><font color="red">*</font>籍贯: </label>
			<div class="col-sm-5" >
				<input type="text" name="nativePlace"  id="nativePlace" disabled="disabled" value="${MedicalEntryInformation.nativePlace}" >
			</div>

		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="education"><font color="red">*</font>学历: </label>
			<div class="col-sm-1">
				<input type="hidden" name="education" id="oldEducation"  value="${MedicalEntryInformation.education}" >
				<select   name="education"   id="education"  class="col-xs-12 col-sm-12 " disabled="disabled">
					<option  value="1" >小学</option>
					<option  value="2" >初中</option>
					<option  value="3" >高中</option>
					<option  value="4" >大专</option>
					<option  value="5" >本科</option>
					<option  value="6" >研究生</option>
					<option  value="7" >博士生</option>
					<option  value="8" >博士后</option>
				</select>

			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="educationExperience"><font color="red">*</font>学习经历: </label>
			<div class="col-sm-5">
				<textarea class="form-control" name="educationExperience" id="educationExperience" rows="3" disabled="disabled"
						  class="col-xs-10 col-sm-12 ">${MedicalEntryInformation.educationExperience}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="medicalExperience"><font color="red">*</font>行医经历: </label>
			<div class="col-sm-5">
				<textarea class="form-control" name="medicalExperience" id="medicalExperience" rows="3" disabled="disabled"
						  class="col-xs-10 col-sm-12 ">${MedicalEntryInformation.medicalExperience}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="goal"><font color="red">*</font>学中医的目标: </label>
			<div class="col-sm-5">
				<textarea class="form-control" name="goal" id="goal" rows="3" disabled="disabled"
						  class="col-xs-10 col-sm-12 ">${MedicalEntryInformation.goal}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="tel"><font color="red">*</font>手机号: </label>
			<div class="col-sm-1">
				<input type="text" name="tel" id="tel"  value="${MedicalEntryInformation.tel}" disabled="disabled">
			</div>
		</div>
		<%--<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="wechat">微信号: </label>
			<div class="col-sm-1">
				<input type="text" name="wechat" id="wechat"  value="${MedicalEntryInformation.wechat}" disabled="disabled">
			</div>
		</div>--%>

	</form>
	<div class="col-xs-7" style="text-align: right;margin-top:50px;">
		<button class="btn btn-sm btn-success" id="returnbutton">
			返回
		</button>
	</div>
</div>



