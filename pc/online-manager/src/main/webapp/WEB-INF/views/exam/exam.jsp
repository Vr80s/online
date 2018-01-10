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
  当前位置：组卷管理  <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
   <span> 课程列表 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void >
                 <tr>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder = "请输入课程名称" class="propertyValue1" id="search_courseName">
                            <input type="hidden" value="search_courseName" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                       <div class="profile-info-value searchTr">
                            <select name="menuName" id="search_menu" class="propertyValue1"  >
			               		<option value="">选择学科</option>
			               		<c:forEach var="menus" items="${menuVo}">
			                        <option value="${menus.id}">${menus.name}</option>
			                    </c:forEach>
			               </select>
                            <input type="hidden" value="search_menu" class="propertyName"/>
                        </div>
                    </td>
                    <td>
						<div class="profile-info-value searchTr">
							<select name="search_status" id="search_status" class="propertyValue1" >
								<option value="">课程状态</option>
								<option value="1">已启用</option>
								<option value="0">已禁用</option>
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
            </table>
        </div>
    </div>

	<div class="row">
		<div class="col-xs-12">
			<table id="examTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="${base}/js/exam/exam.js?v=1.7"></script>
