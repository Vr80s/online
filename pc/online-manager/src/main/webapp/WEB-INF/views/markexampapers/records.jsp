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
			${grade_name} <small><i class="ace-icon fa fa-angle-double-right"></i></small>
			${barrier_name} <small><i class="ace-icon fa fa-angle-double-right"></i></small>
			学员列表
			<span style="float:right;margin-right:10px">
                <button type="button" class="btn btn-sm" onclick="goBack('${grade_id}','${grade_name}')">
                    <i class="fa fa-arrow-left">返回</i>
                </button>
            </span>
		</div>
	</div>
</div>


<input type="hidden" value="${grade_id}" id="grade_id">
<input type="hidden" value="${barrier_id}" id="barrier_id">
<input type="hidden" value="${barrier_name}" id="barrier_name">
<input type="hidden" value="${grade_name}" id="grade_name">
<div class="mainrighttab tabresourse bordernone">
	<div class="searchDivClass" id="searchDiv">
		<div class="profile-info-row" >
			<table frame=void >
				<tr>
					<td>
						<div class="profile-info-value searchTr">
							<label for="user_name" style="font-size: small;">学员名称：</label>
							<input type="text" id="user_name" class="propertyValue1" style="width:200px;" placeholder = "请输入学员名称"/>
							<input type="hidden" value="user_name" class="propertyName"/>
						</div>
					</td>
					<td>
						<div class="profile-info-value searchTr">
							<label for="barrier_status" style="font-size: small;">当前状态：</label>
							<select id="barrier_status" class="propertyValue1" style="width:100px;">
								<option value="-1">请选择...</option>
								<option value="1">已通过</option>
								<option value="0">未通过</option>
							</select>
							<input type="hidden" value="barrier_status" class="propertyName"/>
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
			<table id="recordTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="${base}/js/markexampapers/records.js?v=1"></script>