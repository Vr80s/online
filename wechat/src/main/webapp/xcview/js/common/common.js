/*(function ($) {
    mui(".navtab").on('tap', 'a', function () {
        document.location.href = this.href;
    })
})(mui);*/

/**
 * cookie
 * third_party_falg   true 需要进行完善信息   false 不用进行完善信息
 * 返回 1000 有效   1002 过期，去登录页面  1005 过期且去完善信息
 */

var USER_TOKEN_NULL = 1001;//token is null
var USER_UN_LOGIN = 1002;//未登录
var USER_TOP = 1003;//被顶掉
var USER_UN_BIND = 1005;//用户用微信登录的但是没有绑定注册信息
var USER_NORMAL = 1000;
var USER_WEIXIN_AUTH = 1006;//需要去微信授权

var APP_TOKEN = "app.token";
var APP_AppUniqueId = "app.appUniqueId";
var headers = {"clientType":"2"};

// QA_quiz

/*$(".QA_quiz").click(function(){

});*/

/**
 * 判断是不是来自微信浏览器
 */
function is_weixin() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}

function getCurrentUrl() {
    return encodeURIComponent(document.location.protocol + "//" + document.location.host + document.location.pathname + document.location.search + document.location.hash);
}

function getCurrentRelativeUrl() {
    return document.location.pathname + document.location.search + document.location.hash;
}

//判断字段为非空值
function isNotBlank(str) {
    str = $.trim(str);
    if (str == undefined || str == null || str == "" || str == "undefined" || str == "null") {
        return false;
    }
    return true;
}

//判断字段为空值
function isBlank(str) {
    return !isNotBlank(str);
}


/**
 * 截取url传递的参数
 * @param name 传递 key  得到value
 * @returns
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var search = decodeURIComponent(window.location.search);
    var r = search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

/*
 * 如果是扫二维码的话
 *   通过另一个参数来判断。
 */
var qr_code = getQueryString("qr_code");
if (isNotBlank(qr_code)) {
    var wxOrbrower = "wx";
    if (!is_weixin()) {
        wxOrbrower = "brower";
    }
    var searchUrl = window.location.href;
    var test = window.location.pathname;
    var int = searchUrl.indexOf("qr_code");
    searchUrl = searchUrl.substring(0, int - 1);
    location.href = "/xczh/share/xcCustomQrCode?search_url=" + searchUrl + "&wxOrbrower=" + wxOrbrower;
}

var accessCommon = localStorage.access;
var current = window.location.search;
//当前域名
var domain = window.location.host;       

/**
 * 得到  online-web 的测试或者生成域名
 * @returns
 */
function getServerHost() {
    var server_domain = sessionStorage.server_domain;
    if (isBlank(server_domain)) {
        requestService("/xczh/common/getDomain", null, function (data) {
            if (data.success) {
                sessionStorage.setItem("server_domain", data.resultObject);
                server_domain = data.resultObject;
            }
        }, false)
    } else {
        return server_domain;
    }
    if (isBlank(server_domain)) {
        return "http://www.ipandatcm.com";
    }
    return server_domain;
}

/**
 * courseId 课程id
 * 如果页面访问是pc浏览器访问跳转到官网浏览器  falg： false 跳到官网首页   ture 跳到课程页面
 *
 */
function h5PcConversions(falg, courserId) {
    var browser = {
        versions: function () {
            var u = navigator.userAgent, app = navigator.appVersion;
            return {//移动终端浏览器版本信息
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
                weixin: u.indexOf('MicroMessenger') > -1, //是否微信
                qq: u.match(/\sQQ/i) == " qq" //是否QQ
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    };
    if (!(browser.versions.mobile || browser.versions.ios || browser.versions.android ||
            browser.versions.iPhone || browser.versions.iPad)) {

        var front_area = getServerHost();
        // 如果是微信浏览器--就保持手机的状态
        if(is_weixin()){
            return true;
        }

        if (falg) {//ture
            location.href = front_area + "/courses/" + courserId + "/info"
        } else {
            location.href = front_area;
        }
        return false;
    } else {
        return true;
    }
}

/**
 *  如果页面来自课程的访问就不跳到官网
 *    所有的课程页面--》
 */
if (current.indexOf("course_id") != -1 ||
    current.indexOf("my_study") != -1) {
    //这里都表示课程id
    var courseId = getQueryString("course_id");
    if (isNotBlank(courseId)) {
        h5PcConversions(true, courseId);
    } else {
        var my_study = getQueryString("my_study");
        if (isNotBlank(my_study)) {
            h5PcConversions(true, my_study);
        } else {
            h5PcConversions(false);
        }
    }
} else {
    //跳转到课程页面
    h5PcConversions(false);
}

function requestGetService(url, param, callback, ac) {
    ajaxRequest(url, param,"get", callback, ac);
}

function requestPostService(url, param, callback, ac) {
    ajaxRequest(url, param, "post", callback, ac);
}
function requestPostJsonService(url, param, callback, ac) {
    ajaxRequest(url, param, "post", callback, ac,"application/json");
}

//ajax统一请求
function requestService(url, param, callback, ac) {
    ajaxRequest(url, param,"post", callback, ac);
}


//ajax统一请求
function ajaxRequest(url, param,type, callback, ac, contentType) {
    if(contentType == null){
        contentType = "application/x-www-form-urlencoded";
    }
    if (ac == null)
        ac = true;// 默认异步
    if(document.location.host.indexOf('dev.ixincheng.com')!=-1){
       // url = "/apis"+url;
    }
    var appToken = localStorage.getItem(APP_TOKEN);
    var appUniqueId = localStorage.getItem(APP_AppUniqueId);
    if(appToken!=null && appUniqueId!=null){
        var wv = localStorage.getItem(wv);
        if(wv=="ios"){
            wv = 4;
        }else{
            wv = 3;
        }
        headers = {token:appToken,appUniqueId:appUniqueId,clientType:wv};
    }
//  alert(JSON.stringify(headers))
    $.ajax({
        url: url,
        type: type,
        data: param,
        headers: headers,
        contentType: contentType,
        async: ac,
        success: function (msg) {
            var rd = getCurrentRelativeUrl();
            if (msg.code == USER_UN_LOGIN || msg.code == USER_TOKEN_NULL) {
                localStorage.setItem("rd", rd);
                location.href = "/xcview/html/cn_login.html";
//              location.href = "/xcview/html/enter.html";
            } else if (msg.code == USER_TOP) { //被同一用户顶掉了
                location.href = "/xcview/html/common.html";
            } else if (msg.code == USER_UN_BIND) { //去完善信息页面
                localStorage.setItem("rd", rd);
                location.href = "/xcview/html/evpi.html";
            } else if (msg.code == USER_WEIXIN_AUTH) {
                location.href = "/xczh/wxlogin/middle?url=" + getCurrentUrl();
            }else {
                if (callback) {
                    callback(msg);
                }
            }
        },
        error: function (msg) {
            // alert(msg);
        }
    });
}

//点击学习判断游客
function go_study() {
    location.href = "/xcview/html/my_study.html";
}

/**
 * 现在的入口有两个呢，一个是
 */
//function isLoginJump() {
//
//    var userId = localStorage.userId;
//    if (isNotBlank(userId)) {
//        /*
//         * 判断这上个地址是否来自我们的浏览器啦。如果是的就返回上一页，如果不是的话，那么就返回首页吧。
//         */
//        var before_address = document.referrer;
//        if (before_address.indexOf("page/index") != -1 ||
//            before_address.indexOf("bought.html") != -1 ||   //购买
//            before_address.indexOf("home_page.html") != -1 ||   //学堂
//            before_address.indexOf("my_study.html") != -1 ||   //学习
//            before_address.indexOf("live_play.html") != -1 ||   //直播展示页后面播放页
//            before_address.indexOf("/xcview/html/physician/physicians_page.html") != -1 ||   //医师页面
//            before_address.indexOf("live_personal.html") != -1) {  //主播页
//
//            window.history.back();
//        } else {
//            location.href = "home_page.html";
//        }
//    } else {
//        //登录页面
//        location.href = "home_page.html";
//        
//    }
//}

function common_share_back(url) {
	
	
	if(url != null){
    
		location.href = url;
    
    }else{
        var back = document.referrer;
	    var shareBack = getQueryString("shareBack");
	    
	    
	    if(isNotBlank(shareBack)){
	        if (isNotBlank(back) && back.indexOf("wx_share.html") == -1) {
	            window.history.back();
	        } else {
	            window.location.href = "/xcview/html/physician/index.html";
	        }
	    }else{
	        window.history.back();
	    }
    }
	
    
}

function common_share_backs(url) {
	
	if(url != null){
		
		location.href = url;
	}else{
		if(localStorage.getItem("wv") == null){
	        var back = document.referrer;
		   	var shareBack = getQueryString("shareBack");
		   	/*alert(back);
		   	alert(shareBack);
		   	alert(111);*/
		    if(isNotBlank(shareBack)){
		        if (isNotBlank(back) && back.indexOf("wx_share.html") == -1) {
		            window.history.back();
		        } else {
		            window.location.href = "/xcview/html/physician/index.html";
		        }
		    }else{
		        window.history.back();
		    }
	    }else{
//	    	alert(location.href);
	    	var back = document.referrer;
		   	var shareBack = getQueryString("shareBack");
		   	/*alert(back);
		   	alert(shareBack);*/
		    if(isNotBlank(shareBack)){
		        if (isNotBlank(back) && back.indexOf("wx_share.html") == -1) {
		            window.history.back();
		        } else {
		            window.location.href = "/xcview/html/physician/index.html";
		        }
		    }else{
		        window.history.back();
		    }
	    }
		
		/*var back = document.referrer;
	   	var shareBack = getQueryString("shareBack");
	   	
	    if(isNotBlank(shareBack)){
	        if (isNotBlank(back) && back.indexOf("wx_share.html") == -1) {
	            window.history.back();
	        } else {
	            window.location.href = "/xcview/html/physician/index.html";
	        }
	    }else{
	        window.history.back();
	    }*/
	}
}


/**
 * 公共点击事件
 */

/**************************************  直播课程点击事件,因为只有直播有预约  *************************88**************************/

// 获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); // 匹配目标参数
    // if (r != null) return unescape(r[2]);
    if (r != null)
        return decodeURI(r[2]);
    return null; // 返回参数值
}


//获取url中的参数，并返回一个对象
function getUrlParamsReturnJson() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
        }
    }
    return theRequest;
};

/*
 * 传递一个json返回对象瓶装为ur请求地址，待参数的
 */
function createUrl(obj) {
    var length = obj && obj.length,
        idx = 0,
        url = obj.url + '?';
    for (var key in obj) {
        if (key != 'url' && obj[key] !== null) {
            url += (key + '=' + encodeURIComponent(obj[key]) + '&');
        }
    }
    return url.substring(0, url.lastIndexOf('&'));
}

// 钱的转换
function money(pay) {
    return pay / 100;
}

// 逗号分隔
function imgsubstr(imgurl) {
    var strurl = imgurl;
    var attrurl = strurl.split(",");
    return attrurl;
}

/*
 * 判断字段空值   如果值等于null 或者 undefined 或者 'undefined' 或者 "" 统一返回 ""
 *          如果不等于null时，返回真实的值
 */
function returnstring(zifu) {
    if (zifu == "" || zifu == null || zifu == undefined || zifu == "undefined"
        || zifu == "null") {
        return "";
    }
    return zifu;
}

String.prototype.stripHTML = function () {
    var reTag = /<(?:.|\s)*?>/g;
    return this.replace(reTag, "");
}

function stripHTML(str) {
    var reTag = /<(?:.|\s)*?>/g;
    return str.replace(reTag, "");
}

function dataConverStr(startTime) {

    var date = new Date(startTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    var minute = date.getMinutes();
    minute = minute < 10 ? ('0' + minute) : minute;
    return y + "." + m + "." + d + "  " + h + ":" + minute;
}

function timestampConverStr(startTime) {

    var timestamp2 = Date.parse(new Date(stringTime));

    var date = new Date(startTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    var minute = date.getMinutes();
    minute = minute < 10 ? ('0' + minute) : minute;

    return y + "." + m + "." + d + "  " + h + ":" + minute;
}

/**
 * 公共的用localStorage存储用户信息接口
 *
 * @param data
 */
function commonLocalStorageSetItem(data) {
    var configresult = data.resultObject;
    localStorage.setItem("token", JSON.stringify(configresult));
    localStorage.setItem("userId", configresult.id)
    localStorage.setItem("name", configresult.name);

    localStorage.setItem("smallHeadPhoto", configresult.smallHeadPhoto);
    localStorage.setItem("sex", configresult.sex);

    localStorage.setItem("province", configresult.province);
    localStorage.setItem("city", configresult.city);
    localStorage.setItem("district", configresult.district);


    localStorage.setItem("provinceName", configresult.provinceName);
    localStorage.setItem("cityName", configresult.cityName);
    localStorage.setItem("countyName", configresult.countyName);

    localStorage.setItem("email", configresult.email);
    localStorage.setItem("info", configresult.info);
    localStorage.setItem("username", configresult.loginName);


    localStorage.setItem("occupation", configresult.occupation);
    localStorage.setItem("occupationOther", configresult.occupationOther);
    localStorage.setItem("occupationText", configresult.occupationText);
}

/**
 * 公共的移除 localStorage 和cookie 的信息
 *
 * @param data
 */
function commonLocalStorageRemoveItem(data) {

    //删除所有的信息
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("name");
    localStorage.removeItem("smallHeadPhoto");
    localStorage.removeItem("sex");

    localStorage.removeItem("provinceName");
    localStorage.removeItem("cityName");
    localStorage.removeItem("province");

    localStorage.removeItem("province");
    localStorage.removeItem("city");
    localStorage.removeItem("district");

    localStorage.removeItem("city");
    localStorage.removeItem("email");
    localStorage.removeItem("info");
    localStorage.removeItem("username");

    localStorage.removeItem("occupation");
    localStorage.removeItem("occupationOther");
    localStorage.removeItem("occupationText");

    //localStorage.setItem("openid", openId);
    localStorage.removeItem("openid");

    //删除  登录时用到的cookie
    cookie.delete1("_ipandatcm_user_");
    //删除  第三方登录时用到的cookie
    cookie.delete1("_third_ipandatcm_user_");
}


/** * 对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
 可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
 Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.pattern = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

var date = new Date();
//window.alert(date.pattern("yyyy-MM-dd hh:mm:ss"));


/**
 * 控制cookie
 */
var cookie = {

    set: function (key, val, time) {//设置cookie方法
        var date = new Date(); //获取当前时间
        var expiresDays = time;  //将date设置为n天以后的时间
        date.setTime(date.getTime() + expiresDays * 24 * 3600 * 1000); //格式化为cookie识别的时间
        document.cookie = key + "=" + val + ";expires=" + date.toGMTString() + ";path=/";  //设置cookie
    },

    get: function (key) {//获取cookie方法
        /*获取cookie参数*/
        var getCookie = document.cookie.replace(/[ ]/g, "");  //获取cookie，并且将获得的cookie格式化，去掉空格字符
        var arrCookie = getCookie.split(";")  //将获得的cookie以"分号"为标识 将cookie保存到arrCookie的数组中
        var tips;  //声明变量tips
        for (var i = 0; i < arrCookie.length; i++) {   //使用for循环查找cookie中的tips变量
            var arr = arrCookie[i].split("=");   //将单条cookie用"等号"为标识，将单条cookie保存为arr数组
            if (key == arr[0]) {  //匹配变量名称，其中arr[0]是指的cookie名称，如果该条变量为tips则执行判断语句中的赋值操作
                tips = arr[1];   //将cookie的值赋给变量tips
                return tips;
                break;   //终止for循环遍历
            }
        }
    },
    delete1: function (key) { //删除cookie方法
        var date = new Date(); //获取当前时间
        date.setTime(date.getTime() - 10000); //将date设置为过去的时间
        document.cookie = key + "=;expires =" + date.toGMTString() + ";path=/";//设置cookie
    }
};

function getFlagStatus() {
    var falg = USER_NORMAL;
    var user_cookie = cookie.get("_ipandatcm_user_");
    var third_party_cookie = cookie.get("_third_ipandatcm_user_");
    if (isBlank(user_cookie)) {
        falg = USER_UN_LOGIN;
        if (isNotBlank(third_party_cookie)) {
            falg = USER_UN_BIND;
        }
    }
    return falg;
}

/**
 * 用于h5校验用户的登陆状态
 * @param courseId
 * @param type
 */
function checkAuth(courseId, type) {
    var flag = getFlagStatus();
    if (flag === USER_UN_LOGIN) {
        if (courseId && type) {
            location.href = "/xcview/html/cn_login.html?course_id=" + courseId + "&type=" + type;
        } else {
            location.href = "/xcview/html/cn_login.html";
        }
    }
    if (flag === USER_UN_BIND) {
        location.href = "/xcview/html/evpi.html";
    }
}

//关闭浏览器后，又一次打开浏览器了
var firstEntry = false;
var session = sessionStorage.getItem("session");
if(session==null || session ==undefined || session ==""){
    sessionStorage.setItem("session","session");
    var shareBack = getQueryString("shareBack");
    if(shareBack==null || shareBack ==undefined || shareBack ==""){
        firstEntry = true;
    }
}

//是否存在微信信息
var thirdPartyUCT = cookie.get("_third_ipandatcm_user_");

if ((is_weixin()) &&  (!thirdPartyUCT  || firstEntry)) {//在微信里打开,没有授权时，先去微信授权
    
	localStorage.setItem("userId","");
	
    location.href = "/xczh/wxlogin/middle?url=" + getCurrentUrl();
    
}

function locationToOriginPage() {
    var rd = localStorage.getItem("rd");
    localStorage.setItem("rd", null);
    if (rd) {
        window.location.href = rd;
    } else {
        window.location.href = "/xcview/html/home_page.html";
    }
}


/**
 * 这个方法保证用户id一直存在
 */
var userId = localStorage.getItem("userId");
if (isBlank(userId)) {
    var user_cookie = cookie.get("_ipandatcm_user_");
    if (isNotBlank(user_cookie)) {//说明已经登录了
        /* 如果是微信公众号进入页面时，没有给他返回token。所以这里他在请求下呢  */
        var ccontrollerAddress = "/xczh/set/isLogined";
        requestService(ccontrollerAddress, null, function (data) {
            if (data.success) {
                commonLocalStorageSetItem(data);
            } else {
                // alert("网络异常");
            }
        }, false)
    }
}

/**
 * 公共的分享页面后的跳转
 */

function gotoLiveSelectAlbum() {

    //window.location="/xcview/html/live_select_album.html?course_id="+collectionId;
    var back = document.referrer;
    if (isNotBlank(back) && back.indexOf("wx_share.html") == -1) {
        window.history.back();
    } else {
        
        
        location.replace("/xcview/html/live_select_album.html?course_id=" + collectionId);
    }


    // location.replace("/xcview/html/live_select_album.html?course_id="+collectionId);
}



/*
** randomWord 产生任意长度随机字母数字组合
** randomFlag-是否任意长度 min-任意长度最小位[固定位数] max-任意长度最大位
** xuanfeng 2014-08-28
*/

function randomWord(randomFlag, min, max) {
    var str = "",
        range = min,
        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
    // 随机产生
    if (randomFlag) {
        range = Math.round(Math.random() * (max - min)) + min;
    }
    for (var i = 0; i < range; i++) {
        pos = Math.round(Math.random() * (arr.length - 1));
        str += arr[pos];
    }
    return str;
}


/**
 *  h5  ---> 百度统计
 */
var _hmt = _hmt || [];
//(function () {
//    var hm = document.createElement("script");
//    hm.src = "https://hm.baidu.com/hm.js?9edcc82ab19a52e4f6e3f13050141a70";
//    var s = document.getElementsByTagName("script")[0];
//    s.parentNode.insertBefore(hm, s);
//})();


// 主播链接错误时用到的默认头像
function nofind(){ 
    var img=event.srcElement; 
    img.src="/xcview/images/regards.png"; 
    img.onerror=null; //控制不要一直跳动 
} 


//用户链接错误时用到的头像
function nofinds(){ 
    var img=event.srcElement; 
    img.src="/xcview/images/default_pic.png"; 
    img.onerror=null; //控制不要一直跳动 
} 

// onerror="nofinds();"


var getParam = function(name) {
    var search = document.location.search;
    //alert(search);
    var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    var matcher = pattern.exec(search);
    var items = null;
    if(null != matcher) {
        try {
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        } catch(e) {
            try {
                items = decodeURIComponent(matcher[1]);
            } catch(e) {
                items = matcher[1];
            }
        }
    }
    return items;
};

var goHistory = function(url){
    if(url != null){
        location.href = url;
    }else{
        history.go(-1);
    }
}