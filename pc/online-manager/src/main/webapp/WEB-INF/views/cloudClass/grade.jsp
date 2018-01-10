<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
    <style>
        .caret {
            display: inline-block;
            width: 0;
            height: 0;
            margin-left: 2px;
            vertical-align: middle;
            border-top: 4px dashed;
            border-top: 4px solid \9;
            border-right: 4px solid transparent;
            border-left: 4px solid transparent;
            -webkit-transform: rotate(0deg);
            -moz-transform: rotate(0deg);
            -ms-transform: rotate(0deg);
            -o-transform: rotate(0deg);
            transform: rotate(0deg);
        }
        .rotateCaret{
            display: inline-block;
            width: 0;
            height: 0;
           /*  margin-left: 2px; */
            vertical-align: middle;
            border-top: 4px dashed;
            border-top: 4px solid \9;
            border-right: 4px solid transparent;
            border-left: 4px solid transparent;
            -webkit-transform: rotate(-90deg);
            -moz-transform: rotate(-90deg);
            -ms-transform: rotate(-90deg);
            -o-transform: rotate(-90deg);
            transform: rotate(-90deg);
        }
        .clearfix:after {
            content: '';
            height: 0;
            visibility: hidden;
            display: block;
            clear: both;
        }
        .zhezhao {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: #666;
            opacity: 0.5;
            z-index: 99999;
        }
        #fenpeiTeacher{
            width:520px;
            font-size:12px;
            margin:0 auto;
            border:1px solid #ddd;
            background-color:#fff;
            position:fixed;
            z-index:999999;
            left:50%;
            top:50%;
            transform: translate(-50%,-50%);
        }
        .fenpeiTearchTop{
            width:100%;
            border-bottom:1px solid #ddd;
        }
        .title{
            display:inline-block;
            font-size:16px;
            color:#333;
            font-weight:bold;
            line-height:35px;
            padding-left:10px;
        }
        .fenpeiTearcherClose{
            float:right;
            margin-top:10px;
            padding-right:10px;
            cursor:pointer;
        }
        .fenpeiTeacherAbove{
            padding:15px 30px;
        }
        .fengpeiTeacher-content{

        }
        .fengpeiTeacher-content ul{
            list-style:none;
        }
        .fengpeiSearch{
            position:relative;
            width:242px;
        }
        .fengpeiSearch input{
            width:100%;
            height:28px;
            line-height:20px;
        }
        .fengpeiSearch img{
            position:absolute;
            right:8px;
            top:4px;
        }
        .fenpeiList{
            margin-top:10px;
            width:242px;
            height:395px;
            border:1px solid #ddd;
            padding:10px;
            float:left;
            overflow-y:auto;
        }
        .fenpeiList .xuekename{
            display:inline-block;
            vertical-align: top;
            margin:5px 0;
        }
       .fenpeiList .xuekeleibei{
            display:inline-block;
            margin:0;
            padding:0;
            width:150px;
        }
        .fenpeiList .xuekeleibei span{
            cursor:pointer;
        }
        .list-items2 li{
            cursor:pointer;
        }
        .row li{
            cursor:pointer;
        }
        .fenpeiTeacherImg{
            cursor:pointer;
        }
        .allTeacher span,.allTeacher img{
            cursor:pointer;
        }
        .child-parrent-title .clear{
            cursor:pointer;
        }
        .fenpeiTeacherBtn span{
            cursor:pointer;
        }
        .fenpeiList .xuekeleibei li{
            margin:5px 0;
        }
        .fenpeiList .xuekeleibei .tagname{
            /* margin-left:5px; */
        }
        .fenpeiList .xuekeleibei li::before{

        }
        .fenpeirow{
            float:left;
            margin:0;
            padding:0;
            width:50px;
            margin-left:5px;
        }
       .fenpeirow li{
            width:30px;
            height:30px;
            background-color:#f8f8f8;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            text-align:center;
            line-height:30px;
            margin:80px 10px;
            cursor:pointer;
        }
        .child-parrent{
            width:155px;
            float:right;
            margin-top:10px;
        }
        .child-parrent>div{
            width:100%;
            height:125px;
            border:1px solid #ddd;
            margin-bottom:10px;
        }
        .child-parrent .child-parrent-title{
            height:25px;
            padding:0 10px;
            border-bottom:1px solid #ddd;
        }
        .child-parrent .child-parrent-title span{
            margin-top:5px;
        }
        .child-parrent .child-parrent-title span:nth-child(1){
            float:left;
        }
        .child-parrent .child-parrent-title span:nth-child(2){
            float:right;
        }
        .child-parrent .allTeacher{
            padding:5px 10px;
            height:88px;
            overflow-y:auto;
        }
        .fenpeiTeacherBtn{
            width:200px;
            margin:20px auto;
        }
        .fenpeiTeacherBtn .anniu{
            display:inline-block;
            width:58px;
            height:27px;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            text-align:center;
            line-height:27px;
            background-color:#428bca;
            color:#fff;
            font-size:14px;
        }
        .fenpeiTeacherBtn span{
            margin-left:30px;
        }
     
        .select{
            color:#fff;
            background-color:#428bca;
        }
        .allTeacher span{
            display:inline-block;
            width:80px;
        }
        .allTeacher img{
            vertical-align: bottom;
            display:none;
        }
        .allTeacher div:hover img{
            display:inline-block;
        }
        .list-items1{
            display:none;
        }
        .list-items2{
            display:none;
        }
        
      
       
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
        width: 90%;
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
    </style>

<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
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
</small>
  <span> 班级管理 </span>
</div>

<div style="height: 100%;" class="clearfix">
    <!-- Nav tabs -->
    <ul class="nav nav-tab vertical-tab" role="tablist" id="vtab">
        <li role="presentation" class="active">
            <a href="#vocationalCourseGrade" aria-controls="vocationalCourseGrade" class="vocationalCourseGrade_bx" role="tab"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">班级管理</a>
        </li>
        <%--<li role="presentation">
            <a href="#microCourseGradeBox" aria-controls="microCourseGradeBox" class="microCourseGrade_bx" role="tab"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">微课班级管理</a>
        </li>--%>
    </ul>
        <!-- Tab panes -->
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="vocationalCourseGrade">
			<div class="mainrighttab tabresourse bordernone" id="vocationalCourseGradeDiv">
				<p class="col-xs-3" style="padding: 0;width:20%">
					<button class="btn btn-sm btn-success add_bx" title="新增班级">
						<i class="glyphicon glyphicon-plus"></i> 新增班级
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
			                            <input type="text"   id="sname" class="propertyValue1"  placeholder = "班级名称" style="width: 115px;"/>
			                            <input type="hidden" value="sname" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="menuId"    class="propertyValue1" style="width:55px">
			                                <option  value="-1" >学科</option>
			                                <c:forEach var="m" items="${menus}">
			                                    <option value="${m.id}">${m.name}</option>
			                                </c:forEach>
			                            </select>
			                            <input type="hidden" value="menuId" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="scoreTypeId" style="width:78px"   class="propertyValue1" onchange="addTitle(this)">
			                                <option value="-1" >课程类别</option>
			                                <c:forEach var="st" items="${scoreTypes}">
			                                    <option value="${st.id}">${st.name}</option>
			                                </c:forEach>
			                            </select>
			                            <input type="hidden" value="scoreTypeId" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="teachMethodIdSearch"  style="width:78px"  class="propertyValue1" >
			                                <option value="-1">授课方式</option>
			                                <c:forEach var="tm" items="${teachMethods}">
			                                    <option value="${tm.id}">${tm.name}</option>
			                                </c:forEach>
			                            </select>
			                            <input type="hidden" value="teachMethodIdSearch" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="courseId"    class="propertyValue1" style="width:80px;" onchange="addTitle(this)">
			                                <option value="-1">课程名称</option>
			                                <c:forEach var="c" items="${courses}">
			                                    <option value="${c.id}">${c.courseName}</option>
			                                    <%-- <option style="width:140px;" value="${c.id}">${c.courseName}</option> --%>
			                                </c:forEach>
			                            </select>
			                            <input type="hidden" value="courseId" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="gradeStatus" style="width:76px"   class="propertyValue1" >
			                                <option value="-1">开班状态</option>
			                                <option value="1">已开班</option>
			                                <option value="0">未开班</option>
			                            </select>
			                            <input type="hidden" value="gradeStatus" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text" class="datetime-picker"  id="sstart_time"  placeholder = "开课时间" style="width: 76px" readonly="readonly"/>
			                            <input type="hidden" value="curriculum_time" class="propertyValue1"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            	至
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text" class="datetime-picker"  id="sstop_time"  placeholder = "结课时间" style="width: 76px" readonly="readonly"/>
			                            <input type="hidden" value="stop_time" class="propertyValue1"/>
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
						<table id="cloudClassTable"
							class="table table-striped table-bordered table-hover">
						</table>
					</div>
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane" id="microCourseGradeBox">
			<div class="mainrighttab tabresourse bordernone" id="vocationalCourseGradeDiv">
				<div class="searchDivClass" id="searchMicroDiv">
			        <div class="profile-info-row" >
			
			            <table frame=void >
			                <tr>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="menuIdMicro"    class="propertyValue1" >
			                                <option  value="-1" >学科</option>
			                                <c:forEach var="m" items="${menus}">
			                                    <option value="${m.id}">${m.name}</option>
			                                </c:forEach>
			                            </select>
			                            <input type="hidden" value="menuIdMicro" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="scoreTypeIdMicro" class="propertyValue1" >
			                                <option value="-1" >课程类别</option>
			                                <c:forEach var="st" items="${scoreTypes}">
			                                    <option value="${st.id}">${st.name}</option>
			                                </c:forEach>
			                            </select>
			                            <input type="hidden" value="scoreTypeIdMicro" class="propertyName"/>
			                        </div>
			                    </td>
			                    <%-- <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="teachMethodIdSearchMicro"  class="propertyValue1" >
			                                <option value="-1">授课方式</option>
			                                <c:forEach var="tm" items="${teachMethods}">
			                                    <option value="${tm.id}">${tm.name}</option>
			                                </c:forEach>
			                            </select>
			                            <input type="hidden" value="teachMethodIdSearchMicro" class="propertyName"/>
			                        </div>
			                    </td> --%>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <select id="courseIdMicro"    class="propertyValue1">
			                                <option value="-1" >微课课程名称</option>
			                                <c:forEach var="c" items="${coursesMicro}">
			                                    <option value="${c.id}">${c.courseName}</option>
			                                    <%-- <option style="width:140px;" value="${c.id}">${c.courseName}</option> --%>
			                                </c:forEach>
			                            </select>
			                            <input type="hidden" value="courseIdMicro" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <div class="profile-info-value searchTr">
			                            <input type="text"   id="snameMicro" class="propertyValue1"  placeholder = "班级名称"/>
			                            <input type="hidden" value="snameMicro" class="propertyName"/>
			                        </div>
			                    </td>
			                    <td>
			                        <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
			                                onclick="searchMicro();">
			                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
			                        </button>
			                    </td>
			                </tr>
			            </table>
			        </div>
			    </div>
			
			
				<div class="row">
					<div class="col-xs-12">
						<table id="gradeMicroTable"
							class="table table-striped table-bordered table-hover">
							<colgroup>
								<col width='5%'></col>
							    <col width='15%'></col>
							    <col width='15%'></col>
							    <col width='10%'></col>
							    <col width='10%'></col>
							    <col width='10%'></col>
							    <col width='7%'></col>
							    <col width='7%'></col>
							    <col width='7%'></col>
							    <col width='14%'></col>
							</colgroup>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- <div class="mainrighttab tabresourse bordernone">
    <p class="col-xs-4" style="padding: 0;">
        <button class="btn btn-sm btn-success add_bx"><i class="glyphicon glyphicon-plus"></i> 新增班级</button>
        <button class="btn btn-sm btn-success del_bx"><i class="glyphicon glyphicon-trash"></i> 批量删除</button>
    </p>
    <div class="col-xs-12 paddingright0px text-right" id="searchDiv">
        <div class="searchTr">
                 <span>
                    <input type="text"   id="sname" class="propertyValue1"  placeholder = "班级名称"/>
                    <input type="hidden" value="name" class="propertyName"/>
                </span>&nbsp;&nbsp;
                <span>
                   <input type="text" class="datetime-picker"  id="sstart_time"  placeholder = "开课时间"/>
                    <input type="hidden" value="curriculum_time" class="propertyValue1"/>
                </span>
                &nbsp;至 &nbsp;
                <span>
                    <input type="text" class="datetime-picker"  id="sstop_time"  placeholder = "结课时间"/>
                    <input type="hidden" value="stop_time" class="propertyValue1"/>
                </span>

                <button class=" btn btn-sm  btn-primary" onclick="search()"><span class="glyphicon glyphicon-search"></span></button>
        </div>
    </div>
  <div class="row">
    <div class="col-xs-12">
      <table id="cloudClassTable" class="table table-striped table-bordered table-hover"></table>
    </div>
  </div>
</div> -->

<!-- 增加form -->
<div id="dialogGradeDiv"></div>
<div id="gradeDialog" class="hide">
  <form action="" method="post" class="form-horizontal" role="form" id="grade-form">
        <input type="hidden"  name="classTemplate" id="classTemplateId"/>
        <input type="hidden"  name="courseType" id="courseType"/>
      <div class="space-4"></div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 所属学科: </label>
          <div class="col-sm-9">
              <select name="menuId"   id="menuIdId"   class="propertyValue1 col-xs-8 {required:true}" onchange="courseList();">
               	<option value="">请选择</option>
                  <c:forEach var="m" items="${menus}">
                      <option value="${m.id}">${m.name}</option>
                  </c:forEach>
              </select>
          </div>
      </div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 课程类别: </label>
          <div class="col-sm-9">
              <select name="scoreTypeId" id="scoreTypeIdId" class="propertyValue1 col-xs-8 {required:true}" onchange="courseList();">
               <option value="">请选择</option>
                  <c:forEach var="st" items="${scoreTypes}">
                      <option value="${st.id}">${st.name}</option>
                  </c:forEach>
              </select>
          </div>
      </div>
      <div class="space-4"></div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 所属课程: </label>
          <div class="col-sm-7">
              <select name="courseId" id="courseIdId" class="propertyValue1 col-xs-8 {required:true}" onchange="teachMethodInfo();">
                   <option value="">请选择</option>
                  <%-- <c:forEach var="co" items="${courses}">
                      <option value="${co.id}">${co.courseName}</option>
                  </c:forEach> --%>
              </select>
              <label class=" control-label no-padding-right" id="teachMethodId"></label>
          </div>
      </div>
     <div class="form-group" style="margin-top: 18px;">
         <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 班级名称: </label>

         <div class="col-sm-9">
         <!-- <span id="firstNameNumber"></span> -->
         <label class=" control-label no-padding-right" style="float: left" id="classTemplateShowId"></label><input type="text" style="width : 8%" name="nameNumber" id="nameNumber"  maxlength="3" class=" {required:true,digits:true}">
         <label class=" control-label no-padding-right" id="lastNameNumber">期</label>
         </div>
     </div>
     <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"> <i class="text-danger">*</i> 班级额定人数: </label>

          <div class="col-sm-9">
          <!-- <span id="firstNameNumber"></span> -->
          <input type="text" name="studentAmount" id="studentAmount"  maxlength="5" class=" col-sm-8 {required:true,digits:true,range:[1,99999]}">
          </div>
      </div>
     <!-- <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"> <i class="text-danger">*</i> 默认报名人数: </label>

          <div class="col-sm-9">
          <input type="text" name="defaultStudentCount" id="defaultStudentCount"  maxlength="5" class=" col-sm-8 {required:true,digits:true,range:[1,99999],lessAmount:true}" placeholder="请填写一个基数，统计的时候加上这个基数">
          </div>
      </div> -->
     <!-- <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"> 学习计划规则: </label>
          <div class="col-sm-1" style="padding-top: 7px;width: 10px;">
          	上
          </div>
          <div class="col-sm-2" style="padding-right: 0px;margin-right: 0px;">
          		<input type="text" name="workDaySum" id="workDaySum"  maxlength="2" class=" col-sm-8 {syncRequiredDay:true,digits:true,range:[1,99],lessThenTeachingDays:true}">
          </div>
          <div class="col-sm-1"  style="padding-top: 7px;width: 10px;margin-left: -33px;">
          	天
          </div>
          <div class="col-sm-1"  style="padding-top: 7px;">
          	休息
          </div>
          <div class="col-sm-2"  style="margin-left: -18px;">
          		<input type="text" name="restDaySum" id="restDaySum"  maxlength="2" class=" col-sm-8 {syncRequiredDay:true,digits:true,range:[1,99]}">
          </div>
          <div class="col-sm-1"  style="padding-top: 7px;width: 10px;margin-left: -33px;">
          	天
          </div>
      </div>
    <div class="space-4"></div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"> 授课天数: </label>
          <div class="col-sm-7" style="width: 22%;">
              <input type="text" name="teachingDays"  id="teachingDays" maxlength="20" readonly="readonly" disabled="disabled">
          </div>
          <div class="col-sm-1" style="padding-top: 7px;">
              	天
          </div>
      </div> -->
    <div class="space-4"></div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 班级Q群: </label>
          <div class="col-sm-9">
              <input type="text" name="qqno"  id="qqno" maxlength="20"  class="col-sm-8 {required:true,qq:true}">
          </div>
      </div>
    <div class="space-4"></div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 开课时间: </label>
          <div class="col-sm-9">
              <input type="text" name="curriculumTime"  id="curriculum_time" maxlength="20" readonly="readonly"  class="datetime-picker  col-sm-8 {required:true,date:true,rangelength:[10,19]}">
          </div>
      </div>
    <div class="space-4"></div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i>  结课时间: </label>
          <div class="col-sm-9">
              <input type="text" name="stopTime" id="stop_time"  maxlength="20" readonly="readonly" class="datetime-picker  col-sm-8 {required:true,date:true,rangelength:[10,19]}">
          </div>
      </div>
  </form>
</div>

<!-- 修改form -->
<div id="updateGradeDiv"></div>
<div id="updateDialog" class="hide">
    <form action="" method="post" class="form-horizontal" role="form" id="update-form">
        <input type="hidden" name="id" id="gradeId_update" />
        <input type="hidden"  name="classTemplate" id="classTemplateId_update"/>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"> <i class="text-danger">*</i> 所属学科: </label>
            <div class="col-sm-9">
                <select name="menuId"   id="menuIdId_update"   class="propertyValue1 col-xs-8 {required:true}" onchange="courseList_update();">
                    <option value="">请选择</option>
                    <c:forEach var="m" items="${menus}">
                        <option value="${m.id}">${m.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"> <i class="text-danger">*</i> 课程类别: </label>
            <div class="col-sm-9">
                <select name="scoreTypeId" id="scoreTypeIdId_update" class="propertyValue1 col-xs-8 {required:true}" onchange="courseList_update();">
                    <option value="">请选择</option>
                    <c:forEach var="st" items="${scoreTypes}">
                        <option value="${st.id}">${st.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"> <i class="text-danger">*</i> 所属课程: </label>
            <div class="col-sm-7">
                <select name="courseId" id="courseIdId_update" class="propertyValue1 col-xs-8 {required:true}" onchange="teachMethodInfo_update();">
                    <option value="">请选择</option>
                    <%-- <c:forEach var="co" items="${courses}">
                        <option value="${co.id}">${co.courseName}</option>
                    </c:forEach> --%>
                </select>
                <p id="teachMethodId_update"></p>
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 班级名称: </label>

            <div class="col-sm-9">
                <!-- <span id="firstNameNumber_update"></span> -->
                <label class="control-label no-padding-right" style="float: left" id="classTemplateShowId_update"></label><input type="text" style="width : 8%" name="nameNumber" id="nameNumber_update"  maxlength="3" class=" {required:true,digits:true}">
                <label class="control-label no-padding-right" id="lastNameNumber_update">期</label>
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 班级额定人数: </label>

          <div class="col-sm-9">
          <!-- <span id="firstNameNumber"></span> -->
          <input type="text" name="studentAmount" id="studentAmount_update"  maxlength="5" class=" col-sm-8 {required:true,digits:true,range:[1,99999]}">
          </div>
      </div>
        <!-- <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 默认报名人数: </label>

          <div class="col-sm-9">
          <span id="firstNameNumber"></span>
          <input type="text" name="defaultStudentCount" id="defaultStudentCount_update"  maxlength="5" class=" col-sm-8 {required:true,digits:true,range:[1,99999],lessAmount:true}"  placeholder="请填写一个基数，统计的时候加上这个基数">
          </div>
      </div> -->
      <!-- <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right">学习计划规则: </label>
          <div class="col-sm-1" style="padding-top: 7px;width: 10px;">
          	上
          </div>
          <div class="col-sm-2" style="padding-right: 0px;margin-right: 0px;">
          		<input type="text" name="workDaySum" id="workDaySum_update"  maxlength="2" class="col-sm-8 {syncRequiredDay:true,digits:true,range:[1,99],lessThenTeachingDays:true}">
          </div>
          <div class="col-sm-1"  style="padding-top: 7px;width: 10px;margin-left: -33px;">
          	天
          </div>
          <div class="col-sm-1"  style="padding-top: 7px;">
          	休息
          </div>
          <div class="col-sm-2"  style="margin-left: -18px;">
          		<input type="text" name="restDaySum" id="restDaySum_update"  maxlength="2" class="col-sm-8 {syncRequiredDay:true,digits:true,range:[1,99]}">
          </div>
          <div class="col-sm-1"  style="padding-top: 7px;width: 10px;margin-left: -33px;">
          	天
          </div>
      </div>
    <div class="space-4"></div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"> 授课天数: </label>
          <div class="col-sm-7" style="width: 22%;">
              <input type="text" name="teachingDays"  id="teachingDays_update" maxlength="20" readonly="readonly" disabled="disabled">
          </div>
          <div class="col-sm-1" style="padding-top: 7px;">
              	天
          </div>
      </div> -->
    <div class="space-4"></div>
      <div class="form-group" style="margin-top: 18px;">
          <label class="col-sm-3 control-label no-padding-right"> <i class="text-danger">*</i> 班级Q群: </label>
          <div class="col-sm-9">
              <input type="text" name="qqno"  id="qqno_update" maxlength="20"  class="col-sm-8 {required:true,qq:true}">
          </div>
      </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 开课时间: </label>
            <div class="col-sm-9">
                <input type="text" name="curriculumTime"  id="curriculum_time_update" maxlength="20"  class="datetime-picker  col-sm-8 {required:true,date:true,rangelength:[10,19],disableEdit:true}">
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 结课时间: </label>
            <div class="col-sm-9">
                <input type="text" name="stopTime" id="stop_time_update"  maxlength="20" class="datetime-picker  col-sm-8 {required:true,date:true,rangelength:[10,19]}">
            </div>
        </div>
    </form>
</div>



<!-- teacher -->
<div id="dialogTeacherGradeDiv" class="clearfix"></div>
<div id="gradeTeacherDialog" class="hide" style="width:550px;height:455px"">
    <form action="" method="post" class="form-horizontal" role="form" id="grade-teacher-form">
        <input type="hidden" id="teachers_grade_id" name="gradeId" />
        <input type="hidden" id="teachers_course_id" name="courseId" />
        <input type='hidden' id="jiangshiInput" name='roleType1' class='{required:true}' > 
        <input type='hidden' id="banzhurenInput" name='roleType2' class='{required:true}' > 
         <input type='hidden' id="zhujiaoInput" name='roleType3' class='{required:true}' >
        <!-- <div class="form-group" style="margin-top:15px">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 学科: </label>
            <div class="col-sm-9">
                <p id="teachers_courseName" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group" style="margin-top:15px">
            <label style="margin-top:-5px" class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 讲师: </label>
            <div class="col-sm-9" id="teachers_roleType1">
            </div>
        </div>
        <div class="form-group" style="margin-top:15px">
            <label style="margin-top:-5px" class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 班主任: </label>
            <div class="col-sm-9" id="teachers_roleType2">
            </div>
        </div>
        <div class="form-group" style="margin-top:15px">
            <label style="margin-top:-5px" class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 助教: </label>
            <div class="col-sm-9" id="teachers_roleType3">
            </div>
        </div> -->
		<div class="fenpeiTeacherAbove">
			<div class="fengpeiTeacher-content clearfix">
				<%-- <div class="fengpeiSearch">
					<input class="fenpeiTeacherSearch" type="text"
						placeholder="老师姓名、拼音、首字母搜索" /> <img class="fenpeiTeacherImg"
						src="${base}/images/mynote-search.png" alt="" />
				</div> --%>
				<div class="fenpeiList">
					<div class="fenpeiList-content">
						<span class="xuekename">学科：</span>
						<ul class="xuekeleibei">
							<li><div class="xuekeleibeiID"><em id="xuekeleibeiId" class="rotateCaret"></em><span id="teachers_courseName" class="tagname">UI</span></div>
								<ul class="list-items1">
									<li><span class="tagname"><span class="rotateCaret" id="jiaoshiSanjiao"></span>讲师</span>
										<ul id="jiangshiLi" class="list-items2">
											
										</ul></li>
									<li><span class="tagname"><span class="rotateCaret" id="zhujiaoSanjiao"></span>助教</span>
										<ul id="zhujiaoLi" class="list-items2">
											
										</ul></li>
									<li><span class="tagname"><span class="rotateCaret" id="banzhurenSanjiao"></span>班主任</span>
										<ul id="banzhurenLi" class="list-items2">
											
										</ul></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<ul class="fenpeirow">
					<li class="row-first">-></li>
					<li class="row-second">-></li>
					<li class="row-third">-></li>
				</ul>
				<div class="child-parrent clearfix">
					<div class="teacher">
						<div class="child-parrent-title">
							<span>讲师</span> <span class="clear">清空</span>
						</div>
						<div class="allTeacher"></div>
					</div>
					<div class="zhujiao">
						<div class="child-parrent-title">
							<span>助教</span> <span class="clear">清空</span>
						</div>
						<div class="allTeacher"></div>
					</div>
					<div class="banzhuren">
						<div class="child-parrent-title">
							<span>班主任</span> <span class="clear">清空</span>
						</div>
						<div class="allTeacher"></div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<!-- 详情页面form -->
<div id="showGradeDiv"></div>
<div id="showDialog" class="hide">
    <form class='form-horizontal'>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 所属学科: </span></label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  id="menuName_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 课程类别:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="name" id="scoreTypeName_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 所属课程: </span></label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="name" id="courseName_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 班级名称:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="name" id="name_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <!-- <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 学习计划规则:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="plan" id="plan_show" class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div> -->
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 班级Q群:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="qqno" id="qqno_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"> <i class="text-danger">*</i> 班级额定人数:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="studentAmount" id="studentAmount_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <!-- <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"> <i class="text-danger">*</i> 累计已报名人数:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  id="totalStudentCount_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div> -->
	      <!-- div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"> <i class="text-danger">*</i> 默认已报名人数:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7" id="defaultStudentCount_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div> -->
   		<div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"> <i class="text-danger">*</i> 已报名人数:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  id="studentCount_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
	<!-- <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 讲师: </span></label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  id="role_type1_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div> -->
        <!-- <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 班主任 : </span></label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  id="role_type2_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 助教 :</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="name" id="role_type3_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div> -->
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 开课日期:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="name" id="curriculumTime_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" ><span style="font-weight: bold;"><i class="text-danger">*</i> 结课日期:</span> </label>
            <div class="col-sm-5">
                <label class="paddingtop7px padding7"  name="name" id="stopTime_show"  class=" col-sm-8 {required:true,rangelength:[2,20]}"></label>
            </div>
        </div>
    </form>
</div>



<!--权限配置  -->
<div id="dialogConfigDiv"></div>
<div id="configDialog" class="hide">
  <table style="width:100%">
    <tr>
      <td style="vertical-align: top;">
        <div style="text-align: center"><label for="form-field-select-2">选择资源</label></div>
        <div class="contrightbox"
             style="overflow-y: auto; overflow-x: auto;height: 300px;min-width:240px;">
          <div class="zTreeDemoBackground">
            <ul id="resource" class="ztree"
                style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
          </div>
        </div>
      </td>
      <td style="vertical-align: top;">
        <div>
          <div style="text-align: center"><label for="form-field-select-2">已选择资源</label></div>
          <div>
            <div class="contrightbox"
                 style="overflow-y: auto; overflow-x: auto;height: 300px;min-width:240px;">

              <div class="zTreeDemoBackground">
                <ul id="resource2" class="ztree"
                    style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
              </div>
            </div>
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>

<script type="text/javascript" src="${base}/js/cloudClass/grade.js?v=1"></script>