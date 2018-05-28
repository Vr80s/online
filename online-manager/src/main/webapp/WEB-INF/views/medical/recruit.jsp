<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/jstl_taglib.jsp" %>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css"/>
<link href="/js/layer/skin/layer.css" type="text/css"/>

<style type="text/css">
    .vertical-tab > li {
        text-align: center;
    }

    .vertical-tab > li.active > a, .vertical-tab > li.active > a:focus, .vertical-tab > li.active > a:hover {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff;
        border-right: 1px solid #ffffff;
        z-index: 2;
    }

    .vertical-tab > li > a {
        /* border-radius: 4px 4px 4px 4px; */
        border-radius: 4px 0px 0px 4px;
    }

    .vertical-tab-content {
        float: left;
        width: 90%;
        padding: 5px;
        margin-left: -1px;
        margin-bottom: 2px;
        border-radius: 0px 4px 4px 4px;
        border: solid 1px #ccc;
        color: #666;
    }

    .send {
        position: relative;
        width: 90%;
        background: #FFFFFF;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px; /* 圆角 */
        border: 1px solid #ccc;
    }

    .tag {
        width: 300px;
        min-height: 30px;
        border: 1px solid #ccc;
        position: relative;
        padding: 10px;
        background-color: #ccc;
        border-radius: 4px;
        margin-left: 30px;
        -moz-box-shadow: 1px 1px 2px hsla(0, 0%, 0%, 0.3);
        -webkit-box-shadow: 1px 1px 2px hsla(0, 0%, 0%, 0.3);
        box-shadow: 1px 1px 2px hsla(0, 0%, 0%, 0.3);
    }

    .tag:before, .tag:after {
        position: absolute;
        content: "\00a0";
        width: 0px;
        height: 0px;
        border-width: 8px 18px 8px 0;
        border-style: solid;
        border-color: transparent #CCC transparent transparent;
        top: 5px;
        left: -18px;

    }

    .tag:after {
        bottom: -33px;
        border-color: #FFF transparent transparent;
    }

    .clearfix:after {
        content: "";
        height: 0;
        visibility: hidden;
        display: block;
        clear: both;
    }

</style>

<style>
    .caret {
        display: inline-block;
        width: 0;
        height: 0;
        margin-left: 2px;
        vertical-align: middle;
        border-top: 4px dashed;
        border-top: 4px solid \9;
        border-right: 4px solid transparent;
        border-left: 4px solid transparent;
        -webkit-transform: rotate(0deg);
        -moz-transform: rotate(0deg);
        -ms-transform: rotate(0deg);
        -o-transform: rotate(0deg);
        transform: rotate(0deg);
    }

    .rotateCaret {
        display: inline-block;
        width: 0;
        height: 0;
        /*  margin-left: 2px; */
        vertical-align: middle;
        border-top: 4px dashed;
        border-top: 4px solid \9;
        border-right: 4px solid transparent;
        border-left: 4px solid transparent;
        -webkit-transform: rotate(-90deg);
        -moz-transform: rotate(-90deg);
        -ms-transform: rotate(-90deg);
        -o-transform: rotate(-90deg);
        transform: rotate(-90deg);
    }

    .clearfix:after {
        content: '';
        height: 0;
        visibility: hidden;
        display: block;
        clear: both;
    }

    .zhezhao {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: #666;
        opacity: 0.5;
        z-index: 99999;
    }

    #fenpeiTeacher {
        width: 520px;
        font-size: 12px;
        margin: 0 auto;
        border: 1px solid #ddd;
        background-color: #fff;
        position: fixed;
        z-index: 999999;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }

    .fenpeiTearchTop {
        width: 100%;
        border-bottom: 1px solid #ddd;
    }

    .title {
        display: inline-block;
        font-size: 16px;
        color: #333;
        font-weight: bold;
        line-height: 35px;
        padding-left: 10px;
    }

    .fenpeiTearcherClose {
        float: right;
        margin-top: 10px;
        padding-right: 10px;
        cursor: pointer;
    }

    .fenpeiTeacherAbove {
        padding: 15px 30px;
    }

    .fengpeiTeacher-content {

    }

    .fengpeiTeacher-content ul {
        list-style: none;
    }

    .fengpeiSearch {
        position: relative;
        width: 242px;
    }

    .fengpeiSearch input {
        width: 100%;
        height: 28px;
        line-height: 20px;
    }

    .fengpeiSearch img {
        position: absolute;
        right: 8px;
        top: 4px;
    }

    .fenpeiList {
        margin-top: 10px;
        width: 242px;
        height: 395px;
        border: 1px solid #ddd;
        padding: 10px;
        float: left;
        overflow-y: auto;
    }

    .fenpeiList .xuekename {
        display: inline-block;
        vertical-align: top;
        margin: 5px 0;
    }

    .fenpeiList .xuekeleibei {
        display: inline-block;
        margin: 0;
        padding: 0;
        width: 150px;
    }

    .fenpeiList .xuekeleibei span {
        cursor: pointer;
    }

    .list-items2 li {
        cursor: pointer;
    }

    .row li {
        cursor: pointer;
    }

    .fenpeiTeacherImg {
        cursor: pointer;
    }

    .allTeacher span, .allTeacher img {
        cursor: pointer;
    }

    .child-parrent-title .clear {
        cursor: pointer;
    }

    .fenpeiTeacherBtn span {
        cursor: pointer;
    }

    .fenpeiList .xuekeleibei li {
        margin: 5px 0;
    }

    .fenpeiList .xuekeleibei .tagname {
        /* margin-left:5px; */
    }

    .fenpeiList .xuekeleibei li::before {

    }

    .fenpeirow {
        float: left;
        margin: 0;
        padding: 0;
        width: 50px;
        margin-left: 5px;
    }

    .fenpeirow li {
        width: 30px;
        height: 30px;
        background-color: #f8f8f8;
        -webkit-border-radius: 4px;
        -moz-border-radius: 4px;
        border-radius: 4px;
        text-align: center;
        line-height: 30px;
        margin: 80px 10px;
        cursor: pointer;
    }

    .child-parrent {
        width: 155px;
        float: right;
        margin-top: 10px;
    }

    .child-parrent > div {
        width: 100%;
        height: 125px;
        border: 1px solid #ddd;
        margin-bottom: 10px;
    }

    .child-parrent .child-parrent-title {
        height: 25px;
        padding: 0 10px;
        border-bottom: 1px solid #ddd;
    }

    .child-parrent .child-parrent-title span {
        margin-top: 5px;
    }

    .child-parrent .child-parrent-title span:nth-child(1) {
        float: left;
    }

    .child-parrent .child-parrent-title span:nth-child(2) {
        float: right;
    }

    .child-parrent .allTeacher {
        padding: 5px 10px;
        height: 88px;
        overflow-y: auto;
    }

    .fenpeiTeacherBtn {
        width: 200px;
        margin: 20px auto;
    }

    .fenpeiTeacherBtn .anniu {
        display: inline-block;
        width: 58px;
        height: 27px;
        -webkit-border-radius: 4px;
        -moz-border-radius: 4px;
        border-radius: 4px;
        text-align: center;
        line-height: 27px;
        background-color: #428bca;
        color: #fff;
        font-size: 14px;
    }

    .fenpeiTeacherBtn span {
        margin-left: 30px;
    }

    .select {
        color: #fff;
        background-color: #428bca;
    }

    .allTeacher span {
        display: inline-block;
        width: 80px;
    }

    .allTeacher img {
        vertical-align: bottom;
        display: none;
    }

    .allTeacher div:hover img {
        display: inline-block;
    }

    .list-items1 {
        display: none;
    }

    .list-items2 {
        display: none;
    }

    .vertical-tab {
        width: 8%;
        height: 100%;
        float: left;
        /* overflow: hidden; */
    }

    .vertical-tab > li {
        text-align: center;
    }

    .vertical-tab > li.active > a, .vertical-tab > li.active > a:focus, .vertical-tab > li.active > a:hover {
        border: solid #ccc;
        border-width: 1px 1px 1px 1px;
        background-color: #ffffff;
        border-right: 1px solid #ffffff;
        z-index: 2;
    }

    .vertical-tab > li > a {
        /* border-radius: 4px 4px 4px 4px; */
        border-radius: 4px 0px 0px 4px;
    }

    .vertical-tab-content {
        float: left;
        width: 90%;
        padding: 5px;
        margin-left: -1px;
        margin-bottom: 2px;
        border-radius: 0px 4px 4px 4px;
        border: solid 1px #ccc;
        color: #666;
    }

    .send {
        position: relative;
        width: 90%;
        background: #FFFFFF;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px; /* 圆角 */
        border: 1px solid #ccc;
    }

    .tag {
        width: 300px;
        min-height: 30px;
        border: 1px solid #ccc;
        position: relative;
        padding: 10px;
        background-color: #ccc;
        border-radius: 4px;
        margin-left: 30px;
        -moz-box-shadow: 1px 1px 2px hsla(0, 0%, 0%, 0.3);
        -webkit-box-shadow: 1px 1px 2px hsla(0, 0%, 0%, 0.3);
        box-shadow: 1px 1px 2px hsla(0, 0%, 0%, 0.3);
    }

    .tag:before, .tag:after {
        position: absolute;
        content: "\00a0";
        width: 0px;
        height: 0px;
        border-width: 8px 18px 8px 0;
        border-style: solid;
        border-color: transparent #CCC transparent transparent;
        top: 5px;
        left: -18px;

    }

    .tag:after {
        bottom: -33px;
        border-color: #FFF transparent transparent;
    }

    .clearfix:after {
        content: "";
        height: 0;
        visibility: hidden;
        display: block;
        clear: both;
    }

    input.custom-combobox-input.ui-widget.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input.valid {
        width: 100%;
    }

    input.custom-combobox-input.ui-widget.ui-widget-content.ui-state-default.ui-corner-left.ui-autocomplete-input {
        width: 100%;
    }

</style>
<script type="text/javascript">
    var hospitalId = '${hospitalId}';
    $("#hospitalId").val(hospitalId);
</script>
<script type="text/javascript">

    try {
        var scripts = [null, null];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        });
    } catch (e) {
    }

    $(function () {
        $('#vtab li').click(function () {
            tops = $(this).offset().top - $('#vtab').offset().top + $('#vtab').scrollTop() - $('#vtab').height() / 4;
            console.log(tops);
            $('#vtab').animate({
                scrollTop: tops
            }, 'slow');
        });
    });
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
    当前位置：医师医馆
    <small><i class="ace-icon fa fa-angle-double-right"></i>
    </small>
    <span> 招聘管理 </span>
</div>

<div style="height: 100%;" class="clearfix">

    <!-- Tab panes -->
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <div class="mainrighttab tabresourse bordernone" id="courseDiv">
                <p class="col-xs-4" style="padding: 0;">
                    <button class="btn btn-sm btn-success add-recruit" title="新增">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <button class="btn btn-sm btn-success dele_P" title="批量删除">
                        <i class="glyphicon glyphicon-trash"></i> 批量删除
                    </button>
                </p>

                <div class="searchDivClass" id="searchDiv">
                    <div class="profile-info-row">
                        <table frame=void>
                            <tr>
                                <td>
                                    <div class="profile-info-value searchTr">
                                        <input type="text" placeholder="职位名" class="propertyValue1"
                                               id="search_recruitName" style="width: 150px;">
                                        <input type="hidden" value="search_recruitName" class="propertyName"/>
                                    </div>
                                </td>
                                <td>
                                    <div class="profile-info-value searchTr">
                                        <select name="search_status" id="search_status" value="" class="propertyValue1">
                                            <option value="">是否启用</option>
                                            <option value="1">已启用</option>
                                            <option value="0">未启用</option>
                                        </select>
                                        <input type="hidden" value="search_status" class="propertyName"/>
                                    </div>
                                </td>
                                <td>
                                    <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
                                            onclick="search_P();">
                                        <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <table id="recruitTable"
                               class="table table-striped table-bordered table-hover" style="width: 100%;">
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="box_px">
            <div class="mainrighttab tabresourse bordernone" id="courseDiv_PX">
                <div class="searchDivClass" id="searchDiv_PX">
                    <div class="profile-info-row">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <table id="recruitTable_PX"
                               class="table table-striped table-bordered table-hover">
                            <colgroup>
                                <col width='5%'></col>
                                <col width='12%'></col>
                                <col width='10%'></col>
                                <col width='12%'></col>
                                <col width='8%'></col>
                                <col width='8%'></col>
                                <col width='8%'></col>
                                <col width='10%'></col>
                                <col width='8%'></col>
                                <col width='10%'></col>
                            </colgroup>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 增加form -->
<div id="addRecruitDiv"></div>
<div id="addRecruitDialog" class="hide">
    <form id="addRecruitForm" class="form-horizontal" method="post" action="" style="margin-top: 15px;">
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="position"><font color="red">*</font>招聘职位:
            </label>
            <div class="col-sm-6">
                <input type="text" name="position" id="position"
                       class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:30}">
                <input type="hidden" name="hospitalId" id="hospitalId" class="col-xs-10 col-sm-12">
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="years">工作经验: </label>
            <div class="col-sm-6">
                <select name="years" id="years">
                    <option value="0">不限</option>
                    <option value="1">0-1年</option>
                    <option value="2">1-3年</option>
                    <option value="3">3-5年</option>
                    <option value="4">5-10年</option>
                    <option value="5">10年以上</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="postDuties"><font color="red">*</font>岗位职责:
            </label>
            <div class="col-sm-6">
                <textarea class="form-control" name="postDuties" id="postDuties" cols="30" rows="6"
                          class="col-xs-10 col-sm-12 {required:true,rangelength:[1,5]}"></textarea>
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><font color="red">*</font>职位要求: </label>
            <div class="col-sm-6">
                <textarea class="form-control" name="jobRequirements" id="jobRequirements" cols="30" rows="6"
                          class="col-xs-10 col-sm-12 {required:true,rangelength:[1,5]}"></textarea>
            </div>
        </div>
    </form>
</div>

<!-- 查看form -->
<div id="showRecruitDiv"></div>
<div id="showRecruitDialog" class="hide">
    <form id="showRecruit-form" class="form-horizontal" method="post" action="">
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right"><b>招聘职位:</b> </label>
            <div class="col-sm-6">
                <p id="show_position" class="paddingtop7px padding7"></p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right"><b>工作经验:</b> </label>
            <div class="col-sm-6">
                <p id="show_years" class="paddingtop7px padding7"
                   style="word-break:break-all;word-wrap:break-word;width:250px"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right"><b>岗位职责:</b> </label>
            <div class="col-sm-6">
                <p id="show_postDuties" class="paddingtop7px padding7"
                   style="word-break:break-all;word-wrap:break-word;width:250px"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right"><b>职位要求:</b> </label>
            <div class="col-sm-6">
                <p id="show_jobRequirements" class="paddingtop7px padding7"
                   style="word-break:break-all;word-wrap:break-word;width:250px"></p>
            </div>
        </div>
    </form>
</div>

<!-- 修改form -->
<div id="editRecruitDiv"></div>
<div id="editRecruitDialog" class="hide">
    <form class="form-horizontal" id="updateRecruitForm" method="post" action="" style="margin-top: 15px;">
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="position"><font color="red">*</font>职位: </label>
            <div class="col-sm-6">
                <input type="text" name="position" id="edit_position" maxlength="30"
                       class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:30}">
                <input type="hidden" name="id" id="edit_id" maxlength="32" class="col-xs-10 col-sm-12 {required:true}">
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="years">工作经验: </label>
            <div class="col-sm-6">
                <select name="years" id="edit_years">
                    <option value="0">不限</option>
                    <option value="1">0-1年</option>
                    <option value="2">1-3年</option>
                    <option value="3">3-5年</option>
                    <option value="4">5-10年</option>
                    <option value="5">10年以上</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="postDuties"><font color="red">*</font>岗位职责:
            </label>
            <div class="col-sm-6">
                <textarea class="form-control" name="postDuties" id="edit_postDuties" cols="30" rows="6"
                          class="col-xs-10 col-sm-12 {required:true,rangelength:[1,5]}"></textarea>
            </div>
        </div>


        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><font color="red">*</font>职位要求: </label>
            <div class="col-sm-6">
                <textarea class="form-control" name="jobRequirements" id="edit_jobRequirements" cols="30" rows="6"
                          class="col-xs-10 col-sm-12 {required:true,rangelength:[1,5]}"></textarea>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="/js/medical/recruit.js?v=ipandatcm_1.3"></script>