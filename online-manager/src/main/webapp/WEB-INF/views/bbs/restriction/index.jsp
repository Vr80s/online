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
    <span>拉黑/禁言管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
    <button class="btn btn-sm btn-danger blacklist_bx" title="拉黑">
        <i class="glyphicon glyphicon-trash"></i> 拉黑
    </button>
    <button class="btn btn-sm btn-primary cancel_blacklist_bx" title="取消拉黑">
        <i class="glyphicon glyphicon-trash"></i> 取消拉黑
    </button>
    <button class="btn btn-sm btn-danger gags_bx" title="禁言">
        <i class="glyphicon glyphicon-trash"></i> 禁言
    </button>
    <button class="btn btn-sm btn-primary cancel_gags_bx" title="取消禁言">
        <i class="glyphicon glyphicon-trash"></i> 取消禁言
    </button>

    <!-- 条件查询 -->
    <div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row">
            <table frame=void>
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder="手机号" class="propertyValue1" id="mobile"
                                   style="width: 150px;">
                            <input type="hidden" value="mobile" class="propertyName"/>
                        </div>
                    </td>
                    <td>
                        <button id="searchBtn" type="button" class="btn btn-sm btn-primary"
                                onclick="search();">
                            <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <!-- 拉黑禁言列表 -->
    <div class="row">
        <div class="col-xs-12">
            <table id="restrictionTable" class="table table-striped table-bordered table-hover">

            </table>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/bbs/restriction/restriction.js?v=ipandatcm_1.3"></script>