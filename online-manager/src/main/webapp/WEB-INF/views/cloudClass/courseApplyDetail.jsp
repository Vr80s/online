<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="/js/layer/skin/layer.css" type="text/css" />
<style>
	.z{float: left;}
	.y{float: right;}
	#btn-myset{
		width: 175px;
		margin: 0 auto;
	}
	#btn-myset button:nth-child(odd){


	}
	#btn-myset button:nth-child(even){
		float: right;

		
	}
	.my-set-leftTitle{
		width: 150px;
		text-align: right;
		font-size: 16px;
	}
	.my-set-rightText{
		width: 190px;
		text-align: left !important;
		font-size: 16px;
	}
	.my-setallContent p{
		font-size: 16px;
	}
	.my-setallContent span{
		font-size: 16px;
	}
	.return-text{
		    width: 500px;
    text-align: left !important;
    font-size: 16px;
	}
</style>
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
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">审核结果：</label>
			<div class="" >
				<div class="clearfix">
					<c:choose>
						<c:when test="${courseApplyInfo.status== '1'}">
							<label class="control-label no-padding-right my-set-rightText z">已通过</label>
						</c:when>
						<c:when test="${courseApplyInfo.status== '0'}">
							<label class="control-label no-padding-right my-set-rightText z">已拒绝</label>
						</c:when>
						<c:when test="${courseApplyInfo.status== '2'}">
							<label class="control-label no-padding-right my-set-rightText z">未审核</label>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
			<c:choose>
				<c:when test="${courseApplyInfo.status== '0'}">
					<div class="form-group" style="margin-top:18px;">
						<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">拒绝理由：</label>
						<div  >
							<label class="control-label no-padding-right return-text">${courseApplyInfo.dismissalText}</label>
						</div>
					</div>
					<div class="form-group" style="margin-top:18px;">
						<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">补充理由：</label>
						<div >
							<label class="control-label no-padding-right return-text">${courseApplyInfo.dismissalRemark}</label>
						</div>
					</div>
				</c:when>
			</c:choose>



		<div class="form-group" style="margin-top:18px;">
			<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">封面：</label>
			<div class="" >
				<img src="${courseApplyInfo.imgPath}" height="450" width="800" alt="课程封面" />
			</div>
		</div>
		<c:choose>
			<c:when test="${!courseApplyInfo.collection && courseApplyInfo.courseForm=='2'}">
				<div class="form-group" style="margin-top:18px;">
					<label class="col-sm-1 control-label no-padding-right  my-set-leftTitle z">视频：</label>
					<div class="" >
						${courseApplyInfo.playCode}
					</div>
				</div>
			</c:when>
		</c:choose>

		<div class="form-group" style="margin-top:50px;">
			<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">主标题：</label>


					<label class=" control-label no-padding-right my-set-rightText z">${courseApplyInfo.title}</label>



			<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z" >副标题：</label>


					<label class=" control-label no-padding-right my-set-rightText z">${courseApplyInfo.subtitle}</label>



			<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">课程类型：</label>


					<c:choose>
						<c:when test="${courseApplyInfo.courseForm== '1'}">
							<label class="control-label no-padding-right my-set-rightText z">直播</label>
						</c:when>
						<c:when test="${courseApplyInfo.courseForm== '2'}">
							<label class="control-label no-padding-right my-set-rightText z">点播</label>
						</c:when>
						<c:otherwise>
							<label class="control-label no-padding-right my-set-rightText z">线下课</label>
						</c:otherwise>
					</c:choose>



			<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">课程分类：</label>


					<label class="control-label no-padding-right z" style="font-size: 16px;">${courseApplyInfo.courseMenu}</label>


		</div>
		<div class="form-group" style="margin-top: 18px;">
			<label class="control-label no-padding-right my-set-leftTitle z">主讲人：</label>
			<div class="" >

					<label class=" control-label no-padding-right my-set-rightText z">${courseApplyInfo.lecturer}</label>

			</div>
<c:choose>
	<c:when test="${courseApplyInfo.courseForm== '2'}">
			<label class="control-label no-padding-right my-set-leftTitle z">多媒体类型：</label>


					<c:choose>
						<c:when test="${courseApplyInfo.multimediaType== '1'}">
							<label class="control-label no-padding-right my-set-rightText z">视频</label>
						</c:when>
						<c:when test="${courseApplyInfo.multimediaType== '2'}">
							<label class="control-label no-padding-right my-set-rightText z">音频</label>
						</c:when>
					</c:choose>


	</c:when>
</c:choose>
			<c:choose>
				<c:when test="${courseApplyInfo.courseForm== '3'}">
					<label class="control-label no-padding-right my-set-leftTitle z">授课地址：</label>


							<label class="control-label no-padding-right my-set-rightText z">${courseApplyInfo.address}</label>


				</c:when>
			</c:choose>
			<label class="control-label no-padding-right my-set-leftTitle z">课程单价：</label>


					<label class="control-label no-padding-right my-set-rightText z">${courseApplyInfo.price}熊猫币</label>


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
			<label class="control-label no-padding-right my-set-leftTitle z">课程开始时间：</label>
					<label class="control-label no-padding-right my-set-rightText z"><fmt:formatDate value="${courseApplyInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
	</c:when>
</c:choose>
			<c:choose>
	<c:when test="${courseApplyInfo.courseForm== '3'}">
			<label class="control-label no-padding-right my-set-leftTitle z">课程结束时间：</label>
					<label class="control-label no-padding-right my-set-rightText z"><fmt:formatDate value="${courseApplyInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
	</c:when>
</c:choose>
			<c:choose>
				<c:when test="${!courseApplyInfo.collection}">
					<label class="control-label no-padding-right my-set-leftTitle z">课程时长：</label>
							<label class="control-label no-padding-right my-set-rightText z">${courseApplyInfo.courseLength}分钟</label>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${courseApplyInfo.collection}">
					<label class="control-label no-padding-right my-set-leftTitle z">总集数：</label>


							<label class="control-label no-padding-right my-set-rightText z">${courseApplyInfo.courseNumber}</label>


				</c:when>

			</c:choose>

		</div>



		<div class="form-group " style="margin-top:50px;margin-bottom:30px">
			<label class="control-label no-padding-right my-set-leftTitle z" >主讲人介绍：</label>
			<div class="col-lg-10 my-setallContent" style="padding-left: 0;">
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
			<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">课程详情：</label>
			<div class="col-lg-10 my-setallContent" style="padding-left: 0;">
				<div class="clearfix" style="width: 1000px;">
					<p >${courseApplyInfo.courseDetail}</p>
				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${courseApplyInfo.collection}">
				<div class="form-group" style="margin-top:18px;">
					<label class="col-sm-1 control-label no-padding-right my-set-leftTitle z">课程大纲：</label>
					<div class="col-sm-10 my-setallContent" style="padding-left: 0;">
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

	<div id="btn-myset">
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
