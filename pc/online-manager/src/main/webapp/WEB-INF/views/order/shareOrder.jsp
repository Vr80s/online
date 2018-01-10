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
  当前位置：销售管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 佣金管理 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >
            <table frame=void >
                <tr>
                	<td>
                        <div class="profile-info-value">
                            <select id="selType" class="propertyValue1">
                                <option  value="0" >手机号</option>
                                <option  value="1" >邮箱</option>
                                <option  value="2" >用户名</option>
                                <option  value="3" >推荐人(用户名)</option>
                            </select>
                        </div>
                    </td>
                	<td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_mobile" name="search_mobile"  class="propertyValue1"  placeholder = "手机号" maxlength="30"/>
                            <input type="hidden" value="search_mobile" class="propertyName"/>
                        </div>
                    </td>
                	<td style="display:none">
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_email" name="search_email" class="propertyValue1"  placeholder = "邮箱" maxlength="30"/>
                            <input type="hidden" value="search_email" class="propertyName"/>
                        </div>
                    </td>
                	<td style="display:none">
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_createPersonName" name="search_createPersonName" class="propertyValue1"  placeholder = "用户名" maxlength="30"/>
                            <input type="hidden" value="search_createPersonName" class="propertyName"/>
                        </div>
                    </td>
                	<td style="display:none">
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="search_sharePersonName" name="search_sharePersonName" class="propertyValue1"  placeholder = "推荐人(昵称)" maxlength="30"/>
                            <input type="hidden" value="search_sharePersonName" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker propertyValue1"  id="startTime" name="startTime" placeholder = "注册时间(起)" />
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
                            <input type="text" class="datetime-picker propertyValue1"  id="stopTime" name="stopTime" placeholder = "注册时间(止)"/>
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


	<div class="row">
		<div class="col-xs-12">
			<table id="shareOrderTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>
<script type="text/javascript" src="${base}/js/order/shareOrder.js?v=1.7"></script>
