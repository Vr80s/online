<!DOCTYPE html>
<html lang="en">

<head>
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">

    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>熊猫中医云课堂 - 线上中医教育</title>
    <link rel="shortcut icon" href="favicon.ico"/>
    <meta name="keywords"
          content="中医教育,中医传承,中医线下教育，海口中医养生，国粹，传承，中医，中药，心承，熊猫中医"/>
    <meta name="description"
          content="熊猫中医云课堂为。课程大纲全新优化，内容有广度、有深度，顶尖讲师全程直播授课。专注整合优势教学资源、打造适合在线学习并能保证教学结果的优质教学产品，同时打造和运营一整套教育生态软件体系，为用户提供满足自身成长和发展要求的有效服务。"/>
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF"/>
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/index.css?v=ipandatcm_1.3"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>
    <link rel="stylesheet" href="/web/css/index-9a525196fb.css" type="text/css">

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/jquery.SuperSlide.2.1.1.js"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>


    <script>
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

        if(browser.versions.mobile || browser.versions.ios || browser.versions.android ||
                browser.versions.iPhone || browser.versions.iPad){

            if(document.location.host=='www.ipandatcm.com' ||document.location.host=='www.ixincheng.com'){
                wxurl = "http://m.ipandatcm.com";
            }else{
                wxurl = "http://test-wx.ixincheng.com";
            }
            window.location = wxurl;
        }else if(document.location.host=='www.ixincheng.com'){
            window.location = "";
        }
    </script>
</head>
<body>
<div class="popover_order">
    <div class="popover_order_bg"></div>
    <div class="order">
        <div class="order_close"><span style="font-size: 32px;position: absolute;right: 10px;">X</span></div>
        <div class="both"></div>
        <div class="order_size">请输入预约手机号</div>
        <div class="order_cell">
            <span>手机号</span>
            <input type="hidden" id="subscribeId">
            <input type="text" class="phone" placeholder="输入11位阿拉伯数字"
                   onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;if(event.keyCode==13) {btnValidate.click();return false;}">
            <div id="tips" style="top: 154px; display:none ;"><span
                    style="margin-left: 21%;font-size: 15px;color: red;"></span></div>
        </div>

        <input type="button" class="order_affirm" value="确认" id="btnValidate">
    </div>
</div>
<div class="webSiteNotice" style="display:none;">
    <div class="innerBox clearfix"><i class="iconfont icon-xiaoxilaba xiaoxilaba"></i><span class="noticeInfo"></span><i
            class="iconfont icon-guanbi noticeClose"></i></div>
</div>
<header>
    <div class="head-top">
        <div class="content">
            <ul>
                <li class="first-li"><a href="/web/html/aboutUs.html">关于我们</a></li>
                <li>
                    <div class="loginGroup">
                        <div class="login" style="display: block;">
                            <div class="dropdown" id="myDropdown">
                                <div class="userPic"
                                     style="background: url(&quot;http://attachment-center.ixincheng.com:38080/data/attachment/online/2018/03/16/18/a4b5a212841448209ddf4c179f5ca1c4.png&quot;) 0% 0% / 100% 100% no-repeat;"></div>
                                <div id="dLabel" data-target="#" role="button" aria-haspopup="true"><span class="name"
                                                                                                          title="熊猫中医">熊猫中医</span><span
                                        class="caret"></span></div>
                                <ul class="dropdownmenu" aria-labelledby="dLabel">
                                    <div class="pointer"></div>
                                    <li><a data-id="mydata"><i class="iconfont icon-xueyuan"></i>我的资料</a></li>
                                    <li><a data-id="idea"><i class="iconfont icon-yijianfankui"></i>意见反馈</a></li>
                                    <li><a data-id="mymoney"><i class="iconfont icon-qianbao"></i>我的资产</a></li>
                                    <li><a href="http://bbs.ipandatcm.com/myReply" data-id="mytiezi"><i
                                            class="iconfont icon-zaixiankecheng"></i>我的贴子</a></li>
                                    <li><a data-exit="exit"><i class="iconfont icon-tuichu"></i>安全退出</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="logout" style="display:none;"><a class="btn-login btn-link" data-toggle="modal"
                                                                     data-target="#login"
                                                                     data-backdrop="static">登录</a><span>|</span><a
                                class="btn-register btn-link"
                                href="/web/html/login.html?ways=register">注册</a></div>
                    </div>
                </li>
                <li><a href="/App.html" class="appDown">APP下载</a></li>
                <li></li>
                <li>
                    <div class="messageBox"><a href="javascript:;" data-id="mynews" class="message">消息</a><span
                            class="messageCount" style="display: none;"><em
                            style="background-color: #F97B49;height:20px;padding: 2px 6px;border-radius: 10px 10px 10px 10px;position: absolute;color:white;font-style:normal"></em></span>
                    </div>
                </li>
                <li><a href="javascript:;" class="studentCenterBox">学习中心</a></li>
                <li><a href="/web/html/anchors_resources.html" class="" id="docOrHos">我是医师</a>
                </li>
                <li><a href="/web/html/anchor/curriculum.html" class="" id="anchorWorkbench">主播工作台</a>
                </li>
            </ul>
        </div>
    </div>
    <#include "header-body.ftl">
</header>
<div class="index_body">
    <!--轮播图-->
    <div id="banner" class="clearfix">
        <div class="slider-container">
            <ul id="slider" class="slider">

                <li data-indexid="72" class="cur"
                    data-img="http://attachment-center.ixincheng.com:38080/data/picture/online/2018/03/16/11/99577ff0e4824272a4ff7bce2c21b1ab.jpg"
                    style="display: none;"><a id="aImg0" target="_blank"
                                              href="/web/html/freeOpenCourseDetailPage.html?id=608&amp;direct_id=396903971"
                                              style="background: url(&quot;http://attachment-center.ixincheng.com:38080/data/picture/online/2018/03/16/11/99577ff0e4824272a4ff7bce2c21b1ab.jpg&quot;) center top no-repeat;"></a>
                </li>
                <li data-indexid="71" class="cur" style="display: block;"
                    data-img="http://attachment-center.ixincheng.com:38080/data/picture/online/2018/02/24/16/73c6c531ac784c0eb0b387542aa595da.jpg">
                    <a id="aImg1" target="_blank"
                       href="/web/html/payCourseDetailPage.html?id=584"
                       style="background: url(&quot;http://attachment-center.ixincheng.com:38080/data/picture/online/2018/02/24/16/73c6c531ac784c0eb0b387542aa595da.jpg&quot;) center top no-repeat;"></a>
                </li>
                <li data-indexid="42" class="cur" style="display: none;"
                    data-img="http://attachment-center.ixincheng.com:38080/data/picture/online/2017/12/18/15/12db98e4fc674f1d9b1e5995d2c533d3.jpg">
                    <a id="aImg2" target="_blank" href="/index.html"
                       style="background: url(&quot;http://attachment-center.ixincheng.com:38080/data/picture/online/2017/12/18/15/12db98e4fc674f1d9b1e5995d2c533d3.jpg&quot;) center top no-repeat;"></a>
                </li>
                <li data-indexid="70" class="cur" style="display: none;"
                    data-img="http://attachment-center.ixincheng.com:38080/data/picture/online/2018/01/02/18/9883c22b429c4f24993baa9c345a8bc3.jpg">
                    <a id="aImg3" target="_blank"
                       href="/web/html/forumDetail.html?articleId=105"
                       style="background: url(&quot;http://attachment-center.ixincheng.com:38080/data/picture/online/2018/01/02/18/9883c22b429c4f24993baa9c345a8bc3.jpg&quot;) center top no-repeat;"></a>
                </li>
                <li data-indexid="61" class="cur" style="display: none;"
                    data-img="http://attachment-center.ixincheng.com:38080/data/picture/online/2017/12/16/03/21ba2520090c45e48c8221878165fd92.jpg">
                    <a id="aImg4" target="_blank"
                       href="/web/html/practitionerDetails.html?Id=cadef278b0ef4a7eab9a682d35198cf4"
                       style="background: url(&quot;http://attachment-center.ixincheng.com:38080/data/picture/online/2017/12/16/03/21ba2520090c45e48c8221878165fd92.jpg&quot;) center top no-repeat;"></a>
                </li>
            </ul>
            <div id="left"><em></em></div>
            <div id="right"><em></em></div>
            <div id="selector" class="selector" style="left: 50%; margin-left: -45px;">

                <span class=""></span><span class="cur"></span><span class=""></span><span class=""></span><span
                    class=""></span></div>

        </div>
    </div>


    <!--课程列表-->
    <div id="bgColor">

        <div class="zhibo" style="margin-top: 1%;">
            <div class="yugao1 yugao2">
                <!-- 视频 -->
                <div class="video_left">
                    <div class="video_div">
                        <div class="" style="width: 100%; height: 350px;">
                            <img src="./熊猫中医云课堂 - 线上中医教育_files/3d3e02d742634d5c9e018545dee08a9f.jpg" alt=""
                                 class="video_img" id="liveImg">
                        </div>

                        <div class="video_div_bottom">
                            <div class="video_div_bottom_right">
                                <div class="video_div_bottom_bg2"><img src="/web/images/watching.png"
                                                                       alt=""><span id="liveLearndCount">61</span>人&nbsp;&nbsp;<span>同时观看</span>
                                </div>
                                <a href="javascript: ;"></a>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="w-video-module w_yugao2">
                    <div class="w_yugao_hd">
                        <h2 class="h_tl" style="margin-top:0px;">
                            <img src="/web/images/0802301_03.png" alt="">
                            <span href="javascript:void(0);">直播活动</span>
                        </h2>
                    </div>
                    <div class="w-video-module-bd">
                        <div class="w_div">
                            <div class="w_ul" style="overflow: hidden1;">
                                <ul class="w_ul_ul">
                                    <li>
                                        <img src="/web/images/082305_03.png" alt="" class="li_img">
                                        <span class="box">
                                        <span class="time"><strong></strong>&nbsp;14:00</span>
                                        <span class="time1"><strong></strong>&nbsp;2018-01-28</span>
                                        <a href="/course/courses/581" class="reser-btn">
                                            <img src="/web/images/turn.png" alt="">回放
                                        </a>
                                        <span class="cover poi" onclick="jump(&#39;/course/courses/581&#39;)">
                                            <span class="cover_img">
                                            <img class="lazy" alt="" style="display: inline; visibility: visible;"
                                                 src="./熊猫中医云课堂 - 线上中医教育_files/3d3e02d742634d5c9e018545dee08a9f.jpg">
                                            </span>
                                        </span>
                                        <p class="box_p poi" onclick="jump(&#39;/course/courses/581&#39;)">【熊猫讲堂】“天人合一”与最佳怀孕时间</p>
                                            <span class="t poi" onclick="jump(&#39;/course/courses/581&#39;)">熊猫中医学堂&nbsp;<span>主讲</span></span>
                                        <span class="intro poi" onclick="jump(&#39;/course/courses/581&#39;)"></span>
                                        </span>
                                    </li>
                                    <li>
                                    <img src="/web/images/082305_03.png" alt="" class="li_img">
                                        <span class="box">
                                            <span class="time"><strong></strong>&nbsp;19:00</span>
                                            <span class="time1"><strong></strong>&nbsp;2018-03-17</span>
                                            <a href="/course/courses/609" class="reser-btn"><img src="/web/images/turn.png" alt="">回放</a>
                                            <span class="cover poi" onclick="jump(&#39;/course/courses/609&#39;)">
                                                <span class="cover_img">
                                                <img class="lazy" alt="" style="display: inline; visibility: visible;" src="./熊猫中医云课堂 - 线上中医教育_files/5c1f4cc0e387461a882b7a08d98f2fcf.jpg">
                                                </span>
                                            </span>
                                            <p class="box_p poi" onclick="jump(&#39;/course/courses/609&#39;)">朱小宝中医外治交流会——感冒的预防和治疗</p>
                                            <span class="t poi" onclick="jump(&#39;/course/courses/609&#39;)">熊猫中医学堂&nbsp;<span>主讲</span></span>
                                            <span class="intro poi" onclick="jump(&#39;/course/courses/609&#39;)">朱小宝中医外治交流会——感冒的预防和初期治疗。主讲：朱小宝、苏昊明、彭时雨、神秘嘉宾。</span>
                                        </span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="main" class="clearfix">
            <h1>中医学习</h1>
            <ul id="tabFirst_zyxx" class="tabFirst">
                <li class="select" data-number="1"><span>全部</span></li>
                <li data-number="200"><span>脉诊大全</span></li>
                <li data-number="201"><span>针灸课程</span></li>
                <li data-number="202"><span>各式推拿</span></li>
                <li data-number="205"><span>古籍经典</span></li>
            </ul>
            <div class="hr"></div>
            <div id="log_zyxx" style="width: 100%; margin-top: 0.7%">
                <div id="content_zyxx" class="content clearfix" style="width: 110%;">
                    <div class="course clearfix">
                        <img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png"><a style="cursor:pointer" href="/course/courses/584" target="_blank">
                        <div class="img">
                            <img src="http://attachment-center.ixincheng.com:38080/data/picture/online/2018/02/24/15/44e6cc44f9b84b5fbd18a132e4f1ad87.jpg">
                        </div>
                        <span class="classCategory">视频专辑</span>
                        <div class="detail">
                            <p class="title" data-text="小宝中医带你快速入门学针灸【合辑（全）】" title="小宝中医带你快速入门学针灸【合辑（全）】">
                                小宝中医带你快速入门学针灸【合辑（全）】
                            </p>
                            <p class="timeAndTeac">
                                <span>讲师：<span class="teacher">熊猫中医学堂</span></span>
                            </p>
                            <p class="info clearfix">
                                <span><span class="price">2980</span><span>熊猫币</span></span><span class="stuCount"><img src="/web/images/studentCount.png" alt=""><span class="studentCou">339</span></span>
                            </p>
                        </div>
                    </a>
                    </div>

                </div>

                <div class="zhongyi device-page clearfix">
                    <nav>
                        <ul class="pagination pagination-sm" style="float: right; display: block;">
                            <li class="active"><a title="Current page is 1">1</a></li>
                            <li><a title="Go to page 2">2</a></li>
                            <li><a title="Go to page 3">3</a></li>
                            <li><a title="Go to page 4">4</a></li>
                            <li><a title="Go to page 5">5</a></li>
                            <li><a title="Go to next page">&gt;</a></li>
                            <li><a title="Go to last page">&gt;&gt;</a></li>
                        </ul>
                    </nav>
                </div>

                <div class="pages" style="display: none;">
                    <!-- <div id="Pagination"></div> -->
                    <div class="searchPage">
                        <span class="page-sum">共<strong class="allPage">0</strong>页</span>
                        <span class="page-go">跳转<input type="text" style="width: 37px;" min="1" max="">页</span>
                        <a href="javascript:;" class="page-btn">确定</a>
                    </div>
                </div>
            </div>

            <h1>线下课</h1>
            <ul id="tabFirst_xxpxb" class="tabFirst">

            </ul>
            <div class="hr"></div>
            <div id="log_xxpxb" style="width: 100%; margin-top: 0.7%">
                <div id="content_xxpxb" class="content clearfix" style="width: 110%;">
                    <div class="course xxpxb clearfix">
                        <a style="cursor:pointer" href="/course/courses/607" target="_blank">
                            <div class="img">
                                <img src="http://attachment-center.ixincheng.com:38080/data/picture/online/2018/03/16/10/40e3bbdc36344d1cb8dbdb796d2856a1.jpg">
                            </div>
                            <div class="detail">
                                <p class="title" data-text="朱小宝中医外治交流会——感冒的预防和治疗" title="朱小宝中医外治交流会——感冒的预防和治疗">朱小宝中医外治交流会——感冒的预防和治疗</p>
                                <p class="timeAndTeac">
                                    <span>讲师：<span class="teacher">熊猫中医学堂</span></span>
                                </p>
                                <p class="timeAndTeac"><span>地址：<span class="teacher">北京市-北京市-海淀区 北京市海淀区亮甲店130-5号恩济大厦B座1层 坤鹤百草堂</span></span></p>
                                <p class="timeAndTeac"><span>时间：<span class="teacher">2018/03/17-2018/03/17</span></span></p><p class="info clearfix"><span></span></p>
                            </div>
                        </a>
                    </div>
                    <div class="course xxpxb clearfix">
                        <a style="cursor:pointer" href="/course/courses/607" target="_blank">
                            <div class="img">
                                <img src="http://attachment-center.ixincheng.com:38080/data/picture/online/2018/03/16/10/40e3bbdc36344d1cb8dbdb796d2856a1.jpg">
                            </div>
                            <div class="detail">
                                <p class="title" data-text="朱小宝中医外治交流会——感冒的预防和治疗" title="朱小宝中医外治交流会——感冒的预防和治疗">朱小宝中医外治交流会——感冒的预防和治疗</p>
                                <p class="timeAndTeac">
                                    <span>讲师：<span class="teacher">熊猫中医学堂</span></span>
                                </p>
                                <p class="timeAndTeac"><span>地址：<span class="teacher">北京市-北京市-海淀区 北京市海淀区亮甲店130-5号恩济大厦B座1层 坤鹤百草堂</span></span></p>
                                <p class="timeAndTeac"><span>时间：<span class="teacher">2018/03/17-2018/03/17</span></span></p><p class="info clearfix"><span></span></p>
                            </div>
                        </a>
                    </div>

                <div class="peixun device-page clearfix hide">
                    <nav>
                        <ul class="pagination pagination-sm" style="float:right"><li class="active"><a title="Current page is 1">1</a></li></ul>
                    </nav>
                </div>
            </div>


            <!-------------学员故事---------------->
            <!-- 暂时关闭---20170725---yuruixin -->
            <div style="width:100%;overflow:hidden;position: relative;">
                <div id="studentStore" class="slide-box">
                    <h2>学员故事</h2>

                    <div id="box" class="slideBox">
                        <a href="javascript:;" id="prev" class="prev"><em
                                style="background-size: 7px 13px;background-image: url(/web/images/130/banner_left_normal.png);background-repeat: no-repeat;background-position: center center;"></em></a>
                        <div class="tempWrap" style="overflow:hidden; position:relative; width:1218px">
                            <ul class="clearfix boxContent"
                                style="width: 2842px; position: relative; overflow: hidden; padding: 0px; margin: 0px; left: -1218px;">
                                <li data-storyid="2c9aec345dfe4725015e062b17590028" class="clone"
                                    style="float: left; width: 406px;">
                                    <div class="rr">
                                        <div class="student-img"><img
                                                src="./熊猫中医云课堂 - 线上中医教育_files/e5a8743e92ac42ab98b2f865d300464d.jpeg"
                                                alt="">
                                            <p>刘小琳</p></div>
                                        <div class="student-info"><p class="info_top"></p>
                                            <p class="brief"><span><em>“</em>中医是如何炼成的<em>”</em></span></p></div>
                                    </div>
                                </li>
                                <li data-storyid="2c9aec345dfe4725015e062eb79e002a" style="float: left; width: 406px;">
                                    <div class="rr">
                                        <div class="student-img"><img
                                                src="./熊猫中医云课堂 - 线上中医教育_files/7a7ab7814bea4e28baf6073efeb27536.jpg"
                                                alt="">
                                            <p>学员故事三</p></div>
                                        <div class="student-info"><p class="info_top"></p>
                                            <p class="brief"><span><em>“</em>感谢心承智慧让我有机会能系统学习中医知识<em>”</em></span></p>
                                        </div>
                                    </div>
                                </li>
                                <li data-storyid="2c9aec345dfe4725015e062d004a0029" style="float: left; width: 406px;">
                                    <div class="rr">
                                        <div class="student-img"><img
                                                src="./熊猫中医云课堂 - 线上中医教育_files/8139bd10e9be4d569512671739633210.jpg"
                                                alt="">
                                            <p>韩小伟</p></div>
                                        <div class="student-info"><p class="info_top"></p>
                                            <p class="brief"><span><em>“</em>好好学习中医，天天长本事<em>”</em></span></p></div>
                                    </div>
                                </li>
                                <li data-storyid="2c9aec345dfe4725015e062b17590028" style="float: left; width: 406px;">
                                    <div class="rr">
                                        <div class="student-img"><img
                                                src="./熊猫中医云课堂 - 线上中医教育_files/e5a8743e92ac42ab98b2f865d300464d.jpeg"
                                                alt="">
                                            <p>刘小琳</p></div>
                                        <div class="student-info"><p class="info_top"></p>
                                            <p class="brief"><span><em>“</em>中医是如何炼成的<em>”</em></span></p></div>
                                    </div>
                                </li>
                                <li data-storyid="2c9aec345dfe4725015e062eb79e002a" class="clone"
                                    style="float: left; width: 406px;">
                                    <div class="rr">
                                        <div class="student-img"><img
                                                src="./熊猫中医云课堂 - 线上中医教育_files/7a7ab7814bea4e28baf6073efeb27536.jpg"
                                                alt="">
                                            <p>学员故事三</p></div>
                                        <div class="student-info"><p class="info_top"></p>
                                            <p class="brief"><span><em>“</em>感谢心承智慧让我有机会能系统学习中医知识<em>”</em></span></p>
                                        </div>
                                    </div>
                                </li>
                                <li data-storyid="2c9aec345dfe4725015e062d004a0029" class="clone"
                                    style="float: left; width: 406px;">
                                    <div class="rr">
                                        <div class="student-img"><img
                                                src="./熊猫中医云课堂 - 线上中医教育_files/8139bd10e9be4d569512671739633210.jpg"
                                                alt="">
                                            <p>韩小伟</p></div>
                                        <div class="student-info"><p class="info_top"></p>
                                            <p class="brief"><span><em>“</em>好好学习中医，天天长本事<em>”</em></span></p></div>
                                    </div>
                                </li>
                                <li data-storyid="2c9aec345dfe4725015e062b17590028" class="clone"
                                    style="float: left; width: 406px;">
                                    <div class="rr">
                                        <div class="student-img"><img
                                                src="./熊猫中医云课堂 - 线上中医教育_files/e5a8743e92ac42ab98b2f865d300464d.jpeg"
                                                alt="">
                                            <p>刘小琳</p></div>
                                        <div class="student-info"><p class="info_top"></p>
                                            <p class="brief"><span><em>“</em>中医是如何炼成的<em>”</em></span></p></div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <a href="javascript:;" id="next" class="next"><em style="background-size: 7px 13px;background-image: url(/web/images/130/banner_right_normal.png);background-repeat: no-repeat;background-position: center center;
"></em></a>
                    </div>

                </div>
            </div>
            <!-------------学员故事---------------->

        </div>
    </div>
</div>
<hr class="bottom clearfix">
<div class="boxueguBottomIntroduct clearfix">
    <div class="index-footer clearfix">
        <div class="index-footer-middle clearfix">
            <p class="index-footer-title">关注我们</p>

            <div class="weibo-erweima">
                <a href="http://weibo.com/u/6329861207?refer_flag=1001030102_&amp;is_hot=1" target="_blank">
                    <img class="weibo" src="/web/images/125/weibo.png" alt="" title="点击关注熊猫中医微博">
                </a>
                <div class="weibo-hover">

                    <img src="/web/images/125/weiboerweima.png" alt="">

                    <p>&nbsp;&nbsp;关注熊猫中医新浪微博获取更多行业即时资讯</p>
                    <img class="indexSanjiao" src="/web/images/125/index03.png" alt="">
                </div>
            </div>
            <div class="weixin-erweima">
                <img class="weixin" src="/web/images/125/weixin.png" alt="">

                <div class="weixin-hover">
                    <img src="/web/images/125/weixinImg.png" alt="">

                    <p>关注熊猫中医微信公众号每日获取最新学习资料</p>
                    <img class="indexSanjiao" src="/web/images/125/index03.png" alt="">
                </div>
            </div>


        </div>
        <div id="friendLink"><h3>友情链接</h3>
            <a href="http://www.xczhihui.com/" target="_blank">心承智慧</a>
            <a href="http://www.nhfpc.gov.cn/" target="_blank">卫计委</a>
            <a href="http://www.cptcm.com/" target="_blank">中国中医药出版社</a>
            <a  href="http://www.tcmmooc.com/" target="_blank">中医在线</a>
            <a href="http://www.bucm.edu.cn/" target="_blank">北京中医药大学</a></div>
    </div>
</div>
<script type="text/javascript" src="./熊猫中医云课堂 - 线上中医教育_files/jquery.pagination.js.下载"></script>
<script type="text/javascript" src="./熊猫中医云课堂 - 线上中医教育_files/bootstrap-paginator.min.js.下载"></script>
<script type="text/javascript" src="./熊猫中医云课堂 - 线上中医教育_files/footer.js.下载"></script>
<#include "footer.ftl">


<script src="./熊猫中医云课堂 - 线上中医教育_files/z_stat.php" type="text/javascript"></script>
<script src="./熊猫中医云课堂 - 线上中医教育_files/core.php" charset="utf-8" type="text/javascript"></script>
<script src="./熊猫中医云课堂 - 线上中医教育_files/placeHolder.js.下载"></script>
<script type="text/javascript">
    var yyFlag = false;
    $(function () {
        $('input').placeholder();
    });

    $(function () {
        $(".order_close").click(function () {
            $(".popover_order").css('display', 'none');
        });

        function check() {
            var regPhone = /^1[3-5678]\d{9}$/;
            if ($(".phone").val().trim().length === 0) {
                $(".phone").css("border", "1px solid #ff4012");
                $("#tips span").html("请输入手机号");
                $("#tips").show();
                yyFlag = false;
            } else if (!(regPhone.test($(".phone").val().trim()))) {
                $(".phone").css("border", "1px solid #ff4012");
                $("#tips span").html("手机号格式不正确");
                $("#tips").show();
                yyFlag = false;
            } else {
                $("#tips").hide();
                $(".phone").css("border", "1px solid #2cb82c");
                $(".cyinput1").css("border", "");
                yyFlag = true;
            }
        }

        $(".phone").blur(function () {
            check();
        });
        $(".order_affirm").click(function () {
            check();
            if (yyFlag) {
                var phone = $(".phone").val();
                var subscribeId = $("#subscribeId").val();
                RequestService("/course/subscribe", "GET", {
                    mobile: phone,
                    courseId: subscribeId
                }, function (data) {
                    console.info(data);
                    if (data.success) {
                        $(".popover_order").css('display', 'none');
                        RequestService("/online/live/getLiveTrailer", "GET", {num: 4}, function (data) {
                            $(".w_ul_ul").html(template.compile(liveTrailerTemplate)({
                                items: data.resultObject
                            }))
                            subscribeInit();
                        });
//                            $("#yyStart").html($("#startTime").val())
                        rTips(data.resultObject);
                    } else {
                        $(".popover_order").css('display', 'none');
//				            rTips(data.errorMessage);
                        rTips();

                    }
                })
            }
        });


    })
</script>


<div class="browserBody" style="display:none;">
    <div class="bcgop"></div>
    <div class="browserBody-text"><p>您目前使用的浏览器可能无法实现最佳浏览效果，建议使用Chrome(谷歌)、Firefox(火狐)、Edge、IE9及IE9以上版本浏览器。</p><a
            href="/web/html/Download.html">立即升级</a><img
            src="./熊猫中医云课堂 - 线上中医教育_files/BWcolse.png"></div>
</div>
<span id="sbmarwbthv5"></span></body>
</html>