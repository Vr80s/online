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
  <span> 授课方式管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
  <p class="col-xs-4" style="padding:0px">
    <button class="btn btn-sm btn-success add_bx" id="add_button" title="新增授课方式"><i class="glyphicon glyphicon-plus"></i> 新增授课方式</button>
    <button class="btn btn-sm btn-success deletes_bx"  onclick="deleteBatch();" title="批量删除" ><i class="glyphicon glyphicon-trash"></i> 批量删除</button>
  </p>
  <div class="row">
    <div class="col-xs-12">
      <table id="teachMethodTable" class="table table-striped table-bordered table-hover"></table>
    </div>
  </div>
</div>

<!-- 增加form -->
<div id="dialogRoleDiv"></div>
<div id="roleDialog" class="hide">
  <form  method="post" class="form-horizontal" role="form" id="add-form" style="margin-top: 25px;">
    <input type="hidden" name="id" id="id">
    <div class="form-group">
      <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 授课方式名称: </label>
      <div class="col-sm-9">
        <input type="text" name="name"  maxlength="2"    class="col-xs-10 col-sm-9 {required:true,minlength:2}">
      </div>
    </div>
    <div class="space-4"></div>
    <div class="form-group">
      <label class="col-sm-3 control-label no-padding-right"><i class="text-danger"></i> 备注: </label>
      <div class="col-sm-9">
        <textarea class="col-xs-10 col-sm-9" name="remark" id="remark" maxlength = "100"  style="margin-top:10px" rows="6"></textarea>
      </div>
    </div>
  </form>
</div>


<!-- 修改form -->
<div id="updateDialogDiv"></div>
<div id="updateDialog" class="hide">
    <form action="" method="post" class="form-horizontal" role="form" id="update-form" style="margin-top: 25px;">
        <input type="hidden" name="id" id="update_id">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 授课方式名称: </label>
            <div class="col-sm-9">
                <input type="text" id="update_name" name="name"   maxlength="2"   class="col-xs-10 col-sm-9 {required:true,minlength:2}">
            </div>
        </div>
          
        <div class="space-4"></div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><i class="text-danger"></i> 备注: </label>
            <div class="col-sm-9">
                <textarea class="col-xs-10 col-sm-9" name="remark" id="update_remark" maxlength = "100" style="margin-top:10px"  rows="6"></textarea>
            </div>
        </div>
    </form>
</div>


<!-- 查看 -->
<div id="previewCloudClasMenuDialogDiv"></div>
<div id="previewCloudClasMenuDialog" class="hide" >
    <form class="form-horizontal"  method="post" action="" style="width:500px;">
        <div class="form-group">
            <label class="col-sm-5 control-label no-padding-right"><i class="text-danger">*</i> <span style="font-weight: bold;">授课方式名称: </span></label>
            <div class="col-sm-7">
                <p id="show_name" class="paddingtop7px padding7"></p>
            </div>
        </div>
         <div class="form-group">
            <label class="col-sm-5 control-label no-padding-right"><span style="font-weight: bold;"> 创建人:</span> </label>
            <div class="col-sm-7">
                <p id="show_createPerson" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-5 control-label no-padding-right"><span style="font-weight: bold;"> 创建日期: </span></label>
            <div class="col-sm-7">
                <p id="show_createTime" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-5 control-label no-padding-right"> <span style="font-weight: bold;">状态: </span></label>
            <div class="col-sm-7">
                <p id="show_status" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-5 control-label no-padding-right"><i class="text-danger"></i> <span style="font-weight: bold;">备注:</span> </label>
            <div class="col-sm-7">
            	<p id="show_remark" class="paddingtop7px padding7"></p>
            </div>
        </div>
    </form>
</div>

<!--权限配置  -->
<div id="dialogConfigDiv"></div>
<div id="configDialog" class="hide">
  <table style="width:100%">
    <tr>
      <td style="vertical-align: top;">
        <div style="text-align: center"><label for="form-field-select-2">选择资源</label></div>
        <div class="contrightbox"
             style="overflow-y: auto; overflow-x: auto;height: 300px;min-width:240px;">
          <div class="zTreeDemoBackground">
            <ul id="resource" class="ztree"
                style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
          </div>
        </div>
      </td>
      <td style="vertical-align: top;">
        <div>
          <div style="text-align: center"><label for="form-field-select-2">已选择资源</label></div>
          <div>
            <div class="contrightbox"
                 style="overflow-y: auto; overflow-x: auto;height: 300px;min-width:240px;">

              <div class="zTreeDemoBackground">
                <ul id="resource2" class="ztree"
                    style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
              </div>
            </div>
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>

<script type="text/javascript" src="${base}/js/cloudClass/teachMethod.js?ver=1.2"></script>