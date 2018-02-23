<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>

<link href="${base}/css/medical/doctorApplyDetail.css" type="text/css" />

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
	</small> <span> 医师入驻详情---- </span>
</div>

<div class="page-main">

	<input type="hidden" name="applyId" id="applyId" value="${medicalDoctorApply.id}">

	<div class="doctor-name-container">
		<div class="doctor-name">医师姓名：</div>
		<div class="doctor-name-text">${medicalDoctorApply.name}</div>
	</div>

	<div class="doctor-cardnum-container">
		<div class="doctor-cardnum">身份证号：</div>
		<div class="doctor-cardnum-text">${medicalDoctorApply.cardNum}</div>
	</div>

	<div class="doctor-cardPicture-container">
		<div class="doctor-cardPicture">身份证：</div>
		<img class="doctor-cardPositive-img" src="${medicalDoctorApply.cardPositive}" />
		<img class="doctor-cardNegative-img" src="${medicalDoctorApply.cardNegative}" />
	</div>

	<div class="doctor-qualification-container">
		<div class="doctor-qualification">医师资格证：</div>
		<img class="doctor-qualification-img" src="${medicalDoctorApply.qualificationCertificate}"/>
	</div>

	<div class="doctor-professional-container">
		<div class="doctor-professional">执业资格证：</div>
		<img class="doctor-professional-img" src="${medicalDoctorApply.professionalCertificate}"/>
	</div>

	<div class="doctor-headPortrait-container">
		<div class="doctor-headPortrait">真实头像：</div>
		<img class="doctor-headPortrait-img" src="${medicalDoctorApply.headPortrait}"/>
	</div>

	<div class="doctor-title-container">
		<div class="doctor-title">职称：</div>
		<div class="doctor-title-text">${medicalDoctorApply.title}</div>
	</div>

	<div class="doctor-titleProve-container">
		<div class="doctor-titleProve">职称证明：</div>
		<img class="doctor-titleProve-img" src="${medicalDoctorApply.titleProve}"/>
	</div>

	<div class="doctor-departments-container">
		<div class="doctor-departments">科室：</div>
		<c:forEach var="department" items="${medicalDoctorApply.departments}">
			<div class="doctor-departments-text">${department.name}</div>
		</c:forEach>
	</div>

	<div class="doctor-field-container">
		<div class="doctor-field">擅长：</div>
		<div class="doctor-field-text">${medicalDoctorApply.field}</div>
	</div>

	<div class="doctor-description-container">
		<div class="doctor-description">个人介绍：</div>
		<div class="doctor-description-text">${medicalDoctorApply.description}</div>
	</div>

	<div class="doctor-address-container">
		<div class="doctor-address">城市：</div>
		<div class="doctor-address-text">
			<span class="procince">${medicalDoctorApply.province}</span>
			<span class="city">${medicalDoctorApply.city}</span>
			<span class="address-detail">${medicalDoctorApply.detailedAddress}</span>
		</div>
	</div>

	<div class="doctor-name-container">
		<div class="doctor-name">申请时间：</div>
		<fmt:formatDate value="${medicalDoctorApply.createTime}"  type="both" />
	</div>

	<c:choose>
		<c:when test="${medicalDoctorApply.status == 2}">
			<div class="action">
				<div class="pass" id="pass">通过</div>
				<div class="reject" id="reject">拒绝</div>
			</div>
		</c:when>
		<c:when test="${medicalDoctorApply.status == 1}">
			<div class="action">
				<div class="pass" id="passed">已通过</div>
			</div>
		</c:when>
		<c:when test="${medicalDoctorApply.status == 0}">
			<div class="action">
				<div class="reject" id="rejected">已拒绝</div>
			</div>
		</c:when>
	</c:choose>
</div>


<script type="text/javascript" src="${base}/js/medical/doctorApplyDetail.js?v=ipandatcm_1.3"></script>