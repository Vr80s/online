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
			<link rel="stylesheet" href="/web/html/school/school-header/header.css?version=b504aa0ea9" />
			<link rel="stylesheet" href="/web/css/footer.css?version=c40fece58d" />
			<link rel="stylesheet" href="/web/font/iconfont.css?version=62fcddd84e" />
		<!--公共头部和底部样式结束-->
		<!--登陆的bootstrap样式-->
			<link rel="stylesheet" href="/web/css/mylogin.css?version=61637524d6" />
			<link href="/web/bootstrap-select/bootstrap.min.css" rel="stylesheet">

		<!--登陆的bootstrap样式-->
			<link rel="stylesheet" href="/web/css/school/school-recommend.css?version=6b7f0ba64e"  />
			
	</head>
	<body>
<header>
<#include "../header-body.ftl">
</header>
<!--左侧精品、免费、最新、养生课程-->		
		<div class="wp">
			<div class="wrap-left z">
				<div class="wrap-banner">
					<#include "../common/banner_common.ftl">
				</div>
				
				<#if projectList?? && projectList?size gt 0 >
					<div class="physic-type">
						<ul>
							<#list projectList as project>
						     	<#if project_index lt 4 >
						     	 <li>
						     		<a href="">
										<p><img src="${project.icon}" style="width: 54px;height: 54px;" alt="${project.name}" /></p>
										<span>${project.name}</span>
								    </a>
								  </li>  
						        </#if>
							</#list>
						</ul>
					</div>
				</#if>
				
<!--精品课程、免费课程、最新课程、养生课程、-->	

		
				<#list courseTypeList as courseTypeItem>
					<div class="main">
						<div class="content-class">	
							<div class="wrap-title">
							    <#-- 课程的小标题--> 
								<span>${courseTypeItem.title}</span>
								<#-- 课程列表 页跳转  带上跳转条件 -->
								<#if courseTypeItem.title?? && courseTypeItem.title == "最新课程"> 
									<p><a href="/courses/list?menuType=${courseTypeItem.menuType}&sortOrder=2" target="_blank">更多</a>
								<#elseif  courseTypeItem.title?? && courseTypeItem.title == "免费课程">
								    <p><a href="/courses/list?isFree=1" target="_blank">更多</a>
								<#else>
									<p><a href="/courses/list?menuType=${courseTypeItem.menuType}" target="_blank">更多</a>
								</#if>
								<img src="/web/images/rili_icon.png" alt="箭头" /> </p>
							</div>
							<#list courseTypeItem.courseList as courseItem>
								<div class="course clearfix">
									<#-- 推荐课程的标记  -->

										<#if courseItem.recommendSort?? &&  courseItem.recommendSort gt 0>	
											<img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" 
														src="/web/images/recommend2.png">
										</#if>    
									
									   <#if courseItem.type == 1 ||  courseItem.type == 2 ||  courseItem.type == 4 > 
										    <a style="cursor:pointer" href="/courses/${courseItem.id}/info" target="_blank">
									   <#elseif courseItem.type == 3>
								            <a style="cursor:pointer" href="/web/liveCoursePage/${courseItem.id}" target="_blank">
									   </#if>
									
										<div class="img"><img src="${courseItem.smallImgPath}"></div>
										   <#if courseItem.type == 1 > 
										       <#if courseItem.collection> 
										         <span class="classCategory">视频专辑</span>
										       <#else>
										         <span class="classCategory">视频</span>
										       </#if>
										   <#elseif courseItem.type == 2>
										      <#if courseItem.collection> 
										         <span class="classCategory">音频专辑</span>
										      <#else>
										         <span class="classCategory">音频</span>
										      </#if>
										   <#elseif courseItem.type == 3>
									          <#if courseItem.lineState  == 1  > 
										        <span class="classCategory">直播中</span>
											  <#elseif courseItem.lineState  == 2>
											      <span class="classCategory">直播预告</span>
											  <#elseif courseItem.lineState  == 3>
											      <span class="classCategory">直播回放</span>
											  <#elseif courseItem.lineState  == 4>
									             <span class="classCategory">即将直播</span>
									          <#else>   
							            		 <span class="classCategory">暂未开播</span>
									          </#if>
										   <#elseif courseItem.type == 4>
										      <span class="classCategory">线下培训班</span>
										   </#if>
										<div class="detail">
											<p class="title" data-text="音频测试3" title="音频测试3">${courseItem.gradeName}</p>
											<p class="timeAndTeac">
												<span class="teacher">${courseItem.name}</span>
											</p>
											<p class="info clearfix"><span>
											
											 <#if courseItem.currentPrice gt 0 >
											 	 <span class="price">${courseItem.currentPrice}</span>
											 	 <span>熊猫币</span>
											 <#else> 	 
											 	 <span class="price">免费</span>
											 </#if>
											
											<span class="stuCount">
											<img src="/web/images/studentCount.png" alt=""><span class="studentCou">${courseItem.learndCount}</span></span>
											</p>
										</div>
									</a>
								</div>
							</#list>	
					   </div>
				   </div>				
				</#list>
			</div>
	
<!--右侧成为主播、搜索、名师推荐-->
			<div class="wrap-right y">
			
				<div class="wrap-anchor hide" >
					<p>期待有志于传承和发展中医药的医师医馆加入</p>
					<span><a href="${webUrl}/web/html/want-anchor.html" target="_blank" style="color: white;">成为主播</a></span>
				</div>
			
			    
			    <#if hotList??>	
					<div class="hot-search">
						<p>热门搜索</p>
						<ul>
					    <#list hotList as hot>
	                        <li data-id="hot.id">
								<a href="/courses/list?queryKey=${hot.name}" target="_blank">${hot.name}</a>
							</li> 
	                    </#list>
						</ul>
					</div>
				</#if>
				
				 <#if doctorList??>	
					<div class="wrap-docter">
						<span>名师推荐</span>
						<#include "../common/famous_doctor_common.ftl"> 
					</div>
				</#if>
			
			</div>
		</div>

	    <script src="/web/js/jquery-1.12.1.js?version=da6deec5d1" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="/web/js/artTemplate.js?version=8ba0d405af"></script>	
		<script src="/web/js/bootstrap.js?version=32941860bf" type="text/javascript" charset="utf-8"></script>
		<!--公共头部和底部-->
		<script src="/web/js/ajax.js?version=b9420555ff" type="text/javascript" charset="utf-8"></script>
		<script src="/web/html/school/school-header/header.js?version=1fead7f563" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="/web/js/footer.js?version=355911bda4"></script>
		<!--公共头部和底部结束-->
				

		<!--登陆结束-->	
		<script src="/web/js/school/school-recommend.js?version=ff165246cb" type="text/javascript" charset="utf-8"></script>
			
	</body>
		
</html>
