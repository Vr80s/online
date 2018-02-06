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
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header">
	当前位置：移动端课程管理 <small> <i
		class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 课程详情 </span>
</div>
<div class="mainrighttab tabresourse bordernone">
	        <div role="tabpanel" class="tab-pane active" id="home">
        	<div class="mainrighttab tabresourse bordernone" id="courseDiv">
				<p class="col-xs-4" style="padding: 0;">
				    <button class="btn btn-sm btn-success upload_bx" title="上传word文档">
						<i class="glyphicon glyphicon-trash"></i> 上传word文档
					</button>
					
					 <button class="btn btn-sm btn-success upload_excel" title="上传excel文档">
						<i class="glyphicon glyphicon-trash"></i> 上传excel文档
					</button>
				<div class="row">
					<div class="col-xs-12">
						<table id="courseTable"
							class="table table-striped table-bordered table-hover" style="width: 100%;">
						</table>
					</div>
				</div>
			</div>
        </div>
</div>


<!-- 增加wordform -->
<div id="dialogAddWordDiv"></div>
<div id="addwordDialog" class="hide">
	<form id="addword-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" f><font color="red">*</font>安装包: </label>
			 <div class="col-sm-6">
					 <input type="file" name="file" id="imgPath_file"/>
					 <input type="hidden" name="filename" id="jia_imgPath_file"/>
    				 <input name="downUrl" id="add_imgPath" value="" type="hidden" class="{required:true}" >
             </div>
		</div>
	</form>
</div>

<!-- 上传excelform -->
<div id="dialogAddExcelDiv"></div>
<div id="addExcelDialog" class="hide">
	<form id="addExcel-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" f><font color="red">*</font>安装包: </label>
			 <div class="col-sm-6">
					 <input type="file" name="file" id="excel_file"/>
					 <input type="hidden" name="filename" id="jia_imgPath_file"/>
                     <input name="downUrl" id="add_imgPath" value="" type="hidden" class="{required:true}" >
             </div>
		</div>
	</form>
</div>

<script type="text/javascript" src="${base}/js/mobile/link.js?v=ipandatcm_1.3"></script>