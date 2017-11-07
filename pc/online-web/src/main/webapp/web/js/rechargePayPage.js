/**
 * Created by admin on 2016/11/8.
 */
$(function() {
	var payType;
	
	$(".boxuegu-protocol").click(function() {
		$(".protocol-shadow").css({
			"display": "block",
			"backgroundColor": "rgba(0,0,0,0.5)"
		});
	});
	$(".pay-protocol-close").click(function() {
		$(".protocol-shadow").css("display", "none");
	});
	//支付方式
	$(".pay-ways>span").click(function() {
		if($(this).hasClass("noClick")) {

		} else {
			$(this).addClass("select").find(".selectImg1").css("display", "block");
			$(this).siblings().removeClass("select").find(".selectImg1").css("display", "none");
			$(".unionPayAccount").css("display", "none");
		}
	})

	//银联支付效果
	function unionPayHover() {
		$(".unionPay").hover(function() {
			$(".unionPayAccount").css("display", "block");
			$(".unionPay>.selectImg1").css("display", "none");
		}, function() {
			$(".unionPayAccount").css("display", "none");
			if($(this).hasClass("select")) {
				$(".unionPay>.selectImg1").css("display", "block");
			}
		})
	}

	unionPayHover();
	$(".unionPayAccount li").click(function(event) {
		event.stopPropagation();
		$(this).addClass("select").siblings().removeClass("select");
		$(".unionPay").addClass("select").siblings().removeClass("select");
		$(".selectImg1").css("display", "none");
		$(".unionPay").removeClass("noClick");
		var imgSrc = $(this).find("img:first-child").attr("src");
		$(".unionPay>img:first-child").attr("src", imgSrc);
	});
	//显示更多
	var $category = $('.unionPayAccount li:gt(5):not(:last)');
	$category.hide();
	var $toggleBtn = $('.more');
	$toggleBtn.click(function() {
		if($category.is(":visible")) {
			$category.hide();
			$(this).find("em").text("查看更多");
		} else {
			$category.show();
			$(this).find("em").text("收起");
		}
	})
	$(".pay-protocol-box").click(function() {
		$(".pay-protocol-box em").removeClass("select");
		$(".protocol-tip").css("display", "block");
	});
	$(".tip-top,.tip-btn").click(function() {
		$(".protocol-tip").css("display", "none");
		$(".pay-protocol-box em").addClass("select");
	});
	$(".boxuegu-protocol2").click(function() {
		$(".protocol-shadow").css({
			"display": "block",
			"backgroundColor": "rgba(255,255,255,0)"
		});
	})
	$(".pay-result1-close").click(function() {
		$(".pay-result1").css("display", "none");
	})
	$(".pay-result2-close").click(function() {
		$(".userShip").css("display", "none");
		$(".pay-result2").css("display", "none");
	})

	//订单名称
	var all = courseName.split(",");
	var allName = "";
	for(var i = 0; i < all.length; i++) {
		allName += "<span>" + all[i] + "</span>";
	};
	$(".order-courseName").html(allName);
	//订单号
	$(".order-number").text(orderNo);
	//支付金额
	$(".pay-prices").text(actualPay);
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
	//立即支付
	//0 支付宝 1 微信  2 银联
	$(".order-pay-btn").click(function() {
		RequestService("/userCoin/getRchargeOrderNo", "GET", {
		}, function(data) {
			if(data.success){
				$(".pay-ways span").each(function() {
					if($(this).hasClass("select")) {
						payType = $(this).attr("data-payType");
					}
					return;
				});
				if(payType == 0) {
					$(".pay-result1").css("display", "block");
                    window.open("/web/alipay/recharge/" + actualPay+"/"+data.resultObject);
				} else if(payType == 1) {
					$(".pay-result1").css("display", "block");
					window.open("/web/weixin_pay_unifiedorder/recharge/"+actualPay+"/"+data.resultObject);
				}
			}
		},false);
	});

	//确定
	$(".userShip-btn").click(function() {
		RequestService("/share/saveShareRelation", "GET", null, function() {

		}, false);
		$(".userShip").css("display", "none");
		$(".pay-result1").css("display", "block");
		window.open("/web/weixin_pay_unifiedorder/" + orderNo);
	});
	//取消按钮
	$(".userShip-cancleBtn").click(function() {
		$(".userShip").css("display", "none");
		$(".pay-result1").css("display", "block");
		window.open("/web/weixin_pay_unifiedorder/" + orderNo);
	});
	//支付结果页面1
	$(".pay-success-btn").click(function() {
		location.href="/web/html/personcenter.html"
		localStorage.setItem("personcenter","mymoney ");
		localStorage.setItem("findStyle","profile ");
		window.open('/web/html/personcenter.html')
	});
	//支付结果页面2
	$(".pay-result2-btn").click(function() {
		$(".pay-result2").css("display", "none");
	})

	function rTips(errorMessage) {
		$(".rTips").text(errorMessage);
		$(".rTips").css("display", "block");
		setTimeout(function() {
			$(".rTips").css("display", "none");
		}, 2000)
	};
})