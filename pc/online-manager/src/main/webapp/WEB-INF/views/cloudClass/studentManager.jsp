<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
	当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 学员管理 </span><small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 班级列表 </span>
</div>

<div class="mainrighttab tabresourse bordernone">

<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

        </div>
    </div>


	<div class="row">
		<div class="col-xs-12">
			<table id="studentManagerTable" class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>


<script type="text/javascript" src="${base}/js/cloudClass/studentManager.js?v=1.0"></script>