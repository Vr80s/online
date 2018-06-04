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
						<li class="course">
							<a style="cursor:pointer" href="/web/liveCoursePage/677" target="_blank">
								<div class="img"><img src="http://test-file.ipandatcm.com/18508164259/cb2b6641a715-2c9aec345eba077f015ebbac558400061525768979627.png"></div>
								<span class="classCategory">直播中</span>
								<div class="detail">
									<p class="title" data-text="音频测试3" title="音频测试3">H5需要的PC直播1</p>
									<p class="timeAndTeac"><span class="teacher">你你你你</span>
									</p>
									<p class="info clearfix"><span><span class="price">0</span><span>熊猫币</span></span>
										<span class="stuCount"><img src="/web/images/studentCount.png" alt="">
								<span class="studentCou">10</span></span>
									</p>
								</div>
							</a>
						</li>

					</ul>
				</div>
				
				
				
				</#if>				

				<!--线下课程部分-->
				<div class="underline_course">
					<div class="course_title"><span class="title">线下课程</span>
						<a href="javascript:;" class="more">更多&nbsp;></a>
					</div>
					<ul class="online_course_list clearfix">
						<li class="course">
							<a style="cursor:pointer" href="/web/liveCoursePage/677" target="_blank">
								<div class="img"><img src="http://test-file.ipandatcm.com/18508164259/cb2b6641a715-2c9aec345eba077f015ebbac558400061525768979627.png"></div>
								<span class="classCategory">直播中</span>
								<div class="detail">
									<p class="title" data-text="音频测试3" title="音频测试3">H5需要的PC直播1</p>
									<p class="timeAndTeac"><span class="teacher">你你你你</span>
									</p>
									<p class="info clearfix"><span><span class="price">0</span><span>熊猫币</span></span>
										<span class="stuCount"><img src="/web/images/studentCount.png" alt="">
								<span class="studentCou">10</span></span>
									</p>
								</div>
							</a>
						</li>

						<li class="course">
							<a style="cursor:pointer" href="/web/liveCoursePage/677" target="_blank">
								<div class="img"><img src="http://test-file.ipandatcm.com/18508164259/cb2b6641a715-2c9aec345eba077f015ebbac558400061525768979627.png"></div>
								<span class="classCategory">直播中</span>
								<div class="detail">
									<p class="title" data-text="音频测试3" title="音频测试3">H5需要的PC直播1</p>
									<p class="timeAndTeac"><span class="teacher">你你你你</span>
									</p>
									<p class="info clearfix"><span><span class="price">0</span><span>熊猫币</span></span>
										<span class="stuCount"><img src="/web/images/studentCount.png" alt="">
								<span class="studentCou">10</span></span>
									</p>
								</div>
							</a>
						</li>

						<li class="course">
							<a style="cursor:pointer" href="/web/liveCoursePage/677" target="_blank">
								<div class="img"><img src="http://test-file.ipandatcm.com/18508164259/cb2b6641a715-2c9aec345eba077f015ebbac558400061525768979627.png"></div>
								<span class="classCategory">直播中</span>
								<div class="detail">
									<p class="title" data-text="音频测试3" title="音频测试3">H5需要的PC直播1</p>
									<p class="timeAndTeac"><span class="teacher">你你你你</span>
									</p>
									<p class="info clearfix"><span><span class="price">0</span><span>熊猫币</span></span>
										<span class="stuCount"><img src="/web/images/studentCount.png" alt="">
								<span class="studentCou">10</span></span>
									</p>
								</div>
							</a>
						</li>
					</ul>
				</div>

				<!--名家坐诊部分-->
				<div class="famous_doctor">
					<div class="course_title"><span class="title">名家坐诊</span>
						<a href="javascript:;" class="more">更多&nbsp;></a>
					</div>
					<ul class="famous_doctor_list clearfix">
						<li class="doctorTpl">
							<a href="http://dev-www.ixincheng.com/doctors/d629a5bfd8934ccbac46ebe2e0ff5490" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/picture/online/2018/01/12/21/e55d83d376f448478e811fa55e5fa671.jpg" alt="路志正">
							<h5>路志正&nbsp;<span>主任医师</span></h5>
							<p>周四上午</p>
							<p>北京市&nbsp;北京市&nbsp; </p>
						</li>

						<li class="doctorTpl">
							<a href="http://dev-www.ixincheng.com/doctors/d629a5bfd8934ccbac46ebe2e0ff5490" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/picture/online/2018/01/12/21/e55d83d376f448478e811fa55e5fa671.jpg" alt="路志正">
							<h5>路志正&nbsp;<span>主任医师</span></h5>
							<p>周四上午</p>
							<p>北京市&nbsp;北京市&nbsp; </p>
						</li>

						<li class="doctorTpl">
							<a href="http://dev-www.ixincheng.com/doctors/d629a5bfd8934ccbac46ebe2e0ff5490" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/picture/online/2018/01/12/21/e55d83d376f448478e811fa55e5fa671.jpg" alt="路志正">
							<h5>路志正&nbsp;<span>主任医师</span></h5>
							<p>周四上午</p>
							<p>北京市&nbsp;北京市&nbsp; </p>
						</li>

						<li class="doctorTpl">
							<a href="http://dev-www.ixincheng.com/doctors/d629a5bfd8934ccbac46ebe2e0ff5490" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/picture/online/2018/01/12/21/e55d83d376f448478e811fa55e5fa671.jpg" alt="路志正">
							<h5>路志正&nbsp;<span>主任医师</span></h5>
							<p>周四上午</p>
							<p>北京市&nbsp;北京市&nbsp; </p>
						</li>

					</ul>
				</div>

				<!--头条新闻部分开始-->
				<div class="topLine_news">
					<div class="course_title"><span class="title">头条新闻</span>
						<a href="javascript:;" class="more">更多&nbsp;></a>
					</div>
					<ul class="topLine_news_list">

						<li class="newsTpl clearfix">
							<a href="http://dev-www.ixincheng.com/headline/details/385" target="_blank">
								<img class="forum-info-left" src="https://file.ipandatcm.com/data/picture/online/2018/03/13/16/1d1d5be02e8a4a73a56dd07a867c8bd5.png" alt="">
							</a>
							<div class="forum-info-right">
								<div class="forum-info-title">
									<a href="http://dev-www.ixincheng.com/headline/details/385" target="_blank">校地合作 推动基层和农村中医药人才素质能力建设</a>
								</div>
								<div class="forum-info-content dot-ellipsis">
									由上海中医药大学和台州市政府合办的台州市中医人才队伍素质提升工程基层中医骨干培训班(第一期)及开班仪式在台州举行，台州市区社区卫生服务中心和乡镇卫生院中取得中医执业医师资格的相关人员、“台州市农村中青年中医骨干”称号获得者等73名学员参加了培训。上海中医药大学副校长胡鸿毅表示，大学与台州市政府的合作是落实十九大提出的关于做好中医药传承发展工作精神的实践。为期3个月的培训班组织安排了学校多位教授专家与台州市基层中医骨干一起探讨交流，希望促进当地基层中医骨干将新的中医发展理念、学术观点运用到临床一线。台州市副市长吴丽慧在开班仪式中指出，“中医基层化，基层中医化”是我国中医药事业发展的方向。加快基层中医药人才的培训培养，优化农村基层中医药人才队伍结构，提升整体素质能力，已成为当前台州中医药事业补短板的关键。台州市委市政府高度重视中医药事业的发展问题，从全面接轨上海，建立中医药人才教育培训体系开始，开展多方面举措合力推动台州市中医药事业的发展。开班仪式后，胡鸿毅作题为“新时代与中医药传承发展的使命”的专题讲座。下午，上海中医药大学杨柏灿教授以“临证中药选用三结合”为题作学术讲座，受到学员们欢迎。据悉，2017年11月，上海中医药大学与台州市政府签订了《台州市中医药事业发展战略合作协议》，双方明确将在医疗服务、医学教育以及科学研究等方面展开全面合作。首期培训班开班标志着基层中医骨干轮训工作正式开启，双方第一轮合作项目落地。
								</div>
								<div class="forum-info-tags">
									<span>来源：中新网上海<em></em>2018-03-13</span>
								</div>
							</div>
						</li>

						<li class="newsTpl clearfix">
							<a href="http://dev-www.ixincheng.com/headline/details/385" target="_blank">
								<img class="forum-info-left" src="https://file.ipandatcm.com/data/picture/online/2018/03/13/16/1d1d5be02e8a4a73a56dd07a867c8bd5.png" alt="">
							</a>
							<div class="forum-info-right">
								<div class="forum-info-title">
									<a href="http://dev-www.ixincheng.com/headline/details/385" target="_blank">校地合作 推动基层和农村中医药人才素质能力建设</a>
								</div>
								<div class="forum-info-content dot-ellipsis">
									由上海中医药大学和台州市政府合办的台州市中医人才队伍素质提升工程基层中医骨干培训班(第一期)及开班仪式在台州举行，台州市区社区卫生服务中心和乡镇卫生院中取得中医执业医师资格的相关人员、“台州市农村中青年中医骨干”称号获得者等73名学员参加了培训。上海中医药大学副校长胡鸿毅表示，大学与台州市政府的合作是落实十九大提出的关于做好中医药传承发展工作精神的实践。为期3个月的培训班组织安排了学校多位教授专家与台州市基层中医骨干一起探讨交流，希望促进当地基层中医骨干将新的中医发展理念、学术观点运用到临床一线。台州市副市长吴丽慧在开班仪式中指出，“中医基层化，基层中医化”是我国中医药事业发展的方向。加快基层中医药人才的培训培养，优化农村基层中医药人才队伍结构，提升整体素质能力，已成为当前台州中医药事业补短板的关键。台州市委市政府高度重视中医药事业的发展问题，从全面接轨上海，建立中医药人才教育培训体系开始，开展多方面举措合力推动台州市中医药事业的发展。开班仪式后，胡鸿毅作题为“新时代与中医药传承发展的使命”的专题讲座。下午，上海中医药大学杨柏灿教授以“临证中药选用三结合”为题作学术讲座，受到学员们欢迎。据悉，2017年11月，上海中医药大学与台州市政府签订了《台州市中医药事业发展战略合作协议》，双方明确将在医疗服务、医学教育以及科学研究等方面展开全面合作。首期培训班开班标志着基层中医骨干轮训工作正式开启，双方第一轮合作项目落地。
								</div>
								<div class="forum-info-tags">
									<span>来源：中新网上海<em></em>2018-03-13</span>
								</div>
							</div>
						</li>

					</ul>
				</div>

				<!--底部的医馆部分-->
				<div class="hospital_part">
					<div class="course_title"><span class="title">医馆</span>
						<a href="javascript:;" class="more">更多&nbsp;></a>
					</div>
					<ul class="hostpital_list">
						<li class="hospitalTpl">
							<a href="http://dev-www.ixincheng.com/clinics/08a08cf4f87848298576838206653c39" id="08a08cf4f87848298576838206653c39" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/17/17/d649639747464d7c8343bd0c3df333ea.png" style="width: 100%;height: 147px;" alt="坤鹤百草堂中医馆">
							<div class="hospital_inf">
								<span class="hospital_name">坤鹤百草堂中医馆</span>
								<span class="hospital_pass">已认证</span>
								<div class="hospital_address"><em></em>
									<span>北京市&nbsp;&nbsp;北京市</span>
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

						<li class="hospitalTpl">
							<a href="http://dev-www.ixincheng.com/clinics/08a08cf4f87848298576838206653c39" id="08a08cf4f87848298576838206653c39" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/17/17/d649639747464d7c8343bd0c3df333ea.png" style="width: 100%;height: 147px;" alt="坤鹤百草堂中医馆">
							<div class="hospital_inf">
								<span class="hospital_name">坤鹤百草堂中医馆</span>
								<span class="hospital_pass">已认证</span>
								<div class="hospital_address"><em></em>
									<span>北京市&nbsp;&nbsp;北京市</span>
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

						<li class="hospitalTpl">
							<a href="http://dev-www.ixincheng.com/clinics/08a08cf4f87848298576838206653c39" id="08a08cf4f87848298576838206653c39" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/17/17/d649639747464d7c8343bd0c3df333ea.png" style="width: 100%;height: 147px;" alt="坤鹤百草堂中医馆">
							<div class="hospital_inf">
								<span class="hospital_name">坤鹤百草堂中医馆</span>
								<span class="hospital_pass">已认证</span>
								<div class="hospital_address"><em></em>
									<span>北京市&nbsp;&nbsp;北京市</span>
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

						<li class="hospitalTpl">
							<a href="http://dev-www.ixincheng.com/clinics/08a08cf4f87848298576838206653c39" id="08a08cf4f87848298576838206653c39" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/17/17/d649639747464d7c8343bd0c3df333ea.png" style="width: 100%;height: 147px;" alt="坤鹤百草堂中医馆">
							<div class="hospital_inf">
								<span class="hospital_name">坤鹤百草堂中医馆</span>
								<span class="hospital_pass">已认证</span>
								<div class="hospital_address"><em></em>
									<span>北京市&nbsp;&nbsp;北京市</span>
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

						<li class="hospitalTpl">
							<a href="http://dev-www.ixincheng.com/clinics/08a08cf4f87848298576838206653c39" id="08a08cf4f87848298576838206653c39" target="_blank"></a>
							<img src="https://file.ipandatcm.com/data/attachment/online/2018/03/17/17/d649639747464d7c8343bd0c3df333ea.png" style="width: 100%;height: 147px;" alt="坤鹤百草堂中医馆">
							<div class="hospital_inf">
								<span class="hospital_name">坤鹤百草堂中医馆</span>
								<span class="hospital_pass">已认证</span>
								<div class="hospital_address"><em></em>
									<span>北京市&nbsp;&nbsp;北京市</span>
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

					</ul>
				</div>
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