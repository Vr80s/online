/**
 * Created by admin on 2016/11/25.
 */
$(function () {
    //解析url地址
    var ourl = document.location.search;
    var apams = ourl.substring(1).split("&");
    var arr = [];
    for (i = 0; i < apams.length; i++) {
        var apam = apams[i].split("=");
        arr[i] = apam[1];
        var ways = arr[0];
    }
    ;
    //错误提示信息
    var flag = false;

    function errorMessage(info) {
        flag = false;
        var errorReg = /[a-zA-z]+/g;
        if (errorReg.test(info)) {
            rTips("系统繁忙，请稍候再试!");
            return flag = true;
        }
    };

    function rTips(errorMessage) {
        $(".rTips").text(errorMessage);
        $(".rTips").css("display", "block");
        setTimeout(function () {
            $(".rTips").css("display", "none");
        }, 2000)
    };
	//点击切换图形验证码
	$(".imgVertifyCode img").on("click",function(){
		var a=Math.random();
        $(this).attr("src","/online/verificationCode/vcode?t="+a+"");
	});
	//-------------
    $(".boxueguUserPro").click(function () {
        $(".protocol-shadow").css("display", "block");
       /* $("html").css("overflow", "hidden");*/
    });
    $(".pay-protocol-close").click(function () {
        $(".protocol-shadow").css("display", "none");
       /* $("html").css("overflow", "auto");*/
    });
    /*登录*/
    initLogin();
    function initLogin() {
        /*登录注册切换*/
        $(".login-register div").click(function () {
            $(this).addClass("select").siblings().removeClass("select");
        });
        $(".login-button").click(function () {
            $(".login-content").css("display", "block");
            $(".register-content").css("display", "none");
        });
        $(".register-button").click(function () {
            $(".login-content").css("display", "none");
            $(".register-content").css("display", "block");
        });
        $(".autoLoginSelect,.allowProtocal").click(function () {
            $(this).toggleClass("select").siblings().removeClass("select");
        });
        if (ways == "register") {
            $(".register-button").trigger("click");
        }
        /*登陆右边的叉*/
        var logUserInput = $(".login-content .userName"),
            logPassInput = $(".login-content .password");
        logUserInput.on("input", function () {
            var val = $(this).val();
            if (val == "") {
                $(".userNameClose").css("display", "none");
            } else {
                $(".userNameClose").css("display", "block");
            }
            return false;
        });
        logPassInput.on("input", function () {
            var logPass = $(this).val();
            if (logPass == "") {
                $(".passwordClose").css("display", "none");
            } else {
                $(".passwordClose").css("display", "block");
            }
            return false;
        });
        $(".userNameClose").click(function () {
            logUserInput.val("");
        });
        $(".passwordClose").click(function () {
            logPassInput.val("");
        });
        /*按回车键进行登录*/
        $(".login-content .userName,.login-content .password").bind("keyup", function (evt) {
            if (evt.keyCode == "13") {
                $(".goLogin").trigger("click");
            }
        });
        $(".userNameClose").on("click", function () {
            $(this).css("display", "none");
            logUserInput.css({"border": "1px solid #2cb82c"});
            logUserInput.val("");
        });
        $(".passwordClose").on("click", function () {
            $(this).css("display", "none");
            logPassInput.css({"border": "1px solid #2cb82c"});
            logPassInput.val("");
        });
        var isCliclLogin = false;
        var cymyLogin = $(".usernameBox .userNameHit"),
            cymyLoginInfo = $(".usernameBox .HitWord"),
            cymyPass = $(".passwordBox .passwordHit"),
            cymyPassInfo = $(".passwordBox .HitWord");
        logUserInput.focus(function () {
            cymyLogin.css("display", "none");
            $(this).css("border", "1px solid #2cb82c");
        });
        logUserInput.blur(function () {
            var regPhone = /^1[3-578]\d{9}$/;
            var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
            if (logUserInput.val().trim().length === 0) {
                logUserInput.css("border", "1px solid #ff4012");
                cymyLoginInfo.text("请输入手机号");
                cymyLogin.css("display", "block");
            } else if (!(regPhone.test(logUserInput.val().trim()))) {
/*            } else if (logUserInput.val().trim().indexOf("@") == "-1" && !(regPhone.test(logUserInput.val().trim()))) {
*/                logUserInput.css("border", "1px solid #ff4012");
                cymyLoginInfo.text("手机号格式不正确!");
                cymyLogin.css("display", "block");
            } /*else if (logUserInput.val().trim().indexOf("@") != "-1" && !(regEmail.test(logUserInput.val().trim()))) {
                logUserInput.css("border", "1px solid #ff4012");
                cymyLoginInfo.text("邮箱格式不正确!");
                cymyLogin.css("display", "block");
            }*/ else {
                logUserInput.css("border", "");
            }
        });
        logPassInput.focus(function () {
            if (cymyPassInfo.text() == "请输入6-18位密码!") {
                cymyPass.css("display", "none");
            }
            logPassInput.css("border", "1px solid #2cb82c");
        });
        logPassInput.blur(function () {
            var cyinput2Length = logPassInput.val().trim().length;
            if (cyinput2Length == 0) {
                logPassInput.css("border", "1px solid #ff4012").val("");
                cymyPassInfo.text("请输入6-18位密码!");
                cymyPass.css("display", "block");
            } else if (cyinput2Length < 6 && cyinput2Length > 18) {
                logPassInput.css("border", "1px solid #ff4012").val("");
                cymyPassInfo.text("请输入6-18位密码!");
                cymyPass.css("display", "block");
            } else {
                logPassInput.css("border", "")
            }
        });
        $(".goLogin").click(function (evt) { //登录验证
            var regPhone = /^1[3-578]\d{9}$/;/*
            */var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
            logUserInput.css("border", "");
            logPassInput.css("border", "");
            var data = {
                username: logUserInput.val().trim(),
                password: logPassInput.val()
            };
            if (data.username.length === 0) {
                logUserInput.css("border", "1px solid #ff4012");
                cymyLoginInfo.text("请输入手机号");
                cymyLogin.css("display", "block");
                return;
            } else if (!(regPhone.test(data.username))) {
/*            } else if (data.username.indexOf("@") == "-1" && !(regPhone.test(data.username))) {
*/                logUserInput.css("border", "1px solid #ff4012");
                cymyLoginInfo.text("手机号格式不正确!");
                cymyLogin.css("display", "block");
                return;
            } /*else if (data.username.indexOf("@") != "-1" && !(regEmail.test(data.username))) {
                logUserInput.css("border", "1px solid #ff4012");
                cymyLoginInfo.text("邮箱格式不正确!");
                cymyLogin.css("display", "block");
                return;
            }*/ else if (data.password.length === 0) {
                logPassInput.css("border", "1px solid #ff4012");
                cymyPassInfo.text("请输入6-18位密码!");
                cymyPass.css("display", "block");
                return;
            }
            isCliclLogin = true;
            login(data);
        });
        function login(data, autoLogin) {
            RequestService("/online/user/login", "POST", data, function (result) { //登陆/index.html   /online/user/login
                if (result.success === true || result.success == undefined) {
                    location.href = "/index.html";
                } else { //登陆错误提示
                    errorMessage(result.errorMessage);
                    if (!flag) {
                        if (result.errorMessage == "用户名密码错误！") {
                        	cymyPassInfo.text("用户名或密码不正确!");
                            cymyPass.css("display", "block");
                        } else {
                        	cymyPassInfo.text(result.errorMessage);
                            cymyPass.css("display", "block");
                        }
                    }
                }
            });
        }
    }

    /*登录结束*/
    /*注册*/
    initRegister();
    function initRegister() {
        var cymYonghuInput = $(".registerYonghunameBox .RyonghuName"),
            cymYonghuInfo = $(".registerYonghunameBox .HitWord"),
            cymYonghuBox = $(".registerYonghunameBox .RyonghuNameHit"),
            
            cymregInput = $(".registerUsernameBox .Rusername"),
            cymregInfo = $(".registerUsernameBox .HitWord"),
            cymregBox = $(".registerUsernameBox .RuserNameHit"),
            
            cymCodeInput = $(".verification-code .verifCode"),
            cymCodeInfo = $(".verification-code .HitWord"),
            cymCodeBox = $(".verification-code .RverificationCodeHit"),
            
            cymPassInput = $(".registerPassword .Rpassword"),
            cymPassInfo = $(".registerPassword .HitWord"),
            cymPassBox = $(".registerPassword .RpassowrdHit");


        var nickNameReg = /^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/;//支持中文、字母、数字、'-'、'_'的组合，4-20个字符
        var numberReg=/^\d{4,20}$/;//纯数字验证
        var passwordReg = /^(?!\s+)[\w\W]{6,18}$/;//密码格式验证
        $(".allowProtocal").click(function () {
            $(".goRegister").toggleClass("registerProtocal");
        });
        $(".goRegister").click(function () { //验证注册
            var reg = /^1[3-578]\d{9}$/;
            var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
            var yonghuNameLength;

            if ($(".goRegister").hasClass("registerProtocal")) {
                $(".goRegister").unbind("click");
                return false;
            } else {
                $(".goRegister").bind("click");
            };
            //手机注册不需要验证码
            var data = {
                nikeName: cymYonghuInput.val().trim(),
                username: cymregInput.val().trim(),
                password: cymPassInput.val()
            };
            //邮箱注册需要验证呢干嘛
            var dataEmail = {
                nikeName: cymYonghuInput.val().trim(),
                username: cymregInput.val().trim(),
                password: cymPassInput.val(),
                vcode: $.trim($(".imgVertify").val())
            };
           
            if(nickNameReg.test(data.nikeName)){
                yonghuNameLength = nickName();
            }else{
                yonghuNameLength=0;
            }
            if (data.nikeName == "") {
                cymYonghuInfo.text("支持中文、字母、数字、'-'、'_'的组合，4-20个字符");
                cymYonghuBox.css("display", "block");
                cymYonghuInput.css("border", "1px solid #ff4012");
                return false;
            }else if(!nickNameReg.test(data.nikeName)){
                cymYonghuInfo.text('格式错误,仅支持汉字、字母、数字、"-""_"的组合');
                cymYonghuBox.css("display", "block");
                cymYonghuInput.css("border", "1px solid #ff4012");
                return false;
            }else if(numberReg.test(data.nikeName)){
                cymYonghuInfo.text("用户名不能是纯数字，请重新输入！");
                cymYonghuBox.css("display", "block");
                cymYonghuInput.css("border", "1px solid #ff4012");
                return false;
            }else if (yonghuNameLength > 20 || yonghuNameLength < 4){
                cymYonghuInfo.text("长度只能在4-20个字符之间");
                cymYonghuBox.css("display", "block");
                cymYonghuInput.css("border", "1px solid #ff4012");
                return false;
            }else if (data.username == "") {
                cymregInfo.text("手机号或邮箱不能为空！");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
                return false;
            } else if (data.username.indexOf('@') != -1 && !regEmail.test(data.username)) {
                cymregInfo.text("邮箱格式不正确!");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
                return false;
            } else if (data.username.indexOf('@') == -1 && !reg.test(data.username)) {
                cymregInfo.text("手机号格式不正确!");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
                return false;
            } else if (registMethond === "mobile") {
                data.code = cymCodeInput.val();
                if (data.code.length === 0) {
                    cymCodeInfo.text("请输入动态码!");
                    cymCodeInput.css("border", "1px solid #ff4012");
                    cymCodeBox.css("display", "block");
                    return false;
                } else if (data.code.length < 4) {
                    cymCodeInfo.text("请输入4位动态码!");
                    cymCodeInput.css("border", "1px solid #ff4012");
                    cymCodeBox.css("display", "block");
                    return false;
                } else if (data.password.length === 0) {
                    cymPassInfo.text("请输入6-18位密码!");
                    cymPassBox.css("display", "block");
                    cymPassInput.css("border", "1px solid #ff4012");
                    return false;
                } else if (data.password.length < 6 || data.password.length > 18) {
                    cymPassInfo.text("请输入6-18位密码!");
                    cymPassBox.css("display", "block");
                    cymPassInput.css("border", "1px solid #ff4012");
                    return false;
                } else if (!passwordReg.test(data.password)) {
                    cymPassInfo.text("密码格式不正确");
                    cymPassBox.css("display", "block");
                    cymPassInput.css("border", "1px solid #ff4012");
                    return false;
                } else {
                    if ($(".goRegister").hasClass("registerProtocal")) {

                    } else {
                        RequestService("/online/user/phoneRegist", "POST", data, function (result) {
                            if (result.success === true) {
                                rTips("注册成功");
                                setTimeout(function () {
                                    // $(".login-button").trigger("click");
                                    // $(".usernameBox .userName").css("border", "");
                                    // $(".usernameBox .userNameHit").css("display", "none");
                                    // $(".passwordBox .password").css("border", "");
                                    // $(".passwordBox .passwordHit").css("display", "none");
                                    // $(".userName").val(data.username);
                                    window.location.href = "/";
                                }, 1000);
                            } else {
                                errorMessage(result.errorMessage);
                                if (!flag) {
                                    if (result.errorMessage == "动态码不正确！") {
                                        cymCodeInfo.text(result.errorMessage);
                                        cymCodeBox.css("display", "block");
                                    } else if (result.errorMessage == "用户名已存在") {
                                        cymYonghuInfo.text(result.errorMessage);
                                        cymYonghuBox.css("display", "block");
                                    }
                                }
                                cymCodeInput.css("border", "1px solid #ff4012");
                            }
                        })
                    }
                }
            } else if (registMethond === "email") {
                if (data.password.length === 0) {
                    cymPassInfo.text("请输入6-18位密码!");
                    cymPassBox.css("display", "block");
                    cymPassInput.css("border", "1px solid #ff4012");
                    return false;
                } else if (data.password.length < 6 || data.password.length > 18) {
                    cymPassInfo.text("请输入6-18位密码!");
                    cymPassBox.css("display", "block");
                    cymPassInput.css("border", "1px solid #ff4012");
                    return false;
                } else if (!passwordReg.test(data.password)) {
                    cymPassInfo.text("密码格式不正确");
                    cymPassBox.css("display", "block");
                    cymPassInput.css("border", "1px solid #ff4012");
                    return false;
                } else {
                    cymPassInfo.text("");
                    RequestService("/online/user/emailRegist", "POST", dataEmail, function (result) {
                        if (result.success === true) {
                            cymregBox.css("display", "none");
                            localStorage.emailName = $(".Rusername").val().trim();
                           /* rTips("注册成功，请前往邮箱进行激活！");*/
                            window.location.href="/web/html/emailRegisterSuccess.html";
                        } else {
                            errorMessage(result.errorMessage);
                            if (result.errorMessage == "用户名已存在") {
                                cymYonghuInfo.text(result.errorMessage);
                                cymYonghuBox.css("display", "block");
                            } else if (!flag) {
                                cymregInfo.text(result.errorMessage);
                                cymregBox.css("display", "block");
                            }
                        }
                    })
                }
            }
        });

        //用户名
        cymYonghuInput.focus(function () {
            cymYonghuBox.css("display", "none");
            $(this).css("border", "1px solid #2cb82c");
        });
        cymYonghuInput.blur(function () {
            if(nickNameReg.test(cymYonghuInput.val())==true){
                var yonghuNameLength = nickName();
                if (yonghuNameLength > 20 || yonghuNameLength < 4) {
                    cymYonghuInfo.text("长度只能在4-20个字符之间");
                    cymYonghuBox.css("display", "block");
                    $(this).css("border", "1px solid #ff4012");
                } else if(numberReg.test(cymYonghuInput.val())){
                    cymYonghuInfo.text("用户名不能是纯数字，请重新输入！");
                    cymYonghuBox.css("display", "block");
                    $(this).css("border", "1px solid #ff4012");
                }else{
                    $(this).css("border", "");
                    RequestService("/online/user/checkNickName","get",{
                        nickName: $.trim($(".RyonghuName").val())
                    },function(data){
                        if(data.resultObject==true){
                            cymYonghuInfo.text("用户名已存在");
                            cymYonghuBox.css("display", "block");
                            cymYonghuInput.css("border", "1px solid #ff4012");
                            $(".registerYonghunameBox .cymyloginsuccess").css("display", "none");
                        }else{
                            $(".registerYonghunameBox .cymyloginsuccess").css("display", "block");
                        }
                    });
                }
            }else{
                if(cymYonghuInput.val()==""){
                    cymYonghuInfo.text("支持中文、字母、数字、'-'、'_'的组合，4-20个字符");
                    cymYonghuBox.css("display", "block");
                    $(this).css("border", "1px solid #ff4012");
                }else if(!nickNameReg.test(cymYonghuInput.val())){
                    cymYonghuInfo.text('格式错误,仅支持汉字、字母、数字、"-""_"的组合');
                    cymYonghuBox.css("display", "block");
                    $(this).css("border", "1px solid #ff4012");
                }
            }

        });
        //注册获取焦点清空提示
        cymregInput.focus(function () {
            cymregBox.css("display", "none");
            $(this).css("border", "1px solid #2cb82c");
        });
        //注册失去焦点判断用户名
        cymregInput.blur(function () {
            var reg = /^1[3-578]\d{9}$/;
            var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
            var data = {
                username: cymregInput.val().trim(),
                password: cymPassInput.val()
            };
            if (data.username == "") {
                cymregInfo.text("手机号不能为空！");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            }/* else if (data.username.indexOf('@') != -1 && !regEmail.test(data.username)) {
                cymregInfo.text("邮箱格式不正确!");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            } */else if (!reg.test(data.username)) {
                cymregInfo.text("手机号格式不正确!");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            } else {
                cymregInput.css("border", "");
            }
        });
        //注册获取焦点清空提示
        cymPassInput.focus(function () {
            cymPassBox.css("display", "none");
            $(this).css("border", "1px solid #2cb82c");
        });
        //注册失去焦点判断密码
        cymPassInput.blur(function () {
            var passwordReg = /^(?!\s+)[\w\W]{6,18}$/;//密码格式验证
            var data = {
                username: cymregInput.val().trim(),
                password: cymPassInput.val()
            };
            if (data.password.length === 0) {
                cymPassInfo.text("请输入6-18位密码!");
                cymPassBox.css("display", "block");
                cymPassInput.css("border", "1px solid #ff4012");
            } else if (data.password.length < 6 || data.password.length > 18) {
                cymPassInfo.text("请输入6-18位密码!");
                cymPassInput.css("border", "1px solid #ff4012");
                cymPassBox.css("display", "block");
            } else if (!passwordReg.test(data.password)) {
                cymPassInfo.text("密码格式不正确!");
                cymPassInput.css("border", "1px solid #ff4012");
                cymPassBox.css("display", "block");
            } else {
                cymPassInput.css("border", "");
            }
        });
        
        //注册失去焦点判断验证码
        cymCodeInput.blur(function () {
            var code = $(this).val();
            if (code.length === 0) {
                cymCodeInfo.text("请输入动态码!");
                cymCodeInput.css("border", "1px solid #ff4012");
                cymCodeBox.css("display", "block");
                return false;
            } else if (code.length < 4) {
                cymCodeInfo.text("请输入4位动态码!");
                cymCodeBox.css("display", "block");
                cymCodeInput.css("border", "1px solid #ff4012");
                return false;
            } else {
                cymCodeInput.css("border", "");
            }
        });
        //图形验证码输入框失去焦点判断
        $('.imgVertify').blur(function(){
        	if($(this).val()==''){
        		$('.RverificationCodeHit .HitWord').text('请输入验证码');
        		$('.imgVertifyCode .RverificationCodeHit').css("display", "block");
        	}
        })
         $('.imgVertify').focus(function(){
        	 $('.imgVertifyCode .RverificationCodeHit').css("display", "none");
         })
        
        cymCodeInput.focus(function () {
            var reg = /^1[3-578]\d{9}$/;
            var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
            var data = {
                username: cymregInput.val(),
                password: cymPassInput.val()
            };
            cymCodeBox.css("display", "none");
            if (data.username == "") {
                cymregInfo.text("用户名不能为空！");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            } else if (data.username.length < 6) {
                cymregInfo.text("请输入6-18位的用户名");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            } else if (data.username.length > 18) {
                cymregInfo.text("请输入6-18位的用户名");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            }/* else if (data.username.indexOf('@') != -1 && !regEmail.test(data.username)) {
                cymregInfo.text("邮箱格式不正确!");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            } */else if (!reg.test(data.username)) {
                cymregInfo.text("手机号格式不正确!");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            }
            cymCodeInput.css("border", "1px solid #2cb82c");
        });
        $(".btn-getcode").click(function () { //获取动态码
            $(this).css("background", "#ccc");
            var btn = this;
            var tel = $(".Rusername").val().trim();
            var imgVertify=$(".imgVertify").val();
            var data = {phone: tel, vtype: 1,vcode:imgVertify};
            var reg = /^1[34578]\d{9}$/;
            if (cymregInput.val() == "") {
                cymregInfo.text("用户名不能为空！");
                $(".btn-getcode").css("background", "#2cb82c");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            } else if (!reg.test(tel)/*||tel.match(/\s/).length>0*/) {
                cymregInfo.text("手机号格式不正确！");
                $(".btn-getcode").css("background", "#2cb82c");
                cymregBox.css("display", "block");
                cymregInput.css("border", "1px solid #ff4012");
            } else {
                RequestService("/online/verificationCode/sendmessage", "POST", data, function (result) {
                    if (result.success === false) {
                        errorMessage(result.errorMessage);
                        if (result.errorMessage == "该手机号已注册，请直接登录！") {
                            cymregInfo.text(result.errorMessage);
                            cymregBox.css("display", "block");
                            cymregInput.css("border", "1px solid #ff4012");
                        } else if (result.errorMessage == "验证码错误") {
                        	console.log(1)
                        	console.log(result.errorMessage)
                        	$('.imgVertifyCode .RverificationCodeHit .HitWord').text(result.errorMessage);
                        	$('.imgVertifyCode .RverificationCodeHit').css("display", "block");
                        } else if (!flag) {
                            cymCodeInfo.text(result.errorMessage);
                            cymCodeBox.css("display", "block");
                        }
                        $(".btn-getcode").css("background", "#2cb82c");
                    } else {
                        cymregBox.css("display", "none");
                        $(btn).addClass("enable");
                        var second = 90;
                        var oldval = $(btn).val();
                        cymCodeInput.css("border", "");
                        cymCodeBox.css("display", "none");
                        var timer = setInterval(function () {
                            $(btn).text(second-- + "s");
                            $(btn).addClass("enable");
                            if (second === 0 || $(btn).val() != oldval) {
                                second = 0;
                                $(btn).removeClass("enable");
                                $(btn).css("background", "#2cb82c");
                                $(btn).text("获取动态码");
                                clearInterval(timer);
                            }
                        }, 1000)
                    }
                });
            }
        });
        cymregInput.on("input", function () { //用户名提示
            var regPhone = /^1[3-578]\d{9}$/;
            if (regPhone.test($(this).val())) {
                registMethond = "mobile";
                $(".verification-code").css("display", "block");
            } else {
                registMethond = "email";
                $(".verification-code").css("display", "none");
            }
        });
        //登录框输入内容清空按钮出现，点击清空按钮内容清空自身消失 格式正确则显示绿色对勾
        /*登陆右边的叉*/
        cymregInput.on("input", function () {
            var val = $(this).val();
            if (val == "") {
                $(".RusernameClose").css("display", "none");
            } else {
                $(".RusernameClose").css("display", "block");
            }
            return false;
        });
        cymPassInput.on("input", function () {
            var logPass = $(this).val();
            if (logPass == "") {
                $(".RpasswordClose").css("display", "none");
            } else {
                $(".RpasswordClose").css("display", "block");
            }
        });

        //用户名输入内容清空按钮出现，点击清空按钮内容清空自身消失
        cymYonghuInput.on("input", function () {
            var yonghuNameLength=cymYonghuInput.val().length;
            if (cymYonghuInput.val() == "") {
                $(".registerYonghunameBox .cymyloginsuccess").css("display", "none");
                $(".RyonghuNameClose").css("display", "none");
            } else if(nickNameReg.test(cymYonghuInput.val()) && (yonghuNameLength>=4 && yonghuNameLength<=20) ){
                $(".RyonghuNameClose").css("display", "block");
            }else{
                $(".registerYonghunameBox .cymyloginsuccess").css("display", "none");
            }
        });
        //注册框输入内容清空按钮出现，点击清空按钮内容清空自身消失
        cymregInput.on("input", function () {
            var val = cymregInput.val();
            var regPhone = /^1[3-578]\d{9}$/;
            var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
            if (cymregInput.val() == "") {
                $(".registerUsernameBox .cymyloginsuccess").css("display", "none");
            } else {
                if (regPhone.test(val) || regEmail.test(val)) {
                    $(".registerUsernameBox .cymyloginsuccess").css("display", "block");
                } else {
                    $(".registerUsernameBox .cymyloginsuccess").css("display", "none");
                }
            }
        });
        $(".RusernameClose").click(function () {
            cymregInput.val("");
        });
        $(".RpasswordClose").click(function () {
            cymPassInput.val("");
        });
        $(".RyonghuNameClose").click(function () {
            cymYonghuInput.val("");
        });
        /*按回车键进行注册*/
        $(".RyonghuName,.Rusername,.verifCode,.Rpassword").bind('keyup', function (event) {
            if (event.keyCode == "13") {
                $(".goRegister").trigger("click");
            }
        });
    }

    //用户名长度
    function nickName() {
        var yonghuReg = /([\u4e00-\u9fa5]+)|(\w+)|([-]+)/g;
        var yonghuNameLength = 0;
        var yonghuName = $(".RyonghuName").val().trim();
        var arr = [];
        yonghuName.replace(yonghuReg, function (a, hanzi, number, zXian) {
            if (hanzi != undefined) {
                var hanziL = hanzi.length * 2;
                arr.push(hanziL);
            }
            if (number != undefined) {
                var numberL = number.length;
                arr.push(numberL);
            }
            if (zXian != undefined) {
                var zXianL = zXian.length;
                arr.push(zXianL);
            }
        });
        for (var i = 0; i < arr.length; i++) {
            yonghuNameLength += arr[i];
        }
        return yonghuNameLength;

    }

    /*注册结束*/

});
