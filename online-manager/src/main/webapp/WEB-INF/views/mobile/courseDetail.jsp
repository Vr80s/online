<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
	var weburl = '${weburl}';
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header row">
		<div class="col-xs-8" style="padding-left: 0px;padding-right: 0px;">
			当前位置：移动端管理
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> 课程管理 </span> 
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small> 
			<span id="titleXQ"> 课程详情 </span>
		</div>
		<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
			<button class="btn btn-sm btn-success" id="returnbutton">
				<i class="glyphicon glyphicon-arrow-left"></i>
				返回上级
			</button>
		</div>
</div>
<!-- Tab panes -->
<div class="tab-content" style="padding-left: 0;">
  		<div id="divKcxq">
		<!-- 增加form -->
		<div id="detailDiv">
			<form class="form-horizontal" id="courseDetailForm" method="post" action="">
				<input type="hidden" name="courseId" id="courseId" value="${param.courseId}">
				<input type="hidden" name="weburl" id="weburl" value="${weburl}">
				<input type="hidden" name="page" id="page" value="${param.page}">
				<div class="form-group" style="margin-top:18px;">
					<label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>课程展示图:</label>
					<div class="col-sm-3" style="width: 285px; height: 140px;">
						<div class="clearfix">
							<input type="file" name="smallImgPath_file" id="smallImgPath_file" class="uploadImg"/>
						</div>
						<input name="smallImgPath" id="edit_smallImgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group " style="margin-top:50px;margin-bottom:60px">
					<label class="col-sm-1 control-label no-padding-right" for="courseDetail_content"><font color="red">*</font>课程详情:</label>
					<div class="col-lg-10 " style="height:293px">
						<div class="clearfix">
							<div class="wysiwyg-editor" id="courseDetail_content"></div><br>
							<input type="hidden" name="courseDetail"  id="courseDetail" class="col-xs-10 col-sm-12 {required:true,minlength:1}">
						</div>
					</div>
				</div>
			</form>
		</div>
		<div style="width:100%; text-align: center;">
			<button class="btn btn-sm btn-success add_bx" id="okbt">
				<i class="glyphicon glyphicon-ok"></i> 确定
			</button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button class="btn btn-sm btn-success qx_bx" id="cancelbt">
				<i class="glyphicon glyphicon-remove"></i> 取消
			</button>
		</div>
	</div>
 </div>
 <script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" src="/js/mobile/courseDetail.js?v=ipandatcm_1.3"></script>