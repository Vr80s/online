<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>

<link href="/css/medical/doctorInfo.css" type="text/css" />

<script type="text/javascript">

    try {
        var scripts = [ null, null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        });
    } catch (e) {
    }
    var weburl = '${weburl}';

    var doctorId = '${doctorId}';
    var mdaiId = '${mdaiId}';
    $(function () {
        $('#vtab li').click(function () {
            tops = $(this).offset().top - $('#vtab').offset().top + $('#vtab').scrollTop() - $('#vtab').height() / 4;
            $('#vtab').animate({
                scrollTop: tops
            }, 'slow');
        });
    });
</script>
<style>
	.remove {
		display: none !important;
	}

</style>
<div class="page-header">
	当前位置：医师医馆<small> <i class="ace-icon fa fa-angle-double-right"></i>
	</small> <span> 医师详情</span>
</div>

<div class="page-main">

	<input type="hidden" name="applyId" id="applyId" value="${doctor.id}">

	<div class="doctor-name-container container">
		<div class="doctor-name container-left">医师姓名：</div>
		<div class="doctor-name-text">${doctor.name}</div>
	</div>

	<div class="doctor-title-container container">
		<div class="doctor-title container-left">职称：</div>
		<div class="doctor-title-text">${doctor.title}</div>
	</div>

	<div class="doctor-field-container container">
		<div class="doctor-type container-left">医师类别：</div>
		<div class="doctor-type-text">${doctor.type}</div>
	</div>

	<div class="container">
		<div class="doctor-province container-left">所在省：</div>
		<div class="doctor-province-text">${doctor.province}</div>
	</div>

	<div class="container">
		<div class="doctor-city container-left">所在市：</div>
		<div class="doctor-city-text">${doctor.city}</div>
	</div>

	<div class="container">
		<div class="doctor-detailedAddress container-left">详细地址：</div>
		<div class="doctor-detailedAddress-text">${doctor.detailedAddress}</div>
	</div>

	<div class="doctor-description-container container">
		<div class="doctor-workTime container-left">坐诊时间：</div>
		<div class="doctor-workTime-text">${doctor.workTime}</div>
	</div>

	<div class="doctor-description-container container">
		<div class="doctor-description container-left">医师简介：</div>
		<div class="doctor-description-text">${doctor.description}</div>
	</div>

</div>


<div class="container">
	<div style="font-size: 18px; width: 120px;display:flex;flex-direction: row;justify-content: flex-end;color: #B22222;">证明图片：</div>
</div>

<div class="tab-content" style="padding-left: 0;">

                    <div role="tabpanel" class="tab-pane active" id="profile">
                    <div id="divKcxq">
                    <!-- 增加form -->
                    <div id="detailDiv">
                    <form class="form-horizontal" id="courseDetailForm" method="post"
                action="">
                    <input type="hidden" name="medicalDoctorId" id="medicalDoctorId"
                value="${doctorId}">
                    <c:if test="${mdaiId !=null}">
                    <input type="hidden" name="authenticationInformationId"
                " value="${mdaiId}">
                </c:if>

                <input type="hidden" name="weburl" id="weburl" value="${weburl}">
                    <input type="hidden" name="page" id="page" value="${param.page}">
                    <div class="form-group col-sm-6" style="margin-top: 18px;">
                    <label class="col-sm-2 control-label no-padding-right"><font
                color="red">*</font>真实头像:</label>
                <div class="col-sm-4" style="width: 285px; height: 140px;">
                    <div class="clearfix">
                    <input type="file" name="smallImgPath_file"
                id="smallImgPath_file" class="uploadImg" />
                    </div>
                    <input name="picture1" id="edit_smallImgPath" value="" class="{required:true}"
                type="text"
                style="position: absolute; opacity: 0; filter: Alpha(opacity = 0);">
                    </div>
                    </div>
                    <!-- 设置多个展示图 -->
                    <div class="form-group col-sm-6" style="margin-top: 18px;">
                    <label class="col-sm-2 control-label no-padding-right">职称证明:</label>
                <div class="col-sm-4" style="width: 285px; height: 140px;">
                    <div class="clearfix">
                    <input type="file" name="smallImgPath_file"
                id="smallImgPath_file1" class="uploadImg" />
                    </div>
                    <input name="picture2" id="edit_smallImgPath1" value=""
                type="text"
                style="position: absolute; opacity: 0; filter: Alpha(opacity = 0);">
                    </div>
                    </div>

                    <!-- 设置多个展示图 -->
                    <div class="form-group col-sm-6" style="margin-top: 18px;">
                    <label class="col-sm-2 control-label no-padding-right">医师资格证:</label>
                <div class="col-sm-4" style="width: 285px; height: 140px;">
                    <div class="clearfix">
                    <input type="file" name="smallImgPath_file"
                id="smallImgPath_file4" class="uploadImg" />
                    </div>
                    <input name="picture5" id="edit_smallImgPath4" value=""
                type="text"
                style="position: absolute; opacity: 0; filter: Alpha(opacity = 0);">
                    </div>
                    </div>
                    <!-- 设置多个展示图 -->
                    <div class="form-group  col-sm-6" style="margin-top: 18px;">
                    <label class="col-sm-2 control-label no-padding-right">职业医师证:</label>
                <div class="col-sm-4" style="width: 285px; height: 140px;">
                    <div class="clearfix">
                    <input type="file" name="smallImgPath_file"
                id="smallImgPath_file5" class="uploadImg" />
                    </div>
                    <input name="picture6" id="edit_smallImgPath5" value=""
                type="text"
                style="position: absolute; opacity: 0; filter: Alpha(opacity = 0);">
                    </div>
                    </div>
                    </div>

                    </form>
                    </div>
                    <div style="width: 100%; text-align: center;">

                    </div>
                    </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="messages">
                    <div id="divKctj">
                    <div class="mainrighttab tabresourse bordernone" id="courseRecDiv">
                    <p class="col-xs-4">
                    </p>
                    <div class="searchDivClass" id="searchDivTj">
                    <div class="profile-info-value searchTr">
                    <input type="hidden" class="propertyValue1"
                id="search_showCourseId" value="${param.courseId}"> <input
                type="hidden" value="search_showCourseId" class="propertyName" />
                    <input type="hidden" value="8" class="tempMatchType" /> <input
                type="hidden" value="String" class="tempType" />
                    </div>
                    </div>

                    </div>
                    </div>
                    </div>
                    </div>
                    <!-- 预览 -->
                    <div id="dialogShowCourseDetailDiv"></div>
                    <div id="showShowCourseDialog" class="hide">
                    <div id="ylContent"></div>
                    </div>

                    <script type="text/javascript" charset="utf-8"
                src="/ueditor/ueditor.config.js"></script>
				<script type="text/javascript" charset="utf-8"
						src="/ueditor/ueditor.all.min.js">

				</script>
				<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
				<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
				<script type="text/javascript" charset="utf-8"
						src="/ueditor/lang/zh-cn/zh-cn.js"></script>

				<script type="text/javascript"
						src="/js/medical/doctorDetail.js?v=ipandatcm_1.3"></script>
