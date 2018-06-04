<!DOCTYPE html>
<html lang="en">

	<head>
		<!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    
    <![endif]-->
		<meta http-equiv="X-UA-Compatible" content="IEedge" />
		<meta charset="UTF-8">
		<title>熊猫中医云课堂 - 线上中医教育</title>
		<link rel="shortcut icon" href="favicon.ico" />
		<meta name="keywords" content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医" />
		<meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。" />
		<meta name="renderer" content="webkit">
		<meta name="baidu-site-verification" content="UHaAQAeAQF" />
		<link rel="stylesheet" href="/web/css/bootstrap.min.css">
		<link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
		<link rel="stylesheet" href="/web/css/mylogin.css" />
		<link rel="stylesheet" href="/web/css/componet.css" />
		<link rel="stylesheet" href="/web/css/index2.css?v=ipandatcm_1.3" />
		<link rel="stylesheet" href="/web/css/header.css" />
		<link rel="stylesheet" href="/web/css/footer.css" />
		<link rel="stylesheet" href="/web/font/iconfont.css" />
		<!--<link rel="stylesheet" href="/web/css/index-9a525196fb.css" type="text/css">-->
	</head>

	<body>
		<!-- <div class="dianwo">点我</div> -->
		<div class="popover_order">
			<div class="popover_order_bg"></div>
			<div class="order">
				<div class="order_close"><span style="font-size: 32px;position: absolute;right: 10px;">X</span></div>
				<div class="both"></div>
				<div class="order_size">请输入预约手机号</div>
				<div class="order_cell">
					<span>手机号</span>
					<input type="hidden" id="subscribeId" />
					<input type="text" class="phone" placeholder="输入11位阿拉伯数字" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;if(event.keyCode==13) {btnValidate.click();return false;}" />
					<div id="tips" style="top: 154px; display:none ;"><span style="margin-left: 21%;font-size: 15px;color: red;"></span></div>
				</div>

				<input type="button" class="order_affirm" value="确认" id='btnValidate'>
			</div>
		</div>
		<div id="content_body">
			<div class="left_content">
				<!--轮播图-->
				<div id="banner" class="clearfix">
					<div class="slider-container">
						<ul id="slider" class="slider">
						   <#if bannerList??>	
								<#list bannerList as bannerItem >	
									<li data-indexid="${bannerItem.id}" 
										class="cur"
										data-img="${bannerItem.imgPath}"
										<#if bannerList_index == 0>
											style="display: none;"
										</#if>
										>
										<a id="aImg${bannerList_index}" target="_blank"
											href="${bannerItem.imgHref}"
											style="background: url(&quot;${bannerItem.imgPath}&quot;) center top no-repeat;">
									   </a>
								   </li>
								</#list>	
				            </#if>
						</ul>
						   <#if bannerList?? && bannerList == 1>	
					            <div id="left"><em></em></div>
					            <div id="right"><em></em></div>
					        <#elseif bannerList?? && bannerList gt 1>    
					            <div id="left" style="display:none"><em></em></div>
					            <div id="right" style="display:none"><em></em></div>
							</#if>	
					
				            <div id="selector" class="selector">
								<#if bannerList??>	
									<#list bannerList as bannerItem >
									   <#if bannerList_index == 0>
									   	  <span class="cur"></span>
									   <#else> 
									   	  <span></span>
										</#if>
									</#list>	
								</#if>
				            </div>
						</div>
					</div>
				</div>



				<!--在线课程部分-->
				<#if courseTypeList.listLive?size gt 0 >
				
					<div class="online_course">
					<div class="course_title"><span class="title">在线课程</span>
						<a href="javascript:;" class="more">更多&nbsp;></a>
					</div>
					<ul class="online_course_list clearfix">
					
						<#list courseTypeList.listLive as courseItem>
					
						<li class="course">
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
						          </#if>
							   <#elseif courseItem.type == 4>
							      <span class="classCategory">线下培训班</span>
							   </#if>
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
						</li>
						</#list>
					</ul>
				</div>
				</#if>				

				<#if courseTypeList.listReal?size gt 0 >
				<!--线下课程部分-->
				<div class="underline_course">
					<div class="course_title"><span class="title">线下课程</span>
						<a href="javascript:;" class="more">更多&nbsp;></a>
					</div>
					<ul class="online_course_list clearfix">
					
						<#list courseTypeList.listReal as courseItem>
						<li class="course">
							    
							    <#if courseItem.type == 1 ||  courseItem.type == 2 ||  courseItem.type == 4 > 
							 		    <a style="cursor:pointer" href="/courses/${courseItem.id}/info" target="_blank">
								 <#elseif courseItem.type == 3>
							            <a style="cursor:pointer" href="/web/liveCoursePage/${courseItem.id}" target="_blank">
								 </#if>
								
								<div class="img"><img src="src="${courseItem.smallImgPath}""></div>
								
								
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
									<p class="timeAndTeac"><span class="teacher">${courseItem.name}</span>
									</p>
									<p class="info clearfix"><span><span class="price">${courseItem.currentPrice}</span><span>熊猫币</span></span>
										<span class="stuCount"><img src="/web/images/studentCount.png" alt="">
								<span class="studentCou">${courseItem.learndCount}</span></span>
									</p>
								</div>
							</a>
						</li>
						</#list>
					</ul>
				</div>
				</#if>	

				<#if doctorList?size gt 0 >	
					<!--名家坐诊部分-->
					<div class="famous_doctor">
						<div class="course_title"><span class="title">名家坐诊</span>
							<a href="javascript:;" class="more">更多&nbsp;></a>
						</div>
						<ul class="famous_doctor_list clearfix">
							
							<#list doctorList as doctor>
						
							<li class="doctorTpl">
								<a href="${webUrl}/doctors/${doctor.id}" target="_blank"></a>
								<img src="${doctor.headPortrait!''}" alt="${doctor.name}">
								<h5>${doctor.name}&nbsp;<span>${doctor.title?default('暂无')}</span></h5>
								<p>${doctor.workTime}</p>
                      			<p>${doctor.province}&nbsp;${doctor.city}&nbsp; </p>
							</li>
						
							</#list>
						</ul>
					</div>
                </#if>



				<!--头条新闻部分开始-->

			  <#if articles.records?size gt 0 >	
				<div class="topLine_news">
					<div class="course_title"><span class="title">头条新闻</span>
						<a href="javascript:;" class="more">更多&nbsp;></a>
					</div>
					<ul class="topLine_news_list">
					 <#list articles.records as article>	
						<li class="newsTpl clearfix">
							<a href="${webUrl}/headline/details/${article.id}" target="_blank">
								<img class="forum-info-left" src="${article.imgPath}" alt="">
							</a>
							<div class="forum-info-right">
								<div class="forum-info-title">
									<a href="${webUrl}/headline/details/${article.id}" target="_blank">${article.title}</a>
								</div>
								<div class="forum-info-content dot-ellipsis">
								 ${article.content}
								</div>
								<div class="forum-info-tags">
									<span>${article.author!''}<em></em>${(article.createTime?string("yyyy-MM-dd"))!}</span>
								</div>
							</div>
						</li>
					   </#list>
					</ul>
				</div>
			 </#if>

				<!--底部的医馆部分-->
			<#if clinics.records?size gt 0 >	
				
				<div class="hospital_part">
					<div class="course_title"><span class="title">医馆</span>
						<a href="javascript:;" class="more">更多&nbsp;></a>
					</div>
					<ul class="hostpital_list">
						
						<#list clinics.records as clinic>
							<li class="hospitalTpl">
								<a href="${webUrl}/clinics/${clinic.id}" id="${clinic.id}" target="_blank"></a>
								<#if clinic.medicalHospitalPictures[0]??>
		                            <img src="${clinic.medicalHospitalPictures[0].picture}" style="width: 100%;height: 147px;"
		                                 alt="${clinic.name}">
		                        <#else >
		                            <img src="/web/images/hospitalDefault.png" style="width: 100%;height: 147px;"
		                                 alt="${clinic.name}">
		                        </#if>
								
								<div class="hospital_inf">
									<span class="hospital_name">${clinic.name}</span>
									
									<#if clinic.authentication==true>
                                		<span class="hospital_pass">已认证</span>
                            		</#if>
									 <div class="hospital_address"><em></em>
                                	     <span>${clinic.province}&nbsp;&nbsp;${clinic.city}</span>
                           			 </div>
									<div class="hospital_star">
										<em class="full_star"></em>
										<em class="full_star"></em>
										<em class="full_star"></em>
										<em class="full_star"></em>
										<em class="full_star"></em>
									</div>
								</div>
							</li>
						</#list>
					</ul>
				</div>
				</#if>	
				
			</div>
			<div class="right_content clearfix">
				<!--名医部分-->
				<div class="famousDocter">

					<div class="right_title"><span class="title">名医</span></div>

					<ul>
						<li class="doctorInfTpl clearfix">
							<div class="touxiang">
								<a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">
									<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/16/23/45980c8a6b9046029c3504d06e167a42.png" alt="">
								</a>
							</div>
							<div class="zuozhe_inf">
								<span><a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">于瑞鑫 </a><span style="font-size: 14px;color: #666666;font-weight: 400;margin-left: 10px;">四川&nbsp;新通</span></span>
								<p>课程：中医外治四期班第二季</p>
							</div>
						</li>

						<li class="doctorInfTpl clearfix">
							<div class="touxiang">
								<a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">
									<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/16/23/45980c8a6b9046029c3504d06e167a42.png" alt="">
								</a>
							</div>
							<div class="zuozhe_inf">
								<span><a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">于瑞鑫 </a><span style="font-size: 14px;color: #666666;font-weight: 400;margin-left: 10px;">四川&nbsp;新通</span></span>
								<p>课程：中医外治四期班第二季</p>
							</div>
						</li>

						<li class="doctorInfTpl clearfix">
							<div class="touxiang">
								<a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">
									<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/16/23/45980c8a6b9046029c3504d06e167a42.png" alt="">
								</a>
							</div>
							<div class="zuozhe_inf">
								<span><a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">于瑞鑫 </a><span style="font-size: 14px;color: #666666;font-weight: 400;margin-left: 10px;">四川&nbsp;新通</span></span>
								<p>课程：中医外治四期班第二季</p>
							</div>
						</li>
					</ul>
				</div>

				<!--专题课程部分-->
				<div class="thematic_course">
					<div class="right_title"><span class="title">专题课程</span></div>
					<ul class="thematic_course_list">
						<li class="thematic_courseTpl">
							<a href="javascript:;">
								<img src="http://test-file.ipandatcm.com/18508140759/62e63271326a-2c9aec356231d035016231eb7b0b00001525759679430.png" alt="" />
							</a>
						</li>

						<li class="thematic_courseTpl">
							<a href="javascript:;">
								<img src="http://test-file.ipandatcm.com/18508140759/62e63271326a-2c9aec356231d035016231eb7b0b00001525759679430.png" alt="" />
							</a>
						</li>
					</ul>
				</div>

				<!--活动部分-->
				<div class="activity">
					<div class="right_title"><span class="title">活动</span></div>

					<ul class="activity_list">
						<li class="activityTpl">
							<div>
								<a href="javascript:;"><span class="activity_teacher">施小墨</span><span class="activity_title">讲学：中医对药的误区</span></a>
							</div>
							<div><em class="activity_timePic"></em><span class="activity_time">2017.12.12  14:00</span></div>
							<div><em class="activity_addressPic"></em><span class="activity_address">北京中医药大学礼堂</span></div>
						</li>

						<li class="activityTpl">
							<div>
								<a href="javascript:;"><span class="activity_teacher">施小墨</span><span class="activity_title">讲学：中医对药的误区</span></a>
							</div>
							<div><em class="activity_timePic"></em><span class="activity_time">2017.12.12  14:00</span></div>
							<div><em class="activity_addressPic"></em><span class="activity_address">北京中医药大学礼堂</span></div>
						</li>
					</ul>
				</div>

				<!--大家专栏部分-->
				<div class="columnist">
					<div class="right_title"><span class="title">大家专栏</span></div>

					<ul>
						<li class="doctorInfTpl clearfix">
							<div class="touxiang">
								<a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">
									<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/16/23/45980c8a6b9046029c3504d06e167a42.png" alt="">
								</a>
							</div>
							<div class="zuozhe_inf">
								<span><a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">于瑞鑫 </a></span>
								<p>课程：中医外治四期班第二季55</p>
							</div>
						</li>

						<li class="doctorInfTpl clearfix">
							<div class="touxiang">
								<a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">
									<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/16/23/45980c8a6b9046029c3504d06e167a42.png" alt="">
								</a>
							</div>
							<div class="zuozhe_inf">
								<span><a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">于瑞鑫 </a></span>
								<p>课程：中医外治四期班第二季</p>
							</div>
						</li>

						<li class="doctorInfTpl clearfix">
							<div class="touxiang">
								<a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">
									<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/16/23/45980c8a6b9046029c3504d06e167a42.png" alt="">
								</a>
							</div>
							<div class="zuozhe_inf">
								<span><a href="/doctors/c654bcc007354244a6ba22cfd907f426" style="color: #0C0C0C" target="_blank">于瑞鑫 </a></span>
								<p>课程：中医外治四期班第二季</p>
							</div>
						</li>
					</ul>
				</div>

				<!--坐诊医生招募-->
				<div class="recruitment_information">
					<div class="right_title"><span class="title">坐诊医生招募</span></div>
					
					<ul class="recruitment_information_list">
						<li class="recruitment_informationTpl">
							<h4><a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" style="color: #000;" target="_blank">招募治未病医师</a></h4>
							<a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" target="_blank">黄石市 &nbsp;&nbsp;王总医馆
							</a>
						</li>
						
						<li class="recruitment_informationTpl">
							<h4><a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" style="color: #000;" target="_blank">招募治未病医师</a></h4>
							<a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" target="_blank">黄石市 &nbsp;&nbsp;王总医馆
							</a>
						</li>
						
						<li class="recruitment_informationTpl">
							<h4><a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" style="color: #000;" target="_blank">招募治未病医师</a></h4>
							<a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" target="_blank">黄石市 &nbsp;&nbsp;王总医馆
							</a>
						</li>
						
						<li class="recruitment_informationTpl">
							<h4><a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" style="color: #000;" target="_blank">招募治未病医师</a></h4>
							<a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" target="_blank">黄石市 &nbsp;&nbsp;王总医馆
							</a>
						</li>
						
						<li class="recruitment_informationTpl">
							<h4><a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" style="color: #000;" target="_blank">招募治未病医师</a></h4>
							<a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" target="_blank">黄石市 &nbsp;&nbsp;王总医馆
							</a>
						</li>
						
						<li class="recruitment_informationTpl">
							<h4><a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" style="color: #000;" target="_blank">招募治未病医师</a></h4>
							<a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" target="_blank">黄石市 &nbsp;&nbsp;王总医馆
							</a>
						</li>
						
						<li class="recruitment_informationTpl">
							<h4><a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" style="color: #000;" target="_blank">招募治未病医师</a></h4>
							<a href="http://dev-www.ixincheng.com/clinics/2acbd572c3f9491997d2ea8b491f2b34" target="_blank">黄石市 &nbsp;&nbsp;王总医馆
							</a>
						</li>
					</ul>
				</div>
			</div>

		</div>

	</body>

</html>
<script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/js/lazyload/jquery.lazyload.js?v=1.9.1"></script>
<script type="text/javascript" src="/web/js/artTemplate.js"></script>
<script type="text/javascript" src="/web/js/jquery.SuperSlide.2.1.1.js"></script>
<script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/js/jquery.form.min.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/js/md5.js" type="text/javascript" charset="UTF-8"></script>
<script src="/web/js/html5.js" type="text/javascript" charset="utf-8"></script>
<!-----引用layer------>
<script type="text/javascript" src="/web/layer-v2.1/layer/layer.js"></script>
<!-----引用layer------>
<script type="text/javascript" src="/web/js/helpers.js"></script>
<script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
	home = true;
</script>
<script src="/web/js/header.js?v=ipandatcm_1.3" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="/web/js/jquery.pagination.js"></script>
<script type="text/javascript" src="/web/js/bootstrap-paginator.min.js"></script>
<script type="text/javascript" src="/web/js/footer.js?v=ipandatcm_1.3"></script>
<script src="/web/js/placeHolder.js"></script>
<script type="text/javascript" charset="utf-8">
	function lazyCkeck() {
		$("img.lazy").lazyload({
			//placeholder : "web/images/load26.gif",
			//effect: "fadeIn",
			skip_invisible: false,
			threshold: 1500, //预加载，在图片距离屏幕180px时提前载入
		});
	}
</script>
<script src="/web/js/index2.js?v=ipandatcm_1.3" type="text/javascript" charset="utf-8"></script>

<script>
	$(document).ready(function() {
		$(".video_img").click(function() {
			$(".video_embed").show();
		});
	});
</script>

<script>
	var browser = {
		versions: function() {
			var u = navigator.userAgent,
				app = navigator.appVersion;
			return { //移动终端浏览器版本信息   
				trident: u.indexOf('Trident') > -1, //IE内核  
				presto: u.indexOf('Presto') > -1, //opera内核  
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核  
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核  
				mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端  
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端  
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器  
				iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器  
				iPad: u.indexOf('iPad') > -1, //是否iPad    
				webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部  
				weixin: u.indexOf('MicroMessenger') > -1, //是否微信   
				qq: u.match(/\sQQ/i) == " qq" //是否QQ  
			};
		}(),
		language: (navigator.browserLanguage || navigator.language).toLowerCase()
	}

	if(browser.versions.mobile || browser.versions.ios || browser.versions.android ||
		browser.versions.iPhone || browser.versions.iPad) {

		if(document.location.host == 'www.ipandatcm.com' || document.location.host == 'www.ixincheng.com') {
			wxurl = "http://m.ipandatcm.com";
		} else {
			wxurl = "http://test-wx.xczhihui.com";
		}
		window.location = wxurl;
	} else if(document.location.host == 'www.ixincheng.com') {
		window.location = "http://www.ipandatcm.com";
	}
</script>

<script type="text/javascript">
	var yyFlag = false;
	$(function() {
		$('input').placeholder();
	});

	$(function() {
		$(".order_close").click(function() {
			$(".popover_order").css('display', 'none');
		});

		function check() {
			var regPhone = /^1[3-5678]\d{9}$/;
			if($(".phone").val().trim().length === 0) {
				$(".phone").css("border", "1px solid #ff4012");
				$("#tips span").html("请输入手机号");
				$("#tips").show();
				yyFlag = false;
			} else if(!(regPhone.test($(".phone").val().trim()))) {
				$(".phone").css("border", "1px solid #ff4012");
				$("#tips span").html("手机号格式不正确");
				$("#tips").show();
				yyFlag = false;
			} else {
				$("#tips").hide();
				$(".phone").css("border", "1px solid #2cb82c");
				$(".cyinput1").css("border", "");
				yyFlag = true;
			}
		}
		$(".phone").blur(function() {
			check();
		});
		$(".order_affirm").click(function() {
			check();
			if(yyFlag) {
				var phone = $(".phone").val();
				var subscribeId = $("#subscribeId").val();
				RequestService("/course/subscribe", "GET", {
					mobile: phone,
					courseId: subscribeId
				}, function(data) {
					console.info(data);
					if(data.success) {
						$(".popover_order").css('display', 'none');
						RequestService("/online/live/getLiveTrailer", "GET", {
							num: 4
						}, function(data) {
							$(".w_ul_ul").html(template.compile(liveTrailerTemplate)({
								items: data.resultObject
							}))
							subscribeInit();
						});
						//                            $("#yyStart").html($("#startTime").val())
						rTips(data.resultObject);
					} else {
						$(".popover_order").css('display', 'none');
						//				            rTips(data.errorMessage);
						rTips();

					}
				})
			}
		});

	})
</script>