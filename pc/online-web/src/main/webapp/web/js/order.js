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
	$.ajax({
		type: "get",
		url: bath + "/online/user/isAlive",
		async: false,
		success: function(m) {
			//			if(m.success === true) {
			//0不是老学员 1是老学员
			if(m.success === true) {
				var isOldUser = m.resultObject.isOldUser;
			} else {
				var isOldUser = 0;
			};
			//凑单曲的订单课程
			RequestService("/web/findActivityOrder", "get", {
				ids: ids
			}, function(n) {
				if(n.success == true) {
					if(n.resultObject != "" && n.resultObject != undefined) {
						$("#weiOrd").html(template.compile(orderWei)({
							data: n.resultObject
						}));
					};
					//单独的一条条数据
					if(n.resultObject == "" || n.resultObject == undefined || n.resultObject[0].orderNo == "") {
						var orderNo = ""
					} else {
						var orderNo = n.resultObject[0].orderNo;
					};
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
							//返回购物车
							$(".sub .return").off("click").on("click", function() {
								$.ajax({
									type: "get",
									url: bath + "/online/user/isAlive",
									async: false,
									success: function(m) {
										if(m.success === true) {
											window.location.href = "/web/html/shoppingCart.html";
										} else {
											$('#login').modal('show');
											$('#login').css("display", "block");
											$(".loginGroup .logout").css("display", "block");
											$(".loginGroup .login").css("display", "none");
											return false;
										}
									}
								});

							});

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
							orderAll();
							lbImg();
						}
					}, false);
				}
			}, false);
			//			} else {
			//				$('#login').modal('show');
			//				$('#login').css("display", "block");
			//				$(".loginGroup .logout").css("display", "block");
			//				$(".loginGroup .login").css("display", "none");
			//				return false;
			//			}
		}
	});
	var lb =
		'{{each data as $value i}}' +
		'<div class="lbBox">' +
		'<div class="couOrder">' +
		'<p class="tabTitle instead">' +
		'<a href="{{$value.url}}" data-ruleid={{$value.rule_id}} target="_blank">活动："{{$value.name}}"</a> 凑单区' +
		'</p>' +
		'<div class="lunbo">' +
		'<span class="lbLeft" id="lbLeft{{i}}"></span>' +
		'<span class="lbRight" id="lbRight{{i}}"></span>' +
		'<div class="lbmid" id="lbmid{{i}}">' +
		'	<div style="width: 110%;">' +
		'		<ul>' +
		'			{{each $value.listCouse as $a a}}' +
		'			<li>' +
		'				<div class="lbcourse" data-id={{$a.id}}>' +
		'					<div class="bathImg">' +
		'					<span class="check" data-csid="{{$a.id}}"></span>' +
		'						<img src="{{$a.smallimg_path}}" />' +
		'					</div>' +
		'					<div class="bathCon clearfix">' +
		'						<span title="{{$a.courseName}}">{{$a.courseName}}</span>' +
		'						<em>￥{{#add00($a.current_price)}}</em>' +
		'					</div>' +
		'				</div>' +
		'			</li>' +
		'			{{/each}}' +
		'		</ul>' +
		'	</div>' +
		'</div>' +
		'	</div>' +
		'	</div>' +
		'<div class="couDan">' +
		'	<span class="yijian" data-ruleid={{$value.rule_id}}>一键添加至购物车</span>' +
		'	<span class="reCar">加入购物车</span>' +
		'</div>' +
		'</div>' +
		'{{/each }}';
	//凑单区轮播
	function lbImg() {
		RequestService("/web/findActivityCourse", "get", {
			ids: ids
		}, function(data) {
			$("#couOrder").html(template.compile(lb)({
				data: data.resultObject
			}));
			$(".lbmid").each(function() {
				var midId = $(this).attr("id");
				var leftId = $(this).siblings(".lbLeft").attr("id");
				var rightId = $(this).siblings(".lbRight").attr("id");
				$("body").slide({
					boxId: midId, //包围ul的盒模型ID
					autoPlay: true, //自动播放,默认false
					time: "1500", //轮播间隔
					prevId: leftId, //上一页按钮
					nextId: rightId, //下一页按钮
				});
			});
			//跳转到详情页
			$(".lbcourse").off("click").on("click", function(e) {
				window.open("/web/html/payCourseDetailPage.html?id=" + $(this).attr("data-id") + "&courseType=1&free=0");
			});
			$(".lbcourse .check").off().on("click", function() {
				var $this = $(this);
				if($(this).hasClass("act")) {
					$(this).removeClass("act");
					$(this).parents("ul").find(".check").each(function() {
						if($(this).attr("data-csid") == $this.attr("data-csid")) {
							$(this).removeClass("act");
						};
					});
					if($(this).parents("ul").find(".act").length == 0) {
						$(this).parents(".lbBox").find(".reCar").hide();
					}
				} else {
					$(this).parents(".lbBox").find(".reCar").show();
					$(this).addClass("act");
					$(this).parents("ul").find(".check").each(function() {
						if($(this).attr("data-csid") == $this.attr("data-csid")) {
							$(this).addClass("act");
						};
					});
				};
				return false;
			});
			//返回购物车
			$(".hasAddcar i").click(function() {
				$(".hasAddcar").css("display", "none");
			});
			$(".hasAddcar .goCar").click(function() {
				$(".hasAddcar").css("display", "none");
				window.location.href = "/web/html/shoppingCart.html";
			});
			//一键添加
			$(".yijian").off("click").on("click", function() {
				var rule_id = $(this).attr("data-ruleid");
				var courseIds = "";
				RequestService("/shoppingCart/batchJoin", "post", {
					rule_id: rule_id
				}, function(data) {
					if(data.success == true) {
						$(".hasAddcar").show();
						car();
					}
				});
			});
			//添加购物车
			$(".reCar").off("click").on("click", function() {
				var rule_id = "";
				var courseIds = "";
				var box = $(this).parent().parent().find(".couOrder").find(".act");
				if(box != 0) {
					for(var i = 0; i < box.length; i++) {
						if(courseIds.indexOf(box.eq(i).attr("data-csid")) == -1) {
							courseIds += "," + box.eq(i).attr("data-csid");
						}
					};
					courseIds = courseIds.substring(1);
					RequestService("/shoppingCart/batchJoin", "post", {
						courseIds: courseIds
					}, function(data) {
						if(data.success == true) {
							$(".hasAddcar").show();
							car();
						}
					}, false);
				} else {
					return false;
				}
			});
		});
	};
	//购物车数量
	function car() {
		RequestService("/shoppingCart/findCourseNum", "GET", null, function(data) {
			if(data.success == true && data.resultObject != 0) {
				$(".shopping").css("display", "block");
				if(data.resultObject <= 99) {
					$(".shopping em").text(data.resultObject);
				} else {
					$(".shopping em").text(99);
				}
			}
		});
	};
	//页面事件绑定
	function orderAll() {
		//应付金额总价
		var a1 = 0;
		var a2 = 0;
		for(var i = 0; i < $(".xiaoji").length; i++) {
			if(!isNaN(parseFloat($(".xiaoji i span").eq(i).text() * 1))) {
				a1 += parseFloat($(".xiaoji i span").eq(i).text() * 1);
			} else {
				a1 += 0
			};
		};
		for(var i = 0; i < $(".xiaoJiwei").length; i++) {
			if(!isNaN(parseFloat($(".xiaoJiwei i span").eq(i).text() * 1))) {
				a2 += parseFloat($(".xiaoJiwei i span").eq(i).text() * 1);
			} else {
				a2 += 0
			}
		};
		var all = (a1 + a2).toFixed(2);
		$(".js").html("￥" + all);
		//点击认证老学员
		$(".sub .cardNum").off().on("click", function() {
			$.ajax({
				type: "get",
				url: bath + "/online/user/isAlive",
				async: false,
				success: function(m) {
					if(m.success === true) {
						$(".identity").show();
					} else {
						$('#login').modal('show');
						$('#login').css("display", "block");
						$(".loginGroup .logout").css("display", "block");
						$(".loginGroup .login").css("display", "none");
						return false;
					}
				}
			});
			
		});
		$(".identityMess .title i").off().on("click", function() {
			$(".identity").hide();
			$(".identity .warning").hide();
			$(".truename").val("");
			$(".truecard").val("");
		});
		//焦点认证
		$(".truename").off("focus").on("focus", function() {
			$(".name-warn").css("display", "none");
		});
		$(".truename").off("blur").on("blur", function() {
			var value = $(".truename").val();
			if($.trim(value) == "") {
				$(".name-warn").text("真实姓名不能为空").css("display", "inline-block");
				return false;
			} //昵称大于4位
			else if($.trim(value).length < 2 || $.trim(value).length > 7) {
				$(".name-warn").text("真实姓名请输入2-7个汉字").css("display", "inline-block");
				return false;
			} else {
				$(".name-warn").css("display", "none");
			}
		});
		$(".truecard").off("focus").on("focus", function() {
			$(".shenfen-warn").css("display", "none");
		});
		$(".truecard").off("blur").on("blur", function() {
			var cardPatt = /^\d{17}(\d|x)$/;
			var value = $.trim($(".shenfen input").val());
			if(value == "") {
				$(".shenfen-warn").text("身份证号不能为空").css("display", "inline-block");
				return false;
			} else if(isCardID(value) != true) {
				$(".shenfen-warn").text(isCardID(value)).css("display", "inline-block");
				return false;
			} else {
				$(".shenfen-warn").css("display", "none");
			}
		});
		//提交认证
		$(".startIden").off("click").on("click", function() {
			var name = true;
			var card = true;
			var value = $(".truename").val();
			var cardPatt = /^\d{17}(\d|x)$/;
			if($.trim(value) == "") {
				$(".name-warn").text("真实姓名不能为空").css("display", "inline-block");
				name = false;
			} //昵称大于4位
			else if($.trim(value).length < 2) {
				$(".name-warn").text("真实姓名为2-7个汉字").css("display", "inline-block");
				name = false;
			};
			var value1 = $.trim($(".shenfen input").val());
			if(value1 == "") {
				$(".shenfen-warn").text("身份证号不能为空").css("display", "inline-block");
				card = false;
			} else if(isCardID(value1) != true) {
				$(".shenfen-warn").text(isCardID(value1)).css("display", "inline-block");
				card = false;
			};
			if(name && card) {
				RequestService("/online/apply/isOldUser", "get", {
					realName: value,
					idCardNumber: value1,
					lot_no:17001
				}, function(data) {
					if(data.success == true) {
						if(data.resultObject == false) {
							$(".shenfen-warn").text("认证失败").css("display", "inline-block");
						} else {
							$(".rrTips").html("认证成功").css("display", "block");
							setTimeout(function() {
								window.location.reload();
							}, 1000);
						}
					} else {
						$(".shenfen-warn").text("该身份证号已被填写").css("display", "inline-block");
					};
				});
			};
		});
	};
};


//购物车列表点击事件
template.helper('orderClick', function (order) {
	var	a = '<a style="cursor:pointer;color=#333;display: block;width: 100%; height: 100%;"  href="/course/courses/'+order.id+'"  target="_blank">';
	return a;
});