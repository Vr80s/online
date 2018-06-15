<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="/bootstrap/assets/css/bootstrap-select.css" />
<script type="text/javascript" src="/bootstrap/assets/js/bootstrap-select.js"></script>
<script type="text/javascript" src="/bootstrap/assets/js/defaults-zh_CN.js"></script>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
		
	}
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：销售策略管理 	<small><i class="ace-icon fa fa-angle-double-right"></i></small>
  <span> 课程兑换码管理</span><small><i class="ace-icon fa fa-angle-double-right"></i></small>
  <span> 兑换码使用详情</span>
  <button class="btn btn-sm btn-success add_bx" id="returnbutton" onclick="javascript:history.back(-1);" style="float:right;margin-top:-13px" >
		<i class="glyphicon glyphicon-arrow-left"></i> 返回上级
  </button>
</div>

<!-- <div class="mainrighttab tabresourse bordernone"
	style="text-align: right; width: 100%;">
	<button class="btn btn-sm btn-success add_bx" id="returnbutton">
		<i class="glyphicon glyphicon-arrow-left"></i> 返回上级
	</button>
</div>  -->
<!-- F码列兑换码使用详情表 -->
<div class="mainrighttab tabresourse bordernone">
  <p class="col-xs-4 col-md-5 " style="margin-top:5px">
  	当前兑换活动名称：${activityName} 已生成：${createFcodeSum}   已兑换：${lockFcodeSum}   已使用：${useFcodeSum}
    <!--<button class="btn btn-sm btn-success add_bx" title="新增兑换活动"><i class="glyphicon glyphicon-plus"></i> 新增兑换活动</button>
    <button class="btn btn-sm btn-success deletes_bx" title="批量删除"  onclick="deleteBatch();" ><i class="glyphicon glyphicon-trash"></i> 批量删除</button> -->
  </p>
    <div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
			<input type="hidden" value="${lotNo}" id="lotNo">
            <table frame=void >
                 <tr>
                 	<td>
                       <div class="profile-info-value searchTr">
                            <select name="search_status" id="search_status" value="" class="propertyValue1" >
	               			<option value="">兑换码状态</option>
	                        <option value="0">未发放</option>
	                        <option value="1">已发放</option>
	                        <option value="2">已兑换</option>
	                        <option value="3">已使用</option>
	                        <option value="4">已作废</option>
	               		 </select>
                         <input type="hidden" value="search_status" class="propertyName"/>
                       </div>
                    </td>
                    <!-- <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder = "兑换码/兑换课程名称/使用人(用户名)/使用人(帐号)" class="propertyValue1" id="NameSearch" style="width: 170px;">
                            <input type="hidden" value="NameSearch" class="propertyName"/>
                        </div>
                    </td> -->
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder = "兑换码/兑换课程名称/使用人(用户名)/使用人(帐号)" class="propertyValue1" id="NameSearch" style="width: 300px;">
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
<script type="text/javascript" src="/js/fcode/fcodeDetail.js?v=ipandatcm_1.3"></script>