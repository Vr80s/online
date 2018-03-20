<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<style>
#edit_imgAdd img{
width:85px!important;
height:85px!important;
}
</style>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {
	}
</script>
<script src="/js/layer/layer.js"></script>
<link href="/js/layer/skin/layer.css" type="text/css" />
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
	当前位置：运营管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 学员故事 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-3" style="padding: 0;">
		<button class="btn btn-sm btn-success add_bx" title="新增学员故事">
			<i class="glyphicon glyphicon-plus"></i> 新增学员故事
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
                            <input type="text" placeholder = "学员姓名" class="propertyValue1" id="NameSearch" style="width: 150px;">
                            <input type="hidden" value="NameSearch" class="propertyName"/>
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
                       <div class="profile-info-value searchTr">
                           <select name="courseTypeId" id="search_scoreType" value="" class="propertyValue1" >
			               		<option value="">所属课程</option>
			               		<c:forEach var="scoreTypes" items="${scoreTypeVo}">
			                        <option value="${scoreTypes.id}">${scoreTypes.gradeName}</option>
			                    </c:forEach>
		               		</select>
                           <input type="hidden" value="search_scoreType" class="propertyName"/>
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
			<table id="studentStoryTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 增加form -->
<div id="dialogAddStudentStoryDiv"></div>
<div id="addStudentStoryDialog" class="hide">
	<form class="form-horizontal" id="addStudentStory-form" method="post" action="">
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="smallingPath"><font color="red">*</font>头像:</label>
			<div class="col-sm-4">
				<div class="clearfix col-xs-10 col-sm-8" id="imgAdd" style="padding:0px">
					
				</div>
				<input name='headImg' id="headImgPath" value="" type="text" class="{required:true,minlength:2}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);top:70px;width:0px;">
			</div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
		<label class="col-sm-1 control-label no-padding-right" for="addName"><font color="red">*</font>学员姓名: </label>
			<div class="col-sm-4">
            	<input type="text" name="name"  id="addname" maxlength="7"  class="col-xs-10 col-sm-8 {required:true,minlength:2,maxlength:7}">
            </div>
			<label class="col-sm-2 control-label no-padding-right" for="addotherName">学员化名: </label>
			<div class="col-sm-4">
	            	<input type="text" name="otherName"  id="addotherName" maxlength="7"  class="col-xs-8 col-sm-8 ">
	            	<div class="col-xs-4 col-sm-4">
	            		<p style="margin-top:5px">
		            		<label style="cursor: pointer;">
		            			<input style="cursor: pointer;" type="checkbox" name="useOtherName" id="adduseOtherName" >使用化名
		            		</label>
	            		</p>
	            	</div>
            </div>
           
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
		 	<label class="col-sm-1 control-label no-padding-right" for="addcourse"><font color="red">*</font>学科名称: </label>
			<div class="col-sm-4">
            	<select name="menuId" id="addmenuId" class="col-xs-10 col-sm-12 {required:true}">
               		<option value="">请选择</option>
               		<c:forEach var="menus" items="${menuVo}">
                        <option value="${menus.id}">${menus.name}</option>
                    </c:forEach>
               </select>
            </div>
            <label class="col-sm-2 control-label no-padding-right" for="addcourse"><font color="red">*</font>课程名称: </label>
			<div class="col-sm-4">
           	  <select name="courseTypeId" id="courseTypeId"  class="col-xs-10 col-sm-12 {required:true}">
               		<option value="">请选择</option>
               		<c:forEach var="scoreTypes" items="${scoreTypeVo}">
                        <option value="${scoreTypes.id}">${scoreTypes.gradeName}</option>
                    </c:forEach>
               </select>
               <input type="hidden" name="course"  id="addcourse"  class="col-xs-10 col-sm-12 ">
            </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="addcompany"><font color="red">*</font>入职公司: </label>
			<div class="col-sm-4">
            	<input type="text" name="company"  id="addcompany" maxlength="5"  class="col-xs-10 col-sm-12 {required:true,rangelength:[0,5]}">
            </div>
            <label class="col-sm-2 control-label no-padding-right" for="addsalary"><font color="red">*</font>薪资: </label>
			<div class="col-sm-3" style="width: 31%;">
            	<input type="text" name="salary"  id="addsalary" placeholder="请输入整数不超过5位数"  maxlength="5"  class="col-xs-10 col-sm-12 {required:true,digits:true,range:[0,99999]}">
            </div>
             <div class="col-sm-1 control-label no-padding-left" style="text-align: left;width:10px;">
           	 K
            </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
            <label class="col-sm-1 control-label no-padding-right" for="addintroduce"><font color="red">*</font>故事简介: </label>
            <div class="col-sm-10">
            	<textarea name="introduce" id="addintroduce" value="" rows="2" style="resize:none;width: 100%; font-size: 12px;" maxlength="74"  class="clearfix {required:true,maxlength:74}"></textarea>
            </div>
        </div>
		<div class="space-4"></div>
        
      	<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="addtitle"><font color="red">*</font>故事标题: </label>
			<div class="col-sm-4">
            	<input type="text" name="title"  id="addtitle" maxlength="60"  class="col-xs-10 col-sm-12 {required:true,rangelength:[2,60]}">
            </div>
		</div>
		<div class="space-4"></div>
		<div class="form-group " style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="message_content"><font color="red">*</font>故事正文:</label>
			<div class="col-lg-10 ">
				<div class="clearfix">
					<div class="wysiwyg-editor" id="message_content" ></div><br>
					<div id="message_content_msg" style="color:red;margin-top:-25px;position:relative;height:10px;">
					
						
					</div>
					<input type="hidden" name="text"  id="addtext" class="col-xs-10 col-sm-12 {required:true}">
				</div>
			</div>
			
		</div>
	</form>
</div>

<!-- 查看form -->
<div id="dialogShowStudentStoryDiv"></div>
<div id="showStudentStoryDialog" class="hide">
	<form class="form-horizontal" id="showStudentStory-form" method="post" action="">
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-2 control-label no-padding-right" for="smallingPath"><font color="red">*</font>头像:</label>
			<div class="col-sm-4">
				<div class="clearfix" id="imgshow">
					<img id="show_headImgPathFile" src="" style="text-align: center; margin:0 auto ; height: 80px;width: 80px;">
				</div>
			</div>
		</div>
	<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-2 control-label no-padding-right" for="showName"><font color="red">*</font>学员姓名: </label>
			<div class="col-sm-4">
				<p id="showname" class="paddingtop7px padding7"></p>
            </div>
            <label class="col-sm-2 control-label no-padding-right" for="showotherName"><font color="red">*</font>学员化名: </label>
			<div class="col-sm-4">
				<p id="showotherName" style="line-height: 30px" class="col-xs-8 col-sm-8"></p>
            	<!-- <input type="text" name="otherName"  id="showotherName" maxlength="20"  disabled="disabled" class="col-xs-8 col-sm-8 "> -->
            	<div class="col-xs-4 col-sm-4" style="line-height: 30px">
	<!--<p style="margin-top:5px"><input type="checkbox"  disabled="disabled" id="showuseOtherName" >使用化名</p>-->
            	</div>
            </div>
		</div>
	<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
		 	<label class="col-sm-2 control-label no-padding-right" for="showcourse"><font color="red">*</font>学科名称: </label>
			<div class="col-sm-4">
              <select name="menuId" id="showmenuIdSelect" disabled="disabled" hidden="hidden">
               		<c:forEach var="menus" items="${menuVo}">
                        <option value="${menus.id}">${menus.name}</option>
                    </c:forEach>
               </select> 
               <p id="showmenuId" class="paddingtop7px padding7"></p>
            </div>
            <label class="col-sm-2 control-label no-padding-right" for="showcourse"><font color="red">*</font>课程名称: </label>
			<div class="col-sm-4">
           	   <select name="courseTypeId" id="showscoreTypeSelect" disabled="disabled" hidden="hidden">
               		<c:forEach var="scoreTypes" items="${scoreTypeVo}">
                        <option value="${scoreTypes.id}">${scoreTypes.gradeName}</option>
                    </c:forEach>
               </select> 
                <p id="showscoreType" class="paddingtop7px padding7"></p>
            </div>
		</div>
	<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-2 control-label no-padding-right" for="showcompany"><font color="red">*</font>入职公司: </label>
			<div class="col-sm-4">
            	 <p id="showcompany" class="paddingtop7px padding7"></p>
            </div>
            <label class="col-sm-2 control-label no-padding-right" for="showsalary"><font color="red">*</font>薪资: </label>
			<div class="col-sm-3" style="width: 31%;">
            	<p id="showsalary" class="paddingtop7px padding7"></p>
            </div>
		</div>
	<div class="space-4"></div>

	<div class="form-group" style="margin-top:18px;">
	<label class="col-sm-2 control-label no-padding-right" for="showtitle"><font color="red">*</font>标题: </label>
	<div class="col-sm-4">
	<p id="showtitle" class="paddingtop7px padding7"></p>
	</div>
	</div>
	<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
            <label class="col-sm-2 control-label no-padding-right" for="showintroduce"><font color="red">*</font>故事简介: </label>
            <div class="col-sm-10">
            	<p id="showintroduce" class="paddingtop7px padding7"></p>
            </div>
        </div>

	<div class="space-4"></div>
		
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-2 control-label no-padding-right" for="showtitle">故事正文: </label>
			<div class="col-sm-10 ">
				<div class="paddingtop7px padding7"  id="message_content_preview" ></div>
			</div>
		</div>


	</form>
</div>

<!-- 修改form -->
<div id="dialogEditStudentStoryDiv"></div>
<div id="editStudentStoryDialog" class="hide">
	<form class="form-horizontal" id="editStudentStory-form" method="post" action="">
	<input type="hidden" id="edit_id"  name="id" class="col-xs-10 col-sm-8 {required:true}">
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="smallingPath"><font color="red">*</font>头像:</label>
			<div class="col-sm-4">
				<div class="clearfix" id="edit_imgAdd">
					
				</div>
				<input name='headImg' id="edit_headImgPath" value="" type="text" class="{required:true}" style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
			</div>
		</div>
	<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="editName"><font color="red">*</font>学员姓名: </label>
			<div class="col-sm-4">
            	<input type="text" name="name"  id="editname" maxlength="7"  class="col-xs-10 col-sm-12 {required:true}">
            </div>
           <label class="col-sm-2 control-label no-padding-right" for="editotherName"><font color="red">*</font>学员化名: </label>
			<div class="col-sm-4">
            	<input type="text" name="otherName"  id="editotherName" maxlength="7"  class="col-xs-8 col-sm-8 ">
            	<div class="col-xs-4 col-sm-4">
            		<label style="cursor: pointer;">
            			<input style="cursor: pointer;" type="checkbox"  name="useOtherName" id="edituseOtherName" >使用化名
            		</label>
            	</div>
            </div>
		</div>
	<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
		 	<label class="col-sm-1 control-label no-padding-right" for="editmenuId"><font color="red">*</font>学科名称: </label>
			<div class="col-sm-4">
            	<select name="menuId" id="editmenuId" class="col-xs-10 col-sm-12 {required:true}">
               		<c:forEach var="menus" items="${menuVo}">
                        <option value="${menus.id}">${menus.name}</option>
                    </c:forEach>
               </select>
            </div>
            <label class="col-sm-2 control-label no-padding-right" for="editscoreType"><font color="red">*</font>课程名称: </label>
			<div class="col-sm-4">
           	  <select name="courseTypeId" id="editscoreType" class="col-xs-10 col-sm-12 {required:true}">
               		<c:forEach var="scoreTypes" items="${scoreTypeVo}">
                        <option value="${scoreTypes.id}">${scoreTypes.gradeName}</option>
                    </c:forEach>
               </select>
                <input type="hidden" name="course"  id="editcourse"   class="col-xs-10 col-sm-12 ">
            </div>
		</div>
	<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="editcompany"><font color="red">*</font>入职公司: </label>
			<div class="col-sm-4">
            	<input type="text" name="company"  id="editcompany" maxlength="5"  class="col-xs-10 col-sm-12 {required:true,rangelength:[0,5]}">
            </div>
            <label class="col-sm-2 control-label no-padding-right" for="editsalary"><font color="red">*</font>薪资: </label>
			<div class="col-sm-3" style="width: 31%;">
            	<input type="text" name="salary"  id="editsalary" placeholder="请输入整数不超过5位数"  maxlength="5"  class="col-xs-10 col-sm-12 {required:true,digits:true,range:[0,99999]}">
            </div>
            <div class="col-sm-1 control-label no-padding-left" style="text-align: left;width:10px;">
           	 K
            </div>
		</div>
	<div class="space-4"></div>
		<div class="form-group" style="margin-top:18px;">
            <label class="col-sm-1 control-label no-padding-right" for="editintroduce"><font color="red">*</font>故事简介: </label>
            <div class="col-sm-10">
            	<textarea name="introduce" id="editintroduce" value="" rows="2" style="resize:none;width: 100%; font-size: 12px;" maxlength="74" class="clearfix {required:true,maxlength:74}"></textarea>
            </div>
        </div>
	<div class="space-4"></div>
      	<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="edittitle"><font color="red">*</font>标题: </label>
			<div class="col-sm-4">
            	<input type="text" name="title"  id="edittitle" maxlength="60"  class="col-xs-10 col-sm-12 {required:true,rangelength:[2,60]}">
            </div>
		</div>
	<div class="space-4"></div>
		<div class="form-group " style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right" for="message_content"><font color="red">*</font>故事正文:</label>
			<div class="col-lg-10 ">
				<div class="clearfix">
					<div class="wysiwyg-editor" id="message_content_edit" ></div><br>
					<div id="message_content_msg_edit" style="color:red;margin-top:-15px;margin-top:-25px;position:relative;height:10px;">
					
					</div>
					<input type="hidden" name="text"  id="edittext" class="col-xs-10 col-sm-12 {required:true}">
				</div>
			</div>
			
		</div>
	</form>
</div>


<script type="text/javascript" src="/js/cloudClass/studentStory.js?v=ipandatcm_1.3"></script>