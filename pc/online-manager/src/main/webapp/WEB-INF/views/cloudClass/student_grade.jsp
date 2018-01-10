<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
</script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 学员管理 </span><i class="ace-icon fa fa-angle-double-right"> </i> <span> 班级列表 </span>
</div>


<div class="mainrighttab tabresourse bordernone">

	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void >
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                           <input type="text"   id="sname" class="propertyValue1"  placeholder = "班级名称/老师姓名"  style="width:120px"/>
                    		<input type="hidden" value="sname" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                       <div class="profile-info-value searchTr">
                              <select id="menuId"    class="propertyValue1" >
			                      <option value="-1" >学科</option>
			                      <c:forEach var="m" items="${menus}">
			                          <option value="${m.id}" >${m.name}</option>
			                      </c:forEach>
			                  </select>
                    		 <input type="hidden" value="menuId" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                       <div class="profile-info-value searchTr">
                           <select id="scoreTypeId"    class="propertyValue1" style="width:78px" >
		                      <option value="-1">课程类别</option>
		                      <c:forEach var="st" items="${scoreTypes}">
		                          <option value="${st.id}">${st.name}</option>
		                      </c:forEach>
		                 	</select>
                    		<input type="hidden" value="scoreTypeId" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                       <div class="profile-info-value searchTr">
                           <select id="teachMethodId"    class="propertyValue1"  style="width:78px">
		                      <option value="-1">授课方式</option>
		                      <c:forEach var="tm" items="${teachMethods}">
		                          <option value="${tm.id}">${tm.name}</option>
		                      </c:forEach>
		                   </select>
                    	   <input type="hidden" value="teachMethodId" class="propertyName"/>
                        </div>
                    </td>
                     <td>
                       <div class="profile-info-value searchTr">
                           <select id="courseId"    class="propertyValue1" style="width:160px;">
		                      <option value="-1">课程名称</option>
		                      <c:forEach var="c" items="${courses}">
		                          <option value="${c.id}">${c.courseName}</option>
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
                           <input type="text" class="datetime-picker"  id="sstart_time" readonly="readonly" placeholder = "开课时间" style="width:75px"/>
                   		   <input type="hidden" value="curriculum_time" class="propertyValue1"/>
                        </div>
                    </td>
                    <td>
                      <div class="profile-info-value searchTr">
                       &nbsp;至 &nbsp;
                       </div>
                    </td>
                    <td>
                       <div class="profile-info-value searchTr">
                          <input type="text" class="datetime-picker"  id="sstop_time" readonly="readonly" placeholder = "结课时间" style="width:75px"/>
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

<%-- <div class="mainrighttab tabresourse bordernone">
    <div  id="searchDiv">
        <div class="searchTr">
                 <span>
                    <input type="text"   id="sname" class="propertyValue1"  placeholder = "班级名称/老师姓名"/>
                    <input type="hidden" value="sname" class="propertyName"/>
                </span>&nbsp;&nbsp;
                 <span>
                  <select id="menuId"    class="propertyValue1" >
                      <option value="-1">学科</option>
                      <c:forEach var="m" items="${menus}">
                          <option value="${m.id}">${m.name}</option>
                      </c:forEach>
                  </select>
                    <input type="hidden" value="menuId" class="propertyName"/>
                </span>&nbsp;&nbsp;
                 <span>
                  <select id="scoreTypeId"    class="propertyValue1" >
                      <option value="-1">课程类别</option>
                      <c:forEach var="st" items="${scoreTypes}">
                          <option value="${st.id}">${st.name}</option>
                      </c:forEach>
                  </select>
                    <input type="hidden" value="scoreTypeId" class="propertyName"/>
                </span>&nbsp;&nbsp;
                 <span>
                  <select id="teachMethodId"    class="propertyValue1" >
                      <option value="-1">授课方式</option>
                      <c:forEach var="tm" items="${teachMethods}">
                          <option value="${tm.id}">${tm.name}</option>
                      </c:forEach>
                  </select>
                    <input type="hidden" value="teachMethodId" class="propertyName"/>
                </span>&nbsp;&nbsp;
                 <span>
                  <select id="courseId"    class="propertyValue1" >
                      <option value="-1">课程名称</option>
                      <c:forEach var="c" items="${courses}">
                          <option value="${c.id}">${c.courseName}</option>
                      </c:forEach>
                  </select>
                    <input type="hidden" value="courseId" class="propertyName"/>
                </span>&nbsp;&nbsp;
                 <span>
                  <select id="gradeStatus"    class="propertyValue1" >
                      <option value="-1">开班状态</option>
                      <option value="1">已开班</option>
                      <option value="0">未开班</option>
                  </select>
                    <input type="hidden" value="gradeStatus" class="propertyName"/>
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
</div> --%>

<script type="text/javascript" src="${base}/js/cloudClass/student_grade.js?v=1.7"></script>