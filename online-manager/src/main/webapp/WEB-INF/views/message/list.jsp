<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<script type="text/javascript" src="js/message/message_manage_list.js"></script>
<script type="text/javascript" src="bootstrap/assets/js/bootstrap-select.js"></script>
<script type="text/javascript" src="bootstrap/assets/js/defaults-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="bootstrap/assets/css/bootstrap-select.css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	
<style type="text/css">
.wysiwyg-style3{
	text-align: right !important;
}
</style>
<style type="text/css">
.bs-select-all.btn, .bs-deselect-all.btn, .dropdown-toggle.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 12px  !important;
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
    width: 49% !important;
}

</style>

<script src="/js/layer/layer.js"></script>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
	}
</script>
<%@ include file="../common/excel.jsp"%>
<!-- <div style="width:100%;float:right">
		<table frame=void style="width: 100%;margin-bottom:5px">
			<tr>
				<td style="width:7%">
					<button class="btn btn-sm btn-success add_bx" type="button" id="add_message_btn" data-toggle="button"><i class="ace-icon fa fa-plus bigger-120"></i> 推送消息</button>
				</td>
				<td style="width:7%">
					<button class="btn btn-sm btn-success" type="button" data-toggle="button" onclick="deleteBatch();"><i class="ace-icon fa fa-trash-o bigger-120"></i> 批量删除</button>
				</td>
				<td style="width:70%">
				</td>
				<td style="width:4%">
					<div class="profile-info-value searchTr">
						<input type="text"  id="q_name" placeholder="用户名称" class="propertyValue1" />
						<input type="hidden" value="q_name" class="propertyName"/>
						<input type="hidden" value="5" class="tempMatchType"/>4等于 5包含 6小于 7大于  8小于等于  9大于等于  10之间
						<input type="hidden" value="String" class="tempType"/>默认是String
					</div>
				</td>
				<td style="width:4%">
					<div class="profile-info-value searchTr">
						<input type="text"  id="q_title" placeholder="消息标题" class="propertyValue1" />
						<input type="hidden" value="q_title" class="propertyName"/>
						<input type="hidden" value="5" class="tempMatchType"/>4等于 5包含 6小于 7大于  8小于等于  9大于等于  10之间
						<input type="hidden" value="String" class="tempType"/>默认是String
					</div>
				</td>
				<td style="width:3%">
					<div class="profile-info-value searchTr">
						<input type="text" class="datetime-picker" name="time_start" id="time_start"  placeholder = "开始时间" />
						<input type="hidden" value="time_start" class="propertyName"/>
					</div>
				</td>
				<td style="width:1%;font-size: 12px">
					&nbsp;&nbsp;至
				</td>
				<td style="width:5%">
					<div class="profile-info-value searchTr" style="margin-right: 30px">
						<input type="text" class="datetime-picker" name="time_end" id="time_end"  placeholder = "结束时间"/>
						<input type="hidden" value="time_end" class="propertyName"/>
					</div>
				</td>
				<td style="width:5%">
					<button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
						onclick="search();">
						<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
				</td>
			</tr>
		</table>
	</div> -->
<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" type="button" id="add_message_btn" data-toggle="button">
		<i class="ace-icon fa fa-plus bigger-120"></i> 推送消息</button>
		<!-- <button class="btn btn-sm btn-success" type="button" data-toggle="button" onclick="deleteBatch();">
		<i class="ace-icon fa fa-trash-o bigger-120"></i> 批量删除</button> -->
	</p>

	<div class="searchDivClass" id="searchDiv">
		<div class="profile-info-row">

			<table frame=void >
				<tr>
					<!-- <td>
						<div class="profile-info-value searchTr">
							<input type="text"  id="q_name" placeholder="用户名称" class="propertyValue1" />
								<input type="hidden" value="q_name" class="propertyName"/>
								<input type="hidden" value="5" class="tempMatchType"/>4等于 5包含 6小于 7大于  8小于等于  9大于等于  10之间
								<input type="hidden" value="String" class="tempType"/>默认是String
						</div>
					</td>
					<td>
						<div class="profile-info-value searchTr">
							    <input type="text"  id="q_title" placeholder="消息标题" class="propertyValue1" />
								<input type="hidden" value="q_title" class="propertyName"/>
								<input type="hidden" value="5" class="tempMatchType"/>4等于 5包含 6小于 7大于  8小于等于  9大于等于  10之间
								<input type="hidden" value="String" class="tempType"/>默认是String
						</div>
					</td> -->
					<td>
						<div class="profile-info-value searchTr">
							   <input type="text" class="datetime-picker" name="time_start" id="time_start"  placeholder = "开始时间" />
								<input type="hidden" value="time_start" class="propertyName"/>
						</div>
					</td>
					<td>
						<div class="profile-info-value searchTr">
							  至
						</div>
					</td>
					<td>
							<div class="profile-info-value searchTr">
								<input type="hidden" value="time_end" class="propertyName"/>
								<input type="text" class="datetime-picker" name="time_end" id="time_end"  placeholder = "结束时间"/>
							</div>
							<button id="searchBtn" type="button" class="btn btn-sm  btn-primary " style="margin-left: -5;"
								onclick="search();">
								<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
							</button>
					</td>
				</tr>
			</table>
		</div>
	</div>


<table id="message_questionTable"
	class="table table-striped table-bordered table-hover">
</table>
</div>

<div id="addMessageDialogDiv"></div>
<div id="addMessageDialog" class="hide" >
	<form class="form-horizontal" id="addmessage-form" method="post" action="" >
		<div class="form-group row">
			<label class="control-label col-xs-1  no-padding-right" for="message_user_select"
				   style="width:70px;margin-top: 5px;" ><span style="color: red">*</span>接收用户:</label>
			<div class="col-lg-10 col-xs-10">
				<div class="clearfix control-label col-xs-3" style="margin-left: -10px;">
					<select id="subject" class="form-control"  data-live-search="true" >
						<option value="">全部学科</option>
							<c:forEach var="menu" items="${menuVo}">
		                        <option value="${menu.id}">${menu.name}</option>
		                    </c:forEach>
					</select>
				</div>
				
				<div class="clearfix control-label col-xs-4">
					<select id="course" class="form-control"  data-live-search="true" >
						<option value="">全部课程</option>
						<c:forEach var="course" items="${courseVo}">
	                        <option value="${course.id}">${course.gradeName}</option>
	                    </c:forEach>
					</select>
				</div>
				
				<div class="clearfix control-label col-xs-5">
					<select id="grade" class="form-control selectpicker" multiple data-live-search="true">
						<!-- <option value="-1">全部班级</option> -->
						<c:forEach var="grade" items="${gradeVo}">
	                        <option value="${grade.id}">${grade.name}</option>
	                    </c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="form-group row " style="margin-top:15px;">
			<label class="control-label col-sm-1 no-padding-right" for="message_content"
				   style="width:70px;"	><span style="color: red">*</span>消息内容:</label>
			<div class="col-lg-10 col-xs-9">
				<div class="clearfix">
					<div class="wysiwyg-editor" id="message_content" ></div><br>
					<div id="message_content_msg" style="color:red;margin-top:-15px"></div>
				</div>
			</div>
		</div>
	</form>
</div>


<!-- 消息查看 -->
<div id="previewMessageDialogDiv"></div>
<div id="previewMessageDialog" class="hide" >
	<form class="form-horizontal"  method="post" action="" style="width:500px;">
		<div class="form-group row">
			<label class="control-label col-xs-2 col-xs-1  no-padding-right" for="sendToUser_preview"
				   style="width:70px;" >接收用户:</label>
			<div class="col-lg-10 col-xs-9">
				<input type="text" disabled="disabled" id="sendToUser_preview" style="width:420px"/>
			</div>
		</div>
		<!-- <div class="form-group row"  style="margin-top:15px;">
			<label  class="control-label col-xs-2 col-xs-1  no-padding-right"  style="width:70px;" >消息标题:</label>
			<div class="col-lg-10 col-xs-9">
				<input type="text" disabled="disabled" id="message_title_preview" style="width:420px"/>
			</div>
		</div> -->
		<div class="form-group row"  style="margin-top:15px;">
			<label  class="control-label col-xs-2 col-xs-1  no-padding-right"  style="width:70px;" >创建人:</label>
			<div class="col-lg-10 col-xs-9">
				<input type="text" disabled="disabled" id="message_createPerson_preview" style="width:420px"/>
			</div>
		</div>
		<div class="form-group row"  style="margin-top:15px;">
			<label  class="control-label col-xs-2 col-xs-1  no-padding-right"  style="width:70px;" >发送状态:</label>
			<div class="col-lg-7 col-xs-9">
				<select id="message_statusStr_preview" disabled="disabled">
					<option value="1" >已发送</option>
				</select>
			</div>
		</div>
		<div class="form-group row"  style="margin-top:15px;">
			<label  class="control-label col-xs-2 col-xs-1  no-padding-right"  style="width:70px;" >发送人数:</label>
			<div class="col-lg-7 col-xs-9">
				<input type="text" disabled="disabled" id="message_userCount_preview" style="width:100px"/>
			</div>
		</div>
		<div class="form-group row"  style="margin-top:15px;">
			<label class="control-label col-xs-2 col-xs-1  no-padding-right" style="width:70px;"
					>消息内容:</label>
			<div class="col-lg-10 col-xs-9">
				<div class="clearfix">
					<div class="wysiwyg-editor" id="message_content_preview"></div><div style="position:absolute;z-index:2;background-color:#333;opacity:0;width:455px;height:320px;top:0px;"></div><br>
				</div>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	var message_user_select_id="-1";
	var message_user_select_text="全部";
	jQuery(function($){

		 $(".wysiwyg-editor").ace_wysiwyg({
				toolbar : [null,
						     {name : 'createLink', className : 'btn-info'}, 
						     {name : 'unlink', className : 'btn-info'},null],
				'wysiwyg' : {
					fileUploadError : showErrorAlert
				},
				uploadType:{type:'url',action:basePath+'/cloudclass/course/uploadImg'}//图片上传方式，url/base64
			}).prev().addClass('wysiwyg-style2').addClass('wysiwyg-style3');

		 $('#grade').selectpicker({
			  padding:0,
			  size: 10,
			  width:225,
			  noneSelectedText: '全部班级',
			  actionsBox:true, //在下拉选项添加选中所有和取消选中的按钮
		    });
	});
</script>

