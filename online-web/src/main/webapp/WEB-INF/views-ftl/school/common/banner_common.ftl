
<#-- 公共的banner图 -->

<div id="carousel-example-generic" class="carousel slide hover-select" data-ride="carousel">
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
		    <a href="${webUrl}/courses/${replaceUrl(banner.url,'linkCondition',"")}/info" data-indexId="${banner.id}" target="_blank"  >
		<#elseif banner.linkType == 4>    
		      <a href="${webUrl}/anchors/${replaceUrl(banner.url,'linkCondition',"")}/info" data-indexId="${banner.id}" target="_blank">
		<#elseif banner.linkType == 5>   
		    <a href="${webUrl}/courses/list?${banner.url}" data-indexId="${banner.id}"  target="_blank">
		<#else>      
		    <a href="" >
		</#if>	
     		<img src="${banner.imgPath}" alt="广告图">
     	</a>
    </div>	
    <!-- Controls -->
      <a class="left carousel-control banner-prev" href="#carousel-example-generic" role="button" data-slide="prev">
        <em></em>
      </a>
      <a class="right carousel-control banner-prev banner-next" href="#carousel-example-generic" role="button" data-slide="next">
         <em></em>
      </a>
</#list>
  </div>	
</div>