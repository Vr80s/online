<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css"/>
<link href="/js/layer/skin/layer.css" type="text/css"/>

<script type="text/javascript">

    try {
        var scripts = [null, null];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        });
    } catch (e) {
    }

</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/cloudClass/provinces.js?v=ipandatcm_1.3"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/medical/enrollmentRegulationsDetail.js"></script>
<div class="page-header">
    当前位置：师承管理
    <small><i class="ace-icon fa fa-angle-double-right"></i>
    </small>
    <span> 师承详情</span>
</div>

<!-- 查看 form -->
<div id="dialogDiv"></div>
<div id="editDialog">
    <form id="update-form" class="form-horizontal" method="post" action="">
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="update_title"><font color="red">*</font>招生标题:
            </label>
            <div class="col-sm-3">
                ${MedicalEnrollmentRegulations.title}
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="doctorId">老师: </label>
            <div class="col-sm-2">
                ${MedicalEnrollmentRegulations.doctorName}
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="add_imgPath">封面图: </label>
            <div class="col-sm-3">
                <img class="middle" style="width: 250px; height: 140px;" src="${MedicalEnrollmentRegulations.coverImg}">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="tuition"><font color="red">*</font>学费: </label>
            <div class="col-sm-1">
                <div style="padding-top: 5px">${MedicalEnrollmentRegulations.tuition}元</div>
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="countLimit"><font color="red">*</font>收徒人数:
            </label>
            <div class="col-sm-1">
                ${MedicalEnrollmentRegulations.countLimit}
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="deadline"><font color="red">*</font>报名截止时间:
            </label>
            <div class="col-sm-3">
                ${fn:substring(MedicalEnrollmentRegulations.deadline,0,16)}
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="startTime"><font color="red">*</font>学习时间:
            </label>
            <div class="">
                ${fn:substring(MedicalEnrollmentRegulations.startTime,0,16)}至${fn:substring(MedicalEnrollmentRegulations.endTime,0,16)}
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="studyAddress"><font color="red">*</font>学习地址:
            </label>
            ${MedicalEnrollmentRegulations.studyAddress}

        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="ceremonyAddress"><font color="red">*</font>相关介绍:
            </label>
            <div class="col-sm-5 ">
                ${MedicalEnrollmentRegulations.ceremonyAddress}
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right" for="regulations"><font color="red">*</font>招生简章:
            </label>
            <div class="col-sm-5">
                ${MedicalEnrollmentRegulations.regulations}
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-1 yrx no-padding-right"><font color="red">*</font>招生简章: </label>
            <div class="col-sm-3" style="padding-top:7px">

                <a href="${MedicalEnrollmentRegulations.entryFormAttachment}" style="color:blue"> 下载招生简章</a>
            </div>
        </div>

    </form>
    <div class="col-xs-7" style="text-align: right;margin-top:50px;">
        <button class="btn btn-sm btn-success" id="returnbutton">
            返回
        </button>
    </div>
</div>


<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var oldCeremonyAddress = '${MedicalEnrollmentRegulations.ceremonyAddress}';
    var oldRegulations = '${MedicalEnrollmentRegulations.regulations}';
</script>
