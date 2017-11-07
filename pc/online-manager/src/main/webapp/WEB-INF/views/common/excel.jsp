<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="js/excel_import.js"></script>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>

<style type="text/css">

.ace-file-container {
    height: 30px !important;
    width: 360px !important;
}

.ace-file-input .remove {
    position: absolute;
    right: -20px;
    top: 7px;
    display: none;
    width: 17px;
    text-align: center;
    height: 17px;
    line-height: 17px;
    font-size: 17px;
    font-weight: normal;
    background-color: #FB7142;
    border-radius: 100%;
    color: #FFF;
    text-decoration: none;
}

</style>

<div>
<div id="excelShowDiv"></div>

<div id="xlsupload" class="hide">   
	<div class="form-group no-margin-bottom">
		<form id="uploadForm" action="test/upload" method="post" enctype="multipart/form-data">
			<div id="otherFile"></div>
			<div class="">
				<label class="col-sm-3 control-label no-padding-right" style="margin-top:7px;"><span style="float:right">请选择文件：</span></label>
				<div class="col-sm-9">
					<div id="form-attachments">
						<!-- #section:custom/file-input -->
						
						<!-- /section:custom/file-input -->
					</div>
					<a style="margin-left:300px;" href="question/questionExcelTemplateDownload">下载模版</a>
				</div>  
			</div>
		</form>
	</div>
	<div id="result"></div>
</div>

<div id="xlsuploading" class="hide">
	<div class="col-sm-6" style="width:100%">
		<h3 class="blue lighter smaller">
			<i class="ace-icon fa fa-spinner"></i> 当前进度
		</h3>

		<div id="progressbar"></div>
	</div>
	<div><b><font color="red">注意:系统未导入完成前，请不要关闭此窗口</font></b></div>
	<div id="importResult"></div>
	<input type="hidden" id="totalCount" />
</div>

<div id="errorDiv" class="hide">
	<div style="overflow: auto;height: 280px;border: 1px solid #000;padding: 10px;" id="errorHtml">
	
	</div>
</div>

</div>

