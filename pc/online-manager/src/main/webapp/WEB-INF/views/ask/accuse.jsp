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
  var weburl = '${weburl}';
  var mname = "${md5UserName}";
</script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：博问答管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 投诉管理 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

            <table frame=void >
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="accuseType" class="propertyValue1" >
                                <option  value="" >投诉类型</option>
                                <option  value="0" >广告营销</option>
                                <option  value="1" >抄袭内容</option>
                                <option  value="2" >人身攻击</option>
                                <option  value="3" >色情反动</option>
                                <option  value="4" >其他</option>
                            </select>
                        </div>
                        <input type="hidden" value="accuseType" class="propertyName"/>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <select id="status" class="propertyValue1">
                                <option  value="" >投诉状态</option>
                                <option  value="0" >未处理</option>
                                <option  value="1" >已删除</option>
                                <option  value="2" >无效投诉</option>
                            </select>
                        </div>
                        <input type="hidden" value="status" class="propertyName"/>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text"   id="content" class="propertyValue1"  placeholder = "内容关键字"/>
                            <input type="hidden" value="content" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker"  id="startTime"  placeholder = "开始日期" />
                            <input type="hidden" value="startTime" class="propertyValue1"/>
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            &nbsp;至 &nbsp;
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" class="datetime-picker"  id="stopTime"  placeholder = "结束日期"/>
                            <input type="hidden" value="stopTime" class="propertyValue1"/>
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
			<table id="accuseTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="${base}/js/ask/accuse.js?v=ipandatcm_1.3"></script>