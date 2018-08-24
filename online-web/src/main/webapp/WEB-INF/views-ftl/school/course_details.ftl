<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>${courseInfo.gradeName} - 熊猫中医学堂</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description" content="${description}"/>
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF"/>
    
    <!-- 分享显示的缩略图-->
    
    <!--公共头部和底部样式-->
    <link rel="stylesheet" href="/web/html/school/school-header/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>
    <!--公共头部和底部样式结束-->
    <!--登陆的bootstrap样式-->
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link href="/web/bootstrap-select/bootstrap.min.css" rel="stylesheet">
    <!--登陆的bootstrap样式-->
    <!--字体图标样式-->
    <link rel="stylesheet" href="/web/fonts/style.css"/>
    <!--分页CSS-->
    <link rel="stylesheet" href="/web/css/ftl-page.css"/>
    <link rel="stylesheet" href="/web/css/school/details-album.css"/>

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/common_msg.js"></script>
    <!--公共头部和底部-->
    <script src="/web/js/common/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/html/school/school-header/header.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/jquery.qrcode.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/utf.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<header>
<#include "header-body.ftl">
</header>
<div class="wp">
    <div class="wrap-buy">
        <div class="left-cover z">
        	
        	  <#if courseInfo.courseForm == 2  && courseInfo.multimediaType == 1>
                  <#if courseInfo.collection> 
                     <p class="class-style">视频专辑</p>
                  <#elseif !courseInfo.collection>
                      <p class="class-style">视频</p>
                  </#if>
                 
              <#elseif courseInfo.courseForm == 2  && courseInfo.multimediaType == 2>
                  <#if courseInfo.collection> 
                   <p class="class-style">音频专辑</p>
                  <#elseif !courseInfo.collection>
                     <p class="class-style">音频</p>
                  </#if>
              <#elseif courseInfo.courseForm == 1  && courseInfo.multimediaType == 1>
                  <#if courseInfo.lineState  == 1  >
                      <p class="class-style">视频直播中</p>
                  <#elseif courseInfo.lineState  == 2>
                      <p class="class-style">视频直播预告</p>
                  <#elseif courseInfo.lineState  == 3>
                      <p class="class-style">视频直播回放</p>
                  <#elseif courseInfo.lineState  == 4>
                      <p class="class-style">即将视频直播</p>
                  <#else>
                      <p class="class-style">暂未开播</p>
                  </#if>
              <#elseif courseInfo.courseForm == 1  && courseInfo.multimediaType == 2>
                  <#if courseInfo.lineState  == 1  >
                      <p class="class-style">语音直播中</p>
                  <#elseif courseInfo.lineState  == 2>
                      <p class="class-style">语音直播预告</p>
                  <#elseif courseInfo.lineState  == 3>
                      <p class="class-style">语音直播回放</p>
                  <#elseif courseInfo.lineState  == 4>
                      <p class="class-style">即将语音直播</p>
                  <#else>
                      <p class="class-style">暂未开播</p>
                  </#if>
              <#elseif courseInfo.courseForm == 3>
                  <p class="class-style">线下课程</p>
              </#if>
            <img src="${courseInfo.smallImgPath}?imageMogr2/thumbnail/!462x260r|imageMogr2/gravity/Center/crop/462x260" alt="${courseInfo.gradeName}" />
            <div class="progress"
                 style="position: absolute;bottom: 0;left: 0;width: 100%;
                 	margin-bottom: 0;height: 14px;display:none">
                <div class="progress-bar progress-bar-success" role="progressbar"
                     aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                     style="background: #00BC12;">
                    <span class="sr-only">90% 完成（成功）</span>
                </div>
            </div>
        </div>
        <div class="right-details y">
            <h4>${courseInfo.gradeName}</h4>
            <p class="subtitle">${courseInfo.subtitle?default('')}</p>
            <ul class="author-inf">
                <li>
                    <span>主讲人：<a href="${webUrl}/anchors/${courseInfo.userLecturerId}/info"
                                 target="_blank">${courseInfo.name}</a></span>
                </li>
                <li class="grade">
                    <img src="../../web/images/icon-start${startLevel}.png"/>
                </li>
                <li>
                    <span>${courseInfo.criticizeCount}条评论</span>
                </li>
            </ul>
            <div class="display-price">
                <div class="under-price">
                    <span>价</span>
                    <span>格</span>
                </div>

            <#if courseInfo.watchState == 0 || courseInfo.watchState == 2>
                <p><span>${courseInfo.currentPrice}</span>熊猫币
                <#if (courseInfo.originalCost)?? && courseInfo.originalCost != 0>
                    <span style="text-decoration: line-through;color:#787878;font-size:12px">原价${courseInfo.originalCost}</span>
                </#if></p>
            <#elseif courseInfo.watchState == 1>
                <p><span style="font-size: 17px;">免费</span>
                <#if (courseInfo.originalCost)?? && courseInfo.originalCost != 0>
                    <span style="text-decoration: line-through;color:#787878;font-size:12px">原价${courseInfo.originalCost}</span>
                </#if></p>
            </#if>

            <#-- 根据不同的课程类型，显示不同的课程介绍 -->

            <#if courseInfo.courseForm == 2>
                <#if courseInfo.collection >
                    <ul>
                        <li>更新时间</li>
                        <li>共${courseInfo.courseNumber}集, 已更新${collectionListSize!'0'}集
                            <#if updateDateText?? &&  updateDateText!=''>
                                (${updateDateText})
                            </#if>
                        </li>
                    <#-- <li>共16集，已更新13集（每周三、周五更新）</li> -->
                    </ul>
                </#if>
            <#elseif courseInfo.courseForm == 1>
            <#-- 直播的 -->
                <ul>
                    <li>直播时间</li>
                    <li>${courseInfo.startTime?string("yyyy.MM.dd HH:mm")}</li>
                </ul>
            <#elseif courseInfo.courseForm == 3>
            <#-- 线下课的 -->
                <ul>
                    <li>上课时间</li>
                    <li>${courseInfo.startTime?string("yyyy.MM.dd HH:mm")} -
                    ${courseInfo.endTime?string("yyyy.MM.dd HH:mm")}</li>
                </ul>
                <p class="under-address">上课地址
                    <span>
                        <#if courseInfo.address??>
									${(courseInfo.address?replace("-",""))?replace(" ","")}
								</#if>
							</span></p>
            </#if>

            </div>

             
            <#if courseInfo.status == 0 && courseInfo.currentPrice gt 0 &&  courseInfo.watchState != 2>
                 <button type="button" class="immediately-buy" style="background:#DEDEDE;">课程下架</button>
            <#elseif courseInfo.status == 0 && courseInfo.currentPrice lte 0 && courseInfo.learning != 1>
                 <button type="button" class="immediately-buy" style="background:#DEDEDE;">课程下架</button>     
            <#else>
                <#if courseInfo.courseForm==1  && courseInfo.lineState == 3  && !courseInfo.record  >
                    <span class="live-null-tip">当前直播无回放，请随时关注讲师动态，避免错过下次直播</span>
                <#else>
                	 <#if courseInfo.collectionHint??>
                        <p class="contain-album">该课程属于专辑《${courseInfo.collectionHint.gradeName}》，购买专辑更全面划算哦~
                        	<a href="${webUrl}/courses/${courseInfo.collectionHint.id}/info">查看详情&gt;&gt;</a></p>
                     </#if>
                
                    <#if courseInfo.watchState == 1 || courseInfo.watchState == 2>
                    	
                        <button type="button" class="immediately-buy  learning_immediately"
                                data-watchState="${courseInfo.watchState}"
                                data-type="${courseInfo.type}"
                                data-courseForm="${courseInfo.courseForm}"
                                data-multimediaType="${courseInfo.multimediaType}"
                                data-realCourseId="${courseInfo.id}"
                                data-learning="${courseInfo.learning}"
                                data-cutoff="${courseInfo.cutoff}"
                                data-collection="${courseInfo.collection?string("1","0")}"
                                <#if courseInfo.watchState == 1  && courseInfo.courseForm == 3 && courseInfo.cutoff = 1>
                                   style="background:#DEDEDE;"
                                </#if>
                                >
                            <#if courseInfo.watchState == 2  && courseInfo.courseForm == 3>
                                   		已报名
                            <#elseif courseInfo.watchState == 1  && courseInfo.courseForm == 3>
                                <#if courseInfo.learning == 1>
                                   		已报名
                                <#elseif courseInfo.cutoff = 1>
                                  		 已结束报名
                                <#else>
                                    	立即报名
                                </#if>
                            <#else>
                                		   开始学习
                            </#if>
                        </button>
                    <#elseif courseInfo.watchState == 0>
                        <#if courseInfo.courseForm == 3 && courseInfo.cutoff = 1>
                            <button type="button" class="immediately-buy" style="background:#DEDEDE;">已结束报名</button>
                        <#else>
                            <button type="button" class="immediately-buy J-course-buy"
                                    data-id="${courseInfo.id}"
                                    data-courseForm="${courseInfo.courseForm}"
                                    data-multimediaType="${courseInfo.multimediaType}"
                                    data-type="${courseInfo.type}">立即购买
                            </button>
                        </#if>
                    </#if>
                </#if>
            </#if>

            <span class="remember-last hide">上次播放位置：<span>标幽赋的前世今生详解 </span></span>
        </div>
    </div>

    <div class="main">
        <!--左侧详情/评价/常见问题-->
        <div class="content-inf z">
            <!--nav-->
            <div class="wrap-sidebar">
                <ul>
                <#-- tab的显示，这个就当做专辑页面来写   -->
                <#if  courseInfo.collection> <#-- 专辑tab显示    -->
                    <#if courseInfo.watchState = 1 || courseInfo.watchState = 2> <#-- 免费或已购买  -->
                        <li><a href="${webUrlParam}/selection">选集</a></li>
                        <li><a href="${webUrlParam}/albumInfo">详情</a></li>
                    <#elseif courseInfo.watchState = 0>
                        <li><a href="${webUrlParam}/info">详情</a></li>
                        <li><a href="${webUrlParam}/outline">课程大纲</a></li>
                    </#if>
                <#else> <#-- 非专辑tab显示    -->
                    <li><a href="${webUrlParam}/info">详情</a></li>
                </#if>
                    <li><a href="${webUrlParam}/comment">评价（${courseInfo.criticizeCount}）</a></li>
                    <li><a href="${webUrlParam}/aq">常见问题</a></li>
                </ul>
            </div>
            <!--content-->
            <!--选集-->
            <div class="sidebar-content buy_tab hide" style="padding: 0;">
                <div class="wrap-anthology">
                    <ul>
                    <#if collectionList??>
                        <#list collectionList as collectionItem>
                            <li>
                                <#-- 判断有没有登录,判断是否是免费的呗。增加一个标记 -->
                                <a href="/web/html/ccvideo/liveVideoAlbum.html?collectionId=${courseInfo.id}&courseId=${collectionItem.id}&watchState=${courseInfo.watchState}"
                                   target="_blank">
                                    <div class="play-img z">
                                        <div class="circle">
                                            <div class="percent left percentleftId" data-courseId="${collectionItem.id}"
                                                 data-timeLength="${collectionItem.courseLength}"></div>
                                            <div class="percent right wth0"></div>
                                        </div>
                                        <img src="../../web/images/icon-play.png"/>
                                    </div>
                                    <div class="play-album z" data-courseId="${collectionItem.id}">
                                        <p>${collectionItem.gradeName}</p>
                                        <p class="course-length"><img src="/web/images/class_search_time.png"/>${collectionItem.courseLength}分钟<span></span></p>
                                    </div>
                                </a>
                            </li>
                        </#list>
                    </#if>
                    </ul>
                </div>
            </div>

            <!--详情-->
            <div class="sidebar-content hide">
            <#if courseInfo.lecturerDescription?? || courseInfo.description??>
                <div class="author-introduce">
                    主讲人
                </div>
                <div class="author-content">
                    <div class="author-text">
                        <#if courseInfo.lecturerDescription??>
                        ${courseInfo.lecturerDescription}
                        <#else>
                            <p style="padding-top:20px;">暂无主讲人介绍</p>
                        </#if>
                    </div>
                </div>
                <div class="author-introduce" style="margin-top: 30px;">
                    课程简介
                </div>
                <div class="author-content">
                    <div class="class-text">
                        <#if courseInfo.description??>
                        ${courseInfo.description}
                        <#else>
                            <p style="padding-top:20px;">暂无课程简介</p>
                        </#if>
                    </div>
                </div>
            <#else>
                <div class="all-null course-null">
                    <div class="null-img">
                        <img src="/web/images/icon-nodata.png"/>
                    </div>
                    <p>暂无主讲人介绍</p>
                </div>
            </#if>

            </div>

            <!--课程大纲-->
            <div class="sidebar-content no_buy_tab hide">
            <#if courseInfo.courseOutline??>
            ${courseInfo.courseOutline}
            <#else>
                <!--无数据时显示背景图-->
                <div class="all-null course-null">
                    <div class="null-img">
                        <img src="/web/images/icon-nodata.png"/>
                    </div>
                    <p>暂无数据</p>
                </div>
            </#if>
            </div>
            <!--评价-->
            <div class="sidebar-content hide">
            <#if criticizesMap??>
            	 <#include "common/comment.ftl">
			   </#if>
            </div>
            <!--常见问题-->
            <div class="sidebar-content hide">
                <ul class="often-problem">
                <#if commonProblem??>
                     ${commonProblem}
                <#else>
                    <!--无数据时显示背景图-->
                    <div class="all-null course-null">
                        <div class="null-img">
                            <img src="/web/images/icon-nodata.png"/>
                        </div>
                        <p>暂无数据</p>
                    </div>
                </#if>
                </ul>
            </div>
        </div>
        <!--右侧推荐课程-->
        <div class="wrap-recommend y">
            <h3>推荐课程</h3>
        <#if recommendCourse?? && recommendCourse?size gt 0 >
            <#include "common/recommend_course.ftl">
        </#if>
        </div>
    </div>
</div>
<!--语音直播弹窗-->
<div id="video-cover" class="hide" style="position: fixed; top: 0px; left: 0px; width: 100%; height: 100%; background: rgb(0, 0, 0); opacity: 0.3; z-index: 888;"></div>
<div class="video-live hide">
	<p class="video-top">语音直播</p>
	<div class="video-qr-code">
		
	</div>
	<p class="video-tip">请使用微信扫描二维码进入语音直播间观看</p>
	<p class="video-tip2">或用熊猫中医APP进入语音直播间观看</p>
</div>
<!--公共头部和底部结束-->

<!--登陆结束-->
<script type="text/javascript" charset="utf-8">
    var type = "${type}";
    var watchState = "${courseInfo.watchState}";
    var courseId = "${courseInfo.id}";
    var userId = "${courseInfo.userLecturerId}";
    var courseType = "${courseInfo.type}";
    var courseForm = "${courseInfo.courseForm}";
    var multimediaType = "${courseInfo.multimediaType}";
    var collection = ${courseInfo.collection?string("1","0")};
    <#if criticizesMap??>
    var commentCode = ${criticizesMap.commentCode};
    </#if>
    <#if courseInfo.courseLength??>
    var courseLength = "${courseInfo.courseLength}";
    </#if>


    var config = "";
    $.ajax({url:"/config.json",async:false,success:function(data){
        config = data;
    }});
    var shareCourseId = courseId;

    /**
     * 获取微信端 域名
     */
    var wxurl = "http://" + config.wechat;
    /**
     * 获取微信端分享连接地址
     */
//-- shareType 分享类型 1 课程  2 主播    shareId 当类型是1时为课程id，当是2时为用户id
    var share_link = "/wx_share.html?shareType=1&shareId="+shareCourseId;
    var qrcodeurl = wxurl+share_link;

    /**
     * 微信pc分享 显示二维码
     */
    $(".video-qr-code").qrcode({
        render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
        text : qrcodeurl,    //扫描了二维码后的内容显示,在这里也可以直接填一个网址，扫描二维码后
        width : "115",               //二维码的宽度
        height : "115",              //二维码的高度
        background : "#ffffff",       //二维码的后景色
        foreground : "#000000",        //二维码的前景色
//        src: '/web/images/yrx.png'             //二维码中间的图片
    });
</script>
<script src="/web/js/school/course-details.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/js/school/comment.js" type="text/javascript" charset="utf-8"></script>

<#include "../footer.ftl">

</body>
</html>