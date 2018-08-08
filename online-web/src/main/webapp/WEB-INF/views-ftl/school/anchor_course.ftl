<!-- 导入自定义ftl -->
<#import "../page.ftl" as cast/>

<div class="wrap-class" style="overflow: hidden;">
<#if courseList?? && courseList.records?size gt 0>
    <#list courseList.records as courseItem>
    <#include "./common/list_course.ftl">
</#list>
</#if>
</div>
<@cast.page pageNo=courseList.current totalPage=courseList.pages showPages=5 callUrl="${webUrlParam}/courses?pageNumber=" />
