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
<script src="${base}/js/layer/layer.js"></script>
<script src="${base}/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header row">
	<div class="col-xs-8" style="padding-left: 0px;padding-right: 0px;">
  		当前位置：作业管理
		<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
		<span>${className}</span>
		<small> <i class="ace-icon fa fa-angle-double-right"></i></small>
		<span> 试卷列表 </span>
	</div>
	<div class="col-xs-4" style="text-align: right; margin-top:-14px;padding-left: 0px;padding-right: 0px;">
		<button class="btn btn-sm btn-success" id="returnbutton">
			<i class="glyphicon glyphicon-arrow-left"></i>
			返回上级
		</button>
	</div>
</div>
<input type="hidden" id="className" value="${className}"/>
<div style="height: 100%;" class="clearfix">
    <div class="mainrighttab tabresourse bordernone" id="vocationalCourseGradeDiv">
        <p class="col-xs-3" style="padding: 0;width:20%">
            <button class="btn btn-sm btn-success add_SJ" title="布置试卷" onclick="addDialog();">
                <i class="glyphicon glyphicon-plus"></i> 布置试卷
            </button>
        </p>
        <div class="searchDivClass" id="searchDiv">
            <div class="profile-info-row" >
                <table frame=void >
                    <tr>
                        <td>
                            <div class="profile-info-value searchTr">
                                难易度：
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                <select id="difficulty"  class="propertyValue1" style="width:80px;">
                                    <option value="">请选择</option>
                                    <option value="D">非常困难</option>
                                    <option value="C">困难</option>
                                    <option value="B">一般</option>
                                    <option value="A">简单</option>
                                </select>
                                <input type="hidden" value="difficulty" class="propertyName"/>
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                状态：
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                <select id="status" style="width:76px"   class="propertyValue1" >
                                    <option value="-1">请选择</option>
                                    <option value="0">未发布</option>
                                    <option value="1">未批阅</option>
                                    <option value="2">待发成绩</option>
                                    <option value="3">已完成</option>
                                </select>
                                <input type="hidden" value="status" class="propertyName"/>
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                布置日期：
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                <input type="text" class="datetime-picker"  id="start_time"  placeholder = "开始时间" style="width: 100px" readonly="readonly"/>
                                <input type="hidden" value="start_time" class="propertyValue1"/>
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                至
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                <input type="text" class="datetime-picker"  id="end_time"  placeholder = "结束时间" style="width: 100px" readonly="readonly"/>
                                <input type="hidden" value="end_time" class="propertyValue1"/>
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                <input type="text"   id="classPaperName" class="propertyValue1"  placeholder = "试卷名称" style="width: 125px;"/>
                                <input type="hidden" value="classPaperName" class="propertyName"/>
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
                <table id="paperTable"
                    class="table table-striped table-bordered table-hover">
                </table>
            </div>
        </div>
    </div>
</div>

<!-- 安排或修改考试 -->
<div id="addOrUpdateHomeworkDialogDiv"></div>
<div id="addOrUpdateHomeworkDialog" class="hide position-top">
    <form class="form-horizontal" id="makeHomeworkForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="course_id" id="courseId" value="${courseId}"/>
        <input type="hidden" name="paper_id" id="paperId"/>
        <input type="hidden" name="duration" id="duration"/>
        <input type="hidden" name="class_id" id="classId" value="${classId}"/>
        <input type="hidden" name="id" id="homeworkId"/>
        <div class="form-group row">
            <label class="control-label col-xs-12 col-sm-2 no-padding-right profile-info-name"
            ><span style="color: red">*</span>选择试卷：</label>
            <div class="col-lg-9 col-xs-9">
                <button type="button" id="choosePaperButton" class="btn btn-info padding-3-5 hide" onclick="openChoosePaperDialog()">请选择试卷</button>
                <span id="paperNameShowSpan" class="hide position-top-6"></span>
                <a href="javascript:void(-1)" id="paperChange" class="hide position-top-6" onclick="openChoosePaperDialog()">更改<input type="hidden" autofocus="autofocus"/> </a>
            </div>
        </div>
        <div class="form-group row" style="margin-top: 10px;">
            <label class="control-label col-xs-12 col-sm-2 no-padding-right profile-info-name"
            ><span style="color: red">*</span>开始时间：</label>
            <div class="col-lg-8">
                <div class="input-control " style="position:relative">
                    <input type="text" readonly="readonly" class="datetime-picker input-control required"
                           name="start_time" id="startTime" />
                    <span class="input-group-btn" style="position:absolute;top: 0px;left: 136px;">
				        <button class="clearStartTime btn btn-default" type="button" style="padding: 0px 5px;height: 27px;line-height: 20px;">X</button>
					</span>
                </div>
            </div>
            <span class="redWordsShow hide"></span>
        </div>
        <div class="form-group row" style="margin-top: 10px;">
            <label class="control-label col-xs-12 col-sm-2 no-padding-right profile-info-name"
            ><span style="color: red">*</span>截止时间：</label>
            <div class="col-lg-8">
                <div class="input-control " style="position:relative">
                    <input type="text" readonly="readonly" class="input-control required"
                           name="end_time" id="endTime" />
                </div>
            </div>
            <span class="redWordsShow hide"></span>
        </div>
    </form>
</div>

<!-- 选择作业卷 -->
<div id="choosePaperDialogDiv"></div>
<div id="choosePaperDialog" class="hide">
    <div class="col-sm-6 col-lg-12 padding0px mep-size-outer" id="searchPaperListDiv">
        <div class="profile-info-row fleft">
            <div class="profile-info-name short-padding">作业卷名称：</div>
            <div class="profile-info-value searchTr">
                <input type="text" class="propertyValue1 input-size" placeholder="请输入作业卷名称" id="paperName" />
                <input type="hidden" value="paperName" class="propertyName"/>
                <input type="hidden" value="4" class="tempMatchType"/>
                <input type="hidden" value="String" class="tempType"/>
            </div>
            <div class="profile-info-name short-padding">难度：</div>
            <div class="profile-info-value searchTr">
                <select class="propertyValue1"  id="difficult">
                    <option value="">请选择</option>
                    <option value="A">简单</option>
                    <option value="B">一般</option>
                    <option value="C">困难</option>
                    <option value="D">非常困难</option>
                </select>
                <input type="hidden" value="difficult" class="propertyName"/>
                <input type="hidden" value="4" class="tempMatchType"/>
                <input type="hidden" value="String" class="tempType"/>
            </div>
            <div class="profile-info-value searchTr">
                <button id="searchBtn1" type="button" class="btn btn-sm  btn-primary "
                        onclick="searchPaperList();">
                    <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                </button>
            </div>
        </div>
    </div>
    <table id="paperListTable" class="table table-striped table-bordered table-hover"></table>
</div>
<script type="text/javascript" src="${base}/js/homework/papers.js?v=1"></script>