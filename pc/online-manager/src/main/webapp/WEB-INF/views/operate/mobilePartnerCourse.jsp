<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<div class="page-header">
	当前位置：运营管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
	<span> 移动端合伙人课程管理</span>
</div>

<div class="mainrighttab tabresourse bordernone">
	<div id="detailDiv">
		<form class="form-horizontal" id="courseDetailForm" method="post" action="">
			<input type="hidden" name="id" id="id">
			<input type="hidden" name="course_id" id="course_id" value="${course_id}">
			<div class="form-group" style="margin-top:18px;">
				<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>课程展示图：</label>
				<div class="col-sm-3" style="width: 285px; height: 140px;">
					<div class="clearfix">
						<input type="file" name="smallImgPath_file" id="smallImgPath_file" class="uploadImg"/>
					</div>
					<input name="img_url" id="edit_img_url" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
				</div>
			</div>
			<div class="space-4"></div>
			<div class="form-group " style="margin-top:50px;margin-bottom:60px">
				<label class="col-sm-1 control-label no-padding-right" for="edit_share_desc"><font color="red">*</font>合伙人介绍：</label>
				<div class="col-lg-10 " style="height:293px">
					<div class="clearfix">
						<div class="wysiwyg-editor" id="shareDesc_content"></div><br>
						<input type="hidden" name="share_desc"  id="edit_share_desc" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
					</div>
				</div>
			</div>
			<div class="form-group " style="margin-top:50px;margin-bottom:60px">
				<label class="col-sm-1 control-label no-padding-right" for="edit_work_flow"><font color="red">*</font>推广流程：</label>
				<div class="col-lg-10 " style="height:293px">
					<div class="clearfix">
						<div class="wysiwyg-editor" id="workFlow_content"></div><br>
						<input type="hidden" name="work_flow"  id="edit_work_flow" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
					</div>
				</div>
			</div>
		</form>
	</div>
	<div style="width:100%; text-align: center;">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="btn btn-sm btn-success add_bx" id="okbt">
			<i class="glyphicon glyphicon-ok"></i> 确定
		</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="btn btn-sm btn-success qx_bx" id="cancelbt">
			<i class="glyphicon glyphicon-remove"></i> 取消
		</button>
	</div>
</div>
<script type="text/javascript" src="${base}/js/operate/mobilePartnerCourse.js?v=1.8"></script>