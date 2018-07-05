///**
// * 如果是来自分享的话。需要判断这个id啦
// */
var opendId = getQueryString("openId");
if (isNotBlank(opendId)) {
    localStorage.setItem("openid", openId);
}
//if(isNotBlank(param_page)){
//	localStorage.setItem("code", param_page);
//}
//
//
//var error = getQueryString("error");
//if(isNotBlank(error)){
//	alert("帐号信息有误,重试或联系客户!");
//}

/**
 * 是否显示第三方登录啦
 */
var wxOrbrower = "";
if (is_weixin()) {
    console.log(" 是来自微信内置浏览器")
    wxOrbrower = "wx";


} else {
    wxOrbrower = "brower";

    $(".other_means").hide();
    $(".enter_bottom").hide();
}
//localStorage.setItem("access", wxOrbrower);
//var access = localStorage.access;
//if(access == "brower"){
//	$(".footer").hide();
//}else if(access == "wx"){
//	$(".footer").show();
//}
/**
 * 第三方登录
 */
function sanfangLogin() {

    /**
     * 清理下存在的信息
     */
    commonLocalStorageRemoveItem();

    location.href = "/xczh/wxlogin/publicWechatAndMobile";
}

/*******  新的接口  *************/

/**
 * 跳转注册页面
 */
$(".enroll").click(function () {
    location.href = "/xcview/html/cn_login.html";
})

//获取用户信息
//传值
//掉接口
//成功返回
//var page = param_page;
var openid = localStorage.openid;
var access = localStorage.access;
var tishi = document.getElementById("tishi");

/**
 * 点击登录
 * @returns {Boolean}
 */
function login() {

    var number = document.getElementById("account").value;
    var password = document.getElementById("password").value;

    if (!isNotBlank(number) || !isNotBlank(password)) {
//		webToast("手机号或密码不能为空","middle",1500);
        return false;
    }
    if (!(/^1[346578]\d{9}$/.test(number))) {

        webToast("请输入正确的手机号", "middle", 1500);
        return false;
    }
    var pwdLength = password.trim().length;
    if (pwdLength < 6 || pwdLength > 18) {
        webToast("请输入6~18位的密码", "middle", 1500);
        return false;
    }
    /**
     * 请求登录
     *
     */
    var urlparm = {
        username: number,
        password: password
    };
    requestService("/xczh/user/login", urlparm, function (data) {
        if (data.success) {
            /**
             * 添加 所有关于用户的缓存
             */
            commonLocalStorageSetItem(data);
            locationToOriginPage();
        } else {
            webToast(data.errorMessage, "middle", 1500);
        }
    });
}










