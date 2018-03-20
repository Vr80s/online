<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%><%@ include file="../common/jstl_taglib.jsp"%>
<%
	String[] nums = {"一","二","三","四","五","六"};
	request.setAttribute("nums", nums);	
%>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
	}
</script>
<link rel="stylesheet" href="/css/common.css"/>
<link rel="stylesheet" href="/css/npyPaper.css"/>
<link rel="stylesheet" href="/js/imageviewer/viewer.min.css"/>
<link href="/js/layer/skin/layer.css" type="text/css" />
<script src="/js/layer/layer.js"></script>
<div class="page-header">
	<input id="py_type" type="hidden" value="${type}"/>
	<ul class="breadcrumb">
		<li>当前位置 : 作业管理</li>
		<li>${className}</li>
		<li>${paperName}</li>
		<li>${studentName}</li>
	</ul>
	<div class="fright">
		<button class="btn btn-sm" onclick="returnStudentPaperList()">
		<i class="fa fa-arrow-left returnCalssList">返回</i></button>
	</div>
</div>

<div class="checkhomework">
    <ul class="ultabs">
        <li id="li1" class="liactive">主观题</li>
        <li id="li2">客观题</li>
    </ul>
    <div class="divpaper">
        <div class="divtab divactive" id="div1">
        	<c:if test="${0==fn:length(questionType_3.questionInfoVos) && 0==fn:length(questionType_4.questionInfoVos) && 0==fn:length(questionType_5.questionInfoVos) && 0==fn:length(questionType_6.questionInfoVos)
        		&& 0==fn:length(questionType_6.questionInfoVos)}">
        		<span style="margin-left: 25px;">当前试卷中没有主观题</span>
        	</c:if>
            <ul class="ulaccrodion">
            <c:if test="${0< fn:length(questionType_3.questionInfoVos)}">
                <li>
                   	<p class="ptitle">
                   	<span class="sptitle"><i class="fa fa-chevron-down title-arrow"></i>填空题 共
                   		<span id="tk_questionCount">${fn:length(questionType_3.questionInfoVos)}</span>题</span>
                   	<span class="sptitle-right">
                   	<span class="padding-right-10">满分：${fn:substring(questionType_3.totleScore, 0, fn:indexOf(questionType_3.totleScore, ".0"))}分</span>
                   		得分：<i  class="priceNum">${fn:substring(questionType_3.score, 0, fn:indexOf(questionType_3.score, ".0"))}</i>分</span>
                  		</p>
                    <div class="divaccordion" style="display:block;">
                    <c:forEach items="${questionType_3.questionInfoVos}" var="question"  varStatus="status">
                              <div class="divtk">
                                  <div class="pque"><span class="def-f-l">${status.index+1}、</span>
                                  ${question.question_head} &nbsp;(<span id="tk_spscore${status.index+1}" class="spscore" >
                                  ${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}</span>分)</div>
                                  <div class="indent_t">
                                  <div class="spt-line"><b class="stuA">学员答案：</b><c:out value="${question.user_answer}"></c:out></div>
                                  <div class="spt-line ptrueans"><b>参考答案：</b>${question.answer}</div>
                                  <div class="spt-line ptrueans"><b>答案说明：</b>${question.solution}</div>
                                 <div class="spt-line">
	                               	批阅：<span id="tk_star${status.index+1}" data-question_id="${question.id}" data-question_type="${questionType_3.questionTypeNumber}"
		                               	<c:choose>
		                               	<c:when test="${null == question || question.answer==null}"> class="star-dome notAnswer" </c:when>
		                               	<c:otherwise> class="star-dome" </c:otherwise>
		                               	</c:choose>>
	                               	</span>
									 <input type="text" class="small-input getscore padding-right-10 js_starinput" style="width:50px;"
									 	onkeyup="controllerValue(this,'${question.question_score}')" maxlength="4"
									 	onblur="ajaxRequestPy(this,'${question.id}','${question.question_score}')"
								 		value="${fn:substring(question.user_score, 0, fn:indexOf(question.user_score, '.0'))}"
								 		id="tk_targetInput${status.index+1}"/>&nbsp;分
									 <input type="hidden" value="${question.user_score}" class="fs_hiddenInput"/>
									 <span class="red" id="tk_targeterror${status.index+1}"></span>
								 </div>
                              	</div>
                              </div>
					</c:forEach>
                    </div>
                </li>
            </c:if>
            <c:if test="${0< fn:length(questionType_4.questionInfoVos)}">
                <li>
				<!-- 	                简答题 -->
                    <p class="ptitle">
                    	<span class="sptitle"><i class="fa fa-chevron-down title-arrow"></i>简答题 共
                    		<span id="jd_questionCount">${fn:length(questionType_4.questionInfoVos)}</span>题</span>
	                    <span class="sptitle-right">
	                    <span class="padding-right-10">满分：${fn:substring(questionType_4.totleScore, 0, fn:indexOf(questionType_4.totleScore, ".0"))}分</span>
	                    	得分：<i class="priceNum">${fn:substring(questionType_4.score, 0, fn:indexOf(questionType_4.score, ".0"))}</i>分</span>
                   	</p>
                    <div class="divaccordion">
                    <c:forEach items="${questionType_4.questionInfoVos}" var="question"  varStatus="status">
                           <div class="divtk">
                               <div class="pque"><span class="def-f-l">${status.index+1+fn:length(questionType_3.questionInfoVos)}、</span> 
                         		${question.question_head}&nbsp;(<span id="jd_spscore${status.index+1}" class="spscore" >
                         		${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}</span>分)</div>
                               <div class="indent_t">
                               <div class="spt-line"><b class="stuA">学员答案：</b>${question.user_answer}</div>
                               <div class="spt-line ptrueans"><b>参考答案：</b>${question.answer}</div>
                               <div class="spt-line ptrueans"><b>答案说明：</b>${question.solution}</div>
                               <div class="spt-line">
                                 	批阅：<span id="jd_star${status.index+1}" data-question_id="${question.id}" data-question_type="${questionType_4.questionTypeNumber}"
                                 	<c:choose>
                                     	<c:when test="${null == question || question.answer==null}"> class="star-dome notAnswer" </c:when>
                                     	<c:otherwise> class="star-dome" </c:otherwise>
                                   	</c:choose>></span>
									<input type="text" class="small-input getscore padding-right-10" style="width:50px;"
										onkeyup="controllerValue(this,'${question.question_score}')" maxlength="4"
										onblur="ajaxRequestPy(this,'${question.id}','${question.question_score}')"
							 			value="${fn:substring(question.user_score, 0, fn:indexOf(question.user_score, '.0'))}"
							 			maxlength="4" id="jd_targetInput${status.index+1}"/> &nbsp;分
									<input type="hidden" value="${question.user_score}" class="fs_hiddenInput"/>
									<span class="red" id="jd_targeterror${status.index+1}"></span>
                                 </div>
                               </div>
                           </div>
					</c:forEach>
                   	</div>
                </li>
            </c:if>
            <c:if test="${0< fn:length(questionType_5.questionInfoVos)}">
                <li>
                    <p class="ptitle"><span class="sptitle"><i class="fa fa-chevron-down title-arrow"></i>代码题 共
						<span id="dm_questionCount">${fn:length(questionType_5.questionInfoVos)}</span>题</span>
                   	<span class="sptitle-right">
                   		<span class="padding-right-10">满分：${fn:substring(questionType_5.totleScore, 0, fn:indexOf(questionType_5.totleScore, ".0"))}分</span>
                   		得分：<i class="priceNum">${fn:substring(questionType_5.score, 0, fn:indexOf(questionType_5.score, ".0"))}</i>分</span></p>
                    <div class="divaccordion">
                    <c:forEach items="${questionType_5.questionInfoVos}" var="question"  varStatus="status">
			            <div class="divtk">
		                    <div class="pque"><span class="def-f-l">${status.index+1+fn:length(questionType_5.questionInfoVos)+fn:length(questionType_3.questionInfoVos)}、</span>
		                    ${question.question_head} &nbsp;(<span id="dm_spscore${status.index+1}" class="spscore" >
		                    ${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}</span>分)</div>
							<div class="indent_t">
								<div class="spt-line"><b class="stuA" style="vertical-align: top; visibility: inherit; ">学员答案：</b>
									<div style="display: inline-block;">
										${question.user_answer}
									</div>
								</div>
								<div class="spt-line ptrueans"><b class="stuA">答案附件：</b>

                                    <c:choose>
                                        <c:when test="${!empty question && (fn:contains(question.usrFileName,'.jpg')
                                                    ||fn:contains(question.usrFileName,'.png')||fn:contains(question.usrFileName,'.gif')
                                                    ||fn:contains(question.usrFileName,'.JPG')
                                                    ||fn:contains(question.usrFileName,'.PNG')||fn:contains(question.usrFileName,'.GIF'))
                                                    }">
                                            <a href="javascript:void(-1)" class="sac-link">
                                            <img src="${question.answer_attachment_url}" class="sacimg"/>
                                            </a>

                                        </c:when>
                                        <c:when test="${!empty question && (fn:contains(question.usrFileName,'.txt')
                                                    ||fn:contains(question.usrFileName,'.doc')
                                                    ||fn:contains(question.usrFileName,'.docx')
                                                    ||fn:contains(question.usrFileName,'.xls')
                                                    ||fn:contains(question.usrFileName,'.xlsx'))
                                                    }">
											<a class="down_l" href="${question.answer_attachment_url}">${question.usrFileName}</a>
                                        </c:when>
                                        <%--<c:when test="${!empty question && question.usrFileName ne ''}">--%>
                                            <%--<span>${question.usrFileName}</span>--%>
                                        <%--</c:when>--%>
                                        <c:otherwise>
                                            <span>${question.usrFileName}</span>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${!empty question.answer_attachment_url}">
                                        <a class="down_l" href="${question.answer_attachment_url}">下载</a>
                                    </c:if>
								</div>
								<div class="spt-line ptrueans"><b>参考答案：</b>
									<c:choose>
										<c:when test="${!empty question && (fn:contains(question.orgFileName,'.jpg')
													||fn:contains(question.orgFileName,'.png')||fn:contains(question.orgFileName,'.gif')
													||fn:contains(question.orgFileName,'.JPG')
													||fn:contains(question.orgFileName,'.PNG')||fn:contains(question.orgFileName,'.GIF'))
													}">
											<a href="javascript:void(-1)" class="sac-link">
											<img src="${question.attachment_url}" class="sacimg"/>
											</a>
			
										</c:when>
										<c:when test="${!empty question && (fn:contains(question.orgFileName,'.txt')
													||fn:contains(question.orgFileName,'.doc')
													||fn:contains(question.orgFileName,'.docx')
													||fn:contains(question.orgFileName,'.xls')
													||fn:contains(question.orgFileName,'.xlsx'))
													}">
											<a class="down_l" href="${question.attachment_url}">${question.orgFileName}</a>
										</c:when>
										<%--<c:when test="${!empty question && question.orgFileName ne ''}">--%>
											<%--<span>${question.orgFileName}</span>--%>
										<%--</c:when>--%>
										<c:otherwise>
											<span>${question.orgFileName}</span>
										</c:otherwise>
									</c:choose>
									<c:if test="${!empty question.attachment_url}">
										<a class="down_l" href="${question.attachment_url}">下载</a>
									</c:if>
								
								</div>
								<div class="spt-line ptrueans"><b>答案说明：</b>${question.solution}</div>
								<div class="spt-line">
									批阅：<span id="dm_star${status.index+1}" data-question_id="${question.id}" data-question_type="${questionType_5.questionTypeNumber}"
									<c:choose>
                                     	<c:when test="${null == question.answer_attachment_url && null == question.user_answer}"> class="star-dome dmOrScNotAnswer" </c:when>
                                     	<c:otherwise> class="star-dome" </c:otherwise>
                                   	</c:choose>></span>
									<input type="text" class="small-input getscore padding-right-10" style="width:50px;"
										onkeyup="controllerValue(this,'${question.question_score}')" maxlength="4"
										onblur="ajaxRequestPy(this,'${question.id}','${question.question_score}')"
								 		value="${fn:substring(question.user_score, 0, fn:indexOf(question.user_score, '.0'))}"
								 		maxlength="4" id="dm_targetInput${status.index+1}"/>&nbsp;分
									<input type="hidden" value="${question.user_score}" class="fs_hiddenInput"/>
									<span class="red" id="dm_targeterror${status.index+1}"></span>
								</div>
							</div>
			            </div>
	             	</c:forEach>
	                </div>
                </li>
            </c:if>
            <c:if test="${0< fn:length(questionType_6.questionInfoVos)}">
                <li>
                    <p class="ptitle">
	                    <span class="sptitle"><i class="fa fa-chevron-down title-arrow"></i>实操题 共
							<span id="sc_questionCount">${fn:length(questionType_6.questionInfoVos)}</span>题</span>
	                    <span class="sptitle-right">
	                    	<span class="padding-right-10">满分：${fn:substring(questionType_6.totleScore, 0, fn:indexOf(questionType_6.totleScore, ".0"))}分</span>
	                    	得分：<i class="priceNum">${fn:substring(questionType_6.score, 0, fn:indexOf(questionType_6.score, ".0"))}</i>分</span>
                   	</p>
					<div class="divaccordion">
					<c:forEach items="${questionType_6.questionInfoVos}" var="question"  varStatus="status">
						<div class="divtk">
							<div class="pque"><span class="def-f-l">${status.index+1+fn:length(questionType_5.questionInfoVos)+fn:length(questionType_5.questionInfoVos)+fn:length(questionType_3.questionInfoVos)}、</span>
							${question.question_head}&nbsp;(<span id="sc_spscore${status.index+1}" class="spscore" >
							${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}</span>分)</div>
							<div class="indent_t">
								<div class="spt-line ptrueans"><b class="stuA">学员答案：</b>
                                    <c:choose>
                                        <c:when test="${!empty question && (fn:contains(question.usrFileName,'.jpg')
                                                    ||fn:contains(question.usrFileName,'.png')||fn:contains(question.usrFileName,'.gif')
                                                    ||fn:contains(question.usrFileName,'.JPG')
                                                    ||fn:contains(question.usrFileName,'.PNG')||fn:contains(question.usrFileName,'.GIF'))
                                                    }">
                                            <a href="javascript:void(-1)" class="sac-link">
                                            <img src="${question.answer_attachment_url}" class="sacimg"/>
                                            </a>

                                        </c:when>
                                        <c:when test="${!empty question && (fn:contains(question.usrFileName,'.txt')
                                                    ||fn:contains(question.usrFileName,'.doc')
                                                    ||fn:contains(question.usrFileName,'.docx')
                                                    ||fn:contains(question.usrFileName,'.xls')
                                                    ||fn:contains(question.usrFileName,'.xlsx'))
                                                    }">
											<a class="down_l" href="${question.answer_attachment_url}">${question.usrFileName}</a>
                                        </c:when>
                                        <%--<c:when test="${!empty question && question.usrFileName ne ''}">--%>
                                            <%--<span>${question.usrFileName}</span>--%>
                                        <%--</c:when>--%>
                                        <c:otherwise>
                                            <span>${question.usrFileName}</span>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${!empty question.answer_attachment_url}">
                                        <a class="down_l" href="${question.answer_attachment_url}">下载</a>
                                    </c:if>
								</div>
								<div class="spt-line ptrueans"><b>素材：</b>
									<c:choose>
										<c:when test="${!empty question && (fn:contains(question.orgFileName,'.jpg')
													||fn:contains(question.orgFileName,'.png')||fn:contains(question.orgFileName,'.gif')
													||fn:contains(question.orgFileName,'.JPG')
													||fn:contains(question.orgFileName,'.PNG')||fn:contains(question.orgFileName,'.GIF'))
													}">
											<a href="javascript:void(-1)" class="sac-link">
											<img src="${question.attachment_url}" class="sacimg"/>
											</a>
			
										</c:when>
										<c:when test="${!empty question && (fn:contains(question.orgFileName,'.txt')
													||fn:contains(question.orgFileName,'.doc')
													||fn:contains(question.orgFileName,'.docx')
													||fn:contains(question.orgFileName,'.xls')
													||fn:contains(question.orgFileName,'.xlsx'))
													}">
											<a class="down_l" href="${question.attachment_url}">${question.orgFileName}</a>
										</c:when>
										<%--<c:when test="${!empty question && question.answer ne ''}">--%>
											<%--<span>${question.orgFileName}</span>--%>
										<%--</c:when>--%>
										<c:otherwise>
											<span>${question.orgFileName}</span>
										</c:otherwise>
									</c:choose>
									<c:if test="${!empty question.attachment_url}">
										<a class="down_l" href="${question.attachment_url}">下载</a>
									</c:if>
								</div>
								<div class="spt-line ptrueans"><b>答案说明：</b>${question.solution}</div>
								<div class="spt-line">
									批阅：<span id="sc_star${status.index+1}" data-question_id="${question.id}" data-question_type="${questionType_6.questionTypeNumber}"
									<c:choose>
                                     	<c:when test="${null == question.answer_attachment_url && null == question.user_answer}"> class="star-dome dmOrScNotAnswer" </c:when>
                                     	<c:otherwise> class="star-dome" </c:otherwise>
                                   	</c:choose>></span>
									<input type="text" class="small-input getscore padding-right-10" style="width:50px;"
										onkeyup="controllerValue(this,'${question.question_score}')" maxlength="4"
										onblur="ajaxRequestPy(this,'${question.id}','${question.question_score}')"
								 		value="${fn:substring(question.user_score, 0, fn:indexOf(question.user_score, '.0'))}"
								 		maxlength="4" id="sc_targetInput${status.index+1}"/>&nbsp;分
									<input type="hidden" value="${question.user_score}" class="fs_hiddenInput"/>
									<span class="red" id="sc_targeterror${status.index+1}"></span>
								</div>
							</div>
						</div>
					</c:forEach>
					</div>
                </li>
            </c:if>
            </ul>
        </div>
        <div class="divtab" id="div2">
        	<c:if test="${0==fn:length(questionType_0.questionInfoVos) && 0==fn:length(questionType_1.questionInfoVos) && 0==fn:length(questionType_2.questionInfoVos)}">
        		<span style="margin-left: 25px;">当前试卷中没有客观题</span>
        	</c:if>
        	<ul class="ulaccrodion">
        	<c:if test="${0< fn:length(questionType_0.questionInfoVos)}">
                <li>
				<!-- 		                以下为单选题 -->
                   <p class="ptitle">
	                   <span class="sptitle"><i class="fa fa-chevron-down title-arrow"></i>单选题 共${fn:length(questionType_0.questionInfoVos)}题</span>
	                   <span class="sptitle-right">
	                   <span class="padding-right-10">满分：${fn:substring(questionType_0.totleScore, 0, fn:indexOf(questionType_0.totleScore, ".0"))}分</span>
	                   <span class="padding-right-10">正确题数：${questionType_0.correctQuestions}</span>
	                   <span class="padding-right-10">错误题数：${questionType_0.errorQuestions}</span>
	                    	得分：<i  class="priceNum">${fn:substring(questionType_0.score, 0, fn:indexOf(questionType_0.score, ".0"))}</i>分</span>
                   	</p>
                   <div class="divaccordion" style="display: block;">
                   <c:forEach items="${questionType_0.questionInfoVos}" var="question"  varStatus="status">
	               		<div class="divtk">
	                    <div class="pque"><span class="def-f-l">${status.index+1}、</span>
	                    	${question.question_head}&nbsp;(<span class="spscore" >${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}</span>分)</div>
						<div class="indent_t">
								<div class="divchose spt-line">
									<c:forEach items="${question.optionList}" var="opt">
										<p><input name="rdoans${status.index+1}" type="radio"
											<c:if test="${fn:contains(question.user_answer,opt.key)}"> checked="checked" </c:if>
										/>${opt.key}、${opt.value}
										<c:if test="${! empty opt.pic}"><img alt="" src="${opt.pic}"></c:if></p>
									</c:forEach>
								</div>
								<div class="spt-line"><b class="stuA">学员答案：</b>${question.user_answer}
								<c:choose>
									<c:when test="${question.user_score eq question.question_score}">
										<i class="iconcss iconcorrect"></i>
									</c:when> 
									<c:otherwise><i class="iconcss iconerror"></i></c:otherwise>
								</c:choose>
								</div>
								<div class="spt-line ptrueans"><b>参考答案：</b>${question.answer}</div>
								<div class="spt-line ptrueans"><b>答案说明：</b>${question.solution}</div>
						  </div>
		            	</div>
				  	</c:forEach>
			    </div>
                </li>
       	    </c:if>
       	    <c:if test="${0< fn:length(questionType_1.questionInfoVos)}">
                <li>
				<!-- 		                以下为多选题 -->
                   <p class="ptitle"><span class="sptitle"><i class="fa fa-chevron-down title-arrow"></i>多选题 共${fn:length(questionType_1.questionInfoVos)}题</span>
                   <span class="sptitle-right">
                   <span class="padding-right-10">满分：${fn:substring(questionType_1.totleScore, 0, fn:indexOf(questionType_1.totleScore, ".0"))}分</span>
                   <span class="padding-right-10">正确题数：${questionType_1.correctQuestions}</span>
                   <span class="padding-right-10">错误题数：${questionType_1.errorQuestions}</span>
                   	得分：<i  class="priceNum">${fn:substring(questionType_1.score, 0, fn:indexOf(questionType_1.score, ".0"))}</i>分</span></p>
                   <div class="divaccordion">
                    <c:forEach items="${questionType_1.questionInfoVos}" var="question"  varStatus="status">
                        <div class="divtk">
                            <div class="pque"><span class="def-f-l">${status.index+1+fn:length(questionType_0.questionInfoVos)}、</span>
                            	${question.question_head} &nbsp;(
                            	<span class="spscore" >${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}</span>分)</div>
							<div class="indent_t">
							<div class="divchose spt-line">
							<c:forEach items="${question.optionList}" var="opt">
								<p>
                                    <input name="rdoans" type="checkbox"
											<c:if test="${fn:contains(question.user_answer,opt.key)}"> checked="checked" </c:if>
									/>${opt.key}、${opt.value}
									<c:if test="${! empty opt.pic}"><img alt="" src="${opt.pic}"></c:if>
                                </p>
							</c:forEach>
							</div>
							<div class="spt-line"><b class="stuA">学员答案：</b>${question.user_answer}
							<c:choose>
								<c:when test="${question.user_score eq question.question_score}">
									<i class="iconcss iconcorrect"></i>
								</c:when> 
								<c:otherwise><i class="iconcss iconerror"></i></c:otherwise>
							</c:choose>
							</div>
							<div class="spt-line ptrueans"><b>参考答案：</b>${question.answer}</div>
							<div class="spt-line ptrueans"><b>答案说明：</b>${question.solution}</div>
							</div>
                        </div>
                       </c:forEach>
                	</div>
                </li>
            </c:if>
            <c:if test="${0< fn:length(questionType_2.questionInfoVos)}">
                <li>
				<!-- 		                以下为判断题 -->
                   <p class="ptitle"><span class="sptitle"><i class="fa fa-chevron-down title-arrow"></i>判断题 共${fn:length(questionType_2.questionInfoVos)}题</span>
                   <span class="sptitle-right">
                   <span class="padding-right-10">满分：${fn:substring(questionType_2.totleScore, 0, fn:indexOf(questionType_2.totleScore, ".0"))}分</span>
                   <span class="padding-right-10">正确题数：${questionType_2.correctQuestions}</span>
                   <span class="padding-right-10">错误题数：${questionType_2.errorQuestions}</span>
                  		得分：<i  class="priceNum">${fn:substring(questionType_2.score, 0, fn:indexOf(questionType_2.score, ".0"))}</i>分</span></p>
                   <div class="divaccordion">
                    <c:forEach items="${questionType_2.questionInfoVos}" var="question"  varStatus="status">
                        <div class="divtk">
                            <div class="pque"><span class="def-f-l">${status.index+1+fn:length(questionType_1.questionInfoVos)+fn:length(questionType_0.questionInfoVos)}、</span>
                            	${question.question_head} &nbsp;(
                            	<span class="spscore" >${fn:substring(question.question_score, 0, fn:indexOf(question.question_score, ".0"))}</span>分)</div>
							<div class="indent_t">
								<div class="spt-line"><b class="stuA">学员答案：</b>${question.user_answer}
								<c:choose>
									<c:when test="${question.user_score eq question.question_score}">
										<i class="iconcss iconcorrect"></i>
									</c:when> 
									<c:otherwise><i class="iconcss iconerror"></i></c:otherwise>
								</c:choose>
								</div>
								<div class="spt-line ptrueans"><b>参考答案：</b>${question.answer}</div>
								<div class="spt-line ptrueans"><b>答案说明：</b>${question.solution}</div>
							</div>
                        </div>
                        </c:forEach>
                   </div>
                </li>
            </c:if>
            </ul>
        </div>

	<div class="py-bottom">
		<div class="textarea-outer">
	          <div class="evaluate-outer">
	            <a href="javascript:void(-1)" class="evaluate-link">
					<h4 class="evaluate-h4">试卷评语</h4>
					<div class="evaluate-line"><i class="fa fa-chevron-down"></i></div>
					<ul class="evaluate-list">
						<li class="js-evaluate-li">做的不错！继续努力！</li>
						<li class="js-evaluate-li">这次题目完成的不是很理想，好好巩固最近学的知识，加油！</li>
						<li class="js-evaluate-li">这次题目完成的一般，再重新复习一下最近的知识，把没搞懂的再梳理一遍。</li>
						<li class="js-evaluate-li">完成的很棒，平常和同学多交流，大家互相帮助！</li>
					</ul>
	          	</a>
	          </div>
	          <div style="margin-top: 10px;">
	          	<textarea id="textarea_comment" maxlength="1000" class="kindeditor" style="width:100%;" placeholder="评语...">${comment}</textarea>
	          </div>
	    </div>
		<div class="py-button-outer">
		<input type="hidden" value="${classPaperId}" id="input_classPaperId"/>
		<input type="hidden" value="${className}" id="input_className"/>
		<input type="hidden" value="${classId}" id="input_classId"/>
		<input type="hidden" value="${paperName}" id="input_paperName"/>
		<input type="hidden" value="${courseId}" id="input_courseId"/>
		<input type="hidden" value="${userId}" id="input_userId"/>
		<input type="hidden" value="${studentName}" id="input_studentName"/>
        <input type="hidden" value="${userPaperId}" id="input_userPaperId"/>
	    <button type="button" class="btnone mar-rigt-30 pybtn_hide nextone" onclick="nextStu();">下一位学员</button>
		<button type="button" class="btnone btn-danger pybtn_hide" onclick="retStu();">返回列表</button>
		</div>
	</div>
    </div>
</div>

<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/imageviewer/viewer.min.js"> </script>
<script type="text/javascript" src="/js/homework/pyHomeworkPaper.js"> </script>