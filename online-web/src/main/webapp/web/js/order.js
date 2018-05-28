window.onload = function() {
	var courseId = $.getUrlParam("courseId");
	if(courseId == undefined) {
		courseId = 138;
	} else {
		courseId.replace("", "%20");
		courseId = $.trim(courseId);
		courseId.replace(/(^\s*)|(\s*$)/g, "");
	};
	var ids = courseId;
	//	var ord = [198,197,200,435,1,239]
	//	for(i = 0; i < ord.length; i++) {
	//		ids += "," + ord[i]
	//	};
	//	ids = ids.substring(1);
	template.helper("oldPrice", function(num) {
		var price = parseFloat(num) * 1 - 1000;
		if(price < 0) {
			price = 0;
		};
		return price.toFixed(2);
	});
	template.helper("add00", function(num) {
		var price = parseFloat(num) * 1;
		if(price < 0) {
			price = 0;
		};
		return price.toFixed(2);
	});
	var order =
		'{{each data as $value i}}' +
		'<div class="orderTab">' +
		'<div class="instead"></div>' +

		'<div style="border: 1px solid #eee;">' +
		'<div class="tab clearfix">' +
		'<div class="odbox">' +
		'<div class="td1">' +
		'<span class="img">{{#orderClick($value)}}<img src="{{$value.smallImgPath}}"/></a></span>' +
		'<table border="0" cellspacing="" cellpadding="">' +
		'<tr><td><span class="name">{{$value.courseName}}</span></td></tr>' +
		'</table>' +
		'</div>' +
		'<div class="td2" style="visibility:hidden">即日起至{{#expiry($value.create_time)}}</div>' +
		'<div class="td3">￥{{#add00($value.currentPrice)}}</div>' +
		'<div class="td4">' +

		//判断是否老学员
		'{{if $value.course_type==0}}' +
			'{{if isOldUser==1}}' +
				'<table border="0" cellspacing="" cellpadding="">' +
				'<tr style="text-indent:30px"><td>' +
					'{{if ($value.preferentyMoney)!=0}}' +
					'-￥{{#add00($value.preferentyMoney)}}' +
					'{{else}}' +
					'-￥{{#add00($value.preferentyMoney)}}' +
					'{{/if}}' +
				'</td></tr>' +
				'<tr style="text-indent:30px"><td>' +
				'<p><span>-￥1000.00</span><em class="red">老学员优惠</em></p>' +
				'</td></tr>' +
				'</table>' +
				'</div>' +
				'<div class="td5">￥{{#oldPrice($value.currentPrice)}}</div>' +
				'</div>' +
				'</div>' +
				'</div>' +
		
				'<div class="xiaoji">' +
				'<span>小计：</span><i>￥<span>{{#oldPrice($value.currentPrice)}}</span></i>' +
				'</div>' +
			'{{else}}' +
				'<table border="0" cellspacing="" cellpadding="" style="text-align:center">' +
				'<tr><td>' +
					'{{if $value.fcode != undefined}}' +
					'已使用兑换码' +
					'</td></tr>' +
					'</table>' +
					'</div>' +
					'<div class="td5">￥{{#add00($value.priceCount)}}</div>' +
					'{{else}}' +
					'{{if ($value.preferentyMoney)!=0}}' +
					'-￥{{#add00($value.preferentyMoney)}}' +
					'{{else}}' +
					'-' +
					'{{/if}}' +
					'</td></tr>' +
					'</table>' +
					'</div>' +
					'<div class="td5">￥{{#add00($value.currentPrice)}}</div>' +
					'{{/if}}' +
				'</div>' +
				'</div>' +
				'</div>' +
		
				'<div class="xiaoji">' +
				'<span>小计：</span><i>￥<span>{{#add00($value.currentPrice)}}</span></i>' +
				'</div>' +
			'{{/if}}' +
		'{{else}}' +
			'{{if $value.fcode != undefined}}' +
				'<table border="0" cellspacing="" cellpadding="" style="text-align:center">' +
				'<tr><td>' +
					'已使用兑换码' +
				'</td></tr>' +
				'</table>' +
				'</div>' +
				'<div class="td5">￥{{#add00($value.priceCount)}}</div>' +
				'</div>' +
				'</div>' +
				'</div>' +
		
				'<div class="xiaoji">' +
				'<span>小计：</span><i>￥<span>{{#add00($value.priceCount)}}</span></i>' +
				'</div>' +
			'{{else}}'+
				'<table border="0" cellspacing="" cellpadding="" style="text-align:center">' +
				'<tr><td>' +
					'{{if ($value.preferentyMoney)!=0}}' +
					'-￥{{#add00($value.preferentyMoney)}}' +
					'{{else}}' +
					'-' +
					'{{/if}}' +
				'</td></tr>' +
				'</table>' +
				'</div>' +
				'<div class="td5">￥{{#add00($value.currentPrice)}}</div>' +
				'</div>' +
				'</div>' +
				'</div>' +
		
				'<div class="xiaoji">' +
				'<span>小计：</span><i>￥<span>{{#add00($value.currentPrice)}}</span></i>' +
				'</div>' +
			'{{/if}}'+
		'{{/if}}' +
		//判断是否老学员

		'</div>' +
		'{{/each}}';
	var orderWei =
		'{{each data as $value i}}' +
		'<div class="orderTabTwo" >' +
		'<p class="tabTitle instead">' +
		'<a href="{{$value.url}}" data-id={{$value.rule_id}} target="_blank">活动："{{$value.name}}"</a>{{$value.ruleContent}}' +
		'</p>' +
		//遍历tab
		'<div style="border: 1px solid #eee;">' +
		'{{each $value.course as $a a}}' +
		'<div class="tab clearfix">' +
		'<div class="odbox">' +
		'<div class="td1">' +
		'<span class="img"><img src="{{$a.smallImgPath}}"/></span>' +
		'<table border="0" cellspacing="" cellpadding="">' +
		'<tr><td><span class="name">{{$a.courseName}}</span></td></tr>' +
		'</table>' +
		'</div>' +
		'<div class="td2">即日起至{{#expiry($a.create_time)}}</div>' +
		'<div class="td3">￥{{#add00($a.currentPrice)}}</div>' +
		'<div class="td4">' +
		'<table border="0" cellspacing="" cellpadding="" style="text-align:center">' +
		'<tr><td>' +
		'{{if $a.fcode != undefined}}' +
			'已使用兑换码' +
			'</td></tr>' +
			'</table>' +
			'</div>' +
			'<div class="td5">￥{{#add00($a.priceCount)}}</div>' +
		'{{else}}' +
			'{{if ($a.preferentyMoney)!=0}}' +
			'-￥{{#add00($a.preferentyMoney)}}' +
			'{{else}}' +
			'-' +
			'{{/if}}' +
			'</td></tr>' +
			'</table>' +
			'</div>' +
			'<div class="td5">￥{{#add00($a.currentPrice)}}</div>' +
		'{{/if}}' +
		'</div>' +
		'</div>' +
		'{{/each}}' +
		//bianli 
		'</div>' +
		'<div class="xiaoJiwei">' +
		'{{if $value.priceDifference!=0}}' +
		'<span class="a2">总额:<em>￥{{#add00($value.totalPrice)}}</em></span><span class="a3">立减:<em>￥{{#add00($value.priceDifference)}}</em></span>' +
		'{{/if}}' +

		'<span class="a1">小计：</span><i>￥<span>{{#add00($value.subtotal)}}</span></i>' +
		'</div>' +
		'</div>' +
		'{{/each}}';
	var step =
		'<a class="save" style="cursor:pointer"  >提交订单</a>' +
		// '<a class="getRedeem" href="/web/html/myStudyCenter.html?location=fcode">我有课程兑换码，前去兑换</a>' +
		'{{if course_type==0}}' +
		'{{if isOldUser==0}}' +
		'<p>应付金额：<span class="js"></span></p>' +
		// '<a href="javascript:void(0);"  class="return">返回购物车修改</a>' +
//		'<span class="cardNum">验证老学员身份，享受优惠</span>' +
		'{{else}}' +

		'<p>应付金额：<span class="js"></span></p>' +
		// '<a href="javascript:void(0);"  class="return">返回购物车修改</a>' +
		'{{/if}}' +
		'{{else}}' +
		'<p>应付金额：<span class="js"></span></p>' +
		// '<a href="javascript:void(0);"  class="return">返回购物车修改</a>' +
		'{{/if}}';
	var isOldUser = 0;
			RequestService("/web/findInactiveOrder", "get", {
				ids: ids,
				orderNo: orderNo
			}, function(data) {
				if(data.success == true) {
					if(data.resultObject != "" && data.resultObject != undefined) {
						$("#professionalOrd").html(template.compile(order)({
							data: data.resultObject,
							isOldUser: isOldUser
						}));
					};
					var course_type = "1";
					for(var i = 0; i < data.resultObject.length; i++) {
						if(data.resultObject[i].course_type == 0) {
							course_type = 0;
						}
					};
					$(".sub").html(template.compile(step)({
						course_type: course_type,
						isOldUser: isOldUser
					}));
					//提交订单
					if(orderNo == "") {
						orderNo = data.resultObject[0].orderNo;
					} else {

					};

					//提交订单
					$(".sub .save").off().on("click", function() {
						var $this = $(this);
						RequestService("/online/user/isAlive", "GET", null, function(data) {
							if(data.success === true) {
								window.location.href="/web/" + ids + "/" + orderNo + "/saveOrder";
							} else {
								$('#login').modal('show');
								$(".loginGroup .logout").css("display", "block");
								$(".loginGroup .login").css("display", "none");
								return false;
							}
						}, false)
					});
				}
			}, false);
};
