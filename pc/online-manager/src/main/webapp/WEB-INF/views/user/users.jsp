<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>

<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<div class="page-header">
	当前位置：用户管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 后台用户管理 </span>
</div>

<h4></h4>

<div class="mainrighttab tabresourse bordernone">
	<p class="col-sm-4 col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" title="新增用户">
			<i class="glyphicon glyphicon-plus"></i> 新增用户
		</button>
		<button class="btn btn-sm btn-success dele_bx" title="批量删除">
			<i class="glyphicon glyphicon-trash"></i> 批量删除
		</button>
		<button class="btn btn-sm btn-success user" title="赋予角色">赋予角色</button>

	</p>
	<div class="searchDivClass" id="searchDiv">
	<div class="profile-info-row" >
		<table frame=void >
			<tr>
				<td>
					<div class="profile-info-value searchTr">
						<input type="text" id="searchName" class="propertyValue1"
							placeholder="请输入姓名/登录名" /> <input type="hidden"
							value="searchName" class="propertyName" />
					</div>
				</td>
				<td>
					<div class="profile-info-value searchTr">
						<select class="propertyValue1" id="">
							<option value="">状态</option>
							<option value="0">启用</option>
							<option value="1">禁用</option>
						</select> <input type="hidden" value="is_delete" class="propertyName" />
					</div>
				</td>
				<td>
					<span class="searchTr">
						<select class="propertyValue1" id=""   style="margin-top: 1px;">
							<option value="">所属角色</option>
							<c:forEach var="role" items="${roles}" varStatus="status">
								<option value="${role.id}">${role.name}</option>
							</c:forEach>
						</select> <input type="hidden" value="roleId" class="propertyName" />
					</span>
					<button id="searchBtn" type="button" style="margin-bottom: 1px;"
							class="btn btn-sm  btn-primary " onclick="search();">
							<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
					<!-- <button class="btn btn-sm  btn-primary" onclick="reset();" title="重置" 
						style="height: 28px;line-height: 12px;">重置</button> -->
				</td>
			</tr>
		</table>
	</div>

	<div class="row">
		<div class="col-xs-12">
			<table id="teacherTable"
				class="table table-striped table-bordered table-hover"></table>
		</div>
	</div>
</div>



<!-- 增加form -->
<div id="dialogTeacherDiv"></div>
<div id="teacherDialog" class="hide">
	<form action="" method="post" class="form-horizontal" role="form"
		id="teacher-form" style="margin-left: 45px">
		<input type="hidden" name="id" id="id">
		<div class="form-group" style="margin-top: 18px;">
			<br /> <label class="col-sm-1 control-label no-padding-right"><i
				class="text-danger">*</i> 姓名: </label>
			<div class="col-sm-4">
				<input type="text" name="name" id="name"
					class="col-xs-10 col-sm-12 {required:true,maxlength:18,minlength:2}">
			</div>
			<label class="col-sm-2 control-label no-padding-right"><i
				class="text-danger">*</i> 登录名: </label>
			<div class="col-sm-4">
				<input type="text" name="loginName" id="loginName"
					class="col-xs-10 col-sm-12 {required:true,ENNUM:[],maxlength:18,minlength:2}">
			</div>
		</div>

		<%--<div class="space-4"></div>--%>
		<%--<div class="form-group" style="margin-top: 18px;">--%>
			<%--<label class="col-sm-1 control-label no-padding-right"><i--%>
				<%--class="text-danger">*</i> 手机: </label>--%>
			<%--<div class="col-sm-4">--%>
				<%--<input type="text" name="mobile" id="mobile"--%>
					<%--class="col-xs-10 col-sm-12 {required:true,phone:[]}">--%>
			<%--</div>--%>
			<%--<label class="col-sm-2 control-label no-padding-right">身份证: </label>--%>
			<%--<div class="col-sm-4">--%>
				<%--<input type="text" name="identity" id="identity" maxlength="18"--%>
					<%--class="col-xs-10 col-sm-12 {cardNo:[]}">--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right">邮箱: </label>
			<div class="col-sm-4">
				<input type="text" name="email" id="email" maxlength="32"
					class="col-xs-10 col-sm-12 {email:true}">
			</div>
			<label class="col-sm-2 control-label no-padding-right">QQ: </label>
			<div class="col-sm-4">
				<input type="text" name="qq" id="qq"
					class="col-xs-10 col-sm-12 {qq:[]}" maxlength="12" />
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right">密码: </label>
			<div class="col-sm-4">
				<input type="password" name="password" id="password" maxlength="18"
					class="col-xs-10 col-sm-12 {ENNUMUNDERLINE:[]}">
			</div>
			<%--<label class="col-sm-2 control-label no-padding-right">学历: </label>--%>
			<%--<div class="col-sm-4">--%>
				<%--<select class="col-sm-12" name="education" id="education">--%>
					<%--<option value="">全部</option>--%>
					<%--<c:forEach var="education" items="${educations}" varStatus="status">--%>
						<%--<option value="${education.value}">${education.value}</option>--%>
					<%--</c:forEach>--%>
				<%--</select>--%>
			<%--</div>--%>
			<label class="col-sm-2 control-label no-padding-right"> 状态: </label>
			<div class="col-sm-4">
				<p style="padding-top: 5px">
					<label style="cursor: pointer;"><input class="checkbox-align" type="radio" id="delete_false"name="delete" value="0" class="aa" checked style="cursor: pointer;">启用</label>
					&nbsp;&nbsp;&nbsp;
					<label style="cursor: pointer;"><input class="checkbox-align" type="radio" id="delete_true" name="delete" value="1" style="cursor: pointer;">禁用</label>
				</p>
			</div>
		</div>
		<%--<div class="space-4"></div>--%>
		<%--<div class="form-group" style="margin-top: 18px;">--%>
			<%--<label class="col-sm-1 control-label no-padding-right"> 状态: </label>--%>
			<%--<div class="col-sm-10">--%>
				<%--<p style="padding-top: 5px">--%>
					<%--<label style="cursor: pointer;"><input class="checkbox-align" type="radio" id="delete_false"name="delete" value="0" class="aa" checked style="cursor: pointer;">启用</label>--%>
					<%--&nbsp;&nbsp;&nbsp;--%>
					<%--<label style="cursor: pointer;"><input class="checkbox-align" type="radio" id="delete_true" name="delete" value="1" style="cursor: pointer;">禁用</label>--%>
				<%--</p>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-1 control-label no-padding-right"> 备注: </label>
			<div class="col-sm-10">
				<textarea class="col-sm-12" name="description" id="description"
					maxlength="255"></textarea>
			</div>
		</div>

	</form>
</div>

<!-- 查看 -->
<div id="dialogUserInfoDiv"></div>
<div id="userInfoDialog" class="hide">
	<form action="" class="form-horizontal autowrap">
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right">姓名: </label>
			<div class="col-sm-4">
				<p id="uiName" class="paddingtop7px padding7"></p>
			</div>
			<label class="col-sm-2 control-label no-padding-right">登录名: </label>
			<div class="col-sm-4">
				<p id="uiLoginName" class="hg paddingtop7px padding7"></p>
			</div>
		</div>

		<div class="clean"></div>
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right">手机: </label>
			<div class="col-sm-4">
				<p id="uiMobile" class="paddingtop7px padding7"></p>
			</div>
			<label class="col-sm-2 control-label no-padding-right">身份证: </label>
			<div class="col-sm-4">
				<p id="uiIdentity" class="hg paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right">邮箱: </label>
			<div class="col-sm-4">
				<p id="uiEmail" class="paddingtop7px padding7 autowrap"></p>
			</div>
			<label class="col-sm-2 control-label no-padding-right">QQ: </label>
			<div class="col-sm-4">
				<p id="uiqq" class="hg paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="cleanfix"></div>
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right">学历: </label>
			<div class="col-sm-4">
				<p id="uiEducation" class="paddingtop7px padding7"></p>
			</div>
			<label class="col-sm-2 control-label no-padding-right"> 状态: </label>
			<div class="col-sm-4">
				<p id="uiDelete" class="hg paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right"> 备注: </label>
			<div class="col-sm-11">
				<p id="uiDescription" class="hg paddingtop7px padding7"></p>
			</div>
		</div>

		<!-- <div class="form-group">
           <label class="col-sm-5"></label> 
        <div class="col-sm-2">  <a class="btn btn-default" id="close"> 关闭 </a>
        </div>
    </div> -->

	</form>
</div>

<!--重置密码-->
<div id="dialogResetPasswordDiv"></div>
<div id="resetPasswordDialog" class="hide">
	<div class="form-group">
		<div id="dialog-confirm" class="ui-dialog-content ui-widget-content"
			style="width: auto; min-height: 27px; max-height: none; height: auto;">
			<div class="alert alert-info bigger-110">请谨慎操作！</div>
			<div class="space-6"></div>
			<p class="bigger-110 bolder center grey">
				<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i> <span
					id="confirm_content">确定要把该用户密码重置成123456吗?</span>
			</p>
		</div>
	</div>
</div>

<!--赋角色-->
<div id="dialogRoleDiv"></div>
<div id="roleDialog" class="hide">
	<form action="/user/edit/role" method="post" class="form-horizontal"
		role="form" id="role-form">
		<input type="hidden" name="userIds" id="userIds">
		<c:forEach var="role" items="${roles}" varStatus="status">
			<c:if test="${status.index % 3 == 0}">
				<div class="form-group">
					<div class="col-sm-1"></div>
			</c:if>
			<div class="col-sm-3">
				<label> <input type="checkbox" name="roleIds"
					value="${role.id}" class="ace" /> <span class="lbl"></span>${role.name}
				</label>
			</div>
			<c:if test="${(status.index+1) % 3 == 0}">
</div>
</c:if>
</c:forEach>
</form>
</div>

<script type="text/javascript" src="${base}/js/user/user.js?v=ipandatcm_1.3"></script>