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
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：头条管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 评价管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void >
            	<tr>
            		<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="articleId" class="propertyValue1"  placeholder = "文章ID"/>
                            <input type="hidden" value="articleId" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="content" class="propertyValue1"  placeholder = "评价关键字"/>
                            <input type="hidden" value="content" class="propertyName"/>
                        </div>
                    </td> 
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="name" class="propertyValue1"  placeholder = "作者/用户昵称"/>
                            <input type="hidden" value="name" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker"  id="startTime"  placeholder = "开始日期" />
                            <input type="hidden" value="startTime" class="propertyValue1"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            &nbsp;至 &nbsp;
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker"  id="stopTime"  placeholder = "结束日期"/>
                            <input type="hidden" value="stopTime" class="propertyValue1"/>
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
			<table id="appraiseTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div> 
<!-- 查看form -->
<div id="dialogShowAppraiseDiv"></div>
<div id="showAppraise" class="hide">
	<form id="updateAppraise-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="show_id" id="show_id">
		<div id="showDiv"></div>
	</form>
</div>
<script type="text/javascript" src="${base}/js/boxueshe/appraise.js?v=ipandatcm_1.3"></script>