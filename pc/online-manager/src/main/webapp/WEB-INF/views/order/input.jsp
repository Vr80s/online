<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/jstl_taglib.jsp"%>

<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,function(){});
	} catch (e) {}
</script>
<script src="/js/layer/layer.js"></script>
<link href="/js/layer/skin/layer.css" type="text/css" />
<script src="/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header">
		当前位置：销售管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
		</small>
		<span>线下订单录入</span>
</div>

<h4></h4>

<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
	 	<button class="btn btn-sm btn-success add_bx" onclick="add();"><i class="glyphicon glyphicon-plus"></i>线下订单录入</button>      
	 	<button class="btn btn-sm btn-success add_bx" onclick="importOrders();"><i class="glyphicon glyphicon-plus"></i>批量导入</button>      
	</p>
	<div style="padding-bottom:10px; float: right;" id="searchDiv" >
		<table>
			<tr>
				<td>
					<span class="searchTr">
						<input type="text"  id="create_time_start" class="propertyValue1" placeholder="开始时间"/>
						<input type="hidden" value="create_time_start" class="propertyName"/>
					</span>
					&nbsp;&nbsp;
					<span class="searchTr">
						<input type="text"  id="create_time_end" class="propertyValue1" placeholder="结束时间"/>
						<input type="hidden" value="create_time_end" class="propertyName"/>
					</span>
					&nbsp;&nbsp;
				</td>
				<td>
					<span style="font-size:13px;">关键词</span>
					<span class="searchTr">
						<select class="propertyValue1" id="key_type">
							<option value="0">购买者</option>
							<option value="1">课程名称</option>
							<option value="2">创建人</option>
						</select>
						<input type="hidden" value="key_type" class="propertyName"/>
					</span>
					<span class="searchTr">
						<input type="text"  id="key_value" class="propertyValue1" placeholder="请输入关键词"/>
						<input type="hidden" value="key_value" class="propertyName"/>
					</span>
			       	<button id="searchBtn" type="button" style="margin-bottom: 1px;"
							class="btn btn-sm  btn-primary " onclick="search();">
							<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
				</td>
			</tr>
		</table>
    </div>
	<div class="row">
		<div class="col-xs-12">
			<table id="orderinputTable" class="table table-striped table-bordered table-hover"></table>
		</div>
	</div>
</div>
 
 <!-- 增加form -->
<div id="dialogOrderInputDiv"></div>
<div id="orderInputDialog" class="hide">
<form action="" method="post" class="form-horizontal" role="form" id="orderinputForm">
	<div class="form-group" style="margin-top: 20px;">
		<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="login_name"><font color="red">*</font>帐号</label>
		<div class="col-xs-12 col-sm-9">
			<div class="clearfix">
				<input class="form-control {required:true,maxlength:60}" name="login_name"
				 id="login_name" type="text" placeholder="请输入60位以内的手机号/邮箱" style="width:200px;"/>
			</div>
		</div>
	</div>
	<div class="form-group" style="margin-top: 20px;">
		<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="course_id"><font color="red">*</font>课程ID</label>
		<div class="col-xs-12 col-sm-9">
			<div class="clearfix">
				<input class="form-control {required:true,range:[1,99999],digits:true}" name="course_id"
				 id="course_id" type="text" placeholder="请输入5位以内课程id" style="width:200px;"/>
			</div>
		</div>
	</div>
	<div class="form-group" style="margin-top: 20px;">
		<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="class_id">班级ID</label>
		<div class="col-xs-12 col-sm-9">
			<div class="clearfix">
				<input class="form-control " name="class_id" value="0"
				 id="class_id" type="text" placeholder="请输入5位以内班级id" style="width:200px;"/>
			</div>
		</div>
	</div>
	<div class="form-group" style="margin-top: 20px;">
		<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="order_from"><font color="red">*</font>订单类型</label>
		<div class="col-xs-12 col-sm-9">
			<div class="clearfix">
				<select name="order_from" id="order_from" style="width:200px;">
					<option value="5">线下订单</option>
					<option value="6">工作人员</option>
					<option value="0">赠送</option>
				</select>
			</div>
		</div>
	</div>
</form>
</div>

<!-- 导入form -->
<div id="dialogOrderImportDiv"></div>
<div id="orderImportDialog" class="hide">
	<form action="" method="post" class="form-horizontal" role="form" id="orderImportForm" enctype="multipart/form-data">
		<p style="padding-top: 10px;"><a href="/template/importOrders.xlsx">下载模板</a></p>
		<div class="form-group" style="margin-top: 20px;">
			<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="excelFile"><font color="red">*</font>Excel：</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<input type="file" name="excelFile" id="excelFile" style="width:200px;"/>
				</div>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript" src="/js/order/input.js?v=ipandatcm_1.3"></script>