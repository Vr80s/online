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
	</small> <span> 医馆详情 </span>
</div>

<div class="page-main">

	<div class="container">
		<div class="hospital-name container-left">医馆名称：</div>
		<div class="hospital-name-text">${hospital.name}</div>
	</div>

	<div class="container">
		<div class="hospital-tel container-left">联系电话：</div>
		<div class="hospital-tel-text">${hospital.tel}</div>
	</div>

	<div class="container">
		<div class="hospital-lal container-left">经纬度：</div>
		<div class="hospital-lal-text">${hospital.lal}</div>
	</div>

	<div class="container">
		<div class="hospital-postCode container-left">邮编：</div>
		<div class="hospital-postCode-text">${hospital.postCode}</div>
	</div>

	<div class="container">
		<div class="hospital-email container-left">email：</div>
		<div class="hospital-email-text">${hospital.email}</div>
	</div>

	<div class="container">
		<div class="hospital-province container-left">所在省：</div>
		<div class="hospital-province-text">${hospital.province}</div>
	</div>

	<div class="container">
		<div class="hospital-city container-left">所在市：</div>
		<div class="hospital-city-text">${hospital.city}</div>
	</div>

	<div class="container">
		<div class="hospital-detailedAddress container-left">详细地址：</div>
		<div class="hospital-detailedAddress-text">${hospital.detailedAddress}</div>
	</div>

	<div class="container">
		<div class="hospital-score container-left">评分：</div>
		<div class="hospital-score-text">${hospital.score}</div>
	</div>

	<div class="container">
		<div class="hospital-description container-left">简介：</div>
		<div class="hospital-description-text">${hospital.description}</div>
	</div>
</div>

