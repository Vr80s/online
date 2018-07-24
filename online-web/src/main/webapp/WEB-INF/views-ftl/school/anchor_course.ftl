<!-- 导入自定义ftl -->
<#import "../page.ftl" as cast/>

<div class="wrap-class" style="overflow: hidden;">
<#if courseList?? && courseList.records?size gt 0>
    <#list courseList.records as courseItem>
       <div class="course clearfix">
			
				<#if courseItem.recommendSort?? &&  courseItem.recommendSort gt 0>	
					<img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" 
								src="/web/images/recommend2.png">
				</#if> 
			    
				<#if courseItem.type == 1 ||  courseItem.type == 2 ||  courseItem.type == 4 > 
			 		    <a style="cursor:pointer" href="/courses/${courseItem.id}/info" target="_blank">
				<#elseif courseItem.type == 3>
			            <a style="cursor:pointer" href="/web/liveCoursePage/${courseItem.id}" target="_blank">
				</#if>
				<div class="img">
				<img src="${courseItem.smallImgPath}?imageMogr2/thumbnail/!260x147r|imageMogr2/gravity/Center/crop/260x147"></div>
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
				    <span class="classCategory">直播预告</span>
				  <#elseif courseItem.lineState  == 3>
				      <span class="classCategory">直播回放</span>
				  <#elseif courseItem.lineState  == 4>
		             <span class="classCategory">即将直播</span>
		          <#else>   
					  <span class="classCategory">暂未开播</span>   
		          </#if>
			    <#elseif courseItem.type == 4>
			      <span class="classCategory">线下课程</span>
			    </#if>
				<div class="detail">
					<p class="title" title="${courseItem.gradeName}">${courseItem.gradeName}</p>
					<p class="timeAndTeac"><span>
					<span class="teacher z">${courseItem.name}</span>
					 <#if courseItem.type == 4>
                        <span class="y">${courseItem.city}</span>
                   		 <#elseif courseItem.lineState?? && courseItem.lineState !=1>
                    	<#if courseItem.startDateStr?index_of(".")!=-1 >
                    		<span class="y"><img src="/web/images/date_month.png" style="margin: -3px 2px 0 0;" />${courseItem.startTime?string("MM月dd日")}</span>
                    	<#else>
                    	    <span class="y"><img src="/web/images/myvideo-time.png" style="margin: -3px 3px 0 0;" />${courseItem.startDateStr}</span>
                    	</#if>
					</#if>
					</span>
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
					 <span class="stuCount"><img src="/web/images/studentCount.png" alt="">
					 <span class="studentCou">${courseItem.learndCount}</span></span>
					</p>
				</div>
			</a>
		</div>		
  	</#list>
</#if>							
</div>
<@cast.page pageNo=courseList.current totalPage=courseList.pages showPages=5 callUrl="${webUrlParam}/courses?pageNumber=" />
