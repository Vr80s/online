(function(){
    //解析url地址
    var ourl = document.location.search;
    var browser={
        versions:function(){
            var u = navigator.userAgent, app = navigator.appVersion;
            return {//移动终端浏览器版本信息
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
                weixin: u.indexOf('MicroMessenger') > -1, //是否微信
                qq: u.match(/\sQQ/i) == " qq" //是否QQ
            };
        }(),
        language:(navigator.browserLanguage || navigator.language).toLowerCase()
    }
    if(document.location.host=='www.ixincheng.com'){
        window.location = "http://www.ipandatcm.com";
    }else if(browser.versions.mobile || browser.versions.ios || browser.versions.android ||
        browser.versions.iPhone || browser.versions.iPad){

        if(document.location.host=='www.ipandatcm.com' ||document.location.host=='www.ixincheng.com' || document.location.host=='ipandatcm.com' ||document.location.host=='ixincheng.com'){
            wxurl = "http://m.ipandatcm.com";
        }else{
            wxurl = "http://test-wx.ixincheng.com";
        }
        window.location = wxurl;
    }
})();
//医师或医馆入口是否展示
function showDOrH(){
    //请求判断顶部是否具有我是医师、医馆的入口
    RequestService("/medical/common/isDoctorOrHospital","GET",null,function(data){
        if(data.success == true){
            //判断
            localStorage.AccountStatus = data.resultObject;
            if(data.resultObject == 1 ){
                //医师认证成功
                $('#docOrHos').text('我是医师');
                $('#docOrHos').attr('href','/web/html/anchors_resources.html')
                $('#docOrHos').removeClass('hide');
            }else if(data.resultObject == 2 ){
                //医馆认证成功
                $('#docOrHos').text('我是医馆');
                $('#docOrHos').attr('href','/web/html/ResidentHospital.html')
                $('#docOrHos').removeClass('hide');
            }
            showAnchorWorkbench();
        }else if(data.success == false && data.errorMessage == "请登录！" ){
            $('#docOrHos').addClass('hide');
        }
    });
}
function showAnchorWorkbench(){
    RequestService("/anchor/info/hasPower","GET",null,function(data){
        if(data.success == true){
            $('#anchorWorkbench').removeClass('hide');
        }else{
            $('#anchorWorkbench').addClass('hide');
        }
    });
}

var bbs_domain = 'http://bbs.ipandatcm.com';
var domain = document.domain;
if(domain.indexOf("dev")!=-1){//开发环境
	bbs_domain = 'http://dev.ixincheng.com:8082';
}else if(domain.indexOf("test")!=-1){//测试环境
    bbs_domain = 'http://test-bbs.ixincheng.com';
}else{//生产环境
    bbs_domain = 'http://bbs.ipandatcm.com';
}
//如果有链接的点击事件
function on_click_msg(msg_id, msg_link) {
    var $this=$(this);
    RequestService("/online/message/updateReadStatusById", "post", {
        id: msg_id
    }, function(data) {
        if(data.success == true) {
            window.open(msg_link,"_blank");
        }
    }, false);
};



$(function () {
    /**
     * Created by admin on 2016/9/14.
     */
    //==========================================================================================
    var headersIndex={
    	navtop:'<div class="head-top"><div class="content"><ul><li class="first-li"><a href="/web/html/aboutUs.html">关于我们</a></li>'+

    	'<li>'+
    	'<div class="loginGroup">'+
        ' <div class="login" style="display:none;">'+
        '<div class="dropdown" id="myDropdown">'+
        '<div class="userPic"></div>'+
        '<div id="dLabel" data-target="#" role="button" aria-haspopup="true">'+
        '<span class="name"></span>'+
        '<span class="caret"></span>'+
        ' </div>'+
        '<ul class="dropdownmenu" aria-labelledby="dLabel">'+
        '<div class="pointer"></div>'+
        '<li><a data-id="mydata"><i class="iconfont icon-xueyuan"></i>我的资料</a></li>'+
        '<li><a data-id="idea"><i class="iconfont icon-yijianfankui"></i>意见反馈</a></li>'+
        '<li><a data-id="mymoney"><i class="iconfont icon-qianbao"></i>我的资产</a></li>'+
        '<li><a href="'+bbs_domain+'/myReply"  data-id="mytiezi"><i class="iconfont icon-zaixiankecheng"></i>我的贴子</a></li>'+
        '<li><a data-exit="exit"><i class="iconfont icon-tuichu"></i>安全退出</a></li>'+
      
        ' </ul></div></div>'+
        '<div class="logout" style="display:none;">'+
        '<a class="btn-login btn-link" data-toggle="modal" data-target="#login" data-backdrop="static">登录</a>'+
        '<span>|</span>'+
        '<a class="btn-register btn-link" href="/web/html/login.html?ways=register">注册</a>'+
        '</div>'+
    	'</li>'+
    	

    	'<li><a href="/App.html" class="appDown">APP下载</a></li>'+
    	
    	'<li>'+

    	'</li>'+
    	'<li><div class="messageBox"><a href="javascript:;" data-id="mynews" class="message">消息</a><span class="messageCount" style="display: none;"><em style="background-color: #F97B49;height:20px;padding: 2px 6px;border-radius: 10px 10px 10px 10px;position: absolute;color:white;font-style:normal"></em></span></div></li><li><a href="javascript:;" class="studentCenterBox">学习中心</a></li><li><a href="javascript:;" class="hide" id="docOrHos">我是医师</a></li><li><a href="/web/html/anchor/curriculum.html" class="hide" id="anchorWorkbench">主播工作台</a></li>'+
    	'</ul></div></div>'
    };
    //<!--网站公告-->
    var webNotice='<div class="webSiteNotice" style="display:none;">'+
            '<div class="innerBox clearfix">'+
            '<i class="iconfont icon-xiaoxilaba xiaoxilaba"></i><span class="noticeInfo"></span><i class="iconfont icon-guanbi noticeClose"></i>'+
            '</div></div>';
    var slideNavIndex={
        /* 咨询中心*/
        consult_center:'<div class="consult_center">咨询中心</div>',
        //<!--在线咨询和电话咨询-->
        online_consult:'<div class="online_consult">'+
        '<a href="http://p.qiao.baidu.com/cps/chat?siteId=11043846&userId=24331702" target="_blank">'+
        '<img src="/web/images/zixun.gif" alt=""/><span>在线咨询</span>'+
        '</a></div>',
        phone_consult:'<div class="phone_consult">'+
        '<div class="phone_consult_box"><img src="/web/images/tel.gif" alt=""/><span class="dianhuazixun">电话咨询</span></div>' +
        '<span class="phone_number">0898-32881833</span>'+
        ' </div>',
        weixin:'<div class="sideWeixinBox">'+
            '<div class="sideWeixin">'+
            '<img src="/web/images/sideWeixinErma.png" />'+
            '<p>关注微信</p>'+
            '</div>'+
        '</div>',
        weixinErma:'<div class="sideWeixinErma">'+
        '<img src="/web/images/sideWeixin.png" />'+
        '<div class="sideSanjiao">'+
        '<img src="/web/images/float_sanjiao.png" />'+
        '</div>'+
        '</div>',
        weibo:'<a class="sideWeiboBox" href="http://weibo.com/u/6329861207?refer_flag=1001030102_&amp;is_hot=1" target="_blank">' +
        '<div class="sideWeibo">'+
        '<img src="/web/images/sideWeiboErma.png" />'+
        '<p>关注微博</p>'+
        '</div>'+
        '</a>',
        weiboErma:'<div class="sideWeiboErma">'+
        '<img src="/web/images/sideWeibopng.png" />'+
        '<div class="sideSanjiao1">'+
        '<img src="/web/images/float_sanjiao.png" />'+
        '</div>'+
        '</div>',
        //<!--返回顶部-->
        h_top:'<div class="h_top" onclick="pageScroll();">' +
        '<span class="wrap">'+
        '<img src="/web/images/top.png" alt=""/><span class="h_top_s">top</span>'+
        '</span></div>'
    }

    var header=$('header');
    $(".header_body").before(template.compile(headersIndex.navtop));//首页头部顶部


    if(home) {
    	$("body").children().eq(1).before(header);
    } else {
    	$("body").children(":first").before(header);
    }
    // $(header).append(template.compile(headersIndex.login));
    $(header).before(template.compile(webNotice));
    $(header).append(template.compile(slideNavIndex.consult_center));
    $(header).append(template.compile(slideNavIndex.online_consult));
    $(header).append(template.compile(slideNavIndex.phone_consult));
    $(header).append(template.compile(slideNavIndex.weixin));
    $(header).append(template.compile(slideNavIndex.weibo));
    $(header).append(template.compile(slideNavIndex.weixinErma));
    $(header).append(template.compile(slideNavIndex.weiboErma));
    $(header).append(template.compile(slideNavIndex.h_top));


    $('.phone_consult').mouseover(function(){
		$(this).stop().animate({'width':'200px'})
	})
	$('.phone_consult').mouseout(function(){
		$(this).stop().animate({'width':'57px'})
	})
//==========================================================================================
    //侧边栏微信，微博
    $(".sideWeixinBox").hover(function(){
        $(".sideWeixinErma").css("display","block");
        $(".sideWeixinBox").css("backgroundColor","#eef3f8");
    },function(){
        $(".sideWeixinErma").css("display","none");
        $(".sideWeixinBox").css("backgroundColor","#fff");
    });
    $(".sideWeiboBox").hover(function(){
        $(".sideWeiboErma").css("display","block");
        $(".sideWeiboBox").css("backgroundColor","#eef3f8");
    },function(){
        $(".sideWeiboErma").css("display","none");
        $(".sideWeiboBox").css("backgroundColor","#fff");
    });
    //网站公告
    RequestService("/online/message/findNewestNotice","GET",null,function(data){
        if(data.resultObject!=null && data.resultObject!=""){
            if(data.resultObject==null){
                $(".webSiteNotice").css("display","none");
            }else{
                $(".webSiteNotice .noticeInfo").html(data.resultObject.notice_content);
                if(data.resultObject.infoType==1){
                    $(".noticeClose").css("display","none");
                }else{
                    $(".webSiteNotice a").attr("target","_blank");
                }
                if(window.localStorage.dontsee ==data.resultObject.id){
                    $(".webSiteNotice").css("display","none");
                }else{
                    $(".webSiteNotice").css("display","block");
                    $(".webSiteNotice .noticeClose").click(function(){
                        window.localStorage.dontsee = data.resultObject.id;
                        $(".webSiteNotice").css("display","none");
                    });
                }
            }
        }
    });
    //学习中心
    $(".studentCenterBox").click(function(){
        RequestService("/online/user/isAlive","GET",null,function(data){
            if(!data.success){
                localStorage.myStudyCenter="1";
                $('#login').modal('show');
            }else{
                window.location.href="/web/html/myStudyCenter.html";
            }
        })
    });
    //购物车
    $(".shoppingCar").click(function(){
        RequestService("/online/user/isAlive", "GET", null, function (data) {
            if(data.success==false){
                $("#login").modal("show");
            }else{
                window.location.href="/web/html/shoppingCart.html";
            }
        })
    });
    //消息提醒
    $(".message").click(function(){
        RequestService("/online/user/isAlive", "GET", null, function (data) {
            if(data.success==false){
                $("#login").modal("show");
            }else{
                window.localStorage.personcenter = $(this).attr("data-id");
                window.location.href="/web/html/personcenter.html";
            }
        })
    });



//==========================================================================================


    $(".cyinput1").on("input", function () {
        var val = $(this).val();
        if (val == "") {
            $(".cymyloginclose1").css("display", "none");
        } else {
            $(".cymyloginclose1").css("display", "block");
        }
        return false;
    });
    $(".cyinput2").on("input", function () {
        var logPass = $(this).val();
        if (logPass == "") {
            $(".cymyloginclose2").css("display", "none");
        } else {
            $(".cymyloginclose2").css("display", "block");
        }
        return false;
    });
    /*个人中心点击立即登录后，当前用户退出登录*/
    $(".pLogin").on("click", function () {
        $(".loginGroup .logout").css("display", "block");
        $(".loginGroup .login").css("display", "none");
    });

    var flag = false;
    function errorMessage(info) {
        flag = false;
        var errorReg = /[a-zA-z]+/g;
        if (errorReg.test(info)) {
            layer.alert("系统繁忙，请稍候再试!", {icon: 2});
            return flag = true;
        }
    }

    /*登录注册字体颜色和箭头方向的改变*/
    $("#myDropdown").hover(function () {
        $("#myDropdown").addClass("open");
    }, function () {
        $("#myDropdown").removeClass("open");
    });
    initLogin();
    /*按回车键进行登录*/
    $(".cymylogin .cyinput2,.cymylogin .cyinput1").bind("keyup", function (evt) {
        if (evt.keyCode == "13") {
            $(".cymylogin .cymyloginbutton").trigger("click");
        }
    });
    function initLogin() {
        //清空登录
        var cymyLogin = document.getElementsByClassName("cymlogin")[0];
        $("#login").on('shown.bs.modal', function () {
            $(".cymylogin-bottom input").css("border","");
            cymyLogin.style.display = "none";
        });
        RequestService("/online/user/isAlive", "GET", null, function (data) {///online/user/isAlive
            if (data.success === true) {
                var path;
                localStorage.username = data.loginName;
                //头像预览
                if (data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
                    path = data.resultObject.smallHeadPhoto;
                } else {
                    path = bath + data.resultObject.smallHeadPhoto
                }
                $(".userPic").css({
                    background: "url(" + path + ") no-repeat",
                    backgroundSize: "100% 100%"
                });
                $('#login').css("display", "none");
                $(".loginGroup .logout").css("display", "none");
                $(".loginGroup .login").css("display", "block");
                $(".dropdown .name").text(data.resultObject.name).attr("title", data.resultObject.name);
                //20180205主播信息
                $(".nickname").text(data.resultObject.name).attr("title", data.resultObject.name);
                $(".anchor_info").show();

                //首页未读消息总数
                RequestService("/online/message/findMessageCount","GET",null,function(data){
                    if(data.success==true && data.resultObject.count!=0){
                        $(".messageCount").css("display","block");
                        if(data.resultObject.count<=99){
                            $(".messageCount em").text(data.resultObject.count);
                        }else{
                            $(".messageCount em").text(99);
                        }
                    }
                });
                //首页购物车数量
                RequestService("/shoppingCart/findCourseNum","GET",null,function(data){
                    if(data.success==true && data.resultObject!=0){
                        $(".shopping").css("display","block");
                        if(data.resultObject<=99){
                            $(".shopping em").text(data.resultObject);
                        }else{
                            $(".shopping em").text(99);
                        }
                    }
                });
                showDOrH();
            }else {
                $('#login').css("display", "none");
                $(".loginGroup .logout").css("display", "block");
                $(".loginGroup .login").css("display", "none");
            }
        });
        $(".form-login .cymyloginclose1").on("click", function () {
            $(".cymyloginclose1").css("display","none");
            $(".cyinput1").css({"border":"1px solid #2cb82c"});
            $(".cyinput1").val("");
        });
        $(".form-login .cymyloginclose2").on("click",function(){
            $(".cymyloginclose2").css("display","none");
            $(".cyinput2").css({"border":"1px solid #2cb82c"});
            $(".cyinput2").val("");
        });
        var isCliclLogin = false;
        $(".form-login .cyinput1").focus(function () {
            if (cymyLogin.innerText == "请输入手机号" || cymyLogin.innerText == "手机号格式不正确!" || cymyLogin.innerText == "邮箱格式不正确!" || cymyLogin.innerText=="用户名或密码错误") {
                cymyLogin.style.display="none";
            }
            $(".cyinput1").css("border","1px solid #2cb82c");
        });

        $(".form-login .cyinput1").blur(function () {
            var cymyLogin = document.getElementsByClassName("cymlogin")[0];
            var regPhone = /^1[3-5678]\d{9}$/;
            /*var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;*/
            if ($(".form-login .cyinput1").val().trim().length === 0) {
                $(".cyinput1").css("border", "1px solid #ff4012");
                cymyLogin.innerText = "请输入手机号";
                cymyLogin.style.top = "154px";
                cymyLogin.style.display = "block";
            } else if (!(regPhone.test($(".form-login .cyinput1").val().trim()))) {
/*            } else if ($(".form-login .cyinput1").val().trim().indexOf("@") == "-1" && !(regPhone.test($(".form-login .cyinput1").val().trim()))) {
*/                $(".cyinput1").css("border", "1px solid #ff4012");
                cymyLogin.innerText = "手机号格式不正确!";
                cymyLogin.style.top = "154px";
                cymyLogin.style.display = "block";
            }/* else if ($(".form-login .cyinput1").val().trim().indexOf("@") != "-1" && !(regEmail.test($(".form-login .cyinput1").val().trim()))) {
                $(".cyinput1").css("border", "1px solid #ff4012");
                cymyLogin.innerText = "邮箱格式不正确!";
                cymyLogin.style.top = "154px";
                cymyLogin.style.display = "block";
            }*/else{
                $(".cyinput1").css("border", "");
            }
        });
        $(".form-login .cyinput2").focus(function () {
            if (cymyLogin.innerText == "请输入6-18位密码") {
                cymyLogin.style.display="none";
            }
            $(".cyinput2").css("border", "1px solid #2cb82c");
        });
        $(".form-login .cyinput2").blur(function () {
            var cymyLogin = document.getElementsByClassName("cymlogin")[0];
            var cyinput2Length=$(".form-login .cyinput2").val().trim().length;
            if(cyinput2Length==0){
                $(".cyinput2").css("border", "1px solid #ff4012");
                $(".cymlogin").css("top", "221px");
                cymyLogin.innerText = "请输入6-18位密码";
                cymyLogin.style.display = "block";
            }else if (cyinput2Length<6&&cyinput2Length>18) {
                $(".cyinput2").css("border", "1px solid #ff4012");
                $(".cymlogin").css("top", "221px");
                cymyLogin.innerText = "请输入6-18位密码";
                cymyLogin.style.display = "block";
            }else{
                $(".cyinput2").css("border"," ");
            }
        });
        $(".form-login .cyinput2").focus().blur();
        $(".form-login .cymyloginbutton").click(function (evt) { //登录验证
            var regPhone = /^1[3-5678]\d{9}$/;
            /*var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w{2,})*\.\w{2,}([-.]\w{2,})*$/;*/
            var passwordReg = /^(?!\s+)[\w\W]{6,18}$/;//密码格式验证
            $(".cyinput1").css("border", "");
            $(".cyinput2").css("border", "");
            var data = {
                username: $(".form-login .cyinput1").val().trim(),
                password: $(".form-login .cyinput2").val()
            };
            if (data.username.length === 0) {
                $(".cyinput1").css("border", "1px solid #ff4012");
                cymyLogin.innerText = "请输入手机号";
                cymyLogin.style.top = "154px";
                cymyLogin.style.display = "block";
                return;
            } else if (!(regPhone.test(data.username))) {
/*            } else if (data.username.indexOf("@") == "-1" && !(regPhone.test(data.username))) {
*/                $(".cyinput1").css("border", "1px solid #ff4012");
                cymyLogin.innerText = "手机号格式不正确!";
                cymyLogin.style.top = "154px";
                cymyLogin.style.display = "block";
                return;
            }/* else if (data.username.indexOf("@") != "-1" && !(regEmail.test(data.username))) {
                $(".cyinput1").css("border", "1px solid #ff4012");
                cymyLogin.innerText = "邮箱格式不正确!";
                cymyLogin.style.top = "154px";
                cymyLogin.style.display = "block";
                return;
            } */else if (data.password.length === 0) {
                $(".cyinput2").css("border", "1px solid #ff4012");
                $(".cyinput2").val("");
                $(".cymlogin").css("top", "221px");
                cymyLogin.innerText = "请输入6-18位密码";
                cymyLogin.style.display = "block";
                return;
            }
            isCliclLogin = true;
            
            login(data);
        });

        function login(data, autoLogin) {
            RequestService("/online/user/login", "POST", data, function (result) { //登录/index.html   /online/user/login
                if (result.success === true || result.success == undefined) {
                
                	
                    window.localStorage.userName=data.username;
                    window.location.reload();
                    var myStudent=window.localStorage.myStudyCenter;
                    
                    if(myStudent==1){
                        window.location.href="/web/html/myStudyCenter.html";
                        window.localStorage.myStudyCenter=null;
                    }
                    /*
                     * 获取当前页面
                     */
                    var current = location.href;
//                  debugger;
                    if(current.indexOf("otherDevice.html")!=-1){
                    	
                    	window.location.href="/index.html";
                    }
                    
                } else { //登录错误提示
                    $(".loginGroup .logout").css("display", "block");
                    errorMessage(result.errorMessage);
                    if (!flag) {
                        if (result.errorMessage == "用户名密码错误！") {
                            cymyLogin.innerText = "用户名或密码不正确!";
                            
                            cymyLogin.style.display = "block";
                            $(".cymlogin").css("top", " 221px");
                        } else {
                            cymyLogin.innerText = result.errorMessage;
                            $(".cymlogin").css("top", " 221px");
                            cymyLogin.style.display = "block";
                        }
                    }

                }
            });
        }
        
        $(".dropdownmenu li a").click(function (evt) {
        	
        	
            $(".top-item").removeClass("selected");
            var btn = $(evt.target);
            var id = "personcenter";
            window.localStorage.personcenter = $(evt.target).attr("data-id");

            if($(evt.target).attr("data-id") == "mytiezi"){ //
            	
            	location.href = bbs_domain+"/myReply";
            }else{
                if (window.location.pathname == "/web/html/personcenter.html") {
                    if ($(this).attr("data-exit")) {
                        RequestService("/online/user/logout", "GET", {}, function () {
                            location.href = "/index.html";
                            $(".loginGroup .logout").css("display", "block");
                            $(".loginGroup .login").css("display", "none");
                        });
                    } else {
                        $(".left-nav ." + window.localStorage.personcenter).click();
                    }
                } else {
                    if ($(this).attr("data-exit")) {
                        RequestService("/online/user/logout", "GET", {}, function () {
                            location.href = "/index.html";
                            $(".loginGroup .logout").css("display", "block");
                            $(".loginGroup .login").css("display", "none");
                        });
                    } else {
                        location.href = "/web/html/personcenter.html";
                        RequestService("/online/user/isAlive", "GET", null, function (data) {///online/user/isAlive
                            if (data.success) {
                                if (data.resultObject.smallHeadPhoto != "/web/images/defaultHeadImg.jpg") {
                                    path = data.resultObject.smallHeadPhoto;
                                } else {
                                    path = bath + data.resultObject.smallHeadPhoto
                                }
                                //头像预览
                                $(".userPic").css({
                                    background: "url(" + path + ") no-repeat",
                                    backgroundSize: "100% 100%"
                                });
                                $('#login').modal('hide');
                                $("html").css({"overflow-x": "hidden", "overflow-y": "auto"});
                                $(".loginGroup .logout").hide();
                                $(".loginGroup .login").show();
                                $(".dropdown .name").text(data.resultObject.name).attr("title", data.resultObject.name);
                                localStorage.username = data.resultObject.loginName;
                                localStorage.userid = data.resultObject.id;
                                if ($(btn.parent().hasClass('selected'))) {

                                } else {
                                    hideHtml();
                                }
                            } else {
                            	location.href = "/webapp/otherDevice.html";
                                localStorage.username = null;
                                localStorage.password = null;
                                $(".login").css("display", "none");
                                $(".logout").css("display", "block");
                            }
                        });
                    }
                }
            }

        });
    }


});
