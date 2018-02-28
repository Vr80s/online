<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>

<link href="${base}/css/medical/doctorInfo.css" type="text/css" />

<script type="text/javascript">

    try {
        var scripts = [ null, null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        });
    } catch (e) {
    }

    $(function () {
        $('#vtab li').click(function () {
            tops = $(this).offset().top - $('#vtab').offset().top + $('#vtab').scrollTop() - $('#vtab').height() / 4;
            $('#vtab').animate({
                scrollTop: tops
            }, 'slow');
        });
    });
</script>

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
