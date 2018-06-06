<!DOCTYPE html>
<html lang="en">

	<head>
		<meta http-equiv="X-UA-Compatible" content="IEedge" />
		<meta charset="UTF-8">
		<title>熊猫中医云课堂 - 线上中医教育</title>
		<link rel="shortcut icon" href="/favicon.ico" />
		<meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医" />
		<meta name="description" content="熊猫中医云课堂为。课程大纲全新优化，内容有广度、有深度，顶尖讲师全程直播授课。专注整合优势教学资源、打造适合在线学习并能保证教学结果的优质教学产品，同时打造和运营一整套教育生态软件体系，为用户提供满足自身成长和发展要求的有效服务。" />
		<meta name="renderer" content="webkit">
		<meta name="baidu-site-verification" content="UHaAQAeAQF" />
		<!--公共头部和底部样式-->
			<link rel="stylesheet" href="/web/html/school/school-header/header.css" />
			<link rel="stylesheet" href="/web/css/footer.css" />
			<link rel="stylesheet" href="/web/font/iconfont.css" />
		<!--公共头部和底部样式结束-->
			<link href="/web/bootstrap-select/bootstrap.min.css" rel="stylesheet">
			<link rel="stylesheet" href="/web/css/mylogin.css"/>
			<!--分页样式-->
			<link rel="stylesheet" href="/web/css/ftl-page.css"/>
			<!--分页样式-->
			<link rel="stylesheet" href="/web/css/school/anchor-details.css"  />
	</head>
	<body>
		<div class="wrap-top">
			<div class="main-top">
				<div class="head-portrait z">
					<img src="${lecturerInfo.small_head_photo}"   />

				</div>
					<span class="icon-adopt"></span>
				
				<div class="anchor-status z">
					<h2>${lecturerInfo.name?default('')}</h2>
					<#if lecturerInfo.type == 1 && hospital?? && hospital.name??>
						<h4>${hospital.name}</h4>
					</#if>
					<ul class="follow-box cl">
						<li>关注<span id="focusCount">${fansCount}</span></li>
						<li>|</li>
						<li>粉丝<span id="fansCount">${focusCount}</span></li>
					</ul>
					<#if isFours == 0>
						<button type="button" class="isAdd-follow">
						  <img src="../../web/images/icon-up.png"/>
						  <span>加关注</span>
						</button>
					<#elseif isFours == 1>
						<button type="button" class="isAdd-follow  isAdd-active" >
						  <img src="../../web/images/icon-down.png"/>
						  <span>已关注</span>
						</button>
					</#if>
				</div>
			</div>
		</div>
		
		<div class="wp">
			<div class="main">
		<!--左侧详情/评价/常见问题-->
				<div class="content-inf z">
		<!--nav-->
					<div class="wrap-sidebar">
						<ul>
							<li><a href="${webUrlParam}/courses">课程</a></li>
							<li><a href="${webUrlParam}/info">介绍</a></li>
							<li><a href="${webUrlParam}/comment">评价（${criticizeCount}）</a></li>
						</ul>
					</div>
		<!--content-->
		<!--课程-->
		<div class="sidebar-content" style="padding: 0 0 30px;">
			 <#if type == 'courses' >
			  	<#include "anchor_course.ftl">
			 </#if>			
		</div>
		
		<!--介绍-->					
					<div class="sidebar-content hide" style="background: #F8F8F8;padding: 0;">
						<div class="anchor-introduce">
							<p>
								${lecturerInfo.detail?default("")}
							</p>
						</div>
						<div class="anchor-hospital">
							<h5>坐诊医馆</h5>
							<div class="hospital-img z">
							    <#if hospital?? >
									<img src="${hospital.versionPictures}"/>
								<#else> 
								    <img src="${webUrl}/web/images/defaultHead/18.png" />
							    </#if>
							</div>
							<div class="hospital-inf y">
								<div class="hospital-status">
									<#if lecturerInfo.type == 1 && hospital?? && hospital.name??>
										<p>${hospital.name?default("")}</p>
									</#if>
									<p>预约电话：400-800-9999</p>
									<#if lecturerInfo.type == 1 && lecturerInfo.workTime??>
										<p>坐诊时间：${lecturerInfo.workTime?default("")}</p>
									</#if>
								</div>
								<div class="address-box">
									<#if hospital??>
										<p class="address z">
											<span>地</span>
											<span>址：</span>
										</p>
										<p class="address-text z">
											${hospital.province?default("")}
											${hospital.city?default("")}
											${hospital.detailedAddress}
										</p>
									</#if>	
								</div>
							</div>
						</div>
						<#if lecturerInfo.video??>
							<div class="anchor-video">
								<h5>主播视频介绍</h5>
								<div class="save-video">
								</div>
							</div>
						</#if>
					</div>
		<!--评价-->	
		<div class="sidebar-content hide">		
			 <#if type == 'comment' >
			  	<#include "common/comment.ftl">
			 </#if>
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
		
	
		<script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="/web/js/artTemplate.js"></script>	
		<script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
		
		<!--公共头部和底部-->
		<script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
		<script src="/web/html/school/school-header/header.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="/web/js/footer.js"></script>
		<!--公共头部和底部结束-->
		<script  type="text/javascript" >
			var userId = "${userId}";
		    var courseId = 0;
		    var collection = 0;
			<#-- 因为评论主播时不用显示星星的，所以这里就直接默认：不显示就行 -->
			var commentCode = 2;
			
			var type ="${type}"; 
			var video ="";
			<#if lecturerInfo.video??>
			    video = "${lecturerInfo.video}";
			</#if>
		    console.error("type:"+type+",userId:"+userId+",video:"+video);
		</script>
		<script src="/web/js/school/anchor-details.js" type="text/javascript" charset="utf-8"></script>
		<script src="/web/js/school/comment.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>