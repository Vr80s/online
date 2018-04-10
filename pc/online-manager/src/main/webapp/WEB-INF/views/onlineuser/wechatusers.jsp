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
		当前位置：用户管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
		</small>
		<span>微信关注用户管理 </span>
</div>

<h4></h4>

<div class="mainrighttab tabresourse bordernone">
	<div style="padding-bottom:10px; float: right;" id="searchDiv" >
		<table>
			<tr>
				<td >
					<span class="searchTr">
						<input type="text"  id="nickname" class="propertyValue1" style="width:240px;" placeholder="用户昵称/用户名"/>
						<input type="hidden" value="nickname" class="propertyName"/>
					</span>
					&nbsp;&nbsp;
					<span class="searchTr">
						<input type="text"  id="subscribeTimeStart" class="propertyValue1" placeholder="关注时间"/>
						<input type="hidden" value="subscribeTimeStart" class="propertyName"/>
					</span>
					&nbsp;&nbsp;
				   <span class="searchTr">
						<input type="text"  id="subscribeTimeEnd" class="propertyValue1" placeholder="关注时间"/>
						<input type="hidden" value="subscribeTimeEnd" class="propertyName"/>
					</span>
					&nbsp;&nbsp;
				</td>
				<td>
					<!-- 渠道名字  -->
					<span class="searchTr">
						 <select name="qr_scene"   id="qr_scene"   class="propertyValue1 col-xs-8 {required:true}">
			               	<option value="-1" select="selected">渠道名字</option>
			                 <c:forEach var="m" items="${wechatChannelVos}" >
			                      <option value="${m.id}">${m.name}</option>
			                  </c:forEach>
              		      </select>
                         <input type="hidden" value="qr_scene" class="propertyName"/>
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
			<label class="col-sm-2 control-label no-padding-right">微信昵称: </label>
	        <div class="col-sm-4"><p id="name_look" class="paddingtop7px padding7"></p></div>
			<label class="col-sm-2 control-label no-padding-right">性别: </label>
	        <div class="col-sm-4"><p id="sex_look" class="paddingtop7px padding7"></p></div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right">用户名: </label>
	        <div class="col-sm-4"><p id="account_look" class="paddingtop7px padding7"></p></div>
			<label class="col-sm-2 control-label no-padding-right">渠道名称: </label>
			<div class="col-sm-4"><p id="vhallId_look" class="paddingtop7px padding7"></p></div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right">所在城市: </label>
	        <div class="col-sm-4"><p id="balanceGive_look" class="paddingtop7px padding7"></p></div>
			<label class="col-sm-2 control-label no-padding-right">关注时间: </label>
			<div class="col-sm-4"><p id="balance_look" class="paddingtop7px padding7"></p></div>
		</div>
		
		
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right">最后更新时间: </label>
	        <div class="col-sm-4"><p id="last_time_look" class="paddingtop7px padding7"></p></div>
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
<script type="text/javascript" src="/js/onlineuser/wechatuser.js?v=ipandatcm_1.3"></script>