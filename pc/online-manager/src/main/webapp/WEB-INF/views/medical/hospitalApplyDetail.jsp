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
	</small> <span> 医馆入驻详情 </span>
</div>

<div class="page-main">

	<input type="hidden" name="applyId" id="applyId" value="${medicalHospitalApply.id}">

	<div class="doctor-name-container">
		<div class="doctor-name">所属公司：</div>
		<div class="doctor-name-text">${medicalHospitalApply.company}</div>
	</div>

	<div class="doctor-cardnum-container">
		<div class="doctor-cardnum">营业执照号：</div>
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

	<div class="action">
		<div class="pass" id="pass">通过</div>
		<div class="reject" id="reject">拒绝</div>
	</div>
</div>

<script src="${base}/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/js/medical/hospitalApplyDetail.js?v=ipandatcm_1.3"></script>