<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<link href="${base}/css/jquery-ui-timepicker-addon.css" type="text/css" />
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
<style>
    #studentTable_wrapper thead .showSortIcon{
        color: #307ecc;

    }
    #studentTable_wrapper .dataTable > thead > tr > th.sorting_asc:after {
        content: "\f0de";
        top: 4px;
        display: block;
        color: #307ecc;
    }
    #studentTable_wrapper .dataTable > thead > tr > th:first-child:after{
        content: none;
        top: 4px;
        display: block;
    }
    #studentTable_wrapper .dataTable > thead > tr > th[class*=showSortIcon]:after {
        display: inline;
        color: #307ecc;
    }
</style>
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header row">
    <div class="col-xs-8" style="padding-left: 0px;padding-right: 0px;">
        当前位置：作业管理
        <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
        <span>${className}</span>
        <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
        <span>${paperName}</span>
        <small> <i class="ace-icon fa fa-angle-double-right"></i></small>
        <span> 学员列表 </span>
    </div>
    <div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
        <button class="btn btn-sm btn-success" id="returnbutton">
            <i class="glyphicon glyphicon-arrow-left"></i>
            返回上级
        </button>
    </div>
</div>
<input type="hidden" id="classPaperId" value="${classPaperId}"/>
<input type="hidden" id="classId" value="${classId}"/>
<input type="hidden" id="courseId" value="${courseId}"/>
<input type="hidden" id="className" value="${className}"/>
<input type="hidden" id="paperName" value="${paperName}"/>
<div style="height: 100%;" class="clearfix">
    <div class="mainrighttab tabresourse bordernone" id="vocationalCourseGradeDiv">
        <p class="col-xs-3" style="padding: 0;width:20%">
            <button class="btn btn-sm btn-success go_FB" title="批量发布" onclick="batchPublish();">
                <i class="glyphicon glyphicon-plus"></i> 批量发布
            </button>
            <%--<button class="btn btn-sm btn-success go_XZ" title="下载附件" onclick="downLoad();">--%>
                <%--<i class="glyphicon glyphicon-plus"></i> 下载附件--%>
            <%--</button>--%>
        </p>
        <div class="searchDivClass" id="searchDiv">
            <div class="profile-info-row" >
                <table frame=void >
                    <tr>
                        <td>
                            <div class="profile-info-value searchTr">
                                试卷状态：
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                <select id="status" class="propertyValue1" style="width:80px;">
                                    <option value="-1">请选择</option>
                                    <option value="0">未交卷</option>
                                    <option value="1">已交卷</option>
                                    <option value="2">已批阅</option>
                                    <option value="3">已发布</option>
                                </select>
                                <input type="hidden" value="status" class="propertyName"/>
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                <input type="text"   id="studentName" class="propertyValue1"  placeholder = "请输入学员名称" style="width: 125px;"/>
                                <input type="hidden" value="studentName" class="propertyName"/>
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
        <div class="row">
            <div class="col-xs-12">
                <table id="studentTable"
                    class="table table-striped table-bordered table-hover">
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${base}/js/homework/students.js?v=ipandatcm_1.3"></script>