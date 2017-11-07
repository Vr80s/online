<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<link rel="stylesheet" type="text/css" href="${base}/bootstrap/assets/css/bootstrap-select.css" />
<style type="text/css">
.bs-select-all.btn, .bs-deselect-all.btn, .dropdown-toggle.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 10px  !important;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent !important;
    border-radius: 4px;
    text-shadow: 0 0px 0 rgba(0, 0, 0, 0.25) !important;
}

.bs-select-all.btn-default, .bs-deselect-all.btn-default, .dropdown-toggle.btn-default{
    color: #333 !important;
    background-color: #fff !important;
    border-color: #ccc !important;
}

.bs-actionsbox .btn-group button {
    width: 48.6% !important;
}

</style>
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
<script type="text/javascript" src="${base}/bootstrap/assets/js/bootstrap-select.js"></script>
<script type="text/javascript" src="${base}/bootstrap/assets/js/defaults-zh_CN.js"></script>
<div class="page-header">
  当前位置：销售策略管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 满减优惠管理 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" title="新增满减规则">
			<i class="glyphicon glyphicon-plus"></i> 新增满减规则
		</button>
	</p>
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void style="width: 100%">
                <tr>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_name" class="propertyValue1"  placeholder = "活动名称关键字" maxlength="30"/>
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
			<table id="activityRuleTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 增加form -->
<div id="dialogAddActivityRuleDiv"></div>
<div id="addActivityRuleDialog" class="hide">
	<form id="addActivityRule-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="name"><font color="red">*</font>活动名称: </label>
			 <div class="col-sm-6">
			 	  <input type="text" name="name"  id="add_name" maxlength="100" class="col-sm-12 {required:true}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>活动落地页地址: </label>
			 <div class="col-sm-6">
			 	 <input type="text" name="url"  id="add_url" maxlength="100" class="col-sm-12 {required:true,maxlength:100}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="subjectIds"><font color="red">*</font>课程范围: </label>
			 <div class="col-sm-3">
			 	 <select name="subjectIds" id="add_subjectIds" class="selectpicker  {required:true}" multiple data-live-search="true" style="padding: 0px;">
						<!-- <option value="-1">全部班级</option> -->
					<c:forEach var="menu" items="${menuVo}">
                        <option value="${menu.id}">${menu.name}</option>
                    </c:forEach>
				</select>
             </div>
			 <div class="col-sm-3">
			 	 <select name="microCourseIds" id="add_microCourseIds" class="selectpicker {required:true,greaterThanFour:true}" multiple data-live-search="true" style="padding: 0px;">
				 </select>
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgHref"><font color="red">*</font>规则生效时间: </label>
			 <div class="col-sm-3">
               		<input type="text" name="startTime"  id="add_startTime" readonly="readonly" class="datetime-picker col-sm-12 {required:true}">
			 </div>
			 <div class="col-sm-1" style="margin-top: 7px;margin-left: -18px;margin-right: -39px;">
               		至
             </div>
			 <div class="col-sm-3">
               		<input type="text" name="endTime"  id="add_endTime" readonly="readonly"  class="datetime-picker col-sm-12 {required:true,greaterThanStrat:true}">
             </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgHref"><font color="red">*</font>规则内容:</label>
			 <div class="col-sm-6" style="margin-top: 7px;margin-right: -25px;">
               		<table style="margin-top: -21px;" id="ruleTable" border="0">
               			<tr style="height: 62px;">
               				<td>
               					 <div class="col-sm-1" style="margin-top: 7px;">
					               		满
					             </div>
								 <div class="col-sm-3">
					               		<input type="text" name="reachMoney"  class="reachMoney col-sm-12 {required:true,number:true,range:[0.00,999999.99],greaterThanReach:false,notRepeat:true}">
					             </div>
								 <div class="col-sm-1" style="width: 65px;margin-left: -11px;margin-top: 7px;margin-right: -14px;">
									元
									&nbsp;
									减
					             </div>
								 <div class="col-sm-3">
					               		<input type="text" name="minusMoney"  class="minusMoney col-sm-12 {required:true,number:true,range:[0.00,999999.99],greaterThanReach:true}">
					             </div>
								 <div class="col-sm-2" style="margin-top: 7px;margin-left: -12px;text-align: left">
					               		元
					             </div>
               				</td>
               			</tr>
               		</table>
             </div>
		</div>
		<div class="form-group" >
			 <label class="col-sm-3 control-label no-padding-right">
				 <button type="button" id="add_rule" class="btn btn-sm  btn-primary ">
		                 <i class="glyphicon glyphicon-plus"></i>
		         </button>
             </label>
		</div>
	</form>
</div>

<!-- 详情页面form -->
<div id="dialogShowActivityRuleDiv"></div>
<div id="showActivityRuleDialog" class="hide">
    <form class='form-horizontal'>
    	<input type="hidden" id="nouse" autofocus="autofocus">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 活动名称: </span></label>
            <div class="col-sm-9">
                <label class="paddingtop7px padding7"  id="show_name"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 活动落地页地址:</span> </label>
            <div class="col-sm-9">
                <label class="paddingtop7px padding7" id="show_url"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 课程范围: </span></label>
            <div class="col-sm-9">
                <label class="paddingtop7px padding7" id="show_courseNames"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 规则生效时间:</span> </label>
            <div class="col-sm-9">
                <label class="paddingtop7px padding7" id="show_time"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 规则内容:</span> </label>
            <div class="col-sm-9">
                <label class="paddingtop7px padding7" id="show_ruleContent"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 创建人:</span> </label>
            <div class="col-sm-9">
                <label class="paddingtop7px padding7" id="show_createPersonName"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" ><span style="font-weight: bold;"> <i class="text-danger">*</i> 创建时间:</span> </label>
            <div class="col-sm-9">
                <label class="paddingtop7px padding7" id="show_createTime"></label>
            </div>
        </div>
    </form>
</div>

<!-- 修改form -->
<div id="dialogUpdateActivityRuleDiv"></div>
<div id="updateActivityRuleDialog" class="hide">
	<form id="updateActivityRule-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="id" id="update_id">
		<input type="hidden" name="isEdit" id="isEdit">
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="name"><font color="red">*</font>活动名称: </label>
			 <div class="col-sm-6">
			 	  <input type="text" name="name"  id="update_name" maxlength="100" class="col-sm-12 {required:true}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>活动落地页地址: </label>
			 <div class="col-sm-6">
			 	 <input type="text" name="url"  id="update_url" maxlength="100" class="col-sm-12 {required:true,maxlength:100}">
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="subjectIds"><font color="red">*</font>课程范围: </label>
			 <div class="col-sm-3">
			 	 <select name="subjectIds" id="update_subjectIds" class="selectpicker  {required:true}" multiple data-live-search="true" style="padding: 0px;">
						<!-- <option value="-1">全部班级</option> -->
					<c:forEach var="menu" items="${menuVo}">
                        <option value="${menu.id}">${menu.name}</option>
                    </c:forEach>
				</select>
             </div>
			 <div class="col-sm-3">
			 	 <select name="microCourseIds" id="update_microCourseIds" class="selectpicker  {required:true,greaterThanFour:true}" multiple data-live-search="true" style="padding: 0px;">
				 </select>
             </div>
		</div>
	    <div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgHref"><font color="red">*</font>规则生效时间: </label>
			 <div class="col-sm-3">
               		<input type="text" name="startTime"  id="update_startTime" readonly="readonly" class="datetime-picker col-sm-12 {required:true}">
			 </div>
			 <div class="col-sm-1" style="margin-top: 7px;margin-left: -18px;margin-right: -39px;">
               		至
             </div>
			 <div class="col-sm-3">
               		<input type="text" name="endTime"  id="update_endTime" readonly="readonly"  class="datetime-picker col-sm-12 {required:true,greaterThanStrat:true}">
             </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="imgHref"><font color="red">*</font>规则内容:</label>
			 <div class="col-sm-6" style="margin-top: 7px;margin-right: -25px;">
               		<table style="margin-top: -21px;" id="update_ruleTable" border="0">
               			<tr style="height: 62px;">
               				<td>
               					 <div class="col-sm-1" style="margin-top: 7px;">
					               		满
					             </div>
								 <div class="col-sm-3">
					               		<input type="text" name="reachMoney"  class="reachMoney col-sm-12 {required:true,number:true,range:[0.00,999999.99],greaterThanReach:false,notRepeat:true}">
					             </div>
								 <div class="col-sm-1" style="width: 65px;margin-left: -11px;margin-top: 7px;margin-right: -14px;">
									元
									&nbsp;
									减
					             </div>
								 <div class="col-sm-3">
					               		<input type="text" name="minusMoney"  class="minusMoney col-sm-12 {required:true,number:true,range:[0.00,999999.99],greaterThanReach:true}">
					             </div>
								 <div class="col-sm-2" style="margin-top: 7px;margin-left: -12px;text-align: left">
					               		元
					             </div>
               				</td>
               			</tr>
               		</table>
             </div>
		</div>
		<div class="form-group" >
			 <label class="col-sm-3 control-label no-padding-right">
				 <button type="button" id="update_rule" class="btn btn-sm  btn-primary ">
		                 <i class="glyphicon glyphicon-plus"></i>
		         </button>
             </label>
		</div>
	</form>
</div>
<script type="text/javascript" src="${base}/js/activity/activityRule.js?v=1.7"></script>
