<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css"/>
<link href="/js/layer/skin/layer.css" type="text/css"/>
<script type="text/javascript">
    try {
        var scripts = [null, null];
        $('.page-content-area').ace_ajax('loadScripts', scripts,
            function () {
            });
    } catch (e) {
    }
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
    当前位置：BBS管理
    <small><i class="ace-icon fa fa-angle-double-right"></i>
    </small>
    <span>标签管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
    <button class="btn btn-sm btn-success add_bx" title="新增">
        <i class="glyphicon glyphicon-plus"></i> 新增
    </button>
    <button class="btn btn-sm btn-danger delete_bx" title="删除">
        <i class="glyphicon glyphicon-trash"></i> 删除
    </button>
    <button class="btn btn-sm btn-primary edit_bx" title="编辑">
        <i class="glyphicon glyphicon-plus"></i> 编辑
    </button>
    <button class="btn btn-sm btn-danger status_bx" title="禁用/启用">
        <i class="glyphicon glyphicon-trash"></i> 禁用/启用
    </button>

    <!-- 标签列表 -->
    <div class="row">
        <div class="col-xs-12">
            <table id="labelTable" class="table table-striped table-bordered table-hover">

            </table>
        </div>
    </div>
</div>

<!-- 标签创建 -->
<div id="addLabel"></div>
<div id="addLabelDialog" class="hide">
    <form id="addLabelDialog-form" class="form-horizontal" method="post" action="" style="margin-top: 15px;">

        <input type="hidden" value="1" name="type" maxlength="50">

        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="name">*标签: </label>
            <div class="col-sm-6">
                <input type="text" name="name" id="name" maxlength="50" class="col-xs-10 col-sm-12 {required:true}"/>
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="details">*标签详情: </label>
            <div class="col-sm-6">
                <input type="text" name="details" id="details" maxlength="50"
                       class="col-xs-10 col-sm-12 {required:true}">
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="sort">*序号: </label>
            <div class="col-sm-6">
                <div class="clearfix">
                    <input name="sort" id="sort" class="{required:true}" cols="39" rows="5"/>
                </div>
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="labelImgUrl">*文章图片: </label>
            <div class="col-sm-6">
                <div class="clearfix">
                    <div class="clearfix" style="width: 240px;">
                        <input type="file" name="img" id="imgPath_file" class="uploadImg"/>
                    </div>
                    <input name="labelImgUrl" id="add_imgPath" class="{required:true}" cols="39" rows="5" hidden/>
                </div>
            </div>
        </div>
    </form>
</div>

<!-- 标签编辑 -->
<div id="editLabel"></div>
<div id="editLabelDialog" class="hide">
    <form id="editLabelDialog-form" class="form-horizontal" method="post" action="" style="margin-top: 15px;">
        <input type="hidden" name="id" id="J_edit_id" value="1" name="type" maxlength="50">

        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="name">*标签: </label>
            <div class="col-sm-6">
                <input type="text" name="name" id="J_edit_name" maxlength="50"
                       class="col-xs-10 col-sm-12 {required:true}"/>
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="details">*标签详情: </label>
            <div class="col-sm-6">
                <input type="text" name="details" id="J_edit_details" maxlength="50"
                       class="col-xs-10 col-sm-12 {required:true}">
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="sort">*序号: </label>
            <div class="col-sm-6">
                <div class="clearfix">
                    <input name="sort" id="J_edit_sort" class="{required:true}" cols="39" rows="5" type="number"/>
                </div>
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="labelImgUrl">*文章图片: </label>
            <div class="col-sm-6">
                <div class="clearfix">
                    <div class="clearfix" style="width: 240px;">
                        <input type="file" name="img" id="J_edit_imgPath_file" class="uploadImg"/>
                    </div>
                    <input name="labelImgUrl" id="J_edit_add_imgPath" class="{required:true}" cols="39" rows="5"
                           hidden/>
                </div>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="/js/bbs/label/label.js?v=ipandatcm_1.3"></script>