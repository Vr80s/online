<#-- 参数说明：pageNo当前的页码，totalPage总页数， showPages显示的页码个数，callFunName回调方法名（需在js中自己定义）-->
<#macro page pageNo totalPage showPages callFunName>
    <#if totalPage!=1>
    <div class="page_list clearfix">
        <#if pageNo!=1>
            <a href="javascript:${callFunName+'('+1+')'};" class="top_page">首页</a>
            <a href="javascript:${callFunName+'('+(pageNo-1)+')'};" class="page_prev">上一页</a>
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
                <a href="javascript:${callFunName+'('+page+')'};" class="current">${page}</a>
            <#else>
                <a href="javascript:${callFunName+'('+page+')'};">${page}</a>
            </#if>
        </#list>
        <#if pageNo!=totalPage>
            <a href="javascript:${callFunName+'('+(pageNo+1)+')'};" class="page_next">下一页</a>
            <a href="javascript:${callFunName+'('+totalPage+')'};" class="end_page">尾页</a>
        </#if>
    </div>
    </#if>
</#macro>