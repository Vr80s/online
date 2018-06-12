<!--右侧推荐课程-->
<#list recommendCourse as courseItem>
	<div class="course clearfix">
	
	     <#if courseItem.type == 1 ||  courseItem.type == 2 ||  courseItem.type == 4 > 
	 		    <a style="cursor:pointer" href="${webUrl}/courses/${courseItem.id}/info" target="_blank">
		 <#elseif courseItem.type == 3>
	            <a style="cursor:pointer" href="${webUrl}/web/liveCoursePage/${courseItem.id}" target="_blank">
		 </#if>
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
				      <span class="classCategory">直播预告</span>
				  <#elseif courseItem.lineState  == 3>
				      <span class="classCategory">直播回放</span>
				  <#elseif courseItem.lineState  == 4>
		             <span class="classCategory">即将直播</span>
		          </#if>
			   <#elseif courseItem.type == 4>
			      <span class="classCategory">线下课程</span>
			   </#if>
				</p>
				<p class="title">${courseItem.gradeName}</p>
				<p class="timeAndTeac">
				    <span class="teacher">${courseItem.name}</span>
					<#if courseItem.type == 4> 
						<span class="y">${courseItem.city}</span>
					</#if>
				</p>
				<p class="info clearfix">
				<span>
				
					<#if courseItem.currentPrice gt 0 >
				 	 <span class="price">${courseItem.currentPrice}</span>
				 	 <span>熊猫币</span>
				 <#else> 	 
				 	 <span class="price">免费</span>
				 </#if>
				
				
				</span>
				<span class="stuCount"><img src="/web/images/studentCount.png" alt=""><span class="studentCou">
					${courseItem.learndCount}</span></span>
				</p>
			</div>
		</a>
	</div>	
</#list>	
