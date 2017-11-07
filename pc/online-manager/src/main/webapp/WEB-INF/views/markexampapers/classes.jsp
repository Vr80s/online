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
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
    <div class="row">
        <div style="margin-left:10px">
          当前位置：学习闯关管理 <small><i class="ace-icon fa fa-angle-double-right"></i></small>
          批阅试卷 <small><i class="ace-icon fa fa-angle-double-right"></i></small>
          班级列表
        </div>
    </div>
</div>


<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void style="width: 100%">
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                            <label for="course_id" style="font-size: small;">课程名称：</label>
                            <select id="course_id" class="propertyValue1" style="width:250px;">
                                <option value="-1">请选择...</option>
                                <c:forEach var="c" items="${courses}">
                                    <option value="${c.id}">${c.courseName}</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" value="course_id" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <label for="grade_name" style="font-size: small;">班级名称：</label>
                            <input type="text" id="grade_name" class="propertyValue1" style="width:200px;" placeholder = "请输入班级名称"/>
                            <input type="hidden" value="grade_name" class="propertyName"/>
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

<script type="text/javascript" src="${base}/js/markexampapers/classes.js?v=1"></script>