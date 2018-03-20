<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<link href="/js/layer/skin/layer.css" type="text/css" />	
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
	当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 师资管理 </span>
</div>
<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" title="新增老师">
			<i class="glyphicon glyphicon-plus"></i> 新增老师
		</button>
		<button class="btn btn-sm btn-success dele_bx" title="批量删除">
			<i class="glyphicon glyphicon-trash"></i> 批量删除
		</button>
	</p>
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void >
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder = "老师姓名" class="propertyValue1" id="search_name" style="width: 150px;">
                            <input type="hidden" value="search_name" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                       <div class="profile-info-value searchTr">
                            <select name="roleType" id="search_roleType" value="" class="propertyValue1" >
			               		<option value="">老师角色</option>
			               		<c:forEach var="roles" items="${roles}">
                        			<option value="${roles.value}">${roles.name}</option>
                    			</c:forEach>
			               </select>
                            <input type="hidden" value="search_roleType" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                       <div class="profile-info-value searchTr">
                            <select name="menuId" id="search_menu" value="" class="propertyValue1"  >
			               		<option value="">所属学科</option>
			               		<c:forEach var="menus" items="${menuVo}">
			                        <option value="${menus.id}">${menus.name}</option>
			                    </c:forEach>
			               </select>
                            <input type="hidden" value="search_menu" class="propertyName"/>
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
			<table id="lecturerTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 增加form -->
<div id="dialogAddLecturerDiv"></div>
<div id="addLecturerDialog" class="hide">
	<form class="form-horizontal" id="addLecturer-form" method="post" action="" style="margin-top: 18px;">
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="imgAdd"><font color="red">*</font>头像:</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix" id="imgAdd">
					
				</div>
				<input name='headImg' id="headImgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0;top:70px;width:0px; filter:Alpha(opacity=0);">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 所属学科: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <select name="menuId" id="menuId"  class="col-xs-10 col-sm-8 {required:true}">
               		<option value="">请选择</option>
               		<c:forEach var="menuVo" items="${menuVo}">
                        <option value="${menuVo.id}">${menuVo.name}</option>
                    </c:forEach>
               </select>
		      </div>
	      </div>
    	</div>
    	<div class="form-group" style="margin-top: 18px;">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 老师姓名: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <input type="text" name="name" style="margin-right: 30px;"  maxlength=5 class="col-xs-10 col-sm-8 {required:true,minlength:2,maxlength:5}">
		      </div>
	      </div>
        </div>
		<div class="form-group" style="margin-top: 18px;">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 角色: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <select name="roleType" id="roleType"  class="col-xs-10 col-sm-8 {required:true}">
               		<option value="">请选择</option>
               		<c:forEach var="roles" items="${roles}">
                        <option value="${roles.value}">${roles.name}</option>
                    </c:forEach>
               </select>
		      </div>
	      </div>
    	</div>
		<div class="form-group" style="margin-top: 18px;">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 老师昵称: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <input type="text" name="nickname" style="margin-right: 30px;"   maxlength=7 class="col-xs-10 col-sm-8 {required:true,minlength:2,maxlength:7}">
		      </div>
	      </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
	      <label class="col-xs-12 col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 简介: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <textarea class="col-xs-10 col-sm-8 {required:true}" maxlength=115 name="description" id="description"  rows="3"></textarea>
	      	 </div>
	      </div>
	    </div>
	</form>
</div>	
<!-- 修改form -->
<div id="dialogEditLecturerDiv"></div>
<div id="editLecturerDialog" class="hide">
	<form class="form-horizontal" id="editLecturer-form" method="post" action="" style="margin-top: 18px;">
		<input type="hidden" id="edit_id"  name="id" class="col-xs-10 col-sm-8 {required:true}">
		<div class="form-group" style="margin-top: 18px;">
			<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="smallingPath"><font color="red">*</font>头像:</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix" id="edit_imgAdd">
					
				</div>
				<input name='headImg' id="edit_headImgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 所属学科: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <select name="menuId" id="edit_menuId"  class="col-xs-10 col-sm-8 {required:true}">
               		<option value="">请选择</option>
               		<c:forEach var="menuVo" items="${menuVo}">
                        <option value="${menuVo.id}">${menuVo.name}</option>
                    </c:forEach>
               </select>
		      </div>
	      </div>
    	</div>
    	<div class="form-group" style="margin-top: 18px;">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 老师姓名: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <input type="text" name="name" id="edit_name" style="margin-right: 30px;"  maxlength=5 class="col-xs-10 col-sm-8 {required:true,minlength:1,maxlength:5}">
		      </div>
	      </div>
        </div>
		<div class="form-group"  style="margin-top: 18px;">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 角色: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <select name="roleType" id="edit_roleType"  class="col-xs-10 col-sm-8 {required:true}">
               		<option value="">请选择</option>
               		<c:forEach var="roles" items="${roles}">
                        <option value="${roles.value}">${roles.name}</option>
                    </c:forEach>
               </select>
		      </div>
	      </div>
    	</div>
		<div class="form-group" style="margin-top: 18px;">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 老师昵称: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <input type="text" name="nickname" id="edit_nickname" style="margin-right: 30px;"  maxlength=7 class="col-xs-10 col-sm-8 {required:true,minlength:1,maxlength:7}">
		      </div>
	      </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
	      <label class="col-xs-12 col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 简介: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		        <textarea class="col-xs-10 col-sm-8 {required:true}" maxlength=115 name="description" id="edit_description"  rows="3"></textarea>
	      	 </div>
	      </div>
	    </div>
	</form>
</div>
<!-- 查看 -->
<div id="dialogViewLecturerDiv"></div>
<div id="ViewLecturerDialog" class="hide">
	<form class="form-horizontal" id="editStudentStory-form" method="post" action="">
		<input type="hidden" id="edit_id"  name="id" class="col-xs-10 col-sm-8 {required:true}">
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="smallingPath"><font color="red">*</font><b>头像:</b></label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix" id="edit_imgAdd">
					<img id="show_headImgPathFile" src="" style="text-align: center; margin:0 auto ; height: 80px;width: 80px;">
				</div>
			</div>
		</div>
		<div class="form-group">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i><b>所属学科: </b> </label>
	      <div class="col-xs-12 col-sm-7">
		      <div class="clearfix">
		      	<p id="show_menu" class="paddingtop7px padding7"></p>
		      </div>
	      </div>
    	</div>
    	<div class="form-group">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i><b> 老师姓名: </b></label>
	      <div class="col-xs-12 col-sm-7">
		      <div class="clearfix">
		      	<p id="show_name" class="paddingtop7px padding7"></p>
		      </div>
	      </div>
        </div>
		<div class="form-group">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> <b>角色: </b></label>
	      <div class="col-xs-12 col-sm-7">
		      <div class="clearfix">
		      	<p id="show_roleType" class="paddingtop7px padding7"></p>
		      </div>
	      </div>
    	</div>
		<div class="form-group">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> <b>老师昵称:</b> </label>
	      <div class="col-xs-12 col-sm-7">
		      <div class="clearfix">
		      	<p id="show_nickname" class="paddingtop7px padding7"></p>
		      </div>
	      </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger"></i><span> <b>简介:</b></span> </label>
            <div class="col-sm-7">
            	<p id="show_description" class="paddingtop7px padding7" style="width: 300px;overflow: hidden;white-space: normal;word-wrap: break-word;"></p>
            </div>
        </div>
	</form>
</div>
<!-- 授课记录 -->
<div id="dialogViewTeachRecondsDiv"></div>
<div id="ViewTeachRecondsDialog" class="hide">
	<form class="form-horizontal" id="editStudentStory-form" method="post" action="">
		<div class="form-group">
	      <label class="control-label col-xs-12 col-sm-3 no-padding-right"><i class="text-danger">*</i> 老师姓名: </label>
	      <div class="col-xs-12 col-sm-9">
		      <div class="clearfix">
		      	<p id="TeachReconds_name" class="paddingtop7px padding7"></p>
		      </div>
	      </div>
	   	</div>
	   	
	   	<div class="form-group">
	        <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i><span> 授课记录:</span> </label>
	        <div class="col-sm-9">
	        	<p id="TeachReconds_description" class="paddingtop7px padding7" style="width: 300px;overflow: hidden;white-space: normal;word-wrap: break-word;"></p>
	        </div>
	    </div>
    </form>
</div>
<script type="text/javascript" src="/js/cloudClass/lecturer.js?v=ipandatcm_1.3"></script>