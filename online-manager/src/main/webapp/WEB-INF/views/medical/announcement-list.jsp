<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css"/>
<link href="/js/layer/skin/layer.css" type="text/css"/>
<style>
    .dataTable > tbody > tr > td {
        white-space:normal;
    }
</style>
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
    当前位置：医师医馆
    <small><i class="ace-icon fa fa-angle-double-right"></i>
    </small>
    医馆管理
    <small><i class="ace-icon fa fa-angle-double-right"></i>
    </small>
    <span>公告管理</span>
</div>

<div class="mainrighttab tabresourse bordernone">

    <!-- 公告列表 -->
    <div class="row">
        <div class="col-xs-12">
            <table id="announcementTable" class="table table-striped table-bordered table-hover">

            </table>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/medical/announcement.js?v=ipandatcm_1.3"></script>