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
  当前位置：云课堂管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 评价管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
		<button class="btn btn-sm btn-success bj_num" title="好评率从高到低排列" onclick="search(2)">
			好评率↓
		</button>
		<button class="btn btn-sm btn-success bj_num" title="评价条数从高到低排列" onclick="search(1)">
			评价条数↓
		</button>
	</p>
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
                </tr>
            </table>
        </div>
    </div>

	<div class="row">
		<div class="col-xs-12">
			<table id="criticizeTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>
<script type="text/javascript" src="${base}/js/cloudClass/criticize.js?v=ipandatcm_1.3"></script>
