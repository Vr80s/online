<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/jstl_taglib.jsp" %>
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
    当前位置：云课堂管理
    <small><i class="ace-icon fa fa-angle-double-right"></i>
    </small>
    <span> 评价管理 </span>
</div>

<div class="mainrighttab tabresourse bordernone">
    <button class="btn btn-sm btn-danger top_bx" title="删除" onclick="deleteCriticizes()">
        <i class="glyphicon"></i> 删除
    </button>
    <div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row">
            <table frame=void>
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="text" placeholder="课程名称/主播名称" class="propertyValue1" id="search_keyword"
                                   style="width: 150px;">
                            <input type="hidden" value="keyword" class="propertyName"/>
                            <button id="searchBtn" type="button" class="btn btn-sm btn-primary"
                                    onclick="search();">
                                <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                            </button>
                        </div>
                    </td>
                </tr>

            </table>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">
            <table id="criticizeTable"
                   class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/cloudClass/criticize.js?v=ipandatcm_1.3"></script>
