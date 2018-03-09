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
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header">
	当前位置：移动端管理 <small> <i
		class="ace-icon fa fa-angle-double-right"></i>
	</small> <span>文件管理 </span>
</div>
<div class="mainrighttab tabresourse bordernone">
	        <div role="tabpanel" class="tab-pane active" id="home">
	        
	        	<div class="mainrighttab tabresourse bordernone">
					<p class="col-xs-4" style="padding: 0;">
					    <button class="btn btn-sm btn-success upload_bx" title="上传word文档">
							<i class="glyphicon glyphicon-open"></i> 上传word文档
						</button>
						<!-- <button class="btn btn-sm btn-success download_bx" title="上传word文档">
							<i class="glyphicon glyphicon-save"></i> 下载word文档
						</button> -->
					
						<a href="/link/word/download?filename=链接地址添加文档.docx"> 下载word说明文档</a>
					
					<p>
				</div>
				
				<div style="font-size: 16px;padding-bottom: 50px;letter-spacing: 0.5px">
					 word文档内容主要包括专题页的跳转添加、学堂banner的跳转。下载文档可看具体描述情况，开发人员，测试人员维护，运营人员使用操作
				</div>
				
				<div class="mainrighttab tabresourse bordernone">
					<p class="col-xs-4" style="padding: 0;">
						 <button class="btn btn-sm btn-success upload_excel" title="上传excel文档">
							<i class="glyphicon glyphicon-open"></i> 上传excel文档
						</button>
				<!-- 		 <button class="btn btn-sm btn-success download_excel" title="上传excel文档">
							<i class="glyphicon glyphicon-save"></i> 下载excel文档
						</button> -->
						
						<a href="/link/word/download?filename=常见问题模板.xls"> 下载excel说明文档</a>
					</p>	
				</div>
				
				<div style="font-size: 16px;padding-bottom: 50px;letter-spacing: 0.5px">
					 excel文档主要维护帮助中的常见问题，及其回答。
				</div>
				
				
				<!-- 文件记录 -->
				
				
				
				<!-- 常见问题上传记录   -->
				<c:forEach var="item" items="${record}">
					 <%-- <option value="${record.path}">${excel_file_record.cityName}</option> --%>
					 <a href="/link/word/download?filename=${item.path}">${item.name}</a>
			    </c:forEach>
				
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

<script type="text/javascript" src="/js/mobile/link.js?v=ipandatcm_1.3"></script>