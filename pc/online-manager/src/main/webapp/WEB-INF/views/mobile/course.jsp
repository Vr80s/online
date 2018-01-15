<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		});
	} catch (e) {

	}
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div class="page-header">
	当前位置：移动端课程管理 <small> <i
		class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 课程详情 </span>
</div>
<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv_M">
		<div class="profile-info-row" >
			<table frame=void >
				<tr>
					<td>
						<div class="profile-info-value searchTr">
							<%--<input type="text" placeholder = "课程名称" class="propertyValue1" id="search_courseName_M" style="width: 150px;">--%>
								<select name="isCourseDetails" id="isCourseDetails" value="" class="propertyValue1"  >
									<option value="">是否展示</option>
									<option value="1">展示</option>
									<option value="0">未展示</option>
								</select>
							<input type="hidden" value="search_Course" class="propertyName"/>
						</div>
					</td>
					<td>
						<div class="profile-info-value searchTr">
							<input type="text" placeholder = "课程名称" class="propertyValue1" id="search_courseName_M" style="width: 150px;">
							<input type="hidden" value="search_courseName" class="propertyName"/>
						</div>
					</td>
					<td>
						<div class="profile-info-value searchTr">
							<select name="menuName" id="search_menu_M" value="" class="propertyValue1"  >
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
							<select name="courseTypeId" id="search_scoreType_M" value="" class="propertyValue1" >
								<option value="">课程类别</option>
								<c:forEach var="scoreTypes" items="${scoreTypeVo}">
									<option value="${scoreTypes.id}">${scoreTypes.name}</option>
								</c:forEach>
							</select>
							<input type="hidden" value="search_scoreType" class="propertyName"/>
						</div>
					</td>
					<td>
						<div class="profile-info-value searchTr">
							<select name="search_isRecommend" id="search_isRecommend_M" value="" class="propertyValue1" >
								<option value="">是否已推荐</option>
								<option value="1">已推荐</option>
								<option value="0">未推荐</option>
							</select>
							<input type="hidden" value="search_isRecommend" class="propertyName"/>
						</div>
					</td>
					<td>
						<button id="searchBtn_M" type="button" class="btn btn-sm  btn-primary "
								onclick="search_M();">
							<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
						</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<table id="courseTable_M"
				class="table table-striped table-bordered table-hover">
				<colgroup>
					<col width='5%'></col>
					<col width='5%'></col>
					<col width='10%'></col>
					<col width='8%'></col>
					<col width='8%'></col>
					<col width='8%'></col>
					<col width='9%'></col>
					<col width='10%'></col>
					<col width='8%'></col>
					<col width='8%'></col>
					<col width='12%'></col>
				</colgroup>
			</table>
		</div>
	</div>
</div>
<!-- 查看form -->
<div id="showCourseDiv"></div>
<div id="showCourseDialog" class="hide">
	<form id="addCourse-form1"  class="form-horizontal" method="post" action="">
		<input type="hidden" id="edit_id"  name="id" class="col-xs-10 col-sm-8 {required:true}" style="margin-top: 15px;">
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>课程名称:</b> </label>
			 <div class="col-sm-6">
			  	<p id="show_courseName" class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="menuName"><font color="red">*</font><b>所属学科:</b> </label>
			 <div class="col-sm-6">
			 	<p id="show_menuName" class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-4 control-label no-padding-right" for="menuNameSecond"><font color="red">*</font><b>课程类别:</b> </label>
            <div class="col-sm-6">
           	 	<p id=show_menuNameSecond class="paddingtop7px padding7"></p>
            </div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-4 control-label no-padding-right" for="menuNameSecond"><font color="red">*</font><b>授课方式: </b></label>
            <div class="col-sm-6">
            	<p id=show_courseType class="paddingtop7px padding7"></p>
            </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>课程时长:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_courseLength class="paddingtop7px padding7"></p>
             </div>
		</div>
		<div class="form-group" id="classGradeQQ">
			<label class="col-sm-4 control-label no-padding-right" for="show_gradeQQ"><font color="red">*</font><b>班级QQ群:</b> </label>
			<div class="col-sm-6">
				<p id=show_gradeQQ class="paddingtop7px padding7"></p>
			</div>
		</div>
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>咨询QQ:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_qqno class="paddingtop7px padding7"></p>
             </div>
		</div>
		<%--<div class="form-group"  >
		 	 <label class="col-sm-4 control-label no-padding-right" for="courseName"><b>课程链接:</b> </label>
			 <div class="col-sm-6" >
			 	<p id=show_cloudClassroom class="paddingtop7px padding7"></p>
             </div>
		</div>--%>
        <div class="form-group" id="show_classRatedNum">
            <label class="col-sm-4 control-label no-padding-right" for="show_gradeStudentSum"><font color="red">*</font><b>班级额定人数: </b></label>
            <div class="col-sm-6" >
                <p id="show_gradeStudentSum" class="paddingtop7px padding7"></p>
            </div>
        </div>
        
        <div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>累计学习人数:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_sum class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>默认学习人数:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_learndCount class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>实际学习人数:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_actCount class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 讲师: </span></label>
            <div class="col-sm-6">
                <label class="paddingtop7px padding7"  id="role_type1_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <%--<div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 班主任 : </span></label>
            <div class="col-sm-6">
                <label class="paddingtop7px padding7"  id="role_type2_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 助教 :</span> </label>
            <div class="col-sm-6">
                <label class="paddingtop7px padding7"  name="name" id="role_type3_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>--%>
		
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>创建时间:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_createTime class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>状态:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_status class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<!-- <div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>是否收费:</b> </label>
			 <div class="col-sm-3">
				<p class='paddingtop7px padding7'>收费<input type="radio" style="vertical-align:text-top;margin-top:2px;margin-left:2px;margin-right:5px" name="isFree"  id="show_is_free" disabled="disabled" value="1">免费<input type="radio" style="vertical-align:text-top;margin-top:2px;margin-left:2px" name="isFree"  id="show_no_free" disabled="disabled" value="0"></p>
             </div>
             <div class="col-sm-3">
             </div>
		</div> -->
		
		<div class="form-group" id="show-originalCost">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>原价格:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_originalCost class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group" id="show-currentPrice">
			 <label class="col-sm-4 control-label no-padding-right" for="courseName"><font color="red">*</font><b>现价格:</b> </label>
			 <div class="col-sm-6">
			 	<p id=show_currentPrice class="paddingtop7px padding7"></p>
             </div>
		</div>
		
		<div class="form-group">
			 <label class="col-sm-4 control-label no-padding-right" for="courseDescribe"><font color="red">*</font><b>课程简介:</b> </label>
			 <div class="col-sm-6">
			 	<!-- <input type="text" name="courseDescribe"  id="show_courseDescribe" disabled="disabled" maxlength="20"  class="col-xs-10 col-sm-12 {required:true,rangelength:[2,20]}"> -->
			 	<p id=show_courseDescribe class="paddingtop7px padding7" style="word-break:break-all;word-wrap:break-word;width:250px"></p>
             </div>
		</div>
	</form>
</div>
<script type="text/javascript" src="${base}/js/mobile/course.js?v=ipandatcm_1.3"></script>