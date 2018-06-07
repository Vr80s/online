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
			<link rel="stylesheet" href="/web/css/school/school-live.css"  />
	</head>
	<body>
<!--左侧精品、免费、最新、养生课程-->		
		<div class="wp">
			<div class="wrap-left z">
				<div class="wrap-banner">
					<#include "../common/banner_common.ftl">
				</div>		
<!--精品课程、免费课程、最新课程、养生课程、-->	
		
		
				<#list courseTypeList as courseTypeItem>
					<div class="main">
						<div class="content-class">	
							<div class="wrap-title">
								<#-- 课程的小标题--> 
								<span>${courseTypeItem.title}</span>
								<#-- 课程列表 页跳转  带上跳转条件 -->
								<p><a href="/courses/list?lineState=${courseTypeItem.lineState}">更多</a>
								<img src="/web/images/rili_icon.png" alt="箭头" /> </p>
							</div>
							
						<#list courseTypeItem.courseList as courseItem>	
							<div class="course clearfix">
								<img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png">
								    <#if courseItem.type == 1 ||  courseItem.type == 2 ||  courseItem.type == 4 > 
										    <a style="cursor:pointer" href="/courses/${courseItem.id}/info" target="_blank">
									   <#elseif courseItem.type == 3>
								            <a style="cursor:pointer" href="/web/liveCoursePage/${courseItem.id}" target="_blank">
									</#if>
									<div class="img"><img src="${courseItem.smallImgPath}"></div>
								    <#if courseItem.type == 3>
							          <#if courseItem.lineState  == 1  > 
								        <span class="classCategory">直播中</span>
									  <#elseif courseItem.lineState  == 2>
									      <span class="classCategory">预告</span>
									  <#elseif courseItem.lineState  == 3 ||  courseItem.lineState == 5 ||  courseItem.lineState == 6>
									      <span class="classCategory">直播回放</span>
									  <#elseif courseItem.lineState  == 4>
							             <span class="classCategory">即将直播</span>
							         </#if>
									</#if>
									<div class="detail">
										<p class="title" data-text="音频测试3" title="音频测试3">${courseItem.gradeName}</p>
										<p class="timeAndTeac"><span class="teacher">${courseItem.name}</span>
										</p>
										<p class="info clearfix"><span>
										 <#if courseItem.currentPrice gt 0 >
										 	 <span class="price">${courseItem.currentPrice}</span>
										 	 <span>熊猫币</span>
										 <#else> 	 
										 	 <span class="price">免费</span>
										 </#if>
										
										</span>
										<span class="stuCount"><img src="/web/images/studentCount.png" alt="">
										<span class="studentCou">${courseItem.learndCount}</span></span>
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
			    <#if doctorList??>					
					<div class="wrap-docter">
						<span>名师推荐</span>
						<#include "../common/famous_doctor_common.ftl"> 
					</div>
				</#if>
				
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
			<script src="/web/js/school/school-live.js" type="text/javascript" charset="utf-8"></script>
			
	</body>
		
</html>