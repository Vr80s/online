<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/jstl_taglib.jsp" %>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css"/>
<link href="/js/layer/skin/layer.css" type="text/css"/>

<style type="text/css">
    .vertical-tab {
        width: 8%;
        height: 100%;
        float: left;
        /* overflow: hidden; */
        margin-top: 50px;
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

    .show_description img {
        width: 250px;
        height: 200px;
    }

</style>
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
    <span> 医馆管理 </span>
</div>

<div style="height: 100%;" class="clearfix">
    <ul class="nav nav-tab vertical-tab" role="tablist" id="vtab">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" class="zykgl_bx" role="tab"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">医馆管理</a>
        </li>
        <li role="presentation">
            <a href="#box_px" aria-controls="box_px" class="kctj_bx" role="tab" onclick="updateRec(null,1)"
               data-toggle="tab" style="padding-left: 0px;padding-right: 0px;">医馆推荐</a>
        </li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <div class="mainrighttab tabresourse bordernone" id="courseDiv">
                <p class="col-xs-8" style="padding: 0;">
                    <%--<button class="btn btn-sm btn-success add_P" title="新增">--%>
                        <%--<i class="glyphicon glyphicon-plus"></i> 新增--%>
                    <%--</button>--%>
                    <button class="btn btn-sm btn-success dele_P" title="批量删除">
                        <i class="glyphicon glyphicon-trash"></i> 批量删除
                    </button>
                    <button class="btn btn-sm btn-success rec_P" title="设为推荐">
                        <i class="glyphicon glyphicon-cog"></i> 设为推荐
                    </button>
                    <button class="btn btn-sm btn-success J-announcement" title="公告管理">
                        公告管理
                    </button>
                </p>

                <div class="searchDivClass" id="searchDiv">
                    <div class="profile-info-row">
                        <table frame=void>
                            <tr>
                                <td>
                                    <div class="profile-info-value searchTr">
                                        <input type="text" placeholder="医馆名" class="propertyValue1"
                                               id="search_courseName" style="width: 150px;">
                                        <input type="hidden" value="search_courseName" class="propertyName"/>
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
                                    <div class="profile-info-value searchTr">
                                        <select name="search_authentication" id="search_authentication" value="" class="propertyValue1">
                                            <option value="">是否已认证</option>
                                            <option value="1">已认证</option>
                                            <option value="0">未认证</option>
                                        </select>
                                        <input type="hidden" value="search_authentication" class="propertyName"/>
                                    </div>
                                </td>
                                <td>
                                    <div class="profile-info-value searchTr">
                                        <select name="search_score" id="search_score" value="" class="propertyValue1">
                                            <option value="">星级评分</option>
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                        </select>
                                        <input type="hidden" value="search_score" class="propertyName"/>
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
                        <table id="courseTable"
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
                        <table id="courseTable_PX"
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
<div id="dialogAddCourseDiv"></div>
<div id="addCourseDialog" class="hide">
    <form id="addCourse-form" class="form-horizontal" method="post" action="" style="margin-top: 15px;">
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="name"><font color="red">*</font>医馆名称: </label>
            <div class="col-sm-6">
                <input type="text" name="name" id="name"
                       class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="tel"><font color="red">*</font>联系电话: </label>
            <div class="col-sm-6">
                <input type="text" name="tel" id="tel" maxlength="11" class="col-xs-10 col-sm-12 {required:true}">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="lal">经纬度: </label>
            <div class="col-sm-6">
                <input type="text" id="lal" name="lal" maxlength="10" class="col-xs-10 col-sm-12 ">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="postCode">邮编: </label>
            <div class="col-sm-6">
                <input type="text" id="postCode" name="postCode" maxlength="15"
                       class="col-xs-10 col-sm-12 {number:true,digits:true,minlength:5}">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="email">email: </label>
            <div class="col-sm-6">
                <input type="text" id="email" name="email" maxlength="100" class="col-xs-10 col-sm-12 ">
            </div>
        </div>
        <div class="space-4"></div>


        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="province"><font color="red">*</font>所在省市:
            </label>
            <div class="col-sm-3">
                <select id="province" onchange="doProvAndCityRelation();"
                        class="clearfix col-xs-10 col-sm-12 {required:true}">
                </select>
                <input type="hidden" name="province" id="realProvince"/>
            </div>
            <div class="col-sm-3">
                <select id="citys" onchange="onchangeCityAdd();" class="clearfix col-xs-10 col-sm-12 {required:true}">
                 </select>
                <input type="hidden" name="city" id="realCitys"/>
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" id="add-currentPrice" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="detailedAddress"><font color="red">*</font>详细地址:
            </label>
            <div class="col-sm-6">
                <input type="text" name="detailedAddress" value="" id="address"
                       class="col-xs-10 col-sm-12 {required:true}">
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right">医馆简介: </label>
            <div class="col-sm-6">
                <textarea class="form-control" name="description" id="description" rows="3"
                          class="col-xs-10 col-sm-12 {required:true,rangelength:[1,170]}"></textarea>
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right">评分: </label>
            <div class="col-sm-6">
                <input type="text" name="score" maxlength="50"
                       class="col-xs-10 col-sm-12 {required:true,number:true,digits:true,range:[0,5]}">
            </div>
        </div>
    </form>
</div>

<!-- 查看form -->
<div id="showCourseDiv"></div>
<div id="showCourseDialog" class="hide">
    <form id="addCourse-form1" class="form-horizontal" method="post" action="">
        <input type="hidden" id="edit_id" name="id" class="col-xs-10 col-sm-8 {required:true}"
               style="margin-top: 15px;">
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" for="name"><font color="red">*</font><b>医馆名称:</b>
            </label>
            <div class="col-sm-6">
                <p id="show_name" class="paddingtop7px padding7"></p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" for="tel"><font color="red">*</font><b>联系电话:</b>
            </label>
            <div class="col-sm-6">
                <p id="show_tel" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" for="lal"><b>经纬度:</b> </label>
            <div class="col-sm-6">
                <p id="show_lal" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" for="postCode"><b>邮编:</b> </label>
            <div class="col-sm-6">
                <p id="show_postCode" class="paddingtop7px padding7"></p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" for="email"><b>email:</b> </label>
            <div class="col-sm-6">
                <p id="show_email" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right"><font color="red">*</font><b>所在省:</b> </label>
            <div class="col-sm-6">
                <p id="show_province" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right"><font color="red">*</font><b>所在市:</b> </label>
            <div class="col-sm-6">
                <p id="show_city" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" for="detailedAddress"><font color="red">*</font><b>详细地址:</b>
            </label>
            <div class="col-sm-6">
                <p id="show_detailedAddress" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right"><b>评分:</b> </label>
            <div class="col-sm-6">
                <p id="show_score" class="paddingtop7px padding7"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label no-padding-right" for="description"><b>医馆简介:</b> </label>
            <div class="col-sm-6">
                <p id="show_description" class="paddingtop7px padding7 show_description"
                   style="word-break:break-all;word-wrap:break-word;width:250px"></p>
            </div>
        </div>
    </form>
</div>

<!-- 修改form -->
<div id="dialogEditCourseDiv"></div>
<div id="EditCourseDialog" class="hide">
    <form class="form-horizontal" id="updateCourse-form" method="post" action="" style="margin-top: 15px;">
        <input type="hidden" id="editHospital_id" name="id" class="col-xs-10 col-sm-8 {required:true}">
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="name"><font color="red">*</font>医馆名称: </label>
            <div class="col-sm-6">
                <input type="text" name="name" id="edit_name" maxlength="20"
                       class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:20}">
            </div>
        </div>
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="tel"><font color="red">*</font>联系电话: </label>
            <div class="col-sm-6">
                <input type="text" name="tel" id="edit_tel" maxlength="11" class="col-xs-10 col-sm-12 {required:true}">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="lal">经纬度: </label>
            <div class="col-sm-6">
                <input type="text" id="edit_lal" name="lal" maxlength="50" class="col-xs-10 col-sm-12 ">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="postCode">邮编: </label>
            <div class="col-sm-6">
                <input type="text" id="edit_postCode" name="postCode" maxlength="15"
                       class="col-xs-10 col-sm-12 {number:true,digits:true,minlength:5}">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="email">email: </label>
            <div class="col-sm-6">
                <input type="text" id="edit_email" name="email" maxlength="100" class="col-xs-10 col-sm-12 ">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="email">微信: </label>
            <div class="col-sm-6">
                <input type="text" id="edit_wechat" name="wechat" maxlength="100" class="col-xs-10 col-sm-12 ">
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="email">联系人: </label>
            <div class="col-sm-6">
                <input type="text" id="edit_contactor" name="contactor" maxlength="100" class="col-xs-10 col-sm-12 ">
            </div>
        </div>
        <div class="space-4"></div>


        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right"><font color="red">*</font>所在省市: </label>
            <div class="col-sm-3">
                <select id="edit_province" onchange="doProvAndCityRelationEdit();"
                        class="clearfix col-xs-10 col-sm-12 {required:true}">
                    </select>
                <input type="hidden" name="province" id="edit_realProvince"/>
            </div>
            <div class="col-sm-3">
                <select id="edit_citys" onchange="onchangeCityEdit();"
                        class="clearfix col-xs-10 col-sm-12 {required:true}">
                    </select>
                <input type="hidden" name="city" id="edit_realCitys"/>
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="detailedAddress"><font color="red">*</font>详细地址:
            </label>
            <div class="col-sm-6">
                <input type="text" name="detailedAddress" value="" id="edit_address"
                       class="col-xs-10 col-sm-12 {required:true}">
            </div>
        </div>

        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="description">医馆简介: </label>
            <div class="col-sm-6">
                <textarea class="form-control" name="description" id="edit_description" rows="3"></textarea>
                <%--<input type="hidden" name="descriptionHid" id="edit_descriptionHid" class="col-xs-10 col-sm-12 {required:true,rangelength:[1,170]}">--%>
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="postCode">是否已认证: </label>
            <div class="col-sm-6">
                <input type="radio" id="edit_authentication_y" name="authentication" value="1">是
                <input type="radio" id="edit_authentication_n" name="authentication" value="0">否
            </div>
        </div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right">评分: </label>
            <div class="col-sm-6">
                <input type="text" id="edit_score" name="score" maxlength="50"
                       class="col-xs-10 col-sm-12 {required:true,number:true,range:[0,5]}">
            </div>
        </div>
    </form>
</div>


<!-- 修改推荐图片-->
<div id="dialogUpdateRecImgDiv"></div>
<div id="updateRecImgDialog" class="hide">
    <form id="updateRecImg-form" class="form-horizontal" method="post" action="" style="margin-top: 15px;">
        <input type="hidden" name="id" id="update_id">
        <div class="space-4"></div>
        <div class="form-group" style="margin-top: 18px;">
            <label class="col-sm-3 control-label no-padding-right" for="imgPath"><font color="red">*</font>医馆图片:
            </label>
            <div class="col-sm-6" id="updateRecImg">
                <div class="clearfixUpdate">
                    <input type="file" name="update_recImgPath_file" id="update_recImgPath_file" class="uploadImg"/>
                </div>
                （图片尺寸上传限制：252*97）
                <input name="recImgPath" id="update_recImgPath" value="" type="text" class="{required:true}"
                       style="position: absolute; opacity: 0; filter:Alpha(opacity=0);">
            </div>
        </div>
    </form>
</div>

<!-- 查看 -->
<div id="childMenuDialogDiv"></div>
<div id="childMenuDialog" class="hide">

    <form class='form-horizontal' id="childMenu-form" method="post" action="">
        <input type="hidden" name="id" id="parentId"/>
        <div class='form-group'>
            <div class='col-sm-3 control-label no-padding-right'><b>医馆名称:</b></div>
            <div id="child_MenuName" class='col-sm-8 paddingtop7px padding7'></div>
        </div>
        <div class='form-group'>
            <div class='col-sm-3 control-label no-padding-right'><b>医疗领域:</b></div>
            <div class='col-sm-8'>
                <%--<input type="hidden" name="nouse" id="nouse" value="nouse" autofocus="autofocus">--%>
                <table id="childMenus">

                </table>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="/js/medical/provinces.js?v=ipandatcm_1.3"></script>
<script type="text/javascript" src="/js/medical/hospital.js?v=ipandatcm_1.3"></script>