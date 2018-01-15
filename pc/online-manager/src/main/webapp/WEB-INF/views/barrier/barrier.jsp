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
  当前位置：学习闯关管理 <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
   <span> 关卡管理 </span>
   <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
   <span> 课程列表 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void >
                 <tr>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder = "课程名称" class="propertyValue1" id="search_courseName" style="width: 150px;">
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
                            <select name="" id="" value="" class="propertyValue1" >
			               		<option value="">业务类别</option>
		                        <option value="">微课</option>
		                        <option value="">职业课</option>
			               </select>
                            <input type="hidden" value="" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                       <div class="profile-info-value searchTr">
                            <select name="courseTypeId" id="search_scoreType" value="" class="propertyValue1" >
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
                            <select name="search_courseType" id="search_courseType" value="" class="propertyValue1" >
			               		<option value="">授课方式</option>
			               		<c:forEach var="teachMethods" items="${teachMethodVo}">
			                        <option value="${teachMethods.id}">${teachMethods.name}</option>
			                    </c:forEach>
			               </select>
                            <input type="hidden" value="search_courseType" class="propertyName"/>
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
			<table id="barrierTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="${base}/js/barrier/barrier.js?v=ipandatcm_1.3"></script>
