<#assign defaultDoctorHeadImg="/web/images/defaultHead/18.png"/>
<#macro workTime text>
    <#if text?? && text != '暂无' && text != ''>
    <p>${text}</p>
    </#if>
</#macro>
<div class="head-top">
    <div class="content">
        <ul>
            <li class="first-li"><span">全球中医传承平台</span"></li>
            <li>
                <div class="loginGroup">
                    <div class="logout" style="display: block;">
                        <a class="btn-login btn-link" data-toggle="modal"  data-target="#login"  data-backdrop="static">登录</a>
                        <span>|</span>
                        <a class="btn-register btn-link" href="/web/html/login.html?ways=register">注册</a></div>
                </div>
            </li>
            <li><a href="/app" class="appDown hide">APP下载</a></li>
            <li><a href="/anchor/guide" class="want-anchor" target="_blank">我要当主播</a></li>
            <li><div class="messageBox"><a href="javascript:;" data-id="mynews" class="message">消息</a></div></li>
            <li><a class="studentCenterBox" href="javascript:;">学习中心</a></li>
        </ul>
    </div>
</div>
<div class="header_body">
    <div class="header_right"><a href="/"><img
            src="/web/images/logos.png" alt="logo" style="float:left"></a>
    </div>
    <div class="header_left">
        <div class="path" style="width:95%">
            <a href="${webUrl}/" class="home">首页</a>
            <a href="${webUrl}/headline/1" class="headline">头条</a>
            <a href="${webUrl}/courses/recommendation" class="classroom">学堂</a>
            <a href="${webUrl}/doctors" class="doctor-tab">名医</a>
            <a href="${webUrl}/clinics" class="hospital-tab">医馆</a>
            <a href="${webUrl}/questions" class="ansAndQus">问道</a>
            <a href="${webUrl}/bbs" class="bbs-tab">论坛</a>
        </div>
    </div>
</div>


<div class="modal" id="login" data-backdrop="static" style="display: none;">
    <div class="modal-dialog login-module" role="document">
        <div class="cymylogin">
            <div class="cymylogin-top clearfix">
                <div class="cymyloginclose" data-dismiss="modal" aria-label="Close" data-backdrop="static"></div>
                <div class="cymyloginlogo">欢迎登录&nbsp;&nbsp;熊猫中医云课堂</div>
                <div class="cymyloginhint cymlogin" style="top: 221px; display: block;">请输入6-18位密码</div>
            </div>
            <div class="cymylogin-bottom form-login"><input type="text" class="cyinput1 form-control" maxlength="30"
                                                            placeholder="请输入手机号" autocomplete="off">
                <div class="cymyloginclose1"></div>
                <input type="password" class="cyinput2 form-control" maxlength="18" placeholder="请输入6-18位密码"
                       autocomplete="new-password" style="border: 1px solid rgb(255, 64, 18);">
                <div class="cymyloginclose2"></div>
                <button class="cymyloginbutton">登<em></em>录</button>
                <div class="cymyloginpassword"><a class="atOnceRegister"
                                                  href="/web/html/login.html?ways=register">立即注册</a><a
                        class="atOnceResetPassword" href="/web/html/resetPassword.html">忘记密码？</a>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="oldModalBack"></div>
<div class="consult_center">咨询中心</div>
<div class="online_consult">
    <a href="http://p.qiao.baidu.com/cps/chat?siteId=11043846&amp;userId=24331702" target="_blank">
        <img src="/web/images/zixun.gif" alt="">
        <span>在线咨询</span>
    </a>
</div>
<div class="phone_consult">
    <div class="phone_consult_box">
        <img src="/web/images/tel.gif" alt="">
        <span class="dianhuazixun">电话咨询</span>
    </div>
    <span class="phone_number">0898-32881934</span>
</div>
<div class="sideWeixinBox">
    <div class="sideWeixin">
        <img src="/web/images/sideWeixinErma.png">
        <p>关注微信</p>
    </div>
</div>
<a class="sideWeiboBox" href="http://weibo.com/u/6329861207?refer_flag=1001030102_&amp;is_hot=1" target="_blank">
    <div class="sideWeibo">
        <img src="/web/images/sideWeiboErma.png">
        <p>关注微博</p>
    </div>
</a>
<div class="sideWeixinErma">
    <img src="/web/images/sideWeixin.png">
    <div class="sideSanjiao">
        <img src="/web/images/float_sanjiao.png">
    </div>
</div>
<div class="sideWeiboErma">
    <img src="/web/images/sideWeibopng.png">
    <div class="sideSanjiao1">
        <img src="/web/images/float_sanjiao.png">
    </div>
</div>
<div class="h_top" onclick="pageScroll();" style="display: none;">
        <span class="wrap">
            <img src="/web/images/top.png" alt="">
            <span class="h_top_s">top</span>
        </span>
</div>