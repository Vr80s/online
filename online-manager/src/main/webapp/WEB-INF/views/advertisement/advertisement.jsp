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
<script src="/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header">
	当前位置：移动端管理 <small> <i
		class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 启动页广告 </span>
</div>
<div class="mainrighttab tabresourse bordernone">

	<div role="tabpanel" class="tab-pane active" id="home">
		<div class="mainrighttab tabresourse bordernone" id="courseDiv">
			<p class="col-xs-4" style="padding: 0;">
				<button class="btn btn-sm btn-success add_bx" title="新增">
					<i class="glyphicon glyphicon-plus"></i> 新增
				</button>
			</p>
			<div class="row">
				<div class="col-xs-12">
					<table id="mobileAdvertisementTable"
						   class="table table-striped table-bordered table-hover" style="width: 100%;">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 增加form -->
<div id="dialogAddAdvertisementDiv"></div>
<div id="addAdvertisementDialog" class="hide">
	<form id="addAdvertisement-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="linkParam" id="J-add-linkParam">
		<div class="form-group"  style="margin-top: 18px;" >
			<label class="col-sm-3 control-label no-padding-right" for="add_name"><font color="red">*</font>广告名称: </label>
			<div class="col-sm-6">
				<input type="text" name="name"  id="add_name"  maxlength="50" class="col-xs-10 col-sm-12 {required:true}">
			</div>

		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" for="imgPath_file">
				<font color="red">*</font>
				广告图片:

			</label>
			<div style="padding-top:10px;color: #949494;float: right;margin-right: 16px;">
				<p style="margin: 0px;padding: 0px;">推荐使用尺寸750*1080px</p>
				<%--<p style="margin: 0px;padding: 0px;">推荐高宽比在0.4左右</p>--%>
			</div>
			<div class="col-sm-6" id="addDiv">
				<div class="clearfixAdd">
					<input type="file" name="imgPath_file" id="imgPath_file" class="uploadImg"/>
				</div>
				<input name="imgPath" id="add_imgPath" value="" type="text" class="{required:true}"
					   style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>连接类型:
			</label>
			<div class="col-sm-6">
				<select name="routeType" id="linkType" onchange="routeTypeChange(this)"
						class="clearfix col-xs-10 col-sm-12 {required:true}">
					<option value="">请选择</option>
					<option value="COMMON_COURSE_DETAIL_PAGE">课程</option><!-- 课程详情 3-->
					<option value="ANCHOR_INDEX">主播</option>
					<option value="PUBLIC_COURSE_LIST_PAGE">课程列表</option><!-- 课程列表 -->
					<option value="H5">外部链接</option>
					<option value="APPRENTICE_DETAIL">招生简章</option><!-- 招生简章 -->
					<option value="DOCTOR_POST">医师动态</option>
				</select>
			</div>
		</div>
		<!-- 友情提示：  -->
		<div class="form-group" id="yqti_div" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" for="description"></label>
			<div class="col-sm-6">
				<div class="J-course-detail" style="display: none;">
					<select name="menuType" id="J-menu">
						<c:forEach var="menu" items="${menus}">
							<option value="${menu.id}">${menu.name}</option>
						</c:forEach>
					</select>
					<select id="J-course" style="width: 170px;">
						<c:forEach var="course" items="${courses}">
							<option value="${course.id}">
									${course.courseName}
							</option>
						</c:forEach>
					</select>
				</div>

				<div class="J-anchor-detail" style="display: none">
					<select data-live-search="true" id="J-anchor">
						<c:forEach var="anchor" items="${anchors}">
							<option value="${anchor.userId}">
									${anchor.name}
							</option>
						</c:forEach>
					</select>
				</div>
				<div class="J-doctor-detail" style="display: none">
					<select data-live-search="true" id="J-doctor">
						<c:forEach var="anchor" items="${anchors}">
							<option value="${anchor.refId}">
									${anchor.refName}
							</option>
						</c:forEach>
					</select>
				</div>
				<div class="J-apprentice-detail" style="display: none">
					<select data-live-search="true" id="J-apprentice">
						<c:forEach var="regulation" items="${regulations}">
							<option value="${regulation.id}">
									${regulation.title}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group J-link" style="margin-top: 18px; display: none">
			<label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>链接条件: </label>
			<div class="col-sm-6">
        <textarea name="url" placeholder="请填写课程列表/外部链接" id="J-link-param" rows="6"
				  cols="20" class="col-xs-10 col-sm-12 {required:true,maxlength:225}"></textarea>
			</div>
		</div>

	</form>
</div>

<!-- 修改form -->
<div id="dialogUpdateAdvertisementDiv"></div>
<div id="updateAdvertisementDialog" class="hide">
	<form id="updateAdvertisement-form" class="form-horizontal" method="post" action="" style="margin-top: 15px;">
		<input type="hidden" name="Id" id="update_id">
		<input type="hidden" name="linkParam" id="J-edit-linkParam">
		<input type="hidden" name="linkType" id="J-edit-linkType">
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" for="name"><font color="red">*</font>广告名称:
			</label>
			<div class="col-sm-6">
				<input type="text" name="name" id="update_name" maxlength="50"
					   class="col-xs-10 col-sm-12 {required:true}">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" for="imgPath"><font color="red">*</font>
				广告图片:
				<div style="padding-top:10px;color: blue;">
					<p style="margin: 0px;padding: 0px;">推荐使用尺寸750*1080px</p>
					<%--<p style="margin: 0px;padding: 0px;">推荐高宽比在0.4左右</p>--%>
				</div>
			</label>
			<div class="col-sm-6" id="editDiv">
				<div class="clearfixUpdate">
					<input type="file" name="update_imgPath_file" id="update_imgPath_file" class="uploadImg"/>
				</div>
				<input name="imgPath" id="update_imgPath" type="text" class="{required:true}"
					   style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>

		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ><font color="red">*</font>链接条件:
			</label>
			<div class="col-sm-6">
				<select name="routeType" id="update_routeType" value="" onchange="routeTypeChangeEdit(this)"
						class="clearfix col-xs-10 col-sm-12 {required:true}">
					<option value="">请选择</option>
					<option value="COMMON_COURSE_DETAIL_PAGE">课程</option><!-- 课程详情 3-->
					<option value="ANCHOR_INDEX">主播</option>
					<option value="PUBLIC_COURSE_LIST_PAGE">课程列表</option><!-- 课程列表 -->
					<option value="H5">外部链接</option>
					<option value="APPRENTICE_DETAIL">招生简章</option><!-- 招生简章 -->
					<option value="DOCTOR_POST">医师动态</option>
				</select>
			</div>
		</div>

		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right" ></label>
			<div class="col-sm-6">
				<div class="J-edit-course-detail" style="display: none;">
					<select name="menuType" id="J-edit-menu">
						<c:forEach var="menu" items="${menus}">
							<option value="${menu.id}">${menu.name}</option>
						</c:forEach>
					</select>
					<select id="J-edit-course" style="width: 170px;">
						<c:forEach var="course" items="${courses}">
							<option value="${course.id}">
									${course.courseName}
							</option>
						</c:forEach>
					</select>
				</div>

				<div class="J-edit-anchor-detail" style="display: none">
					<select id="J-edit-anchor">
						<c:forEach var="anchor" items="${anchors}">
							<option value="${anchor.userId}">
									${anchor.name}
							</option>
						</c:forEach>
					</select>
				</div>
				<div class="J-edit-doctor-detail" style="display: none">
					<select id="J-edit-doctor">
						<c:forEach var="anchor" items="${anchors}">
							<option value="${anchor.refId}">
									${anchor.refName}
							</option>
						</c:forEach>
					</select>
				</div>
				<div class="J-edit-apprentice-detail" style="display: none">
					<select data-live-search="true" id="J-edit-apprentice">
						<c:forEach var="regulation" items="${regulations}">
							<option value="${regulation.id}">
									${regulation.title}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group J-edit-link" style="margin-top: 18px; display: none">
			<label class="col-sm-3 control-label no-padding-right" for="url"><font color="red">*</font>链接条件: </label>
			<div class="col-sm-6">
            <textarea name="url" placeholder="请填写课程列表/外部链接" id="J-edit-link-param" rows="6"
					  cols="20" class="col-xs-10 col-sm-12 {required:true,maxlength:225}"></textarea>
			</div>
		</div>
	</form>
</div>





<script type="text/javascript" src="/js/mobile/mobileAdvertisement.js?v=ipandatcm_1.3"></script>