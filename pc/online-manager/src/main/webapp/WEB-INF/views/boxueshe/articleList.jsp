<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script type="text/javascript" src="${base}/js/boxueshe/articleList.js?v=1"></script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：博学社管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span>文章管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-3" style="padding: 0;width:20%">
		<button class="btn btn-sm btn-success add_bx" title="新增文章">
			<i class="glyphicon glyphicon-plus"></i> 新增文章
		</button>
		<button class="btn btn-sm btn-success dele_bx" title="批量删除">
			<i class="glyphicon glyphicon-trash"></i> 批量删除
		</button>
	</p>
	
	<!-- 条件查询 -->
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
        	 <table frame=void style="width: 100%">
                <tr>
                   <td>
	                   <div class="profile-info-value searchTr">
		                    <input type="text" placeholder = "关键词" class="propertyValue1" id="search_title" style="width: 150px;">
					        <input type="hidden" value="search_title" class="propertyName"/>
	                  	</div>
                   </td>
                   <td>
	              		<div class="profile-info-value searchTr">
	              			<select name="menuName" id="search_type" value="" class="propertyValue1"  >
			               		<option value="">分类</option>
			               		<c:forEach var="type" items="${articleTypes}">
			                        <option value="${type.id}">${type.name}</option>
			                    </c:forEach>
			               </select>
                            <input type="hidden" value="search_type" class="propertyName"/>
	              		</div>
                   </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="statusSearch" name="status" class="propertyValue1">
                                <option  value="" >状态</option>
                                <option  value="0" >已禁用</option>
                                <option  value="1" >已启用</option>
                            </select>
                        	<input type="hidden" value="statusSearch" class="propertyName"/>
                        </div>
                    </td>
                    <td>
			            <div class="profile-info-value searchTr">
			                <select name="search_isRecommend" id="search_isRecommend" value="" class="propertyValue1" >
						        <option value="">是否推荐</option>
						        <option value="1">已推荐</option>
						        <option value="0">未推荐</option>
						    </select>
			                <input type="hidden" value="search_isRecommend" class="propertyName"/>
			            </div>
			       </td>
                   <td>
                    <div class="profile-info-value searchTr">
                        <input type="text" class="datetime-picker propertyValue1"  id="startTime" name="startTime" placeholder = "开始日期" style="width:130px"/>
                        <input type="hidden" value="startTime" class="propertyName"/>
                    </div>
                </td>
                <td>
                    <div class="profile-info-value">
                    	    至 
                    </div>
                </td>
                <td>
                    <div class="profile-info-value searchTr">
                        <input type="text" class="datetime-picker propertyValue1"  id="stopTime" name="stopTime" placeholder = "结束日期" style="width:130px"/>
                        <input type="hidden" value="stopTime" class="propertyName"/>
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
    
    <!-- 文章列表 -->
    <div class="row">
		<div class="col-xs-12">
			<table id="articleTable" class="table table-striped table-bordered table-hover">
			
			</table>
		</div>
	</div>    
</div>
