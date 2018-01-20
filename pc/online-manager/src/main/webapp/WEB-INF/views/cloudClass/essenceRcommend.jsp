<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
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
<link href="${base}/js/layer/skin/layer.css" type="text/css" />

<div class="page-header">
  当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small>
  <span> 精品推荐管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
  <p class="col-xs-4" style="padding:0px">
    <button class="btn btn-sm btn-success add_bx" id="add_button" title="新增课程类别"><i class="glyphicon glyphicon-plus"></i> 新增课程类别</button>
    <button class="btn btn-sm btn-success deletes_bx"  onclick="deleteBatch();" title="批量删除"><i class="glyphicon glyphicon-trash"></i> 批量删除</button>
  </p>
    <div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row" >

        </div>
    </div>
  <div class="row">
    <div class="col-xs-12">
      <table id="scoreTypeTable" class="table table-striped table-bordered table-hover"></table>
    </div>
  </div>
</div>

<!-- 查看 -->
<div id="previewCloudClasMenuDialogDiv"></div>
<div id="previewCloudClasMenuDialog" class="hide" >
    <form class="form-horizontal"  method="post" action="" style="width:500px;margin-top: 15px;" >
        <div class="form-group row">
            <label class="col-sm-5 control-label no-padding-right"><i class="text-danger">*</i><span style="font-weight: bold;"> 课程类别名称: </span></label>
            <div class="col-sm-7">
                <p id="show_menuName" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-5 control-label no-padding-right"><span style="font-weight: bold;"> 创建人:</span> </label>
            <div class="col-sm-7">
                <p id="show_createPerson" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-5 control-label no-padding-right"> <span style="font-weight: bold;">创建日期: </span></label>
            <div class="col-sm-7">
                <p id="show_createTime" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-5 control-label no-padding-right"> <span style="font-weight: bold;">状态:</span> </label>
            <div class="col-sm-7">
                <p id="show_status" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-5 control-label no-padding-right"><i class="text-danger"></i><span style="font-weight: bold;"> 备注:</span> </label>
            <div class="col-sm-7">
                <p id="show_remark" class="paddingtop7px padding7"></p>
            </div>
        </div>
    </form>
</div>


<script type="text/javascript" src="${base}/js/cloudClass/essenceRecommend.js?ver=1.2"></script>