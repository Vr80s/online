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
  当前位置：销售策略管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 销售策略数据</span>
</div>

<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void >
            	<tr>
            		<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_name" class="propertyValue1"  placeholder = "活动名称关键字"/>
                            <input type="hidden" value="search_name" class="propertyName"/>
                        </div>
                    </td> 
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="propertyValue1 datetime-picker" id="search_ruleStartTime"  placeholder = "开始日期" readonly="readonly"/>
                            <input type="hidden" value="search_ruleStartTime" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            	至
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="propertyValue1 datetime-picker"  id="search_ruleEndTime"  placeholder = "结束日期" readonly="readonly"/>
                            <input type="hidden" value="search_ruleEndTime" class="propertyName"/>
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
			<table id="activityDetailTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div> 
<!-- 各规则数据 -->
<div id="dialogShowActivityDetailDiv"></div>
<div id="showActivityDetailDialog" class="hide">
    <div class="tab-content">
    	<table class="table table-bordered" border="0" id="showCoursePreferenty">
			 
		</table>
		
    </div><span style="position:absolute;bottom:10px;right:10px;color:red;top:2px;">数据截止至：<span id="dataTime"></span></span>
</div>
<!-- 课程详细数据 -->
<div id="dialogShowCourseActivityDetailDiv"></div>
<div id="showCourseActivityDetailDialog" class="hide">
    <div class="tab-content">
    	<table class="table table-bordered" border="0" id="showCourseDetailPreferenty">
			 
		</table>
		
    </div>
    <span style="position:absolute;bottom:10px;right:10px;color:red;top:2px;">数据截止至：<span id="updateDataTime"></span></span>
</div>
<script type="text/javascript" src="${base}/js/activity/activityDetail.js?v=ipandatcm_1.3"></script>