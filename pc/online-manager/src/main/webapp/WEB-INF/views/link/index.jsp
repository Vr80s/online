<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/jstl_taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />	
<link href="/js/layer/skin/layer.css" type="text/css" />	
<script src="/js/layer/layer.js?v=4"></script>
<script type="text/javascript" src="/js/link/list.js?v=ipandatcm_1.3"></script>

<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
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
	当前位置：运营管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 友情链接 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
	 	<button class="btn btn-sm btn-success add_bx" ><i class="glyphicon glyphicon-plus"></i> 新增</button>      
    	<button class="btn btn-sm btn-success dels_bx" onclick="deleteBatch();"><i class="glyphicon glyphicon-trash"></i> 批量删除</button>
	</p>
	
	<div class="searchDivClass" id="searchDiv">
		<div class="profile-info-row" >
			<table frame=void >
	        	<tr>
	            	<td>
						<div class="profile-info-value searchTr" >
						
							<input type="text" placeholder = "链接名称" class="propertyValue1">
							<input type="hidden" value="a.name" class="propertyName"/>
							<input type="hidden" value="5" class="tempMatchType"/><!-- 4等于 5包含 6小于 7大于  8小于等于  9大于等于  10之间 -->
							<input type="hidden" value="String" class="tempType"/><!-- 默认是String -->
						</div>
					</td>
					<td>
						<button id="searchBtn" type="button" class="btn btn-sm btn-primary "
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
			<table id="linkTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>



<!-- 增加form -->
<div id="dialogLinkDiv"></div>
<div id="addLinkDialog" class="hide">
	<form class="form-horizontal" id="addlink-form" method="post" action="">
		<input name="id" type="hidden" value="" id="link_id">
		
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="title">链接名称<font color="red">*</font></label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<input name='name' id="add_orgname" value="" data-field="orgname" type='text' class="col-xs-5 secheck2 {required:true,messages:{required:'名称不能为空'}}" style="width:250px;" />
				</div>
			</div>
		</div>
		
		<div class="form-group" style="margin-top: 18px;">
			<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="timeLong">链接<font color="red">*</font></label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<input name='url' id="add_url" value="" data-field="url" type='text' class="col-xs-5 secheck2 {required:true,messages:{required:'链接不能为空'}}" style="width:250px;" />
				</div>
			</div>
		</div>
		
		<div class="form-group" style="margin-top: 18px;">
			<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="sort">排序<font color="red">*</font></label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<input name='sort' id="link_sort" value="" data-field="sort" type='text' class="col-xs-10 col-sm-12 {required:true,digits:true,range:[0,99],messages:{required:'排序不能为空'}}" style="width: 250px;">
				</div>
			</div>
		</div>
	
	</form>
</div>

<!-- 查看 -->
<div class="modal fade" id="previewlinkDialog" tabindex="-1" role="dialog"
	 aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close"
						data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="preview_modal_title">
					链接详情
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="control-label col-xs-12 col-sm-3 no-padding-right"></label>
						<div class="col-xs-12 col-sm-9">
							<div class="clearfix paddingtop7px">
								<img id="link_img" src="" style="text-align: center; margin:0 auto ; height: 80px;width: 180px;"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">机构名称:</label>
						<div class="col-lg-10">
							<input type="text" id="link_name" class="form-control" readonly="readonly">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">地址:</label>
						<div class="col-lg-10">
							<textarea class="form-control" id="link_url" rows="3" readonly="readonly"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
						data-dismiss="modal">关闭
				</button>
			</div>
		</div>
	</div>
</div>