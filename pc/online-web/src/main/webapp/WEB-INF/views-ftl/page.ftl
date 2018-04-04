<#-- 参数说明：pageNo当前的页码，totalPage总页数， showPages显示的页码个数，callUrl回调方法名（需在js中自己定义）-->
<#macro page pageNo totalPage showPages callUrl>
    <#if totalPage!=1 && totalPage gt 0>
    <div class="page_list clearfix">
        <#if pageNo!=1>
            <a href="${callUrl+1}" class="top_page"><<</a>
            <a href="${callUrl+(pageNo-1)}" class="page_prev"><</a>
        </#if>
        <#if pageNo-showPages/2 gt 0>
            <#assign start = pageNo-(showPages-1)/2/>
            <#if showPages gt totalPage>
                <#assign start = 1/>
            </#if>
        <#else>
            <#assign start = 1/>
        </#if>
        <#if totalPage gt showPages>
            <#assign end = (start+showPages-1)/>
            <#if end gt totalPage>
                <#assign start = totalPage-showPages+1/>
                <#assign end = totalPage/>
            </#if>
        <#else>
            <#assign end = totalPage/>
        </#if>
        <#assign pages=start..end/>
        <#list pages as page>
            <#if page==pageNo>
                <a href="${callUrl+page}" class="current">${page}</a>
            <#else>
                <a href="${callUrl+page}">${page}</a>
            </#if>
        </#list>
        <#if pageNo!=totalPage>
            <a href="${callUrl+(pageNo+1)}" class="page_next">></a>
            <a href="${callUrl+totalPage}" class="end_page">>></a>
        </#if>
    </div>
    </#if>
</#macro>