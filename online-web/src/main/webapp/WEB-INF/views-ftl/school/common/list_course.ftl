<div class="course clearfix">
<#if courseItem.recommendSort?? &&  courseItem.recommendSort gt 0>
    <img style="position:absolute;width: 16%;top:-2px;left:-2px;z-index:999" src="/web/images/recommend2.png">
</#if>
<#if courseItem.courseForm == 1 && courseItem.multimediaType == 1>
<a style="cursor:pointer" href="${webUrl}/web/liveCoursePage/${courseItem.id}" target="_blank">
<#else>
<a style="cursor:pointer" href="${webUrl}/courses/${courseItem.id}/info" target="_blank">
</#if>
    <div class="img">
        <img src="${courseItem.smallImgPath}?imageMogr2/thumbnail/!280x157r|imageMogr2/gravity/Center/crop/280x157">
    </div>
    <div class="detail">
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
            <span class="classCategory">即将语音直播</span>
        <#else>
            <p class="class-style">暂未开播</p>
        </#if>
    <#elseif courseItem.courseForm == 1 && courseItem.multimediaType == 2>
        <#if courseItem.lineState  == 1  >
            <span class="classCategory">语音直播中</span>
        <#elseif courseItem.lineState  == 2>
            <span class="classCategory">语音直播预告</span>
        <#elseif courseItem.lineState  == 3>
            <span class="classCategory">语音直播回放</span>
        <#elseif courseItem.lineState  == 4>
            <span class="classCategory">即将语音直播</span>
        <#else>
            <p class="class-style">暂未开播</p>
        </#if>
    <#elseif courseItem.courseForm == 3>
        <span class="classCategory">线下课程</span>
    </#if>
        <p class="title" title="${courseItem.gradeName}">${courseItem.gradeName}</p>
        <p class="timeAndTeac">
            <span class="teacher z">${courseItem.name}</span>
        <#if courseItem.courseForm == 3>
            <span class="y">${courseItem.city}</span>
        <#elseif courseItem.lineState?? && courseItem.lineState !=1>
            <#if courseItem.startDateStr?index_of(".")!=-1 >
                <span class="y">
                    <img src="/web/images/date_month.png" style="margin: -3px 2px 0 0;"/>${courseItem.startTime?string("MM月dd日")}</span>
            <#else>
                <span class="y">
                    <img src="/web/images/myvideo-time.png" style="margin: -3px 3px 0 0;"/>${courseItem.startDateStr}</span>
            </#if>
        </#if>
        </p>
        <p class="info clearfix">
            <span>
                <#if courseItem.currentPrice gt 0 >
                    <span class="price">${courseItem.currentPrice}</span>
                        <span>熊猫币</span>
                    <#if (courseItem.originalCost)?? && courseItem.originalCost != 0>
                        <span>原价</span>
                        <span style="text-decoration: line-through">${courseItem.originalCost}</span>
                    </#if>
                <#else>
                    <span class="price">免费</span>
                    <#if (courseItem.originalCost)?? && courseItem.originalCost != 0>
                        <span>原价</span>
                        <span style="text-decoration: line-through">${courseItem.originalCost}</span>
                    </#if>
                </#if>
            </span>
            <span class="stuCount">
                <img src="/web/images/studentCount.png" alt="">
                <span class="studentCou">${courseItem.learndCount}</span>
            </span>
        </p>
    </div>
</a>
</div>