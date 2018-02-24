/**
 * Created by admin on 2016/11/26.
 */
$(function(){
    //错误提示信息
    var flag = false;
    function errorMessage(info) {
        flag = false;
        var errorReg = /[a-zA-z]+/g;
        if (errorReg.test(info)) {
            rTips("系统繁忙，请稍候再试!");
            return flag = true;
        }
    }
    function rTips(errorMessage){
        $(".rTips").text(errorMessage);
        $(".rTips").css("display","block");
        setTimeout(function(){
            $(".rTips").css("display","none");
        },2000)
    };
    //点击切换图形验证码
	$(".resetImgBox img").on("click",function(){
		var a=Math.random();
        $(this).attr("src","/online/verificationCode/vcode?t="+a+"");
	});
    $(".viaPhoneNumber").click(function(){
        $(".resetPasswordWays").css("display","none");
        $(".phoneNumberBox").css("display","block");
    });
    $(".viaEmail").click(function(){
        $(".resetPasswordWays").css("display","none");
        $(".emilaAccountBox").css("display","block");
    });
    $(".byPhoneNumber").click(function(){
        $(".emilaAccountBox").css("display","none");
        $(".phoneNumberBox").css("display","block");
        $(".phoneNumberBox .resetUsernameBox .resetusername").css("border","none");
        $(".phoneNumberBox .resetUsernameBox .resetusername").val("");
        $(".phoneNumberBox .resetUsernameHit").css("display","none");
        $(".phoneNumberBox .resetUsernameCode .resetcode").css("border","none");
        $(".phoneNumberBox .resetUsernameCode .resetcode").val("");
        $(".phoneNumberBox .resetVificationHit").css("display","none");
    });
    $(".byEmail").click(function(){
        $(".emilaAccountBox").css("display","block");
        $(".phoneNumberBox").css("display","none");
        $(".emilaAccountBox .resetUsernameBox .resetusername").css("border","none");
        $(".emilaAccountBox .resetUsernameBox .resetusername").val("");
        $(".emilaAccountBox .resetUsernameHit").css("display","none");
    });
    $(".atOnceLogin").click(function(){
        window.location.href="/web/html/login.html";
    });
    var username=window.localStorage.getItem("userName");
    /*重置密码*/
    $(".newPasswordBox .newPassword").on("focus",function(){
        $(this).css("border","1px solid #2cb82c");
        $(".newPasswordBox .resetUsernameHit").css("display","none");
    });
    $(".newPasswordBox .newPassword").on("blur",function(){
        $(this).css("border","");
        var resetPwd = $(".newPassword").val().trim();
        if (resetPwd.length == 0) {
            $(".newPasswordBox .HitWord").text("请输入6-18位密码");
            $(".newPassword").css("border", "1px solid #ff4012");
            $(".newPasswordBox .resetUsernameHit").css("display","block");
            return false;
        } else if (resetPwd.length < 6 || resetPwd.length > 18) {
            $(".newPasswordBox .HitWord").text("请输入6-18位密码");
            $(".newPassword").css("border", "1px solid #ff4012");
            $(".newPasswordBox .resetUsernameHit").css("display","block");
            return false;
        } else if (resetPwd == "") {
            $(".newPasswordBox .HitWord").text("请输入6-18位密码");
            $(".newPassword").css("border", "1px solid #ff4012");
            $(".newPasswordBox .resetUsernameHit").css("display","block");
            return false;
        }
    });
    $(".againComfirPassword .againNewPassword").on("focus",function(){
        $(this).css("border","1px solid #2cb82c");
        $(".againComfirPassword .resetUsernameHit").css("display","none");
    });
    $(".againComfirPassword .againNewPassword").on("blur",function(){
        $(this).css("border","");
        var resetPwd = $(".newPassword").val().trim();
        var resetPwd2 = $(".againNewPassword").val().trim();
        if (resetPwd2.length == 0) {
            $(".againComfirPassword .HitWord").text("请输入6-18位密码");
            $(".againNewPassword").css("border", "1px solid #ff4012");
            $(".againComfirPassword .resetUsernameHit").css("display","block");
            return false;
        }else if (resetPwd != resetPwd2) {
            $(".againComfirPassword .HitWord").text("两次密码不一致");
            $(".againNewPassword").css("border", "1px solid #ff4012");
            $(".againComfirPassword .resetUsernameHit").css("display","block");
            $(".newPasswordBox .resetUsernameHit").css("display","none");
            return false;
        }
    });
    RequestService("/online/user/isAlive", "GET", null, function (data) {
        if (!data.success) {
            $(".resetPasswordTitle").text("找回密码");
        } else{
            $(".resetPasswordTitle").text("修改密码");
            username=data.resultObject.loginName;
            if (username.indexOf("@") == "-1") {
                $(".viaPhoneNumber").click();
                $(".phoneNumberBox .resetusername").val("");
                $(".phoneNumberBox .resetusername").val(data.resultObject.loginName).attr("disabled", "disabled").css("backgroundColor", "#eee");
                $(".phoneNumberBox .nextStep").attr("data-vtype", "1");
            } else {
                $(".viaEmail").click();
                $(".emilaAccountBox .resetusername").val("");
                $(".emilaAccountBox .resetusername").val(data.resultObject.loginName).attr("disabled", "disabled").css("backgroundColor", "#eee");
                $(".emilaAccountBox .nextStep").attr("data-vtype", "0");
            }
        }
    });
    var resetPhoneInput=$(".phoneNumberBox .resetusername"),
        resetEmailInput=$(".emilaAccountBox .resetusername");
    resetPhoneInput.on("input", function () {
        if ($(".phoneNumberBox .resetusername").val() == "") {
            $(".phoneNumberBox .resetUsernameClose").css("display", "none");
        } else {
            $(".phoneNumberBox .resetUsernameClose").css("display", "block");
        }
    });
    resetEmailInput.on("input",function(){
        if ($(".emilaAccountBox .resetusername").val() == "") {
            $(".emilaAccountBox .resetUsernameClose").css("display", "none");
        } else {
            $(".emilaAccountBox .resetUsernameClose").css("display", "block");
        }
    });
    //重置密码
    var oldPassInput=$(".newPasswordBox .newPassword"),
        newPassInput=$(".againComfirPassword .againNewPassword");
    oldPassInput.on("input",function(){
        if($(this).val()==""){
            $(".newPasswordBox .resetUsernameClose").css("display","none");
        }else{
            $(".newPasswordBox .resetUsernameClose").css("display","block");
        }
    });
    $(".newPasswordBox .resetUsernameClose").click(function(){
        oldPassInput.val("");
        $(this).css("display","none");
    });
    newPassInput.on("input",function(){
        if($(this).val()==""){
            $(".againComfirPassword .resetPasswordClose").css("display","none");
        }else{
            $(".againComfirPassword .resetPasswordClose").css("display","block");
        }
    });
    $(".againComfirPassword .resetPasswordClose").click(function(){
        newPassInput.val("");
        $(this).css("display","none");
    });
    $(".resetUsernameBox .resetUsernameClose").click(function () {
        resetPhoneInput.val("");
        $(this).css("display", "none");
    });
    $(".emilaAccountBox .resetUsernameClose").click(function(){
        resetEmailInput.val("");
        $(this).css("display", "none");
    });
    /*找回密码回车事件*/
    resetPhoneInput.bind("keypress",function(evt){
        if(evt.which==13){
            $(".phoneNumberBox .nextStep").trigger("click");
        }
    });
    $(".resetcode").bind("keypress",function(evt){
        if(evt.which==13){
            $(".phoneNumberBox .nextStep").trigger("click");
        }
    });
    resetEmailInput.bind("keypress",function(evt){
        if(evt.which==13){
            $(".emilaAccountBox .nextStep").trigger("click");
        }
    });
    function resetPassVerfication(){

    }
    $(".cyResetPasswordbutton").click(function () { //验证
        var btn = this;
        var resetPhoneInput=$(".phoneNumberBox .resetusername");
        var tel = resetPhoneInput.val().trim();
        var rel = /^1[3-578]\d{8,9}$/;
        var resetcode = $(".resetcode").val();
        //验证码
        var yzm=$(".resetImg").val().trim();
        if (tel == "") {
            $(".phoneNumberBox .resetUsernameBox .HitWord").text("用户名不能为空!");
            resetPhoneInput.css("border", "1px solid #ff4012");
            $(".phoneNumberBox .resetUsernameBox .resetUsernameHit").css("display","block");
            return false;
        } else if (!rel.test(tel)) {
            $(".phoneNumberBox .resetusername .HitWord").text("手机号格式不正确!");
            resetPhoneInput.css("border", "1px solid #ff4012");
            $(".phoneNumberBox .resetusername .resetUsernameHit").css("display","block");
            return false;
        } else {
            RequestService("/online/verificationCode/sendmessage", "POST", {
                phone: tel,
                vtype: 2,
                vcode:yzm
            }, function (result) {
                if (result.success === false) {
                    errorMessage(result.errorMessage);
                    if (!flag) {
//                      $(".phoneNumberBox .resetUsernameBox .HitWord").text(result.errorMessage);
//                      $(".phoneNumberBox .resetUsernameBox .resetUsernameHit").css("display","block");
                        //验证码错误的时候
                        $(".resetVificationHit   .HitWord").text(result.errorMessage);
                         $(".resetImg").css("border", "1px solid #ff4012");
                        $(".resetVificationHit ").css("display","block");
                    }
                    $(this).css("background-color", "#2cb82c");
                } else {
                    if ((rel.test(tel))) {
                        //手机
                        $(".resetUsernameBox .resetUsernameHit").css("display","none");
                        $(".resetcode").css("border", "");
                        $(".resetUsernameCode .resetVificationHit").css("display","none");
                        $(btn).addClass("enable");
                        var oldval = $(".cyResetPasswordbutton").val();
                        var second = 90;
                        var timer = setInterval(function () {
                            $(btn).text(second-- + "s");
                            $(btn).addClass("btndisabled");
                            $(".cyResetPasswordbutton").css("background-color", "#ccc");
                            if (second === 0 || $(".cyResetPasswordbutton").val() != oldval) {
                                second = 0;
                                $(".cyResetPasswordbutton").css("background-color", "#2cb82c");
                                $(btn).removeClass("enable");
                                $(btn).text("获取动态码");
                                clearInterval(timer);
                            }
                        }, 1000)
                    }
                }
            })
        }
    });
    $(".emilaAccountBox .nextStep").click(function () { //验证
        resetPassVerfication();
    });
    $(".phoneNumberBox .nextStep").click(function () { //验证邮箱注册
        var regPhone = /^1[3-578]\d{9}$/;
        var code1 = $(".resetcode").val().trim();
        var imgCode = $('.resetImg').val().trim();
        var resetuname=resetPhoneInput.val().trim();
        if (resetuname == "") {
            $(".phoneNumberBox .resetUsernameBox .HitWord").text("用户名不能为空!");
            resetPhoneInput.css("border", "1px solid #ff4012");
            $(".phoneNumberBox .resetUsernameBox .resetUsernameHit").css("display","block");
            return false;
        } else if (!regPhone.test(resetuname)) {
            $(".phoneNumberBox .resetUsernameBox .HitWord").text("手机号格式不正确!");
            resetPhoneInput.css("border", "1px solid #ff4012");
            $(".phoneNumberBox .resetUsernameBox .resetUsernameHit").css("display","block");
            return false;
        }else if(imgCode.length == 0){
        	$(".resetVificationHit .HitWord").text("验证码错误");
            $(".resetImg").css("border", "1px solid #ff4012");
            $(".resetVificationHit").css("display","block");
            return false;
        }else if(code1.length==0){
//          $(".resetUsernameCode .HitWord").text("请输入动态码!");
//          $(".resetcode").css("border", "1px solid #ff4012");
//          $(".resetUsernameCode .resetVificationHit").css("display","block");
            
            $('#moveCode_warn > span').text("请输入动态码!");
            $(".resetcode").css("border", "1px solid #ff4012");
            $("#moveCode_warn").css("display","block");
            return false;
        }else if (code1.length != 0 && code1.length < 4) {
//          $(".resetUsernameCode .HitWord").text("请输入4位动态码!");
//          $(".resetcode").css("border", "1px solid #ff4012");
//          $(".resetUsernameCode .resetVificationHit").css("display","block");
            
            
            $('#moveCode_warn > span').text("请输入4位动态码!");
            $(".resetcode").css("border", "1px solid #ff4012");
            $("#moveCode_warn").css("display","block");
            return false;
        } else if (resetPhoneInput.val() != "" && resetPhoneInput.val().length == 11) {
                $(".phoneNumberBox .resetUsernameHit").css("display","none");
                var data = {
                    phone:resetPhoneInput.val().trim(),
                    code: $(".resetcode").val().trim()
                };
            }
            if (code1 == "" || code1.length == 4) {
                $(".resetUsernameCode .resetVificationHit").css("display","none");
                RequestService("/online/verificationCode/checkCode", "POST", data, function (result) {
                    if (code1.length == 0 && result.errorMessage != "用户不存在！") {
                        $(".resetUsernameCode .HitWord").text("请输入动态码!");
                        $(".resetcode").css("border", "1px solid #ff4012");
                        $(".resetUsernameCode .resetVificationHit").css("display","block");
                    }else if(result.success==false){
//                      $(".resetUsernameCode .HitWord").text(result.errorMessage);
//                      $(".resetcode").css("border", "1px solid #ff4012");
//                      $(".resetUsernameCode .resetVificationHit").css("display","block");
                        
                        
                        $('#moveCode_warn > span').text(result.errorMessage);
			            $(".resetcode").css("border", "1px solid #ff4012");
			            $("#moveCode_warn").css("display","block");
                        
                    }
                    if (result.success === true) {
                        $(".phoneNumberBox").css("display","none");
                        $(".comfirePasswordBox").css("display","block");
                        $(".resetPaswordComplete").click(function () {
                            var resetPasswordReg = /^(?!\s+)[\w\W]{6,18}$/;//密码格式验证
                            var resetPwd = $(".newPassword").val().trim();
                            var resetPwd2 = $(".againNewPassword").val().trim();
                            if (resetPwd.length == 0 || resetPwd2.length == 0) {
                                $(".newPasswordBox .HitWord").text("请输入6-18位密码");
                                $(".newPassword").css("border", "1px solid #ff4012");
                                $(".newPasswordBox .resetUsernameHit").css("display","block");
                                return false;
                            } else if (resetPwd.length < 6 || resetPwd.length > 18) {
                                $(".newPasswordBox .HitWord").text("请输入6-18位密码");
                                $(".newPassword").css("border", "1px solid #ff4012");
                                $(".newPasswordBox .resetUsernameHit").css("display","block");
                                return false;
                            } else if (resetPwd == "") {
                                $(".newPasswordBox .HitWord").text("请输入6-18位密码");
                                $(".newPassword").css("border", "1px solid #ff4012");
                                $(".newPasswordBox .resetUsernameHit").css("display","block");
                                return false;
                            } else if (resetPwd != resetPwd2) {
                                $(".againComfirPassword .HitWord").text("两次密码不一致");
                                $(".againNewPassword").css("border", "1px solid #ff4012");
                                $(".againComfirPassword .resetUsernameHit").css("display","block");
                                $(".newPasswordBox .resetUsernameHit").css("display","none");
                                return false;
                            } else {
                                var data = {
                                    username: $(".phoneNumberBox .resetusername").val().trim(),
                                    password: resetPwd,
                                    code: $(".resetcode").val().trim()
                                };
                                window.localStorage.username=data.username;
                                $(".newPasswordBox .resetUsernameHit").css("display","none");
                                RequestService("/online/user/resetUserPassword", "POST", data, function (result) {
                                    if (result.success === true) {
                                        rTips("密码设置成功，立即登录");
                                        setInterval(function(){
                                            window.location.href="/web/html/login.html";
                                        },1000);
                                    } else {
                                        errorMessage(result.errorMessage);
                                        if (!flag) {
                                            $(".newPasswordBox .HitWord").text(result.errorMessage);
                                            $(".newPasswordBox .resetUsernameHit").css("display","block");
                                        }
                                    }
                                });
                            }
                        })
                    } else {
                        errorMessage(result.errorMessage);
                        if (!flag && result.errorMessage == "用户不存在！") {
                            $(".newPasswordBox .HitWord").text(result.errorMessage);
                            $(".newPassword").css("border", "1px solid #ff4012");
                            $(".newPasswordBox .resetUsernameHit").css("display","block");
                            return;
                        } else if (!flag && code1 == "") {
                            $(".resetUsernameCode .HitWord").innerText = "请输入动态码!";
                            $(".resetcode").css("border", "1px solid #ff4012");
                            $(".resetUsernameCode .resetVificationHit").css("display","block");
                        }
                        else {
                            if (!flag) {
                                $(".resetUsernameCode .HitWord").text(result.errorMessage);
                                $(".resetUsernameCode .resetcode").css("border", "1px solid #ff4012");
                                $(".resetUsernameCode .resetUsernameHit").css("display","block");
                            }
                        }
                    }
                })
            }
    });
    //跳转到指定的邮箱登录页面
    $(".loginEmial").click(function () {
        var uurl = localStorage.getItem("emailName");
        uurl = gotoEmail(uurl);
        if (uurl != "") {
            window.location.href="http://" + uurl;
        } else {
            alert("抱歉!未找到对应的邮箱登录地址，请自己登录邮箱查看邮件！");
        }
    });
    //功能：根据用户输入的Email跳转到相应的电子邮箱首页
    function gotoEmail($mail) {
        $t = $mail.split('@')[1];
        $t = $t.toLowerCase();
        if ($t == '163.com') {
            return 'mail.163.com';
        } else if ($t == 'vip.163.com') {
            return 'vip.163.com';
        } else if ($t == '126.com') {
            return 'mail.126.com';
        } else if ($t == 'qq.com' || $t == 'vip.qq.com' || $t == 'foxmail.com') {
            return 'mail.qq.com';
        } else if ($t == 'gmail.com') {
            return 'mail.google.com';
        } else if ($t == 'sohu.com') {
            return 'mail.sohu.com';
        } else if ($t == 'tom.com') {
            return 'mail.tom.com';
        } else if ($t == 'vip.sina.com') {
            return 'vip.sina.com';
        } else if ($t == 'sina.com.cn' || $t == 'sina.com') {
            return 'mail.sina.com.cn';
        } else if ($t == 'tom.com') {
            return 'mail.tom.com';
        } else if ($t == 'yahoo.com.cn' || $t == 'yahoo.cn') {
            return 'mail.cn.yahoo.com';
        } else if ($t == 'tom.com') {
            return 'mail.tom.com';
        } else if ($t == 'yeah.net') {
            return 'www.yeah.net';
        } else if ($t == '21cn.com') {
            return 'mail.21cn.com';
        } else if ($t == 'hotmail.com') {
            return 'www.hotmail.com';
        } else if ($t == 'sogou.com') {
            return 'mail.sogou.com';
        } else if ($t == '188.com') {
            return 'www.188.com';
        } else if ($t == '139.com') {
            return 'mail.10086.cn';
        } else if ($t == '189.cn') {
            return 'webmail15.189.cn/webmail';
        } else if ($t == 'wo.com.cn') {
            return 'mail.wo.com.cn/smsmail';
        } else if ($t == 'itcast.cn') {
            return 'mm.263.com';
        } else if ($t == 'ixincheng.com') {
            return 'mail.ixincheng.com';
        } else {
            return '';
        }
    };
    $(".emilaAccountBox .nextStep").click(function(){
        var email = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
        var emailVal=resetEmailInput.val().trim();
        if (emailVal == "") {
            $(".emilaAccountBox .resetUsernameBox .HitWord").text("用户名不能为空!");
            resetEmailInput.css("border", "1px solid #ff4012");
            $(".emilaAccountBox .resetUsernameBox .resetUsernameHit").css("display","block");
            return false;
        } else if (!email.test(emailVal)) {
            $(".emilaAccountBox .resetUsernameBox .HitWord").text("邮箱格式不正确!");
            resetEmailInput.css("border", "1px solid #ff4012");
            $(".emilaAccountBox .resetUsernameBox .resetUsernameHit").css("display","block");
            $(".emilaAccountBox .resetUsernameBox .resetUsernameHit").css("display","block");
            return false;
        }   else { //发送邮件
            if ($(".emilaAccountBox .resetusername").val() != "") {
                var data = {
                    email: $(".emilaAccountBox .resetusername").val().trim()
                };
                RequestService("/online/verificationCode/sendemail", "POST", data, function (result) {
                    if (result.success === true) {
                        window.localStorage.emailName=data.email;
                        $(".emilaAccountBox").css("display","none");
                        $(".emailSendSuccessBox").css("display","block");
                    } else {
                        errorMessage(result.errorMessage);
                        if (!flag) {
                            $(".emilaAccountBox .HitWord").text(result.errorMessage);
                            $(".emilaAccountBox .resetusername").css("border","1px solid #ff4012");
                            $(".emilaAccountBox .resetUsernameHit").css("display", "block");
                        }
                    }
                })
            } else {
                $(".emilaAccountBox .HitWord").text("请输入6-18位的用户名");
                $(".emilaAccountBox .resetusername").css("border","1px solid #ff4012");
                $(".emilaAccountBox .resetUsernameHit").css("display", "block");
            }
        }
    });
    //重置密码失去焦点验证
    $(".emilaAccountBox .resetusername").focus(function () {
        $(this).css("border", "1px solid #2cb82c");
        $(".emilaAccountBox .resetUsernameHit").css("display","none");
    });
    $(".emilaAccountBox .resetusername").blur(function () {
        var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
        var resetEmailVal=resetEmailInput.val().trim();
        if (resetEmailVal == "") {
            $(".emilaAccountBox .resetUsernameBox .HitWord").text("用户名不能为空!");
            resetEmailInput.css("border", "1px solid #ff4012");
            $(".emilaAccountBox .resetUsernameBox .resetUsernameHit").css("display","block");
            return false;
        }if (!regEmail.test(resetEmailVal)) {
            $(".emilaAccountBox .resetUsernameBox .HitWord").text("邮箱格式不正确!");
            resetEmailInput.css("border", "1px solid #ff4012");
            $(".emilaAccountBox .resetUsernameBox  .resetUsernameHit").css("display","block");
        }else{
            resetEmailInput.css("border", "");
        }
    });
    $(".phoneNumberBox .resetusername").focus(function () {
        $(this).css("border", "1px solid #2cb82c");
        $(".phoneNumberBox .resetUsernameHit").css("display","none");
    });
    $(".phoneNumberBox .resetusername").blur(function () {
        var regPhone = /^1[3-578]\d{9}$/;
        var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
        var resetuname=resetPhoneInput.val().trim();
        var cyResetPasswordhint = document.getElementsByClassName("cyResetPasswordhint")[0];
        if (resetuname == "") {
            $(".phoneNumberBox .resetUsernameBox .HitWord").text("用户名不能为空!");
            resetPhoneInput.css("border", "1px solid #ff4012");
            $(".phoneNumberBox .resetUsernameBox .resetUsernameHit").css("display","block");
        } else if (!regPhone.test(resetuname)) {
            $(".phoneNumberBox .resetUsernameBox .HitWord").text("手机号格式不正确!");
            resetPhoneInput.css("border", "1px solid #ff4012");
            $(".phoneNumberBox .resetUsernameBox .resetUsernameHit").css("display","block");
        }else{
            resetPhoneInput.css("border", "");
            $(".resetcode").css("border", "");
        }
    });
    $(".resetcode").blur(function () {
        var code1 = $(".resetcode").val();
        if (code1.length == 0) {
//          $(".resetUsernameCode .HitWord").text("请输入动态码!");
//          $(".resetcode").css("border", "1px solid #ff4012");
//          $(".resetUsernameCode .resetVificationHit").css("display","block");
            
            $('#moveCode_warn > span').text("请输入动态码!");
            $(".resetcode").css("border", "1px solid #ff4012");
            $("#moveCode_warn").css("display","block");
        } else if (code1.length > 0 && code1.length < 4) {
//          $(".resetUsernameCode .HitWord").text("请输入4位动态码!");
//          $(".resetcode").css("border", "1px solid #ff4012");
//          $(".resetUsernameCode .resetVificationHit").css("display","block");
            
             $('#moveCode_warn > span').text("请输入4位动态码!");
            $(".resetcode").css("border", "1px solid #ff4012");
            $("#moveCode_warn").css("display","block");
        }else{
            $(".resetcode").css("border", "");
        }
    })
    $(".resetcode").focus(function () {
        $(".resetcode").css("border", "1px solid #2cb82c");
        $(".resetUsernameCode .resetVificationHit").css("display","none");
    })
});