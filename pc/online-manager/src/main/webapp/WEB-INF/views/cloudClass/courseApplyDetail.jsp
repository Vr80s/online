<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />	
<script type="text/javascript" src="js/cloudClass/courseApplyDetail.js"></script>
<script type="text/javascript">
	try {
		var scripts = [ null, null ];
		$('.page-content-area').ace_ajax('loadScripts', scripts,
				function() {
				});
	} catch (e) {
		
	}
	var courseApplyId = '${courseApplyInfo.id}';
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<div class="page-header">
  当前位置：云课堂管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
			</small> 
			课程管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
		</small>
  <span>审核课程 </span>
</div>
<!-- 修改 form -->
<div id="dialogArticleDiv"></div>
<div id="addArticleDialog" >

	<form id="addArticle-form" class="form-horizontal"  method="post" action="" >
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right">审核结果:</label>
			<div class="" >
				<div class="clearfix" style="width: 240px;">
					<c:choose>
						<c:when test="${courseApplyInfo.status== '1'}">
							<label class="control-label no-padding-right">已通过</label>
						</c:when>
						<c:when test="${courseApplyInfo.status== '0'}">
							<label class="control-label no-padding-right">已拒绝</label>
						</c:when>
						<c:when test="${courseApplyInfo.status== '2'}">
							<label class="control-label no-padding-right">未审核</label>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
			<c:choose>
				<c:when test="${courseApplyInfo.status== '0'}">
					<div class="form-group" style="margin-top:18px;">
						<label class="col-sm-1 control-label no-padding-right">拒绝理由:</label>
						<div  >
							<label class="control-label no-padding-right">${courseApplyInfo.dismissalText}</label>
						</div>
					</div>
					<div class="form-group" style="margin-top:18px;">
						<label class="col-sm-1 control-label no-padding-right">补充理由:</label>
						<div >
							<label class="control-label no-padding-right">${courseApplyInfo.dismissalRemark}</label>
						</div>
					</div>
				</c:when>
			</c:choose>



		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right">封面:</label>
			<div class="col-sm-1" >
				<img src="${courseApplyInfo.imgPath}"  alt="课程封面" />
			</div>
		</div>
		<c:choose>
			<c:when test="${!courseApplyInfo.collection && courseApplyInfo.courseForm=='2'}">
				<div class="form-group" style="margin-top:18px;">
					<label class="col-sm-1 control-label no-padding-right">视频:</label>
					<div class="" >
						${courseApplyInfo.playCode}
					</div>
				</div>
			</c:when>
		</c:choose>

		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right">主标题:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<label class=" control-label no-padding-right">${courseApplyInfo.title}</label>
				</div>
			</div>

			<label class="col-sm-1 control-label no-padding-right">副标题:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<label class=" control-label no-padding-right">${courseApplyInfo.subtitle}</label>
				</div>
			</div>

			<label class="col-sm-1 control-label no-padding-right">课程类型:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<c:choose>
						<c:when test="${courseApplyInfo.courseForm== '1'}">
							<label class="control-label no-padding-right">直播</label>
						</c:when>
						<c:when test="${courseApplyInfo.courseForm== '2'}">
							<label class="control-label no-padding-right">点播</label>
						</c:when>
						<c:otherwise>
							<label class="control-label no-padding-right">线下课</label>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<label class="col-sm-1 control-label no-padding-right">课程分类:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<label class="control-label no-padding-right">${courseApplyInfo.courseMenu}</label>
				</div>
			</div>
		</div>
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right">主播:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<label class=" control-label no-padding-right">${courseApplyInfo.lecturer}</label>
				</div>
			</div>
<c:choose>
	<c:when test="${courseApplyInfo.courseForm== '2'}">
			<label class="col-sm-1 control-label no-padding-right">多媒体类型:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<c:choose>
						<c:when test="${courseApplyInfo.multimediaType== '1'}">
							<label class="control-label no-padding-right">视频</label>
						</c:when>
						<c:when test="${courseApplyInfo.multimediaType== '2'}">
							<label class="control-label no-padding-right">音频</label>
						</c:when>
					</c:choose>
				</div>
			</div>
	</c:when>
</c:choose>
			<c:choose>
				<c:when test="${courseApplyInfo.courseForm== '3'}">
					<label class="col-sm-1 control-label no-padding-right">授课地址:</label>
					<div class="col-sm-1" >
						<div class="clearfix" style="width: 240px;">
							<label class="control-label no-padding-right">${courseApplyInfo.address}</label>
						</div>
					</div>
				</c:when>
			</c:choose>
			<label class="col-sm-1 control-label no-padding-right">课程单价:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<label class="control-label no-padding-right">${courseApplyInfo.price}熊猫币</label>
				</div>
			</div>
			<%--<label class="col-sm-1 control-label no-padding-right">是否上架:</label>--%>
			<%--<div class="col-sm-1" >--%>
				<%--<div class="clearfix" style="width: 240px;">--%>
					<%--<c:choose>--%>
						<%--<c:when test="${courseApplyInfo.sale}">--%>
							<%--<label class="control-label no-padding-right">是</label>--%>
						<%--</c:when>--%>
						<%--<c:otherwise>--%>
							<%--<label class="control-label no-padding-right">否</label>--%>
						<%--</c:otherwise>--%>
					<%--</c:choose>--%>
				<%--</div>--%>
			<%--</div>--%>
		</div>
		<div class="form-group" style="margin-top:18px;">
<c:choose>
	<c:when test="${courseApplyInfo.courseForm!= '2'}">
			<label class="col-sm-1 control-label no-padding-right">课程开始时间:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<label class="control-label no-padding-right"><fmt:formatDate value="${courseApplyInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
				</div>
			</div>
	</c:when>
</c:choose>
			<c:choose>
	<c:when test="${courseApplyInfo.courseForm== '3'}">
			<label class="col-sm-1 control-label no-padding-right">课程结束时间:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 240px;">
					<label class="control-label no-padding-right"><fmt:formatDate value="${courseApplyInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
				</div>
			</div>
	</c:when>
</c:choose>
			<c:choose>
				<c:when test="${!courseApplyInfo.collection}">
					<label class="col-sm-1 control-label no-padding-right">课程时长:</label>
					<div class="col-sm-1" >
						<div class="clearfix" style="width: 240px;">
							<label class="control-label no-padding-right">${courseApplyInfo.courseLength}分钟</label>
						</div>
					</div>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${courseApplyInfo.collection}">
					<label class="col-sm-1 control-label no-padding-right">总集数:</label>
					<div class="col-sm-1" >
						<div class="clearfix" style="width: 240px;">
							<label class="control-label no-padding-right">${courseApplyInfo.courseNumber}</label>
						</div>
					</div>
				</c:when>

			</c:choose>

		</div>



		<div class="form-group " style="margin-top:18px;margin-bottom:60px">
			<label class="col-sm-1 control-label no-padding-right" >主播介绍:</label>
			<div class="col-lg-10 " >
				<div class="clearfix" style="width: 1000px;">
					<p>${courseApplyInfo.lecturerDescription}</p>
				</div>
			</div>
		</div>

		<%--<div class="form-group" style="margin-top:18px;">--%>
			<%--<label class="col-sm-1 control-label no-padding-right">课程简介:</label>--%>
			<%--<div class="col-lg-10 " >--%>
				<%--<div class="clearfix" style="width: 1000px;">--%>
					<%--<p>${courseApplyInfo.courseDescription}</p>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right">课程详情:</label>
			<div class="col-sm-1" >
				<div class="clearfix" style="width: 1000px;">
					<p >${courseApplyInfo.courseDetail}</p>
				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${courseApplyInfo.collection}">
				<div class="form-group" style="margin-top:18px;">
					<label class="col-sm-1 control-label no-padding-right">课程大纲:</label>
					<div class="col-sm-1" >
						<div class="clearfix" style="width: 1000px;">
							<p >${courseApplyInfo.courseOutline}</p>
						</div>
					</div>
				</div>
			</c:when>
		</c:choose>
	
	</form>
	<c:choose>
		<c:when test="${courseApplyInfo.collection}">
			<table class="table table-bordered">
				<caption>包含课程</caption>
				<thead>
				<tr>
					<th>课程名称</th>
					<th>主播</th>
					<th>价格</th>
					<th>状态</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${courseApplyInfo.courseApplyInfoList}" var="cai" varStatus="vs">
					<tr>
						<td align = "center"><a id="aImg0" target="_blank" href='/home#cloudclass/courseApply/courseDetail?id=${cai.id}' style="color: blue">${cai.title}</a></td>
						<td align = "center">${cai.lecturer}</td>
						<td align = "center">${cai.price}</td>
						<c:choose>
							<c:when test="${cai.status== '2'}">
								<td align = "center"><span style="color: #257e77;">未审核</span></td>
							</c:when>
							<c:when test="${cai.status== '1'}">
								<td align = "center"><span style="color: #13da08;">已通过</span></td>
							</c:when>
							<c:when test="${cai.status== '0'}">
								<td align = "center"><span style="color: #da3346;">未通过</span></td>
							</c:when>
						</c:choose>

					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:when>
	</c:choose>

	<div class="col-xs-7" style="text-align: right;margin-top:50px;">
		<c:choose>
			<c:when test="${courseApplyInfo.status== '2'}">
				<button class="btn btn-success" id="previewSaveBtn" onclick="pass()">通过 </button>
				<button class="btn btn-warning" id="saveBtn" onclick="confirmNotPass()">不通过</button>
			</c:when>
			<c:when test="${courseApplyInfo.status== '1'}">
				<button class="btn btn-info  " id="previewSaveBtn">已通过 </button>
			</c:when>
			<c:when test="${courseApplyInfo.status== '0'}">
				<button class="btn btn-danger" id="previewSaveBtn">已拒绝 </button>
			</c:when>
		</c:choose>

  </div>
</div>


<div id="setDismissalDiv"></div>
<div id="setDismissalDialog" class="hide">
	<form action="" method="post" class="form-horizontal">
		<input type="hidden" name="courseId" id="courseId" value="">
		<div class="space-4"></div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"><i class="text-danger">*</i> 失败理由1: </label>
			<div class="col-sm-7">
				<select id="dismissal">
					<c:forEach items="${dismissalList}" var="dm" varStatus="vs1">
						<option value="${dm.code}">${dm.text}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="col-sm-3 control-label no-padding-right"> 补充: </label>
			<div class="col-sm-7">
				<textarea class="form-control" name="description" id="dismissalRemark"  rows="3" class="col-xs-10 col-sm-12 {required:true,rangelength:[1,200]}"></textarea>
			</div>
		</div>
	</form>
</div>
