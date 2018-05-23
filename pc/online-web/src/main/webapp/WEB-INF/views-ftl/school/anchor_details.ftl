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
			<link rel="stylesheet" href="/web/css/school/anchor-details.css"  />
	</head>
	<body>
		<div class="wrap-top">
			<div class="main-top">
				<div class="head-portrait z">
					<img src="../../images/criticism_img.png"/>
					<span></span>
				</div>
				<div class="anchor-status z">
					<h2>${lecturerInfo.name}</h2>
					
					<#if lecturerInfo.type == 1>
						<h4>${hospital.name??}</h4>
					</#if>
					<ul class="follow-box cl">
						<li>关注${fansCount}</li>
						<li>|</li>
						<li>粉丝${focusCount}</li>
					</ul>
					<#if isFours == 1>
						<button type="button" class="isAdd-follow  isAdd-active">
						  <img src="../../images/icon-up.png"/>
						  <span>加关注</span>
						</button>
					<#elseif ifFours == 0>	
						<button type="button" class="isAdd-follow">
						  <img src="../../images/icon-down.png"/>
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
							<li class="active-footer"><a href="${webUrlParam}/courses">课程</a></li>
							<li><a href="${webUrlParam}/info">介绍</a></li>
							<li><a href="${webUrlParam}/comment">评价（200）</a></li>
						</ul>
					</div>
		<!--content-->
		<!--课程-->
					<div class="sidebar-content" style="padding: 0 0 30px;">
						<div class="wrap-class">
						
						    <#if courseList??>
						        <#list courseList as courseItem>
						           <div class="course clearfix">
										<img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png">
										<a style="cursor:pointer" href="/course/courses/611" target="_blank">
											<div class="img">
											<img src="${courseItem.smallImgPath}"></div>
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
									          </#if>
										    <#elseif courseItem.type == 4>
										      <span class="classCategory">线下培训班</span>
										    </#if>
											<div class="detail">
												<p class="title" data-text="音频测试3" title="音频测试3">${courseItem.gradeName}</p>
												<p class="timeAndTeac"><span>讲师：<span class="teacher">${courseItem.name}</span></span>
												</p>
												<p class="info clearfix"><span><span class="price">${courseItem.currentPrice}</span><span>熊猫币</span></span>
												 <span class="stuCount"><img src="/web/images/studentCount.png" alt="">
												 <span class="studentCou">${courseItem.learndCount}</span></span>
												</p>
											</div>
										</a>
									</div>		
						      	</#list>
							</#if>							
						</div>
					</div>
		
		<!--介绍-->					
					<div class="sidebar-content hide" style="background: #F8F8F8;padding: 0;">
						<div class="anchor-introduce">
							<p>
								${lecturerInfo.detail}
							</p>
						</div>
						<div class="anchor-hospital">
							<h5>坐诊医馆</h5>
							<div class="hospital-img z">
								<img src="../../images/04.png"/>
							</div>
							<div class="hospital-inf y">
								<div class="hospital-status">
									<#if lecturerInfo.type == 1 && hospital.name??>
										<p>${hospital.name}</p>
									</#if>
									<p>预约电话：400-800-9999</p>
									<#if lecturerInfo.type == 1 && lecturerInfo.workTime??>
										<p>坐诊时间：${lecturerInfo.workTime}</p>
									</#if>
								</div>
								<div class="address-box">
									<p class="address z">
										<span>地</span>
										<span>址：</span>
									</p>
									<p class="address-text z">
										${hospital.province}${hospital.city}${hospital.detailedAddress}
									</p>
								</div>
							</div>
						</div>
						<div class="anchor-video">
							<h5>主播视频介绍</h5>
							<div class="save-video">
								<img src="../../images/04.png" style="width: 460px; height: 260px;"/>
							</div>
						</div>
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
			<!--更多按钮添加-->
						<div class="more-evaluate">
							<button type="button">更多</button>
						</div>
				<!--更多按钮添加结束-->
						</div>	
					</div>
				
				</div>
		
			
		<!--右侧推荐课程-->
				<div class="wrap-recommend y">
					<h3>推荐课程</h3>
					<div class="course clearfix">
						<!--<img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png">-->
						<a style="cursor:pointer" href="/course/courses/611" target="_blank">
							<div class="img">
								<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/17/14/f8b9b157ae4e4b9d9730607ecdcdac71.png">
							</div>
							<!--<span class="classCategory">音频</span>-->
							<div class="detail">
								<p class="title" data-text="音频测试3" title="音频测试3">音频测试3</p>
								<p class="timeAndTeac"><span>讲师：<span class="teacher">雪灵</span></span>
								</p>
								<p class="info clearfix">
									<span>
										<span class="price">1</span>
										<span>熊猫币</span>
									</span>
									<span class="stuCount">
										<img src="/web/images/studentCount.png" alt="">
										<span class="studentCou">15</span>
									</span>
								</p>
							</div>
						</a>
					</div>	
					<div class="course clearfix">
						<!--<img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png">-->
						<a style="cursor:pointer" href="/course/courses/611" target="_blank">
							<div class="img">
								<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/17/14/f8b9b157ae4e4b9d9730607ecdcdac71.png">
							</div>
							<!--<span class="classCategory">音频</span>-->
							<div class="detail">
								<p class="title" data-text="音频测试3" title="音频测试3">音频测试3</p>
								<p class="timeAndTeac"><span>讲师：<span class="teacher">雪灵</span></span>
								</p>
								<p class="info clearfix">
									<span>
										<span class="price">1</span>
										<span>熊猫币</span>
									</span>
									<span class="stuCount">
										<img src="/web/images/studentCount.png" alt="">
										<span class="studentCou">15</span>
									</span>
								</p>
							</div>
						</a>
					</div>
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
					<img src="../../web/images/star-dim.png">
					<img src="../../web/images/star-dim.png">
					<img src="../../web/images/star-dim.png">
					<img src="../../web/images/star-dim.png">
					<img src="../../web/images/star-dim.png">
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
		<script  type="text/javascript" >
			var userId = ${type};
			var type = ${userId};
		    var video = ${lecturerInfo.video};
		    console.error("type:"+type+",userId:"+userId+",video:"+video);
		</script>
		<script src="/web/js/school/anchor-details.js" type="text/javascript" charset="utf-8"></script>
	</body>
		
</html>