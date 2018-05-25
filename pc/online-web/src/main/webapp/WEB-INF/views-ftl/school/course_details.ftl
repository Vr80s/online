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
		<!--登陆的bootstrap样式-->
			<link rel="stylesheet" href="/web/css/mylogin.css" />
			<link href="/web/bootstrap-select/bootstrap.min.css" rel="stylesheet">

		<!--登陆的bootstrap样式-->
			<link rel="stylesheet" href="/web/css/school/details-album.css"  />
	</head>
	<body>
		<div class="wp">
			<div class="wrap-buy">
				<div class="left-cover z">
					<img src="${courseInfo.smallImgPath}"/>
				</div>
				<div class="right-details y">
					<h4>${courseInfo.gradeName}</h4>
					<p class="subtitle">${courseInfo.subtitle}</p>
					<ul class="author-inf">
						<li>
							<span>主讲人：${courseInfo.name}</span>
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
							<p><span>免费</span></p>
						</#if>
						
						<#-- 根据不同的课程类型，显示不同的课程介绍 -->
						
						<#if courseInfo.type == 1 || courseInfo.type == 2>
						    <#if courseInfo.collection >
						      	<ul>
									<li>更新时间</li>
									<li>共${courseInfo.courseNumber}集</li>
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
							<p class="under-address">上课地址<span>${courseInfo.address}</span></p>
						</#if>
					</div>
					
					<#if courseInfo.watchState == 1 || courseInfo.watchState == 2>  
						<button type="button" class="immediately-buy">进入学习</button>
					<#elseif courseInfo.watchState == 0> 
						<button type="button" class="immediately-buy">立即购买</button>
					</#if>
				</div>
			</div>
		
			<div class="main">
		<!--左侧详情/评价/常见问题-->
				<div class="content-inf z">
		<!--nav-->
					<div class="wrap-sidebar">
						<ul>
						<#-- tab的显示，这个就当做专辑页面来写   -->
						
						    <#-- 免费或已购买  -->
						    <#if courseInfo.watchState == 1 || courseInfo.watchState == 2>  
							    <li><a href="${webUrlParam}/selection">选集</a></li>
								<li><a href="${webUrlParam}/info">详情</a></li>
							<#-- 未购买    -->
							<#elseif courseInfo.watchState == 0> 
								<li><a href="${webUrlParam}/info" >详情</a></li>
								<li><a href="${webUrlParam}/outline" >课程大纲</a></li>
							</#if>
							<li ><a href="${webUrlParam}/comment" >评价（${courseInfo.criticizeCount}）</a></li>
							<li><a href="${webUrlParam}/aq" >常见问题</a></li>	
						
						</ul>
					</div>
		<!--content-->
		<!--选集-->
				  <div class="sidebar-content buy_tab hide"  style="padding: 0;">
						<div class="wrap-anthology">
							<ul>
								<li>
									<div class="play-img z">
										<img src="../../web/images/icon-play.png"/>
									</div>
									<div class="play-album z">
										<p>郝万山 《伤寒论》讲解</p>
										<p>120分钟</p>
									</div>
									
								</li>
								<li>
									<div class="play-img z">
										<img src="../../web/images/icon-play.png"/>
									</div>
									<div class="play-album z">
										<p>郝万山 《伤寒论》讲解</p>
										<p>120分钟</p>
									</div>
								</li>
							</ul>
						</div>
					</div>
		
		<!--详情-->
					<div class="sidebar-content">
						<div class="author-introduce">
							主讲人
						</div>
						<div class="author-content">
							<div class="author-text">
							    <#if courseInfo.lecturerDescription??>
									${courseInfo.lecturerDescription}							    
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
							    </#if>	
							</div>
						</div>
					</div>
		
		<!--课程大纲-->
				
					<div class="sidebar-content no_buy_tab hide">
						    <#if courseInfo.courseOutline??>
									${courseInfo.courseOutline}							    
							 </#if>	
					</div>
			
		<!--评价-->			
					<div class="sidebar-content hide">
						<#include "../common/comment.ftl">
					</div>
		<!--常见问题-->					
					<div class="sidebar-content hide">
						<ul class="often-problem">
							${commonProblem}
						</ul>
					</div>
				</div>
			
		<!--右侧推荐课程-->
				<div class="wrap-recommend y">
					<h3>推荐课程</h3>
					
					<#list recommendCourse as courseItem>
						<div class="course clearfix">
							<a style="cursor:pointer" href="/course/courses/611" target="_blank">
							
								<div class="img"><img src="${courseItem.smallImgPath}"></div>
								<div class="detail">
									<p class="title" data-text="音频测试3" title="音频测试3">
									
								   <#if courseItem.type == 1  > 
								      <span class="classCategory">视频</span>
								   <#elseif courseItem.type == 2>
								      <span class="classCategory">音频</span>
								   <#elseif courseItem.type == 3>
							          <#if courseItem.lineState  == 1  > 
								        <span class="classCategory">直播中</span>
									  <#elseif courseItem.lineState  == 2>
									      <span class="classCategory">预告</span>
									  <#elseif courseItem.lineState  == 3>
									      <span class="classCategory">直播回放</span>
									  <#elseif courseItem.lineState  == 4>
							             <span class="classCategory">即将直播</span>
							          </#if>
								   <#elseif courseItem.type == 4>
								      <span class="classCategory">线下培训班</span>
								   </#if>
									</p>
									
									<p class="timeAndTeac"><span class="teacher">${courseItem.name}</span>
									</p>
									<p class="info clearfix"><span><span class="price">${courseItem.currentPrice}</span><span>熊猫币</span></span>
									<span class="stuCount"><img src="/web/images/studentCount.png" alt=""><span class="studentCou">
										${courseItem.learndCount}</span></span>
									</p>
								</div>
							</a>
						</div>	
					</#list>	
					
					
					
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

		<!--登陆结束-->	
	   <script  type="text/javascript" charset="utf-8">
       		var type ="${type}";  
       		var watchState ="${courseInfo.watchState}";  
       		var courseId ="${courseInfo.id}";  
       		var userId ="${courseInfo.userLecturerId}"; 
       		var collection =${courseInfo.collection?string(1,0)}; 
       	    console.error("type："+type+";watchState："+watchState+";courseId："+courseId+";userId："+userId+";collection："+collection);
       </script>
		<script src="/web/js/school/course-details.js" type="text/javascript" charset="utf-8"></script>	
	
			
	</body>
		
</html>