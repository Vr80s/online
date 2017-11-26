var wait = 90;

function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        //o.setAttribute("style", "background: #00D580;")
        $(o).html("获取验证码");

        wait = 90;
    } else {
        o.setAttribute("disabled", true);
        //o.setAttribute("style", "background: #cbcbcb;")
        //o.value = "" + wait + "S";
        $(o).html("" + wait + "S");
        wait--;
        setTimeout(function() {
            time(o)
        }, 1000)
    }
}
/**
 * 错误提示id
 */
var reminderror = document.getElementById("reminderror");
/**
 * input获取焦点隐藏错误提示
 */
$("input").focus(function(){
	reminderror.innerHTML = "";
});
//返回登录页

document.getElementById("return").addEventListener("tap", function() {
	location.href = "/bxg/page/login/1";
})
var openid = localStorage.openid;

document.getElementById("btn").addEventListener("tap", function() {
	var _this = this;
	var number = document.getElementById("number").value; // 手机号
	var urlparm = {
		username : number,
		vtype:2   	//类型，1注册，2重置密码
	};
	if (!stringnull(number)) {
		reminderror.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>手机号不能为空</div></div></div>";
		setTimeout(function(){$(".vanish").hide();},1500);
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(number))) {
		reminderror.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>手机号格式不正确</div></div></div>";
		setTimeout(function(){$(".vanish").hide();},1500);
		return false;
	}
	requestService("/bxg/user/sendCode", urlparm, function(data) {
		if (data.success) {
			$('#yanzhengma').focus();
			time(_this);
			var configresult = data.resultObject;
			var reg = document.getElementById("reg");
			reminderror.innerHTML = "";
		} else {
			reminderror.innerHTML = "<div class='vanish2'><div class='vanish2_bg'></div><div class='vanish2_cen'><div class='vanish2_size'>同一手机号两次发送间隔至少90秒！</div></div></div>";
		setTimeout(function(){$(".vanish2").hide();},1500);
//			reminderror.innerHTML = data.errorMessage;  同一手机号两次发送间隔至少90秒！  用户不存在用的同一个
		}
	});
})
reg.addEventListener("tap", function() {
	var number = document.getElementById("number").value; // 手机号
	var yanzhengma = document.getElementById("yanzhengma").value;
	var userpassword = document.getElementById("userpassword").value; // 密码
	if (!stringnull(number)) {
		reminderror.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>手机号不能为空</div></div></div>";
		setTimeout(function(){$(".vanish").hide();},1500);
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(number))) {
		reminderror.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>手机号格式不正确</div></div></div>";
		setTimeout(function(){$(".vanish").hide();},1500);
		return false;
	}
	if (!stringnull(yanzhengma)) {
		reminderror.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>验证码不能为空</div></div></div>";
		setTimeout(function(){$(".vanish").hide();},1500);
		return false;
	}
	if (!stringnull(userpassword)) {
		reminderror.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>密码不能为空</div></div></div>";
		setTimeout(function(){$(".vanish").hide();},1500);
		return false;
	}
	var pwdLength = userpassword.trim().length;
    if(pwdLength < 6 || pwdLength > 18) {
          reminderror.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>请输入6-18位密码!</div></div></div>";
          setTimeout(function(){$(".vanish").hide();},1500);
          return false;
    }
	checkCode(number, yanzhengma, userpassword);

})

function checkCode(username, code, userpassword) {
	// 注册的时候需要判断下，是微信注册还是浏览器注册呢
	var urlparm = {
		username : username,
		code : code,
		password : userpassword,
		vtype : "2"
	};
	var access_url = "/bxg/bs/forgotPassword";
	var access = localStorage.access;
//	if (access == "wx") {
//		urlparm.openId = openid;
//		access_url = "/bxg/user/forgotPassword";
//	}
	// console.log(mobile+"--------"+username+"------"+password);
	requestService(access_url, urlparm, function(data) {
		if (data.success) {
			var configresult = data.resultObject;
		   /*mui.toast(configresult);*/
			// 返回的信息 下一个页面
			localStorage.name = configresult.loginName;
			localStorage.useid = configresult.id;
			localStorage.username = configresult.name;
			localStorage.sharecode = localStorage.shareCode;
			localStorage.smallHeadPhoto = localStorage.smallHeadPhoto;
			// localStorage.yanzhengma = configresult.yanzhengma;
			//登录之后到主页面呢
			location.href = "/bxg/page/login/1";
		} else {
			reminderror.innerHTML = "<div class='vanish'><div class='vanish_bg'></div><div class='vanish_cen'><div class='vanish_size'>动态码不正确</div></div></div>";
							setTimeout(function(){$(".vanish").hide();},1500);
		}
	});
}
mui.init({
	swipeBack : false
});
