<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="js/cloudClass/map.min.js"></script>
<script type="text/javascript" src="js/cloudClass/question_type_normal.js"></script>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,function() {});
	} catch (e) {}
</script>
<script type="text/javascript" src="js/cloudClass/question_edit.js?1"></script>
<style>
.picsc{
    padding: 0px;
}
    .ace-file-input{
        width: 69px!important;
        display: inline-block;
    }
    .ace-file-multiple .ace-file-container:before {
display: inline-block;
content: attr(data-title);
position: relative;
right: 0;
left: 0;
 margin: 0px; 
line-height: 22px;
background-color: #FFF;
color: #CCC;
font-size: 12px;
font-weight: bold;
border-width: 0;
}
</style>
<div class="page-header">				
	<div class="row"> 
        <div class="col-xs-8">
      <ul class="breadcrumb">
        <li>
            
            当前位置
        </li>
        <span>:</span>
        <li>
            题库管理
        </li>
        <i class="ace-icon fa fa-angle-double-right"></i>
        <li class="active">编辑题目</li>
    </ul>   
    </div>
    <div class="col-xs-4">
        <div class="fright"><input type="button" id="" onclick="returnToList();" class="btn btn-default btn-sm" value="返回题目列表"></div>
        
    </div>
    </div>

</div><!-- 虚线效果 -->

<questionAnswer id="questionAnswer"   style="display:none;">${question.answer}</questionAnswer>
<questionSolution id="questionSolution" style="display:none;">${question.solution}</questionSolution>
<questionHead id="head"             style="display:none;">${question.questionHead}</questionHead>
<input type="hidden"      id="questionOptions"      value='${question.options}'>
<input type="hidden"      id="orgFileName"          value='${orgFileName}'>
<input type="hidden"      id="attachmentId"         value='${attachmentId}'>
<div id="questionDialogEdit" >
        <form enctype="" action="question/update" method="post" class="form-horizontal" role="form" id="question-edit-form">
            <input type="hidden" name="chapterId" id="knowledgeIds" value="${knowledgeIds}">
          <%--   <input type="hidden" name="lableId" id="lableId" value="${question.lableId}"> --%>
            <input type="hidden" name="questionType" id="questionType" value="${question.questionType}">
            <input type="hidden" name="id"           id="questionId"   value="${question.id}">
           <%--  <input type="hidden" name="questionNo"   id="questionNo"   value="${question.questionNo}"> --%>
            <input type="hidden" name="createTimeStr"id="createTimeStr"value="${question.createTime}">
          <!--    <div class="form-group">
             		<label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span> 标签: </label>
	                <div class="col-xs-12 col-sm-10">
	                    <p id="qustion_lable_type" class="paddingtop7px">
	                       
	                    </p>
	                </div>
            </div> -->
            <div class="form-group">
             	<label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span> 类型: </label>
                <div class="col-xs-12 col-sm-10 paddingtop7px">
                    <p id="qustion_type">
                    <span class="col-xs-1">
                        <input type="radio" id="multiple_one"  name="questionTypeRadio"  value="0" class="aa" checked><label for="multiple_one">单选题</label>
                    </span>
                    <span class="col-xs-1">
                        <input type="radio" id="multiple_many" name="questionTypeRadio"  value="1"><label for="multiple_many">多选题</label>
                    </span>
                    <span class="col-xs-1">
                        <input type="radio" id="true_false"    name="questionTypeRadio"  value="2"><label for="true_false">判断题</label>
                    </span>
                    <span class="col-xs-1">
                        <input type="radio" id="empty_spaces"  name="questionTypeRadio"  value="3"><label for="empty_spaces">填空题</label>
                    </span>
                    <span class="col-xs-1">
                        <input type="radio" id="raw_text"      name="questionTypeRadio"  value="4"><label for="raw_text">简答题</label>
                    </span>
                    <span class="col-xs-1">
                    	<input type="radio" id="code"          name="questionTypeRadio"  value="5"><label for="code">代码题</label>
                    </span>
                    <span class="col-xs-1">
                    	<input type="radio" id="practical"     name="questionTypeRadio"  value="6"><label for="practical">实操题</label>
                    </span>
                    </p>
                </div>
            </div>
            
            <div class="form-group">
             	<label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span>知识体系: </label>
                <div class="col-sm-4">
                  <input type="text" value="${kSystem.courseName}" id="courseName" class="col-xs-20 col-sm-8" readonly="readonly">
                 <input type="hidden" value="${question.courseId}" readonly="readonly"  name="courseId" id="ksystemId">
                    <%-- <select disabled class="col-xs-5 secheck2 {required:true}" name="ksystemId" id="ksystemId">
                   		<option value="">请选择</option>
                    	<c:forEach items="${kSystem}" var="var">
                    		<c:if test="${var.id != null}">
                    			<c:choose>  
								   <c:when test="${question.ksystemId == var.id}"> 
								   		<option selected value="${var.id}">${var.name}</option>';    
								   </c:when>  
								   <c:otherwise>    
								   		<option value="${var.id}">${var.name}</option>';
								   </c:otherwise>  
								</c:choose> 
                    		</c:if>
                    	</c:forEach>
                    </select> --%>
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span> 知识点: </label>
                <div class="col-xs-12 col-sm-7">
                    <input value="${kpointNames}" type="text" href="javascript:void(-1);" name="knowledgeNames" id="knowledgeNames" onclick="manageDialogEdit(this)" class="col-xs-10 col-sm-10 {required:true}">
                </div>
            </div>
            
          <%--   <div class="form-group">
                <label class="col-sm-1 control-label no-padding-right">关键字: </label>
                <div class="col-xs-12 col-sm-7">
                    <input value="${question.qKeyword}" type="text" name="qKeyword" id="qKeyword" maxlength="100" class="col-xs-10 col-sm-10">
                </div>
            </div> --%>
            
            <div id="questionContentValueEdit">
            </div>
            <div id="questionOptionValueEdit">
            </div>
            <div id="questionAnswerValueEdit">
            </div>
            <div id="questionSolutionValueEdit">
            </div>
            <div class="form-group">
             	<label class="col-sm-1 control-label no-padding-right"> 难度: </label>
                <div class="col-sm-5 paddingtop7px">
                        <span class="col-xs-2"><input <c:if test="${question.difficulty == 'A'}" >checked</c:if> type="radio" id="difficultyA" name="difficulty"  value="A"><label for="difficultyA">简单</label></span>
                        <span class="col-xs-2"><input <c:if test="${question.difficulty == 'B'}" >checked</c:if> type="radio" id="difficultyB" name="difficulty"  value="B"><label for="difficultyB">一般</label></span>
                        <span class="col-xs-2"><input <c:if test="${question.difficulty == 'C'}" >checked</c:if> type="radio" id="difficultyC" name="difficulty"  value="C"><label for="difficultyC">困难</label></span>
                        <span class="col-xs-2"><input <c:if test="${question.difficulty == 'D'}" >checked</c:if> type="radio" id="difficultyD" name="difficulty"  value="D"><label for="difficultyD">非常困难</label></span>
                </div>
            </div>
            <div class="form-group">
            	<label class="col-sm-1 control-label no-padding-right"> 状态: </label>
                <div class="col-sm-5 paddingtop7px">
                        <span class="col-xs-2"><input <c:if test="${question.status == '1'}" >checked</c:if> type="radio" id="status_enable"  name="status" value="1"> <label for="status_enable">启用</label></span>
                        <span class="col-xs-2"><input <c:if test="${question.status == '0'}" >checked</c:if> type="radio" id="status_disable" name="status" value="0"> <label for="status_disable">禁用</label></span>
                </div>
            </div>
            <div class="form-group">
            	<div class="col-sm-5">
            	</div>
                <div class="col-sm-4">
                    <input type="button" id="editButton" onclick="submitToEdit();" class="btn btn-primary btn-sm" name="" value="确定编辑">
                </div>
            </div>
        </form> 
    </div>
<!-- 编辑form end -->
<div id="knowledgeDialogDiv"></div>
<div id="knowledgeDialog" class="hide">
	<div class="row">
		<div class="col-xs-12">
			<jsp:include page="../common/ztree_two_block.jsp" flush="true" />
		</div>
	</div>
</div>
<div id="divText" class="hide">
</div>


