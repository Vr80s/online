

var wait = 90;
function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        //o.setAttribute("style", "background: #00D580;")
        $(o).html("获取验证码");

        wait = 90;
        $(".call_code").css("background","#00bc12");
        $("#pet_name_right_bind").css("background","#ccc");
        $("#update_mobile_next").css("background","#ccc");
    } else {
        o.setAttribute("disabled", true);
        //o.setAttribute("style", "background: #cbcbcb;")
        //o.value = "" + wait + "S";
        $(o).html("" + wait + "S");
        wait--;
        $(".call_code").css("background","#ccc");
        $("#update_mobile_next").css("background","#00bc12");
        $("#pet_name_right_bind").css("background","#00bc12");
        setTimeout(function() {
            time(o)
        }, 1000)
    }
}

$("input").focus(function(){
	$("#errorMsg").hide();
});


/*点击返回按钮*/
$(".return").click(function(){
    
    location.href ='persons.html';
});


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



/*
 * 用户修昵称、邮件、地址信息
 */
function checkUser1(saveFalg){

	  var nickname = "";
	  var sex  = "";
	  var email = "";
	  var provinceCityName = "";
	  var info = "";
	  if(saveFalg == "nickname"){
		  nickname =  $("input[name='nickname']").val();
	  }else if(saveFalg == "sex"){
		  sex =  $("input[name='sex']:checked").val();
	  }else  if(saveFalg == "email"){
		  email = $("input[name='email']").val();
	  }else  if(saveFalg == "provinceCityName"){
		  provinceCityName  =  $("#szd_cityP").html();
	  }else  if(saveFalg == "info"){
		  info = $("#info").val().trim();
	  }
	  
	requestService("/bxg/user/userCenterFormSub1", {
		  nickname: stringnull(nickname) ? nickname : "",
		  sex:stringnull(sex) ? sex : "",
		  email:stringnull(email) ? email : "",
		  provinceCityName:stringnull(provinceCityName) ? provinceCityName : "",
		  info:stringnull(info) ? info : ""
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
		} else {
			alert("密码错误！");
		}
	},false);
}