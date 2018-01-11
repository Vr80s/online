<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="js/feedback/index.js"></script>
<script src="${base}/js/layer/layer.js"></script>
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

<div class="page-header">
	当前位置：消息管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 意见反馈 </span>
</div>
<div style="width:100%;float:right">
		<div class="searchDivClass" id="searchDiv">
			<div class="profile-info-row" >

				<table frame=void >
					<tr>
						<td>
							<div class="profile-info-value searchTr">
								<select class="propertyValue1" id="q_answerStatus">
								<option value="-1">全部</option>
								<option value="0" >未回答</option>
								<option value="1" >已回答</option>
							</select>
								<input type="hidden" value="q_answerStatus" class="propertyName"/>
							</div>
						</td>
						<td>
							<div class="profile-info-value searchTr">
								<input type="text" class="datetime-picker" name="time_start" id="time_start"  placeholder = "开始创建时间"/>

							</div>
						</td>
						<td style="font-size: 12px">
							至
						</td>
						<td>
							<div class="profile-info-value searchTr">
								<input type="text" class="datetime-picker" name="time_end" id="time_end" placeholder = "结束创建时间"/>
							</div>
						</td>
						<td>
							<div class="profile-info-value searchTr">
								<%--<input type="text" id="q_title" placeholder = "意见标题" class="propertyValue1">--%>
								<%--<input type="hidden" value="q_title" class="propertyName"/>--%>
								<input type="hidden" value="5" class="tempMatchType"/><!-- 4等于 5包含 6小于 7大于  8小于等于  9大于等于  10之间 -->
								<input type="hidden" value="String" class="tempType"/><!-- 默认是String -->
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
</div>

<table id="feedbackTable"
	   class="table table-striped table-bordered table-hover">
</table>

	<!-- 修改or回复 -->
	<div id="dialogfeedBackDiv"></div>
	<div id="feedBackDialog" class="hide">
	<form class="form-horizontal" id="addfeedBack-form" method="post" action="">
		<input type="hidden" value="" id="feedId" name="id">
		<input type="hidden" value="" id="feedUserId" name="userId">
		<%--<div class="form-group row">--%>
			<%--<label class="control-label col-xs-12 col-sm-2 no-padding-right" ><font color="red">*</font>回答标题:</label>--%>
			<%--<div class="col-xs-12 col-sm-9">--%>
					<%--<input type="text" name="title" id="add_replytitle" maxlength="32" style="width:345px" class="{required:true}"/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-group row">
			<label class="control-label col-xs-12 col-sm-2 no-padding-right" ><font color="red">*</font>回答内容:</label>
			<div class="col-xs-12 col-sm-9">
					<textarea style="position: relative; resize: none;width:345px;font-size: 12px" maxlength="125" name="replytext" id="add_replytext"  class="{required:true}"  ></textarea>
			</div>
		</div>
		<%--<div class="form-group row">--%>
			<%--<label class="control-label col-xs-12 col-sm-2 no-padding-right"--%>
					<%-->意见标题:</label>--%>
			<%--<div class="col-lg-7 col-xs-9">--%>
				<%--<input type="text" disabled="disabled" id="add_title" style="width:345px"/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-group row">
			<label class="control-label col-xs-12 col-sm-2 no-padding-right"
					>创建时间:</label>
			<div class="col-lg-7 col-xs-9">
				<input type="text" disabled="disabled" id="add_createtimeStr" style="width:345px"/>
			</div>
		</div>
		<div class="form-group row">
			<label class="control-label col-xs-12 col-sm-2 no-padding-right"
					>意见描述:</label>
			<div class="col-lg-7 col-xs-9">
				<textarea cols="45" rows="5" id="add_context" class="" readonly="readonly"  style="width:345px;font-size: 12px"></textarea>
			</div>
		</div>
	</form>
	</div>

	<!-- 预览 -->
<div id="showfeedBackDialog" class="hide">
	<%--<div class="form-group row">--%>
		<%--<label class="control-label col-xs-12 col-sm-2 no-padding-right" style="text-align:right;"--%>
				<%-->意见标题:</label>--%>
		<%--<div class="col-lg-7 col-xs-9">--%>
			<%--<input type="text" readonly="readonly" id="show_title" style="width:345px"/>--%>
		<%--</div>--%>
	<%--</div>--%>
	<div class="form-group row">
		<label class="control-label col-xs-12 col-sm-2 no-padding-right" style="text-align:right;"
				>创建时间:</label>
		<div class="col-lg-7 col-xs-9">
			<input type="text" readonly="readonly" id="show_createtimeStr" style="width:345px"/>
		</div>
	</div>
	<div class="form-group row">
		<label class="control-label col-xs-12 col-sm-2 no-padding-right" style="text-align:right;"
				>意见描述:</label>
		<div class="col-lg-7 col-xs-9">
			<textarea cols="45" rows="5" id="show_context" class="col-xs-12" style="position: relative; resize: none; width:345px;font-size: 12px" readonly="readonly"></textarea>
		</div>
	</div>
	<%--<div class="form-group row">--%>
		<%--<label class="control-label col-xs-12 col-sm-2 no-padding-right" style="text-align:right;"--%>
				<%-->回复标题:</label>--%>
		<%--<div class="col-lg-7 col-xs-9">--%>
			<%--<input type="text" readonly="readonly" class="col-xs-12" id="show_replytitle" style="width:345px"/>--%>
		<%--</div>--%>
	<%--</div>--%>
	<div class="form-group row">
		<label class="control-label col-xs-12 col-sm-2 no-padding-right" style="text-align:right;"
				>回复内容:</label>
		<div class="col-lg-7 col-xs-9">
			<textarea cols="45" rows="5" id="show_replytext" style="position: relative; resize: none;width:345px;font-size: 12px" class="col-xs-12" readonly="readonly"></textarea>
		</div>
	</div>
	<div class="form-group row">
		<label class="control-label col-xs-12 col-sm-2 no-padding-right" style="text-align:right;"
				>状态:</label>
		<div class="col-lg-7 col-xs-9">
			<select id="show_statusStr" disabled="disabled">
				<option value="-1">全部</option>
				<option value="0" >未回答</option>
				<option value="1" >已回答</option>
			</select>
		</div>
	</div>

</div>
	
	
	
	
	