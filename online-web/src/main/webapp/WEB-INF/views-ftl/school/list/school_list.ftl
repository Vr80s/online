<!-- 导入自定义ftl -->
<#import "../../page.ftl" as cast/>
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
			<link rel="stylesheet" href="/web/css/ftl-page.css"/>
			<link rel="stylesheet" href="/web/font/iconfont.css" />
		<!--公共头部和底部样式结束-->
		<!--登陆的bootstrap样式-->
			<link rel="stylesheet" href="/web/css/mylogin.css" />
			
			<link href="/web/css/bootstrap.min.css" rel="stylesheet">

		<!--登陆的bootstrap样式-->
			<link rel="stylesheet" href="/web/css/school/curriculum-list.css"  />
	</head>
	
	<body>
	<header>
<#include "../header-body.ftl">
</header>
		<div class="wp" >
			<div class="wrap-screen">
				<p class="class-title">课程列表</p>
				<div class="wrap-list">
					<ul class="select-all">
						<#if courseMenuList?? && courseMenuList?size gt 0>					
							<li>
								<dl id="select-kind">
									<dt>分类 :</dt>
									<a href="${replaceUrl(webUrlParam,'menuType',0)}">
									  <dd class="select-all" subject ="menuType" data-id="0">全部</dd>
									</a>
									<#list courseMenuList as courseMenu>
										<a href="${replaceUrl(webUrlParam,'menuType',courseMenu.id)}"><dd subject ="menuType" data-id="${courseMenu.id}">${courseMenu.name}</dd></a>
									</#list>	
								</dl>
							</li>
						</#if>
						<#if courseTypeEnum?? && courseTypeEnum?size gt 0>
							<li>
								<dl id="select-style">
									<dt>类型 :</dt>
									<a href="${replaceUrl(webUrlParam,'courseType',0)}">
									<dd class="select-all" subject ="courseType" data-id="0">全部</dd>
									</a>
									<#list courseTypeEnum as courseType>
										<a href="${replaceUrl(webUrlParam,'courseType',courseType.id)}">
										<dd subject ="courseType" data-id="${courseType.id}">${courseType.name}</dd>
										</a>
									</#list>	
								</dl>
							</li>
						</#if>
						<#if liveStatusEnum?? && liveStatusEnum?size gt 0>
							<li>
								<dl id="select-status">
									<dt>状态 :</dt>
									<a href="${replaceUrl(webUrlParam,'lineState',0)}">
									<dd class="select-all" subject ="lineState" data-id="0">全部</dd>
									</a>
									<#list liveStatusEnum as liveStatus>
										<a href="${replaceUrl(webUrlParam,'lineState',liveStatus.id)}">
											<dd subject ="lineState" data-id="${liveStatus.id}">${liveStatus.name}</dd>
										</a>
									</#list>	
								</dl>
							</li>
						</#if>
						<#if freeTypeEnum?? && freeTypeEnum?size gt 0>
							<li>
								<dl id="select-price">
									<dt>收费 :</dt>
									<#list freeTypeEnum as freeType>
									<a href="${replaceUrl(webUrlParam,'isFree',freeType.id)}">
										<dd subject ="isFree" data-id="${freeType.id}">${freeType.name}</dd>
										</a>		
									</#list>
								</dl>
							</li>
						</#if>
						<#if cityList?? && cityList?size gt 0>
							<li>
								<dl id="select-address">
								    <a href="${replaceUrl(webUrlParam,'city',"")}"> 
									<dt>城市 :</dt>
									</a>
									<#list cityList as city>
									<a href="${replaceUrl(webUrlParam,'city',city.cityName)}">
										<dd subject ="city" data-id="${city.cityName!''}">${city.cityName}</dd>
									</a>	
									</#list>
								</dl>
							</li>
						</#if>
						<li>
							<dl id="select-condition">
								<dt>筛选条件 :</dt>
							</dl>
							<form action="/courses/list" id="queryKeyFrom" method="get">
							    <input type="hidden"  name="isFree" value="">
                                <input type="hidden" name="menuType" value="">
                                <input type="hidden" name="courseType" value="">
                                <input type="hidden" name="lineState" value="">
                                <input type="hidden" name="city" value="">
								<p class="author-search z">
									<input type="text" name="queryKey" id="search-text" value="" placeholder="如：朱小宝" />
									<button type="submit"></button>
								</p>
							</form>
							<a href="${replaceUrl(webUrlParam,"","")}" class="reset-btn">重置</a>
						</li>
					</ul>
				</div>
			</div>
<!--视频-->			
			<div class="main">
				<div class="wrap-tab">
					<ul>
						<li><a href="${replaceUrl(webUrlParam,"sortOrder",1)}">综合排序</a></li>
						<li><a href="${replaceUrl(webUrlParam,"sortOrder",2)}">最新</a></li>
						<li><a href="${replaceUrl(webUrlParam,"sortOrder",3)}">人气</a></li>	
					</ul>
					<div class="tab-price z">
						<p>价格</p>
						<a href="${replaceUrl(webUrlParam,"sortOrder",4)}">
						 <span class="glyphicon glyphicon-menu-up tab-top" aria-hidden="true" style="color:#333"> </a>
						</span>
						<a href="${replaceUrl(webUrlParam,"sortOrder",5)}">
						   <span class="glyphicon glyphicon-menu-down tab-bottom" aria-hidden="true" style="color:#333"></a>
						</span>
					</div>
				</div>
			
				<#if courseList.records?size gt 0> 				
				
				<div class="wrap-video">	
				  
				 <#list courseList.records as courseItem>			
					<div class="course clearfix">
						<!-- <img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png">-->
						
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
					
						   <#if courseItem.type == 1  > 
						      <#if courseItem.collection> 
						         <span class="classCategory">视频专辑</span>
							  <#elseif !courseItem.collection>
					              <span class="classCategory">视频</span>
					          </#if>
							 
						   <#elseif courseItem.type == 2>
						      <#if courseItem.collection> 
						       <span class="classCategory">音频专辑</span>
							  <#elseif !courseItem.collection>
					             <span class="classCategory">音频</span>
					          </#if>
						      
						   <#elseif courseItem.type == 3>
					          <#if courseItem.lineState  == 1  > 
						        <span class="classCategory">直播中</span>
							  <#elseif courseItem.lineState  == 2>
							      <span class="classCategory">预告</span>
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
				 <!-- 使用该标签 -->	
					<div class="cl" style="overflow: hidden;"></div>
				 
				 <#if (webUrlParam?contains('?'))>
				 	<@cast.page pageNo=courseList.current totalPage=courseList.pages showPages=5 callUrl="${webUrlParam}&page="/>
				 <#else>
					 <@cast.page pageNo=courseList.current totalPage=courseList.pages showPages=5 callUrl="${webUrlParam}?page="/>
				 </#if>
				</div>
				
				<#else>
				 <!--无数据时显示背景图-->
				 <div class="all-null class-null">
				 	<div class="null-img">
				 		<img src="/web/images/other_noResult.png"/>
				 	</div>
				 	<p>更多精彩课程正在更新中,敬请期待...</p>
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
	    <script  type="text/javascript" >
		      var webUrlParam = "${webUrlParam}";
		</script>
			<script src="/web/js/school/curriculum-list.js" type="text/javascript" charset="utf-8"></script>
	
		
	
	</body>
		
</html>