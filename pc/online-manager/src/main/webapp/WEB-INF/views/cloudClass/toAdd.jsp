<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="js/chosen/chosen.css" />
<script src="/js/layer/layer.js"></script>
<script type="text/javascript" src="js/chosen/chosen.jquery.js"></script>
<script type="text/javascript" src="js/cloudClass/map.min.js"></script>
<script type="text/javascript" src="js/cloudClass/question_type_normal.js"></script>
<script type="text/javascript" src="js/cloudClass/question_add.js"></script>
<script type="text/javascript">

	createSelect($("#question-form #ksystemId"), basePath+"/cloudclass/videores/showTree?courseId="+$("#courseId").val()+"&courseName="+$("#courseName").val()+"&currentNodePid=''", {}, true,null, function(){
		//生成chosen 带有搜索
		$('#question-form #ksystemId').chosen({no_results_text: "没有匹配结果",allow_single_deselect:true,search_contains:true});
		if ($("#oldksystemId").val() != -1 && !isnull($("#oldksystemId").val())) {
			$("#question-form #ksystemId").val($("#oldksystemId").val()).trigger("chosen:updated");
		}
	});
	
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
		
	}
	
    /*
     *知识体系变化知识点文本框里的值清空
    */
    $(function(){
    	$('#ksystemId').change(function(){ 
    		$("#question-form #knowledgeIds").val("");
    		$("#question-form #knowledgeNames").val("");
    	});
    });
</script>
<style>
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
        <li class="active">新增题目</li>
    </ul>
    </div>
    <div class="col-xs-4">
        <div class="fright"><input type="button" id="" onclick="returnToList();" class="btn btn-default btn-sm" value="返回题目列表"></div>
          <input type="hidden" name="oldksystemId" id="oldksystemId" value="${courseId}">
    </div>
    </div>
</div><!-- 虚线效果 -->
<!-- 增加form -->
        <form enctype="" action="question/add" method="post" class="form-horizontal" role="form" id="question-form">
            <input type="hidden" name="knowledgeIds" id="knowledgeIds" >
            <input type="hidden" name="labelIds" id="labelIds" >
          
            <div class="form-group">
             		<label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span> 类型: </label>
	                <div class="col-xs-12 col-sm-10">
	                    <p id="qustion_type" class="paddingtop7px">
	                        <span class="col-xs-1">
	                           <input type="radio" id="Single"  name="questionType"  value="0" class="aa" checked><label for="Single">单选题</label>
	                        </span>
	                        <span class="col-xs-1">
	                           <input type="radio" id="Multiple"  name="questionType"  value="1"> <label for="Multiple">多选题</label>
	                        </span>
	                        <span class="col-xs-1">
	                          <input type="radio" id="trueOrfalse"  name="questionType"  value="2"> <label for="trueOrfalse">判断题</label>
	                        </span>
	                        <span class="col-xs-1">
	                           <input type="radio" id="Completion"  name="questionType"  value="3"> <label for="Completion">填空题</label>
	                        </span>
	                        <span class="col-xs-1">
	                            <input type="radio" id="shortanswer"  name="questionType" value="4"> <label for="shortanswer">简答题</label>
	                        </span>
	                        <span class="col-xs-1">
	                           <input type="radio" id="coding"  name="questionType"  value="5"> <label for="coding">代码题</label>
	                        </span>
	                        <span class="col-xs-1">
	                           <input type="radio" id="operation"  name="questionType"  value="6"> <label for="operation">实操题</label>
	                        </span> 
	                    </p>
	                </div>
            </div>
           
            
            <div class="form-group">
             	<label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span>知识体系: </label>
                <div class="col-sm-4">
                   <!--  <select class="col-xs-5 secheck2 {required:true}" name="ksystemId" id="ksystemId">
                    </select> -->
                    <input type="hidden" value="${courseId}" name="courseId" id="courseId" />
					<input type="text" value="${courseName}" name="courseName" id="courseName" />
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-1 control-label no-padding-right"><span style="color: red;">*</span> 知识点: </label>
                <div class="col-xs-12 col-sm-4">
                	<input type="hidden" value="${pId}"  id="pId" />
                	<input type="hidden" value="${currentNodeId}" name="chapterId" id="currentNodeId" />
                    <input type="text" value="${currentNodeName}" href="javascript:void(-1);" name="knowledgeNames" id="knowledgeNames" onclick="manageDialogAdd(this)" class="col-xs-10 col-sm-10 {required:true}">
                </div>
            </div>
            
            <div id="questionContentValue">
            </div>
            <div id="questionOptionValue">
            </div>
            <div id="questionAnswerValue">
            </div>
            <div id="questionSolutionValue">
            </div>
            <div class="form-group">
             	<label class="col-sm-1 control-label no-padding-right"> 难度: </label>
                <div class="col-xs-12 col-sm-5 paddingtop7px">
                    <p>
                       <span class="col-xs-2"> <input type="radio" id="difficultyA" name="difficulty"  value="A" checked="checked"><label for="difficultyA">简单</label></span>
                        <span class="col-xs-2"><input type="radio" id="difficultyB" name="difficulty"  value="B"> <label for="difficultyB">一般</label></span>
                        <span class="col-xs-2"><input type="radio" id="difficultyC" name="difficulty"  value="C"> <label for="difficultyC">困难</label></span>
                        <span class="col-xs-3"><input type="radio" id="difficultyD" name="difficulty"  value="D"> <label for="difficultyD">非常困难</label></span>
                    </p>
                </div>
            </div>
            <div class="form-group">
            	<label class="col-sm-1 control-label no-padding-right"> 状态: </label>
                <div class="col-sm-5 paddingtop7px">
                    <p>
                        <span class="col-xs-2"><input type="radio" id="enable"  name="status" value="1" checked="checked"> <label for="enable">启用<label></label></span>
                        <span class="col-xs-2"><input type="radio" id="unable"  name="status" value="0"> <label for="unable">禁用</label></span>
                    </p>
                </div>
            </div>
            <div class="form-group">
            	<div class="col-sm-3">
            	</div>
                <div class="col-sm-6">
                    <input type="button" id="addButton1" onclick="toReset();" class="btn btn-default btn-sm" name="" value="重置">
                    <input type="button" id="addButton2" onclick="submitToAdd(1);" class="btn btn-primary btn-sm" name="" value="提交并返回列表">
                    <input type="button" id="addButton3" onclick="submitToAdd(2);" class="btn btn-primary btn-sm" name="" value="提交并继续出题">
                </div>
            </div>
        </form> 
<div id="knowledgeDialogDiv"></div>
<div id="knowledgeDialog" class="hide">
	<div class="row">
		<div class="col-xs-12">
			<jsp:include page="../common/ztree_two_block.jsp" flush="true" />
		</div>
	</div>
</div>
<!-- 编辑器存文本数据的div begin -->
<div id="divText" class="hide">
</div>
<!-- 编辑器存文本数据的div end -->

