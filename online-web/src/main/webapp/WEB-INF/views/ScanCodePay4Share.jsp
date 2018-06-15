<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../web/html/weixinPayPage.html" %>
<script type="text/javascript">
var preurl = '${preurl}' ? decodeURIComponent('${preurl}') : null ;
$(function(){
	if('${errorMsg}'){
		alert('下单出错！'+'${errorMsg}');
	} else {
		$('.orderNumber').text('${orderNo}');
		$('.courseName').text('${couseName}');
		$('.coursePrice').text('${price}');
		$('.erweima img').attr('src','${codeimg}');
	}
});
</script>

