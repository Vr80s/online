
<#-- 公共的名医推荐  -->
<ul>
	<#list doctorList as doctorInfo>
	<li>  
		<a href="${webUrl}/anchors/${doctorInfo.userId}/info" target="_blank">
		 <img src="${doctorInfo.headPortrait}?imageMogr2/thumbnail/!60x60r|imageMogr2/gravity/Center/crop/60x60" alt="名医头像"/>
		</a>
		<p data-id ="${doctorInfo.userId}">${doctorInfo.name}</p>
	</li> 
	</#list>
</ul>