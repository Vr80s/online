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
<div class="page-header row">
		<div class="col-xs-8" style="padding-left: 0px;padding-right: 0px;">
		  当前位置：学习闯关管理 <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
		    <span> 关卡管理 </span>
		    <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> ${param.courseName} </span>
			<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
			<span> 关卡列表 </span>
		</div>
		<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
			<button class="btn btn-sm btn-success" id="returnbutton">
				返回上一页
			</button>
		</div>
</div>

<div class="row" style="margin-left: 0;margin-right: 0;">
	<div class="col-md-3" style="padding-left: 0px;">
		<div class="contrightbox" 
			style="overflow-y: hidden; overflow-x: hidden;height: 800px;">
			<div class="zTreeDemoBackground"
				style="overflow-y: auto; overflow-x: auto;height: 800px;">
				<span style="margin-left: 10px;color: #009FFF"><i class="glyphicon glyphicon-info-sign"></i>请在任意知识点上右击设置闯关关卡</span>
				<ul id="ztree" class="ztree"
					style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
			</div>
		</div>
	</div>
	<div class="col-md-9" style="padding: 0;">
		<div class="mainrighttab tabresourse bordernone" style="float: right;">
			<div class="searchDivClass" id="searchDiv" style="display:none">
		        <div class="profile-info-row" >
		            <table frame=void >
		                <tr>
		                	<td style="display: none">
		                        <div class="profile-info-value searchTr">
		                            <input type="hidden" placeholder = "ID" class="propertyValue1" id="search_courseId" value="${param.courseId}">
		                            <input type="hidden" value="search_courseId" class="propertyName"/>
		                        </div>
		                        <input type="hidden" name="courseId" id="courseId" value="${param.courseId}">
								<input type="hidden" name="courseName" id="courseName" value="${param.courseName}">
		                    </td>
		                	<td style="display: none">
		                        <div class="profile-info-value searchTr">
		                            <input type="hidden" placeholder = "ID" class="propertyValue1" id="search_chapterId">
		                            <input type="hidden" value="search_chapterId" class="propertyName"/>
		                        </div>
		                    </td>
		                	<td style="display: none">
		                        <div class="profile-info-value searchTr">
		                            <input type="hidden" placeholder = "ID" class="propertyValue1" id="search_chapterLevel">
		                            <input type="hidden" value="search_chapterLevel" class="propertyName"/>
		                        </div>
		                    </td>
		                    <td>
		                        <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
		                                onclick="search();">
		                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
		                        </button>
		                    </td>
		                </tr>
		            </table>
		        </div>
		    </div>
			<div class="row">
				<div class="col-xs-12">
					<table id="barrierTable"
						class="table table-striped table-bordered table-hover">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${base}/js/barrier/barrierDetail.js?v=ipandatcm_1.3"></script>
