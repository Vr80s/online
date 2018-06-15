
<#-- 公共的名医推荐  -->
<ul>
	<#list doctorList as doctorInfo>
	<li>  
		<a href="${webUrl}/anchors/${doctorInfo.userId}/info" target="_blank">
		 <img src="${doctorInfo.headPortrait}" alt="名医头像"/>
		</a>
		<p data-id ="${doctorInfo.userId}">${doctorInfo.name}</p>
	</li> 
	</#list>
</ul>