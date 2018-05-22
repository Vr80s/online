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
			<link rel="stylesheet" href="/web/css/school/school-under.css"  />
	</head>
		<body>
<!--左侧精品、免费、最新、养生课程-->		
		<div class="wp">
			<div class="wrap-left z">
				<div class="wrap-banner">
					<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					  <ol class="carousel-indicators">
					   	<#list bannerList as banner>
					     	<#if banner_index == 0 >
					     		<li data-target="#carousel-example-generic" data-slide-to="${banner_index}" class="active"></li>
					     	<#else>
					     		<li data-target="#carousel-example-generic" data-slide-to="${banner_index}"></li>
					     	</#if>
						</#list>
					  </ol>
					  <div class="carousel-inner" role="listbox">
					    <#list bannerList as banner>
					     	<#if banner_index == 0 >
					     		<div class="item active">
					     	<#else>
					     		<div class="item">
					     	</#if>
								<a href="">
						     		<img src="${banner.imgPath}" alt="广告图">
						     	</a>
						    </div>	
						</#list>
					  </div>	
					</div>
				</div>
<!--全国课程-->			

				<#list courseTypeList as courseTypeItem>
					<div class="main">
						<div class="content-class">	
							<div class="wrap-title">
								<#-- 课程的小标题--> 
								<span>${courseTypeItem.title}</span>
								<#-- 课程列表 页跳转  带上跳转条件 -->
								<p><a href="/courses/list?city=${courseTypeItem.title}">更多</a>
								<img src="/web/images/rili_icon.png" alt="箭头" /> </p>
							</div>
							
							<#list courseTypeItem.courseList as courseItem>
								<div class="course clearfix">
									<#-- 推荐课程的标记  -->
									<img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png">
									<a style="cursor:pointer" href="/course/courses/${courseItem.id}" target="_blank">
										<div class="img"><img src="${courseItem.smallImgPath}"></div>
										<span class="classCategory">线下课程</span>
										<div class="detail">
											<p class="title" data-text="音频测试3" title="音频测试3">${courseItem.gradeName}</p>
											<p class="timeAndTeac">
												<span class="teacher">${courseItem.name}</span>
												<span class="y">${courseItem.city}</span>
											</p>
											<p class="info clearfix"><span><span class="price">${courseItem.currentPrice}</span><span>熊猫币</span>
											</span><span class="stuCount"><img src="/web/images/studentCount.png" alt="">
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
	
<!--右侧名师推荐-->
			<div class="wrap-right y">				
				<div class="wrap-docter">
					<span>名师推荐</span>
					<ul>
						<#list doctorList as doctorInfo>
	                        <li>
								<img src="${doctorInfo.headPortrait}" alt="名医头像"/>
								<p data-id ="${doctorInfo.userId}">${doctorInfo.name}</p>
							</li> 
	                    </#list>
					</ul>
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
			<script src="/web/js/school/school-under.js" type="text/javascript" charset="utf-8"></script>
		
	</body>
		
</html>