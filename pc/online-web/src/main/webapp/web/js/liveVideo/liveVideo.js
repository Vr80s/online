function checkLoginStatus(){

    $.ajax({
        type: "get",
        url: bath + "/online/user/loginStatus",
        async: false,
        success: function(data) {
            console.log(data);
            if(data.success === true) {
                if(data.resultObject==0){
                    //alert("当前已登录");
                }else if(data.resultObject==1){
                	alert("请登录后观看！");
                    window.location.href=bath+"/web/html/login.html"
                    //alert("未登录状态");
                }else if(data.resultObject==2){
                    //alert("被顶掉！");
                    // window.location.href=bath+"/otherDevice.html"
                }

            } else {

            }
        }
    });

}

// window.setInterval("checkLoginStatus()",1000*60*5);

var teacherId;
var teacherName;
$(function() {
	var roomid = room_id;
	var username;
	RequestService("/online/user/isAlive", "GET", null, function(data) {
		if(!data.success) {
			username = '' + "游客";
			$(".liveMess .liveMember").hide();
			$(".right-list a").css("cursor", "default");
		} else {
			username = data.resultObject.name;
			$(".liveMess .liveMember").html("<span>" + data.resultObject.name + "</span>，欢迎加入直播间学习");
			$(".fugai").css("display", "none");
			$(".right-list a").css("cursor", "pointer");
			refreshBalance();
		};
	});

	var courseId = course_id;
	var planId = plan_id;
	if(courseId == undefined) {
		courseId = 1;
	}
	//获取直播间课程信息
	RequestService("/online/live/getOpenCourseById", "GET", {
		courseId: courseId,
		planId: planId
	}, function(data) {
		teacherId = data.resultObject.teacherId;
		teacherName = data.resultObject.teacherName;
		//讲师名字
		$(".headMess .name").html(data.resultObject.teacherName);
		teacherName = data.resultObject.teacherName;
		//鲜花数
//		$(".headMess .num").html(data.resultObject.flowers_number);
		$(".headMess .learnd").html(data.resultObject.learn_count);
		//课程名称
		$("#courseName").html(data.resultObject.courseName);
        $("#title-share").html(data.resultObject.courseName); 
		$(".liveMess .lb span").html(data.resultObject.courseName);
		//教师头像
		$(".headImg img").attr("src", data.resultObject.head_img);
		//开始结束时间
		$(".liveMess .liveTime span").html('' + data.resultObject.start_time);
		$(".liwu").html(data.resultObject.giftCount);
		$(".dashang").html(data.resultObject.rewardTotal);
		if(data.resultObject.broadcastState==1){
			$("#liveStatus").html("【正在直播】");
		}else if(data.resultObject.broadcastState==3){
			$("#liveStatus").html("【直播回放】");
		}

		var host = window.location.host;
		var weboshare_url="http://"+host+"/course/courses/"+courseId;
        // $("#weibo_share").attr("href","http://service.weibo.com/share/share.php?url="+weboshare_url+"&title="+description)
        // $("#qq_share").attr("href","http://connect.qq.com/widget/shareqq/index.html?url="+weboshare_url+"&title="+description)
		/**
		 * 微博分享
		 */
		$("#weibo_share").click(function(){
			var  p = {
		        url: weboshare_url,/*获取URL，可加上来自分享到QQ标识，方便统计*/
		        title : data.resultObject.courseName,/*分享标题(可选)*/
		        pic : data.resultObject.smallImgPath /*分享图片(可选)*/
		    };
		    var s = [];
		    for (var i in p) {
		        s.push(i + '=' + encodeURIComponent(p[i] || ''));
		    }
		    var _src = "http://service.weibo.com/share/share.php?" + s.join('&') ;
		    window.open(_src);
		})
		
        /**
         * qq分享
         */
        $("#qq_share").click(function(){
            var  p = {
                url: weboshare_url,/*获取URL，可加上来自分享到QQ标识，方便统计*/
                desc: '中医传承', /*分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔）*/
                title : data.resultObject.courseName,/*分享标题(可选)*/
                summary : data.resultObject.description,/*分享描述(可选)*/
                pics : data.resultObject.smallImgPath  /*分享图片(可选)*/
            };
            var s = [];
            for (var i in p) {
                s.push(i + '=' + encodeURIComponent(p[i] || ''));
            }
            var _src = "http://connect.qq.com/widget/shareqq/index.html?" + s.join('&') ;
            window.open(_src);

            
        })
        /**
         * 如果课程名太长的话，可能导致礼物列表的框框变化
         */
        //alert($(".right-header").css("height"));
/*		var rightHeaderHeight = $(".right-header").css("height");
		rightHeaderHeight = rightHeaderHeight.substring(0,rightHeaderHeight.length-2);
		//alert(rightHeaderHeight);
		if(rightHeaderHeight>150){
			$("#chat-list").css("padding-top","60px;");
		}*/
	});
	
	//直播间访问量增加
	RequestService("/online/live/updateBrowseSum", "get", {
		courseId: courseId
	}, function(data) {

	});
	//未登录状态点击登录
	$(".fugai span").click(function() {
		$('#login').modal('show');
	});
	//获取所有人数
	/*var member =
		'{{each item as $value i}}' +
		'{{if $value.userRole==3||$value.userRole==1}}' +
		'<li class="teacher" data-uid="{{$value.userId}}" data-role="{{$value.userRole}}"><span>讲师 {{$value.userName}}</span></li>' +
		'{{/if}}' +
		'{{if $value.userRole==2}}' +
		'<li class="teacher" data-uid="{{$value.userId}}" data-role="{{$value.userRole}}"><span>助教 {{$value.userName}}</span></li>' +
		'{{/if}}' +
		'{{if $value.userRole==4}}' +
		'<li class="student" data-uid="{{$value.userId}}" data-role="{{$value.userRole}}"><span>{{$value.userName}}</span></li>' +
		'{{/if}}' +
		'{{/each}}';*/

	//在线成员和私聊信息模块切换
	$(".head-tab .tab a").off().on("click", function() {
		$(this).parent().addClass("active").siblings().removeClass("active");
		if($(this).attr("data-module") == "mess") {
			$(".mess").show().siblings().hide();
			$(".right-con #chat-list").scrollTop("9999999");
			$(".chat").css("display", "block");
		} else {
			$(".member").show().siblings().hide();
			$(".chat").css("display", "none");
			RequestService("/online/live/getLiveLessionOnLine", "get", {
				roomId: roomid
			}, function(data) {
				var m = JSON.parse(data.resultObject);
				var arr = [];
				var arr1 = [];
				var zhujiao = [];
				var ziji = [];
				$(".right-con .member").html(template.compile(member)({
					item: m.users
				}));
				$(".right-con .member li").each(function() {
					if($(this).attr("data-role") == 1 || $(this).attr("data-role") == 3) {
						arr1.unshift($(this));
					} else if($(this).attr("data-role") == 4) {
						if(username == $(this).find("span").text() && $(this).find("span").text() != "游客") {
							arr.unshift($(this));
						} else {
							arr.push($(this));
						}
					} else {
						zhujiao.unshift($(this));
					}
				});
				arr.unshift(zhujiao[0]);
				arr.unshift(arr1[0]);
				$(".right-con .member").html(arr);
			});
		}
	});
	//点击收缩右侧聊天区域
	$(".right .right-mulu").on("click", function() {
		if($(this).hasClass("act")) {
			$(this).removeClass("act");
			$(".right").animate({
				"right": "0px"
			});
			$(".livePlayer").animate({
				"paddingRight": "310px"
			});
			$(".liveMess").animate({
				"paddingRight": "310px"
			});
			$(".ms").animate({
				"paddingRight": "310px"
			});
			$(".kick").animate({
				"marginLeft": "-280px"
			});
			$(".dialog").animate({
				"marginLeft": "-335px"
			});
		} else {
			$(this).addClass("act");
			$(".ms").animate({
				"paddingRight": "00px"
			});
			$(".dialog").animate({
				"marginLeft": "-180px"
			});
			$(".right").animate({
				"right": "-310px"
			});
			$(".liveMess").animate({
				"paddingRight": "0px"
			});
			$(".livePlayer").animate({
				"paddingRight": "0"
			});
			$(".kick").animate({
				"marginLeft": "-125px"
			});
		}
	});
	var t = true;
	$(".right-list .flower").off().click(function() {
		RequestService("/online/user/isAlive", "GET", null, function(data) {
			if(!data.success) {
				alert("请登录");
				location.reload();
				return false;
			} else {
				RequestService("/online/live/updateFlowersNumber", "get", {
					courseId: courseId,
					from: username,
					roomId: roomid
				}, function(data) {
					if(data.resultObject.state) {
						$(".headMess .num").html(data.resultObject.num);
						$(".right-list .flower .send_wait p").html(data.resultObject.message);
						$(".right-list .flower .send_wait").hide();
					} else {
						$(".right-list .flower .send_wait p").html(data.resultObject.message);
						$(".right-list .flower .send_wait").show();
						if(t) {
							t = false;
							setTimeout(function() {
								$(".right-list .flower .send_wait").hide();
								t = true;
							}, 2000);
						}
						return t;
					}
				})
			}
		}, false);
	});


	//清空文件
	function clearFileInput(file) {
		var form = document.createElement('form');
		document.body.appendChild(form);
		//记住file在旧表单中的的位置
		var pos = file.nextSibling;
		form.appendChild(file);
		form.reset();
		pos.parentNode.insertBefore(file, pos);
		document.body.removeChild(form);
	};
	//只看老师回复
	$(".right-list .onlyteach").off().on("click", function() {
		var $this = $(this);
		RequestService("/online/user/isAlive", "GET", null, function(data) {
			if(!data.success) {
				return false;
			} else {
				if(!($this.hasClass("active"))) {
					$this.addClass("active");
					$('#chat-list li').each(function() {
						if($(this).attr("data-role") != "publisher" && $(this).attr("data-role") != "teacher") {
							$(this).css({
								"display": "none"
							});
						}
					});
				} else {
					$this.removeClass("active");
					$('#chat-list li').each(function() {
						$(this).css({
							"display": "block"
						});
					});
				};
				$(".right-con #chat-list").scrollTop("9999999");
			}
		});

	});
	var n = "";
	var s = "";

	function tips(chatX, chatY, msg) {
		$('#input-tips').find('p').text(msg);
		$('#input-tips').stop(1, 1).fadeIn(200).delay(1500).fadeOut(200);
	};
//	
//	//回车发送
//	$('#chat-content').unbind().bind('keypress', function(e) {
//		if(e.keyCode == 13) {
//			chatSend();
//			return false;
//		}
//	});
	$("#chat-content").bind("input", function() {
		var num = 140 - $("#chat-content").val().length;
		if($("#chat-content").val() == "") {
			$(".right-text .sub").attr("data-send", "all");
		}
		if(num >= 0) {
			$(".right-list span").html(num);
		} else {
			$(".right-list span").html(0);
		}
	});
	//登录
	var flag = false;

	function errorMessage(info) {
		flag = false;
		var errorReg = /[a-zA-z]+/g;
		if(errorReg.test(info)) {
			layer.alert("系统繁忙，请稍候再试!", {
				icon: 2
			});
			return flag = true;
		}
	}
	/*按回车键进行登录*/
	$(".cymylogin .cyinput2,.cymylogin .cyinput1").bind("keyup", function(evt) {
		if(evt.keyCode == "13") {
			$(".cymylogin .cymyloginbutton").trigger("click");
		}
	});
//	var cymyLogin = document.getElementsByClassName("cymlogin")[0];
	var cymyLogin = $(".cymlogin")[0];
	initLogin();
	/*按回车键进行登录*/
	$(".cymylogin .cyinput2,.cymylogin .cyinput1").bind("keyup", function(evt) {
		if(evt.keyCode == "13") {
			$(".cymylogin .cymyloginbutton").trigger("click");
		}
	});
	$(".form-login .cymyloginclose1").on("click", function() {
		$(".cymyloginclose1").css("display", "none");
		$(".cyinput1").css({
			"border": "1px solid #2cb82c"
		});
		$(".cyinput1").val("");
	})
	$(".form-login .cymyloginclose2").on("click", function() {
		$(".cymyloginclose2").css("display", "none");
		$(".cyinput2").css({
			"border": "1px solid #2cb82c"
		});
		$(".cyinput2").val("");
	})
	$(".cyinput1").on("input", function() {
		var val = $(this).val();
		if(val == "") {
			$(".cymyloginclose1").css("display", "none");
		} else {
			$(".cymyloginclose1").css("display", "block");
		}
		return false;
	});
	$(".cyinput2").on("input", function() {
		var logPass = $(this).val();
		if(logPass == "") {
			$(".cymyloginclose2").css("display", "none");
		} else {
			$(".cymyloginclose2").css("display", "block");
		}
		return false;
	});

	function initLogin() {
		//清空登录
//		var cymyLogin = document.getElementsByClassName("cymlogin")[0];
		var cymyLogin = $(".cymlogin")[0];
		$("#login").on('shown.bs.modal', function(e) {
			$(".cymylogin-bottom input").removeAttr("style");
			cymyLogin.style.display = "none";
		});
		RequestService("/online/user/isAlive", "GET", null, function(data) { ///online/user/isAlive
			if(data.success === true) {
				var path;
				localStorage.username = data.loginName;
				//头像预览
				if(data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
					path = data.resultObject.smallHeadPhoto;
				} else {
					path = bath + data.resultObject.smallHeadPhoto
				}
				$(".userPic").css({
					background: "url(" + path + ") no-repeat",
					backgroundSize: "100% 100%"
				});
				$('#login').css("display", "none");
				$(".loginGroup .logout").css("display", "none");
				$(".loginGroup .login").css("display", "block");
				$(".dropdown .name").text(data.resultObject.name).attr("title", data.resultObject.name);
			} else {
				$('#login').css("display", "none");
				$(".loginGroup .logout").css("display", "block");
				$(".loginGroup .login").css("display", "none");
			}
		});
		var isCliclLogin = false;

		$(".form-login .cyinput1").on("blur", function() {
//			var cymyLogin = document.getElementsByClassName("cymlogin")[0];
			var cymyLogin = $(".cymlogin")[0];
			var regPhone = /^1[3-5678]\d{9}$/;
//			var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
			if($(".form-login .cyinput1").val().trim().length === 0) {
				$(".cyinput1").css("border", "1px solid #ff4012");
				cymyLogin.innerText = "请输入手机号";
				cymyLogin.style.top = "154px";
				cymyLogin.style.display = "block";
			} else if(!(regPhone.test($(".form-login .cyinput1").val().trim()))) {
/*			} else if($(".form-login .cyinput1").val().trim().indexOf("@") == "-1" && !(regPhone.test($(".form-login .cyinput1").val().trim()))) {
*/				$(".cyinput1").css("border", "1px solid #ff4012");
				cymyLogin.innerText = "手机号格式不正确!";
				cymyLogin.style.top = "154px";
				cymyLogin.style.display = "block";
			}/* else if($(".form-login .cyinput1").val().trim().indexOf("@") != "-1" && !(regEmail.test($(".form-login .cyinput1").val().trim()))) {
				$(".cyinput1").css("border", "1px solid #ff4012");
				cymyLogin.innerText = "邮箱格式不正确!";
				cymyLogin.style.top = "154px";
				cymyLogin.style.display = "block";
			}*/ else {
				$(".cyinput1").css("border", "");
			}
		});
		$(".form-login .cyinput1").on("focus", function() {
			if(cymyLogin.innerText == "请输入手机号" || cymyLogin.innerText == "手机号格式不正确!" || cymyLogin.innerText == "邮箱格式不正确!" || cymyLogin.innerText == "用户名或密码不正确!") {
				cymyLogin.style.display = "none";
			}
			$(".cyinput1").css("border", "1px solid #2cb82c");
		});
		$(".form-login .cyinput2").focus(function() {
			if(cymyLogin.innerText == "请输入6-18位密码") {
				cymyLogin.style.display = "none";
			}
			$(".cyinput2").css("border", "1px solid #2cb82c");
		});
		$(".form-login .cyinput2").blur(function() {
//			var cymyLogin = document.getElementsByClassName("cymlogin")[0];
			var cymyLogin = $(".cymlogin")[0];
			var cyinput2Length = $(".form-login .cyinput2").val().trim().length;
			if(cyinput2Length == 0) {
				$(".cyinput2").css("border", "1px solid #ff4012");
				$(".cymlogin").css("top", "221px");
				cymyLogin.innerText = "请输入6-18位密码";
				cymyLogin.style.display = "block";
			} else if(cyinput2Length < 6 && cyinput2Length > 18) {
				$(".cyinput2").css("border", "1px solid #ff4012");
				$(".cymlogin").css("top", "221px");
				cymyLogin.innerText = "请输入6-18位密码";
				cymyLogin.style.display = "block";
			} else {
				$(".cyinput2").css("border", "")
			}
		});
		$(".form-login .cymyloginbutton").click(function(evt) { //登录验证
			var regPhone = /^1[3-5678]\d{9}$/;
			var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
			var passwordReg = /^(?!\s+)[\w\W]{6,18}$/; //密码格式验证
			$(".cyinput1").css("border", "");
			$(".cyinput2").css("border", "");
			var data = {
				username: $(".form-login .cyinput1").val().trim(),
				password: $(".form-login .cyinput2").val()
			};
			if(data.username.length === 0) {
				$(".cyinput1").css("border", "1px solid #ff4012");
				cymyLogin.innerText = "请输入手机号";
				cymyLogin.style.top = "154px";
				cymyLogin.style.display = "block";
				return;
			} else if(!(regPhone.test(data.username))) {
				$(".cyinput1").css("border", "1px solid #ff4012");
				cymyLogin.innerText = "手机号格式不正确!";
				cymyLogin.style.top = "154px";
				cymyLogin.style.display = "block";
				return;
			}/* else if(data.username.indexOf("@") != "-1" && !(regEmail.test(data.username))) {
				$(".cyinput1").css("border", "1px solid #ff4012");
				cymyLogin.innerText = "邮箱格式不正确!";
				cymyLogin.style.top = "154px";
				cymyLogin.style.display = "block";
				return;
			} */else if(data.password.length === 0) {
				$(".cyinput2").css("border", "1px solid #ff4012").val("");
				$(".cymlogin").css("top", "221px");
				cymyLogin.innerText = "请输入6-18位密码";
				cymyLogin.style.display = "block";
				return;
			}
			isCliclLogin = true;
			login(data);
		});

		function login(data, autoLogin) {
			RequestService("/online/user/login", "POST", data, function(result) { //登录/index.html   /online/user/login
				if(result.success === true || result.success == undefined) {
					window.localStorage.userName = data.username;
					window.location.reload();
				} else { //登录错误提示
					$(".loginGroup .logout").css("display", "block");
					errorMessage(result.errorMessage);
					if(!flag) {
						if(result.errorMessage == "用户名密码错误！") {
							cymyLogin.innerText = "用户名或密码不正确!";
							$(".cymlogin").css("top", " 154px");
							cymyLogin.style.display = "block";
						} else {
							cymyLogin.innerText = result.errorMessage;
							$(".cymlogin").css("top", " 154px");
							cymyLogin.style.display = "block";
						}
					}

				}
			});
		}
		$(".dropdown-menu li a").click(function(evt) {
			$(".top-item").removeClass("selected");
			var btn = $(evt.target);
			var id = "personcenter";
			var personcenterSt = window.localStorage.personcenter;
			window.localStorage.personcenter = $(evt.target).attr("data-id");
			if(window.location.pathname == "/web/html/personcenter.html") {
				if($(this).attr("data")) {
					RequestService("/online/user/logout", "GET", {}, function(data) {
						location.href = "/index.html";
						$(".loginGroup .logout").css("display", "block");
						$(".loginGroup .login").css("display", "none");
					});
				} else {
					$(".left-nav ." + window.localStorage.personcenter).click();
				}
			} else {
				if($(this).attr("data")) {
					RequestService("/online/user/logout", "GET", {}, function(data) {
						location.href = "/index.html";
						$(".loginGroup .logout").css("display", "block");
						$(".loginGroup .login").css("display", "none");
					});
				} else {
					var pathS = window.location.href;
					location.href = "/web/html/personcenter.html";
					RequestService("/online/user/isAlive", "GET", null, function(data) { ///online/user/isAlive
						if(data.success) {
							if(data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
								path = data.resultObject.smallHeadPhoto;
							} else {
								path = bath + data.resultObject.smallHeadPhoto
							}
							//头像预览
							$(".userPic").css({
								background: "url(" + path + ") no-repeat",
								backgroundSize: "100% 100%"
							});
							$('#login').modal('hide');
							$("html").css({
								"overflow-x": "hidden",
								"overflow-y": "auto"
							});
							$(".loginGroup .logout").hide();
							$(".loginGroup .login").show();
							$(".dropdown .name").text(data.resultObject.name).attr("title", data.resultObject.name);
							localStorage.username = data.resultObject.loginName;
							localStorage.userid = data.resultObject.id;
							if($(btn.parent().hasClass('selected'))) {

							} else {
								hideHtml();
							}
						} else {
							var pathS = window.location.href;
							location.href = "/index.html";
							localStorage.username = null;
							localStorage.password = null;
							$(".login").css("display", "none");
							$(".logout").css("display", "block");
							//                          }
						}
					});
				}
			}

		});
	}
})

function refreshBalance(){
	//获取个人熊猫币余额
	RequestService("/userCoin/balance", "GET", {
	}, function(data) {
		var balance = data.resultObject;
		$(".balanceTotal").html(balance);
		//熊猫币余额
		$("#xmb_ye").html("熊猫币"+balance);
		$("#account").html(balance);
	});
}
refreshBalance();
//返回按钮
$("#return").click(function() {
    location.href = "/course/courses/" + course_id;
});

