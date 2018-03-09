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
<style>
    .clearfix:after{
        content:"";
        height:0;
        display:block;
        visibility:hidden;
        clear:both;
    }
    #weekList{
        display:inline-block;
        border: 1px solid #ddd;
        border-bottom:none;
        border-right:none;
        max-width:1051px;
    }
    #weekList li{
        width: 70px;
        height:30px;
        list-style:none;
        float: left;
        border-right:1px solid #ddd;
        border-bottom:1px solid #ddd;
        background-color: white;
    }
    #weekList li a{
        border:none;
        outline: none;
        margin-left: 18px;
        text-decoration: none;
        color:#333;
    }
    #weekList li span{
        line-height: 30px;
        font-size: 12px;
    }
    .cli{
        background-color: #ddd!important;
    }
    .title{
        font-size: medium;
        font-weight: bold;
    }
</style>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
    <div class="row">
        <div style="margin-left:10px">
            当前位置：云课堂管理 <small><i class="ace-icon fa fa-angle-double-right"></i></small>
            课程管理 <small><i class="ace-icon fa fa-angle-double-right"></i></small>
            ${courseName}-学习计划模板
            <span style="float:right;margin-right:10px">
                <button type="button" class="btn btn-sm" onclick="goBack()">
                    <i class="fa fa-arrow-left">返回</i>
                </button>
            </span>
        </div>
    </div>
</div>
<input type="hidden" id="courseId" value="${courseId}">
<div class="mainrighttab tabresourse bordernone">
    <div class="row" style="margin-left:5px">
        <span class="title">授课天数：</span><span id="totalDay">${totalDay}</span><span>天</span><button type="button" style="margin-bottom: 5px;margin-left: 20px;" class="btn btn-sm btn-success" onclick="editTotalDay()">修改</button>
    </div>
    <div class="row" style="margin-top: 30px;">
        <ul id="weekList" class="clearfix">
        </ul>
    </div>
    <div class="row" style="margin-left:5px;margin-top: 30px;"><span class="title">学习计划详情</span></div>
    <div class="row" style="margin-left:5px;margin-top: 30px;">
        <table class="table table-bordered table-hover dataTable no-footer" style="text-align: center;width: 1051px;">
            <thead>
                <tr>
                    <th aria-label="天数" class="center faultfirstth faulttd ui-state-default sorting_disabled">天数</th>
                    <th aria-label="知识点" class="center faultfirstth faulttd ui-state-default sorting_disabled">知识点</th>
                    <th aria-label="知识点时长" class="center faultfirstth faulttd ui-state-default sorting_disabled">知识点时长</th>
                    <th aria-label="操作" class="center faultfirstth faulttd ui-state-default sorting_disabled">操作</th>
                </tr>
            </thead>
            <tbody id="planInfoBody">
            </tbody>
        </table>
	</div>
    <!-- 修改form -->
    <div id="updateGradeDiv"></div>
    <div id="updateDialog" class="hide">
        <form action="" method="post" class="form-horizontal" role="form" id="update-form">
            <input type="hidden" id="oldDay_update" name="oldDay" value="${totalDay}">
            <input type="hidden" name="courseId" value="${courseId}">
            <div class="space-4"></div>
            <div class="form-group" style="margin-top: 18px;">
                <label class="col-sm-5 control-label no-padding-right"><i class="text-danger">*</i> 学习计划模板天数: </label>
                <div class="col-sm-7">
                    <input type="text" name="totalDay" id="totalDay_update" class="col-xs-3 col-sm-3 {required:true,digits:true,range:[1,365]}">
                    <label class="col-sm-2 control-label no-padding-left">天</label>
                </div>
            </div>
        </form>
    </div>
    <!--知识点配置  -->
    <div id="dialogConfigDiv"></div>
    <div id="configDialog" class="hide">
        <table style="">
            <tr>
                <td style="vertical-align: top;">
                    <div class="contrightbox" style="overflow-y: hidden; overflow-x: hidden;height: 420px;min-width:360px;border:1px solid #99BBE7">
                        <div class="zTreeDemoBackground" style="overflow-y: auto; overflow-x: auto;height: 400px;">
                            <ul id="resource" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>

<script type="text/javascript" src="/js/cloudClass/plantemplate.js?v=ipandatcm_1.3"></script>