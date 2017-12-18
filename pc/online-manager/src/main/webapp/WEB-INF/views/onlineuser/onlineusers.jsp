<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/jstl_taglib.jsp"%>

<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,function(){});
	} catch (e) {}
</script>
<script src="${base}/js/layer/layer.js"></script>
<link href="${base}/js/layer/skin/layer.css" type="text/css" />
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header">
		当前位置：用户管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
		</small>
		<span>注册用户管理 </span>
</div>

<h4></h4>

<div class="mainrighttab tabresourse bordernone">
	<div style="padding-bottom:10px; float: right;" id="searchDiv" >
		<table>
			<tr>
				<td >
					<span class="searchTr">
						<input type="text"  id="searchName" class="propertyValue1" style="width:240px;" placeholder="用户昵称/注册用户名/qq号/邮箱"/>
						<input type="hidden" value="searchName" class="propertyName"/>
					</span>
					&nbsp;&nbsp;
					<span class="searchTr">
						<input type="text"  id="lastLoginIp" class="propertyValue1" style="width:100px;" placeholder="最后登录IP"/>
						<input type="hidden" value="lastLoginIp" class="propertyName"/>
					</span>
					&nbsp;&nbsp;
					<span class="searchTr">
						<input type="text"  id="createTimeStart" class="propertyValue1" placeholder="注册时间"/>
						<input type="hidden" value="createTimeStart" class="propertyName"/>
					</span>
					<!-- --
					 <span class="searchTr">
						<input type="text"  id="createTimeEnd" class="propertyValue1" placeholder="创建时间结束"/>
						<input type="hidden" value="createTimeEnd" class="propertyName"/>
					</span> -->
					&nbsp;&nbsp;
					<span class="searchTr">
						<input type="text"  id="lastLoginTimeStart" class="propertyValue1" placeholder="最后登录时间"/>
						<input type="hidden" value="lastLoginTimeStart" class="propertyName"/>
					</span>
					<!-- --
					 <span class="searchTr">
						<input type="text"  id="lastLoginTimeEnd" class="propertyValue1" placeholder="最后登录时间结束"/>
						<input type="hidden" value="lastLoginTimeEnd" class="propertyName"/>
					</span>--> 
					&nbsp;&nbsp;
				</td>
				<td>
					<span class="searchTr">
						<select class="propertyValue1" id="status">
							<option value="">启/禁用状态</option>
							<option value="0">已启用</option>
							<option value="-1">已禁用</option>
						</select>
						<input type="hidden" value="status" class="propertyName"/>
					</span>
					
					<span class="searchTr">
						 <select id="menuIdSearch" class="propertyValue1" >
                                <option  value="" >讲师权限状态</option>
                                <option value="0">无权限</option>
                                <option value="1">有权限</option>
                         </select>
                         <input type="hidden" value="lstatus" class="propertyName"/>
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
			<table id="onlineuserTable" class="table table-striped table-bordered table-hover"></table>
		</div>
	</div>
</div>
 
 <!-- 增加form -->
<div id="dialogMenuDiv"></div>
<div id="menuDialog" class="hide">
  <form action="" method="post" class="form-horizontal" role="form" id="menu-form">
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 所属学科: </label>
          <div class="col-sm-9">
           	  <input type="hidden"  name="id" id="id"/>
              <select name="menuId"   id="menuId"   class="propertyValue1 col-xs-8 {required:true}">
               	<option value="-1" select="selected">无权限</option>
                  <c:forEach var="m" items="${menus}" >
                      <option value="${m.id}">${m.name}</option>
                  </c:forEach>
              </select>
          </div>
      </div>
  </form>
</div>
 
    
<!-- 查看 -->
<div id="dialogUserInfoDiv"></div>
<div id="userInfoDialog" class="hide">
	<form action="" class="form-horizontal autowrap">
	    <div class="form-group">
			<label class="col-sm-2 control-label no-padding-right">学员姓名: </label>
	        <div class="col-sm-4"><p id="name_look" class="paddingtop7px padding7"></p></div>
			<label class="col-sm-2 control-label no-padding-right">性别: </label>
	        <div class="col-sm-4"><p id="sex_look" class="paddingtop7px padding7"></p></div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right">用户账号: </label>
	        <div class="col-sm-4"><p id="account_look" class="paddingtop7px padding7"></p></div>
			<label class="col-sm-2 control-label no-padding-right">充值余额: </label>
	        <div class="col-sm-4"><p id="balance_look" class="paddingtop7px padding7"></p></div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right">赠送余额: </label>
	        <div class="col-sm-4"><p id="balanceGive_look" class="paddingtop7px padding7"></p></div>
			<label class="col-sm-2 control-label no-padding-right">微吼账号: </label>
	        <div class="col-sm-4"><p id="vhallId_look" class="paddingtop7px padding7"></p></div>
		</div>
		
		
		
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right">最后登录时间: </label>
	        <div class="col-sm-4"><p id="last_time_look" class="paddingtop7px padding7"></p></div>
			<label class="col-sm-2 control-label no-padding-right">注册时间: </label>
	        <div class="col-sm-4"><p id="regis__time_look" class="paddingtop7px padding7"></p></div>
		</div>
		
		<div class="clean"></div>
		<div class="form-group">
	        <label class="col-sm-2 control-label no-padding-right">手机号: </label>
	        <div class="col-sm-4"><p id="mobile_look" class="paddingtop7px padding7"></p></div>
	        <label class="col-sm-2 control-label no-padding-right">QQ号: </label>
	        <div class="col-sm-4"><p id="qq_look" class="paddingtop7px padding7"></p></div>
	    </div>
		<div class="space-4"></div>
		<div class="form-group">
	        <label class="col-sm-2 control-label no-padding-right">邮箱: </label>
	        <div class="col-sm-4"><p id="email_look" class="paddingtop7px padding7 autowrap"></p></div>
	        <label class="col-sm-2 control-label no-padding-right">登陆IP: </label>
	        <div class="col-sm-4"><p id="ip_look" class="paddingtop7px padding7 autowrap"></p></div>
		</div>
		<div class="space-4"></div>
		<div class="form-group">
	        <label class="col-sm-2 control-label no-padding-right">访问次数: </label>
	        <div class="col-sm-4"><p id="visitSum_look" class="paddingtop7px padding7 autowrap"></p></div>
	        
	        <label class="col-sm-2 control-label no-padding-right"> 房间号: </label>
	        <div class="col-sm-4"><p id="view_room_number" class="hg paddingtop7px padding7"></p></div>
		</div>
		<div class="space-4"></div>
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right"> 报名记录: </label>
	        <div class="col-sm-4"><p id="gradeName_look" class="hg paddingtop7px padding7"></p></div>
	        
		</div>
    </form>
</div>
<!-- 查看 -->
<div id="viewRoomNumber"></div>
<div id="userRoomNumberDialog" class="hide">
	<form action="" class="form-horizontal autowrap">
	    <!-- <div class="form-group">
			<label class="col-sm-2 control-label no-padding-right">房间号: </label>
	        <div class="col-sm-4"><p id="room_number" class="paddingtop7px padding7"></p></div>
		</div> -->
		<div class="txtBox"><p id="room_number"></p></div>
    </form>
</div>
<script type="text/javascript" src="${base}/js/onlineuser/onlineuser.js?v=4.0"></script>