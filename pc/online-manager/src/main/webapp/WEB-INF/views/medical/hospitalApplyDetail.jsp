<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>

<link href="/css/medical/doctorApplyDetail.css" type="text/css" />

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
	</small> <span> 医馆入驻详情 </span>
</div>

<div class="page-main">

	<input type="hidden" name="applyId" id="applyId" value="${medicalHospitalApply.id}">

	<div class="doctor-name-container">
		<div class="doctor-name">医馆名称：</div>
		<div class="doctor-name-text">${medicalHospitalApply.name}</div>
	</div>

	<div class="doctor-name-container">
		<div class="doctor-name">所属公司：</div>
		<div class="doctor-name-text">${medicalHospitalApply.company}</div>
	</div>

	<div class="doctor-cardnum-container">
		<div class="doctor-cardnum">统一社会信用代码：</div>
		<div class="doctor-cardnum-text">${medicalHospitalApply.businessLicenseNo}</div>
	</div>

	<div class="doctor-cardPicture-container">
		<div class="doctor-cardPicture">营业执照：</div>
		<img class="doctor-cardPositive-img" src="${medicalHospitalApply.businessLicensePicture}" />
	</div>

	<div class="doctor-cardnum-container">
		<div class="doctor-cardnum">药品经营许可证号：</div>
		<div class="doctor-cardnum-text">${medicalHospitalApply.licenseForPharmaceuticalTrading}</div>
	</div>

	<div class="doctor-cardPicture-container">
		<div class="doctor-cardPicture">药品经营许可证：</div>
		<img class="doctor-cardPositive-img" src="${medicalHospitalApply.licenseForPharmaceuticalTradingPicture}" />
	</div>

	<div class="doctor-name-container">
		<div class="doctor-name">申请时间：</div>
		<fmt:formatDate value="${medicalHospitalApply.createTime}"  type="both" />
	</div>

	<c:choose>
		<c:when test="${medicalHospitalApply.status == 2}">
			<div class="action">
				<div class="pass" id="pass">通过</div>
				<div class="reject" id="reject">拒绝</div>
			</div>
		</c:when>
		<c:when test="${medicalHospitalApply.status == 1}">
			<div class="action">
				<div class="pass" id="passed">已通过</div>
			</div>
		</c:when>
		<c:when test="${medicalHospitalApply.status == 0}">
			<div class="action">
				<div class="reject" id="rejected">已拒绝</div>
			</div>
		</c:when>
	</c:choose>
</div>

<script src="/js/layer/layer.js"></script>
<script type="text/javascript" src="/js/medical/hospitalApplyDetail.js?v=ipandatcm_1.3"></script>