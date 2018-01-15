<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>

<div class="page-header">
	当前位置：权限管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 资源管理 </span>
</div>

<h4></h4>
<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx">
			<i class="glyphicon glyphicon-plus"></i> 新增
		</button>
		<button class="btn btn-sm btn-success dele_bx">
			<i class="glyphicon glyphicon-trash"></i> 批量删除
		</button>
	</p>
	<div class="searchDivClass" id="searchDiv">
		<div class="profile-info-row">
			<table frame=void >
				<tr>
					<td>
						<div class="profile-info-value searchTr">
							<select class="propertyValue1" id="seachValidId">
								<option value="">状态</option>
								<option value="true">启用</option>
								<option value="false">禁用</option>
							</select>
						</div>
					</td>
					<td>
						<div class="profile-info-value searchTr">
							<select class="propertyValue1" id="seachResourceTypeId">
								<option value="">资源类型</option>
								<c:forEach var="resType" items="${resourceTypes}">
									<option value="${resType.value}">${resType.description}</option>
								</c:forEach>
							</select> <input type="hidden" value="type" class="propertyName" />
							<button class="btn btn-sm  btn-primary" style="top: -2px;"
								onclick="search()">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<%@ include file="../common/treegrid.jsp"%>

<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<!-- 增加 -->
<div id="dialogResourceDiv"></div>
<div id="resourceDialog" class="hide">
	<form action="" method="post" class="form-horizontal" role="form"
		id="resource-form">
		<input type="hidden" name="id" id="id"> <input type="hidden"
			name="parentId" id="parentId">
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"><i
				class="text-danger">*</i> 名称: </label>
			<div class="col-sm-9">
				<input type="text" name="name" id="name" maxlength="20"
					class="col-xs-10 col-sm-8 {required:true,maxlength:20,minlength:2}">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"><i
				class="text-danger">*</i> 资源类型: </label>
			<div class="col-sm-9">
				<select class="col-sm-8" name="type" id="type">
					<c:forEach var="resType" items="${resourceTypes}">
						<option value="${resType.value}">${resType.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"><i
				class="text-danger">*</i> 权限代码: </label>
			<div class="col-sm-9">
				<input type="text" name="permission" id="permission" maxlength="64"
					class="col-xs-10 col-sm-8 {required:true,maxlength:64,minlength:2}">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"> 资源地址:
			</label>
			<div class="col-sm-9">
				<input type="text" name="url" id="url" maxlength="100"
					class="col-xs-10 col-sm-8">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"> 图标: </label>
			<div class="col-sm-9">
				<input type="text" name="icon" maxlength="30" id="icon"
					class="col-xs-10 col-sm-8">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"><i
				class="text-danger">*</i> 排序: </label>
			<div class="col-sm-9">
				<input type="number" name="displayOrder" id="displayOrder" value="0"
					min="0" class="col-xs-10 col-sm-2" />
			</div>
		</div>

		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right">说明: </label>
			<div class="col-sm-9">
				<textarea class="col-xs-10 col-sm-8 {maxlength:250}"
					name="description" id="description" maxlength="250"></textarea>
			</div>
		</div>

		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"> 状态: </label>
			<div class="col-sm-9">
				<p style="padding-top: 6px">
					<label style="cursor: pointer;"><input style="cursor: pointer;" type="radio" id="delete_false" name="delete" value="0" class="aa" checked>启用</label>
					 &nbsp;&nbsp;&nbsp; 
					<label style="cursor: pointer;"><input style="cursor: pointer;" type="radio" id="delete_true" name="delete" value="1">禁用</label>
				</p>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript" src="${base}/js/user/resource.js?v=ipandatcm_1.3"></script>