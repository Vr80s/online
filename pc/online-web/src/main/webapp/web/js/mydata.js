function createData() { //模板加载
	$(".pages").css("display", "none");
	$(".view-content-notbodys").html("");
	layer.closeAll('loading');
	//切换选项卡
	$(document).click(function(e) {
		var e = e || event;
		if($(e.target).get(0).className != "select-xiala" && $(e.target).parent().get(0).className != "select-xiala") {
			$(".xiala").css("display", "none")
		}
	})
	$(".tabbar .btn-item").each(function() {
		$(this).click(function() {
			$(this).addClass("color2cb").siblings().removeClass("color2cb");
			var aleft = $(this).offset().left - $(".my-data").offset().left;
			var awidth = $(this).css("width");
			//			$(this).css("color","#2CB82C").siblings().css("color","#333");
			$(".tabbar .pointer").animate({
				"left": aleft,
				"width": awidth
			});
			$(".warning").css("display", "none");
			if($(this).hasClass("kc")) {
				$(".zhanghu").css("display", "none");
				$(".kecheng").css("display", "block");
				$(".geren").css("display", "none");
				$(".usAddress").css("display", "none");
			} else if($(this).hasClass("zh")) {
				$(".zhanghu").css("display", "block");
				$(".geren").css("display", "none");
				$(".kecheng").css("display", "none");
				$(".usAddress").css("display", "none");
				$.ajax({
						type: "get",
						url: "/online/user/isAlive",
						async: false,
						success: function(data) {
							if(data.success === true) {	
								//0不是老学员 1是老学员					
								var isOldUser = data.resultObject.isOldUser;
								if(isOldUser==0){
									$(".zhanghu .yanzheng").show();
									$(".zhanghu .yiyanzheng").hide();
								}else{
									$(".zhanghu .yiyanzheng .xueke span").html(data.resultObject.oldUserSubjectName);
									$(".zhanghu .yiyanzheng .banji span").html(data.resultObject.oldUserClassName);
									$(".zhanghu .yanzheng").hide();
									$(".zhanghu .yiyanzheng").show();
								}
							} else {
								$('#login').modal('show');
								$('#login').css("display", "block");
								$(".loginGroup .logout").css("display", "block");
								$(".loginGroup .login").css("display", "none");
								return false;
							}
						}
					});
			} else if($(this).hasClass("address")){
				$(".usAddress").css("display", "block");
				$(".geren").css("display", "none");
				$(".kecheng").css("display", "none");
				$(".zhanghu").css("display", "none");
				
			}else {
				$(".geren").css("display", "block");
				$(".kecheng").css("display", "none");
				$(".zhanghu").css("display", "none");
				$(".usAddress").css("display", "none");
			};
		});
	});
	addgetdata();
	$(".xiala").css("display", "none");
	//滚动条
	$(".xiala").niceScroll({
		cursorcolor: "#ccc", //#CC0071 光标颜色
		cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
		touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
		cursorwidth: "5px", //像素光标的宽度
		autohidemode: "scroll", //离开内容隐藏
		cursorborder: "0", //     游标边框css定义
		cursorborderradius: "5px", //以像素为光标边界半径
		autohidemode: true //是否隐藏滚动条
	});


	//模态单选框
	$(".cy-myprofile-myfom-dv-radio-zu label").click(function() {
		$(this).find(".radio-cover em").addClass("active").parent().parent().siblings().find(".radio-cover em").removeClass("active");
	})

	//下拉框模态
	$(".select-xiala").each(function() {
		$(this).on("click", function() {
			if($(this).siblings().css("display") == "none") {
				$(".xiala").css("display", "none");
				$(this).siblings().css("display", "block");
			} else {
				$(this).siblings().css("display", "none");
			}
		})
	});
	$(".xiala >span").on("click", function() {
		var text = $(this).html();

		$(this).parent().siblings().html(text);
		$(".xiala").css("display", "none");
		if($(this).attr("id") != undefined) {
			$(this).parent().siblings().attr({
				"data-id": $(this).attr("id"),
				"title": $(this).html()
			});
		}
	});
	//第一个部分的提交函数
	$("#geren").click(function() { //点击保存
		$.ajax({
			type: "get",
			url: bath + "/online/user/isAlive",
			async: false,
			success: function(data) {
				if(data.success === true) {
					geren();
					
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

	//取消点击函数
	$("#cancel").click(function() {
		location.reload();
	});
	$("#cancel2").click(function() {
		addgetdata();
		return $(".kc").click();
	});
	$(".mypassword-g").click(function() {
		$(".form-reset-password").css("display", "block");
		$(".good_resetPwd").css("display", "none");
		$(".form-reset-password2").css("display", "none");
	})
}


function addgetdata() { //回填数据
	RequestService("/online/user/isAlive", "get", null, function(t) {
		if(t.success == true) {
			if(t.resultObject.isOldUser == 1) {
				$(".cardNumber").attr("disabled", "disabled").css("background", "#FAFAFA");
				$(".truename").attr("disabled", "disabled").css("background","#fafafa");
				$(".cardMark").css("display","inline-block");
			}
		} else {
			$('#login').modal('show');
			$('#login').css("display", "block");
			$(".loginGroup .logout").css("display", "block");
			$(".loginGroup .login").css("display", "none");
			return false;
		}
	}, false);
	RequestService("/online/user/getUserData", "GET", "", function(data) {
//		console.log(data)
		if(data.resultObject.nickName == undefined) {
			$(".firsname").val("博小白");
		} else {
			$(".firsname").val(data.resultObject.nickName);
		};
		if(data.resultObject.autograph == undefined) {
			$(".mycytextarea").val("");
		} else {
			$(".mycytextarea").val(data.resultObject.autograph);
		};
		//如果没数据就提示填写信息
		if(data.resultObject.realName == "" || data.resultObject.realName == null) {
			$(".personBack").show();
		}
		
		
		//存放appid
		$(".kc").attr("data-applyId", data.resultObject.applyId);
		$(".username").val(data.resultObject.loginName);
		$(".signature").val(data.resultObject.loginName);
//		$(".WeChat").val(data.resultObject.wechatNo);
		$(".truename").val(data.resultObject.realName);
		$('.zhiYe').val(data.resultObject.occupationOther);
		
		
		//判断用户登录帐号类型，并以此回填
		if(data.resultObject.loginName.indexOf("@") == -1) {
			$(".emailname").val(data.resultObject.email);
			$(".phonenumber").val(data.resultObject.loginName).attr("disabled", "disabled").css("background", "#fafafa");
		} else {
			$(".emailname").val(data.resultObject.loginName).attr("disabled", "disabled").css("background", "#fafafa");
			$(".phonenumber").val(data.resultObject.mobile);
		};
		$(".cardNumber").val(data.resultObject.idCardNo);
		$(".QQnumber").val(data.resultObject.qq);
		if(data.resultObject.sex == 1) {
			$("#myradio1").attr("checked", true);
			$(".cy-myprofile-myfom-dv-radio-zu .radio-cover em").removeClass("active");
			$("#myradio1").parent().find(".radio-cover em").addClass("active");
		} else if(data.resultObject.sex == 0) {
			$("#myradio2").attr("checked", true);
			$(".cy-myprofile-myfom-dv-radio-zu .radio-cover em").removeClass("active");
			$("#myradio2").parent().find(".radio-cover em").addClass("active");
		} else {
			$("#myradio1,#myradio2").attr("checked", false);
			$(".cy-myprofile-myfom-dv-radio-zu .radio-cover em").removeClass("active");
		}
		if(data.resultObject.occupation == "") {
			$(".cy-myprofile-myfom-dv-radio-zu2 input").attr("checked", false);
		} else {
			switch(data.resultObject.occupation) {
				case 24:
					$(".myradio5").attr("checked", true);
					$(".cy-myprofile-myfom-dv-radio-zu2 .radio-cover em").removeClass("active");
					$(".myradio5").parent().find(".radio-cover em").addClass("active");
					break;
				case 23:
					$(".myradio4").attr("checked", true);
					$(".cy-myprofile-myfom-dv-radio-zu2 .radio-cover em").removeClass("active");
					$(".myradio4").parent().find(".radio-cover em").addClass("active");
					//						$(".myradioipt").attr("disabled",true);
					break;
				case 22:
					$(".myradio3").attr("checked", true);
					$(".cy-myprofile-myfom-dv-radio-zu2 .radio-cover em").removeClass("active");
					$(".myradio3").parent().find(".radio-cover em").addClass("active");
					break;
				case 21:
					$(".myradio2").attr("checked", true);
					$(".cy-myprofile-myfom-dv-radio-zu2 .radio-cover em").removeClass("active");
					$(".myradio2").parent().find(".radio-cover em").addClass("active");
					break;
				case 20:
					$(".myradio1").attr("checked", true);
					$(".cy-myprofile-myfom-dv-radio-zu2 .radio-cover em").removeClass("active");
					$(".myradio1").parent().find(".radio-cover em").addClass("active");
					break;
				case 19:
					$(".myradio0").attr("checked", true);
					$(".cy-myprofile-myfom-dv-radio-zu2 .radio-cover em").removeClass("active");
					$(".myradio0").parent().find(".radio-cover em").addClass("active");
					break;
				default:
					$(".cy-myprofile-myfom-dv-radio-zu2 .radio-cover em").removeClass("active");
					break;
			}
		}

	console.log(data);
	var SFinforid = data.resultObject.occupation;
	//身份信息渲染
	
	if(data.resultObject.job.length>=1){
		$(".shenFen").html(template("SFinfor",{item:data.resultObject.job}));
	}
	if(SFinforid){
		$(".shenFen option[value='"+SFinforid+"']").attr("selected","selected");
	}
	
	//职业信息
	if(data.resultObject.occupationOther !=''&&data.resultObject.occupationOther != null){
		$('.zhiYe').val(data.resultObject.occupationOther);
	}
	debugger
	var pcd = {};
        pcd.province=data.resultObject.provinceName;
        pcd.city=data.resultObject.cityName;
        pcd.district=data.resultObject.countyName;
	$(".geren .address-info2").iProvincesSelect("init",pcd);
	}, false);
}

//用户名长度
function nickName() {
	var yonghuReg = /([\u4e00-\u9fa5]+)|(\w+)|([-]+)/g;
	var yonghuNameLength = 0;
	var yonghuName = $(".firsname").val().trim();
	var arr = [];
	yonghuName.replace(yonghuReg, function(a, hanzi, number, zXian) {
		if(hanzi != undefined) {
			var hanziL = hanzi.length * 2;
			arr.push(hanziL);
		}
		if(number != undefined) {
			var numberL = number.length;
			arr.push(numberL);
		}
		if(zXian != undefined) {
			var zXianL = zXian.length;
			arr.push(zXianL);
		}
	});
	for(var i = 0; i < arr.length; i++) {
		yonghuNameLength += arr[i];
	}
	return yonghuNameLength;

}

function geren() {
	RequestService("/online/user/checkNickName", "get", {
		nickName: $.trim($(".firsname").val())
	}, function(data) {
		$(".warning").css("display", "none");
		var value = $(".firsname").val(); // 获取值
		value = $.trim(value); // 用jQuery的trim方法删除前后空格
		//昵称不能为空
		if(value == "" ) {
			$(".nick-warn-name").text("用户名不能为空").css("display", "inline-block");
			return false;
		}
		if(value.length<2 || value.length>20) {
			$(".nick-warn-name").text("用户名长度不能小于2或者大于20").css("display", "inline-block");
			return false;
		}

		if($('.kecheng-man1 em').hasClass('active')){
			sex = 1;
		}else{
			sex = 0;
		}
		
		//身份验证
		if($('.shenFen').val() == 'volvo'){
			$(".nick-warn-shenfen").text("请选择身份信息").css("display", "inline-block");
			return false;
		}
		
		var pcd  = $(".geren .address-info2").iProvincesSelect("val");

//		保存个人信息数据
		RequestService("/online/user/updateUser", "POST", {
			userId: localStorage.userid,
			nickName: $(".firsname").val(),
			autograph: $(".mycytextarea").val(),
			loginName: $(".username").val(),
			// jobyearId: shijian(),
			target: $("#food5").attr("data-id"),
			provinceName: pcd.province,
			cityName: pcd.city,
			countyName: pcd.district,
			province : pcd.province,
			city : pcd.city,
			district :  pcd.district,
			fullAddress: $(".menpaihao").val(),
			sex:sex,
			occupationOther:$('.zhiYe').val(),
			occupation:$('.shenFen option:selected').attr('value'),
		}, function(data) {
			if(data.resultObject == "修改成功") {
				$('.rTips').html('保存成功').fadeIn(500,function(){
					$('.rTips').fadeOut(500)
				});
				
				$(".intro .msg").remove();
				$(".intro").append("<div class='msg' data-maxlengts='11'></div>");
				$(".intro .name").text($(".firsname").val()).attr("title", $(".firsname").val())
				if($.trim($(".mycytextarea").val()) != "") {
					$(".intro .msg0").text($.trim($(".mycytextarea").val())).attr("title", $.trim($(".mycytextarea").val()))
				} else {
					$(".intro .msg0").text("说点什么来彰显你的个性吧……").attr("title", "说点什么来彰显你的个性吧……")
				}

				$(".block-center:eq(1)").text("保存成功");
				$(".loginGroup .name").text($(".firsname").val()).attr("title", $(".firsname").val())
				$(".view-content .view-content-notbodys").html("");
				$(".rrrTips").html("保存成功").css("display", "block");
				setTimeout(function() {
					$(".rrrTips").css("display", "none");
				}, 1500)
			} else {
				$(".rrTips").html("系统繁忙，请稍后再试 !").css("display", "block");
				setTimeout(function() {
					$(".rrTips").css("display", "none");
				}, 1500)
			}
		});
	})
};
