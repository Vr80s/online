<li class="course">
<#if courseItem.courseForm == 1 && courseItem.multimediaType == 1>
<a style="cursor:pointer" href="${webUrl}/web/liveCoursePage/${courseItem.id}" target="_blank">
<#else>
<a style="cursor:pointer" href="${webUrl}/courses/${courseItem.id}/info" target="_blank">
</#if>

    <div class="img"><img src="${courseItem.smallImgPath}?imageMogr2/thumbnail/!260x147r|imageMogr2/gravity/Center/crop/260x147"></div>


<#if courseItem.courseForm == 2 && courseItem.multimediaType == 1 >
    <#if courseItem.collection>
        <span class="classCategory">视频专辑</span>
    <#elseif !courseItem.collection>
        <span class="classCategory">视频</span>
    </#if>
<#elseif courseItem.courseForm == 2 && courseItem.multimediaType == 2>
    <#if courseItem.collection>
        <span class="classCategory">音频专辑</span>
    <#elseif !courseItem.collection>
        <span class="classCategory">音频</span>
    </#if>
<#elseif courseItem.courseForm == 1 && courseItem.multimediaType == 1>
    <#if courseItem.lineState  == 1  >
        <span class="classCategory">视频直播中</span>
    <#elseif courseItem.lineState  == 2>
        <span class="classCategory">视频直播预告</span>
    <#elseif courseItem.lineState  == 3>
        <span class="classCategory">视频直播回放</span>
    <#elseif courseItem.lineState  == 4>
        <span class="classCategory">即将视频直播</span>
    <#else>
        <span class="classCategory">暂未开播</span>
    </#if>
<#elseif courseItem.courseForm == 1 && courseItem.multimediaType == 2>
    <#if courseItem.lineState  == 1  >
        <span class="classCategory">音频直播中</span>
    <#elseif courseItem.lineState  == 2>
        <span class="classCategory">音频直播预告</span>
    <#elseif courseItem.lineState  == 3>
        <span class="classCategory">音频直播回放</span>
    <#elseif courseItem.lineState  == 4>
        <span class="classCategory">即将音频直播</span>
    <#else>
        <span class="classCategory">暂未开播</span>
    </#if>
<#elseif courseItem.courseForm == 3>
    <span class="classCategory">线下课程</span>
</#if>


    <div class="detail">
        <p class="title" data-text="音频测试3"
           title="${courseItem.gradeName}">${courseItem.gradeName}</p>
        <p class="timeAndTeac">
            <span class="teacher z">${courseItem.name}</span>
        <#if courseItem.courseForm == 3>
            <span class="y" style="float: right;">${courseItem.city}</span>
        </#if>
        </p>
        <p class="info clearfix">
									 <span>
                                     <#if courseItem.currentPrice gt 0 >
                                         <span class="price">${courseItem.currentPrice}</span>
									 	 <span>熊猫币</span>
                                         <#if courseItem.originalCost?? && courseItem.originalCost != 0>
                                                     <span style="text-decoration: line-through;font-size: 12px;">原价${courseItem.originalCost}</span>
                                         </#if>
                                     <#else>
                                         <span class="price">免费</span>
                                         <#if courseItem.originalCost?? && courseItem.originalCost != 0>
                                                     <span style="text-decoration: line-through;font-size: 12px;">原价${courseItem.originalCost}</span>
                                         </#if>
                                     </#if>
									 </span>
            <span class="stuCount"><img src="/web/images/studentCount.png" alt="">
								<span class="studentCou">${courseItem.learndCount}</span></span>
        </p>
    </div>
</a>
</li>