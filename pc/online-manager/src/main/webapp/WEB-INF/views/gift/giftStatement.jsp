<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="${base}/js/layer/skin/layer.css" type="text/css" />	
<style type="text/css">
#showOrderTable td{
	text-align: left;
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
</script>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：销售管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 订单管理 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void >
                <tr>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker propertyValue1"  id="startTime" name="startTime" placeholder = "起始时间" />
                            <input type="hidden" value="startTime" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value">
                            &nbsp;至 &nbsp;
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker propertyValue1"  id="stopTime" name="stopTime" placeholder = "结束时间"/>
                            <input type="hidden" value="stopTime" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                    <div class="profile-info-value searchTr">
                            <select id="searchType" name="searchType" class="propertyValue1">
                                <option  value="1" >礼物名称</option>
                                <option  value="2" >赠送人</option>
                                <option  value="3" >收到人</option>
                                <option  value="4" >订单编号</option>
                            </select>
                             <input type="hidden" value="searchType" class="propertyName" />
                        </div>
                    </td>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="searchCondition" name="searchCondition"  class="propertyValue1"  placeholder = "关键字" maxlength="30"/>
                            <input type="hidden" value="searchCondition" class="propertyName"/>
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
			<table id="orderTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>


<script type="text/javascript" src="${base}/js/gift/giftStatement.js?v=1.7"></script>
