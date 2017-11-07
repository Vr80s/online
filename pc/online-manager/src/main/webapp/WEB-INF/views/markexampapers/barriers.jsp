<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
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
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
    <div class="row">
        <div style="margin-left:10px">
            当前位置：学习闯关管理 <small><i class="ace-icon fa fa-angle-double-right"></i></small>
            批阅试卷 <small><i class="ace-icon fa fa-angle-double-right"></i></small>
            ${grade_name} <small><i class="ace-icon fa fa-angle-double-right"></i></small>
            关卡列表
            <span style="float:right;margin-right:10px">
                <button type="button" class="btn btn-sm" onclick="goBack()">
                    <i class="fa fa-arrow-left">返回</i>
                </button>
            </span>
        </div>
    </div>
</div>


<div class="mainrighttab tabresourse bordernone">
    <input type="hidden" value="${grade_id}" id="grade_id">
    <input type="hidden" value="${grade_name}" id="grade_name">
	<div class="row">
		<div class="col-xs-12">
			<table id="barrierTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="${base}/js/markexampapers/barriers.js?v=1"></script>