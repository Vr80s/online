
<#-- 公共的banner图 -->

<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
  <ol class="carousel-indicators">
   	<#list bannerList as banner>
 	<#if banner_index == 0 >
 		<li data-target="#carousel-example-generic" data-slide-to="${banner_index}" class="active"></li>
 	<#else>
 		<li data-target="#carousel-example-generic" data-slide-to="${banner_index}"></li>
 	</#if>
</#list>
  </ol>
  <div class="carousel-inner" role="listbox">
    <#list bannerList as banner>
 	<#if banner_index == 0 >
 		<div class="item active">
 	<#else>
 		<div class="item">
 	</#if>
		<#-- banner图的点击     连接类型：1：活动页、2：专题页、3：课程:4：主播:5：课程列表（带筛选条件） -->
		<#if banner.linkType == 3>
		    <a href="${webUrl}/courses/${replaceUrl(banner.url,'linkCondition',"")}/info" target="_blank">
		<#elseif banner.linkType == 4>    
		      <a href="${webUrl}/anchors/${replaceUrl(banner.url,'linkCondition',"")}/info" target="_blank">
		<#elseif banner.linkType == 5>   
		    <a href="${webUrl}/courses/list?${banner.url}" target="_blank">
		<#else>      
		    <a href="" >
		</#if>	
     		<img src="${banner.imgPath}" alt="广告图">
     	</a>
    </div>	
</#list>
  </div>	
</div>