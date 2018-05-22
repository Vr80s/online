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
							<img src="../../images/star.png"/>
						</li>
						<li>
							<span>${courseInfo.criticizeCount}200条评论</span>
						</li>
					</ul>
					<div class="display-price">
						<div class="under-price">
							<span>价</span>
							<span>格</span>
						</div>
						
						<#if courseInfo.watchState == 0 || courseInfo.watchState == 2>  
							<p><span>200</span>熊猫币</p>
						<#elseif courseInfo.watchState == 1> 
							<p><span>免费</span></p>
						</#if>
						
						<#-- 根据不同的课程类型，显示不同的课程介绍 -->
						
						<#if courseInfo.type == 1 || courseInfo.type == 2>
						    <#if courseInfo.collection >
						      	<ul>
									<li>更新时间</li>
									<li>共${courseNumber}集</li>
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
							    <li>选集</li>
								<li>详情</li>
							<#-- 未购买    -->
							<#elseif courseInfo.watchState == 0> 
								<li>详情</li>
								<li>课程大纲</li>
							</#if>
							<li >评价（${courseInfo.criticizeCount}）</li>
							<li>常见问题</li>	
						
						</ul>
					</div>
		<!--content-->
		<!--选集-->
				  <div class="sidebar-content buy_tab hide"  style="padding: 0;">
						<div class="wrap-anthology">
							<ul>
								<li>
									<div class="play-img z">
										<img src="../../images/icon-play.png"/>
									</div>
									<div class="play-album z">
										<p>郝万山 《伤寒论》讲解</p>
										<p>120分钟</p>
									</div>
									
								</li>
								<li>
									<div class="play-img z">
										<img src="../../images/icon-play.png"/>
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
					
					
						<div class="impression-content">
							<ul class="impression-list">
								<li>
									<span>总体印象</span>
									<img src="../../images/star.png"/>	
								</li>
								<li>
									<span>节目内容</span>
									<img src="../../images/smile.png"/>	
								</li>
								<li>
									<span>主播演绎</span>
									<img src="../../images/smile.png"/>	
								</li>
							</ul>
							<p class="want-evaluate">我要评价</p>
							<ul class="impression-write cl">
								<li>很赞(2)</li>
								<li>干货很多(26)</li>
								<li>超值推荐(35)</li>
								<li>喜欢(2)</li>
								<li>买对了(2)</li>
							</ul>
						</div>
						<div class="wrap-comment">
			<!--一个完整的评论内容-->
							<div class="container-comment">
								<div class="wrap-portrait z">
									<div class="header-img">
										<img src="../../images/criticism_img.png"/>
									</div>
									<p>已购</p>
								</div>
								<div class="right-comment y">
									<ul class="user-name">
										<li>中医小精灵</li>
										<li>2017-12-09</li>
										<li><img src="../../images/star.png"/></li>
									</ul>
									<p class="write-text cl">
										这里是评论的内容这里是评论的内容这里是评论的内容这里是评论的内容这里是评论的
										内容这里是评论的内容这里是评论的内容这里是评论的内容
									</p>
				<!--回复的内容-->
									<div class="wrap-reply y">
										<div class="header-reply z">
											<img src="../../images/209809998651228423.png"/>
										</div>
										<div class="content-reply y">
											<ul class="name-reply">
												<li>可乐</li>
												<li>2017-08-23</li>
											</ul>
											<p class="reply-text cl">
												这里是评论的内容这里是评论的内容这里是评论的内容这里是评论的内容这里是评论的
												内容这里是评论的内容这里是评论的内容这里是评论的内容							
											</p>
										</div>
									</div>
				<!--回复点赞按钮-->
									<ul class="operation-reply">
										<li>
											<span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>			
											<span>20</span>
										</li>
										<li class="reply-icon">
											<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>												
										</li>
									</ul>
				<!--回复的输入框-->
									<div class="wrap-input cl hide">
										<div class="header-input z">
											<img src="../../images/criticism_img.png"/>
										</div>
										<div class="input-write z">
				<!--未登录-->
											<p class="">
												写下你的评论，请先
												<span>登陆</span>
											</p>
				<!--已登陆-->
											<input class="hide" type="text" name="" value="" />
										</div>
				<!--未登录按钮-->
										<button class="no-login y" type="button">回复</button>
				<!--已登陆按钮-->
										<button class="login y hide" type="button">回复</button>										
									</div>
								</div>
							</div>
							
			<!--一个完整的评论内容结束-->
						</div>	
						
				<!--更多按钮添加-->
						<div class="more-evaluate">
							<button type="button">更多</button>
						</div>
				<!--更多按钮添加结束-->
						
						
					</div>
		<!--常见问题-->					
					<div class="sidebar-content hide">
						<ul class="often-problem">
							<li>
								<h5>视频可以多次重复观看吗？</h5>
								<p>我们的视频均可以进行多次重复观看，观众可根据自己的时间对学习过程进行安排，最大化地利用时间。</p>
							</li>
							<li>
								<h5>视频可以多次重复观看吗？</h5>
								<p>我们的视频均可以进行多次重复观看，观众可根据自己的时间对学习过程进行安排，最大化地利用时间。</p>
							</li>
							<li>
								<h5>视频可以多次重复观看吗？</h5>
								<p>我们的视频均可以进行多次重复观看，观众可根据自己的时间对学习过程进行安排，最大化地利用时间。</p>
							</li>
							<li>
								<h5>视频可以多次重复观看吗？</h5>
								<p>我们的视频均可以进行多次重复观看，观众可根据自己的时间对学习过程进行安排，最大化地利用时间。</p>
							</li>
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
									<span class="stuCount"><img src="/web/images/studentCount.png" alt=""><span class="studentCou">${courseItem.learndCount}</span></span>
									</p>
								</div>
							</a>
						</div>	
					</#list>	
					
					
					
				</div>
			</div>	
		</div>
<!--发表评论弹窗-->

		<div class="bg-modal hide"></div>
		<div class="wrap-modal hide">
			<p class="close-impression">
				<img src="../../images/qxCloser.png"/>
			</p>
			<h4>我要评价</h4>
				<div class="impression-comment impression-star cl">
					<p>总体印象</p>
					<img src="../../images/star-dim.png">
					<img src="../../images/star-dim.png">
					<img src="../../images/star-dim.png">
					<img src="../../images/star-dim.png">
					<img src="../../images/star-dim.png">
					<span></span>
				</div>
				<div class="impression-comment impression-face cl">
					<p>节目内容</p>
					<img src="../../images/gs.png">
					<img src="../../images/gs.png">
					<img src="../../images/gs.png">
					<img src="../../images/gs.png">
					<img src="../../images/gs.png">
					
					<span></span>
				</div>
				<div class="impression-comment impression-show cl">
					<p>主播演绎</p>
					<img src="../../images/gs.png">
					<img src="../../images/gs.png">
					<img src="../../images/gs.png">
					<img src="../../images/gs.png">
					<img src="../../images/gs.png">
					<span></span>
				</div>	

			<ul class="impression-setlist">
				<li>很赞</li>
				<li>干货很多</li>
				<li>超值推荐</li>
				<li>喜欢</li>
				<li>买对了</li>
			</ul>
			<textarea class="impression-text" name="" rows="" cols="" placeholder="写评价给主播鼓励一下吧~"></textarea>
			<button class="submission" type="button">发表评价</button>
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
       </script>
		<script src="/web/js/school/course-details.js" type="text/javascript" charset="utf-8"></script>	
	
			
	</body>
		
</html>