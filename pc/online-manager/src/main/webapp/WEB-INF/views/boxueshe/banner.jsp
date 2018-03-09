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
<div class="page-header">
  当前位置：头条管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> banner管理 </span>
</div>
<div class="mainrighttab tabresourse bordernone">
    <div class="row">
		<div class="col-xs-12">
			<table id="bannerTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div> 
<div id="dialogUpdateRecImgDiv"></div>
<div id="updateRecImgDialog" class="hide">
	<form id="updateRecImg-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="id" id="update_id">
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="bannerPath"><font color="red">*</font>banner图片: </label>
			 <div class="col-sm-6" id="updateRecImg">
			 		<div class="clearfixUpdate">
						<input type="file" name="update_bannerPath_file" id="update_bannerPath_file" class="uploadImg"/>
					</div>
					（图片尺寸限制：890*366）
					<input name="bannerPath" id="update_bannerPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
             </div>
		</div>
	</form>
</div>
<script type="text/javascript" src="/js/boxueshe/banner.js?v=ipandatcm_1.3"></script>