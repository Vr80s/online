<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript" src="/js/cloudClass/provinces.js?v=ipandatcm_1.3"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/medical/enrollmentRegulationsEdit.js"></script>


<link href="/bootstrap/assets/css/bootstrap-select.css" rel="stylesheet" />
<script src="/bootstrap/assets/js/bootstrap-select.js"></script>

<style>
	.btn:hover, .btn-default:hover, .btn:active, .btn-default:active, .open .btn.dropdown-toggle, .open .btn-default.dropdown-toggle{
		background-color: #abbac3 !important;
	}
</style>

<div class="page-header">
  当前位置：师承管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
			</small>
  <span>编辑师承 </span>
</div>
<!-- 修改 form -->
<div id="dialogDiv"></div>
<div id="editDialog" >
	<form id="update-form" class="form-horizontal"  method="post" action="" >
		<input type="hidden" id="update_id"  name="id" value="${MedicalEnrollmentRegulations.id}" >
		<input type="hidden" id="creator"  name="creator" value="${MedicalEnrollmentRegulations.creator}" >
		<input type="hidden" id="createTimeStr"  name="createTimeStr" value="${MedicalEnrollmentRegulations.createTime}" >
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right" for="update_title"><font color="red">*</font>招生标题: </label>
			<div class="col-sm-3">
				<input type="text" name="title"  id="update_title" maxlength="30" placeholder = "最多30字" value="${MedicalEnrollmentRegulations.title}" class="col-xs-10 col-sm-8 {required:true}">
			</div>
		</div>
		<div class="space-4"></div>
		<%--<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="doctorId"><font color="red">*</font>老师: </label>
			<div class="col-sm-3">
				<select   name="doctorId"   id="doctorId"  class="col-xs-12 col-sm-8 {required:true}" >
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
		</div>--%>

		<div class="form-group">
			<label class="col-sm-1 control-label"><font color="red">*</font>老师: </label>
			<div class="col-sm-3" style="padding-top:5px ">
				${MedicalEnrollmentRegulations.doctorName}
			</div>
		</div>


		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="deadline"><font color="red">*</font>报名截止时间: </label>
			<div class="col-sm-3">
				<input type="text" name="deadlineStr" id="deadline" value="${fn:substring(MedicalEnrollmentRegulations.deadline,0,19)}"
					   class="datetime-picker propertyValue1 {required:true}" readonly="readonly">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="startTime"><font color="red">*</font>学习时间: </label>
			<div class="col-sm-2">
				<input type="text" name="startTimeStr" id="startTime" value="${fn:substring(MedicalEnrollmentRegulations.startTime,0,19)}"
					   placeholder = "开始时间" class="datetime-picker col-md-11 {required:true}" readonly="readonly">
			</div>
			<div style="float: left;padding-top: 5px;margin-right: 5px">
				至
			</div>
			<div class="col-sm-2">
				<input type="text" name="endTimeStr" id="endTime" value="${fn:substring(MedicalEnrollmentRegulations.endTime,0,19)}"
					   class="datetime-picker col-md-11 {required:true}" readonly="readonly">
			</div>
		</div>

        <div class="form-group"  style="margin-top: 18px;" >
            <label class="col-sm-1 control-label no-padding-right" for="add_imgPath"><font color="red">*</font>封面图: </label>
            <div class="col-sm-2" id="imgDivAdd">
                <div class="clearfix clearfixAdd" id="imgAdd" style="width: 240px;">
                </div>
                <input type="text" name="coverImg"  id="add_imgPath" class="{required:true}" value="${MedicalEnrollmentRegulations.coverImg}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
            </div>
            <div style="padding-left:60px;float: left;width: 400px; ">
                <p><font color="red">*</font>支持图片格式：jpg,png,gif,bmp</p>
                <p><font color="red">*</font>推荐最佳尺寸：750*425</p>
                <p><font color="red">*</font>推荐高宽比在0.56左右</p>
                </div>
        </div>
        <%--<div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;" >
            <label class="col-sm-1 control-label no-padding-right" for="tuition"><font color="red">*</font>学费: </label>
            <div class="col-sm-1">
                <input type="text" name="tuition" id="tuition" maxlength="11"  onkeyup="value=value.replace(/[^\d]/g,'')" value="${MedicalEnrollmentRegulations.tuition}" class="col-xs-10 col-sm-8 {required:true}">
                <div style="padding-top: 5px">元</div>
            </div>
        </div>--%>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;" >
            <label class="col-sm-1 control-label no-padding-right" for="countLimit"><font color="red">*</font>收徒人数: </label>
            <div class="col-sm-1">
                <input type="text" name="countLimit" id="countLimit" maxlength="50" onkeyup="this.value=this.value.replace(/\D|^0/g,'')"
                       onafterpaste="this.value=this.value.replace(/\D|^0/g,'')"value="${MedicalEnrollmentRegulations.countLimit}"  class="col-xs-10 col-sm-8 {required:true}">
            </div>
        </div>

		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="studyAddress"><font color="red">*</font>学习地址: </label>
			<input type="hidden" name="oldStudyAddress" id="oldStudyAddress" class="{required:true}" value="${MedicalEnrollmentRegulations.studyAddress}"/>
			<div class="col-sm-2">
				<select id="edit_province" onchange="doProvAndCityRelationEdit();"
						class="clearfix col-xs-10 col-sm-12 {required:true}">
					<option id="choosePro"value=""></option>
				</select>
			</div>
			<div class="col-sm-2">
				<select id="edit_citys" onchange="doProvAndCountyRelationEdit();" class="clearfix col-xs-10 col-sm-12 {required:true}">
					<option id='chooseCity' value=''></option>
				</select>
			</div>
			<div class="col-sm-2">
				<select id="edit_county" onchange="onchangeCountyEdit();" class="clearfix col-xs-10 col-sm-12 {required:true}">
					<option id='chooseCountys' value=''></option>
				</select>
			</div>
			<div class="col-sm-2">
				<input type="text" name="detailedAddress" id="detailedAddress" maxlength="70"  class="col-xs-10 col-sm-12 {required:true}">
			</div>
			<input type="hidden" name="studyAddress" id="studyAddress"/>
		</div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;" >
            <label class="col-sm-1 control-label no-padding-right" for="entryFormAttachment"><font color="red">*</font>招生简章: </label>
            <div class="col-sm-4">

                <input type="file" name="file" id="attachment_file" class="col-xs-10 col-sm-8 "/>
                <input type="hidden" name="entryFormAttachment" id="entryFormAttachment" class="{required:true}" value="${MedicalEnrollmentRegulations.entryFormAttachment}"/>
                <div style="padding-left:30px;float: left;width: 200px; "><font color="red">*</font>文件格式：docx</div>
                <a href="${MedicalEnrollmentRegulations.entryFormAttachment}" style="float:right;color:blue"> 下载招生简章</a>
            </div>
        </div>

		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="ceremonyAddress"><font color="red">*</font>相关介绍: </label>
			<div class="col-sm-5 " >
				<div>
					<script id="editor" type="text/plain" style="width:600px;height:300px;"></script></div>
				<input type="hidden" name="ceremonyAddress"  id="ceremonyAddress" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;" >
			<label class="col-sm-1 control-label no-padding-right" for="regulations"><font color="red">*</font>招生简章: </label>
			<div class="col-sm-5">
				<div>
					<script id="editor1" type="text/plain" style="width:600px;height:300px;"></script></div>
				<input type="hidden" name="regulations"  id="regulations" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
			</div>
		</div>
		<%--<div class="space-4"></div>--%>
		<%--<div class="form-group"  style="margin-top: 18px;" >--%>
			<%--<label class="col-sm-1 control-label no-padding-right" for="posterImg"><font color="red">*</font>海报: </label>--%>
			<%--<div class="col-sm-3">--%>
				<%--<div class="clearfix" id="imgAddPoster" style="width: 240px;">--%>

				<%--</div>--%>
				<%--<input type="text" name="posterImg"  id="posterImg" class="{required:true}" value="${MedicalEnrollmentRegulations.posterImg}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">--%>
			<%--</div>--%>
            <%--<div style="padding-left:30px;float: left;width: 400px; "><font color="red">*</font>支持图片格式：jpg,png,gif,bmp</div>--%>
		<%--</div>--%>


	</form>
	<div class="col-xs-7" style="text-align: right;margin-top:50px;">
          <button class="btn btn-sm btn-success" id="saveBtn">
	                       保存
          </button>
          <button class="btn btn-sm btn-success" id="returnbutton">
	                      返回
          </button>
  </div>
</div>



<script type="text/javascript">
	$(function() {
		$(".selectpicker").selectpicker({
			noneSelectedText: '请选择',
			countSelectedText: function(){}
		});
	});

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
