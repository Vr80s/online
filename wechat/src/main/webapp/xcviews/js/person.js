

var wait = 90;
function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        //o.setAttribute("style", "background: #00D580;")
        $(o).html("获取验证码");

        wait = 90;
        $("#call_code_current").css("background","#00bc12");
    } else {
        o.setAttribute("disabled", true);
        //o.setAttribute("style", "background: #cbcbcb;")
        //o.value = "" + wait + "S";
        $(o).html("" + wait + "S");
        wait--;
        $("#call_code_current").css("background","#ccc");
        setTimeout(function() {
            time(o)
        }, 1000)
    }
}

/**
 * 	更换手机号开始
 */
var currentName = localStorage.name;

/*
 * 发送短信验证码
 */
function  sendCode(obj){
	var vtype = "";
	var number= "";
	if(obj.id == "call_code_current"){
		vtype = 3;
		number = currentName; // 手机号
	}else if(obj.id == "call_code_update"){
		vtype = 4;
		number = $("#new_mobile").val();
	}
	if (!stringnull(number)) {
		$("#errorMsg").text("手机号不能为空");
		$("#errorMsg").show();
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(number))) {
		$("#errorMsg").text("手机号格式不正确");
		$("#errorMsg").show();
		return false;
	}
	//
	if(vtype == 4 && currentName.trim() == number.trim()){
		$("#errorMsg").text("当前绑定的手机号和原来的一样,换个吧");
		$("#errorMsg").show();
		return false;
	}
	var urlparm = {
		username : number,
		vtype:vtype   	//类型，3注册，4重置密码
	};
	requestService("/bxg/bs/phoneCheck", 
			urlparm, function(data) {
		if (data.success) {
			time(obj);
			/*reminderror.innerHTML = "";*/
		} else {
			$("#errorMsg").html(data.errorMessage);
			$("#errorMsg").show();
		}
	});
}
/**
 * 下一步
 */
$("#update_mobile_next").click(function(){
	
	if (!stringnull(currentName) || !(/^1[34578]\d{9}$/.test(currentName))) {
		$("#errorMsg").html("获取用户手机号有误");
		$("#errorMsg").show();
		return false;
	}
	var code= $("#current_mobile").val();
	if(!stringnull(code)){
		$("#errorMsg").html("验证码不能为空");
		$("#errorMsg").show();
		return false;
	}
	var urlparm = {
		username : currentName,
		code:code,
		vtype:3   	//类型，3注册，2重置密码
	};
	requestService("/bxg/bs/phoneCheckAndCode", 
			urlparm, function(data) {
		if (data.success) {
			//time(obj);
			$(".call01").show();
			$(".call").hide();
    		$(".attention").hide(); 
		} else {
			$("#errorMsg").html(data.errorMessage);
			$("#errorMsg").show();
		}
	});
})

/*
 * 绑定现有的手机号，也就是更换原来的手机号
 */
function updateMobile(){
	
	if (!stringnull(currentName) || !(/^1[34578]\d{9}$/.test(currentName))) {
		$("#errorMsg").html("获取用户手机号有误");
		$("#errorMsg").show();
		return false;
	}
	
	var number = $("#new_mobile").val();
	if (!(/^1[34578]\d{9}$/.test(number))) {
		$("#errorMsg").text("手机号格式不正确");
		$("#errorMsg").show();
		return false;
	}
	
	var code= $("#new_code").val();
	if(!stringnull(code)){
		$("#errorMsg").html("验证码不能为空");
		$("#errorMsg").show();
		return false;
	}
	
	var urlparm = {
		oldUsername:currentName,
		newUsername:number,
		code:code,
		vtype:4   	//类型，3注册，2重置密码
    };
	
	requestService("/bxg/bs/updatePhone", 
			urlparm, function(data) {
		if (data.success) {
			//更改完手机号后，需要把session中的这个东西换下呢？
			localStorage.setItem("name",number);
			$(".call_popup_size2").text(number);
			$(".call_popup").show();
		} else {
			$("#errorMsg").html(data.errorMessage);
			$("#errorMsg").show();
			return false;
		}
	});
	
}

$(".call_popup_btn").click(function(){
	$(".call_popup").hide(); //去设置页面
	$(".return").click();
})



function checkCode(username, code, userpassword) {
	// 注册的时候需要判断下，是微信注册还是浏览器注册呢
	var urlparm = {
		username : username,
		code : code,
		password : userpassword,
		vtype : "2"
	};
	var access_url = "/bxg/bs/editPassword";
	var access = localStorage.access;
	if (access == "wx") {
		urlparm.openId = openid;
		access_url = "/bxg/bs/editPassword";
	}
	// console.log(mobile+"--------"+username+"------"+password);
	requestService(access_url, urlparm, function(data) {
		if (data.success) {
			var configresult = data.resultObject;
			mui.toast(configresult);
			alert("修改密码成功！");
			// 返回的信息 下一个页面
//			localStorage.name = configresult.loginName;
//			localStorage.useid = configresult.id;
//			localStorage.username = configresult.name;
//			localStorage.sharecode = localStorage.shareCode;
//			localStorage.smallHeadPhoto = localStorage.smallHeadPhoto;
			// localStorage.yanzhengma = configresult.yanzhengma;
			//登录之后到主页面呢
			//location.href = "/bxg/page/login/1";
		} else {
			//tishi.innerHTML = data.errorMessage;
			alert("修改密码失败！");
		}
	});
}


//这个有问题啊


function ajaxFileUpload(fileId,url,data,callback){
	$.ajaxFileUpload({
		type:"post",
        url:url, // 需要链接到服务器地址
        secureuri: false,
        data:data,
        fileElementId:fileId, // 文件选择框的id属性
        dataType: 'json', // 服务器返回的格式
        success: function(data,status){
        	//data=strToJson(data);
        	if(data.success==true){
        		if(null!=callback){
        			callback(data);
        		}
        	}else{
        		alertInfo(data.message);
        	}
        }
	});
}
/*
 * 用户修昵称、邮件、地址信息
 */
function checkUser1(saveFalg){

	  var nickname = "";   //昵称
	  var sex  = "";       //性别
	  var email = "";      // 
	  var provinceCityName = "";
	  var info = "";
	  var occupation = "";
	  var  occupationOther = "";
	  if(saveFalg == "nickname"){
		  nickname =  $("#form input[name='nickname']").val();
	  }else if(saveFalg == "sex"){
		  sex =  $("#form input[name='sex']:checked").val();
	  }else  if(saveFalg == "email"){
		  email = $("#form input[name='email']").val();
	  }else  if(saveFalg == "provinceCityName"){
		  provinceCityName  =  $("#szd_cityP").html();
	  }else  if(saveFalg == "info"){
		  info = $("#info").val().trim();
	  }else if(saveFalg == "occupation"){
		  occupation = $("#sfxx").attr("class");
	  }else if(saveFalg == "occupationOther"){
		  occupationOther =  $("#form input[name='occupationOther']").val();
	  }
//	  map.put("occupation", occupation);
//      map.put("occupationOther", occupationOther);
	  
	requestService("/bxg/user/userCenterFormSub1", {
		  nickname: stringnull(nickname) ? nickname : "",
		  sex:stringnull(sex) ? sex : "",
		  email:stringnull(email) ? email : "",
		  provinceCityName:stringnull(provinceCityName) ? provinceCityName : "",
		  info:stringnull(info) ? info : "",
	      occupation:stringnull(occupation) ? occupation : "",
	      occupationOther:stringnull(occupationOther) ? occupationOther : ""
	}, function(data) {
		if (data.success) {
			
			var result = data.resultObject;
			if(stringnull(result.nickname)){
				$("#person_one").html(result.nickname);
				localStorage.setItem("username",result.nickname);
			}
			if(stringnull(result.sex)){
				if(result.sex==1){
					$("#person_two").html("男");
				}else if(result.sex==0){
					$("#person_two").html("女");
				}
				localStorage.setItem("sex",result.sex);
			}
			if(stringnull(result.email)){
				$("#person_five").html(result.email);
				localStorage.setItem("email",result.email);
			}
			if(stringnull(result.provinceName)){
				localStorage.setItem("provinceName",result.provinceName);
				localStorage.setItem("provinceId",result.provinceId);
			}
			if(stringnull(result.cityName)){
				localStorage.setItem("cityName",result.cityName);
				localStorage.setItem("cityId",result.cityId);
			}
			if(stringnull(result.info)){
				localStorage.setItem("info",result.info);
				$("#person_sign").html(result.info);
			}
			if(stringnull(result.occupation)){
				localStorage.setItem("occupation",result.occupation);
			}
			if(stringnull(result.occupationOther)){
				localStorage.setItem("occupationOther",result.occupationOther);
			}
		} else {
			alert("密码错误！");
		}
	});
}

$("input").focus(function(){
	$("#errorMsg").hide();
});



/*
 * 用户修改头像信息
 */
function checkUser() {

	
	var v = $("#file").val().split(".")[$("#file").val().split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		//layer.alert("图片格式错误,请重新选择.");
		$("#errorMsg").show();
		$("#errorMsg").text("图片格式错误,请重新选择.");
		return;
	}	
	
	
   ajaxFileUpload("file","/bxg/user/userCenterFormSub2",null,function(data){
	  /*  alert("中国你好啊");*/
		if (data.success == true) {
			if(data.resultObject!=null){
				var result = data.resultObject;
				if(stringnull(result.smallHeadPhoto)){
					/*alert("result.smallHeadPhoto"+result.smallHeadPhoto);*/
					$("#headImg").attr("src",result.smallHeadPhoto);
					//同时我们也要变化token中的头像值。
					localStorage.setItem("smallHeadPhoto",result.smallHeadPhoto);
				}
			}
		}else {
			alert("请求有误");
		}
	});
}

// 返回到直播列表中
document.getElementById("grabble_img").addEventListener("tap", function() {
	location.href = "/xcviews/html/my.html";
})