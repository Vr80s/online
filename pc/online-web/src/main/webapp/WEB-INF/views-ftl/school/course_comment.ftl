<!-- 导入自定义ftl -->
<#import "../page.ftl" as cast/>

<div class="impression-content">
				<ul class="impression-list">
					<li>
						<span>总体印象</span>
						<img src="../../web/images/icon-start${startLevel}.png"/>
					</li>
					<li>
						<span>节目内容</span>
						<img src="../../web/images/smile${listCommentCount[0]}.png"/>
					</li>
					<li>
						<span>主播演绎</span>
						<img src="../../web/images/smile${listCommentCount[1]}.png"/>
					</li>
				</ul>
				<p class="want-evaluate">我要评价</p>
				<ul class="impression-write cl">
					<li>很赞(${listCommentCount[2]})</li>
					<li>干货很多(${listCommentCount[3]})</li>
					<li>超值推荐(${listCommentCount[4]})</li>
					<li>喜欢(${listCommentCount[5]})</li>
					<li>买对了(${listCommentCount[6]})</li>
				</ul>
			</div>
			<div class="wrap-comment">
			
			<#list criticizesMap.items as criticizesItem>
<!--一个完整的评论内容-->
				<div class="container-comment">
					<div class="wrap-portrait z">
						<div class="header-img">
							<img src="${criticizesItem.onlineUser.smallHeadPhoto}"/>
						</div>
						<#if criticizesItem.isBuy >
						  <p>已购</p>
						</#if>
					</div>
					<div class="right-comment y">
						<ul class="user-name">
							<li>${criticizesItem.onlineUser.name}</li>
							<li>${criticizesItem.createTime?string("yyyy.MM.dd")}</li>
							<li><img src="../../web/images/icon-start${startLevel}.png"/></li>
						</ul>
						<p class="write-text cl">
							${criticizesItem.content}
						</p>
						
						<#if criticizesItem.reply?? && (criticizesItem.reply?size gt 0) >
	<!--回复的内容 开始-->
						<#assign reply = criticizesItem.reply[0]> 
						<div class="wrap-reply y">
							<div class="header-reply z">
								<img src="${reply.onlineUser.smallHeadPhoto}"/>
							</div>
							<div class="content-reply y">
								<ul class="name-reply">
									<li>${reply.onlineUser.name}</li>
									<li>${reply.createTime?string("yyyy.MM.dd")}</li>
								</ul>
								<p class="reply-text cl">
									${reply.replyContent}						
								</p>
							</div>
						</div>
	<!--回复的内容 结束-->					
						</#if>
	<!--回复点赞按钮  开始-->
						<ul class="operation-reply" data-criticizeId ="${criticizesItem.id}" >
							<#if criticizesItem.isPraise>							
							  <li class="selected">
							<#elseif !criticizesItem.isPraise>  
							   <li>
							</#if>	
								<span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>			
								<span class="praiseSum">${criticizesItem.praiseSum}</span>
							</li>
							<li class="reply-icon">
								<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>												
							</li>
						</ul>
	<!--回复点赞按钮  结束-->
						
	<!--回复的输入框-->
						<div class="wrap-input cl hide" data-criticizeId ="${criticizesItem.id}">
							<div class="header-input z">
								<img src="/web/images/defaultHead/18.png"/>
							</div>
							<div class="input-write z">
	<!--未登录-->
								<p class="reply_no_login hide" >写下你的评论，请先<span>登陆</span>
								</p>
	<!--已登陆-->
								<input  class="hide reply_login"  type="text" name="" value="" />
							</div>
	<!--未登录按钮-->
							<button class="no-login y reply_no_login" type="button">回复</button>
	<!--已登陆按钮-->
							<button class="login y hide reply_login reply_criticize" type="button">回复</button>										
						</div>
					</div>
				</div>
				
<!--一个完整的评论内容结束-->
			</#list>
			

			</div>	
		
 			
	<!--更多按钮添加-->
	<#--	<#if criticizesMap.items?? &&  criticizesMap.current == 1 &&(criticizesMap.total gt criticizesMap.size) && (criticizesMap.size  != 4 )>
			<div class="more-evaluate">
				<button type="button">
				  <a href="${webUrlParam}/comment?pageSize=4" >更多</a>
				</button>
			</div>
		</#if>	 -->	
	<!--更多按钮添加结束-->
	
    <!-- 这里觉的需要分页呢  -->
	     <!-- 使用该标签 -->		
		<#--     <#if criticizesMap.items?? && (criticizesMap.pages gt 2) && criticizesMap.current gt 1>
		      <@cast.page pageNo=criticizesMap.current totalPage=criticizesMap.pages showPages=5 callUrl="${webUrlParam}/comment?pageNumber=" />
		 </#if>	 -->
	<!-- 这里觉的需要分页呢  -->
	
	<@cast.page pageNo=criticizesMap.current totalPage=criticizesMap.pages showPages=5 callUrl="${webUrlParam}/comment?pageNumber=" />
