<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/jstl_taglib.jsp"%>
<!DOCTYPE>
<html>
  <head>
    <title>试卷预览</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/css/npyPaper.css"/>
  </head>
  <body>
	<div class="container-all">
	<a class="f-r" href="javascript:window.close();"><img src="/images/cancel-off-big.png" alt=""></a>
	<h1 class="big-title">${paperName}</h1>
	<c:if test="${0==fn:length(questionType_3.questionInfoVos) && 0==fn:length(questionType_4.questionInfoVos) && 0==fn:length(questionType_5.questionInfoVos) && 0==fn:length(questionType_6.questionInfoVos)
			&& 0==fn:length(questionType_6.questionInfoVos) && 0==fn:length(questionType_0.questionInfoVos) && 0==fn:length(questionType_1.questionInfoVos) && 0==fn:length(questionType_2.questionInfoVos)}">
		<span style="margin-left: 25px;">当前试卷中没有试题</span>
	</c:if>
	<c:if test="${0< fn:length(questionType_0.questionInfoVos)}">
		<div class="topic-type">
			<p class="type-background"><span class="type-title">单选题 共${fn:length(questionType_0.questionInfoVos)}题</span>
			<span class="f-r type-title">满分：${fn:substring(questionType_0.totleScore, 0, fn:indexOf(questionType_0.totleScore, ".0"))}分</span></p>
			<c:forEach items="${questionType_0.questionInfoVos}" var="question"  varStatus="status">
				<dl class="topic">
				<dt><span class="f-l">${status.index+1}、</span>
					<p class="topic-word-break">${question.question_head}(${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}分)</p> </dt>
				<c:forEach items="${question.optionList}" var="opt">
					<dd><span class="f-l bold-font"><input type="radio">${opt.key}、</span>
					<p class="topic-word-break nsimsun">
						<c:out value="${opt.value}" escapeXml="true" ></c:out> 
						<c:if test="${! empty opt.pic}"><img alt="" src="${opt.pic}"></c:if></p></dd>
				</c:forEach>
				<dd class="topic-word-break nsimsun"><span class="bold-font">参考答案：</span>${question.answer}</dd>
				<dd class="topic-word-break nsimsun"><span class="bold-font">答案说明：</span>${question.solution}</dd>
	
			   </dl>
			<b class="topic-hr"></b>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${0< fn:length(questionType_1.questionInfoVos)}">
		<div class="topic-type">
			<p class="type-background"><span class="type-title">多选题 共${fn:length(questionType_1.questionInfoVos)}题</span>
			<span class="f-r type-title">满分：${fn:substring(questionType_1.totleScore, 0, fn:indexOf(questionType_1.totleScore, ".0"))}分</span></p>
			<c:forEach items="${questionType_1.questionInfoVos}" var="question"  varStatus="status">
				<dl class="topic">
				<dt><span class="f-l">${status.index+1+fn:length(questionType_0.questionInfoVos)}、</span>
					<p class="topic-word-break">${question.question_head}(${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}分)</p> </dt>
				<c:forEach items="${question.optionList}" var="opt">
				<dd><span class="f-l bold-font"><input type="checkbox">${opt.key}、</span>
				<p class="topic-word-break nsimsun">
					<c:out value="${opt.value}" escapeXml="true" ></c:out> 
					<c:if test="${! empty opt.pic}"><img alt="" src="${opt.pic}"></c:if></p></dd>
				</c:forEach>
				<dd class="topic-word-break nsimsun"><span class="bold-font">参考答案：</span>${question.answer}</dd>
				<dd class="topic-word-break nsimsun"><span class="bold-font">答案说明：</span>${question.solution}</dd>
	
			</dl>
			<b class="topic-hr"></b>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${0< fn:length(questionType_2.questionInfoVos)}">
		<div class="topic-type">
			<p class="type-background"><span class="type-title">判断题 共${fn:length(questionType_2.questionInfoVos)}题</span>
			<span class="f-r type-title">满分：${fn:substring(questionType_2.totleScore, 0, fn:indexOf(questionType_2.totleScore, ".0"))}分</span></p>
			<c:forEach items="${questionType_2.questionInfoVos}" var="question"  varStatus="status">
				<dl class="topic">
				<dt><span class="f-l">${status.index+1 +fn:length(questionType_1.questionInfoVos) +fn:length(questionType_0.questionInfoVos)}、
				</span><p class="topic-word-break">${question.question_head}(${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}分)</p> </dt>
				<dd class="topic-word-break nsimsun"><span class="bold-font">参考答案：</span>${question.answer}</dd>
				<dd class="topic-word-break nsimsun"><span class="bold-font">答案说明：</span>${question.solution}</dd>
			</dl>
			<b class="topic-hr"></b>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${0< fn:length(questionType_3.questionInfoVos)}">
		<div class="topic-type">
			<p class="type-background"><span class="type-title">填空题 共${fn:length(questionType_3.questionInfoVos)}题</span>
			<span class="f-r type-title">满分：${fn:substring(questionType_3.totleScore, 0, fn:indexOf(questionType_3.totleScore, ".0"))}分</span></p>
			<c:forEach items="${questionType_3.questionInfoVos}" var="question"  varStatus="status">
				<dl class="topic">
				<dt><span class="f-l">${status.index+1 +fn:length(questionType_2.questionInfoVos) +fn:length(questionType_1.questionInfoVos)
														+fn:length(questionType_0.questionInfoVos)}、
				</span><p class="topic-word-break">${question.question_head}(${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}分)</p> </dt>
				<dd class="topic-word-break nsimsun"><span class="bold-font">参考答案：</span>${question.answer}</dd>
				<dd class="topic-word-break nsimsun"><span class="bold-font">答案说明：</span>${question.solution}</dd>
	
			</dl>
			<b class="topic-hr"></b>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${0< fn:length(questionType_4.questionInfoVos)}">
		<div class="topic-type">
			<p class="type-background"><span class="type-title">简答题 共${fn:length(questionType_4.questionInfoVos)}题</span>
			<span class="f-r type-title">满分：${fn:substring(questionType_4.totleScore, 0, fn:indexOf(questionType_4.totleScore, ".0"))}分</span></p>
			<c:forEach items="${questionType_4.questionInfoVos}" var="question"  varStatus="status">
				<dl class="topic">
				<dt><span class="f-l">${status.index+1 +fn:length(questionType_3.questionInfoVos)
												+fn:length(questionType_2.questionInfoVos) +fn:length(questionType_1.questionInfoVos)
												+fn:length(questionType_0.questionInfoVos)}、</span>
				<p class="topic-word-break">${question.question_head}(${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}分)</p></dt>
				<dd class="topic-word-break nsimsun"><span class="bold-font">参考答案：</span>${question.answer}</dd>
				<dd class="topic-word-break nsimsun"><span class="bold-font">答案说明：</span>${question.solution}</dd>
	
			</dl>
			<b class="topic-hr"></b>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${0< fn:length(questionType_5.questionInfoVos)}">
		<div class="topic-type">
			<p class="type-background"><span class="type-title">代码题 共${fn:length(questionType_5.questionInfoVos)}题</span>
			<span class="f-r type-title">满分：${fn:substring(questionType_5.totleScore, 0, fn:indexOf(questionType_5.totleScore, ".0"))}分</span></p>
			<c:forEach items="${questionType_5.questionInfoVos}" var="question"  varStatus="status">
				<dl class="topic">
				<dt><span class="f-l">${status.index+1 +fn:length(questionType_4.questionInfoVos)
												+fn:length(questionType_3.questionInfoVos) +fn:length(questionType_2.questionInfoVos)
												+fn:length(questionType_1.questionInfoVos) +fn:length(questionType_0.questionInfoVos)}、</span>
				<p class="topic-word-break">${question.question_head}(${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}分)</p></dt>
				<dd class="topic-word-break nsimsun"><span class="bold-font">参考答案：</span><span>${question.orgFileName}</span>
				<c:if test="${question.attachment_url ne ''}">
					<a class="down_l" href="${question.attachment_url}">下载</a>
				</c:if></dd>
				<dd class="topic-word-break nsimsun"><span class="bold-font">答案说明：</span>${question.solution}</dd>
			</dl>
			<b class="topic-hr"></b>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${0< fn:length(questionType_6.questionInfoVos)}">
		<div class="topic-type">
			<p class="type-background"><span class="type-title">实操题 共${fn:length(questionType_6.questionInfoVos)}题</span>
			<span class="f-r type-title">满分：${fn:substring(questionType_6.totleScore, 0, fn:indexOf(questionType_6.totleScore, ".0"))}分</span></p>
			<c:forEach items="${questionType_6.questionInfoVos}" var="question"  varStatus="status">
				<dl class="topic">
				<dt><span class="f-l">${status.index+1 +fn:length(questionType_5.questionInfoVos)
												+fn:length(questionType_4.questionInfoVos) +fn:length(questionType_3.questionInfoVos)
												+fn:length(questionType_2.questionInfoVos) +fn:length(questionType_1.questionInfoVos)
												+fn:length(questionType_0.questionInfoVos)}、</span>
				<p class="topic-word-break">${question.question_head}(${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}分)</p></dt>
				<dd class="topic-word-break nsimsun"><span class="bold-font">素材：</span><span>${question.orgFileName}</span>
				<c:if test="${question.attachment_url ne ''}">
					<a class="down_l" href="${question.attachment_url}">下载</a>
				</c:if></dd>
				<dd class="topic-word-break nsimsun"><span class="bold-font">答案说明：</span>${question.solution}</dd>
			</dl>
			<b class="topic-hr"></b>
			</c:forEach>
		</div>
	</c:if>
	</div>
	<script type="text/javascript" src="/bootstrap/assets/js/jquery.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".topic").on("click","img",function () {
				$(".imgpreshow").remove();
				var cimg = $("<img class='imgpreshow' src='"+$(this).attr("src")
						+"' style='height:400px; position: fixed; top: 20%; left: 30%; display: none;' />")
						.bind('click', function () {
					$(this).fadeOut();
				});
				$("body").append(cimg);
				cimg.fadeIn();
			});
			$(document).bind("click",function(e){
				var target = $(e.target);
				if(target.closest("img").length == 0){
					$(".imgpreshow").remove();
				}
			});
		});
		function catLastZeros(data){
			return ''+parseFloat(data);
		}
	</script>
	</body>
</html>
