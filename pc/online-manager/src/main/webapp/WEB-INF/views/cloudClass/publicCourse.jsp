<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<style type="text/css">
    .vertical-tab {
        width: 8%;
        height: 100%;
        float: left;
        /* overflow: hidden; */
    }
 
    .vertical-tab > li {
        text-align: center;
    }
    
/*  	.vertical-tab > li > a {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff; 
         border-right: 1px solid #ffffff; 
        border-right: 1px solid #ccc;
        z-index: 2;
    } */
    
    .vertical-tab > li.active > a, .vertical-tab > li.active > a:focus, .vertical-tab > li.active > a:hover {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff;
        border-right: 1px solid #ffffff;
        z-index: 2;
    }
 
    .vertical-tab > li > a {
         /* border-radius: 4px 4px 4px 4px; */
         border-radius: 4px 0px 0px 4px; 
    }
 
    .vertical-tab-content {
        float: left;
        width: 92%;
        padding: 5px;
        margin-left: -1px;
        margin-bottom: 2px;
        border-radius: 0px 4px 4px 4px;
        border: solid 1px #ccc;
        color: #666;
    }
 
    .send {
        position: relative;
        width: 90%;
        background: #FFFFFF;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px; /* 圆角 */
        border: 1px solid #ccc;
    }
 
    .tag {
        width: 300px;
        min-height: 30px;
        border: 1px solid #ccc;
        position: relative;
        padding: 10px;
        background-color: #ccc;
        border-radius: 4px;
        margin-left: 30px;
        -moz-box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
        -webkit-box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
        box-shadow:1px 1px 2px hsla(0, 0%, 0%, 0.3);
    }
 
    .tag:before, .tag:after {
        position:absolute;
        content:"\00a0";
        width:0px;
        height:0px;
        border-width:8px 18px 8px 0;
        border-style:solid;
        border-color:transparent #CCC transparent transparent;
        top:5px;
        left:-18px;
 
    }
 
    .tag:after {
        bottom: -33px;
        border-color: #FFF transparent transparent;
    }
 
    .clearfix:after{
     content:"";
     height:0;
     visibility:hidden;
     display:block;
     clear:both;
    }
 
    input.custom-combobox-input.ui-widget.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input.valid{
      width: 100%;
    }
    input.custom-combobox-input.ui-widget.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input{
     width: 100%;
    }
 
</style>
<script type="text/javascript">

	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
	
	$(function () {
        $('#vtab li').click(function () {
            tops = $(this).offset().top - $('#vtab').offset().top + $('#vtab').scrollTop() - $('#vtab').height() / 4;
            console.log(tops);
            $('#vtab').animate({
                scrollTop: tops
            }, 'slow');
        });
    });
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
	当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 直播管理 </span>
</div>

<div style="height: 100%;" class="clearfix">
    <!-- Nav tabs -->
    <ul class="nav nav-tab vertical-tab" role="tablist" id="vtab" style="margin:0px;">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" class="kcgl_bx" role="tab" style="padding: 10px 5px;"
               data-toggle="tab">直播管理</a>
        </li>
        <li role="presentation">
            <a href="#inbox" aria-controls="inbox" class="kctj_bx" role="tab" style="padding: 10px 5px;"
               data-toggle="tab">直播统计</a>
        </li>

		<li role="presentation">
            <a href="#liveremon" aria-controls="#liveremon" title="1" class="zbtj_bx" role="tab"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">直播中推荐</a>
        </li>
        
        <li role="presentation">
            <a href="#liveremon" aria-controls="#liveremon" title="2"  class="zbtj_bx" role="tab"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">直播预告推荐</a>
        </li>
        
        <li role="presentation">
            <a href="#liveremon" aria-controls="#liveremon" title="3" class="zbtj_bx" role="tab"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">直播结束推荐</a>
        </li>
        
    </ul>
 
    <!-- Tab panes -->
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
        	<div class="mainrighttab tabresourse bordernone" id="courseDiv">
				<p class="col-xs-4" style="padding: 0;">
					<button class="btn btn-sm btn-success add_bx" title="新增课程">
						<i class="glyphicon glyphicon-plus"></i> 新增课程
					</button>
					<button class="btn btn-sm btn-success dele_bx" title="批量删除">
						<i class="glyphicon glyphicon-trash"></i> 批量删除
					</button>
					<button class="btn btn-sm btn-success rec_P" title="设为推荐">
						<i class="glyphicon glyphicon-cog"></i> 设为推荐 
					</button>
				</p>
			
			<div class="searchDivClass" id="searchDiv">
			        <div class="profile-info-row" >
			
			            <table frame=void >
			                <tr>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text" placeholder = "公开课名称" class="propertyValue1" id="search_courseName" style="width: 150px;">
			                            <input type="hidden" value="search_courseName" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                       <div class="profile-info-value searchTr">
			                            <select name="menuName" id="search_menu" value="" class="propertyValue1"  >
						               		<option value="">学科</option>
						               		<c:forEach var="menus" items="${menuVo}">
						                        <option value="${menus.id}">${menus.name}</option>
						                    </c:forEach>
						               </select>
			                            <input type="hidden" value="search_menu" class="propertyName"/>
			                        </div>
			                    </td>
								<td>
									<div class="profile-info-value searchTr">
										<input type="text" placeholder="授课老师" class="propertyValue1" id="search_lecturerName" style="width: 150px;"> 
										<input type="hidden" value="search_lecturerName" class="propertyName" />
									</div>
								</td>
								<td>
			                       <div class="profile-info-value searchTr">
			                            <select name="search_status" id="search_status" value="" class="propertyValue1" >
						               		<option value="">状态</option>
						                        <option value="0">已禁用</option>
						                        <option value="1">已启用</option>
						               </select>
			                            <input type="hidden" value="search_status" class="propertyName"/>
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
						<table id="courseTable"
							class="table table-striped table-bordered table-hover">
						</table>
					</div>
				</div>
			</div>
        </div>
        <div role="tabpanel" class="tab-pane" id="inbox">
        	<div class="mainrighttab tabresourse bordernone" id="courseRecDiv" style="display:none">
        			
        			<div class="searchDivClass" id="searchDiv">
			        <div class="profile-info-row" >
			
			            <table frame=void >
			                <tr>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text" placeholder = "公开课名称" class="propertyValue1" id="search_courseName" style="width: 150px;">
			                            <input type="hidden" value="search_courseName" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                       <div class="profile-info-value searchTr">
			                            <select name="menuName" id="search_menu" value="" class="propertyValue1"  >
						               		<option value="">学科</option>
						               		<c:forEach var="menus" items="${menuVo}">
						                        <option value="${menus.id}">${menus.name}</option>
						                    </c:forEach>
						               </select>
			                            <input type="hidden" value="search_menu" class="propertyName"/>
			                        </div>
			                    </td>
								<td>
									<div class="profile-info-value searchTr">
										<input type="text" placeholder="授课老师" class="propertyValue1" id="search_lecturerName" style="width: 150px;"> 
										<input type="hidden" value="search_lecturerName" class="propertyName" />
									</div>
								</td>
								<td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text"  class="propertyValue1" id="search_startTime"  placeholder = "开始时间" style="width: 150px" readonly="readonly"/>
			                            <input type="hidden" value="search_startTime" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            &nbsp;至 &nbsp;
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text"  class="propertyValue1" id="search_endTime"  placeholder = "结束时间" style="width: 150px" readonly="readonly"/>
			                            <input type="hidden" value="search_endTime" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
			                                onclick="searchCourseRecTable();">
			                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
			                        </button>
			                    </td>
			                </tr>
			            </table>
			        </div>
			    </div>
        	
				<div class="row">
					<div class="col-xs-12">
						<table id="courseRecTable"
							class="table table-striped table-bordered table-hover">
							<colgroup>
							    <col width='5%'></col>
							    <col width='17%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='14%'></col>
							    
							    <col width='14%'></col>
							</colgroup>
						</table>
					</div>
				</div>
			</div>
        </div>
        
        
        <div role="tabpanel" class="tab-pane" id="liveremon">
        	<div class="mainrighttab tabresourse bordernone" id="courseZbRecDiv" style="display:none">

				<div class="row">
					<div class="col-xs-12">
						<table id="courseZbRecTable"
							class="table table-striped table-bordered table-hover">
							<colgroup>
							    <col width='5%'></col>
							    <col width='17%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='12%'></col>
							    <col width='14%'></col>
							    <col width='14%'></col>
							</colgroup>
						</table>
					</div>
				</div>
			</div>
        </div>
        
    </div>
 
	<!-- 增加form -->
	<div id="dialogAddCourseDiv"></div>
	<div id="addCourseDialog" class="hide">
		<form id="addCourse-form" class="form-horizontal"  method="post" action="" style="margin-top: 15px;">
		
			 <div class="form-group" style="margin-top:18px;">
				<label class="col-sm-3 control-label no-padding-right"><font color="red">*</font>课程展示图:</label>
				<div class="col-sm-6">
					<div class="clearfix"  id="imgAdd">
						<!-- <input type="file" name="smallImgPath_file" id="smallImgPath_file" class="uploadImg"/> -->
					</div>
					<input name="smallimgPath" id="smallImgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
				</div>
			</div>
			<div class="form-group"  style="margin-top: 18px;" >
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>课程名称: </label>
				 <div class="col-sm-6">
				 		<input type="text" name="courseName"  id="courseName" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
	             </div>
			</div>

			<div class="form-group"  style="margin-top: 18px;" >
				<label class="col-sm-3 control-label no-padding-right" for="subtitle"><font color="red">*</font>副标题: </label>
				<div class="col-sm-6">
					<input type="text" name="subtitle"  id="subtitle" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
				</div>
			</div>
		    <div class="space-4"></div>
			
		    <div class="space-4"></div>
			<div class="form-group"  style="margin-top: 18px;" >
				 <label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>所属学科: </label>
				 <div class="col-sm-6">
	               <select name="menuId" id="menuId" value="" class="clearfix col-xs-10 col-sm-12 {required:true}" ><!--onchange="changeMenu();"  -->
	               		<option value="">请选择</option>
	               		<c:forEach var="menus" items="${menuVo}">
	                        <option value="${menus.id}">${menus.name}</option>
	                    </c:forEach>
	               </select>
	             </div>
			</div>
		   <div class="space-4"></div>


			<div class="form-group"  style="margin-top: 18px;" >
			            <label class="col-sm-3 control-label no-padding-right" ><font color="red">*</font>作者：</label>
					    <div class="ui-widget col-sm-6">
							  <select  name="userLecturerId" id="combobox" class="clearfix col-xs-10 col-sm-12 {required:true}">
							    <option value="">请选择...</option>
							    <c:forEach var="map" items="${mapList}">
				                        <option  value="${map.id}">${map.name}</option>
				                </c:forEach>
				              </select>  
					     </div>
			</div>
			<div class="form-group"  style="margin-top: 18px;" >
				<label class="col-sm-3 control-label no-padding-right" for="subtitle"><font color="red">*</font>主播: </label>
				<div class="col-sm-6">
					<input type="text" name="lecturer"  id="lecturer" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
				</div>
			</div>
		
			<div class="space-4"></div>
			<div class="form-group"  style="margin-top: 18px;" >
				 <label class="col-sm-3 control-label no-padding-right" for="courseLength"><font color="red">*</font>课程时长: </label>
				 <div class="col-sm-3" style="width: 43%;">
				 	<input type="text" name="courseLength"  id="courseLength" maxlength="4"  class="col-xs-10 col-sm-12 {required:true,number:true}">
	             </div>
				 <div class="col-sm-1 control-label no-padding-left" style="text-align: left;">
	           	 小时
	            </div>
			</div>
			
			<div class="space-4"></div>
	        <div class="form-group" style="margin-top: 18px;">
	            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 开播时间: </label>
	            <div class="col-sm-9">
	                <input type="text" name="startTime"  id="startTime" maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
	            </div>
	        </div>

	        <div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
				 <label class="col-sm-3 control-label no-padding-right" for="coursePwd">密码: </label>
				 <div class="col-sm-6">
				 	<input type="text" name="coursePwd"   maxlength="9"  class="col-xs-10 col-sm-12">
	             </div>
			</div>
	        
			<div class="form-group" id="edit-currentPrice" style="margin-top: 18px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>价格: </label>
				 <div class="col-sm-6">
				 	<input type="text" name="currentPrice"  maxlength="9"  class="col-xs-10 col-sm-12 {required:true,number:true,range:[0.00,99999.99]}">
	             </div>
			</div>
	         <div class="form-group"  style="margin-top: 18px;" >
				 <label class="col-sm-3 control-label no-padding-right" ><font color="red">*</font>直播布局: </label>
				 <div class="">
				 	<p class="paddingtop7px padding7" style="line-height:22px">
				 		<label style="cursor: pointer;"><input type="radio" class="{required:true}" style="cursor: pointer;vertical-align:text-top;margin-top:2px;margin-left:2px;margin-right:5px" name="directSeeding"  id="this_web1" value="1" checked="checked" >单视频</label>
				 		<label style="cursor: pointer;"><input type="radio" class="{required:true}" style="vertical-align:text-top;margin-top:2px;margin-right:5px" name="directSeeding"  id="this_web2" value="2">单文档</label>
				 		<label style="cursor: pointer;"><input type="radio" class="{required:true}" style="vertical-align:text-top;margin-top:2px;margin-right:5px" name="directSeeding"  id="this_web3" value="3">文档+视频</label>
				 	</p>
	             </div>
			</div>
			<div class="form-group" id="add-directIdDiv1" style="margin-top: 18px; display: none;" >
				 <label class="col-sm-3 control-label no-padding-right" for="directId"><font color="red">*</font>直播间ID: </label>
				 <div class="col-sm-6" >
				 	<input type="text" name="directId" id="directId" maxlength="100" class="col-xs-10 col-sm-12">
	             </div>
			</div>

		</form>
	</div>
	
	<!-- 修改form -->
	<div id="dialogEditCourseDiv"></div>
	<div id="EditCourseDialog" class="hide">
		<form class="form-horizontal" id="updateCourse-form" method="post" action="" style="margin-top: 15px;">
			<input type="hidden" id="editCourse_id"  name="id" class="col-xs-10 col-sm-8 {required:true}">
			<div class="form-group" style="margin-top:18px;">
					<label class="col-sm-3 control-label no-padding-right"><font color="red">*</font>课程展示图:</label>
					<div class="col-sm-6">
						<div class="clearfix">
							<input type="file" name="smallImgPath_file" id="smallImgPath_file_edit" class="uploadImg"/>
						</div>
						<input name="smallimgPath" id="smallImgPath_edit" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
					</div>
				</div>
				<div class="form-group"  style="margin-top: 18px;" >
					 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>课程名称: </label>
					 <div class="col-sm-6">
					 		<input type="text" name="courseName"  id="courseName_edit" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
		             </div>
				</div> 
			    <div class="space-4"></div>
				<div class="form-group"  style="margin-top: 18px;" >
					<label class="col-sm-3 control-label no-padding-right" for="subtitle"><font color="red">*</font>副标题: </label>
					<div class="col-sm-6">
						<input type="text" name="subtitle"  id="subtitle_edit" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
					</div>
				</div>
			    <div class="space-4"></div>
				<div class="form-group"  style="margin-top: 18px;" >
					 <label class="col-sm-3 control-label no-padding-right" for="menuName"><font color="red">*</font>所属学科: </label>
					 <div class="col-sm-6">
		               <select name="menuId" id="menuId_edit" value="" class="clearfix col-xs-10 col-sm-12 {required:true}" ><!-- onchange="changeMenu_edit();" -->
		               		<option value="">请选择</option>
		               		<c:forEach var="menus" items="${menuVo}">
		                        <option value="${menus.id}">${menus.name}</option>
		                    </c:forEach>
		               </select>
		             </div>
				</div>
			   <div class="space-4"></div>
				<div class="form-group"  style="margin-top: 18px;" >
					<label class="col-sm-3 control-label no-padding-right" ><font color="red">*</font>作者：</label>
					<div class="ui-widget col-sm-6">
						  <select  name="userLecturerId" id="combobox1" readonly="readonly" class="clearfix col-xs-10 col-sm-12 {required:true}">
							<option value="">请选择...</option>
							<c:forEach var="map" items="${mapList}">
									<option  value="${map.id}">${map.name}</option>
							</c:forEach>
						  </select>
					 </div>
				</div>
				<div class="form-group"  style="margin-top: 18px;" >
					<label class="col-sm-3 control-label no-padding-right" for="subtitle"><font color="red">*</font>主播: </label>
					<div class="col-sm-6">
						<input type="text" name="lecturer"  id="lecturer_edit" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group"  style="margin-top: 18px;" >
					 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>课程时长: </label>
					 <div class="col-sm-3" style="width: 43%;">
					 	<input type="text" name="courseLength"  id="courseLength_edit" maxlength="4"  class="col-xs-10 col-sm-12 {required:true,number:true}">
		             </div>
					 <div class="col-sm-1 control-label no-padding-left" style="text-align: left;">
		           	 小时
		            </div>
				</div>
				
				<div class="space-4"></div>
		        <div class="form-group" style="margin-top: 18px;">
		            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 开播时间: </label>
		            <div class="col-sm-9">
		                <input type="text" name="startTime"  id="startTime_edit" maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
		            </div>
		        </div>
		       <%--<div class="space-4"></div>--%>
		        <%--<div class="form-group" style="margin-top: 18px;">--%>
		            <%--<label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 结束时间: </label>--%>
		            <%--<div class="col-sm-9">--%>
		                <%--<input type="text" name="endTime" id="endTime_edit"  maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">--%>
		            <%--</div>--%>
		        <%--</div>--%>

			     <div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
					 <label class="col-sm-3 control-label no-padding-right" for="coursePwd">密码: </label>
					 <div class="col-sm-6">
					 	<input type="text" name="coursePwd" id="coursePwd_edid"  maxlength="10"  class="col-xs-10 col-sm-12">
		             </div>
				</div>

				<div class="form-group" id="edit-currentPrice" style="margin-top: 18px;">
					 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>价格: </label>
					 <div class="col-sm-6">
					 	<input type="text" name="currentPrice"  id="edid_currentPrice" maxlength="9"  class="col-xs-10 col-sm-12 {required:true,number:true,range:[0.00,99999.99]}">
		             </div>
				</div>

				<div class="form-group"  style="margin-top: 18px;" id="edit-defaultStudent">
					<label class="col-sm-3 control-label no-padding-right" for="defaultStudentCount"><font color="red">*</font>默认报名人数: </label>
					<div class="col-sm-6" >
						<input type="text" id="edit-defaultStudentCount" name="defaultStudentCount" maxlength="10" class="col-xs-10 col-sm-12 {required:true,number:true,digits:true,range:[0,9999999]}">
					</div>
				</div>

				<%--<div class="form-group" style="margin-top: 18px;">--%>
					 <%--<label class="col-sm-3 control-label no-padding-right" for="courseDescribe"><font color="red">*</font>课程简介: </label>--%>
					 <%--<div class="col-sm-6">--%>
					 	<%--<textarea class="form-control" name="description" id="edid_courseDescribe"  rows="3"></textarea>--%>
					 	<%--<input type="hidden" name="descriptionHid"  id="edid_descriptionHid" class="col-xs-10 col-sm-12 {rangelength:[1,170]}">--%>
		             <%--</div>--%>
				<%--</div>--%>
		         <div class="form-group"  style="margin-top: 18px;" >
					 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>直播布局: </label>
					 <div class="">
					 	<p class="paddingtop7px padding7" style="line-height:22px"> 
						 	<label style="cursor: pointer;"><input type="radio" style="cursor: pointer;vertical-align:text-top;margin-top:2px;margin-left:2px;margin-right:5px" name="directSeeding"  id="this_web_edit1" value="1" >单视频</label> 
						 	<label style="cursor: pointer;"><input type="radio" style="cursor: pointer;vertical-align:text-top;margin-top:2px;margin-right:5px" name="directSeeding"  id="this_web_edit2" value="2">单文档</label>
						 	<label style="cursor: pointer;"><input type="radio" style="cursor: pointer;vertical-align:text-top;margin-top:2px;margin-right:5px" name="directSeeding"  id="this_web_edit3" value="3">文档+视频</label>
					 	</p>
		             </div>
				</div>
				<div class="form-group" id="directIdDiv_edit" style="margin-top: 18px;" >
					 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>活动ID: </label>
					 <div class="col-sm-6" >
					 	<input type="text" name="directId"  id="directId_edit" readonly="readonly" maxlength="100" class="col-xs-10 col-sm-12 {required:true}">
		             </div>
				</div>
		</form>
	</div>
	
	
	
	<!-- 查看主播入口form -->
	<div id="dialogShowUrlDiv"></div>
	<div id="ShowUrlDialog" class="hide">
		<form class="form-horizontal" id="updateCourse-form" method="post" action="" style="margin-top: 15px;">
				<div class="form-group"  style="margin-top: 18px;" >
					 <label class="col-sm-3 control-label no-padding-right" for="courseName"><font color="red">*</font>主播直播链接: </label>
					 <div class="col-sm-8">
					 		<textarea class="form-control" name="description" id="zburl" rows="3"></textarea>
		             </div>
				</div> 
		</form>
	</div>
	
	
	
</div>

<script type="text/javascript">
			var availableTags = [
			                 	"ActionScript",
			                 	"AppleScript",
			                 	"Asp",
			                 	"BASIC",
			                 	"C",
			                 	"C++"
			                 ];
            $( "#autocomplete" ).autocomplete({
            	source: availableTags
            });			
			
</script>
<script type="text/javascript" src="${base}/js/cloudClass/publicCourse.js?v=ipandatcm_1.3"></script>