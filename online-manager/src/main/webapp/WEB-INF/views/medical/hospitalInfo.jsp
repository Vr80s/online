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
		<div class="hospital-name container-left my-setWhite">医馆名称：</div>
		<div class="hospital-name-text my-setWhite-test">${hospital.name}</div>
	</div>

	<div class="container">
		<div class="hospital-tel container-left my-setWhite">联系电话：</div>
		<div class="hospital-tel-text my-setWhite-test">${hospital.tel}</div>
	</div>

	<div class="container">
		<div class="hospital-lal container-left my-setWhite">经纬度：</div>
		<div class="hospital-lal-text my-setWhite-test">${hospital.lal}</div>
	</div>

	<div class="container">
		<div class="hospital-postCode container-left my-setWhite">邮编：</div>
		<div class="hospital-postCode-text my-setWhite-test">${hospital.postCode}</div>
	</div>

	<div class="container">
		<div class="hospital-email container-left my-setWhite">email：</div>
		<div class="hospital-email-text my-setWhite-test">${hospital.email}</div>
	</div>

	<div class="container">
		<div class="hospital-province container-left my-setWhite">所在省：</div>
		<div class="hospital-province-text my-setWhite-test">${hospital.province}</div>
	</div>

	<div class="container">
		<div class="hospital-city container-left my-setWhite">所在市：</div>
		<div class="hospital-city-text my-setWhite-test">${hospital.city}</div>
	</div>

	<div class="container">
		<div class="hospital-detailedAddress container-left my-setWhite">详细地址：</div>
		<div class="hospital-detailedAddress-text my-setWhite-test">${hospital.detailedAddress}</div>
	</div>

	<div class="container">
		<div class="hospital-score container-left my-setWhite">评分：</div>
		<div class="hospital-score-text my-setWhite-test">${hospital.score}</div>
	</div>

	<div class="container">
		<div class="hospital-description container-left my-setWhite">简介：</div>
		<div class="hospital-description-text my-setWhite-test">${hospital.description}</div>
	</div>
</div>

