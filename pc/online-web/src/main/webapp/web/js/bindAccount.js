/**
 * Created by admin on 2016/11/28.
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
    }
    var  bindMethond;
    $(".bindWaysBox div").click(function(){
        $(this).addClass("select").siblings().removeClass("select");
    });
    $(".bindNewAccount").click(function(){
        $(".bindOldAccountBox").css("display","none");
        $(".bindNewAccountBox").css("display","block");
    });
    $(".bindOldAccount").click(function(){
        $(".bindOldAccountBox").css("display","block");
        $(".bindNewAccountBox").css("display","none");
    });
    $(".newAccountUsername").on("input",function(){
        if($(this).val()!=""){
            $(".bindNewAccountClose").css("display","block");
        }else{
            $(".bindNewAccountClose").css("display","none");
        }
        if ($(this).val().indexOf("@") == "-1") {
            bindMethond = "mobile";
            $(".bindVerficationCode").css("display","block");
        } else {
            bindMethond = "email";
            $(".bindVerficationCode").css("display","none");
        }
    });
    $(".oldAccountUsername").on("input",function(){
        if($(this).val()!=""){
            $(".bindOldAccountUsername .bindOldAccountClose").css("display","block");
        }else{
            $(".bindOldAccountUsername .bindOldAccountClose").css("display","none");
        }
    });
    $(".oldAccountPassword").on("input",function(){
        if($(this).val()!=""){
            $(".bindOldAccountPassword .bindOldAccountClose").css("display","block");
        }else{
            $(".bindOldAccountPassword .bindOldAccountClose").css("display","none");
        }
    });
    $(".bindNewAccountClose").click(function(){
        $(".newAccountUsername").val("");
    });
    $(".bindOldAccountUsername .bindOldAccountClose").click(function(){
        $(".oldAccountUsername").val("");
    });
    $(".bindOldAccountPassword .bindOldAccountClose").click(function(){
        $(".oldAccountPassword").val("");
    });
    //绑定新帐号失去焦点验证
    var newAccNameInput=$(".newAccountUsername"),
        newAccNameInfo=$(".bindNewAccountHit .HitWord"),
        newAccNameBox=$(".bindNewAccountHit"),
        verfiCodeInput=$(".bindverifCode"),
        verfiCodeInfo=$(".bindVerficationCodeHit .HitWord"),
        verfiCodeBox=$(".bindVerficationCodeHit");
    newAccNameInput.on("focus",function(){
        $(this).css("border","1px solid #2cb82c");
        newAccNameBox.css("display","none");
    });
    newAccNameInput.on("blur",function(){
        var reg = /^1[3-5678]\d{9}$/;
        var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
        var newAccNameVal=$(this).val();
        if (newAccNameVal == "") {
            newAccNameInfo.text("用户名不能为空！");
            newAccNameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        } else if (newAccNameVal.length < 6) {
            newAccNameInfo.text("请输入6-18位的用户名");
            newAccNameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        } else if (newAccNameVal.length > 18) {
            newAccNameInfo.text("请输入6-18位的用户名");
            newAccNameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        } else if (newAccNameVal.indexOf('@') != -1 && !regEmail.test(newAccNameVal)) {
            newAccNameInfo.text("邮箱格式不正确!");
            newAccNameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        } else if (newAccNameVal.indexOf('@') == -1 && !reg.test(newAccNameVal)) {
            newAccNameInfo.text("手机号格式不正确!");
            newAccNameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        }else{
            $(this).css("border","");
        }
    });
    verfiCodeInput.on("focus",function(){
        $(this).css("border","1px solid #2cb82c");
        verfiCodeBox.css("display","none");
    });
    verfiCodeInput.on("blur",function(){
        var verfiCodeVal=$(this).val();
        if (verfiCodeVal.length === 0) {
            verfiCodeInfo.text("请输入动态码!");
            $(this).css("border","1px solid #ff4012");
            verfiCodeBox.css("display", "block");
        } else if (verfiCodeVal.length < 4) {
            verfiCodeInfo.text("请输入4位动态码!");
            $(this).css("border","1px solid #ff4012");
            verfiCodeBox.css("display", "block");
        }
    });
    $(".bind-getcode").click(function(){
        alert(bindMethond === "email")
        var bindUserName=$(".newAccountUsername");
        var username=bindUserName.val().trim();
        $(this).css("background", "#ccc");
        var data = {phone:username , vtype: 1};
        var btn = this;
        var reg = /^1[3-5678]\d{9}$/;
        if (username == "") {
            newAccNameInfo.text("用户名不能为空！");
            newAccNameBox.css("display", "block");
            newAccNameInput.css("border", "1px solid #ff4012");
        } else if (username.length < 6 || username.length > 18) {
            newAccNameInfo.text("请输入6-18位的用户名");
            newAccNameBox.css("display", "block");
            newAccNameInput.css("border", "1px solid #ff4012");
            return false;
        } else if (username.indexOf('@') != -1 && !regEmail.test(username)) {
            newAccNameInfo.text("邮箱格式不正确!");
            newAccNameBox.css("display", "block");
            newAccNameInput.css("border", "1px solid #ff4012");
            return false;
        } else if (username.indexOf('@') == -1 && !reg.test(username)) {
            newAccNameInfo.text("手机号格式不正确!");
            newAccNameBox.css("display", "block");
            newAccNameInput.css("border", "1px solid #ff4012");
            return false;
        }else{
            RequestService("/online/verificationCode/sendmessage", "POST", data, function (result) {
                if (result.success === false) {
                    errorMessage(result.errorMessage);
                    if(result.errorMessage=="该手机号已注册，请直接登录！"){
                        newAccNameInfo.text(result.errorMessage);
                        newAccNameBox.css("display", "block");
                        newAccNameInput.css("border", "1px solid #ff4012");
                    }else if(!flag) {
                        newAccNameInfo.text(result.errorMessage);
                        newAccNameBox.css("display", "block");
                    }
                    $(".bind-getcode").css("background", "#2cb82c");
                } else {
                    newAccNameInput.css("border", "");
                    newAccNameBox.css("display","none");
                    $(btn).addClass("enable");
                    var second = 90;
                    var oldval = $(btn).val();
                    $(btn).unbind("click");
                    var timer = setInterval(function () {
                        $(btn).text(second-- + "s");
                        $(btn).addClass("enable");
                        if (second === 0 || $(btn).val() != oldval) {
                            second = 0;
                            $(btn).bind("click");
                            $(btn).removeClass("enable");
                            $(btn).css("background", "#2cb82c");
                            $(btn).text("获取动态码");
                            clearInterval(timer);
                        }
                    }, 1000)
                }
            })
        }
    });
    $(".newBindAndLogin").click(function(){
        var reg = /^1[3-5678]\d{9}$/;
        var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
        var bindUserName=$(".newAccountUsername");
        var bindVerfiCode=$(".bindverifCode");
        var data = {
            username: bindUserName.val().trim()
        };
        if (data.username == "") {
            newAccNameInfo.text("用户名不能为空！");
            newAccNameBox.css("display", "block");
            newAccNameInput.css("border", "1px solid #ff4012");
        } else if (data.username.length < 6 || data.username.length > 18) {
            newAccNameInfo.text("请输入6-18位的用户名");
            newAccNameBox.css("display", "block");
            newAccNameInput.css("border", "1px solid #ff4012");
            return false;
        } else if (data.username.indexOf('@') != -1 && !regEmail.test(data.username)) {
            newAccNameInfo.text("邮箱格式不正确!");
            newAccNameBox.css("display", "block");
            newAccNameInput.css("border", "1px solid #ff4012");
            return false;
        } else if (data.username.indexOf('@') == -1 && !reg.test(data.username)) {
            newAccNameInfo.text("手机号格式不正确!");
            newAccNameBox.css("display", "block");
            newAccNameInput.css("border", "1px solid #ff4012");
            return false;
        } else if (bindMethond === "mobile") {
            alert(bindMethond === "mobile")
            data.code = bindVerfiCode.val();
            if (data.code.length === 0) {
                verfiCodeInfo.text("请输入动态码!");
                verfiCodeInput.css("border", "1px solid #ff4012");
                verfiCodeBox.css("display", "block");
                return false;
            } else if (data.code.length < 4) {
                verfiCodeInfo.text("请输入4位动态码!");
                verfiCodeInput.css("border", "1px solid #ff4012");
                verfiCodeBox.css("display", "block");
                return false;
            }
            RequestService("/online/user/phoneRegist", "POST", data, function (result) {
                if (result.success === true) {
                   RequestService("/online/user/bindcount",'GET',{username:data.username},function(){
                       if(result.success==false){
                           errorMessage(result.errorMessage);
                           if (!flag) {
                               newAccNameInfo.text(result.errorMessage);
                               newAccNameBox.css("display", "block");
                           }
                       }
                   });
                } else {
                    errorMessage(result.errorMessage);
                    if (!flag) {
                        if (result.errorMessage == "动态码不正确！") {
                            verfiCodeInfo.text(result.errorMessage);
                            verfiCodeBox.css("display", "block");
                        } else {
                            verfiCodeInfo.text(result.errorMessage);
                            verfiCodeBox.css("display", "block");
                        }
                    }
                    verfiCodeInput.css("border", "1px solid #ff4012");
                }
            })
        }else if(bindMethond === "email"){
            RequestService("/online/user/emailRegist", "POST", data, function (result) {
                if (result.success === true) {
                    newAccNameBox.css("display", "none");
                    RequestService("/online/user/bindcount",'POST',{username:data.username},function(data){
                        if(data.success == false){
                            errorMessage(data.errorMessage);
                            if (!flag) {
                                newAccNameInfo.text(data.errorMessage);
                                newAccNameBox.css("display", "block");
                            }
                        }else{
                            window.location.href="/index.html";
                        }
                    });
                    if (result.resultObject != "用户已存在！") {

                    }
                } else {
                    errorMessage(result.errorMessage);
                    if (!flag) {
                        newAccNameInfo.text(result.errorMessage);
                        newAccNameBox.css("display", "block");
                    }
                }
            })
        }
    });
    //绑定已有帐号
   var oldAccnameInput=$(".oldAccountUsername"),
       oldAccnameInfo=$(".bindOldAccountUsername .HitWord"),
       oldAccnameBox=$(".bindOldAccountUsername .bindOldAccountHit"),
       oldAccpassInput=$(".oldAccountPassword"),
       oldAccpassInfo=$(".bindOldAccountPassword .HitWord"),
       oldAccpassBox=$(".bindOldAccountPassword .bindOldAccountHit");
    oldAccnameInput.on("focus",function(){
        $(this).css("border","1px solid #2cb82c");
        oldAccnameBox.css("display","none");
    });
    oldAccnameInput.on("blur",function(){
        var oldAccnamVal=$(this).val();
        var reg = /^1[3-5678]\d{9}$/;
        var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
        if (oldAccnamVal == "") {
            oldAccnameInfo.text("用户名不能为空！");
            oldAccnameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        } else if (oldAccnamVal.length < 6) {
            oldAccnameInfo.text("请输入6-18位的用户名");
            oldAccnameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        } else if (oldAccnamVal.length > 18) {
            oldAccnameInfo.text("请输入6-18位的用户名");
            oldAccnameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        } else if (oldAccnamVal.indexOf('@') != -1 && !regEmail.test(oldAccnamVal)) {
            oldAccnameInfo.text("邮箱格式不正确!");
            oldAccnameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        } else if (oldAccnamVal.indexOf('@') == -1 && !reg.test(oldAccnamVal)) {
            oldAccnameInfo.text("手机号格式不正确!");
            oldAccnameBox.css("display", "block");
            $(this).css("border","1px solid #ff4012");
        }else{
            $(this).css("border","");
        }
    })
    oldAccpassInput.on("focus",function(){
        $(this).css("border","1px solid #2cb82c");
        oldAccpassBox.css("display","none");
    });
    oldAccpassInput.on("blur",function(){
        var oldAccpassVal=$(this).val();
        var passwordReg = /^(?!\s+)[\w\W]{6,18}$/;//密码格式验证
        if (oldAccpassVal.length === 0) {
            oldAccpassInfo.text("请输入6-18位密码!");
            oldAccpassBox.css("display", "block");
            $(this).css("border", "1px solid #ff4012");
        } else if (oldAccpassVal.length < 6 || oldAccpassVal.length > 18) {
            oldAccpassInfo.text("请输入6-18位密码!");
            oldAccpassBox.css("display", "block");
            $(this).css("border", "1px solid #ff4012");
        } else if (!passwordReg.test(oldAccpassVal)) {
            oldAccpassInfo.text("密码格式不正确");
            oldAccpassBox.css("display", "block");
            $(this).css("border", "1px solid #ff4012");
        }
    });
    $(".oldBindAndLogin").click(function(){
        var reg = /^1[3-5678]\d{9}$/;
        var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;
        var passwordReg = /^(?!\s+)[\w\W]{6,18}$/;//密码格式验证
        var bindUserName=$(".oldAccountUsername");
        var bindUserPass=$(".oldAccountPassword");
        var data = {
            username: bindUserName.val().trim(),
            password:bindUserPass.val()
        };
        if (data.username == "") {
            oldAccnameInfo.text("用户名不能为空！");
            oldAccnameBox.css("display", "block");
            oldAccnameInput.css("border", "1px solid #ff4012");
        } else if (data.username.length < 6 || data.username.length > 18) {
            oldAccnameInfo.text("请输入6-18位的用户名");
            oldAccnameBox.css("display", "block");
            oldAccnameInput.css("border", "1px solid #ff4012");
            return false;
        } else if (data.username.indexOf('@') != -1 && !regEmail.test(data.username)) {
            oldAccnameInfo.text("邮箱格式不正确!");
            oldAccnameBox.css("display", "block");
            oldAccnameInput.css("border", "1px solid #ff4012");
            return false;
        } else if (data.username.indexOf('@') == -1 && !reg.test(data.username)) {
            oldAccnameInfo.text("手机号格式不正确!");
            oldAccnameBox.css("display", "block");
            oldAccnameInput.css("border", "1px solid #ff4012");
            return false;
        }else if (data.password.length === 0) {
            oldAccpassInfo.text("请输入6-18位密码!");
            oldAccpassBox.css("display", "block");
            oldAccpassInput.css("border", "1px solid #ff4012");
            return false;
        } else if (data.password.length < 6 || data.password.length > 18) {
            oldAccpassInfo.text("请输入6-18位密码!");
            oldAccpassBox.css("display", "block");
            oldAccpassInput.css("border", "1px solid #ff4012");
            return false;
        } else if (!passwordReg.test(data.password)) {
            oldAccpassInfo.text("密码格式不正确");
            oldAccpassBox.css("display", "block");
            oldAccpassInput.css("border", "1px solid #ff4012");
            return false;
        }else{
            RequestService("/online/user/bindcount",'POST',{username:data.username},function(data){
                if(data.success == false){
                    errorMessage(data.errorMessage);
                    if (!flag) {
                        oldAccnameInfo.text(data.errorMessage);
                        oldAccnameBox.css("display", "block");
                    }
                }else{
                    window.location.href="index.html";
                }
            });
        }
    });
});