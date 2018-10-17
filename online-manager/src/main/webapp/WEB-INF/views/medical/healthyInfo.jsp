<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>

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
<script type="text/javascript">
    var recordId = '${recordId}';
    var sex = 1;//'${sex}';
</script>
<style>
	.ajax-loading-overlay {
		display: none !important;
	}

</style>
<div class="page-header">
	当前位置：健康评测<small> <i class="ace-icon fa fa-angle-double-right"></i>
</small> <span> 评测详情</span>
</div>
<div class="result" style="font-size: 20px;"></div>
<div class="page-main">
	<table class="table">
		<thead>
		<tr>
			<th style="width: 50px">题号</th>
			<th>题目</th>
			<th>分值</th>
			<th style="width: 150px">病名</th>
			<th style="width: 200px">症状</th>
			<th>对应自诊证型前台标签</th>
		</tr>
		</thead>
		<tbody class="list">
		<%--<tr>
			<td>1</td>
			<td>平时有胸口发闷，喘气喘不到头，或者呼吸短促，接不上气的感觉，在劳累后症状加重吗</td>
			<td>A没有 B经常 C欧文</td>
			<td>感冒</td>
			<td>感冒</td>
			<td rowspan="5">体质虚弱</td>
		</tr>
		<tr>
			<td>2</td>
			<td>平时有胸口发闷，喘气喘不到头，或者呼吸短促，接不上气的感觉，在劳累后症状加重吗</td>
			<td>A没有 B经常 C欧文</td>
			<td>感冒</td>
			<td>感冒</td>
		</tr>
		<tr>
			<td>3</td>
			<td>平时有胸口发闷，喘气喘不到头，或者呼吸短促，接不上气的感觉，在劳累后症状加重吗</td>
			<td>A没有 B经常 C欧文</td>
			<td>感冒</td>
			<td>感冒</td>
		</tr>
		<tr>
			<td>4</td>
			<td>平时有胸口发闷，喘气喘不到头，或者呼吸短促，接不上气的感觉，在劳累后症状加重吗</td>
			<td>A没有 B经常 C欧文</td>
			<td>感冒</td>
			<td>感冒</td>
		</tr>
		<tr>
			<td>5</td>
			<td>平时有胸口发闷，喘气喘不到头，或者呼吸短促，接不上气的感觉，在劳累后症状加重吗</td>
			<td>A没有 B经常 C欧文</td>
			<td>感冒</td>
			<td>感冒</td>
		</tr>--%>

		</tbody>
	</table>
</div>

<script type="text/javascript"
		src="/js/medical/healthyDetail.js?v=ipandatcm_1.3"></script>
