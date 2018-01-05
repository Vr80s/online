
/**
 * 因为拦截器没有对这个页面进行拦截，所以请求下，重新获取session
 */
function getUserInfo(){
	var userId = localStorage.setItem("userId",configresult.id)
	if(stringnull(userId)){
		/**
		 * 请求下，获取用户信息
		 */
		var ccontrollerAddress = "/bxg/user/isLogined";
		requestService(ccontrollerAddress, null, function(data) {
			if (data.success) {
				
				commonLocalStorageSetItem(data);
				
			}else{
				alert("获取用户信息有误");
			}
		},false)	
	}
}


function init(){
    //未支付
    requestService("/bxg/focus/myHome",  {userId : localStorage.userId}, function(data) {
        var result = data.resultObject;
        $("#countFans").html(result.countFans);
        $("#countFocus").html(result.countFocus);
        $("#xmbCount").html(result.xmbCount);
        //这一期暂时不显示房间号
        $("#name").html(result.user.name);
        $("#smallHeadPhoto").attr("src",result.user.smallHeadPhoto);
    },false);
}


$(".my_bg_color").hide();
$(".my_bg_cen").hide();
var openid = getQueryString("openId");
var center = getQueryString("center");

if(openid == null){          //并非绑定手机号
	$(".my_bg_color").hide();
	$(".my_bg_cen").hide();
	
	//getUserInfo()
	init();
}else{
	/**
	 * 如果存在openid。说明来自微信端的请求，需要让他绑定手机号
	 */
	localStorage.setItem("openid",openid);
	$(".my_bg_color").show();
	$(".my_bg_cen").show();
}

var wxOrbrower
if (isWeiXin()) {
	console.log(" 是来自微信内置浏览器")
	wxOrbrower = "wx";
} else {
	wxOrbrower = "brower";
}
localStorage.setItem("access", wxOrbrower);

/**
 * 如果是微信公众号进入页面时，没有给他返回token。所以这里他在请求下呢 
 */
if(wxOrbrower == "wx" && stringnull(center)){
	
	var ccontrollerAddress = "/bxg/user/isLogined";
	requestService(ccontrollerAddress, null, function(data) {
		if (data.success) {
			
			commonLocalStorageSetItem(data);
		}else{
			 location.href = "/bxg/page/login/1";
		}
	})	
}



document.getElementById("my_set_p").addEventListener("tap", function() {
	location.href = "/xcviews/html/person.html";
})


/**
 * 下面这一块来自绑定手机号的事件
 */

var falg = true;
var isSend = false;
var wait = 90;

var reminderror = document.getElementById("reminderror"); // 错误提示

$("#yanzhengma").focus(function(){
	$("#reminderror").hide();
});
$("#userpassword").focus(function(){
	$("#reminderror").hide();
});


$("#number").focus(function(){
	$("#reminderror").hide();
});

function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        $(o).html("获取验证码");
        $(".my_bg_tel2_a").css("background-color","#16a951");
        $(".my_bg_tel2_a").css("color","#fff");
        wait = 90;
    } else {
        o.setAttribute("disabled", true);
        
        $(o).html("" + wait + "S");
        wait--;
        setTimeout(function() {
            time(o)
        }, 1000)
    }
}
document.getElementById("btn").addEventListener("tap", function() {
	
	var tel2_a = $(".my_bg_tel2_a").html();
	if(tel2_a.indexOf("获取验证码")==-1){
		return;
	}
	var _this = this;
	var number = document.getElementById("number").value; // 手机号
	if (!stringnull(number)) {
		$("#reminderror").show();
		reminderror.innerHTML = "手机号不能为空";
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(number))) {
		$("#reminderror").show();
		reminderror.innerHTML = "手机号格式不正确";
		return false;
	}
	
	if(isSend){
		return;
	}
	isSend = true;
	var urlparm = {
			username : number,
	};	
	/**
	 * 判断这个手机号有没有注册过。
	 */
	requestService("/bxg/bs/isReg", urlparm, function(data) {
		//alert("/bxg/bs/isReg:"+data.success);
		if(data.success){ //用户已注册   //类型，1注册，2重置密码   这个不行啊
			urlparm.vtype=2;
			falg = true;
		}else{
			urlparm.vtype=1;
			falg = false;
		}
		isSend = false;
		requestService("/bxg/user/sendCode", urlparm, function(data) {
			isSend = false;
			if (data.success) {
				time(_this);
				var configresult = data.resultObject;
				var reg = document.getElementById("reg");
				reminderror.innerHTML = "";
				$("#btn").css("background-color","#cfcfcf");
				$(".my_bg_btn").css("background-color","#16a951");
				$(".my_bg_btn").addClass("yxbtn");
				
				if(urlparm.vtype == 1){
					$("#hide_pws").show();
				}else{
					$("#hide_pws").hide();
				}
			} else {
				$("#reminderror").show();
				reminderror.innerHTML = data.errorMessage;
			}
		});
	})
})

var reg = document.getElementById("reg");
reg.addEventListener("tap", function() {
	if($(".my_bg_btn").attr("class").indexOf("yxbtn")==-1){
		return;
	}
	var number = document.getElementById("number").value; // 手机号
	var yanzhengma = document.getElementById("yanzhengma").value;
	if (!stringnull(number)) {
		$("#reminderror").show();
		reminderror.innerHTML = "手机号不能为空";
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(number))) {
		$("#reminderror").show();
		reminderror.innerHTML = "手机号格式不正确";
		return false;
	}
	if (!stringnull(yanzhengma)) {
		$("#reminderror").show();
		reminderror.innerHTML = "验证码不能为空";
		return false;
	}
	var userpassword = document.getElementById("userpassword").value; // 密码
	if(!falg){
		if (!stringnull(userpassword)) {
			$("#reminderror").show();
			reminderror.innerHTML = "请输入6-18位密码!";
			return false;
		}
		var pwdLength = userpassword.trim().length;
	    if(pwdLength < 6 || pwdLength > 18) {
	    	 $("#reminderror").show();
	         reminderror.innerHTML = "请输入6-18位密码!";
	         return false;
	    }
	}
	checkCode(number, yanzhengma,userpassword,falg);
})

var openid = localStorage.openid;
function checkCode(username, code,userpassword,falg) {
	// 注册的时候需要判断下，是微信注册还是浏览器注册呢
	//获取openid
	var urlparm = {
		username : username,
		code : code,
		openId : openid
	};
	var access_url ="";
	if(falg){
		access_url = "/bxg/user/wxBindAndPhoneNumberIsReg";
	}else{
		urlparm.password = userpassword;
		access_url = "/bxg/user/wxBindPhoneNumber";
	}
	//console.log(mobile+"--------"+username+"------"+password);
	requestService(access_url, urlparm, function(data) {
		if (data.success) {
			//存储session，跳转首页
			var configresult = data.resultObject;
			localStorage.setItem("token",JSON.stringify(configresult));
            localStorage.setItem("userId",configresult.id)
			localStorage.setItem("name",configresult.loginName);
            localStorage.setItem("smallHeadPhoto",configresult.smallHeadPhoto);
            localStorage.setItem("sex",configresult.sex);
            localStorage.setItem("provinceName",configresult.provinceName);
            localStorage.setItem("cityName",configresult.cityName);
            localStorage.setItem("province",configresult.province);
            localStorage.setItem("city",configresult.city);
            localStorage.setItem("email",configresult.email);
            localStorage.setItem("username",configresult.name);
            //如果是个人中心话，跳转页面了
            if(!stringnull(center)){
            	location.href = "/bxg/page/index/" + openid + "/null";
            }
		} else {
			//tishi.innerHTML = data.errorMessage;
			alert(data.errorMessage);
		}
	});
}







