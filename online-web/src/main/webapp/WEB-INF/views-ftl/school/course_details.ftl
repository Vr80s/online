<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>熊猫中医云课堂 - 线上中医教育</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description"
          content="熊猫中医云课堂为。课程大纲全新优化，内容有广度、有深度，顶尖讲师全程直播授课。专注整合优势教学资源、打造适合在线学习并能保证教学结果的优质教学产品，同时打造和运营一整套教育生态软件体系，为用户提供满足自身成长和发展要求的有效服务。"/>
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF"/>
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
	<script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
	<script src="/web/html/school/school-header/header.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<header>
<#include "header-body.ftl">
</header>
<div class="wp">
    <div class="wrap-buy">
        <div class="left-cover z">
            <img src="${courseInfo.smallImgPath}"/>
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
                <p><span>${courseInfo.currentPrice}</span>熊猫币</p>
            <#elseif courseInfo.watchState == 1>
                <p><span style="font-size: 17px;">免费</span></p>
            </#if>

            <#-- 根据不同的课程类型，显示不同的课程介绍 -->

            <#if courseInfo.type == 1 || courseInfo.type == 2>
                <#if courseInfo.collection >
                    <ul>
                        <li>更新时间</li>
                        <li>共${courseInfo.courseNumber}集, 已更新${collectionListSize!'0'}集<#if updateDateText??>
                            (每${updateDateText}更新)</#if></li>
                    <#-- <li>共16集，已更新13集（每周三、周五更新）</li> -->
                    </ul>
                </#if>
            <#elseif courseInfo.type == 3>
            <#-- 直播的 -->
                <ul>
                    <li>直播时间</li>
                    <li>${courseInfo.startTime?string("yyyy.MM.dd HH:mm")}</li>
                </ul>
            <#elseif courseInfo.type == 4>
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
        <#--
            免费的进入到一个：
                直播、预告、回放的进入到一个。   /web/livepage/{courseId}
                专辑的进入一个。
                  /web/html/ccvideo/liveVideoAlbum.html?collectionId={}&courseId={}
                课程的进入一个 。
                  /web/html/ccvideo/video.html?courseId={}
                线下课不用显示
                还需要判断如果报名截止的话，显示报名截止
           付费的跳转到：支付页面了呗：
        -->

        <#if courseInfo.watchState == 1 || courseInfo.watchState == 2>
            <button type="button" class="immediately-buy  learning_immediately"
                    data-watchState="${courseInfo.watchState}"
                    data-type="${courseInfo.type}"
                    data-realCourseId="${courseInfo.id}"
                    data-learning = "${courseInfo.learning}"
                    data-collection="${courseInfo.collection?string(1,0)}">
                <#if courseInfo.watchState == 2  && courseInfo.type == 4>
                  		  已报名
                <#elseif courseInfo.watchState == 1  && courseInfo.type == 4>
                    <#if courseInfo.learning == 1>
                       	 已报名
                    <#else>
                      	立即报名
                    </#if>
                <#else>
                  	开始学习
                </#if>
            </button>
            
        <#elseif courseInfo.watchState == 0>
            <#if courseInfo.type ==4 && courseInfo.cutoff = 1>
                <button type="button" class="immediately-buy" style="background:#DEDEDE;">报名截止</button>
            <#else>
                <button type="button" class="immediately-buy J-course-buy" data-id="${courseInfo.id}" data-type="${courseInfo.type}">
                 	   立即购买
                </button>
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
                        <li><a href="javascript:void(0);">选集</a></li>
                        <li><a href="javascript:void(0);">详情</a></li>
                    <#elseif courseInfo.watchState = 0>
                        <li><a href="javascript:void(0);">详情</a></li>
                        <li><a href="javascript:void(0);">课程大纲</a></li>
                    </#if>
                <#else> <#-- 非专辑tab显示    -->
                    <li><a href="javascript:void(0);">详情</a></li>
                </#if>
                    <li><a href="${webUrlParam}/comment">评价（${courseInfo.criticizeCount}）</a></li>
                    <li><a href="javascript:void(0);">常见问题</a></li>
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
                                <a href="/web/html/ccvideo/liveVideoAlbum.html?collectionId=${courseInfo.id}&courseId=${collectionItem.id}"
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
                                        <p>${collectionItem.courseLength}</p>
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
                <div class="author-introduce">
                    主讲人
                </div>
                <div class="author-content">
                    <div class="author-text">
                    <#if courseInfo.lecturerDescription??>
                    	${courseInfo.lecturerDescription}
                    <#else>
                        <!--无数据时显示背景图-->
                        <div class="all-null course-null">
                            <div class="null-img">
                                <img src="/web/images/icon-nodata.png"/>
                            </div>
                            <p>暂无主讲人介绍</p>
                        </div>
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
                        <!--无数据时显示背景图-->
                        <div class="all-null course-null">
                            <div class="null-img">
                                <img src="/web/images/icon-nodata.png"/>
                            </div>
                            <p>暂无数据</p>
                        </div>
                    </#if>

                    </div>
                </div>
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
                <#if courseInfo.description??>
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

<script type="text/javascript" src="/web/js/footer.js"></script>
<!--公共头部和底部结束-->

<!--登陆结束-->
<script type="text/javascript" charset="utf-8">
    var type = "${type}";
    var watchState = "${courseInfo.watchState}";
    var courseId = "${courseInfo.id}";
    var userId = "${courseInfo.userLecturerId}";
    var courseType = "${courseInfo.type}";
    var collection = ${courseInfo.collection?string(1,0)};
    <#if criticizesMap??> 
         var commentCode = ${criticizesMap.commentCode};
	</#if>	
    var courseLength = ${courseInfo.courseLength};
    //    console.info("type：" + type + ";watchState：" + watchState + ";courseId：" + courseId);
    //    console.info("userId：" + userId + ";collection：" + collection+",commentCode:"+commentCode);
</script>
<script src="/web/js/school/course-details.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/js/school/comment.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>