

var wait = 90;
function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        $(o).html("获取验证码");

        wait = 90;
        $(".call_code").css("background","#00bc12");
    } else {
        o.setAttribute("disabled", true);
        $(o).html("" + wait + "S");
        wait--;
        $(".call_code").css("background","#ccc");
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
var currentName = localStorage.username;

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
		return false;
	}
	if (!(/^1[34578]\d{9}$/.test(number))) {
		webToast("手机号格式不正确","middle",3000);
		return false;
	}
	//
	if(vtype == 4 && currentName.trim() == number.trim()){
		webToast("当前绑定的手机号和原来的一样,换个吧","middle",3000);
		return false;
	}
	var urlparm = {
		username : number,
		vtype:vtype   	//类型，3注册，4重置密码
	};
	requestService("/xczh/set/phoneCheck",
			urlparm, function(data) {
		if (data.success) {
			time(obj);
		} else {
			webToast(data.errorMessage,"middle",3000);				
		}
	});
}



/*
 * 用户修昵称、邮件、地址信息
 */
function checkUser1(saveFalg){

	  var falg = true;
	
	  var nickname = "";  var sex  = ""; var email = "";  var provinceCityName = "";
	  var info = "";var occupation = ""; var  occupationOther = "";
	  if(saveFalg == "nickname"){
		  nickname =  $("#form input[name='nickname']").val();
	  }else if(saveFalg == "sex"){
		  sex =  $("#form input[name='sex']:checked").val();
	  }else  if(saveFalg == "email"){
		  email = $("#form input[name='email']").val();
	  }else  if(saveFalg == "provinceCityName"){
		  provinceCityName  =  $("#xzdz").html();
	  }else  if(saveFalg == "info"){
		  info = $("#info").val().trim();
	  }else if(saveFalg == "occupation"){
		  occupation = $("#sfxx").attr("class");
	  }else if(saveFalg == "occupationOther"){
		  occupationOther =  $("#form input[name='occupationOther']").val();
	  }
	  var user_id = localStorage.getItem("userId");
	requestService("/xczh/set/userInfoWechat", {
          id: stringnull(user_id) ? user_id : "",
		  name: stringnull(nickname) ? nickname : "",
		  sex:stringnull(sex) ? sex : "",
		  email:stringnull(email) ? email : "",
          provinceName:stringnull(provinceCityName) ? provinceCityName : "",
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
				localStorage.setItem("province",result.province);
			}
			if(stringnull(result.cityName)){
				localStorage.setItem("cityName",result.cityName);
				localStorage.setItem("city",result.city);
			}
			if(stringnull(result.countyName)){
				localStorage.setItem("countyName",result.countyName);
				localStorage.setItem("district",result.district);
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
			falg = false;
			webToast(data.errorMessage,"middle",3000);		
		}
	},false);
	
	return falg;
}


/*
 * 绑定现有的手机号，也就是更换原来的手机号
 */
function updateMobile(){

    var newMobile= $("#new_mobile").val();
    if(!stringnull(newMobile)){
        return false;
    }
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
       /* $("#errorMsg").html("验证码不能为空");
        $("#errorMsg").show();*/
        return false;
    }

    var urlparm = {
        oldUsername:currentName,
        newUsername:number,
        code:code,
        vtype:4   	//类型，3注册，2重置密码
    };

    requestService("/xczh/set/updatePhone",
        urlparm, function(data) {
            if (data.success) {
                //更改完手机号后，需要把session中的这个东西换下呢？
                localStorage.setItem("name",number);
                $(".call_popup_size2").text(number);
                $(".call_popup").show();
            } else {
            	webToast(data.errorMessage,"middle",3000);
                return false;
            }
        });

}
$(".call_popup_btn").click(function(){
    $(".call_popup").hide(); //去设置页面
    $(".return").click();

    requestService("/set/user/logout",{
		}, function(data) {
            if (data.success) {
                window.location.href="enter.html";
            }
        });


})

