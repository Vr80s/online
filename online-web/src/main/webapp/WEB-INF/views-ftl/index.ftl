<!DOCTYPE html>
<html lang="en">

<head>
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge"/>
    <meta charset="UTF-8">
    <title>熊猫中医云课堂 - 线上中医教育</title>
    <link rel="shortcut icon" href="favicon.ico"/>
    <meta name="keywords"
          content="中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医"/>
    <meta name="description"
          content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。"/>
    <meta name="renderer" content="webkit">
    <meta name="baidu-site-verification" content="UHaAQAeAQF"/>
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/index.css?v=ipandatcm_1.3"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>
    <link rel="stylesheet" href="/web/css/index-9a525196fb.css" type="text/css">
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
			<input type="hidden" id="subscribeId"/>
			<input type="text" class="phone" placeholder="输入11位阿拉伯数字" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;if(event.keyCode==13) {btnValidate.click();return false;}"/>
			<div id="tips" style="top: 154px; display:none ;"><span style="margin-left: 21%;font-size: 15px;color: red;"></span></div>
		</div>
		
		<input type="button" class="order_affirm" value="确认" id='btnValidate'>
	</div>
</div>
<div class="index_body">
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
        <!-- 20170724---yuruixin -->
        <!-- <div class="lastest-news">
            <div class="newsTitle">
                <img src="/web/images/125/icon.png" alt=""/>

                <p class="biaoti">最新动态</p>
            </div>
            <ul class="newsList">

            </ul>
        </div> -->
    </div>


    <!--课程列表-->
    <div id="bgColor">
        

        <div class="zhibo" style="margin-top: 1%;">
            <!-- 视频 + 直播预告-->
            <!-- <div class="yugao1 yugao2">
                <div class="video_left">
                
                	<a href = "/courses/${indexLive.id}/info" target="_blank">
	                    <div class="video_div">
	                        <img src="${indexLive.smallimg_path}" alt="" width='500' height='391' class="video_img" id="liveImg" />
	                        <div class="video_div_bottom">
	                            <div class="video_div_bottom_bg1"></div>
	                            <div class="video_div_bottom_right">
	                                <div class="video_div_bottom_bg2">
	                                	<#if data.resultObject.learnd_count?? || data.resultObject.learnd_count lt 0>
	                                		暂无报名
	                                	<#else>
	                                	    <span id="liveLearndCount">${data.resultObject.learnd_count}</span>
		                                    &nbsp;&nbsp;<span>人报名</span>
	                                	</#if>
	                                </div>
	                                <a href="javascript: ;"><span id="liveStatus">进入直播</span></a>
	                            </div>
	                        </div>
	                    </div>
	                </a>     
                </div>
                <div class="w-video-module w_yugao2">
                    <div class="w_yugao_hd">
                        <h2 class="h_tl" style="margin-top:0px;">
                            <i class="icon icon-205"></i>
                            <a href="javascript:void(0);">直播预告</a>
                        </h2>
                    </div>
                    <div class="w-video-module-bd">
                        <div class="w_div">
                            <div class="w_ul" style="overflow: hidden1;">
                                <ul  class="w_ul_ul">
									<#if liveActivity??>	
										<#list liveActivity as liveItem >
										   <#if bannerList_index == 0>
										   	  <span class="cur"></span>
										   <#else> 
										   	  <span></span>
											</#if>
										</#list>	
									</#if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div> -->
            <div class="yugao1 yugao2">
            <!-- 视频 -->
            <div class="video_left">
                <div class="video_div">
                	<div class="" style="width: 100%; height: 350px;">
                		<img src="" alt="" class="video_img" id="liveImg" />
                	</div>
                    
                    <div class="video_div_bottom">
                        <!-- <div class="video_div_bottom_bg1"></div> -->
                        <div class="video_div_bottom_right">
                            <div class="video_div_bottom_bg2"><img src="web/images/yugao/watching.png" alt="" /><span  id="liveLearndCount"></span>人&nbsp;&nbsp;<span>同时观看</span></div>
                            <a href="javascript: ;"></a>
                        </div>
                    </div>

                </div>
            </div>
        	<div class="w-video-module w_yugao2">
                <div class="w_yugao_hd">
                    <h2 class="h_tl" style="margin-top:0px;">
                        <img src="web/images/yugao/0802301_03.png" alt="" />
                        <span href="javascript:void(0);" >直播活动</span>
                    </h2>
                    <!-- <a href="javascript: ;" class="w_yugao_hd_a">更多></a> -->
        		</div>
                <div class="w-video-module-bd">
                    <div class="w_div">
                        <div class="w_ul" style="overflow: hidden1;">
                            <ul  class="w_ul_ul">
                                
							</ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>

        <div id="main" class="clearfix">
            <h1>中医学习</h1>
            <ul id="tabFirst_zyxx" class="tabFirst">

            </ul>
            <div class="hr"></div>
            <!-- <ul id="tabSecond_zyxx" class="clearfix tabSecond">

            </ul> -->
            <div id="log_zyxx" style="width: 100%; margin-top: 0.7%">
                <div id="content_zyxx" class="content clearfix" style="width: 110%;">

                    <img class="loadingImg" src="/web/images/ansandqus/loading.gif" alt=""/>
                    <p class="loadingWord">加载中</p>
                </div>

                <!-- <div id="emptyTitle">
                    <img src="web/images/index2.png" alt=""/>
                    <span>更多精彩课程正在上新中，敬请期待...</span>
                </div> -->
                <div class="zhongyi device-page clearfix">
					<nav>
            			<ul class="pagination" style="float:right"></ul>
        			</nav>	
				</div>
				
            <div class="pages" style="display: none;">
				<!-- <div id="Pagination"></div> -->
				<div class="searchPage">
					<span class="page-sum">共<strong class="allPage">0</strong>页</span>
					<span class="page-go">跳转<input type="text" style="width: 37px;" min="1" max="">页</span>
					<a href="javascript:;" class="page-btn">确定</a>
				</div>
			</div>
            </div>
            <!-- <h1>身心修养</h1>
            <ul id="tabFirst_sxxy" class="tabFirst">

            </ul>
            <div class="hr"></div>
            <div id="log_sxxy" style="width: 100%; margin-top: 0.7%">
                <div id="content_sxxy" class="content clearfix" style="width: 110%;">

                    <img class="loadingImg" src="/web/images/ansandqus/loading.gif" alt=""/>
                    <p class="loadingWord">加载中</p>
                </div>
            </div>
            <h1>经方要略</h1>
            <ul id="tabFirst_jfyl" class="tabFirst">

            </ul>
            <div class="hr"></div>
            <div id="log_jfyl" style="width: 100%; margin-top: 0.7%">
                <div id="content_jfyl" class="content clearfix" style="width: 110%;">

                    <img class="loadingImg" src="/web/images/ansandqus/loading.gif" alt=""/>
                    <p class="loadingWord">加载中</p>
                </div>
            </div> -->
            <h1>线下课</h1>
            <ul id="tabFirst_xxpxb" class="tabFirst">

            </ul>
            <div class="hr"></div>
            <div id="log_xxpxb" style="width: 100%; margin-top: 0.7%">
                <div id="content_xxpxb" class="content clearfix" style="width: 110%;">

                    <img class="loadingImg" src="/web/images/ansandqus/loading.gif" alt=""/>
                    <p class="loadingWord">加载中</p>
                </div>
                <!--分页-->
                <!--<div class="pages" style="display: none;">
				<div id="Pagination"></div>
				</div>
				-->
				 <div class="peixun device-page clearfix">
					<nav>
            			<ul class="pagination" style="float:right"></ul>
        			</nav>	
				</div>
				
				
				<!--第二种分页-->
				<!--<div class="pages" style="display: none;">
					<div class="searchPage" id="searchPage">
						<span class="page-sum">共<strong class="allPage">0</strong>页</span>
						<span class="page-go">跳转<input type="text" style="width: 37px;" min="1" max="">页</span>
						<a href="javascript:;" class="page-btn" id="#page-btn">确定</a>
					</div>
				</div>-->
				
				

                <!-- <div id="emptyTitle">
                    <img src="web/images/index2.png" alt=""/>
                    <span>更多精彩课程正在上新中，敬请期待...</span>
                </div> -->
            </div>
           

            <!-------------学员故事---------------->
            <!-- 暂时关闭---20170725---yuruixin -->
            <div style="width:100%;overflow:hidden;position: relative;">
                <div id="studentStore" class="slide-box">
                    <h2>学员故事</h2>

                    <div id="box" class="slideBox">
                        <a href="javascript:;" id="prev" class="prev"><em style="background-size: 7px 13px;background-image: url(/web/images/130/banner_left_normal.png);background-repeat: no-repeat;background-position: center center;"></em></a>
                        <ul class="clearfix boxContent">
                            
                        </ul>
                        <a href="javascript:;" id="next" class="next"><em style="background-size: 7px 13px;background-image: url(/web/images/130/banner_right_normal.png);background-repeat: no-repeat;background-position: center center;
"></em></a>
                    </div>

                </div>
            </div>
            <!-------------学员故事---------------->

        </div>
    </div>
</div>
<hr class="bottom clearfix">
<div class="boxueguBottomIntroduct clearfix">
    <div class="index-footer clearfix">
        <!-- <div class="index-footer-left">
            <p class="bottom-title">熊猫中医云课堂</p>

            <p class="bottom-content">
            </p>
        </div> -->
        <div class="index-footer-middle clearfix">
            <p class="index-footer-title">关注我们</p>

            <div class="weibo-erweima">
                    <a href="http://weibo.com/u/6329861207?refer_flag=1001030102_&amp;is_hot=1" target="_blank">
                        <img class="weibo" src="/web/images/125/weibo.png" alt="" title="点击关注熊猫中医微博"/>
                    </a>
                <div class="weibo-hover">

                    <img src="/web/images/sideWeibopng.jpg" alt=""/>

                    <p>&nbsp;&nbsp;关注熊猫中医新浪微博获取更多行业即时资讯</p>
                    <img class="indexSanjiao" src="/web/images/125/index03.png" alt=""/>
                </div>
            </div>
            <div class="weixin-erweima">
                <img class="weixin" src="/web/images/125/weixin.png" alt=""/>

                <div class="weixin-hover">
                    <img src="/web/images/sideWeixin.jpg" alt=""/>

                    <p>关注熊猫中医微信公众号每日获取最新学习资料</p>
                    <img class="indexSanjiao" src="/web/images/125/index03.png" alt=""/>
                </div>
            </div>


        </div>
        <div id="friendLink">

        </div>
    </div>
</div>

<!--友情链接下的分隔线-->
<!--<hr class="bottom clearfix" >-->
<!--<div class="rTips"></div>-->
<div class="rTips-bg"></div>
<div class="rTips">
	<span>X</span>
	<div class="wrap-img">
		<img src="web/images/i-success1.png"/>
	</div>
	<div class="wrap-img02"></div>
	<p>恭喜你，预约成功</p>
    <p id="yyStart"></p>
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
	function lazyCkeck(){
		$("img.lazy").lazyload({
		  //placeholder : "web/images/load26.gif",
	  	  //effect: "fadeIn",
	  	  skip_invisible : false,
	  	  threshold : 1500, //预加载，在图片距离屏幕180px时提前载入
	  	  });
	}
	</script>
    <script src="/web/js/index.js?v=ipandatcm_1.3" type="text/javascript" charset="utf-8"></script>

    <script>
        $(document).ready(function(){
            $(".video_img").click(function(){
                $(".video_embed").show();
            });
        });
    </script>

    <script>
    var browser={
            versions:function(){   
                   var u = navigator.userAgent, app = navigator.appVersion;   
                   return {//移动终端浏览器版本信息   
                        trident: u.indexOf('Trident') > -1, //IE内核  
                        presto: u.indexOf('Presto') > -1, //opera内核  
                        webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核  
                        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核  
                        mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端  
                        ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端  
                        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器  
                        iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器  
                        iPad: u.indexOf('iPad') > -1, //是否iPad    
                        webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部  
                        weixin: u.indexOf('MicroMessenger') > -1, //是否微信   
                        qq: u.match(/\sQQ/i) == " qq" //是否QQ  
                    };
                 }(),  
                 language:(navigator.browserLanguage || navigator.language).toLowerCase()  
        }   
          
          if(browser.versions.mobile || browser.versions.ios || browser.versions.android ||   
            browser.versions.iPhone || browser.versions.iPad){

              if(document.location.host=='www.ipandatcm.com' ||document.location.host=='www.ixincheng.com'){
                  wxurl = "http://m.ipandatcm.com";
              }else{
                  wxurl = "http://test-wx.xczhihui.com";
              }
              window.location = wxurl;
          }else if(document.location.host=='www.ixincheng.com'){
              window.location = "http://www.ipandatcm.com";
          }
    </script>


<script type="text/javascript">
	var yyFlag = false;
    $(function () {
        $('input').placeholder();
    });
   	
    $(function(){
		$(".order_close").click(function(){
			$(".popover_order").css('display','none');
		});
		function check(){
			 var regPhone = /^1[3-5678]\d{9}$/;
	            if ($(".phone").val().trim().length === 0) {
	                $(".phone").css("border", "1px solid #ff4012");
	                $("#tips span").html("请输入手机号");
	                $("#tips").show();
	                yyFlag=false;
	            } else if (!(regPhone.test($(".phone").val().trim()))) {
	                $(".phone").css("border", "1px solid #ff4012");
	                $("#tips span").html("手机号格式不正确");
	                $("#tips").show();
	                yyFlag=false;
	            }else{
	                $("#tips").hide();
	                $(".phone").css("border", "1px solid #2cb82c");
	                $(".cyinput1").css("border", "");
	                yyFlag=true;
	            }
		}
		$(".phone").blur(function () {
			check();
        });		
			$(".order_affirm").click(function(){
				check();
				if(yyFlag){
					var phone = $(".phone").val();
					var subscribeId = $("#subscribeId").val();
					RequestService("/course/subscribe", "GET", {
						mobile:phone,
						courseId:subscribeId
			        }, function(data) {
			            console.info(data);
			            if(data.success){
							$(".popover_order").css('display','none');
							RequestService("/online/live/getLiveTrailer","GET",{num:4},function(data){
							    $(".w_ul_ul").html(template.compile(liveTrailerTemplate)({
							        items:data.resultObject
							    }))
							    subscribeInit();
							});
//                            $("#yyStart").html($("#startTime").val())
				            rTips(data.resultObject);
			            }else{
							$(".popover_order").css('display','none');
//				            rTips(data.errorMessage);
				            rTips();
				            
			            }
			        })
				}
			});
	
		
		
	
	})
</script>

