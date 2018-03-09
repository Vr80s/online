<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	

<script type="text/javascript">
  try {
    var scripts = [ null, null ];
    $('.page-content-area').ace_ajax('loadScripts', scripts,
            function() {
            });
  } catch (e) {
  }
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：头条管理  <small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 文章分类管理 </span>
</div>


<div class="mainrighttab tabresourse bordernone">
	<p class="col-xs-4" style="padding: 0;">
        <button class="btn btn-sm btn-success add_type" title="新增分类">
            <i class="glyphicon glyphicon-plus"></i> 新增分类
        </button>
        <button class="btn btn-sm btn-success del_batch" title="批量删除">
            <i class="glyphicon glyphicon-trash"></i> 批量删除
        </button>
		<button class="btn btn-sm btn-primary" title="文章数从高到低排列" onclick="search(1)">
			文章数↓
		</button>
	</p>


	<div class="row">
		<div class="col-xs-12">
			<table id="typeTable"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
</div>

<!-- 添加form -->
<div id="addTypeDiv"></div>
<div id="addDialog" class="hide">
    <form action="" method="post" class="form-horizontal" role="form" id="add-form">
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 分类名称: </label>
            <div class="col-sm-9">
                <input type="text" name="name" class="col-xs-9 col-sm-9 {required:true}">
            </div>
        </div>
    </form>
</div>

<!-- 修改form -->
<div id="updateTypeDiv"></div>
<div id="updateDialog" class="hide">
    <form action="" method="post" class="form-horizontal" role="form" id="update-form">
        <input type="hidden" name="id" id="edit_id" value="${id}">
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 分类名称: </label>
            <div class="col-sm-9">
                <input type="text" name="name" id="edit_name" class="col-xs-9 col-sm-9 {required:true}">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="/js/boxueshe/articletype.js?v=ipandatcm_1.3"></script>
