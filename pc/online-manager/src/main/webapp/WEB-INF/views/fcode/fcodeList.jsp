<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/bootstrap/assets/css/bootstrap-select.css" />
<script type="text/javascript" src="${base}/bootstrap/assets/js/bootstrap-select.js"></script>
<script type="text/javascript" src="${base}/bootstrap/assets/js/defaults-zh_CN.js"></script>
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
<div class="page-header">
  当前位置：销售策略管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 课程兑换码管理</span>
</div>

<!-- F码列表 -->
<div class="mainrighttab tabresourse bordernone">
  <p class="col-xs-4 col-md-2 " style="padding:0px">
     <button class="btn btn-sm btn-success add_bx" title="新增兑换活动"><i class="glyphicon glyphicon-plus"></i> 新增兑换活动</button>
    <!--<button class="btn btn-sm btn-success deletes_bx" title="批量删除"  onclick="deleteBatch();" ><i class="glyphicon glyphicon-trash"></i> 批量删除</button> -->
  </p>
    <div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void >
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder = "兑换活动名称/批次号/创建人" class="propertyValue1" id="NameSearch" style="width: 170px;">
                            <input type="hidden" value="NameSearch" class="propertyName"/>
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
      <table id="fcodeTable" class="table table-striped table-bordered table-hover"></table>
    </div>
  </div>
</div>
<!-- 增加form -->
<div id="dialogaddFcodeDiv"></div>
<div id="addFcodeDialog" class="hide">
	  <form action="" method="post" class="form-horizontal" role="form" id="fcode-form">
	  	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换码批次: </label>
	         <div class="col-md-9">
	         <input type="text" name="lotNo" id="lotNo"  class="col-md-11 {required:true,digits:true}">
	         </div>
     	 </div>
     	  <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换活动名称: </label>
	         <div class="col-md-9">
	         <input type="text" name="name" id="name"  class="col-md-11 {required:true}">
	         </div>
     	 </div>
     	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换码数量: </label>
	         <div class="col-md-9">
	         	<input type="text" name="fcodeSum" id="fcodeSum" >
	         	<span style="margin-top:20px;">
	          	<input type="checkbox" name="auto" checked="checked" value="1"  id="auto" >
	          	</span>
	          	<label >自动规则<i class="text-danger"> (根据具体活动确认数量)</i> </label>
	         </div>
     	 </div>
     	 <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="subjectIds"><font color="red">*</font>课程范围: </label>
			 <div class="col-md-9" style="margin-left: -47px;">
			 	<select name="subjectIds" id="add_subjectIds" class="col-md-5 selectpicker  {required:true}" multiple data-live-search="true" style="padding: 0px;">
						<!-- <option value="-1">全部班级</option> -->
					<c:forEach var="menu" items="${menuVo}">
                        <option value="${menu.id}">${menu.name}</option>
                    </c:forEach>
				</select>
				<span class="col-md-1"></span>
			 	<select name="includeCourseIds" id="add_microCourseIds" class="col-md-5 selectpicker {required:true}" multiple data-live-search="true" style="padding: 0px;">
				</select>
			</div>	 
		</div>
		 <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-4 control-label no-padding-right" for="subjectIds"></label>
			 <div class="col-md-8" style="margin-left: -47px;">
			 	<span id="spanCount1" class="col-md-5"></span>
			 	<span id="spanCount2" class="col-md-5"></span>
			</div>	 
		</div>
		<div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 活动开始时间: </label>
	         <div class="col-md-9">
	         <input type="text" name="startTime" id="startTime" class="datetime-picker col-md-11 {required:true}">
	         </div>
     	 </div>
     	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 活动结束时间: </label>
	         <div class="col-md-9">
	         <input type="text" name="endTime" id="endTime"  class="datetime-picker col-md-11 {required:true}">
	         </div>
     	 </div>
     	  <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"> 兑换码有效期: </label>
	         <div class="col-md-9">
	         <input type="text" readonly="readonly" onclick="getFCodeEndTime()" id="fCodeEndTime"  class=" col-md-11">
	         </div>
     	 </div>
	  </form>
</div>

<!-- 查看 -->
<div id="dialogViewFcodeDiv"></div>
<div id="viewFcodeDialog" class="hide">
	  <form action="" method="post" class="form-horizontal" role="form" id="viewfcode-form">
	  	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换码批次: </label>
	         <div class="col-md-9">
	         <input type="text" readonly="readonly" id="view_lotNo"  class="col-md-11">
	         </div>
     	 </div>
     	  <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换活动名称: </label>
	         <div class="col-md-9">
	         <input type="text" id="view_name" readonly="readonly"  class="col-md-11 ">
	         </div>
     	 </div>
     	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换码数量: </label>
	         <div class="col-md-9">
	         	<input readonly="readonly" type="text" id="view_fcodeSum" >
	         	<span style="margin-top:20px;">
	          	<input disabled="disabled" type="checkbox" id="view_auto" >
	          	</span>
	          	<label >自动规则<i class="text-danger"> (根据具体活动确认数量)</i> </label>
	         </div>
     	 </div>
     	 <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 学科范围: </label>
	         <div class="col-md-9">
	         <input type="text" readonly="readonly" id="view_subjectIds"  class="col-md-11 ">
	         </div>
		</div>
		 <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 课程范围: </label>
	         <div class="col-md-9">
	         <input type="text" readonly="readonly" id="view_microCourseIds"  class="col-md-11 ">
	         </div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 活动开始时间: </label>
	         <div class="col-md-9">
	         <input type="text" readonly="readonly" id="view_startTime"  class=" col-md-11">
	         </div>
     	 </div>
     	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 活动结束时间: </label>
	         <div class="col-md-9">
	         <input type="text" readonly="readonly" id="view_endTime"  class=" col-md-11">
	         </div>
     	 </div>
     	  <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right">兑换码有效期: </label>
	         <div class="col-md-9">
	         <input type="text" readonly="readonly" id="view_fCodeEndTime"  class=" col-md-11">
	         </div>
     	 </div>
	  </form>
</div>

<!-- 修改form -->
<div id="dialogEditFcodeDiv"></div>
<div id="editFcodeDialog" class="hide">
	  <form action="" method="post" class="form-horizontal" role="form" id="editfcode-form">
	  	<input type="hidden" name="id" id="edit_fcodeId" >
	  	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换码批次: </label>
	         <div class="col-md-9">
	         <input type="text" name="lotNo" disabled="disabled" id="edit_lotNo"  class="col-md-11 {required:true,digits:true}">
	         </div>
     	 </div>
     	  <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换活动名称: </label>
	         <div class="col-md-9">
	         <input type="text" name="name" id="edit_name"  class="col-md-11 {required:true}">
	         </div>
     	 </div>
     	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 兑换码数量: </label>
	         <div class="col-md-9">
	         	<input type="text" disabled="disabled" name="fcodeSum" id="edit_fcodeSum" >
	         	<span style="margin-top:20px;">
	          	<input type="checkbox" disabled="disabled" name="auto" value="1"  id="edit_auto" >
	          	</span>
	          	<label >自动规则<i class="text-danger"> (根据具体活动确认数量)</i> </label>
	         </div>
     	 </div>
     	 
     	 <div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-3 control-label no-padding-right" for="subjectIds"><font color="red">*</font>课程范围: </label>
			 <div class="col-md-9" style="margin-left: -47px;">
			 	<select name="subjectIds" id="edit_subjectIds" class="col-md-5 selectpicker  {required:true}" multiple data-live-search="true" style="padding: 0px;">
						<!-- <option value="-1">全部班级</option> -->
					<c:forEach var="menu" items="${menuVo}">
                        <option value="${menu.id}">${menu.name}</option>
                    </c:forEach>
				</select>
				<span class="col-md-1"></span>
			 	<select name="includeCourseIds" id="edit_microCourseIds" class="col-md-5 selectpicker {required:true}" multiple data-live-search="true" style="padding: 0px;">
				</select>
			</div>	 
		</div>
     	<div class="form-group"  style="margin-top: 18px;" >
			 <label class="col-sm-4 control-label no-padding-right" for="subjectIds"></label>
			 <div class="col-md-8" style="margin-left: -47px;">
			 	<span id="edit_spanCount1" class="col-md-5"></span>
			 	<span id="edit_spanCount2" class="col-md-5"></span>
			</div>	 
		</div>
		<div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 活动开始时间: </label>
	         <div class="col-md-9">
	         <input type="text" name="startTime" readonly="readonly" id="edit_startTime"  class="datetime-picker col-md-11 {required:true}">
	         </div>
     	 </div>
     	 <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"><i class="text-danger">*</i> 活动结束时间: </label>
	         <div class="col-md-9">
	         <input type="text" name="endTime" id="edit_endTime" readonly="readonly"  class="datetime-picker col-md-11 {required:true}">
	         </div>
     	 </div>
     	  <div class="form-group" style="margin-top: 18px;">
	         <label class="col-md-3 control-label no-padding-right"> 兑换码有效期: </label>
	         <div class="col-md-9">
	         <input type="text" readonly="readonly" onclick="edit_getFCodeEndTime()" id="edit_fCodeEndTime"  class=" col-md-11">
	         </div>
     	 </div>
	  </form>
</div>
<script type="text/javascript" src="${base}/js/fcode/fcode.js?v=1.7"></script>