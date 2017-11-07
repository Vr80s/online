<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var recharge = '${recharge}';
</script>
<%@include file="../../web/html/weixinPayPage.html" %>
<script type="text/javascript">
	var preurl = '${preurl}' ? decodeURIComponent('${preurl}') : null;
	$(function() {
		if('${errorMsg}') {
			alert('下单出错！' + '${errorMsg}');
			window.close();
		} else {
			//订单名称
			var all = '${couseName}'.split(",");
			var allName = "";
			for(var i = 0; i < all.length; i++) {
				allName += "<span>" + all[i] + "</span>";
			};
			$('.orderNumber').text('${orderNo}');
			$('.order-courseName').html(allName);
			$('.coursePrice').text('${price}');
			$('.erweima img').attr('src', '${codeimg}');
			//点击收缩课程名称
			if($(".order-courseName").height() > 30) {
				$(".spread").addClass("active").show()
			} else {
				$(".spread").hide()
			};
			$(".spread").off("click").on("click", function() {
				if($(this).hasClass("active")) {
					$(this).removeClass("active");
					$(".orderName .order-courseName").height("30px");
				} else {
					$(this).addClass("active");
					$(".orderName .order-courseName").height("auto");
				}
			});
		}
	});
</script>