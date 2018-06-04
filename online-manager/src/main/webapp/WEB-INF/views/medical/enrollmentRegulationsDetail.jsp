<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="js/medical/enrollmentRegulationsDetail.js"></script>
<div class="page-header">
	当前位置：师承管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small> <span> 师承详情</span>
</div>

<!-- 查看 form -->
<div id="dialogDiv"></div>
<div id="editDialog" >
	<form id="update-form" class="form-horizontal"  method="post" action="" >
		<input type="hidden" id="update_id"  name="id" value="${MedicalEnrollmentRegulations.id}" >
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right" for="update_title"><font color="red">*</font>招生标题: </label>
			<div class="col-sm-3">
				<input type="text" name="title"  id="update_title" disabled="disabled"  value="${MedicalEnrollmentRegulations.title}" >
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="doctorId">老师: </label>
			<div class="col-sm-2">
				<select   name="doctorId"   id="doctorId"  class="col-xs-12 col-sm-8 " disabled="disabled">
					<option  value="" >请选择</option>
					<c:forEach var="m" items="${doctorList}">
						<c:choose>
							<c:when test="${m.id eq MedicalEnrollmentRegulations.doctorId}">
								<option value="${m.id}" selected="selected">${m.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${m.id}">${m.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="add_imgPath">封面图: </label>
			<div class="col-sm-3" >
				<div class="clearfix" id="imgAdd" style="width: 240px;">
				</div>
				<input type="text" name="coverImg"  id="add_imgPath" value="${MedicalEnrollmentRegulations.coverImg}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>
		<div class="form-group"  style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right">收徒宣传语: </label>
			<div class="col-sm-5" >
				<textarea class="form-control" name="propaganda" id="propaganda" rows="5" disabled="disabled"
						  class="col-xs-10 col-sm-12 {required:true}">${MedicalEnrollmentRegulations.propaganda}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right">大师简介: </label>
			<div class="col-sm-5" >
				<textarea class="form-control" name="doctorIntroduction" id="doctorIntroduction" rows="5" disabled="disabled"
						  class="col-xs-10 col-sm-12 {required:true}">${MedicalEnrollmentRegulations.doctorIntroduction}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="tuition"><font color="red">*</font>学费: </label>
			<div class="col-sm-1">
				<input type="text" name="tuition" id="tuition" class="col-xs-10 col-sm-8" value="${MedicalEnrollmentRegulations.tuition}" disabled="disabled">
				<div style="padding-top: 5px">元</div>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="countLimit"><font color="red">*</font>收徒人数: </label>
			<div class="col-sm-1">
				<input type="text" name="countLimit" id="countLimit" class="col-xs-10 col-sm-8" value="${MedicalEnrollmentRegulations.countLimit}"  disabled="disabled">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="deadline"><font color="red">*</font>报名截止时间: </label>
			<div class="col-sm-3">
				<input type="text" name="deadlineStr" id="deadline" disabled="disabled"
					   value="${fn:substring(MedicalEnrollmentRegulations.deadline,0,19)}"  class="datetime-picker propertyValue1 ">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="startTime"><font color="red">*</font>学习时间: </label>
			<div class="col-sm-2">
				<input type="text" name="startTimeStr" id="startTime" disabled="disabled"
					   value="${fn:substring(MedicalEnrollmentRegulations.startTime,0,19)}" class="datetime-picker">
			</div>
			<div style="float: left;padding-top: 5px;margin-right: 5px">
				至
			</div>
			<div class="col-sm-2">
				<input type="text" name="endTimeStr" id="endTime" disabled="disabled"
					   value="${fn:substring(MedicalEnrollmentRegulations.endTime,0,19)}"  class="datetime-picker">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="studyAddress"><font color="red">*</font>学习地址: </label>
			<input type="hidden" name="oldStudyAddress" id="oldStudyAddress" value="${MedicalEnrollmentRegulations.studyAddress}"/>
			<div class="col-sm-2">
				<select id="edit_province" onchange="doProvAndCityRelationEdit();"
						class="clearfix col-xs-10 col-sm-12 {required:true}">
					<option id="choosePro"value="-1">请选择您所在省份</option>
				</select>
			</div>
			<div class="col-sm-2">
				<select id="edit_citys" onchange="onchangeCityEdit();" class="clearfix col-xs-10 col-sm-12 {required:true}">
					<option id='chooseCity' value='-1'>请选择您所在城市</option>
				</select>
			</div>
			<div class="col-sm-2">
				<select id="edit_county" onchange="onchangeCountyEdit();" class="clearfix col-xs-10 col-sm-12 {required:true}">
					<option id='chooseCountys' value='-1'>请选择您所在县区</option>
				</select>
			</div>
			<div class="col-sm-2">
				<input type="text" name="detailedAddress" id="detailedAddress" disabled="disabled" class="col-xs-10 col-sm-12 ">
			</div>
			<input type="hidden" name="studyAddress" id="studyAddress"/>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="learningProcess"><font color="red">*</font>拜师学习流程: </label>
			<div class="col-sm-5">
				<textarea class="form-control" name="learningProcess" id="learningProcess" rows="5" disabled="disabled"
						  class="col-xs-10 col-sm-12 ">${MedicalEnrollmentRegulations.learningProcess}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="contactWay"><font color="red">*</font>联系人: </label>
			<div class="col-sm-5">
				<textarea class="form-control" name="contactWay" id="contactWay" rows="5" disabled="disabled"
						  class="col-xs-10 col-sm-12 ">${MedicalEnrollmentRegulations.contactWay}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="rightsAndInterests"><font color="red">*</font>弟子权益: </label>
			<div class="col-sm-5">
				<textarea class="form-control" name="rightsAndInterests" id="rightsAndInterests" rows="5" disabled="disabled"
						  class="col-xs-10 col-sm-12 ">${MedicalEnrollmentRegulations.rightsAndInterests}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="qualification"><font color="red">*</font>拜师资格: </label>
			<div class="col-sm-5">
				<textarea class="form-control" name="qualification" id="qualification" rows="5" disabled="disabled"
						  class="col-xs-10 col-sm-12 ">${MedicalEnrollmentRegulations.qualification}</textarea>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="ceremonyAddress"><font color="red">*</font>拜师地址: </label>
			<div class="col-sm-5 " >
				<div>
					<script id="editor" type="text/plain" style="width:600px;height:300px;"></script></div>
				<input type="hidden" name="ceremonyAddress"  id="ceremonyAddress" class="col-xs-10 col-sm-12 ">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="regulations"><font color="red">*</font>招生简章: </label>
			<div class="col-sm-5">
				<div>
					<script id="editor1" type="text/plain" style="width:600px;height:300px;"></script></div>
				<input type="hidden" name="regulations"  id="regulations" class="col-xs-10 col-sm-12 ">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="posterImg"><font color="red">*</font>海报: </label>
			<div class="col-sm-3">
				<div class="clearfix" id="imgAddPoster" style="width: 240px;">

				</div>
				<input type="text" name="posterImg"  id="posterImg" disabled="disabled" value="${MedicalEnrollmentRegulations.posterImg}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" ><font color="red">*</font>招生简章: </label>
			<div class="col-sm-3" style="padding-top:7px">

				<a href="${MedicalEnrollmentRegulations.entryFormAttachment}" style="color:blue"> 下载招生简章</a>
			</div>
		</div>

	</form>
	<div class="col-xs-7" style="text-align: right;margin-top:50px;">
		<button class="btn btn-sm btn-success" id="returnbutton">
			返回
		</button>
	</div>
</div>



<script type="text/javascript">
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	var ue = UE.getEditor('editor',{
		toolbars:[['source', //源代码
			'undo', //撤销
			'redo', //重做
			'bold', //加粗
			'forecolor', //字体颜色
			'backcolor', //背景色
			'indent', //首行缩进
			'removeformat',//清除格式
			'formatmatch', //格式刷
			'blockquote', //引用
			'fontfamily', //字体
			'fontsize', //字号
			'paragraph', //段落格式
			'italic', //斜体
			'underline', //下划线
			'strikethrough', //删除线
			'superscript', //上标
			'subscript', //下标
			'touppercase', //字母大写
			'tolowercase', //字母小写
			'justifyleft', //居左对齐
			'justifyright', //居右对齐
			'justifycenter', //居中对齐
			'justifyjustify',//两端对齐
			'link', //超链接
			'unlink', //取消链接
			'simpleupload', //单图上传
			// 'insertimage', //多图上传
			'emotion', //表情
			'fullscreen'
		] ],
		autoHeightEnabled: false,
		autoFloatEnabled: true,
		enableAutoSave:false,
		imagePopup:false
	});
	var ue1 = UE.getEditor('editor1',{
		toolbars:[['source', //源代码
			'undo', //撤销
			'redo', //重做
			'bold', //加粗
			'forecolor', //字体颜色
			'backcolor', //背景色
			'indent', //首行缩进
			'removeformat',//清除格式
			'formatmatch', //格式刷
			'blockquote', //引用
			'fontfamily', //字体
			'fontsize', //字号
			'paragraph', //段落格式
			'italic', //斜体
			'underline', //下划线
			'strikethrough', //删除线
			'superscript', //上标
			'subscript', //下标
			'touppercase', //字母大写
			'tolowercase', //字母小写
			'justifyleft', //居左对齐
			'justifyright', //居右对齐
			'justifycenter', //居中对齐
			'justifyjustify',//两端对齐
			'link', //超链接
			'unlink', //取消链接
			'simpleupload', //单图上传
			// 'insertimage', //多图上传
			'emotion', //表情
			'fullscreen'
		] ],
		autoHeightEnabled: false,
		autoFloatEnabled: true,
		enableAutoSave:false,
		imagePopup:false
	});



    var oldCeremonyAddress = '${MedicalEnrollmentRegulations.ceremonyAddress}';
    var oldRegulations = '${MedicalEnrollmentRegulations.regulations}';
    ue.ready(function() {
        if (oldCeremonyAddress) {
            UE.getEditor('editor').setContent(oldCeremonyAddress);
        }
    });
    ue1.ready(function() {
        if (oldRegulations) {
            UE.getEditor('editor1').setContent(oldRegulations);
        }
    });
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		var url = '/ueditor/upload'
		if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
			return url;
		} else if (action == 'uploadvideo') {//视频上传：
			return url;
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	}
</script>
