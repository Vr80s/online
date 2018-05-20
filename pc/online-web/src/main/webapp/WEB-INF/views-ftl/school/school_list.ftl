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
			<link href="/web/css/bootstrap.min.css" rel="stylesheet">

		<!--登陆的bootstrap样式-->
			<link rel="stylesheet" href="/web/css/school/curriculum-list.css"  />
	</head>
	<body>
		<div class="wp">
			<div class="wrap-screen">
				<p class="class-title">课程列表</p>
				<div class="wrap-list">
					<ul class="select-all">
					
						<#if courseMenuList?? && courseMenuList?size gt 0>					
							<li>
								<dl id="select-kind">
									<dt>分类 :</dt>
									<dd class="select-all" subject ="menuType">全部</dd>
									<#list courseMenuList as courseMenu>
										<dd subject ="menuType" data-id="${courseMenu.id}">${courseMenu.name}</dd>
									</#list>	
								</dl>
							</li>
						</#if>
						<#if courseTypeEnum?? && courseTypeEnum?size gt 0>
							<li>
								<dl id="select-style">
									<dt>类型 :</dt>
									<dd class="select-all" subject ="courseType">全部</dd>
									<#list courseTypeEnum as courseType>
										<dd subject ="courseType" data-id="${courseType.id}">${courseType.name}</dd>
									</#list>	
								</dl>
							</li>
						</#if>
						<#if liveStatusEnum?? && liveStatusEnum?size gt 0>
							<li>
								<dl id="select-status">
									<dt>状态 :</dt>
									<dd class="select-all" subject ="lineState">全部</dd>
									<#list liveStatusEnum as liveStatus>
											<dd subject ="lineState" data-id="${liveStatus.id}">${liveStatus.name}</dd>
									</#list>	
								</dl>
							</li>
						</#if>
						<#if freeTypeEnum?? && freeTypeEnum?size gt 0>
							<li>
								<dl id="select-price">
									<dt>收费 :</dt>
									<#list freeTypeEnum as freeType>
										<dd subject ="isFree" data-id="${freeType.id}">${freeType.name}</dd>
									</#list>
								</dl>
							</li>
						</#if>
						<#if cityList?? && cityList?size gt 0>
							<li>
								<dl id="select-address">
									<dt>城市 :</dt>
									<#list cityList as city>
										<dd subject ="city" data-id="${city.cityName!''}">${city.cityName}</dd>
									</#list>
								</dl>
							</li>
						</#if>
						<li>
							<dl id="select-condition">
								<dt>筛选条件 :</dt>
							</dl>
							<p class="author-search z">
								<input type="text" id="search-text" value=""
								 placeholder="如：朱小宝" />
								<button type="button"></button>
							</p>
						</li>
					</ul>
				</div>
			</div>
<!--视频-->			
			<div class="main">
				<div class="wrap-tab">
					<ul>
						<li>综合排序</li>
						<li>最新</li>
						<li>人气</li>	
					</ul>
					<div class="tab-price z">
						<p>价格</p>
						<span class="glyphicon glyphicon-menu-up tab-top" aria-hidden="true"></span>
						<span class="glyphicon glyphicon-menu-down tab-bottom" aria-hidden="true"></span>
						
					</div>
				</div>
				<div class="wrap-video">	
				 <#list courseList as courseItem>			
					<div class="course clearfix">
						<!--<img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png">-->
						<a style="cursor:pointer" href="details-video.html">
							<div class="img"><img src="${courseItem.smallImgPath}"></div><span class="classCategory">直播中</span>
							<div class="detail">
								<p class="title" data-text="音频测试3" title="音频测试3">${courseItem.gradeName}</p>
								<p class="timeAndTeac"><span class="teacher">${courseItem.name}</span>
								</p>
								<p class="info clearfix"><span><span class="price">${courseItem.currentPrice}</span><span>熊猫币</span></span>
								<span class="stuCount"><img src="/web/images/studentCount.png" alt="">
								<span class="studentCou">${courseItem.learndCount}</span></span>
								</p>
							</div>
						</a>
					</div>
					<!--<div class="clearfix"></div>-->
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
			<script src="/web/js/school/curriculum-list.js" type="text/javascript" charset="utf-8"></script>
			
	</body>
		
</html>