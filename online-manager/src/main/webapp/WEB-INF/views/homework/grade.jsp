<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	
<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：作业管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 班级列表 </span>
</div>

<div style="height: 100%;" class="clearfix">
    <div class="mainrighttab tabresourse bordernone" id="vocationalCourseGradeDiv">
        <div class="searchDivClass" id="searchDiv">
            <div class="profile-info-row" >
                <table frame=void >
                    <tr>
                        <td>
                            <div class="profile-info-value searchTr">
                                <select id="courseId"    class="propertyValue1" style="width:200px;">
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
                                <input type="text"   id="courseName" class="propertyValue1"  placeholder = "班级名称" style="width: 125px;"/>
                                <input type="hidden" value="courseName" class="propertyName"/>
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
<script type="text/javascript" src="/js/homework/grade.js?v=ipandatcm_1.3"></script>