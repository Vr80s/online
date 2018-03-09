<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<link href="/js/layer/skin/layer.css" type="text/css" />	
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
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
	当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 直播管理 </span>
</div>

<div style="height: 100%;" class="clearfix">

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
        	<div class="mainrighttab tabresourse bordernone" id="courseDiv">
				<p class="col-xs-3" style="padding: 0;">
					<button class="btn btn-sm btn-success dele_bx" title="批量删除">
						<i class="glyphicon glyphicon-trash"></i> 批量删除直播
					</button>
					
					<button class="btn btn-sm btn-success recovery_bx" title="批量恢复">
						<i class="glyphicon glyphicon-trash"></i> 批量恢复直播
					</button>
				</p>
			<div class="searchDivClass" id="searchDiv">
			        <div class="profile-info-row" >
			            <table frame=void >
			                <tr>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                           	申请时间
			                        </div>
			                    </td>
								 <td>
		                        <div class="profile-info-value searchTr">
		                            <input type="text" class="datetime-picker"  id="s_startTime"  placeholder = "开始日期" />
		                            <input type="hidden" value="s_startTime" class="propertyValue1"/>
		                        </div>
                   				 </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            &nbsp;至 &nbsp;
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text" class="datetime-picker"  id="s_stopTime"  placeholder = "结束日期"/>
			                            <input type="hidden" value="s_stopTime" class="propertyValue1"/>
			                        </div>
			                    </td>
								
								<td>
			                       <div class="profile-info-value searchTr">
			                            <select name="search_status" id="search_status" value="" class="propertyValue1" >
						               		    <option value="">审核状态</option>
						                        <option value="2">未审核</option>
						                        <option value="1">审核通过</option>
						                        <option value="0">审核未通过</option>
						               </select>
			                            <input type="hidden" value="search_status" class="propertyName"/>
			                        </div>
			                    </td>
			                    
			                   <td>
			                       <div class="profile-info-value searchTr">
			                            <select name="search_type" id="search_type" value="" class="propertyValue1" >
						               		    <option value="">观看方式</option>
						                        <option value="0">免费</option>
						                        <option value="1">收费</option>
						                        <option value="2">密码</option>
						                        
						                        <!-- 观看方式 0公开 1 收费 2 密码 -->
						               </select>
			                            <input type="hidden" value="search_type" class="propertyName"/>
			                        </div>
			                    </td>
			                    
			                    <td>
			                       <div class="profile-info-value searchTr">
			                            <select name="search_isdelete" id="search_isdelete" value="" class="propertyValue1" >
						               		    <option value="">是否有效</option>
						                        <option value="0">有效</option>
						                        <option value="1">无效</option>
						               </select>
			                            <input type="hidden" value="search_isdelete" class="propertyName"/>
			                        </div>
			                    </td>
			                    
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text" placeholder = "直播课程/主播" class="propertyValue1" id="search_courseName" style="width: 150px;">
			                            <input type="hidden" value="search_courseName" class="propertyName"/>
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
    </div>
	
	<div id="bohuiDiv"></div>
	<div id="bohuiDialog" class="hide">
		<form class="form-horizontal" id="bohui-form" method="post" action="" style="margin-top: 15px;">
			<input type="hidden" id="bohuiExamineId"  name="examineId" class="col-xs-10 col-sm-8">
			<div class="form-group" style="margin-top: 18px;">
				 <label class="col-sm-3 control-label no-padding-right" for="courseDescribe"><font color="red">*</font>驳回理由: </label>
				 <div class="col-sm-6">
				 	<textarea class="form-control {required:true}" name="againstReason" id="edid_courseDescribe"  rows="3"></textarea>
				 	<!-- <input type="hidden" name="descriptionHid"  id="edid_descriptionHid" class="col-xs-10 col-sm-12 {rangelength:[1,170]}"> -->
	             </div>
			</div>
		</form>
	</div>
	
	<!-- 修改form -->
	<div id="dialogEditCourseDiv"></div>
	<div id="EditCourseDialog" class="hide">
		<form class="form-horizontal" id="updateCourse-form" method="post" action="" style="margin-top: 15px;">
			<input type="hidden" id="editCourse_id"    name="id" class="col-xs-10 col-sm-8 {required:true}">
			
			<div style="background-color: #eacaca;font-size: 16px;">申请课程描述:</div>
			
			<div class="form-group" style="margin-top:18px;">
					<label class="col-sm-2 control-label no-padding-right"><font color="red">*</font>课程展示图:</label>
					<div class="col-sm-4">
						<div class="clearfix">
							<input type="file" name="log" id="smallImgPath_file_edit" class="uploadImg">
						</div>
					</div>
				</div>
				<div class="form-group"  style="margin-top: 18px;" >
					 <label class="col-sm-2 control-label" for="courseName"><font color="red">*</font>公开课名称: </label>
					 <div class="col-sm-3">
					 		<input type="text" name="title" disabled="disabled"  readonly="readonly"  id="courseName_edit" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
		             </div>
		             <label class="col-sm-2 control-label" for="menuName"><font color="red">*</font>申请状态: </label>
					 <div class="col-sm-3">
		               <select name="examineStatus" id="examineStatus" disabled="disabled"  readonly="readonly"  value="" class="clearfix col-xs-10 col-sm-12 {required:true}" ><!-- onchange="changeMenu_edit();" -->
		               		<option value="">请选择</option>
		               		<!-- 审核状态 0未审核 1 审核通过 2 审核未通过 -->
		                    <option value="0">未审核</option>
		                       <option value="1">审核通过</option>
		                          <option value="2">审核未通过</option>
		               </select>
		             </div>
				</div> 
				
				<div class="form-group" style="margin-top: 18px;">
					 <label class="col-sm-2 control-label" for="menuName"><font color="red">*</font>所属学科: </label>
					 <div class="col-sm-3">
		               <select name="type" id="menuId_edit" value="" disabled="disabled"  readonly="readonly"  class="clearfix col-xs-10 col-sm-12 {required:true}" ><!-- onchange="changeMenu_edit();" -->
		               		<option value="">请选择</option>
		               		<c:forEach var="menus" items="${menuVo}">
		                        <option value="${menus.id}">${menus.name}</option>
		                    </c:forEach>
		               </select>
		             </div>
		             
		              <label class="col-sm-2 control-label" ><font color="red">*</font>讲师：</label>
					    <div class="ui-widget col-sm-3">
							  <select  name="userId" id="userId" disabled="disabled"  readonly="readonly"  class="clearfix col-xs-10 col-sm-12 {required:true}">
							    <option value="">请选择...</option>
							    <c:forEach var="map" items="${mapList}">
				                        <option  value="${map.id}">${map.name}</option>
				                </c:forEach>
				              </select>  
					     </div>
				</div>
			   <div class="space-4"></div>
					<%-- <div class="form-group"   >
			            <label class="col-sm-3 control-label no-padding-right" ><font color="red">*</font>讲师：</label>
					    <div class="ui-widget col-sm-6">
							  <select  name="userId" id="userId" disabled="disabled"  readonly="readonly"  class="clearfix col-xs-10 col-sm-12 {required:true}">
							    <option value="">请选择...</option>
							    <c:forEach var="map" items="${mapList}">
				                        <option  value="${map.id}">${map.name}</option>
				                </c:forEach>
				              </select>  
					     </div>
					</div> --%>	
				<div class="space-4"></div>
				<div class="form-group"   >
					 <label class="col-sm-2 control-label" for="courseName"><font color="red">*</font>课程时长: </label>
					 <div class="col-sm-2">
					 	<input type="text" name="whenLong"  disabled="disabled"  readonly="readonly"  id="courseLength_edit" maxlength="4"  class="col-xs-10 col-sm-12 {required:true,number:true}">
		             </div>
					 <div class="col-sm-1 control-label " style="text-align: left;">
		           	      分钟
		            </div>
		            
		            <label class="col-sm-2 control-label "><i class="text-danger">*</i> 开播时间: </label>
		            <div class="col-sm-4">
		                <input type="text" name="startTime" disabled="disabled"  readonly="readonly"   id="startTime_edit" maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
		            </div>
		             
				</div>
				
		       <!--  <div class="form-group" style="margin-top: 18px;">
		            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 开播时间: </label>
		            <div class="col-sm-9">
		                <input type="text" name="startTime" disabled="disabled"  readonly="readonly"   id="startTime_edit" maxlength="20" readonly="readonly" class="datetime-picker col-xs-10 col-sm-8 {required:true,date:true,rangelength:[10,19]}">
		            </div>
		        </div> -->
			     <div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
					 <label class="col-sm-2 control-label" for="coursePwd">密码: </label>
					 <div class="col-sm-3">
					 	<input type="text" name="password" disabled="disabled"  readonly="readonly"  id="coursePwd_edit"  maxlength="10"  class="col-xs-10 col-sm-12">
		             </div>
		             
		             <label class="col-sm-3 control-label" for="courseName">现价格(人民币/元): </label>
					 <div class="col-sm-3">
					 	<input type="text" name="price" disabled="disabled"  readonly="readonly"   id="edid_currentPrice" maxlength="9"  class="col-xs-10 col-sm-12 {required:true,number:true,range:[0.00,99999.99]}">
		             </div>
				</div>
			<!-- 	<div class="form-group" id="edit-currentPrice" style="margin-top: 18px;">
					 <label class="col-sm-3 control-label no-padding-right" for="courseName">现价格(人民币/元): </label>
					 <div class="col-sm-6">
					 	<input type="text" name="price" disabled="disabled"  readonly="readonly"   id="edid_currentPrice" maxlength="9"  class="col-xs-10 col-sm-12 {required:true,number:true,range:[0.00,99999.99]}">
		             </div>
				</div> -->
				<div class="form-group" style="margin-top: 15px;">
					 <label class="col-sm-2 control-label no-padding-right" for="courseDescribe"><font color="red">*</font>课程简介: </label>
					 <div class="col-sm-6">
					 	<textarea class="form-control" name="content" id="edid_courseDescribe1"  disabled="disabled"  readonly="readonly"  rows="3"></textarea>
					 	<!-- <input type="hidden" name="descriptionHid" disabled="disabled"  readonly="readonly"   id="edid_descriptionHid" class="col-xs-10 col-sm-12 {rangelength:[1,170]}"> -->
		             </div>
				</div>
		      
 <div style="background-color: #eacaca;font-size: 16px;margin-top: 15px;"  class="ssxx">审核信息:</div>
		      
		      <div id="shxx">
		      
		      </div>
				
				
<div class="ssxx">
	<div style="background-color: #eacaca;font-size: 16px;margin-top: 15px;" >申诉信息:</div>



		      	<div class="form-group" id="edit-originalCost" style="margin-top: 15px;">
					 <label class="col-sm-2 control-label" for="coursePwd">申诉时间: </label>
					 <div class="col-sm-3">
					 	<input type="text" name="appealTime" disabled="disabled"  readonly="readonly"  id="appealTime"  maxlength="10"  class="col-xs-10 col-sm-12">
		             </div>
		             
		             <!-- <label class="col-sm-3 control-label" for="courseName">申诉理由: </label>
					 <div class="col-sm-3">
					 	<input type="text" name="price" disabled="disabled"  readonly="readonly"   id="edid_currentPrice" maxlength="9"  class="col-xs-10 col-sm-12 {required:true,number:true,range:[0.00,99999.99]}">
		             </div> -->
				</div>
				
					<div class="form-group" style="margin-top: 15px;">
					 <label class="col-sm-2 control-label no-padding-right" for="courseDescribe">申诉理由: </label>
					 <div class="col-sm-6">
					 	<textarea class="form-control" name="appealReason" id="appealReason"  disabled="disabled"  readonly="readonly"  rows="3"></textarea>
					 	<!-- <input type="hidden" name="descriptionHid" disabled="disabled"  readonly="readonly"   id="edid_descriptionHid" class="col-xs-10 col-sm-12 {rangelength:[1,170]}"> -->
		             </div>
				</div>
		      
		</form>
	</div>
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
<script type="text/javascript" src="/js/cloudClass/examineCourse.js?v=ipandatcm_1.3"></script>